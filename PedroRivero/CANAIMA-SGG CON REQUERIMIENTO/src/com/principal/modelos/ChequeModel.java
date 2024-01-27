/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package com.principal.modelos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
public final class ChequeModel {

    private final String montoPagar;

    private final String benef_razonsocial;

    private final String benef_rif_ci;

    private final String numLiteral_1;

    private final String numLiteral_2;

    private final String ciudad;

    private final java.util.Date fecha;

    private final String endosable;

    public ChequeModel(final String inmontoPagar, final String inbenef_razonsocial, final String inbenef_rif_ci, final String inNumLiteral_1, final String inNumLiteral_2, final String inciudad, final java.util.Date infecha, final String inendosable) {
        this.montoPagar = inmontoPagar;
        this.benef_razonsocial = inbenef_razonsocial;
        this.benef_rif_ci = inbenef_rif_ci;
        this.numLiteral_1 = inNumLiteral_1;
        this.numLiteral_2 = inNumLiteral_2;
        this.ciudad = inciudad;
        this.fecha = infecha;
        this.endosable = inendosable;
    }

    /**
     * @return the montoPagar
     */
    public String getMontoPagar() {
        return montoPagar;
    }

    /**
     * @return the benef_razonsocial
     */
    public String getBenef_razonsocial() {
        return benef_razonsocial;
    }

    /**
     * @return the benef_rif_ci
     */
    public String getBenef_rif_ci() {
        return benef_rif_ci;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @return the fecha
     */
    public java.util.Date getFecha() {
        return fecha;
    }

    /**
     * @return the endosable
     */
    public String getEndosable() {
        return endosable;
    }

    /**
     * @return the numLiteral_1
     */
    public String getNumLiteral_1() {
        return numLiteral_1;
    }

    /**
     * @return the numLiteral_2
     */
    public String getNumLiteral_2() {
        return numLiteral_2;
    }

    private static final Logger logger = LogManager.getLogger(ChequeModel.class);
}
