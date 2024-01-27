package modelos;

import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 * Modelo para la tabla de avande_efectivo
 *
 * @author Capip Sistemas C.A.
 */
public class AvanceEfectivoModel implements PagAvaInterface {

    private final long id;
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final java.sql.Date ejefis;

    private final String benef_razonsocial;
    private final String benef_rif_ci;
    private final java.sql.Date fecha_pago;
    private final String observacion;
    private final String banco;
    private final String cuenta;
    private final String cheque;
    private final java.sql.Date fecha_cheque;
    private final String endosable_sn;
    private final BigDecimal apagar_bs;
    private final String anulado_sn;

    public AvanceEfectivoModel(final ResultSet _rs) throws Exception {
        id = _rs.getLong("id");
        id_user = _rs.getLong("id_user");
        id_session = _rs.getLong("id_session");
        date_session = _rs.getTimestamp("date_session");
        ejefis = _rs.getDate("ejefis");
        benef_razonsocial = _rs.getString("benef_razonsocial");
        benef_rif_ci = _rs.getString("benef_rif_ci");
        fecha_pago = _rs.getDate("fecha_pago");
        observacion = _rs.getString("observacion");
        banco = _rs.getString("banco");
        cuenta = _rs.getString("cuenta");
        cheque = _rs.getString("cheque");
        fecha_cheque = _rs.getDate("fecha_cheque");
        endosable_sn = _rs.getString("endosable_sn");
        apagar_bs = _rs.getBigDecimal("a_pagar_bs");
        anulado_sn = _rs.getString("anulado_sn");
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    /**
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @return the id_user
     */
    @Override
    public long getId_user() {
        return id_user;
    }

    /**
     * @return the id_session
     */
    @Override
    public long getId_session() {
        return id_session;
    }

    /**
     * @return the date_session
     */
    @Override
    public java.sql.Timestamp getDate_session() {
        return date_session;
    }

    /**
     * @return the ejefis
     */
    @Override
    public java.sql.Date getEjefis() {
        return ejefis;
    }

    /**
     * @return the benef_razonsocial
     */
    @Override
    public String getBenef_razonsocial() {
        return benef_razonsocial;
    }

    /**
     * @return the benef_rif_ci
     */
    @Override
    public String getBenef_rif_ci() {
        return benef_rif_ci;
    }

    /**
     * @return the fecha_pago
     */
    @Override
    public java.sql.Date getFecha_pago() {
        return fecha_pago;
    }

    /**
     * @return the observacion
     */
    @Override
    public String getObservacion() {
        return observacion;
    }

    /**
     * @return the banco
     */
    @Override
    public String getBanco() {
        return banco;
    }

    /**
     * @return the cuenta
     */
    @Override
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @return the cheque
     */
    @Override
    public String getCheque() {
        return cheque;
    }

    /**
     * @return the fecha_cheque
     */
    @Override
    public java.sql.Date getFecha_cheque() {
        return fecha_cheque;
    }

    /**
     * @return the apagar_bs
     */
    @Override
    public BigDecimal getA_pagar_bs() {
        return apagar_bs;
    }

    /**
     * @return the endosable_sn
     */
    public String getEndosable_sn() {
        return endosable_sn;
    }

    /**
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }

}
