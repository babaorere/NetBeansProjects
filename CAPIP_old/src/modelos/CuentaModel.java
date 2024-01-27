/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package modelos;

import capipsistema.Conn;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author Baba
 */
public class CuentaModel {

    private final long id_banco;
    private final String cuenta;
    private final String banco;
    private final BigDecimal saldoi;

    public CuentaModel(ResultSet _rs) throws Exception {
        this.id_banco = _rs.getLong("id");
        this.cuenta = _rs.getString("cuenta");
        this.banco = _rs.getString("banco");
        this.saldoi = _rs.getBigDecimal("saldoi");
    }

    /**
     * Rev 21/10/2016
     *
     * @param _id
     * @return
     * @throws Exception
     */
    public static CuentaModel getxID(final long _id) throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos WHERE id= " + _id);
        if (rs.next()) {
            return new CuentaModel(rs);
        }

        return null;
    }

    /**
     * Rev 21/10/2016
     *
     * @param _cuenta
     * @return
     * @throws Exception
     */
    public static CuentaModel getxCuenta(final String _cuenta) throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos WHERE cuenta= '" + _cuenta + "'");
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

}
