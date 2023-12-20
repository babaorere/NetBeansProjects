package com.replaceinfiles;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ReplaceInFiles {

    public static void main(String[] args) {
        // Directorio base donde se inicia la búsqueda
        String directorioBase = "."; // Directorio actual

        List<File> archivosJava = buscarArchivosJava(directorioBase);

        if (archivosJava.isEmpty()) {
            System.out.println("No se encontraron archivos .java.");
        } else {
            for (File archivo : archivosJava) {
                try {
                    sustituirCadenaEnArchivo(archivo, "\"/com/principal/imagenes/", "\"/com/principal/imagenes/");
                    System.out.println("Sustitución realizada en: " + archivo.getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("Error: " + e.toString());
                }
            }
        }
    }

    public static List<File> buscarArchivosJava(String directorio) {
        List<File> archivosJava = new ArrayList<>();

        try {
            Files.walk(Paths.get(directorio))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .forEach(path -> archivosJava.add(path.toFile()));
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }

        return archivosJava;
    }

    public static void sustituirCadenaEnArchivo(File archivo, String cadenaAntigua, String cadenaNueva) throws IOException {
        Path rutaArchivo = archivo.toPath();
        String contenido = new String(Files.readAllBytes(rutaArchivo), "UTF-8");
        contenido = contenido.replace(cadenaAntigua, cadenaNueva);
        Files.write(rutaArchivo, contenido.getBytes("UTF-8"));
    }
}
