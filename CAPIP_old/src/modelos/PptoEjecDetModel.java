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
public class PptoEjecDetModel implements Comparable<PptoEjecDetModel> {

    final private String ejeMov;
    final private java.sql.Date fecha;
    final private BigDecimal monto;
    final private String doc;
    final private String obs;

    public PptoEjecDetModel(final String _ejeMov, final java.sql.Date _fecha, final BigDecimal _monto, final String _doc, final String _obs) {

        this.ejeMov = _ejeMov;
        this.fecha = _fecha;
        this.monto = _monto;
        this.doc = _doc;
        this.obs = _obs;
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
    public int compareTo(PptoEjecDetModel _other) {
        return fecha.compareTo(_other.fecha);
    }

}
