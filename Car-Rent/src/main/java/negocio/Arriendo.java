package negocio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;


/**
 *
 */
public class Arriendo implements Arriendo_IF, Serializable {

    public static final int MIN_PRECIO_DIA = 10_000;
    public static final int MAX_PRECIO_DIA = 1_000_000;

    public static final int MIN_DIAS_ARR = 1;
    public static final int MAX_DIAS_ARR = 365;

    private static int nextId = 1;

    private int numArr;
    private String fecArr;
    private int diasArr;

    private Cliente cliente;
    private Vehiculo vehiculo;


    public Arriendo(String inFecArr, int inDiasArr) {
        this.numArr = nextId++;
        this.fecArr = inFecArr;
        this.diasArr = inDiasArr;

        cliente = null;
        vehiculo = null;
    }


    /**
     * Este método calcula y entrega el monto del arriendo instanciado multiplicando el número de dias <br>
     * por el precio diario. Este precio lo indica el usuario una vez que se ingresa un arriendo al sistema.<br>
     *
     * @param inPrecioDia
     * @return el monto del arriendo, -1 en caso de error.
     */
    public int ObtenerMontoPagar(int inPrecioDia) {

        if ((cliente == null) || (vehiculo == null)) {
            JOptionPane.showMessageDialog(null, "ERROR. Debe configurar Cliente y Vehiculo");
            return -1;
        }

        if ((inPrecioDia < MIN_PRECIO_DIA) || (inPrecioDia > MAX_PRECIO_DIA)) {
            JOptionPane.showMessageDialog(null, "ERROR. Precio por dia fuera de rango");
            return -1;
        }

        return inPrecioDia * diasArr;
    }


    /**
     * Operación que se ejecuta antes de guardar el arriendo al sistema y valida que el cliente del arriendo esté vigente y que <br>
     * el vehículo de este mismo arriendo tenga condición D (disponible). El método retorna un true (si está ok) o <br>
     * false (si no es posible arrendar). <br>
     *
     * @return
     */
    public boolean EvaluarArriendo() {
        if ((cliente == null) || (vehiculo == null)) {
            JOptionPane.showMessageDialog(null, "ERROR. Debe configurar Cliente y Vehiculo");
            return false;
        }

        return cliente.isVigente() && (vehiculo.getCondicion() == 'D');
    }


    @Override
    public String toString() {
        return String.valueOf(numArr) + " : " + String.valueOf(numArr) + " : " + fecArr + " : " + diasArr;
    }


    /**
     * @return the numArriendo
     */
    public int getNumArriendo() {
        return numArr;
    }


    /**
     * retorna verdadero si el inNUmArr es valido
     *
     * @param inNumArr
     * @return
     */
    public static boolean ValNumArr(int inNumArr) {
        return inNumArr > 0;
    }


    /**
     * @param inNumArr the numArriendo to set
     */
    public void setNumArr(int inNumArr) {
        if (!ValNumArr(inNumArr)) {
            JOptionPane.showMessageDialog(null, "ERROR. Número de arriendo invalido");
            return;
        }
        this.numArr = inNumArr;
    }


    /**
     * @return the fecArr
     */
    public String getFecArr() {
        return fecArr;
    }


    /**
     * Retorna verdadero si la fecha es valida
     *
     * @param inFecArr
     * @return
     */
    public static boolean ValFecArr(String inFecArr) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(inFecArr);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }


    /**
     * @param inFecArr the fecArr to set
     */
    public void setFecArr(String inFecArr) {

        if (!ValFecArr(inFecArr)) {
            JOptionPane.showMessageDialog(null, "ERROR. Fecha invalida");
            return;
        }

        this.fecArr = inFecArr;
    }


    /**
     * @return the diasArr
     */
    public int getDiasArr() {
        return diasArr;
    }


    /**
     * @param inDiasArr the diasArr to set
     * @return
     */
    public static boolean ValDiasArr(int inDiasArr) {
        return ((inDiasArr >= MIN_DIAS_ARR) && (inDiasArr <= MAX_DIAS_ARR));
    }


    /**
     * @param inDiasArr the diasArr to set
     */
    public void setDiasArr(int inDiasArr) {
        if (!ValDiasArr(inDiasArr)) {
            JOptionPane.showMessageDialog(null, "ERROR. Días de arriendo invalido");
            return;
        }

        this.diasArr = inDiasArr;
    }


    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }


    /**
     * @param inCliente the cliente to set
     * @return
     */
    public static boolean ValCliente(Cliente inCliente) {
        return inCliente != null;
    }


    /**
     * @param inCliente the cliente to set
     */
    public void setCliente(Cliente inCliente) {
        if (!ValCliente(inCliente)) {
            JOptionPane.showMessageDialog(null, "ERROR. Cliente invalido");
            return;
        }

        this.cliente = inCliente;
    }


    /**
     * @return the vehiculo
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }


    /**
     * @param inVehiculo
     * @return
     */
    public static boolean ValVehiculo(Vehiculo inVehiculo) {
        return inVehiculo != null;
    }


    /**
     * @param inVehiculo
     */
    public void setVehiculo(Vehiculo inVehiculo) {
        if (!ValVehiculo(inVehiculo)) {
            JOptionPane.showMessageDialog(null, "ERROR. Vehiculo invalido");
            return;
        }

        this.vehiculo = inVehiculo;
    }

}
