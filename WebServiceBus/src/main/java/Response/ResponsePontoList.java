package Response;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import core.model.Bus;
import core.model.Linha;
import core.model.Ponto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mask
 */
public class ResponsePontoList {
    
    private List<ResponsePonto> onibuspontos;

    @JsonCreator
    public ResponsePontoList(List<ResponsePonto> onibuspontos) {
        this.onibuspontos = onibuspontos;
    }

    
    
    public static List<ResponsePonto> convertBusListToResponsePonto(List<Bus> onibusPonto, Linha linha) {
    	List<ResponsePonto> responsePontos = new ArrayList<ResponsePonto>();
		for (Bus bus : onibusPonto) {
			for (Ponto ponto : linha.getPontosNaLinha()) {
				ResponsePonto responsePonto = new ResponsePonto(ponto,linha, bus);
				responsePonto.atualizar();
				responsePontos.add(responsePonto);
			}
		}
		return responsePontos;
	}



	/**
     * @return the posts
     */
    @JsonProperty
    public List<ResponsePonto> getOnibuspontos() {
        return onibuspontos;
    }

    /**
     * @param posts the posts to set
     */
    @JsonProperty
    public void setOnibuspontos(List<ResponsePonto> onibuspontos) {
        this.onibuspontos = onibuspontos;
    }
    
}
