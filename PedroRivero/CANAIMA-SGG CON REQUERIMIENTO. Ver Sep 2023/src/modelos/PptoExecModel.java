package modelos;

import java.math.BigDecimal;

/**
 *
 * @author Capip Sistemas C.A.
 */
public final class PptoExecModel {

    private final String codpresu;
    private final String partida;
    private final BigDecimal aprobado;
    private final BigDecimal inicial;
    private final BigDecimal credito;
    private final BigDecimal traspaso;
    private final BigDecimal pptoMod;
    private final BigDecimal compromiso;
    private final BigDecimal saldo;

    private final BigDecimal causado;
    private final BigDecimal pagado;

    public PptoExecModel(PptoModel inppto, BigDecimal ininicial, BigDecimal incredito, BigDecimal intraspaso, BigDecimal inpptoMod, BigDecimal incomprommiso, BigDecimal insaldo, BigDecimal incausado, BigDecimal inpagado) {

        this.codpresu = inppto.getCodigo();
        this.partida = inppto.getPartida();
        this.aprobado = inppto.getMontoIni();
        this.inicial = ininicial;
        this.credito = incredito;
        this.traspaso = intraspaso;
        this.pptoMod = inpptoMod;
        this.compromiso = incomprommiso;
        this.saldo = insaldo;
        this.causado = incausado;
        this.pagado = inpagado;
    }

    @Override
    public String toString() {
        return String.valueOf(codpresu);
    }

    /**
     * @return the inicial
     */
    public BigDecimal getInicial() {
        return inicial;
    }

    /**
     * @return the credito
     */
    public BigDecimal getCredito() {
        return credito;
    }

    /**
     * @return the traspaso
     */
    public BigDecimal getTraspaso() {
        return traspaso;
    }

    /**
     * @return the comprommiso
     */
    public BigDecimal getCompromiso() {
        return compromiso;
    }

    /**
     * @return the saldo
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * @return the causado
     */
    public BigDecimal getCausado() {
        return causado;
    }

    /**
     * @return the pagado
     */
    public BigDecimal getPagado() {
        return pagado;
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
     * @return the aprobado
     */
    public BigDecimal getAprobado() {
        return aprobado;
    }

    /**
     * @return the pptoMod
     */
    public BigDecimal getPptoMod() {
        return pptoMod;
    }

}
