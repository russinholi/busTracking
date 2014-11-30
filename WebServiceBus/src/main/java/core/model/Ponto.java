package core.model;

import org.springframework.data.mongodb.core.mapping.Document;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
@Document
public class Ponto {
    
    private double latitude;
    private double longitude;
    private final long id;
    private String address;
    private boolean pontoDeOnibus = false;
    private long linhaId;

    public Ponto(long id, double latitude, double longitude) {
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

    public long getId() {
        return id;
    }
    
    public String pegarStringLatLng() {
        return latitude+","+longitude;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)id;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Ponto other = (Ponto) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	public long getLinhaId() {
		return linhaId;
	}

	public void setLinhaId(long linhaId) {
		this.linhaId = linhaId;
	}

}
