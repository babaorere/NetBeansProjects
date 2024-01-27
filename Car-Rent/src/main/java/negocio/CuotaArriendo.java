package negocio;

import java.io.Serializable;


/**
 *
 * @author manager
 */
public class CuotaArriendo implements CuotaArriendo_IF, Serializable {

    static Long nextId = 1L;

    private final Long id;
    private Integer numCuota;
    private Integer valorCuota;
    private Boolean pagada;


    public CuotaArriendo(int inNumCuota, int inValorCuota, boolean inPagada) {
        this.id = nextId++;
        this.numCuota = inNumCuota;
        this.valorCuota = inValorCuota;
        this.pagada = inPagada;
    }


    /**
     * Operación que recibe la cuota a pagar y busca la cuota en la lista respectiva. Si la encuentra, el método <br>
     * actualiza el atributo pagada con True y retorna un true, en caso contrario, retorna un false. <br>
     *
     * @param inPagada
     * @return
     */
    public boolean PagarCuota(boolean inPagada) {
        setPagada(inPagada);
        return inPagada;
    }


    @Override
    public String toString() {
        return String.valueOf(numCuota) + " : " + String.valueOf(valorCuota) + " : " + String.valueOf(pagada);
    }


    /**
     * @return the id
     */
    public long getId() {
        return id;
    }


    /**
     * @return the numCuota
     */
    public int getNumCuota() {
        return numCuota;
    }


    public boolean ValNumCuota(int inNumCuota) {
        return (inNumCuota >= 1) && (inNumCuota <= 6);
    }


    /**
     * @param inNumCuota the numCuota to set
     */
    public void setNumCuota(int inNumCuota) {
        if (ValNumCuota(inNumCuota)) {
            this.numCuota = inNumCuota;
        }
    }


    /**
     * @return the valorCuota
     */
    public int getValorCuota() {
        return valorCuota;
    }


    public boolean ValValorCuota(int inValorCuota) {
        return (inValorCuota >= 0);
    }


    /**
     * @param inValorCuota the valorCuota to set
     */
    public void setValorCuota(int inValorCuota) {
        if (ValValorCuota(inValorCuota)) {
            this.valorCuota = inValorCuota;
        }
    }


    /**
     * @return the pagada
     */
    public Boolean getPagada() {
        return pagada;
    }


    /**
     * @param inPagada the pagada to set
     */
    public void setPagada(boolean inPagada) {
        this.pagada = inPagada;
    }

}
