package com.principal.modelos;

import com.principal.connection.ConnCapip;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
public final class NegPri_DetModel {

    private final long id_neg_pri_det;

    private final long id_neg_pri;

    private final long id_causado;

    private final java.sql.Date fecha_fact;

    private final String num_fact;

    private final String tipo_compr;

    private final long id_compr;

    private final BigDecimal total_fact;

    private final BigDecimal base_imponible;

    private final BigDecimal gravable_bs;

    private final BigDecimal retenido_bs;

    private final String observacion;

    private final java.sql.Date ejefis;

    private final String benef_razonsocial;

    private final String benef_rif_ci;

    private final String anulado_sn;

    /*
     * Rev 14-04-2017
     *
     */
    public NegPri_DetModel(final ResultSet inrs) throws Exception {
        id_neg_pri_det = inrs.getLong("id_neg_pri_det");
        id_neg_pri = inrs.getLong("id_neg_pri");
        id_causado = inrs.getLong("id_causado");
        fecha_fact = inrs.getDate("fecha_fact");
        num_fact = inrs.getString("num_fact");
        tipo_compr = inrs.getString("tipo_compr");
        id_compr = inrs.getLong("id_compr");
        total_fact = inrs.getBigDecimal("total_fact");
        base_imponible = inrs.getBigDecimal("base_imponible");
        gravable_bs = inrs.getBigDecimal("gravable_bs");
        retenido_bs = inrs.getBigDecimal("retenido_bs");
        observacion = inrs.getString("observacion");
        ejefis = inrs.getDate("ejefis");
        benef_razonsocial = inrs.getString("benef_razonsocial");
        benef_rif_ci = inrs.getString("benef_rif_ci");
        anulado_sn = inrs.getString("anulado_sn");
    }

    @Override
    public String toString() {
        return String.valueOf(id_neg_pri_det);
    }

    /**
     * Rev 14-04-2017
     *
     * @param id_causado
     * @return
     * @throws Exception
     */
    public static ArrayList<NegPri_DetModel> getxId_causado(final long id_causado) throws Exception {
        ArrayList<NegPri_DetModel> list = new ArrayList<>();
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM neg_pri_det WHERE id_causado= " + id_causado);
        while (rs.next()) {
            list.add(new NegPri_DetModel(rs));
        }
        return list;
    }

    /**
     * Rev 14-04-2017
     *
     * @param inid
     * @return
     * @throws Exception
     */
    public static ArrayList<NegPri_DetModel> getxId_retencion(final long inid) throws Exception {
        ArrayList<NegPri_DetModel> list = new ArrayList<>();
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM neg_pri_det WHERE id_neg_pri= " + inid);
        while (rs.next()) {
            list.add(new NegPri_DetModel(rs));
        }
        return list;
    }

    /**
     * Rev 14-04-2017
     *
     * @param inid
     * @return
     * @throws Exception
     */
    public static NegPri_DetModel getxId(final long inid) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM neg_pri_det WHERE id_neg_pri_det= " + inid);
        if (rs.next()) {
            return new NegPri_DetModel(rs);
        }
        return null;
    }

    /**
     * @return the id_neg_pri_det
     */
    public long getId_neg_pri_det() {
        return id_neg_pri_det;
    }

    /**
     * @return the id_neg_pri
     */
    public long getId_neg_pri() {
        return id_neg_pri;
    }

    /**
     * @return the id_causado
     */
    public long getId_causado() {
        return id_causado;
    }

    /**
     * @return the fecha_fact
     */
    public java.sql.Date getFecha_fact() {
        return fecha_fact;
    }

    /**
     * @return the num_fact
     */
    public String getNum_fact() {
        return num_fact;
    }

    /**
     * @return the tipo_compr
     */
    public String getTipo_compr() {
        return tipo_compr;
    }

    /**
     * @return the id_compr
     */
    public long getId_compr() {
        return id_compr;
    }

    /**
     * @return the total_fact
     */
    public BigDecimal getTotal_fact() {
        return total_fact;
    }

    /**
     * @return the base_imponible
     */
    public BigDecimal getBase_imponible() {
        return base_imponible;
    }

    /**
     * @return the gravable_bs
     */
    public BigDecimal getGravable_bs() {
        return gravable_bs;
    }

    /**
     * @return the islr_retenido_bs
     */
    public BigDecimal getRetenido_bs() {
        return retenido_bs;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
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
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }

    private static final Logger logger = LogManager.getLogger(NegPri_DetModel.class);
}
