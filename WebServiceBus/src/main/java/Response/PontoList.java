package Response;


import core.model.Ponto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mask
 */
public class PontoList {
    
    private List<Ponto> pontos;

    @JsonCreator
    public PontoList(List<Ponto> pontos) {
        this.pontos = pontos;
    }

    
    
    /**
     * @return the posts
     */
    @JsonProperty
    public List<Ponto> getPontos() {
        return pontos;
    }

    /**
     * @param posts the posts to set
     */
    @JsonProperty
    public void setPontos(List<Ponto> pontos) {
        this.pontos = pontos;
    }
    
}
