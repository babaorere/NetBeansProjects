package Conexion;

import java.awt.HeadlessException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class reportes {

    public void listarEmpresas() {
        try {
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacontable", "root", "");
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Empresas Registradas", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);

            if (conn != null) {
                String ruta = "src//reportes//EmpresasRegistradas.jasper";
                JasperReport r = (JasperReport) JRLoader.loadObjectFromFile(ruta);
                JasperPrint ver = JasperFillManager.fillReport(r, null, conn);
                JasperViewer visor = new JasperViewer(ver, false);
                dialog.getContentPane().add(visor.getContentPane());
                dialog.setTitle("Empresas Registradas");
                dialog.setVisible(true);
                visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | HeadlessException | JRException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }

    public void listarPlanCuenta(String codigo) {
        try {
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacontable", "root", "");
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "src//reportes//Plan de Cuentas", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);
            if (conn != null) {
                String ruta = "src//reportes//mh_PlanCuenta.jasper";
                JasperReport r = (JasperReport) JRLoader.loadObjectFromFile(ruta);
                Map parametro = new HashMap();
                parametro.put("emp", codigo);
                JasperPrint ver = JasperFillManager.fillReport(r, parametro, conn);
                JasperViewer visor = new JasperViewer(ver, false);
                dialog.getContentPane().add(visor.getContentPane());
                dialog.setTitle("Plan de Cuentas");
                dialog.setVisible(true);
                visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | JRException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }

    public void libro(String codigo, String fecha1, String fecha2) throws Exception {
        try {
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacontable", "root", "");

            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Libro Diario", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);
            
            if (conn != null) {
                String ruta = "src//reportes//mh_LibroDiario.jasper";
                JasperReport r = (JasperReport) JRLoader.loadObjectFromFile(ruta);
                Map parametros = new HashMap();
                parametros.put("cod", codigo);
                parametros.put("fecha1", fecha1);
                parametros.put("fecha2", fecha2);
                JasperPrint ver = JasperFillManager.fillReport(r, parametros, conn);
                JasperViewer visor = new JasperViewer(ver, false);
                
                dialog.getContentPane().add(visor.getContentPane());
                dialog.setTitle("Libro Diario");
                dialog.setVisible(true);
                visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }

    public void mayor(String emp, String inicioFiscal, String mesAnterior, String mesActual, String fecha) {
        try {
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacontable", "root", "");

            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Mayor Analitico", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);
            if (conn != null) {
                String ruta = "mayorP2.jasper";
                JasperReport r = (JasperReport) JRLoader.loadObjectFromFile(ruta);
                Map parametros = new HashMap();
                parametros.put("emp", emp);
                parametros.put("inicioFiscal", inicioFiscal);
                parametros.put("mesAnterior", mesAnterior);
                parametros.put("mesActual", mesActual);
                parametros.put("fecha", fecha);
                JasperPrint ver = JasperFillManager.fillReport(r, parametros, conn);
                JasperViewer visor = new JasperViewer(ver, false);
                dialog.getContentPane().add(visor.getContentPane());
                dialog.setTitle("Mayor Analitico");
                dialog.setVisible(true);
                visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | HeadlessException | JRException e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }

    }

    public void BalanceComp(String fecha1, String fecha2, String fecha, String empresa) throws Exception {
        try {
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemacontable", "root", "");
            
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Balance de Comprobacion", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);

            if (conn != null) {
                String ruta = "src//reportes//mh_BalComp.jasper";
                JasperReport r = (JasperReport) JRLoader.loadObjectFromFile(ruta);
                Map parametros = new HashMap();
                parametros.put("fecha1", fecha1);
                parametros.put("fecha2", fecha2);
                parametros.put("fecha", fecha);
                parametros.put("empresa", empresa);
                JasperPrint ver = JasperFillManager.fillReport(r, parametros, conn);
                JasperViewer visor = new JasperViewer(ver, false);
                
                dialog.getContentPane().add(visor.getContentPane());
                dialog.setTitle("Libro Diario");
                dialog.setVisible(true);
                visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
}
