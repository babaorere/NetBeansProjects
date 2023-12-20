/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018, 2018 ... 2023
 */
package connection;

/**
 */
public class ConnCapip extends ConnImpl {

    /**
     */
    private ConnCapip() {
        super("com.mysql.cj.jdbc.Driver", "localhost", "3306", "sistema_gestion", "root", "$%&@root");
    }

    public static ConnCapip getInstance() {
        return ConnCapip.NewSingletonHolder.INSTANCE;
    }

    private static class NewSingletonHolder {

        private static final ConnCapip INSTANCE = new ConnCapip();
    }

}
