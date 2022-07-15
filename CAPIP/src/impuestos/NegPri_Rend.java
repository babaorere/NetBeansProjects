package impuestos;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.InputStream;
import static java.lang.Math.min;
import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.NegPriRptModel;
import modelos.NegPri_DetModel;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.DateCellRenderer;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;
import utils.TableToExcel;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class NegPri_Rend extends javax.swing.JFrame {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private static final int colNum = 0;
    private static final int colAnu = 1;
    private static final int colNegPriNum = 2;
    private static final int colCausado = 3;
    private static final int colFecha = 4;
    private static final int colFactura = 5;
    private static final int colBenef_razonsocial = 6;
    private static final int colMonto = 7;
    private static final int colBaseImponible = 8;
    private static final int colRetenido = 9;
    private static final int colReg = 10;

    private String mesDesde;
    private String mesHasta;

    private java.sql.Date ejeFisMesDesde;
    private java.sql.Date ejeFisMesHasta;

    private String periodoSTotal;
    private String periodoSBaseImponible;
    private String periodoSRet;

    private final java.awt.Window parent;

    /**
     * Creates new form
     *
     * @param _parent
     */
    public NegPri_Rend(final java.awt.Window _parent) {
        super();
        initComponents();

        parent = _parent;

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     * Rev 25/09/2016
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
     * Rev 10/10/2016
     *
     */
    private void setOwnBehavior() {
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
                actSalir();
            }
        });

    }

    /**
     * Rev 10/10/2016
     */
    private void setCompBehavior() {

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colNegPriNum).setCellRenderer(tcr);

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colFactura).setCellRenderer(tcr);

        tblMaster.getColumnModel().getColumn(colFecha).setCellRenderer(new DateCellRenderer());

        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        btnAno.setSelected(true);

        MostrarTblMaster();
        txtEjeFis.setText(String.valueOf(Globales.getServerYear()));
    }

    /**
     * Rev 22/11/2016
     *
     */
    void MostrarTblMaster() {

        final int filaAnt = tblMaster.getSelectedRow();

        tblMaster.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[11];
        try {
            final ResultSet rs = Conn.executeQuery(sqlPeriodo());
            while (rs.next()) {
                NegPri_DetModel reg = new NegPri_DetModel(rs);
                datos[colNum] = reg.getId_neg_pri_det();
                datos[colAnu] = reg.getAnulado_sn().equals("S") ? "S" : "";
                datos[colNegPriNum] = String.valueOf(reg.getId_neg_pri());
                datos[colCausado] = reg.getId_causado();
                datos[colFecha] = reg.getFecha_fact();
                datos[colFactura] = reg.getNum_fact();
                datos[colBenef_razonsocial] = reg.getBenef_razonsocial();
                datos[colMonto] = reg.getTotal_fact().doubleValue();
                datos[colBaseImponible] = reg.getBase_imponible().doubleValue();
                datos[colRetenido] = reg.getRetenido_bs().doubleValue();
                datos[colReg] = reg;
                model.addRow(datos);
            }

            tblMaster.setModel(model);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        if ((filaAnt >= 0) && (tblMaster.getRowCount() > 0)) {
            int fila = min(filaAnt, tblMaster.getRowCount() - 1);

            tblMaster.setRowSelectionInterval(fila, fila);
            tblMaster.scrollRectToVisible(tblMaster.getCellRect(fila, fila, true));
        }

        calcularRet();
    }

    /**
     * Rev 23/11/2016
     *
     */
    void calcularRet() {
        double total = 0.00d;
        double base_imponible = 0.00d;
        double ret = 0.00d;
        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            if (((String) tblMaster.getValueAt(i, colAnu)).isEmpty()) {
                total += (double) tblMaster.getValueAt(i, colMonto);
                base_imponible += (double) tblMaster.getValueAt(i, colBaseImponible);
                ret += (double) tblMaster.getValueAt(i, colRetenido);
            }
        }

        total = new BigDecimal(total).setScale(2, HALF_UP).doubleValue();
        base_imponible = new BigDecimal(base_imponible).setScale(2, HALF_UP).doubleValue();
        ret = new BigDecimal(ret).setScale(2, HALF_UP).doubleValue();

        try {
            periodoSTotal = Format.toStr(total);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            periodoSTotal = "";
        }

        try {
            periodoSBaseImponible = Format.toStr(base_imponible);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            periodoSBaseImponible = "";
        }

        try {
            periodoSRet = Format.toStr(ret);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            periodoSRet = "";
        }
        txtIvaRet.setText(periodoSRet);

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btngMes = new javax.swing.ButtonGroup();
        btnRend = new javax.swing.ButtonGroup();
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        btnExcel1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtEjeFis = new javax.swing.JTextField();
        chkAnulado = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        btnProcesar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnConsultar = new javax.swing.JButton();
        txtIvaRet = new javax.swing.JFormattedTextField();
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
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("RENDICIÓN NEGRO PRIMERO");
        setMinimumSize(new java.awt.Dimension(1040, 620));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel1, gridBagConstraints);

        btnReset.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnReset.setText("RESET");
        btnReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReset.setMaximumSize(new java.awt.Dimension(50, 20));
        btnReset.setMinimumSize(new java.awt.Dimension(50, 20));
        btnReset.setPreferredSize(new java.awt.Dimension(50, 20));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(btnReset, gridBagConstraints);

        jcbFilter.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID", "Islr Núm.", "Causado", "Fecha Factura", "Factura", "Contribuyente" }));
        jcbFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbFilter.setMaximumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setMinimumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jcbFilter, gridBagConstraints);

        btnExcel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnExcel1.setText("EXCEL");
        btnExcel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExcel1.setPreferredSize(new java.awt.Dimension(50, 20));
        btnExcel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcel1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(btnExcel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Ejercicio Fiscal");
        jLabel2.setMaximumSize(new java.awt.Dimension(90, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(90, 20));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
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
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(txtEjeFis, gridBagConstraints);

        chkAnulado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chkAnulado.setText("Anulado");
        chkAnulado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkAnuladoItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jpFiltro.add(chkAnulado, gridBagConstraints);

        getContentPane().add(jpFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ANU.", "N.P. NUM.", "CAUSADO", "FECHA FACT.", "FACTURA", "CONTRIBUYENTE", "MONTO Bs.", "BASE IMPONIBLE", "RETENIDO BS.", "Reg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(20);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(60);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(250);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(250);
            tblMaster.getColumnModel().getColumn(7).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(7).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(7).setMaxWidth(250);
            tblMaster.getColumnModel().getColumn(8).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(8).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(8).setMaxWidth(250);
            tblMaster.getColumnModel().getColumn(9).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(9).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(9).setMaxWidth(250);
            tblMaster.getColumnModel().getColumn(10).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1007, 410));

        btnProcesar.setBackground(new java.awt.Color(153, 153, 0));
        btnProcesar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnProcesar.setText("PROCESAR RENDICIÓN");
        btnProcesar.setToolTipText("");
        btnProcesar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnProcesar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProcesar.setIconTextGap(-3);
        btnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarActionPerformed(evt);
            }
        });
        getContentPane().add(btnProcesar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 190, 40));

        jLabel5.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("MONTO RETENIDO BS.");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 530, 190, 50));

        btnConsultar.setBackground(new java.awt.Color(153, 153, 0));
        btnConsultar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnConsultar.setText("CONSULTAR RENDICIÓN");
        btnConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnConsultar.setEnabled(false);
        btnConsultar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        getContentPane().add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 210, 40));

        txtIvaRet.setBackground(new java.awt.Color(204, 102, 0));
        txtIvaRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtIvaRet.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIvaRet.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        getContentPane().add(txtIvaRet, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 530, 250, 50));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.setOpaque(false);

        btngMes.add(btnAno);
        btnAno.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAno.setSelected(true);
        btnAno.setText("Año");
        btnAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnoActionPerformed(evt);
            }
        });
        jPanel2.add(btnAno);

        btnEne.setBackground(new java.awt.Color(255, 102, 0));
        btngMes.add(btnEne);
        btnEne.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnEne.setText("Ene");
        btnEne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEneActionPerformed(evt);
            }
        });
        jPanel2.add(btnEne);

        btnFeb.setBackground(new java.awt.Color(255, 102, 0));
        btngMes.add(btnFeb);
        btnFeb.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFeb.setText("Feb");
        btnFeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFebActionPerformed(evt);
            }
        });
        jPanel2.add(btnFeb);

        btnMar.setBackground(new java.awt.Color(255, 102, 0));
        btngMes.add(btnMar);
        btnMar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnMar.setText("Mar");
        btnMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarActionPerformed(evt);
            }
        });
        jPanel2.add(btnMar);

        btnAbr.setBackground(new java.awt.Color(255, 102, 0));
        btngMes.add(btnAbr);
        btnAbr.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAbr.setText("Abr");
        btnAbr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrActionPerformed(evt);
            }
        });
        jPanel2.add(btnAbr);

        btnMay.setBackground(new java.awt.Color(102, 102, 0));
        btngMes.add(btnMay);
        btnMay.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnMay.setText("May");
        btnMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMayActionPerformed(evt);
            }
        });
        jPanel2.add(btnMay);

        btnJun.setBackground(new java.awt.Color(102, 102, 0));
        btngMes.add(btnJun);
        btnJun.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnJun.setText("Jun");
        btnJun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJunActionPerformed(evt);
            }
        });
        jPanel2.add(btnJun);

        btnJul.setBackground(new java.awt.Color(102, 102, 0));
        btngMes.add(btnJul);
        btnJul.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnJul.setText("Jul");
        btnJul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJulActionPerformed(evt);
            }
        });
        jPanel2.add(btnJul);

        btnAgo.setBackground(new java.awt.Color(102, 102, 0));
        btngMes.add(btnAgo);
        btnAgo.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAgo.setText("Ago");
        btnAgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgoActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgo);

        btnSep.setBackground(new java.awt.Color(51, 102, 0));
        btngMes.add(btnSep);
        btnSep.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnSep.setText("Sep");
        btnSep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSepActionPerformed(evt);
            }
        });
        jPanel2.add(btnSep);

        btnOct.setBackground(new java.awt.Color(51, 102, 0));
        btngMes.add(btnOct);
        btnOct.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnOct.setText("Oct");
        btnOct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOctActionPerformed(evt);
            }
        });
        jPanel2.add(btnOct);

        btnNov.setBackground(new java.awt.Color(51, 102, 0));
        btngMes.add(btnNov);
        btnNov.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnNov.setText("Nov");
        btnNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovActionPerformed(evt);
            }
        });
        jPanel2.add(btnNov);

        btnDic.setBackground(new java.awt.Color(51, 102, 0));
        btngMes.add(btnDic);
        btnDic.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnDic.setText("Dic");
        btnDic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDicActionPerformed(evt);
            }
        });
        jPanel2.add(btnDic);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, 930, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 1050, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                new ().setVisible(true);
//            }
//        });
//
//        setVisible(false);
//        dispose();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnEneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEneActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnEneActionPerformed

    private void btnFebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFebActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnFebActionPerformed

    private void btnMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnMarActionPerformed

    private void btnAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnAbrActionPerformed

    private void btnMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMayActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnMayActionPerformed

    private void btnJunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJunActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnJunActionPerformed

    private void btnJulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJulActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnJulActionPerformed

    private void btnAgoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgoActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnAgoActionPerformed

    private void btnSepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSepActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnSepActionPerformed

    private void btnOctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOctActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnOctActionPerformed

    private void btnNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnNovActionPerformed

    private void btnDicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnDicActionPerformed

    private void btnAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnoActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_btnAnoActionPerformed

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarActionPerformed
        ReporteRendicion();
    }//GEN-LAST:event_btnProcesarActionPerformed

    private void btnExcel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel1ActionPerformed
        try {
            final String fileName = "RptNegPriRendicion " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblMaster, fileName + ".xls");
            TableToExcel.exportTSV(tblMaster, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }//GEN-LAST:event_btnExcel1ActionPerformed

    private void txtEjeFisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEjeFisActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_txtEjeFisActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed

    }//GEN-LAST:event_btnResetActionPerformed

    private void chkAnuladoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkAnuladoItemStateChanged
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                MostrarTblMaster();
            }
        });
    }//GEN-LAST:event_chkAnuladoItemStateChanged

    /**
     * Rev 23/11/2016
     *
     */
    private void ReporteRendicion() {
        if (tblMaster.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "No hay registros para el reporte");
            return;
        }

        final Map<String, Object> param = new HashMap<>(101);
        param.put("ejefis", Globales.getEjeFisYear());
        param.put("ejefismes_desde", ejeFisMesDesde);
        param.put("ejefismes_hasta", ejeFisMesHasta);
        param.put("mesdesde", mesDesde);
        param.put("meshasta", mesHasta);
        param.put("fechahoy", Globales.dateFormat.format(Globales.getServerTimeStamp()));
        param.put("periodo_total", periodoSTotal);
        param.put("periodo_total_base_imponible", periodoSBaseImponible);
        param.put("periodo_totalret", periodoSRet);
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

        ArrayList<NegPriRptModel> list = new ArrayList<>();

        BigDecimal tot = BigDecimal.ZERO;
        BigDecimal tot_base_imp = BigDecimal.ZERO;
        BigDecimal tot_ret = BigDecimal.ZERO;

        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            NegPri_DetModel reg = (NegPri_DetModel) tblMaster.getValueAt(i, colReg);

            list.add(new NegPriRptModel(new java.util.Date(reg.getEjefis().getTime()), reg.getAnulado_sn().equals("S") ? "S" : "",
                reg.getId_neg_pri(), reg.getNum_fact(), reg.getBenef_rif_ci(), reg.getBenef_razonsocial(),
                reg.getTotal_fact(), reg.getBase_imponible(), reg.getRetenido_bs()));

            if (reg.getAnulado_sn().equals("N")) {
                tot = tot.add(reg.getTotal_fact());
                tot_base_imp = tot_base_imp.add(reg.getBase_imponible());
                tot_ret = tot_ret.add(reg.getRetenido_bs());
            }
        }

        param.put("tot", tot);
        param.put("tot_base_imp", tot_base_imp);
        param.put("tot_ret", tot_ret);

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/neg_pri_rend.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), new JRBeanCollectionDataSource(list)), false);
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de rendicion no encontrado");
        }
    }

    /**
     * Rev 14/04/2017
     *
     * @return
     */
    String sqlPeriodo() {
        String sql;
        final String desde;
        final String hasta;

        long ejeFis;

        try {
            ejeFis = Format.toLong(txtEjeFis.getText().trim());
        } catch (final Exception _ex) {
            ejeFis = Globales.getServerYear();
            txtEjeFis.setText(String.valueOf(ejeFis));
        }

        try {

            if (btnAno.isSelected()) {
                mesDesde = "Enero";
                mesHasta = "Diciembre";
                desde = ejeFis + "/01/01";
                hasta = ejeFis + "/12/31";
            } else if (btnEne.isSelected()) {
                mesDesde = "Enero";
                mesHasta = "Enero";
                desde = ejeFis + "/01/01";
                hasta = ejeFis + "/01/31";
            } else if (btnFeb.isSelected()) {
                mesDesde = "Febrero";
                mesHasta = "Febrero";
                Calendar cal = Calendar.getInstance();
                cal.set((int) ejeFis, 1, 1); // 0 enero, 2, febreso ... 11 diciembre
                desde = ejeFis + "/02/01";
                hasta = ejeFis + "/02/" + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            } else if (btnMar.isSelected()) {
                mesDesde = "Marzo";
                mesHasta = "Marzo";
                desde = ejeFis + "/03/01";
                hasta = ejeFis + "/03/31";
            } else if (btnAbr.isSelected()) {
                mesDesde = "Abril";
                mesHasta = "Abril";
                desde = ejeFis + "/04/01";
                hasta = ejeFis + "/04/30";
            } else if (btnMay.isSelected()) {
                mesDesde = "Mayo";
                mesHasta = "Mayo";
                desde = ejeFis + "/05/01";
                hasta = ejeFis + "/05/31";
            } else if (btnJun.isSelected()) {
                mesDesde = "Junio";
                mesHasta = "Junio";
                desde = ejeFis + "/06/01";
                hasta = ejeFis + "/06/30";
            } else if (btnJul.isSelected()) {
                mesDesde = "Julio";
                mesHasta = "Julio";
                desde = ejeFis + "/07/01";
                hasta = ejeFis + "/07/31";
            } else if (btnAgo.isSelected()) {
                mesDesde = "Agosto";
                mesHasta = "Agosto";
                desde = ejeFis + "/08/01";
                hasta = ejeFis + "/08/31";
            } else if (btnSep.isSelected()) {
                mesDesde = "Septiembre";
                mesHasta = "Septiembre";
                desde = ejeFis + "/09/01";
                hasta = ejeFis + "/09/30";
            } else if (btnOct.isSelected()) {
                mesDesde = "Octubre";
                mesHasta = "Octubre";
                desde = ejeFis + "/10/01";
                hasta = ejeFis + "/10/31";
            } else if (btnNov.isSelected()) {
                mesDesde = "Noviembre";
                mesHasta = "Noviembre";
                desde = ejeFis + "/11/01";
                hasta = ejeFis + "/11/30";
            } else if (btnDic.isSelected()) {
                mesDesde = "Diciembre";
                mesHasta = "Diciembre";
                desde = ejeFis + "/12/01";
                hasta = ejeFis + "/12/31";
            } else {
                mesDesde = "Enero";
                mesHasta = "Diciembre";
                desde = ejeFis + "/01/01";
                hasta = ejeFis + "/12/31";
            }

            ejeFisMesDesde = new java.sql.Date(simpleDateFormat.parse(desde).getTime());
            ejeFisMesHasta = new java.sql.Date(simpleDateFormat.parse(hasta).getTime());
            final String sqlAnulado;
            if (chkAnulado.isSelected()) {
                sqlAnulado = " AND anulado_sn= 'S'";
            } else {
                sqlAnulado = "";
            }

            sql = "SELECT * FROM neg_pri_det WHERE ejefis >= '" + desde + "' AND ejefis <= '" + hasta + "' " + sqlAnulado + " ORDER BY id_neg_pri_det ASC";

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al formatear las fechas" + System.getProperty("line.separator") + _ex);
            ejeFisMesDesde = null;
            ejeFisMesHasta = null;
            sql = "SELECT * FROM neg_pri_det WHERE YEAR(ejefis) = " + Globales.getEjeFisYear() + " ORDER BY id_neg_pri_det ASC";
        }

        return sql;
    }

    /**
     * Rev 06/11/2016
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
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new NegPri_Rend(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAbr;
    private javax.swing.JToggleButton btnAgo;
    private javax.swing.JToggleButton btnAno;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JToggleButton btnDic;
    private javax.swing.JToggleButton btnEne;
    private javax.swing.JButton btnExcel1;
    private javax.swing.JToggleButton btnFeb;
    private javax.swing.JToggleButton btnJul;
    private javax.swing.JToggleButton btnJun;
    private javax.swing.JToggleButton btnMar;
    private javax.swing.JToggleButton btnMay;
    private javax.swing.JToggleButton btnNov;
    private javax.swing.JToggleButton btnOct;
    private javax.swing.JButton btnProcesar;
    private javax.swing.ButtonGroup btnRend;
    private javax.swing.JButton btnReset;
    private javax.swing.JToggleButton btnSep;
    private javax.swing.ButtonGroup btngMes;
    private javax.swing.JCheckBox chkAnulado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbFilter;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JTable tblMaster;
    private javax.swing.JTextField txtEjeFis;
    private javax.swing.JFormattedTextField txtIvaRet;
    // End of variables declaration//GEN-END:variables

    private final Connection cn = Conn.getConnection();
}
