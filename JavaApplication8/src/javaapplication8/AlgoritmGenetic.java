package javaapplication8;

import java.util.Random;

public class AlgoritmGenetic {

    private final long timpAdmis;
    private final int dimPopulatie;

    private int rataMutatie;
    private final int rataMutatieMica;
    private final int rataMutatieMare;
    private final int deltaMutatie;

    private int generatieSolutie;
    private Populatie populatie;
    Individ solutie;

    static Random random = new Random();

    public AlgoritmGenetic(long timpAdmis, int dimPopulatie, int rataMutatieMica,
            int rataMutatieMare, int deltaMutatie) {

        this.timpAdmis = timpAdmis;
        this.rataMutatieMica = rataMutatieMica;
        rataMutatie = rataMutatieMica;
        this.rataMutatieMare = rataMutatieMare;
        this.dimPopulatie = dimPopulatie;
        this.deltaMutatie = deltaMutatie;
        populatie = new Populatie(dimPopulatie);
        solutie = new Individ();
        solutie.distantaTotala = Integer.MAX_VALUE;//initializam solutia cu infinit
    }

    public Individ start() {
        long tStart = System.nanoTime();
        //initializare populatie random
        populatie.initRandom();
        populatie.calculeazaDistanteTotale();
        actualizeazaSolutie(0);
        int deltaGeneratii = 0;
        //int nrRandom;

        Populatie populatieParinti;
        Populatie populatieCopii;
        int nrGeneratie;
        for (nrGeneratie = 1; (System.nanoTime() - tStart) < timpAdmis; nrGeneratie++) {
            //selectie
            populatieParinti = populatie.selectieTurneu();

            //recombinare
            populatieCopii = populatieParinti.recombinare();

            //mutatie
            actualizeazaRataMutatie(deltaGeneratii);
            populatieCopii.mutatie(rataMutatie);

            //copiii devin parinti in urmatoarea generatie
            populatie = populatieCopii;
            populatie.calculeazaDistanteTotale();
            //verificam daca am gasit o solutie mai buna
            actualizeazaSolutie(nrGeneratie);
            deltaGeneratii = nrGeneratie - generatieSolutie;
        }
        
        System.out.println("oprire\nNumar generatii: " + nrGeneratie
                + "\nGeneratia din care face parte solutia: " + generatieSolutie);
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
        } else {
            rataMutatie = rataMutatieMica;
        }
    }
}
