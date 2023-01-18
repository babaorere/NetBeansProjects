package com.testwriteread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 */
public class Vehiculo implements Serializable {

    private static final String FNAME = "vehiculos.dat";

    public static final ArrayList<Vehiculo> list = new ArrayList<Vehiculo>();

    public final int MIN_LOG_PATENTE = 5;
    public final int MAX_LOG_PATENTE = 6;

    static long nextId = 1;

    private final long id;
    private String patente;
    private char condicion;


    public Vehiculo(String inPatente, char inCondicion) {
        this.id = nextId++;
        this.patente = inPatente;
        this.condicion = inCondicion;

        java.awt.EventQueue.invokeLater(() -> {
            list.add(this);
        });
    }


    public static boolean ReadFromFile() {
        FileInputStream fis = null;
        ObjectInputStream fIn = null;

        try {
            fis = new FileInputStream(new File(FNAME));

            while (fis.available() > 0) {
                fIn = new ObjectInputStream(fis);

                Vehiculo item = (Vehiculo) fIn.readObject();
            }

        } catch (Exception ex) {
            return false;
        } finally {
            if (fIn != null) {
                try {
                    fIn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al tratar de cerrar el archivo: " + FNAME + "\n" + ex.toString());
                }
            }
        }

        return true;
    }


    public static boolean WriteToFile() {

        ObjectOutputStream fOut = null;

        try {

            fOut = new ObjectOutputStream(new FileOutputStream(FNAME));
            for (Vehiculo item : list) {
                fOut.writeObject(fOut);
            }

        } catch (Exception ex) {
            return false;
        } finally {
            if (fOut != null) {
                try {
                    fOut.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al tratar de cerrar el archivo: " + FNAME + "\n" + ex.toString());
                }
            }
        }

        return true;
    }


    @Override
    public String toString() {
        return String.valueOf(id) + " : " + patente + " : " + condicion;
    }


    /**
     * @return the id
     */
    public long getId() {
        return id;
    }


    /**
     * @return the patente
     */
    public String getPatente() {
        return patente;
    }


    /**
     * @param inPatente the patente to set
     */
    public void setPatente(String inPatente) {
        if ((inPatente == null) || (inPatente.length() < MIN_LOG_PATENTE) || (inPatente.length() > MAX_LOG_PATENTE)) {
            JOptionPane.showMessageDialog(null, "ERROR. Longitu de de la patente, fuera de rango");
            return;
        }

        this.patente = inPatente;
    }


    /**
     * @return the condicion
     */
    public char getCondicion() {
        return condicion;
    }


    /**
     * @param inCondicion the condicion to set
     */
    public void setCondicion(char inCondicion) {
        inCondicion = Character.toUpperCase(inCondicion);

        if ((inCondicion != 'D') && (inCondicion != 'A')) {
            JOptionPane.showMessageDialog(null, "ERROR. Condición invalida");
            return;
        }

        this.condicion = inCondicion;
    }

}
