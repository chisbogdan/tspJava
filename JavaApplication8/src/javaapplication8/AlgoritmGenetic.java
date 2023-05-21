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

    private final int nrMinGeneratii;
    private final int dimPopulatie;

    private int rataMutatie;
    private final int rataMutatieMica;
    private final int rataMutatieMare;

    private final int delta;
    private final int deltaMutatie;

    private int generatieSolutie;
    private Populatie populatie;
    Individ solutie;

    static Random random = new Random();

    public AlgoritmGenetic(int nrMinGeneratii, int dimPopulatie, int rataMutatieMica,
            int rataMutatieMare, int delta, int deltaMutatie) {

        this.nrMinGeneratii = nrMinGeneratii;
        this.rataMutatieMica = rataMutatieMica;
        this.rataMutatie = rataMutatieMica;
        this.rataMutatieMare = rataMutatieMare;
        this.dimPopulatie = dimPopulatie;
        this.delta = delta;
        this.deltaMutatie = deltaMutatie;
        populatie = new Populatie(dimPopulatie);
        solutie = new Individ();
        solutie.distantaTotala = Integer.MAX_VALUE;//initializam solutia cu infinit
    }

    public Individ start() {
        //initializare populatie random
        populatie.initRandom();
        populatie.calculeazaFitness();
        actualizeazaSolutie(0);
        int deltaGeneratii = 0;

        for (int nrGeneratie = 1; (nrGeneratie <= nrMinGeneratii) | (delta > deltaGeneratii); nrGeneratie++) {
            //selectie
            Populatie populatieParinti = populatie.selectie();
            //recombinare
            Populatie populatieCopii = populatieParinti.recombinare();
            //mutatie
            actualizeazaRataMutatie(deltaGeneratii);
            populatieCopii.mutatie(rataMutatie);

            //copiii devin parinti in urmatoarea generatie
            populatie = populatieCopii;
            populatie.calculeazaFitness();
            //System.out.println(nrGeneratie + " " + deltaGeneratii + " " + rataMutatie+"");
            //verificam daca am gasit o solutie mai buna
            actualizeazaSolutie(nrGeneratie);
            deltaGeneratii = nrGeneratie - generatieSolutie;
        }
        System.out.println("Generatia din care face parte solutia: " + generatieSolutie);
        return solutie;
    }

    private void actualizeazaSolutie(int nrGeneratie) {
        for (Individ i : populatie.solutii) {
            if (i.distantaTotala < solutie.distantaTotala) {
                solutie = i;
                generatieSolutie = nrGeneratie;
            }
        }
    }

    private void actualizeazaRataMutatie(int deltaGeneratii) {
        if (deltaGeneratii >= deltaMutatie) {
            rataMutatie = rataMutatieMare;
        }
        else{
            rataMutatie = rataMutatieMica;
        }
    }
}
