package core.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
@Document
public class Bus {
    
	@Id
    private int id;
    private double latitudeAtual = 0;
    private double longitudeAtual = 0; 
    private String nome;
    private int linhaId;
    private int crowdness;
    private ArrayList<Crowdness> list = new ArrayList<Crowdness>();
    private Ponto anterior;
    private boolean dadosAtuais = false;
    private int tempoProximoPonto = 0;
    
    public Bus() {}
    
    public Bus(int id, Linha linha) {
        this.id = id;
        this.linhaId = linha.getId();
        linha.adicionarOnibus(this);
        this.nome = "Ônibus "+id+ " - "+linha.getNome();
        crowdness = 1;
        anterior = linha.getPontosNaLinha().get(0);
    }

    public double getLatitudeAtual() {
        return latitudeAtual;
    }

    public double getLongitudeAtual() {
        return longitudeAtual;
    }

    
    public long getId(){
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getLinhaId() {
        return linhaId;
    }

    public void setPosicaoAtual(double latitudeAtual, double longitudeAtual) {
        this.latitudeAtual = latitudeAtual;
        this.longitudeAtual = longitudeAtual;
    }
    
    public void setLinhaId(Linha l) {
        linhaId = l.getId();
        nome = "Onibus "+id+" - "+l.getNome();
        anterior = l.getPontosNaLinha().get(0);        
    }

    public int getCrowdness() {
        return crowdness;
    }
    
    public void setTempoProximoPonto(int tempo, Ponto p) {
        tempoProximoPonto = tempo;
        anterior = p;
        dadosAtuais = true;        
    }
    
    public boolean dadosAtualizados() {
        return dadosAtuais;
    }
    
    public int pegarTempoProximoPonto() {
        return tempoProximoPonto;
    }
//    public void setCrowdness(int crowdness) {
//        this.crowdness = crowdness;
//    }
    

    public void atualizarCrowdness() {
        if(list.isEmpty()) crowdness = 1;
        else {
            Iterator<Crowdness> iterador = list.iterator();
            Crowdness proxima;
            int somatorio = 0;
            int sum_indices = 0;
            Date agora = Calendar.getInstance().getTime();
            long tempoAgora = agora.getTime();
            long tempoCrowd;
            boolean removeu = false;
            int deltaT = 0;
            while(iterador.hasNext() && !removeu) {
                proxima = iterador.next();
                tempoCrowd = proxima.getTempo().getTime();
                deltaT = (int) (25 - ((tempoAgora - tempoCrowd) / 60000));
                if(deltaT <= 0) {                    
                    somatorio += (deltaT * proxima.getValue());
                    sum_indices += deltaT;
                }
                else {
                    list.remove(proxima);
                    removeu = true;
                }
            }

            if(removeu) Collections.sort(list);

            if(sum_indices == 0) crowdness = 1;
            else {
                float resultado = somatorio / sum_indices;
                crowdness = Math.round(resultado);
            }
                    
        }
        
        dadosAtuais = false;
        
        
    }

    public void adicionarCrowdness(int crowd) {
        Crowdness c = new Crowdness(crowd);
        list.add(c);        
    }
    
    public Ponto pegarUltimoPonto() {
        return anterior;
    }  
    
}
