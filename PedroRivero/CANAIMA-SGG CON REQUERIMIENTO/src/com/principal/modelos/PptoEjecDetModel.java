/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package com.principal.modelos;

import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Baba
 */
public class PptoEjecDetModel implements Comparable<PptoEjecDetModel> {

    final private String ejeMov;

    final private java.sql.Date fecha;

    final private BigDecimal monto;

    final private String doc;

    final private String obs;

    public PptoEjecDetModel(final String inejeMov, final java.sql.Date infecha, final BigDecimal inmonto, final String indoc, final String inobs) {
        this.ejeMov = inejeMov;
        this.fecha = infecha;
        this.monto = inmonto;
        this.doc = indoc;
        this.obs = inobs;
    }

    @Override
    public String toString() {
        return ejeMov + " " + fecha.toString();
    }

    /**
     * @return the ejecMov
     */
    public String getEjeMov() {
        return ejeMov;
    }

    /**
     * @return the fecha
     */
    public java.sql.Date getFecha() {
        return fecha;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @return the doc
     */
    public String getDoc() {
        return doc;
    }

    @Override
    public int compareTo(PptoEjecDetModel inother) {
        return fecha.compareTo(inother.fecha);
    }

    private static final Logger logger = LogManager.getLogger(PptoEjecDetModel.class);
}
