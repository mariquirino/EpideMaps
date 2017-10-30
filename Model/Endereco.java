package com.example.mariana.projeto.Model;

/**
 * Created by Mariana on 10/09/2017.
 */

public class Endereco{
    private double latitude, longitude;

    public Endereco(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
