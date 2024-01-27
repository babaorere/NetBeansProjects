/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.addline;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author manager
 */
public class AddLine {

    public static void main(String[] args) throws IOException {
        System.out.print("Ejecutando ");

        // Indicamos el directorio que queremos recorrer.
        File directorio = new File(".");

        // Llamamos a la función leerArchivos().
        leerArchivos(directorio);

        System.out.println("Programa ejecutado");
    }

    public static void leerArchivos(File directorio) throws IOException {
        System.out.print(".");

        // Obtenemos una lista de todos los archivos en el directorio.
        File[] archivos = directorio.listFiles();

        // Iteramos sobre los archivos.
        for (File archivo : archivos) {
            // Si el archivo es un archivo .java, lo leemos.
            if (archivo.getName().endsWith(".java")) {
                // Obtenemos el contenido del archivo.
                String contenido = new String(Files.readAllBytes(archivo.toPath()));

                // Obtenemos la declaración de la clase.
                int posicionDeclaracionClase = contenido.indexOf("public class");

                // Incluimos la línea adicional.
                contenido = contenido.substring(0, posicionDeclaracionClase) + "\n// Esta línea se agregó con un programa en Java.\n" + contenido.substring(posicionDeclaracionClase);

                // Escribimos el contenido del archivo modificado.
                Files.write(archivo.toPath(), contenido.getBytes());
            }

            // Si el archivo es un directorio, lo recorremos recursivamente.
            if (archivo.isDirectory()) {
                leerArchivos(archivo);
            }
        }
    }

}
