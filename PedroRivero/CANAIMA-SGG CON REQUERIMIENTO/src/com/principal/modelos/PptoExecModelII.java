package com.principal.modelos;

import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
public final class PptoExecModelII {

    private final String codpresu;

    private final String partida;

    private final BigDecimal inicial;

    private final BigDecimal modif_ppto;

    private final BigDecimal modif_ppto_parcial;

    private final BigDecimal ppto_modif;

    private final BigDecimal compr;

    private final BigDecimal cau;

    private final BigDecimal pag;

    private final BigDecimal deuda;

    private final BigDecimal saldo_final;

    public PptoExecModelII(final PptoModel inppto, final BigDecimal ininicial, final BigDecimal inmodif_ppto, final BigDecimal inmodif_ppto_parcial, final BigDecimal inppto_modif, final BigDecimal incompr, final BigDecimal incau, final BigDecimal inpag, final BigDecimal indeuda, final BigDecimal insaldo_final) {
        this.codpresu = inppto.getCodigo();
        this.partida = inppto.getPartida();
        this.inicial = ininicial;
        this.modif_ppto = inmodif_ppto;
        this.modif_ppto_parcial = inmodif_ppto_parcial;
        this.ppto_modif = inppto_modif;
        this.compr = incompr;
        this.cau = incau;
        this.pag = inpag;
        this.deuda = indeuda;
        this.saldo_final = insaldo_final;
    }

    @Override
    public String toString() {
        return String.valueOf(getCodpresu());
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
     * @return the inicial
     */
    public BigDecimal getInicial() {
        return inicial;
    }

    /**
     * @return the modif_ppto
     */
    public BigDecimal getModif_ppto() {
        return modif_ppto;
    }

    /**
     * @return the ppto_modif
     */
    public BigDecimal getPpto_modif() {
        return ppto_modif;
    }

    /**
     * @return the compr
     */
    public BigDecimal getCompr() {
        return compr;
    }

    /**
     * @return the cau
     */
    public BigDecimal getCau() {
        return cau;
    }

    /**
     * @return the pag
     */
    public BigDecimal getPag() {
        return pag;
    }

    /**
     * @return the deuda
     */
    public BigDecimal getDeuda() {
        return deuda;
    }

    /**
     * @return the saldo_final
     */
    public BigDecimal getSaldo_final() {
        return saldo_final;
    }

    /**
     * @return the modif_ppto_parcial
     */
    public BigDecimal getModif_ppto_parcial() {
        return modif_ppto_parcial;
    }

    private static final Logger logger = LogManager.getLogger(PptoExecModelII.class);
}
