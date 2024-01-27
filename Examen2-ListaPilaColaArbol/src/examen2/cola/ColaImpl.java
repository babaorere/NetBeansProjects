package examen2.cola;

import examen2.automovil.Automovil;
import java.util.Objects;

/**
 *
 * @author Felipe Belloy
 */
public class ColaImpl implements Cola {

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

    public ColaImpl() {
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

    private void addIni(Automovil item) {
        head = new Nodo(item, head);

        if (size <= 0) {
            tail = head;
        }

        size++;
    }

    @Override
    public void push(Automovil item) {
        if (isEmpty()) {
            addIni(item);
            return;
        }

        tail.sig = new Nodo(item, null);
        tail = tail.sig;

        size++;
    }

    @Override
    public Automovil pull() {
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

}
