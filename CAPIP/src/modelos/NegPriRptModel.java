package modelos;

import java.math.BigDecimal;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class NegPriRptModel {

    private final java.util.Date fechaop;
    private final String anulado_sn;
    private final long numop;
    private final String numfact;
    private final String rif_ci;
    private final String contribuyente;
    private final BigDecimal mtfactura;
    private final BigDecimal base_imponible;
    private final BigDecimal iretenido;

    /**
     * Rev 16-04-2017
     *
     * @param _fechaop
     * @param _anulado_sn
     * @param _numop
     * @param _numfact
     * @param _contribuyente
     * @param _iretenido
     * @param _mtfactura
     * @param _base_imponible
     * @param _rif_ci
     */
    public NegPriRptModel(final java.util.Date _fechaop, final String _anulado_sn, final long _numop, final String _numfact,
        final String _rif_ci, final String _contribuyente, final BigDecimal _mtfactura, final BigDecimal _base_imponible,
        final BigDecimal _iretenido) {
        fechaop = _fechaop;
        anulado_sn = _anulado_sn;
        numop = _numop;
        numfact = _numfact;
        rif_ci = _rif_ci;
        contribuyente = _contribuyente;
        mtfactura = _mtfactura;
        base_imponible = _base_imponible;
        iretenido = _iretenido;
    }

    /**
     * Rev 16/04/2017
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(numop);
    }

    /**
     * @return the fechaop
     */
    public java.util.Date getFechaop() {
        return fechaop;
    }

    /**
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }

    /**
     * @return the numop
     */
    public long getNumop() {
        return numop;
    }

    /**
     * @return the numfact
     */
    public String getNumfact() {
        return numfact;
    }

    /**
     * @return the rif_ci
     */
    public String getRif_ci() {
        return rif_ci;
    }

    /**
     * @return the contribuyente
     */
    public String getContribuyente() {
        return contribuyente;
    }

    /**
     * @return the mtfactura
     */
    public BigDecimal getMtfactura() {
        return mtfactura;
    }

    /**
     * @return the base_imponible
     */
    public BigDecimal getBase_imponible() {
        return base_imponible;
    }

    /**
     * @return the iretenido
     */
    public BigDecimal getIretenido() {
        return iretenido;
    }

}
