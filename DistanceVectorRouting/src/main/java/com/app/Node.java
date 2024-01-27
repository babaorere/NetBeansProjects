package com.app;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Amacenar la informacion basica del nodo, como su identificador, y la respectiva "DV Table"
 *
 */
public class Node {

    private final int id;

    // Tiene el modificado "volatile" para informa que las operaciones sobre esta variable deben ser atomicas, 
    // es decir sin interrupcion
    private volatile ConcurrentHashMap<Integer, Integer> dv;

    // Default constructor
    public Node(int inId, int numNodes, ConcurrentHashMap<Integer, Integer> inLinkMap) {
        this.id = inId;
        dv = inLinkMap;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the dv
     */
    public ConcurrentHashMap<Integer, Integer> getDv() {
        return dv;
    }

    /**
     * Parte de la magia, actualizamos la "DV Table", hay que mencionar que esta actualizacion debe
     * estar dentro de un "block", ya que la "DV Table" es un recurso compartido
     *
     * @param inDV
     */
    void setDVTable(ConcurrentHashMap<Integer, Integer> inDV) {
        dv = inDV;
    }

}
