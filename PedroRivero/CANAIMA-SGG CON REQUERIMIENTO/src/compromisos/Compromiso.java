/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package compromisos;

import capipsistema.CapipPropiedades;
import static capipsistema.CapipPropiedades.CAPIP_IVA_APLICADO;
import static capipsistema.CapipPropiedades.CAPIP_IVA_PARTIDA;
import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import configuracion.RegistroBenef;
import gen_next_num.GenNextNum;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import static java.lang.Math.min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.BenefModel;
import modelos.ComprDetModel;
import modelos.ComprModel;
import modelos.IvaAplicModel;
import modelos.PptoModel;
import modelos.PresupeModel;
import static modelos.PresupeModel.anuComprPttoE;
import static modelos.PresupeModel.genComprPttoE;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;
import utils.Num2Let;
import utils.TipoCompr;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class Compromiso extends javax.swing.JFrame {

    private final javax.swing.ImageIcon checkIcon;

    private static final long serialVersionUID = 1L;

    private static final int COL_BENEF_RazonS = 0;
    private static final int COL_BENEF_RIF_CI = 1;
    private static final int COL_BENEF_Domicilio = 2;
    private static final int COL_BENEF_Telefonos = 3;

    private static final int COL_ITEM_CANT = 0;
    private static final int COL_ITEM_DESC = 1;
    private static final int COL_ITEM_PUNITARIO = 2;
    private static final int COL_ITEM_SUBTOTAL = 3;
    private static final int COL_ITEM_CODIGO = 4;
    private static final int COL_ITEM_PARTIDA = 5;

    final java.awt.Window parent;

    /**
     * Mantiene el generador de numeros o ID
     */
    GenNextNum comprNextNum;

    BigDecimal IVA_DEC_VALUE;

    PptoModel regPptoSel;

    /**
     * Mantiene actualizado el monto gravado en Bs. al cual se le aplica el IVA
     */
    private BigDecimal iva_grav_bs;

    /**
     * Inicialización y configuración de Componentes Rev. 06-03-2017
     *
     * @param _parent
     */
    public Compromiso(final java.awt.Window _parent) {
        super();
        initComponents();

        checkIcon = new javax.swing.ImageIcon(getClass().getResource("/imagenes/circled_checkmark_32x32.png"));
        parent = _parent;
        comprNextNum = null;

        comprNextNum = new GenNextNum("OC");

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
            genNextID();
            UpdateTblBenef(tblBenef);

            final int idSelIO = PresupeSel.getIdSelIO();
            if (idSelIO >= 1) {
                try {
                    regPptoSel = PptoModel.getReg_x_Id("presupe", idSelIO);
                    if (regPptoSel != null) {
                        actInsertItem();
                    }
                } catch (final Exception _ex) {
                    regPptoSel = null;
                    JOptionPane.showMessageDialog(this, "Error al tratar de recuperar la Selección" + System.getProperty("line.separator") + _ex);
                }
            } else {
                regPptoSel = null;
            }
        }

        super.setVisible(_b);
    }

    /**
     * Establece el comportamiento de la presente Ventana Rev 21/09/2016
     *
     */
    public void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        setTitle(CapipPropiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        // Establecer la acción al cerrar ventana
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
                if (txtCant.isFocusOwner() && txaConcepto.getText().isEmpty()) {
                    java.awt.EventQueue.invokeLater(txaConcepto::requestFocusInWindow);
                } else {
                    actSalir();
                }
            }
        });

    }

    /**
     * Establece el comportamiento de los componentes de la Ventana Rev 21/09/2016
     */
    public void setCompBehavior() {

        setTblBenefBehavior(tblBenef, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                actSelectBenef();
            }
        });

        setTblItemBehavior(tblItem);

        setTxtFechaFactBehavior(txtFechaFact);

        setTxtCantBehavior((JFormattedTextField) txtCant, (JFormattedTextField) txtPUnitario, txtSubTotal);
        setTxtPUnitarioBehavior((JFormattedTextField) txtCant, (JFormattedTextField) txtPUnitario, txtSubTotal);
    }

    /**
     * Rev 25/09/2016
     *
     * @param _tblBenef
     * @param _action
     */
    public static void setTblBenefBehavior(final JTable _tblBenef, AbstractAction _action) {
        // Configurar la tabla de los Beneficiarios, para manejar el ENTER
        _tblBenef.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "action.enter");
        _tblBenef.getActionMap().put("action.enter", _action);

        // Configurar la tabla de los Beneficiarios, para manejar el SPACE
        _tblBenef.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "action.space");
        _tblBenef.getActionMap().put("action.space", _action);

        DefaultTableCellRenderer tcr;
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        _tblBenef.getColumnModel().getColumn(COL_BENEF_RIF_CI).setCellRenderer(tcr);

        _tblBenef.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        _tblBenef.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        _tblBenef.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 25/09/2016
     *
     * @param _tblItem
     */
    public static void setTblItemBehavior(JTable _tblItem) {
        DefaultTableCellRenderer tcr;

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        _tblItem.getColumnModel().getColumn(COL_ITEM_CODIGO).setCellRenderer(tcr);

        _tblItem.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        _tblItem.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        _tblItem.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 221/09/2016
     *
     * @param _tblPartida
     * @param _action
     */
    public static void setTblPartidaBehavior(JTable _tblPartida, final AbstractAction _action) {
        // Configurar la tabla de las Partidas, para manejar el ENTER
        _tblPartida.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "action.enter");
        _tblPartida.getActionMap().put("action.enter", _action);

        // Configurar la tabla de las Partidas, para manejar el SPACE
        _tblPartida.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "action.space");
        _tblPartida.getActionMap().put("action.space", _action);

        // Configurar la tabla de partidas
        _tblPartida.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        _tblPartida.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        _tblPartida.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev. 13/09/2016
     *
     * @param _txtFechaFact
     */
    public static void setTxtFechaFactBehavior(final JFormattedTextField _txtFechaFact) {
        // Establecer el formateador de fecha
        _txtFechaFact.setFormatterFactory(Globales.getDateFormatterFactory());
    }

    /**
     * Rev 21/09/2016
     *
     * @param _txtCant
     * @param _txtPUnitario
     * @param _txtSubTotal
     */
    public static void setTxtCantBehavior(final JFormattedTextField _txtCant, final JFormattedTextField _txtPUnitario, final JTextField _txtSubTotal) {

        // Para que cada vez que se modifique el campo se actualice el subtotal
        _txtCant.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        newSubTotal(_txtCant, _txtPUnitario, _txtSubTotal);
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        newSubTotal(_txtCant, _txtPUnitario, _txtSubTotal);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        newSubTotal(_txtCant, _txtPUnitario, _txtSubTotal);
                    }
                });
    }

    /**
     * Rev 21/09/2016
     *
     * @param _txtCant
     * @param _txtPUnitario
     * @param _txtSubTotal
     */
    public static void setTxtPUnitarioBehavior(final JFormattedTextField _txtCant, final JFormattedTextField _txtPUnitario, final JTextField _txtSubTotal) {
        // Para que cada vez que se modifique el campo se actualice el subtotal
        _txtPUnitario.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        newSubTotal(_txtCant, _txtPUnitario, _txtSubTotal);
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        newSubTotal(_txtCant, _txtPUnitario, _txtSubTotal);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        newSubTotal(_txtCant, _txtPUnitario, _txtSubTotal);
                    }
                });
    }

    /**
     * Actualiza el sub-total, con cada modificación del precio unitario Rev. 14/09/2016
     *
     * @param _txtCant
     * @param _txtPUnitario
     * @param _txtSubTotal
     */
    public static void newSubTotal(final JFormattedTextField _txtCant, final JFormattedTextField _txtPUnitario, final JTextField _txtSubTotal) {
        final BigDecimal bdCant;
        try {
            _txtCant.commitEdit();
            bdCant = (BigDecimal) _txtCant.getValue();
        } catch (final Exception _ex) {
            _txtSubTotal.setText("0,00");
            return;
        }

        final BigDecimal bdPU;
        try {
            _txtPUnitario.commitEdit();
            bdPU = (BigDecimal) _txtPUnitario.getValue();
        } catch (final Exception _ex) {
            _txtSubTotal.setText("0,00");
            return;
        }

        _txtSubTotal.setText(Format.toStr(bdCant.multiply(bdPU)));
    }

    /**
     * Rev 25/09/2016
     */
    private void calcularTotal() {
        BigDecimal total_bs = BigDecimal.ZERO;
        for (int i = 0; i < tblItem.getRowCount(); i++) {
            total_bs = total_bs.add(BigDecimal.valueOf((double) tblItem.getValueAt(i, COL_ITEM_SUBTOTAL)));
        }

        txtTotalBs.setText(Format.toStr(total_bs));
    }

    /**
     * Establece las condiciones iniciales de la Ventana, segun el estado
     *
     * Rev 16/02/2017
     *
     */
    public void setStartConditions() {

        cargarComboIva(cmbIva);

        // Inicializar variables miembro
        regPptoSel = null;
        iva_grav_bs = BigDecimal.ZERO;
        txtFechaCompr.setText(Format.toStr(new java.sql.Date(Globales.getServerTimeStamp().getTime())));

        // Generar los procesos
        genNextID();
        UpdateTblBenef(tblBenef);

        txtRazonSocial.setText("");
        txtRIF_CI.setText("");
        txtDomicilio.setText("");
        txtTelefonos.setText("");

        txtNroFact.setEnabled(false);
        txtNroFact.setText("");
        txtNumControl.setText("");

        txtFechaFact.setEnabled(false);
        txtFechaFact.setText("");

        // Establecer el punto de inicio a nivel de componente
        tblBenef.setEnabled(true);
        java.awt.EventQueue.invokeLater(tblBenef::requestFocusInWindow);

        txtCant.setEnabled(false);
        ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);

        txtDesc.setEnabled(false);
        txtDesc.setText("");

        txtPUnitario.setEnabled(false);
        ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);

        txtCodPartida.setText("");
        txtSubTotal.setText("0,00");

        tblItem.setEnabled(false);

        tblItem.clearSelection();
        final DefaultTableModel modelItem = (DefaultTableModel) tblItem.getModel();
        modelItem.getDataVector().removeAllElements();
        modelItem.fireTableDataChanged();

        txaConcepto.setEnabled(false);
        txaConcepto.setText("");

        txtTotalBs.setText("0,00");

        btnCompra.setSelected(true);

//        lblOC.setIcon(checkIcon);
        btnInsertar.setEnabled(false);
        btnEliminar.setEnabled(false);

        btnIVA.setEnabled(false);
        if (cmbIva.getItemCount() > 0) {
            cmbIva.setSelectedIndex(0);
        } else {
            cmbIva.setSelectedIndex(-1);
        }

        cmbIva.setEditable(false);
        IVA_DEC_VALUE = BigDecimal.ZERO;

        chkISLR.setEnabled(false);
        chkISLR.setSelected(false);
        btnGuardar.setEnabled(false);
    }

    /**
     * Rev 12/09/2016
     *
     * @param _tblBenef
     */
    public static void UpdateTblBenef(JTable _tblBenef) {

        _tblBenef.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) _tblBenef.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[4];

        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM beneficiario ORDER BY razonsocial ASC");
            while (rs.next()) {
                datos[0] = rs.getString("razonsocial");
                datos[1] = rs.getString("rif_ci");
                datos[2] = rs.getString("domicilio");
                datos[3] = rs.getString("telefonos");
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        if (_tblBenef.getRowCount() > 0) {
            _tblBenef.setRowSelectionInterval(0, 0);
            _tblBenef.scrollRectToVisible(_tblBenef.getCellRect(0, 0, true));
        }
    }

    /**
     * Rev 12/09/2016
     *
     * @param _tblPartida
     */
    public static void UpdateTblPartida(final JTable _tblPartida) {

        _tblPartida.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) _tblPartida.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            final Object[] datos = new Object[3];

            final ResultSet rs = Conn.executeQuery("SELECT * FROM presupe WHERE ejefis=" + Globales.getEjeFisYear() + " AND anulado_sn= 'N' ORDER BY codigo ASC");
            while (rs.next()) {
                datos[0] = rs.getString("codigo");
                datos[1] = rs.getString("partida");
                datos[2] = Format.toDouble(rs.getDouble("monto"));
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        if (_tblPartida.getRowCount() > 0) {
            _tblPartida.setRowSelectionInterval(0, 0);
            _tblPartida.scrollRectToVisible(_tblPartida.getCellRect(0, 0, true));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnOtrosCompr = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelefonos = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNroFact = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFechaFact = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCant = textfield_decimal.DecimalTextField.getTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPUnitario = textfield_decimal.DecimalTextField.getTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCodPartida = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblItem = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaConcepto = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        btnInsertar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnIVA = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        txtSubTotal = new javax.swing.JFormattedTextField();
        chkISLR = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBenef = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnServicio = new javax.swing.JToggleButton();
        btnCompra = new javax.swing.JToggleButton();
        lblTipoCompr = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtRIF_CI = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTotalBs = new javax.swing.JFormattedTextField();
        txtID_Compr = new javax.swing.JFormattedTextField();
        txtFechaCompr = new javax.swing.JFormattedTextField();
        btnAnular = new javax.swing.JButton();
        btnBenefNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnReiniciar = new javax.swing.JToggleButton();
        lblOC = new javax.swing.JLabel();
        lblOS = new javax.swing.JLabel();
        lblCO = new javax.swing.JLabel();
        cmbIva = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtNumControl = new javax.swing.JTextField();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("COMPROMISOS");
        setMinimumSize(new java.awt.Dimension(1150, 730));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnOtrosCompr.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroup1.add(btnOtrosCompr);
        btnOtrosCompr.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnOtrosCompr.setText("OTROS COMPROMISOS");
        btnOtrosCompr.setBorder(null);
        btnOtrosCompr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtrosComprActionPerformed(evt);
            }
        });
        getContentPane().add(btnOtrosCompr, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 240, 40));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setLabelFor(txtRazonSocial);
        jLabel1.setText("Razon Social");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 120, 30));

        txtRazonSocial.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtRazonSocial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtRazonSocial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRazonSocial.setEnabled(false);
        getContentPane().add(txtRazonSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 420, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setLabelFor(txtDomicilio);
        jLabel2.setText("Domicilio Fiscal");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 120, 30));

        txtDomicilio.setEditable(false);
        txtDomicilio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDomicilio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDomicilio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDomicilio.setEnabled(false);
        getContentPane().add(txtDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 640, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setLabelFor(txtTelefonos);
        jLabel3.setText("Teléfonos");
        jLabel3.setMinimumSize(new java.awt.Dimension(30, 17));
        jLabel3.setPreferredSize(new java.awt.Dimension(30, 17));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 70, 30));

        txtTelefonos.setEditable(false);
        txtTelefonos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTelefonos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefonos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTelefonos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTelefonos.setEnabled(false);
        getContentPane().add(txtTelefonos, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 150, 31));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setLabelFor(txtNroFact);
        jLabel5.setText("Nº  Factura");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 80, 30));

        txtNroFact.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txtNroFact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNroFact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNroFact.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNroFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroFactActionPerformed(evt);
            }
        });
        txtNroFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNroFactKeyTyped(evt);
            }
        });
        getContentPane().add(txtNroFact, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 150, 31));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setLabelFor(txtFechaFact);
        jLabel4.setText("Fecha");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, 50, 30));

        txtFechaFact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFechaFact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaFact.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFechaFact.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtFechaFact.setMinimumSize(new java.awt.Dimension(130, 31));
        txtFechaFact.setPreferredSize(new java.awt.Dimension(130, 31));
        txtFechaFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFactActionPerformed(evt);
            }
        });
        getContentPane().add(txtFechaFact, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 130, -1));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(815, 540));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Sub-Total");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 78, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setLabelFor(txtCant);
        jLabel9.setText("Cant.");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 10, 40, 38));

        txtCant.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtCant.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCant.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantActionPerformed(evt);
            }
        });
        txtCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantKeyTyped(evt);
            }
        });
        jPanel1.add(txtCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 60, 35));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setLabelFor(txtDesc);
        jLabel11.setText("Descripción");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 90, 36));

        txtDesc.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDesc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescActionPerformed(evt);
            }
        });
        txtDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescKeyTyped(evt);
            }
        });
        jPanel1.add(txtDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 430, 34));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setLabelFor(txtPUnitario);
        jLabel10.setText("P/Unit");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 50, 32));

        txtPUnitario.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtPUnitario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPUnitario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPUnitario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPUnitarioActionPerformed(evt);
            }
        });
        txtPUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPUnitarioKeyTyped(evt);
            }
        });
        jPanel1.add(txtPUnitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 170, 31));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setLabelFor(txtCodPartida);
        jLabel13.setText("Imputacion Presupuestaria");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 190, 30));

        txtCodPartida.setEditable(false);
        txtCodPartida.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtCodPartida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodPartida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodPartida.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodPartida.setEnabled(false);
        jPanel1.add(txtCodPartida, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 370, 30));

        tblItem.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        tblItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cant.", "Descripción", "P. Unitario Bs.", "Sub. Total Bs.", "Cod. Partida", "Partida"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblItem.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblItem.setGridColor(new java.awt.Color(0, 0, 0));
        tblItem.setSelectionBackground(new java.awt.Color(175, 204, 125));
        jScrollPane2.setViewportView(tblItem);
        if (tblItem.getColumnModel().getColumnCount() > 0) {
            tblItem.getColumnModel().getColumn(0).setMinWidth(25);
            tblItem.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblItem.getColumnModel().getColumn(0).setMaxWidth(75);
            tblItem.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblItem.getColumnModel().getColumn(2).setMinWidth(50);
            tblItem.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblItem.getColumnModel().getColumn(2).setMaxWidth(200);
            tblItem.getColumnModel().getColumn(3).setMinWidth(50);
            tblItem.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblItem.getColumnModel().getColumn(3).setMaxWidth(200);
            tblItem.getColumnModel().getColumn(4).setMinWidth(50);
            tblItem.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblItem.getColumnModel().getColumn(4).setMaxWidth(200);
            tblItem.getColumnModel().getColumn(5).setPreferredWidth(350);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 850, 230));

        jLabel7.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        jLabel7.setLabelFor(txaConcepto);
        jLabel7.setText("Concepto de la Orden");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 220, -1));

        txaConcepto.setColumns(20);
        txaConcepto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txaConcepto.setLineWrap(true);
        txaConcepto.setRows(5);
        txaConcepto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txaConcepto.setSelectionColor(new java.awt.Color(175, 204, 125));
        txaConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txaConceptoKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txaConcepto);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 850, 80));

        btnGuardar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnGuardar.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 440, 130, 50));

        btnInsertar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnInsertar.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnInsertar.setText("INSERTAR");
        btnInsertar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });
        jPanel1.add(btnInsertar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 130, 50));

        btnEliminar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnEliminar.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnEliminar.setText("ELIM. ULT.");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 440, 120, 50));

        btnIVA.setBackground(java.awt.SystemColor.inactiveCaption);
        btnIVA.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnIVA.setText("I.V.A.");
        btnIVA.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIVAActionPerformed(evt);
            }
        });
        jPanel1.add(btnIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 440, 130, 50));

        btnConsultar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnConsultar.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        jPanel1.add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 440, 140, 50));

        txtSubTotal.setEditable(false);
        txtSubTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSubTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubTotal.setText("0,00");
        txtSubTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubTotal.setEnabled(false);
        txtSubTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel1.add(txtSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 50, 220, 31));

        chkISLR.setBackground(java.awt.SystemColor.inactiveCaption);
        chkISLR.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        chkISLR.setText("I.S.L.R.");
        chkISLR.setAlignmentX(0.5F);
        chkISLR.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        chkISLR.setBorderPainted(true);
        chkISLR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chkISLR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkISLRActionPerformed(evt);
            }
        });
        jPanel1.add(chkISLR, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 440, 130, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 860, 490));

        tblBenef.setAutoCreateRowSorter(true);
        tblBenef.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblBenef.setForeground(new java.awt.Color(51, 51, 51));
        tblBenef.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BENEFICIARIO", "RIF / CI", "DOMICILIO", "TELÉFONOS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBenef.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblBenef.setGridColor(new java.awt.Color(255, 255, 255));
        tblBenef.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblBenef.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblBenef.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBenefMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblBenef);
        tblBenef.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tblBenef.getColumnModel().getColumnCount() > 0) {
            tblBenef.getColumnModel().getColumn(0).setPreferredWidth(230);
            tblBenef.getColumnModel().getColumn(1).setMinWidth(100);
            tblBenef.getColumnModel().getColumn(1).setPreferredWidth(125);
            tblBenef.getColumnModel().getColumn(1).setMaxWidth(150);
            tblBenef.getColumnModel().getColumn(2).setMinWidth(100);
            tblBenef.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblBenef.getColumnModel().getColumn(2).setMaxWidth(250);
            tblBenef.getColumnModel().getColumn(3).setMinWidth(100);
            tblBenef.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblBenef.getColumnModel().getColumn(3).setMaxWidth(250);
        }

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 230, 250, 290));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BENEFICIARIOS");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 190, 160, 31));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("  FECHA");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 60, 80, 40));

        btnServicio.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroup1.add(btnServicio);
        btnServicio.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnServicio.setText("ORDEN DE SERVICIO");
        btnServicio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServicioActionPerformed(evt);
            }
        });
        getContentPane().add(btnServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 240, 40));

        btnCompra.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroup1.add(btnCompra);
        btnCompra.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnCompra.setSelected(true);
        btnCompra.setText("ORDEN DE COMPRA");
        btnCompra.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCompra.setMaximumSize(new java.awt.Dimension(180, 25));
        btnCompra.setMinimumSize(new java.awt.Dimension(180, 25));
        btnCompra.setPreferredSize(new java.awt.Dimension(180, 25));
        btnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraActionPerformed(evt);
            }
        });
        getContentPane().add(btnCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 40));

        lblTipoCompr.setFont(new java.awt.Font("Tahoma", 0, 40)); // NOI18N
        lblTipoCompr.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
        getContentPane().add(lblTipoCompr, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 100, 130, 50));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setLabelFor(txtRIF_CI);
        jLabel16.setText("RIF / CI");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 60, 30));

        txtRIF_CI.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtRIF_CI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRIF_CI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtRIF_CI.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRIF_CI.setEnabled(false);
        getContentPane().add(txtRIF_CI, new org.netbeans.lib.awtextra.AbsoluteConstraints(652, 60, 150, 31));

        jLabel6.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        jLabel6.setText("Monto Orden Bs.");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 600, 150, 20));

        txtTotalBs.setEditable(false);
        txtTotalBs.setBackground(new java.awt.Color(175, 204, 125));
        txtTotalBs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalBs.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalBs.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalBs.setText("0,00");
        txtTotalBs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalBs.setEnabled(false);
        txtTotalBs.setFont(new java.awt.Font("Tahoma", 0, 32)); // NOI18N
        txtTotalBs.setSelectionColor(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtTotalBs, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 620, 260, 50));

        txtID_Compr.setEditable(false);
        txtID_Compr.setBackground(new java.awt.Color(175, 204, 125));
        txtID_Compr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtID_Compr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtID_Compr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtID_Compr.setEnabled(false);
        txtID_Compr.setFont(new java.awt.Font("Tahoma", 0, 40)); // NOI18N
        getContentPane().add(txtID_Compr, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 110, 190, 40));

        txtFechaCompr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFechaCompr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
        txtFechaCompr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaCompr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFechaCompr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        getContentPane().add(txtFechaCompr, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 60, 150, 40));

        btnAnular.setBackground(java.awt.SystemColor.inactiveCaption);
        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
        btnAnular.setToolTipText("Anular Ordenes de Compromiso, sin Causar.");
        btnAnular.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAnular.setMaximumSize(new java.awt.Dimension(55, 41));
        btnAnular.setMinimumSize(new java.awt.Dimension(55, 41));
        btnAnular.setPreferredSize(new java.awt.Dimension(55, 41));
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnular, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 10, -1, -1));

        btnBenefNuevo.setBackground(java.awt.SystemColor.inactiveCaption);
        btnBenefNuevo.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnBenefNuevo.setText("Nuevo");
        btnBenefNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBenefNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBenefNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnBenefNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 180, 90, 40));

        btnEditar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar_32x32.png"))); // NOI18N
        btnEditar.setToolTipText("Editar Orden");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.setMaximumSize(new java.awt.Dimension(55, 41));
        btnEditar.setMinimumSize(new java.awt.Dimension(55, 41));
        btnEditar.setPreferredSize(new java.awt.Dimension(55, 41));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1015, 10, -1, -1));

        btnReiniciar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnReiniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/requisicion_32x32.png"))); // NOI18N
        btnReiniciar.setToolTipText("Reiniciar Ventana");
        btnReiniciar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReiniciar.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
        btnReiniciar.setMaximumSize(new java.awt.Dimension(55, 41));
        btnReiniciar.setMinimumSize(new java.awt.Dimension(55, 41));
        btnReiniciar.setPreferredSize(new java.awt.Dimension(55, 41));
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });
        getContentPane().add(btnReiniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, -1, -1));

        lblOC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/circled_checkmark_32x32.png"))); // NOI18N
        getContentPane().add(lblOC, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 14, 32, 32));
        getContentPane().add(lblOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(565, 14, 32, 32));

        lblCO.setToolTipText("");
        getContentPane().add(lblCO, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 14, 32, 32));

        cmbIva.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbIva.setMinimumSize(new java.awt.Dimension(64, 27));
        cmbIva.setPreferredSize(new java.awt.Dimension(64, 27));
        getContentPane().add(cmbIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 560, 240, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Núm. Control");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 100, 30));

        txtNumControl.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtNumControl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNumControl.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNumControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumControlActionPerformed(evt);
            }
        });
        getContentPane().add(txtNumControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 150, 31));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        lblFondo.setText("jLabel18");
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 1160, 690));
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Rev 14/09/2016
     *
     * @param evt
     */
    private void btnOtrosComprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtrosComprActionPerformed

        comprNextNum = new GenNextNum("CO");

        genNextID();
        btnCompra.setEnabled(true);
        btnServicio.setEnabled(true);
        btnOtrosCompr.setEnabled(false);

        lblOC.setIcon(null);
        lblOS.setIcon(null);
        lblCO.setIcon(checkIcon);

        java.awt.EventQueue.invokeLater(txtCant::requestFocus);
    }//GEN-LAST:event_btnOtrosComprActionPerformed

    /**
     * Rev 14/09/2016
     *
     * @param evt
     */
    private void tblBenefMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBenefMouseClicked
        actSelectBenef();
    }//GEN-LAST:event_tblBenefMouseClicked

    /**
     * Rev 14/09/2016
     *
     * @throws HeadlessException
     */
    private void actSelectBenef() {
        int selRow = tblBenef.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        txtRazonSocial.setText(tblBenef.getValueAt(selRow, COL_BENEF_RazonS).toString());
        txtRazonSocial.setCaretPosition(0);

        txtRIF_CI.setText(tblBenef.getValueAt(selRow, COL_BENEF_RIF_CI).toString());
        txtRIF_CI.setCaretPosition(0);

        txtDomicilio.setText(tblBenef.getValueAt(selRow, COL_BENEF_Domicilio).toString());
        txtDomicilio.setCaretPosition(0);

        txtTelefonos.setText(tblBenef.getValueAt(selRow, COL_BENEF_Telefonos).toString());
        txtTelefonos.setCaretPosition(0);

        txtNroFact.setEnabled(true);
        txtFechaFact.setEnabled(true);

        txtCant.setEnabled(true);
        txtDesc.setEnabled(true);
        txtPUnitario.setEnabled(true);

        tblItem.setEnabled(true);

        txaConcepto.setEnabled(true);

        btnInsertar.setEnabled(true);

        java.awt.EventQueue.invokeLater(txtNroFact::requestFocus);
    }

    /**
     * Rev 12/05/2017
     *
     * @param _isIVA
     */
    boolean actInsertItem() {
        if (regPptoSel == null) {
            return false;
        }

        // Validar cantidad
        final BigDecimal cant;
        try {
            final JFormattedTextField txtAux = (JFormattedTextField) txtCant;
            txtAux.commitEdit();
            cant = (BigDecimal) txtAux.getValue();
            if (cant.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("Cantidad inválida");
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida");
            txtCodPartida.setText("");
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return false;
        }

        // Validar descripción
        final String sAux = txtDesc.getText().trim();
        if (sAux.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descripción inválida");
            txtCodPartida.setText("");
            java.awt.EventQueue.invokeLater(txtDesc::requestFocusInWindow);
            return false;
        }
        final String sDesc = sAux.toUpperCase().substring(0, min(sAux.length(), 128));

        // Validar precio unitario
        final BigDecimal punitario;
        try {
            final JFormattedTextField txtAux = (JFormattedTextField) txtPUnitario;
            txtAux.commitEdit();
            punitario = (BigDecimal) txtAux.getValue();
            if (punitario.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("Precio Unitario inválido");
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Precio Unitario inválido");
            txtCodPartida.setText("");
            java.awt.EventQueue.invokeLater(txtPUnitario::requestFocusInWindow);
            return false;
        }

        final BigDecimal subtotal = cant.multiply(punitario).setScale(2, RoundingMode.HALF_UP);
        txtSubTotal.setText(Format.toStr(subtotal));

        final String sCodPartida;
        final String sDescPartida;

        if (regPptoSel == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            txtCodPartida.setText("");
            java.awt.EventQueue.invokeLater(txtPUnitario::requestFocusInWindow);
            return false;
        }

        sCodPartida = regPptoSel.getCodigo();
        sDescPartida = regPptoSel.getPartida();

        for (PptoModel partIva : CAPIP_IVA_PARTIDA.values()) {
            if (sCodPartida.equals(partIva.getCodigo())) {
                JOptionPane.showMessageDialog(null, "Esta partida no puede insertarse directamente");
                java.awt.EventQueue.invokeLater(txtPUnitario::requestFocusInWindow);
                return false;
            }
        }

        // Verificar en la base de datos
        try {
            if (!PresupeModel.checkMontoDescontar(sCodPartida, subtotal)) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente en la partida: " + sCodPartida + System.getProperty("line.separator") + sDescPartida);
                java.awt.EventQueue.invokeLater(txtPUnitario::requestFocusInWindow);
                return false;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al verificar la disponibilidad presupuestaria" + System.getProperty("line.separator") + _ex);
            return false;
        }

        txtCodPartida.setText(sCodPartida);

        final Object[] datos = new Object[6];

        final DefaultTableModel model = (DefaultTableModel) tblItem.getModel();
        datos[0] = Format.toDouble(cant);
        datos[1] = sDesc;
        datos[2] = Format.toDouble(punitario);
        datos[3] = Format.toDouble(subtotal);
        datos[4] = sCodPartida;
        datos[5] = sDescPartida;
        model.addRow(datos);

        // Limpiar campos
        ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);
        txtDesc.setText("");
        ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);
        txtSubTotal.setText("0,00");
        txtCodPartida.setText("");

        if (tblItem.getRowCount() == 1) {
            btnEliminar.setEnabled(true);

            btnIVA.setEnabled(true);
            cmbIva.setSelectedIndex(-1);
            cmbIva.setEditable(true);

            chkISLR.setEnabled(true);
            btnGuardar.setEnabled(true);
        }

        calcularTotal();

        java.awt.EventQueue.invokeLater(txtCant::requestFocus);

        return true;
    }

    /**
     * Rev 12/05/2017
     *
     */
    boolean actInsertIVA() {

        if (tblItem.getRowCount() <= 0) {
            return false;
        }

        // Validar cantidad
        final BigDecimal cant;
        try {
            final JFormattedTextField txtAux = (JFormattedTextField) txtCant;
            txtAux.commitEdit();
            cant = (BigDecimal) txtAux.getValue();

            if (cant.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("Cantidad inválida");
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida");

            txtCant.setText("0,00");
            ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);

            txtDesc.setText("");

            txtPUnitario.setText("0,00");
            ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);

            txtCodPartida.setText("");
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return false;
        }

        // Validar descripción
        final String sAux = txtDesc.getText().trim();
        if (sAux.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descripción inválida");

            txtCant.setText("0,00");
            ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);

            txtDesc.setText("");

            txtPUnitario.setText("0,00");
            ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);

            txtCodPartida.setText("");
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return false;
        }
        final String sDesc = sAux.toUpperCase().substring(0, min(sAux.length(), 128));

        // Validar precio unitario
        final BigDecimal punitario;
        try {
            final JFormattedTextField txtAux = (JFormattedTextField) txtPUnitario;
            txtAux.commitEdit();
            punitario = (BigDecimal) txtAux.getValue();

            if (punitario.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("Precio Unitario inválido");
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Precio Unitario inválido");

            txtCant.setText("0,00");
            ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);

            txtDesc.setText("");

            txtPUnitario.setText("0,00");
            ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);

            txtCodPartida.setText("");
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return false;
        }

        final BigDecimal subtotal = cant.multiply(punitario).setScale(2, RoundingMode.HALF_UP);
        txtSubTotal.setText(Format.toStr(subtotal));

        if (cmbIva.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un IVA");
            return false;
        }

        final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
        final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
        PptoModel regPpto = null;
        try {
            regPpto = PptoModel.getReg_x_Id("presupe", regIva.getId_part_ppto());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar el IVA" + System.getProperty("line.separator") + _ex);
            java.awt.EventQueue.invokeLater(txtPUnitario::requestFocusInWindow);
            return false;
        }

        final String sCodPartida = regPpto.getCodigo();
        final String sDescPartida = regPpto.getPartida();

        // Verificar en la base de datos
        try {
            if (!PresupeModel.checkMontoDescontar(sCodPartida, subtotal)) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente en la partida: " + sCodPartida + System.getProperty("line.separator") + sDescPartida);

                txtCant.setText("0,00");
                ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);

                txtDesc.setText("");

                txtPUnitario.setText("0,00");
                ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);

                txtCodPartida.setText("");
                return false;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al verificar la disponibilidad presupuestaria" + System.getProperty("line.separator") + _ex);
            return false;
        }

        txtCodPartida.setText(sCodPartida);

        final Object[] datos = new Object[6];

        final DefaultTableModel model = (DefaultTableModel) tblItem.getModel();
        datos[0] = Format.toDouble(cant);
        datos[1] = sDesc;
        datos[2] = Format.toDouble(punitario);
        datos[3] = Format.toDouble(subtotal);
        datos[4] = sCodPartida;
        datos[5] = sDescPartida;
        model.addRow(datos);

        // Limpiar campos
        txtCant.setText("0,00");
        ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);

        txtDesc.setText("");

        txtPUnitario.setText("0,00");
        ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);

        txtSubTotal.setText("0,00");
        ((JFormattedTextField) txtSubTotal).setValue(BigDecimal.ZERO);
        txtCodPartida.setText("");

        calcularTotal();

        btnIVA.setEnabled(false);
        cmbIva.setSelectedIndex(-1);
        cmbIva.setEditable(false);

        java.awt.EventQueue.invokeLater(txtCant::requestFocus);

        return true;
    }

    /**
     * Rev 14/09/2016
     *
     */
    void genNextID() {
        if (btnCompra.isSelected()) {
            lblTipoCompr.setText("  OC  -");
        } else if (btnServicio.isSelected()) {
            lblTipoCompr.setText("  OS  -");
        } else if (btnOtrosCompr.isSelected()) {
            lblTipoCompr.setText("  CO  -");
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Presupuesto");
            java.awt.EventQueue.invokeLater(btnCompra::requestFocusInWindow);
            return;
        }

        try {
            txtID_Compr.setText(String.valueOf(comprNextNum.checkNum()));
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al generar Num. Compromiso" + System.getProperty("line.separator") + _ex);
            txtID_Compr.setText("1");
        }

    }

    /**
     *
     * @param evt
     */
    private void btnServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServicioActionPerformed

        comprNextNum = new GenNextNum("OS");

        genNextID();
        btnCompra.setEnabled(true);
        btnServicio.setEnabled(false);
        btnOtrosCompr.setEnabled(true);

        lblOC.setIcon(null);
        lblOS.setIcon(checkIcon);
        lblCO.setIcon(null);

        java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
    }//GEN-LAST:event_btnServicioActionPerformed

    /**
     *
     * @param evt
     */
    private void btnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraActionPerformed
        comprNextNum = new GenNextNum("OC");

        genNextID();
        btnCompra.setEnabled(false);
        btnServicio.setEnabled(true);
        btnOtrosCompr.setEnabled(true);

        lblOC.setIcon(checkIcon);
        lblOS.setIcon(null);
        lblCO.setIcon(null);

        java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
    }//GEN-LAST:event_btnCompraActionPerformed

    /**
     * Rev 05/10/2016
     *
     * @param evt
     */
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed

        if (tblItem.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(this, "¿Los datos cargados se perderan. Desea continuar con la Consulta ?",
                    "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }
        }

        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new CompromisosConsultar(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnConsultarActionPerformed

    /**
     * Rev 14/09/2016
     *
     * @param evt
     */
    private void btnIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIVAActionPerformed

        // Verificar la existencia de al menos 1 item
        if (tblItem.getRowCount() <= 0) {
            iva_grav_bs = BigDecimal.ZERO;
            return;
        }

        if (cmbIva.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Debe Seleccionar un IVA");
            return;
        }

        // Guardar el monto gravamen
        BigDecimal aux_grav_bs;
        try {
            aux_grav_bs = Format.toBigDec(txtTotalBs.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
            iva_grav_bs = BigDecimal.ZERO;
            return;
        }

        if (aux_grav_bs.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        // Establecer los campos
        txtCant.setText("1");
        txtDesc.setText(Globales.PART_IVA_DESC);

        final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
        final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
        final PptoModel regPpto;
        try {
            regPpto = PptoModel.getReg_x_Id("presupe", regIva.getId_part_ppto());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar el IVA" + System.getProperty("line.separator") + _ex);
            return;
        }

        IVA_DEC_VALUE = regIva.getValor_porc().movePointLeft(2); // 

        final String sIVA_bs = Format.toStr(aux_grav_bs.multiply(IVA_DEC_VALUE));
        txtPUnitario.setText(sIVA_bs);
        txtSubTotal.setText(sIVA_bs);
        txtCodPartida.setText(regPpto.getCodigo());

        if (actInsertIVA()) {
            btnIVA.setEnabled(false);
            cmbIva.setSelectedIndex(-1);
            cmbIva.setEditable(false);

            iva_grav_bs = aux_grav_bs;
        } else {
            iva_grav_bs = BigDecimal.ZERO;
            txtCant.setText("0,00");
            txtDesc.setText("");
            txtPUnitario.setText("0,00");
            txtSubTotal.setText("0,00");
            txtCodPartida.setText("");
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
        }
    }//GEN-LAST:event_btnIVAActionPerformed

    /**
     * Elimina el ultimo item Rev 14/09/2016
     *
     * @param evt
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        // Verifica que no existe items en la tabla
        if (tblItem.getRowCount() <= 0) {
            return;
        }

        // Seleccionar el ultimo item de la tabla
        final int row = tblItem.getRowCount() - 1;
        final String codPar = (String) tblItem.getValueAt(row, 4);

        // Verificar que es la partida del IVA
        for (PptoModel partIva : CAPIP_IVA_PARTIDA.values()) {
            if (codPar.equals(partIva.getCodigo())) {
                iva_grav_bs = BigDecimal.ZERO;
                btnIVA.setEnabled(true);
                cmbIva.setSelectedIndex(-1);
                cmbIva.setEditable(true);

            }
        }

        ((DefaultTableModel) tblItem.getModel()).removeRow(row);

        if (tblItem.getRowCount() <= 0) {
            btnEliminar.setEnabled(false);
            btnIVA.setEnabled(false);
            cmbIva.setSelectedIndex(-1);
            cmbIva.setEditable(false);

            chkISLR.setEnabled(false);
            btnGuardar.setEnabled(false);
        }

        calcularTotal();

        txtCant.requestFocusInWindow();
    }//GEN-LAST:event_btnEliminarActionPerformed

    /**
     * Rev 14/09/2016
     *
     * @param evt
     */
    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        actInsertItem();
    }//GEN-LAST:event_btnInsertarActionPerformed

    /**
     * Rev 14/09/2016
     *
     * @param evt
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        actGuardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Rev 15/09/2016
     */
    private void actGuardar() {

        final Map<String, Object> param = new HashMap<>(101);
        if (!valComp(param)) {
            return;
        }

        try {
            Conn.BeginTransaction();

            param.put("id_compr", comprNextNum.nextNum());
            genMaster(param, tblItem, IVA_DEC_VALUE);
            genDetail(param, tblItem);

            comprNextNum.update();

            Conn.EndTransaction();
        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
            }

            JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
            return;
        }

        JOptionPane.showMessageDialog(this, "Operación realizada");

        try {
            genReport((long) param.get("id_compr"), new HashMap<>(param), true);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
        }

        setStartConditions();
    }

    /**
     * Rev 12/10/2016
     *
     * @param _param
     * @return
     */
    private boolean valComp(final Map<String, Object> _param) {

        // Verificar si hay items seleccionados
        if (tblItem.getRowCount() <= 0) {
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return false;
        }

        // Validación interna, por si acaso falto alguna actualización
        // 1.-
        String sAux = txtID_Compr.getText().trim();
        if (sAux.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Número de compromiso inválido");
            java.awt.EventQueue.invokeLater(txtID_Compr::requestFocusInWindow);
            return false;
        }

        // a los fines de Debug
        try {
            final long id_compr = Format.toLong(sAux);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Número de compromiso inválido");
            java.awt.EventQueue.invokeLater(txtID_Compr::requestFocusInWindow);
            return false;
        }
        // Esta linea no va, porque el verdadero id_compr lo determina la operacion de Insercion
        // se deja como recordatorio
        //_param.put("id_compr", id_compr);

        // Verificar si hay items seleccionados
        // 2.-
        if (tblItem.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "Debe incluir al menos un Ítem");
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return false;
        }

        // Verificar el tipo de compromiso
        // 3.-
        final TipoCompr tipo_compr;
        if (btnCompra.isSelected()) {
            tipo_compr = TipoCompr.OC;
        } else if (btnServicio.isSelected()) {
            tipo_compr = TipoCompr.OS;
        } else if (btnOtrosCompr.isSelected()) {
            tipo_compr = TipoCompr.CO;
        } else {
            JOptionPane.showMessageDialog(null, "Orden de Compromiso inválida");
            return false;
        }
        _param.put("tipo_compr", tipo_compr);

        // Verficar la fecha de Compromiso
        // 4.-
        final java.sql.Date fecha_compr;
        try {
            fecha_compr = Format.toDateSql(txtFechaCompr.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Fecha de Compromiso inválida: " + System.getProperty("line.separator") + _ex);
            java.awt.EventQueue.invokeLater(txtFechaCompr::requestFocusInWindow);
            return false;
        }
        _param.put("fecha_compr", fecha_compr);

        // Verificar el Beneficiario
        // 5.-
        final String benef_razonsocial = txtRazonSocial.getText();
        if (benef_razonsocial.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Beneficiario inválido");
            java.awt.EventQueue.invokeLater(tblBenef::requestFocusInWindow);
            return false;
        }
        _param.put("benef_razonsocial", benef_razonsocial);

        final boolean isNormalBenef = !benef_razonsocial.substring(0, 1).equals("@");

        // Verificar el beneficiario, rif_ci
        // 6.-
        final String benef_rif_ci = txtRIF_CI.getText();
        if (benef_rif_ci.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Beneficiario inválido");
            java.awt.EventQueue.invokeLater(tblBenef::requestFocusInWindow);
            return false;
        }
        _param.put("benef_rif_ci", benef_rif_ci);

        // Verificar las observaciones
        // 7.-
        final String auxObs = txaConcepto.getText().trim().toUpperCase();
        if (auxObs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Concepto de la Orden inválido");
            java.awt.EventQueue.invokeLater(txaConcepto::requestFocusInWindow);
            return false;
        }
        _param.put("observacion", auxObs.substring(0, min(auxObs.length(), 512)));

        final String num_fact;
        final String num_control;
        final java.sql.Date fecha_fact;

        if (isNormalBenef) {

            // Verificar el número de factura
            // 8.-    
            final String sAuxNumFact = txtNroFact.getText().trim().toUpperCase();
            if (sAuxNumFact.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Número de factura inválido");
                java.awt.EventQueue.invokeLater(txtNroFact::requestFocusInWindow);
                return false;
            }
            num_fact = sAuxNumFact.substring(0, min(sAuxNumFact.length(), 32));
            _param.put("num_fact", num_fact);

            final String sAuxNumControl = txtNumControl.getText().trim().toUpperCase();
            num_control = sAuxNumControl.substring(0, min(sAuxNumControl.length(), 32));
            _param.put("num_control", num_control);

            // Verificar la fecha de la factura
            // 9.-
            try {
                fecha_fact = Format.toDateSql(txtFechaFact.getText().trim());
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, "Fecha de factura inválida");
                java.awt.EventQueue.invokeLater(txtFechaFact::requestFocusInWindow);
                return false;
            }

            // Validar la Fecha, no puede tener mas de un año
            // 11.-
            Calendar fechaAntAno = Calendar.getInstance();
            fechaAntAno.add(Calendar.YEAR, -1);

            if (fecha_fact.compareTo(Format.toDateSql(fechaAntAno.getTime())) <= 0) {
                JOptionPane.showMessageDialog(null, "Fecha de factura inválida");
                java.awt.EventQueue.invokeLater(txtFechaFact::requestFocusInWindow);
                return false;
            }
            _param.put("fecha_fact", fecha_fact);

        } else {
            // Es un beneficiario especial, tipo requisicion, 
            // donde los datos de la facturan seran editados posteriormente
            num_fact = "";
            num_control = "";
            fecha_fact = Globales.getEjefis();

            _param.put("num_fact", num_fact);
            _param.put("num_control", num_control);
            _param.put("fecha_fact", fecha_fact);
        }

        // Validar el Monto
        // 12.- 
        BigDecimal total_bs;
        try {
            total_bs = Format.toBigDec(txtTotalBs.getText().trim());
        } catch (final Exception ex) {
            total_bs = BigDecimal.ZERO;
        }

        if (total_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Monto total inválido");
            return false;
        }
        _param.put("total_bs", total_bs);

        // Verificar el monto del IVA
        // 12.-
        final BigDecimal iva_bs = iva_grav_bs.multiply(IVA_DEC_VALUE).setScale(2, RoundingMode.HALF_UP);
        if (iva_bs.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Monto del IVA inválido");
            return false;
        }
        _param.put("iva_grav_bs", iva_grav_bs);
        _param.put("iva_bs", iva_bs);

        // Verificar la base imponible
        // 13.-
        final BigDecimal base_imponible_bs = total_bs.subtract(iva_bs).setScale(2, RoundingMode.HALF_UP);
        if (base_imponible_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Base imponible inválida");
            return false;
        }
        _param.put("base_imponible_bs", base_imponible_bs);
        if (chkISLR.isSelected()) {
            _param.put("islr_grav_bs", total_bs.subtract(iva_bs).setScale(2, RoundingMode.HALF_UP));
        } else {
            _param.put("islr_grav_bs", BigDecimal.ZERO);
        }
        _param.put("islr_porc_ret", BigDecimal.ZERO);

        // Verificar que para el beneficiario dado, NO se halla procesado la misma factura
        final String[] arrTbl = {"compr_compra", "compr_servicio", "compr_otros"};

        for (String tbl : arrTbl) {
            try {
                final ResultSet rs = Conn.executeQuery("SELECT * FROM " + tbl + " WHERE benef_rif_ci='" + benef_rif_ci + "' AND num_fact= '" + num_fact + "'");
                if (rs.next()) {
                    final long lAuxIdCompr = rs.getLong("id_compr");
                    final String sAuxFechaCompr = Format.toStr(rs.getDate("fecha_compr"));
                    JOptionPane.showMessageDialog(this, "Este núm. de Factura ya fue procesada (" + tbl + ")" + System.getProperty("line.separator") + "Núm. de Orden: " + lAuxIdCompr + ", de fecha: " + sAuxFechaCompr);
                    java.awt.EventQueue.invokeLater(txtNroFact::requestFocusInWindow);
                    return false;
                }
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(this, "No fue posible verificar la validez de la factura" + System.getProperty("line.separator") + _ex);
                return false;
            }
        }

        return true;
    }

    /**
     * Rev 12/10/2016
     *
     * @param _param
     * @param _tblItem
     * @param _IVA_DEC_VALUE
     * @throws Exception
     */
    public static void genMaster(final Map<String, Object> _param, final JTable _tblItem, BigDecimal _IVA_DEC_VALUE) throws Exception {

        try (final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO " + ((TipoCompr) _param.get("tipo_compr")).getTbl()
                + "(id_compr, id_user, id_session, id_causado, id_orden_pago, " // 1, 2, 3, 4, 5
                + "fecha_compr, date_session, benef_razonsocial, benef_rif_ci, " // 6, 7, 8, 9
                + "observacion, num_fact, num_control, fecha_fact, total_bs, " // 10, 11, 12, 13, 14
                + "base_imponible_bs, iva_grav_bs, iva_porc_aplic, iva_porc_ret, " // 15, 16, 17, 18
                + "islr_grav_bs, islr_porc_ret, ejefis, " // 19, 20, 21
                + "oret_grav_bs, oret_bs) " // 22, 23
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pst.setLong(1, (long) _param.get("id_compr"));
            pst.setLong(2, UserPassIn.getIdUser());
            pst.setLong(3, UserPassIn.getIdSession());
            pst.setLong(4, 0); // id_causado
            pst.setLong(5, 0); // id_pago
            pst.setDate(6, (java.sql.Date) _param.get("fecha_compr"));
            pst.setTimestamp(7, UserPassIn.getDateSession());
            pst.setString(8, (String) _param.get("benef_razonsocial"));
            pst.setString(9, (String) _param.get("benef_rif_ci"));
            pst.setString(10, (String) _param.get("observacion"));

            pst.setString(11, (String) _param.get("num_fact"));
            pst.setString(12, (String) _param.get("num_control"));
            pst.setDate(13, (java.sql.Date) _param.get("fecha_fact"));
            pst.setBigDecimal(14, (BigDecimal) _param.get("total_bs"));

            BigDecimal base_imponible = (BigDecimal) _param.get("base_imponible_bs");
            pst.setBigDecimal(15, base_imponible);

            pst.setBigDecimal(16, (BigDecimal) _param.get("iva_grav_bs"));
            pst.setBigDecimal(17, _IVA_DEC_VALUE.movePointRight(2)); // iva_porc_aplic

            pst.setBigDecimal(18, BigDecimal.ZERO); // iva_porc_ret
            pst.setBigDecimal(19, (BigDecimal) _param.get("islr_grav_bs")); // islr_grav_bs
            pst.setBigDecimal(20, BigDecimal.ZERO); // islr_porc_ret
            pst.setDate(21, Globales.getEjefis());
            pst.setBigDecimal(22, BigDecimal.ZERO);
            pst.setBigDecimal(23, BigDecimal.ZERO);

            if (pst.executeUpdate() != 1) {
                throw new Exception("Error al insertar el registro");
            }
        }
    }

    /**
     * Anular una orden de compromiso
     *
     * Rev 12/10/2016
     *
     * @param _id_compr
     * @param _tipo_compr
     * @throws Exception
     */
    public static void anuMaster(final long _id_compr, final TipoCompr _tipo_compr) throws Exception {

        try (final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE " + _tipo_compr.getTbl() + " SET anulado_sn= 'S' WHERE id_compr = " + _id_compr)) {
            if (pst.executeUpdate() != 1) {
                throw new Exception("Error en la cantidad de registros presupe actualizados");
            }
        }
    }

    /**
     * Rev 12/10/2016
     *
     * @param _tblItem
     * @param _param
     * @throws Exception
     */
    public static void genDetail(final Map<String, Object> _param, JTable _tblItem) throws Exception {

        final long id_compr = (long) _param.get("id_compr");
        final TipoCompr tipo_compr = (TipoCompr) _param.get("tipo_compr");

        for (int i = 0; i < _tblItem.getRowCount(); i++) {
            final BigDecimal cantpro = BigDecimal.valueOf((double) _tblItem.getValueAt(i, COL_ITEM_CANT));
            final String descPro = (String) _tblItem.getValueAt(i, COL_ITEM_DESC);
            final BigDecimal punitario = BigDecimal.valueOf((double) _tblItem.getValueAt(i, COL_ITEM_PUNITARIO));
            final String codpresupe = (String) _tblItem.getValueAt(i, COL_ITEM_CODIGO);
            final String partida = (String) _tblItem.getValueAt(i, COL_ITEM_PARTIDA);

            genComprPttoE(codpresupe, Format.toBigDec((double) _tblItem.getValueAt(i, 3)));

            try (final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO compr_det"
                    + "(id_compr, tipo_compr, cantpro, descpro, " // 1, 2, 3, 4
                    + "punitario, codpresu, partida) " // 5, 6, 7
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?)")) {
                pst.setLong(1, id_compr);
                pst.setString(2, tipo_compr.name());
                pst.setBigDecimal(3, cantpro);
                pst.setString(4, descPro);
                pst.setBigDecimal(5, punitario);
                pst.setString(6, codpresupe);
                pst.setString(7, partida);
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error. Al insertar el registro de ítem");
                }
            }
        }
    }

    /**
     * Anular los detalles de una orden de compromiso
     *
     * Rev 12/10/2016
     *
     * @param _id_compr
     * @param _tipo_compr
     * @throws Exception
     */
    public static void anuDetail(final long _id_compr, final TipoCompr _tipo_compr) throws Exception {

        final ResultSet rsComprDet = Conn.executeQuery("SELECT * FROM compr_det "
                + "WHERE id_compr= " + _id_compr + " AND tipo_compr= '" + _tipo_compr.name() + "'");
        while (rsComprDet.next()) {
            final ComprDetModel regComprDet = new ComprDetModel(rsComprDet);
            anuComprPttoE(regComprDet.getCodPresu(), regComprDet.getCantPro().multiply(regComprDet.getPUnitario()).setScale(2, RoundingMode.HALF_UP));
        }

    }

    /**
     * Rev 15/11/2016
     *
     * @param _id
     * @param _param
     * @param _export
     * @throws Exception
     */
    public static void genReport(final long _id, final Map<String, Object> _param, final boolean _export) throws Exception {
        final TipoCompr tipo_compr = (TipoCompr) _param.get("tipo_compr");

        final ComprModel compr = ComprModel.getxID(_id, tipo_compr);

        if (compr == null) {
            JOptionPane.showMessageDialog(null, "Registro no encontrado");
            return;
        }

        final BenefModel benef = BenefModel.getxRifCi(compr.getBenef_rif_ci());

        final Map<String, Object> param = new HashMap<>(101);
        param.put("benef_domicilio", benef.getDomicilio());
        param.put("benef_razonsocial", benef.getRazonsocial());
        param.put("benef_rif_ci", benef.getRif_ci());
        param.put("benef_telefonos", benef.getTelefonos());
        param.put("date_session", UserPassIn.getDateSession());
        param.put("ejefis", Globales.getEjeFisYear());
        param.put("fechacompr", Format.toStr(compr.getFecha_compr()));
        param.put("fechafact", Format.toStr(compr.getFechaFact()));
        param.put("id_user", UserPassIn.getIdUser());
        param.put("id_session", UserPassIn.getIdSession());
        param.put("numfact", compr.getNumFact());

        final String NumLiteral = Num2Let.convert(Format.toBigDec(compr.getTotal_bs()));
        final String NumLiteral_1;
        final String NumLiteral_2;

        if (NumLiteral.length() < 65) {
            NumLiteral_1 = NumLiteral;
            NumLiteral_2 = "";
        } else {
            int pos = NumLiteral.lastIndexOf(" CON ");
            final String s1 = NumLiteral.substring(0, pos);
            if (s1.length() > 65) {
                pos = s1.lastIndexOf(" ") + 1;
            }

            NumLiteral_1 = NumLiteral.substring(0, pos).trim();
            NumLiteral_2 = NumLiteral.substring(pos).trim();
        }

        param.put("NumLiteral_1", NumLiteral_1);
        param.put("NumLiteral_2", NumLiteral_2);

        param.put("Numorden", compr.getId_compromiso());
        param.put("observacion", compr.getObservacion());
        param.put("tipo_compr", tipo_compr.name().toUpperCase());
        param.put("titulo", tipo_compr.getRpt().toUpperCase());
        param.put("total", Format.toStr(compr.getTotal_bs()));
        param.put("user", UserPassIn.getUserName());
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("anulado", compr.getAnulado_sn().equals("S") ? "ANULADO" : "");

        param.put("aux_1", CapipPropiedades.CAPIP_AUX_1);
        param.put("aux_2", CapipPropiedades.CAPIP_AUX_2);
        param.put("linea_1", CapipPropiedades.CAPIP_LINEA_1);
        param.put("linea_2", CapipPropiedades.CAPIP_LINEA_2);
        param.put("linea_3", CapipPropiedades.CAPIP_LINEA_3);
        param.put("linea_4", CapipPropiedades.CAPIP_LINEA_4);

        param.put("desc_1", CapipPropiedades.CAPIP_DESC_1);
        param.put("desc_2", CapipPropiedades.CAPIP_DESC_2);
        param.put("desc_3", CapipPropiedades.CAPIP_DESC_3);
        param.put("desc_4", CapipPropiedades.CAPIP_DESC_4);
        param.put("desc_5", CapipPropiedades.CAPIP_DESC_5);
        param.put("desc_6", CapipPropiedades.CAPIP_DESC_6);
        param.put("desc_7", CapipPropiedades.CAPIP_DESC_7);
        param.put("desc_8", CapipPropiedades.CAPIP_DESC_8);

        param.put("func_1", CapipPropiedades.CAPIP_FUNC_1);
        param.put("func_2", CapipPropiedades.CAPIP_FUNC_2);
        param.put("func_3", CapipPropiedades.CAPIP_FUNC_3);
        param.put("func_4", CapipPropiedades.CAPIP_FUNC_4);
        param.put("func_5", CapipPropiedades.CAPIP_FUNC_5);
        param.put("func_6", CapipPropiedades.CAPIP_FUNC_6);
        param.put("func_7", CapipPropiedades.CAPIP_FUNC_7);
        param.put("func_8", CapipPropiedades.CAPIP_FUNC_8);

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/compromiso.jasper");
        if (pathRpt == null) {
            JOptionPane.showMessageDialog(null, "Reporte de orden de compra no encontrado");
        } else {
            new Thread() {
                @Override
                public void run() {
                    try {
                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), Conn.getConnection());

                        JasperViewer.viewReport(jasperPrint, false);

                        if (_export) {
                            JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_COMPROMISO + "/" + _id + ".pdf");
                        }

                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        }
    }

    /**
     * Rev 14/09/2016
     *
     */
    private void actSalir() {
        if (tblItem.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(this, "Hay registros pendientes . ¿ Seguro desea Salir ?",
                    "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }
        }

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

    private void txtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescActionPerformed
        txtDesc.transferFocus();
    }//GEN-LAST:event_txtDescActionPerformed

    private void txtCantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantActionPerformed
        txtCant.transferFocus();
    }//GEN-LAST:event_txtCantActionPerformed

    private void txtNroFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroFactActionPerformed
        txtNroFact.transferFocus();
    }//GEN-LAST:event_txtNroFactActionPerformed

    private void txtFechaFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFactActionPerformed
        java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
    }//GEN-LAST:event_txtFechaFactActionPerformed

    /**
     * Rev 13/09/2016
     *
     * @param evt
     */
    private void txtPUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPUnitarioActionPerformed
        Compromiso.newSubTotal((JFormattedTextField) txtCant, (JFormattedTextField) txtPUnitario, txtSubTotal);

        try {
            if (Format.toDouble(txtSubTotal.getText()) > 0) {

                final java.awt.Window me = this;
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new PresupeSel(me, 0).setVisible(true);
                        me.setVisible(false);
                    }
                });

            }
        } catch (final Exception _ex) {
            txtSubTotal.setText("0,00");
        }
    }//GEN-LAST:event_txtPUnitarioActionPerformed

    /**
     * Rev 13/09/2016
     *
     * @param evt
     */
    private void txtCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyTyped
        if (!"0123456789.".contains(String.valueOf(evt.getKeyChar())) || txtCant.getText().length() > 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantKeyTyped

    /**
     * Rev 13/09/2016
     *
     * @param evt
     */
    private void txaConceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaConceptoKeyTyped
        if (txaConcepto.getText().length() >= 512) {
            evt.consume();
        }
    }//GEN-LAST:event_txaConceptoKeyTyped

    /**
     * Rev 13/09/2016
     *
     * @param evt
     */
    private void txtNroFactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroFactKeyTyped
        if (txtNroFact.getText().length() >= 32) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNroFactKeyTyped

    private void txtPUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPUnitarioKeyTyped
        if (!"0123456789.".contains(String.valueOf(evt.getKeyChar())) || (txtPUnitario.getText().length() > 16)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPUnitarioKeyTyped

    private void txtDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescKeyTyped
        if (txtDesc.getText().length() > 128) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescKeyTyped

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        if (JOptionPane.showConfirmDialog(this, "La operación de anulación de Compromiso es irreversible. ¿ Desea continuar ?",
                "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }

        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new CompromisoAnular(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnBenefNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBenefNuevoActionPerformed
        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                regPptoSel = null;
                new RegistroBenef(me, false).setVisible(true);
                setVisible(false);
            }
        });
    }//GEN-LAST:event_btnBenefNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CompromisoSelEditar(me).setVisible(true);
                setVisible(false);
            }
        });
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        setStartConditions();
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void txtNumControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumControlActionPerformed
        txtNumControl.transferFocus();
    }//GEN-LAST:event_txtNumControlActionPerformed

    private void chkISLRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkISLRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkISLRActionPerformed

    /**
     *
     * @param _cmb
     */
    public static void cargarComboIva(final JComboBox<String> _cmb) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        try {
            modeloCombo.addElement("  0,00% #\t 00.00.00.00.000.00.00.00 #\t 0");
            final ResultSet rs = Conn.executeQuery("SELECT * FROM iva_aplicado ORDER BY id_iva_aplicado  ");
            while (rs.next()) {
                final IvaAplicModel regIva = new IvaAplicModel(rs);
                final PptoModel regPpto = PptoModel.getReg_x_Id("presupe", regIva.getId_part_ppto());

                if (regPpto != null) {
                    modeloCombo.addElement(Format.toStr(regIva.getValor_porc()) + "% #\t " + regPpto.getCodigo() + " #\t " + regIva.getId_iva_aplicado());
                } else {
                    JOptionPane.showMessageDialog(null, "Error en Partida asociada al IVA " + Format.toStr(regIva.getValor_porc()) + "%" + System.getProperty("line.separator")
                            + "Aún no se encuentra cargada en el Presupuesto" + System.getProperty("line.separator")
                            + "Trate cargando dicha partida, o reestableciendo el IVA Aplicado");
                    return;
                }
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        _cmb.setModel(modeloCombo);

        if (_cmb.getItemCount() >= 2) {
            _cmb.setSelectedIndex(1);
        } else {
            _cmb.setSelectedIndex(0);
        }

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
                new Compromiso(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnBenefNuevo;
    private javax.swing.JToggleButton btnCompra;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnIVA;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JToggleButton btnOtrosCompr;
    private javax.swing.JToggleButton btnReiniciar;
    private javax.swing.JToggleButton btnServicio;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkISLR;
    private javax.swing.JComboBox<String> cmbIva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblCO;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblOC;
    private javax.swing.JLabel lblOS;
    private javax.swing.JLabel lblTipoCompr;
    private javax.swing.JTable tblBenef;
    private javax.swing.JTable tblItem;
    private javax.swing.JTextArea txaConcepto;
    private javax.swing.JTextField txtCant;
    private javax.swing.JTextField txtCodPartida;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JFormattedTextField txtFechaCompr;
    private javax.swing.JFormattedTextField txtFechaFact;
    private javax.swing.JFormattedTextField txtID_Compr;
    private javax.swing.JTextField txtNroFact;
    private javax.swing.JTextField txtNumControl;
    private javax.swing.JTextField txtPUnitario;
    private javax.swing.JTextField txtRIF_CI;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JFormattedTextField txtSubTotal;
    private javax.swing.JTextField txtTelefonos;
    private javax.swing.JFormattedTextField txtTotalBs;
    // End of variables declaration//GEN-END:variables

}
