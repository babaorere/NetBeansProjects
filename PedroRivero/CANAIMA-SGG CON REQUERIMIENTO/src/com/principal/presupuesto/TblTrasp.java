/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.presupuesto;

import java.math.BigDecimal;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
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

    public static TblTrasp getReg_x_Id(long inid_trasp) throws Exception {
        TblTrasp aux;
        final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM trasppartidas WHERE id_trasp= '" + inid_trasp + "'");
        if (rs.next()) {
            aux = new TblTrasp(rs);
        } else {
            aux = null;
        }
        return aux;
    }

    public TblTrasp(final ResultSet inrs) throws Exception {
        id_trasp = inrs.getLong("id_trasp");
        id_user = inrs.getLong("id_user");
        id_session = inrs.getLong("id_session");
        date_session = inrs.getTimestamp("date_session");
        ppto_egr_ing = inrs.getString("ppto_egr_ing");
        referencia = inrs.getString("referencia");
        fecha = inrs.getDate("fecha");
        monto = inrs.getBigDecimal("monto");
        concepto = inrs.getString("concepto");
        ejefis = inrs.getLong("ejefis");
        ejefismes = inrs.getLong("ejefismes");
        anulado_sn = inrs.getString("anulado_sn");
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

    private static final Logger logger = LogManager.getLogger(TblTrasp.class);
}
