package com.app.controlanimal;


/**
 *
 */
public class Animal {

    private static int nextId = 1;

    private int id;
    private String raza;
    private String ubicacion;
    private float precioCompra;
    private float precioVenta;


    public Animal(String inRaza, String inUbicacion, float inPrecioCompra, float inPrecioVenta) {
        this.id = nextId++;
        this.raza = inRaza;
        this.ubicacion = inUbicacion;
        this.precioCompra = inPrecioCompra;
        this.precioVenta = inPrecioVenta;
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * @param inId the id to set
     */
    public void setId(int inId) {
        this.id = inId;
    }


    /**
     * @return the raza
     */
    public String getRaza() {
        return raza;
    }


    /**
     * @param inRaza the raza to set
     */
    public void setRaza(String inRaza) {
        this.raza = inRaza;
    }


    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }


    /**
     * @param inUbicacion the ubicacion to set
     */
    public void setUbicacion(String inUbicacion) {
        this.ubicacion = inUbicacion;
    }


    /**
     * @return the precioCompra
     */
    public float getPrecioCompra() {
        return precioCompra;
    }


    /**
     * @param inPrecioCompra the precioCompra to set
     */
    public void setPrecioCompra(float inPrecioCompra) {
        if (inPrecioCompra >= 0) {
            this.precioCompra = inPrecioCompra;
        }
    }


    /**
     * @return the precioVenta
     */
    public float getPrecioVenta() {
        return precioVenta;
    }


    /**
     * @param inPrecioVenta the precioVenta to set
     */
    public void setPrecioVenta(float inPrecioVenta) {
        if (inPrecioVenta > 0) {
            this.precioVenta = inPrecioVenta;
        }
    }

}
