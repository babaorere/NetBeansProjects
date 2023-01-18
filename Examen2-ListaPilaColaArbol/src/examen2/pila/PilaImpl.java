package examen2.pila;

import examen2.automovil.Automovil;
import java.util.Objects;

/**
 *
 * @author Felipe Belloy
 */
public class PilaImpl implements Pila {

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
    private int size; // tamaño de la lista, cantidad de items

    public PilaImpl() {
        head = null;
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
    public void push(Automovil item) {
        head = new Nodo(item, head);

        size++;
    }

    @Override
    public Automovil pop() {
        if (isEmpty()) {
            return null;
        }

        Automovil item = head.dato;

        head = head.sig;

        size--;

        return item;
    }

}
