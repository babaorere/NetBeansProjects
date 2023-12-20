package modelos;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class CompromisoRptParam {

    private final String numOrden;
    private final String sTotal;
    private final String EnLetras;

    public CompromisoRptParam() {
        this(null, null, null);
    }

    public CompromisoRptParam(String innumOrden, String insTotal, String inEnLetras) {
        this.numOrden = innumOrden;
        this.sTotal = insTotal;
        this.EnLetras = inEnLetras;
    }

    /**
     * @return the numOrden
     */
    public String getNumOrden() {
        return numOrden;
    }

    /**
     * @return the sTotal
     */
    public String getSTotal() {
        return sTotal;
    }

    /**
     * @return the EnLetras
     */
    public String getEnLetras() {
        return EnLetras;
    }
}
