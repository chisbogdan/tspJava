/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class AlgoritmGenetic {

    private int nrMaxGeneratii;
    private int dimPopulatieInitiala;
    private int rataMutatie;
    private Populatie populatie;

    public AlgoritmGenetic() {
        populatie = new Populatie();
    }

    public void setup(int nrMaxGeneratii, int dimPopulatieInitiala,
            int rataMutatie) {
        this.nrMaxGeneratii = nrMaxGeneratii;
        this.rataMutatie = rataMutatie;
        this.dimPopulatieInitiala = dimPopulatieInitiala;
    }

    public void start() {
        //initializare populatie random
        populatie.initRandom();

        for(int i = 0; i < nrMaxGeneratii; i++) {
            populatie.calculeazaFitness();
            //selectie
            Populatie populatieNoua = populatie.selectie();
            //recombinare
            populatieNoua.recombinare();
            //mutatie
            populatieNoua.mutatie(rataMutatie);
            //copiii devin parinti in urmatoarea generatie
            populatie = populatieNoua;
        }
        
        //la ult
        populatie.calculeazaFitness();
    }
}
