package View.Usuario;

import Controller.LoginController;
import javax.swing.JOptionPane;

/**
 * Ventana inicial, para cargar al Usuario
 */
public final class UsuarioLoginDialog extends javax.swing.JDialog {

    private String nombreCompleto;
    private char passWord[];

    /**
     * Creates new form UserCreateUpdateDialog
     *
     * @param inWin
     */
    public UsuarioLoginDialog(final java.awt.Window inWin) {
        super(inWin, DEFAULT_MODALITY_TYPE);

        // Aqui se procede a crear los componentes graficos, varios de la GUI
        initComponents();

        // Aqui se procede a crear los componentes propios de la clase, los funcionales de la aplicación
        myInitComponents();
    }

    public void myInitComponents() {

        // Esto se utiliza mas adelante, para pasar esta Win como parametro en el "windowClosing"
        final java.awt.Window me = this;

        // Creación del listener, qie sera ejecutado una vez que se quiera cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    LoginController.actionSalir(me);
                });
            }
        });

        // Añadimos un default button
        getRootPane().setDefaultButton(btnEntrar);

        nombreCompleto = null;
        passWord = null;

    }

    public String getNickname() {
        return nombreCompleto;
    }

    public char[] getPassWord() {
        return passWord;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNombreCompleto = new javax.swing.JTextField();
        txtPassWord = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnCrear = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        btnEntrar = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        btnCancelar = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Login Usuario");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        jPanel3.setMinimumSize(new java.awt.Dimension(216, 40));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 40));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login Usuario");
        jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Nickname");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel8.setPreferredSize(new java.awt.Dimension(135, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Password");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel9.setPreferredSize(new java.awt.Dimension(135, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtNombreCompleto.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtNombreCompleto.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel2.add(txtNombreCompleto, gridBagConstraints);

        txtPassWord.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txtPassWord.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel2.add(txtPassWord, gridBagConstraints);

        getContentPane().add(jPanel2);

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 30));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(filler3);

        btnCrear.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnCrear.setText("Crear");
        btnCrear.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrear);
        jPanel1.add(filler1);

        btnEntrar.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnEntrar.setText("Entrar");
        btnEntrar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEntrar);
        jPanel1.add(filler2);

        btnCancelar.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar);
        jPanel1.add(filler4);

        getContentPane().add(jPanel1);

        setSize(new java.awt.Dimension(410, 430));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed

        // Validar las entradas
        nombreCompleto = txtNombreCompleto.getText().trim().toUpperCase();
        if (nombreCompleto.length() < 4) {
            nombreCompleto = null;
            JOptionPane.showMessageDialog(this, "Error, nickname invalido, debe tener al menos 4 caracteres");
            return; // Salir
        }

        passWord = txtPassWord.getPassword();

        if (passWord.length < 4) {
            passWord = null;
            JOptionPane.showMessageDialog(this, "Error, password invalido, debe tener al menos 4 caracteres");
            return; // Salir
        }

        // Aqui llamamos al controlador para verifique condiciones, y realice la accion.
        LoginController.Login(this);
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        LoginController.actionSalir(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // Aqui llamamos al controlador para verifique condiciones, y realice la accion.
        LoginController.CrearUsuario(this);
    }//GEN-LAST:event_btnCrearActionPerformed

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
            java.util.logging.Logger.getLogger(UsuarioLoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuarioLoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuarioLoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuarioLoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UsuarioLoginDialog dialog = new UsuarioLoginDialog(null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEntrar;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtNombreCompleto;
    private javax.swing.JPasswordField txtPassWord;
    // End of variables declaration//GEN-END:variables

}
