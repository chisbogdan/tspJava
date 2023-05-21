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
public class Populatie {

    private final int dimPopulatie;
    public ArrayList<Individ> solutii;

    public Populatie(int dimPopulatie) {
        this.dimPopulatie = dimPopulatie;
        solutii = new ArrayList();
    }

    public void initRandom() {
        for (int i = 0; i < dimPopulatie; i++) {
            Individ individ = new Individ();
            individ.initRandom();
            solutii.add(individ);
        }
    }

    public void calculeazaDistanteTotale() {
        solutii.forEach((i) -> {
            i.calculeazaDistantaTotala();
        });
    }

    public Populatie selectieTurneu() {
        Populatie populatieParinti = new Populatie(2 * dimPopulatie);
        int indexCandidat1;
        int indexCandidat2;
        Individ candidat1;
        Individ candidat2;

        for (int i = 0; i < 2 * dimPopulatie; i++) {

            indexCandidat1 = AlgoritmGenetic.random.nextInt(dimPopulatie);
            indexCandidat2 = AlgoritmGenetic.random.nextInt(dimPopulatie);

            candidat1 = solutii.get(indexCandidat1);
            candidat2 = solutii.get(indexCandidat2);

            if (candidat1.distantaTotala < candidat2.distantaTotala) {
                populatieParinti.solutii.add(candidat1);
            } else {
                populatieParinti.solutii.add(candidat2);
            }
        }

        return populatieParinti;
    }

    Populatie selectieRuleta() {
        Populatie populatieParinti = new Populatie(2 * dimPopulatie);
        
        int[] fitness = calculeazaFitness();
        int fitnessTotal = calculeazaSumaFitness(fitness);
        float[] probabilitati = calculeazaProbabilitati(fitness, fitnessTotal);
        float[] sumeCumulate = calculeazaSumeCumulate(probabilitati);

        for (int i = 0; i < dimPopulatie*2; i++) {
            float numarRandom = AlgoritmGenetic.random.nextFloat();
            int index = determinaParinte(sumeCumulate, numarRandom);
            populatieParinti.solutii.add(solutii.get(index));
        }
        
         return populatieParinti;
    }

    private int[] calculeazaFitness(){
        int[] fitness = new int[dimPopulatie];
        int distantaTotalaSolutii = calculeazaDistantaTotala();
        
        for(int i = 0; i < dimPopulatie; i++){
            fitness[i] = distantaTotalaSolutii - solutii.get(i).distantaTotala;
        }
        
        return fitness;
    }
    
    private int calculeazaSumaFitness(int[] fitness) {
        int suma = 0;
        for(int i = 0; i < fitness.length; i++){
            suma += fitness[i];
        }
        return suma;
    }

    private float[] calculeazaProbabilitati(int[] fitness, float fitnessTotal) {
        float[] probabilitati = new float[dimPopulatie];
        for (int i = 0; i < dimPopulatie; i++) {
            probabilitati[i] = fitness[i] / fitnessTotal;
        }
        return probabilitati;
    }

    private float[] calculeazaSumeCumulate(float[] probabilitati) {
        float[] sumeCumulate = new float[dimPopulatie];
        sumeCumulate[0] = probabilitati[0];
        for (int i = 1; i < dimPopulatie; i++) {
            sumeCumulate[i] = sumeCumulate[i - 1] + probabilitati[i];
        }
        return sumeCumulate;
    }

    private int determinaParinte(float[] sumeCumulate, float numarRandom) {
        int i = 0;
        while (i < dimPopulatie - 1) {
            // Verific din care interval face parte numarul aleator generat
            if (numarRandom < sumeCumulate[i]) {
                return i;
            }
            i++;
        }
        // Numerele in virgula mobila sunt aproximate
        // Evitam situatia in care numarul aleator generat e 9.99999
        // Suma totala a valorilor fitness poate fi 9.99998 din cauza aproximarilor
        return dimPopulatie - 1;
    }

    public Populatie recombinare() {
        //copiii rezulta in urma recombinarii populatiei de parinti
        //care este de doua ori mai numeroasa decat populatia din care s-au
        //selectat parintii
        Populatie populatieCopii = new Populatie(dimPopulatie / 2);
        int indexTaietura, j, k;
        Individ parinte1, parinte2, copil;
        Punct p;

        for (int i = 0; i < dimPopulatie / 2; i++) {

            parinte1 = solutii.get(2 * i);
            parinte2 = solutii.get(2 * i + 1);
            copil = new Individ();

            indexTaietura = AlgoritmGenetic.random.nextInt(JavaApplication8.nrPuncteTraseu);

            //copiem genele primului parinte
            for (j = 0; j < indexTaietura; j++) {
                copil.traseu.add(parinte1.traseu.get(j));
            }

            for (j = 0, k = 0; k < (JavaApplication8.nrPuncteTraseu - indexTaietura); j++) {
                p = parinte2.traseu.get(j);
                if (!copil.traseu.contains(p)) {
                    copil.traseu.add(p);
                    k++;
                }
            }

            populatieCopii.solutii.add(copil);
        }

        return populatieCopii;
    }

    public Populatie recombinare2() {

        Populatie populatieCopii = new Populatie(dimPopulatie / 2);
        int indexTaieturaDreapta, indexTaieturaStanga, j, poz;
        Individ parinte1, parinte2, copil;
        Punct p;

        for (int i = 0; i < dimPopulatie / 2; i++) {

            parinte1 = solutii.get(2 * i);
            parinte2 = solutii.get(2 * i + 1);
            copil = new Individ();

            do {
                indexTaieturaDreapta = AlgoritmGenetic.random.nextInt(JavaApplication8.nrPuncteTraseu);
            } while (indexTaieturaDreapta == 0);

            indexTaieturaStanga = AlgoritmGenetic.random.nextInt(indexTaieturaDreapta);
            //copiem din primul parinte genele dintre punctele de taietura
            for (j = indexTaieturaStanga; j < indexTaieturaDreapta; j++) {
                copil.traseu.add(parinte1.traseu.get(j));
            }

            //completam cu genele din al doilea parinte parcurgand de la
            //al doilea punct de taietura spre dreapta
            for (j = indexTaieturaDreapta; j < JavaApplication8.nrPuncteTraseu; j++) {
                p = parinte2.traseu.get(j);
                if (!copil.traseu.contains(p)) {
                    copil.traseu.add(p);
                }
            }

            //si reluam secventa de la inceput cand se depaseste colectia
            poz = 0;
            for (j = 0; j < indexTaieturaDreapta; j++) {
                p = parinte2.traseu.get(j);
                if (!copil.traseu.contains(p)) {
                    copil.traseu.add(poz, p);
                    poz++;
                }
            }

            populatieCopii.solutii.add(copil);
        }

        return populatieCopii;
    }

    public void mutatie(int rataMutatie) {
        int nr;
        int indexPunctRandom;
        Punct punctRandom;
        Punct punctCurent;

        for (Individ i : solutii) {
            for (int j = 0; j < JavaApplication8.nrPuncteTraseu; j++) {
                nr = AlgoritmGenetic.random.nextInt(100);
                if (nr < rataMutatie) {
                    indexPunctRandom = AlgoritmGenetic.random.nextInt(JavaApplication8.nrPuncteTraseu);

                    punctRandom = i.traseu.get(indexPunctRandom);
                    punctCurent = i.traseu.get(j);

                    i.traseu.set(j, punctRandom);
                    i.traseu.set(indexPunctRandom, punctCurent);
                }
            }
        }
    }

    private int calculeazaDistantaTotala() {
        int suma = 0;
        for (Individ i : solutii) {
            suma += i.distantaTotala;
        }

        return suma;
    }

    /*
    private Individ cautaCelMaiBun() {
        Individ best = solutii.get(0);

        for (Individ i : solutii) {
            if (i.distantaTotala < best.distantaTotala) {
                best = i;
            }
        }

        return best;
    }
    */
}
