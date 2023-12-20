/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.configuracion;

import com.principal.bancos.BancosContabilidad;
import com.principal.capipsistema.Globales;
import com.principal.capipsistema.Propiedades;
import com.principal.capipsistema.UserPassIn;
import com.principal.capipsistema.UserTrack;
import com.principal.compromisos.Compromiso;
import com.principal.connection.ConnCapip;
import com.principal.modelos.UserModel;
import com.principal.reportes.UserMenuReport;
import com.principal.textfield_decimal.DecimalTextField;
import com.principal.utils.Format;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import static java.lang.System.exit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
public final class Configuracion extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private final java.awt.Window parent;

    private UserModel regUsuario;

    /**
     * Creates new form configuracion
     *
     * @param inparent
     */
    public Configuracion(java.awt.Window inparent) {
        super();
        initComponents();
        parent = inparent;
        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Rev /10/2016
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        setTitle(Propiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + Propiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        // Establecer la acción al cerrar ventana
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                actSalir();
            }
        });
        // Para salir con la tecla ESC
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");
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
        regUsuario = null;
        btnCargar.setEnabled(true);
        btnCancelar.setEnabled(false);
        MostrarDatos();
    }

    /**
     * Para Reubicar la ventana al ser visualizada Rev 25/09/2016
     *
     * @param inb
     */
    @Override
    public void setVisible(boolean inb) {
        // Para mostrar la ventana en el tope de la pantalla
        if (inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }
        super.setVisible(inb);
    }

    /**
     */
    private void MostrarDatos() {
        UpdateTblBenef();
        Compromiso.cargarComboIva(cmbIva);
        txtEjeFis.setText(String.valueOf(Globales.getEjeFisYear()));
        MostrarUT();
    }

    /**
     */
    private void UpdateTblBenef() {
        final DefaultTableModel model = (DefaultTableModel) tblBenef.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        final Object[] datos = new Object[4];
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM beneficiario ORDER BY razonsocial ASC");
            while (rs.next()) {
                datos[0] = rs.getString("razonsocial");
                datos[1] = rs.getString("rif_ci");
                datos[2] = rs.getString("domicilio");
                datos[3] = rs.getString("telefonos");
                model.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
    }

    void limpiarBenef() {
        txtRazonS.setText("");
        txtRifCI.setText("");
        txtTelfs.setText("");
        txtDomicilio.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtRazonS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtRifCI = new javax.swing.JTextField();
        txtDomicilio = new javax.swing.JTextField();
        txtTelfs = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnNuevoBenef = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnCambiarIVA = new javax.swing.JButton();
        cmbIva = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtEjeFis = new javax.swing.JTextField();
        btnGuardarEjercicioF = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtUser = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        btnGuardarUsuario = new javax.swing.JButton();
        btnAutorizaciones = new javax.swing.JButton();
        btnSeguimiento = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnCargar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        chkActivo = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBenef = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtUnidadT = DecimalTextField.getTextField();
        btnCambiarUT = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnRespaldo = new javax.swing.JButton();
        btnFirma = new javax.swing.JButton();
        lblAdelanto = new javax.swing.JLabel();
        btnReConversion = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("CONFIGURACION");
        setMinimumSize(new java.awt.Dimension(720, 750));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {

            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }

            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        // NOI18N
        jLabel3.setFont(new java.awt.Font("Arial", 3, 36));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CONFIGURACIÓN DEL SISTEMA");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 640, -1));
        // NOI18N
        jLabel6.setFont(new java.awt.Font("Arial", 3, 18));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("REGISTRO DE BENEFICIARIOS ");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 570, -1));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);
        // NOI18N
        txtRazonS.setFont(new java.awt.Font("Arial", 3, 16));
        txtRazonS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtRazonS.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRazonS.setEnabled(false);
        txtRazonS.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonSKeyTyped(evt);
            }
        });
        jPanel1.add(txtRazonS);
        txtRazonS.setBounds(140, 10, 420, 30);
        // NOI18N
        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 12));
        jLabel4.setText("RIF/CI");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 60, 50, 15);
        // NOI18N
        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 12));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("TELÉFONO");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(290, 60, 80, 15);
        // NOI18N
        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 12));
        jLabel2.setText("RAZÓN SOCIAL");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 20, 110, 15);
        // NOI18N
        txtRifCI.setFont(new java.awt.Font("Arial", 2, 15));
        txtRifCI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtRifCI.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRifCI.setEnabled(false);
        txtRifCI.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtRifCI.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRifCIKeyTyped(evt);
            }
        });
        jPanel1.add(txtRifCI);
        txtRifCI.setBounds(80, 50, 200, 30);
        // NOI18N
        txtDomicilio.setFont(new java.awt.Font("Arial", 2, 15));
        txtDomicilio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtDomicilio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDomicilio.setEnabled(false);
        txtDomicilio.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtDomicilio.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDomicilioKeyTyped(evt);
            }
        });
        jPanel1.add(txtDomicilio);
        txtDomicilio.setBounds(90, 100, 470, 30);
        // NOI18N
        txtTelfs.setFont(new java.awt.Font("Arial", 2, 15));
        txtTelfs.setToolTipText("Formato NNNN-NNNNNNN");
        txtTelfs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtTelfs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTelfs.setEnabled(false);
        txtTelfs.setSelectionColor(new java.awt.Color(175, 204, 125));
        jPanel1.add(txtTelfs);
        txtTelfs.setBounds(380, 50, 180, 30);
        // NOI18N
        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 12));
        jLabel15.setText("DIRECCIÓN");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(10, 110, 68, 15);
        btnNuevoBenef.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnNuevoBenef.setFont(new java.awt.Font("Arial", 3, 18));
        btnNuevoBenef.setText("Configuración");
        btnNuevoBenef.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNuevoBenef.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoBenefActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoBenef);
        btnNuevoBenef.setBounds(240, 140, 140, 40);
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 600, 190));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel1.setText("CAMBIO I.V.A.");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(50, 10, 160, 17);
        btnCambiarIVA.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnCambiarIVA.setFont(new java.awt.Font("Arial", 3, 18));
        btnCambiarIVA.setText("Cambiar");
        btnCambiarIVA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCambiarIVA.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarIVAActionPerformed(evt);
            }
        });
        jPanel2.add(btnCambiarIVA);
        btnCambiarIVA.setBounds(50, 70, 120, 40);
        cmbIva.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        cmbIva.setFont(new java.awt.Font("Arial", 2, 16));
        cmbIva.setMinimumSize(new java.awt.Dimension(64, 27));
        cmbIva.setPreferredSize(new java.awt.Dimension(64, 27));
        jPanel2.add(cmbIva);
        cmbIva.setBounds(10, 30, 230, 30);
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 520, 250, 120));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);
        // NOI18N
        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 14));
        jLabel8.setText("CAMBIO EJERCICIO FISCAL ");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(40, 10, 204, 17);
        txtEjeFis.setBackground(new java.awt.Color(175, 204, 125));
        // NOI18N
        txtEjeFis.setFont(new java.awt.Font("Arial", 3, 20));
        txtEjeFis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEjeFis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtEjeFis.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEjeFis.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtEjeFis.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtEjeFis.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEjeFisActionPerformed(evt);
            }
        });
        jPanel3.add(txtEjeFis);
        txtEjeFis.setBounds(80, 30, 120, 40);
        btnGuardarEjercicioF.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnGuardarEjercicioF.setFont(new java.awt.Font("Arial", 3, 18));
        btnGuardarEjercicioF.setText("Guardar");
        btnGuardarEjercicioF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardarEjercicioF.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEjercicioFActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardarEjercicioF);
        btnGuardarEjercicioF.setBounds(70, 70, 140, 40);
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 520, 270, 120));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(null);
        // NOI18N
        txtUser.setFont(new java.awt.Font("Arial", 2, 15));
        txtUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtUser.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUser.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtUser.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUserKeyTyped(evt);
            }
        });
        jPanel4.add(txtUser);
        txtUser.setBounds(110, 10, 180, 40);
        // NOI18N
        jLabel10.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("CONTRASEÑA");
        jPanel4.add(jLabel10);
        jLabel10.setBounds(3, 60, 100, 40);
        // NOI18N
        txtPass.setFont(new java.awt.Font("Arial", 2, 15));
        txtPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtPass.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPass.setSelectionColor(new java.awt.Color(175, 204, 125));
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
        jPanel4.add(txtPass);
        txtPass.setBounds(110, 60, 180, 40);
        // NOI18N
        jLabel12.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("USUARIO");
        jPanel4.add(jLabel12);
        jLabel12.setBounds(3, 10, 100, 40);
        btnGuardarUsuario.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnGuardarUsuario.setFont(new java.awt.Font("Arial", 3, 18));
        btnGuardarUsuario.setText("Guardar");
        btnGuardarUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardarUsuario.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuarioActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardarUsuario);
        btnGuardarUsuario.setBounds(430, 110, 140, 40);
        btnAutorizaciones.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnAutorizaciones.setFont(new java.awt.Font("Tahoma", 1, 13));
        btnAutorizaciones.setText("Autorizaciones");
        btnAutorizaciones.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorizacionesActionPerformed(evt);
            }
        });
        jPanel4.add(btnAutorizaciones);
        btnAutorizaciones.setBounds(430, 10, 150, 30);
        btnSeguimiento.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnSeguimiento.setFont(new java.awt.Font("Tahoma", 1, 13));
        btnSeguimiento.setText("Seguimiento");
        btnSeguimiento.setAlignmentX(0.5F);
        btnSeguimiento.setMaximumSize(new java.awt.Dimension(180, 30));
        btnSeguimiento.setMinimumSize(new java.awt.Dimension(180, 30));
        btnSeguimiento.setPreferredSize(new java.awt.Dimension(180, 30));
        btnSeguimiento.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeguimientoActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeguimiento);
        btnSeguimiento.setBounds(430, 50, 150, 30);
        // NOI18N
        jLabel5.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("NOMBRE");
        jLabel5.setToolTipText("Longitud máxima 32 caracteres");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(3, 110, 100, 40);
        // NOI18N
        txtNombre.setFont(new java.awt.Font("Arial", 2, 15));
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtNombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombre.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel4.add(txtNombre);
        txtNombre.setBounds(110, 110, 310, 40);
        btnCargar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnCargar.setFont(new java.awt.Font("Arial", 3, 18));
        btnCargar.setText("Cargar");
        btnCargar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCargar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCargar);
        btnCargar.setBounds(300, 10, 100, 40);
        btnCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnCancelar.setFont(new java.awt.Font("Arial", 3, 18));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);
        btnCancelar.setBounds(300, 60, 100, 40);
        chkActivo.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        chkActivo.setFont(new java.awt.Font("Arial", 3, 16));
        chkActivo.setText("Activo");
        jPanel4.add(chkActivo);
        chkActivo.setBounds(110, 160, 90, 27);
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 600, 200));
        // NOI18N
        jLabel14.setFont(new java.awt.Font("Arial", 3, 18));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("REGISTRO DE USUARIOS");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 530, -1));
        // NOI18N
        tblBenef.setFont(new java.awt.Font("Arial", 2, 15));
        tblBenef.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "BENEFICIARIO", "R.I.F. / C.I.", "DOMICILIO", "TELÉFONOS" }) {

            Class[] types = new Class[] { java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class };

            boolean[] canEdit = new boolean[] { false, false, false, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblBenef.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblBenef.setCellSelectionEnabled(true);
        tblBenef.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblBenef.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBenefMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblBenef);
        tblBenef.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tblBenef.getColumnModel().getColumnCount() > 0) {
            tblBenef.getColumnModel().getColumn(0).setMinWidth(128);
            tblBenef.getColumnModel().getColumn(0).setPreferredWidth(256);
            tblBenef.getColumnModel().getColumn(1).setMinWidth(50);
            tblBenef.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblBenef.getColumnModel().getColumn(2).setMinWidth(128);
            tblBenef.getColumnModel().getColumn(2).setPreferredWidth(256);
            tblBenef.getColumnModel().getColumn(3).setMinWidth(64);
            tblBenef.getColumnModel().getColumn(3).setPreferredWidth(128);
        }
        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, 360, 430));
        btnEliminar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnEliminar.setFont(new java.awt.Font("Arial", 3, 16));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 40, -1, -1));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);
        // NOI18N
        jLabel11.setFont(new java.awt.Font("Tahoma", 3, 14));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("CAMBIO UNIDAD TRIBUTARIA");
        jPanel5.add(jLabel11);
        jLabel11.setBounds(10, 10, 260, 17);
        txtUnidadT.setBackground(new java.awt.Color(175, 204, 125));
        // NOI18N
        txtUnidadT.setFont(new java.awt.Font("Arial", 3, 20));
        txtUnidadT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUnidadT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtUnidadT.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUnidadT.setEnabled(false);
        txtUnidadT.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtUnidadT.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnidadTActionPerformed(evt);
            }
        });
        jPanel5.add(txtUnidadT);
        txtUnidadT.setBounds(80, 30, 120, 40);
        btnCambiarUT.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnCambiarUT.setFont(new java.awt.Font("Arial", 3, 18));
        btnCambiarUT.setText("Cambiar");
        btnCambiarUT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCambiarUT.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarUTActionPerformed(evt);
            }
        });
        jPanel5.add(btnCambiarUT);
        btnCambiarUT.setBounds(80, 70, 120, 40);
        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 520, 270, 120));
        btnSalir.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnSalir.setFont(new java.awt.Font("Arial", 2, 18));
        btnSalir.setText("MENU PRINCIPAL");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 650, 240, 40));
        btnRespaldo.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnRespaldo.setFont(new java.awt.Font("Arial", 2, 18));
        btnRespaldo.setText("RESPALDO. B.D.");
        btnRespaldo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnRespaldo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRespaldo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRespaldoActionPerformed(evt);
            }
        });
        getContentPane().add(btnRespaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 650, 220, 40));
        btnFirma.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnFirma.setFont(new java.awt.Font("Arial", 2, 18));
        btnFirma.setText("FIRMAS");
        btnFirma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnFirma.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFirma.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirmaActionPerformed(evt);
            }
        });
        getContentPane().add(btnFirma, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 650, 140, 40));
        lblAdelanto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAdelanto.setMaximumSize(new java.awt.Dimension(100, 25));
        lblAdelanto.setMinimumSize(new java.awt.Dimension(100, 25));
        lblAdelanto.setPreferredSize(new java.awt.Dimension(100, 25));
        getContentPane().add(lblAdelanto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 710, 220, -1));
        btnReConversion.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnReConversion.setFont(new java.awt.Font("Arial", 2, 18));
        btnReConversion.setText("Reconversión");
        btnReConversion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReConversion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReConversion.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReConversionActionPerformed(evt);
            }
        });
        getContentPane().add(btnReConversion, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 650, 180, 40));
        // NOI18N
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png")));
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 750));
        pack();
        setLocationRelativeTo(null);
    }

    // </editor-fold>//GEN-END:initComponents
    /**
     * Rev 21/11/2016
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
     * Rev 01/10/2016
     */
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }

//GEN-LAST:event_btnSalirActionPerformed
    private void btnGuardarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnGuardarUsuarioActionPerformed
        final String user = txtUser.getText().trim().toUpperCase();
        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Favor introduzca el Usuario");
            java.awt.EventQueue.invokeLater(txtUser::requestFocusInWindow);
            return;
        }
        if ((txtPass.getPassword().length == 0) && (regUsuario == null)) {
            JOptionPane.showMessageDialog(this, "Favor introduzca la contraseña");
            java.awt.EventQueue.invokeLater(txtPass::requestFocusInWindow);
            return;
        }
        final String nombre = txtNombre.getText().trim().toUpperCase();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Favor introduzca el nombre");
            java.awt.EventQueue.invokeLater(txtPass::requestFocusInWindow);
            return;
        }
        final String activo = (chkActivo.isSelected() || user.equals("ADMIN")) ? "true" : "false";
        if (regUsuario == null) {
            try {
                final Integer access = user.equals("ADMIN") ? 1 : 0;
                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO usuario(user, pass, nombre, ppto, banco, compr, cau, imp, pago, cfg, rpt, active) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setString(1, txtUser.getText().trim().toUpperCase());
                try {
                    pst.setBytes(2, MessageDigest.getInstance("MD5").digest(charToByte(txtPass.getPassword())));
                } catch (final Exception inex) {
                    JOptionPane.showMessageDialog(null, inex);
                    logger.error(inex);
                    return;
                }
                pst.setString(3, nombre);
                pst.setInt(4, access);
                pst.setInt(5, access);
                pst.setInt(6, access);
                pst.setInt(7, access);
                pst.setInt(8, access);
                pst.setInt(9, access);
                pst.setInt(10, access);
                pst.setInt(11, access);
                pst.setString(12, "true");
                if (pst.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Problemas al generar el registro");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Los datos se guardaron correctamente");
                limpiarBenef();
                UpdateTblBenef();
                txtUser.setText("");
                txtPass.setText("");
                txtNombre.setText("");
                chkActivo.setSelected(false);
            } catch (final Exception inex) {
                JOptionPane.showMessageDialog(null, inex);
                logger.error(inex);
            }
        } else {
            try {
                final int access = user.equals("ADMIN") ? 1 : 0;
                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE usuario set nombre= ?, active= ? WHERE id_user= ?");
                pst.setString(1, nombre);
                pst.setString(2, activo);
                pst.setLong(3, regUsuario.getIdUser());
                if (pst.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Problemas al actualizar el registro");
                    return;
                }
                regUsuario = null;
                JOptionPane.showMessageDialog(null, "Los datos se guardaron correctamente");
                txtUser.setText("");
                txtPass.setText("");
                txtNombre.setText("");
                chkActivo.setSelected(false);
                txtUser.setEnabled(true);
                txtPass.setEnabled(true);
                txtNombre.setEnabled(true);
                chkActivo.setEnabled(true);
                btnCancelar.setEnabled(false);
            } catch (final Exception inex) {
                JOptionPane.showMessageDialog(null, inex);
                logger.error(inex);
            }
        }
    }

//GEN-LAST:event_btnGuardarUsuarioActionPerformed
    private byte[] charToByte(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        java.nio.ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        // clear sensitive data
        Arrays.fill(charBuffer.array(), '\u0000');
        // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }

    private void tblBenefMouseClicked(java.awt.event.MouseEvent evt) {
//GEN-FIRST:event_tblBenefMouseClicked
        int fila = tblBenef.getSelectedRow();
        if (fila >= 0) {
            txtRazonS.setText(tblBenef.getValueAt(fila, 0).toString());
            txtRifCI.setText(tblBenef.getValueAt(fila, 1).toString());
            txtDomicilio.setText(tblBenef.getValueAt(fila, 2).toString());
            txtTelfs.setText(tblBenef.getValueAt(fila, 3).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Debe selecciona un registro");
        }
    }

//GEN-LAST:event_tblBenefMouseClicked
    private void btnCambiarIVAActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnCambiarIVAActionPerformed
        new DlgIvaPorcAplic(this).setVisible(true);
    }

//GEN-LAST:event_btnCambiarIVAActionPerformed
    /**
     * Rev 07/01/2016
     *
     * @param evt
     */
    private void btnGuardarEjercicioFActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnGuardarEjercicioFActionPerformed
        try {
            actGuardarEjeFis(txtEjeFis.getText().trim());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de actualizar el Ejercicio Fiscal" + System.getProperty("line.separator") + inex);
            java.awt.EventQueue.invokeLater(txtEjeFis::requestFocusInWindow);
            logger.error(inex);
        }
        txtEjeFis.setText(String.valueOf(Globales.getEjeFisYear()));
    }

//GEN-LAST:event_btnGuardarEjercicioFActionPerformed
    /**
     * Rev 07/01/2016
     *
     * @param insEjeFis
     * @throws Exception
     */
    public static void actGuardarEjeFis(final String insEjeFis) throws Exception {
        if ((insEjeFis == null) || insEjeFis.isEmpty()) {
            throw new Exception("Ejercicio Fiscal inválido");
        }
        final long iEjeFis;
        try {
            iEjeFis = Integer.parseInt(insEjeFis);
        } catch (final Exception inex) {
            logger.error(inex);
            throw new Exception("Ejercicio Fiscal inválido");
        }
        final long actEjeFis = Globales.getEjeFisYear();
        final long serverYear = Globales.getServerYear();
        // No es necesario actualizar el ejercicio fiscal, ya esta actualizado
        if (actEjeFis == serverYear) {
            return;
        }
        // Verificar el rango valido, para el nuevo ejercicio fiscal
        if ((iEjeFis < serverYear) || (iEjeFis > serverYear + 1)) {
            throw new Exception("Ejercicio Fiscal inválido. Debe ser " + serverYear + " <= Ejercicio Fiscal <= " + (serverYear + 1) + ", el año del Servidor");
        }
        try {
            ConnCapip.getInstance().BeginTransaction();
            ConnCapip.getInstance().getConnection().prepareStatement("UPDATE global SET valor= '" + insEjeFis + "' WHERE nombre = 'ejercicio_fiscal'").executeUpdate();
            // Para cada uno de los Bancos, se debe actualizar su respectivo saldo anual.
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM bancos");
            while (rs.next()) {
                final long id_banco = rs.getLong("id");
                final String cuenta = rs.getString("cuenta");
                final BigDecimal banco_saldoi = Format.toBigDec(rs.getDouble("saldoi"));
                // Hallar el saldo final para el año anterior,
                // lo cual vendra siendo el saldo inicial para el nuevo año
                final long ejeFisAnt = iEjeFis - 1;
                final BigDecimal saldoiAnt;
                final BigDecimal saldofAnt;
                final ResultSet rsAnt = ConnCapip.getInstance().executeQuery("SELECT * FROM banco_saldo_anual WHERE id_banco= " + id_banco + " AND YEAR(ejefis) = " + ejeFisAnt);
                if (rsAnt.next()) {
                    saldoiAnt = rsAnt.getBigDecimal("saldoi");
                    // todos los movimiento del año
                    saldofAnt = saldoiAnt.add(BancosContabilidad.getMovHasta(cuenta, ejeFisAnt, 12)).setScale(2, RoundingMode.HALF_UP);
                } else {
                    saldoiAnt = banco_saldoi;
                    saldofAnt = saldoiAnt;
                }
                // Borrar algun posible registro, de una inicializacion anterior.
                final PreparedStatement pstDelete = ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM banco_saldo_anual WHERE id_banco= ? AND YEAR(ejefis) = ?");
                pstDelete.setLong(1, id_banco);
                pstDelete.setLong(2, iEjeFis);
                pstDelete.executeUpdate();
                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement(// 1, 2
                        "INSERT INTO banco_saldo_anual("
                        + // 3, 4,
                        "id_user, id_session, "
                        + // 5, 6
                        "date_session, ejefis, " + "id_banco, saldoi) " + "VALUES (?, ?, ?, ?, ?, ?)");
                // id_user
                pst.setLong(1, UserPassIn.getIdUser());
                // id_session
                pst.setLong(2, UserPassIn.getIdSession());
                pst.setTimestamp(3, UserPassIn.getDateSession());
                // ejefis
                pst.setDate(4, Globales.getEjefis());
                pst.setLong(5, id_banco);
                pst.setBigDecimal(6, saldofAnt);
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error al insertar el registro");
                }
            }
            // Cargar el presupuesto del nuevo año fiscal
            //
            ConnCapip.getInstance().EndTransaction();
            JOptionPane.showMessageDialog(null, "Ejercicio Fiscal actualizado");
        } catch (final Exception inex) {
            try {
                ConnCapip.getInstance().RollBack();
            } catch (final Exception inex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de anular la operación" + System.getProperty("line.separator") + inex2);
                exit(1);
                logger.error(inex2);
            }
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnEliminarActionPerformed
        final int fila = tblBenef.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Beneficiario");
            return;
        }
        final String rif_ci = (String) tblBenef.getValueAt(fila, 1);
        // Verificar que existe un compromiso
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM compr_compra WHERE benef_rif_ci= '" + rif_ci + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "No es Posible eliminar, ya existe al menos un compromiso en curso");
                return;
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        // Verificar que existe un compromiso
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM compr_servicio WHERE benef_rif_ci= '" + rif_ci + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "No es Posible eliminar, ya existe al menos un compromiso en curso");
                return;
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        // Verificar que existe un compromiso
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM compr_otros WHERE benef_rif_ci= '" + rif_ci + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "No es Posible eliminar, ya existe al menos un compromiso en curso");
                return;
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "¿ Esta seguro de Eliminar el Beneficiario ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
            try {
                if (ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM beneficiario WHERE rif_ci='" + rif_ci + "'").executeUpdate() != 1) {
                    throw new Exception("Cantidad inválida");
                }
            } catch (final Exception inex) {
                JOptionPane.showMessageDialog(null, "Error al tratar de Eliminar: " + System.getProperty("line.separator") + inex);
                logger.error(inex);
            }
        }
        limpiarBenef();
        UpdateTblBenef();
    }

//GEN-LAST:event_btnEliminarActionPerformed
    private void btnAutorizacionesActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnAutorizacionesActionPerformed
        if (UserModel.isCfg(UserPassIn.getIdUser())) {
            final java.awt.Window me = this;
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new ConfigUser(me).setVisible(true);
                    setVisible(false);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no autorizado");
        }
    }

//GEN-LAST:event_btnAutorizacionesActionPerformed
    private void btnSeguimientoActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnSeguimientoActionPerformed
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new UserMenuReport(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }

//GEN-LAST:event_btnSeguimientoActionPerformed
    private void btnCambiarUTActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnCambiarUTActionPerformed
        actGuardarUT();
    }

//GEN-LAST:event_btnCambiarUTActionPerformed
    private void btnRespaldoActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnRespaldoActionPerformed
        final JFileChooser fc = new JFileChooser();
        // Indicamos que podemos seleccionar un unico ficheros
        fc.setMultiSelectionEnabled(false);
        // Indicamos lo que podemos seleccionar
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // Creamos el filtro
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.sql", "sql");
        // Le indicamos el filtro
        fc.setFileFilter(filtro);
        // Indicar el directorio inicial
        final File backupDir = new File(Propiedades.CAPIP_DIR_LOCAL);
        if (!backupDir.exists()) {
            backupDir.mkdir();
        }
        fc.setCurrentDirectory(backupDir);
        final File f_default = new File(Propiedades.CAPIP_DIR_LOCAL + "/Respaldo-" + Globales.getServerSqlDate() + ".sql");
        fc.setSelectedFile(f_default);
        // Si no se ha aprovado el archivo, retorna sin accion
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        final File f_bak = fc.getSelectedFile();
        // Si no ha sido seleccionado algun archivo
        if (f_bak == null) {
            return;
        }
        // clase Runtime para lanzar DOS
        Runtime runtime = Runtime.getRuntime();
        try {
            lblAdelanto.setText("");
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            //ruta donde se encuntra mysqldump
            final String path = Propiedades.CAPIP_PATH_MYSQLDUMP + "/";
            final File f = new File(path + "C:\\Program Files\\MySQL\\MySQL Server 6.0\\bin\\mysqldump.exe");
            // verificar si el programa utilitario del mysql, se encuentra instalado
            if (f.exists()) {
                //se lanza el DOS
                Process child = runtime.exec(// + "--host=capipsistema "
                        path + "mysqldump.exe " + "--compact --complete-insert --extended-insert --skip-quote-names --opt "
                        + // + "--port=3308 "
                        "--host=servidor "
                        + // + "--user=servidor "
                        "--port=3306 "
                        + // + "--password=407111787 "
                        "--user=servidor " + "--password=pb407111787 " + "--databases capip2022 -R");
                final InputStream is = child.getInputStream();
                try (FileOutputStream fos = new FileOutputStream(f_bak)) {
                    byte[] buffer = new byte[1_024_000];
                    int cont = 0;
                    int leido = is.read(buffer);
                    while (leido > 0) {
                        fos.write(buffer, 0, leido);
                        buffer = new byte[1_024_000];
                        leido = is.read(buffer);
                        cont++;
                        final String marca = lblAdelanto.getText();
                        if (marca.length() < 10) {
                            lblAdelanto.setText(marca + "@");
                        } else {
                            lblAdelanto.setText("@");
                        }
                    }
                    if (cont <= 0) {
                        JOptionPane.showMessageDialog(this, "Error al tratar de realizar el comando" + System.getProperty("line.separator") + "No se ha podido crear el archivo de respaldo");
                    } else {
                        JOptionPane.showMessageDialog(this, "Operación realizada");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al tratar de realizar el respaldo" + System.getProperty("line.separator") + "el MySQL NO se encuentra instalado localmente, falta el archivo mysqldump.exe" + System.getProperty("line.separator") + f.getCanonicalPath());
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de realizar el respaldo de la B.D." + System.getProperty("line.separator") + inex);
            logger.error(inex);
        } finally {
            lblAdelanto.setText("");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

//GEN-LAST:event_btnRespaldoActionPerformed
    private void txtRazonSKeyTyped(java.awt.event.KeyEvent evt) {
//GEN-FIRST:event_txtRazonSKeyTyped
        if (txtRazonS.getText().length() >= 128) {
            evt.consume();
        }
    }

//GEN-LAST:event_txtRazonSKeyTyped
    private void txtDomicilioKeyTyped(java.awt.event.KeyEvent evt) {
//GEN-FIRST:event_txtDomicilioKeyTyped
        if (txtDomicilio.getText().length() >= 256) {
            evt.consume();
        }
    }

//GEN-LAST:event_txtDomicilioKeyTyped
    private void txtUserKeyTyped(java.awt.event.KeyEvent evt) {
//GEN-FIRST:event_txtUserKeyTyped
        if (txtUser.getText().length() >= 16) {
            evt.consume();
        }
    }

//GEN-LAST:event_txtUserKeyTyped
    private void txtPassKeyTyped(java.awt.event.KeyEvent evt) {
//GEN-FIRST:event_txtPassKeyTyped
        if (txtPass.getPassword().length >= 16) {
            evt.consume();
        }
    }

//GEN-LAST:event_txtPassKeyTyped
    private void txtRifCIKeyTyped(java.awt.event.KeyEvent evt) {
//GEN-FIRST:event_txtRifCIKeyTyped
        if (txtRifCI.getText().length() >= 10) {
            evt.consume();
        }
    }

//GEN-LAST:event_txtRifCIKeyTyped
    private void txtEjeFisActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtEjeFisActionPerformed
        btnGuardarEjercicioF.requestFocusInWindow();
    }

//GEN-LAST:event_txtEjeFisActionPerformed
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {
//GEN-FIRST:event_formWindowGainedFocus
        //        Compromiso.cargarComboIva(cmbIva);
        txtEjeFis.setText(String.valueOf(Globales.getEjeFisYear()));
        MostrarUT();
    }

//GEN-LAST:event_formWindowGainedFocus
    private void btnFirmaActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnFirmaActionPerformed
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                // La Ventana se abre como Modal, asi que se espera a su respectivo cierre
                new DlgFirmaRpt(me).setVisible(true);
            }
        });
    }

//GEN-LAST:event_btnFirmaActionPerformed
    private void btnNuevoBenefActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnNuevoBenefActionPerformed
        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new RegistroBenef(me, false).setVisible(true);
                setVisible(false);
            }
        });
    }

//GEN-LAST:event_btnNuevoBenefActionPerformed
    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {
//GEN-FIRST:event_txtNombreKeyTyped
        if (txtPass.getPassword().length >= 32) {
            evt.consume();
        }
    }

//GEN-LAST:event_txtNombreKeyTyped
    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtUserActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                txtPass.requestFocusInWindow();
            }
        });
    }

//GEN-LAST:event_txtUserActionPerformed
    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtPassActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                txtNombre.requestFocusInWindow();
            }
        });
    }

//GEN-LAST:event_txtPassActionPerformed
    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtNombreActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                btnGuardarUsuario.requestFocusInWindow();
            }
        });
    }

//GEN-LAST:event_txtNombreActionPerformed
    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnCargarActionPerformed
        actCargar();
    }

//GEN-LAST:event_btnCargarActionPerformed
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnCancelarActionPerformed
        actCancelar();
    }

//GEN-LAST:event_btnCancelarActionPerformed
    private void btnReConversionActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnReConversionActionPerformed
        actReconversion();
    }

//GEN-LAST:event_btnReConversionActionPerformed
    private void txtUnidadTActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtUnidadTActionPerformed
        // TODO add your handling code here:
    }

//GEN-LAST:event_txtUnidadTActionPerformed
    private void actGuardarUT() {
        new DlgUnidadTributariaAplic(this).setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Configuracion(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutorizaciones;

    private javax.swing.JButton btnCambiarIVA;

    private javax.swing.JButton btnCambiarUT;

    private javax.swing.JButton btnCancelar;

    private javax.swing.JButton btnCargar;

    private javax.swing.JButton btnEliminar;

    private javax.swing.JButton btnFirma;

    private javax.swing.JButton btnGuardarEjercicioF;

    private javax.swing.JButton btnGuardarUsuario;

    private javax.swing.JButton btnNuevoBenef;

    private javax.swing.JButton btnReConversion;

    private javax.swing.JButton btnRespaldo;

    private javax.swing.JButton btnSalir;

    private javax.swing.JButton btnSeguimiento;

    private javax.swing.JCheckBox chkActivo;

    private javax.swing.JComboBox<String> cmbIva;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel10;

    private javax.swing.JLabel jLabel11;

    private javax.swing.JLabel jLabel12;

    private javax.swing.JLabel jLabel14;

    private javax.swing.JLabel jLabel15;

    private javax.swing.JLabel jLabel2;

    private javax.swing.JLabel jLabel3;

    private javax.swing.JLabel jLabel4;

    private javax.swing.JLabel jLabel5;

    private javax.swing.JLabel jLabel6;

    private javax.swing.JLabel jLabel7;

    private javax.swing.JLabel jLabel8;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JPanel jPanel3;

    private javax.swing.JPanel jPanel4;

    private javax.swing.JPanel jPanel5;

    private javax.swing.JScrollPane jScrollPane4;

    private javax.swing.JLabel lblAdelanto;

    private javax.swing.JLabel lblFondo;

    private javax.swing.JTable tblBenef;

    private javax.swing.JTextField txtDomicilio;

    private javax.swing.JTextField txtEjeFis;

    private javax.swing.JTextField txtNombre;

    private javax.swing.JPasswordField txtPass;

    private javax.swing.JTextField txtRazonS;

    private javax.swing.JTextField txtRifCI;

    private javax.swing.JTextField txtTelfs;

    private javax.swing.JTextField txtUnidadT;

    private javax.swing.JTextField txtUser;

    // End of variables declaration//GEN-END:variables
    private void MostrarUT() {
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM unidad_tributaria_aplic WHERE fecha_vigencia <= CURDATE() ORDER BY fecha_vigencia DESC");
            if (rs.next()) {
                txtUnidadT.setText(Format.toStr(rs.getBigDecimal("valor_bs")));
            } else {
                txtUnidadT.setText("0,00");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error. No es posible obtener el valor de la U.T." + System.getProperty("line.separator") + inex);
            java.awt.EventQueue.invokeLater(btnCambiarUT::requestFocusInWindow);
            logger.error(inex);
        }
    }

    /**
     * Formatea un string en su posible representación telefónica
     */
    private String FormatPhone(String insaux) {
        StringBuilder resp = new StringBuilder();
        final String[] list = insaux.split(" ");
        if (list.length <= 0) {
            return resp.toString();
        }
        for (String phone : list) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < phone.length(); j++) {
                final String temp = String.valueOf(phone.charAt(j));
                if ("0123456789".contains(temp)) {
                    sb.append(temp);
                }
            }
            //         0412 716 63 88
            // Formato NNNN NNN NN NN, 11 digitos
            if (sb.length() != 11) {
                continue;
            }
            resp.append(sb.charAt(0)).append(sb.charAt(1)).append(sb.charAt(2)).append(sb.charAt(3)).append("-").append(sb.charAt(4)).append(sb.charAt(5)).append(sb.charAt(6)).append(".").append(sb.charAt(7)).append(sb.charAt(8)).append(".").append(sb.charAt(9)).append(sb.charAt(10));
        }
        return resp.toString();
    }

    /**
     */
    private void actCargar() {
        final String user = txtUser.getText().trim().toUpperCase();
        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Favor introduzca el Usuario");
            txtUser.setText("");
            txtPass.setText("");
            txtNombre.setText("");
            txtUser.setEnabled(true);
            txtPass.setEnabled(true);
            btnCancelar.setEnabled(true);
            java.awt.EventQueue.invokeLater(txtUser::requestFocusInWindow);
            return;
        }
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM usuario WHERE user= '" + user + "'");
            if (rs.next()) {
                regUsuario = new UserModel(rs);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado" + System.getProperty("line.separator") + "");
                java.awt.EventQueue.invokeLater(txtUser::requestFocusInWindow);
                regUsuario = null;
                return;
            }
            txtUser.setText(regUsuario.getUser());
            txtPass.setText("");
            txtNombre.setText(regUsuario.getNombre());
            chkActivo.setSelected(regUsuario.getActive().equals("true"));
            txtUser.setEnabled(false);
            txtPass.setEnabled(false);
            btnCancelar.setEnabled(true);
            java.awt.EventQueue.invokeLater(txtNombre::requestFocusInWindow);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar el Usuario" + System.getProperty("line.separator") + inex);
            java.awt.EventQueue.invokeLater(txtUser::requestFocusInWindow);
            regUsuario = null;
            logger.error(inex);
        }
    }

    /**
     */
    private void actCancelar() {
        regUsuario = null;
        txtUser.setText("");
        txtPass.setText("");
        txtNombre.setText("");
        chkActivo.setSelected(false);
        btnCargar.setEnabled(true);
        btnCancelar.setEnabled(false);
        java.awt.EventQueue.invokeLater(txtUser::requestFocusInWindow);
    }

    private void actReconversion() {
        final String user = UserPassIn.getUser();
        if ((user == null) || ((!user.equals("ADMIN") && (!user.equals("DEBUG"))))) {
            return;
        }
        if (UserPassIn.getIdUser() > 0) {
            final String fecha = Globales.getServerSqlDate().toString();
            final int mes = Integer.valueOf(fecha.substring(5, 7));
            final ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/BCV_logo.png"));
            if (im != null) {
                JOptionPane.showMessageDialog(this, "Esta Operación es IRREVERSIBLE, " + System.getProperty("line.separator") + "cualquier arrepentimiento generara un costo equivalente a 100$ Americanos (USD), " + System.getProperty("line.separator") + "totalmente imputable al Cliente.", "Reconversión Monetaria 2021 I", JOptionPane.WARNING_MESSAGE, im);
            } else {
                JOptionPane.showMessageDialog(this, "Esta Operación es IRREVERSIBLE, " + System.getProperty("line.separator") + "cualquier arrepentimiento generara un costo equivalente a 100$ Americanos (USD), " + System.getProperty("line.separator") + "totalmente imputable al Cliente.", "Reconversión Monetaria 2021 I", JOptionPane.WARNING_MESSAGE);
            }
            if (JOptionPane.showConfirmDialog(this, "Esta Absolutamente seguro de continuar (1) ?") == JOptionPane.OK_OPTION) {
                if (JOptionPane.showConfirmDialog(this, "De Verdad esta seguro de continuar (2) ?") == JOptionPane.OK_OPTION) {
                    if (JOptionPane.showConfirmDialog(this, "Si se equivoca tendra que pagar 100 USD" + System.getProperty("line.separator") + "Desea continuar con la reconversion (3) ?") == JOptionPane.OK_OPTION) {
                        final String clave = JOptionPane.showInputDialog("Favor indicar la clave de seguridad").toUpperCase();
                        if (!clave.equals("PB407111787")) {
                            JOptionPane.showMessageDialog(this, "Clave erronea" + System.getProperty("line.separator") + "Operación abortada");
                            return;
                        }
                        final String sFactor = JOptionPane.showInputDialog("Por favor introducir valor de reconversion. menos seis ceros es igual a 1000000").toUpperCase();
                        if (sFactor == null || sFactor.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Factor inválido" + System.getProperty("line.separator") + "Operación abortada");
                            return;
                        }
                        int iFactor = 1;
                        try {
                            iFactor = Integer.parseInt(sFactor);
                        } catch (Exception inex) {
                            JOptionPane.showMessageDialog(this, "Factor inválido" + System.getProperty("line.separator") + "Operación abortada" + System.getProperty("line.separator") + inex);
                            logger.error(inex);
                            return;
                        }
                        if (!(JOptionPane.showConfirmDialog(this, "Esta seguro de aplicar un Factor de " + iFactor + " a la Base de Datos" + System.getProperty("line.separator") + "Desea continuar con la reconversion (4) ?") == JOptionPane.OK_OPTION)) {
                            JOptionPane.showMessageDialog(this, "Factor inválido" + System.getProperty("line.separator") + "Operación abortada");
                            return;
                        }
                        try {
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            ConnCapip.getInstance().BeginTransaction();
                            ConnCapip.getInstance().executeUpdate("UPDATE ava_efe_aux_report SET total = ROUND(total / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE ava_efe_rpt_summary SET total = ROUND(total / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE avance_efectivo SET a_pagar_bs = ROUND(a_pagar_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE banco_saldo_anual SET saldoi = ROUND(saldoi / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE bancos SET saldoi = ROUND(saldoi / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE bancos_ch_anu SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE bancos_ch_anu_aux_rpt SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE bancos_cheque SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE bancos_operaciones SET debe = ROUND(debe / " + sFactor + ", 2), haber = ROUND(haber / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE cau_rpt_summary SET total = ROUND(total / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE causado SET resta_bs = ROUND(resta_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE causado_aux_report SET total = ROUND(total / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE causado_avance_efectivo_nn SET apagar_bs = ROUND(apagar_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE causado_det SET subtotal = ROUND(subtotal / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE compr_compra SET total_bs = ROUND(total_bs / " + sFactor + ", 2)," + "base_imponible_bs = ROUND(base_imponible_bs / " + sFactor + ", 2), " + "iva_grav_bs = ROUND(iva_grav_bs / " + sFactor + ", 2), " + "iva_porc_aplic = ROUND(iva_porc_aplic / " + sFactor + ", 2), " + "iva_porc_ret = ROUND(iva_porc_ret / " + sFactor + ", 2), " + "islr_grav_bs = ROUND(islr_grav_bs / " + sFactor + ", 2), " + "islr_porc_ret = ROUND(islr_porc_ret / " + sFactor + ", 2), " + "imp_mun_grav_bs = ROUND(imp_mun_grav_bs / " + sFactor + ", 2), " + "imp_mun_bs = ROUND(imp_mun_bs / " + sFactor + ", 2), " + "neg_pri_grav_bs = ROUND(neg_pri_grav_bs / " + sFactor + ", 2), " + "neg_pri_bs = ROUND(neg_pri_bs / " + sFactor + ", 2), " + "oret_grav_bs = ROUND(oret_grav_bs / " + sFactor + ", 2), " + "oret_bs = ROUND(oret_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE compr_det SET punitario = ROUND(punitario / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE compr_otros SET total_bs = ROUND(total_bs / " + sFactor + ", 2)," + "base_imponible_bs = ROUND(base_imponible_bs / " + sFactor + ", 2), " + "iva_grav_bs = ROUND(iva_grav_bs / " + sFactor + ", 2), " + "iva_porc_aplic = ROUND(iva_porc_aplic / " + sFactor + ", 2), " + "iva_porc_ret = ROUND(iva_porc_ret / " + sFactor + ", 2), " + "islr_grav_bs = ROUND(islr_grav_bs / " + sFactor + ", 2), " + "islr_porc_ret = ROUND(islr_porc_ret / " + sFactor + ", 2), " + "imp_mun_grav_bs = ROUND(imp_mun_grav_bs / " + sFactor + ", 2), " + "imp_mun_bs = ROUND(imp_mun_bs / " + sFactor + ", 2), " + "neg_pri_grav_bs = ROUND(neg_pri_grav_bs / " + sFactor + ", 2), " + "neg_pri_bs = ROUND(neg_pri_bs / " + sFactor + ", 2), " + "oret_grav_bs = ROUND(oret_grav_bs / " + sFactor + ", 2), " + "oret_bs = ROUND(oret_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE compr_rpt_summary SET total = ROUND(total / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE compr_servicio SET total_bs = ROUND(total_bs / " + sFactor + ", 2)," + "base_imponible_bs = ROUND(base_imponible_bs / " + sFactor + ", 2), " + "iva_grav_bs = ROUND(iva_grav_bs / " + sFactor + ", 2), " + "iva_porc_aplic = ROUND(iva_porc_aplic / " + sFactor + ", 2), " + "iva_porc_ret = ROUND(iva_porc_ret / " + sFactor + ", 2), " + "islr_grav_bs = ROUND(islr_grav_bs / " + sFactor + ", 2), " + "islr_porc_ret = ROUND(islr_porc_ret / " + sFactor + ", 2), " + "imp_mun_grav_bs = ROUND(imp_mun_grav_bs / " + sFactor + ", 2), " + "imp_mun_bs = ROUND(imp_mun_bs / " + sFactor + ", 2), " + "neg_pri_grav_bs = ROUND(neg_pri_grav_bs / " + sFactor + ", 2), " + "neg_pri_bs = ROUND(neg_pri_bs / " + sFactor + ", 2), " + "oret_grav_bs = ROUND(oret_grav_bs / " + sFactor + ", 2), " + "oret_bs = ROUND(oret_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE creditoadicional SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE creditoadicional_det SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE creditoadicional_rpt_summary SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE imp_mun_det SET total_fact = ROUND(total_fact / " + sFactor + ", 2), " + "base_imponible = ROUND(base_imponible / " + sFactor + ", 2), " + "gravable_bs = ROUND(gravable_bs / " + sFactor + ", 2), " + "retenido_bs = ROUND(retenido_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE islr_retencion_det SET total_fact = ROUND(total_fact / " + sFactor + ", 2), " + "base_imponible = ROUND(base_imponible / " + sFactor + ", 2), " + "gravable_bs = ROUND(gravable_bs / " + sFactor + ", 2), " + "islr_porc_ret = ROUND(islr_porc_ret / " + sFactor + ", 2), " + "islr_retenido_bs = ROUND(islr_retenido_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE iva_retencion_det SET total_fact = ROUND(total_fact / " + sFactor + ", 2), " + "exento = ROUND(exento / " + sFactor + ", 2), " + "base_imponible = ROUND(base_imponible / " + sFactor + ", 2), " + "iva_porc_aplic = ROUND(iva_porc_aplic / " + sFactor + ", 2), " + "iva_bs = ROUND(iva_bs / " + sFactor + ", 2), " + "iva_retenido = ROUND(iva_retenido / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE neg_pri_det SET total_fact = ROUND(total_fact / " + sFactor + ", 2), " + "base_imponible = ROUND(base_imponible / " + sFactor + ", 2), " + "gravable_bs = ROUND(gravable_bs / " + sFactor + ", 2), " + "retenido_bs = ROUND(retenido_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE orden_pago SET total_bs = ROUND(total_bs / " + sFactor + ", 2)," + "apagar_bs = ROUND(apagar_bs / " + sFactor + ", 2), " + "iva_bs = ROUND(iva_bs / " + sFactor + ", 2), " + "resta_bs = ROUND(resta_bs / " + sFactor + ", 2), " + "iva_ret_bs = ROUND(iva_ret_bs / " + sFactor + ", 2), " + "islr_ret_bs = ROUND(islr_ret_bs / " + sFactor + ", 2), " + "imp_mun_ret_bs = ROUND(imp_mun_ret_bs / " + sFactor + ", 2), " + "neg_pri_ret_bs = ROUND(neg_pri_ret_bs / " + sFactor + ", 2), " + "otras_ret_bs = ROUND(otras_ret_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE ordenpago_aux_report SET total = ROUND(total / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE ordenpago_rpt_summary SET total = ROUND(total / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE otras_ret_det SET total_fact = ROUND(total_fact / " + sFactor + ", 2), " + "base_imponible = ROUND(base_imponible / " + sFactor + ", 2), " + "gravable_bs = ROUND(gravable_bs / " + sFactor + ", 2), " + "retenido_bs = ROUND(retenido_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE presupe SET monto_ini = ROUND(monto_ini / " + sFactor + ", 2), " + "monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE presupi SET monto_ini = ROUND(monto_ini / " + sFactor + ", 2), " + "monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE tmppptoadicional SET monto_ini = ROUND(monto_ini / " + sFactor + ", 2), " + "monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE trasp_part_rpt_summary SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE trasppartidas SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE trasppartidas_det SET monto = ROUND(monto / " + sFactor + ", 2)");
                            ConnCapip.getInstance().executeUpdate("UPDATE unidad_tributaria_aplic SET valor_bs = ROUND(valor_bs / " + sFactor + ", 2)");
                            ConnCapip.getInstance().EndTransaction();
                            JOptionPane.showMessageDialog(this, "Operación realizada" + System.getProperty("line.separator") + "Recuerde, son 100USD para intentar recuperar los datos originales");
                        } catch (Exception inex) {
                            try {
                                ConnCapip.getInstance().RollBack();
                            } catch (Exception inex2) {
                                JOptionPane.showMessageDialog(this, "Error al tratar de reversar la operación" + System.getProperty("line.separator") + inex2);
                                logger.error(inex2);
                            }
                            JOptionPane.showMessageDialog(this, "Error al tratar de actualizar la Base de Datod" + System.getProperty("line.separator") + inex);
                            logger.error(inex);
                        } finally {
                            setCursor(null);
                        }
                    }
                }
            }
        }
    }

    private static final Logger logger = LogManager.getLogger(Configuracion.class);
}
