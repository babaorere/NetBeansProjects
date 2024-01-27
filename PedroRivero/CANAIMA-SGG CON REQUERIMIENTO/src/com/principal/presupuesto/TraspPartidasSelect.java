/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.presupuesto;

import com.principal.capipsistema.FrmPrincipal;
import com.principal.capipsistema.Globales;
import com.principal.capipsistema.Propiedades;
import com.principal.capipsistema.UserPassIn;
import com.principal.capipsistema.UserTrack;
import com.principal.connection.ConnCapip;
import com.principal.modelos.UserModel;
import com.principal.utils.DateCellRenderer;
import com.principal.utils.DecimalCellRenderer;
import com.principal.utils.Format;
import com.principal.utils.IntegerCellRenderer;
import com.principal.utils.TableToExcel;
import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author CAPIP Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class TraspPartidasSelect extends XJFrameSelect {

    /**
     * Utilizada para hacer mas claro la lectura del codigo programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colGenerada = 0;

    private final int colReferencia = 1;

    private final int colFecha = 2;

    private final int colMonto = 3;

    private final int colConcepto = 4;

    private final int colReg = 5;

    /**
     * @param inparent
     * @param inidSelIO
     */
    public TraspPartidasSelect(java.awt.Window inparent, int inidSelIO) {
        super(inparent, inidSelIO);
        initComponents();
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
                Salir();
            }
        });
        // Para salir con la tecla ESC
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Salir();
            }
        });
    }

    private void setCompBehavior() {
        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, jbRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, jbResetear);
        param.put(JTABLE, tblMaster);
        param.put(COLOBJ, colReferencia);
        param.put(JBSELECT, jbSeleccionar);
        param.put(JBCANCEL, jbCancelar);
        ConfigComponents(param);
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        InitConditions();
        UpdatePanels();
        UpdateEnabledAndFocus();
    }

    /**
     * Valores y/o condiciones iniciales de la ventana
     */
    @Override
    public void InitConditions() {
        super.InitConditions();
        winState = WinState.NORMAL;
    }

    /**
     * Configuracion de los componentes de la ventana. Generalmente establece los modelos o funcionamiento de los componentes.
     * Rev 25/11/2016
     *
     * @param inparam
     */
    @Override
    public void ConfigComponents(final Map<String, Object> inparam) {
        super.ConfigComponents(inparam);
        // Configurar la tabla de Maestra, para alinear las columnas
        tblMaster.getColumnModel().getColumn(colGenerada).setCellRenderer(new DateCellRenderer());
        tblMaster.getColumnModel().getColumn(colFecha).setCellRenderer(new DateCellRenderer());
        // Configurar la tabla
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                txtCant.setText(String.valueOf(tblMaster.getRowCount()));
            }
        });
    }

    @Override
    protected void UpdatePanels() {
        UpdateJTable();
    }

    /**
     * Actualizar la vista de la ventana, cuando se halla modificado la tabla en fisico.
     * Rev 06/12/2017
     */
    @Override
    protected void UpdateJTable() {
        // Registrar la fila seleccionada
        int lastRowSel = tblMaster.getSelectedRow();
        // Para evitar multiples llamadas al metodo valueChanged
        tblMaster.clearSelection();
        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        final Object[] datos = new Object[6];
        try {
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + PptoGlobales.TBL_TRASP_PARTIDAS + " WHERE ejefis= " + Globales.getEjeFisYear() + " ORDER BY id_trasp DESC");
            while (rs.next()) {
                TblTrasp regTrasp = new TblTrasp(rs);
                datos[colGenerada] = new java.sql.Date(regTrasp.getDate_session().getTime());
                datos[colReferencia] = regTrasp.getReferencia();
                datos[colFecha] = regTrasp.getFecha();
                datos[colMonto] = regTrasp.getMonto().doubleValue();
                datos[colConcepto] = regTrasp.getConcepto();
                datos[colReg] = regTrasp;
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
            tblMaster.setRowSelectionInterval(lastRowSel, lastRowSel);
        }
        // Actualizar el campo cantidad
        txtCant.setText(String.valueOf(tblMaster.getRowCount()));
    }

    /**
     * Rev 06-02-2017
     */
    @Override
    protected void UpdatePanelDetails() {
        if (tblMaster.getRowCount() > 0) {
            int selectedRow = tblMaster.getSelectedRow();
            if (selectedRow >= 0) {
                jtGenerar.setText(Format.toStr((java.sql.Date) tblMaster.getValueAt(selectedRow, colGenerada)));
                txtReferencia.setText((String) tblMaster.getValueAt(selectedRow, colReferencia));
                txtConcepto.setText((String) tblMaster.getValueAt(selectedRow, colConcepto));
                txtFecha.setText(Format.toStr((java.sql.Date) tblMaster.getValueAt(selectedRow, colFecha)));
                txtMonto.setText(Format.toStr((double) tblMaster.getValueAt(selectedRow, colMonto)));
            } else {
                ClearDetails();
            }
        } else {
            ClearDetails();
        }
    }

    @Override
    protected void ClearDetails() {
        jtGenerar.setText(PptoGlobales.strFchZERO);
        txtReferencia.setText("");
        txtConcepto.setText("");
        txtFecha.setText(PptoGlobales.strFchZERO);
        txtMonto.setText("0,00");
    }

    private void UpdateEnabledAndFocus() {
        boolean isTableFill = tblMaster.getRowCount() > 0;
        jbResetear.setEnabled(isTableFill);
        jbSeleccionar.setEnabled(isTableFill);
        jbCancelar.setEnabled(isTableFill && (tblMaster.getSelectedRow() >= 0));
        jbRetornar.setEnabled(true);
        if (isTableFill) {
            tblMaster.requestFocusInWindow();
            getRootPane().setDefaultButton(jbSeleccionar);
        } else {
            jbRetornar.requestFocusInWindow();
            getRootPane().setDefaultButton(jbRetornar);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        buttonGroup1 = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jbResetear = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        btnExcel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jlGenerar = new javax.swing.JLabel();
        jtGenerar = new javax.swing.JTextField();
        jlRef = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JTextField();
        jlFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jlMonto = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jpCmdState = new javax.swing.JPanel();
        jbSeleccionar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        txtCant = new javax.swing.JFormattedTextField(Integer.valueOf(0));
        jbCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtConcepto = new javax.swing.JTextField();
        jpComandos = new javax.swing.JPanel();
        jbRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();
        setTitle("SELECCIONAR TRASPASO");
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
        jtFiltro.setFont(new java.awt.Font("Arial", 3, 16));
        jtFiltro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtFiltro.setDoubleBuffered(true);
        jtFiltro.setMaximumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setMinimumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setPreferredSize(new java.awt.Dimension(200, 21));
        jtFiltro.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpFiltro.add(jtFiltro, gridBagConstraints);
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(jLabel1, gridBagConstraints);
        jbResetear.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jbResetear.setFont(new java.awt.Font("Arial", 2, 16));
        jbResetear.setText("RESET");
        jbResetear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jbResetear, gridBagConstraints);
        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16));
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Generada", "Referencia", "Fecha" }));
        jcbFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbFilter.setMaximumSize(new java.awt.Dimension(102, 21));
        jcbFilter.setMinimumSize(new java.awt.Dimension(102, 21));
        jcbFilter.setPreferredSize(new java.awt.Dimension(102, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jcbFilter, gridBagConstraints);
        btnExcel.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnExcel.setFont(new java.awt.Font("Arial", 2, 16));
        btnExcel.setText("EXCEL");
        btnExcel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExcel.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(btnExcel, gridBagConstraints);
        jpFondo.add(jpFiltro);
        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 320));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 320));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 320));
        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        // NOI18N
        tblMaster.setFont(new java.awt.Font("Arial", 3, 15));
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "Generada", "Referencia", "Fecha", "Monto Bs.", "Concepto", "Reg" }) {

            Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class };

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
            tblMaster.getColumnModel().getColumn(0).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(300);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(300);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(0);
        }
        jpFondo.add(jScrollPane1);
        jpEditables.setBorder(javax.swing.BorderFactory.createTitledBorder(" Detalle "));
        jpEditables.setMaximumSize(new java.awt.Dimension(1060, 200));
        jpEditables.setMinimumSize(new java.awt.Dimension(1060, 200));
        jpEditables.setOpaque(false);
        jpEditables.setPreferredSize(new java.awt.Dimension(1060, 200));
        jpEditables.setLayout(new java.awt.GridBagLayout());
        // NOI18N
        jlGenerar.setFont(new java.awt.Font("Arial", 3, 14));
        jlGenerar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlGenerar.setText("Fecha Generación ");
        jlGenerar.setMaximumSize(new java.awt.Dimension(150, 21));
        jlGenerar.setMinimumSize(new java.awt.Dimension(150, 21));
        jlGenerar.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpEditables.add(jlGenerar, gridBagConstraints);
        // NOI18N
        jtGenerar.setFont(new java.awt.Font("Arial", 3, 16));
        jtGenerar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtGenerar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtGenerar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtGenerar.setEnabled(false);
        jtGenerar.setMaximumSize(new java.awt.Dimension(150, 21));
        jtGenerar.setMinimumSize(new java.awt.Dimension(150, 21));
        jtGenerar.setPreferredSize(new java.awt.Dimension(150, 21));
        jtGenerar.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        jpEditables.add(jtGenerar, gridBagConstraints);
        // NOI18N
        jlRef.setFont(new java.awt.Font("Arial", 3, 14));
        jlRef.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlRef.setText("Referencia ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jpEditables.add(jlRef, gridBagConstraints);
        // NOI18N
        txtReferencia.setFont(new java.awt.Font("Arial", 3, 16));
        txtReferencia.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtReferencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtReferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtReferencia.setEnabled(false);
        txtReferencia.setMaximumSize(new java.awt.Dimension(200, 21));
        txtReferencia.setMinimumSize(new java.awt.Dimension(200, 21));
        txtReferencia.setPreferredSize(new java.awt.Dimension(200, 21));
        txtReferencia.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        jpEditables.add(txtReferencia, gridBagConstraints);
        // NOI18N
        jlFecha.setFont(new java.awt.Font("Arial", 3, 14));
        jlFecha.setText("Fecha ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jpEditables.add(jlFecha, gridBagConstraints);
        // NOI18N
        txtFecha.setFont(new java.awt.Font("Arial", 3, 16));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.setMaximumSize(new java.awt.Dimension(100, 21));
        txtFecha.setMinimumSize(new java.awt.Dimension(100, 21));
        txtFecha.setPreferredSize(new java.awt.Dimension(100, 21));
        txtFecha.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        jpEditables.add(txtFecha, gridBagConstraints);
        // NOI18N
        jlMonto.setFont(new java.awt.Font("Arial", 3, 14));
        jlMonto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlMonto.setText("Monto Bs. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jpEditables.add(jlMonto, gridBagConstraints);
        txtMonto.setBackground(new java.awt.Color(175, 204, 125));
        // NOI18N
        txtMonto.setFont(new java.awt.Font("Arial", 3, 18));
        txtMonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMonto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMonto.setEnabled(false);
        txtMonto.setMaximumSize(new java.awt.Dimension(200, 21));
        txtMonto.setMinimumSize(new java.awt.Dimension(200, 21));
        txtMonto.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        jpEditables.add(txtMonto, gridBagConstraints);
        jpCmdState.setOpaque(false);
        jpCmdState.setLayout(new java.awt.GridBagLayout());
        jbSeleccionar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jbSeleccionar.setFont(new java.awt.Font("Arial", 2, 16));
        jbSeleccionar.setText("SELECCIONAR");
        jbSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbSeleccionar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSeleccionarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(jbSeleccionar, gridBagConstraints);
        // NOI18N
        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblmonto1.setText("Cantidad de Traspasos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 205, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);
        txtCant.setEditable(false);
        txtCant.setBackground(new java.awt.Color(175, 204, 125));
        txtCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCant.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCant.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCant.setDoubleBuffered(true);
        txtCant.setFocusable(false);
        // NOI18N
        txtCant.setFont(new java.awt.Font("Arial", 3, 18));
        txtCant.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCant.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jpCmdState.add(txtCant, gridBagConstraints);
        jbCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jbCancelar.setFont(new java.awt.Font("Arial", 2, 16));
        jbCancelar.setText("CANCELAR");
        jbCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(jbCancelar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jpCmdState, gridBagConstraints);
        // NOI18N
        jLabel2.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Concepto ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jpEditables.add(jLabel2, gridBagConstraints);
        // NOI18N
        txtConcepto.setFont(new java.awt.Font("Arial", 3, 16));
        txtConcepto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtConcepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtConcepto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtConcepto.setEnabled(false);
        txtConcepto.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        jpEditables.add(txtConcepto, gridBagConstraints);
        jpFondo.add(jpEditables);
        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());
        jbRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jbRetornar.setFont(new java.awt.Font("Arial", 2, 16));
        jbRetornar.setText("RETORNAR");
        jbRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 560, 0, 0);
        jpComandos.add(jbRetornar, gridBagConstraints);
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
    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnExcelActionPerformed
        try {
            final String fileName = "RptTraspPartidas " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblMaster, fileName + ".xls");
            TableToExcel.exportTSV(tblMaster, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
    }

//GEN-LAST:event_btnExcelActionPerformed
    private void jbSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_jbSeleccionarActionPerformed
        // Codigo programado
    }

//GEN-LAST:event_jbSeleccionarActionPerformed
    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent ine) {
        final JTable jtable = (JTable) xjparam.get(JTABLE);
        if (jtable != null) {
            final int selRow = jtable.getSelectedRow();
            if (selRow >= 0) {
                ReportSingle((TblTrasp) tblMaster.getValueAt(selRow, colReg));
            }
        }
    }

    /**
     * Rev 25/11/2016
     *
     * @param inreg
     */
    public static void ReportSingle(final TblTrasp inreg) {
        if (inreg == null) {
            return;
        }
        final Calendar fechaOp = Calendar.getInstance();
        fechaOp.setTimeInMillis(Globales.getServerTimeStamp().getTime());
        final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());
        fechaOp.add(Calendar.DAY_OF_MONTH, -7);
        final java.sql.Date fchDelete = new java.sql.Date(fechaOp.getTimeInMillis());
        try {
            // Borrar los reportes viejos, a los reportes del presente usuario / sesion
            ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM trasp_part_rpt_summary WHERE date_session <= '" + fchDelete + "' OR id_user= " + UserPassIn.getIdUser() + " AND id_session= " + UserPassIn.getIdSession()).executeUpdate();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        final Map<String, Object> param = new HashMap<>(101);
        param.put("fecha", Format.toStr(inreg.getFecha()));
        param.put("referencia", inreg.getReferencia());
        param.put("monto", Format.toStr(inreg.getMonto()));
        param.put("concepto", inreg.getConcepto());
        param.put("id_trasp", inreg.getId_trasp());
        param.put("ejefis", Globales.getEjeFisYear());
        param.put("fechahoy", Globales.dateFormat.format(Globales.getServerTimeStamp()));
        param.put("iduser", UserPassIn.getIdUser());
        param.put("idsession", UserPassIn.getIdSession());
        param.put("user", UserPassIn.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(UserPassIn.getIdUser()));
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("aux_1", Propiedades.CAPIP_AUX_1);
        param.put("aux_2", Propiedades.CAPIP_AUX_2);
        param.put("linea_1", Propiedades.CAPIP_LINEA_1);
        param.put("linea_2", Propiedades.CAPIP_LINEA_2);
        param.put("linea_3", Propiedades.CAPIP_LINEA_3);
        param.put("linea_4", Propiedades.CAPIP_LINEA_4);
        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));
        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/trasp_part_rpt.jasper");
        if (pathRpt != null) {
            new Thread() {

                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport(pathRpt, new HashMap<>(param), ConnCapip.getInstance().getConnection()), false);
                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                        logger.error(inex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de Traspaso de Partidas no encontrado");
        }
    }

    @Override
    protected void UpdateTotal() {
        // Actualizar el campo cantidad
        txtCant.setText(String.valueOf(tblMaster.getRowCount()));
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
                new TraspPartidasSelect(null, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcel;

    private javax.swing.ButtonGroup buttonGroup1;

    private javax.swing.Box.Filler filler1;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel2;

    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JButton jbCancelar;

    private javax.swing.JButton jbResetear;

    private javax.swing.JButton jbRetornar;

    private javax.swing.JButton jbSeleccionar;

    private javax.swing.JComboBox jcbFilter;

    private javax.swing.JLabel jlFecha;

    private javax.swing.JLabel jlFondo;

    private javax.swing.JLabel jlGenerar;

    private javax.swing.JLabel jlMonto;

    private javax.swing.JLabel jlRef;

    private javax.swing.JPanel jpCmdState;

    private javax.swing.JPanel jpComandos;

    private javax.swing.JPanel jpEditables;

    private javax.swing.JPanel jpFiltro;

    private javax.swing.JPanel jpFondo;

    private javax.swing.JTextField jtFiltro;

    private javax.swing.JTextField jtGenerar;

    private javax.swing.JLabel lblmonto1;

    private javax.swing.JTable tblMaster;

    private javax.swing.JFormattedTextField txtCant;

    private javax.swing.JTextField txtConcepto;

    private javax.swing.JTextField txtFecha;

    private javax.swing.JTextField txtMonto;

    private javax.swing.JTextField txtReferencia;

    // End of variables declaration//GEN-END:variables
    private static final Logger logger = LogManager.getLogger(TraspPartidasSelect.class);
}
