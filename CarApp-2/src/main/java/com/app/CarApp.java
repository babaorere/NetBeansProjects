package com.app;

import com.app.modelo.Vehiculo;
import static java.util.Arrays.fill;

/**
 *
 */
public class CarApp {

    /**
     * Determinar las marcas de Automoviles leidas del archivo
     *
     * @return
     */
    private static String[] determinarMarcas(Vehiculo[] arrAuto) {
        // creamos un array de String, marcas
        String[] arrMarca = new String[5];
        int cant = 0;

        // Recorremos el array de automoviles, y a medida que conseguimos una nueva marca,
        // la agregamos al array de marcas
        for (Vehiculo item : arrAuto) {

            // Bucamos si la marca ya fue agregada al array de marcas
            boolean encontrado = false;
            for (String marca : arrMarca) {
                if (item.getMarca().equals(marca)) {
                    encontrado = true;
                    break;
                }
            }

            // no fue encontrado, es agregado
            if (!encontrado) {

                // Si el array esta lleno, lo redimensionamos un 50% adicional
                if (cant >= arrMarca.length) {

                    // creamos un nuevo array, un 50% mas grande
                    String arrAux[] = arrMarca;
                    arrMarca = new String[(int) (arrAux.length * 1.50)];

                    // copiamos los elementos
                    System.arraycopy(arrAux, 0, arrMarca, 0, arrAux.length);
                }

                arrMarca[cant++] = item.getMarca();
            }

        }

        // Redimensionamiento final del array de String, marcas, 
        // al tamaño exacto dada por cant
        // redimensionar el array
        String[] arrAux = arrMarca;
        arrMarca = new String[cant];

        // copiamos los elementos
        System.arraycopy(arrAux, 0, arrMarca, 0, cant);

        return arrMarca;
    }

    public static void main(String[] args) {

        System.out.println("Bienvenido a Car Sale");

        // Lenar el array interno, con los datos del archivo
        ArchivoDatos.leerDeArchivo();

        // Crear el array de automoviles
        Vehiculo Galpon1[] = ArchivoDatos.getArrAuto();

        if ((Galpon1 == null) || (Galpon1.length <= 0)) {
            System.out.println("Error al tratar de leer del archivo");
            System.exit(-1);
        }

        mostrarArr(Galpon1);

        String[] arrMarcas = determinarMarcas(Galpon1);

        int[] contar = ContarPorMarca(Galpon1, arrMarcas);

        mostrarPorContador(arrMarcas, contar);

        mostrarPorMarca(Galpon1, arrMarcas);

        // cambiar la marca del automvil con indice 0
        if (Galpon1.length > 0) {
            Vehiculo item = Galpon1[0];

            System.out.println("\nAntes de cambiar la marca: " + item);
            item.setMarca("Ferrari");
            System.out.println("Despues de cambiar la marca: " + item);

        } else {
            System.out.println("\nNo hay automovil para cambiar la marca");
        }

        // copiamos los elementos
        Vehiculo[] Galpon2 = new Vehiculo[Galpon1.length];
        System.arraycopy(Galpon1, 0, Galpon2, 0, Galpon1.length);

        OrdenarDescendenteSeleccion(Galpon2);

        // copiamos los elementos
        Vehiculo[] Galpon3 = new Vehiculo[Galpon1.length];
        System.arraycopy(Galpon1, 0, Galpon3, 0, Galpon1.length);
        OrdenarAscendenteQuicksort(Galpon3);

        mostrarTresArr(Galpon1, Galpon2, Galpon3);

        System.out.println("\n\nGracias por su confianza");
    }

    private static void mostrarArr(Vehiculo[] arr) {
        System.out.println("\n\nContenido del array:");

        for (Vehiculo item : arr) {
            System.out.println(item);
        }
    }

    private static void mostrarPorContador(String[] arrMarca, int[] arrContar) {

        if ((arrMarca == null) || (arrMarca.length <= 0) || (arrContar == null) || (arrMarca.length <= 0)) {
            System.out.println("\n\nNo hay datos que mostrar");
            return;
        }

        if (arrMarca.length != arrContar.length) {
            System.out.println("\n\nError el tamaño de los arrays difieren");
            return;
        }

        System.out.println("\n\nmostrarPorContador. Contenido del array:");

        int total = 0;
        for (int i = 0; i < arrMarca.length; i++) {
            total += arrContar[i];

            if (arrContar[i] <= 1) {
                System.out.println(arrMarca[i] + " tiene " + arrContar[i] + " automovil");
            } else {
                System.out.println(arrMarca[i] + " tiene " + arrContar[i] + " automoviles");
            }
        }

        System.out.println("Total es: " + total);

    }

    private static int[] ContarPorMarca(Vehiculo[] arr, String[] arrMarcas) {

        int[] arrContar = new int[arrMarcas.length];

        fill(arrContar, 0);

        // Recorremos el array de automoviles, y a buscamos su marca, para haller el respectivo indice, 
        // e incrementar el Contador
        for (Vehiculo item : arr) {

            // Bucamos el indice asociado a la marca
            for (int idx = 0; idx < arrMarcas.length; idx++) {
                if (item.getMarca().equals(arrMarcas[idx])) {
                    arrContar[idx]++;
                    break;
                }
            }
        }

        return arrContar;
    }

    private static void mostrarPorMarca(Vehiculo[] inArrAuto, String[] inArrMarcas) {

        if ((inArrAuto == null) || (inArrAuto.length <= 0) || (inArrMarcas == null) || (inArrMarcas.length <= 0)) {
            System.out.println("\n\nNo hay datos que mostrar");
            return;
        }

        if (inArrAuto.length < inArrMarcas.length) {
            System.out.println("\n\nError en el tamaño de los arrays");
            return;
        }

        System.out.println("\n\nMostrarPorMarca. Contenido del array:");

        // Recorremos el array de marcas, y procedemos a mostrar los automoviles que corresponden a esa marca 
        for (String itemMarca : inArrMarcas) {
            System.out.println("Marca: " + itemMarca);

            // Bucamos los automoviles correspondiente a esa marca
            for (Vehiculo itemAuto : inArrAuto) {
                if (itemMarca.equals(itemAuto.getMarca())) {
                    System.out.println(itemAuto);
                }
            }
        }

    }

    private static void OrdenarDescendenteSeleccion(Vehiculo[] arrAuto) {
        for (int i = 0; i < arrAuto.length; i++) {

            int min = i;
            for (int j = i + 1; j < arrAuto.length; j++) {
                if (arrAuto[j].getAnio() > arrAuto[min].getAnio()) {
                    min = j;
                }
            }

            // Swapping i-th and min-th elements
            Vehiculo swap = arrAuto[i];
            arrAuto[i] = arrAuto[min];
            arrAuto[min] = swap;
        }
    }

    private static void OrdenarAscendenteQuicksort(Vehiculo[] arrAuto) {

        quicksort(arrAuto, 0, arrAuto.length - 1);

    }

    private static void quicksort(Vehiculo arreglo[], int izquierda, int derecha) {
        if (izquierda < derecha) {
            int indiceParticion = particion(arreglo, izquierda, derecha);
            quicksort(arreglo, izquierda, indiceParticion);
            quicksort(arreglo, indiceParticion + 1, derecha);
        }
    }

    private static int particion(Vehiculo arreglo[], int izquierda, int derecha) {

        Vehiculo pivote = arreglo[izquierda];

        // Ciclo infinito
        while (true) {
            while (arreglo[izquierda].getAnio() < pivote.getAnio()) {
                izquierda++;
            }

            while (arreglo[derecha].getAnio() > pivote.getAnio()) {
                derecha--;
            }

            if (izquierda >= derecha) {
                return derecha;
            }

            Vehiculo temporal = arreglo[izquierda];
            arreglo[izquierda] = arreglo[derecha];
            arreglo[derecha] = temporal;
            izquierda++;
            derecha--;
        }
    }

    private static void mostrarTresArr(Vehiculo[] inArrAuto1, Vehiculo[] inArrAuto2, Vehiculo[] inArrAuto3) {

        // Validar la entrada de datos
        if ((inArrAuto1 == null) || (inArrAuto1.length <= 0)
                || (inArrAuto2 == null) || (inArrAuto2.length <= 0)
                || (inArrAuto3 == null) || (inArrAuto3.length <= 0)) {
            System.out.println("\n\nNo hay datos que mostrar");
            return;
        }

        // La longitud de los tres array deben ser iguales
        if ((inArrAuto1.length != inArrAuto2.length)
                || (inArrAuto1.length != inArrAuto2.length)
                || (inArrAuto2.length != inArrAuto3.length)) {
            System.out.println("\n\nError en el tamaño de los arrays");
            return;
        }

        System.out.println("\n\nmostrarTresArr. Solo columna años");
        System.out.println("Galpon1  Galpon2  Galpon3");
        for (int i = 0; i < inArrAuto1.length; i++) {
            System.out.printf("\n%5d @ %5d @ %5d", inArrAuto1[i].getAnio(), inArrAuto2[i].getAnio(), inArrAuto3[i].getAnio());
        }

        System.out.println("\n\nmostrarTresArr.");
        System.out.println("Galpon1          Galpon2          Galpon3");
        for (int i = 0; i < inArrAuto1.length; i++) {
            System.out.printf("\n%50s @ %50s @ %50s", inArrAuto1[i], inArrAuto2[i], inArrAuto3[i]);
        }

    }
}

//Le comento que te voy a pasar los archivos de la carpera src del pryecto, Ud debe crear un proyecto netbeans llamado CarApp, y copiar los fuentes que le doy. Estoy por la incompatibilidad de versiones del NB, yo tengo la 14, y ya la version 8.2 no se consigue de Oracle
