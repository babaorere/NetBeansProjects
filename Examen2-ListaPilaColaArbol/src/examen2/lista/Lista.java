package examen2.lista;

import examen2.arbol.ArbolBinarioImpl;
import examen2.automovil.Automovil;
import examen2.automovil.Catalitico;
import examen2.cola.Cola;
import examen2.pila.Pila;

/**
 * Interface para manejar una lista generica
 *
 * @author Felipe Belloy
 */
public interface Lista {

    /**
     * Retorna true si la lista esta vacia
     */
    public boolean isEmpty();

    /**
     * Retorna el tamaño de la lista
     */
    public int getSize();

    /**
     * Agrega un automovil, al inicio de la lista
     */
    public void addIni(Automovil item);

    /**
     * Agrega un automavil, al final de la lista
     */
    public void addEnd(Automovil item);

    /**
     * Elimina el Nodo inicio, retorna el Automovil, o null si la lista esta vacia
     */
    public Automovil delIni();

    /**
     * Elimina el Nodo al final, retorna el Automovil, o null si la lista esta vacia
     */
    public Automovil delEnd();

    /**
     * Elimina el Nodo, donde este ubicado el Automovil indicado, retorna el Automovil, o null si no se encuantra, o la lista esta vacia
     */
    public Automovil delItem(Automovil item);

    /**
     * Elimina el Nodo, en la posicion idx (desde 0), retorna el Automovil, o null si no se encuantra, o la lista esta vacia
     */
    public Automovil delItem(int idx);

    /**
     * Buscar el Automovil, donde este ubicado el Automovil indicado, retorna el Automovil, o null si la lista esta vacia
     */
    public Automovil get(int idx);

    public String[] toArrStr();

    public Lista getListaAnt2010();

    public void ordInsercionById();

    public void ordInsercionByAnio();

    public void ordInsercionByCatalitico();

    /**
     * retorna una cola con los automoviles de una marca determinada
     */
    public Cola getColaMarca(String marca);

    /**
     * retorna una pila con los automoviles de una marca determinada
     */
    public Pila getPilaMarca(String marca);

    /**
     * retorna un arbol binario ordenado por año
     */
    public ArbolBinarioImpl getArbol();

    public Catalitico busquedaBinaria(int value);

}
