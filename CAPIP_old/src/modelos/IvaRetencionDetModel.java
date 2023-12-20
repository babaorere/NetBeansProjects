package modelos;

import capipsistema.Conn;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class IvaRetencionDetModel {

    private final long id_iva_retencion_det;
    private final long id_iva_retencion;
    private final long id_causado;
    private final java.sql.Date fecha_fact;
    private final String num_fact;
    private final long id_compr;
    private final String ndebito;
    private final String ncredito;
    private final String transaccion;
    private final String factura_aft;
    private final BigDecimal total_fact;
    private final BigDecimal exento;
    private final BigDecimal base_imponible;
    private final BigDecimal iva_porc_aplic;
    private final BigDecimal iva_bs;
    private final BigDecimal iva_retenido;
    private final String observacion;
    private final java.sql.Date ejefis;
    private final String benef_razonsocial;
    private final String benef_rif_ci;
    private final String anulado_sn;

    public IvaRetencionDetModel(final ResultSet _rs) throws Exception {
        id_iva_retencion_det = _rs.getLong("id_iva_retencion_det");
        id_iva_retencion = _rs.getLong("id_iva_retencion");
        id_causado = _rs.getLong("id_causado");
        fecha_fact = _rs.getDate("fecha_fact");
        num_fact = _rs.getString("num_fact");
        id_compr = _rs.getLong("id_compr");
        ndebito = _rs.getString("ndebito");
        ncredito = _rs.getString("ncredito");
        transaccion = _rs.getString("transaccion");
        factura_aft = _rs.getString("factura_aft");
        total_fact = _rs.getBigDecimal("total_fact");
        exento = _rs.getBigDecimal("exento");
        base_imponible = _rs.getBigDecimal("base_imponible");
        iva_porc_aplic = _rs.getBigDecimal("iva_porc_aplic");
        iva_bs = _rs.getBigDecimal("iva_bs");
        iva_retenido = _rs.getBigDecimal("iva_retenido");
        observacion = _rs.getString("observacion");
        ejefis = _rs.getDate("ejefis");
        benef_razonsocial = _rs.getString("benef_razonsocial");
        benef_rif_ci = _rs.getString("benef_rif_ci");
        anulado_sn = _rs.getString("anulado_sn");
    }

    @Override
    public String toString() {
        return String.valueOf(id_iva_retencion_det);
    }

    /**
     * @return the id_iva_retencion_det
     */
    public long getId_iva_retencion_det() {
        return id_iva_retencion_det;
    }

    /**
     * @return the id_iva_retencion
     */
    public long getId_iva_retencion() {
        return id_iva_retencion;
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
     * @return the id_compr
     */
    public long getId_compr() {
        return id_compr;
    }

    /**
     * @return the ndebito
     */
    public String getNdebito() {
        return ndebito;
    }

    /**
     * @return the ncredito
     */
    public String getNcredito() {
        return ncredito;
    }

    /**
     * @return the transaccion
     */
    public String getTransaccion() {
        return transaccion;
    }

    /**
     * @return the factura_aft
     */
    public String getFactura_aft() {
        return factura_aft;
    }

    /**
     * @return the total_fact
     */
    public BigDecimal getTotal_fact() {
        return total_fact;
    }

    /**
     * @return the exento
     */
    public BigDecimal getExento() {
        return exento;
    }

    /**
     * @return the base_imponible
     */
    public BigDecimal getBase_imponible() {
        return base_imponible;
    }

    /**
     * @return the iva_porc_aplic
     */
    public BigDecimal getIva_porc_aplic() {
        return iva_porc_aplic;
    }

    /**
     * @return the iva_bs
     */
    public BigDecimal getIva_bs() {
        return iva_bs;
    }

    /**
     * @return the iva_retenido
     */
    public BigDecimal getIva_retenido() {
        return iva_retenido;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Rev 19/10/2016
     *
     * @param id_causado
     * @return
     * @throws Exception
     */
    public static ArrayList<IvaRetencionDetModel> getxId_causado(final long id_causado) throws Exception {
        ArrayList<IvaRetencionDetModel> list = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT * FROM iva_retencion_det WHERE id_causado= " + id_causado);
        while (rs.next()) {
            list.add(new IvaRetencionDetModel(rs));
        }

        return list;
    }

    /**
     * Rev 19/10/2016
     *
     * @param _id
     * @return
     * @throws Exception
     */
    public static ArrayList<IvaRetencionDetModel> getxId_retencion(final long _id) throws Exception {
        ArrayList<IvaRetencionDetModel> list = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT * FROM iva_retencion_det WHERE id_iva_retencion= " + _id);
        while (rs.next()) {
            list.add(new IvaRetencionDetModel(rs));
        }

        return list;
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
