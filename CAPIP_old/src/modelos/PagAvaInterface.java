package modelos;

import java.math.BigDecimal;

/**
 *
 * @author Capip Sistemas C.A.
 */
public interface PagAvaInterface {

    @Override
    String toString();

    /**
     * @return the id_orden_pago
     */
    public long getId();

    /**
     * @return the id_user
     */
    public long getId_user();

    /**
     * @return the id_session
     */
    public long getId_session();

    /**
     * @return the date_session
     */
    public java.sql.Timestamp getDate_session();

    /**
     * @return the ejefis
     */
    public java.sql.Date getEjefis();

    /**
     * @return the benef_razonsocial
     */
    public String getBenef_razonsocial();

    /**
     * @return the benef_rif_ci
     */
    public String getBenef_rif_ci();

    /**
     * @return the fecha_pago
     */
    public java.sql.Date getFecha_pago();

    /**
     * @return the observacion
     */
    public String getObservacion();

    /**
     * @return the banco
     */
    public String getBanco();

    /**
     * @return the cuenta
     */
    public String getCuenta();

    /**
     * @return the cheque
     */
    public String getCheque();

    /**
     * @return the fecha_cheque
     */
    public java.sql.Date getFecha_cheque();

    /**
     * @return the apagar_bs
     */
    public BigDecimal getA_pagar_bs();

}
