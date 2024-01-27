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
public final class TblTraspDet {

    private final long id_trasp_det;
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final long id_trasp;
    private final String ppto_egr_ing;
    private final String tipo_ori_dest;
    private final String codpresu;
    private final String partida;
    private final BigDecimal monto;

    public TblTraspDet(ResultSet _rs) throws Exception {

        id_trasp_det = _rs.getLong("id_trasp_det");
        id_user = _rs.getLong("id_user");
        id_session = _rs.getLong("id_session");
        date_session = _rs.getTimestamp("date_session");
        id_trasp = _rs.getLong("id_trasp");
        ppto_egr_ing = _rs.getString("ppto_egr_ing");
        tipo_ori_dest = _rs.getString("tipo_ori_dest");
        codpresu = _rs.getString("codpresu");
        partida = _rs.getString("partida");
        monto = _rs.getBigDecimal("monto");
    }

    @Override
    public String toString() {
        return String.valueOf(id_trasp_det);
    }

    /**
     * @return the id_trasp_det
     */
    public long getId_trasp_det() {
        return id_trasp_det;
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
     * @return the id_trasp
     */
    public long getId_trasp() {
        return id_trasp;
    }

    /**
     * @return the ppto_egr_ing
     */
    public String getPpto_egr_ing() {
        return ppto_egr_ing;
    }

    /**
     * @return the tipo_ori_dest
     */
    public String getTipo_ori_dest() {
        return tipo_ori_dest;
    }

    /**
     * @return the codpresu
     */
    public String getCodpresu() {
        return codpresu;
    }

    /**
     * @return the partida
     */
    public String getPartida() {
        return partida;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

}
