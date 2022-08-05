package negocio;


/**
 *
 */
public interface CuotaArriendo_IF {

    /**
     * Operación que recibe la cuota a pagar y busca la cuota en la lista respectiva. Si la encuentra, el método <br>
     * actualiza el atributo pagada con True y retorna un true, en caso contrario, retorna un false. <br>
     *
     * @param inPagada
     * @return
     */
    public boolean PagarCuota(boolean inPagada);


    @Override
    public String toString();

}
