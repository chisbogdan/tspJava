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
    public ArrayList<Individ> solutii;

    public Populatie() {
        solutii = new ArrayList();
    }
    
    public void initRandom(){
        
    }
    
    public void calculeazaFitness(){
        for(Individ i : solutii){
            i.calculeazaFitness();
        }
    }
    
    public Populatie selectie(){
        Populatie populatieNoua = new Populatie();
        
        
        return populatieNoua;
    }
    
    public void recombinare(){
    
    }
    
    public void mutatie(int rataMutatie){
    
    }
}
