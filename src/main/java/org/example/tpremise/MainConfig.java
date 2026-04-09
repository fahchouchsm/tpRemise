package org.example.tpremise;

import org.example.tpremise.persistence.Remise;
import org.example.tpremise.persistence.Transaction;
import org.example.tpremise.persistence.repository.RemiseRepository;
import org.example.tpremise.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    public CommandLineRunner demonstrationRemise(RemiseRepository remiseRepository, 
                                                 TransactionService transactionService) {
        return args -> {
            System.out.println("\n=== DÉMONSTRATION DU SYSTÈME DE REMISE ===\n");

            // 1. Créer une remise (ou en récupérer une existante)
            System.out.println("1. Création/Récupération d'une remise...");
            Remise remise = remiseRepository.findById(1L)
                    .orElseGet(() -> {
                        Remise newRemise = new Remise();
                        newRemise.setMontantMin(1000);
                        newRemise.setMontantMax(5000);
                        newRemise.setTaux(0.05);
                        return remiseRepository.save(newRemise);
                    });
            System.out.println("   Remise trouvée/créée: ID=" + remise.getId() + 
                             ", Taux=" + remise.getTaux() + 
                             ", Plage: [" + remise.getMontantMin() + "-" + remise.getMontantMax() + "]");

            // 2. Effectuer un calcul et créer une transaction
            System.out.println("\n2. Calcul de remise et création de transaction...");
            double montantAvant = 2000;
            double remiseCalculee = montantAvant * remise.getTaux();
            double montantApres = montantAvant - remiseCalculee;

            Transaction transaction = transactionService.save(montantAvant, montantApres, remise);
            System.out.println("   Transaction créée: ID=" + transaction.getId());
            System.out.println("   Montant avant: " + montantAvant);
            System.out.println("   Remise appliquée: " + remiseCalculee);
            System.out.println("   Montant après: " + montantApres);

            // 3. Modifier la transaction
            System.out.println("\n3. Modification de la transaction...");
            double nouveauMontantAvant = 3000;
            double nouvelleRemise = nouveauMontantAvant * remise.getTaux();
            double nouveauMontantApres = nouveauMontantAvant - nouvelleRemise;

            Transaction transactionModifiee = transactionService.update(
                    transaction.getId(), 
                    nouveauMontantAvant, 
                    nouveauMontantApres, 
                    remise);
            System.out.println("   Transaction modifiée: ID=" + transactionModifiee.getId());
            System.out.println("   Nouveau montant avant: " + nouveauMontantAvant);
            System.out.println("   Nouvelle remise: " + nouvelleRemise);
            System.out.println("   Nouveau montant après: " + nouveauMontantApres);

            // 4. Afficher les détails de la transaction
            System.out.println("\n4. Détails de la transaction...");
            Transaction transactionFinale = transactionService.findById(transaction.getId()).get();
            System.out.println("   ID: " + transactionFinale.getId());
            System.out.println("   Date: " + transactionFinale.getDate());
            System.out.println("   Montant avant: " + transactionFinale.getMontantAvant());
            System.out.println("   Montant après: " + transactionFinale.getMontantApres());
            System.out.println("   Remise associée: ID=" + transactionFinale.getRemise().getId());

            // 5. Supprimer la transaction
            System.out.println("\n5. Suppression de la transaction...");
            Long transactionId = transaction.getId();
            transactionService.deleteById(transactionId);
            System.out.println("   Transaction ID=" + transactionId + " supprimée.");

            // 6. Vérifier la suppression
            System.out.println("\n6. Vérification de la suppression...");
            boolean existe = transactionService.findById(transactionId).isPresent();
            System.out.println("   La transaction existe toujours: " + existe);

            System.out.println("\n=== FIN DE LA DÉMONSTRATION ===\n");
        };
    }
}

