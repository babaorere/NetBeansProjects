package modelos;

import capipsistema.Conn;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class CauDetModel {

    private final long id_causado_det; // 1
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final String codpresu;
    private final String partida;
    private final BigDecimal subtotal;

    public CauDetModel(final ResultSet _rs) throws Exception {
        id_causado_det = _rs.getLong("id_causado_det");
        id_user = _rs.getLong("id_user");
        id_session = _rs.getLong("id_session");
        date_session = _rs.getTimestamp("date_session");
        codpresu = _rs.getString("codpresu");
        partida = _rs.getString("partida");
        subtotal = _rs.getBigDecimal("subtotal");
    }

    public static CauDetModel getxID(final long _id) throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT * FROM causado_det WHERE id_causado_det= " + _id);
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

}
