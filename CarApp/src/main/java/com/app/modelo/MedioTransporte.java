package com.app.modelo;

/**
 *
 */
public class MedioTransporte {

    private int anio;

    //atributos
    public int velocidad, potencia, pasajeros, costo, consumo;

    //constructor
    public MedioTransporte(int velocidad, int potencia, int pasajeros, int costo, int consumo, int anio) {
        this.velocidad = velocidad;
        this.potencia = potencia;
        this.pasajeros = pasajeros;
        this.costo = costo;
        this.consumo = consumo;
        this.anio = anio;
    }

    //Accesadores
    public int getVelocidad() {
        return velocidad;
    }

    public int getPotencia() {
        return potencia;
    }

    public int getPasajeros() {
        return pasajeros;
    }

    public int getCosto() {
        return costo;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public void setPasajeros(int pasajeros) {
        this.pasajeros = pasajeros;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public String toString() {
        return "[ " + String.valueOf(getVelocidad())
                + "; " + String.valueOf(getPotencia())
                + "; " + String.valueOf(getPasajeros())
                + "; " + String.valueOf(getCosto())
                + "; " + String.valueOf(getConsumo())
                + "; " + String.valueOf(getAnio()) + " ]";
    }

}
