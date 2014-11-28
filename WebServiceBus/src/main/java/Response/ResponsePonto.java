/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Response;

import core.*;

/**
 *
 * @author Felipe Cousin
 */
public class ResponsePonto implements Comparable<ResponsePonto>{
    
    private Linha linha;
    private Bus onibus;
    private Ponto ponto;
    private int tempo;
    private int crowdness;


    public ResponsePonto(Ponto ponto, Linha linha, Bus bus) {
        this.linha = linha;
        this.ponto = ponto;
        this.onibus = bus;       
        tempo = -1;
    }   

    public int getIdPonto() {
        return ponto.getId();
    }

    public int getIdOnibus() {
        return onibus.getId();
    }

    public String getLinha() {
        return linha.getNome();
    }

    public int getTempo() {
        return tempo;
    }

    public int getCrowdness() {
        return crowdness;
    }

    @Override
    public int compareTo(ResponsePonto o) {
        return this.tempo - o.getTempo();
    }

    public void atualizar() {
        tempo = linha.calcularTempo(onibus, ponto);
        crowdness = onibus.getCrowdness();
    }
    
    
    
    
}
