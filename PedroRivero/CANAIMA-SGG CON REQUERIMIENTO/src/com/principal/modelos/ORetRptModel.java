package com.principal.modelos;

import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
public final class ORetRptModel {

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
     * @param infechaop
     * @param inanulado_sn
     * @param innumop
     * @param innumfact
     * @param incontribuyente
     * @param iniretenido
     * @param inmtfactura
     * @param inbase_imponible
     * @param inrif_ci
     */
    public ORetRptModel(final java.util.Date infechaop, final String inanulado_sn, final long innumop, final String innumfact, final String inrif_ci, final String incontribuyente, final BigDecimal inmtfactura, final BigDecimal inbase_imponible, final BigDecimal iniretenido) {
        fechaop = infechaop;
        anulado_sn = inanulado_sn;
        numop = innumop;
        numfact = innumfact;
        rif_ci = inrif_ci;
        contribuyente = incontribuyente;
        mtfactura = inmtfactura;
        base_imponible = inbase_imponible;
        iretenido = iniretenido;
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

    private static final Logger logger = LogManager.getLogger(ORetRptModel.class);
}
