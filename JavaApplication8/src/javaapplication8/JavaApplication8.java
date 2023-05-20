package javaapplication8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaApplication8 {
     static ArrayList<Punct> puncte;

    public static void main(String[] args) {
        int nrPuncte = 0;
        
        try {
            //citim datele din fisier
            Scanner scannerFisier = new Scanner(new File("./seturiTEST/11eil51.gtsp"));
            //ignoram primele 3 linii
            for (int i = 0; i < 3; i++) {
                scannerFisier.nextLine();
            }
            //obtinem nr de puncte printr-un split al urmatoarei linii
            String linieCitita = scannerFisier.nextLine();
            String[] subsiruri = linieCitita.split(":");
            nrPuncte = Integer.parseInt(subsiruri[1].trim());
            System.out.println(nrPuncte);

            for (int i = 0; i < 3; i++) {
                scannerFisier.nextLine();
            }

            //citim punctele
            puncte = new ArrayList();
            
            for(int i = 0; i < nrPuncte; i++){
                int nrOrdine = scannerFisier.nextInt();
                int x = scannerFisier.nextInt();
                int y = scannerFisier.nextInt();
                
                puncte.add(new Punct(nrOrdine, x, y));
                System.out.println(nrOrdine + " " + x + " " + y);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaApplication8.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Eroare citire fisier");
        }
        
        //citim p de la tastatura
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        System.out.println(p);
         
        int n = nrPuncte*p/100;
        System.out.println(n);
 
        AlgoritmGenetic algoritmGenetic = new AlgoritmGenetic();
        algoritmGenetic.setup(100, 200, 5);
        algoritmGenetic.start();
         
    }

}
