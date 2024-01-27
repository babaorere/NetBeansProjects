package com.manager.regnumeros;

public class TNodoCola {

    private String strNombreProf;
    private int ficha;
    private TPila pila;

    private TNodoCola atras;

    public TNodoCola(String strNomProf, int ficha) {
        this.strNombreProf = strNomProf;
        this.ficha = ficha;

        this.atras = null;

        pila = new TPila();
    }

    public TNodoCola getAtras() {
        return atras;
    }

    public void setAtras(TNodoCola atras) {
        this.atras = atras;
    }

    /**
     * @return the strNombreProf
     */
    public String getNombreProf() {
        return strNombreProf;
    }

    /**
     * @param strNombreProf the strNombreProf to set
     */
    public void setStrNombreProf(String strNombreProf) {
        this.strNombreProf = strNombreProf;
    }

    /**
     * @return the ficha
     */
    public int getFicha() {
        return ficha;
    }

    /**
     * @param ficha the ficha to set
     */
    public void setFicha(int ficha) {
        this.ficha = ficha;
    }

    void addDecBin(TNodoPila tNodoPila) {
        pila.push(tNodoPila);
    }

    int getPilaTam() {
        return pila.getTam();
    }

    public TNodoPila getPop() {
        return pila.pop();
    }

}
