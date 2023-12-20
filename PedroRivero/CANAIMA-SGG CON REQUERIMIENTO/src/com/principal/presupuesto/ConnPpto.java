/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.presupuesto;

import com.principal.connection.ConnCapip;
import java.sql.Connection;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utileria para realizar Start/Stop de la BD
 *
 * Se utiliza el patron Sigleton, "solo puede haber uno"
 */
public final class ConnPpto {

    public static final Connection conn;

    static {
        conn = ConnCapip.getInstance().getConnection();
    }

    /**
     * Detiene la conexion
     *
     * @param sql
     * @return
     * @throws java.lang.Exception
     */
    public static Boolean execute(String sql) throws Exception {
        return conn.createStatement().execute(sql);
    }

    public static final ResultSet executeQuery(String sql) throws Exception {
        return conn.prepareStatement(sql).executeQuery();
    }

    public static int executeUpdate(String sql) throws Exception {
        return conn.prepareStatement(sql).executeUpdate();
    }

    public static void BeginTransaction() throws Exception {
        ConnPpto.conn.setAutoCommit(false);
    }

    public static void EndTransaction() throws Exception {
        ConnPpto.conn.commit();
        ConnPpto.conn.setAutoCommit(true);
    }

    public static void RollBack() throws Exception {
        ConnPpto.conn.rollback();
        ConnPpto.conn.setAutoCommit(true);
    }

    private static final Logger logger = LogManager.getLogger(ConnPpto.class);
}
