package org.example.tpremise;

import org.example.tpremise.JDBC.Remise;
import org.example.tpremise.JDBC.RemiseJdbcService;
import org.example.tpremise.JDBC.Transaction;
import org.example.tpremise.JDBC.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    public CommandLineRunner demonstrationRemise(RemiseJdbcService remiseJdbcService,
                                                 TransactionService transactionService) {
        return args -> {
            System.out.println("\n=== DEMONSTRATION DU SYSTEME DE REMISE ===\n");

            System.out.println("1. Creation/recuperation d'une remise...");
            Remise remise = remiseJdbcService.findById(1L)
                    .orElseGet(() -> remiseJdbcService.create(1000, 5000, 0.05));
            System.out.println("   Remise trouvee/cree: ID=" + remise.getId() +
                             ", Taux=" + remise.getTaux() +
                             ", Plage: [" + remise.getMontantMin() + "-" + remise.getMontantMax() + "]");

            System.out.println("\n2. Calcul de remise et creation de transaction...");
            double montantAvant = 2000;
            double remiseCalculee = montantAvant * remise.getTaux();
            double montantApres = montantAvant - remiseCalculee;

            Transaction transaction = transactionService.save(montantAvant, montantApres, remise);
            System.out.println("   Transaction creee: ID=" + transaction.getId());
            System.out.println("   Montant avant: " + montantAvant);
            System.out.println("   Remise appliquee: " + remiseCalculee);
            System.out.println("   Montant apres: " + montantApres);

            System.out.println("\n3. Modification de la transaction...");
            double nouveauMontantAvant = 3000;
            double nouvelleRemise = nouveauMontantAvant * remise.getTaux();
            double nouveauMontantApres = nouveauMontantAvant - nouvelleRemise;

            Transaction transactionModifiee = transactionService.update(
                    transaction.getId(),
                    nouveauMontantAvant,
                    nouveauMontantApres,
                    remise);
            System.out.println("   Transaction modifiee: ID=" + transactionModifiee.getId());
            System.out.println("   Nouveau montant avant: " + nouveauMontantAvant);
            System.out.println("   Nouvelle remise: " + nouvelleRemise);
            System.out.println("   Nouveau montant apres: " + nouveauMontantApres);

            System.out.println("\n4. Details de la transaction...");
            Transaction transactionFinale = transactionService.findById(transaction.getId())
                    .orElseThrow();
            System.out.println("   ID: " + transactionFinale.getId());
            System.out.println("   Date: " + transactionFinale.getDate());
            System.out.println("   Montant avant: " + transactionFinale.getMontantAvant());
            System.out.println("   Montant apres: " + transactionFinale.getMontantApres());
            System.out.println("   Remise associee: ID=" + transactionFinale.getRemiseId());

            System.out.println("\n5. Suppression de la transaction...");
            Long transactionId = transaction.getId();
            transactionService.deleteById(transactionId);
            System.out.println("   Transaction ID=" + transactionId + " supprimee.");

            System.out.println("\n6. Verification de la suppression...");
            boolean existe = transactionService.findById(transactionId).isPresent();
            System.out.println("   La transaction existe toujours: " + existe);

            System.out.println("\n=== FIN DE LA DEMONSTRATION ===\n");
        };
    }
}

