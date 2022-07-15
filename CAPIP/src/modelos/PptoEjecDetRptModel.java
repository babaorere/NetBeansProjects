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

    public PptoEjecDetRptModel(final String _ejeMov, final java.sql.Date _fecha, final BigDecimal _monto, final String _doc, final String _obs) {

        this.mov = _ejeMov;
        this.fecha = _fecha;
        this.monto = _monto;
        this.doc = _doc;
        this.obs = _obs;
    }

    public PptoEjecDetRptModel(final PptoEjecDetModel _reg) {

        this.mov = _reg.getEjeMov();
        this.fecha = new java.util.Date(_reg.getFecha().getTime());
        this.monto = _reg.getMonto();
        this.doc = _reg.getDoc();
        this.obs = _reg.getObs();
    }

    @Override
    public String toString() {
        return mov + " " + fecha.toString();
    }

    @Override
    public int compareTo(PptoEjecDetRptModel _other) {
        return fecha.compareTo(_other.fecha);
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
