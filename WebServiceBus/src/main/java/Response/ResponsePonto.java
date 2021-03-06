/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Response;

import core.model.Bus;
import core.model.Linha;
import core.model.Ponto;

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

    public long getIdPonto() {
        return ponto.getId();
    }

    public long getIdOnibus() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ponto == null) ? 0 : ponto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponsePonto other = (ResponsePonto) obj;
		if (ponto == null) {
			if (other.ponto != null)
				return false;
		} else if (!ponto.equals(other.ponto))
			return false;
		return true;
	}
    
    
    
    
}
