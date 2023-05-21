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

    public void calculeazaFitness() {
        solutii.forEach((i) -> {
            i.calculeazaFitness();
        });
    }

    public Populatie selectie() {
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

    public Populatie recombinare() {
        //copiii rezulta in urma recombinarii populatiei de parinti
        //care este de doua ori mai numeroasa decat populatia din care s-au
        //selectat parintii
        Populatie populatieCopii = new Populatie(dimPopulatie / 2);

        for (int i = 0; i < dimPopulatie / 2; i++) {

            Individ parinte1 = solutii.get(2 * i);
            Individ parinte2 = solutii.get(2 * i + 1);
            Individ copil = new Individ();

            int indexTaietura = AlgoritmGenetic.random.nextInt(JavaApplication8.nrPuncteTraseu);          
            
            //copiem genele primului parinte
            int j, k;
            for (j = 0; j < indexTaietura; j++) {
                copil.traseu.add(parinte1.traseu.get(j));
            }

            for (j = 0, k = 0; k < (JavaApplication8.nrPuncteTraseu - indexTaietura); j++) {
                Punct p = parinte2.traseu.get(j);
                if (!copil.traseu.contains(p)) {
                    copil.traseu.add(p);
                    k++;
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
}
