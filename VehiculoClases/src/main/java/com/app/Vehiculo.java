package com.app;

public abstract class Vehiculo {

    // declaracion de variables, 
    // siemdo privadas para tenr la encapsulacion
    private int ccMotor;
    private String combustible;
    private int numeroRuedas;
    private String marca;
    private String linea;
    private int cantidadPasajeros;
    private Color color;

    // default constructor
    public Vehiculo() {
        this.ccMotor = -1;
        this.combustible = null;
        this.numeroRuedas = -1;
        this.marca = null;
        this.linea = null;
        this.cantidadPasajeros = -1;
        this.color = null;
    }

    // contructor con parametros
    public Vehiculo(int in_ccMotor, String in_combustible, int in_numeroRuedas, String in_marca, String in_linea, int in_cantidadPasajeros, Color in_color) {
        this.ccMotor = in_ccMotor;
        this.combustible = in_combustible;
        this.numeroRuedas = in_numeroRuedas;
        this.marca = in_marca;
        this.linea = in_linea;
        this.cantidadPasajeros = in_cantidadPasajeros;
        this.color = in_color;
    }

    public abstract int darCcMotor();

    public abstract String darCombustible();

    public abstract int darNumeroRuedas();

    public abstract String darMarcaLinea();

    public abstract String tipoVehiculo();

    public abstract int darCantidadPasajeros();

    public int getCcMotor() {
        return ccMotor;
    }

    public void setCcMotor(int in_ccMotor) {
        this.ccMotor = in_ccMotor;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String in_combustible) {
        this.combustible = in_combustible;
    }

    public int getNumeroRuedas() {
        return numeroRuedas;
    }

    public void setNumeroRuedas(int in_numeroRuedas) {
        this.numeroRuedas = in_numeroRuedas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String in_marca) {
        this.marca = in_marca;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String in_linea) {
        this.linea = in_linea;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int in_cantidadPasajeros) {
        this.cantidadPasajeros = in_cantidadPasajeros;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color in_color) {
        this.color = in_color;
    }

}
