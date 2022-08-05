package negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 */
public interface Vehiculo_IF {

    public static final String FNAME = "vehiculos.dat";


    public static boolean ReadFromFile(final ArrayList<Vehiculo> listV) {

        FileInputStream fis = null;
        ObjectInputStream fIn = null;

        listV.clear();

        try {
            File f = new File(FNAME);

            if (!f.exists()) {
                return true;
            }

            fis = new FileInputStream(f);
            fIn = new ObjectInputStream(fis);

            while (fis.available() > 0) {

                Vehiculo item = (Vehiculo) fIn.readObject();
                listV.add(item);
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

        if (fis != null) {
            try {
                fis.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar el archivo: " + FNAME + "\n" + ex.toString());
            }
        }

        return true;
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
    public String toString();

}
