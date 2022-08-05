package servicios;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp2.BasicDataSource;

// dbpool-7.0.jar, substance-4.3.jar
/**
 * Constructor de DbConnection
 */
public class Conn {

    private static final String server = "localhost";
    private static final String bd = "solo_pan";
    private static final String login = "root";
    private static final String password = "$%&/root";
    private static final String url = "jdbc:mysql://" + server + ":3306/" + bd;

    private static Connection conexion;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer el DataSource: " + System.getProperty("line.separator") + _ex);
            System.exit(1);
        }

        conexion = null;
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);
    }

    /**
     * Permite retornar la conexión
     *
     * @return n
     * @throws java.lang.Exception
     */
    public static Connection getConnection() throws Exception {
        try {
            if ((conexion == null) || (!conexion.isValid(0))) {
                conexion = dataSource.getConnection();
            }
        } catch (Exception inEx) {
            throw inEx;
        }

        return conexion;
    }

    /**
     * Permite retornar la conexión
     *
     * @throws java.lang.Exception
     */
    public static void CloseConnection() throws Exception {
        try {
            if ((conexion != null) && (conexion.isValid(0))) {
                conexion.close();
            }
        } catch (Exception inEx) {
            throw inEx;
        } finally {
            conexion = null;
        }

    }

    /**
     *
     * @throws Exception
     */
    public static void BeginTransaction() throws Exception {
        if ((conexion != null) && !conexion.getAutoCommit()) {
            throw new Exception("Error. Transacción en Proceso");
        }

        conexion = dataSource.getConnection();
        conexion.createStatement().execute("UNLOCK TABLES");
        conexion.setAutoCommit(false);
    }

    /**
     *
     * @throws Exception
     */
    public static void EndTransaction() throws Exception {
        try {
            if ((conexion != null) && (conexion.isValid(0))) {
                conexion.commit();
                conexion.setAutoCommit(true);
                conexion.createStatement().execute("UNLOCK TABLES");
                conexion.close();
            } else {
                throw new Exception("Error al tratar de cerrar la transacción");
            }
        } catch (Exception inEx) {
            throw inEx;
        } finally {
            conexion = null;
        }
    }

    /**
     *
     * @throws Exception
     */
    public static void RollBack() throws Exception {
        try {
            if ((conexion != null) && (conexion.isValid(0))) {
                conexion.rollback();
                conexion.setAutoCommit(true);
                conexion.createStatement().execute("UNLOCK TABLES");
                conexion.close();
            } else {
                throw new Exception("Error al tratar de retroceder la transacción");
            }
        } catch (Exception inEx) {
            throw inEx;
        } finally {
            conexion = null;
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
            if ((conexion == null) || (!conexion.isValid(0))) {
                conexion = dataSource.getConnection();
            }
        } catch (Exception inEx) {
            throw inEx;
        }

        return conexion.createStatement().executeQuery(_sql);
    }

    /**
     *
     * @param _sql
     * @return
     * @throws Exception
     */
    public static int executeUpdate(final String _sql) throws Exception {
        try {
            if ((conexion == null) || (!conexion.isValid(0))) {
                conexion = dataSource.getConnection();
            }
        } catch (Exception inEx) {
            throw inEx;
        }

        return conexion.createStatement().executeUpdate(_sql);
    }
}
