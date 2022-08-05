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

    public CompromisoRptParam(String _numOrden, String _sTotal, String _EnLetras) {
        this.numOrden = _numOrden;
        this.sTotal = _sTotal;
        this.EnLetras = _EnLetras;
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
