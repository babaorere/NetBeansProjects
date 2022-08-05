package doblelink;

import java.util.Random;

/**
 *
 * @author manager
 */
public class DLista implements ILista {

    private class Nodo {

        Fraccion dato;
        Nodo ant;
        Nodo sig;

        public Nodo(Fraccion dato, Nodo sig) {
            this.ant = null;
            this.dato = dato;
            this.sig = sig;
        }

        public Nodo(Nodo ant, Fraccion dato) {
            this.ant = ant;
            this.dato = dato;
            this.sig = null;
        }

    }

    private Nodo inicio;
    private Nodo fin;
    private int tam;

    public DLista() {
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
        Nodo aux = inicio;
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
        Nodo aux = fin;
        while (aux != null) {
            sb.append(aux.toString()).append(strSeparador);
            aux = aux.ant;
        }

        sb.delete(sb.lastIndexOf(strSeparador), sb.length());

        return sb.toString();
    }

    @Override
    public void insertarInicio(Fraccion elemento) {
        inicio = new Nodo(elemento, inicio);

        if (tam <= 0) {
            fin = inicio;
        } else {
            inicio.sig.ant = inicio;
        }

        tam++;
    }

    @Override
    public void insertarFinal(Fraccion elemento) {
        if (vacia()) {
            insertarInicio(elemento);
            return;
        }

        fin.sig = new Nodo(fin, elemento);
        fin = fin.sig;

        tam++;
    }

    @Override
    public Fraccion eliminarInicio() {
        if (vacia()) {
            return null;
        }

        Fraccion r = inicio.dato;

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
    public Fraccion eliminarFinal() {
        if (vacia()) {
            return null;
        }

        Fraccion r = fin.dato;

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
    public boolean eliminar(Fraccion elemento) {
        Nodo aux = buscarNodo(elemento);

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
    public boolean buscar(Fraccion elemento) {
        if (vacia()) {
            return false;
        }

        Nodo aux = inicio;
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
    private Nodo buscarNodo(Fraccion elemento) {
        if (vacia()) {
            return null;
        }

        Nodo aux = inicio;
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
    public String[] getStrDatosSig() {

        if (vacia()) {
            return null;
        }

        String[] aux = new String[tam];

        Nodo p = inicio;
        for (int i = 0; (i < tam) && (p != null); i++, p = p.sig) {
            aux[i] = p.dato.toString();
        }

        return aux;
    }

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @return the datos
     */
    public String[] getStrDatosAnt() {

        if (vacia()) {
            return null;
        }

        String[] aux = new String[tam];

        Nodo p = fin;
        for (int i = 0; (i < tam) && (p != null); i++, p = p.ant) {
            aux[i] = p.dato.toString();
        }

        return aux;
    }

    /**
     * Llena el Array de valores aleatorios
     *
     * @param intam
     */
    public void fillRandom(int intam) {

        int cant = intam % 100;

        if (cant <= 0) {
            return;
        }

        Random r = new Random();

        for (int i = 0; i < cant; i++) {
            insertarFinal(new Fraccion(r.nextInt(100), r.nextInt(100)));
        }

    }

}
