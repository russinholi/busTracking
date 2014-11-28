/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Geocoding;

/**
 *
 * @author Mask
 */
public class GeoPoint {
    
    private double latitude;
    private double longitude;

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        //System.out.println("gps = "+latitude+", "+longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getLatitudeE6() {
        return (int) (latitude);
    }

    public int getLongitudeE6() {
        return (int) (longitude);
    }
    
    
    
}
