/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.compromisos;

import com.principal.capipsistema.Globales;
import com.principal.capipsistema.Propiedades;
import com.principal.capipsistema.UserTrack;
import com.principal.connection.ConnCapip;
import com.principal.modelos.ComprModel;
import com.principal.presupuesto.ConnPpto;
import com.principal.presupuesto.WinState;
import static com.principal.presupuesto.XJFrame.COLOBJ;
import static com.principal.presupuesto.XJFrame.JBCANCEL;
import static com.principal.presupuesto.XJFrame.JBSELECT;
import static com.principal.presupuesto.XJFrame.JTABLE;
import com.principal.presupuesto.XJFrameJTable;
import com.principal.utils.DateCellRenderer;
import com.principal.utils.DecimalCellRenderer;
import com.principal.utils.Format;
import com.principal.utils.IntegerCellRenderer;
import com.principal.utils.Num2Let;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.sql.Connection;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class CompromisoSelect extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del código programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colNum = 0;

    private final int colFecha = 1;

    private final int colBenef = 2;

    private final int colRifCI = 3;

    private final int colMonto = 4;

    private final int colReg = 5;

    private String bdato;

    //    private final java.awt.Window parent;
    /**
     * @param inparent
     */
    public CompromisoSelect(final JFrame inparent) {
        super(inparent);
        initComponents();
        //        parent = inparent;
        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
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
        if (inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }
        super.setVisible(inb);
    }

    /**
     * Establece el comportamiento de la presente Ventana
     * Rev 10/10/2016
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
        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, btnRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, txtReset);
        param.put(JTABLE, tblMaster);
        param.put(COLOBJ, colNum);
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
        bdato = "compr_compra";
        tbtnCompra.setSelected(true);
        winState = WinState.NORMAL;
    }

    @Override
    protected void ConfigComponents(final Map<String, Object> inparam) {
        super.ConfigComponents(inparam);
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
        // Configurar el formato de la fecha
        txtComprFecha.setFormatterFactory(Globales.getDateFormatterFactory());
        // Configurar el campo de Monto del Credito, para que acepte y maneje BigDecimal
        txtComprMonto.setFormatterFactory(Globales.getCurFormatterFactory());
        DefaultTableCellRenderer tcr;
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colNum).setCellRenderer(tcr);
        tcr = new DateCellRenderer();
        tblMaster.getColumnModel().getColumn(colFecha).setCellRenderer(tcr);
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colRifCI).setCellRenderer(tcr);
        // Configurar la tabla de partidas
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    @Override
    protected void UpdatePanels() {
        UpdateJTable();
        UpdateEnabledAndFocus();
    }

    /**
     * Actualizar la vista de la ventana. Deberia llamarse cuando se halla modificado la tabla
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
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + bdato + " WHERE ejefis=" + Globales.getEjeFisYear() + " ORDER BY id_compr ASC");
            while (rs.next()) {
                ComprModel aux = new ComprModel(rs);
                datos[colNum] = aux.getId_compromiso();
                datos[colFecha] = aux.getFecha_compr();
                datos[colBenef] = aux.getBenef_razonsocial();
                datos[colRifCI] = aux.getBenef_rif_ci();
                datos[colMonto] = Format.toDouble(aux.getTotal_bs());
                datos[colReg] = aux;
                model.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
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
            int selectedRow = tblMaster.getSelectedRow();
            if (selectedRow >= 0) {
                txtComprNum.setText(tblMaster.getValueAt(selectedRow, colNum).toString());
                txtComprFecha.setText(tblMaster.getValueAt(selectedRow, colFecha).toString());
                txtComprBenef.setText(tblMaster.getValueAt(selectedRow, colBenef).toString());
                txtComprRif.setText(tblMaster.getValueAt(selectedRow, colRifCI).toString());
                txtComprMonto.setText(tblMaster.getValueAt(selectedRow, colMonto).toString());
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
        txtComprNum.setText("");
        txtComprFecha.setText("");
        txtComprBenef.setText("");
        txtComprRif.setText("");
        txtComprMonto.setText("0,00");
    }

    /*
     * Segun el estado de la ventana, actualiza los componentes
     */
    public void UpdateEnabledAndFocus() {
        boolean isTableFill = tblMaster.getRowCount() > 0;
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
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        buttonGroupTipo = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtReset = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        tbtnOtros = new javax.swing.JToggleButton();
        tbtnCompra = new javax.swing.JToggleButton();
        tbtnServicio = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jpCmdState = new javax.swing.JPanel();
        btnSeleccionar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        txtCant = new javax.swing.JFormattedTextField(Integer.valueOf(0));
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtComprNum = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtComprFecha = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtComprBenef = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtComprRif = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtComprMonto = new javax.swing.JFormattedTextField(new BigDecimal("0.00"));
        jpComandos = new javax.swing.JPanel();
        btnRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();
        setTitle("SELECCIONAR COMPROMISO");
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
        // NOI18N
        jtFiltro.setFont(new java.awt.Font("Arial", 3, 14));
        jtFiltro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtFiltro.setDoubleBuffered(true);
        jtFiltro.setMaximumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setMinimumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setPreferredSize(new java.awt.Dimension(200, 21));
        jtFiltro.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel1, gridBagConstraints);
        txtReset.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        txtReset.setFont(new java.awt.Font("Arial", 2, 16));
        txtReset.setText("RESET");
        txtReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(txtReset, gridBagConstraints);
        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16));
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
        tbtnOtros.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroupTipo.add(tbtnOtros);
        // NOI18N
        tbtnOtros.setFont(new java.awt.Font("Arial", 2, 16));
        tbtnOtros.setText("Otros");
        tbtnOtros.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnOtrosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(tbtnOtros, gridBagConstraints);
        tbtnCompra.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroupTipo.add(tbtnCompra);
        // NOI18N
        tbtnCompra.setFont(new java.awt.Font("Arial", 2, 16));
        tbtnCompra.setSelected(true);
        tbtnCompra.setText("Compras");
        tbtnCompra.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnCompraActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(tbtnCompra, gridBagConstraints);
        tbtnServicio.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroupTipo.add(tbtnServicio);
        // NOI18N
        tbtnServicio.setFont(new java.awt.Font("Arial", 2, 16));
        tbtnServicio.setText("Servicios");
        tbtnServicio.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnServicioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(tbtnServicio, gridBagConstraints);
        // NOI18N
        jLabel3.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel3.setText("TIPO DE ORDEN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(jLabel3, gridBagConstraints);
        jpFondo.add(jpFiltro);
        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 390));
        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        // NOI18N
        tblMaster.setFont(new java.awt.Font("Arial", 3, 16));
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "Número", "Fecha", "Beneficiario", "RIF/CI", "Monto Bs.", "RegCompr" }) {

            Class[] types = new Class[] { java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class };

            boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblMaster.setDoubleBuffered(true);
        tblMaster.setFillsViewportHeight(true);
        tblMaster.setSelectionBackground(new java.awt.Color(175, 204, 125));
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
        btnSeleccionar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnSeleccionar.setFont(new java.awt.Font("Arial", 2, 14));
        btnSeleccionar.setText("SELECCIONAR");
        btnSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(btnSeleccionar, gridBagConstraints);
        // NOI18N
        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblmonto1.setText("Cantidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 392, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);
        txtCant.setEditable(false);
        txtCant.setBackground(new java.awt.Color(175, 204, 125));
        txtCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCant.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCant.setDoubleBuffered(true);
        txtCant.setFocusable(false);
        // NOI18N
        txtCant.setFont(new java.awt.Font("Arial", 3, 24));
        txtCant.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCant.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        jpCmdState.add(txtCant, gridBagConstraints);
        btnCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnCancelar.setFont(new java.awt.Font("Arial", 2, 14));
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
        // NOI18N
        jLabel6.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel6.setText("Número");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jLabel6, gridBagConstraints);
        // NOI18N
        txtComprNum.setFont(new java.awt.Font("Arial", 3, 14));
        txtComprNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComprNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtComprNum.setEnabled(false);
        txtComprNum.setMaximumSize(new java.awt.Dimension(150, 21));
        txtComprNum.setMinimumSize(new java.awt.Dimension(150, 21));
        txtComprNum.setPreferredSize(new java.awt.Dimension(150, 21));
        txtComprNum.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtComprNum.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComprNumActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtComprNum, gridBagConstraints);
        // NOI18N
        jLabel9.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel9.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel9, gridBagConstraints);
        txtComprFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtComprFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        txtComprFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComprFecha.setEnabled(false);
        // NOI18N
        txtComprFecha.setFont(new java.awt.Font("Arial", 3, 14));
        txtComprFecha.setMaximumSize(new java.awt.Dimension(100, 21));
        txtComprFecha.setMinimumSize(new java.awt.Dimension(100, 21));
        txtComprFecha.setPreferredSize(new java.awt.Dimension(100, 21));
        txtComprFecha.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtComprFecha, gridBagConstraints);
        // NOI18N
        jLabel7.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel7.setText("Beneficiario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel7, gridBagConstraints);
        // NOI18N
        txtComprBenef.setFont(new java.awt.Font("Arial", 3, 14));
        txtComprBenef.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtComprBenef.setEnabled(false);
        txtComprBenef.setMaximumSize(new java.awt.Dimension(300, 21));
        txtComprBenef.setMinimumSize(new java.awt.Dimension(300, 21));
        txtComprBenef.setPreferredSize(new java.awt.Dimension(300, 21));
        txtComprBenef.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtComprBenef, gridBagConstraints);
        // NOI18N
        jLabel2.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel2.setText("RIF/CI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel2, gridBagConstraints);
        // NOI18N
        txtComprRif.setFont(new java.awt.Font("Arial", 3, 14));
        txtComprRif.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComprRif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtComprRif.setMaximumSize(new java.awt.Dimension(100, 21));
        txtComprRif.setMinimumSize(new java.awt.Dimension(100, 21));
        txtComprRif.setPreferredSize(new java.awt.Dimension(100, 21));
        txtComprRif.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtComprRif, gridBagConstraints);
        // NOI18N
        jLabel8.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel8.setText("Monto Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel8, gridBagConstraints);
        txtComprMonto.setBackground(new java.awt.Color(175, 204, 125));
        txtComprMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtComprMonto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtComprMonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtComprMonto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtComprMonto.setDoubleBuffered(true);
        txtComprMonto.setEnabled(false);
        // NOI18N
        txtComprMonto.setFont(new java.awt.Font("Arial", 3, 20));
        txtComprMonto.setMaximumSize(new java.awt.Dimension(150, 21));
        txtComprMonto.setMinimumSize(new java.awt.Dimension(150, 21));
        txtComprMonto.setPreferredSize(new java.awt.Dimension(150, 21));
        txtComprMonto.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtComprMonto.setSelectionColor(new java.awt.Color(0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(txtComprMonto, gridBagConstraints);
        jpFondo.add(jpEditables);
        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());
        btnRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnRetornar.setFont(new java.awt.Font("Arial", 2, 14));
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
        // NOI18N
        jlFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png")));
        jlFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        getContentPane().add(jlFondo);
        jlFondo.setBounds(0, -10, 1150, 660);
        pack();
    }

    // </editor-fold>//GEN-END:initComponents
    private void tbtnCompraActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_tbtnCompraActionPerformed
        bdato = "compr_compra";
        UpdatePanels();
    }

//GEN-LAST:event_tbtnCompraActionPerformed
    private void tbtnServicioActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_tbtnServicioActionPerformed
        bdato = "compr_servicio";
        UpdatePanels();
    }

//GEN-LAST:event_tbtnServicioActionPerformed
    private void tbtnOtrosActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_tbtnOtrosActionPerformed
        bdato = "compr_otros";
        UpdatePanels();
    }

//GEN-LAST:event_tbtnOtrosActionPerformed
    private void txtComprNumActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_txtComprNumActionPerformed
        // TODO add your handling code here:
    }

//GEN-LAST:event_txtComprNumActionPerformed
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
                new CompromisoSelect(null).setVisible(true);
            }
        });
    }

    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent ine) {
        JTable jtable = (JTable) xjparam.get(JTABLE);
        Integer colReg = (Integer) xjparam.get(COLOBJ);
        if (jtable != null) {
            int selRow = jtable.getSelectedRow();
            if (selRow >= 0) {
                int col = colReg == null ? -1 : colReg;
                if (col >= 0) {
                    comprRpt(selRow, col);
                }
            }
        }
    }

    protected void jbCancelarActionPerformed() {
        Salir();
    }

    private void comprRpt(Integer inselRow, Integer incol) {
        final ComprModel reg = (ComprModel) tblMaster.getValueAt(inselRow, incol);
        final String numcompr = String.valueOf(reg.getId_compromiso());
        final String sTotal = Format.toStr(reg.getTotal_bs());
        String EnLetras;
        try {
            EnLetras = Num2Let.convert(reg.getTotal_bs());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        if (tbtnCompra.isSelected()) {
            ver_CompraRpt(numcompr, EnLetras, sTotal);
        } else if (tbtnServicio.isSelected()) {
            ver_ServicioRpt(numcompr, EnLetras, sTotal);
        } else if (tbtnOtros.isSelected()) {
            ver_OtrosRpt(numcompr, EnLetras, sTotal);
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo de Compromiso");
        }
    }

    /**
     * Rev 06/11/2016
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

    public void ver_CompraRpt(String numcompr, String EnLetras, String SumaTotal) {
    }

    public void ver_ServicioRpt(String numcompr, String EnLetras, String SumaTotal) {
    }

    public void ver_OtrosRpt(String numcompr, String EnLetras, String SumaTotal) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;

    private javax.swing.JButton btnRetornar;

    private javax.swing.JButton btnSeleccionar;

    private javax.swing.ButtonGroup buttonGroupTipo;

    private javax.swing.Box.Filler filler1;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel2;

    private javax.swing.JLabel jLabel3;

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

    private javax.swing.JToggleButton tbtnCompra;

    private javax.swing.JToggleButton tbtnOtros;

    private javax.swing.JToggleButton tbtnServicio;

    private javax.swing.JFormattedTextField txtCant;

    private javax.swing.JTextField txtComprBenef;

    private javax.swing.JFormattedTextField txtComprFecha;

    private javax.swing.JFormattedTextField txtComprMonto;

    private javax.swing.JTextField txtComprNum;

    private javax.swing.JTextField txtComprRif;

    private javax.swing.JButton txtReset;

    // End of variables declaration//GEN-END:variables
    private final Connection cn = ConnCapip.getInstance().getConnection();

    private static final Logger logger = LogManager.getLogger(CompromisoSelect.class);
}
