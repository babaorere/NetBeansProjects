/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package modelos;

import capipsistema.Conn;
import java.math.BigDecimal;
import java.sql.ResultSet;
import utils.Format;

/**
 *
 * @author Capip Sistemas C.A.
 *
 * ImpAlValor
 */
public final class UTModel {

    /**
     * Rev 04/10/2016
     *
     * @param _id
     * @return
     * @throws Exception
     */
    public static UTModel getxID(long _id) throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT * FROM unidad_tributaria_aplic WHERE id_unidad_tributaria_aplic=" + _id);
        if (rs.next()) {
            return new UTModel(rs);
        }

        return null;
    }

    private final long id_unidad_tributaria_aplic; // indice primario (pk)
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final String descripcion;
    private final java.sql.Date fecha_vigencia;
    private final BigDecimal valor_bs; // mantiene el valor, en Bs.

    /**
     * Rev 04/10/2016
     *
     * @param _rs
     * @throws Exception
     */
    public UTModel(final ResultSet _rs) throws Exception {
        id_unidad_tributaria_aplic = _rs.getLong("id_unidad_tributaria_aplic");
        id_user = _rs.getLong("id_user");
        id_session = _rs.getLong("id_session");
        date_session = _rs.getTimestamp("date_session");
        descripcion = _rs.getString("descripcion");
        fecha_vigencia = _rs.getDate("fecha_vigencia");
        valor_bs = _rs.getBigDecimal("valor_bs");
    }

    @Override
    public String toString() {
        return Format.toStr(valor_bs);
    }

    /**
     * @return the id_unidad_tributaria_aplic
     */
    public long getId_unidad_tributaria_aplic() {
        return id_unidad_tributaria_aplic;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the fecha_vigencia
     */
    public java.sql.Date getFecha_vigencia() {
        return fecha_vigencia;
    }

    /**
     * @return the valor_bs
     */
    public BigDecimal getValor_bs() {
        return valor_bs;
    }

}
