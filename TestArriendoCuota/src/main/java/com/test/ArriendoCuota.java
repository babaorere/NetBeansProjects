package com.test;

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
public class ArriendoCuota extends Arriendo implements Serializable {

    private static final String FNAME = "arriendo_cuota.dat";


    public ArriendoCuota(String inFecArr, int inDiasArr, int inCantCuotas) {
        super(inFecArr, inDiasArr);
    }


    public static boolean ReadFromFile(ArrayList<ArriendoCuota> list) {
        FileInputStream fis = null;
        ObjectInputStream fIn = null;

        try {
            fis = new FileInputStream(new File(FNAME));
            fIn = new ObjectInputStream(fis);

            while (fis.available() > 0) {

                list.add((ArriendoCuota) fIn.readObject());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de leer el archivo: " + FNAME + "\n" + ex.toString());
            return false;
        } finally {
            if (fIn != null) {
                try {
                    fIn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al tratar de cerrar el archivo: " + FNAME + "\n" + ex.toString());
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al tratar de cerrar el archivo: " + FNAME + "\n" + ex.toString());
                }
            }

        }

        return true;
    }


    public static boolean WriteToFile(ArrayList<ArriendoCuota> list) {

        ObjectOutputStream fOut = null;

        try {

            fOut = new ObjectOutputStream(new FileOutputStream(FNAME));
            for (ArriendoCuota item : list) {
                fOut.writeObject(item);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de guardar el archivo: " + FNAME + "\n" + ex.toString());
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

}
