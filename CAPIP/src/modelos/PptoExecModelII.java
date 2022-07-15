package modelos;

import java.math.BigDecimal;

/**
 *
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

    public PptoExecModelII(final PptoModel _ppto, final BigDecimal _inicial, final BigDecimal _modif_ppto,
            final BigDecimal _modif_ppto_parcial, final BigDecimal _ppto_modif, final BigDecimal _compr,
            final BigDecimal _cau, final BigDecimal _pag, final BigDecimal _deuda,
            final BigDecimal _saldo_final) {

        this.codpresu = _ppto.getCodigo();
        this.partida = _ppto.getPartida();
        this.inicial = _inicial;
        this.modif_ppto = _modif_ppto;
        this.modif_ppto_parcial = _modif_ppto_parcial;
        this.ppto_modif = _ppto_modif;
        this.compr = _compr;
        this.cau = _cau;
        this.pag = _pag;
        this.deuda = _deuda;
        this.saldo_final = _saldo_final;

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

}
