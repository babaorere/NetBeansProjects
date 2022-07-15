package com.manager.apptdalistaligada;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;

/**
 * Maneja un vector HASH, con el ultimo digito del TItem.codigo, no se permiten Items repetidos
 *
 * @author manager
 */
public class TDA_Hash_List_DLink implements IHash {

    private final THashList[] list;

    public TDA_Hash_List_DLink() {
        list = new THashList[10];

        for (int i = 0; i < 10; i++) {
            list[i] = new THashList();
        }

    }

    @Override
    public boolean vacia() {
        for (int i = 0; i < 10; i++) {
            if ((list[i] != null) && !list[i].vacia()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean vacia(int idx) {
        return list[idx].vacia();
    }

    @Override
    public int tamano() {
        int suma = 0;

        for (int i = 0; i < 10; i++) {
            if (list[i] != null) {
                suma += list[i].tamano();
            }
        }

        return suma;
    }

    @Override
    public int tamano(int idx) {
        return list[idx].tamano();
    }

    @Override
    public String recorrerSig() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (list[i] != null) {
                sb.append(list[i].recorrerSig());
            }
        }

        return sb.toString();
    }

    @Override
    public String recorrerSig(int idx) {
        return list[idx].recorrerSig();
    }

    @Override
    public String recorrerAnt() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (list[i] != null) {
                sb.append(list[i].recorrerAnt());
            }
        }

        return sb.toString();
    }

    @Override
    public String recorrerAnt(int idx) {
        return list[idx].recorrerAnt();
    }

    /**
     * Inserta segun el hash del Item
     *
     * @param elemento
     */
    @Override
    public void insertarInicio(TItem elemento) {
        int idx = abs(elemento.hashCode() % 10);
        list[idx].insertarInicio(elemento);
    }

    /**
     * Inserta segun el hash del Item
     *
     * @param elemento
     */
    @Override
    public void insertarFinal(TItem elemento) {
        int idx = abs(elemento.hashCode()) % 10;
        list[idx].insertarFinal(elemento);
    }

    /**
     * Elimina el inicio de la lista idx
     *
     * @param idx
     * @return
     */
    @Override
    public TItem eliminarInicio(int idx) {
        return list[idx].eliminarInicio();
    }

    @Override
    public TItem eliminarFinal(int idx) {
        return list[idx].eliminarFinal();
    }

    @Override
    public boolean eliminar(TItem elemento) {
        int idx = abs(elemento.hashCode() % 10);
        return list[idx].eliminar(elemento);
    }

    @Override
    public boolean buscar(TItem elemento) {
        int idx = abs(elemento.hashCode()) % 10;
        return list[idx].buscar(elemento);
    }

    /**
     * Llenar la Lista de valores aleatorios
     *
     * @param intam
     */
    @Override
    public void fillRandom(int intam) {

        String[] users = {"Burgundófora", "Ladislao", "Hierónides", "Pantaleona", "Digna", "Marciana", "Dombina", "Piedrasantas",
            "Oristila", "Tesifonte", "Luzdivino", "Segismundo", "Gumersindo", "Cojoncio", "Bonifacio", "Arsenio", "Altagracia",
            "Amadora", "Arnulfo", "Apolinario", "Cipriniano", "Diosnelio", "Escolástico", "Estanislada", "Expiración", "Froilana",
            "Fulgencio", "Hermógenes", "Iluminado", "Protasio"};

        int cant = intam % 100;

        if (cant <= 0) {
            return;
        }

        Random r = new Random();

        for (int i = 0; i < cant;) {
            final String passw = "" + r.nextInt(10) + "" + r.nextInt(10) + "" + r.nextInt(10) + "" + r.nextInt(10);

            // No se permiten Items repetidos
            TItem tmp = new TItem(users[r.nextInt(users.length)].toUpperCase(), passw);
            if (!buscar(tmp)) {
                insertarFinal(tmp);
                i++;
            }
        }

    }

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @return the datos
     */
    @Override
    public ArrayList<String> getStrDatosSig(int idx) {
        return list[idx].getStrDatosSig();
    }

    /**
     * Retorna un array de String, correspondiente a la representacion de los datos. Recorrido Sig.
     *
     * @return the datos
     */
    @Override
    public ArrayList<String> getStrDatosAnt(int idx) {
        return list[idx].getStrDatosSig();
    }
}
