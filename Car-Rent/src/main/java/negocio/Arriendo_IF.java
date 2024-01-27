package negocio;


/**
 *
 */
public interface Arriendo_IF {

    public static final String FNAME = "clientes.dat";


    /**
     * Este método calcula y entrega el monto del arriendo instanciado multiplicando el número de dias <br>
     * por el precio diario. Este precio lo indica el usuario una vez que se ingresa un arriendo al sistema.<br>
     *
     * @param inPrecioDia
     * @return el monto del arriendo, -1 en caso de error.
     */
    public int ObtenerMontoPagar(int inPrecioDia);


    /**
     * Operación que se ejecuta antes de guardar el arriendo al sistema y valida que el cliente del arriendo esté vigente y que <br>
     * el vehículo de este mismo arriendo tenga condición D (disponible). El método retorna un true (si está ok) o <br>
     * false (si no es posible arrendar). <br>
     *
     * @return
     */
    public boolean EvaluarArriendo();


    @Override
    public String toString();

}
