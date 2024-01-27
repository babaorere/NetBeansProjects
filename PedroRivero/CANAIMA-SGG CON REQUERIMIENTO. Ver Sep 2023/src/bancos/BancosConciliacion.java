/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package bancos;

import capipsistema.CapipPropiedades;
import connection.ConnCapip;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.ModelBancosOperaciones;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.TableToExcel;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class BancosConciliacion extends javax.swing.JFrame {

    private static final long ejeFis = Globales.getEjeFisYear();

    private final java.awt.Window parent;

    private static final int colId = 0;
    private static final int colNumOp = 1;
    private static final int colFecha = 2;
    private static final int colSoporte = 3;
    private static final int colDesc = 4;
    private static final int colTipo = 5;
    private static final int colDebe = 6;
    private static final int colHaber = 7;
    private static final int colConciliado = 8;

    /**
     * Rev 25/11/2016
     *
     * @param inparent
     */
    public BancosConciliacion(final java.awt.Window inparent) {

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
        // Configurar la JTable de las Partidas, para manejar el ENTER
        final InputMap im = tblAuxBco.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap am = tblAuxBco.getActionMap();

        final KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        im.put(enterKey, "Action.enter");
        am.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                btnConciliar.doClick();
            }
        });

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(colId).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(colNumOp).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(colFecha).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(colSoporte).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(colTipo).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        tblAuxBco.getColumnModel().getColumn(colDebe).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        tblAuxBco.getColumnModel().getColumn(colHaber).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(colConciliado).setCellRenderer(tcr);

        cbxCuentas.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    if (cbxCuentas.getSelectedIndex() < 0) {
                        return;
                    }

                    mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
                }
            }
        });

    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        btnReset.doClick();
    }

    public static String fechaHoy() {
        return Globales.dateFormat.format(Globales.getServerTimeStamp());
    }

    String sqlAuxBancosDet() {
        final String sql;

        if (btnAno.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnEne.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 01 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnFeb.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 02 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnMar.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 03 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnAbr.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 04 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnMay.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 05 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnJun.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 06 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnJul.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 07 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnAgo.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 08 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnSep.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 09 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnOct.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 10 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnNov.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 11 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (btnDic.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 12 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        } else {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') AND anulado_sn= 'N' ORDER BY id DESC";
        }

        return sql;
    }

    String sqlAuxBancosDet(final String incuenta) {
        if (( incuenta == null) || incuenta.isEmpty()) {
            return sqlAuxBancosDet();
        }

        final String sql;

        if (btnAno.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnEne.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 01 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnFeb.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 02 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnMar.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 03 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnAbr.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 04 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnMay.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 05 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnJun.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 06 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnJul.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 07 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnAgo.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 08 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnSep.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 09 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnOct.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 10 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnNov.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 11 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else if (btnDic.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 12 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        } else {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + incuenta + "' AND (tipo_operacion='CH' OR tipo_operacion='FA') ORDER BY id DESC";
        }

        return sql;
    }

    void mostrarTblAuxBco(String incuenta) {

        final Integer filaAnt = tblAuxBco.getSelectedRow();
        tblAuxBco.clearSelection();

        // Para evitar multiples llamadas al metodo valueChanged
        tblAuxBco.clearSelection();

        final DefaultTableModel model = (DefaultTableModel) tblAuxBco.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final String sql;

        if ( incuenta.isEmpty()) {
            sql = sqlAuxBancosDet();
        } else {
            sql = sqlAuxBancosDet( incuenta);
        }

        final Object[] datos = new Object[9];
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery(sql);
            while (rs.next()) {
                ModelBancosOperaciones reg = new ModelBancosOperaciones(rs);
                datos[colId] = reg;
                datos[colNumOp] = reg.getCuentaNumOp();
                datos[colFecha] = reg.getFecha();
                datos[colSoporte] = reg.getSoporte_o_Cheque();
                datos[colDesc] = reg.getDesc();
                datos[colTipo] = reg.getTipo();
                datos[colDebe] = Globales.curFormat.valueToString(reg.getDebe());
                datos[colHaber] = Globales.curFormat.valueToString(reg.getHaber());
                datos[colConciliado] = reg.getConciliado();
                model.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        if ((tblAuxBco.getRowCount() > 0) && (filaAnt >= 0)) {
            tblAuxBco.setRowSelectionInterval(filaAnt, filaAnt);
            tblAuxBco.scrollRectToVisible(tblAuxBco.getCellRect(filaAnt, filaAnt, true));
        }

    }

    void cambiarEstado(Integer id, String sta) {
        try {
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("UPDATE bancos_operaciones SET conciliado='" + sta + "' WHERE id = '" + id + "' AND anulado_sn= 'N'");
            if (pst.executeUpdate() != 1) {
                JOptionPane.showMessageDialog(null, "Error al cambiar estado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }

    void cargarComboCuentas() {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM bancos ORDER BY banco, cuenta ASC");
            while (rs.next()) {
                modeloCombo.addElement(rs.getString("cuenta") + " " + rs.getString("banco"));
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        cbxCuentas.setModel(modeloCombo);
        cbxCuentas.setSelectedIndex(-1);
    }

    public void ver_orden(final String inbanco, final String incuenta, final Long inmesIni, final Long inmesFin,
        final long inejeFis, final String intotal, final String inNumLiteral) {
        try {
            final Map<String, Object> param = new HashMap<>(101);
            param.clear();
            param.put("banco", inbanco);
            param.put("cuenta", incuenta);
            param.put("mesini", inmesIni);
            param.put("mesfin", inmesFin);
            param.put("ejefis", inejeFis);
            param.put("total", intotal);

            final String NumLiteral_1;
            final String NumLiteral_2;

            if ( inNumLiteral.length() < 65) {
                NumLiteral_1 = inNumLiteral;
                NumLiteral_2 = "";
            } else {
                int pos = inNumLiteral.lastIndexOf(" CON ");
                final String s1 = inNumLiteral.substring(0, pos);
                if (s1.length() > 65) {
                    pos = s1.lastIndexOf(" ") + 1;
                }

                NumLiteral_1 = inNumLiteral.substring(0, pos).trim();
                NumLiteral_2 = inNumLiteral.substring(pos).trim();
            }

            param.put("NumLiteral_1", NumLiteral_1);
            param.put("NumLiteral_2", NumLiteral_2);

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

            final InputStream pathRpt = aClass.getResourceAsStream("/reportes/conciliacion bancaria.jasper");
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
                JOptionPane.showMessageDialog(null, "Reporte canciliacion no encontrado");
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
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnReset = new javax.swing.JButton();
        cbxCuentas = new javax.swing.JComboBox<String>();
        txtFecha = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAuxBco = new javax.swing.JTable();
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
        btnExcel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnConciliar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("\"CONCILIACIONES BANCARIAS\"");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnReset.setBackground(java.awt.SystemColor.inactiveCaption);
        btnReset.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        getContentPane().add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        cbxCuentas.setBackground(java.awt.SystemColor.inactiveCaption);
        cbxCuentas.setFont(new java.awt.Font("Tahoma", 2, 16)); // NOI18N
        cbxCuentas.setForeground(new java.awt.Color(0, 51, 153));
        getContentPane().add(cbxCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 530, 40));

        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, 150, 40));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabel3.setText("CUENTAS BANCARIAS");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 280, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CUENTAS  REGISTRADAS");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 240, -1));

        tblAuxBco.setAutoCreateRowSorter(true);
        tblAuxBco.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "# Op.", "Fecha", "Soporte", "Descripción", "Tipo", "Debe Bs.", "Haber Bs.", "Conciliado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tblAuxBco.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblAuxBco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAuxBcoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAuxBco);
        if (tblAuxBco.getColumnModel().getColumnCount() > 0) {
            tblAuxBco.getColumnModel().getColumn(0).setMinWidth(50);
            tblAuxBco.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblAuxBco.getColumnModel().getColumn(0).setMaxWidth(150);
            tblAuxBco.getColumnModel().getColumn(1).setMinWidth(50);
            tblAuxBco.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblAuxBco.getColumnModel().getColumn(1).setMaxWidth(150);
            tblAuxBco.getColumnModel().getColumn(2).setMinWidth(50);
            tblAuxBco.getColumnModel().getColumn(2).setPreferredWidth(75);
            tblAuxBco.getColumnModel().getColumn(2).setMaxWidth(150);
            tblAuxBco.getColumnModel().getColumn(5).setMinWidth(25);
            tblAuxBco.getColumnModel().getColumn(5).setPreferredWidth(30);
            tblAuxBco.getColumnModel().getColumn(5).setMaxWidth(50);
            tblAuxBco.getColumnModel().getColumn(8).setMinWidth(50);
            tblAuxBco.getColumnModel().getColumn(8).setPreferredWidth(100);
            tblAuxBco.getColumnModel().getColumn(8).setMaxWidth(150);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 106, 1110, 460));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnAno.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnAno);
        btnAno.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnAno.setSelected(true);
        btnAno.setText("Año");
        btnAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnoActionPerformed(evt);
            }
        });
        jPanel2.add(btnAno, new java.awt.GridBagConstraints());

        btnEne.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnEne);
        btnEne.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnEne.setText("Ene");
        btnEne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEneActionPerformed(evt);
            }
        });
        jPanel2.add(btnEne, new java.awt.GridBagConstraints());

        btnFeb.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnFeb);
        btnFeb.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnFeb.setText("Feb");
        btnFeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFebActionPerformed(evt);
            }
        });
        jPanel2.add(btnFeb, new java.awt.GridBagConstraints());

        btnMar.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnMar);
        btnMar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnMar.setText("Mar");
        btnMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarActionPerformed(evt);
            }
        });
        jPanel2.add(btnMar, new java.awt.GridBagConstraints());

        btnAbr.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnAbr);
        btnAbr.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnAbr.setText("Abr");
        btnAbr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrActionPerformed(evt);
            }
        });
        jPanel2.add(btnAbr, new java.awt.GridBagConstraints());

        btnMay.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnMay);
        btnMay.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnMay.setText("May");
        btnMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMayActionPerformed(evt);
            }
        });
        jPanel2.add(btnMay, new java.awt.GridBagConstraints());

        btnJun.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnJun);
        btnJun.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnJun.setText("Jun");
        btnJun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJunActionPerformed(evt);
            }
        });
        jPanel2.add(btnJun, new java.awt.GridBagConstraints());

        btnJul.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnJul);
        btnJul.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnJul.setText("Jul");
        btnJul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJulActionPerformed(evt);
            }
        });
        jPanel2.add(btnJul, new java.awt.GridBagConstraints());

        btnAgo.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnAgo);
        btnAgo.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnAgo.setText("Ago");
        btnAgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnAgo, gridBagConstraints);

        btnSep.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnSep);
        btnSep.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnSep.setText("Sep");
        btnSep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSepActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnSep, gridBagConstraints);

        btnOct.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnOct);
        btnOct.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnOct.setText("Oct");
        btnOct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOctActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnOct, gridBagConstraints);

        btnNov.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnNov);
        btnNov.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnNov.setText("Nov");
        btnNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnNov, gridBagConstraints);

        btnDic.setBackground(new java.awt.Color(175, 204, 125));
        buttonGroup1.add(btnDic);
        btnDic.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnDic.setText("Dic");
        btnDic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDicActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnDic, gridBagConstraints);

        btnExcel.setBackground(java.awt.SystemColor.inactiveCaption);
        btnExcel.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnExcel.setText("EXCEL");
        btnExcel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(btnExcel, gridBagConstraints);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 1100, 40));

        jLabel4.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FECHA");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 150, -1));

        btnConciliar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnConciliar.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnConciliar.setText("CONCILIAR");
        btnConciliar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnConciliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConciliarActionPerformed(evt);
            }
        });
        getContentPane().add(btnConciliar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 640, 190, 50));

        btnReporte.setBackground(java.awt.SystemColor.inactiveCaption);
        btnReporte.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnReporte.setText("REPORTE");
        btnReporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });
        getContentPane().add(btnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 640, 190, 50));

        btnSalir.setBackground(java.awt.SystemColor.inactiveCaption);
        btnSalir.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 640, 190, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1150, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnConciliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConciliarActionPerformed
        int fila = tblAuxBco.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return;
        }

        final Integer id = ((ModelBancosOperaciones) tblAuxBco.getValueAt(tblAuxBco.getSelectedRow(), 0)).getId();
        final String tipo = (String) tblAuxBco.getValueAt(tblAuxBco.getSelectedRow(), colTipo);
        final String conc = (String) tblAuxBco.getValueAt(tblAuxBco.getSelectedRow(), colConciliado);

        if (conc.equals("EN TRANSITO")) {
            //if (tipo.equals("CH")) {
            if (JOptionPane.showConfirmDialog(this, "¿ Seguro desea Conciliar ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                cambiarEstado(id, "COBRADO");
            }
            //}
        } else {
            //if (tipo.equals("CH")) {
            if (JOptionPane.showConfirmDialog(this, "¿ Seguro desea Reversar ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                cambiarEstado(id, "EN TRANSITO");
            }
            //}
        }

        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }

        tblAuxBco.requestFocusInWindow();
    }//GEN-LAST:event_btnConciliarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta");
            return;
        }

        // Verificar que existen operaciones con Cheques
        boolean cent = false;
        for (int i = 0; i < tblAuxBco.getRowCount(); i++) {
            final String aux = (String) tblAuxBco.getValueAt(i, colTipo);
            if (aux.equals("CH") || aux.equals("FA")) {
                cent = true;
                break;
            }
        }

        if (!cent) {
            JOptionPane.showMessageDialog(null, "No existen operaciones con cheques para esta Cuenta, y periodo seleccionado");
            return;
        }

        final Long mesini;
        final Long mesfin;

        if (btnAno.isSelected()) {
            mesini = 1L;
            mesfin = 12L;
        } else if (btnEne.isSelected()) {
            mesini = 1L;
            mesfin = mesini;
        } else if (btnFeb.isSelected()) {
            mesini = 2L;
            mesfin = mesini;
        } else if (btnMar.isSelected()) {
            mesini = 3L;
            mesfin = mesini;
        } else if (btnAbr.isSelected()) {
            mesini = 4L;
            mesfin = mesini;
        } else if (btnMay.isSelected()) {
            mesini = 5L;
            mesfin = mesini;
        } else if (btnJun.isSelected()) {
            mesini = 6L;
            mesfin = mesini;
        } else if (btnJul.isSelected()) {
            mesini = 7L;
            mesfin = mesini;
        } else if (btnAgo.isSelected()) {
            mesini = 8L;
            mesfin = mesini;
        } else if (btnSep.isSelected()) {
            mesini = 9L;
            mesfin = mesini;
        } else if (btnOct.isSelected()) {
            mesini = 10L;
            mesfin = mesini;
        } else if (btnNov.isSelected()) {
            mesini = 11L;
            mesfin = mesini;
        } else if (btnDic.isSelected()) {
            mesini = 12L;
            mesfin = mesini;
        } else {
            mesini = 1L;
            mesfin = 12L;
        }

        String total = "";
        final String banco = ((String) cbxCuentas.getSelectedItem()).substring(21);
        final String cuenta = ((String) cbxCuentas.getSelectedItem()).substring(0, 20);
        String EnLetras = "";
        ver_orden(banco, cuenta, mesini, mesfin, ejeFis, EnLetras, total);

    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnEneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEneActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnEneActionPerformed

    private void btnFebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFebActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnFebActionPerformed

    private void btnMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnMarActionPerformed

    private void btnAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnAbrActionPerformed

    private void btnMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMayActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnMayActionPerformed

    private void btnJunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJunActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnJunActionPerformed

    private void btnJulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJulActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnJulActionPerformed

    private void btnAgoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgoActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnAgoActionPerformed

    private void btnSepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSepActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnSepActionPerformed

    private void btnOctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOctActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnOctActionPerformed

    private void btnNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnNovActionPerformed

    private void btnDicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnDicActionPerformed

    private void btnAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnoActionPerformed
        if (cbxCuentas.getSelectedIndex() < 0) {
            mostrarTblAuxBco("");
        } else {
            mostrarTblAuxBco(((String) cbxCuentas.getSelectedItem()).substring(0, 20));
        }
    }//GEN-LAST:event_btnAnoActionPerformed

    private void tblAuxBcoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAuxBcoMouseClicked
        btnConciliar.doClick();
    }//GEN-LAST:event_tblAuxBcoMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtFecha.setText(fechaHoy());
        btnAno.setSelected(true);
        cargarComboCuentas();
        mostrarTblAuxBco("");

        if (tblAuxBco.getRowCount() > 0) {
            tblAuxBco.setRowSelectionInterval(0, 0);
            tblAuxBco.scrollRectToVisible(tblAuxBco.getCellRect(0, 0, true));
        }

        tblAuxBco.requestFocusInWindow();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        try {
            final String fileName = "RptBancosConciliacion " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblAuxBco, fileName + ".xls");
            TableToExcel.exportTSV(tblAuxBco, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }//GEN-LAST:event_btnExcelActionPerformed

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
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new BancosConciliacion(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAbr;
    private javax.swing.JToggleButton btnAgo;
    private javax.swing.JToggleButton btnAno;
    private javax.swing.JButton btnConciliar;
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
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSalir;
    private javax.swing.JToggleButton btnSep;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxCuentas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAuxBco;
    private javax.swing.JFormattedTextField txtFecha;
    // End of variables declaration//GEN-END:variables

    Connection cn = ConnCapip.getInstance().getConnection();
}
