package com.principal.modelos;

import com.principal.connection.ConnCapip;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
public class PagoModel implements PagAvaInterface {

    private final long id_orden_pago;

    private final long id_user;

    private final long id_session;

    private final java.sql.Timestamp date_session;

    private final java.sql.Date ejefis;

    private final long id_cuenta;

    private final long num_x_pag;

    private final long num_x_cuenta;

    private final String benef_razonsocial;

    private final String benef_rif_ci;

    private final java.sql.Date fecha_pago;

    private final String observacion;

    private final String banco;

    private final String cuenta;

    private final String cheque;

    private final java.sql.Date fecha_cheque;

    private final String endosable_sn;

    private final BigDecimal total_bs;

    private final BigDecimal a_pagar_bs;

    private final BigDecimal iva_bs;

    private final BigDecimal resta_bs;

    private final BigDecimal iva_ret_bs;

    private final BigDecimal islr_ret_bs;

    private final BigDecimal imp_mun_ret_bs;

    private final BigDecimal neg_pri_ret_bs;

    private final BigDecimal otras_ret_bs;

    private final String anulado_sn;

    public PagoModel(final ResultSet inrs) throws Exception {
        id_orden_pago = inrs.getLong("id_orden_pago");
        id_user = inrs.getLong("id_user");
        id_session = inrs.getLong("id_session");
        date_session = inrs.getTimestamp("date_session");
        ejefis = inrs.getDate("ejefis");
        id_cuenta = inrs.getLong("id_cuenta");
        num_x_pag = inrs.getLong("num_x_pag");
        num_x_cuenta = inrs.getLong("num_x_cuenta");
        benef_razonsocial = inrs.getString("benef_razonsocial");
        benef_rif_ci = inrs.getString("benef_rif_ci");
        fecha_pago = inrs.getDate("fecha_pago");
        observacion = inrs.getString("observacion");
        banco = inrs.getString("banco");
        cuenta = inrs.getString("cuenta");
        cheque = inrs.getString("cheque");
        fecha_cheque = inrs.getDate("fecha_cheque");
        endosable_sn = inrs.getString("endosable_sn");
        total_bs = inrs.getBigDecimal("total_bs");
        a_pagar_bs = inrs.getBigDecimal("apagar_bs");
        iva_bs = inrs.getBigDecimal("iva_bs");
        resta_bs = inrs.getBigDecimal("resta_bs");
        iva_ret_bs = inrs.getBigDecimal("iva_ret_bs");
        islr_ret_bs = inrs.getBigDecimal("islr_ret_bs");
        imp_mun_ret_bs = inrs.getBigDecimal("imp_mun_ret_bs");
        neg_pri_ret_bs = inrs.getBigDecimal("neg_pri_ret_bs");
        otras_ret_bs = inrs.getBigDecimal("otras_ret_bs");
        anulado_sn = inrs.getString("anulado_sn");
    }

    @Override
    public String toString() {
        return String.valueOf(id_orden_pago);
    }

    /**
     * Retorna verdadero si el Pago fue realizado con cheque
     *
     * @param inid
     * @return
     * @throws Exception
     */
    public static boolean isByCheck(final long inid) throws Exception {
        // Verificar que la orden de pago existe
        try (final PreparedStatement pstPago = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM orden_pago " + "WHERE id_orden_pago= ?")) {
            pstPago.setLong(1, inid);
            try (final ResultSet rsPago = pstPago.executeQuery()) {
                if (rsPago.next()) {
                    // Buscar e la tabla de bancos, la forma en que fue realizado el pago
                    try (final PreparedStatement pstBanco = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM bancos_operaciones " + "WHERE (tipo_operacion = 'ND' OR tipo_operacion = 'CH') AND num_pag_ava= ?")) {
                        pstBanco.setLong(1, inid);
                        final ResultSet rsBanco = pstBanco.executeQuery();
                        if (rsBanco.next()) {
                            return rsBanco.getString("tipo_operacion").equals("CH");
                        } else {
                            return false;
                            //                            throw new Exception("Error, registro bancario del Pago no encontrado");
                        }
                    }
                } else {
                    throw new Exception("Error, registro no encontrado");
                }
            }
        }
    }

    /**
     * Retorna verdadero si el Pago fue realizado con cheque
     *
     * @return
     * @throws Exception
     */
    public boolean isByCheck() throws Exception {
        return isByCheck(id_orden_pago);
    }

    /**
     * @return the id_orden_pago
     */
    public long getId_orden_pago() {
        return id_orden_pago;
    }

    /**
     * @return the id_orden_pago
     */
    @Override
    public long getId() {
        return id_orden_pago;
    }

    /**
     * @return the id_user
     */
    @Override
    public long getId_user() {
        return id_user;
    }

    /**
     * @return the id_session
     */
    @Override
    public long getId_session() {
        return id_session;
    }

    /**
     * @return the date_session
     */
    @Override
    public java.sql.Timestamp getDate_session() {
        return date_session;
    }

    /**
     * @return the ejefis
     */
    @Override
    public java.sql.Date getEjefis() {
        return ejefis;
    }

    /**
     * @return the id_cuenta
     */
    public long getId_cuenta() {
        return id_cuenta;
    }

    /**
     * @return the id_rel
     */
    public long getNum_x_Pag() {
        return num_x_pag;
    }

    /**
     * @return the id_rel
     */
    public long getNum_x_Cuenta() {
        return num_x_cuenta;
    }

    /**
     * @return the benef_razonsocial
     */
    @Override
    public String getBenef_razonsocial() {
        return benef_razonsocial;
    }

    /**
     * @return the benef_rif_ci
     */
    @Override
    public String getBenef_rif_ci() {
        return benef_rif_ci;
    }

    /**
     * @return the fecha_pago
     */
    @Override
    public java.sql.Date getFecha_pago() {
        return fecha_pago;
    }

    /**
     * @return the observacion
     */
    @Override
    public String getObservacion() {
        return observacion;
    }

    /**
     * @return the banco
     */
    @Override
    public String getBanco() {
        return banco;
    }

    /**
     * @return the cuenta
     */
    @Override
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @return the cheque
     */
    @Override
    public String getCheque() {
        return cheque;
    }

    /**
     * @return the fecha_cheque
     */
    @Override
    public java.sql.Date getFecha_cheque() {
        return fecha_cheque;
    }

    /**
     * @return the endosable_sn
     */
    public String getEndosable_sn() {
        return endosable_sn;
    }

    /**
     * @return the total_bs
     */
    public BigDecimal getTotal_bs() {
        return total_bs;
    }

    /**
     * @return the a_pagar_bs
     */
    @Override
    public BigDecimal getA_pagar_bs() {
        return a_pagar_bs;
    }

    /**
     * @return the iva_bs
     */
    public BigDecimal getIva_bs() {
        return iva_bs;
    }

    /**
     * @return the resta_bs
     */
    public BigDecimal getResta_bs() {
        return resta_bs;
    }

    /**
     * @return the iva_ret_bs
     */
    public BigDecimal getIva_ret_bs() {
        return iva_ret_bs;
    }

    /**
     * @return the islr_ret_bs
     */
    public BigDecimal getIslr_ret_bs() {
        return islr_ret_bs;
    }

    /**
     * @return the otras_ret_bs
     */
    public BigDecimal getOtras_ret_bs() {
        return otras_ret_bs;
    }

    /**
     * @return the anulado_sn
     */
    public String getAnulado_sn() {
        return anulado_sn;
    }

    private static final Logger logger = LogManager.getLogger(PagoModel.class);
}
