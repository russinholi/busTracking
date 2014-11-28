/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Felipe
 */
public class Crowdness implements Comparable<Crowdness>{
    
    private final int value;
    private final Date tempo;

    public Crowdness(int value) {
        this.value = value;
        tempo = Calendar.getInstance().getTime();
    }

    public int getValue() {
        return value;
    }

    public Date getTempo() {
        return tempo;
    }

    @Override
    public int compareTo(Crowdness o) {
        return o.getTempo().compareTo(this.tempo);
    }
    
    
    
    
    
}
