package vista;

import controlador.ControlAppMain;
import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;
import negocio.Vehiculo;


/**
 *
 */
public final class DlgVehiculo extends javax.swing.JDialog implements DlgVehiculo_IF {

    /**
     * Creates new form DlgCliente
     *
     * @param parent
     */
    public DlgVehiculo(java.awt.Window parent) {
        super(parent, ModalityType.DOCUMENT_MODAL);
        initComponents();
        myInit();
    }


    @Override
    public void myInit() {

        final java.awt.Window me = this;

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    ControlAppMain.actionSalir(me);
                });
            }
        });

        btnDisponible.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                btnDisponible.setText("D");
            } else {
                btnDisponible.setText("A");
            }
        });

        LimpiarCampos();
    }


    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPatente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnDisponible = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CAR-RENT - Vehículo");
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Vehículo");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setMaximumSize(new java.awt.Dimension(200, 40));
        jLabel1.setMinimumSize(new java.awt.Dimension(200, 40));
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Patente");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel2.setMaximumSize(new java.awt.Dimension(75, 30));
        jLabel2.setMinimumSize(new java.awt.Dimension(75, 30));
        jLabel2.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtPatente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPatente.setMaximumSize(new java.awt.Dimension(75, 30));
        txtPatente.setMinimumSize(new java.awt.Dimension(75, 30));
        txtPatente.setPreferredSize(new java.awt.Dimension(75, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 10);
        jPanel2.add(txtPatente, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("¿ Condición ?");
        jLabel4.setMaximumSize(new java.awt.Dimension(100, 30));
        jLabel4.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 10);
        jPanel2.add(jLabel4, gridBagConstraints);

        btnDisponible.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnDisponible.setSelected(true);
        btnDisponible.setText("D");
        btnDisponible.setMaximumSize(new java.awt.Dimension(70, 30));
        btnDisponible.setMinimumSize(new java.awt.Dimension(70, 30));
        btnDisponible.setPreferredSize(new java.awt.Dimension(70, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 10);
        jPanel2.add(btnDisponible, gridBagConstraints);

        getContentPane().add(jPanel2);

        btnAgregar.setBackground(new java.awt.Color(0, 102, 0));
        btnAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setMaximumSize(new java.awt.Dimension(100, 30));
        btnAgregar.setMinimumSize(new java.awt.Dimension(100, 30));
        btnAgregar.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(197, Short.MAX_VALUE)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3);

        setSize(new java.awt.Dimension(410, 390));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        actionAgregar();
    }//GEN-LAST:event_btnAgregarActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            DlgVehiculo dialog = new DlgVehiculo(new javax.swing.JFrame());
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JToggleButton btnDisponible;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtPatente;
    // End of variables declaration//GEN-END:variables


    private void actionAgregar() {

        String strCedula = txtPatente.getText().trim().toUpperCase();
        if (!Vehiculo.ValPatente(strCedula)) {
            JOptionPane.showMessageDialog(this, "Patente invalida.");
            txtPatente.requestFocus();
            return;
        }

        char vigente = btnDisponible.isSelected() ? 'D' : 'A';

        if (FrmAppMain.addVehiculo(new Vehiculo(strCedula, vigente))) {
            LimpiarCampos();
            JOptionPane.showMessageDialog(this, "Vehículo agregado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al tratar de agregar.");
        }

    }


    @Override
    public void LimpiarCampos() {
        txtPatente.setText("");
        btnDisponible.setSelected(true);
        txtPatente.requestFocus();
    }
}
