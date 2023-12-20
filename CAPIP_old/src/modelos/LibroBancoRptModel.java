/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2017
 *
 */
package modelos;

import java.math.BigDecimal;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class LibroBancoRptModel {

    private final long asiento;
    private final String fecha;
    private final String soporte;
    private final String desc;
    private final String tipo;
    private final BigDecimal debe;
    private final BigDecimal haber;
    private final String conciliado;

    public LibroBancoRptModel(final long _asiento, final String _fecha, final String _soporte, final String _desc,
        final String _tipo, final BigDecimal _debe, final BigDecimal _haber, final String _conciliado) {

        this.asiento = _asiento;
        this.fecha = _fecha;
        this.soporte = _soporte;
        this.desc = _desc;
        this.tipo = _tipo;
        this.debe = _debe;
        this.haber = _haber;
        this.conciliado = _conciliado;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @return the soporte
     */
    public String getSoporte() {
        return soporte;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the debe
     */
    public BigDecimal getDebe() {
        return debe;
    }

    /**
     * @return the haber
     */
    public BigDecimal getHaber() {
        return haber;
    }

    /**
     * @return the conciliado
     */
    public String getConciliado() {
        return conciliado;
    }

    /**
     * @return the asiento
     */
    public long getAsiento() {
        return asiento;
    }

}
