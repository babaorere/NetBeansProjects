package Conexion;

import java.sql.*;
import javax.swing.*;

public class BaseDatos {

    private Connection con;
    private Statement stm, stmt;
    private ResultSet rSet;

    public BaseDatos() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Drivers no encontrado");
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacontable", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO CONECTA B.D.");
        }
    }

    public void ejecutar(String sql) {
        try {
            stm = con.createStatement();
            stm.executeUpdate(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void eliminar (String sql){
        try{
           stm=con.createStatement();
            stm.executeUpdate(sql);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public ResultSet buscar(String sql) {
        try {
            stmt = con.createStatement();
            rSet = stmt.executeQuery(sql);
            return rSet;
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, ee);
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
     *
     */
    public boolean existeDato(String tabla, String campoVerificacion, String datoVerificar, int limite) {
        boolean b = false;
        ResultSet rs = null;
        String query = "SELECT " + campoVerificacion + " FROM " + tabla + " WHERE " + campoVerificacion + "='" + datoVerificar + "' LIMIT " + limite;
        System.out.print(query + "\n");
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.last()) {
                b = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return b;
    }

    //Metodo que ayuda a buscar dos datos en una tabla sql 
    public boolean existeDato2(String tabla, String campo1, String campo2, String dato1, String dato2, int limite) {
        boolean b = false;
        ResultSet rs = null;
        String query = "SELECT " + campo1 + "," + campo2 + " FROM " + tabla + " WHERE " + campo1 + "='" + dato1 + "' AND " + campo2 + "='" + dato2 + "'  LIMIT " + limite;
        System.out.print(query + "\n");
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.last()) {
                b = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return b;
    }

    //Metodo que ayuda a buscar tres datos en una tabla sql 
    public boolean existeDato3(String tabla, String campo1, String campo2, String campo3, String dato1, String dato2, String dato3, int limite) {
        boolean b = false;
        ResultSet rs = null;
        String query = "SELECT " + campo1 + "," + campo2 + "," + campo3 + " FROM " + tabla + " WHERE " + campo1 + "='" + dato1 + "' AND " + campo2 + "='" + dato2 + "' AND " + campo3 + "='" + dato3 + "'  LIMIT " + limite;
        System.out.print(query + "\n");
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.last()) {
                b = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return b;
    }

    /**
     * Metodo que me ayuda a buscar un dato
     *
     * @param query Sentencia sql para la verificacion del dato
     * @return boolean
     *
     */
    public boolean existeDato(String query) {
        boolean b = false;
        ResultSet rs = null;
        System.out.print(query + "\n");
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.last()) {
                b = true;
            }
            while (rs.next()) {
                System.out.print("1 \n");
            };
        } catch (Exception e) {

        }
        return b;
    }
}