/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package com.principal.modelos;

import com.principal.connection.ConnCapip;
import com.principal.utils.Format;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 *
 * ImpAlValor
 */
public final class UTModel {

    /**
     * Rev 04/10/2016
     *
     * @param inid
     * @return
     * @throws Exception
     */
    public static UTModel getxID(long inid) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM unidad_tributaria_aplic WHERE id_unidad_tributaria_aplic=" + inid);
        if (rs.next()) {
            return new UTModel(rs);
        }
        return null;
    }

    // indice primario (pk)
    private final long id_unidad_tributaria_aplic;

    private final long id_user;

    private final long id_session;

    private final java.sql.Timestamp date_session;

    private final String descripcion;

    private final java.sql.Date fecha_vigencia;

    // mantiene el valor, en Bs.
    private final BigDecimal valor_bs;

    /**
     * Rev 04/10/2016
     *
     * @param inrs
     * @throws Exception
     */
    public UTModel(final ResultSet inrs) throws Exception {
        id_unidad_tributaria_aplic = inrs.getLong("id_unidad_tributaria_aplic");
        id_user = inrs.getLong("id_user");
        id_session = inrs.getLong("id_session");
        date_session = inrs.getTimestamp("date_session");
        descripcion = inrs.getString("descripcion");
        fecha_vigencia = inrs.getDate("fecha_vigencia");
        valor_bs = inrs.getBigDecimal("valor_bs");
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

    private static final Logger logger = LogManager.getLogger(UTModel.class);
}
