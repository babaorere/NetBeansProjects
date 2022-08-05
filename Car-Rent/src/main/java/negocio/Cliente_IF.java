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
public interface Cliente_IF {

    public static final String FNAME = "clientes.dat";


    public static boolean ReadFromFile(ArrayList<Cliente> listClte) {
        FileInputStream fis = null;
        ObjectInputStream fIn = null;

        listClte.clear();

        try {
            File f = new File(FNAME);

            if (!f.exists()) {
                return true;
            }

            fis = new FileInputStream(f);
            fIn = new ObjectInputStream(fis);

            while (fis.available() > 0) {

                Cliente item = (Cliente) fIn.readObject();
                listClte.add(item);
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


    public static boolean WriteToFile(ArrayList<Cliente> listClte) {

        ObjectOutputStream fOut = null;

        try {

            fOut = new ObjectOutputStream(new FileOutputStream(FNAME));
            for (Cliente item : listClte) {
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
}
