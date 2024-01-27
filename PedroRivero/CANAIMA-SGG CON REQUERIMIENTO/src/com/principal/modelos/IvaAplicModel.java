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
public final class IvaAplicModel {

    public static IvaAplicModel getxID(long inid) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM iva_aplicado WHERE id_iva_aplicado= " + inid);
        if (rs.next()) {
            return new IvaAplicModel(rs);
        }
        return null;
    }

    // indice primario (pk)
    private final long id_iva_aplicado;

    private final long id_part_ppto;

    // mantiene el valor, como un porcentaje, ej. 12,00%
    private final BigDecimal valor_porc;

    public IvaAplicModel(final ResultSet inrs) throws Exception {
        id_iva_aplicado = inrs.getLong("id_iva_aplicado");
        id_part_ppto = inrs.getLong("id_part_ppto");
        valor_porc = inrs.getBigDecimal("valor_porc");
    }

    @Override
    public String toString() {
        return Format.toStr(valor_porc) + "%";
    }

    /**
     * @return the id_iva_aplicado
     */
    public long getId_iva_aplicado() {
        return id_iva_aplicado;
    }

    /**
     * @return the id_part_ppto
     */
    public long getId_part_ppto() {
        return id_part_ppto;
    }

    /**
     * @return the porc_aplicado
     */
    public BigDecimal getValor_porc() {
        return valor_porc;
    }

    private static final Logger logger = LogManager.getLogger(IvaAplicModel.class);
}
