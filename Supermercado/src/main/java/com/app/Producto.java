package com.app;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;


/**
 *
 */
public class Producto {

    private String nombre;
    private int precio;
    private int cantidad;


    /**
     * Constructor con parametros
     *
     * @param inNombre
     * @param inPrecio
     */
    public Producto(String inNombre, int inPrecio) {
        this(inNombre, inPrecio, 0);
    }


    /**
     * Constructor con parametros
     *
     * @param inNombre
     * @param inPrecio
     * @param inCant
     */
    public Producto(String inNombre, int inPrecio, int inCant) {
        this.nombre = inNombre;
        this.precio = inPrecio;
        this.cantidad = inCant;
    }

    /**
     * Nombre de archivo a ser utilizado al realizar lectura y escritura
     */
    private static final String FNAME = "productos.txt";
    private static final int MAXPROD = 13;


    public static boolean ReadFromFile(List<Producto> inlist) {

        File f;
        Scanner sc = null;

        inlist.clear();

        try {
            f = new File(FNAME);

            if (!f.exists()) {
                return true;
            }

            sc = new Scanner(f);

            int cont = 0;
            while (sc.hasNextLine()) {

                // Leer la linea, y separar los items, por el caracter ";"
                String item[] = sc.nextLine().trim().split(";");

                // Verificar la cantidad de items de la linea recien leida
                if (item.length < 2) {
                    break; // dejar de leer el archivo
                }

                String nombreProd = item[0].trim();

                int precioProd = 0;
                try {
                    precioProd = Integer.parseInt(item[1].trim());
                } catch (NumberFormatException inNumberFormatException) {
                    break; // error en el formato del precio. Dejar de leer el archivo
                }

                inlist.add(new Producto(nombreProd, precioProd));

                // Se pueden leer un maximo de 13 productos
                cont++;
                if (cont >= 13) {
                    break;
                }
            }

        } catch (Exception ex) {
            return false;
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (Exception ex) {
                }
            }
        }

        return true;
    }


    public static boolean WriteToFile(List<Producto> listProd) {

        PrintWriter writer = null;

        try {

            writer = new PrintWriter(FNAME, "UTF-8");

            for (Producto item : listProd) {
                writer.println(item.getNombre() + ";" + String.valueOf(item.getPrecio()));
            }

        } catch (Exception ex) {
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ex) {
                }
            }
        }

        return true;
    }


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * @param inNombre
     */
    public void setNombre(String inNombre) {
        if ((inNombre != null) && (inNombre.length() > 0)) {
            this.nombre = inNombre;
        }
    }


    /**
     * @return the precio
     */
    public int getPrecio() {
        return precio;
    }


    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        if ((precio >= 500) && (precio <= 2000)) {
            this.precio = precio;
        }
    }


    /**
     * @return the FNAME
     */
    public static String getFNAME() {
        return FNAME;
    }


    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }


    /**
     * @param inCantidad the cantidad to set
     */
    public void setCantidad(int inCantidad) {
        if (inCantidad > 0) {
            this.cantidad = inCantidad;
        }
    }


    @Override
    public String toString() {

        return String.format("%-15s%10d%5d", nombre, precio, cantidad);
    }

}
