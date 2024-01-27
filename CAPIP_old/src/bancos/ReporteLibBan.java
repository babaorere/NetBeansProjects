package bancos;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class ReporteLibBan extends javax.swing.JFrame {

    private final java.awt.Window parent;

    public static String cuenta;

    /**
     * Creates new form ccompromisos
     *
     * @param _parent
     */
    public ReporteLibBan(java.awt.Window _parent) {
        super();
        initComponents();

        parent = _parent;

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     *
     * @param _b
     */
    @Override
    public void setVisible(boolean _b) {
        // Para mostrar la ventana en el tope de la pantalla
        if (_b) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }

        super.setVisible(_b);
    }

    /**
     * Establece el comportamiento de la presente Ventana
     * Rev 21/09/2016
     *
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        setTitle(CapipPropiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        // Establecer acción al cerrar ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                actSalir();
            }
        });

        // Para salir con la tecla ESC
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");

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
            Statement st = Conn.getConnection().createStatement();
            final ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object nombreCompleto = rs.getObject("cuenta");
                nombreCompleto += " " + rs.getObject("banco");
                modeloCombo.addElement(nombreCompleto);
            }
            cbxCuentas.setModel(modeloCombo);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    @SuppressWarnings("unchecked")
    public void ver_orden(String numorden, String _NumLiteral, String SumaTotal) {
        try {
            final Map<String, Object> param = new HashMap<>(101);
            param.clear();
            param.put("Numorden", numorden);

            // EnLetras
            final String NumLiteral_1;
            final String NumLiteral_2;

            if (_NumLiteral.length() < 65) {
                NumLiteral_1 = _NumLiteral;
                NumLiteral_2 = "";
            } else {
                int pos = _NumLiteral.lastIndexOf(" CON ");
                final String s1 = _NumLiteral.substring(0, pos);
                if (s1.length() > 65) {
                    pos = s1.lastIndexOf(" ") + 1;
                }

                NumLiteral_1 = _NumLiteral.substring(0, pos).trim();
                NumLiteral_2 = _NumLiteral.substring(pos).trim();
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
                            JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), cn), false);
                        } catch (final Exception _ex) {
                            JOptionPane.showMessageDialog(null, _ex);
                        }
                    }
                }.start();
            } else {
                JOptionPane.showMessageDialog(null, "Reporte no encontrado");
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

        jLabel1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INTRODUZCA  NUMERO  DE CUENTA");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 350, -1));

        btnSalir.setBackground(new java.awt.Color(153, 153, 0));
        btnSalir.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(51, 51, 51));
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 110, 40));

        btnAceptar.setBackground(new java.awt.Color(153, 153, 0));
        btnAceptar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 110, 40));

        cbxCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCuentasActionPerformed(evt);
            }
        });
        getContentPane().add(cbxCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 370, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 210));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String Total = "";
        String numorden = cuenta;
        String EnLetras = "";
        ver_orden(numorden, EnLetras, Total);
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void cbxCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCuentasActionPerformed
        String Mensaje = cbxCuentas.getSelectedItem().toString();
        cuenta = Mensaje.substring(0, 20);
    }//GEN-LAST:event_cbxCuentasActionPerformed

    /**
     * Rev 25/11/2016
     *
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
    public static void main(String args[]) {

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

    Connection cn = Conn.getConnection();
}
