package modelos;

import java.math.BigDecimal;
import utils.Format;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class PagoDetSinImpModel {

    private final String codpresu;
    private final String partida;
    private final BigDecimal subtotal;

    public PagoDetSinImpModel(final String _codpresu, final String _partida, final BigDecimal _subtotal) throws Exception {
        codpresu = _codpresu;
        partida = _partida;
        subtotal = _subtotal;
    }

    @Override
    public String toString() {
        return Format.toStr(subtotal);
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
