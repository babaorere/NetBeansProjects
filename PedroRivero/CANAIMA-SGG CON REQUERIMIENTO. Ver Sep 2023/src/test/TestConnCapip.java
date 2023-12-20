package test;

import accounting_module.Conexion.ConnAccounting;
import connection.Conn;
import connection.ConnCapip;
import java.sql.Connection;

/**
 *
 */
public class TestConnCapip {

    public static void main(String[] args) {

        Conn conn = ConnCapip.getInstance();
        try {
            if (conn != null) {
                System.out.println("CAPIP Conn => " + conn);

                Connection connection = conn.getConnection();

                System.out.println("CAPIP Connection => " + connection);

            } else {
                System.out.println("CAPIP Conn => NULL");
            }

        } catch (Exception ex) {
            System.out.println("CAPIP. Error in the conection");
        }

        conn = ConnAccounting.getInstance();
        try {
            if (conn != null) {
                System.out.println("ACCO Conn => " + conn);

                Connection connection = conn.getConnection();

                System.out.println("ACCO Connection => " + connection);

            } else {
                System.out.println("ACCO Conn => NULL");
            }

        } catch (Exception ex) {
            System.out.println("ACCO. Error in the conection");
        }

    }

}
