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
public interface ArriendoCuota_IF {

    public static final String FNAME = "arriendo_cuota.dat";


    /**
     * Este método evalúa los datos del arriendo instanciado (invocando la función evaluarArriendo) <br>
     * retornando true si la operación fue exitosa y false si no lo fue. En el caso de que la operación sea <br>
     * exitosa, este método automáticamente dejará el vehículo arrendado con condición A y para aplicar correctamente la <br>
     * relación compuesta entre los objetos arriendo y cuotas, asignará las cuotas respectivas del arriendo invocando la <br>
     * función definida en c).
     *
     * @param inPrecioDia
     * @return
     */
    public boolean IngresarArriendoConCuotas(int inPrecioDia);


    /**
     * operación que se ejecuta al guardar un arriendo exitoso al sistema, que recibe el precio por día del arriendo y <br>
     * crea las cuotas según la siguiente especificación: - El número de cada cuota es un correlativo que comienza en 1 y <br>
     * se aumenta en uno para las cuotas siguientes. - El valor de cada cuota es la división entre monto a pagar y <br>
     * cantidad de cuotas. - Todas las cuotas deben quedar inicialmente con pagada igual a False. <br>
     * Este método debe retornar la lista (ArrayList) de cuotas generada para que sea asignada (o referenciada) al arriendo respectivo.
     *
     * @param inPrecioDia
     * @return
     */
    public ArrayList<CuotaArriendo> GenerarCuotas(int inPrecioDia);


    /**
     * Operación que recibe la cuota a pagar y busca la cuota en la lista respectiva. Si la encuentra, el método <br>
     * actualiza el atributo pagada con True y retorna un true, en caso contrario, retorna un false. <br>
     *
     * @param inNumCuota
     * @param inPagada
     * @return
     */
    public boolean PagarCuota(int inNumCuota, boolean inPagada);


    public static boolean ReadFromFile(ArrayList<ArriendoCuota> list) {
        FileInputStream fis = null;
        ObjectInputStream fIn = null;

        try {
            File f = new File(FNAME);

            if (!f.exists()) {
                return true;
            }

            fis = new FileInputStream(f);
            fIn = new ObjectInputStream(fis);

            while (fis.available() > 0) {

                ArriendoCuota item = (ArriendoCuota) fIn.readObject();
                list.add(item);
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
