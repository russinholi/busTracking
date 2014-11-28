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

import core.Bus;
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
public class BusList {
    
    private List<Bus> buses;

    @JsonCreator
    public BusList(List<Bus> buses) {
        this.buses = buses;
    }

    
    
    /**
     * @return the posts
     */
    @JsonProperty
    public List<Bus> getBuses() {
        return buses;
    }

    /**
     * @param posts the posts to set
     */
    @JsonProperty
    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }
    
}
