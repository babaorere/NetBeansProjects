package com.app;

import com.app.modelo.Vehiculo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Esta clase maneja la entrada y salida del dato Automovil, a un archivo CSV, separado por ";"
 */
public class ArchivoDatos {

    private static final String FNAME = "autos_listado.csv";

    private static Vehiculo[] arrAuto = null;

    public static Vehiculo[] leerDeArchivo() {

        Random rand = new Random();

        // creamos un array de automoviles
        arrAuto = new Vehiculo[5];

        // inicializar contador de automoviles
        int cant = 0;

        // try con recursos, abre y cierra automaticamente el recurso
        try (Scanner sc = new Scanner(new File(FNAME))) {

            // Leer la linea del titulo de columna, la cual es desechada
            String linea = sc.nextLine().trim();

            while (sc.hasNextLine()) {

                linea = sc.nextLine().trim();

                if (linea.length() <= 0) {
                    break;
                }

                // Leer la linea, y separar cada columna, por el caracter ";"
                String item[] = linea.split(",");

                // Verificar la cantidad de columnas de la linea recien leida
                if (item.length != 18) {
                    break; // error, dejar de leer el archivo
                }

                int altura;
                int largo;
                int ancho;
                String traccion;
                String motor;
                String hibrido;
                int marchas;
                String transmision;
                int mpgCiudad;
                String tipoCombustible;
                int mpgCarretera;
                String clasificacion;
                String id;
                String marca;
                String modeloAnio;
                int anio;
                int caballosFuerza;
                int torque;

                try {
                    altura = Integer.parseInt(item[0].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                try {
                    largo = Integer.parseInt(item[1].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                try {
                    ancho = Integer.parseInt(item[2].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                traccion = item[3].trim();
                motor = item[4].trim();
                hibrido = item[5].trim();

                try {
                    marchas = Integer.parseInt(item[6].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                transmision = item[7].trim();

                try {
                    mpgCiudad = Integer.parseInt(item[8].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                tipoCombustible = item[9].trim();

                try {
                    mpgCarretera = Integer.parseInt(item[10].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                clasificacion = item[11].trim();
                id = item[12].trim();
                marca = item[13].trim();
                modeloAnio = item[14].trim();

                try {
                    anio = Integer.parseInt(item[15].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                try {
                    caballosFuerza = Integer.parseInt(item[16].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                try {
                    torque = Integer.parseInt(item[17].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                // Si el array esta lleno, lo redimensionamos un 50% adicional
                if (cant == arrAuto.length) {

                    // creamos un nuevo array, un 50% mas grande
                    Vehiculo arrAux[] = arrAuto;
                    arrAuto = new Vehiculo[(int) (arrAux.length * 1.50)];

                    // copiamos los elementos
                    System.arraycopy(arrAux, 0, arrAuto, 0, arrAux.length);
                }

                arrAuto[cant++] = new Vehiculo(altura, largo, ancho, traccion, motor, hibrido, marchas, transmision, mpgCiudad, tipoCombustible, mpgCarretera, clasificacion, id, marca, modeloAnio, anio, caballosFuerza, torque);

            }

        } catch (FileNotFoundException ex) {
            // No hacer, ya que esto puede ocurrir por llegar al final del archivo
        }

        // Redimensionamiento final del array de Vehiculos, 
        // para que el tamaño del array se ajuste exactamente a la cantidad de vehiculos
        // redimensionar el array
        if (cant > 0) {
            Vehiculo[] arrAux = arrAuto;
            arrAuto = new Vehiculo[cant];

            // copiamos los elementos
            System.arraycopy(arrAux, 0, arrAuto, 0, cant);
        } else {
            arrAuto = null;
        }

        return arrAuto;
    }

    /**
     * @return the arr
     */
    public static Vehiculo[] getArrAuto() {
        return arrAuto;
    }

}
