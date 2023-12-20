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

    public LibroBancoRptModel(final long inasiento, final String infecha, final String insoporte, final String indesc,
        final String intipo, final BigDecimal indebe, final BigDecimal inhaber, final String inconciliado) {

        this.asiento = inasiento;
        this.fecha = infecha;
        this.soporte = insoporte;
        this.desc = indesc;
        this.tipo = intipo;
        this.debe = indebe;
        this.haber = inhaber;
        this.conciliado = inconciliado;
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
