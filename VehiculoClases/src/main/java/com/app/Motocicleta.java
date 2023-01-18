package com.app;

public class Motocicleta extends Vehiculo {

    private int cantidadPatas;

    public Motocicleta() {
        super();
        this.cantidadPatas = -1;
    }

    public Motocicleta(int in_cantidadPatas, int in_ccMotor, String in_combustible, int in_numeroRuedas, String in_marca, String in_linea, int in_cantidadPasajeros, Color in_color) {
        super(in_ccMotor, in_combustible, in_numeroRuedas, in_marca, in_linea, in_cantidadPasajeros, in_color);
        this.cantidadPatas = in_cantidadPatas;
    }

    public int darCantidadPatas() {
        return getCantidadPatas();
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
        return "Motocicleta";
    }

    @Override
    public int darCantidadPasajeros() {
        return getCantidadPasajeros();
    }

    public int getCantidadPatas() {
        return cantidadPatas;
    }

    public void setCantidadPatas(int in_cantidadPatas) {
        this.cantidadPatas = in_cantidadPatas;
    }

}
