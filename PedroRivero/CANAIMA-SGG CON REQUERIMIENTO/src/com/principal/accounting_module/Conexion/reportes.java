package com.principal.accounting_module.Conexion;

import com.principal.capipsistema.FrmPrincipal;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class reportes {

    public void listarEmpresas() {
        try {
            Connection conn = ConnAccounting.getInstance().getConnection();
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Empresas Registradas", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);
            if (conn != null) {

                final Class aClass = FrmPrincipal.getInstance().getClass();
                final InputStream pathRpt = aClass.getResourceAsStream("/reportes/EmpresasRegistradas.jasper");
                if (pathRpt == null) {
                    JOptionPane.showMessageDialog(null, "Reporte de no encontrado");
                } else {
                    JasperPrint ver = JasperFillManager.fillReport(pathRpt, null, conn);
                    JasperViewer visor = new JasperViewer(ver, false);
                    dialog.getContentPane().add(visor.getContentPane());
                    dialog.setTitle("Empresas Registradas");
                    dialog.setVisible(true);
                    visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                }

            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
            logger.error(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void listarPlanCuenta(String codigo) {
        try {
            Connection conn = ConnAccounting.getInstance().getConnection();
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Plan de Cuentas", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);
            if (conn != null) {

                final Class aClass = FrmPrincipal.getInstance().getClass();
                final InputStream pathRpt = aClass.getResourceAsStream("/reportes/mh_PlanCuenta.jasper");
                if (pathRpt == null) {
                    JOptionPane.showMessageDialog(null, "Reporte de no encontrado");
                } else {
                    Map parametro = new HashMap();
                    parametro.put("emp", codigo);

                    JasperPrint ver = JasperFillManager.fillReport(pathRpt, parametro, conn);
                    JasperViewer visor = new JasperViewer(ver, false);
                    dialog.getContentPane().add(visor.getContentPane());
                    dialog.setTitle("Plan de Cuentas");
                    dialog.setVisible(true);
                    visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                }

            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
            logger.error(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void libro(String codigo, String fecha1, String fecha2) throws Exception {
        try {
            Connection conn = ConnAccounting.getInstance().getConnection();
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Libro Diario", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);
            if (conn != null) {
                final Class aClass = FrmPrincipal.getInstance().getClass();
                final InputStream pathRpt = aClass.getResourceAsStream("/reportes/mh_LibroDiario.jasper");
                if (pathRpt == null) {
                    JOptionPane.showMessageDialog(null, "Reporte de no encontrado");
                } else {
                    Map parametros = new HashMap();
                    parametros.put("cod", codigo);
                    parametros.put("fecha1", fecha1);
                    parametros.put("fecha2", fecha2);
                    JasperPrint ver = JasperFillManager.fillReport(pathRpt, parametros, conn);
                    JasperViewer visor = new JasperViewer(ver, false);
                    dialog.getContentPane().add(visor.getContentPane());
                    dialog.setTitle("Libro Diario");
                    dialog.setVisible(true);
                    visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                }

            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
            logger.error(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void mayor(String emp, String inicioFiscal, String mesAnterior, String mesActual, String fecha) {
        try {
            Connection conn = ConnAccounting.getInstance().getConnection();
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Mayor Analitico", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);
            if (conn != null) {
                final Class aClass = FrmPrincipal.getInstance().getClass();
                final InputStream pathRpt = aClass.getResourceAsStream("/reportes/mayorP2.jasper");
                if (pathRpt == null) {
                    JOptionPane.showMessageDialog(null, "Reporte de no encontrado");
                } else {
                    Map parametros = new HashMap();
                    parametros.put("emp", emp);
                    parametros.put("inicioFiscal", inicioFiscal);
                    parametros.put("mesAnterior", mesAnterior);
                    parametros.put("mesActual", mesActual);
                    parametros.put("fecha", fecha);
                    JasperPrint ver = JasperFillManager.fillReport(pathRpt, parametros, conn);
                    JasperViewer visor = new JasperViewer(ver, false);
                    dialog.getContentPane().add(visor.getContentPane());
                    dialog.setTitle("Mayor Analitico");
                    dialog.setVisible(true);
                    visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                }

            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
            logger.error(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void BalanceComp(String fecha1, String fecha2, String fecha, String empresa) throws Exception {
        try {
            Connection conn = ConnAccounting.getInstance().getConnection();
            JDialog dialog = new JDialog(new javax.swing.JFrame(), "Balance de Comprobacion", true);
            dialog.setSize(1000, 700);
            dialog.setLocationRelativeTo(null);
            if (conn != null) {
                final Class aClass = FrmPrincipal.getInstance().getClass();
                final InputStream pathRpt = aClass.getResourceAsStream("/reportes/mh_BalComp.jasper");
                if (pathRpt == null) {
                    JOptionPane.showMessageDialog(null, "Reporte de no encontrado");
                } else {
                    Map parametros = new HashMap();
                    parametros.put("fecha1", fecha1);
                    parametros.put("fecha2", fecha2);
                    parametros.put("fecha", fecha);
                    parametros.put("empresa", empresa);
                    JasperPrint ver = JasperFillManager.fillReport(pathRpt, parametros, conn);
                    JasperViewer visor = new JasperViewer(ver, false);
                    dialog.getContentPane().add(visor.getContentPane());
                    dialog.setTitle("Libro Diario");
                    dialog.setVisible(true);
                    visor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                }

            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
            logger.error(e);
        }
    }

    private static final Logger logger = LogManager.getLogger(reportes.class);
}
