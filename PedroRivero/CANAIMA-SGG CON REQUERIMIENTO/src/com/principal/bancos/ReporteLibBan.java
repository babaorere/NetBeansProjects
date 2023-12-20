package com.principal.bancos;

import com.principal.capipsistema.FrmPrincipal;
import com.principal.capipsistema.Globales;
import com.principal.capipsistema.Propiedades;
import com.principal.capipsistema.UserPassIn;
import com.principal.capipsistema.UserTrack;
import com.principal.connection.ConnCapip;
import com.principal.modelos.UserModel;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
public final class ReporteLibBan extends javax.swing.JFrame {

    private final java.awt.Window parent;

    public static String cuenta;

    /**
     * Creates new form ccompromisos
     *
     * @param inparent
     */
    public ReporteLibBan(java.awt.Window inparent) {
        super();
        initComponents();
        parent = inparent;
        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     *
     * @param inb
     */
    @Override
    public void setVisible(boolean inb) {
        // Para mostrar la ventana en el tope de la pantalla
        if (inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }
        super.setVisible(inb);
    }

    /**
     * Establece el comportamiento de la presente Ventana
     * Rev 21/09/2016
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        setTitle(Propiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + Propiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        // Establecer acción al cerrar ventana
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                actSalir();
            }
        });
        // Para salir con la tecla ESC
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actSalir();
            }
        });
    }

    /**
     * Rev /10/2016
     */
    private void setCompBehavior() {
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        cargarComboCuentas();
    }

    @SuppressWarnings("unchecked")
    void cargarComboCuentas() {
        DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
        String sql = "SELECT * FROM bancos";
        try {
            Statement st = ConnCapip.getInstance().getConnection().createStatement();
            final ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object nombreCompleto = rs.getObject("cuenta");
                nombreCompleto += " " + rs.getObject("banco");
                modeloCombo.addElement(nombreCompleto);
            }
            cbxCuentas.setModel(modeloCombo);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
    }

    @SuppressWarnings("unchecked")
    public void ver_orden(String numorden, String inNumLiteral, String SumaTotal) {
        try {
            final Map<String, Object> param = new HashMap<>(101);
            param.clear();
            param.put("Numorden", numorden);
            // EnLetras
            final String NumLiteral_1;
            final String NumLiteral_2;
            if (inNumLiteral.length() < 65) {
                NumLiteral_1 = inNumLiteral;
                NumLiteral_2 = "";
            } else {
                int pos = inNumLiteral.lastIndexOf(" CON ");
                final String s1 = inNumLiteral.substring(0, pos);
                if (s1.length() > 65) {
                    pos = s1.lastIndexOf(" ") + 1;
                }
                NumLiteral_1 = inNumLiteral.substring(0, pos).trim();
                NumLiteral_2 = inNumLiteral.substring(pos).trim();
            }
            param.put("NumLiteral_1", NumLiteral_1);
            param.put("NumLiteral_2", NumLiteral_2);
            param.put("logo", this.getClass().getResourceAsStream("/imagenes/logo.jpg"));
            param.put("iduser", UserPassIn.getIdUser());
            param.put("idsession", UserPassIn.getIdSession());
            param.put("user", UserPassIn.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(UserPassIn.getIdUser()));
            param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
            final Class aClass = FrmPrincipal.getInstance().getClass();
            param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
            param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));
            final InputStream pathRpt = aClass.getResourceAsStream("/reportes/auxiliar bancario.jasper");
            if (pathRpt != null) {
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            JasperViewer.viewReport(JasperFillManager.fillReport(pathRpt, new HashMap<>(param), cn), false);
                        } catch (final Exception inex) {
                            JOptionPane.showMessageDialog(null, inex);
                            logger.error(inex);
                        }
                    }
                }.start();
            } else {
                JOptionPane.showMessageDialog(null, "Reporte no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        cbxCuentas = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("REPORTE LIBRO BANCARIO");
        setMinimumSize(new java.awt.Dimension(500, 200));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Arial", 3, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INTRODUZCA  NUMERO  DE CUENTA");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 350, -1));
        btnSalir.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnSalir.setFont(new java.awt.Font("Arial", 2, 14));
        btnSalir.setForeground(new java.awt.Color(51, 51, 51));
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 110, 40));
        btnAceptar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnAceptar.setFont(new java.awt.Font("Arial", 2, 14));
        btnAceptar.setText("Aceptar");
        btnAceptar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 110, 40));
        cbxCuentas.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        cbxCuentas.setFont(new java.awt.Font("Arial", 2, 16));
        cbxCuentas.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCuentasActionPerformed(evt);
            }
        });
        getContentPane().add(cbxCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 370, 30));
        jLabel2.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png")));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 210));
        pack();
        setLocationRelativeTo(null);
    }

    // </editor-fold>//GEN-END:initComponents
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }

    //GEN-LAST:event_btnSalirActionPerformed
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnAceptarActionPerformed
        String Total = "";
        String numorden = cuenta;
        String EnLetras = "";
        ver_orden(numorden, EnLetras, Total);
    }

    //GEN-LAST:event_btnAceptarActionPerformed
    private void cbxCuentasActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_cbxCuentasActionPerformed
        String Mensaje = cbxCuentas.getSelectedItem().toString();
        cuenta = Mensaje.substring(0, 20);
    }

    //GEN-LAST:event_cbxCuentasActionPerformed
    /**
     * Rev 25/11/2016
     */
    private void actSalir() {
        if (parent != null) {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    parent.setVisible(true);
                    dispose();
                }
            });
        } else {
            System.exit(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ReporteLibBan(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;

    private javax.swing.JButton btnSalir;

    private javax.swing.JComboBox cbxCuentas;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel2;

    // End of variables declaration//GEN-END:variables
    Connection cn = ConnCapip.getInstance().getConnection();

    private static final Logger logger = LogManager.getLogger(ReporteLibBan.class);
}
