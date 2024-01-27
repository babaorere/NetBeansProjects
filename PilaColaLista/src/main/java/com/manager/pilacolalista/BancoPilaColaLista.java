package com.manager.pilacolalista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author manager
 */
public class BancoPilaColaLista {

    /**
     * Este es el punto de entrada al programa
     *
     * @param args
     */
    public static void main(String[] args) {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        // Crear una Pila
        Stack<Integer> pila = new Stack<>();

        System.out.println("\n\n********** Datos de la Pila VACIA *************");
        System.out.println("Estado de la Pila: " + pila.isEmpty());
        System.out.println("El Tamaño de la Pila es: " + pila.size());

        int op = 0;

        System.out.println("\n********** Ingreso de datos **********");
        // Crear un ciclo para insertar valores en la Pila
        do {
            // Solicitar un Valor
            System.out.println("PILA. Ingrese un valor RUT: ");

            try {
                //Acá se captura y almacena el valor en la Pila
                pila.push(Integer.parseInt(bf.readLine()));
            } catch (IOException ex) {
            }

            try {
                System.out.println("Desea ingresar otro valor a la Pila 1[SI] - Otro Valor[NO]: ");
                op = Integer.parseInt(bf.readLine());
            } catch (IOException ex) {
            }

        } while (op == 1);

        System.out.println("\n\nla pila es= " + pila + "\n");

        // Solicitar al usuario un RUT
        System.out.println("\nPILA. (Buscar y Eliminar) Ingrese un RUT valido 99999999-9: ");

        // Leer el RUT desde la consola/teclado        
        //Acá se captura el valor del usuario, se ajusta la longitud a 9 posiciones, y se pasa a mayuscula
        Integer intRut;
        try {
            intRut = Integer.parseInt(bf.readLine());
        } catch (IOException ex) {
            intRut = 0;
        }

        System.out.println("\n\n********** Datos de la Pila con ELEMENTOS *************");
        System.out.println("Estado de la Pila: " + pila.isEmpty());
        System.out.println("El Tamaño de la Pila Actual es: " + pila.size());
        System.out.println("El Top de la Pila es: " + pila.peek());
        System.out.println("la pila es= " + pila + "\n");

        // Recorrer la Pila con un For
        int pos = pila.size();
        for (Integer elemento : pila) {
            System.out.println("Posicion= " + pos + ", RUT= " + elemento);
            pos--;
        }

        // Buscar el elemento utilizando una pila auxiliar
        // Crear una Pila
        Stack<Integer> auxPila = new Stack<>();

        boolean encontrado = false;
        int cont = 0;
        while (pila.peek() != null) {
            cont++;
            if (pila.peek().equals(intRut)) {
                encontrado = true;
                break;
            }

            // Guardar el elemento para restaurarlo
            auxPila.push(pila.pop());
        }

        if (encontrado) {
            System.out.println("\nValor de RUT introducido: " + intRut + " esta en la posicion " + cont + " de la Pila, (1 es el tope)");
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
            if (pila.peek().equals(intRut)) {
                // Eliminar el elemento
                System.out.println("Eliminado el RUT: " + pila.pop());
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
        if (pila.size() > 0) {
            Integer intElemento = pila.pop();
            System.out.println("Valor Rescatado de la Pila: " + intElemento);
        }

        //Revisar el Nuevo Top de la Pila
        if (pila.size() > 0) {
            System.out.println("El Top de la Pila es: " + pila.peek());
        }

        //Vaciado de una Pila
        System.out.println("\nVaciando la pila, mostrando los pop");
        while (!pila.empty()) {
            System.out.println(pila.pop()); //Mostrar el velor que elimina de la Pila
        }

        System.out.println("Estado de la Pila [True-Vacia / False-Contiene Elemenetos]:" + pila.isEmpty());
        /*
         */
        // *****************************************************************************************************************
        System.out.println("\n\n********************************************");
        System.out.println("************ Seccion de la Cola ************");

        // Creación de la Cola
        Queue<Integer> cola = new LinkedList<>();

        // Crear un ciclo para insertar valores en la Cola
        System.out.println("COLA. Ingresar datos a la Cola");
        do {
            // Solicitar un Valor
            System.out.println("COLA. Ingrese un valor RUT: ");

            try {
                //Acá se captura y almacena el valor en la Pila
                cola.add(Integer.parseInt(bf.readLine()));
            } catch (IOException ex) {
            }

            try {
                System.out.println("Desea ingresar otro valor a la COLA 1[SI] - Otro Valor[NO]: ");
                op = Integer.parseInt(bf.readLine());
            } catch (IOException ex) {
                op = 0;
            }

        } while (op == 1);

        System.out.println("\n\nla COLA es= " + cola + "\n");

        // Solicitar al usuario un RUT
        System.out.println("\nCOLA. (Buscar y Eliminar) Ingrese un RUT valido 99999999-9: ");

        // Leer el RUT desde la consola/teclado        
        //Acá se captura el valor del usuario, se ajusta la longitud a 9 posiciones, y se pasa a mayuscula
        try {
            intRut = Integer.parseInt(bf.readLine());
        } catch (IOException ex) {
            intRut = 0;
        }

        // Preguntar por el estado de la Cola
        System.out.println("\nEstado de la Cola [T-F], esta vacia: " + cola.isEmpty());
        System.out.println("El tamaño de la Cola es: " + cola.size());

        //Mostrar los datos de la Cola
        System.out.println("\nDel primero introducido al ultimo, la cola es= " + cola + "\n");

        // **************************************************
        // Buscar el elemento utilizando una cola auxiliar
        // Crear una Pila
        Queue<Integer> auxCola = new LinkedList<>();

        encontrado = false;
        cont = 0;
        while (cola.size() > 0) {
            if (!encontrado) {
                cont++;
            }
            if (cola.peek().equals(intRut)) {
                // Eliminar el elemento
                System.out.println("Encontrado el RUT: " + cola.peek());
                encontrado = true;
            }

            // Guardar el elemento para restaurarlo
            auxCola.add(cola.remove());
        }

        if (encontrado) {
            System.out.println("COLA. Valor de RUT introducido: " + intRut + " esta en la posicion " + cont + " de la Cola, (1 es al salir)");
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
            if (cola.peek().equals(intRut)) {
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
        if (cola.size() > 0) {
            System.out.println("Cúal es el TOP de la Cola: " + cola.peek());
        }

        if (cola.size() > 0) {
            cola.remove(cola.peek());
            System.out.println("Valores de la cola (cola.remove(cola.peek))" + cola);
        }

        if (cola.size() > 0) {
            cola.remove();
            System.out.println("Valores de la cola (cola.remove()): " + cola);
        }

        // Recorrer la Cola con un For
        System.out.println("");
        pos = 0;
        for (Integer nodoCola : cola) {
            pos++;
            System.out.println("Posicion= " + pos + ", RUT= " + nodoCola);
        }

        // Vaciado la Cola
        System.out.println("\nVaciando la COLA, mostrando los remove");
        while (cola.size() > 0) {
            System.out.println(cola.remove()); //Mostrar el velor que elimina de la Pila
        }

        /*
         */
        // *****************************************************************************************************************
        System.out.println("\n\n********************************************");
        System.out.println("************ Seccion de la ArrayList ************");

        // Creación del ARRAY
        List<Integer> list = new ArrayList<>();

        // Crear un ciclo para insertar valores en ARRAY
        System.out.println("ARRAY ingreso de datos");
        do {
            // Solicitar un Valor
            System.out.println("ARRAY. Ingrese un valor RUT: ");

            try {
                //Acá se captura y almacena el valor en la Pila
                cola.add(Integer.parseInt(bf.readLine()));
            } catch (IOException ex) {
            }

            try {
                System.out.println("Desea ingresar otro valor al ARRAY 1[SI] - Otro Valor[NO]: ");
                op = Integer.parseInt(bf.readLine());
            } catch (IOException ex) {
                op = 0;
            }

        } while (op == 1);

        System.out.println("\n\nEl ARRAY es= " + list + "\n");

        // Solicitar al usuario un RUT
        System.out.println("\nARRAY. (Buscar y Eliminar) Ingrese un RUT valido: ");

        // Leer el RUT desde la consola/teclado        
        try {
            intRut = Integer.parseInt(bf.readLine());
        } catch (IOException ex) {
            intRut = 0;
        }

        // Preguntar por el estado de la Cola
        System.out.println("\nEstado del ARRAY [T-F], esta vacia: " + list.isEmpty());
        System.out.println("El tamaño del ARRAY es: " + list.size());

        //Mostrar los datos de la Cola
        System.out.println("\nDel primero introducido al ultimo, del ARRAY es= " + list + "\n");

        // **************************************************
        // Buscar el elemento
        encontrado = false;
        cont = 0;
        for (Integer elemento : list) {
            if (elemento.equals(intRut)) {
                // Eliminar el elemento
                System.out.println("Encontrado el RUT: " + elemento);
                encontrado = true;
                break;
            }
            cont++;
        }

        if (encontrado) {
            System.out.println("ARRAY. Valor de RUT introducido: " + intRut + " esta en la posicion " + cont + " del ARRAY");
        } else {
            System.out.println("ARRAY. RUT no encontrado");
        }

        // **************************************************
        // Eliminar el elemento
        encontrado = false;
        cont = 0;
        while (cont < list.size()) {
            if (list.get(cont).equals(intRut)) {
                encontrado = true;
                break;
            }

            cont++;
        }

        if (encontrado) {
            System.out.println("ARRAY. Eliminado el RUT: " + list.remove(cont));
        } else {
            System.out.println("ARRAY. RUT no encontrado");
        }

        System.out.println("\nValores del ARRAY despues de eliminar el RUT: " + list);

        System.out.println("\n************* Datos del ARRAY ********************\n");
        System.out.println("Estado del ARRAY [F-T], esta vacio: " + list.isEmpty());
        System.out.println("El Tamaño de la ARRAY: " + list.size());
        if (list.size() > 0) {
            System.out.println("Cúal es primero del  ARRAY: " + list.get(0));
        }

        if (list.size() > 0) {
            list.remove(0);
            System.out.println("Valores del ARRAY (list.remove(0))" + list);
        }

        if (list.size() > 0) {
            list.remove(list.size() - 1);
            System.out.println("Valores del ARRAY al remover el ultimo elemento: " + list);
        }

        // Recorrer ARRAY con un For
        System.out.println("");
        pos = 0;
        for (Integer nodoArr : list) {
            System.out.println("Posicion= " + pos + ", RUT= " + nodoArr);
            pos++;
        }

        // Vaciado ARRAY
        System.out.println("\nVaciando el ARRAY, mostrando los remove(0)");
        while (list.size() > 0) {
            System.out.println(list.remove(0)); //Mostrar el velor que elimina del ARRAY
        }

    }
}
