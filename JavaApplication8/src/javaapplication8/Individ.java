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
public class Individ {

    public ArrayList<Punct> traseu;
    public int distantaTotala;

    public Individ() {
        traseu = new ArrayList();
    }

    public void initRandom() {
        int index;
        int i = 0;
        while(i < JavaApplication8.nrPuncteTraseu) {
            index = AlgoritmGenetic.random.nextInt(JavaApplication8.nrTotalPuncte);
            Punct p = JavaApplication8.puncte.get(index);
            if (!traseu.contains(p)) {
                traseu.add(p);
                i++;
            }
        }
    }

    public void calculeazaDistantaTotala() {
        // distanta = 0;
        for (int i = 1; i < JavaApplication8.nrPuncteTraseu; i++) {
            distantaTotala += calculeazaDistantaEuclidiana(traseu.get(i - 1).x, traseu.get(i - 1).y,
                    traseu.get(i).x, traseu.get(i).y);
        }

        //adaugam distanta de la ultimul pct spre primul
        distantaTotala += calculeazaDistantaEuclidiana(traseu.get(JavaApplication8.nrPuncteTraseu - 1).x,
                traseu.get(JavaApplication8.nrPuncteTraseu - 1).y,
                traseu.get(0).x, traseu.get(0).y);
    }

    private int calculeazaDistantaEuclidiana(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        return (int) (Math.sqrt(deltaX * deltaX + deltaY * deltaY) + 0.5);
    }

    public void afiseaza(){
        traseu.forEach((p) -> {
            System.out.print(p.nrOrdine + " ");
        });
        System.out.println(traseu.get(0).nrOrdine + " distantaTotala: " + distantaTotala);
    }
}
