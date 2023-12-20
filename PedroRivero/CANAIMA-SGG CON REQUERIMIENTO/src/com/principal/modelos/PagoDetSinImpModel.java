package com.principal.modelos;

import com.principal.utils.Format;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
public class PagoDetSinImpModel {

    private final String codpresu;

    private final String partida;

    private final BigDecimal subtotal;

    public PagoDetSinImpModel(final String incodpresu, final String inpartida, final BigDecimal insubtotal) throws Exception {
        codpresu = incodpresu;
        partida = inpartida;
        subtotal = insubtotal;
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

    private static final Logger logger = LogManager.getLogger(PagoDetSinImpModel.class);
}
