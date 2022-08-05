package com.app.modelo;

public class Automovil extends MedioTransporte {

    //Atributos
    private String marca;

    //Constructor
    public Automovil(String marca, int velocidad, int potencia, int pasajeros, int costo, int consumo, int anio) {
        super(velocidad, potencia, pasajeros, costo, consumo, anio);
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void obtenerMarca() {
        System.out.println("La marca es :" + marca);

    }

    //Sobrecarga de método
    public void obtenerMarca(String adicional) {
        System.out.println("La marca es :" + marca + adicional);
    }

    @Override
    public String toString() {
        return marca + " => " + super.toString();
    }

}
