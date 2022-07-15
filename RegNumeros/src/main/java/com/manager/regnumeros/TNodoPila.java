package com.manager.regnumeros;

/**
 * Clase inmutable, no se pueden cambiar los atributos, una vez creados, por eso solo tiene getters
 *
 * @author manager
 */
public class TNodoPila {

    private final long numero;
    private final String strBinario;
    private final String nomProf;

    private TNodoPila abajo;

    /**
     *
     * @param numero
     * @param strBinario
     * @param nomProf
     */
    public TNodoPila(long numero, String strBinario, String nomProf) {
        this.numero = numero;
        this.strBinario = strBinario;
        this.nomProf = nomProf;

        this.abajo = null;
    }

    public TNodoPila getAbajo() {
        return abajo;
    }

    public void setAbajo(TNodoPila abajo) {
        this.abajo = abajo;
    }

    /**
     * @return the numero
     */
    public long getNumero() {
        return numero;
    }

    /**
     * @return the strBinario
     */
    public String getStrBinario() {
        return strBinario;
    }

    /**
     * @return the nomProf
     */
    public String getNomProf() {
        return nomProf;
    }

}
