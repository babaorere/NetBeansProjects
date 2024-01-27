package impuestos;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static java.math.RoundingMode.HALF_UP;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatter;
import modelos.CauModel;
import modelos.ComprModel;
import modelos.ImpMunModel;
import modelos.ImpMun_DetModel;
import modelos.IslrRetencionDetModel;
import modelos.IslrRetencionModel;
import modelos.IvaRetencionDetModel;
import modelos.IvaRetencionModel;
import modelos.NegPriModel;
import modelos.NegPri_DetModel;
import modelos.OtrasRet_DetModel;
import modelos.OtrasRet_Model;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.DateCellRenderer;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;
import utils.SpinnerEditor;
import utils.TipoCompr;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class ImpuestoRetencion extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private final java.awt.Window parent;

    private static final BigDecimal dec75 = BigDecimal.valueOf(0.75d);
    private static final BigDecimal dec100 = BigDecimal.valueOf(1.00d);
    private static final BigDecimal[] porcRet = {BigDecimal.valueOf(0.00d),
        BigDecimal.valueOf(1.00d),
        BigDecimal.valueOf(2.00d),
        BigDecimal.valueOf(3.00d),
        BigDecimal.valueOf(4.00d),
        BigDecimal.valueOf(5.00d),
        BigDecimal.valueOf(6.00d)};

    private static final Double[] cmbItems = {0.00d, 1.00d, 2.00d, 3.00d, 4.00d, 5.00d, 6.00d};

    private final int IVACOL_ID_CAUSADO = 0;
    private final int IVACOL_FECHA_CAUSADO = 1;
    private final int IVACOL_RIF_CI = 2;
    private final int IVACOL_BENEF_RAZONSOCIAL = 3;
    private final int IVACOL_TOTAL_BS = 4;
    private final int IVACOL_BASE_IMPONIBLE = 5;
    private final int IVACOL_IVA_GRAV_BS = 6;
    private final int IVACOL_IVA_BS = 7;
    private final int IVACOL_EXENTO_BS = 8;
    private final int IVACOL_OBS = 9;
    private final int IVACOL_REG = 10;

    private final int IVACOLDET_ID_CAUSADO = 0;
    private final int IVACOLDET_FECHA_FACT = 1;
    private final int IVACOLDET_NUM_FACT = 2;
    private final int IVACOLDET_TIPO_COMPR = 3;
    private final int IVACOLDET_NUM_COMPR = 4;
    private final int IVACOLDET_NDEBITO = 5;
    private final int IVACOLDET_NCREDITO = 6;
    private final int IVACOLDET_TRANSACCION = 7;
    private final int IVACOLDET_FACTURA_AFT = 8;
    private final int IVACOLDET_TOTAL = 9;
    private final int IVACOLDET_EXENTO = 10;
    private final int IVACOLDET_BASE_IMPONIBLE = 11;
    private final int IVACOLDET_ALICUOTA = 12;
    private final int IVACOLDET_IVA_BS = 13;
    private final int IVACOLDET_IVA_RETENIDO = 14;
    private final int IVACOLDET_OBS = 15;
    private final int IVACOLDET_REG = 16;

    private final int ISLRCOL_ID_CAUSADO = 0;
    private final int ISLRCOL_FECHA_CAUSADO = 1;
    private final int ISLRCOL_RIF_CI = 2;
    private final int ISLRCOL_BENEF_RAZONSOCIAL = 3;
    private final int ISLRCOL_TOTAL_BS = 4;
    private final int ISLRCOL_BASE_IMPONIBLE = 5;
    private final int ISLRCOL_GRAV_BS = 6;
    private final int ISLRCOL_OBS = 7;
    private final int ISLRCOL_REG = 8;

    private final int ISLRCOLDET_ID_CAUSADO = 0;
    private final int ISLRCOLDET_FECHA_FACT = 1;
    private final int ISLRCOLDET_NUM_FACT = 2;
    private final int ISLRCOLDET_TIPO_COMPR = 3;
    private final int ISLRCOLDET_NUM_COMPR = 4;
    private final int ISLRCOLDET_TOTAL = 5;
    private final int ISLRCOLDET_BASE_IMPONIBLE = 6;
    private final int ISLRCOLDET_GRAVABLE = 7;
    private final int ISLRCOLDET_PORC_RETENCION = 8;
    private final int ISLRCOLDET_RETENSION_BS = 9;
    private final int ISLRCOLDET_OBS = 10;
    private final int ISLRCOLDET_REG = 11;

    private final int ORETCOL_ID_CAUSADO = 0;
    private final int ORETCOL_FECHA_CAUSADO = 1;
    private final int ORETCOL_RIF_CI = 2;
    private final int ORETCOL_BENEF_RAZONSOCIAL = 3;
    private final int ORETCOL_TOTAL_BS = 4;
    private final int ORETCOL_BASE_IMPONIBLE = 5;
    private final int ORETCOL_IVA_BS = 6;
    private final int ORETCOL_OBS = 7;
    private final int ORETCOL_REG = 8;

    private final int ORETCOLDET_ID_CAUSADO = 0;
    private final int ORETCOLDET_FECHA_FACT = 1;
    private final int ORETCOLDET_NUM_FACT = 2;
    private final int ORETCOLDET_TIPO_COMPR = 3;
    private final int ORETCOLDET_NUM_COMPR = 4;
    private final int ORETCOLDET_TOTAL = 5;
    private final int ORETCOLDET_BASE_IMPONIBLE = 6;
    private final int ORETCOLDET_GRAVABLE = 7;
    private final int ORETCOLDET_RETENIDO_BS = 8;
    private final int ORETCOLDET_OBS = 9;
    private final int ORETCOLDET_REG = 10;

    private String ivaBenef_razonsocial;
    private String ivaBenef_rif_ci;

    private String islrBenef_razonsocial;
    private String islrBenef_rif_ci;

    private String impMunBenef_razonsocial;
    private String impMunBenef_rif_ci;

    private String negPriBenef_razonsocial;
    private String negPriBenef_rif_ci;

    private String oRetBenef_razonsocial;
    private String oRetBenef_rif_ci;

    private BigDecimal iva_porc_ret;

    /**
     * Creates new form
     *
     * @param _parent
     */
    public ImpuestoRetencion(final java.awt.Window _parent) {
        super();
        initComponents();

        parent = _parent;

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada Rev 25/09/2016
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
     * Establece el comportamiento de la presente Ventana Rev 10/10/2016
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
        setTblIvaMasterBehavior();
        setTblIvaDetailBehavior();

        setTblIslrMasterBehavior();
        setTblIslrDetailBehavior();

        setTblImpMunMasterBehavior();
        setTblImpMunDetailBehavior();

        setTblNegPriMasterBehavior();
        setTblNegPriDetailBehavior();

        setTblORetMasterBehavior();
        setTblORetDetailBehavior();
    }

    /**
     * Rev 10/10/2016
     */
    private void setTblIvaMasterBehavior() {

        // Configurar la JTable, para manejar el ENTER
        final InputMap imBenef = tblIvaMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap amBenef = tblIvaMaster.getActionMap();

        final KeyStroke enterKeyBenef = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        imBenef.put(enterKeyBenef, "Action.enter");
        amBenef.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                IvaInsertarDetalle();
            }
        });

        tblIvaMaster.getColumnModel().getColumn(IVACOL_FECHA_CAUSADO).setCellRenderer(new DateCellRenderer());

        tblIvaMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblIvaMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblIvaMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 10/10/2016
     */
    private void setTblIvaDetailBehavior() {

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tblIvaDetail.getColumnModel().getColumn(1).setCellRenderer(new DateCellRenderer());

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblIvaDetail.getColumnModel().getColumn(2).setCellRenderer(tcr); // Num. Fact

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblIvaDetail.getColumnModel().getColumn(3).setCellRenderer(tcr); // Num. Control

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblIvaDetail.getColumnModel().getColumn(6).setCellRenderer(tcr); // Transaccion
        tblIvaDetail.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblIvaDetail.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblIvaDetail.setDefaultRenderer(Double.class, new DecimalCellRenderer());

    }

    /**
     * Rev 21/10/2016
     */
    private void setTblIslrMasterBehavior() {

        // Configurar la JTable, para manejar el ENTER
        final InputMap imBenef = tblIslrMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap amBenef = tblIslrMaster.getActionMap();

        final KeyStroke enterKeyBenef = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        imBenef.put(enterKeyBenef, "Action.enter");
        amBenef.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                IslrInsertarDetalle();
            }
        });

        tblIslrMaster.getColumnModel().getColumn(ISLRCOL_FECHA_CAUSADO).setCellRenderer(new DateCellRenderer());

        tblIslrMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblIslrMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblIslrMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 21/10/2016
     */
    private void setTblIslrDetailBehavior() {

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tblIslrDetail.getColumnModel().getColumn(ISLRCOLDET_FECHA_FACT).setCellRenderer(new DateCellRenderer());

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblIslrDetail.getColumnModel().getColumn(ISLRCOLDET_NUM_FACT).setCellRenderer(tcr); // Num. Fact

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblIslrDetail.getColumnModel().getColumn(ISLRCOLDET_TIPO_COMPR).setCellRenderer(tcr); // Num. Control

        tblIslrDetail.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblIslrDetail.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblIslrDetail.setDefaultRenderer(Double.class, new DecimalCellRenderer());

        // Configurar un combo box, como editor de la columna de porcentaje
        final JComboBox<Double> cmb = new JComboBox<>(cmbItems);
        final DefaultCellEditor de = new DefaultCellEditor(cmb);
        tblIslrDetail.getColumnModel().getColumn(ISLRCOLDET_PORC_RETENCION).setCellEditor(de);

        // Añadirle al combo box, un listerned para detectar algun posible cambio
        de.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent _e) {
                final int selRow = tblIslrDetail.getSelectedRow();
                if (selRow >= 0) {
                    tblIslrDetail.setValueAt(BigDecimal.valueOf((double) tblIslrDetail.getValueAt(selRow, ISLRCOLDET_GRAVABLE) * (double) tblIslrDetail.getValueAt(selRow, ISLRCOLDET_PORC_RETENCION)).movePointLeft(2).setScale(2, HALF_UP).doubleValue(), selRow, ISLRCOLDET_RETENSION_BS);
                    calcularIslrTotal();
                }
            }

            @Override
            public void editingCanceled(ChangeEvent _e) {
            }

        });

        TableColumn colSp = tblIslrDetail.getColumnModel().getColumn(ISLRCOLDET_GRAVABLE);
        final SpinnerEditor se = new SpinnerEditor();
        colSp.setCellEditor(se);

        se.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent _e) {
                final int selRow = tblIslrDetail.getSelectedRow();
                if (selRow >= 0) {
                    double base_imponible = (double) tblIslrDetail.getValueAt(selRow, ISLRCOLDET_BASE_IMPONIBLE);
                    double gravable = (double) tblIslrDetail.getValueAt(selRow, ISLRCOLDET_GRAVABLE);

                    if ((gravable >= 0) && (gravable <= base_imponible)) {
                        tblIslrDetail.setValueAt(BigDecimal.valueOf(gravable * (double) tblIslrDetail.getValueAt(selRow, ISLRCOLDET_PORC_RETENCION)).movePointLeft(2).setScale(2, HALF_UP).doubleValue(), selRow, ISLRCOLDET_RETENSION_BS);
                    } else {
                        tblIslrDetail.setValueAt(base_imponible, selRow, ISLRCOLDET_GRAVABLE);
                    }
                    calcularIslrTotal();
                }
            }

            @Override
            public void editingCanceled(ChangeEvent _e) {
            }
        });

    }

    /**
     * Rev 14/04/2017
     */
    private void setTblImpMunMasterBehavior() {

        // Configurar la JTable, para manejar el ENTER
        final InputMap imBenef = tblImpMunMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap amBenef = tblImpMunMaster.getActionMap();

        final KeyStroke enterKeyBenef = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        imBenef.put(enterKeyBenef, "Action.enter");
        amBenef.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                ImpMunInsertarDetalle();
            }
        });

        tblImpMunMaster.getColumnModel().getColumn(ORETCOL_FECHA_CAUSADO).setCellRenderer(new DateCellRenderer());

        tblImpMunMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblImpMunMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblImpMunMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 14/04/2017
     */
    private void setTblNegPriMasterBehavior() {

        // Configurar la JTable, para manejar el ENTER
        final InputMap imBenef = tblNegPriMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap amBenef = tblNegPriMaster.getActionMap();

        final KeyStroke enterKeyBenef = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        imBenef.put(enterKeyBenef, "Action.enter");
        amBenef.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                NegPriInsertarDetalle();
            }
        });

        tblNegPriMaster.getColumnModel().getColumn(ORETCOL_FECHA_CAUSADO).setCellRenderer(new DateCellRenderer());

        tblNegPriMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblNegPriMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblNegPriMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 14/04/2017
     */
    private void setTblImpMunDetailBehavior() {

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tblImpMunDetail.getColumnModel().getColumn(ORETCOLDET_FECHA_FACT).setCellRenderer(new DateCellRenderer());

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblImpMunDetail.getColumnModel().getColumn(ORETCOLDET_NUM_FACT).setCellRenderer(tcr); // Num. Fact

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblImpMunDetail.getColumnModel().getColumn(ORETCOLDET_TIPO_COMPR).setCellRenderer(tcr); // Num. Control

        tblImpMunDetail.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblImpMunDetail.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblImpMunDetail.setDefaultRenderer(Double.class, new DecimalCellRenderer());

        // Configurar la celda del monto Gravable
        final SpinnerEditor speGrav = new SpinnerEditor();
        tblImpMunDetail.getColumnModel().getColumn(ORETCOLDET_GRAVABLE).setCellEditor(speGrav);

        final JSpinner spinnerGrav = speGrav.getSpinner();
        ((DefaultFormatter) ((JFormattedTextField) spinnerGrav.getEditor().getComponent(0)).getFormatter()).setCommitsOnValidEdit(true);

        spinnerGrav.addChangeListener((ChangeEvent _e) -> {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    calcularImpMunTotal();
                }
            });
        });

        speGrav.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent _e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        calcularImpMunTotal();
                    }
                });
            }

            @Override
            public void editingCanceled(ChangeEvent _e) {
            }
        });

        // Configurar la celda del monto Retenido
        final SpinnerEditor speRet = new SpinnerEditor();
        tblImpMunDetail.getColumnModel().getColumn(ORETCOLDET_RETENIDO_BS).setCellEditor(speRet);

        final JSpinner spinnerRet = speRet.getSpinner();
        ((DefaultFormatter) ((JFormattedTextField) spinnerRet.getEditor().getComponent(0)).getFormatter()).setCommitsOnValidEdit(true);

        spinnerRet.addChangeListener((ChangeEvent _e) -> {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    calcularImpMunTotal();
                }
            });
        });

        speRet.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent _e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        calcularImpMunTotal();
                    }
                });
            }

            @Override
            public void editingCanceled(ChangeEvent _e) {
            }
        });

    }

    /**
     * Rev 14/04/2017
     */
    private void setTblNegPriDetailBehavior() {

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tblNegPriDetail.getColumnModel().getColumn(ORETCOLDET_FECHA_FACT).setCellRenderer(new DateCellRenderer());

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblNegPriDetail.getColumnModel().getColumn(ORETCOLDET_NUM_FACT).setCellRenderer(tcr); // Num. Fact

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblNegPriDetail.getColumnModel().getColumn(ORETCOLDET_TIPO_COMPR).setCellRenderer(tcr); // Num. Control

        tblNegPriDetail.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblNegPriDetail.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblNegPriDetail.setDefaultRenderer(Double.class, new DecimalCellRenderer());

        // Configurar la celda del monto Gravable
        final SpinnerEditor speGrav = new SpinnerEditor();
        tblNegPriDetail.getColumnModel().getColumn(ORETCOLDET_GRAVABLE).setCellEditor(speGrav);

        final JSpinner spinnerGrav = speGrav.getSpinner();
        ((DefaultFormatter) ((JFormattedTextField) spinnerGrav.getEditor().getComponent(0)).getFormatter()).setCommitsOnValidEdit(true);

        spinnerGrav.addChangeListener((ChangeEvent _e) -> {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    calcularNegPriTotal();
                }
            });
        });

        speGrav.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent _e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        calcularNegPriTotal();
                    }
                });
            }

            @Override
            public void editingCanceled(ChangeEvent _e) {
            }
        });

        // Configurar la celda del monto Retenido
        final SpinnerEditor speRet = new SpinnerEditor();
        tblNegPriDetail.getColumnModel().getColumn(ORETCOLDET_RETENIDO_BS).setCellEditor(speRet);

        final JSpinner spinnerRet = speRet.getSpinner();
        ((DefaultFormatter) ((JFormattedTextField) spinnerRet.getEditor().getComponent(0)).getFormatter()).setCommitsOnValidEdit(true);

        spinnerRet.addChangeListener((ChangeEvent _e) -> {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    calcularNegPriTotal();
                }
            });
        });

        speRet.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent _e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        calcularNegPriTotal();
                    }
                });
            }

            @Override
            public void editingCanceled(ChangeEvent _e) {
            }
        });

    }

    /**
     * Rev 21/10/2016
     */
    private void setTblORetMasterBehavior() {

        // Configurar la JTable, para manejar el ENTER
        final InputMap imBenef = tblORetMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap amBenef = tblORetMaster.getActionMap();

        final KeyStroke enterKeyBenef = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        imBenef.put(enterKeyBenef, "Action.enter");
        amBenef.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                ORetInsertarDetalle();
            }
        });

        tblORetMaster.getColumnModel().getColumn(ORETCOL_FECHA_CAUSADO).setCellRenderer(new DateCellRenderer());

        tblORetMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblORetMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblORetMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 21/10/2016
     */
    private void setTblORetDetailBehavior() {

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tblORetDetail.getColumnModel().getColumn(ORETCOLDET_FECHA_FACT).setCellRenderer(new DateCellRenderer());

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblORetDetail.getColumnModel().getColumn(ORETCOLDET_NUM_FACT).setCellRenderer(tcr); // Num. Fact

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblORetDetail.getColumnModel().getColumn(ORETCOLDET_TIPO_COMPR).setCellRenderer(tcr); // Num. Control

        tblORetDetail.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblORetDetail.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblORetDetail.setDefaultRenderer(Double.class, new DecimalCellRenderer());

        // Configurar la celda del monto Gravable
        final SpinnerEditor speGrav = new SpinnerEditor();
        tblORetDetail.getColumnModel().getColumn(ORETCOLDET_GRAVABLE).setCellEditor(speGrav);

        final JSpinner spinnerGrav = speGrav.getSpinner();
        ((DefaultFormatter) ((JFormattedTextField) spinnerGrav.getEditor().getComponent(0)).getFormatter()).setCommitsOnValidEdit(true);

        spinnerGrav.addChangeListener((ChangeEvent _e) -> {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    calcularORetTotal();
                }
            });
        });

        speGrav.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent _e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        calcularORetTotal();
                    }
                });
            }

            @Override
            public void editingCanceled(ChangeEvent _e) {
            }
        });

        // Configurar la celda del monto Retenido
        final SpinnerEditor speRet = new SpinnerEditor();
        tblORetDetail.getColumnModel().getColumn(ORETCOLDET_RETENIDO_BS).setCellEditor(speRet);

        final JSpinner spinnerRet = speRet.getSpinner();
        ((DefaultFormatter) ((JFormattedTextField) spinnerRet.getEditor().getComponent(0)).getFormatter()).setCommitsOnValidEdit(true);

        spinnerRet.addChangeListener((ChangeEvent _e) -> {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    calcularORetTotal();
                }
            });
        });

        speRet.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent _e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        calcularORetTotal();
                    }
                });
            }

            @Override
            public void editingCanceled(ChangeEvent _e) {
            }
        });

    }

    /**
     * Rev 11/10/2016
     */
    private void setStartConditions() {
        setIvaStartConditions();
        setIslrStartConditions();
        setImpMunStartConditions();
        setNegPriStartConditions();
        setORetStartConditions();
    }

    /**
     * Rev 11/10/2016
     */
    private void setIvaStartConditions() {
//        lblIVA.setText("CALCULAR  %  SOBRE EL " + Format.toStr(IVA_DEC_VALUE.movePointRight(2)) + "% IVA");

        ivaBenef_razonsocial = "";
        ivaBenef_rif_ci = "";
        txtIVAContribuyente.setText(ivaBenef_razonsocial);

        final Timestamp serverDateTime = Globales.getServerTimeStamp();
        txtIvaControl.setText(new SimpleDateFormat("YYYYMM").format(serverDateTime));
        txtIvaFechaHoy.setText(Format.toStr(new java.sql.Date(serverDateTime.getTime())));

        txtIvaRet.setText("0,00");

        iva_porc_ret = BigDecimal.ZERO;
        btnIva75.setEnabled(false);
        btnIva100.setEnabled(false);
        btnIvaRevertir.setEnabled(false);
        btnIvaEliminar.setEnabled(false);
        btnIvaGuardar.setEnabled(false);

        txtIvaNum.setText(checkIvaNextId());

        updateIvaMaster();
    }

    /**
     * Rev 21/10/2016
     */
    private void setIslrStartConditions() {
        cmbIslrPorc.setSelectedIndex(0);

        islrBenef_razonsocial = "";
        islrBenef_rif_ci = "";
        txtIslrContribuyente.setText(islrBenef_razonsocial);

        final Timestamp serverDateTime = Globales.getServerTimeStamp();
        txtIslrControl.setText(new SimpleDateFormat("YYYYMM").format(serverDateTime));
        txtIslrFechaHoy.setText(Format.toStr(new java.sql.Date(serverDateTime.getTime())));

        txtIslrRetenido.setText("0,00");

        cmbIslrPorc.setEnabled(false);
        btnIslrRevertir.setEnabled(false);
        btnIslrEliminar.setEnabled(false);
        btnIslrGuardar.setEnabled(false);

        txtIslrNum.setText(checkIslrNextId());

        updateIslrMaster();
    }

    /**
     * Rev 14/04/2017
     */
    private void setImpMunStartConditions() {

        impMunBenef_razonsocial = "";
        impMunBenef_rif_ci = "";
        txtIVAContribuyente.setText(impMunBenef_razonsocial);

        final Timestamp serverDateTime = Globales.getServerTimeStamp();
        txtImpMunControl.setText(new SimpleDateFormat("YYYYMM").format(serverDateTime));
        txtImpMunFechaHoy.setText(Format.toStr(new java.sql.Date(serverDateTime.getTime())));

        txtImpMunRetenido.setText("0,00");

        btnImpMunRevertir.setEnabled(false);
        btnImpMunEliminar.setEnabled(false);
        btnImpMunGuardar.setEnabled(false);

        txtImpMunNum.setText(checkImpMunNextId());

        updateImpMunMaster();
    }

    /**
     * Rev 14/04/2017
     */
    private void setNegPriStartConditions() {

        negPriBenef_razonsocial = "";
        negPriBenef_rif_ci = "";
        txtNegPriContribuyente.setText(negPriBenef_razonsocial);

        final Timestamp serverDateTime = Globales.getServerTimeStamp();
        txtNegPriControl.setText(new SimpleDateFormat("YYYYMM").format(serverDateTime));
        txtNegPriFechaHoy.setText(Format.toStr(new java.sql.Date(serverDateTime.getTime())));

        txtNegPriRetenido.setText("0,00");

        btnNegPriRevertir.setEnabled(false);
        btnNegPriEliminar.setEnabled(false);
        btnNegPriGuardar.setEnabled(false);

        txtNegPriNum.setText(checkNegPriNextId());

        updateNegPriMaster();
    }

    /**
     * Rev 21/10/2016
     */
    private void setORetStartConditions() {

        oRetBenef_razonsocial = "";
        oRetBenef_rif_ci = "";
        txtORetContribuyente.setText(oRetBenef_razonsocial);

        final Timestamp serverDateTime = Globales.getServerTimeStamp();
        txtORetControl.setText(new SimpleDateFormat("YYYYMM").format(serverDateTime));
        txtORetFechaHoy.setText(Format.toStr(new java.sql.Date(serverDateTime.getTime())));

        txtORetRetenido.setText("0,00");

        btnORetRevertir.setEnabled(false);
        btnORetEliminar.setEnabled(false);
        btnORetGuardar.setEnabled(false);

        txtORetNum.setText(checkORetNextId());

        updateORetMaster();
    }

    /**
     * Rev 11/10/2016
     */
    void updateIvaMaster() {

        // Borrar la tabla de Detalles
        tblIvaDetail.clearSelection();
        final DefaultTableModel modelDet = (DefaultTableModel) tblIvaDetail.getModel();
        modelDet.getDataVector().removeAllElements();
        modelDet.fireTableDataChanged();

        tblIvaMaster.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) tblIvaMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            final Object[] datos = new Object[12];
            final ResultSet rs = Conn.executeQuery("SELECT * FROM causado WHERE iva_ret_sn= 'N' AND YEAR(ejefis)= " + Globales.getEjeFisYear() + " AND anulado_sn= 'N'");
            while (rs.next()) {
                CauModel reg = new CauModel(rs);
                //              ArrayList<ComprModel> listCompr = ComprModel.getxIdCausado(reg.getId_causado(), reg.getTipo_compr());

                datos[IVACOL_ID_CAUSADO] = reg.getId_causado();
                datos[IVACOL_FECHA_CAUSADO] = reg.getFecha_causado();
                datos[IVACOL_RIF_CI] = reg.getBenef_rif_ci();
                datos[IVACOL_BENEF_RAZONSOCIAL] = reg.getBenef_razonsocial();
                datos[IVACOL_TOTAL_BS] = reg.getTotal_bs().doubleValue();

                double base_imponible_bs = reg.getBase_imponible_bs().doubleValue();
                datos[IVACOL_BASE_IMPONIBLE] = base_imponible_bs;

                double iva_grav_bs = reg.getIva_grav_bs().doubleValue();
                datos[IVACOL_IVA_GRAV_BS] = iva_grav_bs;

                datos[IVACOL_IVA_BS] = reg.getIva_bs().doubleValue();

                datos[IVACOL_EXENTO_BS] = base_imponible_bs - iva_grav_bs;
                datos[IVACOL_OBS] = reg.getObs();
                datos[IVACOL_REG] = reg;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * Rev 22/10/2016
     */
    void updateIslrMaster() {

        // Borrar la tabla de Detalles
        tblIslrDetail.clearSelection();
        final DefaultTableModel modelDet = (DefaultTableModel) tblIslrDetail.getModel();
        modelDet.getDataVector().removeAllElements();
        modelDet.fireTableDataChanged();

        tblIslrMaster.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) tblIslrMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            final Object[] datos = new Object[9];
            final ResultSet rs = Conn.executeQuery("SELECT * FROM causado WHERE islr_ret_sn= 'N' AND YEAR(ejefis)= " + Globales.getEjeFisYear() + " AND anulado_sn='N'");
            while (rs.next()) {
                CauModel reg = new CauModel(rs);
                datos[ISLRCOL_ID_CAUSADO] = reg.getId_causado();
                datos[ISLRCOL_FECHA_CAUSADO] = reg.getFecha_causado();
                datos[ISLRCOL_RIF_CI] = reg.getBenef_rif_ci();
                datos[ISLRCOL_BENEF_RAZONSOCIAL] = reg.getBenef_razonsocial();
                datos[ISLRCOL_TOTAL_BS] = reg.getTotal_bs().doubleValue();
                double base_imponible_bs = reg.getBase_imponible_bs().doubleValue();
                datos[ISLRCOL_BASE_IMPONIBLE] = base_imponible_bs;
                double islr_grabv_bs = base_imponible_bs;
                datos[ISLRCOL_GRAV_BS] = islr_grabv_bs;
                datos[ISLRCOL_OBS] = reg.getObs();
                datos[ISLRCOL_REG] = reg;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * Rev 14/04/2017
     */
    void updateImpMunMaster() {

        // Borrar la tabla de Detalles
        tblImpMunDetail.clearSelection();
        final DefaultTableModel modelDet = (DefaultTableModel) tblImpMunDetail.getModel();
        modelDet.getDataVector().removeAllElements();
        modelDet.fireTableDataChanged();

        tblImpMunMaster.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) tblImpMunMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            final Object[] datos = new Object[9];
            PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT * FROM causado "
                + "WHERE imp_mun_ret_sn= 'N' AND YEAR(ejefis)= ? AND anulado_sn= 'N' "
                + "AND id_causado NOT IN (SELECT id_causado FROM causado_orden_pago WHERE id_causado= causado.id_causado AND anulado_sn= 'N') "); // No ha sido pagado
            pst.setLong(1, Globales.getEjeFisYear());

            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CauModel reg = new CauModel(rs);
                datos[ORETCOL_ID_CAUSADO] = reg.getId_causado();
                datos[ORETCOL_FECHA_CAUSADO] = reg.getFecha_causado();
                datos[ORETCOL_RIF_CI] = reg.getBenef_rif_ci();
                datos[ORETCOL_BENEF_RAZONSOCIAL] = reg.getBenef_razonsocial();
                datos[ORETCOL_TOTAL_BS] = reg.getTotal_bs().doubleValue();
                datos[ORETCOL_BASE_IMPONIBLE] = reg.getBase_imponible_bs().doubleValue();
                datos[ORETCOL_IVA_BS] = reg.getIva_bs();
                datos[ORETCOL_OBS] = reg.getObs();
                datos[ORETCOL_REG] = reg;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * Rev 14/04/2017
     */
    void updateNegPriMaster() {

        // Borrar la tabla de Detalles
        tblNegPriDetail.clearSelection();
        final DefaultTableModel modelDet = (DefaultTableModel) tblNegPriDetail.getModel();
        modelDet.getDataVector().removeAllElements();
        modelDet.fireTableDataChanged();

        tblNegPriMaster.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) tblNegPriMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            final Object[] datos = new Object[9];
            PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT * FROM causado "
                + "WHERE neg_pri_ret_sn= 'N' AND YEAR(ejefis)= ? AND anulado_sn= 'N' "
                + "AND id_causado NOT IN (SELECT id_causado FROM causado_orden_pago WHERE id_causado= causado.id_causado AND anulado_sn= 'N') "); // No ha sido pagado
            pst.setLong(1, Globales.getEjeFisYear());

            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CauModel reg = new CauModel(rs);
                datos[ORETCOL_ID_CAUSADO] = reg.getId_causado();
                datos[ORETCOL_FECHA_CAUSADO] = reg.getFecha_causado();
                datos[ORETCOL_RIF_CI] = reg.getBenef_rif_ci();
                datos[ORETCOL_BENEF_RAZONSOCIAL] = reg.getBenef_razonsocial();
                datos[ORETCOL_TOTAL_BS] = reg.getTotal_bs().doubleValue();
                datos[ORETCOL_BASE_IMPONIBLE] = reg.getBase_imponible_bs().doubleValue();
                datos[ORETCOL_IVA_BS] = reg.getIva_bs();
                datos[ORETCOL_OBS] = reg.getObs();
                datos[ORETCOL_REG] = reg;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * Rev 22/10/2016
     */
    void updateORetMaster() {

        // Borrar la tabla de Detalles
        tblORetDetail.clearSelection();
        final DefaultTableModel modelDet = (DefaultTableModel) tblORetDetail.getModel();
        modelDet.getDataVector().removeAllElements();
        modelDet.fireTableDataChanged();

        tblORetMaster.clearSelection();
        final DefaultTableModel model = (DefaultTableModel) tblORetMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            final Object[] datos = new Object[9];
            PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT * FROM causado "
                + "WHERE oret_ret_sn= 'N' AND YEAR(ejefis)= ? AND anulado_sn= 'N' "
                + "AND id_causado NOT IN (SELECT id_causado FROM causado_orden_pago WHERE id_causado= causado.id_causado AND anulado_sn= 'N') "); // No ha sido pagado
            pst.setLong(1, Globales.getEjeFisYear());

            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CauModel reg = new CauModel(rs);
                datos[ORETCOL_ID_CAUSADO] = reg.getId_causado();
                datos[ORETCOL_FECHA_CAUSADO] = reg.getFecha_causado();
                datos[ORETCOL_RIF_CI] = reg.getBenef_rif_ci();
                datos[ORETCOL_BENEF_RAZONSOCIAL] = reg.getBenef_razonsocial();
                datos[ORETCOL_TOTAL_BS] = reg.getTotal_bs().doubleValue();
                datos[ORETCOL_BASE_IMPONIBLE] = reg.getBase_imponible_bs().doubleValue();
                datos[ORETCOL_IVA_BS] = reg.getIva_bs();
                datos[ORETCOL_OBS] = reg.getObs();
                datos[ORETCOL_REG] = reg;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        pnlFondo = new javax.swing.JPanel();
        pnlComponentes = new javax.swing.JPanel();
        pnlTitulo = new javax.swing.JPanel();
        btnIva_Rend = new javax.swing.JButton();
        btnISLR_Rend = new javax.swing.JButton();
        btnImpMun_Rend = new javax.swing.JButton();
        btnNegPri_Rend = new javax.swing.JButton();
        btnRendicionOtras = new javax.swing.JButton();
        tbbPane = new javax.swing.JTabbedPane();
        pnlIva = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtIVAContribuyente = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIvaControl = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtIvaFechaHoy = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtIvaNum = new javax.swing.JFormattedTextField();
        lblIVA = new javax.swing.JLabel();
        btnIva75 = new javax.swing.JButton();
        btnIva100 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIvaMaster = new javax.swing.JTable();
        btnIvaLimpiar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblIvaDetail = new javax.swing.JTable();
        btnIvaGuardar = new javax.swing.JButton();
        btnIvaConsultar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtIvaRet = new javax.swing.JFormattedTextField();
        btnIvaEliminar = new javax.swing.JButton();
        btnIvaRevertir = new javax.swing.JButton();
        btnAnularIVA = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();
        pnlISLR = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtIslrContribuyente = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtIslrControl = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtIslrFechaHoy = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        txtIslrNum = new javax.swing.JFormattedTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblIslrDetail = new javax.swing.JTable() {public void removeEditor() {

            TableCellEditor editor = getCellEditor();
            // must be called here to remove the editor and to avoid an inifinite
            // loop, because the table is an editor listener and the
            // editingCanceled method calls this removeEditor method
            super.removeEditor();
            if (editor != null) {
                editor.cancelCellEditing();
            }}};
            btnIslrLimpiar = new javax.swing.JButton();
            jLabel6 = new javax.swing.JLabel();
            btnIslrGuardar = new javax.swing.JButton();
            btnIslrEliminar = new javax.swing.JButton();
            btnIslrConsultar = new javax.swing.JButton();
            txtIslrRetenido = new javax.swing.JFormattedTextField();
            jLabel5 = new javax.swing.JLabel();
            jScrollPane2 = new javax.swing.JScrollPane();
            tblIslrMaster = new javax.swing.JTable();
            jLabel2 = new javax.swing.JLabel();
            lblIVA1 = new javax.swing.JLabel();
            cmbIslrPorc = new javax.swing.JComboBox();
            btnIslrRevertir = new javax.swing.JButton();
            btnAnularISLR = new javax.swing.JButton();
            lblFondo2 = new javax.swing.JLabel();
            jLabel39 = new javax.swing.JLabel();
            pnlImpMun = new javax.swing.JPanel();
            jLabel21 = new javax.swing.JLabel();
            txtImpMunContribuyente = new javax.swing.JFormattedTextField();
            jLabel22 = new javax.swing.JLabel();
            txtImpMunControl = new javax.swing.JFormattedTextField();
            jLabel23 = new javax.swing.JLabel();
            txtImpMunFechaHoy = new javax.swing.JFormattedTextField();
            jLabel24 = new javax.swing.JLabel();
            txtImpMunNum = new javax.swing.JFormattedTextField();
            jScrollPane7 = new javax.swing.JScrollPane();
            tblImpMunDetail = new javax.swing.JTable() {public void removeEditor() {

                TableCellEditor editor = getCellEditor();
                // must be called here to remove the editor and to avoid an inifinite
                // loop, because the table is an editor listener and the
                // editingCanceled method calls this removeEditor method
                super.removeEditor();
                if (editor != null) {
                    editor.cancelCellEditing();
                }}};
                btnImpMunLimpiar = new javax.swing.JButton();
                jLabel25 = new javax.swing.JLabel();
                btnImpMunGuardar = new javax.swing.JButton();
                btnImpMunEliminar = new javax.swing.JButton();
                btnImpMunConsultar = new javax.swing.JButton();
                txtImpMunRetenido = new javax.swing.JFormattedTextField();
                jLabel26 = new javax.swing.JLabel();
                jScrollPane8 = new javax.swing.JScrollPane();
                tblImpMunMaster = new javax.swing.JTable();
                jLabel27 = new javax.swing.JLabel();
                btnImpMunRevertir = new javax.swing.JButton();
                btnAnularImpMun = new javax.swing.JButton();
                jLabel36 = new javax.swing.JLabel();
                lblFondo4 = new javax.swing.JLabel();
                pnlNegPri = new javax.swing.JPanel();
                jLabel28 = new javax.swing.JLabel();
                txtNegPriContribuyente = new javax.swing.JFormattedTextField();
                jLabel29 = new javax.swing.JLabel();
                txtNegPriControl = new javax.swing.JFormattedTextField();
                jLabel30 = new javax.swing.JLabel();
                txtNegPriFechaHoy = new javax.swing.JFormattedTextField();
                jLabel31 = new javax.swing.JLabel();
                txtNegPriNum = new javax.swing.JFormattedTextField();
                jScrollPane9 = new javax.swing.JScrollPane();
                tblNegPriDetail = new javax.swing.JTable() {public void removeEditor() {

                    TableCellEditor editor = getCellEditor();
                    // must be called here to remove the editor and to avoid an inifinite
                    // loop, because the table is an editor listener and the
                    // editingCanceled method calls this removeEditor method
                    super.removeEditor();
                    if (editor != null) {
                        editor.cancelCellEditing();
                    }}};
                    btnNegPriLimpiar = new javax.swing.JButton();
                    jLabel32 = new javax.swing.JLabel();
                    btnNegPriGuardar = new javax.swing.JButton();
                    btnNegPriEliminar = new javax.swing.JButton();
                    btnNegPriConsultar = new javax.swing.JButton();
                    txtNegPriRetenido = new javax.swing.JFormattedTextField();
                    jLabel33 = new javax.swing.JLabel();
                    jScrollPane10 = new javax.swing.JScrollPane();
                    tblNegPriMaster = new javax.swing.JTable();
                    jLabel34 = new javax.swing.JLabel();
                    btnNegPriRevertir = new javax.swing.JButton();
                    btnAnularNegPri = new javax.swing.JButton();
                    jLabel35 = new javax.swing.JLabel();
                    lblFondo5 = new javax.swing.JLabel();
                    pnlOtras = new javax.swing.JPanel();
                    jLabel14 = new javax.swing.JLabel();
                    txtORetContribuyente = new javax.swing.JFormattedTextField();
                    jLabel10 = new javax.swing.JLabel();
                    txtORetControl = new javax.swing.JFormattedTextField();
                    jLabel15 = new javax.swing.JLabel();
                    txtORetFechaHoy = new javax.swing.JFormattedTextField();
                    jLabel17 = new javax.swing.JLabel();
                    txtORetNum = new javax.swing.JFormattedTextField();
                    jScrollPane5 = new javax.swing.JScrollPane();
                    tblORetDetail = new javax.swing.JTable() {public void removeEditor() {

                        TableCellEditor editor = getCellEditor();
                        // must be called here to remove the editor and to avoid an inifinite
                        // loop, because the table is an editor listener and the
                        // editingCanceled method calls this removeEditor method
                        super.removeEditor();
                        if (editor != null) {
                            editor.cancelCellEditing();
                        }}};
                        btnORetLimpiar = new javax.swing.JButton();
                        jLabel18 = new javax.swing.JLabel();
                        btnORetGuardar = new javax.swing.JButton();
                        btnORetEliminar = new javax.swing.JButton();
                        btnORetConsultar = new javax.swing.JButton();
                        txtORetRetenido = new javax.swing.JFormattedTextField();
                        jLabel19 = new javax.swing.JLabel();
                        jScrollPane6 = new javax.swing.JScrollPane();
                        tblORetMaster = new javax.swing.JTable();
                        jLabel20 = new javax.swing.JLabel();
                        btnORetRevertir = new javax.swing.JButton();
                        btnAnularOtras = new javax.swing.JButton();
                        jLabel37 = new javax.swing.JLabel();
                        lblFondo3 = new javax.swing.JLabel();
                        lblFondo1 = new javax.swing.JLabel();

                        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                        setTitle("RETENCIÓN DE IMPUESTO");
                        setResizable(false);

                        pnlFondo.setMinimumSize(new java.awt.Dimension(1162, 678));
                        pnlFondo.setPreferredSize(new java.awt.Dimension(1162, 678));
                        pnlFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                        pnlComponentes.setOpaque(false);
                        pnlComponentes.setLayout(new javax.swing.BoxLayout(pnlComponentes, javax.swing.BoxLayout.PAGE_AXIS));

                        pnlTitulo.setMaximumSize(new java.awt.Dimension(1200, 50));
                        pnlTitulo.setMinimumSize(new java.awt.Dimension(1200, 50));
                        pnlTitulo.setOpaque(false);
                        pnlTitulo.setPreferredSize(new java.awt.Dimension(1200, 50));
                        pnlTitulo.setLayout(new java.awt.GridBagLayout());

                        btnIva_Rend.setBackground(new java.awt.Color(0, 153, 153));
                        btnIva_Rend.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIva_Rend.setText("RENDICIÓN IVA");
                        btnIva_Rend.setAlignmentX(0.5F);
                        btnIva_Rend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIva_Rend.setMaximumSize(new java.awt.Dimension(160, 30));
                        btnIva_Rend.setMinimumSize(new java.awt.Dimension(160, 30));
                        btnIva_Rend.setPreferredSize(new java.awt.Dimension(160, 30));
                        btnIva_Rend.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIva_RendActionPerformed(evt);
                            }
                        });
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = 0;
                        gridBagConstraints.gridy = 0;
                        gridBagConstraints.ipadx = 20;
                        gridBagConstraints.ipady = 13;
                        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 0);
                        pnlTitulo.add(btnIva_Rend, gridBagConstraints);

                        btnISLR_Rend.setBackground(new java.awt.Color(0, 153, 153));
                        btnISLR_Rend.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnISLR_Rend.setText("RENDICIÓN ISLR");
                        btnISLR_Rend.setAlignmentX(0.5F);
                        btnISLR_Rend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnISLR_Rend.setMaximumSize(new java.awt.Dimension(160, 30));
                        btnISLR_Rend.setMinimumSize(new java.awt.Dimension(160, 30));
                        btnISLR_Rend.setPreferredSize(new java.awt.Dimension(160, 30));
                        btnISLR_Rend.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnISLR_RendActionPerformed(evt);
                            }
                        });
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = 1;
                        gridBagConstraints.gridy = 0;
                        gridBagConstraints.ipadx = 20;
                        gridBagConstraints.ipady = 13;
                        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 0);
                        pnlTitulo.add(btnISLR_Rend, gridBagConstraints);

                        btnImpMun_Rend.setBackground(new java.awt.Color(0, 153, 153));
                        btnImpMun_Rend.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnImpMun_Rend.setText("REND. IMP. MUN.");
                        btnImpMun_Rend.setAlignmentX(0.5F);
                        btnImpMun_Rend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnImpMun_Rend.setMaximumSize(new java.awt.Dimension(170, 30));
                        btnImpMun_Rend.setMinimumSize(new java.awt.Dimension(170, 30));
                        btnImpMun_Rend.setPreferredSize(new java.awt.Dimension(170, 30));
                        btnImpMun_Rend.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnImpMun_RendActionPerformed(evt);
                            }
                        });
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = 2;
                        gridBagConstraints.gridy = 0;
                        gridBagConstraints.ipadx = 20;
                        gridBagConstraints.ipady = 13;
                        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 0);
                        pnlTitulo.add(btnImpMun_Rend, gridBagConstraints);

                        btnNegPri_Rend.setBackground(new java.awt.Color(0, 153, 153));
                        btnNegPri_Rend.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnNegPri_Rend.setText("REND. NEGRO PRI.");
                        btnNegPri_Rend.setAlignmentX(0.5F);
                        btnNegPri_Rend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnNegPri_Rend.setMaximumSize(new java.awt.Dimension(170, 30));
                        btnNegPri_Rend.setMinimumSize(new java.awt.Dimension(170, 30));
                        btnNegPri_Rend.setPreferredSize(new java.awt.Dimension(170, 30));
                        btnNegPri_Rend.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnNegPri_RendActionPerformed(evt);
                            }
                        });
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = 3;
                        gridBagConstraints.gridy = 0;
                        gridBagConstraints.ipadx = 20;
                        gridBagConstraints.ipady = 13;
                        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 0);
                        pnlTitulo.add(btnNegPri_Rend, gridBagConstraints);

                        btnRendicionOtras.setBackground(new java.awt.Color(0, 153, 153));
                        btnRendicionOtras.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnRendicionOtras.setText("RENDICIÓN OTRAS");
                        btnRendicionOtras.setAlignmentX(0.5F);
                        btnRendicionOtras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnRendicionOtras.setMaximumSize(new java.awt.Dimension(170, 30));
                        btnRendicionOtras.setMinimumSize(new java.awt.Dimension(170, 30));
                        btnRendicionOtras.setPreferredSize(new java.awt.Dimension(170, 30));
                        btnRendicionOtras.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnRendicionOtrasActionPerformed(evt);
                            }
                        });
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = 4;
                        gridBagConstraints.gridy = 0;
                        gridBagConstraints.ipadx = 20;
                        gridBagConstraints.ipady = 13;
                        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 20);
                        pnlTitulo.add(btnRendicionOtras, gridBagConstraints);

                        pnlComponentes.add(pnlTitulo);

                        tbbPane.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

                        pnlIva.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                        jLabel11.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel11.setText("CONTRIBUYENTE");
                        pnlIva.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, 30));

                        txtIVAContribuyente.setEditable(false);
                        txtIVAContribuyente.setBackground(new java.awt.Color(204, 153, 0));
                        txtIVAContribuyente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtIVAContribuyente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
                        txtIVAContribuyente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
                        txtIVAContribuyente.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIVAContribuyente.setEnabled(false);
                        txtIVAContribuyente.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlIva.add(txtIVAContribuyente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 520, 30));

                        jLabel3.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel3.setText("CONTROL");
                        pnlIva.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 100, 30));

                        txtIvaControl.setBackground(new java.awt.Color(204, 153, 0));
                        txtIvaControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtIvaControl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
                        txtIvaControl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtIvaControl.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIvaControl.setEnabled(false);
                        txtIvaControl.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlIva.add(txtIvaControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 120, 30));

                        jLabel7.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel7.setText("FECHA");
                        pnlIva.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 60, 30));

                        txtIvaFechaHoy.setBackground(new java.awt.Color(204, 153, 0));
                        txtIvaFechaHoy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtIvaFechaHoy.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));
                        txtIvaFechaHoy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtIvaFechaHoy.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIvaFechaHoy.setEnabled(false);
                        txtIvaFechaHoy.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlIva.add(txtIvaFechaHoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 120, 30));

                        jLabel8.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel8.setText("IMPUESTO Nº");
                        pnlIva.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 190, 30));

                        txtIvaNum.setEditable(false);
                        txtIvaNum.setBackground(new java.awt.Color(204, 153, 0));
                        txtIvaNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtIvaNum.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
                        txtIvaNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtIvaNum.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIvaNum.setEnabled(false);
                        txtIvaNum.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlIva.add(txtIvaNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 160, 50));

                        lblIVA.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
                        lblIVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        lblIVA.setText("CALCULAR  %  SOBRE EL IVA");
                        pnlIva.add(lblIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 240, 30));

                        btnIva75.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
                        btnIva75.setText("75 %");
                        btnIva75.setEnabled(false);
                        btnIva75.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIva75ActionPerformed(evt);
                            }
                        });
                        pnlIva.add(btnIva75, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, 110, 50));

                        btnIva100.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
                        btnIva100.setText("100 %");
                        btnIva100.setEnabled(false);
                        btnIva100.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIva100ActionPerformed(evt);
                            }
                        });
                        pnlIva.add(btnIva100, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 50, 120, 50));

                        tblIvaMaster.setAutoCreateRowSorter(true);
                        tblIvaMaster.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
                        tblIvaMaster.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "Num. Causado", "Fecha", "RIF/C.I.", "Contribuyente", "Total Bs. (+IVA)", "B. Imponible Bs.", "Gravable Bs.", "Iva Bs.", "Exento Bs.", "Concepto", "RegCau"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
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
                        tblIvaMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        tblIvaMaster.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblIvaMasterMouseClicked(evt);
                            }
                        });
                        jScrollPane1.setViewportView(tblIvaMaster);
                        if (tblIvaMaster.getColumnModel().getColumnCount() > 0) {
                            tblIvaMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
                            tblIvaMaster.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblIvaMaster.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblIvaMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
                            tblIvaMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
                            tblIvaMaster.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblIvaMaster.getColumnModel().getColumn(6).setPreferredWidth(100);
                            tblIvaMaster.getColumnModel().getColumn(7).setPreferredWidth(125);
                            tblIvaMaster.getColumnModel().getColumn(8).setPreferredWidth(125);
                            tblIvaMaster.getColumnModel().getColumn(9).setPreferredWidth(350);
                            tblIvaMaster.getColumnModel().getColumn(10).setMinWidth(0);
                            tblIvaMaster.getColumnModel().getColumn(10).setPreferredWidth(0);
                            tblIvaMaster.getColumnModel().getColumn(10).setMaxWidth(0);
                        }

                        pnlIva.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1130, 190));

                        btnIvaLimpiar.setText("Limpiar");
                        btnIvaLimpiar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIvaLimpiarActionPerformed(evt);
                            }
                        });
                        pnlIva.add(btnIvaLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

                        jLabel1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        jLabel1.setText("DETALLE");
                        pnlIva.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 305, 320, 30));

                        tblIvaDetail.setAutoCreateRowSorter(true);
                        tblIvaDetail.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "NUM. CAUSADO", "FECHA FACT.", "NUM. FACT.", "TIPO", "NUM. COMPR.", "N.DEBITO", "N.CREDITO", "TRANSACCION", "FACTURA AFT", "TOTAL BS. (+IVA)", "EXENTO BS.", "B. IMPONIBLE BS.", "% ALÍCUOTA", "IMPUESTO IVA BS.", "IVA RETENIDO BS.", "OBS", "RegCompr"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
                            };
                            boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
                            };

                            public Class getColumnClass(int columnIndex) {
                                return types [columnIndex];
                            }

                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                            }
                        });
                        tblIvaDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        tblIvaDetail.setEnabled(false);
                        jScrollPane3.setViewportView(tblIvaDetail);
                        if (tblIvaDetail.getColumnModel().getColumnCount() > 0) {
                            tblIvaDetail.getColumnModel().getColumn(0).setPreferredWidth(125);
                            tblIvaDetail.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblIvaDetail.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblIvaDetail.getColumnModel().getColumn(3).setPreferredWidth(50);
                            tblIvaDetail.getColumnModel().getColumn(4).setPreferredWidth(125);
                            tblIvaDetail.getColumnModel().getColumn(5).setPreferredWidth(100);
                            tblIvaDetail.getColumnModel().getColumn(6).setPreferredWidth(100);
                            tblIvaDetail.getColumnModel().getColumn(7).setPreferredWidth(100);
                            tblIvaDetail.getColumnModel().getColumn(8).setPreferredWidth(100);
                            tblIvaDetail.getColumnModel().getColumn(9).setPreferredWidth(125);
                            tblIvaDetail.getColumnModel().getColumn(10).setPreferredWidth(125);
                            tblIvaDetail.getColumnModel().getColumn(11).setPreferredWidth(125);
                            tblIvaDetail.getColumnModel().getColumn(12).setPreferredWidth(100);
                            tblIvaDetail.getColumnModel().getColumn(13).setPreferredWidth(125);
                            tblIvaDetail.getColumnModel().getColumn(14).setPreferredWidth(125);
                            tblIvaDetail.getColumnModel().getColumn(15).setPreferredWidth(350);
                            tblIvaDetail.getColumnModel().getColumn(16).setMinWidth(0);
                            tblIvaDetail.getColumnModel().getColumn(16).setPreferredWidth(0);
                            tblIvaDetail.getColumnModel().getColumn(16).setMaxWidth(0);
                        }

                        pnlIva.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 1130, 180));

                        btnIvaGuardar.setBackground(new java.awt.Color(153, 153, 0));
                        btnIvaGuardar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIvaGuardar.setText("GUARDAR");
                        btnIvaGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIvaGuardar.setEnabled(false);
                        btnIvaGuardar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIvaGuardarActionPerformed(evt);
                            }
                        });
                        pnlIva.add(btnIvaGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 540, 150, 50));

                        btnIvaConsultar.setBackground(new java.awt.Color(153, 153, 0));
                        btnIvaConsultar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIvaConsultar.setText("CONSULTAR");
                        btnIvaConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIvaConsultar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIvaConsultarActionPerformed(evt);
                            }
                        });
                        pnlIva.add(btnIvaConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 540, 160, 50));

                        jLabel9.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel9.setText("MONTO RETENIDO BS.");
                        pnlIva.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 540, 220, 50));

                        txtIvaRet.setBackground(new java.awt.Color(204, 102, 0));
                        txtIvaRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtIvaRet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
                        txtIvaRet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                        txtIvaRet.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIvaRet.setEnabled(false);
                        txtIvaRet.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
                        pnlIva.add(txtIvaRet, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, 190, 50));

                        btnIvaEliminar.setBackground(new java.awt.Color(153, 153, 0));
                        btnIvaEliminar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIvaEliminar.setText("ELIMINAR");
                        btnIvaEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIvaEliminar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIvaEliminarActionPerformed(evt);
                            }
                        });
                        pnlIva.add(btnIvaEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 540, 120, 50));

                        btnIvaRevertir.setBackground(new java.awt.Color(153, 153, 0));
                        btnIvaRevertir.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIvaRevertir.setText("ATRAS");
                        btnIvaRevertir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIvaRevertir.setEnabled(false);
                        btnIvaRevertir.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIvaRevertirActionPerformed(evt);
                            }
                        });
                        pnlIva.add(btnIvaRevertir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 120, 50));

                        btnAnularIVA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
                        btnAnularIVA.setToolTipText("Anular Ordenes de Compromiso, sin Causar.");
                        btnAnularIVA.setMaximumSize(new java.awt.Dimension(55, 41));
                        btnAnularIVA.setMinimumSize(new java.awt.Dimension(55, 41));
                        btnAnularIVA.setPreferredSize(new java.awt.Dimension(55, 41));
                        btnAnularIVA.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnAnularIVAActionPerformed(evt);
                            }
                        });
                        pnlIva.add(btnAnularIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, -1, -1));

                        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
                        lblFondo.setText("jLabel6");
                        pnlIva.add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 600));

                        tbbPane.addTab("I.V.A.", pnlIva);

                        pnlISLR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                        jLabel12.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel12.setText("CONTRIBUYENTE");
                        pnlISLR.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, 30));

                        txtIslrContribuyente.setEditable(false);
                        txtIslrContribuyente.setBackground(new java.awt.Color(204, 153, 0));
                        txtIslrContribuyente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtIslrContribuyente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
                        txtIslrContribuyente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
                        txtIslrContribuyente.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIslrContribuyente.setEnabled(false);
                        txtIslrContribuyente.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlISLR.add(txtIslrContribuyente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 520, 30));

                        jLabel4.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel4.setText("CONTROL");
                        pnlISLR.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 100, 30));

                        txtIslrControl.setBackground(new java.awt.Color(204, 153, 0));
                        txtIslrControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtIslrControl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
                        txtIslrControl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtIslrControl.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIslrControl.setEnabled(false);
                        txtIslrControl.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlISLR.add(txtIslrControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 120, 30));

                        jLabel13.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel13.setText("FECHA");
                        pnlISLR.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 60, 30));

                        txtIslrFechaHoy.setBackground(new java.awt.Color(204, 153, 0));
                        txtIslrFechaHoy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtIslrFechaHoy.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));
                        txtIslrFechaHoy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtIslrFechaHoy.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIslrFechaHoy.setEnabled(false);
                        txtIslrFechaHoy.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlISLR.add(txtIslrFechaHoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 120, 30));

                        jLabel16.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel16.setText("IMPUESTO Nº");
                        pnlISLR.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 190, 30));

                        txtIslrNum.setEditable(false);
                        txtIslrNum.setBackground(new java.awt.Color(204, 153, 0));
                        txtIslrNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtIslrNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtIslrNum.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIslrNum.setEnabled(false);
                        txtIslrNum.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlISLR.add(txtIslrNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 160, 50));

                        tblIslrDetail.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "NUM. CAUSADO", "FECHA FACT.", "NUM. FACT.", "TIPO", "NUM. COMPR.", "TOTAL BS. (+IVA)", "B. IMPONIBLE BS.", "GRAVABLE BS.", "% RETENSIÓN", "ISLR RETENIDO BS.", "OBS", "RegCompr"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
                            };
                            boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, true, true, false, false, false
                            };

                            public Class getColumnClass(int columnIndex) {
                                return types [columnIndex];
                            }

                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                            }
                        });
                        tblIslrDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        jScrollPane4.setViewportView(tblIslrDetail);
                        if (tblIslrDetail.getColumnModel().getColumnCount() > 0) {
                            tblIslrDetail.getColumnModel().getColumn(0).setPreferredWidth(125);
                            tblIslrDetail.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblIslrDetail.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblIslrDetail.getColumnModel().getColumn(3).setPreferredWidth(50);
                            tblIslrDetail.getColumnModel().getColumn(4).setPreferredWidth(100);
                            tblIslrDetail.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblIslrDetail.getColumnModel().getColumn(6).setPreferredWidth(125);
                            tblIslrDetail.getColumnModel().getColumn(7).setPreferredWidth(125);
                            tblIslrDetail.getColumnModel().getColumn(8).setPreferredWidth(100);
                            tblIslrDetail.getColumnModel().getColumn(8).setHeaderValue("% RETENSIÓN");
                            tblIslrDetail.getColumnModel().getColumn(9).setPreferredWidth(125);
                            tblIslrDetail.getColumnModel().getColumn(10).setPreferredWidth(350);
                            tblIslrDetail.getColumnModel().getColumn(11).setMinWidth(0);
                            tblIslrDetail.getColumnModel().getColumn(11).setPreferredWidth(0);
                            tblIslrDetail.getColumnModel().getColumn(11).setMaxWidth(0);
                        }

                        pnlISLR.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 1130, 180));

                        btnIslrLimpiar.setText("Limpiar");
                        btnIslrLimpiar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIslrLimpiarActionPerformed(evt);
                            }
                        });
                        pnlISLR.add(btnIslrLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

                        jLabel6.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        jLabel6.setText("DETALLE");
                        pnlISLR.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 305, 320, 30));

                        btnIslrGuardar.setBackground(new java.awt.Color(153, 153, 0));
                        btnIslrGuardar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIslrGuardar.setText("GUARDAR");
                        btnIslrGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIslrGuardar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIslrGuardarActionPerformed(evt);
                            }
                        });
                        pnlISLR.add(btnIslrGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 540, 150, 50));

                        btnIslrEliminar.setBackground(new java.awt.Color(153, 153, 0));
                        btnIslrEliminar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIslrEliminar.setText("ELIMINAR");
                        btnIslrEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIslrEliminar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIslrEliminarActionPerformed(evt);
                            }
                        });
                        pnlISLR.add(btnIslrEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 540, 120, 50));

                        btnIslrConsultar.setBackground(new java.awt.Color(153, 153, 0));
                        btnIslrConsultar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIslrConsultar.setText("CONSULTAR");
                        btnIslrConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIslrConsultar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIslrConsultarActionPerformed(evt);
                            }
                        });
                        pnlISLR.add(btnIslrConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 540, 160, 50));

                        txtIslrRetenido.setBackground(new java.awt.Color(204, 102, 0));
                        txtIslrRetenido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtIslrRetenido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
                        txtIslrRetenido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                        txtIslrRetenido.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtIslrRetenido.setEnabled(false);
                        txtIslrRetenido.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
                        pnlISLR.add(txtIslrRetenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, 190, 50));

                        jLabel5.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel5.setText("MONTO RETENIDO BS.");
                        pnlISLR.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 610, 220, 50));

                        tblIslrMaster.setAutoCreateRowSorter(true);
                        tblIslrMaster.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
                        tblIslrMaster.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "Num. Causado", "Fecha", "RIF / C.I.", "Contribuyente", "Total Bs. (+IVA)", "B. Imponible Bs.", "ISLR grav. Bs.", "Concepto", "regCau"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
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
                        tblIslrMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        tblIslrMaster.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblIslrMasterMouseClicked(evt);
                            }
                        });
                        jScrollPane2.setViewportView(tblIslrMaster);
                        if (tblIslrMaster.getColumnModel().getColumnCount() > 0) {
                            tblIslrMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
                            tblIslrMaster.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblIslrMaster.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblIslrMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
                            tblIslrMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
                            tblIslrMaster.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblIslrMaster.getColumnModel().getColumn(6).setPreferredWidth(125);
                            tblIslrMaster.getColumnModel().getColumn(7).setPreferredWidth(350);
                            tblIslrMaster.getColumnModel().getColumn(8).setMinWidth(0);
                            tblIslrMaster.getColumnModel().getColumn(8).setPreferredWidth(0);
                            tblIslrMaster.getColumnModel().getColumn(8).setMaxWidth(0);
                        }

                        pnlISLR.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1130, 190));

                        jLabel2.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel2.setText("MONTO RETENIDO BS.");
                        pnlISLR.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 540, 220, 50));

                        lblIVA1.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
                        lblIVA1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        lblIVA1.setText("CALCULAR  %  DE RETENSIÓN GENERALSOBRE EL ISLR");
                        pnlISLR.add(lblIVA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 400, 30));

                        cmbIslrPorc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
                        cmbIslrPorc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0%", "1%", "2%", "3%", "4%", "5%", "6%" }));
                        cmbIslrPorc.setEnabled(false);
                        cmbIslrPorc.addItemListener(new java.awt.event.ItemListener() {
                            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                cmbIslrPorcItemStateChanged(evt);
                            }
                        });
                        pnlISLR.add(cmbIslrPorc, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, -1, -1));

                        btnIslrRevertir.setBackground(new java.awt.Color(153, 153, 0));
                        btnIslrRevertir.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnIslrRevertir.setText("ATRAS");
                        btnIslrRevertir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnIslrRevertir.setEnabled(false);
                        btnIslrRevertir.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnIslrRevertirActionPerformed(evt);
                            }
                        });
                        pnlISLR.add(btnIslrRevertir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 120, 50));

                        btnAnularISLR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
                        btnAnularISLR.setToolTipText("Anular Ordenes de Compromiso, sin Causar.");
                        btnAnularISLR.setMaximumSize(new java.awt.Dimension(55, 41));
                        btnAnularISLR.setMinimumSize(new java.awt.Dimension(55, 41));
                        btnAnularISLR.setPreferredSize(new java.awt.Dimension(55, 41));
                        btnAnularISLR.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnAnularISLRActionPerformed(evt);
                            }
                        });
                        pnlISLR.add(btnAnularISLR, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, -1, -1));

                        lblFondo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
                        lblFondo2.setText("jLabel6");
                        pnlISLR.add(lblFondo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1170, 680));

                        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
                        jLabel39.setText("Negro Primero");
                        pnlISLR.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 500, 40));

                        tbbPane.addTab("I.S.L.R.", pnlISLR);

                        pnlImpMun.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                        jLabel21.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel21.setText("CONTRIBUYENTE");
                        pnlImpMun.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, 30));

                        txtImpMunContribuyente.setEditable(false);
                        txtImpMunContribuyente.setBackground(new java.awt.Color(204, 153, 0));
                        txtImpMunContribuyente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtImpMunContribuyente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
                        txtImpMunContribuyente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
                        txtImpMunContribuyente.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtImpMunContribuyente.setEnabled(false);
                        txtImpMunContribuyente.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlImpMun.add(txtImpMunContribuyente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 520, 30));

                        jLabel22.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel22.setText("CONTROL");
                        pnlImpMun.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 100, 30));

                        txtImpMunControl.setBackground(new java.awt.Color(204, 153, 0));
                        txtImpMunControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtImpMunControl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
                        txtImpMunControl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtImpMunControl.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtImpMunControl.setEnabled(false);
                        txtImpMunControl.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlImpMun.add(txtImpMunControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 120, 30));

                        jLabel23.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel23.setText("FECHA");
                        pnlImpMun.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 60, 30));

                        txtImpMunFechaHoy.setBackground(new java.awt.Color(204, 153, 0));
                        txtImpMunFechaHoy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtImpMunFechaHoy.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));
                        txtImpMunFechaHoy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtImpMunFechaHoy.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtImpMunFechaHoy.setEnabled(false);
                        txtImpMunFechaHoy.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlImpMun.add(txtImpMunFechaHoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 120, 30));

                        jLabel24.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel24.setText("IMPUESTO Nº");
                        pnlImpMun.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 190, 30));

                        txtImpMunNum.setEditable(false);
                        txtImpMunNum.setBackground(new java.awt.Color(204, 153, 0));
                        txtImpMunNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtImpMunNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtImpMunNum.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtImpMunNum.setEnabled(false);
                        txtImpMunNum.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlImpMun.add(txtImpMunNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 160, 50));

                        tblImpMunDetail.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "NUM. CAUSADO", "FECHA FACT.", "NUM. FACT.", "TIPO", "NUM. COMPR.", "TOTAL BS. (+IVA)", "B. IMPONIBLE BS.", "GRAVABLE BS.", "RETENIDO BS.", "OBS", "RegCompr"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
                            };
                            boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, true, true, false, false
                            };

                            public Class getColumnClass(int columnIndex) {
                                return types [columnIndex];
                            }

                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                            }
                        });
                        tblImpMunDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        jScrollPane7.setViewportView(tblImpMunDetail);
                        if (tblImpMunDetail.getColumnModel().getColumnCount() > 0) {
                            tblImpMunDetail.getColumnModel().getColumn(0).setPreferredWidth(125);
                            tblImpMunDetail.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblImpMunDetail.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblImpMunDetail.getColumnModel().getColumn(3).setPreferredWidth(50);
                            tblImpMunDetail.getColumnModel().getColumn(4).setPreferredWidth(100);
                            tblImpMunDetail.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblImpMunDetail.getColumnModel().getColumn(6).setPreferredWidth(125);
                            tblImpMunDetail.getColumnModel().getColumn(7).setPreferredWidth(125);
                            tblImpMunDetail.getColumnModel().getColumn(8).setPreferredWidth(125);
                            tblImpMunDetail.getColumnModel().getColumn(9).setPreferredWidth(350);
                            tblImpMunDetail.getColumnModel().getColumn(10).setMinWidth(0);
                            tblImpMunDetail.getColumnModel().getColumn(10).setPreferredWidth(0);
                            tblImpMunDetail.getColumnModel().getColumn(10).setMaxWidth(0);
                        }

                        pnlImpMun.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 1130, 180));

                        btnImpMunLimpiar.setText("Limpiar");
                        btnImpMunLimpiar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnImpMunLimpiarActionPerformed(evt);
                            }
                        });
                        pnlImpMun.add(btnImpMunLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

                        jLabel25.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        jLabel25.setText("DETALLE");
                        pnlImpMun.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 305, 320, 30));

                        btnImpMunGuardar.setBackground(new java.awt.Color(153, 153, 0));
                        btnImpMunGuardar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnImpMunGuardar.setText("GUARDAR");
                        btnImpMunGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnImpMunGuardar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnImpMunGuardarActionPerformed(evt);
                            }
                        });
                        pnlImpMun.add(btnImpMunGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 540, 150, 50));

                        btnImpMunEliminar.setBackground(new java.awt.Color(153, 153, 0));
                        btnImpMunEliminar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnImpMunEliminar.setText("ELIMINAR");
                        btnImpMunEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnImpMunEliminar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnImpMunEliminarActionPerformed(evt);
                            }
                        });
                        pnlImpMun.add(btnImpMunEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 540, 120, 50));

                        btnImpMunConsultar.setBackground(new java.awt.Color(153, 153, 0));
                        btnImpMunConsultar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnImpMunConsultar.setText("CONSULTAR");
                        btnImpMunConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnImpMunConsultar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnImpMunConsultarActionPerformed(evt);
                            }
                        });
                        pnlImpMun.add(btnImpMunConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 540, 160, 50));

                        txtImpMunRetenido.setBackground(new java.awt.Color(204, 102, 0));
                        txtImpMunRetenido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtImpMunRetenido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
                        txtImpMunRetenido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                        txtImpMunRetenido.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtImpMunRetenido.setEnabled(false);
                        txtImpMunRetenido.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
                        pnlImpMun.add(txtImpMunRetenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, 190, 50));

                        jLabel26.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel26.setText("MONTO RETENIDO BS.");
                        pnlImpMun.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 610, 220, 50));

                        tblImpMunMaster.setAutoCreateRowSorter(true);
                        tblImpMunMaster.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
                        tblImpMunMaster.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "Num. Causado", "Fecha", "RIF / C.I.", "Contribuyente", "Total Bs. (+IVA)", "B. Imponible Bs.", "IVA Bs.", "Concepto", "regCau"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
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
                        tblImpMunMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        tblImpMunMaster.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblImpMunMasterMouseClicked(evt);
                            }
                        });
                        jScrollPane8.setViewportView(tblImpMunMaster);
                        if (tblImpMunMaster.getColumnModel().getColumnCount() > 0) {
                            tblImpMunMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
                            tblImpMunMaster.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblImpMunMaster.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblImpMunMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
                            tblImpMunMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
                            tblImpMunMaster.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblImpMunMaster.getColumnModel().getColumn(6).setPreferredWidth(125);
                            tblImpMunMaster.getColumnModel().getColumn(7).setPreferredWidth(350);
                            tblImpMunMaster.getColumnModel().getColumn(8).setMinWidth(0);
                            tblImpMunMaster.getColumnModel().getColumn(8).setPreferredWidth(0);
                            tblImpMunMaster.getColumnModel().getColumn(8).setMaxWidth(0);
                        }

                        pnlImpMun.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1130, 190));

                        jLabel27.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel27.setText("MONTO RETENIDO BS.");
                        pnlImpMun.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 540, 220, 50));

                        btnImpMunRevertir.setBackground(new java.awt.Color(153, 153, 0));
                        btnImpMunRevertir.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnImpMunRevertir.setText("ATRAS");
                        btnImpMunRevertir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnImpMunRevertir.setEnabled(false);
                        btnImpMunRevertir.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnImpMunRevertirActionPerformed(evt);
                            }
                        });
                        pnlImpMun.add(btnImpMunRevertir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 120, 50));

                        btnAnularImpMun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
                        btnAnularImpMun.setToolTipText("Anular Ordenes de Compromiso, sin Causar.");
                        btnAnularImpMun.setMaximumSize(new java.awt.Dimension(55, 41));
                        btnAnularImpMun.setMinimumSize(new java.awt.Dimension(55, 41));
                        btnAnularImpMun.setPreferredSize(new java.awt.Dimension(55, 41));
                        btnAnularImpMun.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnAnularImpMunActionPerformed(evt);
                            }
                        });
                        pnlImpMun.add(btnAnularImpMun, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, -1, -1));

                        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
                        jLabel36.setText("Impuestos Municipales");
                        pnlImpMun.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 500, 40));

                        lblFondo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
                        lblFondo4.setText("jLabel6");
                        pnlImpMun.add(lblFondo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1170, 680));

                        tbbPane.addTab("IMP. MUN.", pnlImpMun);

                        pnlNegPri.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                        jLabel28.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel28.setText("CONTRIBUYENTE");
                        pnlNegPri.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, 30));

                        txtNegPriContribuyente.setEditable(false);
                        txtNegPriContribuyente.setBackground(new java.awt.Color(204, 153, 0));
                        txtNegPriContribuyente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtNegPriContribuyente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
                        txtNegPriContribuyente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
                        txtNegPriContribuyente.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtNegPriContribuyente.setEnabled(false);
                        txtNegPriContribuyente.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlNegPri.add(txtNegPriContribuyente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 520, 30));

                        jLabel29.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel29.setText("CONTROL");
                        pnlNegPri.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 100, 30));

                        txtNegPriControl.setBackground(new java.awt.Color(204, 153, 0));
                        txtNegPriControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtNegPriControl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
                        txtNegPriControl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtNegPriControl.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtNegPriControl.setEnabled(false);
                        txtNegPriControl.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlNegPri.add(txtNegPriControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 120, 30));

                        jLabel30.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel30.setText("FECHA");
                        pnlNegPri.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 60, 30));

                        txtNegPriFechaHoy.setBackground(new java.awt.Color(204, 153, 0));
                        txtNegPriFechaHoy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtNegPriFechaHoy.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));
                        txtNegPriFechaHoy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtNegPriFechaHoy.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtNegPriFechaHoy.setEnabled(false);
                        txtNegPriFechaHoy.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlNegPri.add(txtNegPriFechaHoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 120, 30));

                        jLabel31.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel31.setText("IMPUESTO Nº");
                        pnlNegPri.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 190, 30));

                        txtNegPriNum.setEditable(false);
                        txtNegPriNum.setBackground(new java.awt.Color(204, 153, 0));
                        txtNegPriNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtNegPriNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtNegPriNum.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtNegPriNum.setEnabled(false);
                        txtNegPriNum.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlNegPri.add(txtNegPriNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 160, 50));

                        tblNegPriDetail.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "NUM. CAUSADO", "FECHA FACT.", "NUM. FACT.", "TIPO", "NUM. COMPR.", "TOTAL BS. (+IVA)", "B. IMPONIBLE BS.", "GRAVABLE BS.", "RETENIDO BS.", "OBS", "RegCompr"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
                            };
                            boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, true, true, false, false
                            };

                            public Class getColumnClass(int columnIndex) {
                                return types [columnIndex];
                            }

                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                            }
                        });
                        tblNegPriDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        jScrollPane9.setViewportView(tblNegPriDetail);
                        if (tblNegPriDetail.getColumnModel().getColumnCount() > 0) {
                            tblNegPriDetail.getColumnModel().getColumn(0).setPreferredWidth(125);
                            tblNegPriDetail.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblNegPriDetail.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblNegPriDetail.getColumnModel().getColumn(3).setPreferredWidth(50);
                            tblNegPriDetail.getColumnModel().getColumn(4).setPreferredWidth(100);
                            tblNegPriDetail.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblNegPriDetail.getColumnModel().getColumn(6).setPreferredWidth(125);
                            tblNegPriDetail.getColumnModel().getColumn(7).setPreferredWidth(125);
                            tblNegPriDetail.getColumnModel().getColumn(8).setPreferredWidth(125);
                            tblNegPriDetail.getColumnModel().getColumn(9).setPreferredWidth(350);
                            tblNegPriDetail.getColumnModel().getColumn(10).setMinWidth(0);
                            tblNegPriDetail.getColumnModel().getColumn(10).setPreferredWidth(0);
                            tblNegPriDetail.getColumnModel().getColumn(10).setMaxWidth(0);
                        }

                        pnlNegPri.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 1130, 180));

                        btnNegPriLimpiar.setText("Limpiar");
                        btnNegPriLimpiar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnNegPriLimpiarActionPerformed(evt);
                            }
                        });
                        pnlNegPri.add(btnNegPriLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

                        jLabel32.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        jLabel32.setText("DETALLE");
                        pnlNegPri.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 305, 320, 30));

                        btnNegPriGuardar.setBackground(new java.awt.Color(153, 153, 0));
                        btnNegPriGuardar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnNegPriGuardar.setText("GUARDAR");
                        btnNegPriGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnNegPriGuardar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnNegPriGuardarActionPerformed(evt);
                            }
                        });
                        pnlNegPri.add(btnNegPriGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 540, 150, 50));

                        btnNegPriEliminar.setBackground(new java.awt.Color(153, 153, 0));
                        btnNegPriEliminar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnNegPriEliminar.setText("ELIMINAR");
                        btnNegPriEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnNegPriEliminar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnNegPriEliminarActionPerformed(evt);
                            }
                        });
                        pnlNegPri.add(btnNegPriEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 540, 120, 50));

                        btnNegPriConsultar.setBackground(new java.awt.Color(153, 153, 0));
                        btnNegPriConsultar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnNegPriConsultar.setText("CONSULTAR");
                        btnNegPriConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnNegPriConsultar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnNegPriConsultarActionPerformed(evt);
                            }
                        });
                        pnlNegPri.add(btnNegPriConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 540, 160, 50));

                        txtNegPriRetenido.setBackground(new java.awt.Color(204, 102, 0));
                        txtNegPriRetenido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtNegPriRetenido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
                        txtNegPriRetenido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                        txtNegPriRetenido.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtNegPriRetenido.setEnabled(false);
                        txtNegPriRetenido.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
                        pnlNegPri.add(txtNegPriRetenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, 190, 50));

                        jLabel33.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel33.setText("MONTO RETENIDO BS.");
                        pnlNegPri.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 610, 220, 50));

                        tblNegPriMaster.setAutoCreateRowSorter(true);
                        tblNegPriMaster.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
                        tblNegPriMaster.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "Num. Causado", "Fecha", "RIF / C.I.", "Contribuyente", "Total Bs. (+IVA)", "B. Imponible Bs.", "IVA Bs.", "Concepto", "regCau"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
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
                        tblNegPriMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        tblNegPriMaster.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblNegPriMasterMouseClicked(evt);
                            }
                        });
                        jScrollPane10.setViewportView(tblNegPriMaster);
                        if (tblNegPriMaster.getColumnModel().getColumnCount() > 0) {
                            tblNegPriMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
                            tblNegPriMaster.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblNegPriMaster.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblNegPriMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
                            tblNegPriMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
                            tblNegPriMaster.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblNegPriMaster.getColumnModel().getColumn(6).setPreferredWidth(125);
                            tblNegPriMaster.getColumnModel().getColumn(7).setPreferredWidth(350);
                            tblNegPriMaster.getColumnModel().getColumn(8).setMinWidth(0);
                            tblNegPriMaster.getColumnModel().getColumn(8).setPreferredWidth(0);
                            tblNegPriMaster.getColumnModel().getColumn(8).setMaxWidth(0);
                        }

                        pnlNegPri.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1130, 190));

                        jLabel34.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel34.setText("MONTO RETENIDO BS.");
                        pnlNegPri.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 540, 220, 50));

                        btnNegPriRevertir.setBackground(new java.awt.Color(153, 153, 0));
                        btnNegPriRevertir.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnNegPriRevertir.setText("ATRAS");
                        btnNegPriRevertir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnNegPriRevertir.setEnabled(false);
                        btnNegPriRevertir.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnNegPriRevertirActionPerformed(evt);
                            }
                        });
                        pnlNegPri.add(btnNegPriRevertir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 120, 50));

                        btnAnularNegPri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
                        btnAnularNegPri.setToolTipText("Anular Ordenes de Compromiso, sin Causar.");
                        btnAnularNegPri.setMaximumSize(new java.awt.Dimension(55, 41));
                        btnAnularNegPri.setMinimumSize(new java.awt.Dimension(55, 41));
                        btnAnularNegPri.setPreferredSize(new java.awt.Dimension(55, 41));
                        btnAnularNegPri.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnAnularNegPriActionPerformed(evt);
                            }
                        });
                        pnlNegPri.add(btnAnularNegPri, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, -1, -1));

                        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
                        jLabel35.setText("Negro Primero");
                        pnlNegPri.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 500, 40));

                        lblFondo5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
                        lblFondo5.setText("jLabel6");
                        pnlNegPri.add(lblFondo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1170, 680));

                        tbbPane.addTab("NEGRO PRIMERO", pnlNegPri);

                        pnlOtras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                        jLabel14.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel14.setText("CONTRIBUYENTE");
                        pnlOtras.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, 30));

                        txtORetContribuyente.setEditable(false);
                        txtORetContribuyente.setBackground(new java.awt.Color(204, 153, 0));
                        txtORetContribuyente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtORetContribuyente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
                        txtORetContribuyente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
                        txtORetContribuyente.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtORetContribuyente.setEnabled(false);
                        txtORetContribuyente.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlOtras.add(txtORetContribuyente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 520, 30));

                        jLabel10.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel10.setText("CONTROL");
                        pnlOtras.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 100, 30));

                        txtORetControl.setBackground(new java.awt.Color(204, 153, 0));
                        txtORetControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
                        txtORetControl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
                        txtORetControl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtORetControl.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtORetControl.setEnabled(false);
                        txtORetControl.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlOtras.add(txtORetControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 120, 30));

                        jLabel15.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
                        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel15.setText("FECHA");
                        pnlOtras.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 60, 30));

                        txtORetFechaHoy.setBackground(new java.awt.Color(204, 153, 0));
                        txtORetFechaHoy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtORetFechaHoy.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL))));
                        txtORetFechaHoy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtORetFechaHoy.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtORetFechaHoy.setEnabled(false);
                        txtORetFechaHoy.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlOtras.add(txtORetFechaHoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 120, 30));

                        jLabel17.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel17.setText("IMPUESTO Nº");
                        pnlOtras.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 190, 30));

                        txtORetNum.setEditable(false);
                        txtORetNum.setBackground(new java.awt.Color(204, 153, 0));
                        txtORetNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtORetNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        txtORetNum.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtORetNum.setEnabled(false);
                        txtORetNum.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        pnlOtras.add(txtORetNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 160, 50));

                        tblORetDetail.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "NUM. CAUSADO", "FECHA FACT.", "NUM. FACT.", "TIPO", "NUM. COMPR.", "TOTAL BS. (+IVA)", "B. IMPONIBLE BS.", "GRAVABLE BS.", "RETENIDO BS.", "OBS", "RegCompr"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
                            };
                            boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, true, true, false, false
                            };

                            public Class getColumnClass(int columnIndex) {
                                return types [columnIndex];
                            }

                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                            }
                        });
                        tblORetDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        jScrollPane5.setViewportView(tblORetDetail);
                        if (tblORetDetail.getColumnModel().getColumnCount() > 0) {
                            tblORetDetail.getColumnModel().getColumn(0).setPreferredWidth(125);
                            tblORetDetail.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblORetDetail.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblORetDetail.getColumnModel().getColumn(3).setPreferredWidth(50);
                            tblORetDetail.getColumnModel().getColumn(4).setPreferredWidth(100);
                            tblORetDetail.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblORetDetail.getColumnModel().getColumn(6).setPreferredWidth(125);
                            tblORetDetail.getColumnModel().getColumn(7).setPreferredWidth(125);
                            tblORetDetail.getColumnModel().getColumn(8).setPreferredWidth(125);
                            tblORetDetail.getColumnModel().getColumn(9).setPreferredWidth(350);
                            tblORetDetail.getColumnModel().getColumn(10).setMinWidth(0);
                            tblORetDetail.getColumnModel().getColumn(10).setPreferredWidth(0);
                            tblORetDetail.getColumnModel().getColumn(10).setMaxWidth(0);
                        }

                        pnlOtras.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 1130, 180));

                        btnORetLimpiar.setText("Limpiar");
                        btnORetLimpiar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnORetLimpiarActionPerformed(evt);
                            }
                        });
                        pnlOtras.add(btnORetLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

                        jLabel18.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        jLabel18.setText("DETALLE");
                        pnlOtras.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 305, 320, 30));

                        btnORetGuardar.setBackground(new java.awt.Color(153, 153, 0));
                        btnORetGuardar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnORetGuardar.setText("GUARDAR");
                        btnORetGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnORetGuardar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnORetGuardarActionPerformed(evt);
                            }
                        });
                        pnlOtras.add(btnORetGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 540, 150, 50));

                        btnORetEliminar.setBackground(new java.awt.Color(153, 153, 0));
                        btnORetEliminar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnORetEliminar.setText("ELIMINAR");
                        btnORetEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnORetEliminar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnORetEliminarActionPerformed(evt);
                            }
                        });
                        pnlOtras.add(btnORetEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 540, 120, 50));

                        btnORetConsultar.setBackground(new java.awt.Color(153, 153, 0));
                        btnORetConsultar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnORetConsultar.setText("CONSULTAR");
                        btnORetConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnORetConsultar.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnORetConsultarActionPerformed(evt);
                            }
                        });
                        pnlOtras.add(btnORetConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 540, 160, 50));

                        txtORetRetenido.setBackground(new java.awt.Color(204, 102, 0));
                        txtORetRetenido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        txtORetRetenido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
                        txtORetRetenido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                        txtORetRetenido.setDisabledTextColor(new java.awt.Color(102, 0, 0));
                        txtORetRetenido.setEnabled(false);
                        txtORetRetenido.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
                        pnlOtras.add(txtORetRetenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, 190, 50));

                        jLabel19.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel19.setText("MONTO RETENIDO BS.");
                        pnlOtras.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 610, 220, 50));

                        tblORetMaster.setAutoCreateRowSorter(true);
                        tblORetMaster.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
                        tblORetMaster.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {

                            },
                            new String [] {
                                "Num. Causado", "Fecha", "RIF / C.I.", "Contribuyente", "Total Bs. (+IVA)", "B. Imponible Bs.", "IVA Bs.", "Concepto", "regCau"
                            }
                        ) {
                            Class[] types = new Class [] {
                                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
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
                        tblORetMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                        tblORetMaster.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblORetMasterMouseClicked(evt);
                            }
                        });
                        jScrollPane6.setViewportView(tblORetMaster);
                        if (tblORetMaster.getColumnModel().getColumnCount() > 0) {
                            tblORetMaster.getColumnModel().getColumn(0).setPreferredWidth(100);
                            tblORetMaster.getColumnModel().getColumn(1).setPreferredWidth(100);
                            tblORetMaster.getColumnModel().getColumn(2).setPreferredWidth(100);
                            tblORetMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
                            tblORetMaster.getColumnModel().getColumn(4).setPreferredWidth(125);
                            tblORetMaster.getColumnModel().getColumn(5).setPreferredWidth(125);
                            tblORetMaster.getColumnModel().getColumn(6).setPreferredWidth(125);
                            tblORetMaster.getColumnModel().getColumn(7).setPreferredWidth(350);
                            tblORetMaster.getColumnModel().getColumn(8).setMinWidth(0);
                            tblORetMaster.getColumnModel().getColumn(8).setPreferredWidth(0);
                            tblORetMaster.getColumnModel().getColumn(8).setMaxWidth(0);
                        }

                        pnlOtras.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1130, 190));

                        jLabel20.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel20.setText("MONTO RETENIDO BS.");
                        pnlOtras.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 540, 220, 50));

                        btnORetRevertir.setBackground(new java.awt.Color(153, 153, 0));
                        btnORetRevertir.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
                        btnORetRevertir.setText("ATRAS");
                        btnORetRevertir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
                        btnORetRevertir.setEnabled(false);
                        btnORetRevertir.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnORetRevertirActionPerformed(evt);
                            }
                        });
                        pnlOtras.add(btnORetRevertir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 120, 50));

                        btnAnularOtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
                        btnAnularOtras.setToolTipText("Anular Ordenes de Compromiso, sin Causar.");
                        btnAnularOtras.setMaximumSize(new java.awt.Dimension(55, 41));
                        btnAnularOtras.setMinimumSize(new java.awt.Dimension(55, 41));
                        btnAnularOtras.setPreferredSize(new java.awt.Dimension(55, 41));
                        btnAnularOtras.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnAnularOtrasActionPerformed(evt);
                            }
                        });
                        pnlOtras.add(btnAnularOtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, -1, -1));

                        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
                        jLabel37.setText("Otras Retenciones");
                        pnlOtras.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 500, 40));

                        lblFondo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
                        lblFondo3.setText("jLabel6");
                        pnlOtras.add(lblFondo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1170, 680));

                        tbbPane.addTab("OTRAS", pnlOtras);

                        pnlComponentes.add(tbbPane);
                        tbbPane.getAccessibleContext().setAccessibleName("tbbPane");

                        pnlFondo.add(pnlComponentes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 860));

                        lblFondo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
                        lblFondo1.setText("jLabel6");
                        pnlFondo.add(lblFondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1170, 680));

                        getContentPane().add(pnlFondo, java.awt.BorderLayout.CENTER);

                        pack();
                        setLocationRelativeTo(null);
                    }// </editor-fold>//GEN-END:initComponents

    /**
     * Rev 15/10/2016
     *
     */
    private void actIvaGuardar() {

        final Map<String, Object> param = new HashMap<>(101);
        if (!valIvaComp(param)) {
            return;
        }

        try {
            Conn.BeginTransaction();
            checkIvaNextId();
            genIvaMaster(param);
            genIvaDetail(param);
            Conn.EndTransaction();

        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
            }

            JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
            return;
        }

        JOptionPane.showMessageDialog(null, "Operación realizada");

        try {
            genIvaReport((long) param.get("id_iva_retencion"), param);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
        }

        setStartConditions();
    }

    /**
     * Rev 23/10/2016
     *
     */
    private void actIslrGuardar() {

        final Map<String, Object> param = new HashMap<>(101);
        if (!valIslrComp(param)) {
            return;
        }

        try {
            Conn.BeginTransaction();
            checkIslrNextId();
            genIslrMaster(param);
            genIslrDetail(param);
            Conn.EndTransaction();
        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
            }

            JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
            return;
        }

        JOptionPane.showMessageDialog(null, "Operación realizada");

        try {
            genIslrReport((long) param.get("id_islr_retencion"), param);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
        }
        setStartConditions();
    }

    /**
     * Rev 14/04/2017
     *
     */
    private void actImpMunGuardar() {

        final Map<String, Object> param = new HashMap<>(101);
        if (!valImpMunComp(param)) {
            return;
        }

        try {
            Conn.BeginTransaction();
            checkImpMunNextId();
            genImpMunMaster(param);
            genImpMunDetail(param);
            Conn.EndTransaction();
        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
            }

            JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
            return;
        }

        JOptionPane.showMessageDialog(null, "Operación realizada");

        try {
            genImpMunReport((long) param.get("id_imp_mun"), param);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
        }

        setStartConditions();
    }

    /**
     * Rev 23/10/2016
     *
     */
    private void actNegPriGuardar() {

        final Map<String, Object> param = new HashMap<>(101);
        if (!valNegPriComp(param)) {
            return;
        }

        try {
            Conn.BeginTransaction();
            checkNegPriNextId();
            genNegPriMaster(param);
            genNegPriDetail(param);
            Conn.EndTransaction();
        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
            }

            JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
            return;
        }

        JOptionPane.showMessageDialog(null, "Operación realizada");

        try {
            genNegPriReport((long) param.get("id_neg_pri"), param);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
        }

        setStartConditions();
    }

    /**
     * Rev 23/10/2016
     *
     */
    private void actORetGuardar() {

        final Map<String, Object> param = new HashMap<>(101);
        if (!valORetComp(param)) {
            return;
        }

        try {
            Conn.BeginTransaction();
            checkORetNextId();
            genORetMaster(param);
            genORetDetail(param);
            Conn.EndTransaction();
        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
            }

            JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
            return;
        }

        JOptionPane.showMessageDialog(null, "Operación realizada");

        try {
            genORetReport((long) param.get("id_otras_ret"), param);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
        }

        setStartConditions();
    }

    /**
     * Rev 13/10/2016
     *
     * @param _param
     * @return
     */
    private boolean valIvaComp(final Map<String, Object> _param) {

        // Retornar si a tabla esta vacia
        if (tblIvaDetail.getRowCount() <= 0) {
            return false;
        }

        // Retornar si no se ha calculado el monto del IVA
        if (txtIvaRet.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha calculado el monto a retener");
            return false;
        }

        final BigDecimal iva_retenido_bs;
        try {
            iva_retenido_bs = Format.toBigDec(txtIvaRet.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        if (iva_retenido_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Monto del IVA es cero (0), no se puede generar la operación");
            return false;
        }

        _param.put("ivaBenef_razonsocial", ivaBenef_razonsocial);
        _param.put("ivaBenef_rif_ci", ivaBenef_rif_ci);
        _param.put("iva_porc_ret", iva_porc_ret);
        _param.put("iva_retenido_bs", iva_retenido_bs);

        return true;
    }

    /**
     * Rev 23/10/2016
     *
     * @param _param
     * @return
     */
    private boolean valIslrComp(final Map<String, Object> _param) {

        // Retornar si a tabla esta vacia
        if (tblIslrDetail.getRowCount() <= 0) {
            return false;
        }

        // Retornar si no se ha calculado el monto del Islr
        if (txtIslrRetenido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha calculado el monto a retener");
            return false;
        }

        final BigDecimal islr_retenido_bs;
        try {
            islr_retenido_bs = Format.toBigDec(txtIslrRetenido.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        if (islr_retenido_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Monto del monto retenido es cero (0), no se puede generar la operación");
            return false;
        }

        _param.put("islrBenef_razonsocial", islrBenef_razonsocial);
        _param.put("islrBenef_rif_ci", islrBenef_rif_ci);
        _param.put("islr_retenido_bs", islr_retenido_bs);

        return true;
    }

    /**
     * Rev 14/04/2017
     *
     * @param _param
     * @return
     */
    private boolean valImpMunComp(final Map<String, Object> _param) {

        // Retornar si a tabla esta vacia
        if (tblImpMunDetail.getRowCount() <= 0) {
            return false;
        }

        // Retornar si no se ha calculado el monto del Islr
        final String aux = txtImpMunRetenido.getText().trim();
        if (aux.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha calculado el monto a retener");
            return false;
        }

        final BigDecimal retenido_bs;
        try {
            retenido_bs = Format.toBigDec(aux);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        if (retenido_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Monto del monto retenido es inválido, no se puede generar la operación");
            return false;
        }

        _param.put("impMunBenef_razonsocial", impMunBenef_razonsocial);
        _param.put("impMunBenef_rif_ci", impMunBenef_rif_ci);
        _param.put("impMun_retenido_bs", retenido_bs);

        return true;
    }

    /**
     * Rev 23/10/2016
     *
     * @param _param
     * @return
     */
    private boolean valNegPriComp(final Map<String, Object> _param) {

        // Retornar si a tabla esta vacia
        if (tblNegPriDetail.getRowCount() <= 0) {
            return false;
        }

        // Retornar si no se ha calculado el monto del Islr
        final String aux = txtNegPriRetenido.getText().trim();
        if (aux.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha calculado el monto a retener");
            return false;
        }

        final BigDecimal retenido_bs;
        try {
            retenido_bs = Format.toBigDec(aux);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        if (retenido_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Monto del monto retenido es inválido, no se puede generar la operación");
            return false;
        }

        _param.put("negPriBenef_razonsocial", negPriBenef_razonsocial);
        _param.put("negPriBenef_rif_ci", negPriBenef_rif_ci);
        _param.put("negPri_retenido_bs", retenido_bs);

        return true;
    }

    /**
     * Rev 23/10/2016
     *
     * @param _param
     * @return
     */
    private boolean valORetComp(final Map<String, Object> _param) {

        // Retornar si a tabla esta vacia
        if (tblORetDetail.getRowCount() <= 0) {
            return false;
        }

        // Retornar si no se ha calculado el monto del Islr
        final String aux = txtORetRetenido.getText().trim();
        if (aux.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha calculado el monto a retener");
            return false;
        }

        final BigDecimal retenido_bs;
        try {
            retenido_bs = Format.toBigDec(aux);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        if (retenido_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Monto del monto retenido es inválido, no se puede generar la operación");
            return false;
        }

        _param.put("oretBenef_razonsocial", oRetBenef_razonsocial);
        _param.put("oretBenef_rif_ci", oRetBenef_rif_ci);
        _param.put("oret_retenido_bs", retenido_bs);

        return true;
    }

    /**
     * Rev 13/10/2016
     *
     * @param _param
     * @throws Exception
     */
    public static void genIvaMaster(final Map<String, Object> _param) throws Exception {

        checkIvaNextId();

        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO iva_retencion("
            + "id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4,
            + "benef_razonsocial, benef_rif_ci) " // 5, 6, 7 
            + "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pst.setLong(1, UserPassIn.getIdUser());
        pst.setLong(2, UserPassIn.getIdSession());
        pst.setTimestamp(3, UserPassIn.getDateSession());
        final java.sql.Date date = new java.sql.Date(Globales.getServerTimeStamp().getTime());
        pst.setDate(4, date); // ejefis
        pst.setString(5, (String) _param.get("ivaBenef_razonsocial"));
        pst.setString(6, (String) _param.get("ivaBenef_rif_ci"));
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error sl insertar el registro");
        }

        // Toma el ultimo id generado
        final long id;
        final ResultSet rsId = pst.getGeneratedKeys();
        if (rsId.next()) {
            id = rsId.getLong(1);
        } else {
            throw new Exception("Error al tratar de recuperar el ID");
        }

        _param.put("id_iva_retencion", id);
    }

    /**
     * Rev 24/10/2016
     *
     * @param _param
     * @throws Exception
     */
    public static void genIslrMaster(final Map<String, Object> _param) throws Exception {

        checkIslrNextId();

        // Mantener actualizado el control iva
        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO islr_retencion("
            + "id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4,
            + "benef_razonsocial, benef_rif_ci) " // 5, 6, 7 
            + "VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pst.setLong(1, UserPassIn.getIdUser());
        pst.setLong(2, UserPassIn.getIdSession());
        pst.setTimestamp(3, UserPassIn.getDateSession());
        final java.sql.Date date = new java.sql.Date(Globales.getServerTimeStamp().getTime());
        pst.setDate(4, date); // ejefis
        pst.setString(5, (String) _param.get("islrBenef_razonsocial"));
        pst.setString(6, (String) _param.get("islrBenef_rif_ci"));
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error sl insertar el registro");
        }

        // Toma el ultimo id generado
        final long id;
        final ResultSet rsId = pst.getGeneratedKeys();
        if (rsId.next()) {
            id = rsId.getLong(1);
        } else {
            throw new Exception("Error al tratar de recuperar el ID");
        }

        _param.put("id_islr_retencion", id);
    }

    /**
     * Rev 14-04-2017
     *
     * @param _param
     * @throws Exception
     */
    public static void genImpMunMaster(final Map<String, Object> _param) throws Exception {

        checkImpMunNextId();

        // Mantener actualizado el control
        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO imp_mun("
            + "id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4,
            + "benef_razonsocial, benef_rif_ci) " // 5, 6, 7 
            + "VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pst.setLong(1, UserPassIn.getIdUser());
        pst.setLong(2, UserPassIn.getIdSession());
        pst.setTimestamp(3, UserPassIn.getDateSession());
        final java.sql.Date date = new java.sql.Date(Globales.getServerTimeStamp().getTime());
        pst.setDate(4, date); // ejefis
        pst.setString(5, (String) _param.get("impMunBenef_razonsocial"));
        pst.setString(6, (String) _param.get("impMunBenef_rif_ci"));
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error sl insertar el registro");
        }

        // Toma el ultimo id generado
        final long id;
        final ResultSet rsId = pst.getGeneratedKeys();
        if (rsId.next()) {
            id = rsId.getLong(1);
        } else {
            throw new Exception("Error al tratar de recuperar el ID");
        }

        _param.put("id_imp_mun", id);
    }

    /**
     * Rev 03-03-2017
     *
     * @param _param
     * @throws Exception
     */
    public static void genNegPriMaster(final Map<String, Object> _param) throws Exception {

        checkNegPriNextId();

        // Mantener actualizado el control iva
        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO neg_pri("
            + "id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4,
            + "benef_razonsocial, benef_rif_ci) " // 5, 6, 7 
            + "VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pst.setLong(1, UserPassIn.getIdUser());
        pst.setLong(2, UserPassIn.getIdSession());
        pst.setTimestamp(3, UserPassIn.getDateSession());
        final java.sql.Date date = new java.sql.Date(Globales.getServerTimeStamp().getTime());
        pst.setDate(4, date); // ejefis
        pst.setString(5, (String) _param.get("negPriBenef_razonsocial"));
        pst.setString(6, (String) _param.get("negPriBenef_rif_ci"));
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error sl insertar el registro");
        }

        // Toma el ultimo id generado
        final long id;
        final ResultSet rsId = pst.getGeneratedKeys();
        if (rsId.next()) {
            id = rsId.getLong(1);
        } else {
            throw new Exception("Error al tratar de recuperar el ID");
        }

        _param.put("id_neg_pri", id);
    }

    /**
     * Rev 03-03-2017
     *
     * @param _param
     * @throws Exception
     */
    public static void genORetMaster(final Map<String, Object> _param) throws Exception {

        checkORetNextId();

        // Mantener actualizado el control iva
        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO otras_ret("
            + "id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4,
            + "benef_razonsocial, benef_rif_ci) " // 5, 6, 7 
            + "VALUES ( ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pst.setLong(1, UserPassIn.getIdUser());
        pst.setLong(2, UserPassIn.getIdSession());
        pst.setTimestamp(3, UserPassIn.getDateSession());
        final java.sql.Date date = new java.sql.Date(Globales.getServerTimeStamp().getTime());
        pst.setDate(4, date); // ejefis
        pst.setString(5, (String) _param.get("oretBenef_razonsocial"));
        pst.setString(6, (String) _param.get("oretBenef_rif_ci"));
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error sl insertar el registro");
        }

        // Toma el ultimo id generado
        final long id;
        final ResultSet rsId = pst.getGeneratedKeys();
        if (rsId.next()) {
            id = rsId.getLong(1);
        } else {
            throw new Exception("Error al tratar de recuperar el ID");
        }

        _param.put("id_otras_ret", id);
    }

    /**
     * Rev 23/10/2016
     *
     * @param _param
     * @throws Exception
     */
    private void genIvaDetail(final Map<String, Object> _param) throws Exception {
        // Para mantener los causados actualizados
        final Set<Long> setCausado = new HashSet<>();

        final long id_iva_retencion = (long) _param.get("id_iva_retencion");

        // Recorrrer la lista de los Causados seleccionados
        for (int i = 0; i < tblIvaDetail.getRowCount(); i++) {

            final long id_causado = (long) tblIvaDetail.getValueAt(i, IVACOLDET_ID_CAUSADO);
            final java.sql.Date fecha_fact = (java.sql.Date) tblIvaDetail.getValueAt(i, IVACOLDET_FECHA_FACT);
            final String num_fact = (String) tblIvaDetail.getValueAt(i, IVACOLDET_NUM_FACT);
            final TipoCompr tipo_compr = TipoCompr.valueOf((String) tblIvaDetail.getValueAt(i, IVACOLDET_TIPO_COMPR));
            final long id_compr = (long) tblIvaDetail.getValueAt(i, IVACOLDET_NUM_COMPR);
            final String ndebito = (String) tblIvaDetail.getValueAt(i, IVACOLDET_NDEBITO); // num. nota de debito
            final String ncredito = (String) tblIvaDetail.getValueAt(i, IVACOLDET_NCREDITO); // num nota de credito
            final String transaccion = (String) tblIvaDetail.getValueAt(i, IVACOLDET_TRANSACCION);
            final String factura_aft = (String) tblIvaDetail.getValueAt(i, IVACOLDET_FACTURA_AFT); // num factura que afecta
            final BigDecimal total_fact = Format.toBigDec((double) tblIvaDetail.getValueAt(i, IVACOLDET_TOTAL));
            final BigDecimal exento = Format.toBigDec((double) tblIvaDetail.getValueAt(i, IVACOLDET_EXENTO));
            final BigDecimal base_imponible = Format.toBigDec((double) tblIvaDetail.getValueAt(i, IVACOLDET_BASE_IMPONIBLE));
            final BigDecimal iva_porc_aplic = Format.toBigDec((double) tblIvaDetail.getValueAt(i, IVACOLDET_ALICUOTA));
            final BigDecimal iva_bs = Format.toBigDec((double) tblIvaDetail.getValueAt(i, IVACOLDET_IVA_BS));
            final BigDecimal iva_retenido = Format.toBigDec((double) tblIvaDetail.getValueAt(i, IVACOLDET_IVA_RETENIDO));
            final String obs = (String) tblIvaDetail.getValueAt(i, IVACOLDET_OBS);

            if (iva_bs.compareTo(BigDecimal.ZERO) > 0) {
                final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO iva_retencion_det("
                    + "id_iva_retencion, id_causado, fecha_fact, num_fact, " // 1, 2, 3, 4, 
                    + "id_compr, ndebito, ncredito, transaccion, " // 5, 6, 7, 8,
                    + "factura_aft, total_fact, exento, base_imponible, " // 9, 10, 11, 12,
                    + "iva_porc_aplic, iva_bs, iva_retenido, observacion, tipo_compr, " // 13, 14, 15, 16, 17
                    + "ejefis, benef_razonsocial, benef_rif_ci) " // 18, 19, 20
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                pst.setLong(1, id_iva_retencion);
                pst.setLong(2, id_causado); // noperacion
                pst.setDate(3, fecha_fact); // ffactura
                pst.setString(4, num_fact); // nfactura
                pst.setLong(5, id_compr); // cfactura, num control
                pst.setString(6, ndebito); // ndebito
                pst.setString(7, ncredito); // ncredito
                pst.setString(8, transaccion); // transaccion
                pst.setString(9, factura_aft); // factura qe afecta
                pst.setBigDecimal(10, total_fact); // 
                pst.setBigDecimal(11, exento); // exento
                pst.setBigDecimal(12, base_imponible); // bimponible
                pst.setBigDecimal(13, iva_porc_aplic); // elicuota
                pst.setBigDecimal(14, iva_bs); // impuesto
                pst.setBigDecimal(15, iva_retenido); // iretenido
                pst.setString(16, obs); // conceptoret
                pst.setString(17, tipo_compr.name()); // tipo_compr
                pst.setDate(18, new java.sql.Date(Globales.getServerTimeStamp().getTime())); // ejefis
                pst.setString(19, (String) _param.get("ivaBenef_razonsocial"));
                pst.setString(20, (String) _param.get("ivaBenef_rif_ci"));
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error sl insertar el registro de Detalle");
                }
            } // por cada compromiso

            // Actualizar los datos del Compromiso asociado
            // Aqui NO se debe actualizar el iva_grav_bs, porque esta cantidad es fija, y establecida en el compromiso
            PreparedStatement pstCompr = Conn.getConnection().prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET iva_porc_ret= ? WHERE id_compr= ?");
            pstCompr.setBigDecimal(1, iva_porc_ret);
            pstCompr.setLong(2, id_compr);
            if (pstCompr.executeUpdate() != 1) {
                throw new Exception("Error al actualizar el Compromiso");
            }

            // Verificar si ya se ha actualizado el campo retenido_sn del causado
            if (!setCausado.contains(id_causado)) {
                setCausado.add(id_causado);
                if (Conn.getConnection().prepareStatement("UPDATE causado SET iva_ret_sn='S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                    throw new Exception("Error al actualizar el Causado");
                }
            }

            final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs - ? WHERE id_causado= ?");
            pstCau.setBigDecimal(1, iva_retenido);
            pstCau.setLong(2, id_causado);
            if (pstCau.executeUpdate() != 1) {
                throw new Exception("Error sl actualizar el impuesto Retenido");
            }
        } // for (int i = 0; i < tblIvaDetail.getRowCount(); i++)
    }

    /**
     * Rev 13/10/2016
     *
     * @param _param
     * @throws Exception
     */
    private void genIslrDetail(final Map<String, Object> _param) throws Exception {

        // Para mantener los causados actualizados
        final Set<Long> setCausado = new HashSet<>();

        final long id_islr_retencion = (long) _param.get("id_islr_retencion");

        // Recorrrer la lista de los Compromisos asociados a los Causados seleccionados
        for (int i = 0; i < tblIslrDetail.getRowCount(); i++) {

            final long id_causado = (long) tblIslrDetail.getValueAt(i, ISLRCOLDET_ID_CAUSADO);
            final java.sql.Date fecha_fact = (java.sql.Date) tblIslrDetail.getValueAt(i, ISLRCOLDET_FECHA_FACT);
            final String num_fact = (String) tblIslrDetail.getValueAt(i, ISLRCOLDET_NUM_FACT);
            final TipoCompr tipo_compr = TipoCompr.valueOf((String) tblIslrDetail.getValueAt(i, ISLRCOLDET_TIPO_COMPR));
            final long id_compr = (long) tblIslrDetail.getValueAt(i, ISLRCOLDET_NUM_COMPR);
            final BigDecimal total_fact = Format.toBigDec((double) tblIslrDetail.getValueAt(i, ISLRCOLDET_TOTAL));
            final BigDecimal base_imponible = Format.toBigDec((double) tblIslrDetail.getValueAt(i, ISLRCOLDET_BASE_IMPONIBLE));
            final BigDecimal isrl_grav_bs = Format.toBigDec((double) tblIslrDetail.getValueAt(i, ISLRCOLDET_GRAVABLE));
            final BigDecimal islr_porc_ret = Format.toBigDec((double) tblIslrDetail.getValueAt(i, ISLRCOLDET_PORC_RETENCION));
            final BigDecimal islr_retenido_bs = Format.toBigDec((double) tblIslrDetail.getValueAt(i, ISLRCOLDET_RETENSION_BS));
            final String obs = (String) tblIslrDetail.getValueAt(i, ISLRCOLDET_OBS);

            final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO islr_retencion_det("
                + "id_islr_retencion, id_causado, fecha_fact, num_fact, " // 1, 2, 3, 4, 
                + "tipo_compr, id_compr, total_fact, base_imponible, " // 5, 6, 7, 8,
                + "gravable_bs, islr_porc_ret, islr_retenido_bs, observacion, " // 9, 10, 11, 12
                + "ejefis, benef_razonsocial, benef_rif_ci) " // 13, 14, 15
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setLong(1, id_islr_retencion);
            pst.setLong(2, id_causado);
            pst.setDate(3, fecha_fact);
            pst.setString(4, num_fact);
            pst.setString(5, tipo_compr.name());
            pst.setLong(6, id_compr);
            pst.setBigDecimal(7, total_fact);
            pst.setBigDecimal(8, base_imponible);
            pst.setBigDecimal(9, isrl_grav_bs);
            pst.setBigDecimal(10, islr_porc_ret);
            pst.setBigDecimal(11, islr_retenido_bs);
            pst.setString(12, obs);
            pst.setDate(13, new java.sql.Date(Globales.getServerTimeStamp().getTime())); // ejefis
            pst.setString(14, (String) _param.get("islrBenef_razonsocial"));
            pst.setString(15, (String) _param.get("islrBenef_rif_ci"));
            if (pst.executeUpdate() != 1) {
                throw new Exception("Error sl insertar el registro de Detalle");
            }

            // Actualizar los datos del Compromiso asociado
            // Aqui si se debe actualizar el islr_grav_bs, porque el usuario tiene la potestad de cambiar este valor en la JTable
            PreparedStatement pstCompr = Conn.getConnection().prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET islr_grav_bs= ?, islr_porc_ret= ? WHERE id_compr= ?");
            pstCompr.setBigDecimal(1, isrl_grav_bs);
            pstCompr.setBigDecimal(2, islr_porc_ret);
            pstCompr.setLong(3, id_compr);
            if (pstCompr.executeUpdate() != 1) {
                throw new Exception("Error al actualizar el Compromiso");
            }

            // Verificar si ya se ha actualizado el campo retenido_sn del causado
            if (!setCausado.contains(id_causado)) {
                setCausado.add(id_causado);
                if (Conn.getConnection().prepareStatement("UPDATE causado SET islr_ret_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                    throw new Exception("Error al actualizar el Causado");
                }
            }

            final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs - ? WHERE id_causado= ?");
            pstCau.setBigDecimal(1, islr_retenido_bs);
            pstCau.setLong(2, id_causado);
            if (pstCau.executeUpdate() != 1) {
                throw new Exception("Error sl actualizar el Resta, en el Causado");
            }
        } // for (int i = 0; i < tblIvaDetail.getRowCount(); i++)
    }

    /**
     * Rev 03-03-2017
     *
     * @param _param
     * @throws Exception
     */
    private void genImpMunDetail(final Map<String, Object> _param) throws Exception {

        // Para mantener los causados actualizados
        final Set<Long> setCausado = new HashSet<>();

        final long id_imp_mun = (long) _param.get("id_imp_mun");

        // Recorrrer la lista de los Compromisos asociados a los Causados seleccionados
        for (int i = 0; i < tblImpMunDetail.getRowCount(); i++) {

            final long id_causado = (long) tblImpMunDetail.getValueAt(i, ORETCOLDET_ID_CAUSADO);
            final java.sql.Date fecha_fact = (java.sql.Date) tblImpMunDetail.getValueAt(i, ORETCOLDET_FECHA_FACT);
            final String num_fact = (String) tblImpMunDetail.getValueAt(i, ORETCOLDET_NUM_FACT);
            final TipoCompr tipo_compr = TipoCompr.valueOf((String) tblImpMunDetail.getValueAt(i, ORETCOLDET_TIPO_COMPR));
            final long id_compr = (long) tblImpMunDetail.getValueAt(i, ORETCOLDET_NUM_COMPR);
            final BigDecimal total_fact = Format.toBigDec((double) tblImpMunDetail.getValueAt(i, ORETCOLDET_TOTAL));
            final BigDecimal base_imponible = Format.toBigDec((double) tblImpMunDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE));
            final BigDecimal grav_bs = Format.toBigDec((double) tblImpMunDetail.getValueAt(i, ORETCOLDET_GRAVABLE));
            final BigDecimal retenido_bs = Format.toBigDec((double) tblImpMunDetail.getValueAt(i, ORETCOLDET_RETENIDO_BS));
            final String obs = (String) tblImpMunDetail.getValueAt(i, ORETCOLDET_OBS);

            final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO imp_mun_det("
                + "id_imp_mun, id_causado, fecha_fact, num_fact, " // 1, 2, 3, 4, 
                + "tipo_compr, id_compr, total_fact, base_imponible, " // 5, 6, 7, 8,
                + "gravable_bs, retenido_bs, observacion, " // 9, 10, 11,
                + "ejefis, benef_razonsocial, benef_rif_ci) " // 12, 13, 14
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setLong(1, id_imp_mun);
            pst.setLong(2, id_causado);
            pst.setDate(3, fecha_fact);
            pst.setString(4, num_fact);
            pst.setString(5, tipo_compr.name());
            pst.setLong(6, id_compr);
            pst.setBigDecimal(7, total_fact);
            pst.setBigDecimal(8, base_imponible);
            pst.setBigDecimal(9, grav_bs);
            pst.setBigDecimal(10, retenido_bs);
            pst.setString(11, obs);
            pst.setDate(12, Globales.getServerSqlDate()); // ejefis
            pst.setString(13, (String) _param.get("impMunBenef_razonsocial"));
            pst.setString(14, (String) _param.get("impMunBenef_rif_ci"));
            if (pst.executeUpdate() != 1) {
                throw new Exception("Error sl insertar el registro de Detalle");
            }

            // Actualizar los datos del Compromiso asociado
            // Aqui si se debe actualizar el islr_grav_bs, porque el usuario tiene la potestad de cambiar este valor en la JTable
            PreparedStatement pstCompr = Conn.getConnection().prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET imp_mun_grav_bs= ?, imp_mun_bs= ? WHERE id_compr= ?");
            pstCompr.setBigDecimal(1, grav_bs);
            pstCompr.setBigDecimal(2, retenido_bs);
            pstCompr.setLong(3, id_compr);
            if (pstCompr.executeUpdate() != 1) {
                throw new Exception("Error al actualizar el Compromiso");
            }

            // Verificar si ya se ha actualizado el campo retenido_sn del causado
            if (!setCausado.contains(id_causado)) {
                setCausado.add(id_causado);
                if (Conn.getConnection().prepareStatement("UPDATE causado SET imp_mun_ret_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                    throw new Exception("Error al actualizar el Causado");
                }
            }

            final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs - ? WHERE id_causado= ?");
            pstCau.setBigDecimal(1, retenido_bs);
            pstCau.setLong(2, id_causado);
            if (pstCau.executeUpdate() != 1) {
                throw new Exception("Error sl actualizar el Resta, en el Causado");
            }
        } // for (int i = 0; i < tblImpMunDetail.getRowCount(); i++)
    }

    /**
     * Rev 03-03-2017
     *
     * @param _param
     * @throws Exception
     */
    private void genNegPriDetail(final Map<String, Object> _param) throws Exception {

        // Para mantener los causados actualizados
        final Set<Long> setCausado = new HashSet<>();

        final long id_neg_pri = (long) _param.get("id_neg_pri");

        // Recorrrer la lista de los Compromisos asociados a los Causados seleccionados
        for (int i = 0; i < tblNegPriDetail.getRowCount(); i++) {

            final long id_causado = (long) tblNegPriDetail.getValueAt(i, ORETCOLDET_ID_CAUSADO);
            final java.sql.Date fecha_fact = (java.sql.Date) tblNegPriDetail.getValueAt(i, ORETCOLDET_FECHA_FACT);
            final String num_fact = (String) tblNegPriDetail.getValueAt(i, ORETCOLDET_NUM_FACT);
            final TipoCompr tipo_compr = TipoCompr.valueOf((String) tblNegPriDetail.getValueAt(i, ORETCOLDET_TIPO_COMPR));
            final long id_compr = (long) tblNegPriDetail.getValueAt(i, ORETCOLDET_NUM_COMPR);
            final BigDecimal total_fact = Format.toBigDec((double) tblNegPriDetail.getValueAt(i, ORETCOLDET_TOTAL));
            final BigDecimal base_imponible = Format.toBigDec((double) tblNegPriDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE));
            final BigDecimal grav_bs = Format.toBigDec((double) tblNegPriDetail.getValueAt(i, ORETCOLDET_GRAVABLE));
            final BigDecimal retenido_bs = Format.toBigDec((double) tblNegPriDetail.getValueAt(i, ORETCOLDET_RETENIDO_BS));
            final String obs = (String) tblNegPriDetail.getValueAt(i, ORETCOLDET_OBS);

            final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO neg_pri_det("
                + "id_neg_pri, id_causado, fecha_fact, num_fact, " // 1, 2, 3, 4, 
                + "tipo_compr, id_compr, total_fact, base_imponible, " // 5, 6, 7, 8,
                + "gravable_bs, retenido_bs, observacion, " // 9, 10, 11,
                + "ejefis, benef_razonsocial, benef_rif_ci) " // 12, 13, 14
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setLong(1, id_neg_pri);
            pst.setLong(2, id_causado);
            pst.setDate(3, fecha_fact);
            pst.setString(4, num_fact);
            pst.setString(5, tipo_compr.name());
            pst.setLong(6, id_compr);
            pst.setBigDecimal(7, total_fact);
            pst.setBigDecimal(8, base_imponible);
            pst.setBigDecimal(9, grav_bs);
            pst.setBigDecimal(10, retenido_bs);
            pst.setString(11, obs);
            pst.setDate(12, Globales.getServerSqlDate()); // ejefis
            pst.setString(13, (String) _param.get("negPriBenef_razonsocial"));
            pst.setString(14, (String) _param.get("negPriBenef_rif_ci"));
            if (pst.executeUpdate() != 1) {
                throw new Exception("Error sl insertar el registro de Detalle");
            }

            // Actualizar los datos del Compromiso asociado
            // Aqui si se debe actualizar el islr_grav_bs, porque el usuario tiene la potestad de cambiar este valor en la JTable
            PreparedStatement pstCompr = Conn.getConnection().prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET neg_pri_grav_bs= ?, neg_pri_bs= ? WHERE id_compr= ?");
            pstCompr.setBigDecimal(1, grav_bs);
            pstCompr.setBigDecimal(2, retenido_bs);
            pstCompr.setLong(3, id_compr);
            if (pstCompr.executeUpdate() != 1) {
                throw new Exception("Error al actualizar el Compromiso");
            }

            // Verificar si ya se ha actualizado el campo retenido_sn del causado
            if (!setCausado.contains(id_causado)) {
                setCausado.add(id_causado);
                if (Conn.getConnection().prepareStatement("UPDATE causado SET neg_pri_ret_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                    throw new Exception("Error al actualizar el Causado");
                }
            }

            final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs - ? WHERE id_causado= ?");
            pstCau.setBigDecimal(1, retenido_bs);
            pstCau.setLong(2, id_causado);
            if (pstCau.executeUpdate() != 1) {
                throw new Exception("Error sl actualizar el Resta, en el Causado");
            }
        } // for (int i = 0; i < tblIvaDetail.getRowCount(); i++)
    }

    /**
     * Rev 03-03-2017
     *
     * @param _param
     * @throws Exception
     */
    private void genORetDetail(final Map<String, Object> _param) throws Exception {

        // Para mantener los causados actualizados
        final Set<Long> setCausado = new HashSet<>();

        final long id_islr_retencion = (long) _param.get("id_otras_ret");

        // Recorrrer la lista de los Compromisos asociados a los Causados seleccionados
        for (int i = 0; i < tblORetDetail.getRowCount(); i++) {

            final long id_causado = (long) tblORetDetail.getValueAt(i, ORETCOLDET_ID_CAUSADO);
            final java.sql.Date fecha_fact = (java.sql.Date) tblORetDetail.getValueAt(i, ORETCOLDET_FECHA_FACT);
            final String num_fact = (String) tblORetDetail.getValueAt(i, ORETCOLDET_NUM_FACT);
            final TipoCompr tipo_compr = TipoCompr.valueOf((String) tblORetDetail.getValueAt(i, ORETCOLDET_TIPO_COMPR));
            final long id_compr = (long) tblORetDetail.getValueAt(i, ORETCOLDET_NUM_COMPR);
            final BigDecimal total_fact = Format.toBigDec((double) tblORetDetail.getValueAt(i, ORETCOLDET_TOTAL));
            final BigDecimal base_imponible = Format.toBigDec((double) tblORetDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE));
            final BigDecimal grav_bs = Format.toBigDec((double) tblORetDetail.getValueAt(i, ORETCOLDET_GRAVABLE));
            final BigDecimal retenido_bs = Format.toBigDec((double) tblORetDetail.getValueAt(i, ORETCOLDET_RETENIDO_BS));
            final String obs = (String) tblORetDetail.getValueAt(i, ORETCOLDET_OBS);

            final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO otras_ret_det("
                + "id_otras_ret, id_causado, fecha_fact, num_fact, " // 1, 2, 3, 4, 
                + "tipo_compr, id_compr, total_fact, base_imponible, " // 5, 6, 7, 8,
                + "gravable_bs, retenido_bs, observacion, " // 9, 10, 11,
                + "ejefis, benef_razonsocial, benef_rif_ci) " // 12, 13, 14
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setLong(1, id_islr_retencion);
            pst.setLong(2, id_causado);
            pst.setDate(3, fecha_fact);
            pst.setString(4, num_fact);
            pst.setString(5, tipo_compr.name());
            pst.setLong(6, id_compr);
            pst.setBigDecimal(7, total_fact);
            pst.setBigDecimal(8, base_imponible);
            pst.setBigDecimal(9, grav_bs);
            pst.setBigDecimal(10, retenido_bs);
            pst.setString(11, obs);
            pst.setDate(12, Globales.getServerSqlDate()); // ejefis
            pst.setString(13, (String) _param.get("oretBenef_razonsocial"));
            pst.setString(14, (String) _param.get("oretBenef_rif_ci"));
            if (pst.executeUpdate() != 1) {
                throw new Exception("Error sl insertar el registro de Detalle");
            }

            // Actualizar los datos del Compromiso asociado
            // Aqui si se debe actualizar el islr_grav_bs, porque el usuario tiene la potestad de cambiar este valor en la JTable
            PreparedStatement pstCompr = Conn.getConnection().prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET oret_grav_bs= ?, oret_bs= ? WHERE id_compr= ?");
            pstCompr.setBigDecimal(1, grav_bs);
            pstCompr.setBigDecimal(2, retenido_bs);
            pstCompr.setLong(3, id_compr);
            if (pstCompr.executeUpdate() != 1) {
                throw new Exception("Error al actualizar el Compromiso");
            }

            // Verificar si ya se ha actualizado el campo retenido_sn del causado
            if (!setCausado.contains(id_causado)) {
                setCausado.add(id_causado);
                if (Conn.getConnection().prepareStatement("UPDATE causado SET oret_ret_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
                    throw new Exception("Error al actualizar el Causado");
                }
            }

            final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs - ? WHERE id_causado= ?");
            pstCau.setBigDecimal(1, retenido_bs);
            pstCau.setLong(2, id_causado);
            if (pstCau.executeUpdate() != 1) {
                throw new Exception("Error sl actualizar el Resta, en el Causado");
            }
        } // for (int i = 0; i < tblORetDetail.getRowCount(); i++)
    }

    /**
     * Rev 13/10/2016
     *
     * @param _id
     * @param _param
     * @throws Exception
     */
    public static void genIvaReport(final long _id, final Map<String, Object> _param) throws Exception {

        final IvaRetencionModel regIva = IvaRetencionModel.getxID(_id);
        if (regIva == null) {
            JOptionPane.showMessageDialog(null, "Registro no encontrado");
            return;
        }

        StringBuilder numCaus = null;
        StringBuilder numComprs = null;
        final ArrayList<IvaRetencionDetModel> list = IvaRetencionDetModel.getxId_retencion(_id);

        // Recorrrer la lista de los detalles insertados
        for (IvaRetencionDetModel reg : list) {
            final String sid_causado = String.valueOf(reg.getId_causado());
            final String sid_compr = String.valueOf(reg.getId_compr());

            // Llevar la cuenta del numero de causados
            if (numCaus == null) {
                numCaus = new StringBuilder(sid_causado);
                numComprs = new StringBuilder(sid_compr);
            } else {
                // Verificar que no se halla añadido el elemento
                if (numCaus.indexOf(sid_causado) < 0) {
                    numCaus.append(", ").append(sid_causado);
                }

                if (numComprs != null) {
                    numComprs.append(", ").append(sid_compr);
                }
            }

        }

        _param.put("ivano", _id);
        _param.put("anulado", regIva.getAnulado_sn().equals("S") ? "ANULADO" : "");
        _param.put("cliente_razonsocial", CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        _param.put("cliente_rif_ci", CapipPropiedades.CAPIP_CLIENTE_RIF_CI);
        _param.put("cliente_domicilio_fiscal", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);
        _param.put("controliva", _id / 100_000_000L);
        _param.put("numcaus", numCaus == null ? "" : numCaus.toString());
        _param.put("numcomprs", numComprs == null ? "" : numComprs.toString());
        _param.put("contribuyente", regIva.getBenef_razonsocial());
        _param.put("rif", regIva.getBenef_rif_ci());
        _param.put("fechahoy", new java.sql.Date(Globales.getServerTimeStamp().getTime()));
        _param.put("iduser", UserPassIn.getIdUser());
        _param.put("idsession", UserPassIn.getIdSession());
        _param.put("user", UserPassIn.getUserName());
        _param.put("rpt_fecha_hora", Globales.getServerTimeStamp());

        _param.put("ejefis", Long.parseLong(regIva.getEjefis().toString().substring(0, 4)));

        _param.put("aux_1", CapipPropiedades.CAPIP_AUX_1);
        _param.put("aux_2", CapipPropiedades.CAPIP_AUX_2);
        _param.put("linea_1", CapipPropiedades.CAPIP_LINEA_1);
        _param.put("linea_2", CapipPropiedades.CAPIP_LINEA_2);
        _param.put("linea_3", CapipPropiedades.CAPIP_LINEA_3);
        _param.put("linea_4", CapipPropiedades.CAPIP_LINEA_4);

        _param.put("desc_1", CapipPropiedades.CAPIP_DESC_1);
        _param.put("desc_2", CapipPropiedades.CAPIP_DESC_2);
        _param.put("desc_3", CapipPropiedades.CAPIP_DESC_3);
        _param.put("desc_4", CapipPropiedades.CAPIP_DESC_4);
        _param.put("desc_5", CapipPropiedades.CAPIP_DESC_5);
        _param.put("desc_6", CapipPropiedades.CAPIP_DESC_6);
        _param.put("desc_7", CapipPropiedades.CAPIP_DESC_7);
        _param.put("desc_8", CapipPropiedades.CAPIP_DESC_8);

        _param.put("func_1", CapipPropiedades.CAPIP_FUNC_1);
        _param.put("func_2", CapipPropiedades.CAPIP_FUNC_2);
        _param.put("func_3", CapipPropiedades.CAPIP_FUNC_3);
        _param.put("func_4", CapipPropiedades.CAPIP_FUNC_4);
        _param.put("func_5", CapipPropiedades.CAPIP_FUNC_5);
        _param.put("func_6", CapipPropiedades.CAPIP_FUNC_6);
        _param.put("func_7", CapipPropiedades.CAPIP_FUNC_7);
        _param.put("func_8", CapipPropiedades.CAPIP_FUNC_8);

        final Class aClass = FrmPrincipal.getInstance().getClass();
        _param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        _param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/iva.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), _param, Conn.getConnection()), false);
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de impuesto no encontrado");
        }
    }

    /**
     * Rev 13/10/2016
     *
     * @param _id
     * @param _param
     * @throws Exception
     */
    public static void genIslrReport(final long _id, final Map<String, Object> _param) throws Exception {

        final IslrRetencionModel regIslr = IslrRetencionModel.getxID(_id);
        if (regIslr == null) {
            JOptionPane.showMessageDialog(null, "Registro no encontrado");
            return;
        }

        StringBuilder numCaus = null;
        StringBuilder numComprs = null;
        final ArrayList<IslrRetencionDetModel> list = IslrRetencionDetModel.getxId_retencion(_id);

        // Recorrrer la lista de los detalles insertados
        for (IslrRetencionDetModel reg : list) {
            final String sid_causado = String.valueOf(reg.getId_causado());
            final String sid_compr = String.valueOf(reg.getId_compr());

            // Llevar la cuenta del numero de causados
            if (numCaus == null) {
                numCaus = new StringBuilder(sid_causado);
                numComprs = new StringBuilder(sid_compr);
            } else {
                // Verificar que no se halla añadido el elemento
                if (numCaus.indexOf(sid_causado) < 0) {
                    numCaus.append(", ").append(sid_causado);
                }

                if (numComprs != null) {
                    numComprs.append(", ").append(sid_compr);
                }
            }

        }

        _param.put("anulado", regIslr.getAnulado_sn().equals("S") ? "ANULADO" : "");
        _param.put("aux_1", CapipPropiedades.CAPIP_AUX_1);
        _param.put("aux_2", CapipPropiedades.CAPIP_AUX_2);
        _param.put("islrno", _id);
        _param.put("cliente_razonsocial", CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        _param.put("cliente_rif_ci", CapipPropiedades.CAPIP_CLIENTE_RIF_CI);
        _param.put("cliente_domicilio_fiscal", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);
        _param.put("controlislr", _id / 100_000_000L);
        _param.put("numcaus", numCaus == null ? "" : numCaus.toString());
        _param.put("numcomprs", numComprs == null ? "" : numComprs.toString());
        _param.put("contribuyente", regIslr.getBenef_razonsocial());
        _param.put("rif", regIslr.getBenef_rif_ci());
        _param.put("fechahoy", new java.sql.Date(Globales.getServerTimeStamp().getTime()));
        _param.put("iduser", UserPassIn.getIdUser());
        _param.put("idsession", UserPassIn.getIdSession());
        _param.put("linea_1", CapipPropiedades.CAPIP_LINEA_1);
        _param.put("linea_2", CapipPropiedades.CAPIP_LINEA_2);
        _param.put("linea_3", CapipPropiedades.CAPIP_LINEA_3);
        _param.put("linea_4", CapipPropiedades.CAPIP_LINEA_4);
        _param.put("user", UserPassIn.getUserName());
        _param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        _param.put("ejefis", Long.parseLong(regIslr.getEjefis().toString().substring(0, 4)));

        _param.put("desc_1", CapipPropiedades.CAPIP_DESC_1);
        _param.put("desc_2", CapipPropiedades.CAPIP_DESC_2);
        _param.put("desc_3", CapipPropiedades.CAPIP_DESC_3);
        _param.put("desc_4", CapipPropiedades.CAPIP_DESC_4);
        _param.put("desc_5", CapipPropiedades.CAPIP_DESC_5);
        _param.put("desc_6", CapipPropiedades.CAPIP_DESC_6);
        _param.put("desc_7", CapipPropiedades.CAPIP_DESC_7);
        _param.put("desc_8", CapipPropiedades.CAPIP_DESC_8);

        _param.put("func_1", CapipPropiedades.CAPIP_FUNC_1);
        _param.put("func_2", CapipPropiedades.CAPIP_FUNC_2);
        _param.put("func_3", CapipPropiedades.CAPIP_FUNC_3);
        _param.put("func_4", CapipPropiedades.CAPIP_FUNC_4);
        _param.put("func_5", CapipPropiedades.CAPIP_FUNC_5);
        _param.put("func_6", CapipPropiedades.CAPIP_FUNC_6);
        _param.put("func_7", CapipPropiedades.CAPIP_FUNC_7);
        _param.put("func_8", CapipPropiedades.CAPIP_FUNC_8);

        final Class aClass = FrmPrincipal.getInstance().getClass();
        _param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        _param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/islr_retencion.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), _param, Conn.getConnection()), false);
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de impuesto no encontrado");
        }
    }

    /**
     * Rev 13/10/2016
     *
     * @param _id
     * @param _param
     * @throws Exception
     */
    public static void genImpMunReport(final long _id, final Map<String, Object> _param) throws Exception {

        final ImpMunModel regImpMun = ImpMunModel.getxID(_id);
        if (regImpMun == null) {
            JOptionPane.showMessageDialog(null, "Registro no encontrado");
            return;
        }

        StringBuilder numCaus = null;
        StringBuilder numComprs = null;
        final ArrayList<ImpMun_DetModel> list = ImpMun_DetModel.getxId_retencion(_id);

        // Recorrrer la lista de los detalles insertados
        for (ImpMun_DetModel reg : list) {
            final String sid_causado = String.valueOf(reg.getId_causado());
            final String sid_compr = String.valueOf(reg.getId_compr());

            // Llevar la cuenta del numero de causados
            if (numCaus == null) {
                numCaus = new StringBuilder(sid_causado);
                numComprs = new StringBuilder(sid_compr);
            } else {
                // Verificar que no se halla añadido el elemento
                if (numCaus.indexOf(sid_causado) < 0) {
                    numCaus.append(", ").append(sid_causado);
                }

                if (numComprs != null) {
                    numComprs.append(", ").append(sid_compr);
                }
            }

        }

        _param.put("anulado", regImpMun.getAnulado_sn().equals("S") ? "ANULADO" : "");
        _param.put("aux_1", CapipPropiedades.CAPIP_AUX_1);
        _param.put("aux_2", CapipPropiedades.CAPIP_AUX_2);
        _param.put("num", _id);
        _param.put("cliente_razonsocial", CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        _param.put("cliente_rif_ci", CapipPropiedades.CAPIP_CLIENTE_RIF_CI);
        _param.put("cliente_domicilio_fiscal", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);
        _param.put("controloret", _id / 100_000_000L);
        _param.put("numcaus", numCaus == null ? "" : numCaus.toString());
        _param.put("numcomprs", numComprs == null ? "" : numComprs.toString());
        _param.put("contribuyente", regImpMun.getBenef_razonsocial());
        _param.put("rif", regImpMun.getBenef_rif_ci());
        _param.put("fechahoy", new java.sql.Date(Globales.getServerTimeStamp().getTime()));
        _param.put("iduser", UserPassIn.getIdUser());
        _param.put("idsession", UserPassIn.getIdSession());
        _param.put("linea_1", CapipPropiedades.CAPIP_LINEA_1);
        _param.put("linea_2", CapipPropiedades.CAPIP_LINEA_2);
        _param.put("linea_3", CapipPropiedades.CAPIP_LINEA_3);
        _param.put("linea_4", CapipPropiedades.CAPIP_LINEA_4);
        _param.put("user", UserPassIn.getUserName());
        _param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        _param.put("ejefis", Long.parseLong(regImpMun.getEjefis().toString().substring(0, 4)));

        final Class aClass = FrmPrincipal.getInstance().getClass();
        _param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        _param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/imp_mun.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), _param, Conn.getConnection()), false);
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de Impuesto Municipal no encontrado");
        }
    }

    /**
     * Rev 13/10/2016
     *
     * @param _id
     * @param _param
     * @throws Exception
     */
    public static void genNegPriReport(final long _id, final Map<String, Object> _param) throws Exception {

        final NegPriModel regNegPri = NegPriModel.getxID(_id);
        if (regNegPri == null) {
            JOptionPane.showMessageDialog(null, "Registro no encontrado");
            return;
        }

        StringBuilder numCaus = null;
        StringBuilder numComprs = null;
        final ArrayList<NegPri_DetModel> list = NegPri_DetModel.getxId_retencion(_id);

        // Recorrrer la lista de los detalles insertados
        for (NegPri_DetModel reg : list) {
            final String sid_causado = String.valueOf(reg.getId_causado());
            final String sid_compr = String.valueOf(reg.getId_compr());

            // Llevar la cuenta del numero de causados
            if (numCaus == null) {
                numCaus = new StringBuilder(sid_causado);
                numComprs = new StringBuilder(sid_compr);
            } else {
                // Verificar que no se halla añadido el elemento
                if (numCaus.indexOf(sid_causado) < 0) {
                    numCaus.append(", ").append(sid_causado);
                }

                if (numComprs != null) {
                    numComprs.append(", ").append(sid_compr);
                }
            }

        }

        _param.put("anulado", regNegPri.getAnulado_sn().equals("S") ? "ANULADO" : "");
        _param.put("aux_1", CapipPropiedades.CAPIP_AUX_1);
        _param.put("aux_2", CapipPropiedades.CAPIP_AUX_2);
        _param.put("num", _id);
        _param.put("cliente_razonsocial", CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        _param.put("cliente_rif_ci", CapipPropiedades.CAPIP_CLIENTE_RIF_CI);
        _param.put("cliente_domicilio_fiscal", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);
        _param.put("controloret", _id / 100_000_000L);
        _param.put("numcaus", numCaus == null ? "" : numCaus.toString());
        _param.put("numcomprs", numComprs == null ? "" : numComprs.toString());
        _param.put("contribuyente", regNegPri.getBenef_razonsocial());
        _param.put("rif", regNegPri.getBenef_rif_ci());
        _param.put("fechahoy", new java.sql.Date(Globales.getServerTimeStamp().getTime()));
        _param.put("iduser", UserPassIn.getIdUser());
        _param.put("idsession", UserPassIn.getIdSession());
        _param.put("linea_1", CapipPropiedades.CAPIP_LINEA_1);
        _param.put("linea_2", CapipPropiedades.CAPIP_LINEA_2);
        _param.put("linea_3", CapipPropiedades.CAPIP_LINEA_3);
        _param.put("linea_4", CapipPropiedades.CAPIP_LINEA_4);
        _param.put("user", UserPassIn.getUserName());
        _param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        _param.put("ejefis", Long.parseLong(regNegPri.getEjefis().toString().substring(0, 4)));

        _param.put("desc_1", CapipPropiedades.CAPIP_DESC_1);
        _param.put("desc_2", CapipPropiedades.CAPIP_DESC_2);
        _param.put("desc_3", CapipPropiedades.CAPIP_DESC_3);
        _param.put("desc_4", CapipPropiedades.CAPIP_DESC_4);
        _param.put("desc_5", CapipPropiedades.CAPIP_DESC_5);
        _param.put("desc_6", CapipPropiedades.CAPIP_DESC_6);
        _param.put("desc_7", CapipPropiedades.CAPIP_DESC_7);
        _param.put("desc_8", CapipPropiedades.CAPIP_DESC_8);

        _param.put("func_1", CapipPropiedades.CAPIP_FUNC_1);
        _param.put("func_2", CapipPropiedades.CAPIP_FUNC_2);
        _param.put("func_3", CapipPropiedades.CAPIP_FUNC_3);
        _param.put("func_4", CapipPropiedades.CAPIP_FUNC_4);
        _param.put("func_5", CapipPropiedades.CAPIP_FUNC_5);
        _param.put("func_6", CapipPropiedades.CAPIP_FUNC_6);
        _param.put("func_7", CapipPropiedades.CAPIP_FUNC_7);
        _param.put("func_8", CapipPropiedades.CAPIP_FUNC_8);

        final Class aClass = FrmPrincipal.getInstance().getClass();
        _param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        _param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/neg_pri.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), _param, Conn.getConnection()), false);
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de Negro Primero no encontrado");
        }
    }

    /**
     * Rev 13/10/2016
     *
     * @param _id
     * @param _param
     * @throws Exception
     */
    public static void genORetReport(final long _id, final Map<String, Object> _param) throws Exception {

        final OtrasRet_Model regORet = OtrasRet_Model.getxID(_id);
        if (regORet == null) {
            JOptionPane.showMessageDialog(null, "Registro no encontrado");
            return;
        }

        StringBuilder numCaus = null;
        StringBuilder numComprs = null;
        final ArrayList<OtrasRet_DetModel> list = OtrasRet_DetModel.getxId_retencion(_id);

        // Recorrrer la lista de los detalles insertados
        for (OtrasRet_DetModel reg : list) {
            final String sid_causado = String.valueOf(reg.getId_causado());
            final String sid_compr = String.valueOf(reg.getId_compr());

            // Llevar la cuenta del numero de causados
            if (numCaus == null) {
                numCaus = new StringBuilder(sid_causado);
                numComprs = new StringBuilder(sid_compr);
            } else {
                // Verificar que no se halla añadido el elemento
                if (numCaus.indexOf(sid_causado) < 0) {
                    numCaus.append(", ").append(sid_causado);
                }

                if (numComprs != null) {
                    numComprs.append(", ").append(sid_compr);
                }
            }

        }

        _param.put("administracion", CapipPropiedades.CAPIP_FUNC_2);
        _param.put("aux_1", CapipPropiedades.CAPIP_AUX_1);
        _param.put("aux_2", CapipPropiedades.CAPIP_AUX_2);
        _param.put("oretno", _id);
        _param.put("cliente_razonsocial", CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        _param.put("cliente_rif_ci", CapipPropiedades.CAPIP_CLIENTE_RIF_CI);
        _param.put("cliente_domicilio_fiscal", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);
        _param.put("controloret", _id / 100_000_000L);
        _param.put("numcaus", numCaus == null ? "" : numCaus.toString());
        _param.put("numcomprs", numComprs == null ? "" : numComprs.toString());
        _param.put("contribuyente", regORet.getBenef_razonsocial());
        _param.put("rif", regORet.getBenef_rif_ci());
        _param.put("contraloria", CapipPropiedades.CAPIP_FUNC_3);
        _param.put("fechahoy", new java.sql.Date(Globales.getServerTimeStamp().getTime()));
        _param.put("iduser", UserPassIn.getIdUser());
        _param.put("idsession", UserPassIn.getIdSession());
        _param.put("linea_1", CapipPropiedades.CAPIP_LINEA_1);
        _param.put("linea_2", CapipPropiedades.CAPIP_LINEA_2);
        _param.put("linea_3", CapipPropiedades.CAPIP_LINEA_3);
        _param.put("linea_4", CapipPropiedades.CAPIP_LINEA_4);
        _param.put("user", UserPassIn.getUserName());
        _param.put("presupuesto", CapipPropiedades.CAPIP_FUNC_1);
        _param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        _param.put("ejefis", Long.parseLong(regORet.getEjefis().toString().substring(0, 4)));

        _param.put("desc_1", CapipPropiedades.CAPIP_DESC_1);
        _param.put("desc_2", CapipPropiedades.CAPIP_DESC_2);
        _param.put("desc_3", CapipPropiedades.CAPIP_DESC_3);
        _param.put("desc_4", CapipPropiedades.CAPIP_DESC_4);
        _param.put("desc_5", CapipPropiedades.CAPIP_DESC_5);
        _param.put("desc_6", CapipPropiedades.CAPIP_DESC_6);
        _param.put("desc_7", CapipPropiedades.CAPIP_DESC_7);
        _param.put("desc_8", CapipPropiedades.CAPIP_DESC_8);

        _param.put("func_1", CapipPropiedades.CAPIP_FUNC_1);
        _param.put("func_2", CapipPropiedades.CAPIP_FUNC_2);
        _param.put("func_3", CapipPropiedades.CAPIP_FUNC_3);
        _param.put("func_4", CapipPropiedades.CAPIP_FUNC_4);
        _param.put("func_5", CapipPropiedades.CAPIP_FUNC_5);
        _param.put("func_6", CapipPropiedades.CAPIP_FUNC_6);
        _param.put("func_7", CapipPropiedades.CAPIP_FUNC_7);
        _param.put("func_8", CapipPropiedades.CAPIP_FUNC_8);

        final Class aClass = FrmPrincipal.getInstance().getClass();
        _param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        _param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/oret.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), _param, Conn.getConnection()), false);
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de Otras Retenciones no encontrado");
        }
    }

    /**
     * Rev 15/10/2016
     *
     */
    private void IvaInsertarDetalle() {

        if (tblIvaMaster.getRowCount() <= 0) {
            return;
        }

        int selRow = tblIvaMaster.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        DefaultTableModel modelDet = (DefaultTableModel) tblIvaDetail.getModel();

        final CauModel regCau = (CauModel) tblIvaMaster.getValueAt(selRow, IVACOL_REG);

        // Es el primer registro a insertar
        if (tblIvaDetail.getRowCount() <= 0) {
            ivaBenef_razonsocial = regCau.getBenef_razonsocial();
            ivaBenef_rif_ci = regCau.getBenef_rif_ci();
            txtIVAContribuyente.setText(ivaBenef_razonsocial);
        } else {
            // Verificar si es el mismo beneficiario
            if (!(regCau.getBenef_razonsocial().equals(ivaBenef_razonsocial))) {
                JOptionPane.showMessageDialog(null, "Seleccione un mismo Contribuyente a la vez");
                return;
            }

            // Verificar que si ya ha sido incluido
            for (int i = 0; i < tblIvaDetail.getRowCount(); i++) {
                if ((regCau.getId_causado() == (long) tblIvaDetail.getValueAt(i, 0))) {
                    return;
                }
            }
        }

        final TipoCompr tipoCompr = regCau.getTipo_compr();
        final String stipo_compr = regCau.getTipo_compr().name();

        // Buscar todas los compromisos Asociados al NumCau
        try {

            // Recuperar todos los compromisos/facturas asociados a este numero de causado
            final ResultSet rsCompr = Conn.executeQuery("SELECT * FROM " + tipoCompr.getTbl() + " WHERE id_causado=" + regCau.getId_causado());

            while (rsCompr.next()) {
                ComprModel regCompr = new ComprModel(rsCompr);

                Object[] dato = new Object[17];
                dato[IVACOLDET_ID_CAUSADO] = regCau.getId_causado(); // dato[0] = numord;
                dato[IVACOLDET_FECHA_FACT] = regCompr.getFechaFact(); // dato[1] = ffactura;
                dato[IVACOLDET_NUM_FACT] = regCompr.getNumFact(); // Num. Factura
                dato[IVACOLDET_TIPO_COMPR] = stipo_compr;
                dato[IVACOLDET_NUM_COMPR] = regCompr.getId_compromiso(); // Num. Control
                dato[IVACOLDET_NDEBITO] = ""; // num de N. Debito
                dato[IVACOLDET_NCREDITO] = ""; // num de N. Credito
                dato[IVACOLDET_TRANSACCION] = "C"; // Transacccion
                dato[IVACOLDET_FACTURA_AFT] = ""; // Factura AFT
                dato[IVACOLDET_TOTAL] = regCompr.getTotal_bs().doubleValue();
                dato[IVACOLDET_EXENTO] = regCompr.getBase_imponible_bs().subtract(regCompr.getIva_grav_bs().setScale(2, RoundingMode.HALF_UP)).doubleValue(); // Exento
                dato[IVACOLDET_BASE_IMPONIBLE] = regCompr.getBase_imponible_bs().doubleValue(); // Base imponible
                dato[IVACOLDET_ALICUOTA] = regCompr.getIva_porc_aplic().doubleValue();
                dato[IVACOLDET_IVA_BS] = regCompr.getIva_grav_bs().multiply(regCompr.getIva_porc_aplic().movePointLeft(2)).setScale(2, HALF_UP).doubleValue(); // iva_bs
                dato[IVACOLDET_IVA_RETENIDO] = 0.00d; // iva retenido
                dato[IVACOLDET_OBS] = regCompr.getObservacion();
                dato[IVACOLDET_REG] = regCompr;
                modelDet.addRow(dato);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la Base de Datos: " + System.getProperty("line.separator") + _ex);
        }

        calcularIvaTotal();
        if ((tblIvaDetail.getRowCount() > 0) && !btnIva75.isEnabled()) {
            btnIva75.setEnabled(true);
            btnIva100.setEnabled(true);
            btnIvaEliminar.setEnabled(true);
            txtIvaRet.setText("0,00");
        }
    }

    /**
     * Rev 15/10/2016
     *
     */
    private void IslrInsertarDetalle() {

        if (tblIslrMaster.getRowCount() <= 0) {
            return;
        }

        int selRow = tblIslrMaster.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        DefaultTableModel modelDet = (DefaultTableModel) tblIslrDetail.getModel();

        final CauModel regCau = (CauModel) tblIslrMaster.getValueAt(selRow, ISLRCOL_REG);

        // Es el primer registro a insertar
        if (tblIslrDetail.getRowCount() <= 0) {
            islrBenef_razonsocial = regCau.getBenef_razonsocial();
            islrBenef_rif_ci = regCau.getBenef_rif_ci();
            txtIslrContribuyente.setText(islrBenef_razonsocial);
        } else {
            // Verificar si es el mismo beneficiario
            if (!(regCau.getBenef_razonsocial().equals(islrBenef_razonsocial))) {
                JOptionPane.showMessageDialog(null, "Seleccione un mismo Contribuyente a la vez");
                return;
            }

            // Verificar que si ya ha sido incluido
            for (int i = 0; i < tblIslrDetail.getRowCount(); i++) {
                if ((regCau.getId_causado() == (long) tblIslrDetail.getValueAt(i, 0))) {
                    return;
                }
            }
        }

        final TipoCompr tipoCompr = regCau.getTipo_compr();
        final String stipo_compr = regCau.getTipo_compr().name();

        // Buscar todas los compromisos Asociados al NumCau
        try {

            // Recuperar todos los compromisos/facturas asociados a este numero de causado
            final ResultSet rsCompr = Conn.executeQuery("SELECT * FROM " + tipoCompr.getTbl() + " WHERE id_causado=" + regCau.getId_causado());

            while (rsCompr.next()) {
                ComprModel regCompr = new ComprModel(rsCompr);

                Object[] dato = new Object[12];
                dato[ISLRCOLDET_ID_CAUSADO] = regCau.getId_causado(); // dato[0] = numord;
                dato[ISLRCOLDET_FECHA_FACT] = regCompr.getFechaFact(); // dato[1] = ffactura;
                dato[ISLRCOLDET_NUM_FACT] = regCompr.getNumFact(); // Num. Factura
                dato[ISLRCOLDET_TIPO_COMPR] = stipo_compr;
                dato[ISLRCOLDET_NUM_COMPR] = regCompr.getId_compromiso();
                dato[ISLRCOLDET_TOTAL] = regCompr.getTotal_bs().doubleValue();
                dato[ISLRCOLDET_BASE_IMPONIBLE] = regCompr.getBase_imponible_bs().doubleValue();
                dato[ISLRCOLDET_GRAVABLE] = regCompr.getIslr_grav_bs().doubleValue();
                dato[ISLRCOLDET_PORC_RETENCION] = 0.00d; // En principio es el mismo para todos, pero es editable
                dato[ISLRCOLDET_RETENSION_BS] = 0.00d; // Exento
                dato[ISLRCOLDET_OBS] = regCompr.getObservacion();
                dato[ISLRCOLDET_REG] = regCompr;
                modelDet.addRow(dato);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la Base de Datos: " + System.getProperty("line.separator") + _ex);
        }

        calcularIslrTotal();
        if ((tblIslrDetail.getRowCount() >= 1) && !cmbIslrPorc.isEnabled()) {
            cmbIslrPorc.setEnabled(true);
            btnIslrEliminar.setEnabled(true);
        }
    }

    /**
     * Rev 15/10/2016
     *
     */
    private void ImpMunInsertarDetalle() {

        if (tblImpMunMaster.getRowCount() <= 0) {
            return;
        }

        int selRow = tblImpMunMaster.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        DefaultTableModel modelDet = (DefaultTableModel) tblImpMunDetail.getModel();

        final CauModel regCau = (CauModel) tblImpMunMaster.getValueAt(selRow, ORETCOL_REG);

        // Es el primer registro a insertar
        if (tblImpMunDetail.getRowCount() <= 0) {
            impMunBenef_razonsocial = regCau.getBenef_razonsocial();
            impMunBenef_rif_ci = regCau.getBenef_rif_ci();
            txtImpMunContribuyente.setText(impMunBenef_razonsocial);
        } else {
            // Verificar si es el mismo beneficiario
            if (!(regCau.getBenef_razonsocial().equals(impMunBenef_razonsocial))) {
                JOptionPane.showMessageDialog(null, "Seleccione un mismo Contribuyente a la vez");
                return;
            }

            // Verificar que si ya ha sido incluido
            for (int i = 0; i < tblImpMunDetail.getRowCount(); i++) {
                if ((regCau.getId_causado() == (long) tblImpMunDetail.getValueAt(i, 0))) {
                    return;
                }
            }
        }

        final TipoCompr tipoCompr = regCau.getTipo_compr();
        final String stipo_compr = regCau.getTipo_compr().name();

        // Buscar todas los compromisos Asociados al NumCau
        try {

            // Recuperar todos los compromisos/facturas asociados a este numero de causado
            final ResultSet rsCompr = Conn.executeQuery("SELECT * FROM " + tipoCompr.getTbl() + " WHERE id_causado=" + regCau.getId_causado());

            Object[] dato = new Object[11];
            while (rsCompr.next()) {
                ComprModel regCompr = new ComprModel(rsCompr);

                dato[ORETCOLDET_ID_CAUSADO] = regCau.getId_causado(); // dato[0] = numord;
                dato[ORETCOLDET_FECHA_FACT] = regCompr.getFechaFact(); // dato[1] = ffactura;
                dato[ORETCOLDET_NUM_FACT] = regCompr.getNumFact(); // Num. Factura
                dato[ORETCOLDET_TIPO_COMPR] = stipo_compr;
                dato[ORETCOLDET_NUM_COMPR] = regCompr.getId_compromiso();
                dato[ORETCOLDET_TOTAL] = regCompr.getTotal_bs().doubleValue();
                dato[ORETCOLDET_BASE_IMPONIBLE] = regCompr.getBase_imponible_bs().doubleValue();
                dato[ORETCOLDET_GRAVABLE] = regCompr.getBase_imponible_bs().doubleValue();
                dato[ORETCOLDET_RETENIDO_BS] = 0.00d; // Exento
                dato[ORETCOLDET_OBS] = regCompr.getObservacion();
                dato[ORETCOLDET_REG] = regCompr;
                modelDet.addRow(dato);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la Base de Datos: " + System.getProperty("line.separator") + _ex);
        }

        calcularImpMunTotal();
        if (tblImpMunDetail.getRowCount() >= 1) {
            btnImpMunEliminar.setEnabled(true);
        }
    }

    /**
     * Rev 15/10/2016
     *
     */
    private void NegPriInsertarDetalle() {

        if (tblNegPriMaster.getRowCount() <= 0) {
            return;
        }

        int selRow = tblNegPriMaster.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        DefaultTableModel modelDet = (DefaultTableModel) tblNegPriDetail.getModel();

        final CauModel regCau = (CauModel) tblNegPriMaster.getValueAt(selRow, ORETCOL_REG);

        // Es el primer registro a insertar
        if (tblNegPriDetail.getRowCount() <= 0) {
            negPriBenef_razonsocial = regCau.getBenef_razonsocial();
            negPriBenef_rif_ci = regCau.getBenef_rif_ci();
            txtNegPriContribuyente.setText(negPriBenef_razonsocial);
        } else {
            // Verificar si es el mismo beneficiario
            if (!(regCau.getBenef_razonsocial().equals(negPriBenef_razonsocial))) {
                JOptionPane.showMessageDialog(null, "Seleccione un mismo Contribuyente a la vez");
                return;
            }

            // Verificar que si ya ha sido incluido
            for (int i = 0; i < tblNegPriDetail.getRowCount(); i++) {
                if ((regCau.getId_causado() == (long) tblIslrDetail.getValueAt(i, 0))) {
                    return;
                }
            }
        }

        final TipoCompr tipoCompr = regCau.getTipo_compr();
        final String stipo_compr = regCau.getTipo_compr().name();

        // Buscar todas los compromisos Asociados al NumCau
        try {

            // Recuperar todos los compromisos/facturas asociados a este numero de causado
            final ResultSet rsCompr = Conn.executeQuery("SELECT * FROM " + tipoCompr.getTbl() + " WHERE id_causado=" + regCau.getId_causado());

            Object[] dato = new Object[11];
            while (rsCompr.next()) {
                ComprModel regCompr = new ComprModel(rsCompr);

                dato[ORETCOLDET_ID_CAUSADO] = regCau.getId_causado(); // dato[0] = numord;
                dato[ORETCOLDET_FECHA_FACT] = regCompr.getFechaFact(); // dato[1] = ffactura;
                dato[ORETCOLDET_NUM_FACT] = regCompr.getNumFact(); // Num. Factura
                dato[ORETCOLDET_TIPO_COMPR] = stipo_compr;
                dato[ORETCOLDET_NUM_COMPR] = regCompr.getId_compromiso();
                dato[ORETCOLDET_TOTAL] = regCompr.getTotal_bs().doubleValue();
                dato[ORETCOLDET_BASE_IMPONIBLE] = regCompr.getBase_imponible_bs().doubleValue();
                dato[ORETCOLDET_GRAVABLE] = regCompr.getBase_imponible_bs().doubleValue();
                dato[ORETCOLDET_RETENIDO_BS] = 0.00d; // Exento
                dato[ORETCOLDET_OBS] = regCompr.getObservacion();
                dato[ORETCOLDET_REG] = regCompr;
                modelDet.addRow(dato);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la Base de Datos: " + System.getProperty("line.separator") + _ex);
        }

        calcularNegPriTotal();
        if (tblNegPriDetail.getRowCount() >= 1) {
            btnNegPriEliminar.setEnabled(true);
        }
    }

    /**
     * Rev 14/04/2017
     *
     */
    private void ORetInsertarDetalle() {

        if (tblORetMaster.getRowCount() <= 0) {
            return;
        }

        int selRow = tblORetMaster.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        DefaultTableModel modelDet = (DefaultTableModel) tblORetDetail.getModel();

        final CauModel regCau = (CauModel) tblORetMaster.getValueAt(selRow, ORETCOL_REG);

        // Es el primer registro a insertar
        if (tblORetDetail.getRowCount() <= 0) {
            oRetBenef_razonsocial = regCau.getBenef_razonsocial();
            oRetBenef_rif_ci = regCau.getBenef_rif_ci();
            txtORetContribuyente.setText(oRetBenef_razonsocial);
        } else {
            // Verificar si es el mismo beneficiario
            if (!(regCau.getBenef_razonsocial().equals(oRetBenef_razonsocial))) {
                JOptionPane.showMessageDialog(null, "Seleccione un mismo Contribuyente a la vez");
                return;
            }

            // Verificar que si ya ha sido incluido
            for (int i = 0; i < tblORetDetail.getRowCount(); i++) {
                if ((regCau.getId_causado() == (long) tblORetDetail.getValueAt(i, 0))) {
                    return;
                }
            }
        }

        final TipoCompr tipoCompr = regCau.getTipo_compr();
        final String stipo_compr = regCau.getTipo_compr().name();

        // Buscar todas los compromisos Asociados al NumCau
        try {

            // Recuperar todos los compromisos/facturas asociados a este numero de causado
            final ResultSet rsCompr = Conn.executeQuery("SELECT * FROM " + tipoCompr.getTbl() + " WHERE id_causado=" + regCau.getId_causado());

            Object[] dato = new Object[11];
            while (rsCompr.next()) {
                ComprModel regCompr = new ComprModel(rsCompr);

                dato[ORETCOLDET_ID_CAUSADO] = regCau.getId_causado(); // dato[0] = numord;
                dato[ORETCOLDET_FECHA_FACT] = regCompr.getFechaFact(); // dato[1] = ffactura;
                dato[ORETCOLDET_NUM_FACT] = regCompr.getNumFact(); // Num. Factura
                dato[ORETCOLDET_TIPO_COMPR] = stipo_compr;
                dato[ORETCOLDET_NUM_COMPR] = regCompr.getId_compromiso();
                dato[ORETCOLDET_TOTAL] = regCompr.getTotal_bs().doubleValue();
                dato[ORETCOLDET_BASE_IMPONIBLE] = regCompr.getBase_imponible_bs().doubleValue();
                dato[ORETCOLDET_GRAVABLE] = regCompr.getBase_imponible_bs().doubleValue();
                dato[ORETCOLDET_RETENIDO_BS] = 0.00d; // Exento
                dato[ORETCOLDET_OBS] = regCompr.getObservacion();
                dato[ORETCOLDET_REG] = regCompr;
                modelDet.addRow(dato);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la Base de Datos: " + System.getProperty("line.separator") + _ex);
        }

        calcularORetTotal();
        if (tblORetDetail.getRowCount() >= 1) {
            btnORetEliminar.setEnabled(true);
        }
    }

    /**
     * Rev 23/10/2016
     */
    private void calcularIvaTotal() {
        BigDecimal retenido_bs = BigDecimal.ZERO;
        for (int i = 0; i < tblIvaDetail.getRowCount(); i++) {
            retenido_bs = retenido_bs.add(BigDecimal.valueOf((double) tblIvaDetail.getValueAt(i, IVACOLDET_IVA_RETENIDO)));
        }

        txtIvaRet.setText(Format.toStr(retenido_bs));
    }

    /**
     * Rev 23/10/2016
     */
    private void calcularIslrTotal() {

        // El centinela es utilizado para determinar que todos los "detail" tengan su impuesto retenido
        boolean cent = true;
        BigDecimal retenido_bs = BigDecimal.ZERO;
        for (int i = 0; i < tblIslrDetail.getRowCount(); i++) {
            retenido_bs = retenido_bs.add(BigDecimal.valueOf((double) tblIslrDetail.getValueAt(i, ISLRCOLDET_RETENSION_BS)));
            if (cent && ((double) tblIslrDetail.getValueAt(i, ISLRCOLDET_PORC_RETENCION) <= 0.00d)) {
                cent = false;
            }
        }

        txtIslrRetenido.setText(Format.toStr(retenido_bs));
        btnIslrGuardar.setEnabled(cent && (retenido_bs.compareTo(BigDecimal.ZERO) > 0));
    }

    /**
     * Rev 14-04-2017
     */
    private void calcularImpMunTotal() {

        // El centinela es utilizado para determinar que todos los "detail" tengan su impuesto retenido
        boolean cent = true;
        BigDecimal retenido_bs = BigDecimal.ZERO;
        for (int i = 0; i < tblImpMunDetail.getRowCount(); i++) {
            final double aux_base_imp_bs = (double) tblImpMunDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE);
            final double aux_grav_bs = (double) tblImpMunDetail.getValueAt(i, ORETCOLDET_GRAVABLE);
            final double aux_ret_bs = (double) tblImpMunDetail.getValueAt(i, ORETCOLDET_RETENIDO_BS);

            if (aux_grav_bs > aux_base_imp_bs) {
                JOptionPane.showMessageDialog(this, "Error, el Gravable no puede ser mayor que la Base Imponible");
                retenido_bs = BigDecimal.ZERO;
                tblImpMunDetail.setValueAt(0.00d, i, ORETCOLDET_GRAVABLE);
                break; // condicion de error, inicializa y rompe el ciclo
            }

            if (aux_ret_bs > aux_grav_bs) {
                JOptionPane.showMessageDialog(this, "Error, el Retenido no puede ser mayor que el Gravable");
                retenido_bs = BigDecimal.ZERO;
                tblImpMunDetail.setValueAt(0.00d, i, ORETCOLDET_RETENIDO_BS);
                break; // condicion de error, inicializa y rompe el ciclo
            }

            if (aux_ret_bs > 0.90 * aux_grav_bs) {
                JOptionPane.showMessageDialog(this, "Advertencia, el Retenido supera el 10% del monto Gravable");
            }

            retenido_bs = retenido_bs.add(BigDecimal.valueOf(aux_ret_bs));

            if (aux_ret_bs > 0) {
                btnImpMunRevertir.setEnabled(true);
            } else {
                if (cent) {
                    cent = false; // Valor invalido
                }
            }

            if (cent && (aux_ret_bs <= 0)) {
                cent = false; // Valor invalido
            }
        }

        txtImpMunRetenido.setText(Format.toStr(retenido_bs));
        btnImpMunGuardar.setEnabled(cent && (retenido_bs.compareTo(BigDecimal.ZERO) > 0));
    }

    /**
     * Rev 03-03-2017
     */
    private void calcularNegPriTotal() {

        // El centinela es utilizado para determinar que todos los "detail" tengan su impuesto retenido
        boolean cent = true;
        BigDecimal retenido_bs = BigDecimal.ZERO;
        for (int i = 0; i < tblNegPriDetail.getRowCount(); i++) {
            final double aux_base_imp_bs = (double) tblNegPriDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE);
            final double aux_grav_bs = (double) tblNegPriDetail.getValueAt(i, ORETCOLDET_GRAVABLE);
            final double aux_ret_bs = (double) tblNegPriDetail.getValueAt(i, ORETCOLDET_RETENIDO_BS);

            if (aux_grav_bs > aux_base_imp_bs) {
                JOptionPane.showMessageDialog(this, "Error, el Gravable no puede ser mayor que la Base Imponible");
                retenido_bs = BigDecimal.ZERO;
                tblNegPriDetail.setValueAt(0.00d, i, ORETCOLDET_GRAVABLE);
                break; // condicion de error, inicializa y rompe el ciclo
            }

            if (aux_ret_bs > aux_grav_bs) {
                JOptionPane.showMessageDialog(this, "Error, el Retenido no puede ser mayor que el Gravable");
                retenido_bs = BigDecimal.ZERO;
                tblNegPriDetail.setValueAt(0.00d, i, ORETCOLDET_RETENIDO_BS);
                break; // condicion de error, inicializa y rompe el ciclo
            }

            if (aux_ret_bs > 0.90 * aux_grav_bs) {
                JOptionPane.showMessageDialog(this, "Advertencia, el Retenido supera el 10% del monto Gravable");
            }

            retenido_bs = retenido_bs.add(BigDecimal.valueOf(aux_ret_bs));

            if (aux_ret_bs > 0) {
                btnNegPriRevertir.setEnabled(true);
            } else {
                if (cent) {
                    cent = false; // Valor invalido
                }
            }

            if (cent && (aux_ret_bs <= 0)) {
                cent = false; // Valor invalido
            }
        }

        txtNegPriRetenido.setText(Format.toStr(retenido_bs));
        btnNegPriGuardar.setEnabled(cent && (retenido_bs.compareTo(BigDecimal.ZERO) > 0));
    }

    /**
     * Rev 03-03-2017
     */
    private void calcularORetTotal() {

        // El centinela es utilizado para determinar que todos los "detail" tengan su impuesto retenido
        boolean cent = true;
        BigDecimal retenido_bs = BigDecimal.ZERO;
        for (int i = 0; i < tblORetDetail.getRowCount(); i++) {
            final double aux_base_imp_bs = (double) tblORetDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE);
            final double aux_grav_bs = (double) tblORetDetail.getValueAt(i, ORETCOLDET_GRAVABLE);
            final double aux_ret_bs = (double) tblORetDetail.getValueAt(i, ORETCOLDET_RETENIDO_BS);

            if (aux_grav_bs > aux_base_imp_bs) {
                JOptionPane.showMessageDialog(this, "Error, el Gravable no puede ser mayor que la Base Imponible");
                retenido_bs = BigDecimal.ZERO;
                tblORetDetail.setValueAt(0.00d, i, ORETCOLDET_GRAVABLE);
                break; // condicion de error, inicializa y rompe el ciclo
            }

            if (aux_ret_bs > aux_grav_bs) {
                JOptionPane.showMessageDialog(this, "Error, el Retenido no puede ser mayor que el Gravable");
                retenido_bs = BigDecimal.ZERO;
                tblORetDetail.setValueAt(0.00d, i, ORETCOLDET_RETENIDO_BS);
                break; // condicion de error, inicializa y rompe el ciclo
            }

            if (aux_ret_bs > 0.90 * aux_grav_bs) {
                JOptionPane.showMessageDialog(this, "Advertencia, el Retenido supera el 10% del monto Gravable");
            }

            retenido_bs = retenido_bs.add(BigDecimal.valueOf(aux_ret_bs));

            if (aux_ret_bs > 0) {
                btnORetRevertir.setEnabled(true);
            } else {
                if (cent) {
                    cent = false; // Valor invalido
                }
            }

            if (cent && (aux_ret_bs <= 0)) {
                cent = false; // Valor invalido
            }
        }

        txtORetRetenido.setText(Format.toStr(retenido_bs));
        btnORetGuardar.setEnabled(cent && (retenido_bs.compareTo(BigDecimal.ZERO) > 0));
    }

    /**
     * Rev 15/10/2016
     */
    private void btnIva_RendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIva_RendActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new IVA_Rend(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnIva_RendActionPerformed

    private void tblIslrMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIslrMasterMouseClicked
        if (evt.getClickCount() == 2) {
            IslrInsertarDetalle();
        }
    }//GEN-LAST:event_tblIslrMasterMouseClicked

    private void btnIslrConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIslrConsultarActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new IslrRetencionConsultar(me).setVisible(true);
                me.setVisible(false);
            }
        });

    }//GEN-LAST:event_btnIslrConsultarActionPerformed

    /**
     * Rev 23/10/2016
     *
     * @param evt
     */
    private void btnIslrEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIslrEliminarActionPerformed
        int selRow = tblIslrMaster.getSelectedRow();
        if (selRow < 0) {
            java.awt.EventQueue.invokeLater(tblIslrMaster::requestFocusInWindow);
            return;
        }

        final long id_causado = (long) tblIslrMaster.getValueAt(selRow, ISLRCOL_ID_CAUSADO);

        // Borrar los registros de la tabla detalle
        final DefaultTableModel model = (DefaultTableModel) tblIslrDetail.getModel();
        for (int i = 0; i < tblIslrDetail.getRowCount();) {
            if (((long) tblIslrDetail.getValueAt(i, ISLRCOLDET_ID_CAUSADO)) == id_causado) {
                model.removeRow(i);
            } else {
                i++;
            }
        }

        // Limpiar el contribuyente
        if (tblIslrDetail.getRowCount() <= 0) {
            txtIslrContribuyente.setText("");
            islrBenef_razonsocial = "";
            islrBenef_rif_ci = "";
            cmbIslrPorc.setEnabled(false);
        }

        calcularIslrTotal();
    }//GEN-LAST:event_btnIslrEliminarActionPerformed

    private void btnIslrGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIslrGuardarActionPerformed
        actIslrGuardar();
    }//GEN-LAST:event_btnIslrGuardarActionPerformed

    /**
     * Poner a cero, el porcentaje de las retenciones
     *
     * @param evt
     */
    private void btnIvaRevertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIvaRevertirActionPerformed

        // Poner a cero, las retenciones
        for (int i = 0; i < tblIvaDetail.getRowCount(); i++) {
            tblIvaDetail.setValueAt(0.00d, i, IVACOLDET_IVA_RETENIDO);
        }

        calcularIvaTotal();

        btnIva75.setEnabled(true);
        btnIva100.setEnabled(true);
        btnIvaRevertir.setEnabled(false);
        btnIvaEliminar.setEnabled(true);
        btnIvaGuardar.setEnabled(false);
    }//GEN-LAST:event_btnIvaRevertirActionPerformed

    /**
     * Rev 16/10/2016
     *
     * @param evt
     */
    private void btnIvaEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIvaEliminarActionPerformed
        int selRow = tblIvaMaster.getSelectedRow();
        if (selRow < 0) {
            java.awt.EventQueue.invokeLater(tblIvaDetail::requestFocusInWindow);
            return;
        }

        final long id_causado = (long) tblIvaMaster.getValueAt(selRow, 0);

        // Borrar los registros de la tabla detalle
        final DefaultTableModel model = (DefaultTableModel) tblIvaDetail.getModel();
        for (int i = 0; i < tblIvaDetail.getRowCount();) {
            if (((long) tblIvaDetail.getValueAt(i, 0)) == id_causado) {
                model.removeRow(i);
            } else {
                i++;
            }
        }

        // Limpiar el contribuyente
        if (tblIvaDetail.getRowCount() <= 0) {
            txtIVAContribuyente.setText("");
            ivaBenef_razonsocial = "";
            ivaBenef_rif_ci = "";

            btnIva75.setEnabled(false);
            btnIva100.setEnabled(false);
        }

        calcularIvaTotal();
    }//GEN-LAST:event_btnIvaEliminarActionPerformed

    private void btnIvaConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIvaConsultarActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new IvaRetencionConsultar(me).setVisible(true);
                me.setVisible(false);
            }
        });

    }//GEN-LAST:event_btnIvaConsultarActionPerformed

    /**
     * Rev 15/10/2016
     *
     */
    private void btnIvaGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIvaGuardarActionPerformed
        actIvaGuardar();
    }//GEN-LAST:event_btnIvaGuardarActionPerformed

    /**
     * Rev 15/10/2016
     */
    private void btnIva100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIva100ActionPerformed
        if (tblIvaDetail.getRowCount() > 0) {
            calcularIvaRet(dec100);
        }
    }//GEN-LAST:event_btnIva100ActionPerformed

    /**
     * Rev 15/10/2016
     */
    private void btnIva75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIva75ActionPerformed
        if (tblIvaDetail.getRowCount() > 0) {
            calcularIvaRet(dec75);
        }
    }//GEN-LAST:event_btnIva75ActionPerformed

    private void btnIslrLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIslrLimpiarActionPerformed
        setStartConditions();
    }//GEN-LAST:event_btnIslrLimpiarActionPerformed

    private void btnIvaLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIvaLimpiarActionPerformed
        setStartConditions();
    }//GEN-LAST:event_btnIvaLimpiarActionPerformed

    /**
     * Rev 23/10/2016
     *
     * @param evt
     */
    private void btnIslrRevertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIslrRevertirActionPerformed

        // Poner a cero, las retenciones
        for (int i = 0; i < tblIslrDetail.getRowCount(); i++) {
            tblIslrDetail.setValueAt(tblIslrDetail.getValueAt(i, ISLRCOLDET_BASE_IMPONIBLE), i, ISLRCOLDET_GRAVABLE);
            tblIslrDetail.setValueAt(0.00d, i, ISLRCOLDET_PORC_RETENCION);
            tblIslrDetail.setValueAt(0.00d, i, ISLRCOLDET_RETENSION_BS);
        }

        calcularIslrTotal();

        cmbIslrPorc.setEnabled(true);
        cmbIslrPorc.setSelectedIndex(-1);
        btnIslrRevertir.setEnabled(false);
        btnIslrEliminar.setEnabled(true);
        btnIslrGuardar.setEnabled(false);
    }//GEN-LAST:event_btnIslrRevertirActionPerformed

    private void tblIvaMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIvaMasterMouseClicked
        if (evt.getClickCount() == 2) {
            IvaInsertarDetalle();
        }
    }//GEN-LAST:event_tblIvaMasterMouseClicked

    private void cmbIslrPorcItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIslrPorcItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            final int selIdx = cmbIslrPorc.getSelectedIndex();
            if (selIdx >= 0) {
                calcularIslrRet(porcRet[selIdx]);
                if (selIdx > 0) {
                    btnIslrGuardar.setEnabled(true);
                }
            }
        }
    }//GEN-LAST:event_cmbIslrPorcItemStateChanged

    private void btnISLR_RendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnISLR_RendActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ISLR_Rend(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnISLR_RendActionPerformed

    private void btnORetLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnORetLimpiarActionPerformed
        setStartConditions();
    }//GEN-LAST:event_btnORetLimpiarActionPerformed

    private void btnORetGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnORetGuardarActionPerformed
        actORetGuardar();
    }//GEN-LAST:event_btnORetGuardarActionPerformed

    private void btnORetEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnORetEliminarActionPerformed
        final int selRow = tblORetMaster.getSelectedRow();
        if (selRow < 0) {
            java.awt.EventQueue.invokeLater(tblORetMaster::requestFocusInWindow);
            return;
        }

        final long id_causado = (long) tblORetMaster.getValueAt(selRow, ORETCOL_ID_CAUSADO);

        // Borrar los registros de la tabla detalle
        final DefaultTableModel model = (DefaultTableModel) tblORetDetail.getModel();
        for (int i = 0; i < tblORetDetail.getRowCount();) {
            if (((long) tblORetDetail.getValueAt(i, ORETCOLDET_ID_CAUSADO)) == id_causado) {
                model.removeRow(i);
            } else {
                i++;
            }
        }

        // Limpiar el contribuyente
        if (tblORetDetail.getRowCount() <= 0) {
            txtORetContribuyente.setText("");
            oRetBenef_razonsocial = "";
            oRetBenef_rif_ci = "";
        }

        calcularORetTotal();

    }//GEN-LAST:event_btnORetEliminarActionPerformed

    private void btnORetConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnORetConsultarActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ORet_Consultar(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnORetConsultarActionPerformed

    private void tblORetMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblORetMasterMouseClicked
        if (evt.getClickCount() == 2) {
            ORetInsertarDetalle();
        }
    }//GEN-LAST:event_tblORetMasterMouseClicked

    private void btnORetRevertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnORetRevertirActionPerformed

        // Poner a cero, las retenciones
        for (int i = 0; i < tblORetDetail.getRowCount(); i++) {
            tblORetDetail.setValueAt(tblORetDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE), i, ORETCOLDET_GRAVABLE);
            tblORetDetail.setValueAt(0.00d, i, ORETCOLDET_RETENIDO_BS);
        }

        calcularORetTotal();

        btnORetRevertir.setEnabled(false);
        btnORetEliminar.setEnabled(true);
        btnORetGuardar.setEnabled(false);
    }//GEN-LAST:event_btnORetRevertirActionPerformed

    private void btnAnularIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularIVAActionPerformed
        if (JOptionPane.showConfirmDialog(this, "La operación de anulación de Retención es irreversible. ¿ Desea continuar ?",
            "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }

        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new IVA_Anular(me).setVisible(true);
                setVisible(false);
            }
        });
    }//GEN-LAST:event_btnAnularIVAActionPerformed

    private void btnAnularISLRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularISLRActionPerformed
        if (JOptionPane.showConfirmDialog(this, "La operación de anulación de Rendición es irreversible. ¿ Desea continuar ?",
            "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }

        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ISLR_Anular(me).setVisible(true);
                setVisible(false);
            }
        });
    }//GEN-LAST:event_btnAnularISLRActionPerformed

    private void btnAnularOtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularOtrasActionPerformed
        if (JOptionPane.showConfirmDialog(this, "La operación de anulación de Rendición es irreversible. ¿ Desea continuar ?",
            "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }

        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Otras_Anular(me).setVisible(true);
                setVisible(false);
            }
        });
    }//GEN-LAST:event_btnAnularOtrasActionPerformed

    private void btnRendicionOtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRendicionOtrasActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ORet_Rend(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnRendicionOtrasActionPerformed

    private void btnImpMunLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpMunLimpiarActionPerformed
        setStartConditions();
    }//GEN-LAST:event_btnImpMunLimpiarActionPerformed

    private void btnImpMunGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpMunGuardarActionPerformed
        actImpMunGuardar();
    }//GEN-LAST:event_btnImpMunGuardarActionPerformed

    private void btnImpMunEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpMunEliminarActionPerformed
        final int selRow = tblImpMunMaster.getSelectedRow();
        if (selRow < 0) {
            java.awt.EventQueue.invokeLater(tblORetMaster::requestFocusInWindow);
            return;
        }

        final long id_causado = (long) tblImpMunMaster.getValueAt(selRow, ORETCOL_ID_CAUSADO);

        // Borrar los registros de la tabla detalle
        final DefaultTableModel model = (DefaultTableModel) tblImpMunDetail.getModel();
        for (int i = 0; i < tblImpMunDetail.getRowCount();) {
            if (((long) tblImpMunDetail.getValueAt(i, ORETCOLDET_ID_CAUSADO)) == id_causado) {
                model.removeRow(i);
            } else {
                i++;
            }
        }

        // Limpiar el contribuyente
        if (tblImpMunDetail.getRowCount() <= 0) {
            txtImpMunContribuyente.setText("");
            impMunBenef_razonsocial = "";
            impMunBenef_rif_ci = "";
        }

        calcularImpMunTotal();
    }//GEN-LAST:event_btnImpMunEliminarActionPerformed

    private void btnImpMunConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpMunConsultarActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
//                new ImpMunRetencionConsultar(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnImpMunConsultarActionPerformed

    private void tblImpMunMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblImpMunMasterMouseClicked
        if (evt.getClickCount() == 2) {
            ImpMunInsertarDetalle();
        }
    }//GEN-LAST:event_tblImpMunMasterMouseClicked

    private void btnImpMunRevertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpMunRevertirActionPerformed

        // Poner a cero, las retenciones
        for (int i = 0; i < tblImpMunDetail.getRowCount(); i++) {
            tblImpMunDetail.setValueAt(tblImpMunDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE), i, ORETCOLDET_GRAVABLE);
            tblImpMunDetail.setValueAt(0.00d, i, ORETCOLDET_RETENIDO_BS);
        }

        calcularImpMunTotal();

        btnImpMunRevertir.setEnabled(false);
        btnImpMunEliminar.setEnabled(true);
        btnImpMunGuardar.setEnabled(false);
    }//GEN-LAST:event_btnImpMunRevertirActionPerformed

    private void btnAnularImpMunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularImpMunActionPerformed
        if (JOptionPane.showConfirmDialog(this, "La operación de anulación de Impuesto Municipal es irreversible. ¿ Desea continuar ?",
            "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }

        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ImpMun_Anular(me).setVisible(true);
                setVisible(false);
            }
        });
    }//GEN-LAST:event_btnAnularImpMunActionPerformed

    private void btnNegPriLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNegPriLimpiarActionPerformed
        setStartConditions();
    }//GEN-LAST:event_btnNegPriLimpiarActionPerformed

    private void btnNegPriGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNegPriGuardarActionPerformed
        actNegPriGuardar();
    }//GEN-LAST:event_btnNegPriGuardarActionPerformed

    private void btnNegPriEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNegPriEliminarActionPerformed
        final int selRow = tblNegPriMaster.getSelectedRow();
        if (selRow < 0) {
            java.awt.EventQueue.invokeLater(tblNegPriMaster::requestFocusInWindow);
            return;
        }

        final long id_causado = (long) tblNegPriMaster.getValueAt(selRow, ORETCOL_ID_CAUSADO);

        // Borrar los registros de la tabla detalle
        final DefaultTableModel model = (DefaultTableModel) tblORetDetail.getModel();
        for (int i = 0; i < tblNegPriDetail.getRowCount();) {
            if (((long) tblNegPriDetail.getValueAt(i, ORETCOLDET_ID_CAUSADO)) == id_causado) {
                model.removeRow(i);
            } else {
                i++;
            }
        }

        // Limpiar el contribuyente
        if (tblNegPriDetail.getRowCount() <= 0) {
            txtNegPriContribuyente.setText("");
            negPriBenef_razonsocial = "";
            negPriBenef_rif_ci = "";
        }

        calcularNegPriTotal();
    }//GEN-LAST:event_btnNegPriEliminarActionPerformed

    private void btnNegPriConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNegPriConsultarActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
//                new NegPriRetencionConsultar(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnNegPriConsultarActionPerformed

    private void tblNegPriMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNegPriMasterMouseClicked
        if (evt.getClickCount() == 2) {
            NegPriInsertarDetalle();
        }
    }//GEN-LAST:event_tblNegPriMasterMouseClicked

    private void btnNegPriRevertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNegPriRevertirActionPerformed
        // Poner a cero, las retenciones
        for (int i = 0; i < tblNegPriDetail.getRowCount(); i++) {
            tblNegPriDetail.setValueAt(tblNegPriDetail.getValueAt(i, ORETCOLDET_BASE_IMPONIBLE), i, ORETCOLDET_GRAVABLE);
            tblNegPriDetail.setValueAt(0.00d, i, ORETCOLDET_RETENIDO_BS);
        }

        calcularNegPriTotal();

        btnNegPriRevertir.setEnabled(false);
        btnNegPriEliminar.setEnabled(true);
        btnNegPriGuardar.setEnabled(false);
    }//GEN-LAST:event_btnNegPriRevertirActionPerformed

    private void btnAnularNegPriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularNegPriActionPerformed
        if (JOptionPane.showConfirmDialog(this, "La operación de anulación de Negro Primero es irreversible. ¿ Desea continuar ?",
            "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }

        java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new NegPri_Anular(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnAnularNegPriActionPerformed

    private void btnImpMun_RendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpMun_RendActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ImpMun_Rend(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnImpMun_RendActionPerformed

    private void btnNegPri_RendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNegPri_RendActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new NegPri_Rend(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_btnNegPri_RendActionPerformed

    /**
     * Rev 16/10/2016
     */
    void calcularIvaRet(final BigDecimal _porcRet) {
        iva_porc_ret = _porcRet;

        for (int i = 0; i < tblIvaDetail.getRowCount(); i++) {
            tblIvaDetail.setValueAt(BigDecimal.valueOf((double) tblIvaDetail.getValueAt(i, IVACOLDET_IVA_BS)).multiply(_porcRet).setScale(2, HALF_UP).doubleValue(), i, IVACOLDET_IVA_RETENIDO);
        }

        calcularIvaTotal();

        btnIva75.setEnabled(false);
        btnIva100.setEnabled(false);
        btnIvaRevertir.setEnabled(true);
        btnIvaEliminar.setEnabled(false);

        try {
            if (Format.toDouble(txtIvaRet.getText()) > 0) {
                btnIvaGuardar.setEnabled(true);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + _ex);
        }
    }

    /**
     * Rev 16/10/2016
     */
    void calcularIslrRet(final BigDecimal _porcRet) {
        for (int i = 0; i < tblIslrDetail.getRowCount(); i++) {
            double montoGravable = (double) tblIslrDetail.getValueAt(i, ISLRCOLDET_GRAVABLE);
            if (montoGravable > 0) {
                tblIslrDetail.setValueAt(_porcRet.doubleValue(), i, ISLRCOLDET_PORC_RETENCION);
                tblIslrDetail.setValueAt(BigDecimal.valueOf(montoGravable).multiply(_porcRet).movePointLeft(2).setScale(2, HALF_UP).doubleValue(), i, ISLRCOLDET_RETENSION_BS);
            } else {
                tblIslrDetail.setValueAt(0.00d, i, ISLRCOLDET_PORC_RETENCION);
                tblIslrDetail.setValueAt(0.00d, i, ISLRCOLDET_RETENSION_BS);
            }
        }

        calcularIslrTotal();

        cmbIslrPorc.setEnabled(false);
        btnIslrRevertir.setEnabled(true);
        btnIslrEliminar.setEnabled(false);

        try {
            if (Format.toDouble(txtIslrRetenido.getText()) > 0) {
                btnIslrGuardar.setEnabled(true);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + _ex);
        }
    }

    /**
     * Rev 10/10/2016
     */
    private void actSalir() {

        if (tblIvaDetail.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(this, "Hay registros pendientes IVA. ¿ Seguro desea Salir ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }
        }

        if (tblIslrDetail.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(this, "Hay registros pendientes ISLR. ¿ Seguro desea Salir ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }
        }

        if (tblImpMunDetail.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(this, "Hay registros pendientes en Impuesto Municipal. ¿ Seguro desea Salir ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }
        }

        if (tblNegPriDetail.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(this, "Hay registros pendientes en Negro Primero. ¿ Seguro desea Salir ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }
        }

        if (tblORetDetail.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(this, "Hay registros pendientes Otras Retenciones. ¿ Seguro desea Salir ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }
        }

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
     * La funcion es mantener actualizado el control iva, dentro de la estructura de autoincrement SQL
     *
     * Rev 14/09/2016
     *
     * @return
     */
    public static String checkIvaNextId() {

        final long id;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT MAX(id_iva_retencion) AS maxid FROM iva_retencion");
            if (rs.next()) {
                id = rs.getLong("maxid") + 1;
            } else {
                id = 0;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el siguiente número de Retención" + System.getProperty("line.separator") + _ex);
            return "";
        }

        // Verificar los casos para el ID
        final long next_id;
        final long inc = id % 100_000_000L;
        final long oldControl = id / 100_000_000L;
        final long actControl = Long.parseLong(new SimpleDateFormat("YYYYMM").format(Globales.getServerTimeStamp()));

        final long oldYear = oldControl / 100L;
        final long actYear = actControl / 100L;

        // Si difiere el control del IVA, se debe actualizar el incremento, ya que se ha detectado
        // un cambio de año y/o mes
        if (actYear > oldYear) {
            try {
                // reinicia la cuenta
                next_id = actControl * 100_000_000L + 1;

                Conn.getConnection().createStatement().execute("ALTER TABLE iva_retencion AUTO_INCREMENT= " + next_id);
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, "Error al trata de actualizar el ID" + System.getProperty("line.separator") + _ex);
                return "";
            }

        } else if (actYear == oldYear) {
            if (actControl > oldControl) {
                try {
                    next_id = actControl * 100_000_000L + inc;

                    Conn.getConnection().createStatement().execute("ALTER TABLE iva_retencion AUTO_INCREMENT= " + next_id);
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                        + "Verifique la fecha del Servidor, o llame a su Proveedor");
                    return "";
                }
            } else if (actControl == oldControl) {
                next_id = id;
            } else {
                JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                    + "Verifique la fecha del Servidor, o llame a su Proveedor");
                return "";
            }
        } else {
            JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                + "Verifique la fecha del Servidor, o llame a su Proveedor");
            return "";
        }

        return String.valueOf(next_id);
    }

    /**
     * La funcon es mantener actualizado el control islr, dentro de la estructura de autoincrement SQL
     *
     * Rev 14/09/2016
     *
     * @return
     */
    public static String checkIslrNextId() {

        final long id;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT MAX(id_islr_retencion) AS maxid FROM islr_retencion");
            if (rs.next()) {
                id = rs.getLong("maxid") + 1;
            } else {
                id = 0;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el siguiente número de Retención" + System.getProperty("line.separator") + _ex);
            return "";
        }

        // Verificar los casos para el ID
        final long next_id;
        final long inc = id % 100_000_000L;
        final long oldControl = id / 100_000_000L;
        final long actControl = Long.parseLong(new SimpleDateFormat("YYYYMM").format(Globales.getServerTimeStamp()));

        final long oldYear = oldControl / 100L;
        final long actYear = actControl / 100L;

        // Si difiere el control, se debe actualizar el incremento, ya que se ha detectado
        // un cambio de año
        if (actYear > oldYear) {
            try {
                // reinicia la cuenta
                next_id = actControl * 100_000_000L + 1;

                Conn.getConnection().createStatement().execute("ALTER TABLE islr_retencion AUTO_INCREMENT= " + next_id);
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, "Error al trata de actualizar el ID" + System.getProperty("line.separator") + _ex);
                return "";
            }

        } else if (actYear == oldYear) {

            if (actControl > oldControl) {
                try {
                    next_id = actControl * 100_000_000L + inc;

                    Conn.getConnection().createStatement().execute("ALTER TABLE islr_retencion AUTO_INCREMENT= " + next_id);
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                        + "Verifique la fecha del Servidor, o llame a su Proveedor");
                    return "";
                }
            } else if (actControl == oldControl) {
                next_id = id;
            } else {
                JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                    + "Verifique la fecha del Servidor, o llame a su Proveedor");
                return "";
            }
        } else {
            JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                + "Verifique la fecha del Servidor, o llame a su Proveedor");
            return "";
        }

        return String.valueOf(next_id);
    }

    /**
     * La funcion es mantener actualizado el control de Impuesto Municipal, dentro de la estructura de autoincrement SQL
     *
     * Rev 14-04-2017
     *
     * @return
     */
    public static String checkImpMunNextId() {

        final long id;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT MAX(id_imp_mun) FROM imp_mun");
            if (rs.next()) {
                id = rs.getLong(1) + 1;
            } else {
                id = 0;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el siguiente número de Retención" + System.getProperty("line.separator") + _ex);
            return "";
        }

        // Verificar los casos para el ID
        final long next_id;
        final long inc = id % 100_000_000L;
        final long oldControl = id / 100_000_000L;
        final long actControl = Long.parseLong(new SimpleDateFormat("YYYYMM").format(Globales.getServerTimeStamp()));

        final long oldYear = oldControl / 100L;
        final long actYear = actControl / 100L;

        // Si difiere el control, se debe actualizar el incremento, ya que se ha detectado
        // un cambio de año
        if (actYear > oldYear) {
            try {
                // reinicia la cuenta
                next_id = actControl * 100_000_000L + 1;

                Conn.getConnection().createStatement().execute("ALTER TABLE imp_mun AUTO_INCREMENT= " + next_id);
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, "Error al trata de actualizar el ID" + System.getProperty("line.separator") + _ex);
                return "";
            }

        } else if (actYear == oldYear) {

            if (actControl > oldControl) {
                try {
                    next_id = actControl * 100_000_000L + inc;

                    Conn.getConnection().createStatement().execute("ALTER TABLE imp_mun AUTO_INCREMENT= " + next_id);
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                        + "Verifique la fecha del Servidor, o llame a su Proveedor");
                    return "";
                }
            } else if (actControl == oldControl) {
                next_id = id;
            } else {
                JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                    + "Verifique la fecha del Servidor, o llame a su Proveedor");
                return "";
            }
        } else {
            JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                + "Verifique la fecha del Servidor, o llame a su Proveedor");
            return "";
        }

        return String.valueOf(next_id);
    }

    /**
     * La funcion es mantener actualizado el control de Negro Primero, dentro de la estructura de autoincrement SQL
     *
     * Rev 14-04-2017
     *
     * @return
     */
    public static String checkNegPriNextId() {

        final long id;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT MAX(id_neg_pri) FROM neg_pri");
            if (rs.next()) {
                id = rs.getLong(1) + 1;
            } else {
                id = 0;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el siguiente número de Retención" + System.getProperty("line.separator") + _ex);
            return "";
        }

        // Verificar los casos para el ID
        final long next_id;
        final long inc = id % 100_000_000L;
        final long oldControl = id / 100_000_000L;
        final long actControl = Long.parseLong(new SimpleDateFormat("YYYYMM").format(Globales.getServerTimeStamp()));

        final long oldYear = oldControl / 100L;
        final long actYear = actControl / 100L;

        // Si difiere el control, se debe actualizar el incremento, ya que se ha detectado
        // un cambio de año
        if (actYear > oldYear) {
            try {
                // reinicia la cuenta
                next_id = actControl * 100_000_000L + 1;

                Conn.getConnection().createStatement().execute("ALTER TABLE neg_pri AUTO_INCREMENT= " + next_id);
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, "Error al trata de actualizar el ID" + System.getProperty("line.separator") + _ex);
                return "";
            }

        } else if (actYear == oldYear) {

            if (actControl > oldControl) {
                try {
                    next_id = actControl * 100_000_000L + inc;

                    Conn.getConnection().createStatement().execute("ALTER TABLE neg_pri AUTO_INCREMENT= " + next_id);
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                        + "Verifique la fecha del Servidor, o llame a su Proveedor");
                    return "";
                }
            } else if (actControl == oldControl) {
                next_id = id;
            } else {
                JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                    + "Verifique la fecha del Servidor, o llame a su Proveedor");
                return "";
            }
        } else {
            JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                + "Verifique la fecha del Servidor, o llame a su Proveedor");
            return "";
        }

        return String.valueOf(next_id);
    }

    /**
     * La funcion es mantener actualizado el control de Otras Retenciones, dentro de la estructura de autoincrement SQL
     *
     * Rev 14-04-2017
     *
     * @return
     */
    public static String checkORetNextId() {

        final long id;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT MAX(id_otras_ret) AS maxid FROM otras_ret");
            if (rs.next()) {
                id = rs.getLong("maxid") + 1;
            } else {
                id = 0;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el siguiente número de Retención" + System.getProperty("line.separator") + _ex);
            return "";
        }

        // Verificar los casos para el ID
        final long next_id;
        final long inc = id % 100_000_000L;
        final long oldControl = id / 100_000_000L;
        final long actControl = Long.parseLong(new SimpleDateFormat("YYYYMM").format(Globales.getServerTimeStamp()));

        final long oldYear = oldControl / 100L;
        final long actYear = actControl / 100L;

        // Si difiere el control, se debe actualizar el incremento, ya que se ha detectado
        // un cambio de año
        if (actYear > oldYear) {
            try {
                // reinicia la cuenta
                next_id = actControl * 100_000_000L + 1;

                Conn.getConnection().createStatement().execute("ALTER TABLE otras_ret AUTO_INCREMENT= " + next_id);
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, "Error al trata de actualizar el ID" + System.getProperty("line.separator") + _ex);
                return "";
            }

        } else if (actYear == oldYear) {

            if (actControl > oldControl) {
                try {
                    next_id = actControl * 100_000_000L + inc;

                    Conn.getConnection().createStatement().execute("ALTER TABLE otras_ret AUTO_INCREMENT= " + next_id);
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                        + "Verifique la fecha del Servidor, o llame a su Proveedor");
                    return "";
                }
            } else if (actControl == oldControl) {
                next_id = id;
            } else {
                JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                    + "Verifique la fecha del Servidor, o llame a su Proveedor");
                return "";
            }
        } else {
            JOptionPane.showMessageDialog(null, "Discrepancia de Fechas del Sistema" + System.getProperty("line.separator")
                + "Verifique la fecha del Servidor, o llame a su Proveedor");
            return "";
        }

        return String.valueOf(next_id);
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
                new ImpuestoRetencion(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnularISLR;
    private javax.swing.JButton btnAnularIVA;
    private javax.swing.JButton btnAnularImpMun;
    private javax.swing.JButton btnAnularNegPri;
    private javax.swing.JButton btnAnularOtras;
    private javax.swing.JButton btnISLR_Rend;
    private javax.swing.JButton btnImpMunConsultar;
    private javax.swing.JButton btnImpMunEliminar;
    private javax.swing.JButton btnImpMunGuardar;
    private javax.swing.JButton btnImpMunLimpiar;
    private javax.swing.JButton btnImpMunRevertir;
    private javax.swing.JButton btnImpMun_Rend;
    private javax.swing.JButton btnIslrConsultar;
    private javax.swing.JButton btnIslrEliminar;
    private javax.swing.JButton btnIslrGuardar;
    private javax.swing.JButton btnIslrLimpiar;
    private javax.swing.JButton btnIslrRevertir;
    private javax.swing.JButton btnIva100;
    private javax.swing.JButton btnIva75;
    private javax.swing.JButton btnIvaConsultar;
    private javax.swing.JButton btnIvaEliminar;
    private javax.swing.JButton btnIvaGuardar;
    private javax.swing.JButton btnIvaLimpiar;
    private javax.swing.JButton btnIvaRevertir;
    private javax.swing.JButton btnIva_Rend;
    private javax.swing.JButton btnNegPriConsultar;
    private javax.swing.JButton btnNegPriEliminar;
    private javax.swing.JButton btnNegPriGuardar;
    private javax.swing.JButton btnNegPriLimpiar;
    private javax.swing.JButton btnNegPriRevertir;
    private javax.swing.JButton btnNegPri_Rend;
    private javax.swing.JButton btnORetConsultar;
    private javax.swing.JButton btnORetEliminar;
    private javax.swing.JButton btnORetGuardar;
    private javax.swing.JButton btnORetLimpiar;
    private javax.swing.JButton btnORetRevertir;
    private javax.swing.JButton btnRendicionOtras;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cmbIslrPorc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblFondo1;
    private javax.swing.JLabel lblFondo2;
    private javax.swing.JLabel lblFondo3;
    private javax.swing.JLabel lblFondo4;
    private javax.swing.JLabel lblFondo5;
    private javax.swing.JLabel lblIVA;
    private javax.swing.JLabel lblIVA1;
    private javax.swing.JPanel pnlComponentes;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlISLR;
    private javax.swing.JPanel pnlImpMun;
    private javax.swing.JPanel pnlIva;
    private javax.swing.JPanel pnlNegPri;
    private javax.swing.JPanel pnlOtras;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTabbedPane tbbPane;
    public static javax.swing.JTable tblImpMunDetail;
    private javax.swing.JTable tblImpMunMaster;
    public static javax.swing.JTable tblIslrDetail;
    private javax.swing.JTable tblIslrMaster;
    public static javax.swing.JTable tblIvaDetail;
    private javax.swing.JTable tblIvaMaster;
    public static javax.swing.JTable tblNegPriDetail;
    private javax.swing.JTable tblNegPriMaster;
    public static javax.swing.JTable tblORetDetail;
    private javax.swing.JTable tblORetMaster;
    private javax.swing.JFormattedTextField txtIVAContribuyente;
    private javax.swing.JFormattedTextField txtImpMunContribuyente;
    private javax.swing.JFormattedTextField txtImpMunControl;
    private javax.swing.JFormattedTextField txtImpMunFechaHoy;
    private javax.swing.JFormattedTextField txtImpMunNum;
    private javax.swing.JFormattedTextField txtImpMunRetenido;
    private javax.swing.JFormattedTextField txtIslrContribuyente;
    private javax.swing.JFormattedTextField txtIslrControl;
    private javax.swing.JFormattedTextField txtIslrFechaHoy;
    private javax.swing.JFormattedTextField txtIslrNum;
    private javax.swing.JFormattedTextField txtIslrRetenido;
    private javax.swing.JFormattedTextField txtIvaControl;
    private javax.swing.JFormattedTextField txtIvaFechaHoy;
    private javax.swing.JFormattedTextField txtIvaNum;
    private javax.swing.JFormattedTextField txtIvaRet;
    private javax.swing.JFormattedTextField txtNegPriContribuyente;
    private javax.swing.JFormattedTextField txtNegPriControl;
    private javax.swing.JFormattedTextField txtNegPriFechaHoy;
    private javax.swing.JFormattedTextField txtNegPriNum;
    private javax.swing.JFormattedTextField txtNegPriRetenido;
    private javax.swing.JFormattedTextField txtORetContribuyente;
    private javax.swing.JFormattedTextField txtORetControl;
    private javax.swing.JFormattedTextField txtORetFechaHoy;
    private javax.swing.JFormattedTextField txtORetNum;
    private javax.swing.JFormattedTextField txtORetRetenido;
    // End of variables declaration//GEN-END:variables

}
