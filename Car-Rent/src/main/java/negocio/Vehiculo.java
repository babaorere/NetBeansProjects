package negocio;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 */
public class Vehiculo implements Vehiculo_IF, Serializable {

    public static final int MIN_LOG_PATENTE = 5;
    public static final int MAX_LOG_PATENTE = 6;

    static long nextId = 1;

    private final long id;
    private String patente;
    private char condicion;


    public Vehiculo(String inPatente, char inCondicion) {
        this.id = nextId++;
        this.patente = inPatente;
        this.condicion = inCondicion;

    }


    public Vehiculo(Vehiculo inOther) {
        this.id = inOther.id;
        this.patente = inOther.patente;
        this.condicion = inOther.condicion;
    }


    public static boolean ReadFromFile(final ArrayList<Vehiculo> listV) {

        return Vehiculo_IF.ReadFromFile(listV);
    }


    public static boolean WriteToFile(final ArrayList<Vehiculo> listV) {

        ObjectOutputStream fOut = null;

        try {

            fOut = new ObjectOutputStream(new FileOutputStream(FNAME));
            for (Vehiculo item : listV) {
                fOut.writeObject(item);
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
     * @param listV
     * @return the list
     */
    public static ArrayList<Vehiculo> getCopyListV(final ArrayList<Vehiculo> listV) {

        ArrayList<Vehiculo> aux = new ArrayList<>();

        listV.forEach((v) -> {
            aux.add(new Vehiculo(v));
        });

        return aux;
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
     * @return
     */
    public static boolean ValPatente(String inPatente) {
        return (inPatente != null) && (inPatente.length() >= MIN_LOG_PATENTE) && (inPatente.length() <= MAX_LOG_PATENTE);
    }


    /**
     * @param inPatente the patente to set
     */
    public void setPatente(String inPatente) {
        if (ValPatente(inPatente)) {
            this.patente = inPatente;
        }

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
