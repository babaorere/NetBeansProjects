/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capipsistema;

import connection.ConnCapip;
import configuracion.Configuracion;
import java.awt.Toolkit;
import static java.lang.System.exit;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelos.ModelMapNextOp;
import modelos.UserModel;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class UserPassIn extends javax.swing.JFrame {

    private static long id_session;
    private static long id_user;
    private static String user;
    private static String userName;

    static {
        id_user = 0;
        id_session = 0;
        user = "DEBUG";
    }

    /**
     * Rev 21/09/2016
     *
     * @return el ID del usuario actual
     */
    public static long getIdUser() {
        return id_user;
    }

    /**
     * Rev 21/09/2016
     *
     * @return El Usuario actual
     */
    public static String getUser() {
        return user;
    }

    /**
     * Rev 21/09/2016
     *
     * @return El Usuario actual
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Rev 21/09/2016
     *
     * @return the id_session
     */
    public static long getIdSession() {
        return id_session;
    }

    /**
     * Rev 23/09/2016
     *
     * @return the date_session
     */
    public static java.sql.Timestamp getDateSession() throws Exception {
        return Globales.getServerTimeStamp();
    }

    /**
     * Creates new form
     */
    public UserPassIn() {

        super();
        initComponents();

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

    /**
     * Establece el comportamiento de la presente Ventana Rev 21/09/2016
     *
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        setTitle(getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        // Establecer acción al cerrar ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                btnSalir.doClick();
            }
        });
    }

    /**
     * Establece el comportamiento de los componentes de la Ventana Rev 21/09/2016
     */
    private void setCompBehavior() {
    }

    /**
     * Establece las condiciones iniciales de la Ventana Rev 21/09/2016
     */
    private void setStartConditions() {

        // Usuario inicial
        id_user = 0;
        id_session = 0;
        user = "DEBUG";
        userName = "USUARIO DE PRUEBA";
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblContraseña = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        lblUsuario = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        btnCambiar = new javax.swing.JButton();
        btnEntrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("CANAIMA-SGG - AUTENTIFICACIÓN");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblContraseña.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        lblContraseña.setText("Contraseña");
        jPanel2.add(lblContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 100, 30));

        txtPass.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        jPanel2.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 150, 30));

        lblUsuario.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        lblUsuario.setText("Usuario");
        jPanel2.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 70, 30));

        txtUser.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });
        jPanel2.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 150, 30));

        btnCambiar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnCambiar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnCambiar.setText("Cambiar Contraseña");
        btnCambiar.setPreferredSize(new java.awt.Dimension(140, 30));
        btnCambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCambiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 180, -1));

        btnEntrar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnEntrar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnEntrar.setText("Entrar");
        btnEntrar.setMaximumSize(new java.awt.Dimension(80, 30));
        btnEntrar.setMinimumSize(new java.awt.Dimension(80, 30));
        btnEntrar.setPreferredSize(new java.awt.Dimension(80, 30));
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, -1, -1));

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

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Contraseña.png"))); // NOI18N
        jLabel6.setText("jLabel6");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 250));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 250));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        txtUser.transferFocus();
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        btnEntrar.doClick();
    }//GEN-LAST:event_txtPassActionPerformed

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        final String aux_user = txtUser.getText().trim().toUpperCase();
        final String aux_user_name;

        if (aux_user.isEmpty()) {
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

        if ((len < 4) || (len > 16)) {
            JOptionPane.showMessageDialog(null, "Longitud de contraseña de 4 hasta 16 caracteres");
            txtPass.requestFocusInWindow();
            return;
        }

        final byte[] pass;
        try {
            pass = MessageDigest.getInstance("MD5").digest(charToByte(txtPass.getPassword()));
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            return;
        }

        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario where user='" + aux_user + "'");
            if (rs.next()) {
                final long aux_id_user = rs.getInt("id_user");
                final byte[] regPass = rs.getBytes("pass");
                final Boolean active = rs.getString("active").equals("true");
                aux_user_name = rs.getString("nombre");

                if (!active) {
                    JOptionPane.showMessageDialog(null, "Usuario inactivo");
                    return;
                }

                if (regPass == null) {
                    JOptionPane.showMessageDialog(null, "Debe actualizar la Contraseña");
                    return;
                }

                if (pass.length != regPass.length) {
                    JOptionPane.showMessageDialog(null, "Usuario/Contraseña inválido(a)");
                    return;
                }

                for (int i = 0; i < pass.length; i++) {
                    if (pass[i] != regPass[i]) {
                        JOptionPane.showMessageDialog(null, "Usuario/Contraseña inválido(a)");
                        return;
                    }
                }

                // Verificar que el Ejercicio Fiscal registrado en el Servidor, corresponda a la fecha del Cliente
                final Calendar fechaAct = Calendar.getInstance();
                final long ejeFisCliente = fechaAct.get(Calendar.YEAR);
                final long ejeFisServidor = Globales.getServerYear();
                final long ejefis = Globales.getEjeFisYear();

                if (ejeFisServidor != ejeFisCliente) {
                    JOptionPane.showMessageDialog(null, "Error. El año del O/S del Servidor: " + ejeFisServidor + System.getProperty("line.separator") + "difiere del año del O/S Local: " + ejeFisCliente);
                    exit(1);
                }

                if (ejeFisServidor != ejefis) {
                    JOptionPane.showMessageDialog(null, "Discrepancia en el Ejercicio Fiscal: " + ejefis + ", Servidor: " + ejeFisServidor);
                    if (aux_user.equals("ADMIN")) {
                        if (JOptionPane.showConfirmDialog(this, "¿ Desea actualizar en el Servidor, el Ejercicio Fiscal: " + ejeFisCliente + " ?",
                                "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {

                            Configuracion.actGuardarEjeFis(String.valueOf(ejeFisServidor));

                            exit(1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Discrepancia de fechas."
                                    + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ);
                            exit(1);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Discrepancia de fechas"
                                + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ);
                        exit(1);
                    }
                }

                id_user = aux_id_user;
                user = aux_user;
                userName = aux_user_name;

                // Solicitar un identificador de sesion
                id_session = ModelMapNextOp.getNext(Globales.getEjeFisYear(), String.valueOf(id_user));

                //////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////
                java.awt.EventQueue.invokeLater(() -> {
                    if (UserModel.isActive(UserPassIn.getIdUser())) {
                        FrmPrincipal.getInstance().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario inactivo");
                        exit(1);
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
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            new UserPassChange().setVisible(true);
        });

        // Finalizar la presente ventana
        setVisible(false);
        dispose();
    }//GEN-LAST:event_btnCambiarActionPerformed

    private void txtPassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyTyped
        if (txtPass.getPassword().length >= 16) {
            evt.consume();
        }

    }//GEN-LAST:event_txtPassKeyTyped

    private static byte[] charToByte(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        java.nio.ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    /**
     * Convierte un arreglo de byte, en una cadena de caracteres
     *
     * @param inin
     * @return
     */
    private static String ArrayByteToStr(byte[] inin) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inin.length; i++) {
            sb.append(String.valueOf( inin[i]));
        }

        return sb.toString();
    }

    /**
     * Ejecuta la accion dependiendo de los argumentos pasados al programa .- Muestra ayuda básica .- Ejecuta el configurador, con password y si el .- Ejecuta a Capip Normal
     *
     * @param args
     */
    private static final void actChoice(final String args[]) {
        switch (args.length) {
            case 0: // sin parametros, ejecuta capip Normal
                actUserPassIn();
                break;

            case 1:
                // Buscar cual de los comandos se requiere activar

                // Llamar al Configurador
                if (args[0].toLowerCase().equals("-c")) {
                    actConfigurador(new StringBuilder());
                } else {
                    actShowHelp();
                }
                break;

            case 2:
                // Buscar cual de los comandos se requiere activar
                final boolean cent;
                final int idxC;

                if (args[0].toLowerCase().equals("-c")) {
                    cent = true;
                    idxC = 0;
                } else if (args[1].toLowerCase().equals("-c")) {
                    cent = true;
                    idxC = 1;
                } else {
                    cent = false;
                    idxC = -11;
                }

                if (!cent) {
                    actShowHelp();
                    break;
                }

                final int idxO = idxC == 0 ? 1 : 0;

                // La longitud es invalida
                if (args[idxO].length() < 2) {
                    actShowHelp();
                    break;
                }

                final String aux_1 = args[idxO].substring(0, 2);

                // Identificar el comando/parámetro
                switch (aux_1) {
                    case "-h":
                        actShowHelp();
                        actConfigurador(new StringBuilder());
                        break;
                    case "-p":
                        actConfigurador(new StringBuilder(args[idxO].substring(2, args[idxO].length())));
                        break;
                    default:
                        actShowHelp();
                        exit(1);
                }

                break;

            default:

                actShowHelp();

                actUserPassIn();
                break;
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            exit(1);
        }

        actChoice(args);
    }

    /**
     * Muestra ayuda básica, sobre los argumentos/parámetros de ejecución
     */
    private static void actShowHelp() {
        JOptionPane.showMessageDialog(null, "Error" + System.getProperty("line.separator")
                + "Comando no reconocido");
        JOptionPane.showMessageDialog(null, "Comandos disponibles" + System.getProperty("line.separator")
                + "-c Modo Configurador" + System.getProperty("line.separator")
                + "-h Ayuda" + System.getProperty("line.separator")
                + "-c -pAAAAAAAA Modo Configurador con password <AAAAAAAA>" + System.getProperty("line.separator"));
    }

    /**
     * Activa la ventana para solicitar el User/PassWord, antes de ejecutar la aplicación
     */
    private static void actUserPassIn() {

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new UserPassIn().setVisible(true);
            }
        });
    }

    /**
     * Activa el COnfigurador para establecer la licencia
     */
    private static void actConfigurador(StringBuilder inaux) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new capip_configurador.FrmConfing( inaux).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiar;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

}
