package com.manager.regnumeros;

public class TCola {

    private TNodoCola frente;
    private TNodoCola ultimo;

    private int tam;

    public TCola() {
        tam = 0;
    }

    public TNodoCola getFrente() {
        return frente;
    }

    public void encola(TNodoCola nuevoElemento) {
        if (frente == null) {
            frente = nuevoElemento;
            ultimo = nuevoElemento;
        } else {
            ultimo.setAtras(nuevoElemento);
            ultimo = nuevoElemento;
        }

        tam++;

    }

    public TNodoCola atiende() {
        TNodoCola aux = frente;
        if (frente != null) {
            frente = frente.getAtras();
            aux.setAtras(null);
        }
        tam--;
        return aux;
    }

    public int getNextFicha() {
        return getTam() + 1;
    }

    public int getTam() {
        return tam;
    }
        
}
