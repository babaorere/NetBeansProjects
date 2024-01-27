package bancos;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.LibroBancoRptModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
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
public final class BancosContabilidad extends javax.swing.JFrame {

    //private static String cuenta;
    private static final long serialVersionUID = 1L;

    private static final long ejeFis = Globales.getEjeFisYear();

    private static final int colCuenta = 0;
    private static final int colBanco = 1;
    private static final int colMonto = 2;

    private static final int colFecha = 0;
    private static final int colSoporte = 1;
    private static final int colDesc = 2;
    private static final int colTipo = 3;
    private static final int colDebe = 4;
    private static final int colHaber = 5;
    private static final int colConciliado = 6;

    private final java.awt.Window parent;

    private String mesdesde;
    private String meshasta;

    public BancosContabilidad(java.awt.Window _parent) {
        super();
        initComponents();

        parent = _parent;

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
            setStartConditions();
        }

        super.setVisible(_b);
    }

    /**
     * Establece el comportamiento de la presente Ventana Rev 21/09/2016
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
                CalcularTodo();
            }
        });

        // Para actualizar automaticamente los campos de detalles, al seleccionar una fila de la tabla
        // al estilo master/detail
        tblCuentas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent _lse) {
                if (_lse.getValueIsAdjusting()) {
                    return;
                }

                if (tblCuentas.getSelectedRow() >= 0) {
                    CalcularTodo();
                }
            }
        });

        DefaultTableCellRenderer tcr;

        // Configurar la tabla de Maestra, para alinear las columnas
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblCuentas.getColumnModel().getColumn(0).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblCuentas.getColumnModel().getColumn(1).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        tblCuentas.getColumnModel().getColumn(2).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(0).setCellRenderer(tcr);

        tblAuxBco.getColumnModel().getColumn(colFecha).setCellRenderer(new DateCellRenderer());

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(colTipo).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblAuxBco.getColumnModel().getColumn(colConciliado).setCellRenderer(tcr);

        tblAuxBco.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblAuxBco.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblAuxBco.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 25/11/2016
     */
    private void setStartConditions() {
        mesdesde = "";
        meshasta = "";
        jTextFieldfecha.setText(Format.toStr(new java.sql.Date(Globales.getServerTimeStamp().getTime())));
        MostrarTblCuentas();
        MostrarTblAuxiliar("");
        CalcularTodo();

    }

    private void MostrarTblCuentas() {

        final int filaAnt = tblCuentas.getSelectedRow();

        // Para evitar multiples llamadas al metodo valueChanged
        tblCuentas.clearSelection();

        final DefaultTableModel model = (DefaultTableModel) tblCuentas.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[3];
        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos WHERE ejefis= " + Globales.getServerYear() + " ORDER BY banco, cuenta ASC");
            while (rs.next()) {
                final String cuenta = rs.getString("cuenta");
                final BigDecimal saldoi = rs.getBigDecimal("saldoi");
                final BigDecimal mov = BancosContabilidad.getMovHasta(cuenta, ejeFis, 12); // tomar todos los movimientos
                datos[0] = cuenta;
                datos[1] = rs.getString("banco");
                datos[2] = Format.toStr(saldoi.add(mov));
                model.addRow(datos);
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        if ((filaAnt >= 0) && (tblCuentas.getRowCount() > 0)) {

            int fila = min(filaAnt, tblCuentas.getRowCount());

            tblCuentas.setRowSelectionInterval(fila, fila);
            tblCuentas.scrollRectToVisible(tblCuentas.getCellRect(fila, fila, true));
        }

        calcularTotalCuentas();
    }

    /**
     *
     * @param _cuenta
     */
    private void MostrarTblAuxiliar(String _cuenta) {
        if (_cuenta == null) {
            return;
        }

        // Para evitar multiples llamadas al metodo valueChanged
        tblAuxBco.clearSelection();

        final DefaultTableModel model = (DefaultTableModel) tblAuxBco.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final String sql;

        if (_cuenta.isEmpty()) {
            sql = sqlAuxBancosDet();
        } else {
            sql = sqlAuxBancosDet(_cuenta);
        }

        final Object[] datos = new Object[7];
        try {
            final ResultSet rs = Conn.executeQuery(sql);
            while (rs.next()) {
                datos[colFecha] = Format.toDateSql(rs.getString("fecha"));
                datos[colSoporte] = rs.getString("soporte_o_cheque");
                datos[colDesc] = rs.getString("descripcion");
                datos[colTipo] = rs.getString("tipo_operacion");
                datos[colDebe] = rs.getDouble("debe");
                datos[colHaber] = rs.getDouble("haber");
                datos[colConciliado] = rs.getString("conciliado");
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * Rev 25/01/2017
     *
     * @return
     */
    private String sqlAuxBancosDet() {
        final String sql;

        if (btnAno.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (tbtnEne.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 01 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (tbtnFeb.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 02 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (tbtnMar.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 03 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtonabr.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 04 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtonmay.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 05 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtonjun.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 06 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtonjul.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 07 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtonago.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 08 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtonsep.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 09 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtonoct.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 10 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtonnov.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 11 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else if (jToggleButtondic.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 12 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        } else {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' AND anulado_sn= 'N' ORDER BY id DESC";
        }

        mesdesde = "";
        meshasta = "";

        return sql;
    }

    /**
     * Rev 09/01/2017
     *
     * @param _cuenta
     * @return
     */
    private String sqlAuxBancosDet(final String _cuenta) {
        if ((_cuenta == null) || _cuenta.isEmpty()) {
            return sqlAuxBancosDet();
        }

        final String sql;

        if (btnAno.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Enero";
            meshasta = "Diciembre";
        } else if (tbtnEne.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 1 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Enero";
            meshasta = "Enero";
        } else if (tbtnFeb.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 2 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Febrero";
            meshasta = "Febrero";
        } else if (tbtnMar.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 3 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Marzo";
            meshasta = "Marzo";
        } else if (jToggleButtonabr.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 4 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Abril";
            meshasta = "Abril";
        } else if (jToggleButtonmay.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 5 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Mayo";
            meshasta = "Mayo";
        } else if (jToggleButtonjun.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 6 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Junio";
            meshasta = "Junio";
        } else if (jToggleButtonjul.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 7 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Julio";
            meshasta = "Julio";
        } else if (jToggleButtonago.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 8 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Agosto";
            meshasta = "Agosto";
        } else if (jToggleButtonsep.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 9 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Septiembre";
            meshasta = "Septiembre";
        } else if (jToggleButtonoct.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 10 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Octubre";
            meshasta = "Octubre";
        } else if (jToggleButtonnov.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 11 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Noviembre";
            meshasta = "Noviembre";
        } else if (jToggleButtondic.isSelected()) {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = 12 AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Diciembre";
            meshasta = "Diciembre";
        } else {
            sql = "SELECT * FROM bancos_operaciones WHERE EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) ='" + ejeFis + "' and cuenta='" + _cuenta + "' AND anulado_sn= 'N' ORDER BY id DESC";
            mesdesde = "Enero";
            meshasta = "Diciembre";
        }

        return sql;
    }

    /**
     * Calcula el monto neto de las operaciones (debe - haber) desde comienzo del año hasta un mes especifico
     *
     * Rev 09/01/2017
     *
     * @param _cuenta
     * @param yyyy
     * @param MM
     * @return (debe - haber) hasta el mes dado
     * @throws java.lang.Exception
     */
    public static BigDecimal getMovHasta(final String _cuenta, final long yyyy, final long MM) throws Exception {
        if ((_cuenta == null) || _cuenta.isEmpty() || (yyyy < 2017) || (MM < 0) || (MM > 12)) {
            return BigDecimal.ZERO;
        }

        final PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT SUM(debe - haber) FROM bancos_operaciones "
                + "WHERE cuenta= ? AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = ? AND EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) <= ? AND anulado_sn= 'N'");
        pst.setString(1, _cuenta);
        pst.setLong(2, yyyy);
        pst.setLong(3, MM == 0 ? 12 : MM);

        BigDecimal mov;
        final ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            mov = rs.getBigDecimal(1);
            if (mov == null) {
                mov = BigDecimal.ZERO;
            }
        } else {
            throw new Exception("Error al tratar de hallar el monto neto del movimiento");
        }

        return mov.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el monto neto de las operaciones (debe - haber) desde comienzo del año hasta un mes especifico
     *
     * Rev 09/01/2017
     *
     * @param _cuenta
     * @param _yyyy
     * @param _MM
     * @return (debe - haber) hasta el mes dado
     * @throws java.lang.Exception
     */
    public static BigDecimal getMovAnt(final String _cuenta, final long _yyyy, final long _MM) throws Exception {
        if ((_cuenta == null) || _cuenta.isEmpty() || (_yyyy < 2017) || (_MM < 0) || (_MM > 12)) {
            return BigDecimal.ZERO;
        }

        final PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT SUM(debe - haber) FROM bancos_operaciones WHERE cuenta= ? AND EXTRACT(YEAR FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) = ? AND EXTRACT(MONTH FROM (STR_TO_DATE(fecha,'%d/%m/%Y'))) < ? AND anulado_sn= 'N'");
        pst.setString(1, _cuenta);
        pst.setLong(2, _yyyy);
        pst.setLong(3, _MM);

        BigDecimal mov;
        final ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            mov = rs.getBigDecimal(1);
            if (mov == null) {
                mov = BigDecimal.ZERO;
            }
        } else {
            throw new Exception("Error al tratar de hallar el monto neto del movimiento");
        }

        return mov.setScale(2, RoundingMode.HALF_UP);
    }

    private long getMM() {
        final long MM;

        if (btnAno.isSelected()) {
            MM = 0L;
        } else if (tbtnEne.isSelected()) {
            MM = 1L;
        } else if (tbtnFeb.isSelected()) {
            MM = 2L;
        } else if (tbtnMar.isSelected()) {
            MM = 3L;
        } else if (jToggleButtonabr.isSelected()) {
            MM = 4L;
        } else if (jToggleButtonmay.isSelected()) {
            MM = 5L;
        } else if (jToggleButtonjun.isSelected()) {
            MM = 6L;
        } else if (jToggleButtonjul.isSelected()) {
            MM = 7L;
        } else if (jToggleButtonago.isSelected()) {
            MM = 8L;
        } else if (jToggleButtonsep.isSelected()) {
            MM = 9L;
        } else if (jToggleButtonoct.isSelected()) {
            MM = 10L;
        } else if (jToggleButtonnov.isSelected()) {
            MM = 11L;
        } else if (jToggleButtondic.isSelected()) {
            MM = 12L;
        } else {
            MM = 0L;
        }

        return MM;
    }

    private void calcularTotalCuentas() {
        Double totalCuentas = 0.00d;
        for (int i = 0; i < tblCuentas.getRowCount(); i++) {
            try {
                totalCuentas += (Double) Globales.curFormat.stringToValue((String) tblCuentas.getValueAt(i, colMonto));
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, _ex);
            }
        }

        try {
            txtTotal.setText(Globales.curFormat.valueToString(totalCuentas));
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            txtTotal.setText("0,00");
        }
    }

    /**
     * Rev 08/11/2017
     *
     * @param _cuenta
     * @param _ejefis
     * @param _MM
     * @return
     * @throws java.lang.Exception
     */
    public static BigDecimal getSaldoI(final String _cuenta, final long _ejefis, final long _MM) throws Exception {

        BigDecimal saldoi;

        // Hallar el banco
        final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos WHERE cuenta='" + _cuenta + "'");
        if (rs.next()) {
            final long id_banco = rs.getLong("id");
            BigDecimal saldoIniAnual;
            final ResultSet rsSaldoAnual = Conn.executeQuery("SELECT * FROM banco_saldo_anual WHERE id_banco= " + id_banco + " AND YEAR(ejefis) = " + _ejefis);
            if (rsSaldoAnual.next()) {
                saldoIniAnual = rsSaldoAnual.getBigDecimal("saldoi");
            } else {
                saldoIniAnual = BigDecimal.ZERO;
            }

            saldoi = saldoIniAnual.add(getMovAnt(_cuenta, _ejefis, _MM));

        } else {
            JOptionPane.showMessageDialog(null, "Cuenta no encontrada");
            saldoi = BigDecimal.ZERO;
        }

        return saldoi;
    }

    /**
     * Rev 08/11/2017
     *
     * @param _cuenta
     * @param _yyyy
     * @param _MM
     * @return
     * @throws java.lang.Exception
     */
    public static BigDecimal getSaldoF(final String _cuenta, final long _yyyy, final long _MM) throws Exception {

        BigDecimal saldof;

        // Hallar el banco
        final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos WHERE cuenta='" + _cuenta + "'");
        if (rs.next()) {
            final long id_banco = rs.getLong("id");
            BigDecimal saldoIniAnual;
            final ResultSet rsSaldoAnual = Conn.executeQuery("SELECT * FROM banco_saldo_anual WHERE id_banco= " + id_banco + " AND YEAR(ejefis) = " + _yyyy);
            if (rsSaldoAnual.next()) {
                saldoIniAnual = rsSaldoAnual.getBigDecimal("saldoi");
            } else {
                saldoIniAnual = BigDecimal.ZERO;
            }

            saldof = saldoIniAnual.add(getMovHasta(_cuenta, _yyyy, _MM));

        } else {
            JOptionPane.showMessageDialog(null, "Cuenta no encontrada");
            saldof = BigDecimal.ZERO;
        }

        return saldof;
    }

    /**
     * Rev 08/11/2017
     *
     */
    private void calcularSaldoI() {

        try {
            // Verificar la fila seleccionada
            int selRow = tblCuentas.getSelectedRow();
            if (selRow < 0) {
                txtSaldoI.setText("");
                return; // no hay fila seleccionada
            }

            String cuenta = (String) tblCuentas.getValueAt(selRow, 0);

            txtSaldoI.setText(Format.toStr(getSaldoI(cuenta, ejeFis, getMM())));

        } catch (final Exception _ex) {
            txtSaldoI.setText("0,00");
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * Rev 26/11/2017
     *
     */
    void calcularSaldoF() {
        // Verificar la fila seleccionada
        int selRow = tblCuentas.getSelectedRow();
        if (selRow < 0) {
            txtSaldoF.setText("");
            return; // no hay fila seleccionada
        }

        try {
            final BigDecimal saldoIni = Format.toBigDec(txtSaldoI.getText());
            final BigDecimal debe = Format.toBigDec(txtDebe.getText());
            final BigDecimal haber = Format.toBigDec(txtHaber.getText());

            txtSaldoF.setText(Format.toStr(saldoIni.add(debe).subtract(haber)));
        } catch (final Exception _ex) {
            txtSaldoF.setText("");
        }

    }

    /**
     * Rev 09/01/2017
     *
     */
    void calcularDebe() {
        double totalDebe = 0.00d;
        for (int i = 0; i < tblAuxBco.getRowCount(); i++) {
            totalDebe += (double) tblAuxBco.getValueAt(i, colDebe);
        }

        try {
            txtDebe.setText(Globales.curFormat.valueToString(totalDebe));
        } catch (final Exception _ex) {
            txtDebe.setText("0,00");
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    /**
     * Rev 09/01/2017
     *
     */
    void calcularHaber() {
        double totalHaber = 0.00d;
        for (int i = 0; i < tblAuxBco.getRowCount(); i++) {
            totalHaber += (double) tblAuxBco.getValueAt(i, colHaber);
        }

        try {
            txtHaber.setText(Globales.curFormat.valueToString(totalHaber));
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            txtHaber.setText("0,00");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        grpMes = new javax.swing.ButtonGroup();
        btnSalir = new javax.swing.JButton();
        btnExcelCuentas = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCuentas = new javax.swing.JTable();
        btnRegistrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnDeposito = new javax.swing.JButton();
        btnTrans = new javax.swing.JButton();
        btnCredito = new javax.swing.JButton();
        btnDebito = new javax.swing.JButton();
        btnConciliaciones = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldfecha = new javax.swing.JFormattedTextField();
        txtTotal = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAuxBco = new javax.swing.JTable();
        btnConsultar = new javax.swing.JButton();
        btnTiposCuentas = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        txtLibro = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        tbtnEne = new javax.swing.JToggleButton();
        tbtnFeb = new javax.swing.JToggleButton();
        tbtnMar = new javax.swing.JToggleButton();
        jToggleButtonabr = new javax.swing.JToggleButton();
        jToggleButtonmay = new javax.swing.JToggleButton();
        jToggleButtonjun = new javax.swing.JToggleButton();
        jToggleButtonjul = new javax.swing.JToggleButton();
        jToggleButtonago = new javax.swing.JToggleButton();
        jToggleButtonsep = new javax.swing.JToggleButton();
        jToggleButtonoct = new javax.swing.JToggleButton();
        jToggleButtonnov = new javax.swing.JToggleButton();
        jToggleButtondic = new javax.swing.JToggleButton();
        btnAno = new javax.swing.JToggleButton();
        btnExcel1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblSaldoI = new javax.swing.JLabel();
        txtSaldoI = new javax.swing.JFormattedTextField();
        txtBenef = new javax.swing.JTextField();
        txtDebe = new javax.swing.JFormattedTextField();
        txtHaber = new javax.swing.JFormattedTextField();
        btnReset = new javax.swing.JButton();
        txtSaldoF = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("BANCOS");
        setMinimumSize(new java.awt.Dimension(1150, 730));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalir.setBackground(new java.awt.Color(153, 153, 0));
        btnSalir.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnSalir.setText("MENU PRINCIPAL");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 640, 200, 40));

        btnExcelCuentas.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnExcelCuentas.setText("EXCEL");
        btnExcelCuentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExcelCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelCuentasActionPerformed(evt);
            }
        });
        getContentPane().add(btnExcelCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, -1, -1));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1030, 768));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCuentas.setAutoCreateRowSorter(true);
        tblCuentas.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        tblCuentas.setForeground(new java.awt.Color(0, 51, 153));
        tblCuentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Banco", "Monto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCuentasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCuentas);
        if (tblCuentas.getColumnModel().getColumnCount() > 0) {
            tblCuentas.getColumnModel().getColumn(0).setMinWidth(150);
            tblCuentas.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblCuentas.getColumnModel().getColumn(0).setMaxWidth(250);
            tblCuentas.getColumnModel().getColumn(2).setMinWidth(100);
            tblCuentas.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblCuentas.getColumnModel().getColumn(2).setMaxWidth(200);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 0, 890, 140));

        btnRegistrar.setBackground(new java.awt.Color(0, 102, 102));
        btnRegistrar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("REGISTRAR CUENTA");
        btnRegistrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 182, 42));

        btnEliminar.setBackground(new java.awt.Color(0, 102, 102));
        btnEliminar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("ELIMINAR CUENTA");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 181, 42));

        btnImprimir.setBackground(new java.awt.Color(0, 102, 102));
        btnImprimir.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnImprimir.setForeground(new java.awt.Color(255, 255, 255));
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnImprimir.setEnabled(false);
        jPanel1.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, 184, 39));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, 225, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SALDO TOTAL BS.");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 65, 200, 20));

        btnDeposito.setBackground(new java.awt.Color(0, 102, 102));
        btnDeposito.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnDeposito.setForeground(new java.awt.Color(255, 255, 255));
        btnDeposito.setText("DEPOSITOS");
        btnDeposito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnDeposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositoActionPerformed(evt);
            }
        });
        jPanel1.add(btnDeposito, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, 210, 42));

        btnTrans.setBackground(new java.awt.Color(0, 102, 102));
        btnTrans.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnTrans.setForeground(new java.awt.Color(255, 255, 255));
        btnTrans.setText("TRANSFERENCIA");
        btnTrans.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransActionPerformed(evt);
            }
        });
        jPanel1.add(btnTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, 184, 42));

        btnCredito.setBackground(new java.awt.Color(0, 102, 102));
        btnCredito.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnCredito.setForeground(new java.awt.Color(255, 255, 255));
        btnCredito.setText("NOTAS DE CREDITO");
        btnCredito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreditoActionPerformed(evt);
            }
        });
        jPanel1.add(btnCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 181, 38));

        btnDebito.setBackground(new java.awt.Color(0, 102, 102));
        btnDebito.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnDebito.setForeground(new java.awt.Color(255, 255, 255));
        btnDebito.setText("NOTAS DE DEBITO");
        btnDebito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDebitoActionPerformed(evt);
            }
        });
        jPanel1.add(btnDebito, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 181, 39));

        btnConciliaciones.setBackground(new java.awt.Color(0, 102, 102));
        btnConciliaciones.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnConciliaciones.setForeground(new java.awt.Color(255, 255, 255));
        btnConciliaciones.setText("CONCILIACIONES BANCARIAS");
        btnConciliaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnConciliaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConciliacionesActionPerformed(evt);
            }
        });
        jPanel1.add(btnConciliaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, 210, 39));

        jLabel4.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText(" FECHA");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, 56, -1));

        jTextFieldfecha.setBackground(new java.awt.Color(204, 153, 0));
        jTextFieldfecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jTextFieldfecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(""))));
        jTextFieldfecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldfecha.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        jTextFieldfecha.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jPanel1.add(jTextFieldfecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, 130, 40));

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(204, 102, 0));
        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtTotal.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jPanel1.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 90, 220, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1140, 240));

        jLabel1.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CUENTAS BANCARIAS");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 330, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("LIBRO AUXILIAR DE BANCOS");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 590, -1));

        tblAuxBco.setAutoCreateRowSorter(true);
        tblAuxBco.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        tblAuxBco.setForeground(new java.awt.Color(0, 51, 153));
        tblAuxBco.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FECHA", "SOPORTE", "DESCRIPCION", "TIPO", "DEBE BS.", "HABER BS.", "CONCILIADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAuxBco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAuxBcoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAuxBco);
        if (tblAuxBco.getColumnModel().getColumnCount() > 0) {
            tblAuxBco.getColumnModel().getColumn(0).setMinWidth(75);
            tblAuxBco.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblAuxBco.getColumnModel().getColumn(0).setMaxWidth(150);
            tblAuxBco.getColumnModel().getColumn(1).setMinWidth(100);
            tblAuxBco.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblAuxBco.getColumnModel().getColumn(1).setMaxWidth(200);
            tblAuxBco.getColumnModel().getColumn(3).setMinWidth(40);
            tblAuxBco.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblAuxBco.getColumnModel().getColumn(3).setMaxWidth(60);
            tblAuxBco.getColumnModel().getColumn(4).setMinWidth(100);
            tblAuxBco.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblAuxBco.getColumnModel().getColumn(4).setMaxWidth(200);
            tblAuxBco.getColumnModel().getColumn(5).setMinWidth(100);
            tblAuxBco.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblAuxBco.getColumnModel().getColumn(5).setMaxWidth(200);
            tblAuxBco.getColumnModel().getColumn(6).setMinWidth(100);
            tblAuxBco.getColumnModel().getColumn(6).setPreferredWidth(120);
            tblAuxBco.getColumnModel().getColumn(6).setMaxWidth(200);
        }

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 1130, 120));

        btnConsultar.setBackground(new java.awt.Color(153, 153, 0));
        btnConsultar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnConsultar.setEnabled(false);
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        getContentPane().add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 640, 190, 40));

        btnTiposCuentas.setBackground(new java.awt.Color(153, 153, 0));
        btnTiposCuentas.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnTiposCuentas.setText("REPORTE LIBRO BANCO");
        btnTiposCuentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnTiposCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiposCuentasActionPerformed(evt);
            }
        });
        getContentPane().add(btnTiposCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 640, 240, 40));

        btnExcel.setBackground(new java.awt.Color(153, 153, 0));
        btnExcel.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnExcel.setText("EXPORTAR  EXCEL");
        btnExcel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnExcel.setEnabled(false);
        getContentPane().add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 640, 220, 40));

        txtLibro.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtLibro.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtLibro.setEnabled(false);
        getContentPane().add(txtLibro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 590, 30));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        tbtnEne.setBackground(new java.awt.Color(255, 102, 0));
        grpMes.add(tbtnEne);
        tbtnEne.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        tbtnEne.setText("Ene");
        tbtnEne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnEneActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(tbtnEne, gridBagConstraints);

        tbtnFeb.setBackground(new java.awt.Color(255, 102, 0));
        grpMes.add(tbtnFeb);
        tbtnFeb.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        tbtnFeb.setText("Feb");
        tbtnFeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnFebActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel2.add(tbtnFeb, gridBagConstraints);

        tbtnMar.setBackground(new java.awt.Color(255, 102, 0));
        grpMes.add(tbtnMar);
        tbtnMar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        tbtnMar.setText("Mar");
        tbtnMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnMarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel2.add(tbtnMar, gridBagConstraints);

        jToggleButtonabr.setBackground(new java.awt.Color(255, 102, 0));
        grpMes.add(jToggleButtonabr);
        jToggleButtonabr.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtonabr.setText("Abr");
        jToggleButtonabr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonabrActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtonabr, gridBagConstraints);

        jToggleButtonmay.setBackground(new java.awt.Color(102, 102, 0));
        grpMes.add(jToggleButtonmay);
        jToggleButtonmay.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtonmay.setText("May");
        jToggleButtonmay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonmayActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtonmay, gridBagConstraints);

        jToggleButtonjun.setBackground(new java.awt.Color(102, 102, 0));
        grpMes.add(jToggleButtonjun);
        jToggleButtonjun.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtonjun.setText("Jun");
        jToggleButtonjun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonjunActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtonjun, gridBagConstraints);

        jToggleButtonjul.setBackground(new java.awt.Color(102, 102, 0));
        grpMes.add(jToggleButtonjul);
        jToggleButtonjul.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtonjul.setText("Jul");
        jToggleButtonjul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonjulActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtonjul, gridBagConstraints);

        jToggleButtonago.setBackground(new java.awt.Color(102, 102, 0));
        grpMes.add(jToggleButtonago);
        jToggleButtonago.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtonago.setText("Ago");
        jToggleButtonago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonagoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtonago, gridBagConstraints);

        jToggleButtonsep.setBackground(new java.awt.Color(51, 102, 0));
        grpMes.add(jToggleButtonsep);
        jToggleButtonsep.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtonsep.setText("Sep");
        jToggleButtonsep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonsepActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtonsep, gridBagConstraints);

        jToggleButtonoct.setBackground(new java.awt.Color(51, 102, 0));
        grpMes.add(jToggleButtonoct);
        jToggleButtonoct.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtonoct.setText("Oct");
        jToggleButtonoct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonoctActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtonoct, gridBagConstraints);

        jToggleButtonnov.setBackground(new java.awt.Color(51, 102, 0));
        grpMes.add(jToggleButtonnov);
        jToggleButtonnov.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtonnov.setText("Nov");
        jToggleButtonnov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonnovActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtonnov, gridBagConstraints);

        jToggleButtondic.setBackground(new java.awt.Color(51, 102, 0));
        grpMes.add(jToggleButtondic);
        jToggleButtondic.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jToggleButtondic.setText("Dic");
        jToggleButtondic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtondicActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jToggleButtondic, gridBagConstraints);

        btnAno.setBackground(new java.awt.Color(255, 102, 0));
        grpMes.add(btnAno);
        btnAno.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAno.setSelected(true);
        btnAno.setText("Año");
        btnAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel2.add(btnAno, gridBagConstraints);

        btnExcel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnExcel1.setText("EXCEL");
        btnExcel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExcel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcel1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(btnExcel1, gridBagConstraints);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 570, 1010, 50));

        jLabel5.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Beneficiario");
        jLabel5.setToolTipText("");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 100, 30));

        jLabel8.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Debe");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 530, 50, 30));

        jLabel10.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Haber");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 530, 60, 30));

        lblSaldoI.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        lblSaldoI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSaldoI.setLabelFor(txtSaldoI);
        lblSaldoI.setText("Saldo Inicial");
        getContentPane().add(lblSaldoI, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, 90, 30));

        txtSaldoI.setEditable(false);
        txtSaldoI.setBackground(new java.awt.Color(204, 153, 0));
        txtSaldoI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSaldoI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtSaldoI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSaldoI.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtSaldoI.setEnabled(false);
        txtSaldoI.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        getContentPane().add(txtSaldoI, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 530, 180, 30));

        txtBenef.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtBenef.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtBenef.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtBenef.setEnabled(false);
        getContentPane().add(txtBenef, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 470, 30));

        txtDebe.setEditable(false);
        txtDebe.setBackground(new java.awt.Color(204, 153, 0));
        txtDebe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtDebe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtDebe.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDebe.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtDebe.setEnabled(false);
        txtDebe.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        getContentPane().add(txtDebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 530, 180, 30));

        txtHaber.setEditable(false);
        txtHaber.setBackground(new java.awt.Color(204, 153, 0));
        txtHaber.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtHaber.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtHaber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHaber.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtHaber.setEnabled(false);
        txtHaber.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        getContentPane().add(txtHaber, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 530, 180, 30));

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        getContentPane().add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtSaldoF.setEditable(false);
        txtSaldoF.setBackground(new java.awt.Color(204, 153, 0));
        txtSaldoF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSaldoF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtSaldoF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSaldoF.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtSaldoF.setEnabled(false);
        txtSaldoF.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        getContentPane().add(txtSaldoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 530, 180, 30));

        jLabel12.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Saldo Final");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 530, 100, 30));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        jLabel11.setText("jLabel11");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 750));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BancosCuentasRegistro(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
//        BancoCuentaEliminar obj = new BancoCuentaEliminar();
//        obj.setVisible(true);
//        dispose();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnDepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositoActionPerformed
        java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BancosDeposito(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnDepositoActionPerformed

    private void btnTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransActionPerformed
        java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BancosTransferencia(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnTransActionPerformed

    private void btnDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDebitoActionPerformed
        java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BancosNotaDebito(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnDebitoActionPerformed

    private void btnCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreditoActionPerformed
        java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BancosNotaCredito(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnCreditoActionPerformed

    private void btnConciliacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConciliacionesActionPerformed
        java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BancosConciliacion(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnConciliacionesActionPerformed

    private void tblCuentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCuentasMouseClicked
//        CalcularTodo();
    }//GEN-LAST:event_tblCuentasMouseClicked

    private void CalcularTodo() {
        final String cuenta;

        final int fila = tblCuentas.getSelectedRow();
        if (fila < 0) {
            txtLibro.setText("");
            cuenta = "";
        } else {
            txtLibro.setText((String) tblCuentas.getValueAt(fila, colCuenta) + "     " + (String) tblCuentas.getValueAt(fila, colBanco));
            cuenta = (String) tblCuentas.getValueAt(fila, colCuenta);
        }

        MostrarTblAuxiliar(cuenta);
        calcularSaldoI();
        calcularDebe();
        calcularHaber();
        calcularSaldoF();
    }

    private void tblAuxBcoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAuxBcoMouseClicked
        int fila = tblAuxBco.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return;
        }

        txtBenef.setText(tblAuxBco.getValueAt(fila, colDesc).toString());
    }//GEN-LAST:event_tblAuxBcoMouseClicked

    private void tbtnEneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnEneActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_tbtnEneActionPerformed

    private void tbtnFebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnFebActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_tbtnFebActionPerformed

    private void tbtnMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnMarActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_tbtnMarActionPerformed

    private void jToggleButtonabrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonabrActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtonabrActionPerformed

    private void jToggleButtonmayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonmayActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtonmayActionPerformed

    private void jToggleButtonjunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonjunActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtonjunActionPerformed

    private void jToggleButtonjulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonjulActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtonjulActionPerformed

    private void jToggleButtonagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonagoActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtonagoActionPerformed

    private void jToggleButtonsepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonsepActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtonsepActionPerformed

    private void jToggleButtonoctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonoctActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtonoctActionPerformed

    private void jToggleButtonnovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonnovActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtonnovActionPerformed

    private void jToggleButtondicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtondicActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_jToggleButtondicActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReporteLibBan(me).setVisible(true);
                setVisible(false);
            }
        });
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnoActionPerformed
        final String cuenta;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
        }

        MostrarTblAuxiliar(cuenta);
        CalcularTodo();
    }//GEN-LAST:event_btnAnoActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        tblCuentas.clearSelection();

        CalcularTodo();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnExcel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel1ActionPerformed
        try {
            final String fileName = "RptAuxBanco " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblAuxBco, fileName + ".xls");
            TableToExcel.exportTSV(tblAuxBco, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }//GEN-LAST:event_btnExcel1ActionPerformed

    private void btnExcelCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelCuentasActionPerformed
        try {
            final String fileName = "RptCuentas " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblCuentas, fileName + ".xls");
            TableToExcel.exportTSV(tblCuentas, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }//GEN-LAST:event_btnExcelCuentasActionPerformed

    private void btnTiposCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiposCuentasActionPerformed
        actReporteLibroBanco();
    }//GEN-LAST:event_btnTiposCuentasActionPerformed

    /**
     * Rev 08/01/2017
     *
     */
    private void actReporteLibroBanco() {
        if (tblAuxBco.getRowCount() <= 0) {
            return;
        }

        int rowSel = tblCuentas.getSelectedRow();
        if (rowSel < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una cuenta");
            tblCuentas.requestFocusInWindow();
            return;
        }

        final Map<String, Object> param = new HashMap<>(101);
        param.put("id_user", UserPassIn.getIdUser());
        param.put("id_session", UserPassIn.getIdSession());
        try {
            param.put("date_session", UserPassIn.getDateSession());
        } catch (final Exception ex) {
            param.put("date_session", Globales.getServerSqlDate());
        }
        param.put("ejefis", Globales.getEjeFisYear());
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("user", UserPassIn.getUserName());

        param.put("linea_1", CapipPropiedades.CAPIP_LINEA_1);
        param.put("linea_2", CapipPropiedades.CAPIP_LINEA_2);
        param.put("linea_3", CapipPropiedades.CAPIP_LINEA_3);
        param.put("linea_4", CapipPropiedades.CAPIP_LINEA_4);
        param.put("aux_1", CapipPropiedades.CAPIP_AUX_1);
        param.put("aux_2", CapipPropiedades.CAPIP_AUX_2);

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

        param.put("saldoi", txtSaldoI.getText());
        param.put("debe", txtDebe.getText());
        param.put("haber", txtHaber.getText());
        param.put("saldof", txtSaldoF.getText());

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final String cuenta;
        final String banco;
        if (txtLibro.getText().isEmpty()) {
            cuenta = "";
            banco = "";
        } else {
            cuenta = txtLibro.getText().substring(0, 20);
            banco = txtLibro.getText().substring(20);
        }

        param.put("banco", banco);
        param.put("cuenta", cuenta);
        param.put("mesdesde", mesdesde);
        param.put("meshasta", meshasta);

        final BigDecimal saldo;
        try {
            saldo = Format.toBigDec(txtSaldoF.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al formatear el Saldo" + System.getProperty("line.separator") + _ex);
            return;
        }
        param.put("saldo", saldo);

        // Generar y agrupar los registros
        final ArrayList<LibroBancoRptModel> list = new ArrayList<>();

        for (int i = 0; i < tblAuxBco.getRowCount(); i++) {
            final String fecha = Format.toStr((java.sql.Date) tblAuxBco.getValueAt(i, colFecha));
            final String soporte = (String) tblAuxBco.getValueAt(i, colSoporte);
            final String desc = (String) tblAuxBco.getValueAt(i, colDesc);
            final String tipo = (String) tblAuxBco.getValueAt(i, colTipo);
            final BigDecimal debe = BigDecimal.valueOf((double) tblAuxBco.getValueAt(i, colDebe));
            final BigDecimal haber = BigDecimal.valueOf((double) tblAuxBco.getValueAt(i, colHaber));
            final String conciliado = (String) tblAuxBco.getValueAt(i, colConciliado);

            list.add(new LibroBancoRptModel(i + 1, fecha, soporte, desc, tipo, debe, haber, conciliado));
        }

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/libro de banco.jasper");
        if (pathRpt != null) {

            new Thread() {
                @Override
                public void run() {
                    try {
                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), new JRBeanCollectionDataSource(list));

                        JasperViewer.viewReport(jasperPrint, false);

                        final String id_file = Format.toStr(Globales.getServerTimeStamp()).replace("/", "-").replace(":", ".");

                        JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL + "/" + "libroBanco " + id_file + ".pdf");
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();

        } else {
            JOptionPane.showMessageDialog(null, "Reporte de pago no encontrado");
        }

    }

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
     * Rev 08/01/2017
     *
     * @param cuenta
     * @param monto
     * @throws Exception
     */
//    public static void sumarCuenta(final String cuenta, final double monto) throws Exception {
//        if (monto < 0) {
//            JOptionPane.showMessageDialog(null, "El monto no puede ser negativo");
//            return;
//        }
//
//        final long id_banco;
//        final double saldoAnt;
//        final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos WHERE cuenta='" + cuenta + "'");
//        if (rs.next()) {
//            id_banco = rs.getLong("id");
//            saldoAnt = rs.getDouble("saldof");
//        } else {
//            throw new Exception("Error al recuperar el saldo anterior");
//        }
//
//        final double saldoNuevo = saldoAnt + monto;
//        final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE bancos SET saldof='" + saldoNuevo + "' WHERE id= " + id_banco);
//        if (pst.executeUpdate() != 1) {
//            throw new Exception("Error al actualizar saldo final");
//        }
//
//        // Actualizar el saldo anual de banco
//        final PreparedStatement pstSaldoAnual = Conn.getConnection().prepareStatement("UPDATE banco_saldo_anual SET saldof= ? WHERE id_banco= ? AND YEAR(ejefis) = ?");
//        pstSaldoAnual.setBigDecimal(1, Format.toBigDec(saldoNuevo));
//        pstSaldoAnual.setLong(2, id_banco);
//        pstSaldoAnual.setLong(3, Globales.getEjeFisYear());
//        if (pstSaldoAnual.executeUpdate() != 1) {
//            throw new Exception("Error al actualizar saldo final, en el saldo anual");
//        }
//    }
//    /**
//     * Rev 08/01/2017
//     *
//     * @param cuenta
//     * @param monto
//     * @throws Exception
//     */
//    public static void restarCuenta(final String cuenta, final double monto) throws Exception {
//        if (monto < 0) {
//            throw new Exception("El monto no puede ser negativo");
//        }
//
//        final long id_banco;
//        final double saldoAnt;
//
//        final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos WHERE  cuenta='" + cuenta + "'");
//        if (rs.next()) {
//            id_banco = rs.getLong("id");
//            saldoAnt = rs.getDouble("saldof");
//        } else {
//            throw new Exception("Error al recuperar el saldo final");
//        }
//
//        final double saldoNuevo = saldoAnt - monto;
//        if (saldoNuevo < 0) {
//            throw new Exception("Saldo en cuenta insuficiente");
//        }
//
//        final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE bancos SET saldof='" + saldoNuevo + "' WHERE id = " + id_banco);
//        if (pst.executeUpdate() != 1) {
//            throw new Exception("Error al actualizar saldo final");
//        }
//
//        // Actualizar el saldo anual de banco
//        final PreparedStatement pstSaldoAnual = Conn.getConnection().prepareStatement("UPDATE banco_saldo_anual SET saldof= ? WHERE id_banco= ? AND YEAR(ejefis) = ?");
//        pstSaldoAnual.setBigDecimal(1, Format.toBigDec(saldoNuevo));
//        pstSaldoAnual.setLong(2, id_banco);
//        pstSaldoAnual.setLong(3, Globales.getEjeFisYear());
//        if (pstSaldoAnual.executeUpdate() != 1) {
//            throw new Exception("Error al actualizar saldo final, en el saldo anual");
//        }
//
//    }
//
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
                new BancosContabilidad(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAno;
    private javax.swing.JButton btnConciliaciones;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnCredito;
    private javax.swing.JButton btnDebito;
    private javax.swing.JButton btnDeposito;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnExcel1;
    private javax.swing.JButton btnExcelCuentas;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTiposCuentas;
    private javax.swing.JButton btnTrans;
    private javax.swing.ButtonGroup grpMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFormattedTextField jTextFieldfecha;
    private javax.swing.JToggleButton jToggleButtonabr;
    private javax.swing.JToggleButton jToggleButtonago;
    private javax.swing.JToggleButton jToggleButtondic;
    private javax.swing.JToggleButton jToggleButtonjul;
    private javax.swing.JToggleButton jToggleButtonjun;
    private javax.swing.JToggleButton jToggleButtonmay;
    private javax.swing.JToggleButton jToggleButtonnov;
    private javax.swing.JToggleButton jToggleButtonoct;
    private javax.swing.JToggleButton jToggleButtonsep;
    private javax.swing.JLabel lblSaldoI;
    private javax.swing.JTable tblAuxBco;
    private javax.swing.JTable tblCuentas;
    private javax.swing.JToggleButton tbtnEne;
    private javax.swing.JToggleButton tbtnFeb;
    private javax.swing.JToggleButton tbtnMar;
    private javax.swing.JTextField txtBenef;
    private javax.swing.JFormattedTextField txtDebe;
    private javax.swing.JFormattedTextField txtHaber;
    private javax.swing.JTextField txtLibro;
    private javax.swing.JFormattedTextField txtSaldoF;
    private javax.swing.JFormattedTextField txtSaldoI;
    private javax.swing.JFormattedTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    private final Connection cn = Conn.getConnection();
}
