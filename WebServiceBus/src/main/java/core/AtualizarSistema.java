/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class AtualizarSistema extends Thread{
    
    private int tempo;
    private ConfigResource resource;
    
    public AtualizarSistema(int tempoMilli, ConfigResource res) {
        tempo = tempoMilli;
        resource = res;
    }
    
    @Override
    public void run() {
        while(true) {            
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException ex) {
                Logger.getLogger(AtualizarSistema.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            resource.atualizarTemposSistema();
        
        }
    }
    
}
