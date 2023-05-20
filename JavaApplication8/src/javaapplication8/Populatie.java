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
    
    private int dimPopulatie;
    public ArrayList<Individ> solutii;

    public Populatie(int dimPopulatie) {
        this.dimPopulatie = dimPopulatie;
        solutii = new ArrayList();
    }
    
    public void initRandom(){
        for(int i = 0; i < dimPopulatie; i++){
            Individ individ = new Individ();
            individ.initRandom();
            solutii.add(individ);
        }
    }
    
    public void calculeazaFitness(){
        for(Individ i : solutii){
            i.calculeazaFitness();
        }
    }
    
    public Populatie selectie(){
        Populatie populatieParinti = new Populatie(2*dimPopulatie);
        
        for(int i = 0; i < 2*dimPopulatie; i++){
            int indexCandidat1 = AlgoritmGenetic.random.nextInt(JavaApplication8.nrPuncteTraseu);
            int indexCandidat2 = AlgoritmGenetic.random.nextInt(JavaApplication8.nrPuncteTraseu);
            
            Individ candidat1 = solutii.get(indexCandidat1);
            Individ candidat2 = solutii.get(indexCandidat2);
            
            if(candidat1.distantaTotala < candidat2.distantaTotala){
                populatieParinti.solutii.add(candidat1);
            }
            else{
                populatieParinti.solutii.add(candidat2);
            }
        }
        
        return populatieParinti;
    }
    
    public Populatie recombinare(){
        //copiii rezulta in urma recombinarii populatiei de parinti
        //care este de doua ori mai numeroasa decat populatia din care s-au
        //selectat parintii
        Populatie populatieCopii = new Populatie(dimPopulatie/2);
        
        for(int i = 0; i < dimPopulatie/2; i++){
            
            Individ parinte1 = solutii.get(2*i);
            Individ parinte2 = solutii.get(2*i + 1);
            Individ copil = new Individ();
            
            int indexTaietura = AlgoritmGenetic.random.nextInt();
            
            //copiem genele primului parinte
            for(int j = 0; j < indexTaietura; j++){
                copil.traseu.add(parinte1.traseu.get(j));
            }
            
            for(int j = 0; j < JavaApplication8.nrPuncteTraseu; j++){
                Punct p = parinte2.traseu.get(j);
                if(!copil.traseu.contains(p)){
                    copil.traseu.add(p);
                }
            }
            
        }
        
        return populatieCopii;
    }
    
    public void mutatie(int rataMutatie){
    
    }
}
