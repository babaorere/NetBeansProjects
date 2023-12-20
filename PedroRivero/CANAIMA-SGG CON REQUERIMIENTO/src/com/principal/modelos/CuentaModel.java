/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package com.principal.modelos;

import com.principal.connection.ConnCapip;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Baba
 */
public class CuentaModel {

    private final long id_banco;

    private final String cuenta;

    private final String banco;

    private final BigDecimal saldoi;

    public CuentaModel(ResultSet inrs) throws Exception {
        this.id_banco = inrs.getLong("id");
        this.cuenta = inrs.getString("cuenta");
        this.banco = inrs.getString("banco");
        this.saldoi = inrs.getBigDecimal("saldoi");
    }

    /**
     * Rev 21/10/2016
     *
     * @param inid
     * @return
     * @throws Exception
     */
    public static CuentaModel getxID(final long inid) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM bancos WHERE id= " + inid);
        if (rs.next()) {
            return new CuentaModel(rs);
        }
        return null;
    }

    /**
     * Rev 21/10/2016
     *
     * @param incuenta
     * @return
     * @throws Exception
     */
    public static CuentaModel getxCuenta(final String incuenta) throws Exception {
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM bancos WHERE cuenta= '" + incuenta + "'");
        if (rs.next()) {
            return new CuentaModel(rs);
        }
        return null;
    }

    /**
     * @return the id_banco
     */
    public long getId_banco() {
        return id_banco;
    }

    /**
     * @return the id_banco
     */
    public long getId_cuenta() {
        return id_banco;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @return the saldoi
     */
    public BigDecimal getSaldoI() {
        return saldoi;
    }

    private static final Logger logger = LogManager.getLogger(CuentaModel.class);
}
