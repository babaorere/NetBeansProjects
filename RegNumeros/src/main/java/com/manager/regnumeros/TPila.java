package com.manager.regnumeros;

public class TPila {

    private int tam;
    private TNodoPila top;

    public TPila() {
        tam = 0;
    }

    public void push(TNodoPila ElementoNuevo) {
        ElementoNuevo.setAbajo(top);
        top = ElementoNuevo;
        tam++;
    }

    public TNodoPila getTop() {
        return top;
    }

    public TNodoPila pop() {
        TNodoPila aux = top;
        if (PilaVacia()) {
            System.out.println("No hay elementos en la pila");
        } else {
            top = top.getAbajo();
            aux.setAbajo(null);
        }

        tam--;
        return aux;
    }

    public boolean PilaVacia() {
        return (top == null);
    }

    /**
     * @return the tam
     */
    public int getTam() {
        return tam;
    }
        
    
}
