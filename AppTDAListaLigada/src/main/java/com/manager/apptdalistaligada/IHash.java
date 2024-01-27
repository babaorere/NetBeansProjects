package com.manager.apptdalistaligada;

import java.util.ArrayList;

/**
 *
 * @author manager
 */
public interface IHash {

    /*vacía: Regresa true si la lista está vacía
    su no, regresa false*/
    public boolean vacia();

    /*vacía: Regresa true si la lista está vacía
    su no, regresa false*/
    public boolean vacia(int idx);

    /* Regresa el tamaño de la lista*/
    public int tamano();

    /* Regresa el tamaño de la lista*/
    public int tamano(int idx);

    /* Regresa un string con todos los elementos 
    del hash, separados por comas, recorriendo hacia adelante  */
    public String recorrerSig();

    /* Regresa un string con todos los elementos 
    de la lista en el indice idx, separados por comas, recorriendo hacia adelante  */
    public String recorrerSig(int idx);

    /*regresa un string con todos los elementos 
    de la lista, separados por comas, recorriendo hacia atras  */
    public String recorrerAnt();

    /*regresa un string con todos los elementos 
    del hash, separados por comas, recorriendo hacia atras  */
    public String recorrerAnt(int idx);

    /*Inserta el elemento al inicio  de la lista*/
    public void insertarInicio(TItem elemento);

    /*Inserta el elemento al final de la lista*/
    public void insertarFinal(TItem elemento);

    /*Elimina el elemento que se encuentra al inicio de
    la lista y lo regresa, si está vacía regresa null*/
    public TItem eliminarInicio(int idx);

    /*Elimina el elemento que se encuentra al final de
    la lista y lo regresa, si está vacía regresa null*/
    public TItem eliminarFinal(int idx);

    /*Busca el ememento y si lo encuentra lo elimina y 
    regresa true, si no lo encuentra sólo regresa false*/
    public boolean eliminar(TItem elemento);

    /*Busca el ememento y si lo encuentra regresa true, si no false*/
    public boolean buscar(TItem elemento);

    public void fillRandom(int intam);

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @param idx
     * @return the datos
     */
    public ArrayList<String> getStrDatosSig(int idx);

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @param idx
     * @return the datos
     */
    public ArrayList<String> getStrDatosAnt(int idx);
}
