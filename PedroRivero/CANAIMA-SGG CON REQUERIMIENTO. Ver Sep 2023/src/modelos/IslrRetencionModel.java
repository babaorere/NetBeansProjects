package modelos;

import connection.ConnCapip;
import java.sql.ResultSet;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class IslrRetencionModel {

    private final long id_islr_retencion;
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final java.sql.Date ejefis;
    private final String benef_razonsocial;
    private final String benef_rif_ci;
    private final String anulado_sn;

    /**
     * Rev 20/10/2016
     *
     * @param inrs
     * @throws Exception
     */
    public IslrRetencionModel(final ResultSet inrs) throws Exception {
        id_islr_retencion = inrs.getLong("id_islr_retencion");
        id_user = inrs.getLong("id_user");
        id_session = inrs.getLong("id_session");
        date_session = inrs.getTimestamp("date_session");
        ejefis = inrs.getDate("ejefis");

        benef_razonsocial = inrs.getString("benef_razonsocial");
        benef_rif_ci = inrs.getString("benef_rif_ci");
        anulado_sn = inrs.getString("anulado_sn");
    }

    /**
     * Rev 20/10/2016
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(id_islr_retencion);
    }

    /**
     * @return the id_iva_retencion
     */
    public long getId_islr_retencion() {
        return id_islr_retencion;
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
    public static IslrRetencionModel getxID(final long id) throws Exception {

        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM islr_retencion WHERE id_islr_retencion= " + id);

        if (rs.next()) {
            return new IslrRetencionModel(rs);
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
