/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.ComprDetModel;
import modelos.ComprModel;
import modelos.PptoEjecDetModel;
import modelos.PptoEjecDetRptModel;
import modelos.PptoModel;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import static presupuesto.XJFrame.JBCANCEL;
import static presupuesto.XJFrame.JBSELECT;
import static presupuesto.XJFrame.JTABLE;
import utils.DateCellRenderer;
import utils.DecimalCellRenderer;
import utils.EjecMov;
import utils.Format;
import utils.IntegerCellRenderer;
import utils.TableToExcel;
import utils.TipoCompr;

/**
 * Rev 29-03-2017
 *
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class PptoEjecDetMesRpt extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del código programado, mantiene relacionado el nombre de la columna con su posición
     */
    private static final int colMov = 0;
    private static final int colFecha = 1;
    private static final int colMonto = 2;
    private static final int colDoc = 3;
    private static final int colObs = 4;
    private static final int colReg = 5;

    private long ejefis_year;
    private String mesDesde;
    private String mesHasta;
    private long ejeFisMesDesde;
    private long ejeFisMesHasta;

    final private PptoModel partida;

    /**
     *
     * @param _parent
     * @param _partida
     */
    public PptoEjecDetMesRpt(final java.awt.Window _parent, final PptoModel _partida) {
        super(_parent);
        initComponents();

        partida = _partida;
        if (partida == null) {
            Salir();
        }

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
        }

        super.setVisible(_b);
    }

    /**
     * Establece el comportamiento de la presente Ventana
     * Rev 21/09/2016
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
        lblCodPresu.setText(partida.getCodigo());
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
     * @param _param
     */
    @Override
    protected void ConfigComponents(final Map<String, Object> _param) {
        super.ConfigComponents(_param);

        JButton jbSelect = (JButton) xjparam.get(JBSELECT);
        if (jbSelect != null) {
            jbSelect.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    jbSeleccionarActionPerformed(evt);
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

        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                txtCant.setText(String.valueOf(tblMaster.getRowCount()));
            }
        });

        // Configurar la tabla de Maestra, para alinear las columnas
        DefaultTableCellRenderer tcr;

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colMov).setCellRenderer(tcr);

        tblMaster.getColumnModel().getColumn(colFecha).setCellRenderer(new DateCellRenderer());

        // Configurar la tabla
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());
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
     * Actualizar la vista de la ventana. Deberia llamarse cuando se halla modificado la tabla
     * Rev 21/11/2016
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

        final Object[] datos = new Object[6];
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            // Generar el comando SQL
            final ArrayList<PptoEjecDetModel> list = genList();

            Iterator<PptoEjecDetModel> iterReg = list.iterator();

            while (iterReg.hasNext()) {
                PptoEjecDetModel reg = iterReg.next();

                datos[colMov] = reg.getEjeMov();
                datos[colFecha] = reg.getFecha();
                datos[colMonto] = reg.getMonto().doubleValue();
                datos[colDoc] = reg.getDoc();
                datos[colObs] = reg.getObs();
                datos[colReg] = reg;

                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        } finally {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
     * Genera varias listas, que contienen un resumen de la ejecucion por periodo de la respectiva partida
     *
     * Rev 20-02-2017
     *
     */
    private ArrayList<PptoEjecDetModel> genList() {

        setPeriodo();

        final String strMesDesde = ejefis_year + "/" + ejeFisMesDesde + "/01";
        final String strMesHastaEx;
        if (ejeFisMesHasta < 12) {
            strMesHastaEx = ejefis_year + "/" + (ejeFisMesHasta + 1) + "/01";
        } else {
            strMesHastaEx = (ejefis_year + 1) + "/01/01";
        }

        // Lleva el acumulado de operaciones
        BigDecimal saldo = BigDecimal.ZERO;

        final ArrayList<PptoEjecDetModel> list = new ArrayList<>();

        try {
            // Buscar la partida en el presupuesto
            final java.sql.Date fechaPpto = Format.toDateSql("01/01/" + partida.getEjefis());
            if ((ejeFisMesDesde == 1) || (ejeFisMesHasta == 1)) {
                list.add(new PptoEjecDetModel(EjecMov.APROBADO.toString(), fechaPpto, partida.getMontoIni(), "", "Presupuesto anual aprobado en Gaceta Oficial"));
            }

            final java.sql.Date fechaIni = Format.toDateSql("01/" + ejeFisMesDesde + "/" + ejefis_year);
            BigDecimal montoIni = getIni();
            list.add(new PptoEjecDetModel(EjecMov.INICIAL.toString(), fechaIni, montoIni, "", "Monto inicial del periodo"));
            saldo = saldo.add(montoIni).setScale(2, RoundingMode.HALF_UP);

            // Buscar en todas las tablas de compromiso
            for (TipoCompr tablaCompr : TipoCompr.values()) {

                // Buscar todos los compromiso activos del ejercicio fiscal
                try (final PreparedStatement pstCompr = Conn.getConnection().prepareStatement("SELECT * FROM " + tablaCompr.getTbl() + " WHERE (ejefis >= '" + strMesDesde + "') AND (ejefis < '" + strMesHastaEx + "') AND anulado_sn= 'N' ORDER BY fecha_compr")) {
                    try (final ResultSet rsCompr = pstCompr.executeQuery()) {
                        while (rsCompr.next()) {
                            final ComprModel regCompr = new ComprModel(rsCompr);

                            // Buscar en los detalles del compromiso
                            try (final PreparedStatement pstComprDet = Conn.getConnection().prepareStatement("SELECT * FROM compr_det WHERE id_compr= ? AND tipo_compr= ? AND codpresu= ?")) {
                                pstComprDet.setLong(1, regCompr.getId_compromiso());
                                pstComprDet.setString(2, tablaCompr.name());
                                pstComprDet.setString(3, partida.getCodigo());
                                try (final ResultSet rsComprDet = pstComprDet.executeQuery()) {
                                    while (rsComprDet.next()) {
                                        final ComprDetModel regComprDet = new ComprDetModel(rsComprDet);
                                        final BigDecimal monto = regComprDet.getCantPro().multiply(regComprDet.getPUnitario()).setScale(2, RoundingMode.HALF_UP).negate();
                                        final String doc = tablaCompr.name() + " " + regCompr.getId_compromiso();
                                        list.add(new PptoEjecDetModel(EjecMov.COMPROMISO.toString(), regCompr.getFecha_compr(), monto, " " + doc, " " + regComprDet.getDescPro()));
                                        saldo = saldo.add(monto).setScale(2, RoundingMode.HALF_UP);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            final long mesDesdeIn = ejefis_year * 100 + ejeFisMesDesde;
            final long mesHastaEx;
            if (ejeFisMesHasta < 12) {
                mesHastaEx = ejefis_year * 100 + ejeFisMesHasta + 1;
            } else {
                mesHastaEx = (ejefis_year + 1) * 100 + 1;
            }

            // buscar la partida en los creditos           
            try (final PreparedStatement pstCre = Conn.getConnection().prepareStatement("SELECT * FROM creditoadicional WHERE (ejefismes >= '" + mesDesdeIn + "') AND (ejefismes < '" + mesHastaEx + "') AND anulado_sn= 'N' ORDER BY fecha")) {
                try (final ResultSet rsCre = pstCre.executeQuery()) {
                    while (rsCre.next()) {
                        long id_cre = rsCre.getLong("id");
                        final java.sql.Date fechaCre = new java.sql.Date(rsCre.getDate("fecha").getTime());
                        final String docSoporte = rsCre.getString("soporte");
                        final String docDesc = rsCre.getString("descripcion");

                        // Buscar en los detalles del credito
                        try (final PreparedStatement pstCreDet = Conn.getConnection().prepareStatement("SELECT * FROM creditoadicional_det WHERE id_cre_adi= ? AND codigo= ?")) {
                            pstCreDet.setLong(1, id_cre);
                            pstCreDet.setString(2, partida.getCodigo());
                            try (final ResultSet rsCreDet = pstCreDet.executeQuery()) {
                                while (rsCreDet.next()) {
                                    final BigDecimal monto = rsCreDet.getBigDecimal("monto");
                                    list.add(new PptoEjecDetModel(EjecMov.CREDITO.toString(), fechaCre, monto, " " + docSoporte, " " + docDesc));
                                    saldo = saldo.add(monto).setScale(2, RoundingMode.HALF_UP);
                                }
                            }
                        }
                    }
                }
            }

            // buscar las partidas en los traspasos
            try (final PreparedStatement pstTra = Conn.getConnection().prepareStatement("SELECT * FROM trasppartidas WHERE (ejefismes >= '" + mesDesdeIn + "') AND (ejefismes < '" + mesHastaEx + "') AND anulado_sn= 'N' AND ppto_egr_ing= 'E' ORDER BY fecha")) {
                try (final ResultSet rsTrasp = pstTra.executeQuery()) {
                    while (rsTrasp.next()) {
                        long id_trasp = rsTrasp.getLong("id_trasp");
                        final java.sql.Date fechaTrasp = new java.sql.Date(rsTrasp.getDate("date_session").getTime());
                        final String ppto_egr_ing = rsTrasp.getString("ppto_egr_ing");
                        final String docReferencia = rsTrasp.getString("referencia");
                        final String docConcepto = "Ppto. de " + ppto_egr_ing + ". Fecha " + fechaTrasp + ". " + rsTrasp.getString("concepto");

                        // Buscar en los detalles del traspaso
                        try (final PreparedStatement pstTraspDet = Conn.getConnection().prepareStatement("SELECT * FROM trasppartidas_det WHERE id_trasp= ? AND codpresu= ? AND ppto_egr_ing= 'E'")) {
                            pstTraspDet.setLong(1, id_trasp);
                            pstTraspDet.setString(2, partida.getCodigo());
                            try (final ResultSet rsTraspDet = pstTraspDet.executeQuery()) {
                                while (rsTraspDet.next()) {
                                    final String ori_dest = rsTraspDet.getString("tipo_ori_dest");
                                    final BigDecimal monto;
                                    if (ori_dest.equals("O")) {
                                        monto = rsTraspDet.getBigDecimal("monto").negate();
                                    } else {
                                        monto = rsTraspDet.getBigDecimal("monto");
                                    }

                                    list.add(new PptoEjecDetModel(EjecMov.TRASPASO.toString(), fechaTrasp, monto, " " + docReferencia, " " + docConcepto));
                                    saldo = saldo.add(monto).setScale(2, RoundingMode.HALF_UP);
                                }
                            }
                        }
                    }
                }
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + _ex);
        }

        list.sort(Comparator.naturalOrder());

        PptoEjecDetModel reg = list.get(list.size() - 1);

        list.add(new PptoEjecDetModel(EjecMov.SALDO.toString(), reg.getFecha(), saldo, "", " Saldo al final del periodo"));

        return list;
    }

    /**
     * Genera varias listas, que contienen un resumen de la ejecucion por periodo de la respectiva partida
     *
     * Rev 20-02-2017
     *
     */
    private BigDecimal getIni() {

        setPeriodo();

        final String strMesDesde = ejefis_year + "/01/01";
        final String strMesHastaEx;
        if (ejeFisMesHasta >= 12) {
            strMesHastaEx = ejefis_year + "/01/01";
        } else {
            strMesHastaEx = ejefis_year + "/" + ejeFisMesHasta + "/01";
        }

        // Presupuesto Inicial
        BigDecimal ini = partida.getMontoIni().setScale(2, RoundingMode.HALF_UP);

        try {

            // Buscar en todas las tablas de compromiso
            for (TipoCompr tablaCompr : TipoCompr.values()) {

                // Buscar todos los compromiso activos del ejercicio fiscal
                try (final PreparedStatement pstCompr = Conn.getConnection().prepareStatement("SELECT * FROM " + tablaCompr.getTbl() + " WHERE (ejefis >= '" + strMesDesde + "') AND (ejefis < '" + strMesHastaEx + "') AND anulado_sn= 'N' ORDER BY fecha_compr")) {
                    try (final ResultSet rsCompr = pstCompr.executeQuery()) {
                        while (rsCompr.next()) {
                            final ComprModel regCompr = new ComprModel(rsCompr);

                            // Buscar en los detalles del compromiso
                            try (final PreparedStatement pstComprDet = Conn.getConnection().prepareStatement("SELECT * FROM compr_det WHERE id_compr= ? AND tipo_compr= ? AND codpresu= ?")) {
                                pstComprDet.setLong(1, regCompr.getId_compromiso());
                                pstComprDet.setString(2, tablaCompr.name());
                                pstComprDet.setString(3, partida.getCodigo());
                                try (final ResultSet rsComprDet = pstComprDet.executeQuery()) {
                                    while (rsComprDet.next()) {
                                        final ComprDetModel regComprDet = new ComprDetModel(rsComprDet);
                                        final BigDecimal monto = regComprDet.getCantPro().multiply(regComprDet.getPUnitario()).setScale(2, RoundingMode.HALF_UP).negate();
                                        ini = ini.add(monto);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            final long mesDesdeIn = ejefis_year * 100 + 1;
            final long mesHastaEx;
            if (ejeFisMesHasta >= 12) {
                mesHastaEx = ejefis_year * 100 + 1;
            } else {
                mesHastaEx = ejefis_year * 100 + ejeFisMesHasta;
            }

            // buscar la partida en los creditos           
            try (final PreparedStatement pstCre = Conn.getConnection().prepareStatement("SELECT * FROM creditoadicional WHERE (ejefismes >= '" + mesDesdeIn + "') AND (ejefismes < '" + mesHastaEx + "') AND (anulado_sn= 'N') ORDER BY fecha")) {
                try (final ResultSet rsCre = pstCre.executeQuery()) {
                    while (rsCre.next()) {
                        long id_cre = rsCre.getLong("id");

                        // Buscar en los detalles del credito
                        try (final PreparedStatement pstCreDet = Conn.getConnection().prepareStatement("SELECT * FROM creditoadicional_det WHERE id_cre_adi= ? AND codigo= ?")) {
                            pstCreDet.setLong(1, id_cre);
                            pstCreDet.setString(2, partida.getCodigo());
                            try (final ResultSet rsCreDet = pstCreDet.executeQuery()) {
                                while (rsCreDet.next()) {
                                    ini = ini.add(rsCreDet.getBigDecimal("monto"));
                                }
                            }
                        }
                    }
                }
            }

            // buscar las partidas en los traspasos
            try (final PreparedStatement pstTra = Conn.getConnection().prepareStatement("SELECT * FROM trasppartidas WHERE (ejefismes >= '" + mesDesdeIn + "') AND (ejefismes < '" + mesHastaEx + "') AND (anulado_sn= 'N') AND (ppto_egr_ing= 'E') ORDER BY fecha")) {
                try (final ResultSet rsTrasp = pstTra.executeQuery()) {
                    while (rsTrasp.next()) {
                        long id_trasp = rsTrasp.getLong("id_trasp");
                        final java.sql.Date fechaTrasp = new java.sql.Date(rsTrasp.getDate("date_session").getTime());
                        final String ppto_egr_ing = rsTrasp.getString("ppto_egr_ing");

                        // Buscar en los detalles del traspaso
                        try (final PreparedStatement pstTraspDet = Conn.getConnection().prepareStatement("SELECT * FROM trasppartidas_det WHERE id_trasp= ? AND codpresu= ? AND ppto_egr_ing= 'E'")) {
                            pstTraspDet.setLong(1, id_trasp);
                            pstTraspDet.setString(2, partida.getCodigo());
                            try (final ResultSet rsTraspDet = pstTraspDet.executeQuery()) {
                                while (rsTraspDet.next()) {
                                    final String ori_dest = rsTraspDet.getString("tipo_ori_dest");
                                    final BigDecimal monto;
                                    if (ori_dest.equals("O")) {
                                        monto = rsTraspDet.getBigDecimal("monto").negate();
                                    } else {
                                        monto = rsTraspDet.getBigDecimal("monto");
                                    }

                                    ini = ini.add(monto).setScale(2, RoundingMode.HALF_UP);
                                }
                            }
                        }
                    }
                }
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar los datos del informe" + System.getProperty("line.separator") + _ex);
        }

        return ini;
    }

    /**
     * Rev 20/02/2017
     *
     * @return
     */
    private void setPeriodo() {

        try {
            ejefis_year = Format.toLong(txtEjeFis.getText().trim());
        } catch (final Exception _ex) {
            ejefis_year = Globales.getServerYear();
            txtEjeFis.setText(String.valueOf(ejefis_year));
        }

        if (btnAno.isSelected()) {
            mesDesde = "Inicio Anual";
            mesHasta = "Inicio Anual";
            ejeFisMesDesde = 1;
            ejeFisMesHasta = 12;
            lblMSJ.setText("Ha seleccionado el Año completo");
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
            lblMSJ.setText("Ha seleccionado el Año completo");
        }
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
        jpEditables = new javax.swing.JPanel();
        jpCmdState = new javax.swing.JPanel();
        btnSeleccionar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        txtCant = new javax.swing.JFormattedTextField(Integer.valueOf(0));
        btnCancelar = new javax.swing.JButton();
        btnReportSummary = new javax.swing.JButton();
        lblCodPresu = new javax.swing.JLabel();
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

        setTitle("EJECUCION MENSUAL DE LA PARTIDA");
        setMaximumSize(new java.awt.Dimension(1100, 650));
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setPreferredSize(new java.awt.Dimension(1100, 650));
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
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Movimiento", "Fecha" }));
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

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 380));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 380));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 380));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setFont(new java.awt.Font("Arial", 3, 15)); // NOI18N
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Movimiento", "Fecha", "Monto Bs.", "Documento", "Observaciones", "Reg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
        tblMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblMaster.setRowHeight(20);
        tblMaster.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblMaster.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(350);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(350);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(600);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(1000);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(0);
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
        btnSeleccionar.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N
        btnSeleccionar.setText("SELECCIONAR");
        btnSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSeleccionar.setEnabled(false);
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

        btnCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
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

        btnReportSummary.setBackground(java.awt.SystemColor.inactiveCaption);
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

        lblCodPresu.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblCodPresu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodPresu.setMaximumSize(new java.awt.Dimension(200, 25));
        lblCodPresu.setMinimumSize(new java.awt.Dimension(200, 25));
        lblCodPresu.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        jpCmdState.add(lblCodPresu, gridBagConstraints);

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

        setBounds(0, 0, 1118, 650);
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
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
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

        param.put("codpresu", partida.getCodigo());
        param.put("partida", partida.getPartida());

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final ArrayList<PptoEjecDetRptModel> list = new ArrayList<>();

        BigDecimal totPpto = BigDecimal.ZERO;
        BigDecimal totIni = BigDecimal.ZERO;
        BigDecimal totCre = BigDecimal.ZERO;
        BigDecimal totTra = BigDecimal.ZERO;
        BigDecimal totCompr = BigDecimal.ZERO;
        BigDecimal totSal = BigDecimal.ZERO;

        for (int i = 0; i < tblMaster.getRowCount(); i++) {

            PptoEjecDetModel reg = (PptoEjecDetModel) tblMaster.getValueAt(i, colReg);
            list.add(new PptoEjecDetRptModel(reg));

            if (reg.getEjeMov().equals(EjecMov.APROBADO.toString())) {
                totPpto = totPpto.add(reg.getMonto()).setScale(2, RoundingMode.HALF_UP);
            } else if (reg.getEjeMov().equals(EjecMov.INICIAL.toString())) {
                totIni = totIni.add(reg.getMonto()).setScale(2, RoundingMode.HALF_UP);
            } else if (reg.getEjeMov().equals(EjecMov.CREDITO.toString())) {
                totCre = totCre.add(reg.getMonto()).setScale(2, RoundingMode.HALF_UP);
            } else if (reg.getEjeMov().equals(EjecMov.TRASPASO.toString())) {
                totTra = totTra.add(reg.getMonto()).setScale(2, RoundingMode.HALF_UP);
            } else if (reg.getEjeMov().equals(EjecMov.COMPROMISO.toString())) {
                totCompr = totCompr.add(reg.getMonto()).setScale(2, RoundingMode.HALF_UP);
            } else if (reg.getEjeMov().equals(EjecMov.SALDO.toString())) {
                totSal = totSal.add(reg.getMonto()).setScale(2, RoundingMode.HALF_UP);
            }
        }

        param.put("tot_ppto", totPpto);
        param.put("tot_ini", totIni);
        param.put("tot_cre", totCre);
        param.put("tot_tra", totTra);
        param.put("tot_compr", totCompr);
        param.put("tot_sal", totSal);
        // ppto_ejecucion_partida
        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/ppto_ejecucion_partida.jasper");
        if (pathRpt != null) {

            new Thread() {
                @Override
                public void run() {
                    try {
                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), new JRBeanCollectionDataSource(list));

                        JasperViewer.viewReport(jasperPrint, false);

                        // JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/ppto_ejecucion.pdf");
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
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
            final String fileName = "EjeMensualPartidaPpto " + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Globales.getServerTimeStamp());

            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            TableToExcel.exportExcel(tblMaster, fileName + ".xls");
            TableToExcel.exportTSV(tblMaster, fileName + ".txt");
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, "Operación realizada. " + System.getProperty("line.separator") + fileName + ".xls");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void txtEjeFisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEjeFisFocusLost
        UpdatePanels();
    }//GEN-LAST:event_txtEjeFisFocusLost

    private void txtEjeFisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEjeFisActionPerformed
        UpdatePanels();
    }//GEN-LAST:event_txtEjeFisActionPerformed

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed

    }//GEN-LAST:event_btnSeleccionarActionPerformed

    /**
     * Rev 21/11/2016
     *
     * @param _e
     */
    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent _e) {
        final JTable jtable = (JTable) xjparam.get(JTABLE);

        if (jtable != null) {
            final int selRow = jtable.getSelectedRow();
            if (selRow >= 0) {
//
            }
        }
    }

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
                try {
                    new PptoEjecDetMesRpt(null, PptoModel.getReg_x_Id("presupe", 1L)).setVisible(true);
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, "Error" + System.getProperty("line.separator") + _ex);
                }
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
    private javax.swing.JLabel lblCodPresu;
    private javax.swing.JLabel lblMSJ;
    private javax.swing.JLabel lblmonto1;
    private javax.swing.JTable tblMaster;
    private javax.swing.JFormattedTextField txtCant;
    private javax.swing.JTextField txtEjeFis;
    private javax.swing.JButton txtReset;
    // End of variables declaration//GEN-END:variables

    private final Connection cn = Conn.getConnection();
}
