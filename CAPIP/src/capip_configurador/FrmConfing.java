/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capip_configurador;

import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import static java.lang.Math.min;
import static java.lang.System.exit;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class FrmConfing extends javax.swing.JFrame {

    private static final String CAPIP_SISTEMAS_PROPERTIES = "CapipSistemas.properties";

    private static final String CAPIP_CLIENTE_RAZONSOCIAL = "Versión Evaluación";
    private static final String CAPIP_CLIENTE_RIF_CI = "J000000000";
    private static final String CAPIP_CLIENTE_DOMICILIO_FISCAL = "Venezuela";
    private static final String CAPIP_LICENCIA = "Capip Sistemas C.A.";
    private static final java.util.Date CAPIP_FECHA_LICENCIA = new java.util.Date();
    private static final String CAPIP_SERVIDOR = "capip_servidor";
    private static final String CAPIP_DIR_SERVIDOR = "C:/CAPIP_SISTEMAS";
    private static final String CAPIP_DIR_LOCAL = "C:/CAPIP_SISTEMAS";
    private static final String CAPIP_BASE_DATOS = "capip";
    private static final String CAPIP_USUARIO_BD = "root";
    private static final String CAPIP_CLAVE_BD = "1234";

    public static final String CAPIP_NUM_PAGO = "ABSOLUTO";
    public static final String CAPIP_PATH_MYSQLDUMP = "C:/Program Files/MySQL/MySQL Server 5.7/bin";
    public static final String CAPIP_DIR_LOCAL_COMPROMISO = CAPIP_DIR_LOCAL + "/COMPROMISO";
    public static final String CAPIP_DIR_LOCAL_CAUSADO = CAPIP_DIR_LOCAL + "/CAUSADO";
    public static final String CAPIP_DIR_LOCAL_IVA = CAPIP_DIR_LOCAL + "/IVA";
    public static final String CAPIP_DIR_LOCAL_ISLR = CAPIP_DIR_LOCAL + "/ISLR";
    public static final String CAPIP_DIR_LOCAL_ORDENPAGO = CAPIP_DIR_LOCAL + "/ORDENPAGO";

    private static final String CAPIP_LINEA_1 = "LINEA 1";
    private static final String CAPIP_LINEA_2 = "LINEA 2";
    private static final String CAPIP_LINEA_3 = "LINEA 3";
    private static final String CAPIP_LINEA_4 = "LINEA 4";
    private static final String CAPIP_AUX_1 = "AUX_1";
    private static final String CAPIP_AUX_2 = "AUX_2";
    private static final StringBuilder CAPIP_AUX_3 = new StringBuilder("J407111787+1");

    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    private static final boolean SO_WIN;

    private final StringBuilder strB;

    static {
        SO_WIN = System.getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    /**
     * Creates new form Frm_Principal
     *
     * @param _aux
     */
    public FrmConfing(final StringBuilder _aux) {

        if (_aux != null) {
            strB = _aux;
        } else {
            strB = new StringBuilder();
        }

        initComponents();
        Configurar();
    }

    /**
     *
     */
    private void Configurar() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                mnuSalir.doClick();
            }
        });

        actInit();
    }

    /**
     *
     */
    private void CargarPropiedades() {
        try {
//            nvbnbv // Primero verificar si ya existe un archivo de propiedades en el sistema
            final InputStream fPropLocal = new FileInputStream(CAPIP_DIR_LOCAL + "/" + CAPIP_SISTEMAS_PROPERTIES);

            // No existe un directorio local de propiedades
            final boolean isLoadLocal;
            if (fPropLocal == null) {
                isLoadLocal = false;
            } else {
                isLoadLocal = JOptionPane.showConfirmDialog(this, "Desea cargar el archivo de Propiedadees Local",
                    "Confirmar acción",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
            }

            // Verificar si se va a cargar el archivo de propiedades local
            final boolean isLoadDefult;
            if (isLoadLocal) {
                txtCifClave.setText("");
                txtCifReClave.setText("");

                // Aqui se solicita el password, y por retrocall a SetPass, se establece la contraseña
                new PropLocalPass(this).setVisible(true);

                // cabe la posibilidad de que el usuario no haya introducido alguna clave
                // por lo que se debe de leer de la opcion por defecto
                isLoadDefult = txtCifClave.getPassword().length == 0;

            } else {
                isLoadDefult = true;
            }

            if (isLoadDefult) {
                // Buscar el archivo de propiedades por defecto
                //
                // El archivo de propiedades por defecto, debe estar ubicado en el mismo directorio que el Configurador
                InputStream is = getClass().getResourceAsStream(CAPIP_SISTEMAS_PROPERTIES);

                // No existe el archivo de propiedades por defecto, asi que se cargara los valores por defecto
                if (is == null) {
                    CargarPropPorDefecto();
                } else {
                    // cargar el archivo de propiedades por defecto
                    CargarProp(is);
                }
            } else {
                CargarPropLocal(fPropLocal);
            }

        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de ubicar el archivo de Propiedades" + System.getProperty("line.separator") + _ex);
            ClearFields();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tbbPanel = new javax.swing.JTabbedPane();
        pnlCifrado = new javax.swing.JPanel();
        pnlClave = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCifClave = new javax.swing.JPasswordField();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        txtCifReClave = new javax.swing.JPasswordField();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        jLabel2 = new javax.swing.JLabel();
        txtCifBytes = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        pnlCampos = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCifCliente = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtCifRifCi = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtDomicilioFiscal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCifLicencia = new javax.swing.JPasswordField();
        jLabel21 = new javax.swing.JLabel();
        txtCifFechaLicencia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCifServidor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCifDirServidor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCifDirLocal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCifBaseDatos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCifUsuarioBD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCifClaveBD = new javax.swing.JPasswordField();
        pnlComandos = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnClear = new javax.swing.JButton();
        filler15 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        btnGenerar = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        btnCifCancelar = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        pnlReportes = new javax.swing.JPanel();
        pnlCampos2 = new javax.swing.JPanel();
        txtLinea_4 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtAux_2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtLinea_2 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtLinea_3 = new javax.swing.JTextField();
        txtLinea_1 = new javax.swing.JTextField();
        txtAux_1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPath_mysqldump = new javax.swing.JTextField();
        chkNumPago = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        pnlComandos2 = new javax.swing.JPanel();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnGenerar_1 = new javax.swing.JButton();
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        btnDesCancelar1 = new javax.swing.JButton();
        filler14 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        mnuBar = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        mnuGenerar = new javax.swing.JMenuItem();
        mnuSalir = new javax.swing.JMenuItem();
        mnuAyuda = new javax.swing.JMenu();
        mnuAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("CAPIP Configurador");
        setResizable(false);

        tbbPanel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        pnlCifrado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnlCifrado.setLayout(new javax.swing.BoxLayout(pnlCifrado, javax.swing.BoxLayout.PAGE_AXIS));

        pnlClave.setMinimumSize(new java.awt.Dimension(40, 30));
        pnlClave.setPreferredSize(new java.awt.Dimension(395, 30));
        pnlClave.setLayout(new javax.swing.BoxLayout(pnlClave, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Clave Secreta ");
        jLabel1.setMaximumSize(new java.awt.Dimension(150, 30));
        jLabel1.setMinimumSize(new java.awt.Dimension(150, 30));
        jLabel1.setPreferredSize(new java.awt.Dimension(150, 30));
        pnlClave.add(jLabel1);

        txtCifClave.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifClave.setMaximumSize(new java.awt.Dimension(125, 30));
        txtCifClave.setMinimumSize(new java.awt.Dimension(125, 30));
        txtCifClave.setPreferredSize(new java.awt.Dimension(125, 30));
        txtCifClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCifClaveActionPerformed(evt);
            }
        });
        txtCifClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCifClaveKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCifClaveKeyTyped(evt);
            }
        });
        pnlClave.add(txtCifClave);
        pnlClave.add(filler9);

        txtCifReClave.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifReClave.setMaximumSize(new java.awt.Dimension(125, 30));
        txtCifReClave.setMinimumSize(new java.awt.Dimension(125, 30));
        txtCifReClave.setPreferredSize(new java.awt.Dimension(125, 30));
        txtCifReClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCifReClaveActionPerformed(evt);
            }
        });
        pnlClave.add(txtCifReClave);
        pnlClave.add(filler11);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Bytes ");
        jLabel2.setMaximumSize(new java.awt.Dimension(50, 30));
        jLabel2.setMinimumSize(new java.awt.Dimension(50, 30));
        jLabel2.setPreferredSize(new java.awt.Dimension(50, 30));
        pnlClave.add(jLabel2);

        txtCifBytes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCifBytes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCifBytes.setEnabled(false);
        txtCifBytes.setMaximumSize(new java.awt.Dimension(50, 30));
        txtCifBytes.setMinimumSize(new java.awt.Dimension(50, 30));
        txtCifBytes.setPreferredSize(new java.awt.Dimension(50, 30));
        pnlClave.add(txtCifBytes);
        pnlClave.add(filler1);

        pnlCifrado.add(pnlClave);

        pnlCampos.setMaximumSize(new java.awt.Dimension(600, 2147483647));
        pnlCampos.setMinimumSize(new java.awt.Dimension(600, 25));
        pnlCampos.setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setLabelFor(txtCifCliente);
        jLabel8.setText("Cliente");
        jLabel8.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel8.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel8.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel8, gridBagConstraints);

        txtCifCliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifCliente.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifCliente.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifCliente.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifCliente, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setLabelFor(txtCifRifCi);
        jLabel32.setText("RIF / C.I.");
        jLabel32.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel32.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel32.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel32, gridBagConstraints);

        txtCifRifCi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifRifCi.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifRifCi.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifRifCi.setPreferredSize(new java.awt.Dimension(250, 25));
        txtCifRifCi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCifRifCiKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifRifCi, gridBagConstraints);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setLabelFor(txtDomicilioFiscal);
        jLabel33.setText("Domicilio Fiscal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel33, gridBagConstraints);

        txtDomicilioFiscal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDomicilioFiscal.setMaximumSize(new java.awt.Dimension(250, 25));
        txtDomicilioFiscal.setMinimumSize(new java.awt.Dimension(250, 25));
        txtDomicilioFiscal.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtDomicilioFiscal, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setLabelFor(txtCifLicencia);
        jLabel6.setText("Licencia");
        jLabel6.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel6.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel6.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel6, gridBagConstraints);

        txtCifLicencia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifLicencia.setEnabled(false);
        txtCifLicencia.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifLicencia.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifLicencia.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifLicencia, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setLabelFor(txtCifFechaLicencia);
        jLabel21.setText("Fecha Licencia");
        jLabel21.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel21.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel21.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel21, gridBagConstraints);

        txtCifFechaLicencia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifFechaLicencia.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifFechaLicencia.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifFechaLicencia.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifFechaLicencia, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setLabelFor(txtCifServidor);
        jLabel4.setText("Servidor");
        jLabel4.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel4.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel4.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel4, gridBagConstraints);

        txtCifServidor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifServidor.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifServidor.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifServidor.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifServidor, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setLabelFor(txtCifDirServidor);
        jLabel3.setText("Directorio Servidor");
        jLabel3.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel3.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel3, gridBagConstraints);

        txtCifDirServidor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifDirServidor.setEnabled(false);
        txtCifDirServidor.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifDirServidor.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifDirServidor.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifDirServidor, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setLabelFor(txtCifDirLocal);
        jLabel9.setText("Directorio Local");
        jLabel9.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel9.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel9.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel9, gridBagConstraints);

        txtCifDirLocal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifDirLocal.setEnabled(false);
        txtCifDirLocal.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifDirLocal.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifDirLocal.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifDirLocal, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setLabelFor(txtCifBaseDatos);
        jLabel10.setText("Base de Datos");
        jLabel10.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel10.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel10.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel10, gridBagConstraints);

        txtCifBaseDatos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifBaseDatos.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifBaseDatos.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifBaseDatos.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifBaseDatos, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setLabelFor(txtCifUsuarioBD);
        jLabel7.setText("Usuario B.D.");
        jLabel7.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel7.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel7.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel7, gridBagConstraints);

        txtCifUsuarioBD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifUsuarioBD.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifUsuarioBD.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifUsuarioBD.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifUsuarioBD, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setLabelFor(txtCifClaveBD);
        jLabel5.setText("Clave B.D.");
        jLabel5.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel5.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel5.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(jLabel5, gridBagConstraints);

        txtCifClaveBD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCifClaveBD.setMaximumSize(new java.awt.Dimension(250, 25));
        txtCifClaveBD.setMinimumSize(new java.awt.Dimension(250, 25));
        txtCifClaveBD.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos.add(txtCifClaveBD, gridBagConstraints);

        pnlCifrado.add(pnlCampos);

        pnlComandos.setMaximumSize(new java.awt.Dimension(600, 40));
        pnlComandos.setMinimumSize(new java.awt.Dimension(600, 40));
        pnlComandos.setPreferredSize(new java.awt.Dimension(600, 40));
        pnlComandos.setLayout(new javax.swing.BoxLayout(pnlComandos, javax.swing.BoxLayout.LINE_AXIS));
        pnlComandos.add(filler2);

        btnClear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnClear.setText("Inicializar");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        pnlComandos.add(btnClear);
        pnlComandos.add(filler15);

        btnGenerar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });
        pnlComandos.add(btnGenerar);
        pnlComandos.add(filler3);

        btnCifCancelar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCifCancelar.setText("Cancelar");
        btnCifCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCifCancelarActionPerformed(evt);
            }
        });
        pnlComandos.add(btnCifCancelar);
        pnlComandos.add(filler4);

        pnlCifrado.add(pnlComandos);

        tbbPanel.addTab("Globales", pnlCifrado);

        pnlReportes.setLayout(new javax.swing.BoxLayout(pnlReportes, javax.swing.BoxLayout.PAGE_AXIS));

        pnlCampos2.setMaximumSize(new java.awt.Dimension(600, 2147483647));
        pnlCampos2.setMinimumSize(new java.awt.Dimension(600, 25));
        pnlCampos2.setLayout(new java.awt.GridBagLayout());

        txtLinea_4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLinea_4.setDisabledTextColor(new java.awt.Color(0, 153, 0));
        txtLinea_4.setMaximumSize(new java.awt.Dimension(250, 25));
        txtLinea_4.setMinimumSize(new java.awt.Dimension(250, 25));
        txtLinea_4.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(txtLinea_4, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Línea 4");
        jLabel24.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel24.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel24.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(jLabel24, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Aux 2");
        jLabel25.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel25.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel25.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(jLabel25, gridBagConstraints);

        txtAux_2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtAux_2.setDisabledTextColor(new java.awt.Color(0, 153, 0));
        txtAux_2.setMaximumSize(new java.awt.Dimension(250, 25));
        txtAux_2.setMinimumSize(new java.awt.Dimension(250, 25));
        txtAux_2.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(txtAux_2, gridBagConstraints);

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Línea 2");
        jLabel26.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel26.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel26.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(jLabel26, gridBagConstraints);

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Aux 1");
        jLabel27.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel27.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel27.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(jLabel27, gridBagConstraints);

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Línea 1");
        jLabel28.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel28.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel28.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(jLabel28, gridBagConstraints);

        txtLinea_2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLinea_2.setDisabledTextColor(new java.awt.Color(0, 153, 0));
        txtLinea_2.setMaximumSize(new java.awt.Dimension(250, 25));
        txtLinea_2.setMinimumSize(new java.awt.Dimension(250, 25));
        txtLinea_2.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(txtLinea_2, gridBagConstraints);

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Línea 3");
        jLabel31.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel31.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel31.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(jLabel31, gridBagConstraints);

        txtLinea_3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLinea_3.setDisabledTextColor(new java.awt.Color(0, 153, 0));
        txtLinea_3.setMaximumSize(new java.awt.Dimension(250, 25));
        txtLinea_3.setMinimumSize(new java.awt.Dimension(250, 25));
        txtLinea_3.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(txtLinea_3, gridBagConstraints);

        txtLinea_1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLinea_1.setDisabledTextColor(new java.awt.Color(0, 153, 0));
        txtLinea_1.setMaximumSize(new java.awt.Dimension(250, 25));
        txtLinea_1.setMinimumSize(new java.awt.Dimension(250, 25));
        txtLinea_1.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(txtLinea_1, gridBagConstraints);

        txtAux_1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtAux_1.setDisabledTextColor(new java.awt.Color(0, 153, 0));
        txtAux_1.setMaximumSize(new java.awt.Dimension(250, 25));
        txtAux_1.setMinimumSize(new java.awt.Dimension(250, 25));
        txtAux_1.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(txtAux_1, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Path mysqldump");
        jLabel11.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel11.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel11.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(jLabel11, gridBagConstraints);

        txtPath_mysqldump.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtPath_mysqldump.setToolTipText("Utilice el /, como separardor, ejem. C:/CARPETA");
        txtPath_mysqldump.setMaximumSize(new java.awt.Dimension(250, 25));
        txtPath_mysqldump.setMinimumSize(new java.awt.Dimension(250, 25));
        txtPath_mysqldump.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(txtPath_mysqldump, gridBagConstraints);

        chkNumPago.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        chkNumPago.setSelected(true);
        chkNumPago.setText("Absoluto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(chkNumPago, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Número de Pago");
        jLabel13.setMaximumSize(new java.awt.Dimension(150, 25));
        jLabel13.setMinimumSize(new java.awt.Dimension(150, 25));
        jLabel13.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        pnlCampos2.add(jLabel13, gridBagConstraints);

        pnlReportes.add(pnlCampos2);

        pnlComandos2.setMaximumSize(new java.awt.Dimension(600, 40));
        pnlComandos2.setMinimumSize(new java.awt.Dimension(600, 40));
        pnlComandos2.setPreferredSize(new java.awt.Dimension(600, 40));
        pnlComandos2.setLayout(new javax.swing.BoxLayout(pnlComandos2, javax.swing.BoxLayout.LINE_AXIS));
        pnlComandos2.add(filler12);

        btnGenerar_1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnGenerar_1.setText("Generar");
        btnGenerar_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerar_1ActionPerformed(evt);
            }
        });
        pnlComandos2.add(btnGenerar_1);
        pnlComandos2.add(filler13);

        btnDesCancelar1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnDesCancelar1.setText("Cancelar");
        btnDesCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesCancelar1ActionPerformed(evt);
            }
        });
        pnlComandos2.add(btnDesCancelar1);
        pnlComandos2.add(filler14);

        pnlReportes.add(pnlComandos2);

        tbbPanel.addTab("Reportes", pnlReportes);

        getContentPane().add(tbbPanel, java.awt.BorderLayout.CENTER);

        mnuArchivo.setText("Archivo");
        mnuArchivo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        mnuGenerar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK));
        mnuGenerar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnuGenerar.setText("Generar");
        mnuGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGenerarActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuGenerar);

        mnuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, java.awt.event.InputEvent.CTRL_MASK));
        mnuSalir.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnuSalir.setText("Salir");
        mnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalirActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuSalir);

        mnuBar.add(mnuArchivo);

        mnuAyuda.setText("Ayuda");
        mnuAyuda.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        mnuAcercaDe.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnuAcercaDe.setText("Acerca de ...");
        mnuAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAcercaDeActionPerformed(evt);
            }
        });
        mnuAyuda.add(mnuAcercaDe);

        mnuBar.add(mnuAyuda);

        setJMenuBar(mnuBar);

        setSize(new java.awt.Dimension(621, 555));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnuAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAcercaDeActionPerformed
        new DlgAcercaDe(this).setVisible(true);
    }//GEN-LAST:event_mnuAcercaDeActionPerformed

    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        actSalir();
    }//GEN-LAST:event_mnuSalirActionPerformed

    private void mnuGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGenerarActionPerformed
        actGenerar();
    }//GEN-LAST:event_mnuGenerarActionPerformed

    private void btnDesCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesCancelar1ActionPerformed
        mnuSalir.doClick();
    }//GEN-LAST:event_btnDesCancelar1ActionPerformed

    private void btnGenerar_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerar_1ActionPerformed
        btnGenerar.doClick();
    }//GEN-LAST:event_btnGenerar_1ActionPerformed

    private void btnCifCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCifCancelarActionPerformed
        mnuSalir.doClick();
    }//GEN-LAST:event_btnCifCancelarActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        actGenerar();
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        actInit();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtCifRifCiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCifRifCiKeyTyped
        if (!"JGVE0123456789".contains(String.valueOf(evt.getKeyChar())) || txtCifRifCi.getText().length() >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCifRifCiKeyTyped

    private void txtCifReClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCifReClaveActionPerformed
        txtCifReClave.transferFocus();
    }//GEN-LAST:event_txtCifReClaveActionPerformed

    private void txtCifClaveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCifClaveKeyTyped
        if (txtCifClave.getPassword().length >= 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCifClaveKeyTyped

    private void txtCifClaveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCifClaveKeyReleased
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                txtCifBytes.setText(String.valueOf(txtCifClave.getPassword().length));
            }
        });
    }//GEN-LAST:event_txtCifClaveKeyReleased

    private void txtCifClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCifClaveActionPerformed
        txtCifClave.transferFocus();
    }//GEN-LAST:event_txtCifClaveActionPerformed

    private void actSalir() {
        java.lang.System.exit(0);
    }

    /**
     *
     */
    private void actInit() {

        ClearFields();

        CargarPropiedades();

        java.awt.EventQueue.invokeLater(txtCifClave::requestFocusInWindow);
    }

    /**
     *
     */
    private void actGenerar() {

        if (txtCifClave.getPassword().length < 4) {
            JOptionPane.showMessageDialog(this, "Error. Campo Clave Secreta, demasiado corto");
            tbbPanel.setSelectedIndex(0);
            txtCifClave.requestFocusInWindow();
            return;
        }

        if (!Arrays.equals(txtCifClave.getPassword(), txtCifReClave.getPassword())) {
            JOptionPane.showMessageDialog(this, "Error. Claves diferentes");
            txtCifReClave.setText("");
            tbbPanel.setSelectedIndex(0);
            txtCifClave.requestFocusInWindow();
            return;
        }

        final String cliente = txtCifCliente.getText().trim().toUpperCase();
        if (cliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo Cliente, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifCliente.requestFocusInWindow();
            return;
        }

        String rif_ci = txtCifRifCi.getText().trim().toUpperCase();
        if (rif_ci.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo RIF / C.I., no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifRifCi.requestFocusInWindow();
            return;
        }

        if (rif_ci.isEmpty()) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido");
            tbbPanel.setSelectedIndex(0);
            java.awt.EventQueue.invokeLater(txtCifRifCi::requestFocusInWindow);
            return;
        }

        final char priC = rif_ci.charAt(0);

        if ((priC != 'J') && (priC != 'G') && (priC != 'V') && (priC != 'E')) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Debe comenzar con J/G/V/E");
            tbbPanel.setSelectedIndex(0);
            java.awt.EventQueue.invokeLater(txtCifRifCi::requestFocusInWindow);
            return;
        }

        if ((rif_ci.length() < 7) || (rif_ci.length() > 10)) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Longitud fuera de rango");
            tbbPanel.setSelectedIndex(0);
            java.awt.EventQueue.invokeLater(txtCifRifCi::requestFocusInWindow);
            return;
        }

        final String sNum = rif_ci.substring(1, rif_ci.length());

        if (((priC == 'J') || (priC == 'G')) && sNum.length() != 9) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Cantidad de caracteres debe ser 9");
            tbbPanel.setSelectedIndex(0);
            java.awt.EventQueue.invokeLater(txtCifRifCi::requestFocusInWindow);
            return;
        }

        final Integer iNum;
        try {
            iNum = Integer.parseInt(sNum);
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Error en el formato del Número ");
            tbbPanel.setSelectedIndex(0);
            java.awt.EventQueue.invokeLater(txtCifRifCi::requestFocusInWindow);
            return;
        }

        if (iNum < 0) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Error en el formato del Número ");
            tbbPanel.setSelectedIndex(0);
            java.awt.EventQueue.invokeLater(txtCifRifCi::requestFocusInWindow);
            return;
        }

        final String domicilio_fiscal = txtDomicilioFiscal.getText().trim().toUpperCase();
        if (domicilio_fiscal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo Domicilio Fiscal, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtDomicilioFiscal.requestFocusInWindow();
            return;
        }

        if (txtCifLicencia.getPassword().length <= 0) {
            JOptionPane.showMessageDialog(this, "Error. Campo Licencia, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifLicencia.requestFocusInWindow();
            return;
        }

        java.util.Date fechaLicencia;
        try {
            fechaLicencia = df.parse(txtCifFechaLicencia.getText());
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error. Campo Fecha de Licencia inválido");
            tbbPanel.setSelectedIndex(0);
            txtCifFechaLicencia.requestFocusInWindow();
            return;
        }

        final String servidor = txtCifServidor.getText().trim().toLowerCase();
        if (servidor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo Servidor, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifServidor.requestFocusInWindow();
            return;
        }

        final String dirServidor = txtCifDirServidor.getText().trim().toUpperCase();
        if (dirServidor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo Directorio Servidor, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifDirServidor.requestFocusInWindow();
            return;
        }

        final String dirLocal = txtCifDirLocal.getText().trim().toUpperCase();
        if (dirLocal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo Directorio Local, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifDirLocal.requestFocusInWindow();
            return;
        }

        final String dirBaseDatos = txtCifBaseDatos.getText().trim().toLowerCase();
        if (dirBaseDatos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo Base de Datos, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifBaseDatos.requestFocusInWindow();
            return;
        }

        final String usuarioBD = txtCifUsuarioBD.getText().trim().toLowerCase();
        if (usuarioBD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo Usuario de Base de Datos, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifUsuarioBD.requestFocusInWindow();
            return;
        }

        if (txtCifClaveBD.getPassword().length <= 0) {
            JOptionPane.showMessageDialog(this, "Error. Campo Clave de Base de Datos, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtCifClaveBD.requestFocusInWindow();
            return;
        }

        final String num_pago = chkNumPago.isSelected() ? "ABSOLUTO" : "RELATIVO";

        final String path_mysqlDump = txtPath_mysqldump.getText().trim().toLowerCase();
        if (path_mysqlDump.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. El campo de unicación de mysqldump, no puede estar vacío");
            tbbPanel.setSelectedIndex(0);
            txtPath_mysqldump.requestFocusInWindow();
            return;
        }

        String aux = txtLinea_1.getText().trim().toUpperCase();
        if (aux.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error. Líena 1 en blanco");
            tbbPanel.setSelectedIndex(1);
            txtLinea_1.requestFocusInWindow();
        }
        final String linea_1 = aux.substring(0, min(aux.length(), 128));

        aux = txtLinea_2.getText().trim().toUpperCase();
        final String linea_2 = aux.substring(0, min(aux.length(), 128));

        aux = txtLinea_3.getText().trim().toUpperCase();
        final String linea_3 = aux.substring(0, min(aux.length(), 128));

        aux = txtLinea_4.getText().trim().toUpperCase();
        final String linea_4 = aux.substring(0, min(aux.length(), 128));

        aux = txtAux_1.getText().trim().toUpperCase();
        final String aux_1 = aux.substring(0, min(aux.length(), 128));

        aux = txtAux_2.getText().trim().toUpperCase();
        final String aux_2 = aux.substring(0, min(aux.length(), 128));

        // Verificar la existencia de los directorios locales
        chkDirs();

        final File flocal = new File(dirLocal);
        flocal.mkdirs();

        try (final FileOutputStream os = new FileOutputStream(dirLocal + File.separator + CAPIP_SISTEMAS_PROPERTIES)) {

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            Properties prop = new Properties();
            prop.setProperty("CAPIP_CLIENTE_RAZONSOCIAL", encrypt(cliente));
            prop.setProperty("CAPIP_CLIENTE_RIF_CI", encrypt(rif_ci));
            prop.setProperty("CAPIP_CLIENTE_DOMICILIO_FISCAL", encrypt(domicilio_fiscal));
            prop.setProperty("CAPIP_LICENCIA", encrypt(charToByte(txtCifLicencia.getPassword())));
            prop.setProperty("CAPIP_FECHA_LICENCIA", encrypt(txtCifFechaLicencia.getText()));
            prop.setProperty("CAPIP_SERVIDOR", encrypt(servidor));
            prop.setProperty("CAPIP_DIR_SERVIDOR", encrypt(dirServidor));
            prop.setProperty("CAPIP_DIR_LOCAL", encrypt(dirLocal));
            prop.setProperty("CAPIP_BASE_DATOS", encrypt(dirBaseDatos));
            prop.setProperty("CAPIP_USUARIO_BD", encrypt(usuarioBD));
            prop.setProperty("CAPIP_CLAVE_BD", encrypt(charToByte(txtCifClaveBD.getPassword())));

            prop.setProperty("CAPIP_NUM_PAGO", encrypt(num_pago));
            prop.setProperty("CAPIP_PATH_MYSQLDUMP", encrypt(path_mysqlDump));

            prop.setProperty("CAPIP_LINEA_1", encrypt(linea_1));
            prop.setProperty("CAPIP_LINEA_2", encrypt(linea_2));
            prop.setProperty("CAPIP_LINEA_3", encrypt(linea_3));
            prop.setProperty("CAPIP_LINEA_4", encrypt(linea_4));
            prop.setProperty("CAPIP_AUX_1", encrypt(aux_1));
            prop.setProperty("CAPIP_AUX_2", encrypt(aux_2));

            prop.storeToXML(os, "");
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de crear el archivo de propiedades" + System.getProperty("line.separator") + _ex);
            return;
        } finally {
            setCursor(null);
        }

        JOptionPane.showMessageDialog(this, "Operación realizada");
        txtCifClave.setText("");
        txtCifReClave.setText("");
        txtCifBytes.setText("");

        txtCifClave.requestFocusInWindow();

    }

    public String encrypt(final String _strToEncrypt) {
        try {
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(charToByte(Arrays.copyOf(txtCifClave.getPassword(), 16)), "AES"));
            return Base64.getEncoder().encodeToString(cipher.doFinal(_strToEncrypt.getBytes("UTF-8")));
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de cifrar" + System.getProperty("line.separator") + _ex);
        }

        return null;
    }

    /**
     *
     * @param _strToEncrypt
     * @return
     */
    public String encrypt(final byte[] _strToEncrypt) {
        try {
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(charToByte(Arrays.copyOf(txtCifClave.getPassword(), 16)), "AES"));
            return Base64.getEncoder().encodeToString(cipher.doFinal(_strToEncrypt));
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de cifrar" + System.getProperty("line.separator") + _ex);
        }

        return null;
    }

    /**
     *
     * @param _in
     */
    public void SetPass(final char _in[]) {
        txtCifClave.setText(new String(_in));
        txtCifReClave.setText(new String(_in));
    }

    /**
     *
     * @param _strToDecrypt
     * @return
     * @throws Exception
     */
    public String decrypt(final String _strToDecrypt) throws Exception {
        final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Arrays.copyOf(charToByte(txtCifClave.getPassword()), 16), "AES"));
        return new String(cipher.doFinal(Base64.getDecoder().decode(_strToDecrypt)));
    }

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
     *
     */
    private void ClearFields() {
        ClearCifFields();
        ClearRepFields();
    }

    /**
     *
     */
    private void ClearCifFields() {
        txtCifClave.setText("");
        txtCifReClave.setText("");

        txtCifCliente.setText("");
        txtCifLicencia.setText("");
        txtCifFechaLicencia.setText("");
        txtCifServidor.setText("");
        txtCifDirServidor.setText("");
        txtCifDirLocal.setText("");
        txtCifBaseDatos.setText("");
        txtCifUsuarioBD.setText("");
        txtCifClaveBD.setText("");
    }

    /**
     *
     */
    private void ClearRepFields() {
        txtLinea_1.setText("");
        txtLinea_2.setText("");
        txtLinea_3.setText("");
        txtLinea_4.setText("");
        txtAux_1.setText("");
        txtAux_2.setText("");
    }

    /**
     *
     * @param _is
     */
    private void CargarProp(final InputStream _is) {

        Properties prop;
        try {
            prop = new Properties();
            prop.loadFromXML(_is);
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de leer el archivo de propiedades" + System.getProperty("line.separator") + _ex);
            return;
        }

        try {
            txtCifCliente.setText(prop.getProperty("CAPIP_CLIENTE_RAZONSOCIAL", CAPIP_CLIENTE_RAZONSOCIAL));
            txtCifRifCi.setText(prop.getProperty("CAPIP_CLIENTE_RIF_CI", CAPIP_CLIENTE_RIF_CI));
            txtDomicilioFiscal.setText(prop.getProperty("CAPIP_CLIENTE_DOMICILIO_FISCAL", CAPIP_CLIENTE_DOMICILIO_FISCAL));

            txtCifFechaLicencia.setText(prop.getProperty("CAPIP_FECHA_LICENCIA", df.format(CAPIP_FECHA_LICENCIA)));
            txtCifServidor.setText(prop.getProperty("CAPIP_SERVIDOR", CAPIP_LICENCIA).toLowerCase());
            txtCifDirServidor.setText(prop.getProperty("CAPIP_DIR_SERVIDOR", CAPIP_DIR_SERVIDOR).toUpperCase());
            txtCifDirLocal.setText(prop.getProperty("CAPIP_DIR_LOCAL", CAPIP_DIR_LOCAL).toUpperCase());
            txtCifBaseDatos.setText(prop.getProperty("CAPIP_BASE_DATOS", CAPIP_BASE_DATOS).toLowerCase());
            txtCifUsuarioBD.setText(prop.getProperty("CAPIP_USUARIO_BD", CAPIP_USUARIO_BD).toLowerCase());
            txtCifClaveBD.setText(prop.getProperty("CAPIP_CLAVE_BD", CAPIP_CLAVE_BD));

            String aux = prop.getProperty("CAPIP_NUM_PAGO");
            if (aux != null) {
                chkNumPago.setSelected(aux.equals("ABSOLUTO"));
            } else {
                chkNumPago.setSelected(CAPIP_NUM_PAGO.equals("ABSOLUTO"));
            }

            aux = prop.getProperty("CAPIP_PATH_MYSQLDUMP");
            if (aux != null) {
                txtPath_mysqldump.setText(aux);
            } else {
                txtPath_mysqldump.setText(CAPIP_PATH_MYSQLDUMP);
            }
            txtPath_mysqldump.setCaretPosition(0);

            txtLinea_1.setText(prop.getProperty("CAPIP_LINEA_1", CAPIP_LINEA_1));
            txtLinea_2.setText(prop.getProperty("CAPIP_LINEA_2", CAPIP_LINEA_2));
            txtLinea_3.setText(prop.getProperty("CAPIP_LINEA_3", CAPIP_LINEA_3));
            txtLinea_4.setText(prop.getProperty("CAPIP_LINEA_4", CAPIP_LINEA_4));
            txtAux_1.setText(prop.getProperty("CAPIP_AUX_1", CAPIP_AUX_1));
            txtAux_2.setText(prop.getProperty("CAPIP_AUX_2", CAPIP_AUX_2));

        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
            ClearFields();
        }

        txtCifClave.requestFocusInWindow();
    }

    /**
     *
     * @param _is
     */
    private void CargarPropLocal(final InputStream _is) {

        Properties prop;
        try {
            prop = new Properties();
            prop.loadFromXML(_is);
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de leer el archivo de propiedades" + System.getProperty("line.separator") + _ex);
            return;
        }

        try {
            txtCifCliente.setText(decrypt(prop.getProperty("CAPIP_CLIENTE_RAZONSOCIAL")));
            txtCifRifCi.setText(decrypt(prop.getProperty("CAPIP_CLIENTE_RIF_CI")));
            txtDomicilioFiscal.setText(decrypt(prop.getProperty("CAPIP_CLIENTE_DOMICILIO_FISCAL")));
            txtCifLicencia.setText("1234567890");
            txtCifFechaLicencia.setText(decrypt(prop.getProperty("CAPIP_FECHA_LICENCIA")));
            txtCifServidor.setText(decrypt(prop.getProperty("CAPIP_SERVIDOR")));
            txtCifDirServidor.setText(decrypt(prop.getProperty("CAPIP_DIR_SERVIDOR")));
            txtCifDirLocal.setText(decrypt(prop.getProperty("CAPIP_DIR_LOCAL")));
            txtCifBaseDatos.setText(decrypt(prop.getProperty("CAPIP_BASE_DATOS")));
            txtCifUsuarioBD.setText(decrypt(prop.getProperty("CAPIP_USUARIO_BD")));
            txtCifClaveBD.setText(decrypt(prop.getProperty("CAPIP_CLAVE_BD")));

            String aux = prop.getProperty("CAPIP_NUM_PAGO");
            if (aux != null) {
                chkNumPago.setSelected(decrypt(aux).equals("ABSOLUTO"));
            } else {
                chkNumPago.setSelected(CAPIP_NUM_PAGO.equals("ABSOLUTO"));
            }

            aux = prop.getProperty("CAPIP_PATH_MYSQLDUMP");
            if (aux != null) {
                txtPath_mysqldump.setText(decrypt(aux));
            } else {
                txtPath_mysqldump.setText(CAPIP_PATH_MYSQLDUMP);
            }
            txtPath_mysqldump.setCaretPosition(0);

            txtLinea_1.setText(decrypt(prop.getProperty("CAPIP_LINEA_1")));
            txtLinea_2.setText(decrypt(prop.getProperty("CAPIP_LINEA_2")));
            txtLinea_3.setText(decrypt(prop.getProperty("CAPIP_LINEA_3")));
            txtLinea_4.setText(decrypt(prop.getProperty("CAPIP_LINEA_4")));
            txtAux_1.setText(decrypt(prop.getProperty("CAPIP_AUX_1")));
            txtAux_2.setText(decrypt(prop.getProperty("CAPIP_AUX_2")));

        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
            ClearFields();
        }

        txtCifClave.requestFocusInWindow();
    }

    /**
     *
     */
    private void CargarPropPorDefecto() {

        txtCifCliente.setText(CAPIP_CLIENTE_RAZONSOCIAL);
        txtCifRifCi.setText(CAPIP_CLIENTE_RIF_CI);
        txtDomicilioFiscal.setText(CAPIP_CLIENTE_DOMICILIO_FISCAL);

//        try {
//            txtCifLicencia.setText(new String(MessageDigest.getInstance("MD5").digest(new CpuInfo().getCpuInfo().toString().getBytes("UTF-8")), "UTF-8"));
//        } catch (Exception _ex) {
//            JOptionPane.showMessageDialog(null, _ex);
//            ClearCifFields();
//            return;
//        }
        txtCifFechaLicencia.setText(df.format(CAPIP_FECHA_LICENCIA));
        txtCifServidor.setText(CAPIP_SERVIDOR);
        txtCifDirServidor.setText(CAPIP_DIR_SERVIDOR);
        txtCifDirLocal.setText(CAPIP_DIR_LOCAL);
        txtCifDirLocal.setText(CAPIP_DIR_LOCAL);
        txtCifBaseDatos.setText(CAPIP_BASE_DATOS);
        txtCifUsuarioBD.setText(CAPIP_USUARIO_BD);
        txtCifClaveBD.setText(CAPIP_CLAVE_BD);

        chkNumPago.setSelected(CAPIP_NUM_PAGO.equals("ABSOLUTO"));
        txtPath_mysqldump.setText(CAPIP_PATH_MYSQLDUMP);
        txtPath_mysqldump.setCaretPosition(0);

        txtLinea_2.setText(CAPIP_LINEA_1);
        txtLinea_2.setText(CAPIP_LINEA_2);
        txtLinea_3.setText(CAPIP_LINEA_3);
        txtLinea_4.setText(CAPIP_LINEA_4);
        txtAux_2.setText(CAPIP_AUX_1);
        txtAux_2.setText(CAPIP_AUX_2);
    }

    /**
     * Verifica la existencia de los directorios que son necesarios,
     * para el funcionamiento de CAPIP
     * Rev 22/11/2016
     */
    public static void chkDirs() {

        final File f0 = new File(CAPIP_DIR_LOCAL);
        if (!f0.exists()) {
            if (!f0.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f0.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f1 = new File(CAPIP_DIR_LOCAL_COMPROMISO);
        if (!f1.exists()) {
            if (!f1.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f1.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f2 = new File(CAPIP_DIR_LOCAL_CAUSADO);
        if (!f2.exists()) {
            if (!f2.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f2.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f3 = new File(CAPIP_DIR_LOCAL_IVA);
        if (!f3.exists()) {
            if (!f3.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f3.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f4 = new File(CAPIP_DIR_LOCAL_ISLR);
        if (!f4.exists()) {
            if (!f4.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f4.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f5 = new File(CAPIP_DIR_LOCAL_ORDENPAGO);
        if (!f5.exists()) {
            if (!f5.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f5.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            exit(1);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new capip_configurador.FrmConfing(new StringBuilder()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCifCancelar;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDesCancelar1;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGenerar_1;
    private javax.swing.JCheckBox chkNumPago;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler14;
    private javax.swing.Box.Filler filler15;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem mnuAcercaDe;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JMenuBar mnuBar;
    private javax.swing.JMenuItem mnuGenerar;
    private javax.swing.JMenuItem mnuSalir;
    private javax.swing.JPanel pnlCampos;
    private javax.swing.JPanel pnlCampos2;
    private javax.swing.JPanel pnlCifrado;
    private javax.swing.JPanel pnlClave;
    private javax.swing.JPanel pnlComandos;
    private javax.swing.JPanel pnlComandos2;
    private javax.swing.JPanel pnlReportes;
    private javax.swing.JTabbedPane tbbPanel;
    private javax.swing.JTextField txtAux_1;
    private javax.swing.JTextField txtAux_2;
    private javax.swing.JTextField txtCifBaseDatos;
    private javax.swing.JTextField txtCifBytes;
    private javax.swing.JPasswordField txtCifClave;
    private javax.swing.JPasswordField txtCifClaveBD;
    private javax.swing.JTextField txtCifCliente;
    private javax.swing.JTextField txtCifDirLocal;
    private javax.swing.JTextField txtCifDirServidor;
    private javax.swing.JTextField txtCifFechaLicencia;
    private javax.swing.JPasswordField txtCifLicencia;
    private javax.swing.JPasswordField txtCifReClave;
    private javax.swing.JTextField txtCifRifCi;
    private javax.swing.JTextField txtCifServidor;
    private javax.swing.JTextField txtCifUsuarioBD;
    private javax.swing.JTextField txtDomicilioFiscal;
    private javax.swing.JTextField txtLinea_1;
    private javax.swing.JTextField txtLinea_2;
    private javax.swing.JTextField txtLinea_3;
    private javax.swing.JTextField txtLinea_4;
    private javax.swing.JTextField txtPath_mysqldump;
    // End of variables declaration//GEN-END:variables

}
