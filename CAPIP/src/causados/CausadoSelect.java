/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package causados;

import capipsistema.CapipPropiedades;
import capipsistema.Globales;
import capipsistema.UserTrack;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.CauModel;
import presupuesto.ConnPpto;
import presupuesto.WinState;
import static presupuesto.XJFrame.COLOBJ;
import static presupuesto.XJFrame.JBCANCEL;
import static presupuesto.XJFrame.JBSELECT;
import static presupuesto.XJFrame.JTABLE;
import presupuesto.XJFrameJTable;
import utils.DateCellRenderer;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;

/**
 *
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class CausadoSelect extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del código programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colNum = 0;
    private final int colFecha = 1;
    private final int colBenef = 2;
    private final int colRifCI = 3;
    private final int colMonto = 4;
    private final int colRegCausado = 5;
    private String bdato;

    /**
     *
     * @param _parent
     */
    public CausadoSelect(final JFrame _parent) {
        super(_parent);
        initComponents();

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     *
     * @param _b
     */
    @Override
    public void setVisible(boolean _b) {
        // Para mostrar la ventana en el tope de la pantalla
        if (_b) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }

        super.setVisible(_b);
    }

    /**
     * Establece el comportamiento de la presente Ventana
     * Rev 21/09/2016
     *
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        setTitle(CapipPropiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        // Establecer acción al cerrar ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Salir();
            }

        });

        // Para salir con la tecla ESC
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");

        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Salir();
            }
        });

    }

    /**
     * Rev 21/11/2016
     *
     */
    private void setCompBehavior() {

        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, btnRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, txtReset);
        param.put(JTABLE, tblMaster);
        param.put(COLOBJ, colRegCausado);
        param.put(JBSELECT, btnSeleccionar);
        param.put(JBCANCEL, btnCancelar);

        ConfigComponents(param);
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        InitConditions();
        UpdatePanels();
    }

    /**
     * Valores y/o condiciones iniciales de la ventana
     */
    @Override
    public void InitConditions() {
        super.InitConditions();
        bdato = "causado";
        winState = WinState.NORMAL;
    }

    /**
     * Rev 21/11/2016
     *
     * @param _param
     */
    @Override
    protected void ConfigComponents(final Map<String, Object> _param) {
        super.ConfigComponents(_param);

        JButton jbSelect = (JButton) xjparam.get(JBSELECT);
        if (jbSelect != null) {
            jbSelect.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jbSeleccionarActionPerformed(evt);
                }
            });
        }

        JButton jbCancel = (JButton) xjparam.get(JBCANCEL);

        if (jbCancel != null) {
            jbCancel.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jbCancelarActionPerformed();
                }
            });
        }

        DefaultTableCellRenderer tcr;

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colRifCI).setCellRenderer(tcr);

        // Configurar la tabla de Maestra, para alinear las columnas
        tblMaster.getColumnModel().getColumn(colFecha).setCellRenderer(new DateCellRenderer());

        // Configurar la tabla
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 21/11/2016
     *
     */
    @Override
    protected void UpdatePanels() {
        UpdateJTable();
        UpdateEnabledAndFocus();
    }

    /**
     * Actualizar la vista de la ventana. debería llamarse cuando se halla modificado la tabla
     * Rev 21/11/2016
     *
     */
    @Override
    public void UpdateJTable() {

        // Registrar la fila seleccionada
        int lastRowSel = tblMaster.getSelectedRow();

        // Para evitar multiples llamadas al metodo valueChanged
        tblMaster.clearSelection();

        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();

        // Eliminar los elementos existentes, manteniendo el mismo modelo
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[6];
        try {
            // Generar el comando SQL
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + bdato + " WHERE YEAR(ejefis)= " + Globales.getEjeFisYear() + " ORDER BY id_causado ASC");
            while (rs.next()) {
                CauModel aux = new CauModel(rs);
                datos[colNum] = aux.getId_causado();
                datos[colFecha] = aux.getFecha_causado();
                datos[colBenef] = aux.getBenef_razonsocial();
                datos[colRifCI] = aux.getBenef_rif_ci();
                datos[colMonto] = Format.toDouble(aux.getTotal_bs());
                datos[colRegCausado] = aux;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        // Reposicionar el registro seleccionado
        int lastRow = tblMaster.getRowCount() - 1;
        if (lastRow >= 0) {
            if (lastRowSel < 0) {
                lastRowSel = 0;
            } else {
                lastRowSel = Math.min(lastRowSel, lastRow);
            }

            tblMaster.scrollRectToVisible(tblMaster.getCellRect(lastRowSel, 0, true));
            tblMaster.clearSelection();
            tblMaster.setRowSelectionInterval(lastRowSel, lastRowSel);
        }

        // Actualizar el campo cantidad de beneficiarios
        txtCant.setText(String.valueOf(tblMaster.getRowCount()));
    }

    @Override
    protected void UpdatePanelDetails() {
        if (tblMaster.getRowCount() > 0) {
            final int selectedRow = tblMaster.getSelectedRow();
            if (selectedRow >= 0) {
                txtCauNum.setText(String.valueOf((long) tblMaster.getValueAt(selectedRow, colNum)));
                txtCauFecha.setText(Format.toStr((java.sql.Date) tblMaster.getValueAt(selectedRow, colFecha)));
                txtCauBenef.setText(tblMaster.getValueAt(selectedRow, colBenef).toString());
                txtCauRif.setText(tblMaster.getValueAt(selectedRow, colRifCI).toString());
                txtCauMonto.setText(Format.toStr((double) tblMaster.getValueAt(selectedRow, colMonto)));
                txtCant.setText(String.valueOf(tblMaster.getRowCount()));
            } else {
                ClearDetails();
            }
        } else {
            ClearDetails();
        }
    }

    @Override
    protected void ClearDetails() {
        txtCauNum.setText("");
        txtCauFecha.setText("");
        txtCauBenef.setText("");
        txtCauRif.setText("");
        txtCauMonto.setText("0,00");
    }
    /*
     * Segun el estado de la ventana, actualiza los componentes
     */

    public void UpdateEnabledAndFocus() {
        final boolean isTableFill = tblMaster.getRowCount() > 0;
        txtReset.setEnabled(isTableFill);
        btnSeleccionar.setEnabled(isTableFill);
        btnCancelar.setEnabled(isTableFill && (tblMaster.getSelectedRow() >= 0));
        btnRetornar.setEnabled(true);

        if (isTableFill) {
            tblMaster.requestFocusInWindow();
            getRootPane().setDefaultButton(btnSeleccionar);
        } else {
            btnRetornar.requestFocusInWindow();
            getRootPane().setDefaultButton(btnRetornar);
        }
    }

    /**
     * Rev 21/11/2016
     *
     * @param _e
     */
    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent _e) {
        final JTable jtable = (JTable) xjparam.get(JTABLE);

        if (jtable != null) {
            final int selRow = jtable.getSelectedRow();
            if (selRow >= 0) {
                cauRpt(selRow);
            }
        }
    }

    protected void jbCancelarActionPerformed() {
        Salir();
    }

    /**
     * Rev 21/11/2016
     *
     * @param _selRow
     */
    private void cauRpt(final Integer _selRow) {
        try {
            Causado.genReport(((CauModel) tblMaster.getValueAt(_selRow, colRegCausado)).getId_causado(), null);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar el registro" + System.getProperty("line.separator") + _ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupTipo = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtReset = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jpCmdState = new javax.swing.JPanel();
        btnSeleccionar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        txtCant = new javax.swing.JFormattedTextField(new Integer(0));
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCauNum = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCauBenef = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCauMonto = new javax.swing.JFormattedTextField(new BigDecimal("0.00"));
        jLabel9 = new javax.swing.JLabel();
        txtCauFecha = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCauRif = new javax.swing.JTextField();
        jpComandos = new javax.swing.JPanel();
        btnRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();

        setTitle("SELECCIONAR CAUSADO");
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        jpFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setOpaque(false);
        jpFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        jpFondo.setLayout(new javax.swing.BoxLayout(jpFondo, javax.swing.BoxLayout.PAGE_AXIS));
        jpFondo.add(filler1);

        jpFiltro.setMaximumSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setMinimumSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setOpaque(false);
        jpFiltro.setPreferredSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setLayout(new java.awt.GridBagLayout());

        jtFiltro.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtFiltro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtFiltro.setDoubleBuffered(true);
        jtFiltro.setMaximumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setMinimumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel1, gridBagConstraints);

        txtReset.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtReset.setText("RESET");
        txtReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(txtReset, gridBagConstraints);

        jcbFilter.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Número", "Fecha", "Beneficiario", "RIF/CI", "Monto Bs." }));
        jcbFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbFilter.setMaximumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setMinimumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jcbFilter, gridBagConstraints);

        jpFondo.add(jpFiltro);

        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 390));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblMaster.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        tblMaster.setForeground(new java.awt.Color(0, 51, 153));
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Número", "Fecha", "Beneficiario", "RIF/CI", "Monto Bs.", "RegCausado"
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
        tblMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblMaster.setDoubleBuffered(true);
        tblMaster.setFillsViewportHeight(true);
        tblMaster.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(150);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jpFondo.add(jScrollPane1);

        jpEditables.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle"));
        jpEditables.setMaximumSize(new java.awt.Dimension(1060, 135));
        jpEditables.setMinimumSize(new java.awt.Dimension(1060, 135));
        jpEditables.setOpaque(false);
        jpEditables.setPreferredSize(new java.awt.Dimension(1060, 135));
        jpEditables.setLayout(new java.awt.GridBagLayout());

        jpCmdState.setOpaque(false);
        jpCmdState.setLayout(new java.awt.GridBagLayout());

        btnSeleccionar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnSeleccionar.setText("SELECCIONAR");
        btnSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(btnSeleccionar, gridBagConstraints);

        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblmonto1.setText("Cantidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 392, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);

        txtCant.setEditable(false);
        txtCant.setBackground(new java.awt.Color(204, 102, 0));
        txtCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCant.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCant.setDoubleBuffered(true);
        txtCant.setFocusable(false);
        txtCant.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCant.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCant.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        jpCmdState.add(txtCant, gridBagConstraints);

        btnCancelar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jpCmdState, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel6.setText("Número");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jLabel6, gridBagConstraints);

        txtCauNum.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtCauNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCauNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCauNum.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCauNum.setEnabled(false);
        txtCauNum.setMaximumSize(new java.awt.Dimension(150, 21));
        txtCauNum.setMinimumSize(new java.awt.Dimension(150, 21));
        txtCauNum.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtCauNum, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel7.setText("Beneficiario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel7, gridBagConstraints);

        txtCauBenef.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtCauBenef.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCauBenef.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCauBenef.setEnabled(false);
        txtCauBenef.setMaximumSize(new java.awt.Dimension(300, 21));
        txtCauBenef.setMinimumSize(new java.awt.Dimension(300, 21));
        txtCauBenef.setPreferredSize(new java.awt.Dimension(300, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtCauBenef, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel8.setText("Monto Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel8, gridBagConstraints);

        txtCauMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCauMonto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtCauMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCauMonto.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCauMonto.setDoubleBuffered(true);
        txtCauMonto.setEnabled(false);
        txtCauMonto.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtCauMonto.setMaximumSize(new java.awt.Dimension(150, 21));
        txtCauMonto.setMinimumSize(new java.awt.Dimension(150, 21));
        txtCauMonto.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtCauMonto, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel9.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel9, gridBagConstraints);

        txtCauFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCauFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        txtCauFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCauFecha.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCauFecha.setEnabled(false);
        txtCauFecha.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtCauFecha.setMaximumSize(new java.awt.Dimension(100, 21));
        txtCauFecha.setMinimumSize(new java.awt.Dimension(100, 21));
        txtCauFecha.setPreferredSize(new java.awt.Dimension(100, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtCauFecha, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel2.setText("RIF/CI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel2, gridBagConstraints);

        txtCauRif.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtCauRif.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCauRif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCauRif.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCauRif.setEnabled(false);
        txtCauRif.setMaximumSize(new java.awt.Dimension(100, 21));
        txtCauRif.setMinimumSize(new java.awt.Dimension(100, 21));
        txtCauRif.setPreferredSize(new java.awt.Dimension(100, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtCauRif, gridBagConstraints);

        jpFondo.add(jpEditables);

        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());

        btnRetornar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnRetornar.setText("RETORNAR");
        btnRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 560, 0, 0);
        jpComandos.add(btnRetornar, gridBagConstraints);

        jpFondo.add(jpComandos);

        getContentPane().add(jpFondo);
        jpFondo.setBounds(0, 0, 1100, 650);

        jlFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        jlFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        getContentPane().add(jlFondo);
        jlFondo.setBounds(0, -10, 1150, 660);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                new CausadoSelect(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRetornar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbFilter;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JPanel jpCmdState;
    private javax.swing.JPanel jpComandos;
    private javax.swing.JPanel jpEditables;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JPanel jpFondo;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JLabel lblmonto1;
    private javax.swing.JTable tblMaster;
    private javax.swing.JFormattedTextField txtCant;
    private javax.swing.JTextField txtCauBenef;
    private javax.swing.JFormattedTextField txtCauFecha;
    private javax.swing.JFormattedTextField txtCauMonto;
    private javax.swing.JTextField txtCauNum;
    private javax.swing.JTextField txtCauRif;
    private javax.swing.JButton txtReset;
    // End of variables declaration//GEN-END:variables
}
