package com.manager.pilacolalista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author manager
 */
public class Respaldo {

    /**
     * Esta clase contendra la informacion a guardar en las estructura de datos
     *
     */
    private static class Nodo {

        private String RUT;

        public Nodo(String RUT) {
            this.RUT = RUT;
        }

        /**
         * @return the RUT
         */
        public String getRUT() {
            return RUT;
        }

        /**
         * @param RUT the RUT to set
         */
        public void setRUT(String RUT) {
            this.RUT = RUT;
        }

        @Override
        public String toString() {
            return RUT;
        }

    }

    /**
     * Este es el punto de entrada al programa
     *
     * @param args
     */
    public static void main(String[] args) {

        /**
         * Para los efectos se crearan 5 variables tipo Nodo con informacion sobre el RUT
         *
         * RUT generdos aleatoriamente http://alagos.github.io/rut_generator/
         */
        Nodo[] vecNodoPila = {new Nodo("3004457-6"), new Nodo("3004457-6"), new Nodo("5608772-9"), new Nodo("7419731-0"),
            new Nodo("8598922-7"), new Nodo("9421254-5"), null};

        /**
         * Este nodo se utilizara para leer informacion del usuario
         */
        Nodo rutIn;

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        // Crear una Pila
        Stack<Nodo> pila = new Stack<>();

        System.out.println("\n\n********** Datos de la Pila VACIA *************");
        System.out.println("Estado de la Pila: " + pila.isEmpty());
        System.out.println("EL Tamaño de la Pila es: " + pila.size());

        // Solicitar al usuario un RUT
        System.out.println("\nPILA. Ingrese un RUT valido 99999999-9: ");

        // Leer el RUT desde la consola/teclado        
        //Acá se captura el valor del usuario, se ajusta la longitud a 9 posiciones, y se pasa a mayuscula
        String strRut;
        try {
            strRut = bf.readLine();
            strRut = strRut.substring(0, (int) min(9, strRut.length())).trim().toUpperCase();
        } catch (IOException ex) {
            strRut = "10,684,937-4";
        }

        // Crear un nuevo nodo con el RUT, y guardarlo en un vector        
        vecNodoPila[vecNodoPila.length - 1] = new Nodo(strRut);
        Random rand = new Random();
        int cont = 0;
        while (cont < vecNodoPila.length) {
            int idx = abs(rand.nextInt() % vecNodoPila.length);
            if (vecNodoPila[idx] != null) {
                cont++;
                pila.push(vecNodoPila[idx]);
                vecNodoPila[idx] = null;
            }
        }

        System.out.println("\n\n********** Datos de la Pila con ELEMENTOS *************");
        System.out.println("Estado de la Pila: " + pila.isEmpty());
        System.out.println("El Tamaño de la Pila Actual es: " + pila.size());
        System.out.println("El Top de la Pila es: " + pila.peek());
        System.out.println("la pila es= " + pila + "\n");

        // Recorrer la Pila con un For
        int pos = pila.size();
        for (Nodo nodo : pila) {
            System.out.println("Posicion= " + pos + ", RUT= " + nodo.getRUT());
            pos--;
        }

        // Buscar el elemento utilizando una pila auxiliar
        // Crear una Pila
        Stack<Nodo> auxPila = new Stack<>();

        boolean encontrado = false;
        cont = 0;
        while (pila.peek() != null) {
            cont++;
            if (pila.peek().getRUT().equals(strRut)) {
                encontrado = true;
                break;
            }

            // Guardar el elemento para restaurarlo
            auxPila.push(pila.pop());
        }

        if (encontrado) {
            System.out.println("\nValor de RUT introducido: " + strRut + " esta en la posicion " + cont + " de la Pila, (1 es el tope)");
        }

        // restaurar la pila original
        while (auxPila.size() > 0) {
            pila.push(auxPila.pop());
        }

        // Mostrar la pila
        System.out.println("Del ultimo al tope, la pila es= " + pila + "\n");

        // **************************************************
        // Eliminar el elemento
        auxPila = new Stack<>();
        encontrado = false;
        while (pila.size() > 0) {
            cont++;
            if (pila.peek().getRUT().equals(strRut)) {
                // Eliminar el elemento
                System.out.println("Eliminado el RUT: " + pila.pop().getRUT());
                encontrado = true;
                break;
            }

            // Guardar el elemento para restaurarlo
            auxPila.push(pila.pop());
        }

        if (!encontrado) {
            System.out.println("RUT no encontrado");
        }

        // restaurar la pila original
        while (auxPila.size() > 0) {
            pila.push(auxPila.pop());
        }

        // Mostrar la pila
        System.out.println("Del elemento mas profundo al tope, la pila es= " + pila + "\n");

        //Rescatar un valor de la pila antes de sacarlo e imprimirlo
        Nodo nodo = pila.pop();
        System.out.println("Valor Rescatado de la Pila: " + nodo);

        //Revisar el Nuevo Top de la Pila
        System.out.println("El Top de la Pila es: " + pila.peek());

        //Vaciado de una Pila
        System.out.println("\nVaciando la pila, mostrando los pop");
        while (!pila.empty()) {
            System.out.println(pila.pop()); //Mostrar el velor que elimina de la Pila
        }
        System.out.println("Estado de la Pila [True-Vacia / False-Contiene Elemenetos]:" + pila.isEmpty());
// *****************************************************************************************************************
        System.out.println("\n\n********************************************");
        System.out.println("************ Seccion de la Cola ************");

        Nodo[] vecNodoCola = {new Nodo("3004457-6"), new Nodo("3004457-6"), new Nodo("5608772-9"), new Nodo("7419731-0"),
            new Nodo("8598922-7"), new Nodo("9421254-5"), null};

        // Solicitar al usuario un RUT
        System.out.println("\nCOLA. Ingrese un RUT valido 99999999-9: ");

        // Leer el RUT desde la consola/teclado        
        //Acá se captura el valor del usuario, se ajusta la longitud a 9 posiciones, y se pasa a mayuscula
//        String strRut;
        try {
            strRut = bf.readLine();
            strRut = strRut.substring(0, (int) min(9, strRut.length())).trim().toUpperCase();
        } catch (IOException ex) {
            strRut = "10,684,937-4";
        }

        // Creación de la Cola
        Queue<Nodo> cola = new LinkedList<>();

        // Preguntar por el estado de la Cola
        System.out.println("\nEstado de la Cola [T-F], esta vacia: " + cola.isEmpty());
        System.out.println("El tamaño de la Cola es: " + cola.size());

        // Llenado de la Cola, las posiciones son aleatorias
        vecNodoCola[vecNodoCola.length - 1] = new Nodo(strRut);
        rand = new Random();
        cont = 0;
        while (cont < vecNodoCola.length) {
            int idx = abs(rand.nextInt() % vecNodoCola.length); // Generar el indice aleatorio
            if (vecNodoCola[idx] != null) {
                cont++;
                cola.add(vecNodoCola[idx]);
                vecNodoCola[idx] = null;
            }
        }

        //Mostrar los datos de la Cola
        System.out.println("\nDel primero introducido al ultimo, la cola es= " + cola + "\n");

        // **************************************************
        // Buscar el elemento utilizando una cola auxiliar
        // Crear una Pila
        Queue<Nodo> auxCola = new LinkedList<>();

        encontrado = false;
        cont = 0;
        while (cola.size() > 0) {
            cont++;
            if (cola.peek().getRUT().equals(strRut)) {
                // Eliminar el elemento
                System.out.println("Encontrado el RUT: " + cola.peek().getRUT());
                encontrado = true;
            }

            // Guardar el elemento para restaurarlo
            auxCola.add(cola.remove());
        }

        if (encontrado) {
            System.out.println("COLA. Valor de RUT introducido: " + strRut + " esta en la posicion " + cont + " de la Cola, (1 es al salir)");
        } else {
            System.out.println("COLA. RUT no encontrado");
        }

        // restaurar la pila original
        while (auxCola.size() > 0) {
            cola.add(auxCola.remove());
        }

        // **************************************************
        // Eliminar el elemento
        // Buscar el elemento utilizando una cola auxiliar
        // Crear una Pila
        auxCola = new LinkedList<>();

        encontrado = false;
        while (cola.size() > 0) {
            cont++;
            if (cola.peek().getRUT().equals(strRut)) {
                // Eliminar el elemento
                System.out.println("Eliminado el RUT: " + cola.remove());
                encontrado = true;
            }

            // Guardar el elemento para restaurarlo
            auxCola.add(cola.remove());
        }

        if (!encontrado) {
            System.out.println("COLA. RUT no encontrado");
        }

        // restaurar la pila original
        while (auxCola.size() > 0) {
            cola.add(auxCola.remove());
        }

        System.out.println("\nValores de la cola despues de eliminar el RUT: " + cola);

        System.out.println("\n************* Datos de la Cola ********************\n");
        System.out.println("Estado de la Cola [F-T], esta vacia: " + cola.isEmpty());
        System.out.println("El Tamaño de la Cola: " + cola.size());
        System.out.println("Cúal es el TOP de la Cola: " + cola.peek());

        cola.remove(cola.peek());
        System.out.println("Valores de la cola (cola.remove(cola.peek))" + cola);

        cola.remove();
        System.out.println("Valores de la cola (cola.remove()): " + cola);

        // Recorrer la Cola con un For
        System.out.println("");
        pos = 0;
        for (Nodo nodoCola : cola) {
            pos++;
            System.out.println("Posicion= " + pos + ", RUT= " + nodoCola.getRUT());
        }
    }

}
