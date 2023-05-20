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

    static Random random = new Random();

    public AlgoritmGenetic(int nrMaxGeneratii, int dimPopulatie, int rataMutatie) {
        this.nrMaxGeneratii = nrMaxGeneratii;
        this.rataMutatie = rataMutatie;
        this.dimPopulatie = dimPopulatie;
        populatie = new Populatie(dimPopulatie);
    }

    public void start() {
        //initializare populatie random
        populatie.initRandom();

        for (int i = 0; i < nrMaxGeneratii; i++) {
            populatie.calculeazaFitness();
            //selectie
            Populatie populatieParinti = populatie.selectie();
            //recombinare
            Populatie populatieCopii = populatieParinti.recombinare();
            //mutatie
            populatieCopii.mutatie(rataMutatie);
            //copiii devin parinti in urmatoarea generatie
            populatie = populatieCopii;
        }

        //la ult
        populatie.calculeazaFitness();
    }
}
