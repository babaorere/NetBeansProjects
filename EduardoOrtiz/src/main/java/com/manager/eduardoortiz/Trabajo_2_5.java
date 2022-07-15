package com.manager.eduardoortiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Ejercicio 2.5
 *
 * @author manager
 */
public class Trabajo_2_5 {

    public static String completa(String reg, int n) {
        if (reg.length() < n) {
            while (reg.length() < n) {
                reg += "-";
            }
        } else {
            reg = reg.substring(0, n);
        }
        return reg;
    }

    public static void main(String[] args) {

        final int LINE_LEN = 64;

        // Para Windows
//        final String nameFileIn = "C:/UNICOS.txt";
//        final String nameFileOut = "C:/QUINTA.txt";
/**/
        // Para Linux
        final String nameFileIn = "/home/manager/UNICOS.txt";
        final String nameFileOut = "/home/manager/QUINTA.txt";

        // Manejador para acceder al archivo de entrada
        FileReader fileIn = null;
        FileWriter fileOut = null;
        PrintWriter pw = null;

        try {
            File archivo = new File(nameFileIn);

            fileIn = new FileReader(archivo);
            if (fileIn == null) {
                System.out.println("\n\nError al tratar de abrir el archivo: " + nameFileIn);
                System.exit(1);
                return;
            }

            BufferedReader br = new BufferedReader(fileIn);
            if (br == null) {
                System.out.println("\n\nError al tratar de abrir el BufferedReader del archivo: " + nameFileIn);
                System.exit(1);
                return;
            }

            fileOut = new FileWriter(nameFileOut);
            if (fileOut == null) {
                System.out.println("\n\nError al tratar de abrir el archivo: " + nameFileOut);
                System.exit(1);
                return;
            }

            pw = new PrintWriter(fileOut);
            if (pw == null) {
                System.out.println("\n\nError al tratar de abrir el PrintWriter del archivo: " + nameFileOut);
                System.exit(1);
                return;
            }

            // Lectura del fichero
            String lineaIn;

            while ((lineaIn = br.readLine()) != null) {

                // Separar la lineas en trozos separados por espacio, puntos, comas
                // Split es un metodo de la clase String de java
                // y se utiliza una expresion regular, para indicar el caracter separador
                String[] vecStr = lineaIn.split("[ \\.\\,]");
                StringBuilder sb = new StringBuilder(LINE_LEN);

                int cont = 0;
                for (String str : vecStr) {
                    String aux = str.trim();
                    if (!aux.isEmpty()) { // esto es para evitar los posibles espacios en blanco que pudieran quedar
                        sb.append(str).append("|"); // añadir el string de longitud variable, al buffer
                        cont++;
                        if (cont >= 5) {
                            break; // salir del ciclo, son solo los primeros 5 registros
                        }
                    }
                }

                // Escribir la linea al archivo
                String strAux = sb.toString();
                if (!strAux.isEmpty()) {
                    pw.print(completa(strAux, LINE_LEN));
                    pw.print("\n");
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("\n\nError archivo: " + nameFileIn + " no encontrado");
            System.exit(1);
            return;
        } catch (IOException ex) {
            System.out.println("\n\nError de I/O");
            System.exit(1);
            return;
        } finally {
            try {
                // Cerrar archivo de entrada
                if (fileIn != null) {
                    fileIn.close();
                }

                // Cerrar archivo de salida
                if (fileOut != null) {
                    fileOut.close();
                }

            } catch (IOException ex) {
                System.out.println("\n\nError al tratar de cerrar el archivo: " + nameFileIn);
                System.exit(1);
                return;
            }
        }
    }
}
