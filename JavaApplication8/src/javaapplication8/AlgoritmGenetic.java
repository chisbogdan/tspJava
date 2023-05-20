/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

import java.util.Random;

/**
 *
 * @author User
 */
public class AlgoritmGenetic {

    private final int nrMaxGeneratii;
    private final int dimPopulatie;
    private final int rataMutatie;
    private Populatie populatie;
    Individ solutie;

    static Random random = new Random();

    public AlgoritmGenetic(int nrMaxGeneratii, int dimPopulatie, int rataMutatie) {
        this.nrMaxGeneratii = nrMaxGeneratii;
        this.rataMutatie = rataMutatie;
        this.dimPopulatie = dimPopulatie;
        populatie = new Populatie(dimPopulatie);
        solutie = new Individ();
        solutie.distantaTotala = Integer.MAX_VALUE;//initializam solutia cu infinit
    }

    public Individ start() {  
        //initializare populatie random
        populatie.initRandom();
        populatie.calculeazaFitness();
        actualizeazaSolutie();

        for (int i = 0; i < nrMaxGeneratii; i++) {  
            //selectie
            Populatie populatieParinti = populatie.selectie();
            //recombinare
            Populatie populatieCopii = populatieParinti.recombinare();
            //mutatie
            populatieCopii.mutatie(rataMutatie);
            //copiii devin parinti in urmatoarea generatie
            populatie = populatieCopii;
            populatie.calculeazaFitness();
            actualizeazaSolutie();
        }
        
        return solutie;
    }
    
    private void actualizeazaSolutie(){
        for(Individ i: populatie.solutii){
            if(i.distantaTotala < solutie.distantaTotala){
                solutie = i;
            }
        }
    }
}
