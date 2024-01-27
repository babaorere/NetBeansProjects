package com.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Basado en el codigo https://gist.github.com/zwilias/3935059
 *
 * Se trata de una lista simplemente enlazada, lo mas generica posible
 *
 * @author manager
 * @param <E>
 */
public class LinkedList<E extends Comparable<E>> {

    // Este objeto se utiliza para realizar una copia profunda Deep Copy, 
    // esta funcionalidad, podria sustituirse con un copy Constructor
    private static final Gson CLONER = new GsonBuilder().setPrettyPrinting().create();

    // El Nodo del que esta formada la Lista
    private class Node implements Comparable<Node> {

        private E data;
        private Node next;

        // Constructor parametros
        public Node(E inData, Node inNext) {

            // Realiza una copia profunda del Item de entrada, esto para desvincular el elemento de entrada, 
            // de la copia realiza internamente
            // Deep Copy, se puede sustituir por un "clone"
            // data= (E)inData.clone();            
            // data= inData;
            data = (E) CLONER.fromJson(CLONER.toJson(inData), inData.getClass());

            next = inNext;
        }

        // Constructor
        public Node(E data) {
            this(data, null);
        }

        // Copy contructor
        public Node(Node inOther) {
            this(inOther.data, inOther.getNext());
        }

        /**
         * Devuelve una copia de la data, esto mantiene encapsulado la data del nodo
         */
        public E getCopyData() {
            return (E) CLONER.fromJson(CLONER.toJson(data), data.getClass());
        }

        /**
         * Retorna el siguiente nodo
         *
         * @return
         */
        public Node getNext() {
            return next;
        }

        /**
         * Actualiza la data del nodo
         *
         * @param inData
         */
        public void setDataToCopy(E inData) {
            // Realiza una copia profunda del Item de entrada, esto para desvincular el elemento de entrada, 
            // de la copia realiza internamente
            // Deep Copy, se puede sustituir por un Copy Constructor
            // data= new E(indata);
            data = (E) CLONER.fromJson(CLONER.toJson(inData), inData.getClass());
        }

        /**
         * Actualiza el nodo siguiente
         *
         * @param inNext
         */
        public void setNext(Node inNext) {
            next = inNext;
        }

        @Override
        public int compareTo(Node otherNode) {
            return data.compareTo(otherNode.data);
        }

    } // End class Node

    // *************************************************************************************************************************
    // *************************************************************************************************************************
    private Node root;
    private Node tail;

    private int count;

    /**
     * COntructor, inicializa el enlaze inicial
     */
    public LinkedList() {
        root = null;
    }

    /**
     * Retorna la cantidad de Nodos en la lista
     *
     * @return
     */
    public int length() {
        return size();
    }

    /**
     * Retorna la cantidad de Nodos en la lista
     *
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * Agrega al final de la lista
     *
     * @param inData
     */
    public void add(E inData) {
        if (inData == null) {
            return;
        }

        addLast(inData);
    }

    /**
     * Agrega al principio de la lista
     *
     * @param inData
     */
    public void addFirst(E inData) {
        if (inData == null) {
            return;
        }

        root = new Node(inData, root);
        count++;

        if (count == 1) {
            tail = root;
        }
    }

    /**
     * Agrega al final de la lista
     *
     * @param inData
     */
    public void addLast(E inData) {
        if (inData == null) {
            return;
        }

        Node newTail = new Node(inData);
        tail.setNext(newTail);
        tail = newTail;
    }

    /**
     * Inserta un nuevo nodo en la posicion idx, el primer nodo tiene idx= 0, si el indice es igual o mayor a la cantidad de elementos, se procede añadir al final
     *
     * @param inData
     * @param idx
     * @return
     */
    public boolean insert(int idx, E inData) {

        if ((idx < 0) || (inData == null) || (root == null)) {
            return false;
        }

        if (idx == 0) {
            addFirst(inData);
            return true;
        }

        if (idx >= count) {
            addLast(inData);
            return true;
        }

        Node current = root;
        int jump = 1; // ya se pregunto por el idx= 0, al hacer el "if (idx == 0)"

        while (jump < idx) {
            current = current.next;
            jump++;
        }

        Node node = new Node(inData, current.next);

        current.next = node;
        count++;

        return true;
    }

    /**
     * Retorna una copia de la data, en la posicion idx
     *
     * @param idx
     * @return
     */
    public E getCopyData(int idx) {

        if ((idx < 0) || (idx >= count) || (root == null)) {
            return null;
        }

        Node current = root;
        for (int i = 0; i < idx; i++) {
            current = current.next;
        }

        return current.getCopyData();
    }

    /**
     * Retorna la data del primer nodo de la lista
     *
     * @return
     */
    public E getFirstCopyData() {
        if (root == null) {
            return null;
        }

        return root.getCopyData();
    }

    /**
     * Retorna la data del ultimo nodo de la lista
     *
     * @return
     */
    public E getLastCopyData() {
        if (root == null) {
            return null;
        }

        return tail.getCopyData();
    }

    /**
     * Presenta la lista como un String
     *
     * @return
     */
    public String print() {
        StringBuilder sb = new StringBuilder();

        sb.append("list= ");
        Node d = root;
        while (d != null) {
            sb.append(d.data.toString()).append(" ");
            d = d.getNext();
        }

        return sb.toString();
    }

    /**
     *
     * @param idx
     * @param inData
     * @return
     */
    public boolean setToCopy(int idx, E inData) {

        // Validar el indice
        if ((idx < 0) || (idx >= count) || (inData == null) || (root == null)) {
            return false;
        }

        Node current = root;

        for (int i = 0; i < idx; i++) {
            current = current.next;
        }

        current.setDataToCopy(inData);

        return true;
    }

    /**
     * Retorna el indice de la primera ocurrencia de data, dentro de la lista, -1 no no fue encontrada
     *
     * @param inData
     * @return
     */
    public int indexOf(E inData) {

        if ((inData == null) || (root == null)) {
            return -1;
        }

        int index = 0;
        Node current = root;

        while (current != null) {
            if (current.data.equals(inData)) {
                return index;
            }

            index++;
            current = current.next;
        }

        return -1;
    }

    /**
     * Indica si la data se encuentra dentro de la lista
     *
     * @param inData
     * @return
     */
    public boolean contains(E inData) {
        if ((inData == null) || (root == null)) {
            return false;
        }

        return indexOf(inData) > 0;
    }

}
