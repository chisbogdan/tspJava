package javaapplication8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaApplication8 {

    static ArrayList<Punct> puncte;
    static int nrTotalPuncte;
    static int nrPuncteTraseu;

    public static void main(String[] args) {

        System.out.println("Tastati numele fisierului de intrare(fără extensie):");
        Scanner scanner = new Scanner(System.in);
        //String numeFisier = scanner.nextLine();
        String numeFisier = "64lin318";
        System.out.println("Nume fisier intrare: " + numeFisier + ".gtsp");
        
        try {
            System.out.println("--- Date citite");
            
            //citim datele din fisier
            Scanner scannerFisier = new Scanner(new File("./seturiTEST/" + numeFisier + ".gtsp"));
            //ignoram primele 3 linii
            for (int i = 0; i < 3; i++) {
                scannerFisier.nextLine();
            }
            //obtinem nr de puncte printr-un split al urmatoarei linii
            String linieCitita = scannerFisier.nextLine();
            String[] subsiruri = linieCitita.split(":");
            nrTotalPuncte = Integer.parseInt(subsiruri[1].trim());
            System.out.println("x: " + nrTotalPuncte);

            for (int i = 0; i < 3; i++) {
                scannerFisier.nextLine();
            }

            //citim punctele
            puncte = new ArrayList();

            for (int i = 0; i < nrTotalPuncte; i++) {
                int nrOrdine = scannerFisier.nextInt();
                int x = scannerFisier.nextInt();
                int y = scannerFisier.nextInt();

                puncte.add(new Punct(nrOrdine, x, y));
                System.out.println(nrOrdine + " " + x + " " + y);
            }
            
            System.out.println("--- Sfarsit");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaApplication8.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Eroare citire fisier");
        }

        //citim p de la tastatura
        System.out.println("Tastati valoarea lui p:");
        //int p = scanner.nextInt();
        int p = 100;
        System.out.println("p: " + p);

        //nrPuncteTraseu = n
        //nrTotalPuncte = x
        nrPuncteTraseu = nrTotalPuncte * p / 100;
        System.out.println("n: " + nrPuncteTraseu);

        //nrMinGeneratii, dimPopulatie, rataMutatieMica, rataMutatieMare, delta, deltaMutatie
        AlgoritmGenetic algoritmGenetic = new AlgoritmGenetic(Integer.MAX_VALUE, 100, 5, 7, 50, 20);
        Individ solutie = algoritmGenetic.start();
        solutie.afiseaza();

        try {
            FileWriter fileWriter = new FileWriter(new File("./fisiereIesire/" + numeFisier + ".sol"));
            PrintWriter printWriter = new PrintWriter(fileWriter);
            // Writing text
            printWriter.println("--- Puncte vizitate");
            printWriter.println(nrPuncteTraseu);
            printWriter.println("--- Ordinea de vizitare");
            for(Punct punct: solutie.traseu){
                printWriter.print(punct.nrOrdine + ",");
            }
            printWriter.println(solutie.traseu.get(0).nrOrdine);
            printWriter.println("--- Distanța totală calculată");
            printWriter.println(solutie.distantaTotala);
            printWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(JavaApplication8.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Eroare scriere fisier");
        }
    }
}
