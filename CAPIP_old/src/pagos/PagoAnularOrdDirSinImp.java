/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package pagos;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import static impuestos.ImpuestoRetencion.checkORetNextId;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import javax.swing.JOptionPane;
import modelos.CuentaModel;
import modelos.PagoModel;
import utils.CuentaUpdatable;
import utils.Format;
import utils.OrdPagIdTipo;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class PagoAnularOrdDirSinImp extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    final private java.awt.Window parent;
    final CuentaModel regCuenta;

    /**
     * Creates new form compromisos
     *
     * @param _parent
     */
    public PagoAnularOrdDirSinImp(final java.awt.Window _parent) {
        super();
        initComponents();

        parent = _parent;

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
        btnSalir = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        lblCuenta = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ANULACIÓN DE ORDEN DE PAGO DIR. SIN IMP.");
        setMinimumSize(new java.awt.Dimension(519, 230));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INTRODUZCA  NÚMERO  DE ORDEN DE PAGO");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 520, 40));

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
        getContentPane().add(txtNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 156, 41));

        btnSalir.setBackground(new java.awt.Color(153, 153, 0));
        btnSalir.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 110, 50));

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

        lblCuenta.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCuenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCuenta.setPreferredSize(new java.awt.Dimension(300, 25));
        getContentPane().add(lblCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 520, -1));

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

        final String sqlQuery;
        if (regCuenta == null) {
            sqlQuery = "SELECT * FROM orden_pago WHERE YEAR(ejefis) = " + Globales.getEjeFisYear() + " AND num_x_pag = " + num;
        } else {
            sqlQuery = "SELECT * FROM orden_pago WHERE YEAR(ejefis) = " + Globales.getEjeFisYear() + " AND id_cuenta = " + regCuenta.getId_cuenta() + " AND num_x_cuenta = " + num;
        }

        try {

            // Verificar la existencia de la orden
            final ResultSet rsPago = Conn.executeQuery(sqlQuery);
            if (rsPago.next()) {

                final PagoModel reg = new PagoModel(rsPago);

                try {
                    PagoOrden.genReport(reg.getId_orden_pago(), "ORDEN DE PAGO", false, reg.isByCheck());
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(this, "Error al generar el reporte" + System.getProperty("line.separator") + _ex);
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

                        Conn.BeginTransaction();

                        // Anular Orden de Pago
                        try (final PreparedStatement pstPago = Conn.getConnection().prepareStatement("UPDATE orden_pago SET anulado_sn= 'S' "
                            + "WHERE anulado_sn = 'N' AND id_orden_pago= ?")) {
                            pstPago.setLong(1, reg.getId_orden_pago());
                            if (pstPago.executeUpdate() != 1) {
                                throw new Exception("Error al tratar de actualizar el Registro");
                            }
                        }

                        // Anular bancos_operaciones
                        try (final PreparedStatement pstBancos = Conn.getConnection().prepareStatement("UPDATE bancos_operaciones SET anulado_sn= 'S' "
                            + "WHERE anulado_sn = 'N' AND (tipo_operacion = 'ND' OR tipo_operacion = 'CH') "
                            + "AND num_pag_ava= ?")) {
                            pstBancos.setLong(1, reg.getId_orden_pago());
                            if (pstBancos.executeUpdate() != 1) {
                                throw new Exception("Error al tratar de anular la operación en Bancos");
                            }
                        }

                        // Buscar todos los pagos que son afectados por la anulacion
                        try (final PreparedStatement pstCauPag = Conn.getConnection().prepareStatement("SELECT * FROM causado_orden_pago WHERE id_orden_pago= ?")) {
                            pstCauPag.setLong(1, reg.getId_orden_pago());
                            try (final ResultSet rsCauPag = pstCauPag.executeQuery()) {
                                while (rsCauPag.next()) {
                                    final long id_causado = rsCauPag.getLong("id_causado");
                                    final BigDecimal apagar_bs = rsCauPag.getBigDecimal("apagar_bs");

                                    // Anular el pago, esta accion debe ejecutarse antes de anular "causado_orden_pago"
                                    PagoOrden.setAnularPagCau(id_causado, apagar_bs);
                                }
                            }
                        }

                        // Anular los causado_orden_pago
                        try (final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE causado_orden_pago SET anulado_sn= 'S' "
                            + "WHERE anulado_sn= 'N' AND id_orden_pago= ?")) {
                            pst.setLong(1, reg.getId_orden_pago());
                            if (pst.executeUpdate() <= 0) {
                                throw new Exception("Error al tratar de actualizar el Registro");
                            }
                        }

                        Conn.EndTransaction();

                        JOptionPane.showMessageDialog(this, "Operación realizada");
                        txtNum.setText("");
                        txtNum.requestFocusInWindow();

                        try {
                            PagoOrden.genReport(reg.getId_orden_pago(), "ORDEN DE PAGO", false, reg.isByCheck());
                        } catch (final Exception _ex) {
                            JOptionPane.showMessageDialog(this, "Error al generar el reporte" + System.getProperty("line.separator") + _ex);
                        }

                    } catch (final Exception _ex) {
                        try {
                            Conn.RollBack();
                        } catch (final Exception _ex2) {
                            JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
                        }

                        JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Registro no encontrado");
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
     * Rev 03-03-2017
     *
     * @param _param
     * @throws Exception
     */
    private void AnuORetMaster(final Map<String, Object> _param) throws Exception {

        checkORetNextId();

        // Mantener actualizado el control iva
        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO otras_ret("
            + "id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4,
            + "benef_razonsocial, benef_rif_ci) " // 5, 6, 7 
            + "VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pst.setLong(1, UserPassIn.getIdUser());
        pst.setLong(2, UserPassIn.getIdSession());
        pst.setTimestamp(3, UserPassIn.getDateSession());
        final java.sql.Date date = new java.sql.Date(Globales.getServerTimeStamp().getTime());
        pst.setDate(4, date); // ejefis
        pst.setString(5, (String) _param.get("oretBenef_razonsocial"));
        pst.setString(6, (String) _param.get("oretBenef_rif_ci"));
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error sl insertar el registro");
        }

        // Toma el ultimo id generado
        final long id;
        final ResultSet rsId = pst.getGeneratedKeys();
        if (rsId.next()) {
            id = rsId.getLong(1);
        } else {
            throw new Exception("Error al tratar de recuperar el ID");
        }

        _param.put("id_otras_ret", id);
    }

    /**
     * Rev 16/11/2016
     *
     * @param _param
     * @throws Exception
     */
    private void AnuOtrasRetDetail(long id_causado) throws Exception {

//
//        final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs - ?, oret_ret_sn= 'S' WHERE id_causado= ?");
//        pstCau.setBigDecimal(1, oret_bs);
//        pstCau.setLong(2, id_causado);
//        if (pstCau.executeUpdate() != 1) {
//            throw new Exception("Error sl actualizar el impuesto Retenido");
//        }
//
//        final long id_otras_ret = (long) _param.get("id_otras_ret");
//        final java.sql.Date fecha_fact = (java.sql.Date) _param.get("fecha_fact");
//        final String num_fact = (String) _param.get("num_fact");
//        final TipoCompr tipo_compr = TipoCompr.OC;
//        final long id_compr = (long) _param.get("id_compr");
//        final BigDecimal total_fact = (BigDecimal) _param.get("total_bs");
//        final BigDecimal base_imponible = (BigDecimal) _param.get("base_imponible_bs");
//        final String obs = (String) _param.get("observacion");
//
//        final String oretBenef_razonsocial = (String) _param.get("oretBenef_razonsocial");
//        final String oretBenef_rif_ci = (String) _param.get("oretBenef_rif_ci");
//
//        if (oret_bs.compareTo(BigDecimal.ZERO) > 0) {
//            final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO otras_ret_det("
//                + "id_otras_ret, id_causado, fecha_fact, num_fact, " // 1, 2, 3, 4, 
//                + "tipo_compr, id_compr, total_fact, base_imponible, " //  5, 6, 7, 8
//                + "gravable_bs, retenido_bs, observacion, " // 9, 10, 11
//                + "ejefis, benef_razonsocial, benef_rif_ci) " // 12, 13, 14
//                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
//
//            pst.setLong(1, id_otras_ret);
//            pst.setLong(2, id_causado); // noperacion
//            pst.setDate(3, fecha_fact); // ffactura
//            pst.setString(4, num_fact); // nfactura
//            pst.setString(5, tipo_compr.name()); // tipo_compr
//            pst.setLong(6, id_compr); // cfactura, num control
//            pst.setBigDecimal(7, total_fact); // 
//            pst.setBigDecimal(8, base_imponible); // bimponible
//            pst.setBigDecimal(9, base_imponible);
//            pst.setBigDecimal(10, oret_bs); // iretenido
//            pst.setString(11, obs); // conceptoret
//            pst.setDate(12, new java.sql.Date(Globales.getServerTimeStamp().getTime())); // ejefis
//            pst.setString(13, oretBenef_razonsocial);
//            pst.setString(14, oretBenef_rif_ci);
//            if (pst.executeUpdate() != 1) {
//                throw new Exception("Error sl insertar el registro de Detalle");
//            }
//
//            // Actualizar los datos del Compromiso asociado
//            // Aqui NO se debe actualizar el iva_grav_bs, porque esta cantidad es fija, y establecida en el compromiso
//            PreparedStatement pstCompr = Conn.getConnection().prepareStatement("UPDATE compr_otros SET oret_grav_bs= ?, oret_bs= ? WHERE id_compr= ?");
//            pstCompr.setBigDecimal(1, base_imponible);
//            pstCompr.setBigDecimal(2, oret_bs);
//            pstCompr.setLong(3, id_compr);
//            if (pstCompr.executeUpdate() != 1) {
//                throw new Exception("Error al actualizar el Compromiso");
//            }
//
//        }
//
    }

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCuenta;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JTextField txtNum;
    // End of variables declaration//GEN-END:variables

}
