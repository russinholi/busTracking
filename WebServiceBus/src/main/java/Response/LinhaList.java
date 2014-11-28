/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */

import core.Linha;
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
public class LinhaList {
    
    private List<Linha> linhas;

    @JsonCreator
    public LinhaList(List<Linha> linhas) {
        this.linhas = linhas;
    }

    
    
    /**
     * @return the posts
     */
    @JsonProperty
    public List<Linha> getLinhas() {
        return linhas;
    }

    /**
     * @param posts the posts to set
     */
    @JsonProperty
    public void setLinhas(List<Linha> linhas) {
        this.linhas = linhas;
    }
    
}
