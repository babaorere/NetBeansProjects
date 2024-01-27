package examen2.lista;

import examen2.arbol.ArbolBinarioImpl;
import examen2.automovil.Automovil;
import examen2.automovil.Catalitico;
import examen2.cola.Cola;
import examen2.cola.ColaImpl;
import examen2.pila.Pila;
import examen2.pila.PilaImpl;
import java.util.Objects;

/**
 *
 * @author Felipe Belloy
 */
public class ListaImpl implements Lista {

    private class Nodo {

        Automovil dato;
        Nodo sig;

        public Nodo() {
            this.dato = null;
            this.sig = null;
        }

        public Nodo(Automovil dato, Nodo sig) {
            this.dato = dato;
            this.sig = sig;
        }

        @Override
        public boolean equals(Object other) {
            Nodo nodo = (Nodo) other;
            return dato.equals(nodo.dato) && sig.equals(nodo.sig);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 41 * hash + Objects.hashCode(this.dato);
            hash = 41 * hash + Objects.hashCode(this.sig);
            return hash;
        }

        @Override
        public String toString() {
            return dato.toString();
        }

    }

    private Nodo tempHead = null;
    private Nodo head; // nodo inicio
    private Nodo tail; // nodo cola
    private int size; // tamaño de la lista, cantidad de items

    public ListaImpl() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @return the datos
     */
    @Override
    public String[] toArrStr() {

        if (isEmpty()) {
            return null;
        }

        String[] aux = new String[size];

        Nodo curr = head;
        for (int i = 0; (i < size) && (curr != null); i++, curr = curr.sig) {
            aux[i] = curr.dato.toString();
        }

        return aux;
    }

    @Override
    public boolean isEmpty() {
        return (head == null);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void addIni(Automovil item) {
        head = new Nodo(item, head);

        if (size <= 0) {
            tail = head;
        }

        size++;
    }

    @Override
    public void addEnd(Automovil item) {
        if (isEmpty()) {
            addIni(item);
            return;
        }

        tail.sig = new Nodo(item, null);
        tail = tail.sig;

        size++;
    }

    @Override
    public Automovil delIni() {
        if (isEmpty()) {
            return null;
        }

        Automovil r = head.dato;

        head = head.sig;
        if (head == null) {
            tail = null;
        }

        size--;

        return r;
    }

    @Override
    public Automovil delEnd() {
        if (isEmpty()) {
            return null;
        }

        Nodo prev = null;
        Nodo curr = head;

        while (curr != null && curr != tail) {
            prev = curr;
            curr = curr.sig;
        }

        Automovil item = tail.dato;

        if (curr == null) {
            head = null;
            tail = null;
        } else {
            tail = prev;
            if (tail == null) {
                head = null;
            } else {
                tail.sig = null;
            }
        }

        size--;

        return item;
    }

    @Override
    public Automovil delItem(Automovil item) {

        Nodo prev = null;
        Nodo curr = head;
        while (curr != null && curr.dato != item) {
            prev = curr;
            curr = curr.sig;
        }

        if (curr == null) {
            return null;
        }

        if (curr.sig == null) {
            delEnd();
            return curr.dato;
        }

        // Aqui eliminamos el nodo
        if (prev != null) {
            prev.sig = curr.sig;
        }

        size--;

        return curr.dato;
    }

    @Override
    public Automovil delItem(int idx) {

        Nodo prev = null;
        Nodo curr = head;

        for (int i = 0; (i <= idx) && (curr != null); i++) {
            prev = curr;
            curr = curr.sig;
        }

        // Aqui eliminamos el nodo
        if (prev != null) {
            prev.sig = curr.sig;
        }

        return curr.dato;
    }

    @Override
    public Automovil get(int idx) {
        Nodo prev = null;
        Nodo curr = head;

        for (int i = 0; (i <= idx) && (curr != null); i++) {
            prev = curr;
            curr = curr.sig;
        }

        // Aqui eliminamos el nodo
        if (curr == null) {
            return null;
        }

        return curr.dato;
    }

    @Override
    public Lista getListaAnt2010() {
        Lista listaAnt2010 = new ListaImpl();

        // recorrer la lista
        Nodo curr = head;
        while (curr != null) {

            if (curr.dato.getAnio() < 2010) {
                listaAnt2010.addEnd(curr.dato);
            }

            curr = curr.sig;
        }

        return listaAnt2010;
    }

    @Override
    public void ordInsercionById() {

        tempHead = null;
        Nodo curr = head;

        while (curr != null) {
            Nodo sig = curr.sig;

            insertarNodoById(curr);

            curr = sig;
        }

        head = tempHead;
    }

    private void insertarNodoById(Nodo insNodo) {
        if (tempHead == null || tempHead.dato.getId() < insNodo.dato.getId()) {
            insNodo.sig = tempHead;
            tempHead = insNodo;
        } else {
            Nodo curr = tempHead;

            while (curr.sig != null && curr.sig.dato.getId() >= insNodo.dato.getId()) {
                curr = curr.sig;
            }

            insNodo.sig = curr.sig;
            curr.sig = insNodo;
        }

    }

    @Override
    public void ordInsercionByAnio() {

        tempHead = null;
        Nodo curr = head;

        while (curr != null) {
            Nodo sig = curr.sig;

            insertarNodoByAnio(curr);

            curr = sig;
        }

        head = tempHead;
    }

    private void insertarNodoByAnio(Nodo insNodo) {
        if (tempHead == null || tempHead.dato.getAnio() < insNodo.dato.getAnio()) {
            insNodo.sig = tempHead;
            tempHead = insNodo;
        } else {
            Nodo curr = tempHead;

            while (curr.sig != null && curr.sig.dato.getAnio() >= insNodo.dato.getAnio()) {
                curr = curr.sig;
            }

            insNodo.sig = curr.sig;
            curr.sig = insNodo;
        }

    }

    @Override
    public void ordInsercionByCatalitico() {

        tempHead = null;
        Nodo curr = head;

        while (curr != null) {
            Nodo sig = curr.sig;

            insertarNodoByCatalitico(curr);

            curr = sig;
        }

        head = tempHead;
    }

    private void insertarNodoByCatalitico(Nodo insNodo) {
        if (tempHead == null || tempHead.dato.getCatalitico().ordinal() >= insNodo.dato.getCatalitico().ordinal()) {
            insNodo.sig = tempHead;
            tempHead = insNodo;
        } else {
            Nodo curr = tempHead;

            while (curr.sig != null && curr.sig.dato.getCatalitico().ordinal() < insNodo.dato.getCatalitico().ordinal()) {
                curr = curr.sig;
            }

            insNodo.sig = curr.sig;
            curr.sig = insNodo;
        }

    }

    public Cola getColaMarca(String marca) {
        Cola listaMarca = new ColaImpl();

        // recorrer la lista
        Nodo curr = head;
        while (curr != null) {

            if (curr.dato.getMarca().equals(marca)) {
                listaMarca.push(curr.dato);
            }

            curr = curr.sig;
        }

        return listaMarca;

    }

    @Override
    public Pila getPilaMarca(String marca) {
        Pila pilaMarca = new PilaImpl();

        // recorrer la lista
        Nodo curr = head;
        while (curr != null) {

            if (curr.dato.getMarca().equals(marca)) {
                pilaMarca.push(curr.dato);
            }

            curr = curr.sig;
        }

        return pilaMarca;
    }

    /**
     * retorna un arbol binario ordenado por año
     */
    public ArbolBinarioImpl getArbol() {

        ArbolBinarioImpl arbol = new ArbolBinarioImpl();

        // recorrer la lista
        Nodo curr = head;
        while (curr != null) {

            arbol.add(curr.dato);

            curr = curr.sig;
        }

        return arbol;
    }

    private Nodo middleNode(Nodo start, Nodo last) {
        if (start == null) {
            return null;
        }

        Nodo slow = start;
        Nodo fast = start.sig;

        while (fast != last) {
            fast = fast.sig;
            if (fast != last) {
                slow = slow.sig;
                fast = fast.sig;
            }
        }
        return slow;
    }

    private Nodo binarySearch(Nodo head, int value) {
        Nodo start = head;
        Nodo last = null;

        do {
            Nodo mid = middleNode(start, last);

            if (mid == null) {
                return null;
            }

            if (mid.dato.getId() == value) {
                return mid;
            } else if (mid.dato.getId() > value) {
                start = mid.sig;
            } else {
                last = mid;
            }
        } while (last == null || last != start);

        return null;
    }

    public Catalitico busquedaBinaria(int value) {

        Nodo nodo = null;

        // ordenar
        ordInsercionById();

        nodo = binarySearch(head, value);

        if (nodo == null) {
            return null;
        }

        return nodo.dato.getCatalitico();
    }

}
