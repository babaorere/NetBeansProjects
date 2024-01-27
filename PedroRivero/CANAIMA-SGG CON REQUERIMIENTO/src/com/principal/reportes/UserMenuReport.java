/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.reportes;

import com.principal.capipsistema.FrmPrincipal;
import com.principal.capipsistema.Globales;
import com.principal.capipsistema.Propiedades;
import com.principal.capipsistema.UserPassIn;
import com.principal.capipsistema.UserTrack;
import com.principal.connection.ConnCapip;
import com.principal.modelos.UserModel;
import com.principal.modelos.UserTrackModel;
import com.principal.presupuesto.ConnPpto;
import com.principal.presupuesto.WinState;
import com.principal.presupuesto.XJFrameJTable;
import com.principal.utils.TableToExcel;
import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class UserMenuReport extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del codigo programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colNum = 0;

    private final int colIdUser = 1;

    private final int colIdMenu = 2;

    private final int colOp = 3;

    private final int colObs = 4;

    private final int colUserDateTime = 5;

    private final int colServDateTime = 6;

    private String bdato;

    private String mesDesde;

    private String mesHasta;

    private int ejeFisMesDesde;

    private int ejeFisMesHasta;

    /**
     * @param inparent
     */
    public UserMenuReport(final java.awt.Window inparent) {
        super(inparent);
        initComponents();
        setTitle(getTitle() + " - " + Propiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, btnRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, txtReset);
        param.put(JTABLE, tblMaster);
        param.put(COLOBJ, colNum);
        param.put(JBSELECT, null);
        param.put(JBCANCEL, btnCancelar);
        ConfigComponents(param);
        InitConditions();
        UpdatePanels();
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
        if (inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }
        super.setVisible(inb);
    }

    /**
     * Valores y/o condiciones iniciales de la ventana
     */
    @Override
    public void InitConditions() {
        super.InitConditions();
        bdato = "user_menu_track";
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
        DefaultTableCellRenderer tcr;
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colNum).setCellRenderer(tcr);
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colIdUser).setCellRenderer(tcr);
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colIdMenu).setCellRenderer(tcr);
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colOp).setCellRenderer(tcr);
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.LEFT);
        tblMaster.getColumnModel().getColumn(colObs).setCellRenderer(tcr);
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colUserDateTime).setCellRenderer(tcr);
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colServDateTime).setCellRenderer(tcr);
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
        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        final Object[] datos = new Object[7];
        try {
            // Generar el comando SQL
            final ResultSet rs = ConnPpto.executeQuery(sqlPeriodo());
            while (rs.next()) {
                UserTrackModel aux = new UserTrackModel(rs);
                datos[colNum] = aux;
                datos[colIdUser] = UserModel.getUser(aux.getIdUser());
                String auxClass = aux.getMnuClass();
                datos[colIdMenu] = auxClass.substring(auxClass.lastIndexOf(".") + 1, auxClass.length());
                datos[colOp] = aux.getOp();
                datos[colObs] = aux.getObs();
                datos[colUserDateTime] = aux.getUserDateTime();
                datos[colServDateTime] = aux.getServDateTime();
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
        txtCant.setText(String.valueOf(tblMaster.getRowCount()));
    }

    String sqlPeriodo() {
        final String sql;
        final long ejeFis = Globales.getEjeFisYear() * 100;
        if (btnAno.isSelected()) {
            mesDesde = "Enero";
            mesHasta = "Diciembre";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 12;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes BETWEEN " + (ejeFis + 1) + " AND " + (ejeFis + 12) + " ORDER BY id DESC";
        } else if (btnEne.isSelected()) {
            mesDesde = "Enero";
            mesHasta = "Enero";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 1;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 1) + " ORDER BY id DESC";
        } else if (btnFeb.isSelected()) {
            mesDesde = "Febrero";
            mesHasta = "Febrero";
            ejeFisMesDesde = 2;
            ejeFisMesHasta = 2;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 2) + " ORDER BY id DESC";
        } else if (btnMar.isSelected()) {
            mesDesde = "Marzo";
            mesHasta = "Marzo";
            ejeFisMesDesde = 3;
            ejeFisMesHasta = 3;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 3) + " ORDER BY id DESC";
        } else if (btnAbr.isSelected()) {
            mesDesde = "Abril";
            mesHasta = "Abril";
            ejeFisMesDesde = 4;
            ejeFisMesHasta = 4;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 4) + " ORDER BY id DESC";
        } else if (btnMay.isSelected()) {
            mesDesde = "Mayo";
            mesHasta = "Mayo";
            ejeFisMesDesde = 5;
            ejeFisMesHasta = 5;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 5) + " ORDER BY id DESC";
        } else if (btnJun.isSelected()) {
            mesDesde = "Junio";
            mesHasta = "Junio";
            ejeFisMesDesde = 6;
            ejeFisMesHasta = 6;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 6) + " ORDER BY id DESC";
        } else if (btnJul.isSelected()) {
            mesDesde = "Julio";
            mesHasta = "Julio";
            ejeFisMesDesde = 7;
            ejeFisMesHasta = 7;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 7) + " ORDER BY id DESC";
        } else if (btnAgo.isSelected()) {
            mesDesde = "Agosto";
            mesHasta = "Agosto";
            ejeFisMesDesde = 8;
            ejeFisMesHasta = 8;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 8) + " ORDER BY id DESC";
        } else if (btnSep.isSelected()) {
            mesDesde = "Septiembre";
            mesHasta = "Septiembre";
            ejeFisMesDesde = 9;
            ejeFisMesHasta = 9;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 9) + " ORDER BY id DESC";
        } else if (btnOct.isSelected()) {
            mesDesde = "Octubre";
            mesHasta = "Octubre";
            ejeFisMesDesde = 10;
            ejeFisMesHasta = 10;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 10) + " ORDER BY id DESC";
        } else if (btnNov.isSelected()) {
            mesDesde = "Noviembre";
            mesHasta = "Noviembre";
            ejeFisMesDesde = 11;
            ejeFisMesHasta = 11;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 11) + " ORDER BY id DESC";
        } else if (btnDic.isSelected()) {
            mesDesde = "Diciembre";
            mesHasta = "Diciembre";
            ejeFisMesDesde = 12;
            ejeFisMesHasta = 12;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFis + 12) + " ORDER BY id DESC";
        } else {
            mesDesde = "Enero";
            mesHasta = "Diciembre";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 12;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes BETWEEN " + (ejeFis + 1) + " AND " + (ejeFis + 12) + " ORDER BY id DESC";
        }
        return sql;
    }

    @Override
    protected void UpdatePanelDetails() {
    }

    @Override
    protected void ClearDetails() {
    }

    /*
     * Segun el estado de la ventana, actualiza los componentes
     */
    public void UpdateEnabledAndFocus() {
        boolean isTableFill = tblMaster.getRowCount() > 0;
        txtReset.setEnabled(isTableFill);
        btnCancelar.setEnabled(isTableFill && (tblMaster.getSelectedRow() >= 0));
        btnRetornar.setEnabled(true);
        if (isTableFill) {
            tblMaster.requestFocusInWindow();
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
        buttonGroupPeriodo = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtReset = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        btnExcel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jpCmdState = new javax.swing.JPanel();
        lblmonto1 = new javax.swing.JLabel();
        txtCant = new javax.swing.JFormattedTextField(Integer.valueOf(0));
        btnCancelar = new javax.swing.JButton();
        btnReportSummary = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAno = new javax.swing.JToggleButton();
        btnEne = new javax.swing.JToggleButton();
        btnFeb = new javax.swing.JToggleButton();
        btnMar = new javax.swing.JToggleButton();
        btnAbr = new javax.swing.JToggleButton();
        btnMay = new javax.swing.JToggleButton();
        btnJun = new javax.swing.JToggleButton();
        btnJul = new javax.swing.JToggleButton();
        btnAgo = new javax.swing.JToggleButton();
        btnSep = new javax.swing.JToggleButton();
        btnOct = new javax.swing.JToggleButton();
        btnNov = new javax.swing.JToggleButton();
        btnDic = new javax.swing.JToggleButton();
        jpComandos = new javax.swing.JPanel();
        btnRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();
        setTitle("REPORTE SUMARIO DE ACCESO DE USUARIOS");
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
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID", "User", "ID Menú", "Operación", "Observación", "Acceso User", "Acceso Serv." }));
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
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 390));
        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        // NOI18N
        tblMaster.setFont(new java.awt.Font("Arial", 3, 15));
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "ID", "User", "Menú", "Oper.", "Observación", "Acceso User", "Acceso Serv." }) {

            Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class };

            boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };

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
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(100);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(175);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(300);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(160);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(6).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(160);
            tblMaster.getColumnModel().getColumn(6).setMaxWidth(200);
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
        // NOI18N
        lblmonto1.setFont(new java.awt.Font("Arial", 3, 16));
        lblmonto1.setText("Cantidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 342, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);
        txtCant.setEditable(false);
        txtCant.setBackground(new java.awt.Color(175, 204, 125));
        txtCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCant.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCant.setDoubleBuffered(true);
        txtCant.setFocusable(false);
        // NOI18N
        txtCant.setFont(new java.awt.Font("Arial", 3, 32));
        txtCant.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCant.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        jpCmdState.add(txtCant, gridBagConstraints);
        btnCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnCancelar.setFont(new java.awt.Font("Arial", 2, 16));
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(btnCancelar, gridBagConstraints);
        btnReportSummary.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnReportSummary.setFont(new java.awt.Font("Arial", 2, 16));
        btnReportSummary.setText("REPORTE");
        btnReportSummary.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReportSummary.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportSummaryActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(btnReportSummary, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jpCmdState, gridBagConstraints);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.setOpaque(false);
        btnAno.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnAno);
        // NOI18N
        btnAno.setFont(new java.awt.Font("Arial", 3, 18));
        btnAno.setSelected(true);
        btnAno.setText("Año");
        btnAno.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnoActionPerformed(evt);
            }
        });
        jPanel2.add(btnAno);
        btnEne.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnEne);
        // NOI18N
        btnEne.setFont(new java.awt.Font("Arial", 3, 18));
        btnEne.setText("Ene");
        btnEne.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEneActionPerformed(evt);
            }
        });
        jPanel2.add(btnEne);
        btnFeb.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnFeb);
        // NOI18N
        btnFeb.setFont(new java.awt.Font("Arial", 3, 18));
        btnFeb.setText("Feb");
        btnFeb.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFebActionPerformed(evt);
            }
        });
        jPanel2.add(btnFeb);
        btnMar.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnMar);
        // NOI18N
        btnMar.setFont(new java.awt.Font("Arial", 3, 18));
        btnMar.setText("Mar");
        btnMar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarActionPerformed(evt);
            }
        });
        jPanel2.add(btnMar);
        btnAbr.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnAbr);
        // NOI18N
        btnAbr.setFont(new java.awt.Font("Arial", 3, 18));
        btnAbr.setText("Abr");
        btnAbr.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrActionPerformed(evt);
            }
        });
        jPanel2.add(btnAbr);
        btnMay.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnMay);
        // NOI18N
        btnMay.setFont(new java.awt.Font("Arial", 3, 18));
        btnMay.setText("May");
        btnMay.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMayActionPerformed(evt);
            }
        });
        jPanel2.add(btnMay);
        btnJun.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnJun);
        // NOI18N
        btnJun.setFont(new java.awt.Font("Arial", 3, 18));
        btnJun.setText("Jun");
        btnJun.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJunActionPerformed(evt);
            }
        });
        jPanel2.add(btnJun);
        btnJul.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnJul);
        // NOI18N
        btnJul.setFont(new java.awt.Font("Arial", 3, 18));
        btnJul.setText("Jul");
        btnJul.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJulActionPerformed(evt);
            }
        });
        jPanel2.add(btnJul);
        btnAgo.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnAgo);
        // NOI18N
        btnAgo.setFont(new java.awt.Font("Arial", 3, 18));
        btnAgo.setText("Ago");
        btnAgo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgoActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgo);
        btnSep.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnSep);
        // NOI18N
        btnSep.setFont(new java.awt.Font("Arial", 3, 18));
        btnSep.setText("Sep");
        btnSep.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSepActionPerformed(evt);
            }
        });
        jPanel2.add(btnSep);
        btnOct.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnOct);
        // NOI18N
        btnOct.setFont(new java.awt.Font("Arial", 3, 18));
        btnOct.setText("Oct");
        btnOct.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOctActionPerformed(evt);
            }
        });
        jPanel2.add(btnOct);
        btnNov.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnNov);
        // NOI18N
        btnNov.setFont(new java.awt.Font("Arial", 3, 18));
        btnNov.setText("Nov");
        btnNov.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovActionPerformed(evt);
            }
        });
        jPanel2.add(btnNov);
        btnDic.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnDic);
        // NOI18N
        btnDic.setFont(new java.awt.Font("Arial", 3, 18));
        btnDic.setText("Dic");
        btnDic.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDicActionPerformed(evt);
            }
        });
        jPanel2.add(btnDic);
        jpEditables.add(jPanel2, new java.awt.GridBagConstraints());
        jpFondo.add(jpEditables);
        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());
        btnRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnRetornar.setFont(new java.awt.Font("Arial", 2, 16));
        btnRetornar.setText("RETORNAR");
        btnRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 560, 10, 0);
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
    private void btnAnoActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnAnoActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnAnoActionPerformed
    private void btnEneActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnEneActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnEneActionPerformed
    private void btnFebActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnFebActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnFebActionPerformed
    private void btnMarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnMarActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnMarActionPerformed
    private void btnAbrActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnAbrActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnAbrActionPerformed
    private void btnMayActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnMayActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnMayActionPerformed
    private void btnJunActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnJunActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnJunActionPerformed
    private void btnJulActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnJulActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnJulActionPerformed
    private void btnAgoActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnAgoActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnAgoActionPerformed
    private void btnSepActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnSepActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnSepActionPerformed
    private void btnOctActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnOctActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnOctActionPerformed
    private void btnNovActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnNovActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnNovActionPerformed
    private void btnDicActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnDicActionPerformed
        UpdatePanels();
    }

//GEN-LAST:event_btnDicActionPerformed
    private void btnReportSummaryActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnReportSummaryActionPerformed
        if (tblMaster.getRowCount() <= 0) {
            return;
        }
        final Calendar fechaOp = Calendar.getInstance();
        final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());
        fechaOp.add(Calendar.DAY_OF_MONTH, -2);
        final java.sql.Date fchDelete = new java.sql.Date(fechaOp.getTimeInMillis());
        try {
            // Borrar la tabla auxiliar para el reporte
            ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM user_menu_track_summary WHERE fchsession <= '" + fchDelete + "' OR iduser=" + UserPassIn.getIdUser() + " AND idsession=" + UserPassIn.getIdSession()).executeUpdate();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        // Se van tomando los valores de la tabla, para guardarlos en la base de datos
        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            UserTrackModel reg = (UserTrackModel) tblMaster.getValueAt(i, colNum);
            if (reg == null) {
                continue;
            }
            try {
                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO user_menu_track_summary(user,mnuclass,op,obs,userdatetime,servdatetime,fchsession,idsession,iduser) VALUES (?,?,?,?,?,?,?,?,?)");
                pst.setString(1, reg.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(reg.getIdUser()));
                String auxClass = reg.getMnuClass();
                auxClass = auxClass.substring(auxClass.lastIndexOf(".") + 1, auxClass.length());
                pst.setString(2, auxClass);
                pst.setString(3, reg.getOp());
                pst.setString(4, reg.getObs());
                pst.setTimestamp(5, reg.getUserDateTime());
                pst.setTimestamp(6, reg.getServDateTime());
                pst.setDate(7, fchSession);
                pst.setLong(8, UserPassIn.getIdSession());
                pst.setLong(9, reg.getIdUser());
                pst.executeUpdate();
            } catch (final Exception inex) {
                JOptionPane.showMessageDialog(null, inex);
                logger.error(inex);
                return;
            }
        }
        reportSummary();
    }

//GEN-LAST:event_btnReportSummaryActionPerformed
    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnExcelActionPerformed
        try {
            final String fileName = "RptUserTrack " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblMaster, fileName + ".xls");
            TableToExcel.exportTSV(tblMaster, fileName + ".txt");
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

//GEN-LAST:event_btnExcelActionPerformed
    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent ine) {
    }

    protected void jbCancelarActionPerformed() {
        Salir();
    }

    @SuppressWarnings("unchecked")
    public void reportSummary() {
        if (tblMaster.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "No hay registros para el reporte");
            return;
        }
        final long ejeFis;
        try {
            ejeFis = Globales.getEjeFisYear();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return;
        }
        final Map<String, Object> param = new HashMap<>(101);
        param.put("ejefis", ejeFis);
        param.put("ejefismes_desde", ejeFis * 100 + ejeFisMesDesde);
        param.put("ejefismes_hasta", ejeFis * 100 + ejeFisMesHasta);
        param.put("mesdesde", mesDesde);
        param.put("meshasta", mesHasta);
        param.put("fechahoy", Globales.dateFormat.format(Globales.getServerTimeStamp()));
        param.put("iduser", UserPassIn.getIdUser());
        param.put("idsession", UserPassIn.getIdSession());
        param.put("user", UserPassIn.getUserName());
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
        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/userTrackRptSummary.jasper");
        if (pathRpt != null) {
            new Thread() {

                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport(pathRpt, new HashMap<>(param), cn), false);
                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                        logger.error(inex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte sumario no encontrado");
        }
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
                new UserMenuReport(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAbr;

    private javax.swing.JToggleButton btnAgo;

    private javax.swing.JToggleButton btnAno;

    private javax.swing.JButton btnCancelar;

    private javax.swing.JToggleButton btnDic;

    private javax.swing.JToggleButton btnEne;

    private javax.swing.JButton btnExcel;

    private javax.swing.JToggleButton btnFeb;

    private javax.swing.JToggleButton btnJul;

    private javax.swing.JToggleButton btnJun;

    private javax.swing.JToggleButton btnMar;

    private javax.swing.JToggleButton btnMay;

    private javax.swing.JToggleButton btnNov;

    private javax.swing.JToggleButton btnOct;

    private javax.swing.JButton btnReportSummary;

    private javax.swing.JButton btnRetornar;

    private javax.swing.JToggleButton btnSep;

    private javax.swing.ButtonGroup buttonGroupPeriodo;

    private javax.swing.ButtonGroup buttonGroupTipo;

    private javax.swing.Box.Filler filler1;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JPanel jPanel2;

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

    private javax.swing.JButton txtReset;

    // End of variables declaration//GEN-END:variables
    private final Connection cn = ConnCapip.getInstance().getConnection();

    private static final Logger logger = LogManager.getLogger(UserMenuReport.class);
}
