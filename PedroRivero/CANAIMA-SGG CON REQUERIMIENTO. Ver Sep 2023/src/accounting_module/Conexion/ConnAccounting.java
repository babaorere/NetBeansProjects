package accounting_module.Conexion;

import connection.ConnImpl;

/**
 */
public class ConnAccounting extends ConnImpl {

    /**
     */
    private ConnAccounting() {
        super("com.mysql.cj.jdbc.Driver", "localhost", "3306", "sistema_contable", "root", "$%&@root");
    }

    public static ConnAccounting getInstance() {
        return ConnAccounting.NewSingletonHolder.INSTANCE;
    }

    private static class NewSingletonHolder {

        private static final ConnAccounting INSTANCE = new ConnAccounting();
    }

}
