package com.app;

public class TipoVehiculo {
    
    private String descripción;
    
    // Constructor por defecto
    TipoVehiculo() {
        this("");
    }

    // constructor con parametros
    public TipoVehiculo(String inDescripción) {
        this.descripción = inDescripción;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
        
}
