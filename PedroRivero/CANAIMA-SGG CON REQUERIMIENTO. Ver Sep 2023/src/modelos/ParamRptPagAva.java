package modelos;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class ParamRptPagAva {

    final private long id_orden_pago;
    final private String numCaus;
    final private String numComprs;
    final private String benef_razonSocial;
    final private String benef_rif_ci;
    final private String pagosAnteriores;
    final private String montoPagar;
    final private String banco;
    final private String cuenta;
    final private String cheque;
    final private String ivaRet;
    final private String ivaTot;
    final private String montoTotal;
    final private String resta;
    final private String fechaHoy;
    final private String enLetras_1;
    final private String enLetras_2;
    final private String concepto;

    public ParamRptPagAva(final long inid_orden_pago,
        final String innumcaus, // 2
        final String incomprs, // 3
        final String inrazonsocial, // 4
        final String inrif, // 5
        final String inpagosAnteriores, // 6
        final String inmontoPagar, //7
        final String inbanco, // 8
        final String incuenta, // 9
        final String incheque, // 10
        final String inivaRet, // 11
        final String inivaTot, // 12
        final String inmontoTotal, // 13
        final String inresta, // 14
        final String infechaHoy, // 15
        final String inenLetras_1, // 16
        final String inenLetras_2, // 16
        final String inconcepto) { // 17

        this.id_orden_pago = inid_orden_pago;
        this.numCaus = innumcaus;
        this.numComprs = incomprs;
        this.pagosAnteriores = inpagosAnteriores;
        this.montoPagar = inmontoPagar;
        this.benef_razonSocial = inrazonsocial;
        this.benef_rif_ci = inrif;
        this.banco = inbanco;
        this.cuenta = incuenta;
        this.cheque = incheque;
        this.ivaRet = inivaRet;
        this.ivaTot = inivaTot;
        this.montoTotal = inmontoTotal;
        this.resta = inresta;
        this.fechaHoy = infechaHoy;
        this.enLetras_1 = inenLetras_1;
        this.enLetras_2 = inenLetras_2;
        this.concepto = inconcepto;
    }

    /**
     * @return el numero de pago
     */
    public long getId_orden_pago() {
        return id_orden_pago;
    }

    /**
     * @return el monto a pagar
     */
    public String getTotal() {
        return getMontoPagar();
    }

    /**
     * @return la razon social
     */
    public String getBenef_RazonSocial() {
        return benef_razonSocial;
    }

    /**
     * @return the rif
     */
    public String getBenef_rif_ci() {
        return benef_rif_ci;
    }

    /**
     * @return the fechaHoy
     */
    public String getFechaHoy() {
        return fechaHoy;
    }

    /**
     * @return el numero de causados
     */
    public String getNumCaus() {
        return numCaus;
    }

    /**
     * @return el numero de compromisos
     */
    public String getNumComprs() {
        return numComprs;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @return the cheque
     */
    public String getCheque() {
        return cheque;
    }

    /**
     * @return the montoPagado
     */
    public String getPagosAnteriores() {
        return pagosAnteriores;
    }

    /**
     * @return the montoPagar
     */
    public String getMontoPagar() {
        return montoPagar;
    }

    /**
     * @return the ivaRet
     */
    public String getIvaRet() {
        return ivaRet;
    }

    /**
     * @return the ivaRet
     */
    public String getIvaTot() {
        return ivaTot;
    }

    /**
     * @return the montoTotal
     */
    public String getMontoTotal() {
        return montoTotal;
    }

    /**
     * @return the resta
     */
    public String getResta() {
        return resta;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @return the enLetras_1
     */
    public String getEnLetras_1() {
        return enLetras_1;
    }

    /**
     * @return the enLetras_2
     */
    public String getEnLetras_2() {
        return enLetras_2;
    }

}
