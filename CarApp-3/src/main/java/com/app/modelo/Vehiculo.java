package com.app.modelo;

public class Vehiculo {

    private int altura;
    private int largo;
    private int ancho;
    private String traccion;
    private String motor;
    private String hibrido;
    private int marchas;
    private String transmision;
    private int mpgCiudad;
    private String tipoCombustible;
    private int mpgCarretera;
    private String clasificacion;
    private String id;
    private String marca;
    private String modeloAnio;
    private int anio;
    private int caballosFuerza;
    private int torque;

    public Vehiculo() {
        this.altura = -1;
        this.largo = -1;
        this.ancho = -1;
        this.traccion = "";
        this.motor = "";
        this.hibrido = "";
        this.marchas = -1;
        this.transmision = "";
        this.mpgCiudad = -1;
        this.tipoCombustible = "";
        this.mpgCarretera = -1;
        this.clasificacion = "";
        this.id = "";
        this.marca = "";
        this.modeloAnio = "";
        this.anio = -1;
        this.caballosFuerza = -1;
        this.torque = -1;
    }

    public Vehiculo(int altura, int largo, int ancho, String traccion, String motor, String hibrido, int marchas, String transmision, int mpgCiudad, String tipoCombustible, int mpgCarretera, String clasificacion, String id, String marca, String modeloAno, int anio, int caballosFuerza, int torque) {
        this.altura = altura;
        this.largo = largo;
        this.ancho = ancho;
        this.traccion = traccion;
        this.motor = motor;
        this.hibrido = hibrido;
        this.marchas = marchas;
        this.transmision = transmision;
        this.mpgCiudad = mpgCiudad;
        this.tipoCombustible = tipoCombustible;
        this.mpgCarretera = mpgCarretera;
        this.clasificacion = clasificacion;
        this.id = id;
        this.marca = marca;
        this.modeloAnio = modeloAno;
        this.anio = anio;
        this.caballosFuerza = caballosFuerza;
        this.torque = torque;
    }

    @Override
    public String toString() {
        return String.format("Marca: %15s => %30s, año %4d", getMarca(), getModeloAnio(), getAnio());
    }

    /**
     * @return the altura
     */
    public int getAltura() {
        return altura;
    }

    /**
     * @param altura the altura to set
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * @return the largo
     */
    public int getLargo() {
        return largo;
    }

    /**
     * @param largo the largo to set
     */
    public void setLargo(int largo) {
        this.largo = largo;
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @param ancho the ancho to set
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    /**
     * @return the traccion
     */
    public String getTraccion() {
        return traccion;
    }

    /**
     * @param traccion the traccion to set
     */
    public void setTraccion(String traccion) {
        this.traccion = traccion;
    }

    /**
     * @return the motor
     */
    public String getMotor() {
        return motor;
    }

    /**
     * @param motor the motor to set
     */
    public void setMotor(String motor) {
        this.motor = motor;
    }

    /**
     * @return the hibrido
     */
    public String getHibrido() {
        return hibrido;
    }

    /**
     * @param hibrido the hibrido to set
     */
    public void setHibrido(String hibrido) {
        this.hibrido = hibrido;
    }

    /**
     * @return the marchas
     */
    public int getMarchas() {
        return marchas;
    }

    /**
     * @param marchas the marchas to set
     */
    public void setMarchas(int marchas) {
        this.marchas = marchas;
    }

    /**
     * @return the transmision
     */
    public String getTransmision() {
        return transmision;
    }

    /**
     * @param transmision the transmision to set
     */
    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    /**
     * @return the mpgCiudad
     */
    public int getMpgCiudad() {
        return mpgCiudad;
    }

    /**
     * @param mpgCiudad the mpgCiudad to set
     */
    public void setMpgCiudad(int mpgCiudad) {
        this.mpgCiudad = mpgCiudad;
    }

    /**
     * @return the tipoCombustible
     */
    public String getTipoCombustible() {
        return tipoCombustible;
    }

    /**
     * @param tipoCombustible the tipoCombustible to set
     */
    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    /**
     * @return the mpgCarretera
     */
    public int getMpgCarretera() {
        return mpgCarretera;
    }

    /**
     * @param mpgCarretera the mpgCarretera to set
     */
    public void setMpgCarretera(int mpgCarretera) {
        this.mpgCarretera = mpgCarretera;
    }

    /**
     * @return the clasificacion
     */
    public String getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the modeloAnio
     */
    public String getModeloAnio() {
        return modeloAnio;
    }

    /**
     * @param modeloAnio the modeloAnio to set
     */
    public void setModeloAnio(String modeloAnio) {
        this.modeloAnio = modeloAnio;
    }

    /**
     * @return the anio
     */
    public int getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * @return the caballosFuerza
     */
    public int getCaballosFuerza() {
        return caballosFuerza;
    }

    /**
     * @param caballosFuerza the caballosFuerza to set
     */
    public void setCaballosFuerza(int caballosFuerza) {
        this.caballosFuerza = caballosFuerza;
    }

    /**
     * @return the torque
     */
    public int getTorque() {
        return torque;
    }

    /**
     * @param torque the torque to set
     */
    public void setTorque(int torque) {
        this.torque = torque;
    }

}
