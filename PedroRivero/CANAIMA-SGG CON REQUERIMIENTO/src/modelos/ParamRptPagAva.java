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

    public ParamRptPagAva(final long _id_orden_pago,
        final String _numcaus, // 2
        final String _comprs, // 3
        final String _razonsocial, // 4
        final String _rif, // 5
        final String _pagosAnteriores, // 6
        final String _montoPagar, //7
        final String _banco, // 8
        final String _cuenta, // 9
        final String _cheque, // 10
        final String _ivaRet, // 11
        final String _ivaTot, // 12
        final String _montoTotal, // 13
        final String _resta, // 14
        final String _fechaHoy, // 15
        final String _enLetras_1, // 16
        final String _enLetras_2, // 16
        final String _concepto) { // 17

        this.id_orden_pago = _id_orden_pago;
        this.numCaus = _numcaus;
        this.numComprs = _comprs;
        this.pagosAnteriores = _pagosAnteriores;
        this.montoPagar = _montoPagar;
        this.benef_razonSocial = _razonsocial;
        this.benef_rif_ci = _rif;
        this.banco = _banco;
        this.cuenta = _cuenta;
        this.cheque = _cheque;
        this.ivaRet = _ivaRet;
        this.ivaTot = _ivaTot;
        this.montoTotal = _montoTotal;
        this.resta = _resta;
        this.fechaHoy = _fechaHoy;
        this.enLetras_1 = _enLetras_1;
        this.enLetras_2 = _enLetras_2;
        this.concepto = _concepto;
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
