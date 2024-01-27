package com.manager;

/**
 *
 * @author manager
 * @param <T>
 */
public class Nodo<T> {

    private final T dato;
    private Nodo ant;
    private Nodo sig;

    public Nodo(Nodo inAnt, T inDato, Nodo inSig) {
        this.ant = inAnt;
        this.dato = inDato;
        this.sig = inSig;
    }

    /**
     * @return the dato
     */
    public T getDato() {
        return dato;
    }

    /**
     * @return the ant
     */
    public Nodo getAnt() {
        return ant;
    }

    /**
     * @param ant the ant to set
     */
    public void setAnt(Nodo ant) {
        this.ant = ant;
    }

    /**
     * @return the sig
     */
    public Nodo getSig() {
        return sig;
    }

    /**
     * @param sig the sig to set
     */
    public void setSig(Nodo sig) {
        this.sig = sig;
    }

}
