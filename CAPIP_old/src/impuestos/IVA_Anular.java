/*
 * Todos los derechos reservados por CAPIP C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package impuestos;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelos.CauModel;
import modelos.ComprModel;
import modelos.IvaRetencionDetModel;
import modelos.IvaRetencionModel;
import utils.Format;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class IVA_Anular extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    final java.awt.Window parent;

    /**
     * Creates new form ccompromisos
     *
     * @param _parent
     */
    public IVA_Anular(final java.awt.Window _parent) {
        super();
        initComponents();

        parent = _parent;

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     * Rev 25/09/2016
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
     *
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        txtNum = new javax.swing.JTextField();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ANULACIÓN DE RETENCIÓN DE I.V.A.");
        setMinimumSize(new java.awt.Dimension(519, 240));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INTRODUZCA  NÚMERO  DE RETENCIÓN");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 520, 40));

        btnSalir.setBackground(new java.awt.Color(153, 153, 0));
        btnSalir.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 110, 50));

        btnAceptar.setBackground(new java.awt.Color(153, 153, 0));
        btnAceptar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnAceptar.setText("ACEPTAR");
        btnAceptar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 110, 50));

        txtNum.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
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
        getContentPane().add(txtNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 80, 270, 41));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        lblFondo.setMinimumSize(new java.awt.Dimension(1280, 240));
        lblFondo.setPreferredSize(new java.awt.Dimension(1280, 240));
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 240));
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

        final long id;
        try {
            id = Format.toLong(sNum);
            if (id <= 0) {
                return;
            }
        } catch (final Exception ex) {
            txtNum.requestFocusInWindow();
            return;
        }

        try {

            // Verificar la existencia de la orden
            final ResultSet rsRet = Conn.executeQuery("SELECT * FROM iva_retencion WHERE id_iva_retencion= " + id);
            if (rsRet.next()) {

                final IvaRetencionModel regRet = new IvaRetencionModel(rsRet);

                if (regRet.getAnulado_sn().equals("S")) {
                    JOptionPane.showMessageDialog(this, "Este registro ya se encuentra anulado");
                    txtNum.requestFocusInWindow();
                    return;
                }

                // Verificar si el causado de alguna de los detalles ha sido pagado
                final ResultSet rsRetDet = Conn.executeQuery("SELECT * FROM iva_retencion_det WHERE id_iva_retencion= " + regRet.getId_iva_retencion());
                while (rsRetDet.next()) {
                    final IvaRetencionDetModel regRetDet = new IvaRetencionDetModel(rsRetDet);

                    // Recuperar el causado
                    final ResultSet rsCau = Conn.executeQuery("SELECT * FROM causado WHERE id_causado= " + regRetDet.getId_causado());
                    if (rsCau.next()) {
                        CauModel regCau = new CauModel(rsCau);

                        // Verificar si el causado ha sido pagado                        
                        final ResultSet rsCauPago = Conn.executeQuery("SELECT * FROM causado_orden_pago WHERE id_causado= " + regCau.getId_causado() + " AND anulado_sn= 'N'");
                        if (rsCauPago.next()) {
                            BigDecimal apagar_bs = rsCauPago.getBigDecimal("apagar_bs");
                            if (apagar_bs.compareTo(BigDecimal.ZERO) > 0) {
                                JOptionPane.showMessageDialog(this, "Causado asociado a la retención, " + System.getProperty("line.separator") + "Pagado, o en proceso de pago");
                                txtNum.requestFocusInWindow();
                                return;
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Causado asociado a la retención, " + System.getProperty("line.separator") + "No encontrado");
                        txtNum.requestFocusInWindow();
                        return;
                    }

                }

                // Reversar la Operacion, y anular la Retencion
                if (JOptionPane.showConfirmDialog(this, "Esta operación es irreversible. Desea anular la Retención ?",
                    "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    try {

                        Conn.BeginTransaction();

                        // Actualizar la Retencion
                        final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE iva_retencion SET anulado_sn= 'S' WHERE id_iva_retencion= " + id);
                        if (pst.executeUpdate() != 1) {
                            throw new Exception("Error al tratar de actualizar el Registro");
                        }

                        // Actualizar los detalles
                        final PreparedStatement pstDet = Conn.getConnection().prepareStatement("UPDATE iva_retencion_det SET anulado_sn= 'S' WHERE id_iva_retencion= " + id);
                        if (pstDet.executeUpdate() <= 0) {
                            throw new Exception("Error al tratar de actualizar el Registro");
                        }

                        // Actualizar todos los Causado asociados
                        final ResultSet rsRetDet2 = Conn.executeQuery("SELECT * FROM iva_retencion_det WHERE id_iva_retencion= " + id);
                        while (rsRetDet2.next()) {
                            final IvaRetencionDetModel regRetDet = new IvaRetencionDetModel(rsRetDet2);
                            final long id_causado = regRetDet.getId_causado();
                            final CauModel regCau = CauModel.getxID(id_causado);

                            if (regCau == null) {
                                throw new Exception("Causado no encontrado");
                            }

                            final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET iva_ret_sn= 'N' WHERE id_causado= " + id_causado);
                            if (pstCau.executeUpdate() != 1) {
                                throw new Exception("Error al tratar de actualizar el Registro");
                            }

                            // Buscar todos lo compromisos asociados al causado                                
                            try (final ResultSet rsCompr = Conn.executeQuery("SELECT * FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= " + id_causado)) {
                                while (rsCompr.next()) {
                                    final ComprModel regCompr = new ComprModel(rsCompr);
                                    final BigDecimal iva_porc_retenido = regCompr.getIva_porc_ret();
                                    final BigDecimal iva_retenido_bs = regCompr.getIva_grav_bs().multiply(regCompr.getIva_porc_aplic()).multiply(iva_porc_retenido).movePointLeft(2).setScale(2, RoundingMode.HALF_UP);

                                    final PreparedStatement pstCompr2 = Conn.getConnection().prepareStatement("UPDATE " + regCau.getTipo_compr().getTbl() + " SET iva_porc_ret= 0 WHERE id_compr= ?");
                                    pstCompr2.setLong(1, regCompr.getId_compromiso());
                                    if (pstCompr2.executeUpdate() != 1) {
                                        throw new Exception("Error sl actualizar el impuesto Retenido");
                                    }

                                    final PreparedStatement pstCau2 = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs + ? WHERE id_causado= ?");
                                    pstCau2.setBigDecimal(1, iva_retenido_bs);
                                    pstCau2.setLong(2, id_causado);
                                    if (pstCau2.executeUpdate() != 1) {
                                        throw new Exception("Error sl actualizar el impuesto Retenido");
                                    }
                                }
                            }
                        }

                        Conn.EndTransaction();

                        JOptionPane.showMessageDialog(this, "Operación realizada");
                        txtNum.setText("");
                        txtNum.requestFocusInWindow();

                    } catch (final Exception _ex) {
                        try {
                            Conn.RollBack();
                        } catch (final Exception _ex2) {
                            JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
                        }

                        JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
                    }

                    try {
                        final Map<String, Object> param = new HashMap<>(101);
                        ImpuestoRetencion.genIvaReport(id, param);
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Registro no encontrado");
                txtNum.requestFocusInWindow();
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
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
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IVA_Anular(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JTextField txtNum;
    // End of variables declaration//GEN-END:variables

}
