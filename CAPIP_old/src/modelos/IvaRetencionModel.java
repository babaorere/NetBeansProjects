package modelos;

import capipsistema.Conn;
import java.sql.ResultSet;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class IvaRetencionModel {

    private final long id_iva_retencion;
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final java.sql.Date ejefis;
    private final String benef_razonsocial;
    private final String benef_rif_ci;
    private final String anulado_sn;

    /**
     * Rev 19/10/2016
     *
     * @param _rs
     * @throws Exception
     */
    public IvaRetencionModel(final ResultSet _rs) throws Exception {
        id_iva_retencion = _rs.getLong("id_iva_retencion");
        id_user = _rs.getLong("id_user");
        id_session = _rs.getLong("id_session");
        date_session = _rs.getTimestamp("date_session");
        ejefis = _rs.getDate("ejefis");

        benef_razonsocial = _rs.getString("benef_razonsocial");
        benef_rif_ci = _rs.getString("benef_rif_ci");
        anulado_sn = _rs.getString("anulado_sn");
    }

    @Override
    public String toString() {
        return String.valueOf(id_iva_retencion);
    }

    /**
     * @return the id_iva_retencion
     */
    public long getId_iva_retencion() {
        return id_iva_retencion;
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
     * @return the ejefis
     */
    public java.sql.Date getEjefis() {
        return ejefis;
    }

    /**
     * @return the benef_razonsocial
     */
    public String getBenef_razonsocial() {
        return benef_razonsocial;
    }

    /**
     * @return the benef_rif_ci
     */
    public String getBenef_rif_ci() {
        return benef_rif_ci;
    }

    /**
     * Rev 19/10/2016
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static IvaRetencionModel getxID(final long id) throws Exception {

        final ResultSet rs = Conn.executeQuery("SELECT * FROM iva_retencion WHERE id_iva_retencion= " + id);

        if (rs.next()) {
            return new IvaRetencionModel(rs);
        }

        return null;
    }

    /**
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }
}
