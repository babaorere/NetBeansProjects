package com.app;

public class Autobus extends Automovil {

    public Autobus() {
        super();
    }

    public Autobus(int in_cantidadPuertas, int in_ccMotor, String in_combustible, int in_numeroRuedas, String in_marca, String in_linea, int in_cantidadPasajeros, Color in_color) {
        super(in_cantidadPuertas, in_ccMotor, in_combustible, in_numeroRuedas, in_marca, in_linea, in_cantidadPasajeros, in_color);
    }

    public int darCantidadPuertas() {
        return super.getCantidadPuertas();
    }

    @Override
    public int darCcMotor() {
        return super.getCcMotor();
    }

    @Override
    public String darCombustible() {
        return super.getCombustible();
    }

    @Override
    public int darNumeroRuedas() {
        return super.getNumeroRuedas();
    }

    @Override
    public String darMarcaLinea() {
        return super.darMarcaLinea();
    }

    @Override
    public String tipoVehiculo() {
        return "Autobus";
    }

    @Override
    public int darCantidadPasajeros() {
        return super.darCantidadPasajeros();
    }

}
