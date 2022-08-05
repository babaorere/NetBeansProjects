package com.app;

import com.app.modelo.Automovil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Esta clase maneja la entrada y salida del dato Automovil, a un archivo CSV, separado por ";"
 */
public class ArchivoDatos {

    private static final String FNAME = "autos.csv";

    private static Automovil[] arrAuto;

    public static Automovil[] leerDeArchivo() {

        Random rand = new Random();

        // creamos un array de automoviles
        arrAuto = new Automovil[5];

        // inicializar contador de automoviles
        int cant = 0;

        // try con recursos, abre y cierra automaticamente el recurso
        try ( Scanner sc = new Scanner(new File(FNAME))) {

            while (sc.hasNextLine()) {

                String linea = sc.nextLine().trim();

                if (linea.length() <= 0) {
                    break;
                }

                // Leer la linea, y separar cada columna, por el caracter ";"
                String item[] = linea.split(",");

                // Verificar la cantidad de columnas de la linea recien leida
                if (item.length != 6) {
                    break; // error, dejar de leer el archivo
                }

                String marca;
                int velocidad, potencia, pasajeros, costo, consumo, anio;

                marca = item[0].trim();

                try {
                    velocidad = Integer.parseInt(item[1].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                try {
                    potencia = Integer.parseInt(item[2].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                try {
                    pasajeros = Integer.parseInt(item[3].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                try {
                    costo = Integer.parseInt(item[4].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                try {
                    consumo = Integer.parseInt(item[5].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato. Dejar de leer el archivo
                }

                // Generar el año a partir de 1970
                anio = 1970 + rand.nextInt(53);

                // Si el array es ta lleno, lo redimensionamos un 50% adicional
                if (cant == arrAuto.length) {

                    // creamos un nuevo array, un 50% mas grande
                    Automovil arrAux[] = arrAuto;
                    arrAuto = new Automovil[(int) (arrAux.length * 1.50)];

                    // copiamos los elementos
                    System.arraycopy(arrAux, 0, arrAuto, 0, arrAux.length);
                }

                arrAuto[cant++] = new Automovil(marca, velocidad, potencia, pasajeros, costo, consumo, anio);

            }

        } catch (FileNotFoundException ex) {
            // No hacer, ya que esto puede ocurrir por llegar al final del archivo
        }

        // Redimensionamiento final del array de Automoviles
        // redimensionar el array
        if (cant > 0) {
            Automovil[] arrAux = arrAuto;
            arrAuto = new Automovil[cant];

            // copiamos los elementos
            System.arraycopy(arrAux, 0, arrAuto, 0, cant);
        } else {
            arrAuto = null;
        }

        return arrAuto;
    }

    public static boolean guardarEnArchivo(Automovil[] arrAuto) {

        // try con recursos, abre y cierra automaticamente el recurso
        try ( PrintWriter writer = new PrintWriter(FNAME, "UTF-8")) {

            for (Automovil item : arrAuto) {
                writer.println(item.getMarca()
                        + ";" + String.valueOf(item.getVelocidad())
                        + ";" + String.valueOf(item.getPotencia())
                        + ";" + String.valueOf(item.getPasajeros())
                        + ";" + String.valueOf(item.getCosto())
                        + ";" + String.valueOf(item.getConsumo()));
            }

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    /**
     * @return the arr
     */
    public static Automovil[] getArrAuto() {
        return arrAuto;
    }

}
