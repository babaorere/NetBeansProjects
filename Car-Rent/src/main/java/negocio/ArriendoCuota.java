package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import vista.FrmAppMain;


/**
 *
 */
public class ArriendoCuota extends Arriendo implements Arriendo_IF, Serializable {

    private static final String FNAME = "arriendo_cuota.dat";

    public static final Integer MIN_CANT_COU = 1;
    public static final Integer MAX_CANT_COU = 6;

    private ArrayList<CuotaArriendo> listCuo;
    private Integer cantCuotas;


    public ArriendoCuota(String inFecArr, int inDiasArr, int inCantCuotas) {
        super(inFecArr, inDiasArr);
        this.cantCuotas = inCantCuotas;
        listCuo = null;

        if ((cantCuotas < MIN_CANT_COU) || (cantCuotas > MAX_CANT_COU)) {
            JOptionPane.showMessageDialog(null, "ERROR. Cantidad de cuotas fuera de rango");
        }

    }


    public static boolean ReadFromFile(ArrayList<ArriendoCuota> list) {
        return ArriendoCuota_IF.ReadFromFile(list);
    }


    public static boolean WriteToFile(ArrayList<ArriendoCuota> list) {
        return ArriendoCuota_IF.WriteToFile(list);
    }


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
    public boolean IngresarArriendoConCuotas(int inPrecioDia) {

        if ((getCliente() == null) || (getVehiculo() == null)) {
            return false;
        }

        if (!EvaluarArriendo()) {
            JOptionPane.showMessageDialog(null, "EvaluarArriendo() = false");
            return false;
        }

        if (listCuo != null) {
            JOptionPane.showMessageDialog(null, "ERROR. Ya fueron generadas las cuotas");
            return false;
        }

        getVehiculo().setCondicion('A');
        FrmAppMain.WriteVehiculo();

        listCuo = GenerarCuotas(inPrecioDia);

        return true;
    }


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
    private ArrayList<CuotaArriendo> GenerarCuotas(int inPrecioDia) {

        if ((inPrecioDia < MIN_PRECIO_DIA) || (inPrecioDia > MAX_PRECIO_DIA)) {
            JOptionPane.showMessageDialog(null, "ERROR. Precio por dia fuera de rango");
            return null;
        }

        ArrayList<CuotaArriendo> list = new ArrayList<>();

        int total = ObtenerMontoPagar(inPrecioDia);

        // Verificar error
        if (total < 0) {
            return null;
        }

        if (cantCuotas == 1) {
            list.add(new CuotaArriendo(1, total, false));
        } else {
            int monto = total / cantCuotas;

            for (int i = 1; i <= (cantCuotas - 1); i++) {
                list.add(new CuotaArriendo(i, monto, false));
            }

            // Actualizar la ultima cuota, ya que hay un truncamiento al hacer una division entera
            // se va generando un error acumulado "total != monto*cantCuotas"
            list.add(new CuotaArriendo(cantCuotas, total - monto * (cantCuotas - 1), false));
        }

        return list;
    }


    /**
     * Operación que recibe la cuota a pagar y busca la cuota en la lista respectiva. Si la encuentra, el método <br>
     * actualiza el atributo pagada con True y retorna un true, en caso contrario, retorna un false. <br>
     *
     * @param inNumCuota
     * @param inPagada
     * @return
     */
    public boolean PagarCuota(int inNumCuota, boolean inPagada) {
        if ((inNumCuota < 0) || (inNumCuota >= listCuo.size())) {
            JOptionPane.showMessageDialog(null, "ERROR. Número de cuota fuera de rango");
            return false;
        }

        return listCuo.get(inNumCuota).PagarCuota(inPagada);
    }


    /**
     * @return the cantCuotas
     */
    public int getCantCuotas() {
        return cantCuotas;
    }


    public boolean ValCantCuotas(int inCantCuotas) {

        return (inCantCuotas >= MIN_CANT_COU) && (inCantCuotas <= MAX_CANT_COU);
    }


    /**
     * @param inCantCuotas the cantCuotas to set
     */
    public void setCantCuotas(int inCantCuotas) {

        if (ValCantCuotas(inCantCuotas)) {
            this.cantCuotas = inCantCuotas;
        }
    }


    /**
     * @return the listCuo
     */
    public ArrayList<CuotaArriendo> getListCuo() {

        return listCuo;
    }


    public boolean Pagar1raCuota() {
        if (listCuo.size() < 1) {
            return false;
        }

        listCuo.get(0).PagarCuota(true);
        FrmAppMain.WriteArrCuo();

        return true;
    }

}
