/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import capipsistema.CapipPropiedades;
import connection.ConnCapip;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import causados.Causado;
import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.CauDetModel;
import modelos.CauModel;
import modelos.ComprDetModel;
import modelos.ComprModel;
import modelos.PagoModel;
import modelos.PptoExecModel;
import modelos.PptoModel;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import static presupuesto.XJFrame.COLOBJ;
import static presupuesto.XJFrame.JBCANCEL;
import static presupuesto.XJFrame.JBSELECT;
import static presupuesto.XJFrame.JTABLE;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;
import utils.TableToExcel;
import utils.TipoCompr;

/**
 *
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class PptoEjecucionMonthReport extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del código programado, mantiene relacionado el nombre de la columna con su posición
     */
    private static final int colCodPresu = 0;
    private static final int colPartida = 1;
    private static final int colPptoAprob = 2;
    private static final int colPptoIni = 3;
    private static final int colCredito = 4;
    private static final int colTrasp = 5;
    private static final int colPptoMod = 6;
    private static final int colCompr = 7;
    private static final int colSaldo = 8;
    private static final int colCau = 9;
    private static final int colPag = 10;
    private static final int colReg = 11;

    private long ejefis_year;
    private String mesDesde;
    private String mesHasta;
    private long ejeFisMesDesde;
    private long ejeFisMesHasta;

    /**
     *
     * @param inparent
     */
    public PptoEjecucionMonthReport(final java.awt.Window inparent) {
        super( inparent);
        initComponents();

        setOwnBehavior();
        long x[] = new long[TipoCompr.CO.ordinal()];
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

    }

    private void setCompBehavior() {

        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, btnRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, txtReset);
        param.put(JTABLE, tblMaster);
        param.put(COLOBJ, colReg);
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
        txtEjeFis.setText(String.valueOf(Globales.getServerYear()));
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
     * Rev 21/11/2016
     *
     * @param inparam
     */
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

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colCodPresu).setCellRenderer(tcr);

        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                txtCant.setText(String.valueOf(tblMaster.getRowCount()));
            }
        });

        // Configurar la tabla
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());

        tblTotales.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblTotales.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblTotales.setDefaultRenderer(Double.class, new DecimalCellRenderer());
    }

    /**
     * Rev 21/11/2016
     *
     */
    @Override
    protected void UpdatePanels() {
        UpdateJTable();
        UpdateEnabledAndFocus();
    }

    /**
     * Actualizar la vista de la ventana. Deberia llamarse cuando se halla modificado la tabla Rev 21/11/2016
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

        BigDecimal totPpto = BigDecimal.ZERO;
        BigDecimal totIni = BigDecimal.ZERO;
        BigDecimal totCre = BigDecimal.ZERO;
        BigDecimal totTra = BigDecimal.ZERO;
        BigDecimal totPptoMod = BigDecimal.ZERO;
        BigDecimal totCompr = BigDecimal.ZERO;
        BigDecimal totSal = BigDecimal.ZERO;
        BigDecimal totCau = BigDecimal.ZERO;
        BigDecimal totPag = BigDecimal.ZERO;

        final Object[] datos = new Object[12];
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            // Generar el comando SQL
            final SortedMap<String, PptoExecModel> list = genList();

            for (PptoExecModel reg : list.values()) {

                final BigDecimal apr_bs = reg.getAprobado();
                final BigDecimal ini_bs = reg.getInicial();
                final BigDecimal cre_bs = reg.getCredito();
                final BigDecimal tra_bs = reg.getTraspaso();
                final BigDecimal pptoMod_bs = reg.getPptoMod();
                final BigDecimal compr_bs = reg.getCompromiso();
                final BigDecimal sal_bs = reg.getSaldo();
                final BigDecimal cau_bs = reg.getCausado();
                final BigDecimal pag_bs = reg.getPagado();

                datos[colCodPresu] = reg.getCodpresu();
                datos[colPartida] = reg.getPartida();
                datos[colPptoAprob] = apr_bs;
                datos[colPptoIni] = ini_bs;
                datos[colCredito] = cre_bs;
                datos[colTrasp] = tra_bs;
                datos[colPptoMod] = pptoMod_bs;
                datos[colCompr] = compr_bs;
                datos[colSaldo] = sal_bs;
                datos[colCau] = cau_bs;
                datos[colPag] = pag_bs;
                datos[colReg] = reg;

                totPpto = totPpto.add(apr_bs);
                totIni = totIni.add(ini_bs);
                totCre = totCre.add(cre_bs);
                if (tra_bs.compareTo(BigDecimal.ZERO) > 0) {
                    totTra = totTra.add(tra_bs);
//                    totPptoMod = totPptoMod.add(pptoMod_bs);
                }

                totPptoMod = totPptoMod.add(pptoMod_bs);

                totCompr = totCompr.add(compr_bs);
                totSal = totSal.add(sal_bs);
                totCau = totCau.add(cau_bs);
                totPag = totPag.add(pag_bs);

                model.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

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

        tblTotales.setValueAt(totPpto, 0, colPptoAprob);
        tblTotales.setValueAt(totIni, 0, colPptoIni);
        tblTotales.setValueAt(totCre, 0, colCredito);
        tblTotales.setValueAt(totTra, 0, colTrasp);
        tblTotales.setValueAt(totPptoMod, 0, colPptoMod);
        tblTotales.setValueAt(totCompr, 0, colCompr);
        tblTotales.setValueAt(totSal, 0, colSaldo);
        tblTotales.setValueAt(totCau, 0, colCau);
        tblTotales.setValueAt(totPag, 0, colPag);

        txtCant.setText(String.valueOf(tblMaster.getRowCount()));
    }

    /**
     * Genera varias listas, que contienen un resumen de la ejecucion por periodo
     *
     * Rev 20-02-2017
     *
     */
    private SortedMap<String, PptoExecModel> genList() {

        setPeriodo();
        final SortedMap<String, PptoExecModel> list = new TreeMap<>();

        final HashMap<String, PptoModel> listPpto = new HashMap<>(101);
        final HashMap<String, BigDecimal> listSaldo = new HashMap<>(101);
        final HashMap<String, BigDecimal> listCre = new HashMap<>(101);
        final HashMap<String, BigDecimal> listTra = new HashMap<>(101);
        final HashMap<String, BigDecimal> listPptoMod = new HashMap<>(101);
        final HashMap<String, BigDecimal> listCompr = new HashMap<>(101);
        final HashMap<String, BigDecimal> listCau = new HashMap<>(101);
        final HashMap<String, BigDecimal> listPag = new HashMap<>(101);

        try {
            // Buscar todos los registros del presupuesto
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM presupe WHERE ejefis= ? AND anulado_sn= 'N' ORDER BY codigo");
            pst.setLong(1, ejefis_year);
            try (final ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    final PptoModel regPpto = new PptoModel(rs);

                    final String codigo = regPpto.getCodigo();

                    listPpto.put(codigo, regPpto);
                    listCre.put(codigo, BigDecimal.ZERO);
                    listTra.put(codigo, BigDecimal.ZERO);
                    listPptoMod.put(codigo, BigDecimal.ZERO);
                    listCompr.put(codigo, BigDecimal.ZERO);
                    listSaldo.put(codigo, BigDecimal.ZERO);
                    listCau.put(codigo, BigDecimal.ZERO);
                    listPag.put(codigo, BigDecimal.ZERO);
                }

                getSaldoHastaEx(listPpto, listSaldo);
                getCredito_delMes(listCre);
                getTraspaso_delMes(listTra);

                // Generar el Ppto. Mod.
                for (PptoModel reg : listPpto.values()) {
                    final String codigo = reg.getCodigo();

//                    final BigDecimal ppto = reg.getMontoIni();
                    BigDecimal saldoIni = listSaldo.get(codigo);
                    if (saldoIni == null) {
                        saldoIni = BigDecimal.ZERO;
                    }

                    final BigDecimal cre;
                    final BigDecimal tra;

                    if (listCre.containsKey(codigo)) {
                        cre = listCre.get(codigo);
                    } else {
                        cre = BigDecimal.ZERO;
                    }

                    if (listTra.containsKey(codigo)) {
                        tra = listTra.get(codigo);
                    } else {
                        tra = BigDecimal.ZERO;
                    }

//                     listPptoMod.put(codigo, ppto.add(cre)).add(tra).setScale(2, RoundingMode.HALF_UP);
                    listPptoMod.put(codigo, saldoIni.add(cre).add(tra).setScale(2, RoundingMode.HALF_UP));
                }

                getCompr_delMes(listCompr);
                getCau_delMes(listCau);
                getPagado_delMes(listPag);

                // Genero el registro
                for (PptoModel reg : listPpto.values()) {
                    final String codigo = reg.getCodigo();

                    BigDecimal saldoIni = listSaldo.get(codigo);
                    if (saldoIni == null) {
                        saldoIni = BigDecimal.ZERO;
                    }

                    BigDecimal cre_bs = listCre.get(codigo);
                    if (cre_bs == null) {
                        cre_bs = BigDecimal.ZERO;
                    }

                    BigDecimal tra_bs = listTra.get(codigo);
                    if (tra_bs == null) {
                        tra_bs = BigDecimal.ZERO;
                    }

                    BigDecimal pptoMod_bs = listPptoMod.get(codigo);
                    if (pptoMod_bs == null) {
                        pptoMod_bs = BigDecimal.ZERO;
                    }

                    BigDecimal compr_bs = listCompr.get(codigo);
                    if (compr_bs == null) {
                        compr_bs = BigDecimal.ZERO;
                    }

                    final BigDecimal saldo = saldoIni.add(cre_bs).add(tra_bs).subtract(compr_bs).setScale(2, RoundingMode.HALF_UP);

                    BigDecimal cau_bs = listCau.get(codigo);
                    if (cau_bs == null) {
                        cau_bs = BigDecimal.ZERO;
                    }

                    BigDecimal pago_bs = listPag.get(codigo);
                    if (pago_bs == null) {
                        pago_bs = BigDecimal.ZERO;
                    }

                    list.put(codigo, new PptoExecModel(reg, saldoIni, cre_bs, tra_bs, pptoMod_bs, compr_bs, saldo, cau_bs, pago_bs));
                }

            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }

        return list;
    }

    /**
     * Rev 18-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getSaldoHastaEx(final HashMap<String, PptoModel> inlistPpto, final HashMap<String, BigDecimal> inlistIni) {
        final HashMap<String, BigDecimal> listCre = new HashMap<>(101);
        final HashMap<String, BigDecimal> listTra = new HashMap<>(101);
        final HashMap<String, BigDecimal> listCompr = new HashMap<>(101);

        getCreditoHastaEx(listCre);
        getTraspasoHastaEx(listTra);
        getComprHastaEx(listCompr);

        for (PptoModel reg : inlistPpto.values()) {
            final String codPresu = reg.getCodigo();

            BigDecimal cre_bs = listCre.get(codPresu);
            if (cre_bs == null) {
                cre_bs = BigDecimal.ZERO;
            }

            BigDecimal tra_bs = listTra.get(codPresu);
            if (tra_bs == null) {
                tra_bs = BigDecimal.ZERO;
            }

            BigDecimal compr_bs = listCompr.get(codPresu);
            if (compr_bs == null) {
                compr_bs = BigDecimal.ZERO;
            }

            inlistIni.put(codPresu, reg.getMontoIni().add(cre_bs).add(tra_bs).subtract(compr_bs).setScale(2, RoundingMode.HALF_UP));
        }

    }

    /**
     * Rev 17-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getCreditoHastaEx(final HashMap<String, BigDecimal> inlist) {

        // Verificar si se trata del mes de Enero, en cuyo caso no es necesario realizar algun proceso
        if (ejeFisMesHasta <= 1) {
            return;
        }

        final long mesIni = ejefis_year * 100 + 1; // mes de Enero como inicio de la busqueda
        final long mesFin = ejefis_year * 100 + ejeFisMesHasta; // el mes actual, que se exluye mediante el "<" del query

        try {
            // Buscar todos los registros de Credito Adicional
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM creditoadicional WHERE ejefis= ? AND (ejefismes >= ?) AND (ejefismes < ?)");
            pst.setLong(1, ejefis_year);
            pst.setLong(2, mesIni);
            pst.setLong(3, mesFin);
            try (final ResultSet rsCre = pst.executeQuery()) {
                while (rsCre.next()) {
                    final long id_credito = rsCre.getLong("id");

                    // Buscar todos los detalles del Credito Adicional
                    final PreparedStatement pstCreDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM creditoadicional_det WHERE ejefis= ? AND id_cre_adi= ?");
                    pstCreDet.setLong(1, ejefis_year);
                    pstCreDet.setLong(2, id_credito);

                    try (final ResultSet rsCreDet = pstCreDet.executeQuery()) {
                        while (rsCreDet.next()) {
                            final String codigo = rsCreDet.getString("codigo");
                            final BigDecimal monto = rsCreDet.getBigDecimal("monto");

                            final BigDecimal suma = inlist.containsKey(codigo) ? inlist.get(codigo) : BigDecimal.ZERO;

                            inlist.put(codigo, suma.add(monto).setScale(2, RoundingMode.HALF_UP));
                        }
                    }
                }
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }
    }

    /**
     * Rev 18-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getCredito_delMes(final HashMap<String, BigDecimal> inlist) {
        final long month = ejefis_year * 100 + ejeFisMesHasta;

        try {
            // Buscar todos los registros de Credito Adicional
            final PreparedStatement pst = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM creditoadicional WHERE ejefis= ? AND ejefismes = ?");
            pst.setLong(1, ejefis_year);
            pst.setLong(2, month);
            try (final ResultSet rsCre = pst.executeQuery()) {
                while (rsCre.next()) {
                    final long id_credito = rsCre.getLong("id");

                    // Buscar todos los detalles del Credito Adicional
                    final PreparedStatement pstCreDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM creditoadicional_det WHERE ejefis= ? AND id_cre_adi= ?");
                    pstCreDet.setLong(1, ejefis_year);
                    pstCreDet.setLong(2, id_credito);
                    try (final ResultSet rsCreDet = pstCreDet.executeQuery()) {
                        while (rsCreDet.next()) {
                            final String codigo = rsCreDet.getString("codigo");
                            final BigDecimal monto = rsCreDet.getBigDecimal("monto");

                            final BigDecimal suma = inlist.containsKey(codigo) ? inlist.get(codigo) : BigDecimal.ZERO;

                            inlist.put(codigo, suma.add(monto).setScale(2, RoundingMode.HALF_UP));
                        }
                    }
                }
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }
    }

    /**
     * Recupera los traspasos del periodo seleccionado, hasta el ultimo mes, exclusivo.
     *
     * Rev 18-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getTraspasoHastaEx(final HashMap<String, BigDecimal> inlist) {
        // Verificar si se trata del mes de Enero, en cuyo caso no es necesario realizar algun proceso
        if (ejeFisMesHasta <= 1) {
            return;
        }

        final String monthIni = ejefis_year + "/01/01"; // el primer dia del año
        final String monthEnd = ejefis_year + "/" + ejeFisMesHasta + "/01"; // el primer dia del mes

        try {
            // Buscar todos los registros de los Traspasos
            final PreparedStatement pstTrasp = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM trasppartidas WHERE (ejefis = ?) AND (fecha >= ?) AND (fecha < ?) AND (ppto_egr_ing = 'E') AND (anulado_sn= 'N')");
            pstTrasp.setLong(1, ejefis_year);
            pstTrasp.setString(2, monthIni);
            pstTrasp.setString(3, monthEnd);
            try (final ResultSet rsTrasp = pstTrasp.executeQuery()) {
                while (rsTrasp.next()) {
                    final long id_trasp = rsTrasp.getLong("id_trasp");

                    // Buscar todos los detalles del Credito Adicional
                    final PreparedStatement pstTraspDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM trasppartidas_det WHERE (YEAR(date_session) = ?) AND id_trasp= ?");
                    pstTraspDet.setLong(1, ejefis_year);
                    pstTraspDet.setLong(2, id_trasp);

                    try (final ResultSet rsTrasDet = pstTraspDet.executeQuery()) {
                        while (rsTrasDet.next()) {
                            final String ori_dest = rsTrasDet.getString("tipo_ori_dest");
                            final String codigo = rsTrasDet.getString("codpresu");
                            final BigDecimal monto = rsTrasDet.getBigDecimal("monto");

                            final BigDecimal suma = inlist.containsKey(codigo) ? inlist.get(codigo) : BigDecimal.ZERO;

                            // Verificar si la partida es de origen o destino
                            if (ori_dest.equals("O")) {
                                inlist.put(codigo, suma.subtract(monto).setScale(2, RoundingMode.HALF_UP));
                            } else {
                                inlist.put(codigo, suma.add(monto).setScale(2, RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }
    }

    /**
     * Recupera los traspasos de Partida para el Mes de interes, en este caso el ultimo mes del periodo
     *
     * Rev 18-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getTraspaso_delMes(final HashMap<String, BigDecimal> inlist) {

        final String monthIni = ejefis_year + "/" + ejeFisMesHasta + "/01"; // el primer dia del mes
        final String monthEnd;

        if (ejeFisMesHasta < 12) {
            monthEnd = ejefis_year + "/" + (ejeFisMesHasta + 1) + "/01"; // el primer dia del mes siguiente
        } else {
            monthEnd = (ejefis_year + 1) + "/01/01"; // el primer dia del mes siguiente
        }

        try {
            // Buscar todos los registros de los Traspasos
            final PreparedStatement pstTrasp = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM trasppartidas WHERE (fecha >= ?) AND (fecha < ?) AND (ppto_egr_ing = 'E') AND (anulado_sn= 'N')");
            pstTrasp.setString(1, monthIni);
            pstTrasp.setString(2, monthEnd);
            try (final ResultSet rsTrasp = pstTrasp.executeQuery()) {
                while (rsTrasp.next()) {
                    final long id_trasp = rsTrasp.getLong("id_trasp");

                    // Buscar todos los detalles del Credito Adicional
                    final PreparedStatement pstTraspDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM trasppartidas_det WHERE (YEAR(date_session) = ?) AND (id_trasp= ?)");
                    pstTraspDet.setLong(1, ejefis_year);
                    pstTraspDet.setLong(2, id_trasp);
                    try (final ResultSet rsTrasDet = pstTraspDet.executeQuery()) {
                        while (rsTrasDet.next()) {
                            final String ori_dest = rsTrasDet.getString("tipo_ori_dest");
                            final String codigo = rsTrasDet.getString("codpresu");
                            final BigDecimal monto = rsTrasDet.getBigDecimal("monto");

                            final BigDecimal suma = inlist.containsKey(codigo) ? inlist.get(codigo) : BigDecimal.ZERO;

                            // Verificar si la partida es de origen o destino
                            if (ori_dest.equals("O")) {
                                inlist.put(codigo, suma.subtract(monto).setScale(2, RoundingMode.HALF_UP));
                            } else {
                                inlist.put(codigo, suma.add(monto).setScale(2, RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }
    }

    /**
     * Rev 18-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getComprHastaEx(final HashMap<String, BigDecimal> inlist) {
        // Verificar si se trata del mes de Enero, en cuyo caso no es necesario realizar algun proceso
        if (ejeFisMesHasta <= 1) {
            return;
        }

        final String monthIni = ejefis_year + "/01/01"; // el primer dia del año
        final String monthEnd = ejefis_year + "/" + ejeFisMesHasta + "/01"; // el primer dia del mes

        try {

            // buscar todas las tablas de Comprometidos
            for (TipoCompr tablaCompr : TipoCompr.values()) {
                try (final PreparedStatement pstCompr = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM " + tablaCompr.getTbl() + " WHERE (fecha_compr >= ?) AND (fecha_compr < ?) AND (anulado_sn= 'N')")) {
                    pstCompr.setString(1, monthIni);
                    pstCompr.setString(2, monthEnd);
                    try (final ResultSet rsCompr = pstCompr.executeQuery()) {

                        // recorrer todos los compromisos, y buscar el respectivo detalle
                        while (rsCompr.next()) {

                            // generar el registro del compromiso
                            final ComprModel regCompr = new ComprModel(rsCompr);

                            // buscar todos los detalles de los compromisos asociados
                            try (final PreparedStatement pstComprDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM compr_det WHERE tipo_compr= ? AND id_compr= ?")) {
                                pstComprDet.setString(1, tablaCompr.name());
                                pstComprDet.setLong(2, regCompr.getId_compromiso());
                                try (final ResultSet rsComprDet = pstComprDet.executeQuery()) {

                                    // recorrer todos los detalles
                                    while (rsComprDet.next()) {

                                        final ComprDetModel regComprDet = new ComprDetModel(rsComprDet);
                                        final BigDecimal suma = inlist.containsKey(regComprDet.getCodPresu()) ? inlist.get(regComprDet.getCodPresu()) : BigDecimal.ZERO;
                                        inlist.put(regComprDet.getCodPresu(), suma.add(regComprDet.getCantPro().multiply(regComprDet.getPUnitario())).setScale(2, RoundingMode.HALF_UP));
                                    }
                                }
                            }
                        }
                    }
                }
            } // for (TipoCompr tablaCompr : TipoCompr.values())
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }

    }

    /**
     * Rev 18-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getCompr_delMes(final HashMap<String, BigDecimal> inlist) {

        final String monthIni = ejefis_year + "/" + ejeFisMesHasta + "/01"; // el primer dia del mes
        final String monthEnd;

        if (ejeFisMesHasta < 12) {
            monthEnd = ejefis_year + "/" + (ejeFisMesHasta + 1) + "/01"; // el primer dia del mes siguiente
        } else {
            monthEnd = (ejefis_year + 1) + "/01/01"; // el primer dia del mes siguiente
        }

        try {

            // buscar todas las tablas de Comprometidos
            for (TipoCompr tablaCompr : TipoCompr.values()) {
                try (final PreparedStatement pstCompr = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM " + tablaCompr.getTbl() + " WHERE (fecha_compr >= ?) AND (fecha_compr < ?) AND (anulado_sn= 'N')")) {
                    pstCompr.setString(1, monthIni);
                    pstCompr.setString(2, monthEnd);
                    try (final ResultSet rsCompr = pstCompr.executeQuery()) {

                        // recorrer todos los compromisos, y buscar el respectivo detalle
                        while (rsCompr.next()) {

                            // generar el registro del compromiso
                            final ComprModel regCompr = new ComprModel(rsCompr);

                            // buscar todos los detalles de los compromisos asociados
                            try (final PreparedStatement pstComprDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM compr_det WHERE tipo_compr= ? AND id_compr= ?")) {
                                pstComprDet.setString(1, tablaCompr.name());
                                pstComprDet.setLong(2, regCompr.getId_compromiso());
                                try (final ResultSet rsComprDet = pstComprDet.executeQuery()) {

                                    // recorrer todos los detalles
                                    while (rsComprDet.next()) {

                                        final ComprDetModel regComprDet = new ComprDetModel(rsComprDet);
                                        final BigDecimal suma = inlist.containsKey(regComprDet.getCodPresu()) ? inlist.get(regComprDet.getCodPresu()) : BigDecimal.ZERO;

                                        inlist.put(regComprDet.getCodPresu(), suma.add(regComprDet.getCantPro().multiply(regComprDet.getPUnitario())).setScale(2, RoundingMode.HALF_UP));
                                    }
                                }
                            }
                        }
                    }
                }
            } // for (TipoCompr tablaCompr : TipoCompr.values())
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }

    }

    /**
     * Rev 18-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getCauHastaEx(final HashMap<String, BigDecimal> inlist) {

        // Verificar si se trata del mes de Enero, en cuyo caso no es necesario realizar algun proceso
        if (ejeFisMesHasta <= 1) {
            return;
        }

        final String monthIni = ejefis_year + "/01/01"; // el primer dia del año
        final String monthEnd = ejefis_year + "/" + ejeFisMesHasta + "/01"; // el primer dia del mes

        try {

            // buscar todas los causados
            try (final PreparedStatement pstCau = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado WHERE (fecha_causado >= ?) AND (fecha_causado < ?) AND (anulado_sn= 'N')")) {
                pstCau.setString(1, monthIni);
                pstCau.setString(2, monthEnd);
                try (final ResultSet rsCau = pstCau.executeQuery()) {

                    // recorrer todos los causados, y buscar el respectivo detalle
                    while (rsCau.next()) {

                        // generar el registro del causado
                        final CauModel regCau = new CauModel(rsCau);

                        // buscar todos los detalles del causado asociado
                        try (final PreparedStatement pstCauDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado_det WHERE id_causado= ?")) {
                            pstCauDet.setLong(1, regCau.getId_causado());
                            try (final ResultSet rsCauDet = pstCauDet.executeQuery()) {

                                // recorrer todos los detalles
                                while (rsCauDet.next()) {

                                    final CauDetModel regCauDet = new CauDetModel(rsCauDet);
                                    final BigDecimal suma = inlist.containsKey(regCauDet.getCodPresu()) ? inlist.get(regCauDet.getCodPresu()) : BigDecimal.ZERO;

                                    inlist.put(regCauDet.getCodPresu(), suma.add(regCauDet.getSubtotal()).setScale(2, RoundingMode.HALF_UP));
                                } // while (rsCauDet.next())
                            }
                        }
                    }
                }
            } // for (TipoCompr tablaCompr : TipoCompr.values()) // for (TipoCompr tablaCompr : TipoCompr.values())
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }

    }

    /**
     * Rev 18-03-2017
     *
     * @param ejefismes
     * @return
     */
    private void getCau_delMes(final HashMap<String, BigDecimal> inlist) {

        final String monthIni = ejefis_year + "/" + ejeFisMesHasta + "/01"; // el primer dia del mes
        final String monthEnd;

        if (ejeFisMesHasta < 12) {
            monthEnd = ejefis_year + "/" + (ejeFisMesHasta + 1) + "/01"; // el primer dia del mes siguiente
        } else {
            monthEnd = (ejefis_year + 1) + "/01/01"; // el primer dia del mes siguiente
        }

        try {

            // buscar todas los causados
            try (final PreparedStatement pstCau = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado WHERE (fecha_causado >= ?) AND (fecha_causado < ?) AND (anulado_sn= 'N')")) {
                pstCau.setString(1, monthIni);
                pstCau.setString(2, monthEnd);
                try (final ResultSet rsCau = pstCau.executeQuery()) {

                    // recorrer todos los causados, y buscar el respectivo detalle
                    while (rsCau.next()) {

                        // generar el registro del causado
                        final CauModel regCau = new CauModel(rsCau);

                        // buscar todos los detalles del causado asociado
                        try (final PreparedStatement pstCauDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado_det WHERE id_causado= ?")) {
                            pstCauDet.setLong(1, regCau.getId_causado());
                            try (final ResultSet rsCauDet = pstCauDet.executeQuery()) {

                                // recorrer todos los detalles
                                while (rsCauDet.next()) {

                                    final CauDetModel regCauDet = new CauDetModel(rsCauDet);
                                    final BigDecimal suma = inlist.containsKey(regCauDet.getCodPresu()) ? inlist.get(regCauDet.getCodPresu()) : BigDecimal.ZERO;

                                    inlist.put(regCauDet.getCodPresu(), suma.add(regCauDet.getSubtotal()).setScale(2, RoundingMode.HALF_UP));
                                } // while (rsCauDet.next())
                            }
                        }
                    }
                }
            } // for (TipoCompr tablaCompr : TipoCompr.values()) // for (TipoCompr tablaCompr : TipoCompr.values())
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }

    }

    /**
     * Rev 21-02-2017
     *
     * @param ejefismes
     * @return
     */
    private void getPagadoIHastaEx(final HashMap<String, BigDecimal> inlist) {

        // Verificar si se trata del mes de Enero, en cuyo caso no es necesario realizar algun proceso
        if (ejeFisMesHasta <= 1) {
            return;
        }

        final String monthIni = ejefis_year + "/01/01"; // el primer dia del año
        final String monthEnd = ejefis_year + "/" + ejeFisMesHasta + "/01"; // el primer dia del mes

        try {

            // buscar todas las ordenes de pago
            try (final PreparedStatement pstOrdPag = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM orden_pago WHERE (fecha_pago >= ?) AND (fecha_pago < ?) AND (anulado_sn= 'N')")) {
                pstOrdPag.setString(1, monthIni);
                pstOrdPag.setString(2, monthEnd);
                try (final ResultSet rsOrdPag = pstOrdPag.executeQuery()) {

                    // recorrer las ordenes de pago, y buscar su relacion con el causado
                    while (rsOrdPag.next()) {

                        // generar el registro de la orden de pago
                        final PagoModel regOrdPag = new PagoModel(rsOrdPag);

                        // buscar todos los causados asociados a la orden de pago
                        try (final PreparedStatement pstCauOrdPag = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado_orden_pago WHERE id_orden_pago= ? AND anulado_sn= 'N'")) {
                            pstCauOrdPag.setLong(1, regOrdPag.getId_orden_pago());
                            try (final ResultSet rsCauOrdPag = pstCauOrdPag.executeQuery()) {

                                // recorrer todos los enlaces, y buscar su causado
                                while (rsCauOrdPag.next()) {

                                    // hallar el id, del causado correspondiente
                                    final long id_causado = rsCauOrdPag.getLong("id_causado");

                                    // recuperar el causado
                                    try (final PreparedStatement pstCauOrdPago = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado WHERE id_causado = ? AND anulado_sn= 'N'")) {
                                        pstCauOrdPago.setLong(1, id_causado);
                                        try (final ResultSet rsCau = pstCauOrdPago.executeQuery()) {

                                            if (rsCau.next()) {
                                                final CauModel regCau = new CauModel(rsCau);

                                                // buscar la partida asociada
                                                try (final PreparedStatement pstCauDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado_det WHERE id_causado = ?")) {
                                                    pstCauDet.setLong(1, id_causado);
                                                    try (final ResultSet rsCauDet = pstCauDet.executeQuery()) {

                                                        if (rsCauDet.next()) {
                                                            final CauDetModel regCauDet = new CauDetModel(rsCauDet);

                                                            final BigDecimal suma = inlist.containsKey(regCauDet.getCodPresu()) ? inlist.get(regCauDet.getCodPresu()) : BigDecimal.ZERO;

                                                            inlist.put(regCauDet.getCodPresu(), suma.add(regCauDet.getSubtotal()).setScale(2, RoundingMode.HALF_UP));
                                                        }
                                                    }
                                                }

                                            } else {
                                                // Si no se encuantra el causado, es sintoma de un error de integridad
                                                // abortar la operacion
                                                JOptionPane.showMessageDialog(this, "Error al tratar de recuperar el causado" + System.getProperty("line.separator") + "Registro no encontrado");
                                                return;
                                            }

                                        }
                                    }

                                }

                            }
                        }

                    }
                }
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }

    }

    /**
     * Rev 21-02-2017
     *
     * @param ejefismes
     * @return
     */
    private void getPagado_delMes(final HashMap<String, BigDecimal> inlist) {

        final String monthIni = ejefis_year + "/" + ejeFisMesHasta + "/01"; // el primer dia del mes
        final String monthEnd;

        if (ejeFisMesHasta < 12) {
            monthEnd = ejefis_year + "/" + (ejeFisMesHasta + 1) + "/01"; // el primer dia del mes siguiente
        } else {
            monthEnd = (ejefis_year + 1) + "/01/01"; // el primer dia del mes siguiente
        }

        try {

            // buscar todas las ordenes de pago
            try (final PreparedStatement pstOrdPag = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM orden_pago WHERE (fecha_pago >= ?) AND (fecha_pago < ?) AND (anulado_sn= 'N')")) {
                pstOrdPag.setString(1, monthIni);
                pstOrdPag.setString(2, monthEnd);
                try (final ResultSet rsOrdPag = pstOrdPag.executeQuery()) {

                    // recorrer las ordenes de pago, y buscar su relacion con el causado
                    while (rsOrdPag.next()) {

                        // generar el registro de la orden de pago
                        final PagoModel regOrdPag = new PagoModel(rsOrdPag);

                        // buscar todos los causados asociados a la orden de pago
                        try (final PreparedStatement pstCauOrdPag = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado_orden_pago WHERE id_orden_pago= ? AND anulado_sn= 'N'")) {
                            pstCauOrdPag.setLong(1, regOrdPag.getId_orden_pago());
                            try (final ResultSet rsCauOrdPag = pstCauOrdPag.executeQuery()) {

                                // recorrer todos los enlaces, y buscar su causado
                                while (rsCauOrdPag.next()) {

                                    // hallar el id, del causado correspondiente
                                    final long id_causado = rsCauOrdPag.getLong("id_causado");

                                    // recuperar el causado
                                    try (final PreparedStatement pstCauOrdPago = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado WHERE id_causado = ? AND anulado_sn= 'N'")) {
                                        pstCauOrdPago.setLong(1, id_causado);
                                        try (final ResultSet rsCau = pstCauOrdPago.executeQuery()) {

                                            if (rsCau.next()) {
                                                final CauModel regCau = new CauModel(rsCau);

                                                // buscar la partida asociada
                                                try (final PreparedStatement pstCauDet = ConnCapip.getInstance().getConnection().prepareStatement("SELECT * FROM causado_det WHERE id_causado = ?")) {
                                                    pstCauDet.setLong(1, id_causado);
                                                    try (final ResultSet rsCauDet = pstCauDet.executeQuery()) {

                                                        if (rsCauDet.next()) {
                                                            final CauDetModel regCauDet = new CauDetModel(rsCauDet);

                                                            final BigDecimal suma = inlist.containsKey(regCauDet.getCodPresu()) ? inlist.get(regCauDet.getCodPresu()) : BigDecimal.ZERO;

                                                            inlist.put(regCauDet.getCodPresu(), suma.add(regCauDet.getSubtotal()).setScale(2, RoundingMode.HALF_UP));
                                                        }
                                                    }
                                                }
                                            } else {
                                                // Si no se encuantra el causado, es sintoma de un error de integridad
                                                // abortar la operacion
                                                JOptionPane.showMessageDialog(this, "Error al tratar de recuperar el causado" + System.getProperty("line.separator") + "Registro no encontrado");
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + inex);
        }
    }

    /**
     * Rev 20/02/2017
     *
     * @return
     */
    private void setPeriodo() {

        try {
            ejefis_year = Format.toLong(txtEjeFis.getText().trim());
        } catch (final Exception inex) {
            ejefis_year = Globales.getServerYear();
            txtEjeFis.setText(String.valueOf(ejefis_year));
        }

        if (btnAno.isSelected()) {
            mesDesde = "Inicio Anual";
            mesHasta = "Inicio Anual";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 12;
            lblMSJ.setText("Mes 0, inicio del Ejercicio Fiscal. NO HAY EJECUCIÓN");
        } else if (btnEne.isSelected()) {
            mesDesde = "Enero";
            mesHasta = "Enero";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 1;
            lblMSJ.setText("");
        } else if (btnFeb.isSelected()) {
            mesDesde = "Febrero";
            mesHasta = "Febrero";
            ejeFisMesDesde = 2;
            ejeFisMesHasta = 2;
            lblMSJ.setText("");
        } else if (btnMar.isSelected()) {
            mesDesde = "Marzo";
            mesHasta = "Marzo";
            ejeFisMesDesde = 3;
            ejeFisMesHasta = 3;
            lblMSJ.setText("");
        } else if (btnAbr.isSelected()) {
            mesDesde = "Abril";
            mesHasta = "Abril";
            ejeFisMesDesde = 4;
            ejeFisMesHasta = 4;
            lblMSJ.setText("");
        } else if (btnMay.isSelected()) {
            mesDesde = "Mayo";
            mesHasta = "Mayo";
            ejeFisMesDesde = 5;
            ejeFisMesHasta = 5;
            lblMSJ.setText("");
        } else if (btnJun.isSelected()) {
            mesDesde = "Junio";
            mesHasta = "Junio";
            ejeFisMesDesde = 6;
            ejeFisMesHasta = 6;
            lblMSJ.setText("");
        } else if (btnJul.isSelected()) {
            mesDesde = "Julio";
            mesHasta = "Julio";
            ejeFisMesDesde = 7;
            ejeFisMesHasta = 7;
            lblMSJ.setText("");
        } else if (btnAgo.isSelected()) {
            mesDesde = "Agosto";
            mesHasta = "Agosto";
            ejeFisMesDesde = 8;
            ejeFisMesHasta = 8;
            lblMSJ.setText("");
        } else if (btnSep.isSelected()) {
            mesDesde = "Septiembre";
            mesHasta = "Septiembre";
            ejeFisMesDesde = 9;
            ejeFisMesHasta = 9;
            lblMSJ.setText("");
        } else if (btnOct.isSelected()) {
            mesDesde = "Octubre";
            mesHasta = "Octubre";
            ejeFisMesDesde = 10;
            ejeFisMesHasta = 10;
            lblMSJ.setText("");
        } else if (btnNov.isSelected()) {
            mesDesde = "Noviembre";
            mesHasta = "Noviembre";
            ejeFisMesDesde = 11;
            ejeFisMesHasta = 11;
            lblMSJ.setText("");
        } else if (btnDic.isSelected()) {
            mesDesde = "Diciembre";
            mesHasta = "Diciembre";
            ejeFisMesDesde = 12;
            ejeFisMesHasta = 12;
            lblMSJ.setText("");
        } else {
            mesDesde = "Inicio Anual";
            mesHasta = "Inicio Anual";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 12;
            lblMSJ.setText("Mes 0, inicio del Ejercicio Fiscal. NO HAY EJECUCIÓN");
        }
    }

    /**
     *
     */
    @Override
    protected void UpdatePanelDetails() {
    }

    /**
     *
     */
    @Override
    protected void ClearDetails() {
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
        txtReset = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        btnExcel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtEjeFis = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTotales = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jpCmdState = new javax.swing.JPanel();
        btnSeleccionar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        txtCant = new javax.swing.JFormattedTextField(Integer.valueOf(0));
        btnCancelar = new javax.swing.JButton();
        btnReportSummary = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
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
        lblMSJ = new javax.swing.JLabel();
        jlFondo = new javax.swing.JLabel();

        setTitle("REPORDE DE EJECUCION MENSUAL DEL PRESUPUESTO");
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

        jtFiltro.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
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
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel1, gridBagConstraints);

        txtReset.setBackground(java.awt.SystemColor.inactiveCaption);
        txtReset.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        txtReset.setText("RESET");
        txtReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(txtReset, gridBagConstraints);

        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Número", "Fecha", "Beneficiario", "RIF/CI", "Monto Bs." }));
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

        btnExcel.setBackground(java.awt.SystemColor.inactiveCaption);
        btnExcel.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnExcel.setText("EXCEL");
        btnExcel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
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
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel2, gridBagConstraints);

        txtEjeFis.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtEjeFis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEjeFis.setMaximumSize(new java.awt.Dimension(75, 21));
        txtEjeFis.setMinimumSize(new java.awt.Dimension(75, 21));
        txtEjeFis.setPreferredSize(new java.awt.Dimension(75, 21));
        txtEjeFis.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtEjeFis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEjeFisFocusLost(evt);
            }
        });
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

        jpFondo.add(jpFiltro);

        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 340));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 340));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 340));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblMaster.setFont(new java.awt.Font("Arial", 3, 15)); // NOI18N
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Presup.", "Partida", "Ppto. Aprobado", "Ppto. Inicial", "Créditos", "Traspasos", "Ppto. Modificado", "Compromisos", "Saldo Final.", "Causado", "Pagado", "Reg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
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
        tblMaster.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblMaster.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(350);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(6).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(7).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(7).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(8).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(8).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(9).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(9).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(10).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(10).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(11).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        jpFondo.add(jScrollPane1);

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(1060, 55));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(1060, 55));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1060, 55));

        tblTotales.setFont(new java.awt.Font("Arial", 3, 15)); // NOI18N
        tblTotales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "-", "-", "Ppto. Aprobado", "Ppto. Inicial", "Créditos", "Traspasos", "PptoMod", "Compromisos", "Saldo", "Causado", "Pagado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
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
        tblTotales.setToolTipText("Totales Bs.");
        tblTotales.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblTotales.setMaximumSize(new java.awt.Dimension(2147483647, 25));
        tblTotales.setMinimumSize(new java.awt.Dimension(60, 25));
        tblTotales.setPreferredSize(new java.awt.Dimension(1400, 25));
        tblTotales.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblTotales.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblTotales);
        if (tblTotales.getColumnModel().getColumnCount() > 0) {
            tblTotales.getColumnModel().getColumn(0).setMinWidth(75);
            tblTotales.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(0).setMaxWidth(300);
            tblTotales.getColumnModel().getColumn(1).setMinWidth(75);
            tblTotales.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblTotales.getColumnModel().getColumn(1).setMaxWidth(500);
            tblTotales.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(2).setMaxWidth(200);
            tblTotales.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(3).setMaxWidth(200);
            tblTotales.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(4).setMaxWidth(200);
            tblTotales.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(5).setMaxWidth(200);
            tblTotales.getColumnModel().getColumn(6).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(6).setMaxWidth(200);
            tblTotales.getColumnModel().getColumn(7).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(7).setMaxWidth(200);
            tblTotales.getColumnModel().getColumn(8).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(8).setMaxWidth(200);
            tblTotales.getColumnModel().getColumn(9).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(9).setMaxWidth(200);
            tblTotales.getColumnModel().getColumn(10).setPreferredWidth(150);
            tblTotales.getColumnModel().getColumn(10).setMaxWidth(200);
        }

        jpFondo.add(jScrollPane2);

        jpEditables.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle"));
        jpEditables.setMaximumSize(new java.awt.Dimension(1060, 135));
        jpEditables.setMinimumSize(new java.awt.Dimension(1060, 135));
        jpEditables.setOpaque(false);
        jpEditables.setPreferredSize(new java.awt.Dimension(1060, 135));
        jpEditables.setLayout(new java.awt.GridBagLayout());

        jpCmdState.setOpaque(false);
        jpCmdState.setLayout(new java.awt.GridBagLayout());

        btnSeleccionar.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N
        btnSeleccionar.setText("SELECCIONAR");
        btnSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSeleccionar.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(btnSeleccionar, gridBagConstraints);

        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblmonto1.setText("Cantidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);

        txtCant.setEditable(false);
        txtCant.setBackground(new java.awt.Color(175, 204, 125));
        txtCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtCant.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCant.setDoubleBuffered(true);
        txtCant.setFocusable(false);
        txtCant.setFont(new java.awt.Font("Arial", 3, 20)); // NOI18N
        txtCant.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCant.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        jpCmdState.add(txtCant, gridBagConstraints);

        btnCancelar.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(btnCancelar, gridBagConstraints);

        btnReportSummary.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MENSUAL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        jpCmdState.add(jLabel3, gridBagConstraints);

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

        btnRetornar.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N
        btnRetornar.setText("RETORNAR");
        btnRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 60, 10, 0);
        jpComandos.add(btnRetornar, gridBagConstraints);

        lblMSJ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMSJ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMSJ.setMaximumSize(new java.awt.Dimension(500, 25));
        lblMSJ.setMinimumSize(new java.awt.Dimension(500, 25));
        lblMSJ.setPreferredSize(new java.awt.Dimension(800, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jpComandos.add(lblMSJ, gridBagConstraints);

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

        final long ejeFis;
        try {
            ejeFis = Globales.getEjeFisYear();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            return;
        }

        final Map<String, Object> param = new HashMap<>(101);
        param.put("ejefis", ejeFis);
        param.put("ejefis_desde", ejeFis * 100 + ejeFisMesDesde);
        param.put("ejefis_hasta", ejeFis * 100 + ejeFisMesHasta);
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

        final ArrayList<PptoExecModel> list = new ArrayList<>();

        BigDecimal totPpto = BigDecimal.ZERO;
        BigDecimal totIni = BigDecimal.ZERO;
        BigDecimal totCre = BigDecimal.ZERO;
        BigDecimal totTra = BigDecimal.ZERO;
        BigDecimal totPptoMod = BigDecimal.ZERO;
        BigDecimal totCompr = BigDecimal.ZERO;
        BigDecimal totSal = BigDecimal.ZERO;
        BigDecimal totCau = BigDecimal.ZERO;
        BigDecimal totPag = BigDecimal.ZERO;

        for (int i = 0; i < tblMaster.getRowCount(); i++) {

            PptoExecModel reg = (PptoExecModel) tblMaster.getValueAt(i, colReg);
            list.add(reg);

            totPpto = totPpto.add(reg.getAprobado());
            totIni = totIni.add(reg.getInicial());
            totCre = totCre.add(reg.getCredito());
            if (reg.getTraspaso().compareTo(BigDecimal.ZERO) > 0) {
                totTra = totTra.add(reg.getTraspaso());
                totPptoMod = totPptoMod.add(reg.getPptoMod());
            }

            totCompr = totCompr.add(reg.getCompromiso());
            totSal = totSal.add(reg.getSaldo());
            totCau = totCau.add(reg.getCausado());
            totPag = totPag.add(reg.getPagado());
        }

        param.put("tot_ppto", totPpto);
        param.put("tot_ini", totIni);
        param.put("tot_cre", totCre);
        param.put("tot_tra", totTra);
        param.put("tot_pptomod", totPptoMod);
        param.put("tot_compr", totCompr);
        param.put("tot_sal", totSal);
        param.put("tot_cau", totCau);
        param.put("tot_pag", totPag);

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/ppto_ejecucion.jasper");
        if (pathRpt != null) {

            new Thread() {
                @Override
                public void run() {
                    try {
                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), new JRBeanCollectionDataSource(list));

                        JasperViewer.viewReport(jasperPrint, false);

                        // JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/ppto_ejecucion.pdf");
                    } catch (final Exception inex) {
                        JOptionPane.showMessageDialog(null, inex);
                    }
                }
            }.start();

        } else {
            JOptionPane.showMessageDialog(null, "Reporte de pago no encontrado");
        }

    }//GEN-LAST:event_btnReportSummaryActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        try {
            final String fileName = "RptEjeMensualPpto " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblMaster, fileName + ".xls");
            TableToExcel.exportTSV(tblMaster, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void txtEjeFisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEjeFisFocusLost
        UpdatePanels();
    }//GEN-LAST:event_txtEjeFisFocusLost

    private void txtEjeFisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEjeFisActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_txtEjeFisActionPerformed

    /**
     * Rev 21/11/2016
     *
     * @param inselRow
     */
    private void cauRpt(final int inselRow) {
        try {
            Causado.genReport(((CauModel) tblMaster.getValueAt( inselRow, colReg)).getId_causado(), null);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar el registro" + System.getProperty("line.separator") + inex);
        }
    }

    /**
     * Rev 21/11/2016
     *
     * @param ine
     */
    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent ine) {
        final JTable jtable = (JTable) xjparam.get(JTABLE);

        if (jtable != null) {
            final int selRow = jtable.getSelectedRow();
            if (selRow >= 0) {
                final java.awt.Window me = this;

                // Buscar el codigo del ppto
                PptoExecModel reg = (PptoExecModel) tblMaster.getValueAt(selRow, colReg);

                PptoModel partida;
                try {
                    partida = PptoModel.getReg_x_Cod("presupe", reg.getCodpresu());
                    if (partida == null) {
                        JOptionPane.showMessageDialog(this, "Registro no encontrado");
                        return;
                    }
                } catch (Exception inex) {
                    JOptionPane.showMessageDialog(this, "Error al tratar de recuperar la partida" + System.getProperty("line.separator") + inex);
                    return;
                }

                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        new PptoEjecDetMesRpt(me, partida).setVisible(true);
                        me.setVisible(false);
                    }
                });

            }
        }
    }

    /**
     *
     */
    protected void jbCancelarActionPerformed() {
        Salir();
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
                new PptoEjecucionMonthReport(null).setVisible(true);
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
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JToggleButton btnSep;
    private javax.swing.ButtonGroup buttonGroupPeriodo;
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox jcbFilter;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JPanel jpCmdState;
    private javax.swing.JPanel jpComandos;
    private javax.swing.JPanel jpEditables;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JPanel jpFondo;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JLabel lblMSJ;
    private javax.swing.JLabel lblmonto1;
    private javax.swing.JTable tblMaster;
    private javax.swing.JTable tblTotales;
    private javax.swing.JFormattedTextField txtCant;
    private javax.swing.JTextField txtEjeFis;
    private javax.swing.JButton txtReset;
    // End of variables declaration//GEN-END:variables

}
