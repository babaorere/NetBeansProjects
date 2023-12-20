package modelos;

import connection.ConnCapip;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import utils.TipoCompr;

/**
 *
 * @author Capip Sistemas C.A.
 */
public class CauModel {

    private final long id_causado; // 1
    private final long id_user;
    private final long id_session;
    private final java.sql.Timestamp date_session;
    private final java.sql.Date ejefis;
    private final java.sql.Date fecha_causado;
    private final TipoCompr tipo_compr;
    private final String benef_razonsocial;
    private final String benef_rif_ci;
    private final String observacion;
    private final BigDecimal resta_bs;

    private final boolean iva;
    private final String iva_ret_sn;

    private final boolean islr;
    private final String islr_ret_sn;

    private final boolean imp_mun;
    private final String imp_mun_ret_sn;

    private final boolean neg_pri;
    private final String neg_pri_ret_sn;

    private final boolean is_oret;
    private final String oret_ret_sn;

    private final boolean uno_x_mil;
    private final String uno_x_mil_ret_sn;

    private final String anulado_sn;

    public CauModel(final ResultSet inrs) throws Exception {
        id_causado = inrs.getLong("id_causado");
        id_user = inrs.getLong("id_user");
        id_session = inrs.getLong("id_session");
        date_session = inrs.getTimestamp("date_session");
        ejefis = inrs.getDate("ejefis");

        fecha_causado = inrs.getDate("fecha_causado");
        tipo_compr = TipoCompr.valueOf( inrs.getString("tipo_compr"));
        benef_razonsocial = inrs.getString("benef_razonsocial");
        benef_rif_ci = inrs.getString("benef_rif_ci");
        observacion = inrs.getString("observacion");
        resta_bs = inrs.getBigDecimal("resta_bs");

        iva = inrs.getString("is_iva_sn").equals("S");
        iva_ret_sn = inrs.getString("iva_ret_sn");

        islr = inrs.getString("is_islr_sn").equals("S");
        islr_ret_sn = inrs.getString("islr_ret_sn");

        imp_mun = inrs.getString("is_imp_mun_sn").equals("S");
        imp_mun_ret_sn = inrs.getString("imp_mun_ret_sn");

        neg_pri = inrs.getString("is_neg_pri_sn").equals("S");
        neg_pri_ret_sn = inrs.getString("neg_pri_ret_sn");

        uno_x_mil = inrs.getString("is_uno_x_mil_sn").equals("S");
        uno_x_mil_ret_sn = inrs.getString("uno_x_mil_ret_sn");

        is_oret = inrs.getString("is_oret_sn").equals("S");
        oret_ret_sn = inrs.getString("oret_ret_sn");

        anulado_sn = inrs.getString("anulado_sn");
    }

    /**
     * Rev 22/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getTotal_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(subtotal) FROM causado_det WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 22/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getBase_imponible_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(base_imponible_bs) FROM " + tipo_compr.getTbl() + " WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 22/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getIva_grav_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(iva_grav_bs) FROM " + tipo_compr.getTbl() + " WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 22/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getIva_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(iva_grav_bs * iva_porc_aplic / 100.00) FROM " + tipo_compr.getTbl() + " WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 22/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getIva_ret_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT ROUND(SUM(iva_grav_bs * iva_porc_aplic / 100 * iva_porc_ret), 2) FROM " + tipo_compr.getTbl() + " WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 25/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getIslr_ret_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(islr_grav_bs * islr_porc_ret / 100.00) FROM " + tipo_compr.getTbl() + " WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 25/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getORet_ret_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(oret_bs) FROM " + tipo_compr.getTbl() + " WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 25/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getImpMun_ret_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(imp_mun_bs) FROM " + tipo_compr.getTbl() + " WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 25/10/2016
     *
     * @return
     * @throws Exception
     */
    public BigDecimal getNegPri_ret_bs() throws Exception {
        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(neg_pri_bs) FROM " + tipo_compr.getTbl() + " WHERE id_causado= " + id_causado).executeQuery();

        if (rs.next()) {
            BigDecimal aux = rs.getBigDecimal(1);
            if (aux != null) {
                return aux.setScale(2, RoundingMode.HALF_UP);
            }
        }

        throw new Exception("Causado no encontrado");
    }

    /**
     * Rev 21/10/2016
     *
     * @param inid
     * @return
     * @throws Exception
     */
    public static CauModel getxID(final long inid) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM causado WHERE id_causado= " + inid);
        if (rs.next()) {
            return new CauModel(rs);
        }

        return null;
    }

//    /**
//     * Rev 21/10/2016
//     *
//     * @param inid
//     * @return
//     * @throws Exception
//     */
//    public static BigDecimal getTotal_bsxId(final long inid) throws Exception {
//        final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(subtotal) FROM causado_det WHERE id_causado= " + inid).executeQuery();
//
//        if (rs.next()) {
//            BigDecimal aux = rs.getBigDecimal(1);
//            if (aux != null) {
//                return aux.setScale(2, RoundingMode.HALF_UP);
//            }
//        }
//        throw new Exception("Causado no encontrado");
//    }
    /**
     * Rev 21/10/2016
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(id_causado);
    }

    /**
     * @return the id_causado
     */
    public long getId_causado() {
        return id_causado;
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
    public java.sql.Timestamp getDate_session() {
        return date_session;
    }

    /**
     * @return the ejefis
     */
    public java.sql.Date getEjefis() {
        return ejefis;
    }

    /**
     * @return the fecha_causado
     */
    public java.sql.Date getFecha_causado() {
        return fecha_causado;
    }

    /**
     * @return the tipo_compr
     */
    public TipoCompr getTipo_compr() {
        return tipo_compr;
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
    public String getObs() {
        return observacion;
    }

    /**
     * @return the resta_bs
     */
    public BigDecimal getResta_bs() {
        return resta_bs;
    }

    /**
     * @return the iva_retenido_sn
     */
    public String getIva_ret_sn() {
        return iva_ret_sn;
    }

    /**
     * @return the islr_retenido_sn
     */
    public String getIslr_ret_sn() {
        return islr_ret_sn;
    }

    /**
     * @return the imp_mun_ret_sn
     */
    public String getImp_mun_ret_sn() {
        return imp_mun_ret_sn;
    }

    /**
     * @return the neg_pri_ret_sn
     */
    public String getNeg_pri_ret_sn() {
        return neg_pri_ret_sn;
    }

    /**
     * @return the oret_retenido_sn
     */
    public String getOret_ret_sn() {
        return oret_ret_sn;
    }

    /**
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }

    /**
     * @return the is_iva
     */
    public boolean isIva() {
        return iva;
    }

    /**
     * @return the is_islr
     */
    public boolean isIslr() {
        return islr;
    }

    /**
     * @return the is_imp_mun
     */
    public boolean isImp_mun() {
        return imp_mun;
    }

    /**
     * @return the is_neg_pri
     */
    public boolean isNeg_pri() {
        return neg_pri;
    }

    /**
     * @return the is_oret
     */
    public boolean isIs_oret() {
        return is_oret;
    }

    /**
     * @return the is_uno_x_mil
     */
    public boolean isUno_x_mil() {
        return uno_x_mil;
    }

    /**
     * @return the uno_x_mil_ret_sn
     */
    public String getUno_x_mil_ret_sn() {
        return uno_x_mil_ret_sn;
    }

}
