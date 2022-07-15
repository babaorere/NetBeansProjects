package com.manager.apptdalistaligada;

import java.util.ArrayList;

/**
 * Se trata de una lista enlazada, que utiliza un vector Hash, para realizar las operaciones
 *
 * @author manager
 */
public class THashList implements IHashList {

    private class THashNodo {

        TItem dato;
        THashNodo ant;
        THashNodo sig;

        public THashNodo(THashNodo _ant, TItem _dato, THashNodo _sig) {
            this.ant = _ant;
            this.dato = _dato;
            this.sig = _sig;
        }

    }

    private THashNodo inicio;
    private THashNodo fin;
    private int tam;

    public THashList() {
        inicio = null;
        fin = null;
        tam = 0;
    }

    @Override
    public boolean vacia() {
        return (inicio == null);
    }

    @Override
    public int tamano() {
        return tam;
    }

    @Override
    public String recorrerSig() {
        StringBuilder sb = new StringBuilder();

        final String strSeparador = " -> ";
        THashNodo aux = inicio;
        while (aux != null) {
            sb.append(aux.toString()).append(strSeparador);
            aux = aux.sig;
        }

        sb.delete(sb.lastIndexOf(strSeparador), sb.length());

        return sb.toString();
    }

    @Override
    public String recorrerAnt() {
        StringBuilder sb = new StringBuilder();

        final String strSeparador = " -> ";
        THashNodo aux = fin;
        while (aux != null) {
            sb.append(aux.toString()).append(strSeparador);
            aux = aux.ant;
        }

        sb.delete(sb.lastIndexOf(strSeparador), sb.length());

        return sb.toString();
    }

    @Override
    public void insertarInicio(TItem elemento) {
        inicio = new THashNodo(null, elemento, inicio);

        if (tam <= 0) {
            fin = inicio;
        } else {
            inicio.sig.ant = inicio;
        }

        tam++;
    }

    @Override
    public void insertarFinal(TItem elemento) {
        if (vacia()) {
            insertarInicio(elemento);
            return;
        }

        fin.sig = new THashNodo(fin, elemento, null);
        fin = fin.sig;

        tam++;
    }

    @Override
    public TItem eliminarInicio() {
        if (vacia()) {
            return null;
        }

        TItem r = inicio.dato;

        inicio = inicio.sig;
        if (inicio == null) {
            fin = null;
        } else {
            inicio.ant = null;
        }

        tam--;

        return r;
    }

    @Override
    public TItem eliminarFinal() {
        if (vacia()) {
            return null;
        }

        TItem r = fin.dato;

        fin = fin.ant;
        if (fin == null) {
            inicio = null;
        } else {
            fin.sig = null;
        }

        tam--;

        return r;
    }

    @Override
    public boolean eliminar(TItem elemento) {
        THashNodo aux = buscarNodo(elemento);

        if (aux == null) {
            return false;
        }

        if (aux.ant == null) {
            eliminarInicio();
            return true;
        }

        if (aux.sig == null) {
            eliminarFinal();
            return true;
        }

        aux.sig.ant = aux.ant;
        aux.ant.sig = aux.sig;
        tam--;

        return true;
    }

    @Override
    public boolean buscar(TItem elemento) {
        if (vacia()) {
            return false;
        }

        THashNodo aux = inicio;
        while (aux != null) {
            if (aux.dato.equals(elemento)) {
                return true;
            }

            aux = aux.sig;
        }

        return false;
    }

    /**
     * Retorna el Nodo buscado y su anterior, null si NO lo encuentra
     *
     * @param elemento
     * @return
     */
    private THashNodo buscarNodo(TItem elemento) {
        if (vacia()) {
            return null;
        }

        THashNodo aux = inicio;
        while (aux != null) {
            if (aux.dato.equals(elemento)) {
                return aux;
            }
            aux = aux.sig;
        }

        return null;
    }

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @return the datos
     */
    @Override
    public ArrayList<String> getStrDatosSig() {

        if (vacia()) {
            return null;
        }

        ArrayList<String> aux = new ArrayList<>(tam);

        THashNodo p = inicio;
        for (int i = 0; (i < tam) && (p != null); i++, p = p.sig) {
            aux.add(p.dato.toString());
        }

        return aux;
    }

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @return the datos
     */
    @Override
    public ArrayList<String> getStrDatosAnt() {

        if (vacia()) {
            return null;
        }

        ArrayList<String> aux = new ArrayList<>(tam);

        THashNodo p = fin;
        for (int i = 0; (i < tam) && (p != null); i++, p = p.ant) {
            aux.add(p.dato.toString());
        }

        return aux;
    }

}
