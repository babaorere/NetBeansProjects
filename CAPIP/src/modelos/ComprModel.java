/*
 * Todos los derechos reservados por CAPIP C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package modelos;

import capipsistema.Conn;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import utils.TipoCompr;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class ComprModel {

    private final long id_compromiso;
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final java.sql.Date ejefis;
    private final long id_causado;
    private final java.sql.Date fecha_compr;
    private final String benef_razonsocial;
    private final String benef_rif_ci;
    private final String observacion;
    private final String num_fact;
    private String num_control;
    private final java.sql.Date fecha_fact;
    private final BigDecimal total_bs;
    private final BigDecimal base_imponible_bs;
    private final BigDecimal iva_grav_bs;
    private final BigDecimal iva_porc_aplic;
    private final BigDecimal iva_porc_ret;
    private final BigDecimal islr_grav_bs;
    private final BigDecimal islr_porc_ret;
    private final BigDecimal imp_mun_grav_bs;
    private final BigDecimal imp_mun_bs;
    private final BigDecimal neg_pri_grav_bs;
    private final BigDecimal neg_pri_bs;
    private final BigDecimal oret_grav_bs;
    private final BigDecimal oret_bs;
    private final String anulado_sn;

    /**
     * Rev 15/09/2016
     *
     * @param _rs
     * @throws Exception
     */
    public ComprModel(final ResultSet _rs) throws Exception {
        id_compromiso = _rs.getLong("id_compr");
        id_user = _rs.getLong("id_user");
        id_session = _rs.getLong("id_session");
        date_session = _rs.getTimestamp("date_session");
        ejefis = _rs.getDate("ejefis");
        id_causado = _rs.getLong("id_causado");
        fecha_compr = _rs.getDate("fecha_compr");
        benef_razonsocial = _rs.getString("benef_razonsocial");
        benef_rif_ci = _rs.getString("benef_rif_ci");
        observacion = _rs.getString("observacion");
        num_fact = _rs.getString("num_fact");
        num_control = _rs.getString("num_control");
        fecha_fact = _rs.getDate("fecha_fact");
        total_bs = _rs.getBigDecimal("total_bs");
        base_imponible_bs = _rs.getBigDecimal("base_imponible_bs");
        iva_grav_bs = _rs.getBigDecimal("iva_grav_bs");
        iva_porc_aplic = _rs.getBigDecimal("iva_porc_aplic");
        iva_porc_ret = _rs.getBigDecimal("iva_porc_ret");
        islr_grav_bs = _rs.getBigDecimal("islr_grav_bs");
        islr_porc_ret = _rs.getBigDecimal("islr_porc_ret");
        imp_mun_grav_bs = _rs.getBigDecimal("imp_mun_grav_bs");
        imp_mun_bs = _rs.getBigDecimal("imp_mun_bs");
        neg_pri_grav_bs = _rs.getBigDecimal("neg_pri_grav_bs");
        neg_pri_bs = _rs.getBigDecimal("neg_pri_bs");
        oret_grav_bs = _rs.getBigDecimal("oret_grav_bs");
        oret_bs = _rs.getBigDecimal("oret_bs");
        anulado_sn = _rs.getString("anulado_sn");
    }

    /**
     *
     * @param _id
     * @param _tipoCompr
     * @return
     * @throws Exception
     */
    public static ComprModel getxID(final long _id, final TipoCompr _tipoCompr) throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT * FROM " + _tipoCompr.getTbl() + " WHERE id_compr=" + _id);
        if (rs.next()) {
            return new ComprModel(rs);
        }

        return null;
    }

    /**
     * Rev 21/10/2016
     *
     * @param _id_causado
     * @param _tipoCompr
     * @return
     * @throws Exception
     */
    public static ArrayList<ComprModel> getxIdCausado(final long _id_causado, final TipoCompr _tipoCompr) throws Exception {

        ArrayList<ComprModel> list = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT * FROM " + _tipoCompr.getTbl() + " WHERE id_causado= " + _id_causado);
        while (rs.next()) {
            list.add(new ComprModel(rs));
        }

        return list;
    }

    /**
     * Rev 15/09/206
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(id_compromiso);
    }

    /**
     * @return the id_compr
     */
    public long getId_compromiso() {
        return id_compromiso;
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
     * @return the fecha_session
     */
    public Timestamp getDate_session() {
        return date_session;
    }

    /**
     * @return the id_causado
     */
    public long getId_causado() {
        return id_causado;
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
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @return the numfact
     */
    public String getNumFact() {
        return num_fact;
    }

    /**
     * @return the fechafact
     */
    public java.sql.Date getFechaFact() {
        return fecha_fact;
    }

    /**
     * @return the total_bs
     */
    public BigDecimal getTotal_bs() {
        return total_bs;
    }

    /**
     * @return the base_imponible_bs
     */
    public BigDecimal getBase_imponible_bs() {
        return base_imponible_bs;
    }

    /**
     * @return the iva_grav_bs
     */
    public BigDecimal getIva_grav_bs() {
        return iva_grav_bs;
    }

    /**
     * @return the iva_porc_aplic
     */
    public BigDecimal getIva_porc_aplic() {
        return iva_porc_aplic;
    }

    /**
     * @return the islr_grav_bs
     */
    public BigDecimal getIslr_grav_bs() {
        return islr_grav_bs;
    }

    /**
     * @return the islr_porc_aplic
     */
    public BigDecimal getIslr_porc_ret() {
        return islr_porc_ret;
    }

    /**
     * @return the ejefis
     */
    public java.sql.Date getEjefis() {
        return ejefis;
    }

    /**
     * @return the fecha_compr
     */
    public java.sql.Date getFecha_compr() {
        return fecha_compr;
    }

    /**
     * @return the num_fact
     */
    public String getNum_fact() {
        return num_fact;
    }

    /**
     * @return the fecha_fact
     */
    public java.sql.Date getFecha_fact() {
        return fecha_fact;
    }

    /**
     * @return the iva_porc_ret
     */
    public BigDecimal getIva_porc_ret() {
        return iva_porc_ret;
    }

    /**
     * @return the oret_grav_bs
     */
    public BigDecimal getOret_grav_bs() {
        return oret_grav_bs;
    }

    /**
     * @return the oret_ret_bs
     */
    public BigDecimal getOret_bs() {
        return oret_bs;
    }

    /**
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }

    /**
     * @return the imp_mun_grav_bs
     */
    public BigDecimal getImp_mun_grav_bs() {
        return imp_mun_grav_bs;
    }

    /**
     * @return the imp_mun_bs
     */
    public BigDecimal getImp_mun_bs() {
        return imp_mun_bs;
    }

    /**
     * @return the neg_pri_grav_bs
     */
    public BigDecimal getNeg_pri_grav_bs() {
        return neg_pri_grav_bs;
    }

    /**
     * @return the neg_pri_bs
     */
    public BigDecimal getNeg_pri_bs() {
        return neg_pri_bs;
    }

    /**
     * @return the num_control
     */
    public String getNum_control() {
        return num_control;
    }

    /**
     * @param num_control the num_control to set
     */
    public void setNum_control(String num_control) {
        this.num_control = num_control;
    }

}
