/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, @2017
 *
 */
package modelos;

import java.math.BigDecimal;

/**
 *
 * @author Baba
 */
public class PagoRptModel {

    private final String codpresu;
    private final String partida;
    private final BigDecimal subtotal;

    public PagoRptModel(String _codpresu, String _partida, BigDecimal _subtotal) {
        this.codpresu = _codpresu;
        this.partida = _partida;
        this.subtotal = _subtotal;
    }

    @Override
    public String toString() {
        return String.valueOf(codpresu);
    }

    /**
     * @return the codpresu
     */
    public String getCodpresu() {
        return codpresu;
    }

    /**
     * @return the partida
     */
    public String getPartida() {
        return partida;
    }

    /**
     * @return the subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

}
