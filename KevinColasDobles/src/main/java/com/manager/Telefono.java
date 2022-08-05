package com.manager;


/**
 *
 * @author manager
 */
public class Telefono {

    private final String nombre;
    private final String numero;


    public Telefono(String inNombre, String inNumero) {
        this.nombre = inNombre;
        this.numero = inNumero;
    }


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }


    @Override
    public String toString() {
        return nombre + " : " + numero;
    }

}
