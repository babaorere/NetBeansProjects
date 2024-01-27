package com.manager.apptdalistaligada;

import java.util.ArrayList;

/**
 *
 * @author manager
 */
public interface IHashList {

    /*vacía: Regresa true si la lista está vacía
    su no, regresa false*/
    public boolean vacia();

    /* Regresa el tamaño de la lista*/
    public int tamano();

    /*regresa un string con todos los elementos 
    de la lista, separados por comas, recorriendo hacia adelante  */
    public String recorrerSig();

    /*regresa un string con todos los elementos 
    de la lista, separados por comas, recorriendo hacia atras  */
    public String recorrerAnt();

    /*Inserta el elemento al inicio  de la lista*/
    public void insertarInicio(TItem elemento);

    /*Inserta el elemento al final de la lista*/
    public void insertarFinal(TItem elemento);

    /*Elimina el elemento que se encuentra al inicio de
    la lista y lo regresa, si está vacía regresa null*/
    public TItem eliminarInicio();

    /*Elimina el elemento que se encuentra al final de
    la lista y lo regresa, si está vacía regresa null*/
    public TItem eliminarFinal();

    /*Busca el ememento y si lo encuentra lo elimina y 
    regresa true, si no lo encuentra sólo regresa false*/
    public boolean eliminar(TItem elemento);

    /*Busca el ememento y si lo encuentra regresa true, si no false*/
    public boolean buscar(TItem elemento);

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @return the datos
     */
    public ArrayList<String> getStrDatosSig();

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Ant.
     *
     * @return the datos
     */
    public ArrayList<String> getStrDatosAnt();

}
