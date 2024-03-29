package serviciobarberia.vista;

import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import java.awt.Window;
import java.util.Optional;
import javax.swing.JOptionPane;
import serviciobarberia.modelo.Cliente;
import serviciobarberia.modelo.ClienteCRUD;

/**
 *
 * @author manager
 */
public class ClienteEliminar extends javax.swing.JDialog {

    private Cliente cliente;

    /**
     * Creates new form ClienteModificar
     */
    public ClienteEliminar(java.awt.Window parent) {
        super(parent, DEFAULT_MODALITY_TYPE);

        cliente = null;

        initComponents();
        myInitComponents();

    }

    @Override
    public void setVisible(boolean b) {

        if (b) {
            String strCliente = JOptionPane.showInputDialog("Indique el nombre").trim().toUpperCase();

            if (strCliente.isEmpty()) {
                JOptionPane.showConfirmDialog(this, "idcliente invalido");
            }

            if (!strCliente.isEmpty()) {

                ClienteCRUD cCrud = new ClienteCRUD();

                Optional<Cliente> oc = cCrud.readStr(strCliente);

                if (oc.isPresent()) {
                    cliente = oc.get();
                    txtxNombre.setText(cliente.getNombre());
                    txtTelefono.setText(cliente.getTelefono());
                    txtDireccion.setText(cliente.getDireccion());
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado");
                    ActionExit(this);
                    return;
                }

            }
        }

        super.setVisible(b);
    }

    public void myInitComponents() {

        // Esto se utiliza mas adelante, para pasar esta Win como parametro en el "windowClosing"
        final java.awt.Window me = this;

        // Creación del listener, qie sera ejecutado una vez que se quiera cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    ActionExit(me);
                });
            }
        });

    }

    private void ActionExit(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        } else {
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtxNombre = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cliente");

        jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jLabel1.setText("Cliente");

        txtxNombre.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        txtxNombre.setEnabled(false);

        txtTelefono.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        txtTelefono.setEnabled(false);

        txtDireccion.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        txtDireccion.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jLabel5.setText("Dirección");

        jLabel4.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jLabel4.setText("Teléfono");

        jLabel3.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jLabel3.setText("Nombre");

        btnEliminar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(217, 217, 217)
                            .addComponent(jLabel1)
                            .addGap(202, 202, 202))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(46, 46, 46)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)))
                            .addGap(53, 53, 53)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtxNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(btnEliminar)
                        .addGap(40, 40, 40)
                        .addComponent(btnCancelar)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtxNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(610, 313));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (cliente == null) {
            return;
        }

        ClienteCRUD cCrud = new ClienteCRUD();

        if (cCrud.delete(cliente.getIdcliente())) {
            JOptionPane.showMessageDialog(this, "Cliente eliminado con exito");
            cliente = null;
        } else {
            JOptionPane.showMessageDialog(null, "Cliente no eliminado");
        }

        ActionExit(this);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        ActionExit(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(ClienteEliminar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteEliminar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteEliminar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteEliminar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClienteEliminar dialog = new ClienteEliminar(null);
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtxNombre;
    // End of variables declaration//GEN-END:variables
}
