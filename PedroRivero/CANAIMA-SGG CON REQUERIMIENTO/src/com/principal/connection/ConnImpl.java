package com.principal.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 */
public class ConnImpl implements Conn {

    private final String DRIVER;

    private final String SERVER;

    private final String PORT;

    private final String DB;

    private final String USER;

    private final String PASSWORD;

    private final String URL;

    private final BasicDataSource dataSource;

    private Connection conn;

    public ConnImpl(String inDRIVER, String inSERVER, String inPORT, String inDB, String inUSER, String inPASSWORD) {
        this.DRIVER = inDRIVER;
        this.SERVER = inSERVER;
        this.PORT = inPORT;
        this.DB = inDB;
        this.USER = inUSER;
        this.PASSWORD = inPASSWORD;
        this.URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DB;
        try {
            // Verificar la existencia del driver para mysql
            Class.forName(DRIVER);
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer el DataSource:\n" + inex);
            System.exit(1);
            logger.error(inex);
        }
        conn = null;
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
    }

    @Override
    public Connection getConnection() {
        try {
            if ((conn == null) || (!conn.isValid(0))) {
                conn = dataSource.getConnection();
            }
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión:\n" + inex);
            System.exit(1);
            logger.error(inex);
        }
        return conn;
    }

    @Override
    public void CloseConnection() throws Exception {
        try {
            if ((conn != null) && (conn.isValid(0))) {
                conn.close();
            }
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión:\n" + inex);
            System.exit(1);
            logger.error(inex);
        } finally {
            conn = null;
        }
    }

    @Override
    public void BeginTransaction() throws Exception {
        if ((conn != null) && !conn.getAutoCommit()) {
            throw new Exception("Error. Transacción en Proceso");
        }
        conn = dataSource.getConnection();
        conn.createStatement().execute("UNLOCK TABLES");
        conn.setAutoCommit(false);
    }

    @Override
    public void EndTransaction() throws Exception {
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
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la transacción" + System.getProperty("line.separator") + inex);
            System.exit(1);
            logger.error(inex);
        } finally {
            conn = null;
        }
    }

    @Override
    public void RollBack() throws Exception {
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
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de retroceder la transacción" + System.getProperty("line.separator") + inex);
            System.exit(1);
            logger.error(inex);
        } finally {
            conn = null;
        }
    }

    @Override
    public ResultSet executeQuery(String insql) throws Exception {
        try {
            if ((conn == null) || (!conn.isValid(0))) {
                conn = dataSource.getConnection();
            }
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + inex);
            System.exit(1);
            logger.error(inex);
        }
        return conn.createStatement().executeQuery(insql);
    }

    @Override
    public int executeUpdate(String insql) throws Exception {
        try {
            if ((conn == null) || (!conn.isValid(0))) {
                conn = dataSource.getConnection();
            }
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de establecer una conexión" + System.getProperty("line.separator") + inex);
            System.exit(1);
            logger.error(inex);
        }
        return conn.createStatement().executeUpdate(insql);
    }

    // ************************************************************************
    // This part corresponds to the accounting module
    @Override
    public void ejecutar(String sql) {
        Statement stm;
        try {
            stm = getConnection().createStatement();
            stm.executeUpdate(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            logger.error(e);
        }
    }

    @Override
    public void eliminar(String sql) {
        Statement stm;
        try {
            stm = getConnection().createStatement();
            stm.executeUpdate(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            logger.error(e);
        }
    }

    @Override
    public ResultSet buscar(String sql) {
        Statement stm;
        ResultSet rSet;
        try {
            stm = getConnection().createStatement();
            rSet = stm.executeQuery(sql);
            return rSet;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            logger.error(e);
        }
        return null;
    }

    /**
     * Metodo que me ayuda a buscar un dato
     *
     * @param tabla nombre de la tabla en la bd
     * @param campoVerificacion campo a verificar en la consulta
     * @param datoVerificar Dato a averificar en la consulta
     * @param limite el limite de datos para la consulta
     * @return boolean
     */
    @Override
    public boolean existeDato(String tabla, String campoVerificacion, String datoVerificar, int limite) {
        Statement stm;
        ResultSet rSet;
        boolean b = false;
        String query = "SELECT " + campoVerificacion + " FROM " + tabla + " WHERE " + campoVerificacion + "='" + datoVerificar + "' LIMIT " + limite;
        System.out.print(query + "\n");
        try {
            stm = getConnection().createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.last()) {
                b = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logger.error(e);
        }
        return b;
    }

    //Metodo que ayuda a buscar dos datos en una tabla sql
    public boolean existeDato2(String tabla, String campo1, String campo2, String dato1, String dato2, int limite) {
        Statement stm;
        ResultSet rSet;
        boolean b = false;
        String query = "SELECT " + campo1 + "," + campo2 + " FROM " + tabla + " WHERE " + campo1 + "='" + dato1 + "' AND " + campo2 + "='" + dato2 + "'  LIMIT " + limite;
        System.out.print(query + "\n");
        try {
            stm = getConnection().createStatement();
            rSet = stm.executeQuery(query);
            if (rSet.last()) {
                b = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logger.error(e);
        }
        return b;
    }

    //Metodo que ayuda a buscar tres datos en una tabla sql
    public boolean existeDato3(String tabla, String campo1, String campo2, String campo3, String dato1, String dato2, String dato3, int limite) {
        Statement stm;
        ResultSet rSet;
        boolean b = false;
        String query = "SELECT " + campo1 + "," + campo2 + "," + campo3 + " FROM " + tabla + " WHERE " + campo1 + "='" + dato1 + "' AND " + campo2 + "='" + dato2 + "' AND " + campo3 + "='" + dato3 + "'  LIMIT " + limite;
        System.out.print(query + "\n");
        try {
            stm = getConnection().createStatement();
            rSet = stm.executeQuery(query);
            if (rSet.last()) {
                b = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logger.error(e);
        }
        return b;
    }

    /**
     * @return the DRIVER
     */
    public String getDRIVER() {
        return DRIVER;
    }

    /**
     * @return the SERVER
     */
    public String getSERVER() {
        return SERVER;
    }

    /**
     * @return the PORT
     */
    public String getPORT() {
        return PORT;
    }

    /**
     * @return the DB
     */
    public String getDB() {
        return DB;
    }

    /**
     * @return the USER
     */
    public String getUSER() {
        return USER;
    }

    /**
     * @return the PASSWORD
     */
    public String getPASSWORD() {
        return PASSWORD;
    }

    /**
     * @return the URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @return the dataSource
     */
    public BasicDataSource getDataSource() {
        return dataSource;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return getConnection();
    }

    private static final Logger logger = LogManager.getLogger(ConnImpl.class);
}
