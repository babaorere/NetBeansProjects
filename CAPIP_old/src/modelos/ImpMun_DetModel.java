package modelos;

import capipsistema.Conn;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class ImpMun_DetModel {

    private final long id_imp_mun_det;
    private final long id_imp_mun;
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
    public ImpMun_DetModel(final ResultSet _rs) throws Exception {
        id_imp_mun_det = _rs.getLong("id_imp_mun_det");
        id_imp_mun = _rs.getLong("id_imp_mun");
        id_causado = _rs.getLong("id_causado");
        fecha_fact = _rs.getDate("fecha_fact");
        num_fact = _rs.getString("num_fact");
        tipo_compr = _rs.getString("tipo_compr");
        id_compr = _rs.getLong("id_compr");
        total_fact = _rs.getBigDecimal("total_fact");
        base_imponible = _rs.getBigDecimal("base_imponible");
        gravable_bs = _rs.getBigDecimal("gravable_bs");
        retenido_bs = _rs.getBigDecimal("retenido_bs");
        observacion = _rs.getString("observacion");
        ejefis = _rs.getDate("ejefis");
        benef_razonsocial = _rs.getString("benef_razonsocial");
        benef_rif_ci = _rs.getString("benef_rif_ci");
        anulado_sn = _rs.getString("anulado_sn");
    }

    @Override
    public String toString() {
        return String.valueOf(id_imp_mun_det);
    }

    /**
     * Rev 14-04-2017
     *
     * @param id_causado
     * @return
     * @throws Exception
     */
    public static ArrayList<ImpMun_DetModel> getxId_causado(final long id_causado) throws Exception {
        ArrayList<ImpMun_DetModel> list = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT * FROM imp_mun_det WHERE id_causado= " + id_causado);
        while (rs.next()) {
            list.add(new ImpMun_DetModel(rs));
        }

        return list;
    }

    /**
     * Rev 14-04-2017
     *
     * @param _id
     * @return
     * @throws Exception
     */
    public static ArrayList<ImpMun_DetModel> getxId_retencion(final long _id) throws Exception {
        ArrayList<ImpMun_DetModel> list = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT * FROM imp_mun_det WHERE id_imp_mun= " + _id);
        while (rs.next()) {
            list.add(new ImpMun_DetModel(rs));
        }

        return list;
    }

    /**
     * Rev 14-04-2017
     *
     * @param _id
     * @return
     * @throws Exception
     */
    public static ImpMun_DetModel getxId(final long _id) throws Exception {

        final ResultSet rs = Conn.executeQuery("SELECT * FROM imp_mun_det WHERE id_imp_mun_det= " + _id);
        if (rs.next()) {
            return new ImpMun_DetModel(rs);
        }

        return null;
    }

    /**
     * @return the id_islr_retencion_det
     */
    public long getId_imp_mun_det() {
        return id_imp_mun_det;
    }

    /**
     * @return the id_islr_retencion
     */
    public long getId_imp_mun() {
        return id_imp_mun;
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

}
