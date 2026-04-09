package org.example.tpremise;

import org.example.tpremise.exception.RemiseException;
import org.example.tpremise.service.ReductionFixService;
import org.example.tpremise.service.ReductionService;
import org.example.tpremise.service.ReductionVarService;

public class DemoMain {

    public static void main(String[] args) {
        System.out.println("=== Démo du projet de remise ===");
        afficher("Remise fixe", new ReductionFixService(), new double[]{100, 500, 2000});
        afficher("Remise variable", new ReductionVarService(), new double[]{500, 2000, 15000});

        System.out.println("\nLe calcul depuis la BDD se teste avec l'application Spring Boot.");

        try {
            new ReductionFixService().calculerRemise(0);
        } catch (RemiseException e) {
            System.out.println("Exception attendue: " + e.getMessage());
        }
    }

    private static void afficher(String  titre, ReductionService service, double[] montants) {
        System.out.println("\n" + titre);
        for (double montant : montants) {
            double remise = service.calculerRemise(montant);
            System.out.println("- montant = " + montant + ", remise = " + remise + ", total = " + (montant - remise));
        }
    }
}

