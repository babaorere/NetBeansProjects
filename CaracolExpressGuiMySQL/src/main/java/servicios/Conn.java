package servicios;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp2.BasicDataSource;


/**
 * Constructor de DbConnection
 */
public class Conn {

    private static final String server = "localhost";
    private static final String bd = "bd_encomiendas";
    private static final String login = "root";
    private static final String password = "$%&/root";
    private static final String url = "jdbc:mysql://" + server + ":3306/" + bd;

    private static Connection conectar;
    private final static BasicDataSource dataSource;


    /**
     * Default constructor
     */
    public Conn() {
        super();
    }


    static {

        try {
            // Verificar la existencia del driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer el DataSource: " + System.getProperty("line.separator") + _ex);
            System.exit(1);
        }

        conectar = null;
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);
    }


    /**
     * Permite retornar la conexión
     *
     * @return n
     */
    public static Connection getConnection() {
        try {
            if ((conectar == null) || (!conectar.isValid(0))) {
                conectar = dataSource.getConnection();
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        }

        return conectar;
    }


    /**
     * Permite retornar la conexión
     *
     */
    public static void CloseConnection() {
        try {
            if ((conectar != null) && (conectar.isValid(0))) {
                conectar.close();
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        } finally {
            conectar = null;
        }

    }


    /**
     *
     * @throws Exception
     */
    public static void BeginTransaction() throws Exception {
        if ((conectar != null) && !conectar.getAutoCommit()) {
            throw new Exception("Error. Transacción en Proceso");
        }

        conectar = dataSource.getConnection();
        conectar.createStatement().execute("UNLOCK TABLES");
        conectar.setAutoCommit(false);
    }


    /**
     *
     * @throws Exception
     */
    public static void EndTransaction() throws Exception {
        try {
            if ((conectar != null) && (conectar.isValid(0))) {
                conectar.commit();
                conectar.setAutoCommit(true);
                conectar.createStatement().execute("UNLOCK TABLES");
                conectar.close();
            } else {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la transacción");
                System.exit(1);
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la transacción" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        } finally {
            conectar = null;
        }
    }


    /**
     *
     * @throws Exception
     */
    public static void RollBack() throws Exception {
        try {
            if ((conectar != null) && (conectar.isValid(0))) {
                conectar.rollback();
                conectar.setAutoCommit(true);
                conectar.createStatement().execute("UNLOCK TABLES");
                conectar.close();
            } else {
                JOptionPane.showMessageDialog(null, "Error al tratar de retroceder la transacción");
                System.exit(1);
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de retroceder la transacción" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        } finally {
            conectar = null;
        }
    }


    /**
     *
     * @param _sql
     * @return
     * @throws Exception
     */
    public static ResultSet executeQuery(final String _sql) throws Exception {
        try {
            if ((conectar == null) || (!conectar.isValid(0))) {
                conectar = dataSource.getConnection();
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        }

        return conectar.createStatement().executeQuery(_sql);
    }


    /**
     *
     * @param _sql
     * @return
     * @throws Exception
     */
    public static int executeUpdate(final String _sql) throws Exception {
        try {
            if ((conectar == null) || (!conectar.isValid(0))) {
                conectar = dataSource.getConnection();
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        }

        return conectar.createStatement().executeUpdate(_sql);
    }
}
