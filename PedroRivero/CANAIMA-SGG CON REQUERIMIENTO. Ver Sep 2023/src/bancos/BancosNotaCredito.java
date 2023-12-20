package bancos;

import capipsistema.CapipPropiedades;
import connection.ConnCapip;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
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
import modelos.ModelBancosOperaciones;
import modelos.ModelMapNextOp;
import utils.DateCellRenderer;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class BancosNotaCredito extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private final java.awt.Window parent;

    final static int colId = 0;
    final static int colFecha = 1;
    final static int colSoporte = 2;
    final static int colDesc = 3;
    final static int colMonto = 4;
    final static int colReg = 5;

    int edo;
    double montoIni;
    final static int edoNormal = 0;
    final static int edoNuevo = 1;

    /**
     * Creates new form depositos
     *
     * @param inparent
     */
    public BancosNotaCredito(final java.awt.Window inparent) {

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
        if ( inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }

        super.setVisible( inb);
    }

    /**
     * Establece el comportamiento de la presente Ventana
     * Rev 21/09/2016
     *
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
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

        final DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colSoporte).setCellRenderer(tcr);

        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        edo = edoNormal;

        txtFecha.setText(fechaActual());
        cargarComboCuentas();
        MostrarDatosBancosDet("");
    }

    /**
     * Rev 25/11/2016
     *
     * @return
     */
    public static String fechaActual() {
        return new SimpleDateFormat("dd/MM/YYYY").format(Globales.getServerTimeStamp());
    }

    void MostrarDatosBancosDet(String cuenta) {
        // Para evitar multiples llamadas al metodo valueChanged
        tblMaster.clearSelection();

        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final String sql;
        if (cuenta.isEmpty()) {
            sql = "SELECT * FROM bancos_operaciones WHERE tipo_operacion='NC' AND anulado_sn= 'N' ORDER BY id DESC";
        } else {
            sql = "SELECT * FROM bancos_operaciones  WHERE cuenta='" + cuenta + "' && tipo_operacion='NC' AND anulado_sn= 'N' ORDER BY id DESC";
        }

        final Object[] datos = new Object[6];
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery(sql);
            while (rs.next()) {
                final ModelBancosOperaciones reg = new ModelBancosOperaciones(rs);
                datos[colId] = reg.getId();
                datos[colFecha] = Format.toDateSql(reg.getFecha());
                datos[colSoporte] = reg.getSoporte_o_Cheque();
                datos[colDesc] = reg.getDesc();
                datos[colMonto] = reg.getDebe();
                datos[colReg] = reg;
                model.addRow(datos);
            }
            calcular();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }

    /**
     * Rev 24-01-2017
     *
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
     *
     */
    private void cargarComboCuentas() {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM bancos ORDER BY banco, cuenta ASC");
            while (rs.next()) {
                modeloCombo.addElement(rs.getObject("cuenta") + " " + rs.getObject("banco"));
            }

            cmbCuentas.setModel(modeloCombo);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        cmbCuentas.setSelectedIndex(-1);
    }

    /**
     * Rev 24-01-2017
     *
     */
    private void calcular() {
        double total = 0.00d;
        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            try {
                total += (double) tblMaster.getValueAt(i, colMonto);
            } catch (final Exception inex) {
                JOptionPane.showMessageDialog(null, inex);
                return;
            }
        }

        try {
            jtTotal.setText(Globales.curFormat.valueToString(total));
        } catch (final Exception inex) {
            jtTotal.setText("0,00");
            JOptionPane.showMessageDialog(null, "Error al formatear: " + System.getProperty("line.separator") + inex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbCuentas = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSoporte = new javax.swing.JTextField();
        txtDescrip = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JFormattedTextField();
        txtMonto = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCurFormatter());
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnReversar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtTotal = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtBanco = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCuenta = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("NOTAS DE CREDITO BANCARIO");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "FECHA", "SOPORTE", "DESCRIPCION", "MONTO BS.", "Reg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class
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
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(125);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(100);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 83, 740, 200));

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel2.setText("CUENTAS  REGISTRADAS");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 190, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CREDITOS BANCARIOS");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 11, 260, -1));

        cmbCuentas.setBackground(java.awt.SystemColor.inactiveCaption);
        cmbCuentas.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        cmbCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCuentasActionPerformed(evt);
            }
        });
        getContentPane().add(cmbCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 316, -1));

        jButton1.setBackground(java.awt.SystemColor.inactiveCaption);
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel5.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SOPORTE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 16, 0, 0);
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("DESCRIPCIÓN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 231;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("MONTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 97;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("FECHA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 71;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        jPanel1.add(jLabel8, gridBagConstraints);

        txtSoporte.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtSoporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoporte.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSoporte.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtSoporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoporteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 89;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 16, 13, 0);
        jPanel1.add(txtSoporte, gridBagConstraints);

        txtDescrip.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescrip.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescrip.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtDescrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 320;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 13, 0);
        jPanel1.add(txtDescrip, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("DATOS DEL DEPOSITO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 510;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 16, 0, 12);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtFecha.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 113;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 13, 12);
        jPanel1.add(txtFecha, gridBagConstraints);

        txtMonto.setBackground(new java.awt.Color(175, 204, 125));
        txtMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMonto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtMonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMonto.setEnabled(false);
        txtMonto.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtMonto.setSelectionColor(new java.awt.Color(175, 204, 125));
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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 144;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 13, 0);
        jPanel1.add(txtMonto, gridBagConstraints);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 740, 96));

        jPanel2.setMaximumSize(new java.awt.Dimension(300, 60));
        jPanel2.setMinimumSize(new java.awt.Dimension(300, 60));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 60));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnGuardar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnGuardar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 0);
        jPanel2.add(btnGuardar, gridBagConstraints);

        btnReversar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnReversar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnReversar.setText("REVERSAR");
        btnReversar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReversar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReversarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 0);
        jPanel2.add(btnReversar, gridBagConstraints);

        btnConsultar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnConsultar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnConsultar.setEnabled(false);
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 0);
        jPanel2.add(btnConsultar, gridBagConstraints);

        btnSalir.setBackground(java.awt.SystemColor.inactiveCaption);
        btnSalir.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 0);
        jPanel2.add(btnSalir, gridBagConstraints);

        btnNuevo.setBackground(java.awt.SystemColor.inactiveCaption);
        btnNuevo.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 0);
        jPanel2.add(btnNuevo, gridBagConstraints);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 740, -1));

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TOTAL CREDITOS BS.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 52;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel3.add(jLabel1, gridBagConstraints);

        jtTotal.setEditable(false);
        jtTotal.setBackground(new java.awt.Color(175, 204, 125));
        jtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtTotal.setEnabled(false);
        jtTotal.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        jtTotal.setMaximumSize(new java.awt.Dimension(210, 40));
        jtTotal.setMinimumSize(new java.awt.Dimension(210, 40));
        jtTotal.setPreferredSize(new java.awt.Dimension(210, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipady = -10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel3.add(jtTotal, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("NOMBRE DEL BANCO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel3.add(jLabel12, gridBagConstraints);

        txtBanco.setEditable(false);
        txtBanco.setBackground(new java.awt.Color(175, 204, 125));
        txtBanco.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtBanco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBanco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBanco.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtBanco.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 206;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel3.add(txtBanco, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("NÚMERO DE CUENTA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 68;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel3.add(jLabel13, gridBagConstraints);

        txtCuenta.setEditable(false);
        txtCuenta.setBackground(new java.awt.Color(175, 204, 125));
        txtCuenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCuenta.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCuenta.setEnabled(false);
        txtCuenta.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 206;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel3.add(txtCuenta, gridBagConstraints);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 740, 70));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed

        if (edo == edoNuevo) {
            limpiar();
            txtMonto.setEnabled(false);
            txtFecha.setEnabled(false);
            btnNuevo.setEnabled(true);
            btnReversar.setEnabled(true);

            MostrarDatosBancosDet(txtCuenta.getText());
            tblMaster.requestFocusInWindow();
            edo = edoNormal;
            return;
        }

        actSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void cmbCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCuentasActionPerformed

        if (cmbCuentas.getSelectedIndex() < 0) {
            return;
        }

        String cuentaCompleta = (String) cmbCuentas.getSelectedItem();
        txtCuenta.setText(cuentaCompleta.substring(0, 20));
        txtBanco.setText(cuentaCompleta.substring(21));
        String cuenta = cuentaCompleta.substring(0, 20);
        MostrarDatosBancosDet(cuenta);
        txtSoporte.setText("");
        txtDescrip.setText("");
        txtMonto.setValue(BigDecimal.ZERO);

        txtSoporte.setEnabled(false);
        txtDescrip.setEnabled(false);
        txtMonto.setEnabled(false);

        tblMaster.requestFocusInWindow();
    }//GEN-LAST:event_cmbCuentasActionPerformed

    private void txtDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripActionPerformed
        txtDescrip.transferFocus();
    }//GEN-LAST:event_txtDescripActionPerformed

    private void txtSoporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoporteActionPerformed
        txtSoporte.transferFocus();
    }//GEN-LAST:event_txtSoporteActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        boolean nuevo = edo == edoNuevo;

        final String cuenta = txtCuenta.getText().trim();
        if (cuenta.isEmpty() && nuevo) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta Bancaria");
            cmbCuentas.requestFocusInWindow();
            return;
        }

        final String banco = txtBanco.getText().trim();
        if (banco.isEmpty() && nuevo) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta Bancaria");
            cmbCuentas.requestFocusInWindow();
            return;
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

        final Double monto;
        try {
            monto = (Double) Globales.curFormat.stringToValue(sMonto);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al formatear: " + System.getProperty("line.separator") + inex);
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
            return;
        }

        final Calendar fechaOp = Calendar.getInstance();
        final long ejeFis = fechaOp.get(Calendar.YEAR);
        final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;

        final String tipo_operacion = "NC";

        // Verificar si el soporte/cheque para la cuenta, en el presente ejercicio fiscal es valido
        try {
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM bancos_operaciones "
                + "WHERE ejefis= ? AND cuenta= ? AND tipo_operacion= ? AND soporte_o_cheque= ?");
            pst.setLong(1, ejeFis);
            pst.setString(2, cuenta);
            pst.setString(3, tipo_operacion);
            pst.setString(4, soporte);

            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new Exception("Soporte/Cheque inválido, posiblemente ya se halla utilizado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            return;
        }

        if (edo == edoNuevo) {
            try {
                ConnCapip.getInstance().BeginTransaction();

                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO bancos_operaciones("
                    + "cuenta, banco, descripcion, fecha, tipo_operacion," // 1, 2, 3, 4, 5,
                    + "soporte_o_cheque, conciliado, debe, haber, cuenta_numop, " // 6, 7, 8, 9 , 10
                    + "cuenta_numop_tipo, num_pag_ava, ejefis, ejefismes, iduser) " // 11, 12, 13, 14, 15
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setString(1, cuenta);
                pst.setString(2, banco);
                pst.setString(3, desc);
                pst.setString(4, sFecha);
                pst.setString(5, tipo_operacion); // tipo_operacion
                pst.setString(6, soporte);
                pst.setString(7, ""); // conciliado
                pst.setDouble(8, monto); // debe
                pst.setDouble(9, 0.00d); // haber
                pst.setDouble(10, ModelMapNextOp.getNext(Globales.getEjeFisYear(), cuenta)); // secuencial
                pst.setLong(11, ModelMapNextOp.getNext(Globales.getEjeFisYear(), cuenta + "-" + tipo_operacion)); // cuenta_numop
                pst.setInt(12, 0); // num_pag_ava
                pst.setLong(13, ejeFis);
                pst.setLong(14, ejeFisMes);
                pst.setLong(15, UserPassIn.getIdUser());

                if (pst.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos");
                    txtSoporte.requestFocusInWindow();
                    try {
                        ConnCapip.getInstance().RollBack();
                    } catch (final Exception inex1) {
                        JOptionPane.showMessageDialog(null, inex1);
                    }
                    return;
                }

                ConnCapip.getInstance().EndTransaction();

                JOptionPane.showMessageDialog(null, "Operación realizada");

                edo = edoNormal;
                txtMonto.setEnabled(false);
                txtFecha.setEnabled(false);
                btnNuevo.setEnabled(true);
                btnReversar.setEnabled(true);

                limpiar();
                tblMaster.requestFocusInWindow();

            } catch (final Exception inex) {
                try {
                    ConnCapip.getInstance().RollBack();
                } catch (final Exception inex1) {
                    JOptionPane.showMessageDialog(null, inex1);
                }
                JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + System.getProperty("line.separator") + inex);
                txtSoporte.requestFocusInWindow();
            }
        } else if (edo == edoNormal) {

            int rowSel = tblMaster.getSelectedRow();
            if (rowSel < 0) {
                JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + "Debe seleccionar un registro");
                return;
            }

            ModelBancosOperaciones reg = (ModelBancosOperaciones) tblMaster.getValueAt(rowSel, colReg);

            try {

                ConnCapip.getInstance().BeginTransaction();

                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE bancos_operaciones "
                    + "SET soporte_o_cheque= ?, descripcion= ?, debe= ? "
                    + "WHERE id= ? AND anulado_sn= 'N'");
                pst.setString(1, soporte);
                pst.setString(2, desc); // desc
                pst.setDouble(3, monto);  // debe
                pst.setLong(4, reg.getId()); // id

                if (pst.executeUpdate() != 1) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos");
                    txtSoporte.requestFocusInWindow();
                    try {
                        ConnCapip.getInstance().RollBack();
                    } catch (final Exception inex1) {
                        JOptionPane.showMessageDialog(null, inex1);
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
                }
                JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + System.getProperty("line.separator") + inex);
                txtSoporte.requestFocusInWindow();
            }

        }

        MostrarDatosBancosDet(cuenta);

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnReversarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReversarActionPerformed

        int fila = tblMaster.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return;
        }

        try {

            if (JOptionPane.showConfirmDialog(this, " ¿ Seguro desea Reversar ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                final ModelBancosOperaciones reg = (ModelBancosOperaciones) tblMaster.getValueAt(fila, colReg);
                final int id = reg.getId();
                final String cuenta = reg.getCuenta();
                final Double monto = reg.getDebe();

                ConnCapip.getInstance().BeginTransaction();

                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM bancos_operaciones "
                    + "WHERE id= '" + id + "' AND anulado_sn= 'N'");
                pst.executeUpdate();

//                BancosContabilidad.restarCuenta(cuenta, monto);
                MostrarDatosBancosDet(cuenta);

                ConnCapip.getInstance().EndTransaction();
                JOptionPane.showMessageDialog(null, "Operación realizada");
            }
        } catch (final Exception inex) {
            try {
                ConnCapip.getInstance().RollBack();
            } catch (final Exception inex1) {
                JOptionPane.showMessageDialog(null, inex1);
            }

            JOptionPane.showMessageDialog(null, inex);
        }
    }//GEN-LAST:event_btnReversarActionPerformed

    private void tblMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMasterMouseClicked
        actUpdateItem();
    }//GEN-LAST:event_tblMasterMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        if (edo != edoNormal) {
            return;
        }

        if (txtCuenta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta");
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
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        txtFecha.requestFocusInWindow();
    }//GEN-LAST:event_txtMontoActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        btnGuardar.requestFocusInWindow();
    }//GEN-LAST:event_txtFechaActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed

    }//GEN-LAST:event_btnConsultarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cmbCuentas.setSelectedIndex(-1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        if (!"0123456789,".contains(String.valueOf(evt.getKeyChar())) || txtMonto.getText().length() > 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMontoKeyTyped

    /**
     * Rev 25/11/2016
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
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new BancosNotaCredito(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnReversar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cmbCuentas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField jtTotal;
    private javax.swing.JTable tblMaster;
    private javax.swing.JTextField txtBanco;
    private javax.swing.JFormattedTextField txtCuenta;
    private javax.swing.JTextField txtDescrip;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JFormattedTextField txtMonto;
    private javax.swing.JTextField txtSoporte;
    // End of variables declaration//GEN-END:variables

}
