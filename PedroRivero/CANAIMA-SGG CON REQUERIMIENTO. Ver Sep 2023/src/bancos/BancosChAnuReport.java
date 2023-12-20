/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package bancos;

import capipsistema.CapipPropiedades;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import connection.ConnCapip;
import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.AvanceEfectivoModel;
import modelos.BancosChAnuModel;
import modelos.PagAvaInterface;
import modelos.PagoModel;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
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
import utils.Num2Let;
import utils.TableToExcel;

/**
 *
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class BancosChAnuReport extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del código programado, mantiene relacionado el nombre de la columna con su posición
     */
    private final int colId = 0;
    private final int colFechaAnu = 1;
    private final int colBanco = 2;
    private final int colCuenta = 3;
    private final int colCheque = 4;
    private final int colMonto = 5;
    private final int colPA = 6;
    private final int colNumPA = 7;
    private final int colMotivoAnu = 8;

    private String bdato;
    private String mesDesde;
    private String mesHasta;
    private int ejeFisMesDesde;
    private int ejeFisMesHasta;

    /**
     *
     * @param inparent
     */
    public BancosChAnuReport(final java.awt.Window inparent) {
        super( inparent);
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
     * Establece el comportamiento de la presente Ventana Rev 10/10/2016
     *
     */
    private void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        setTitle(CapipPropiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        // Establecer la acción al cerrar ventana
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

        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, btnRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, btnReset);
        param.put(JTABLE, tblMaster);
        param.put(COLOBJ, colId);
        param.put(JBSELECT, btnSeleccionar);
        param.put(JBCANCEL, btnCancelar);

        ConfigComponents(param);
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
        InitConditions();
        UpdatePanels();
        txtEjeFis.setText(String.valueOf(Globales.getServerYear()));
    }

    /**
     * Valores y/o condiciones iniciales de la ventana
     */
    @Override
    public void InitConditions() {
        super.InitConditions();
        bdato = "bancos_ch_anu";
        winState = WinState.NORMAL;
    }

    @Override
    protected void ConfigComponents(final Map<String, Object> inparam) {
        super.ConfigComponents( inparam);

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
        tblMaster.getColumnModel().getColumn(colId).setCellRenderer(tcr);

        tblMaster.getColumnModel().getColumn(colFechaAnu).setCellRenderer(new DateCellRenderer());

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colCuenta).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colCheque).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colPA).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colNumPA).setCellRenderer(tcr);

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
        UpdateEnabledAndFocus();
    }

    /**
     * Actualizar la vista de la ventana. Deberia llamarse cuando se halla modificado la tabla
     *
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

        final Object[] datos = new Object[9];
        try {
            // Generar el comando SQL
            final ResultSet rs = ConnPpto.executeQuery(sqlPeriodo());
            while (rs.next()) {
                BancosChAnuModel aux = new BancosChAnuModel(rs);
                datos[colId] = aux;
                datos[colFechaAnu] = aux.getFechaAnu();
                datos[colBanco] = aux.getBanco();
                datos[colCuenta] = aux.getCuenta();
                datos[colCheque] = aux.getChequeAnu();
                datos[colMonto] = aux.getMonto();
                datos[colPA] = aux.getPagAva();
                datos[colNumPA] = aux.getIdPagAva();
                datos[colMotivoAnu] = aux.getMotivoAnu();
                model.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
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

    /**
     * Rev 26/01/2017
     *
     * @return
     */
    private String sqlPeriodo() {
        final String sql;
        long ejeFisMes;

        try {
            ejeFisMes = Format.toLong(txtEjeFis.getText().trim()) * 100;
        } catch (final Exception inex) {
            final long ejeFis = Globales.getServerYear();
            txtEjeFis.setText(String.valueOf(ejeFis));
            ejeFisMes = ejeFis * 100;
        }

        if (btnAno.isSelected()) {
            mesDesde = "Enero";
            mesHasta = "Diciembre";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 12;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes BETWEEN " + (ejeFisMes + 1) + " AND " + (ejeFisMes + 12) + " ORDER BY id_ch_anu ASC";
        } else if (btnEne.isSelected()) {
            mesDesde = "Enero";
            mesHasta = "Enero";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 1;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 1) + " ORDER BY id_ch_anu ASC";
        } else if (btnFeb.isSelected()) {
            mesDesde = "Febrero";
            mesHasta = "Febrero";
            ejeFisMesDesde = 2;
            ejeFisMesHasta = 2;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 2) + " ORDER BY id_ch_anu ASC";
        } else if (btnMar.isSelected()) {
            mesDesde = "Marzo";
            mesHasta = "Marzo";
            ejeFisMesDesde = 3;
            ejeFisMesHasta = 3;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 3) + " ORDER BY id_ch_anu ASC";
        } else if (btnAbr.isSelected()) {
            mesDesde = "Abril";
            mesHasta = "Abril";
            ejeFisMesDesde = 4;
            ejeFisMesHasta = 4;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 4) + " ORDER BY id_ch_anu ASC";
        } else if (btnMay.isSelected()) {
            mesDesde = "Mayo";
            mesHasta = "Mayo";
            ejeFisMesDesde = 5;
            ejeFisMesHasta = 5;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 5) + " ORDER BY id_ch_anu ASC";
        } else if (btnJun.isSelected()) {
            mesDesde = "Junio";
            mesHasta = "Junio";
            ejeFisMesDesde = 6;
            ejeFisMesHasta = 6;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 6) + " ORDER BY id_ch_anu ASC";
        } else if (btnJul.isSelected()) {
            mesDesde = "Julio";
            mesHasta = "Julio";
            ejeFisMesDesde = 7;
            ejeFisMesHasta = 7;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 7) + " ORDER BY id_ch_anu ASC";
        } else if (btnAgo.isSelected()) {
            mesDesde = "Agosto";
            mesHasta = "Agosto";
            ejeFisMesDesde = 8;
            ejeFisMesHasta = 8;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 8) + " ORDER BY id_ch_anu ASC";
        } else if (btnSep.isSelected()) {
            mesDesde = "Septiembre";
            mesHasta = "Septiembre";
            ejeFisMesDesde = 9;
            ejeFisMesHasta = 9;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 9) + " ORDER BY id_ch_anu ASC";
        } else if (btnOct.isSelected()) {
            mesDesde = "Octubre";
            mesHasta = "Octubre";
            ejeFisMesDesde = 10;
            ejeFisMesHasta = 10;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 10) + " ORDER BY id_ch_anu ASC";
        } else if (btnNov.isSelected()) {
            mesDesde = "Noviembre";
            mesHasta = "Noviembre";
            ejeFisMesDesde = 11;
            ejeFisMesHasta = 11;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 11) + " ORDER BY id_ch_anu ASC";
        } else if (btnDic.isSelected()) {
            mesDesde = "Diciembre";
            mesHasta = "Diciembre";
            ejeFisMesDesde = 12;
            ejeFisMesHasta = 12;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes=" + (ejeFisMes + 12) + " ORDER BY id_ch_anu ASC";
        } else {
            mesDesde = "Enero";
            mesHasta = "Diciembre";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 12;
            sql = "SELECT * FROM " + bdato + " WHERE ejefismes BETWEEN " + (ejeFisMes + 1) + " AND " + (ejeFisMes + 12) + " ORDER BY id_ch_anu ASC";
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
        btnReset.setEnabled(isTableFill);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupTipo = new javax.swing.ButtonGroup();
        buttonGroupPeriodo = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        btnExcel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtEjeFis = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jpCmdState = new javax.swing.JPanel();
        btnSeleccionar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        txtCant = new javax.swing.JFormattedTextField(Integer.valueOf((0)));
        btnReportSummary = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
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

        setTitle("REPORTE SUMARIO DE CHEQUES ANULADOS");
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
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel1, gridBagConstraints);

        btnReset.setBackground(java.awt.SystemColor.inactiveCaption);
        btnReset.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnReset.setText("RESET");
        btnReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReset.setMaximumSize(new java.awt.Dimension(50, 20));
        btnReset.setMinimumSize(new java.awt.Dimension(50, 20));
        btnReset.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(btnReset, gridBagConstraints);

        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID", "Fecha Anu.", "Banco", "Cuenta", "Cheque", "Monto Bs.", "P/A", "Núm. P/A", "Motivo Anu." }));
        jcbFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbFilter.setMaximumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setMinimumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jcbFilter, gridBagConstraints);

        btnExcel.setBackground(java.awt.SystemColor.inactiveCaption);
        btnExcel.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnExcel.setText("EXCEL");
        btnExcel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExcel.setPreferredSize(new java.awt.Dimension(50, 20));
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(btnExcel, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Ejercicio Fiscal");
        jLabel2.setMaximumSize(new java.awt.Dimension(90, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(90, 20));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel2, gridBagConstraints);

        txtEjeFis.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtEjeFis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEjeFis.setMaximumSize(new java.awt.Dimension(75, 21));
        txtEjeFis.setMinimumSize(new java.awt.Dimension(75, 21));
        txtEjeFis.setPreferredSize(new java.awt.Dimension(75, 21));
        txtEjeFis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEjeFisActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(txtEjeFis, gridBagConstraints);

        jpFondo.add(jpFiltro);

        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 390));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblMaster.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fecha Anu.", "Banco", "Cuenta", "Cheque", "Monto Bs.", "P/A", "Núm. P/A", "Motivo Anu."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Long.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblMaster.setDoubleBuffered(true);
        tblMaster.setFillsViewportHeight(true);
        tblMaster.setRowHeight(32);
        tblMaster.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblMaster.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(300);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(300);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(6).setMinWidth(30);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(30);
            tblMaster.getColumnModel().getColumn(6).setMaxWidth(50);
            tblMaster.getColumnModel().getColumn(7).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(7).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(7).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(8).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(8).setPreferredWidth(300);
            tblMaster.getColumnModel().getColumn(8).setMaxWidth(400);
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
        btnSeleccionar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnSeleccionar.setText("SELECCIONAR");
        btnSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(btnSeleccionar, gridBagConstraints);

        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
        txtCant.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCant.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCant.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        jpCmdState.add(txtCant, gridBagConstraints);

        btnReportSummary.setBackground(java.awt.SystemColor.inactiveCaption);
        btnReportSummary.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
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

        btnCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnCancelar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
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

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.setOpaque(false);

        btnAno.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnAno);
        btnAno.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
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
        btnEne.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnEne.setText("Ene");
        btnEne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEneActionPerformed(evt);
            }
        });
        jPanel2.add(btnEne);

        btnFeb.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnFeb);
        btnFeb.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFeb.setText("Feb");
        btnFeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFebActionPerformed(evt);
            }
        });
        jPanel2.add(btnFeb);

        btnMar.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnMar);
        btnMar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnMar.setText("Mar");
        btnMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarActionPerformed(evt);
            }
        });
        jPanel2.add(btnMar);

        btnAbr.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnAbr);
        btnAbr.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAbr.setText("Abr");
        btnAbr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrActionPerformed(evt);
            }
        });
        jPanel2.add(btnAbr);

        btnMay.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnMay);
        btnMay.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnMay.setText("May");
        btnMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMayActionPerformed(evt);
            }
        });
        jPanel2.add(btnMay);

        btnJun.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnJun);
        btnJun.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnJun.setText("Jun");
        btnJun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJunActionPerformed(evt);
            }
        });
        jPanel2.add(btnJun);

        btnJul.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnJul);
        btnJul.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnJul.setText("Jul");
        btnJul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJulActionPerformed(evt);
            }
        });
        jPanel2.add(btnJul);

        btnAgo.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnAgo);
        btnAgo.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAgo.setText("Ago");
        btnAgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgoActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgo);

        btnSep.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnSep);
        btnSep.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnSep.setText("Sep");
        btnSep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSepActionPerformed(evt);
            }
        });
        jPanel2.add(btnSep);

        btnOct.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnOct);
        btnOct.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnOct.setText("Oct");
        btnOct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOctActionPerformed(evt);
            }
        });
        jPanel2.add(btnOct);

        btnNov.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnNov);
        btnNov.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnNov.setText("Nov");
        btnNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovActionPerformed(evt);
            }
        });
        jPanel2.add(btnNov);

        btnDic.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroupPeriodo.add(btnDic);
        btnDic.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
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
        btnRetornar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnRetornar.setText("RETORNAR");
        btnRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 560, 20, 0);
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

    private void btnAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnoActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnAnoActionPerformed

    private void btnEneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEneActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnEneActionPerformed

    private void btnFebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFebActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnFebActionPerformed

    private void btnMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnMarActionPerformed

    private void btnAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnAbrActionPerformed

    private void btnMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMayActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnMayActionPerformed

    private void btnJunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJunActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnJunActionPerformed

    private void btnJulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJulActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnJulActionPerformed

    private void btnAgoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgoActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnAgoActionPerformed

    private void btnSepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSepActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnSepActionPerformed

    private void btnOctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOctActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnOctActionPerformed

    private void btnNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnNovActionPerformed

    private void btnDicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_btnDicActionPerformed

    private void btnReportSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportSummaryActionPerformed
        if (tblMaster.getRowCount() <= 0) {
            return;
        }

        final Calendar fechaOp = Calendar.getInstance();
        final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());

        fechaOp.add(Calendar.DAY_OF_MONTH, -2);
        final java.sql.Date fchDelete = new java.sql.Date(fechaOp.getTimeInMillis());

        try {
            ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM bancos_ch_anu_aux_rpt WHERE fchsession <= '" + fchDelete + "' OR iduser=" + UserPassIn.getIdUser() + " AND idsession=" + UserPassIn.getIdSession()).executeUpdate();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            return;
        }

        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            BancosChAnuModel regChAnu = (BancosChAnuModel) tblMaster.getValueAt(i, colId);
            try {
                final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO bancos_ch_anu_aux_rpt(banco,cuenta,cheque_anu,cheque_nvo,monto,pag_ava,num_pag_ava,motivo_anu,fecha_anu,idsession,fchsession,iduser) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, regChAnu.getBanco()); // banco
                pst.setString(2, regChAnu.getCuenta()); // cuenta
                pst.setString(3, regChAnu.getChequeAnu()); // ch_anu
                pst.setString(4, regChAnu.getChequeNvo()); // ch_nvo
                pst.setString(5, regChAnu.getMonto()); // monto
                pst.setString(6, regChAnu.getPagAva()); // pag_ava
                pst.setLong(7, regChAnu.getIdPagAva()); // num_pag_ava
                pst.setString(8, regChAnu.getMotivoAnu()); // motivo
                pst.setDate(9, regChAnu.getFechaAnu());
                pst.setLong(10, UserPassIn.getIdSession());
                pst.setDate(11, fchSession);
                pst.setLong(12, UserPassIn.getIdUser());
                pst.executeUpdate();
            } catch (final Exception inex) {
                JOptionPane.showMessageDialog(null, inex);
                return;
            }
        }

        reportSummary();
    }//GEN-LAST:event_btnReportSummaryActionPerformed

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        // la rutina de ejecucion se realiza por un listerner programado
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        try {
            final String fileName = "RptChAnu_" + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblMaster, fileName + ".xls");
            TableToExcel.exportTSV(tblMaster, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

    }//GEN-LAST:event_btnExcelActionPerformed

    private void txtEjeFisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEjeFisActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_txtEjeFisActionPerformed

    @SuppressWarnings({"unchecked"})
    private void verPlanillaAvaAnu(final PagAvaInterface inregPA, final BancosChAnuModel inregAnu) throws Exception {

        final Map<String, Object> param = new HashMap<>(101);
        param.put("id_ch_anu", inregAnu.getIdChAnu());

        final String NumLiteral = Num2Let.convert(new BigDecimal( inregAnu.getMonto().replace(".", "").replace(",", ".")).setScale(2, RoundingMode.HALF_UP));
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

        param.put("fecha", inregAnu.getFechaAnu());
        param.put("razonsocial", inregPA.getBenef_razonsocial());
        param.put("rif", inregPA.getBenef_rif_ci());
        param.put("idsession", UserPassIn.getIdSession());
        param.put("ejefis", Globales.getEjeFisYear());
        param.put("iduser", UserPassIn.getIdUser());
        param.put("user", UserPassIn.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(UserPassIn.getIdUser()));
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("motivo_anu", inregAnu.getMotivoAnu());
        param.put("monto", inregAnu.getMonto());
        param.put("pag_ava", inregAnu.getPagAva().equals("P") ? "Pago" : "Avance");

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

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/bancos_ch_anu.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), cn), false);
                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Planilla no encontrada");
        }
    }

    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent ine) {
        final JTable jtable = (JTable) xjparam.get(JTABLE);
        final Integer colReg = (Integer) xjparam.get(COLOBJ);

        if (jtable != null) {
            final int selRow = jtable.getSelectedRow();
            if (selRow >= 0) {
                if ((colReg != null) && (colReg >= 0)) {
                    try {

                        final PagAvaInterface regPagAva;
                        BancosChAnuModel regChAnu = (BancosChAnuModel) tblMaster.getValueAt(selRow, colId);

                        switch (regChAnu.getPagAva()) {
                            case "P":
                                final ResultSet rsP = ConnCapip.getInstance().executeQuery("SELECT * FROM orden_pago WHERE id_orden_pago=" + regChAnu.getIdPagAva());
                                if (rsP.next()) {
                                    regPagAva = new PagoModel(rsP);
                                } else {
                                    throw new Exception("Número de Pago, inexistente : " + regChAnu.getIdPagAva());
                                }
                                rsP.close();
                                break;

                            case "A":
                                final ResultSet rsA = ConnCapip.getInstance().executeQuery("SELECT * FROM avance_efectivo WHERE id=" + regChAnu.getIdPagAva());
                                if (rsA.next()) {
                                    regPagAva = new AvanceEfectivoModel(rsA);
                                } else {
                                    throw new Exception("Número de Fondo avance, inexistente : " + regChAnu.getIdPagAva());
                                }
                                rsA.close();
                                break;
                            default:
                                throw new Exception("Tipo de Pago o Avance inválido : <" + regChAnu.getPagAva() + ">");
                        }

                        verPlanillaAvaAnu(regPagAva, regChAnu);

                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                    }
                }
            }
        }
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
        param.put("user", UserPassIn.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(UserPassIn.getIdUser()));
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());

        param.put("aux_1", CapipPropiedades.CAPIP_AUX_1);
        param.put("aux_2", CapipPropiedades.CAPIP_AUX_2);
        param.put("linea_1", CapipPropiedades.CAPIP_LINEA_1);
        param.put("linea_2", CapipPropiedades.CAPIP_LINEA_2);
        param.put("linea_3", CapipPropiedades.CAPIP_LINEA_3);
        param.put("linea_4", CapipPropiedades.CAPIP_LINEA_4);

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/bancos_ch_anu_RptSummary.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), cn), false);
                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte no encontrado");
        }
    }

    /**
     * @param args ninguno
     */
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new BancosChAnuReport(null).setVisible(true);
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
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRetornar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JToggleButton btnSep;
    private javax.swing.ButtonGroup buttonGroupPeriodo;
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTextField txtEjeFis;
    // End of variables declaration//GEN-END:variables

    private final Connection cn = ConnCapip.getInstance().getConnection();
}
