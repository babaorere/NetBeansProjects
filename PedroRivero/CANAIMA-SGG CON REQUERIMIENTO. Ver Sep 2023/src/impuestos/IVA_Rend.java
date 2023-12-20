package impuestos;

import capipsistema.CapipPropiedades;
import connection.ConnCapip;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.IvaRetencionDetModel;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
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
public final class IVA_Rend extends javax.swing.JFrame {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private static final int colNum = 0;
    private static final int colIvaNum = 1;
    private static final int colCausado = 2;
    private static final int colFecha = 3;
    private static final int colFactura = 4;
    private static final int colBenef_razonsocial = 5;
    private static final int colMonto = 6;
    private static final int colMontoIva = 7;
    private static final int colRetenido = 8;
    private static final int colReg = 9;

    private String mesDesde;
    private String mesHasta;

    private java.sql.Date ejeFisMesDesde;
    private java.sql.Date ejeFisMesHasta;

    private String periodoSTotal;
    private String periodoSIva;
    private String periodoSRet;

    private final java.awt.Window parent;

    /**
     * Creates new form rendiciones
     *
     * @param inparent
     */
    public IVA_Rend(final java.awt.Window inparent) {
        super();
        initComponents();

        parent = inparent;

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
        tblMaster.getColumnModel().getColumn(colIvaNum).setCellRenderer(tcr);

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
    private void MostrarTblMaster() {

        final int filaAnt = tblMaster.getSelectedRow();

        tblMaster.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[10];
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery(sqlPeriodo());
            while (rs.next()) {
                IvaRetencionDetModel reg = new IvaRetencionDetModel(rs);
                datos[colNum] = reg.getId_iva_retencion_det();
                datos[colIvaNum] = String.valueOf(reg.getId_iva_retencion());
                datos[colCausado] = reg.getId_causado();
                datos[colFecha] = reg.getFecha_fact();
                datos[colFactura] = reg.getNum_fact();
                datos[colBenef_razonsocial] = reg.getBenef_razonsocial();
                datos[colMonto] = reg.getTotal_fact().doubleValue();
                datos[colMontoIva] = reg.getIva_bs().doubleValue();
                datos[colRetenido] = reg.getIva_retenido().doubleValue();
                datos[colReg] = reg;
                model.addRow(datos);
            }

            tblMaster.setModel(model);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        if ((filaAnt >= 0) && (tblMaster.getRowCount() > 0)) {
            int fila = min(filaAnt, tblMaster.getRowCount() - 1);

            tblMaster.setRowSelectionInterval(fila, fila);
            tblMaster.scrollRectToVisible(tblMaster.getCellRect(fila, fila, true));
        }

        calcularIvaRet();
    }

    /**
     * Rev 23/11/2016
     *
     */
    void calcularIvaRet() {
        double total = 0.00d;
        double iva = 0.00d;
        double ivaRet = 0.00d;
        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            total += (double) tblMaster.getValueAt(i, colMonto);
            iva += (double) tblMaster.getValueAt(i, colMontoIva);
            ivaRet += (double) tblMaster.getValueAt(i, colRetenido);
        }

        total = new BigDecimal(total).setScale(2, HALF_UP).doubleValue();
        iva = new BigDecimal(iva).setScale(2, HALF_UP).doubleValue();
        ivaRet = new BigDecimal(ivaRet).setScale(2, HALF_UP).doubleValue();

        try {
            periodoSTotal = Format.toStr(total);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            periodoSTotal = "";
        }

        try {
            periodoSIva = Format.toStr(iva);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            periodoSIva = "";
        }

        try {
            periodoSRet = Format.toStr(ivaRet);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
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
        jLabel6 = new javax.swing.JLabel();
        txtConcepto = new javax.swing.JTextField();
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
        setTitle("RENDICIÓN IVA");
        setMinimumSize(new java.awt.Dimension(1040, 673));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpFiltro.setMaximumSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setMinimumSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setOpaque(false);
        jpFiltro.setPreferredSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setLayout(new java.awt.GridBagLayout());

        jtFiltro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(btnReset, gridBagConstraints);

        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID", "Iva Núm.", "causado", "Fecha Factura", "Factura", "Contribuyente" }));
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

        btnExcel1.setBackground(java.awt.SystemColor.inactiveCaption);
        btnExcel1.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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

        txtEjeFis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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

        chkAnulado.setBackground(java.awt.SystemColor.inactiveCaption);
        chkAnulado.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
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
                "ID", "IVA NUM", "CAUSADO", "FECHA FACT.", "FACTURA", "CONTRIBUYENTE", "MONTO Bs.", "MONTO IVA BS.", "RETENIDO IVA BS.", "Reg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(250);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(250);
            tblMaster.getColumnModel().getColumn(6).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(6).setMaxWidth(250);
            tblMaster.getColumnModel().getColumn(7).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(7).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(7).setMaxWidth(250);
            tblMaster.getColumnModel().getColumn(8).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(8).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(8).setMaxWidth(250);
            tblMaster.getColumnModel().getColumn(9).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1007, 410));

        btnProcesar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnProcesar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnProcesar.setText("PROCESAR RENDICIÓN");
        btnProcesar.setToolTipText("");
        btnProcesar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnProcesar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProcesar.setIconTextGap(-3);
        btnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarActionPerformed(evt);
            }
        });
        getContentPane().add(btnProcesar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 190, 40));

        jLabel5.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("MONTO RETENIDO BS.");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 570, 180, 50));

        jLabel6.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CONCEPTO DE LA RENDICIÓN");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, 240, 30));

        txtConcepto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtConcepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtConcepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 530, 470, 30));

        btnConsultar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnConsultar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnConsultar.setText("CONSULTAR RENDICIÓN");
        btnConsultar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnConsultar.setEnabled(false);
        btnConsultar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        getContentPane().add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 570, 210, 40));

        txtIvaRet.setBackground(new java.awt.Color(175, 204, 125));
        txtIvaRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIvaRet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIvaRet.setFont(new java.awt.Font("Tahoma", 0, 32)); // NOI18N
        getContentPane().add(txtIvaRet, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 570, 210, 50));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.setOpaque(false);

        btnAno.setBackground(java.awt.SystemColor.activeCaption);
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

        btnEne.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnEne);
        btnEne.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnEne.setText("Ene");
        btnEne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEneActionPerformed(evt);
            }
        });
        jPanel2.add(btnEne);

        btnFeb.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnFeb);
        btnFeb.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnFeb.setText("Feb");
        btnFeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFebActionPerformed(evt);
            }
        });
        jPanel2.add(btnFeb);

        btnMar.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnMar);
        btnMar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnMar.setText("Mar");
        btnMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarActionPerformed(evt);
            }
        });
        jPanel2.add(btnMar);

        btnAbr.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnAbr);
        btnAbr.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAbr.setText("Abr");
        btnAbr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrActionPerformed(evt);
            }
        });
        jPanel2.add(btnAbr);

        btnMay.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnMay);
        btnMay.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnMay.setText("May");
        btnMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMayActionPerformed(evt);
            }
        });
        jPanel2.add(btnMay);

        btnJun.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnJun);
        btnJun.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnJun.setText("Jun");
        btnJun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJunActionPerformed(evt);
            }
        });
        jPanel2.add(btnJun);

        btnJul.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnJul);
        btnJul.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnJul.setText("Jul");
        btnJul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJulActionPerformed(evt);
            }
        });
        jPanel2.add(btnJul);

        btnAgo.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnAgo);
        btnAgo.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAgo.setText("Ago");
        btnAgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgoActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgo);

        btnSep.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnSep);
        btnSep.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnSep.setText("Sep");
        btnSep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSepActionPerformed(evt);
            }
        });
        jPanel2.add(btnSep);

        btnOct.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnOct);
        btnOct.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnOct.setText("Oct");
        btnOct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOctActionPerformed(evt);
            }
        });
        jPanel2.add(btnOct);

        btnNov.setBackground(new java.awt.Color(175, 204, 125));
        btngMes.add(btnNov);
        btnNov.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnNov.setText("Nov");
        btnNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovActionPerformed(evt);
            }
        });
        jPanel2.add(btnNov);

        btnDic.setBackground(new java.awt.Color(175, 204, 125));
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
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 1050, 770));

        setSize(new java.awt.Dimension(1046, 682));
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
            final String fileName = "RptIvaRendicion " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblMaster, fileName + ".xls");
            TableToExcel.exportTSV(tblMaster, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }//GEN-LAST:event_btnExcel1ActionPerformed

    private void txtEjeFisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEjeFisActionPerformed
        MostrarTblMaster();
    }//GEN-LAST:event_txtEjeFisActionPerformed

    private void tblMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMasterMouseClicked
        if (evt.getClickCount() == 2) {
            final int selRow = tblMaster.getSelectedRow();
            if (selRow >= 0) {
                IvaRetencionDetModel reg = (IvaRetencionDetModel) tblMaster.getValueAt(selRow, colReg);
                try {
                    ImpuestoRetencion.genIvaReport(reg.getId_iva_retencion(), new HashMap<>(101));
                } catch (final Exception inex) {
                    JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + inex);
                }
            }
        }
    }//GEN-LAST:event_tblMasterMouseClicked

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
        param.put("periodo_totaliva", periodoSIva);
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

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/rendicion_iva.jasper");
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
            JOptionPane.showMessageDialog(null, "Reporte de rendicion no encontrado");
        }
    }

    /**
     * Rev 23/11/2016
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
        } catch (final Exception inex) {
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
            final String strAnulado;
            if (chkAnulado.isSelected()) {
                strAnulado = "S";
            } else {
                strAnulado = "N";
            }

            sql = "SELECT * FROM iva_retencion_det WHERE ejefis >= '" + desde + "' AND ejefis <= '" + hasta + "' AND anulado_sn= '" + strAnulado + "' ORDER BY id_iva_retencion_det ASC";

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al formatear las fechas" + System.getProperty("line.separator") + inex);
            ejeFisMesDesde = null;
            ejeFisMesHasta = null;
            sql = "SELECT * FROM iva_retencion_det WHERE YEAR(ejefis) = " + Globales.getEjeFisYear() + " ORDER BY id_iva_retencion_det ASC";
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
                new IVA_Rend(null).setVisible(true);
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbFilter;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JTable tblMaster;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtEjeFis;
    private javax.swing.JFormattedTextField txtIvaRet;
    // End of variables declaration//GEN-END:variables

    private final Connection cn = ConnCapip.getInstance().getConnection();
}
