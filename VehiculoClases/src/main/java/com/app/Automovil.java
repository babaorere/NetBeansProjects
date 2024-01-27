package com.app;

public class Automovil extends Vehiculo {

    private int cantidadPuertas;

    public Automovil() {
        super();
        this.cantidadPuertas = -1;
    }

    public Automovil(int in_cantidadPuertas, int in_ccMotor, String in_combustible, int in_numeroRuedas, String in_marca, String in_linea, int in_cantidadPasajeros, Color in_color) {
        super(in_ccMotor, in_combustible, in_numeroRuedas, in_marca, in_linea, in_cantidadPasajeros, in_color);
        this.cantidadPuertas = in_cantidadPuertas;
    }

    @Override
    public int darCcMotor() {
        return getCcMotor();
    }

    @Override
    public String darCombustible() {
        return getCombustible();
    }

    @Override
    public int darNumeroRuedas() {
        return getNumeroRuedas();
    }

    @Override
    public String darMarcaLinea() {
        return getMarca() + " : " + getLinea();
    }

    @Override
    public String tipoVehiculo() {
        return "Autimovil";
    }

    @Override
    public int darCantidadPasajeros() {
        return getCantidadPasajeros();
    }

    public int getCantidadPuertas() {
        return cantidadPuertas;
    }

    public void setCantidadPuertas(int in_cantidadPuertas) {
        this.cantidadPuertas = in_cantidadPuertas;
    }

}
