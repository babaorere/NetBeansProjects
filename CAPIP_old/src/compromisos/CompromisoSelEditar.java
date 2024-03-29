/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package compromisos;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelos.ComprModel;
import utils.Format;
import utils.TipoCompr;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class CompromisoSelEditar extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    final java.awt.Window parent;

    /**
     * Creates new form ccompromisos
     *
     * @param _parent
     */
    public CompromisoSelEditar(final java.awt.Window _parent) {
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
        jPanel1 = new javax.swing.JPanel();
        txtNumCompr = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnCompra = new javax.swing.JToggleButton();
        btnServicio = new javax.swing.JToggleButton();
        btnOtros = new javax.swing.JToggleButton();
        btnSalir = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("EDICIÓN DE COMPROMISO");
        setMinimumSize(new java.awt.Dimension(519, 299));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INTRODUZCA  NÚMERO  DE COMPROMISO");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 410, 40));

        jPanel1.setOpaque(false);

        txtNumCompr.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtNumCompr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumCompr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        txtNumCompr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumComprActionPerformed(evt);
            }
        });
        txtNumCompr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumComprKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNumCompr, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtNumCompr, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 180, 40));

        jPanel2.setOpaque(false);

        btnCompra.setBackground(new java.awt.Color(255, 102, 0));
        buttonGroup1.add(btnCompra);
        btnCompra.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnCompra.setSelected(true);
        btnCompra.setText("OC");
        btnCompra.setBorder(null);
        btnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraActionPerformed(evt);
            }
        });

        btnServicio.setBackground(new java.awt.Color(102, 102, 0));
        buttonGroup1.add(btnServicio);
        btnServicio.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnServicio.setText("OS");
        btnServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServicioActionPerformed(evt);
            }
        });

        btnOtros.setBackground(new java.awt.Color(51, 102, 0));
        buttonGroup1.add(btnOtros);
        btnOtros.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnOtros.setText("CO");
        btnOtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtrosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(btnServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnOtros, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOtros, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 370, 60));

        btnSalir.setBackground(new java.awt.Color(153, 153, 0));
        btnSalir.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 110, 50));

        btnAceptar.setBackground(new java.awt.Color(153, 153, 0));
        btnAceptar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnAceptar.setText("ACEPTAR");
        btnAceptar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 110, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 530, 300));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Rev 15/09/2016
     *
     * @param evt
     */
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        actEditar();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void actEditar() {

        final String sNumCompr = txtNumCompr.getText().trim();

        final long id_compr;
        try {
            id_compr = Format.toLong(sNumCompr);
            if (id_compr <= 0) {
                return;
            }
        } catch (final Exception ex) {
            txtNumCompr.requestFocusInWindow();
            return;
        }

        try {
            final TipoCompr tipo_compr;
            if (btnCompra.isSelected()) {
                tipo_compr = TipoCompr.OC;
            } else if (btnServicio.isSelected()) {
                tipo_compr = TipoCompr.OS;
            } else if (btnOtros.isSelected()) {
                tipo_compr = TipoCompr.CO;
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo de Compromiso");
                return;
            }

            // Verificar la existencia de la orden
            final ResultSet rsCompr = Conn.executeQuery("SELECT * FROM " + tipo_compr.getTbl() + " WHERE id_compr= " + id_compr);
            if (rsCompr.next()) {

                final ComprModel reg = new ComprModel(rsCompr);

                final Map<String, Object> param = new HashMap<>(101);
                param.put("tipo_compr", tipo_compr);

                try {
                    Compromiso.genReport(reg.getId_compromiso(), new HashMap<>(param), false);
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(this, "Error al generar el reporte" + System.getProperty("line.separator") + _ex);
                }

                if (reg.getAnulado_sn().equals("S")) {
                    JOptionPane.showMessageDialog(this, "Este registro se encuentra anulado, no es posible anular");
                    txtNumCompr.requestFocusInWindow();
                    return;
                }

                // Verificar si el registro ha sido causado
                if (reg.getId_causado() != 0) {
                    JOptionPane.showMessageDialog(this, "Este registro ya fue Causado" + System.getProperty("line.separator") + "Intente reversar el Causado asociado, el número: " + reg.getId_causado());
                    txtNumCompr.requestFocusInWindow();
                    return;
                }

                final java.awt.Window me = this;
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new CompromisoEditar(me, id_compr, tipo_compr).setVisible(true);
                        setVisible(false);
                    }
                });

                txtNumCompr.setText("");

            } else {
                JOptionPane.showMessageDialog(null, "Registro no encontrado");
                txtNumCompr.requestFocusInWindow();
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
        }
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraActionPerformed
        txtNumCompr.requestFocus();
    }//GEN-LAST:event_btnCompraActionPerformed

    private void btnServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServicioActionPerformed
        txtNumCompr.requestFocus();
    }//GEN-LAST:event_btnServicioActionPerformed

    private void btnOtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtrosActionPerformed
        txtNumCompr.requestFocus();
    }//GEN-LAST:event_btnOtrosActionPerformed

    private void txtNumComprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumComprActionPerformed
        btnAceptar.doClick();
    }//GEN-LAST:event_txtNumComprActionPerformed

    private void txtNumComprKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumComprKeyTyped
        if (!"0123456789.".contains(String.valueOf(evt.getKeyChar())) || txtNumCompr.getText().length() > 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumComprKeyTyped

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

        txtNumCompr.requestFocusInWindow();
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
                new CompromisoSelEditar(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JToggleButton btnCompra;
    private javax.swing.JToggleButton btnOtros;
    private javax.swing.JButton btnSalir;
    private javax.swing.JToggleButton btnServicio;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtNumCompr;
    // End of variables declaration//GEN-END:variables

}
