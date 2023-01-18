/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class TblTrasp {

    private final long id_trasp;
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final String ppto_egr_ing;
    private final String referencia;
    private final java.sql.Date fecha;
    private final BigDecimal monto;
    private final String concepto;
    private final long ejefis;
    private final long ejefismes;
    private final String anulado_sn;

    public static TblTrasp getReg_x_Id(long _id_trasp) throws Exception {
        TblTrasp aux;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM trasppartidas WHERE id_trasp= '" + _id_trasp + "'");

        if (rs.next()) {
            aux = new TblTrasp(rs);
        } else {
            aux = null;
        }

        return aux;
    }

    public TblTrasp(final ResultSet _rs) throws Exception {
        id_trasp = _rs.getLong("id_trasp");
        id_user = _rs.getLong("id_user");
        id_session = _rs.getLong("id_session");
        date_session = _rs.getTimestamp("date_session");
        ppto_egr_ing = _rs.getString("ppto_egr_ing");
        referencia = _rs.getString("referencia");
        fecha = _rs.getDate("fecha");
        monto = _rs.getBigDecimal("monto");
        concepto = _rs.getString("concepto");
        ejefis = _rs.getLong("ejefis");
        ejefismes = _rs.getLong("ejefismes");
        anulado_sn = _rs.getString("anulado_sn");
    }

    @Override
    public String toString() {
        return String.valueOf(id_trasp);
    }

    /**
     * @return the id_trasp
     */
    public long getId_trasp() {
        return id_trasp;
    }

    /**
     * @return the id_user
     */
    public long getId_user() {
        return id_user;
    }

    /**
     * @return the id_session
     */
    public long getId_session() {
        return id_session;
    }

    /**
     * @return the date_session
     */
    public java.sql.Timestamp getDate_session() {
        return date_session;
    }

    /**
     * @return the ppto_egr_ing
     */
    public String getPpto_egr_ing() {
        return ppto_egr_ing;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @return the fecha
     */
    public java.sql.Date getFecha() {
        return fecha;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @return the ejefis
     */
    public long getEjefis() {
        return ejefis;
    }

    /**
     * @return the ejefismes
     */
    public long getEjefismes() {
        return ejefismes;
    }

    /**
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }

}
