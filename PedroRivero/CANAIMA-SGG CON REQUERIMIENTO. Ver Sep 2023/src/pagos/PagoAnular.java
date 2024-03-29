/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package pagos;

import capipsistema.CapipPropiedades;
import connection.ConnCapip;
import capipsistema.Globales;
import capipsistema.UserTrack;
import compromisos.Compromiso;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelos.CauModel;
import modelos.ComprDetModel;
import modelos.ComprModel;
import modelos.CuentaModel;
import modelos.PagoModel;
import static modelos.PresupeModel.anuComprPttoE;
import utils.CuentaUpdatable;
import utils.Format;
import utils.OrdPagIdTipo;
import utils.TipoCompr;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class PagoAnular extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    final private java.awt.Window parent;
    final CuentaModel regCuenta;

    /**
     * Creates new form compromisos
     *
     * @param inparent
     */
    public PagoAnular(final java.awt.Window inparent) {
        super();
        initComponents();

        parent = inparent;

        if ((Object) parent instanceof CuentaUpdatable) {
            regCuenta = ((CuentaUpdatable) parent).getCuenta();
        } else {
            regCuenta = null;
        }

        if ((regCuenta != null) && (Globales.ORD_PAG == OrdPagIdTipo.RELATIVO)) {
            lblCuenta.setText(regCuenta.getCuenta());
        } else {
            lblCuenta.setText("");
        }

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada Rev 25/09/2016
     *
     * @param inb
     */
    @Override
    public void setVisible(boolean inb) {
        // Para mostrar la ventana en el tope de la pantalla
        if ( inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }

        super.setVisible( inb);
    }

    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
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

    private void setCompBehavior() {
    }

    private void setStartConditions() {

        txtNum.requestFocusInWindow();
    }

    /**
     * Rev 16/09/2016
     *
     */
    private void actSalir() {
        final java.awt.Window me = this;
        if (parent != null) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    parent.setVisible(true);
                    me.dispose();
                }
            });
        } else {
            System.exit(0);
        }
    }

    /**
     *
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtNum = new javax.swing.JTextField();
        chkMostrarReporte = new javax.swing.JCheckBox();
        btnAceptar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblCuenta = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ANULACIÓN DE ORDEN DE PAGO");
        setMinimumSize(new java.awt.Dimension(519, 230));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INTRODUZCA  NÚMERO  DE ORDEN DE PAGO");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 25, 520, 40));

        txtNum.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNum.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumActionPerformed(evt);
            }
        });
        txtNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumKeyTyped(evt);
            }
        });
        getContentPane().add(txtNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 156, 41));

        chkMostrarReporte.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chkMostrarReporte.setSelected(true);
        chkMostrarReporte.setText("Mostrar Reporte");
        getContentPane().add(chkMostrarReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 120, -1, -1));

        btnAceptar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnAceptar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnAceptar.setText("ACEPTAR");
        btnAceptar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 110, 50));

        btnSalir.setBackground(java.awt.SystemColor.inactiveCaption);
        btnSalir.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 110, 50));

        lblCuenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCuenta.setPreferredSize(new java.awt.Dimension(0, 25));
        getContentPane().add(lblCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 5, 510, -1));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 230));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Rev 15/09/2016
     *
     * @param evt
     */
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        actAnular();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void actAnular() {

        final String sNum = txtNum.getText().trim();

        final long num;
        try {
            num = Format.toLong(sNum);
            if (num <= 0) {
                return;
            }
        } catch (final Exception ex) {
            txtNum.requestFocusInWindow();
            return;
        }

        boolean genReporte = chkMostrarReporte.isSelected();

        final String sqlQuery;
        if (regCuenta == null) {
            sqlQuery = "SELECT * FROM orden_pago WHERE YEAR(ejefis) = " + Globales.getEjeFisYear() + " AND num_x_pag = " + num;
        } else {
            sqlQuery = "SELECT * FROM orden_pago WHERE YEAR(ejefis) = " + Globales.getEjeFisYear() + " AND id_cuenta = " + regCuenta.getId_cuenta() + " AND num_x_cuenta = " + num;
        }

        try {

            // Verificar la existencia de la orden
            final ResultSet rsPago = ConnCapip.getInstance().executeQuery(sqlQuery);
            if (rsPago.next()) {

                final PagoModel reg = new PagoModel(rsPago);

                try {
                    if (genReporte) {
                        PagoOrden.genReport(reg.getId_orden_pago(), "ORDEN DE PAGO", false, reg.isByCheck());
                    }
                } catch (final Exception inex) {
                    JOptionPane.showMessageDialog(this, "Error al generar el reporte" + System.getProperty("line.separator") + inex);
                    return;
                }

                if (reg.getAnulado_sn().equals("S")) {
                    JOptionPane.showMessageDialog(this, "Este registro ya se encuentra anulado");
                    txtNum.requestFocusInWindow();
                    return;
                }

                // Reversar la Operacion, y anular el Pago
                if (JOptionPane.showConfirmDialog(this, "Esta operación es irreversible. Desea anular el registro ?",
                        "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    try {

                        ConnCapip.getInstance().BeginTransaction();

                        // Anular Orden de Pago
                        try (final PreparedStatement pstPago = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE orden_pago SET anulado_sn= 'S' "
                                + "WHERE anulado_sn = 'N' AND id_orden_pago= ?")) {
                            pstPago.setLong(1, reg.getId_orden_pago());
                            if (pstPago.executeUpdate() != 1) {
                                throw new Exception("Error al tratar de actualizar el Registro");
                            }
                        }

                        // Anular bancos_operaciones
                        try (final PreparedStatement pstBancos = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE bancos_operaciones SET anulado_sn= 'S' "
                                + "WHERE anulado_sn = 'N' AND (tipo_operacion = 'ND' OR tipo_operacion = 'CH') "
                                + "AND num_pag_ava= ?")) {
                            pstBancos.setLong(1, reg.getId_orden_pago());
                            if (pstBancos.executeUpdate() > 1) { // Hay ordenes sin operacion en Banco, pago directo
                                throw new Exception("Error al tratar de anular la operación en Bancos");
                            }
                        }

                        // Buscar todos los pagos que son afectados por la anulacion
                        try (final PreparedStatement pstCauPag = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado_orden_pago WHERE id_orden_pago= ?")) {
                            pstCauPag.setLong(1, reg.getId_orden_pago());
                            try (final ResultSet rsCauPag = pstCauPag.executeQuery()) {
                                while (rsCauPag.next()) {
                                    final long id_causado = rsCauPag.getLong("id_causado");
                                    final BigDecimal apagar_bs = rsCauPag.getBigDecimal("apagar_bs");

                                    // Hallar el causado
                                    final CauModel regCau = CauModel.getxID(id_causado);

                                    if (regCau != null) {

                                        // Buscar el compromiso asociado al causado, y anularlo
                                        try (final PreparedStatement pstCompr = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM compr_otros WHERE id_causado= ?")) {
                                            pstCompr.setLong(1, id_causado);
                                            try (final ResultSet rsCompr = pstCompr.executeQuery()) {
                                                while (rsCompr.next()) {
                                                    actAnularCompr(rsCompr.getLong("id_compr"), regCau.getTipo_compr(), false /* genReporte */); // false para no generar el reporte, que por alguna razon genera error
                                                }
                                            }
                                        }

                                        // Anular Causado
                                        if (ConnCapip.getInstance().getConnection().prepareStatement("UPDATE causado SET anulado_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                                            throw new Exception("Error al tratar de eliminar el Causado");
                                        }

                                        // Anular el pago, esta accion debe ejecutarse antes de anular "causado_orden_pago"
                                        PagoOrden.setAnularPagCau(id_causado, apagar_bs);
                                    }
                                }
                            }
                        }

                        // Anular los causado_orden_pago, hay ordenes de pago sin causa, las orden directa
                        try (final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE causado_orden_pago SET anulado_sn= 'S' "
                                + "WHERE anulado_sn= 'N' AND id_orden_pago= ?")) {
                            pst.setLong(1, reg.getId_orden_pago());
                            pst.executeUpdate();

                        }

                        // Anular Impuestos
                        //
                        ConnCapip.getInstance().EndTransaction();

                        JOptionPane.showMessageDialog(this, "Operación realizada");
                        txtNum.setText("");
                        txtNum.requestFocusInWindow();

                        try {
                            if (genReporte) {
                                PagoOrden.genReport(reg.getId_orden_pago(), "ORDEN DE PAGO", false, reg.isByCheck());
                            }
                        } catch (final Exception inex) {
                            JOptionPane.showMessageDialog(this, "Error al generar el reporte" + System.getProperty("line.separator") + inex);
                        }

                    } catch (final Exception inex) {
                        try {
                            ConnCapip.getInstance().RollBack();
                        } catch (final Exception inex2) {
                            JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
                        }

                        JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + inex);
                        txtNum.setText("");
                        txtNum.requestFocusInWindow();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Registro no encontrado");
                txtNum.setText("");
                txtNum.requestFocusInWindow();
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, inex);
        }
    }

    ///////////////////////////////////////////////////
    /**
     *
     * @param inid_compr
     */
    private void actAnularCompr(final long inid_compr, final TipoCompr intipoCompr, final boolean genReporte) throws Exception {

        if (( inid_compr <= 0) || ( intipoCompr == null)) {
            return;
        }

        try {

            // Verificar la existencia de la orden
            final ResultSet rsCompr = ConnCapip.getInstance().executeQuery("SELECT * FROM " + intipoCompr.getTbl() + " WHERE id_compr= " + inid_compr);
            if (rsCompr.next()) {

                final ComprModel reg = new ComprModel(rsCompr);

                final Map<String, Object> param = new HashMap<>(101);

                param.put("tipo_compr", intipoCompr);

                try {
                    if (genReporte) {
                        Compromiso.genReport(reg.getId_compromiso(), new HashMap<>(param), false);
                    }
                } catch (final Exception inex) {
                    throw new Exception("Error al generar el reporte" + System.getProperty("line.separator") + inex);
                }

                final ResultSet rsComprDet = ConnCapip.getInstance().executeQuery("SELECT * FROM compr_det WHERE id_compr= " + inid_compr + " AND tipo_compr= 'CO'");
                while (rsComprDet.next()) {
                    final ComprDetModel regComprDet = new ComprDetModel(rsComprDet);
                    anuComprPttoE(regComprDet.getCodPresu(), regComprDet.getCantPro().multiply(regComprDet.getPUnitario()).setScale(2, RoundingMode.HALF_UP));
                }

                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE " + intipoCompr.getTbl() + " SET anulado_sn= 'S' WHERE id_compr= " + inid_compr);
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error al tratar de actualizar el Registro");
                }

                try {
                    if (genReporte) {
                        Compromiso.genReport(reg.getId_compromiso(), new HashMap<>(param), false);
                    }
                } catch (final Exception inex) {
                    throw new Exception("Error al generar el reporte" + System.getProperty("line.separator") + inex);
                }
            } else {
                throw new Exception("Registro no encontrado");
            }

        } catch (final Exception inex) {
            throw new Exception( inex);
        }
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumActionPerformed
        btnAceptar.doClick();
    }//GEN-LAST:event_txtNumActionPerformed

    private void txtNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumKeyTyped
        if (!"0123456789".contains(String.valueOf(evt.getKeyChar())) || txtNum.getText().length() > 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumKeyTyped

    /**
     * @param args the command line arguments
     */
    // Para  que funcione bien esta ventana debe ser activada desde la ventana de orden de pago
//    public static void main(String args[]) {
//
//        /*
//         * Create and display the form
//         */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new PagoAnular(null).setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkMostrarReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCuenta;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JTextField txtNum;
    // End of variables declaration//GEN-END:variables

}
