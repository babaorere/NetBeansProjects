/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capipsistema;

import connection.ConnCapip;
import java.awt.Toolkit;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class LoginRegister extends javax.swing.JFrame {

    /**
     * Creates new form principal
     */
    public LoginRegister() {
        super();
        initComponents();
        setTitle(getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Rev /10/2016
     */
    private void setCompBehavior() {
    }

    /**
     * Rev /10/2016
     */
    private void setOwnBehavior() {
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     * Rev 25/09/2016
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

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblContraseña = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        lblUsuario = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lblContraseña1 = new javax.swing.JLabel();
        txtNewPass = new javax.swing.JPasswordField();
        lblContraseña2 = new javax.swing.JLabel();
        txtConfPass = new javax.swing.JPasswordField();
        btnCambiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contrasena6.jpg"))); // NOI18N
        jLabel4.setText("jLabel4");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contrasena6.jpg"))); // NOI18N
        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("CANAIMA-SGG CAMBIAR CONTRASEÑA");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblContraseña.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        lblContraseña.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContraseña.setText("Contraseña");
        lblContraseña.setMaximumSize(new java.awt.Dimension(100, 30));
        lblContraseña.setMinimumSize(new java.awt.Dimension(100, 30));
        lblContraseña.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.add(lblContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 140, -1));

        txtPass.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });
        jPanel2.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 150, 30));

        lblUsuario.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsuario.setText("Usuario");
        lblUsuario.setMaximumSize(new java.awt.Dimension(100, 30));
        lblUsuario.setMinimumSize(new java.awt.Dimension(100, 30));
        lblUsuario.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 140, -1));

        txtUser.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });
        jPanel2.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 150, 30));

        lblContraseña1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        lblContraseña1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContraseña1.setText("Nueva Cont.");
        lblContraseña1.setMaximumSize(new java.awt.Dimension(100, 30));
        lblContraseña1.setMinimumSize(new java.awt.Dimension(100, 30));
        lblContraseña1.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.add(lblContraseña1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 140, -1));

        txtNewPass.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtNewPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtNewPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPassActionPerformed(evt);
            }
        });
        jPanel2.add(txtNewPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 150, 30));

        lblContraseña2.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        lblContraseña2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContraseña2.setText("Confirmar Cont.");
        lblContraseña2.setMaximumSize(new java.awt.Dimension(100, 30));
        lblContraseña2.setMinimumSize(new java.awt.Dimension(100, 30));
        lblContraseña2.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.add(lblContraseña2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 140, -1));

        txtConfPass.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtConfPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtConfPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConfPassActionPerformed(evt);
            }
        });
        jPanel2.add(txtConfPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 150, 30));

        btnCambiar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnCambiar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnCambiar.setText("Cambiar");
        btnCambiar.setPreferredSize(new java.awt.Dimension(140, 30));
        btnCambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCambiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 180, -1));

        btnSalir.setBackground(java.awt.SystemColor.inactiveCaption);
        btnSalir.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setMaximumSize(new java.awt.Dimension(80, 30));
        btnSalir.setMinimumSize(new java.awt.Dimension(80, 30));
        btnSalir.setPreferredSize(new java.awt.Dimension(80, 30));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 190, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contrasena6.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 250));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        txtUser.transferFocus();
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        txtPass.transferFocus();
    }//GEN-LAST:event_txtPassActionPerformed

    private void txtNewPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPassActionPerformed
        txtNewPass.transferFocus();
    }//GEN-LAST:event_txtNewPassActionPerformed

    private void txtConfPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConfPassActionPerformed
        btnCambiar.doClick();
    }//GEN-LAST:event_txtConfPassActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new UserPassIn().setVisible(true);
            }
        });

        setVisible(false);
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarActionPerformed
        final String user = txtUser.getText().trim().toUpperCase();

        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe indicar un Usuario");
            txtUser.requestFocusInWindow();
            return;
        }

        final int len = txtPass.getPassword().length;
        if (len == 0) {
            JOptionPane.showMessageDialog(null, "Debe indicar la Contraseña");
            txtPass.requestFocusInWindow();
            return;
        }

        if ((len < 4) || (len > 10)) {
            JOptionPane.showMessageDialog(null, "Longitud de contraseña de 4 hasta 10 caracteres");
            txtPass.requestFocusInWindow();
            return;
        }

        final byte[] pass;
        try {
            pass = MessageDigest.getInstance("MD5").digest(charToByte(txtPass.getPassword()));
        } catch (NoSuchAlgorithmException inex) {
            JOptionPane.showMessageDialog(null, inex);
            return;
        }

        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where user='" + user + "'");
            if (rs.next()) {
                final byte[] regPass = rs.getBytes("pass");

                if (regPass != null) {

                    if (pass.length != regPass.length) {
                        JOptionPane.showMessageDialog(null, "Usuario/Contraseña invalido(a)");
                        return;
                    }

                    for (int i = 0; i < pass.length; i++) {
                        if (pass[i] != regPass[i]) {
                            JOptionPane.showMessageDialog(null, "Usuario/Contraseña invalido(a)");
                            return;
                        }
                    }
                }

                final byte[] newPass;
                try {
                    newPass = MessageDigest.getInstance("MD5").digest(charToByte(txtNewPass.getPassword()));
                } catch (NoSuchAlgorithmException inex) {
                    JOptionPane.showMessageDialog(null, inex);
                    return;
                }

                final byte[] confPass;
                try {
                    confPass = MessageDigest.getInstance("MD5").digest(charToByte(txtConfPass.getPassword()));
                } catch (NoSuchAlgorithmException inex) {
                    JOptionPane.showMessageDialog(null, inex);
                    return;
                }

                for (int i = 0; i < pass.length; i++) {
                    if (newPass[i] != confPass[i]) {
                        JOptionPane.showMessageDialog(null, "No coincide la nueva contraseña");
                        return;
                    }
                }

                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE usuario SET pass= ? WHERE user = ?");
                pst.setBytes(1, newPass);
                pst.setString(2, user);

                if (pst.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Error al tratar de actualizar la contraseña");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Contraseña cambiada");

                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        new UserPassIn().setVisible(true);
                    }
                });

                // Finalizar la presente ventana
                setVisible(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario/Contraseña invalido(a)");
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }//GEN-LAST:event_btnCambiarActionPerformed

    /**
     *
     * @param chars
     * @return
     */
    private byte[] charToByte(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        java.nio.ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
            byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
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
                new LoginRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblContraseña1;
    private javax.swing.JLabel lblContraseña2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtConfPass;
    private javax.swing.JPasswordField txtNewPass;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    Connection cn = ConnCapip.getInstance().getConnection();
}