/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package com.principal.modelos;

import com.principal.connection.ConnCapip;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
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

    public static BenefModel getxID(final long inid) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM beneficiario WHERE id_beneficiario=" + inid);
        if (rs.next()) {
            return new BenefModel(rs);
        }
        return null;
    }

    public static BenefModel getxRifCi(final String inrif_ci) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM beneficiario WHERE rif_ci='" + inrif_ci + "'");
        if (rs.next()) {
            return new BenefModel(rs);
        }
        return null;
    }

    public BenefModel(final ResultSet inrs) throws Exception {
        id_beneficiario = inrs.getLong("id_beneficiario");
        id_user = inrs.getLong("id_user");
        id_session = inrs.getLong("id_session");
        date_session = inrs.getTimestamp("date_session");
        razonsocial = inrs.getString("razonsocial");
        rif_ci = inrs.getString("rif_ci");
        domicilio = inrs.getString("domicilio");
        telefonos = inrs.getString("telefonos");
        activo = inrs.getString("activo");
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

    private static final Logger logger = LogManager.getLogger(BenefModel.class);
}
