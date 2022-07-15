/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package modelos;

import capipsistema.Conn;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class BenefModel {

    private final Long id_beneficiario;
    private final Long id_user;
    private final Long id_session;
    private final Timestamp date_session;
    private final String razonsocial;
    private final String rif_ci;
    private final String domicilio;
    private final String telefonos;
    private final String activo;

    public static BenefModel getxID(final long _id) throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT * FROM beneficiario WHERE id_beneficiario=" + _id);
        if (rs.next()) {
            return new BenefModel(rs);
        }

        return null;
    }

    public static BenefModel getxRifCi(final String _rif_ci) throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT * FROM beneficiario WHERE rif_ci='" + _rif_ci + "'");
        if (rs.next()) {
            return new BenefModel(rs);
        }

        return null;
    }

    public BenefModel(final ResultSet _rs) throws Exception {
        id_beneficiario = _rs.getLong("id_beneficiario");
        id_user = _rs.getLong("id_user");
        id_session = _rs.getLong("id_session");
        date_session = _rs.getTimestamp("date_session");
        razonsocial = _rs.getString("razonsocial");
        rif_ci = _rs.getString("rif_ci");
        domicilio = _rs.getString("domicilio");
        telefonos = _rs.getString("telefonos");
        activo = _rs.getString("activo");
    }

    @Override
    public String toString() {
        return razonsocial;
    }

    /**
     * @return the id_compr
     */
    public Long getId_beneficiario() {
        return id_beneficiario;
    }

    /**
     * @return the id_user
     */
    public Long getId_user() {
        return id_user;
    }

    /**
     * @return the id_session
     */
    public Long getId_session() {
        return id_session;
    }

    /**
     * @return the razonsocial
     */
    public String getRazonsocial() {
        return razonsocial;
    }

    /**
     * @return the rif_ci
     */
    public String getRif_ci() {
        return rif_ci;
    }

    /**
     * @return the domicilio
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * @return the telefonos
     */
    public String getTelefonos() {
        return telefonos;
    }

    /**
     * @return the activo
     */
    public String getActivo() {
        return activo;
    }

    /**
     * @return the registro
     */
    public Timestamp getDate_session() {
        return date_session;
    }

}
