package Response;


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
public class ResponsePontoList {
    
    private List<ResponsePonto> onibuspontos;

    @JsonCreator
    public ResponsePontoList(List<ResponsePonto> onibuspontos) {
        this.onibuspontos = onibuspontos;
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
