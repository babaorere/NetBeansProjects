/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018, 2018 ... 2023
 */
package com.principal.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 */
public class ConnCapip extends ConnImpl {

    static public final String DB_NAME = "gestion_v2023_0";

    static public final String USER = "root";

    static public final String PASSWORD = "$%&@root";

    /**
     */
    private ConnCapip() {
        super("com.mysql.cj.jdbc.Driver", "localhost", "3306", DB_NAME, USER, PASSWORD);
    }

    public static ConnCapip getInstance() {
        return ConnCapip.NewSingletonHolder.INSTANCE;
    }

    private static class NewSingletonHolder {

        private static final ConnCapip INSTANCE = new ConnCapip();
    }

    private static final Logger logger = LogManager.getLogger(ConnCapip.class);
}
