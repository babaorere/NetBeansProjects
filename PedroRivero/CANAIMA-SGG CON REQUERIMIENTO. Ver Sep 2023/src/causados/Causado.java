/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package causados;

import capipsistema.CapipPropiedades;
import connection.ConnCapip;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import gen_next_num.GenNextNum;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import static java.lang.Math.min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import modelos.BenefModel;
import modelos.CauModel;
import modelos.ComprDetModel;
import modelos.ComprModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.DateCellRenderer;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;
import utils.Num2Let;
import utils.TableToExcel;
import utils.TipoCompr;

public final class Causado extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private final javax.swing.ImageIcon checkIcon;

    private static final int COL_MASTER_SELECT = 0;
    private static final int COL_MASTER_ID_COMPR = 1;
    private static final int COL_MASTER_RAZON_SOCIAL = 2;
    private static final int COL_MASTER_RIF_CI = 3;
    private static final int COL_MASTER_TOTAL = 4;
    private static final int COL_MASTER_IVA_BS = 5;
    private static final int COL_MASTER_GRAV_BS = 6;
    private static final int COL_MASTER_FECHA_FACT = 7;
    private static final int COL_MASTER_NUM_FACT = 8;
    private static final int COL_MASTER_IVA_PORC_APLIC = 9;
    private static final int COL_MASTER_REG_COMPR = 10;

    private final java.awt.Window parent;

    private BigDecimal iva_aplic;

    private String tbl;

    /**
     * Mantiene el generador de numeros o ID
     */
    private static final GenNextNum cauNextNum;

    static {
        cauNextNum = new GenNextNum("CAU");
    }

    /**
     * Creates new form Rev 24/09/2016
     *
     * @param inparent
     */
    public Causado(final java.awt.Window inparent) {

        super();
        initComponents();

        checkIcon = new javax.swing.ImageIcon(getClass().getResource("/imagenes/circled_checkmark_32x32.png"));
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
     * Establece el comportamiento de la presente Ventana Rev 21/09/2016
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
        setTblMasterBehavior();
        setTblDetalleBehavior();
    }

    /**
     * Rev 21/09/2016
     *
     */
    private void setTblMasterBehavior() {

        // Configurar la tabla de las Ordenes, para manejar el ENTER
        tblMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "action.enter");
        tblMaster.getActionMap().put("action.enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                actInsertarDetalle();
            }
        });

        // Configurar la tabla las Ordenes, para manejar el SPACE
        tblMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "action.space");
        tblMaster.getActionMap().put("action.space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actInsertarDetalle();
            }
        });

        tblMaster.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent ine) {

                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        calcularTotal();
                    }
                });
            }
        });

        // Configurar la tabla de Maestra, para alinear las columnas
        tblMaster.getColumnModel().getColumn(COL_MASTER_FECHA_FACT).setCellRenderer(new DateCellRenderer());

        // Configurar la tabla
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 14/09/2016
     */
    private void calcularTotal() {
        BigDecimal total_bs = BigDecimal.ZERO;
        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            if ((Boolean) tblMaster.getValueAt(i, COL_MASTER_SELECT)) { // Solo las seleccionadas
                total_bs = total_bs.add(BigDecimal.valueOf((double) tblMaster.getValueAt(i, COL_MASTER_TOTAL)));
            }
        }

        txtTotalBs.setText(Format.toStr(total_bs));
    }

    /**
     * Rev 22/09/2016
     *
     */
    private void setTblDetalleBehavior() {

        // Configurar la tabla
        tblDetalle.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblDetalle.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblDetalle.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 25/09/2016
     *
     */
    private void setStartConditions() {

        txtBenefRazonSocial.setText("");
        txtBenefRifCI.setText("");
        iva_aplic = BigDecimal.ZERO;

        txtNroCompr.setText("");

        try {
            txtNroCausado.setText(String.valueOf(cauNextNum.checkNum()));
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + inex);
            txtNroCausado.setText("1");
        }

        txtFecha.setText(Format.toStr(new java.sql.Date(Globales.getServerTimeStamp().getTime())));

        txtConcepto.setText("");

        // Seleccionar la tabla Correspondiente
        if (btnCompra.isSelected()) {

            btnCompra.setEnabled(false);
            btnServicio.setEnabled(true);
            btnOtrosCompr.setEnabled(true);
            tbl = "compr_compra";
        } else if (btnServicio.isSelected()) {

            btnCompra.setEnabled(true);
            btnServicio.setEnabled(false);
            btnOtrosCompr.setEnabled(true);
            tbl = "compr_servicio";
        } else if (btnOtrosCompr.isSelected()) {

            btnCompra.setEnabled(true);
            btnServicio.setEnabled(true);
            btnOtrosCompr.setEnabled(false);
            tbl = "compr_otros";
        } else { // Seleccionar el Presupuesto de compra

            btnCompra.setEnabled(true);
            btnCompra.setSelected(true);

            btnCompra.setEnabled(false);
            btnServicio.setEnabled(true);
            btnOtrosCompr.setEnabled(true);
            tbl = "compr_compra";
        }

        txtTotalBs.setText("0,00");

        UpdateTblCompr();
        mostrarDetalle(0);

        if (tblMaster.getRowCount() > 0) {
            tblMaster.setRowSelectionInterval(0, 0);
            tblMaster.scrollRectToVisible(tblMaster.getCellRect(0, 0, true));
        }

        java.awt.EventQueue.invokeLater(tblMaster::requestFocusInWindow);
    }

    /**
     * Rev 20/09/2016
     */
    private void UpdateTblCompr() {

        // Borrar la tabla maestra
        tblMaster.clearSelection();
        final DefaultTableModel modelMas = (DefaultTableModel) tblMaster.getModel();
        modelMas.getDataVector().removeAllElements();
        modelMas.fireTableDataChanged();

        // Llenar la tabla Maestra
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM " + tbl + " WHERE id_causado= 0 and YEAR(ejefis)= " + Globales.getEjeFisYear() + " AND anulado_sn= 'N' ORDER BY id_compr ASC");
            while (rs.next()) {
                ComprModel reg = new ComprModel(rs);
                final Object[] datos = new Object[11];
                datos[COL_MASTER_SELECT] = false;
                datos[COL_MASTER_ID_COMPR] = reg.getId_compromiso();
                datos[COL_MASTER_RAZON_SOCIAL] = reg.getBenef_razonsocial();
                datos[COL_MASTER_RIF_CI] = reg.getBenef_rif_ci();
                datos[COL_MASTER_TOTAL] = Format.toDouble(reg.getTotal_bs());
                datos[COL_MASTER_IVA_BS] = Format.toDouble(reg.getIva_grav_bs().multiply(reg.getIva_porc_aplic())) / 100.00d;
                datos[COL_MASTER_GRAV_BS] = Format.toDouble(reg.getIva_grav_bs());
                datos[COL_MASTER_FECHA_FACT] = reg.getFechaFact();
                datos[COL_MASTER_NUM_FACT] = reg.getNumFact();
                datos[COL_MASTER_IVA_PORC_APLIC] = Format.toDouble(reg.getIva_porc_aplic());
                datos[COL_MASTER_REG_COMPR] = reg;

                modelMas.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }

    /**
     * Rev 20/09/2016
     *
     * @param inid_compr
     */
    void mostrarDetalle(final long inid_compr) {

        // Borrar la tabla de detalles
        tblDetalle.clearSelection();
        final DefaultTableModel modelDet = (DefaultTableModel) tblDetalle.getModel();
        modelDet.getDataVector().removeAllElements();
        modelDet.fireTableDataChanged();

        if ( inid_compr <= 0) {
            txtNroCompr.setText("");
            return;
        }

        String sql = "SELECT * FROM compr_det WHERE id_compr='" + inid_compr + "' AND tipo_compr= ";
        if (btnCompra.isSelected()) {
            sql = sql + "'OC'";
        } else if (btnServicio.isSelected()) {
            sql = sql + "'OS'";
        } else if (btnOtrosCompr.isSelected()) {
            sql = sql + "'CO'";
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Presupuesto");
            java.awt.EventQueue.invokeLater(btnCompra::requestFocusInWindow);
            return;
        }

        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery(sql);
            while (rs.next()) {
                final Object[] datos = new Object[6];
                BigDecimal cant = rs.getBigDecimal("cantpro");
                datos[0] = Format.toDouble(cant);
                datos[1] = rs.getString("descpro");
                BigDecimal punitario = rs.getBigDecimal("punitario");
                datos[2] = Format.toDouble(punitario);
                datos[3] = Format.toDouble(cant.multiply(punitario));
                datos[4] = rs.getString("codpresu");
                datos[5] = rs.getString("partida");
                modelDet.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        btnCompra = new javax.swing.JToggleButton();
        btnServicio = new javax.swing.JToggleButton();
        btnOtrosCompr = new javax.swing.JToggleButton();
        btnAnular = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnReversar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtConcepto = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtBenefRazonSocial = new javax.swing.JTextField();
        txtBenefRifCI = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtNroCompr = new javax.swing.JFormattedTextField();
        txtNroCausado = new javax.swing.JFormattedTextField();
        txtFecha = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        btnExcel = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        txtTotalBs = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        lblOC = new javax.swing.JLabel();
        lblOS = new javax.swing.JLabel();
        lblCO = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("GASTO CAUSADO");
        setMinimumSize(new java.awt.Dimension(1150, 730));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 2, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ORDENES POR CAUSAR");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 580, -1));

        btnCompra.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroup1.add(btnCompra);
        btnCompra.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnCompra.setSelected(true);
        btnCompra.setText("ORDEN DE COMPRA");
        btnCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnCompra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraActionPerformed(evt);
            }
        });
        getContentPane().add(btnCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 240, 40));

        btnServicio.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroup1.add(btnServicio);
        btnServicio.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnServicio.setText("ORDEN DE SERVICIO");
        btnServicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServicioActionPerformed(evt);
            }
        });
        getContentPane().add(btnServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 240, 40));

        btnOtrosCompr.setBackground(java.awt.SystemColor.inactiveCaption);
        buttonGroup1.add(btnOtrosCompr);
        btnOtrosCompr.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnOtrosCompr.setText("OTROS COMPROMISOS");
        btnOtrosCompr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnOtrosCompr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtrosComprActionPerformed(evt);
            }
        });
        getContentPane().add(btnOtrosCompr, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 240, 40));

        btnAnular.setBackground(java.awt.SystemColor.inactiveCaption);
        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
        btnAnular.setToolTipText("Anular Ordenes de Compromiso, sin Causar.");
        btnAnular.setMaximumSize(new java.awt.Dimension(55, 41));
        btnAnular.setMinimumSize(new java.awt.Dimension(55, 41));
        btnAnular.setPreferredSize(new java.awt.Dimension(55, 41));
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnular, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 10, -1, -1));

        btnGuardar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnGuardar.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 650, 130, 50));

        btnReversar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnReversar.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnReversar.setText("ANULAR");
        btnReversar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReversar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReversarActionPerformed(evt);
            }
        });
        getContentPane().add(btnReversar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 650, 120, 50));

        jLabel10.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel10.setText("DATOS DEL BENEFICIARIO");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 212, -1));

        tblDetalle.setAutoCreateRowSorter(true);
        tblDetalle.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cant.", "Descripción", "Precio U. Bs.", "Sub. Total Bs.", "Cod. Presup.", "Partida"
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
        tblDetalle.setGridColor(new java.awt.Color(0, 0, 0));
        tblDetalle.setSelectionBackground(new java.awt.Color(175, 204, 125));
        jScrollPane2.setViewportView(tblDetalle);
        if (tblDetalle.getColumnModel().getColumnCount() > 0) {
            tblDetalle.getColumnModel().getColumn(0).setMinWidth(50);
            tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblDetalle.getColumnModel().getColumn(0).setMaxWidth(100);
            tblDetalle.getColumnModel().getColumn(2).setMinWidth(75);
            tblDetalle.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblDetalle.getColumnModel().getColumn(2).setMaxWidth(150);
            tblDetalle.getColumnModel().getColumn(3).setMinWidth(100);
            tblDetalle.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblDetalle.getColumnModel().getColumn(3).setMaxWidth(200);
            tblDetalle.getColumnModel().getColumn(4).setMinWidth(100);
            tblDetalle.getColumnModel().getColumn(4).setPreferredWidth(180);
            tblDetalle.getColumnModel().getColumn(4).setMaxWidth(250);
        }

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 1110, 140));

        jLabel11.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("DETALLE DEL COMPROMISO");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 310, -1));

        txtConcepto.setColumns(20);
        txtConcepto.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        txtConcepto.setLineWrap(true);
        txtConcepto.setRows(5);
        txtConcepto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtConcepto.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConceptoKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txtConcepto);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 1110, 70));

        jLabel12.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("CONCEPTO DEL CAUSADO");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 540, 340, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBenefRazonSocial.setEditable(false);
        txtBenefRazonSocial.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        txtBenefRazonSocial.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBenefRazonSocial.setAutoscrolls(false);
        txtBenefRazonSocial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBenefRazonSocial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtBenefRazonSocial.setEnabled(false);
        jPanel1.add(txtBenefRazonSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 13, 260, -1));

        txtBenefRifCI.setEditable(false);
        txtBenefRifCI.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        txtBenefRifCI.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBenefRifCI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBenefRifCI.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtBenefRifCI.setEnabled(false);
        txtBenefRifCI.setMaximumSize(new java.awt.Dimension(150, 26));
        txtBenefRifCI.setMinimumSize(new java.awt.Dimension(150, 26));
        txtBenefRifCI.setPreferredSize(new java.awt.Dimension(150, 26));
        jPanel1.add(txtBenefRifCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 42, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel2.setText("BENEFICIARIO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 134, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel3.setText("CEDULA / R.I.F.");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 42, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 420, 70));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel4.setText("COMPROMISO Nº");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 30));

        jLabel5.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel5.setText("GASTO CAUSADO Nº");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, -1, 30));

        jLabel15.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel15.setText("  FECHA");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, -1, 30));

        txtNroCompr.setEditable(false);
        txtNroCompr.setBackground(new java.awt.Color(175, 204, 125));
        txtNroCompr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNroCompr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNroCompr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNroCompr.setEnabled(false);
        txtNroCompr.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jPanel2.add(txtNroCompr, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 100, 40));

        txtNroCausado.setEditable(false);
        txtNroCausado.setBackground(new java.awt.Color(175, 204, 125));
        txtNroCausado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNroCausado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNroCausado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNroCausado.setEnabled(false);
        txtNroCausado.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jPanel2.add(txtNroCausado, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 110, 40));

        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 110, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 670, 70));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sel", "Núm. Compr.", "Beneficiario", "RIF/CI Benef.", "Total(+IVA) Bs.", "IVA Bs.", "Total Grab. Bs.", "Fecha Fact.", "Num. Fact.", "IVA %", "RegCompr"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Long.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class
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
        tblMaster.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMasterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(25);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(100);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(45);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(90);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(180);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(150);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(80);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(160);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(6).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(125);
            tblMaster.getColumnModel().getColumn(6).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(7).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(7).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(7).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(8).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(8).setPreferredWidth(100);
            tblMaster.getColumnModel().getColumn(8).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(9).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(9).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(10).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 186, 1110, 180));

        btnExcel.setBackground(java.awt.SystemColor.inactiveCaption);
        btnExcel.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnExcel.setText("EXCEL");
        btnExcel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        getContentPane().add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 650, 120, 50));

        btnConsultar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnConsultar.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        getContentPane().add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 650, 150, 50));

        txtTotalBs.setEditable(false);
        txtTotalBs.setBackground(new java.awt.Color(175, 204, 125));
        txtTotalBs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalBs.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalBs.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalBs.setText("0,00");
        txtTotalBs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalBs.setEnabled(false);
        txtTotalBs.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        getContentPane().add(txtTotalBs, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 650, 230, 50));

        jLabel9.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("MONTO BS.");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 650, 130, 50));

        lblOC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/circled_checkmark_32x32.png"))); // NOI18N
        getContentPane().add(lblOC, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 42, 32, 32));
        getContentPane().add(lblOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 42, 32, 32));

        lblCO.setToolTipText("");
        getContentPane().add(lblCO, new org.netbeans.lib.awtextra.AbsoluteConstraints(905, 42, 32, 32));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 1160, 760));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Rev 21/11/2016
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

    private void tblMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMasterMouseClicked
        actInsertarDetalle();
    }//GEN-LAST:event_tblMasterMouseClicked

    private void actInsertarDetalle() {
        final int selectedRow = tblMaster.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        final Boolean sel = !(Boolean) tblMaster.getValueAt(selectedRow, 0);

        // Cambiar estado de la seleccion
        tblMaster.setValueAt(sel, selectedRow, COL_MASTER_SELECT);

        if (sel) {
            final String actRif = txtBenefRifCI.getText();
            if (actRif.isEmpty()) {
                ComprModel reg = (ComprModel) tblMaster.getValueAt(selectedRow, COL_MASTER_REG_COMPR);

                txtNroCompr.setText(Format.toStr(reg.getId_compromiso()));
                txtBenefRazonSocial.setText(reg.getBenef_razonsocial());
                txtBenefRazonSocial.setCaretPosition(0);
                txtBenefRifCI.setText(reg.getBenef_rif_ci());
                iva_aplic = reg.getIva_porc_aplic();
                txtConcepto.setText(reg.getObservacion());
                mostrarDetalle((Long) tblMaster.getValueAt(selectedRow, COL_MASTER_ID_COMPR));
            } else {
                // Significa que ya se ha selecciodado un registro, 
                // por lo que esta seleccion debe ser del mismo bebeficiario
                final String selRif = (String) tblMaster.getValueAt(selectedRow, COL_MASTER_RIF_CI);
                if (selRif.equals(actRif)) {

                    // Verificar que tienen el mismo porcentaje de IVA
                    //
                    final ComprModel reg = (ComprModel) tblMaster.getValueAt(selectedRow, COL_MASTER_REG_COMPR);
                    if (reg.getIva_porc_aplic().equals(iva_aplic)) {
                        txtNroCompr.setText(tblMaster.getValueAt(selectedRow, COL_MASTER_ID_COMPR).toString());
                        mostrarDetalle((Long) tblMaster.getValueAt(selectedRow, COL_MASTER_ID_COMPR));
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar el mismo benef. con el mismo % de IVA");
                        tblMaster.setValueAt(false, selectedRow, COL_MASTER_SELECT);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar el mismo beneficiario");
                    tblMaster.setValueAt(false, selectedRow, COL_MASTER_SELECT);
                }
            }
        } else {
            // Buscar si queda algun registro seleccionado
            int posSel = -1;
            for (int i = 0; i < tblMaster.getRowCount(); i++) {
                if ((Boolean) tblMaster.getValueAt(i, COL_MASTER_SELECT)) {
                    posSel = i;
                    break;
                }
            }

            // Si no quedan registros seleccionado, se procede a limpiar los campos
            if (posSel < 0) {
                txtNroCompr.setText("");
                txtBenefRazonSocial.setText("");
                txtBenefRifCI.setText("");
                txtConcepto.setText("");
            } else {
                ComprModel reg = (ComprModel) tblMaster.getValueAt(posSel, COL_MASTER_REG_COMPR);

                txtNroCompr.setText(Format.toStr(reg.getId_compromiso()));
                txtBenefRazonSocial.setText(reg.getBenef_razonsocial());
                txtBenefRazonSocial.setCaretPosition(0);
                txtBenefRifCI.setText(reg.getBenef_rif_ci());
                txtConcepto.setText(reg.getObservacion());
            }

            // Borrar tabla de detalles
            mostrarDetalle(0);
        }
    }

    private void btnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraActionPerformed
        setStartConditions();

        lblOC.setIcon(checkIcon);
        lblOS.setIcon(null);
        lblCO.setIcon(null);
    }//GEN-LAST:event_btnCompraActionPerformed

    /**
     * Rev 21/09/2016
     *
     * @param evt
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        actGuardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Rev 14/11/2016
     *
     * @param inparam
     * @return
     */
    private boolean valCausadoComp(final Map<String, Object> inparam) {

        // Retornar si no hay ordenes
        if (tblMaster.getRowCount() <= 0) {
            return false;
        }

        // Buscar la primera orden seleccionada
        int idx = -1;
        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            if ((Boolean) tblMaster.getValueAt(i, COL_MASTER_SELECT)) {
                idx = i;
                break;
            }
        }

        // Verificar que al hay menos hay una orden seleccionada
        if (idx < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar al menos una (1) orden");
            return false;
        }

        // Verificar la razonsocial
        final String benef_razonSocial = txtBenefRazonSocial.getText();
        if (benef_razonSocial.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Beneficiario inválido");
            java.awt.EventQueue.invokeLater(tblMaster::requestFocusInWindow);
            return false;
        }

        if (benef_razonSocial.charAt(0) == '@') {
            JOptionPane.showMessageDialog(null, "Beneficiario inválido");
            java.awt.EventQueue.invokeLater(tblMaster::requestFocusInWindow);
            return false;
        }

        final String benef_rif_ci = txtBenefRifCI.getText();
        if (benef_rif_ci.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Beneficiario inválido");
            java.awt.EventQueue.invokeLater(tblMaster::requestFocusInWindow);
            return false;
        }

        final double diva_porc_aplic = (double) tblMaster.getValueAt(idx, COL_MASTER_IVA_PORC_APLIC);

        // Verificar que todos los seleccionados tienen el mismo porc. de IVA
        for (int i = idx + 1; i < tblMaster.getRowCount(); i++) {
            if ((Boolean) tblMaster.getValueAt(i, COL_MASTER_SELECT)) {
                if (diva_porc_aplic != (double) tblMaster.getValueAt(i, COL_MASTER_IVA_PORC_APLIC)) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar el mismo Beneficiario, con el mismo porc. de IVA");
                    return false;
                }
            }
        }

        // Verificar el concepto
        final String auxConcepto = txtConcepto.getText().trim().toUpperCase();
        if (auxConcepto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Concepto inválido");
            java.awt.EventQueue.invokeLater(txtConcepto::requestFocusInWindow);
            return false;
        }
        final String observacion = auxConcepto.substring(0, min(auxConcepto.length(), 512));

        final java.sql.Date fecha_causado;
        try {
            fecha_causado = Format.toDateSql(txtFecha.getText().trim());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida" + System.getProperty("line.separator") + inex);
            return false;
        }

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
        inparam.put("tipo_compr", tipo_compr);

        inparam.put("benef_razonsocial", benef_razonSocial);
        inparam.put("benef_rif_ci", benef_rif_ci);
        inparam.put("iva_porc_aplic", Format.toBigDec(diva_porc_aplic));
        inparam.put("observacion", observacion);
        inparam.put("fecha_causado", fecha_causado);

        return true;
    }

    /**
     * Rev 21/09/2016
     *
     * @return
     */
    private void actGuardar() {

        final Map<String, Object> param = new HashMap<>(101);
        if (!valCausadoComp(param)) {
            return;
        }

        try {
            ConnCapip.getInstance().BeginTransaction();

            param.put("id_causado", cauNextNum.nextNum());
            genCausadoMaster(param, tblMaster);
            genCausadoDetail(param, tblMaster);
            cauNextNum.update();

            ConnCapip.getInstance().EndTransaction();
        } catch (final Exception inex) {
            try {
                ConnCapip.getInstance().RollBack();
            } catch (final Exception inex1) {
                JOptionPane.showMessageDialog(null, inex1);
            }

            JOptionPane.showMessageDialog(null, "Error. Al insertar el registro" + System.getProperty("line.separator") + inex);
            return;
        }

        JOptionPane.showMessageDialog(null, "Operación realizada");

        // Reinicializar la ventana
        setStartConditions();

        try {
            // Generar el Reporte
            genReport((long) param.get("id_causado"), param);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error. Al generar el Reporte" + System.getProperty("line.separator") + inex);
        }
    }

    /**
     * Rev 20/09/2016
     *
     * @param inparam
     * @param intblMaster
     * @throws java.lang.Exception
     */
    public static void genCausadoMaster(final Map<String, Object> inparam, final JTable intblMaster) throws Exception {

        final long id_causado = (long) inparam.get("id_causado");

        final java.sql.Date fecha_causado = (java.sql.Date) inparam.get("fecha_causado");

        final TipoCompr tipo_compr = (TipoCompr) inparam.get("tipo_compr");

        final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("INSERT INTO causado("
            + "id_causado, id_user, id_session, " // 1, 2, 3
            + "date_session, ejefis, fecha_causado,  tipo_compr, " // 4, 5, 6, 7
            + "benef_razonsocial, benef_rif_ci, " // 8, 9
            + "observacion, resta_bs, " // 10, 11, 12
            + "iva_ret_sn, islr_ret_sn) " // 13, 14
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.setLong(1, id_causado); // id_causado
        pst.setLong(2, UserPassIn.getIdUser()); // id_user
        pst.setLong(3, UserPassIn.getIdSession()); // id_session
        pst.setTimestamp(4, UserPassIn.getDateSession());
        pst.setDate(5, Globales.getEjefis()); // ejefis
        pst.setDate(6, fecha_causado);
        pst.setString(7, tipo_compr.name());
        pst.setString(8, (String) inparam.get("benef_razonsocial"));
        pst.setString(9, (String) inparam.get("benef_rif_ci"));
        pst.setString(10, (String) inparam.get("observacion"));
        pst.setBigDecimal(11, BigDecimal.ZERO); // resta_bs
        pst.setString(12, "N"); // iva_retenido_sn
        pst.setString(13, "N"); // islr_retenido_sn
        if (pst.executeUpdate() != 1) {
            throw new Exception("Eror al tratar de insertar el registro");
        }

        // Actualizar el numero de causado en las ordenes de compromiso
        final Connection cn = ConnCapip.getInstance().getConnection();
        if ( intblMaster != null) {
            for (int i = 0; i < intblMaster.getRowCount(); i++) {
                if ((Boolean) intblMaster.getValueAt(i, COL_MASTER_SELECT)) { // Solo las seleccionadas
                    if (cn.prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET id_causado= " + id_causado + " WHERE id_compr = " + (Long) intblMaster.getValueAt(i, COL_MASTER_ID_COMPR)).executeUpdate() != 1) {
                        throw new Exception("Error al actualizar el causado");
                    }
                }
            }
        } else {
            if (cn.prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET id_causado= " + id_causado + " WHERE id_compr= " + (long) inparam.get("id_compr")).executeUpdate() != 1) {
                throw new Exception("Error al actualizar el causado");
            }
        }
    }

    /**
     *
     * Rev 09/10/2016
     *
     * @param inparam
     * @param intblMaster
     * @throws java.lang.Exception
     */
    public static void genCausadoDetail(final Map<String, Object> inparam, final JTable intblMaster) throws Exception {

        final Connection cn = ConnCapip.getInstance().getConnection();
        final long id_causado = (long) inparam.get("id_causado");

        final CauModel regCau = CauModel.getxID(id_causado);
        if (regCau == null) {
            throw new Exception("Causado no encontrado");
        }

        // Para todas los compromisos seleccionado
        if ( intblMaster != null) {
            for (int i = 0; i < intblMaster.getRowCount(); i++) {
                if ((Boolean) intblMaster.getValueAt(i, COL_MASTER_SELECT)) { // Solo las seleccionadas
                    final ComprModel regCompr = (ComprModel) intblMaster.getValueAt(i, COL_MASTER_REG_COMPR);
                    final long id_compr = regCompr.getId_compromiso();

                    // Buscar todas las partidas asociadas al compromiso
                    final ResultSet rs = cn.prepareStatement("SELECT * FROM compr_det WHERE id_compr= " + id_compr + " AND tipo_compr= '" + regCau.getTipo_compr().name() + "'").executeQuery();

                    // Para todas las partidas asociadas al compromiso
                    while (rs.next()) {

                        ComprDetModel regComprDet = new ComprDetModel(rs);
                        final String codpresu = regComprDet.getCodPresu();

                        // Verificar si este codpresu ya ha sido insertado
                        final ResultSet rsAux = cn.prepareStatement("SELECT * FROM causado_det WHERE id_causado= " + id_causado + " AND codpresu= '" + codpresu + "'").executeQuery();

                        // Ya existe por lo que se actualiza el subtotal
                        if (rsAux.next()) {
                            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE causado_det SET subtotal= subtotal + ? WHERE id_causado= ? AND codpresu= ?");
                            pst.setBigDecimal(1, regComprDet.getCantPro().multiply(regComprDet.getPUnitario()).setScale(2, RoundingMode.HALF_UP));
                            pst.setLong(2, id_causado);
                            pst.setString(3, codpresu);
                            if (pst.executeUpdate() != 1) {
                                throw new Exception("Error al actualizar el Causado detalle");
                            }

                        } else {

                            final PreparedStatement pst = cn.prepareStatement("INSERT INTO causado_det"
                                + "(id_causado, id_user, id_session, date_session, " // 1, 2, 3, 4
                                + "subtotal, codpresu, partida) " // 5, 6, 7
                                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                            pst.setLong(1, id_causado);
                            pst.setLong(2, UserPassIn.getIdUser());
                            pst.setLong(3, UserPassIn.getIdSession());
                            pst.setTimestamp(4, UserPassIn.getDateSession());
                            pst.setBigDecimal(5, regComprDet.getCantPro().multiply(regComprDet.getPUnitario()).setScale(2, RoundingMode.HALF_UP));
                            pst.setString(6, rs.getString("codpresu"));
                            pst.setString(7, rs.getString("partida"));
                            if (pst.executeUpdate() != 1) {
                                throw new Exception("Error al insertar el Causado detalle");
                            }
                        }
                    }
                }

            } // for (int i = 0; i < tblMaster.getRowCount(); i++) 
        } else {
            // Een el pago directo, no hay tabla Master
            final ComprModel regCompr = ComprModel.getxID((long) inparam.get("id_compr"), (TipoCompr) inparam.get("tipo_compr"));
            final long id_compr = regCompr.getId_compromiso();

            // Buscar todas las partidas asociadas al compromiso
            final ResultSet rs = cn.prepareStatement("SELECT * FROM compr_det WHERE id_compr= " + id_compr + " AND tipo_compr= '" + regCau.getTipo_compr().name() + "'").executeQuery();

            // Para todas las partidas asociadas al compromiso
            while (rs.next()) {

                ComprDetModel regComprDet = new ComprDetModel(rs);
                final String codpresu = regComprDet.getCodPresu();

                // Verificar si este codpresu ya ha sido insertado
                final ResultSet rsAux = cn.prepareStatement("SELECT * FROM causado_det WHERE id_causado= " + id_causado + " AND codpresu= '" + codpresu + "'").executeQuery();

                // Ya existe por lo que se actualiza el subtotal
                if (rsAux.next()) {
                    final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE causado_det SET subtotal= subtotal + ? WHERE id_causado= ? AND codpresu= ?");
                    pst.setBigDecimal(1, regComprDet.getCantPro().multiply(regComprDet.getPUnitario()).setScale(2, RoundingMode.HALF_UP));
                    pst.setLong(2, id_causado);
                    pst.setString(3, codpresu);
                    if (pst.executeUpdate() != 1) {
                        throw new Exception("Error al actualizar el Causado detalle");
                    }

                } else {

                    final PreparedStatement pst = cn.prepareStatement("INSERT INTO causado_det"
                        + "(id_causado, id_user, id_session, date_session, " // 1, 2, 3, 4
                        + "subtotal, codpresu, partida) " // 5, 6, 7
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                    pst.setLong(1, id_causado);
                    pst.setLong(2, UserPassIn.getIdUser());
                    pst.setLong(3, UserPassIn.getIdSession());
                    pst.setTimestamp(4, UserPassIn.getDateSession());
                    pst.setBigDecimal(5, regComprDet.getCantPro().multiply(regComprDet.getPUnitario()).setScale(2, RoundingMode.HALF_UP));
                    pst.setString(6, rs.getString("codpresu"));
                    pst.setString(7, rs.getString("partida"));
                    if (pst.executeUpdate() != 1) {
                        throw new Exception("Error al insertar el Causado detalle");
                    }
                }
            }
        }

        // Actualizar los totales
        final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE causado SET resta_bs= ? WHERE id_causado= ?");
        pst.setBigDecimal(1, regCau.getTotal_bs());
        pst.setLong(2, id_causado);
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error al actualizar el Causado");
        }

        // Verificar si tiene IVA aplicable
        final BigDecimal iva_bs;
        final PreparedStatement pstIva = ConnCapip.getInstance().getConnection().prepareStatement("SELECT ROUND(SUM(iva_grav_bs * iva_porc_aplic / 100.00), 2) FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= ?");
        pstIva.setLong(1, id_causado);
        try (ResultSet rsIva = pstIva.executeQuery()) {
            if (rsIva.next()) {
                iva_bs = rsIva.getBigDecimal(1);
            } else {
                throw new Exception("Error al verificar el IVA");
            }
        }

        if (iva_bs.compareTo(BigDecimal.ZERO) <= 0) {
            if (cn.prepareStatement("UPDATE causado SET iva_ret_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                throw new Exception("Error al actualizar el Causado");
            }
        }

        // Verificar si tiene ISLR aplicable
        final BigDecimal islr_bs;
        final PreparedStatement pstIslr = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(islr_grav_bs) FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= ?");
        pstIslr.setLong(1, id_causado);
        try (ResultSet rsIslr = pstIslr.executeQuery()) {
            if (rsIslr.next()) {
                islr_bs = rsIslr.getBigDecimal(1);
            } else {
                throw new Exception("Error al verificar el ISLR");
            }
        }

        if (islr_bs.compareTo(BigDecimal.ZERO) <= 0) {
            if (cn.prepareStatement("UPDATE causado SET islr_ret_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                throw new Exception("Error al actualizar el Causado");
            }
        }

    }

    /**
     * Rev 05/09/2016
     *
     * @param inid
     * @param inparam
     * @return
     * @throws Exception
     */
    public static CauModel genReport(final long inid, Map<String, Object> inparam) throws Exception {

        final CauModel reg = CauModel.getxID( inid);
        if (reg == null) {
            return null;
        }

        final BenefModel benef = BenefModel.getxRifCi(reg.getBenef_rif_ci());
        if (benef == null) {
            return null;
        }

        final Map<String, Object> param = new HashMap<>(101);
        param.put("benef_domicilio", benef.getDomicilio());
        param.put("benef_razon_social", benef.getRazonsocial());
        param.put("benef_rif_ci", benef.getRif_ci());
        param.put("benef_telefonos", benef.getTelefonos());
        param.put("date_session", UserPassIn.getDateSession());
        param.put("ejefis", Globales.getEjeFisYear());
        param.put("fechacausado", Format.toStr(reg.getFecha_causado()));
        param.put("id_causado", reg.getId_causado());
        param.put("id_user", UserPassIn.getIdUser());
        param.put("id_session", UserPassIn.getIdSession());

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

        // Hallar los compromisos asociados al causado
        StringBuilder str = null;
        final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM " + reg.getTipo_compr().getTbl() + " WHERE id_causado= " + reg.getId_causado());
        while (rs.next()) {
            ComprModel regAux = new ComprModel(rs);
            if (str == null) {
                str = new StringBuilder(String.valueOf(regAux.getId_compromiso()));
            } else {
                str.append(", ").append(String.valueOf(regAux.getId_compromiso()));
            }
        }

        final String numcomprs;
        if (str == null) {
            numcomprs = "";
        } else {
            numcomprs = str.toString();
        }

        param.put("numcomprs", numcomprs);

        final String NumLiteral = Num2Let.convert(reg.getTotal_bs());

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

        param.put("Numorden", reg.getId_causado());
        param.put("observacion", reg.getObs());
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("tipo_compr", reg.getTipo_compr().name().toUpperCase());
        param.put("anulado", reg.getAnulado_sn().equals("S") ? "ANULADO" : "");
        param.put("titulo", "GASTO CAUSADO");
        param.put("user", UserPassIn.getUserName());

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/causado.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), ConnCapip.getInstance().getConnection()), false);
                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de causado no encontrado");
        }

        return reg;
    }

    private void btnServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServicioActionPerformed
        setStartConditions();

        lblOC.setIcon(null);
        lblOS.setIcon(checkIcon);
        lblCO.setIcon(null);
    }//GEN-LAST:event_btnServicioActionPerformed

    private void btnOtrosComprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtrosComprActionPerformed
        setStartConditions();

        lblOC.setIcon(null);
        lblOS.setIcon(null);
        lblCO.setIcon(checkIcon);
    }//GEN-LAST:event_btnOtrosComprActionPerformed

    /**
     * Rev 05/01/2017
     *
     */
    private void actAnular() {

        if (JOptionPane.showConfirmDialog(this, "La operación de anulación de Causado es irreversible. ¿ Desea continuar ?",
            "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }

        String auxId_causado = JOptionPane.showInputDialog(this, "Indique el número de Orden");

        // Verificar ID de la orden
        if (auxId_causado == null) {
            return;
        }

        final long id_causado;
        try {
            id_causado = Format.toLong(auxId_causado.trim());
        } catch (final Exception ex) {
            return;
        }

        // Verificar ID de la orden
        if (id_causado <= 0) {
            return;
        }

        TipoCompr tipo_compr;
        try {
            // Verificar que la orden existe
            ResultSet rsOrd = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado WHERE id_causado= " + id_causado).executeQuery();

            // Si no existe se aborta
            if (rsOrd.next()) {
                CauModel regCau = new CauModel(rsOrd);

                final BigDecimal total_bs = regCau.getTotal_bs();
                final BigDecimal resta_bs = regCau.getResta_bs();
                final String is_iva_retenido = regCau.getIva_ret_sn();
                final String is_islr_retenido = regCau.getIslr_ret_sn();

                final String is_imp_mun_retenido = regCau.getImp_mun_ret_sn();
                final String is_neg_pri_retenido = regCau.getNeg_pri_ret_sn();

                final String is_oret_retenido = regCau.getOret_ret_sn();

                tipo_compr = regCau.getTipo_compr();

                if (resta_bs.compareTo(BigDecimal.ZERO) <= 0) {
                    JOptionPane.showMessageDialog(null, "No se puede reversar. Orden Pagada");
                    return;
                }

                if (resta_bs.compareTo(total_bs) != 0) {
                    JOptionPane.showMessageDialog(null, "No se puede reversar. Orden en proceso de pago");
                    return;
                }

                if (is_iva_retenido.equals("S")) {

                    // Verificar si la orden tiene gravable > 0
                    ResultSet rsIvaBs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(iva_grav_bs) FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= " + id_causado).executeQuery();
                    if (rsIvaBs.next()) {

                        if (rsIvaBs.getDouble(1) > 0) {
                            JOptionPane.showMessageDialog(null, "No se puede reversar. Orden con IVA retenido");
                            return;

                        }
                    }
                }

                if (is_islr_retenido.equals("S")) {

                    // Verificar si la orden tiene gravable > 0
                    ResultSet rsIslrBs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(islr_grav_bs) FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= " + id_causado).executeQuery();
                    if (rsIslrBs.next()) {

                        if (rsIslrBs.getDouble(1) > 0) {
                            JOptionPane.showMessageDialog(null, "No se puede reversar. Orden con ISLR retenido");
                            return;
                        }
                    }
                }

                if (is_imp_mun_retenido.equals("S")) {

                    // Verificar si la orden tiene gravable > 0
                    ResultSet rsImpMun = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(imp_mun_grav_bs) FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= " + id_causado).executeQuery();
                    if (rsImpMun.next()) {

                        if (rsImpMun.getDouble(1) > 0) {
                            JOptionPane.showMessageDialog(null, "No se puede reversar. Orden con Imp. Mun. retenido");
                            return;
                        }
                    }
                }

                if (is_neg_pri_retenido.equals("S")) {

                    // Verificar si la orden tiene gravable > 0
                    ResultSet rsNegPri = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(neg_pri_grav_bs) FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= " + id_causado).executeQuery();
                    if (rsNegPri.next()) {

                        if (rsNegPri.getDouble(1) > 0) {
                            JOptionPane.showMessageDialog(null, "No se puede reversar. Orden con Imp. Negro Primero retenido");
                            return;
                        }
                    }
                }

                if (is_oret_retenido.equals("S")) {

                    // Verificar si la orden tiene gravable > 0
                    ResultSet rsORet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT SUM(oret_grav_bs) FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= " + id_causado).executeQuery();
                    if (rsORet.next()) {

                        if (rsORet.getDouble(1) > 0) {
                            JOptionPane.showMessageDialog(null, "No se puede reversar. Orden con Otras Ret. retenido");
                            return;
                        }
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Orden #" + id_causado + " no encontrada");
                return;
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar la orden" + System.getProperty("line.separator") + inex);
            return;
        }

        // Orden Causada
        //
        if (JOptionPane.showConfirmDialog(this, "¿Seguro desea Anular la Orden #" + id_causado + " ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
            try {
                ConnCapip.getInstance().BeginTransaction();

                if (ConnCapip.getInstance().getConnection().prepareStatement("UPDATE causado SET anulado_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                    throw new Exception("Error al tratar de eliminar el Causado");
                }

//                if (ConnCapip.getInstance().getConnection().prepareStatement("DELETE FROM causado_det WHERE id_causado=" + id_causado).executeUpdate() <= 0) {
//                    throw new Exception("Error al tratar de eliminar el Causado");
//                }
                if (ConnCapip.getInstance().getConnection().prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET id_causado= 0 WHERE id_causado = " + id_causado).executeUpdate() <= 0) {
                    throw new Exception("Error al tratar de eliminar el Causado");
                }

                ConnCapip.getInstance().EndTransaction();
            } catch (final Exception inex) {
                try {
                    ConnCapip.getInstance().RollBack();
                } catch (final Exception inex1) {
                    JOptionPane.showMessageDialog(null, inex1);
                }
                JOptionPane.showMessageDialog(null, "Error al finalizar la operación: " + System.getProperty("line.separator") + inex);
            } finally {
                UpdateTblCompr();
            }

        } //  if (JOptionPane.showConfirmDialog(this, "¿Seguro desea Reversar la Orden #" + id_causado + " ?") == JOptionPane.OK_OPTION) {
    }

    /**
     * Rev 22/09/2016
     *
     * @param evt
     */
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        new CausadoConsultar(this).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnConsultarActionPerformed

    /**
     * Rev 22/09/2016
     *
     * @param evt
     */
    private void txtConceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConceptoKeyTyped
        if (txtConcepto.getText().length() >= 512) {
            evt.consume();
        }
    }//GEN-LAST:event_txtConceptoKeyTyped

    /**
     * Rev 05/01/2017
     *
     * @param evt
     */
    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        try {
            final String fileName = "RptCausado " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblMaster, fileName + ".xls");
            TableToExcel.exportTSV(tblMaster, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        actAnular();
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnReversarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReversarActionPerformed
        actAnular();
    }//GEN-LAST:event_btnReversarActionPerformed

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
                new Causado(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JToggleButton btnCompra;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JToggleButton btnOtrosCompr;
    private javax.swing.JButton btnReversar;
    private javax.swing.JToggleButton btnServicio;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCO;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblOC;
    private javax.swing.JLabel lblOS;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblMaster;
    private javax.swing.JTextField txtBenefRazonSocial;
    private javax.swing.JTextField txtBenefRifCI;
    private javax.swing.JTextArea txtConcepto;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JFormattedTextField txtNroCausado;
    private javax.swing.JFormattedTextField txtNroCompr;
    private javax.swing.JFormattedTextField txtTotalBs;
    // End of variables declaration//GEN-END:variables

}
