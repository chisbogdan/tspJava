/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programverificare;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class ProgramVerificare {

    static ArrayList<Punct> puncte;
    static int nrTotalPuncte;
    static int nrPuncteTraseu;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ///////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("Tastati numele fisierului de intrare(fără extensie):");
        Scanner scanner = new Scanner(System.in);
        String numeFisier = scanner.nextLine();
        System.out.println("Nume fisier intrare: " + numeFisier + ".gtsp");

        try {
            System.out.println("--- Date citite");

            //citim datele din fisier
            Scanner scannerFisier = new Scanner(new File("../seturiTEST/" + numeFisier + ".gtsp"));
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
            System.out.println("Eroare citire fisier");
        }

        //citim p de la tastatura
        System.out.println("Tastati valoarea lui p:");
        int p = scanner.nextInt();
        System.out.println("p: " + p);

        //nrPuncteTraseu = n
        //nrTotalPuncte = x
        nrPuncteTraseu = nrTotalPuncte * p / 100;
        System.out.println("n: " + nrPuncteTraseu);

        ////////////////////////////////////////////////////////////////////////////////////////////
        int nrPuncteVizitate = 0;
        int distantaTotalaCalculata = 0;
        String[] subsiruri = null;
        try {
            Scanner scannerFisier = new Scanner(new File("../fisiereIesire/" + numeFisier + ".sol"));
            scannerFisier.nextLine();
            nrPuncteVizitate = scannerFisier.nextInt();
            scannerFisier.nextLine();
            scannerFisier.nextLine();
            String linieCitita = scannerFisier.nextLine();
            subsiruri = linieCitita.split(",");
            scannerFisier.nextLine();
            distantaTotalaCalculata = scannerFisier.nextInt();

        } catch (FileNotFoundException ex) {
            System.out.println("Eroare citire fisier iesire");
        }
        //////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("Puncte de pe traseu citite din fisier:");
        for (String s : subsiruri) {
            System.out.print(s + " ");
        }
        System.out.println("\nD:" + distantaTotalaCalculata + "");


        int distantaVerificata = 0;
        for (int i = 1; i < subsiruri.length; i++) {
            int nrOrdinePredecesor = Integer.parseInt(subsiruri[i - 1]);
            int nrOrdineCurent = Integer.parseInt(subsiruri[i]);
            Punct curent = puncte.get(0), predecesor = puncte.get(0);

            for (Punct j : puncte) {
                if (j.nrOrdine == nrOrdinePredecesor) {
                    predecesor = j;
                    break;
                }
            }

            for (Punct j : puncte) {
                if (j.nrOrdine == nrOrdineCurent) {
                    curent = j;
                    break;
                }
            }
            
            distantaVerificata += calculeazaDistantaEuclideana(predecesor.x, predecesor.y,
                    curent.x, curent.y);

        }

        System.out.println("--- Erori");
        if(distantaVerificata != distantaTotalaCalculata){
            System.out.println("Nu a fost calculata corect distanta totala");
        }
        
        if (nrPuncteTraseu != nrPuncteVizitate) {
            System.out.println("Nu au fost vizitate un nr corect de puncte. Trebuie introdusa aceeasi valoare a lui p din momentul in care s-a cautat solutia.");
        }
        
        System.out.println("--- verificare completa");

    }

    static int calculeazaDistantaEuclideana(int x1, int y1, int x2, int y2) {
        int deltaX = x1 - x2;
        int deltaY = y1 - y2;
        return (int) (Math.sqrt(deltaX * deltaX + deltaY * deltaY) + 0.5);
    }

}
