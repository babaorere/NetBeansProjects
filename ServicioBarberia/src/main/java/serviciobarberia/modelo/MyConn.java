package serviciobarberia.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Está en una clase que implementa varios métodos, para abstraer la funcionalidad asociada a una conexión a base de datos<br>
 * Usa la librería commons-dbcp2-2.9.0.jar, la cual consiste en administrar un pool de conexiones validas, <br>
 * con el beneficio colateral, de que el manejo en la estabilidad de la conexión recae sobre ella misma, <br>
 * y permite al programador desentenderse de este chequeo, y mejora el performace de la aplicación, en caso <br>
 * de haber problemas con la conexion, el Objeto la renueva o restaura, ya no es nuestro problema, <br>
 * lo cierto es que estamos confiados en la funcionalidad de esta conexion.
 *
 * La clase utiliza metodos estaticos, a los fines de evitar instanciar la clase, es decir no es necesario hacer un "new Connection". <br>
 * es mas practico y conveniente hacer un "Connection.getConnection()", donde "getConnection()" es u metodo estatico
 *
 * Hay que mencionar, que siempre, y en todo momento, abra una unica conexion disponible, esta es obtenida del <br>
 * pool de conexiones, y compartidas por todas instancias de clase (por ser "static"), <br>
 * esta funcionalidad es muy util para trabajar con transacciones, y varias tablas. <br>
 *
 *
 * @author Alejandra
 */
public class MyConn {

    // base de datos mysql
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SERVER = "localhost";
    private static final String DB_NAME = "servicio_barberia";
    private static final String LOGIN = "admindb";
    private static final String PASSWORD = "@admindb";

    private static final String URL = "jdbc:mysql://" + SERVER + ":3306/" + DB_NAME;

    private static Connection conn;
    private final static BasicDataSource dataSource; // par utilizar el Pool de conexiones

    /**
     * Default constructor
     */
    public MyConn() {
        super();
    }

    /**
     * Esta parte se utiliza para inicializar la conexion, es un bloque estatico, <br>
     * por lo que se ejecuta solo la primera vez que se instacia la clase, <br>
     * recordar que un bloque estatico, y variable statica, es comun a todas las instacias del objeto, por definicion.  <br>
     */
    static {

        try {
            // Verificar la existencia del driver de para mysql
            Class.forName(DRIVER);
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer el DataSource: " + System.getProperty("line.separator") + _ex);
            System.exit(1); // Poner fin a todo al programa/aplicacion
        }

        conn = null;
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(LOGIN);
        dataSource.setPassword(PASSWORD);
    }

    /**
     * Permite retornar la conexión, es unica para todas la instancias
     *
     * @return n
     */
    public static Connection getConnection() {
        try {
            if ((conn == null) || (!conn.isValid(0))) {
                conn = dataSource.getConnection(); // se obtiene una conexion, del pool de conexiones
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        }

        return conn;
    }

    /**
     * Como su nombre lo indica, cierra la conexion
     *
     */
    public static void CloseConnection() {
        try {
            if ((conn != null) && (conn.isValid(0))) {
                conn.close();
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        } finally {
            conn = null;
        }

    }

    /**
     *
     * @throws Exception
     */
    public static void BeginTransaction() throws Exception {
        if ((conn != null) && !conn.getAutoCommit()) {
            throw new Exception("Error. Transacción en Proceso");
        }

        conn = dataSource.getConnection();
        conn.createStatement().execute("UNLOCK TABLES");
        conn.setAutoCommit(false);
    }

    /**
     *
     * @throws Exception
     */
    public static void EndTransaction() throws Exception {
        try {
            if ((conn != null) && (conn.isValid(0))) {
                conn.commit();
                conn.setAutoCommit(true);
                conn.createStatement().execute("UNLOCK TABLES");
                conn.close();
            } else {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la transacción");
                System.exit(1);
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la transacción" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        } finally {
            conn = null;
        }
    }

    /**
     * Utilizado en operaciones transaccionales, donde se requiere anular los comandos ejecutados sobre la base de datos <br>
     * desde el momento de hacer un "BeginTransaction"  <br>
     *
     * @throws Exception
     */
    public static void RollBack() throws Exception {
        try {
            if ((conn != null) && (conn.isValid(0))) {
                conn.rollback();
                conn.setAutoCommit(true);
                conn.createStatement().execute("UNLOCK TABLES");
                conn.close();
            } else {
                JOptionPane.showMessageDialog(null, "Error al tratar de retroceder la transacción");
                System.exit(1);
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de retroceder la transacción" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        } finally {
            conn = null;
        }
    }

    /**
     * Prueba la conexion, y si esta ok, procede a ejecutar una intruccion del grupo QUERY
     *
     * @param _sql
     * @return
     * @throws Exception
     */
    public static ResultSet executeQuery(final String _sql) throws Exception {
        try {
            if ((conn == null) || (!conn.isValid(0))) {
                conn = dataSource.getConnection();
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        }

        return conn.createStatement().executeQuery(_sql);
    }

    /**
     * Prueba la conexion, y si esta ok, procede a ejecutar una intruccion del grupo UPDATE  <br>
     *
     * @param _sql
     * @return
     * @throws Exception
     */
    public static int executeUpdate(final String _sql) throws Exception {
        try {
            if ((conn == null) || (!conn.isValid(0))) {
                conn = dataSource.getConnection();
            }
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + _ex);
            System.exit(1);
        }

        return conn.createStatement().executeUpdate(_sql);
    }

}
