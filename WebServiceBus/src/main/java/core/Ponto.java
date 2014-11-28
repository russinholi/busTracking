package core;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
public class Ponto {
    
    private double latitude;
    private double longitude;
    private final int id;
    private String address;
    private boolean pontoDeOnibus = false;

    public Ponto(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        boolean correto = false;
        while(!correto) {
            try {
                //this.address = AddressConverter.getAddress(latitude, longitude);
                this.address = latitude+","+longitude;
                correto = true;
            } catch (Exception ex) {

            }
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public boolean isPontoDeOnibus() {
        return pontoDeOnibus;
    }

    public void setPontoDeOnibus(boolean pontoDeOnibus) {
        this.pontoDeOnibus = pontoDeOnibus;
    }

    public int getId() {
        return id;
    }
    
    public String pegarStringLatLng() {
        return latitude+","+longitude;
    }
    
    
    
    
}