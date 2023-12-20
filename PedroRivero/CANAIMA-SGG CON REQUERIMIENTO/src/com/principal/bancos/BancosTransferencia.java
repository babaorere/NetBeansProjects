package com.principal.bancos;

import com.principal.capipsistema.Globales;
import com.principal.capipsistema.Propiedades;
import com.principal.capipsistema.UserPassIn;
import com.principal.capipsistema.UserTrack;
import com.principal.connection.ConnCapip;
import com.principal.modelos.ModelBancosOperaciones;
import com.principal.modelos.ModelMapNextOp;
import com.principal.utils.DateCellRenderer;
import com.principal.utils.DecimalCellRenderer;
import com.principal.utils.Format;
import com.principal.utils.IntegerCellRenderer;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
public final class BancosTransferencia extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private final java.awt.Window parent;

    final static int colId = 0;

    final static int colFecha = 1;

    final static int colCuenta = 2;

    final static int colBanco = 3;

    final static int colMonto = 4;

    final static int colSoporte = 5;

    final static int colDesc = 6;

    final static int colReg = 7;

    int edo;

    double montoIni;

    final static int edoNormal = 0;

    final static int edoNuevo = 1;

    /**
     * Creates new form transferencia
     *
     * @param inparent
     */
    public BancosTransferencia(final java.awt.Window inparent) {
        super();
        initComponents();
        parent = inparent;
        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
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
     * Establece el comportamiento de la presente Ventana
     * Rev 21/09/2016
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        setTitle(Propiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + Propiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        // Establecer acción al cerrar ventana
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

    private void setCompBehavior() {
        // Para actualizar automaticamente los campos de detalles, al seleccionar una fila de la tabla
        // al estilo master/detail
        tblMaster.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent inlse) {
                actUpdateItem();
            }
        });
        AbstractAction action = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                actUpdateItem();
            }
        };
        // Configurar la tabla de los Beneficiarios, para manejar el ENTER
        tblMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "action.enter");
        tblMaster.getActionMap().put("action.enter", action);
        // Configurar la tabla de los Beneficiarios, para manejar el SPACE
        tblMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "action.space");
        tblMaster.getActionMap().put("action.space", action);
        // Configurar la tabla de Maestra, para alinear las columnas
        tblMaster.getColumnModel().getColumn(colFecha).setCellRenderer(new DateCellRenderer());
        tblMaster.getColumnModel().getColumn(colFecha).setCellRenderer(new DateCellRenderer());
        DefaultTableCellRenderer tcr;
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colCuenta).setCellRenderer(tcr);
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colSoporte).setCellRenderer(tcr);
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 25/11/2016
     */
    private void setStartConditions() {
        edo = edoNormal;
        cargarComboCuentaOrigen();
        cargarComboCuentaDestino();
        mostrardatosbancos_det("");
        txtFecha.setText(fechaActual());
        txtDescrip.transferFocus();
    }

    /**
     * Rev 25/11/2016
     *
     * @return
     */
    public static String fechaActual() {
        return new SimpleDateFormat("dd/MM/YYYY").format(Globales.getServerTimeStamp());
    }

    /**
     * ev 24-01-2017
     *
     * @param valor
     */
    private void mostrardatosbancos_det(String valor) {
        tblMaster.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        final String sql;
        // Se mostraran solo las transferencias "origen" id_trans_ori='0'
        if (valor.equals("")) {
            sql = "SELECT * FROM bancos_operaciones WHERE ejefis= " + Globales.getEjeFisYear() + " AND tipo_operacion= 'TR' AND id_trans_ori= '0' AND anulado_sn= 'N' ORDER BY id DESC";
        } else {
            sql = "SELECT * FROM bancos_operaciones WHERE ejefis= " + Globales.getEjeFisYear() + " AND cuenta='" + valor + "' AND tipo_operacion='TR' AND id_trans_ori='0' AND anulado_sn= 'N' ORDER BY id DESC";
        }
        final Object[] datos = new Object[8];
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery(sql);
            while (rs.next()) {
                com.principal.modelos.ModelBancosOperaciones reg = new com.principal.modelos.ModelBancosOperaciones(rs);
                datos[colId] = reg.getId();
                datos[colFecha] = Format.toDateSql(reg.getFecha());
                datos[colCuenta] = reg.getCuenta();
                datos[colBanco] = reg.getBanco();
                datos[colMonto] = reg.getHaber();
                datos[colSoporte] = reg.getSoporte_o_Cheque();
                datos[colDesc] = reg.getDesc();
                datos[colReg] = reg;
                model.addRow(datos);
            }
            tblMaster.setModel(model);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
    }

    /**
     * Rev 24-01-2017
     */
    private void limpiar() {
        txtSoporte.setEnabled(false);
        txtDescrip.setEnabled(false);
        txtMonto.setEnabled(false);
        txtSoporte.setText("");
        txtDescrip.setText("");
        txtMonto.setValue(BigDecimal.ZERO);
        txtFecha.setText(fechaActual());
    }

    /**
     * Rev 24-01-2017
     */
    private void cargarComboCuentaOrigen() {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM bancos ORDER BY banco, cuenta ASC");
            while (rs.next()) {
                final String cuenta = rs.getString("cuenta");
                modeloCombo.addElement(cuenta + "  " + rs.getString("banco") + "  " + Format.toStr(BancosContabilidad.getSaldoF(cuenta, Globales.getEjeFisYear(), 12)));
            }
            cbxDes.setModel(modeloCombo);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        cbxDes.setSelectedIndex(-1);
    }

    /**
     * Rev 24-01-2017
     */
    private void cargarComboCuentaDestino() {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM bancos ORDER BY banco, cuenta ASC");
            while (rs.next()) {
                final String cuenta = rs.getString("cuenta");
                modeloCombo.addElement(cuenta + "  " + rs.getString("banco") + "  " + Format.toStr(BancosContabilidad.getSaldoF(cuenta, Globales.getEjeFisYear(), 12)));
            }
            cbxOri.setModel(modeloCombo);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        cbxOri.setSelectedIndex(-1);
    }

    /**
     * Rev 24-01-2017
     */
    private String trimBanco(String inauxBanco) {
        if (inauxBanco == null) {
            return "";
        }
        String aux = inauxBanco.trim();
        if (aux.isEmpty()) {
            return "";
        }
        int pos = -1;
        for (int i = aux.length() - 1; i >= 0; i--) {
            if (Character.isLetter(aux.charAt(i))) {
                pos = i;
                break;
            }
        }
        final String s;
        if (pos >= 0) {
            s = aux.substring(0, pos + 1);
        } else {
            s = "";
        }
        return s;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtDescrip = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSoporte = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JFormattedTextField();
        txtMonto = new javax.swing.JFormattedTextField(com.principal.presupuesto.PptoGlobales.getCurFormatter());
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbxDes = new javax.swing.JComboBox<>();
        cbxOri = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnReversar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("TRANSFERENCIAS BANCARIAS");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        // NOI18N
        tblMaster.setFont(new java.awt.Font("Arial", 3, 16));
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "ID", "Fecha", "Cuenta", "Banco", "Monto", "Soporte", "Concepto", "Reg" }) {

            Class[] types = new Class[] { java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class };

            boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblMaster.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblMaster.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMasterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(100);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(125);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(125);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(175);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(300);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(350);
            tblMaster.getColumnModel().getColumn(6).setMinWidth(125);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(250);
            tblMaster.getColumnModel().getColumn(6).setMaxWidth(350);
            tblMaster.getColumnModel().getColumn(7).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(7).setMaxWidth(0);
        }
        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 892, 136));
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Arial", 3, 18));
        jLabel1.setText("TRANSFERENCIAS EXISTENTES");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 301, -1));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());
        // NOI18N
        txtDescrip.setFont(new java.awt.Font("Arial", 3, 16));
        txtDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescrip.setEnabled(false);
        txtDescrip.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtDescrip.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 376;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(txtDescrip, gridBagConstraints);
        // NOI18N
        jLabel2.setFont(new java.awt.Font("Arial", 3, 12));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("FECHA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 68;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        // NOI18N
        jLabel3.setFont(new java.awt.Font("Arial", 3, 12));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CONCEPTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 312;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);
        // NOI18N
        jLabel4.setFont(new java.awt.Font("Arial", 3, 12));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("MONTO Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 64;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);
        // NOI18N
        txtSoporte.setFont(new java.awt.Font("Arial", 3, 16));
        txtSoporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoporte.setEnabled(false);
        txtSoporte.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtSoporte.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoporteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 136;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(txtSoporte, gridBagConstraints);
        // NOI18N
        jLabel8.setFont(new java.awt.Font("Arial", 3, 12));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("SOPORTE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 82;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(jLabel8, gridBagConstraints);
        txtFecha.setBackground(new java.awt.Color(240, 240, 240));
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setEnabled(false);
        // NOI18N
        txtFecha.setFont(new java.awt.Font("Arial", 3, 16));
        txtFecha.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtFecha.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 116;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(txtFecha, gridBagConstraints);
        txtMonto.setBackground(new java.awt.Color(175, 204, 125));
        txtMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMonto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtMonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonto.setEnabled(false);
        // NOI18N
        txtMonto.setFont(new java.awt.Font("Arial", 3, 16));
        txtMonto.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 134;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(txtMonto, gridBagConstraints);
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 890, 110));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        // NOI18N
        jLabel6.setFont(new java.awt.Font("Arial", 3, 12));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CUENTAS ORIGEN");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 167, -1));
        // NOI18N
        jLabel7.setFont(new java.awt.Font("Arial", 3, 12));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("CUENTAS DESTINO");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 167, -1));
        cbxDes.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        cbxDes.setFont(new java.awt.Font("Arial", 2, 16));
        cbxDes.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDesActionPerformed(evt);
            }
        });
        jPanel2.add(cbxDes, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 790, -1));
        cbxOri.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        cbxOri.setFont(new java.awt.Font("Arial", 2, 16));
        cbxOri.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxOriActionPerformed(evt);
            }
        });
        jPanel2.add(cbxOri, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 790, -1));
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 890, 140));
        // NOI18N
        jLabel5.setFont(new java.awt.Font("Arial", 3, 18));
        jLabel5.setText("             DATOS DE TRANSFERENCIA");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, 368, -1));
        // NOI18N
        jLabel9.setFont(new java.awt.Font("Arial", 3, 18));
        jLabel9.setText("CUENTAS BANCARIAS A TRANSFERIR");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 360, -1));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());
        btnGuardar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnGuardar.setFont(new java.awt.Font("Arial", 2, 18));
        btnGuardar.setText("  GUARDAR  ");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIconTextGap(-3);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 20, 0);
        jPanel3.add(btnGuardar, gridBagConstraints);
        btnReversar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnReversar.setFont(new java.awt.Font("Arial", 2, 18));
        btnReversar.setText("  REVERSAR  ");
        btnReversar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReversar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReversarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 20, 0);
        jPanel3.add(btnReversar, gridBagConstraints);
        btnConsultar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnConsultar.setFont(new java.awt.Font("Arial", 2, 18));
        btnConsultar.setText("  CONSULTAR  ");
        btnConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnConsultar.setEnabled(false);
        btnConsultar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 20, 0);
        jPanel3.add(btnConsultar, gridBagConstraints);
        btnSalir.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnSalir.setFont(new java.awt.Font("Arial", 2, 18));
        btnSalir.setText("  SALIR  ");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setIconTextGap(-3);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 20, 0);
        jPanel3.add(btnSalir, gridBagConstraints);
        btnNuevo.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnNuevo.setFont(new java.awt.Font("Arial", 2, 18));
        btnNuevo.setText("  NUEVO  ");
        btnNuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setIconTextGap(-3);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 20, 0);
        jPanel3.add(btnNuevo, gridBagConstraints);
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 890, 60));
        btnReset.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnReset.setFont(new java.awt.Font("Tahoma", 0, 16));
        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        getContentPane().add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, -1, -1));
        // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png")));
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 630));
        pack();
        setLocationRelativeTo(null);
    }

    // </editor-fold>//GEN-END:initComponents
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }

//GEN-LAST:event_btnSalirActionPerformed
    private void tblMasterMouseClicked(java.awt.event.MouseEvent evt) {
//GEN-FIRST:event_tblMasterMouseClicked
        actUpdateItem();
    }

//GEN-LAST:event_tblMasterMouseClicked
    private void cbxDesActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_cbxDesActionPerformed
        int idx = cbxDes.getSelectedIndex();
        if (idx < 0) {
            return;
        }
        final String cuenta = ((String) cbxDes.getSelectedItem()).substring(0, 20);
        mostrardatosbancos_det(cuenta);
        txtFecha.requestFocus();
    }

//GEN-LAST:event_cbxDesActionPerformed
    private void cbxOriActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_cbxOriActionPerformed
        int idx = cbxOri.getSelectedIndex();
        if (idx < 0) {
            return;
        }
        final String cuenta = ((String) cbxOri.getSelectedItem()).substring(0, 20);
        mostrardatosbancos_det(cuenta);
        txtFecha.requestFocus();
    }

//GEN-LAST:event_cbxOriActionPerformed
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnGuardarActionPerformed
        String cuentaOrigen = "";
        String bancoOrigen = "";
        String cuentaDestino = "";
        String bancoDestino = "";
        if (edo == edoNuevo) {
            if (cbxOri.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta de origen");
                cbxOri.requestFocusInWindow();
                return;
            }
            cuentaOrigen = ((String) cbxOri.getSelectedItem()).substring(0, 20);
            bancoOrigen = trimBanco(((String) cbxOri.getSelectedItem()).substring(21));
            if (cuentaOrigen.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta de origen");
                cbxOri.requestFocusInWindow();
                return;
            }
            if (cbxDes.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta de destino");
                cbxDes.requestFocusInWindow();
                return;
            }
            cuentaDestino = ((String) cbxDes.getSelectedItem()).substring(0, 20);
            bancoDestino = trimBanco(((String) cbxDes.getSelectedItem()).substring(21));
            if (cuentaDestino.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta de destino");
                cbxDes.requestFocusInWindow();
                return;
            }
            if (cuentaDestino.equals(cuentaOrigen)) {
                JOptionPane.showMessageDialog(null, "Las cuentas destino y final, deben ser distintas");
                cbxOri.requestFocusInWindow();
                return;
            }
        }
        final String soporte = txtSoporte.getText().trim().toUpperCase();
        if (soporte.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Soporte inválido");
            txtSoporte.requestFocusInWindow();
            return;
        }
        final String desc = txtDescrip.getText().trim().toUpperCase();
        if (desc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descripción inválida");
            txtDescrip.requestFocusInWindow();
            return;
        }
        final String sMonto = txtMonto.getText().trim().toUpperCase();
        if (sMonto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            txtMonto.requestFocusInWindow();
            return;
        }
        final double monto;
        try {
            monto = (double) Globales.curFormat.stringToValue(sMonto);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al formatear: " + System.getProperty("line.separator") + inex);
            logger.error(inex);
            return;
        }
        if (monto <= 0.00d) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            txtMonto.requestFocusInWindow();
            return;
        }
        final String sFecha = txtFecha.getText().trim();
        if (sFecha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fecha inválida");
            txtFecha.requestFocusInWindow();
            return;
        }
        final java.util.Date fecha;
        try {
            fecha = Globales.dateFormat.parse(sFecha);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Fecha inválida");
            logger.error(inex);
            return;
        }
        final Calendar fechaOp = Calendar.getInstance();
        final long ejeFis = fechaOp.get(Calendar.YEAR);
        final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;
        final String tipo_operacion = "TR";
        // Verificar si el soporte/cheque para la cuenta, en el presente ejercicio fiscal es valido
        try {
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM bancos_operaciones " + "WHERE ejefis= ? AND cuenta= ? AND tipo_operacion= ? AND soporte_o_cheque= ?");
            pst.setLong(1, ejeFis);
            pst.setString(2, cuentaOrigen);
            pst.setString(3, tipo_operacion);
            pst.setString(4, soporte);
            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new Exception("Soporte/Cheque inválido, posiblemente ya se halla utilizado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        try {
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM bancos_operaciones " + "WHERE ejefis= ? AND cuenta= ? AND tipo_operacion= ? AND soporte_o_cheque= ?");
            pst.setLong(1, ejeFis);
            pst.setString(2, cuentaDestino);
            pst.setString(3, tipo_operacion);
            pst.setString(4, soporte);
            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new Exception("Soporte/Cheque inválido, posiblemente ya se halla utilizado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        if (edo == edoNuevo) {
            try {
                ConnCapip.getInstance().BeginTransaction();
                final PreparedStatement pstOri = ConnCapip.getInstance().getConnection().prepareStatement(// 1, 2, 3, 4, 5,
                        "INSERT INTO bancos_operaciones("
                        + // 6, 7, 8, 9, 10
                        "cuenta, banco, descripcion, fecha, tipo_operacion, "
                        + // 11, 12, 13,
                        "soporte_o_cheque, conciliado, debe, haber, id_trans_ori, "
                        + // 14, 15, 16
                        "cuenta_numop, cuenta_numop_tipo, num_pag_ava, " + "ejefis, ejefismes, iduser) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstOri.setString(1, cuentaOrigen);
                pstOri.setString(2, bancoOrigen);
                pstOri.setString(3, desc);
                pstOri.setString(4, sFecha);
                pstOri.setString(5, tipo_operacion);
                // soporte
                pstOri.setString(6, soporte);
                // conciliado
                pstOri.setString(7, "");
                // debe
                pstOri.setDouble(8, 0.00d);
                // haber
                pstOri.setDouble(9, monto);
                // id_trans_ori
                pstOri.setInt(10, 0);
                pstOri.setLong(11, ModelMapNextOp.getNext(Globales.getEjeFisYear(), cuentaOrigen));
                final long cuenta_numop_tipo = ModelMapNextOp.getNext(Globales.getEjeFisYear(), cuentaOrigen + "-" + tipo_operacion);
                // cuenta_numop_tipo
                pstOri.setLong(12, cuenta_numop_tipo);
                // num_pag_ava
                pstOri.setInt(13, 0);
                pstOri.setLong(14, ejeFis);
                pstOri.setLong(15, ejeFisMes);
                pstOri.setLong(16, UserPassIn.getIdUser());
                if (pstOri.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos");
                    txtSoporte.requestFocusInWindow();
                    try {
                        ConnCapip.getInstance().RollBack();
                    } catch (final Exception inex1) {
                        JOptionPane.showMessageDialog(null, inex1);
                        logger.error(inex1);
                    }
                    return;
                }
                final ResultSet rsID = ConnCapip.getInstance().getConnection().prepareStatement("SELECT last_insert_id() AS last_id FROM bancos_operaciones").executeQuery();
                final int idOri;
                if (rsID.next()) {
                    idOri = rsID.getInt("last_id");
                } else {
                    idOri = 0;
                }
                final PreparedStatement pstDest = ConnCapip.getInstance().getConnection().prepareStatement(// 1, 2, 3, 4, 5
                        "INSERT INTO bancos_operaciones("
                        + // 6, 7, 8, 9, 10
                        "cuenta, banco, descripcion, fecha, tipo_operacion, "
                        + // 11, 12, 13
                        "soporte_o_cheque, conciliado, debe, haber, id_trans_ori, "
                        + // 14, 15, 16
                        "cuenta_numop, cuenta_numop_tipo, num_pag_ava, " + "ejefis, ejefismes, iduser) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstDest.setString(1, cuentaDestino);
                pstDest.setString(2, bancoDestino);
                pstDest.setString(3, desc);
                pstDest.setString(4, sFecha);
                pstDest.setString(5, tipo_operacion);
                // soporte
                pstDest.setString(6, soporte);
                // conciliado
                pstDest.setString(7, "");
                // debe
                pstDest.setDouble(8, monto);
                // haber
                pstDest.setDouble(9, 0.00d);
                // id_trans_ori
                pstDest.setInt(10, idOri);
                pstDest.setLong(11, ModelMapNextOp.getNext(Globales.getEjeFisYear(), cuentaDestino));
                // cuenta_numop_tipo
                pstDest.setLong(12, cuenta_numop_tipo);
                // num_pag_ava
                pstDest.setInt(13, 0);
                pstDest.setLong(14, ejeFis);
                pstDest.setLong(15, ejeFisMes);
                pstDest.setLong(16, UserPassIn.getIdUser());
                if (pstDest.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos");
                    txtSoporte.requestFocusInWindow();
                    try {
                        ConnCapip.getInstance().RollBack();
                    } catch (final Exception inex1) {
                        JOptionPane.showMessageDialog(null, inex1);
                        logger.error(inex1);
                    }
                    return;
                }
                mostrardatosbancos_det("");
                cargarComboCuentaOrigen();
                cargarComboCuentaDestino();
                ConnCapip.getInstance().EndTransaction();
                JOptionPane.showMessageDialog(null, "Operación realizada");
                edo = edoNormal;
                btnNuevo.setEnabled(true);
                btnReversar.setEnabled(true);
                btnConsultar.setEnabled(false);
                tblMaster.requestFocusInWindow();
                limpiar();
            } catch (final Exception inex) {
                try {
                    ConnCapip.getInstance().RollBack();
                } catch (final Exception inex1) {
                    JOptionPane.showMessageDialog(null, inex1);
                    logger.error(inex1);
                }
                JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + System.getProperty("line.separator") + inex);
                txtSoporte.requestFocusInWindow();
                logger.error(inex);
            }
        } else if (edo == edoNormal) {
            int rowSel = tblMaster.getSelectedRow();
            if (rowSel < 0) {
                JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + "Debe seleccionar un registro");
                return;
            }
            try {
                ConnCapip.getInstance().BeginTransaction();
                final ModelBancosOperaciones regOri = (ModelBancosOperaciones) tblMaster.getValueAt(rowSel, colReg);
                final ModelBancosOperaciones regDest;
                final ResultSet rsDest = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM bancos_operaciones " + "WHERE id_trans_ori=' AND anulado_sn= 'N'" + regOri.getId() + "'").executeQuery();
                if (rsDest.next()) {
                    regDest = new ModelBancosOperaciones(rsDest);
                } else {
                    throw new Exception("Erroe de integridad. Registro destino no encontrado");
                }
                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE bancos_operaciones SET soporte_o_cheque= ?, descripcion= ?, haber= ?  " + "WHERE id= ? AND anulado_sn= 'N'");
                // soporte
                pst.setString(1, Propiedades.setColStrLen("bancos_operaciones", "soporte_o_cheque", soporte));
                // desc
                pst.setString(2, Propiedades.setColStrLen("bancos_operaciones", "descripcion", desc));
                // haber
                pst.setDouble(3, monto);
                pst.setLong(4, regOri.getId());
                if (pst.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos");
                    txtSoporte.requestFocusInWindow();
                    try {
                        ConnCapip.getInstance().RollBack();
                    } catch (final Exception inex1) {
                        JOptionPane.showMessageDialog(null, inex1);
                        logger.error(inex1);
                    }
                    return;
                }
                final PreparedStatement pstDest = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE bancos_operaciones SET soporte_o_cheque= ?, descripcion= ?, debe= ?  " + "WHERE id_trans_ori= ? AND anulado_sn= 'N'");
                // soporte
                pstDest.setString(1, Propiedades.setColStrLen("bancos_operaciones", "soporte_o_cheque", soporte));
                // desc
                pstDest.setString(2, Propiedades.setColStrLen("bancos_operaciones", "descripcion", desc));
                // debe
                pstDest.setDouble(3, monto);
                pstDest.setLong(4, regOri.getId());
                if (pstDest.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos");
                    txtSoporte.requestFocusInWindow();
                    try {
                        ConnCapip.getInstance().RollBack();
                    } catch (final Exception inex1) {
                        JOptionPane.showMessageDialog(null, inex1);
                        logger.error(inex1);
                    }
                    return;
                }
                ConnCapip.getInstance().EndTransaction();
                JOptionPane.showMessageDialog(null, "Operación realizada");
                limpiar();
                tblMaster.requestFocusInWindow();
            } catch (final Exception inex) {
                try {
                    ConnCapip.getInstance().RollBack();
                } catch (final Exception inex1) {
                    JOptionPane.showMessageDialog(null, inex1);
                    logger.error(inex1);
                }
                JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + System.getProperty("line.separator") + inex);
                txtSoporte.requestFocusInWindow();
                logger.error(inex);
            }
        }
    }

//GEN-LAST:event_btnGuardarActionPerformed
    private void btnReversarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnReversarActionPerformed
        int fila = tblMaster.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return;
        }
        final ModelBancosOperaciones regOri = (ModelBancosOperaciones) tblMaster.getValueAt(fila, colId);
        final int id = regOri.getId();
        final double monto = regOri.getHaber();
        try {
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM bancos_operaciones WHERE  id='" + id + "' AND anulado_sn= 'N'");
            if (JOptionPane.showConfirmDialog(this, "¿ Seguro desea Reversar ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                ConnCapip.getInstance().BeginTransaction();
                if (pst.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Error al Reversar");
                    txtSoporte.requestFocusInWindow();
                    try {
                        ConnCapip.getInstance().RollBack();
                    } catch (final Exception inex1) {
                        JOptionPane.showMessageDialog(null, inex1);
                        logger.error(inex1);
                    }
                    return;
                }
                final ResultSet rsDes = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM bancos_operaciones " + "WHERE id_trans_ori='" + id + "' AND anulado_sn= 'N'").executeQuery();
                if (!rsDes.next()) {
                    JOptionPane.showMessageDialog(null, "Error al Reversar");
                    txtSoporte.requestFocusInWindow();
                    try {
                        ConnCapip.getInstance().RollBack();
                    } catch (final Exception inex1) {
                        JOptionPane.showMessageDialog(null, inex1);
                        logger.error(inex1);
                    }
                    return;
                }
                final ModelBancosOperaciones regDes = new ModelBancosOperaciones(rsDes);
                //                BancosContabilidad.sumarCuenta(regOri.getCuenta(), monto);
                //                BancosContabilidad.restarCuenta(regDes.getCuenta(), monto);
                limpiar();
                mostrardatosbancos_det("");
                ConnCapip.getInstance().EndTransaction();
                JOptionPane.showMessageDialog(null, "Operación realizada");
            }
        } catch (final Exception inex) {
            try {
                ConnCapip.getInstance().RollBack();
            } catch (final Exception inex1) {
                JOptionPane.showMessageDialog(null, inex1);
                logger.error(inex1);
            }
            JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + System.getProperty("line.separator") + inex);
            txtSoporte.requestFocusInWindow();
            logger.error(inex);
        }
    }

//GEN-LAST:event_btnReversarActionPerformed
    private void txtSoporteActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtSoporteActionPerformed
        txtSoporte.transferFocus();
    }

//GEN-LAST:event_txtSoporteActionPerformed
    private void txtDescripActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtDescripActionPerformed
        txtDescrip.transferFocus();
    }

//GEN-LAST:event_txtDescripActionPerformed
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnNuevoActionPerformed
        if (edo != edoNormal) {
            return;
        }
        if (cbxDes.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta de origen");
            cbxDes.requestFocusInWindow();
            return;
        }
        if (cbxOri.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta de destino");
            cbxOri.requestFocusInWindow();
            return;
        }
        edo = edoNuevo;
        limpiar();
        txtSoporte.setEnabled(true);
        txtDescrip.setEnabled(true);
        txtMonto.setEnabled(true);
        txtFecha.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnReversar.setEnabled(false);
        btnConsultar.setEnabled(false);
        txtSoporte.requestFocusInWindow();
    }

//GEN-LAST:event_btnNuevoActionPerformed
    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtMontoActionPerformed
        btnGuardar.requestFocusInWindow();
    }

//GEN-LAST:event_txtMontoActionPerformed
    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtFechaActionPerformed
        btnGuardar.requestFocusInWindow();
    }

//GEN-LAST:event_txtFechaActionPerformed
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnResetActionPerformed
        mostrardatosbancos_det("");
        cargarComboCuentaOrigen();
        cargarComboCuentaDestino();
        limpiar();
        if (tblMaster.getRowCount() > 0) {
            tblMaster.setRowSelectionInterval(0, 0);
            tblMaster.scrollRectToVisible(tblMaster.getCellRect(0, 0, true));
        }
        tblMaster.requestFocusInWindow();
    }

//GEN-LAST:event_btnResetActionPerformed
    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {
//GEN-FIRST:event_txtMontoKeyTyped
        if (!"0123456789,".contains(String.valueOf(evt.getKeyChar())) || txtMonto.getText().length() > 16) {
            evt.consume();
        }
    }

//GEN-LAST:event_txtMontoKeyTyped
    /**
     * Rev 25/11/2016
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
     * Rev 24-01-2017
     */
    private void actUpdateItem() {
        int fila = tblMaster.getSelectedRow();
        if (fila < 0) {
            limpiar();
            return;
        }
        txtSoporte.setEnabled(true);
        txtDescrip.setEnabled(true);
        txtMonto.setEnabled(true);
        txtSoporte.setText((String) tblMaster.getValueAt(fila, colSoporte));
        txtDescrip.setText((String) tblMaster.getValueAt(fila, colDesc));
        montoIni = (double) tblMaster.getValueAt(fila, colMonto);
        txtMonto.setText(Format.toStr(montoIni));
        txtFecha.setText(Format.toStr((java.sql.Date) tblMaster.getValueAt(fila, colFecha)));
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
                new BancosTransferencia(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;

    public static javax.swing.JButton btnGuardar;

    public static javax.swing.JButton btnNuevo;

    private javax.swing.JButton btnReset;

    private javax.swing.JButton btnReversar;

    private javax.swing.JButton btnSalir;

    private javax.swing.JComboBox<String> cbxDes;

    private javax.swing.JComboBox<String> cbxOri;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel10;

    private javax.swing.JLabel jLabel2;

    private javax.swing.JLabel jLabel3;

    private javax.swing.JLabel jLabel4;

    private javax.swing.JLabel jLabel5;

    private javax.swing.JLabel jLabel6;

    private javax.swing.JLabel jLabel7;

    private javax.swing.JLabel jLabel8;

    private javax.swing.JLabel jLabel9;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JPanel jPanel3;

    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JTable tblMaster;

    private javax.swing.JTextField txtDescrip;

    private javax.swing.JFormattedTextField txtFecha;

    private javax.swing.JFormattedTextField txtMonto;

    private javax.swing.JTextField txtSoporte;

    // End of variables declaration//GEN-END:variables
    private final Connection cn = ConnCapip.getInstance().getConnection();

    private static final Logger logger = LogManager.getLogger(BancosTransferencia.class);
}
