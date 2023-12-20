/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package modelos;

import java.math.BigDecimal;

/**
 *
 * @author Baba
 */
public class PptoEjecDetRptModel implements Comparable<PptoEjecDetRptModel> {

    final private String mov;
    final private java.util.Date fecha;
    final private BigDecimal monto;
    final private String doc;
    final private String obs;

    public PptoEjecDetRptModel(final String inejeMov, final java.sql.Date infecha, final BigDecimal inmonto, final String indoc, final String inobs) {

        this.mov = inejeMov;
        this.fecha = infecha;
        this.monto = inmonto;
        this.doc = indoc;
        this.obs = inobs;
    }

    public PptoEjecDetRptModel(final PptoEjecDetModel inreg) {

        this.mov = inreg.getEjeMov();
        this.fecha = new java.util.Date( inreg.getFecha().getTime());
        this.monto = inreg.getMonto();
        this.doc = inreg.getDoc();
        this.obs = inreg.getObs();
    }

    @Override
    public String toString() {
        return mov + " " + fecha.toString();
    }

    @Override
    public int compareTo(PptoEjecDetRptModel inother) {
        return fecha.compareTo( inother.fecha);
    }

    /**
     * @return the mov
     */
    public String getMov() {
        return mov;
    }

    /**
     * @return the fecha
     */
    public java.util.Date getFecha() {
        return fecha;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @return the doc
     */
    public String getDoc() {
        return doc;
    }

    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

}
