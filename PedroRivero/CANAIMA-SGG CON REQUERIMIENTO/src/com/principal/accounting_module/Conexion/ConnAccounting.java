package com.principal.accounting_module.Conexion;

import com.principal.connection.ConnCapip;
import com.principal.connection.ConnImpl;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 */
public class ConnAccounting extends ConnImpl {

    /**
     */
    private ConnAccounting() {
        super("com.mysql.cj.jdbc.Driver", "localhost", "3306", "sistema_contable", ConnCapip.USER, ConnCapip.PASSWORD);
    }

    public static ConnAccounting getInstance() {
        return ConnAccounting.NewSingletonHolder.INSTANCE;
    }

    public ResultSet buscar(String inSql) {
        System.out.println("\ncom.principal.accounting_module.Conexion.ConnAccounting buscar Not supported yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static class NewSingletonHolder {

        private static final ConnAccounting INSTANCE = new ConnAccounting();
    }

    private static final Logger logger = LogManager.getLogger(ConnAccounting.class);
}
