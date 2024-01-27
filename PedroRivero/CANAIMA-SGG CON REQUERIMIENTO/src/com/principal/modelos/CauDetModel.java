package com.principal.modelos;

import com.principal.connection.ConnCapip;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
public class CauDetModel {

    // 1
    private final long id_causado_det;

    private final long id_user;

    private final long id_session;

    private final java.sql.Timestamp date_session;

    private final String codpresu;

    private final String partida;

    private final BigDecimal subtotal;

    public CauDetModel(final ResultSet inrs) throws Exception {
        id_causado_det = inrs.getLong("id_causado_det");
        id_user = inrs.getLong("id_user");
        id_session = inrs.getLong("id_session");
        date_session = inrs.getTimestamp("date_session");
        codpresu = inrs.getString("codpresu");
        partida = inrs.getString("partida");
        subtotal = inrs.getBigDecimal("subtotal");
    }

    public static CauDetModel getxID(final long inid) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM causado_det WHERE id_causado_det= " + inid);
        if (rs.next()) {
            return new CauDetModel(rs);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(id_causado_det);
    }

    /**
     * @return the id_causado_det
     */
    public long getId_causado_det() {
        return id_causado_det;
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
     * @return the subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * @return the codpresu
     */
    public String getCodPresu() {
        return codpresu;
    }

    /**
     * @return the partida
     */
    public String getPartida() {
        return partida;
    }

    private static final Logger logger = LogManager.getLogger(CauDetModel.class);
}
