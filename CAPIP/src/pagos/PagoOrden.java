/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package pagos;

import bancos.BancosChequePagoCambiar;
import bancos.BancosContabilidad;
import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import gen_next_num.GenNum_xCuenta;
import gen_next_num.OrdPagNextNum;
import impuestos.OrdPagCuentaSel;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import static java.lang.Math.min;
import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.AvaEfeModel;
import modelos.CauModel;
import modelos.ChequeModel;
import modelos.ComprModel;
import modelos.CuentaModel;
import modelos.ModelMapNextOp;
import modelos.PagoDetSinImpModel;
import modelos.PagoModel;
import modelos.PagoRptModel;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.CuentaUpdatable;
import utils.DateCellRenderer;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.GenNum_xPag;
import utils.IntegerCellRenderer;
import utils.Num2Let;
import utils.OrdPagIdTipo;
import utils.TipoCompr;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class PagoOrden extends javax.swing.JFrame implements CuentaUpdatable {

    private static final long serialVersionUID = 1L;

    private final int COL_SEL = 0;
    private final int COL_NUM_CAUSADO = 1;
    private final int COL_FECHA_CAUSADO = 2;
    private final int COL_BENEF_RAZONSOCIAL = 3;
    private final int COL_BENEF_RIF_CI = 4;
    private final int COL_TOTAL_BS = 5;
    private final int COL_RESTA_BS = 6;
    private final int COL_REG = 7;

    private static long ultimo_id_orden_pago;

    static {
        ultimo_id_orden_pago = 0;
    }

    private final java.awt.Window parent;
    private CuentaModel regCuenta;
    private OrdPagNextNum pagNextNum;
    private int cantSel;

    /**
     * Rev 25/10/2016
     *
     * @param _parent
     */
    public PagoOrden(final java.awt.Window _parent) {
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
            updateMaster();
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

    /**
     * Rev 26/10/2016
     */
    private void setCompBehavior() {
        setTblMasterBehavior();
        setCompDetailBehavior();

//        txtOtrasRetenciones.setValue(BigDecimal.ZERO);
//
//        txtOtrasRetenciones.getDocument().addDocumentListener(
//            new DocumentListener() {
//                @Override
//                public void changedUpdate(DocumentEvent e) {
//                    calcularTotalRetencion();
//                }
//
//                @Override
//                public void insertUpdate(DocumentEvent e) {
//                    calcularTotalRetencion();
//                }
//
//                @Override
//                public void removeUpdate(DocumentEvent e) {
//                    calcularTotalRetencion();
//                }
//            });
    }

    /**
     * Rev 21/09/2016
     *
     */
    private void setTblMasterBehavior() {

        // Manejar el click en la Tabla
        tblMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) { // un solo click
                    int selRow = tblMaster.getSelectedRow();
                    if (selRow >= 0) {
                        // Cambiar estado de la seleccion
                        tblMaster.setValueAt(!(boolean) tblMaster.getValueAt(selRow, COL_SEL), selRow, COL_SEL);

                        selReg();
                    }
                }
            }
        });

        // Alineacion de las celdas de la JTable
        DefaultTableCellRenderer tcr;

        tblMaster.getColumnModel().getColumn(COL_FECHA_CAUSADO).setCellRenderer(new DateCellRenderer());

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(COL_BENEF_RIF_CI).setCellRenderer(tcr);

        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());

        // Configurar la tabla, para que se active con el SCAPE
        tblMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "SPACE");
        tblMaster.getActionMap().put("SPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = tblMaster.getSelectedRow();
                if (selRow >= 0) {
                    // Cambiar estado de la seleccion
                    tblMaster.setValueAt(!(boolean) tblMaster.getValueAt(selRow, COL_SEL), selRow, COL_SEL);

                    selReg();
                }
            }
        });

        tblMaster.requestFocusInWindow();
        if (tblMaster.getRowCount() > 0) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    tblMaster.setRowSelectionInterval(0, 0);
                    tblMaster.scrollRectToVisible(tblMaster.getCellRect(0, 0, true));
                }
            });
        }

    }

    /**
     * Rev 22/09/2016
     *
     */
    private void setCompDetailBehavior() {
        txtA_Pagar.setFormatterFactory(Globales.getCurFormatterFactory());

        // Para que cada vez que se modifique el campo se actualice el subtotal
        txtA_Pagar.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        newResta();
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        newResta();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        newResta();
                    }

                    /**
                     * Rev 29/10/2016
                     *
                     */
                    private void newResta() {

                        // Inicializar el combo de cuentas
                        if ((Globales.ORD_PAG == OrdPagIdTipo.ABSOLUTO) && (cmbCuentas.getSelectedIndex() >= 0)) {
                            cmbCuentas.setSelectedIndex(-1);
                        }

//                    if ((Globales.ORD_PAG_ID_TIPO == OrdPagIdTipo.ABSOLUTO) && btnGuardar.isEnabled()) {
//                        chkSinCh.setEnabled(false);
//                        btnGuardar.setEnabled(false);
//                    }
//                    
                        BigDecimal resta_bs = BigDecimal.ZERO;
                        for (int i = 0; i < tblMaster.getRowCount(); i++) {
                            if ((Boolean) tblMaster.getValueAt(i, COL_SEL)) {
                                resta_bs = resta_bs.add(((CauModel) tblMaster.getValueAt(i, COL_REG)).getResta_bs());
                            }
                        }

                        final BigDecimal apagar_bs;
                        try {
                            apagar_bs = Format.toBigDec(txtA_Pagar.getText().trim());
                        } catch (final Exception _ex) {
                            txtResta.setText("0,00");
                            return;
                        }

                        if (apagar_bs.compareTo(resta_bs) <= 0) {
                            txtResta.setText(Format.toStr(resta_bs.subtract(apagar_bs)));
                        } else {
                            txtResta.setText("Error");
                        }
                    }
                });
    }

    /**
     * Rev 25/09/2016
     *
     */
    private void setStartConditions() {
        regCuenta = null;

        switch (Globales.ORD_PAG) {
            case ABSOLUTO:
                pagNextNum = new GenNum_xPag();
                break;

            case RELATIVO:
                pagNextNum = new OrdPagNextNum();
                final java.awt.Window me = this;

                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new OrdPagCuentaSel(me).setVisible(true);
                        me.setVisible(false);
                    }
                });
                break;

            default:
                pagNextNum = null;
        }

        txtFecha.setText(Format.toStr(new java.sql.Date(Globales.getServerTimeStamp().getTime())));

        clearComp();
        updateMaster();
    }

    /**
     * Rev 07/11/2016
     *
     */
    void calcularTotalRetencion() {

        final BigDecimal otras_retenciones;

        try {
            final String aux = txtOtrasRetenciones.getText().trim();
            if (aux.isEmpty()) {
                return;
            }

            otras_retenciones = Format.toBigDec(aux);
        } catch (Exception _ex) {
            return;
        }

        try {

            final BigDecimal iva_retenido_bs = Format.toBigDec(txtIvaRetenido.getText());
            final BigDecimal islr_retenido_bs = Format.toBigDec(txtIslrRetenido.getText());

            final BigDecimal sumaTotalCau_retenido = iva_retenido_bs.add(islr_retenido_bs).add(otras_retenciones);

            BigDecimal sumaTotalCau_bs = BigDecimal.ZERO;
            BigDecimal resta_bs = BigDecimal.ZERO;

            for (int row = 0; row < tblMaster.getRowCount(); row++) {
                if (!(boolean) tblMaster.getValueAt(row, COL_SEL)) {
                    continue;
                }

                final CauModel regCau = (CauModel) tblMaster.getValueAt(row, COL_REG);

                // Va acumulando los totales
                sumaTotalCau_bs = sumaTotalCau_bs.add(regCau.getTotal_bs());
                resta_bs = resta_bs.add(regCau.getResta_bs());
            }

            /////////////////////////////////////////////////////////////////
            // Totales
            txtTotalRet.setText(Format.toStr(sumaTotalCau_retenido));

            txtSumaTotalCau.setText(Format.toStr(sumaTotalCau_bs));
            txtSumaTotalCau_menosRet.setText(Format.toStr(sumaTotalCau_bs.subtract(sumaTotalCau_retenido)));
            txtPagado.setText(Format.toStr(sumaTotalCau_bs.subtract(resta_bs)));

            // Por defecto se considera como el pago por el total pendiente 
            txtA_Pagar.setText(Format.toStr(resta_bs));
            txtResta.setText("0,00");

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
        }
    }

    /**
     * Rev 26/10/2016
     */
    private void selReg() {
        int selRow = tblMaster.getSelectedRow();
        if (selRow < 0) {
            return;
        }

        final CauModel regCau = (CauModel) tblMaster.getValueAt(selRow, COL_REG);
        final boolean sel = (boolean) tblMaster.getValueAt(selRow, COL_SEL);

        if (sel) {
            cantSel++;
            txaConceptoCau.setText(regCau.getObs());
        } else {
            cantSel--;
            txaConceptoCau.setText("");
        }

        switch (cantSel) {
            case 0:
                txtA_Pagar.setEditable(false);
                txtOtrasRetenciones.setEnabled(false);
                if (Globales.ORD_PAG == OrdPagIdTipo.ABSOLUTO) {
                    cmbCuentas.setEnabled(false);
                    cmbCuentas.setSelectedIndex(-1);
                }
                btnEditarUltCheque.setEnabled(ultimo_id_orden_pago > 0);
                txtBeneficiario.setText("");
                txtBenef_rif_ci.setText("");
                txaConceptoCau.setText("");
                txtOtrasRetenciones.setText("0,00");
                break;
            case 1:
                txtA_Pagar.setEditable(true);
                txtOtrasRetenciones.setEnabled(true);
                if (Globales.ORD_PAG == OrdPagIdTipo.ABSOLUTO) {
                    cmbCuentas.setEnabled(true);
                    cmbCuentas.setSelectedIndex(-1);
                }
                txtBeneficiario.setText(regCau.getBenef_razonsocial());
                txtBenef_rif_ci.setText(regCau.getBenef_rif_ci());
                txaConceptoCau.setText(regCau.getObs());
                break;
            default:
                if (!regCau.getBenef_rif_ci().equals(txtBenef_rif_ci.getText())) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar el mismo beneficiario");
                    tblMaster.setValueAt(false, selRow, COL_SEL);
                    cantSel--;
                }
                break;
        }

        updatePanelTotal();
    }

    /**
     * Rev 26/10/2016
     */
    private void updatePanelTotal() {

        final int rowCount = tblMaster.getRowCount();
        if (rowCount <= 0) {
            txtTotalRet.setText("0,00");
            txtSumaTotalCau.setText("0,00");
            txtIva_bs.setText("0,00");
            txtOtrasRetenciones.setText("0,00");
            txtSumaTotalCau_menosRet.setText("0,00");
            txtPagado.setText("0,00");
            return;
        }

        try {
            cargarComboCuentas(cmbCuentas, regCuenta);
            clearPnlRetencion();
            clearPnlTotal();

            txaComprFact.setText("");

            BigDecimal sumaTotalCau_bs = BigDecimal.ZERO;
            BigDecimal iva_bs = BigDecimal.ZERO;
            BigDecimal iva_retenido_bs = BigDecimal.ZERO;
            BigDecimal islr_retenido_bs = BigDecimal.ZERO;
            BigDecimal otras_retenciones = BigDecimal.ZERO;
            BigDecimal resta_bs = BigDecimal.ZERO;

            for (int row = 0; row < tblMaster.getRowCount(); row++) {
                if (!(boolean) tblMaster.getValueAt(row, COL_SEL)) {
                    continue;
                }

                final CauModel regCau = (CauModel) tblMaster.getValueAt(row, COL_REG);

                // Hallar todos los compromisos asociados al causado
                ArrayList<ComprModel> list = ComprModel.getxIdCausado(regCau.getId_causado(), regCau.getTipo_compr());
                for (ComprModel regCompr : list) {
                    txaComprFact.append(regCau.getTipo_compr().name() + "-" + regCompr.getId_compromiso() + " : " + regCompr.getNumFact() + "\n");
                }

                // Va acumulando los totales
                sumaTotalCau_bs = sumaTotalCau_bs.add(regCau.getTotal_bs());
                iva_bs = iva_bs.add(regCau.getIva_bs());
                iva_retenido_bs = iva_retenido_bs.add(regCau.getIva_ret_bs());
                islr_retenido_bs = islr_retenido_bs.add(regCau.getIslr_ret_bs());
                otras_retenciones = otras_retenciones.add(regCau.getORet_ret_bs()).add(regCau.getImpMun_ret_bs()).add(regCau.getNegPri_ret_bs());
                resta_bs = resta_bs.add(regCau.getResta_bs());
            }

            txaComprFact.setCaretPosition(0);

            txtIvaRetenido.setText(Format.toStr(iva_retenido_bs));
            txtIslrRetenido.setText(Format.toStr(islr_retenido_bs));
            txtOtrasRetenciones.setText(Format.toStr(otras_retenciones));
            BigDecimal sumaTotalCau_retenido = iva_retenido_bs.add(islr_retenido_bs).add(otras_retenciones);

            /////////////////////////////////////////////////////////////////
            // Totales
            txtTotalRet.setText(Format.toStr(sumaTotalCau_retenido));
            txtSumaTotalCau.setText(Format.toStr(sumaTotalCau_bs));
            txtIva_bs.setText(Format.toStr(iva_bs));
            txtSumaTotalCau_menosRet.setText(Format.toStr(sumaTotalCau_bs.subtract(sumaTotalCau_retenido)));
            txtPagado.setText(Format.toStr(sumaTotalCau_bs.subtract(resta_bs)));

            // Por defecto se considera como el pago por el total pendiente 
            txtA_Pagar.setText(Format.toStr(resta_bs));
            txtResta.setText("0,00");

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
        }
    }

    /**
     * Rev 01/11/2016
     *
     * @param _cmbCuentas
     * @param _idx
     */
    public static void cargarComboCuentas(final JComboBox<String> _cmbCuentas, final int _idx) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos ORDER BY banco, cuenta ASC");
            while (rs.next()) {
                final String nombreCompleto = rs.getObject("cuenta") + " " + rs.getObject("banco");
                modeloCombo.addElement(nombreCompleto);
            }
            _cmbCuentas.setModel(modeloCombo);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        _cmbCuentas.setSelectedIndex(_idx);
    }

    /**
     * Rev 01/11/2016
     *
     * @param _cmbCuentas
     * @param _cuenta
     */
    public static void cargarComboCuentas(final JComboBox<String> _cmbCuentas, final CuentaModel _cuenta) {

        final String clave;
        if (_cuenta == null) {
            clave = "";
        } else {
            clave = _cuenta.getCuenta() + " " + _cuenta.getBanco();
        }

        final DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        int idx = -1;

        try (final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos ORDER BY banco, cuenta ASC")) {

            while (rs.next()) {
                final String nombreCompleto = rs.getObject("cuenta") + " " + rs.getObject("banco");
                modeloCombo.addElement(nombreCompleto);

                if (nombreCompleto.equals(clave)) {
                    idx = modeloCombo.getSize() - 1;
                }
            }
            _cmbCuentas.setModel(modeloCombo);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        // Esto obliga a que se prepare al JComba, para disparar el listerned itemStateChanged
        // de lo contrario, si al hacer un selectedIndex, el idx es 0, no se realiza la llamado,
        // ya que por defecto al hacer un setModel, se ha seleccionado el indice 0.
        if (idx == 0) {
            _cmbCuentas.setSelectedIndex(-1);
        }

        _cmbCuentas.setSelectedIndex(idx);

    }

    /**
     * Rev 24/10/2016
     *
     */
    private void updateMaster() {

        btnEditarUltCheque.setEnabled(ultimo_id_orden_pago > 0);

        // Para evitar multiples llamadas al metodo valueChanged, en el listerned
        tblMaster.clearSelection();

        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            final Object[] datos = new Object[8];
            final ResultSet rs = Conn.executeQuery("SELECT * FROM causado WHERE resta_bs > 0 AND iva_ret_sn= 'S' AND islr_ret_sn= 'S' AND YEAR(ejefis)= " + Globales.getEjeFisYear() + " AND anulado_sn= 'N'");
            while (rs.next()) {
                CauModel regCau = new CauModel(rs);
                datos[0] = false;
                datos[COL_NUM_CAUSADO] = regCau.getId_causado();
                datos[COL_FECHA_CAUSADO] = regCau.getFecha_causado();
                datos[COL_BENEF_RAZONSOCIAL] = regCau.getBenef_razonsocial();
                datos[COL_BENEF_RIF_CI] = regCau.getBenef_rif_ci();
                datos[COL_TOTAL_BS] = regCau.getTotal_bs().doubleValue();
                datos[COL_RESTA_BS] = regCau.getResta_bs().doubleValue();
                datos[COL_REG] = regCau;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
    }

    private void setPagadoCauCompr(long id_causado, long id_orden_pago) throws Exception {
        if (Conn.getConnection().prepareStatement("UPDATE causado SET resta= 0.00, id_orden_pago= " + id_orden_pago + " WHERE id_causado= " + id_causado).executeUpdate() != 1) {
            throw new Exception("Error al establecer el pago");
        }

        // Dado el numero de causado, recuperar el tipo de compromiso
        final TipoCompr tipoCompr;

        final ResultSet rsCau = Conn.executeQuery("SELECT * FROM causado WHERE numcau=" + id_causado);
        if (rsCau.next()) {
            tipoCompr = TipoCompr.valueOf(rsCau.getString("tipo_compr"));
        } else {
            JOptionPane.showMessageDialog(null, "Causado no encontrado");
            return;
        }

        Conn.getConnection().prepareStatement("UPDATE " + tipoCompr.getTbl() + " SET id_orden_pago= " + id_orden_pago + " WHERE numcau = " + id_causado + "").executeUpdate();
    }

    /**
     * Rev 06-03-2017
     *
     * @param _id_causado
     * @param _apagar_bs
     * @throws Exception
     */
    public static void setAPagarCau(final long _id_causado, final BigDecimal _apagar_bs) throws Exception {
        final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE causado "
                + "SET resta_bs= resta_bs - ?, imp_mun_ret_sn= 'S', neg_pri_ret_sn= 'S', oret_ret_sn= 'S' "
                + "WHERE id_causado = ?");
        pst.setBigDecimal(1, _apagar_bs);
        pst.setLong(2, _id_causado);
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error al actualizar el resta del Causado");
        }
    }

    /**
     * Rev 18-04-2017
     *
     * @param _id_causado
     * @param _anular_bs
     * @throws Exception
     */
    public static void setAnularPagCau(final long _id_causado, final BigDecimal _anular_bs) throws Exception {

        final CauModel regCau = CauModel.getxID(_id_causado);

        if (regCau == null) {
            throw new Exception("Error, causado no encontrado");
        }

        final ResultSet rsCauPag = Conn.executeQuery("SELECT COUNT(id_causado) FROM causado_orden_pago "
                + "WHERE id_causado= " + _id_causado + " AND anulado_sn= 'N'");
        if (rsCauPag.next()) {
            final int count = rsCauPag.getInt(1);

            if (count <= 0) {
                throw new Exception("Error, pago no encontrado, o previamente anulado");
            } else if (count <= 1) {
                // El presente en el ultimo (unico) pago, por lo que se debe actualizar los "sn" 
                // de los impuestos, si el retenido es cero por su puesto

                // Buscar todos los compromisos asociados, y hallar los impuestos
                final ResultSet rsComprs = Conn.executeQuery("SELECT SUM(imp_mun_bs), SUM(neg_pri_bs), SUM(oret_bs)  FROM " + regCau.getTipo_compr().getTbl() + " WHERE id_causado= " + regCau.getId_causado() + " AND anulado_sn= 'N'");
                if (rsComprs.next()) {

                    BigDecimal aux;
                    aux = rsComprs.getBigDecimal(1);
                    if (aux == null) {
                        aux = BigDecimal.ZERO;
                    }

                    final BigDecimal imp_mun_bs = aux; // rsComprs.getBigDecimal(1);

                    aux = rsComprs.getBigDecimal(2);
                    if (aux == null) {
                        aux = BigDecimal.ZERO;
                    }
                    final BigDecimal neg_pri_bs = aux; // rsComprs.getBigDecimal(2);

                    aux = rsComprs.getBigDecimal(3);
                    if (aux == null) {
                        aux = BigDecimal.ZERO;
                    }
                    final BigDecimal oret_bs = aux; // rsComprs.getBigDecimal(3);

                    if (imp_mun_bs.compareTo(BigDecimal.ZERO) <= 0) {
                        final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE causado "
                                + "SET imp_mun_ret_sn= 'N' WHERE id_causado = ?");
                        pst.setLong(1, _id_causado);
                        if (pst.executeUpdate() != 1) {
                            throw new Exception("Error al tratar de actualizar la anulaciòn del Imp. Mun. del Causado");
                        }
                    }

                    if (neg_pri_bs.compareTo(BigDecimal.ZERO) <= 0) {
                        final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE causado "
                                + "SET neg_pri_ret_sn= 'N' WHERE id_causado = ?");
                        pst.setLong(1, _id_causado);
                        if (pst.executeUpdate() != 1) {
                            throw new Exception("Error al tratar de actualizar la anulaciòn del \"Negro Primero\" del Causado");
                        }
                    }

                    if (oret_bs.compareTo(BigDecimal.ZERO) <= 0) {
                        final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE causado "
                                + "SET oret_ret_sn= 'N' WHERE id_causado = ?");
                        pst.setLong(1, _id_causado);
                        if (pst.executeUpdate() != 1) {
                            throw new Exception("Error al tratar de actualizar la anulaciòn de \"Otras Ret.\" del Causado");
                        }
                    }

                    final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE causado "
                            + "SET resta_bs= resta_bs + ? WHERE id_causado = ?");
                    pst.setBigDecimal(1, _anular_bs);
                    pst.setLong(2, _id_causado);
                    if (pst.executeUpdate() != 1) {
                        throw new Exception("Error al tratar de actualizar la anulaciòn del pago del Causado");
                    }

                } else {
                    throw new Exception("Error al tratar de actualizar la anulaciòn del pago del Causado" + System.getProperty("line.separator")
                            + "No se encontro los compromisos asociados");
                }

            } else {
                // Esto es porque quedan pagos >= 2, por lo que no se toma en cuanta el "sn" de los impuestos
                // el "sn" solo se toma en cuenta para el ultimo pago que quede antivo, es decir el primer pago realizado
                final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE causado "
                        + "SET resta_bs= resta_bs + ? WHERE id_causado = ?");
                pst.setBigDecimal(1, _anular_bs);
                pst.setLong(2, _id_causado);
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error al tratar de actualizar la anulaciòn del pago del Causado");
                }
            }

        } else {
            throw new Exception("Error el causado no tiene pago asociado");
        }

    }

    /**
     * Rev 28/10/2016
     *
     */
    private void clearComp() {
        txtFecha.setText(Format.toStr(new java.sql.Date(Globales.getServerTimeStamp().getTime())));
        try {
            txtOrdPago.setText(String.valueOf(pagNextNum.checkNum(regCuenta)));
        } catch (Exception ex) {
            txtOrdPago.setText("0");
        }

        txtBeneficiario.setText("");
        txtBenef_rif_ci.setText("");
        txaComprFact.setText("");
        txaConceptoCau.setText("");
        txaConceptoOrden.setText("");

        txtA_Pagar.setEditable(false);
        txtOtrasRetenciones.setEnabled(false);
        txtOtrasRetenciones.setValue(BigDecimal.ZERO);

        cmbCuentas.setEnabled(false);
        btnEditarUltCheque.setEnabled(ultimo_id_orden_pago > 0);
        chkByCheck.setSelected(true);
//        btnGuardar.setEnabled(false);

        cantSel = 0;
        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            tblMaster.setValueAt(false, i, COL_SEL);
        }

        cargarComboCuentas(cmbCuentas, regCuenta);
        if (regCuenta != null) {
            txtCuenta.setText(regCuenta.getCuenta());
            txtBanco.setText(regCuenta.getBanco());
            txtBanco.setCaretPosition(0);
        } else {
            txtCuenta.setText("");
            txtBanco.setText("");
        }
        txtCheque.setText("");

        clearPnlTotal();
        clearPnlRetencion();
    }

    /**
     * Rev 26/10/2016
     */
    private void clearPnlRetencion() {
        txtIvaRetenido.setText("0,00");
        txtIslrRetenido.setText("0,00");
        txtOtrasRetenciones.setText("0,00");
        txtTotalRet.setText("0,00");
    }

    /**
     * Rev 26/10/2016
     */
    private void clearPnlTotal() {

        txtSumaTotalCau.setText("0,00");

        txtIva_bs.setText("0,00");
        txtA_Pagar.setText("0,00");
        txtResta.setText("0,00");
        txtPagado.setText("0,00");
        txtSumaTotalCau_menosRet.setText("0,00");
    }

    /**
     * Rev 29/10/2016
     *
     * @param _id
     * @param _titulo
     * @param _export
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void rptOrdPag(final long _id, final String _titulo, final boolean _export) throws Exception {

        final Map<String, Object> param = genParamRptPag(_id, _titulo);

        // Verificar que se halla realizado exitosamente la operacion
        if (param == null) {
            throw new Exception("Error al generar param");
        }

        // Generar y agrupar los registros
        final ArrayList<PagoRptModel> listPpto = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT codpresu, partida, sum(subtotal) FROM causado_det "
                + "WHERE id_causado = "
                + "ANY (SELECT id_causado FROM causado_orden_pago WHERE id_orden_pago= " + _id + ") "
                + "GROUP BY codpresu");

        while (rs.next()) {
            listPpto.add(new PagoRptModel(rs.getString("codpresu"), rs.getString("partida"), rs.getBigDecimal("sum(subtotal)")));
        }

        if (listPpto.size() > 0) {

            final Class aClass = FrmPrincipal.getInstance().getClass();
            param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
            param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

            param.put("fecha", new java.util.Date(((java.sql.Date) param.get("fechaPago")).getTime()));

            final InputStream pathRpt = aClass.getResourceAsStream("/reportes/orden de pago.jasper");
            if (pathRpt != null) {

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), new JRBeanCollectionDataSource(listPpto));

                            JasperViewer.viewReport(jasperPrint, false);

                            if (_export) {
                                JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/" + _id + "_orden_pago.pdf");
                            }
                        } catch (final Exception _ex) {
                            JOptionPane.showMessageDialog(null, _ex);
                        }
                    }
                }.start();

            } else {
                JOptionPane.showMessageDialog(null, "Reporte de pago no encontrado");
            }
        } else {
            rptOrdPagSinImp(_id, _titulo, _export);
        }
    }

    /**
     * Rev 29/10/2016
     *
     * @param _id
     * @param _titulo
     * @param _export
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void rptOrdPagAvaImputacion(final long _id, final String _titulo, final boolean _export) throws Exception {

        final Map<String, Object> param = genParamRptPagAvaEfe(_id, _titulo);

        // Verificar que se halla realizado exitosamente la operacion
        if (param == null) {
            throw new Exception("Error al generar param");
        }

        // Generar y agrupar los registros
        final ArrayList<PagoRptModel> list = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT codpresu, partida, sum(subtotal) FROM causado_det "
                + "WHERE id_causado = "
                + "ANY (SELECT causado_avance_efectivo_nn.id_causado FROM causado_avance_efectivo_nn WHERE id_avance_efectivo= " + _id + ") "
                + "GROUP BY codpresu");

        while (rs.next()) {
            list.add(new PagoRptModel(rs.getString("codpresu"), rs.getString("partida"), rs.getBigDecimal("sum(subtotal)")));
        }

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/orden de pago.jasper");
        if (pathRpt != null) {

            new Thread() {
                @Override
                public void run() {
                    try {
                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), new JRBeanCollectionDataSource(list));

                        JasperViewer.viewReport(jasperPrint, false);

                        if (_export) {
                            JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/" + _id + ".pdf");
                        }
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
     * Rev 29/10/2016
     *
     * @param _id
     * @param _titulo
     * @param _export
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void rptOrdPagSinImp(final long _id, final String _titulo, final boolean _export) throws Exception {

        final Map<String, Object> param = genParamRptPag(_id, _titulo);

        // Verificar que se halla realizado exitosamente la operacion
        if (param == null) {
            throw new Exception("Error al generar param");
        }

        final ArrayList<PagoDetSinImpModel> listPagodet = new ArrayList<>();
        listPagodet.add(new PagoDetSinImpModel("0", "Sin Imputación", (BigDecimal) param.get("total_bs")));

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/orden de pago_sin_imputacion.jasper");
        if (pathRpt != null) {

            new Thread() {
                @Override
                public void run() {
                    try {
                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), new JRBeanCollectionDataSource(listPagodet));

                        JasperViewer.viewReport(jasperPrint, false);

                        if (_export) {
                            JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/" + _id + ".pdf");
                        }
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
     * Rev 29/10/2016
     *
     * @param _id
     * @param _titulo
     * @param _export
     * @throws Exception
     */
    @SuppressWarnings({"unchecked"})
    public static void rptChPag(final long _id, final String _titulo, final boolean _export) throws Exception {

        final Map<String, Object> param = genParamRptPag(_id, _titulo);

        // Verificar que se halla realizado exitosamente la operacion
        if (param == null) {
            throw new Exception("Error al generar param");
        }

        final String cuenta = ((String) param.get("cuenta"));
        if (cuenta == null || cuenta.isEmpty()) {
            return;
        }

        final ArrayList<ChequeModel> list = new ArrayList<>();
        ChequeModel cheque = new ChequeModel((String) param.get("montoPagar"), (String) param.get("razonSocial"), (String) param.get("rif"),
                (String) param.get("NumLiteral_1"), (String) param.get("NumLiteral_2"), (String) param.get("ciudad"), new java.util.Date(((java.sql.Date) param.get("fechaPago")).getTime()), (String) param.get("endosable"));
        list.add(cheque);

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("ciudad", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/cheque_global.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(500);

                        final JRBeanCollectionDataSource aux = new JRBeanCollectionDataSource(list);

                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), aux);

                        JasperViewer.viewReport(jasperPrint, false);

                        if (_export) {
                            JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/" + _id + "-CH.pdf");
                        }
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Cheque no encontrado");
            return;
        }

        // Los campos se convierten en parametros
        param.put("razonSocial", cheque.getBenef_razonsocial());
        param.put("rif_ci", cheque.getBenef_rif_ci());
        param.put("ciudad", cheque.getCiudad());
        param.put("endosable", cheque.getEndosable());
        param.put("fecha", cheque.getFecha());
        param.put("montoPagar", cheque.getMontoPagar());
        param.put("NumLiteral_1", cheque.getNumLiteral_1());
        param.put("NumLiteral_2", cheque.getNumLiteral_2());

        // Generar y agrupar los registros
        final ArrayList<PagoRptModel> listPpto = new ArrayList<>();

        final ResultSet rs = Conn.executeQuery("SELECT codpresu, partida, sum(subtotal) FROM causado_det "
                + "WHERE id_causado= "
                + "ANY (SELECT id_causado FROM causado_orden_pago WHERE id_orden_pago= " + _id + ") "
                + "GROUP BY codpresu");

        while (rs.next()) {
            listPpto.add(new PagoRptModel(rs.getString("codpresu"), rs.getString("partida"), rs.getBigDecimal("sum(subtotal)")));
        }

        if (listPpto.size() <= 0) {
            listPpto.add(new PagoRptModel("SIN PARTIDA", "SIN PARTIDA", BigDecimal.ZERO));
        }

        if (listPpto.size() > 0) {
            final InputStream pathRptAsiento = aClass.getResourceAsStream("/reportes/asiento_de_cheque.jasper");
            if (pathRptAsiento != null) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(100);

                            final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRptAsiento), new HashMap<>(param), new JRBeanCollectionDataSource(listPpto));

                            JasperViewer.viewReport(jasperPrint, false);

                            JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/" + _id + "_asiento_ch.pdf");
                        } catch (final Exception _ex) {
                            JOptionPane.showMessageDialog(null, _ex);
                        }
                    }
                }.start();
            } else {
                JOptionPane.showMessageDialog(null, "Asiento de Cheque no encontrado");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtBeneficiario = new javax.swing.JTextField();
        txtBenef_rif_ci = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtOrdPago = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaComprFact = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        pnlBanco = new javax.swing.JPanel();
        cmbCuentas = new javax.swing.JComboBox<String>();
        jLabel9 = new javax.swing.JLabel();
        txtCheque = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtBanco = new javax.swing.JTextField();
        txtCuenta = new javax.swing.JTextField();
        txtSaldoCuenta = new javax.swing.JFormattedTextField();
        chkEndosable = new javax.swing.JCheckBox();
        pnlRetencion = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtIvaRetenido = new javax.swing.JFormattedTextField();
        txtIslrRetenido = new javax.swing.JFormattedTextField();
        txtOtrasRetenciones = new javax.swing.JFormattedTextField();
        txtTotalRet = new javax.swing.JFormattedTextField();
        pnlTotal = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSumaTotalCau = new javax.swing.JFormattedTextField();
        txtIva_bs = new javax.swing.JFormattedTextField();
        txtA_Pagar = textfield_decimal.DecimalTextField.getTextField();
        txtSumaTotalCau_menosRet = new javax.swing.JFormattedTextField();
        txtResta = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        txtPagado = new javax.swing.JFormattedTextField();
        btnOrdenDirecta = new javax.swing.JButton();
        btnAvanceEfectivo = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaConceptoCau = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txaConceptoOrden = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        btnEditarUltCheque = new javax.swing.JButton();
        txtConsultar = new javax.swing.JButton();
        txtFecha = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        btnEditarUltCheque1 = new javax.swing.JButton();
        chkByCheck = new javax.swing.JCheckBox();
        btnReImprimirCh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ORDEN DE PAGO");
        setMinimumSize(new java.awt.Dimension(1150, 720));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBeneficiario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        txtBeneficiario.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtBeneficiario.setEnabled(false);
        jPanel1.add(txtBeneficiario, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 13, 290, -1));

        txtBenef_rif_ci.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        txtBenef_rif_ci.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtBenef_rif_ci.setEnabled(false);
        jPanel1.add(txtBenef_rif_ci, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 47, 290, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("BENEFICIARIO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 18, 90, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("CEDULA / R.I.F.");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 52, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 410, 80));

        jLabel1.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("     ORDEN   DE   PAGO");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 720, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel4.setText("NUM. COMPR. / NUM. FACT.");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 20, 150, 20);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("ORDEN DE PAGO Nº");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(460, 25, 100, 40);

        txtOrdPago.setEditable(false);
        txtOrdPago.setBackground(new java.awt.Color(204, 153, 0));
        txtOrdPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtOrdPago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtOrdPago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOrdPago.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtOrdPago.setEnabled(false);
        txtOrdPago.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jPanel2.add(txtOrdPago);
        txtOrdPago.setBounds(570, 25, 100, 40);

        txaComprFact.setEditable(false);
        txaComprFact.setBackground(new java.awt.Color(204, 153, 0));
        txaComprFact.setColumns(20);
        txaComprFact.setRows(3);
        txaComprFact.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        jScrollPane2.setViewportView(txaComprFact);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(160, 10, 290, 60);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SELECCIONADA(S)");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(10, 40, 130, 13);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, 680, 80));

        pnlBanco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        pnlBanco.setOpaque(false);
        pnlBanco.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbCuentas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmbCuentas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCuentasItemStateChanged(evt);
            }
        });
        pnlBanco.add(cmbCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 350, 30));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("SALDO  EN  CUENTA");
        pnlBanco.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, -1, 30));

        txtCheque.setBackground(new java.awt.Color(204, 153, 0));
        txtCheque.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtCheque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCheque.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtCheque.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChequeActionPerformed(evt);
            }
        });
        txtCheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtChequeKeyTyped(evt);
            }
        });
        pnlBanco.add(txtCheque, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 130, 40));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("CH. Nº");
        pnlBanco.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 50, 40));

        txtBanco.setEditable(false);
        txtBanco.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtBanco.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBanco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtBanco.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtBanco.setEnabled(false);
        txtBanco.setFocusable(false);
        pnlBanco.add(txtBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 220, 30));

        txtCuenta.setEditable(false);
        txtCuenta.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtCuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCuenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtCuenta.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCuenta.setEnabled(false);
        txtCuenta.setFocusable(false);
        pnlBanco.add(txtCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 220, 30));

        txtSaldoCuenta.setBackground(new java.awt.Color(204, 153, 0));
        txtSaldoCuenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSaldoCuenta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSaldoCuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSaldoCuenta.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtSaldoCuenta.setEnabled(false);
        txtSaldoCuenta.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        pnlBanco.add(txtSaldoCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 130, 180, 40));

        chkEndosable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        chkEndosable.setSelected(true);
        chkEndosable.setText("Endosable");
        pnlBanco.add(chkEndosable, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, -1, -1));

        getContentPane().add(pnlBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 370, 180));

        pnlRetencion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        pnlRetencion.setOpaque(false);
        pnlRetencion.setLayout(null);

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("I.V.A. RET. Bs.");
        pnlRetencion.add(jLabel17);
        jLabel17.setBounds(5, 20, 90, 15);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("I.S.R.L. RET. Bs.");
        pnlRetencion.add(jLabel19);
        jLabel19.setBounds(5, 60, 90, 15);

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("OTRAS RET. Bs.");
        pnlRetencion.add(jLabel20);
        jLabel20.setBounds(5, 100, 90, 15);

        jLabel21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("TOTAL Bs.");
        pnlRetencion.add(jLabel21);
        jLabel21.setBounds(5, 150, 90, 15);

        txtIvaRetenido.setBackground(new java.awt.Color(240, 240, 240));
        txtIvaRetenido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtIvaRetenido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIvaRetenido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIvaRetenido.setText("0,00");
        txtIvaRetenido.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtIvaRetenido.setEnabled(false);
        txtIvaRetenido.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        pnlRetencion.add(txtIvaRetenido);
        txtIvaRetenido.setBounds(110, 10, 180, 30);

        txtIslrRetenido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtIslrRetenido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIslrRetenido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIslrRetenido.setText("0,00");
        txtIslrRetenido.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtIslrRetenido.setEnabled(false);
        txtIslrRetenido.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        pnlRetencion.add(txtIslrRetenido);
        txtIslrRetenido.setBounds(110, 50, 180, 30);

        txtOtrasRetenciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtOtrasRetenciones.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtOtrasRetenciones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOtrasRetenciones.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtOtrasRetenciones.setEnabled(false);
        txtOtrasRetenciones.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtOtrasRetenciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOtrasRetencionesActionPerformed(evt);
            }
        });
        txtOtrasRetenciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOtrasRetencionesKeyTyped(evt);
            }
        });
        pnlRetencion.add(txtOtrasRetenciones);
        txtOtrasRetenciones.setBounds(110, 90, 180, 30);

        txtTotalRet.setBackground(new java.awt.Color(204, 153, 0));
        txtTotalRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtTotalRet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalRet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalRet.setText("0,00");
        txtTotalRet.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtTotalRet.setEnabled(false);
        txtTotalRet.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        pnlRetencion.add(txtTotalRet);
        txtTotalRet.setBounds(110, 140, 180, 30);

        getContentPane().add(pnlRetencion, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 450, 300, 180));

        pnlTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        pnlTotal.setOpaque(false);
        pnlTotal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("TOTAL");
        jLabel11.setMaximumSize(new java.awt.Dimension(75, 0));
        jLabel11.setMinimumSize(new java.awt.Dimension(75, 0));
        jLabel11.setPreferredSize(new java.awt.Dimension(75, 0));
        pnlTotal.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 10, 55, 30));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("I.V.A ");
        jLabel12.setMaximumSize(new java.awt.Dimension(75, 0));
        jLabel12.setMinimumSize(new java.awt.Dimension(75, 0));
        jLabel12.setPreferredSize(new java.awt.Dimension(75, 0));
        pnlTotal.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 50, 55, 30));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("A PAGAR");
        jLabel13.setMaximumSize(new java.awt.Dimension(75, 0));
        jLabel13.setMinimumSize(new java.awt.Dimension(75, 0));
        jLabel13.setPreferredSize(new java.awt.Dimension(75, 0));
        pnlTotal.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 90, 55, 30));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("TOTAL MENOS RET.");
        pnlTotal.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 130, 20));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("RESTA\n");
        jLabel15.setMaximumSize(new java.awt.Dimension(75, 0));
        jLabel15.setMinimumSize(new java.awt.Dimension(75, 0));
        jLabel15.setPreferredSize(new java.awt.Dimension(75, 0));
        pnlTotal.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 130, 55, 30));

        txtSumaTotalCau.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSumaTotalCau.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSumaTotalCau.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaTotalCau.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtSumaTotalCau.setEnabled(false);
        txtSumaTotalCau.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        pnlTotal.add(txtSumaTotalCau, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 200, 30));

        txtIva_bs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtIva_bs.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIva_bs.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIva_bs.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtIva_bs.setEnabled(false);
        txtIva_bs.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        pnlTotal.add(txtIva_bs, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 200, 30));

        txtA_Pagar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtA_Pagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtA_Pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtA_Pagar.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtA_Pagar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        pnlTotal.add(txtA_Pagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 200, 30));

        txtSumaTotalCau_menosRet.setBackground(new java.awt.Color(204, 153, 0));
        txtSumaTotalCau_menosRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSumaTotalCau_menosRet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSumaTotalCau_menosRet.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSumaTotalCau_menosRet.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtSumaTotalCau_menosRet.setEnabled(false);
        txtSumaTotalCau_menosRet.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        pnlTotal.add(txtSumaTotalCau_menosRet, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 120, 155, 40));

        txtResta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtResta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtResta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtResta.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtResta.setEnabled(false);
        txtResta.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        pnlTotal.add(txtResta, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 200, 30));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("PAGADO (+RET.)");
        pnlTotal.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 130, 20));

        txtPagado.setBackground(new java.awt.Color(204, 153, 0));
        txtPagado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtPagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtPagado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPagado.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtPagado.setEnabled(false);
        txtPagado.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        pnlTotal.add(txtPagado, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 40, 155, 40));

        getContentPane().add(pnlTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 450, 430, 180));

        btnOrdenDirecta.setBackground(new java.awt.Color(153, 153, 0));
        btnOrdenDirecta.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnOrdenDirecta.setText("ORDEN DIRECTA");
        btnOrdenDirecta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnOrdenDirecta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenDirectaActionPerformed(evt);
            }
        });
        getContentPane().add(btnOrdenDirecta, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 640, 160, 40));

        btnAvanceEfectivo.setBackground(new java.awt.Color(153, 153, 0));
        btnAvanceEfectivo.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAvanceEfectivo.setText("FONDO EN AVANCE");
        btnAvanceEfectivo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnAvanceEfectivo.setMaximumSize(new java.awt.Dimension(220, 40));
        btnAvanceEfectivo.setMinimumSize(new java.awt.Dimension(220, 40));
        btnAvanceEfectivo.setPreferredSize(new java.awt.Dimension(220, 40));
        btnAvanceEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanceEfectivoActionPerformed(evt);
            }
        });
        getContentPane().add(btnAvanceEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 640, 200, 40));

        jLabel16.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText(" DATOS DE LA ORDEN DE PAGO, RESUMEN SELECCCIONADA(S)");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, 630, -1));

        btnGuardar.setBackground(new java.awt.Color(153, 153, 0));
        btnGuardar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnGuardar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnGuardarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnGuardarFocusLost(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 640, 130, 40));

        txaConceptoCau.setEditable(false);
        txaConceptoCau.setColumns(20);
        txaConceptoCau.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txaConceptoCau.setLineWrap(true);
        txaConceptoCau.setRows(5);
        txaConceptoCau.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txaConceptoCau.setEnabled(false);
        txaConceptoCau.setFocusable(false);
        jScrollPane3.setViewportView(txaConceptoCau);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 430, 50));

        btnAdd.setText("Añadir");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(466, 345, -1, -1));

        btnClear.setText("Borrar");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        getContentPane().add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 345, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CONCEPTO DE LA PRESENTE ORDEN");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 410, -1));

        txaConceptoOrden.setColumns(20);
        txaConceptoOrden.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txaConceptoOrden.setLineWrap(true);
        txaConceptoOrden.setRows(5);
        txaConceptoOrden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txaConceptoOrdenKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(txaConceptoOrden);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(466, 370, 660, 50));

        jLabel23.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel23.setText("ORDENES POR PAGAR");
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 170, -1));

        jLabel24.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("CONCEPTO DEL CAUSADO SELECCIONADO");
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 380, -1));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        tblMaster.setForeground(new java.awt.Color(0, 51, 102));
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sel", "Num. Cau.", "Fecha Cau.", "Beneficiario", "RIF", "Total Bs.", "Resta Bs.", "RegCau"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(20);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(50);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(50);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(120);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(150);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(6).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(6).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(7).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 1110, 170));

        btnEditarUltCheque.setBackground(new java.awt.Color(153, 153, 0));
        btnEditarUltCheque.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnEditarUltCheque.setText(" EDIT. ULT. CH. ");
        btnEditarUltCheque.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnEditarUltCheque.setMaximumSize(new java.awt.Dimension(170, 27));
        btnEditarUltCheque.setMinimumSize(new java.awt.Dimension(170, 27));
        btnEditarUltCheque.setPreferredSize(new java.awt.Dimension(170, 27));
        btnEditarUltCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUltChequeActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditarUltCheque, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 640, 150, 40));

        txtConsultar.setBackground(new java.awt.Color(153, 153, 0));
        txtConsultar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtConsultar.setText("CONSULTAR");
        txtConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConsultarActionPerformed(evt);
            }
        });
        getContentPane().add(txtConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 640, 130, 40));

        txtFecha.setBackground(new java.awt.Color(204, 153, 0));
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat(" d' de 'MMMM' de 'yyyy"))));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, 150, 40));

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("FECHA");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, -1, 40));

        btnEditarUltCheque1.setBackground(new java.awt.Color(153, 153, 0));
        btnEditarUltCheque1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnEditarUltCheque1.setText("EDITAR CHEQUE");
        btnEditarUltCheque1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnEditarUltCheque1.setMaximumSize(new java.awt.Dimension(170, 27));
        btnEditarUltCheque1.setMinimumSize(new java.awt.Dimension(170, 27));
        btnEditarUltCheque1.setPreferredSize(new java.awt.Dimension(170, 27));
        btnEditarUltCheque1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUltCheque1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditarUltCheque1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 640, 170, 40));

        chkByCheck.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        chkByCheck.setSelected(true);
        chkByCheck.setText("CHEQUE");
        chkByCheck.setToolTipText("Es utilizado cuando se ha realizado, o se realizara, el pago vía transferencia Bancaria.\nPor tal motivo no se genera nota de debito en la cuenta, ni tampoco la impresión de cheque.");
        chkByCheck.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        chkByCheck.setBorderPainted(true);
        chkByCheck.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkByCheckItemStateChanged(evt);
            }
        });
        getContentPane().add(chkByCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 640, 120, 40));

        btnReImprimirCh.setText("Re");
        btnReImprimirCh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReImprimirChActionPerformed(evt);
            }
        });
        getContentPane().add(btnReImprimirCh, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 140, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular_32x32.png"))); // NOI18N
        jButton1.setToolTipText("Anular Ordenes de Compromiso, sin Causar.");
        jButton1.setMaximumSize(new java.awt.Dimension(55, 41));
        jButton1.setMinimumSize(new java.awt.Dimension(55, 41));
        jButton1.setPreferredSize(new java.awt.Dimension(55, 41));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, -1, -1));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 700));
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Rev 24/10/2016
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
     * Rev 29/10/2016
     *
     * @param evt
     */
    private void btnOrdenDirectaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenDirectaActionPerformed

        // Verificar que se ha selecccionado al menos un registro
        boolean cent = false;
        for (int i = 0; i < tblMaster.getRowCount(); i++) {
            if ((Boolean) tblMaster.getValueAt(i, 0)) {
                cent = true;
                break;
            }
        }

        if (cent) {
            if (JOptionPane.showConfirmDialog(this, "¿Hay registros seleccionados, desea ir a la O. P. Directa ?",
                    "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }
        }

        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagoDirectoSel(me).setVisible(true);
            }
        });

        setVisible(false);
    }//GEN-LAST:event_btnOrdenDirectaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        actGuardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Rev 26/10/2016
     *
     * @return
     * @throws HeadlessException
     */
    private boolean actGuardar() {

        final Map<String, Object> param = new HashMap<>(101);
        if (!valPagoComp(param)) {
            return false;
        }

        // Verificar si el soporte/cheque para la cuenta, en el presente ejercicio fiscal es valido
        try {
            final PreparedStatement pstCheck = Conn.getConnection().prepareStatement("SELECT * FROM bancos_operaciones "
                    + "WHERE ejefis= ? AND cuenta= ? AND tipo_operacion= ? AND soporte_o_cheque= ?");
            pstCheck.setLong(1, Globales.getEjeFisYear()); // ejefis
            pstCheck.setString(2, (String) param.get("cuenta")); // cuenta
            pstCheck.setString(3, chkByCheck.isSelected() ? "CH" : "ND"); // tipo_operacion
            pstCheck.setString(4, (String) param.get("cheque"));

            final ResultSet rs = pstCheck.executeQuery();
            if (rs.next()) {
                throw new Exception("Cheque / Soporte inválido, posiblemente ya se halla utilizado");
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        try {
            Conn.BeginTransaction();

            pagNextNum.nextNum(regCuenta);
            param.put("num_xPag", pagNextNum.getNum_xPag());
            param.put("num_xCuenta", pagNextNum.getNum_xCuenta());
            param.put("id_cuenta", pagNextNum.getId_cuenta());

            genPagoMaster(param, chkByCheck.isSelected());
            genPagoDetail(param);

            pagNextNum.update();
            Conn.EndTransaction();
        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación" + System.getProperty("line.separator") + _ex2);
            }

            JOptionPane.showMessageDialog(this, "Error al tratar de realizar la operación" + System.getProperty("line.separator") + _ex);
            return false;
        }

        JOptionPane.showMessageDialog(this, "Operación realizada");
        ultimo_id_orden_pago = (long) param.get("id_orden_pago");

        try {
            genReport((long) param.get("id_orden_pago"), "ORDEN DE PAGO", true, chkByCheck.isSelected()); // exportar datos, no es transferencia
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
        }

        txtFecha.setText(Format.toStr(new java.sql.Date(Globales.getServerTimeStamp().getTime())));
        clearComp();
        updateMaster();

        return true;
    }

    /**
     * Rev 26/10/2016
     *
     * @param param
     * @return
     */
    private boolean valPagoComp(Map<String, Object> _param) {

        if (cantSel <= 0) {
            return false;
        }

        final String benef_razonsocial = txtBeneficiario.getText();
        if (benef_razonsocial.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Razón Social inválida");
            return false;
        }

        final String benef_rif_ci = txtBenef_rif_ci.getText();
        if (benef_rif_ci.isEmpty()) {
            JOptionPane.showMessageDialog(null, "RIF / C.I. inválida");
            return false;
        }

        final BigDecimal total_bs;
        try {
            total_bs = Format.toBigDec(txtSumaTotalCau.getText());
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(null, "Cantidad Total inválida");
            return false;
        }

        if (total_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Cantidad Total inválida");
            return false;
        }

        BigDecimal apagar_bs;
        try {
            apagar_bs = Format.toBigDec(txtA_Pagar.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Cantidad A Pagar inválida");
            return false;
        }

        if ((apagar_bs.compareTo(BigDecimal.ZERO) <= 0) || (apagar_bs.compareTo(total_bs) > 0)) {
            JOptionPane.showMessageDialog(null, "Cantidad A Pagar inválida");
            return false;
        }

        BigDecimal resta_bs;
        try {
            resta_bs = Format.toBigDec(txtResta.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Cantidad Resta inválida");
            return false;
        }

        if ((resta_bs.compareTo(BigDecimal.ZERO) < 0) || (resta_bs.compareTo(total_bs) > 0)) {
            JOptionPane.showMessageDialog(null, "Cantidad Resta inválida");
            return false;
        }

        if (cmbCuentas.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un número de cuenta");
            cmbCuentas.requestFocusInWindow();
            return false;
        }

        final String banco = txtBanco.getText();
        if (banco.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un número de cuenta");
            cmbCuentas.requestFocusInWindow();
            return false;
        }

        final String cuenta = txtCuenta.getText();
        if (cuenta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un número de cuenta");
            cmbCuentas.requestFocusInWindow();
            return false;
        }

        final String cheque = txtCheque.getText().trim().toUpperCase();
        if (cheque.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe indicar un número de cheque");
            txtCheque.requestFocusInWindow();
            return false;
        }

        // Verificar el saldo de la cuanta con el monto a pagar
        try {
            if (BancosContabilidad.getSaldoF(cuenta, Globales.getEjeFisYear(), 12).compareTo(apagar_bs) < 0) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente");
                cmbCuentas.requestFocusInWindow();
                return false;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            cmbCuentas.requestFocusInWindow();
            return false;
        }

        final String auxConcepto = txaConceptoOrden.getText().trim().toUpperCase();
        if (auxConcepto.isEmpty()) {
            txaConceptoOrden.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Concepto inválido");
            return false;
        }
        final String observacion = auxConcepto.substring(0, min(512, auxConcepto.length()));

        final BigDecimal iva_ret_bs;
        try {
            iva_ret_bs = Format.toBigDec(txtIvaRetenido.getText());
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(null, "Iva retenido inválido");
            return false;
        }

        if (iva_ret_bs.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Iva retenido inválido");
            return false;
        }

        final BigDecimal islr_ret_bs;
        try {
            islr_ret_bs = Format.toBigDec(txtIslrRetenido.getText());
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(null, "ISLR retenido inválido");
            return false;
        }

        if (islr_ret_bs.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "ISLR retenido inválido");
            return false;
        }

        final BigDecimal otras_ret_bs;
        try {
            txtOtrasRetenciones.commitEdit();
            otras_ret_bs = Format.toBigDec(txtOtrasRetenciones.getText());
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(null, "Otras retenciones inválido");
            return false;
        }

        if (otras_ret_bs.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Otras retenciones inválido");
            return false;
        }

        final BigDecimal iva_bs;
        try {
            iva_bs = Format.toBigDec(txtIva_bs.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "IVA Bs. inválido");
            return false;
        }

        if (iva_bs.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "IVA Bs. inválido");
            return false;
        }

        final java.sql.Date fecha_hoy = new java.sql.Date(Globales.getServerTimeStamp().getTime());

        final java.sql.Date fecha_cheque;
        try {
            fecha_cheque = Format.toDateSql(txtFecha.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Fecha de Cheque inválida");
            txtFecha.requestFocusInWindow();
            return false;
        }

        final double porcDistrib;

        // Verificar que el monto a pagar y el resta son iguales, esto se hace con la intencion de
        // minimizar el error por decimales.        
        if (apagar_bs.compareTo(resta_bs) == 0) {
            porcDistrib = 1.00d;
        } else {
            porcDistrib = apagar_bs.doubleValue() / (apagar_bs.doubleValue() + resta_bs.doubleValue()); // el resta, es el nuevo resta
        }

        // A manera de debug se realiza un chequeo del porc de distribucion 0 <= % <= 1.00
        if ((porcDistrib < 0.00) || (porcDistrib > 1.00d)) {
            JOptionPane.showMessageDialog(null, "Error en el porcentaje de distribución= " + porcDistrib);
            txtFecha.requestFocusInWindow();
            return false;
        }

        final String endosable_sn = chkEndosable.isSelected() ? "S" : "N";

        _param.put("benef_razonsocial", benef_razonsocial);
        _param.put("benef_rif_ci", benef_rif_ci);
        _param.put("banco", banco);
        _param.put("cuenta", cuenta);
        _param.put("cheque", cheque);
        _param.put("total_bs", total_bs);
        _param.put("a_pagar_bs", apagar_bs);
        _param.put("resta_bs", resta_bs);
        _param.put("iva_retenido_bs", iva_ret_bs);
        _param.put("islr_retenido_bs", islr_ret_bs);
        _param.put("otras_ret_bs", otras_ret_bs);
        _param.put("iva_bs", iva_bs);
        _param.put("fecha_hoy", fecha_hoy);
        _param.put("observacion", observacion);
        _param.put("fecha_cheque", fecha_cheque);
        _param.put("porcDistrib", porcDistrib);
        _param.put("endosable_sn", endosable_sn);
        _param.put("regCuenta", regCuenta);

        return true;
    }

    /**
     * Rev 26/10/2016
     *
     * @param _param
     * @param _isByCheck si el pago se realizo por otro medio
     * @throws java.lang.Exception
     */
    public static void genPagoMaster(Map<String, Object> _param, final boolean _isByCheck) throws Exception {

        final BigDecimal a_pagar_bs = (BigDecimal) _param.get("a_pagar_bs");
        final String cuenta = (String) _param.get("cuenta");

        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO orden_pago "
                + "(id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4
                + "num_x_pag, num_x_cuenta, id_cuenta, " // 5, 6, 7,
                + "benef_razonsocial, benef_rif_ci, fecha_pago, observacion, " // 8, 9, 10, 11, 
                + "banco, cuenta, cheque, fecha_cheque, endosable_sn, " // 12, 13, 14, 15, 16,
                + "total_bs, iva_bs, apagar_bs, " // 17, 18, 19,
                + "resta_bs, iva_ret_bs, islr_ret_bs, otras_ret_bs) " // 20, 21, 22, 23
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pst.setLong(1, UserPassIn.getIdUser());
        pst.setLong(2, UserPassIn.getIdSession());
        pst.setTimestamp(3, UserPassIn.getDateSession()); // date_session
        pst.setDate(4, Globales.getEjefis());
        pst.setLong(5, (long) _param.get("num_xPag"));
        pst.setLong(6, (long) _param.get("num_xCuenta"));
        pst.setLong(7, (long) _param.get("id_cuenta"));
        pst.setString(8, (String) _param.get("benef_razonsocial"));
        pst.setString(9, (String) _param.get("benef_rif_ci")); // rifcli
        pst.setDate(10, (Date) _param.get("fecha_hoy")); // fecha
        pst.setString(11, (String) _param.get("observacion")); // observacion
        pst.setString(12, (String) _param.get("banco")); // banco
        pst.setString(13, cuenta); // cuenta
        pst.setString(14, (String) _param.get("cheque")); // cheque
        pst.setDate(15, (Date) _param.get("fecha_cheque")); // fecha_cheque
        pst.setString(16, (String) _param.get("endosable_sn")); // cheque
        pst.setBigDecimal(17, (BigDecimal) _param.get("total_bs"));
        pst.setBigDecimal(18, (BigDecimal) _param.get("iva_bs"));
        pst.setBigDecimal(19, a_pagar_bs);
        pst.setBigDecimal(20, (BigDecimal) _param.get("resta_bs"));
        pst.setBigDecimal(21, (BigDecimal) _param.get("iva_retenido_bs"));
        pst.setBigDecimal(22, (BigDecimal) _param.get("islr_retenido_bs"));
        pst.setBigDecimal(23, (BigDecimal) _param.get("otras_ret_bs"));
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error al insertar el registro de pago");
        }

        // Recobrar el numero de pago
        // Toma el ultimo id generado
        final long id;
        final ResultSet rsId = pst.getGeneratedKeys();
        if (rsId.next()) {
            id = rsId.getLong(1);
        } else {
            throw new Exception("Error al tratar de recupera el ID");
        }

        _param.put("id_orden_pago", id);

        // Verificar si es un pago con o sin registro bancario, o si es una Transferencia interna, que ya fue realizada
        if (!cuenta.isEmpty()) {
            final Calendar fechaOp = Calendar.getInstance();
            final long ejeFis = Globales.getEjeFisYear();
            final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;

            final PreparedStatement pst2 = Conn.getConnection().prepareStatement("INSERT INTO bancos_operaciones"
                    + "(cuenta, banco, descripcion, fecha, " // 1, 2, 3, 4
                    + "tipo_operacion, soporte_o_cheque, conciliado, " // 5, 6, 7
                    + "debe, haber, cuenta_numop, cuenta_numop_tipo, num_pag_ava, " // 8, 9, 10, 11, 12
                    + "ejefis, ejefismes, iduser) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst2.setString(1, cuenta);
            pst2.setString(2, (String) _param.get("banco"));
            pst2.setString(3, "OP" + "-" + id + " - " + _param.get("benef_razonsocial"));
            pst2.setString(4, Format.toStr((java.sql.Date) _param.get("fecha_cheque")));
            pst2.setString(5, _isByCheck ? "CH" : "ND"); // tipo_operacion
            pst2.setString(6, (String) _param.get("cheque"));
            pst2.setString(7, _isByCheck ? "EN TRANSITO" : ""); // conciliado
            pst2.setDouble(8, 0.00d); // debe
            pst2.setDouble(9, a_pagar_bs.doubleValue()); // haber
            pst2.setLong(10, ModelMapNextOp.getNext(Globales.getEjeFisYear(), cuenta)); // cuenta_numop
            pst2.setLong(11, ModelMapNextOp.getNext(Globales.getEjeFisYear(), cuenta + "-" + (_isByCheck ? "CH" : "ND"))); // cuenta_numop_tipo
            pst2.setLong(12, id); // num_pag_ava
            pst2.setLong(13, ejeFis);
            pst2.setLong(14, ejeFisMes);
            pst2.setLong(15, UserPassIn.getIdUser());
            if (pst2.executeUpdate() != 1) {
                throw new Exception("Error al insertar registro en Banco");
            }

        }
    }

    /**
     * Rev 26/10/2016
     *
     * @param _param
     */
    private void genPagoDetail(Map<String, Object> _param) throws Exception {

        final long id_orden_pago = (long) _param.get("id_orden_pago");
        final BigDecimal a_pagar_bs = (BigDecimal) _param.get("a_pagar_bs");

        // La idea es distribuir equitativamente el pago entre los causados seleccionados
        final double porcDistrib = (double) _param.get("porcDistrib");

        // Utilizado para llevar la cuenta de lo que se ha ido pagando
        BigDecimal acumAPagar = BigDecimal.ZERO;

        // Recorrer todos los causados de la Tabla, para ir actualizando los pagos, proporcionalmente
        for (int row = 0; row < tblMaster.getRowCount(); row++) {

            // Rechazar si no esta seleccionado
            if (!(boolean) tblMaster.getValueAt(row, COL_SEL)) {
                continue;
            }

            // Tomar el causado seleccionado
            final CauModel regCau = (CauModel) tblMaster.getValueAt(row, COL_REG);

            // Esta linea esta un poco de mas, pero se deja a manera de debug, ya que el resta nunca sera <= 0
            if (regCau.getResta_bs().compareTo(BigDecimal.ZERO) > 0.00d) {

                // Si es la ultima fila, se actualiza el monto que resta, con el resto de lo acumulado,
                // esto para mantener contenido el error de las sucesivas multiplicaciones
                final BigDecimal aPagarCau;
                if (row == tblMaster.getRowCount() - 1) {
                    aPagarCau = a_pagar_bs.subtract(acumAPagar).setScale(2, HALF_UP);
                } else {
                    aPagarCau = BigDecimal.valueOf(regCau.getResta_bs().doubleValue() * porcDistrib).setScale(2, HALF_UP);
                    acumAPagar = acumAPagar.add(aPagarCau);
                }

                setAPagarCau(regCau.getId_causado(), aPagarCau);

                final PreparedStatement pstCauPagHist = Conn.getConnection().prepareStatement("INSERT INTO causado_orden_pago"
                        + "(id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4
                        + "id_causado, id_orden_pago, apagar_bs) " // 5, 6, 7
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                pstCauPagHist.setLong(1, UserPassIn.getIdUser());
                pstCauPagHist.setLong(2, UserPassIn.getIdSession());
                pstCauPagHist.setTimestamp(3, UserPassIn.getDateSession()); // date_session
                pstCauPagHist.setDate(4, Globales.getEjefis());
                pstCauPagHist.setLong(5, regCau.getId_causado());
                pstCauPagHist.setLong(6, id_orden_pago);
                pstCauPagHist.setBigDecimal(7, aPagarCau);
                if (pstCauPagHist.executeUpdate() != 1) {
                    throw new Exception("Error al insertar el registro");
                }

            } else {
                throw new Exception("Error en el registro Causado, resta_bs= " + Format.toStr(regCau.getResta_bs()));
            }

        } // for (int ifila = 0; ifila < tblMaestro.getRowCount(); ifila++) {

    }

    private void txtConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConsultarActionPerformed

        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagoConsultar(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_txtConsultarActionPerformed

    private void btnAvanceEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanceEfectivoActionPerformed
        JFrame me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FondoAvanceSinImp(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnAvanceEfectivoActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        txaConceptoOrden.append(txaConceptoCau.getText() + System.getProperty("line.separator"));
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txaConceptoOrden.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void txaConceptoOrdenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaConceptoOrdenKeyTyped
        if (txaConceptoOrden.getText().length() >= 500) {
            evt.consume();
        }
    }//GEN-LAST:event_txaConceptoOrdenKeyTyped

    private void txtChequeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChequeKeyTyped
        if (txtCheque.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtChequeKeyTyped

    private void btnEditarUltChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUltChequeActionPerformed
        if (ultimo_id_orden_pago <= 0) {
            return;
        }

        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BancosChequePagoCambiar(me, ultimo_id_orden_pago).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnEditarUltChequeActionPerformed

    private void cmbCuentasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCuentasItemStateChanged

        if (cmbCuentas.getSelectedIndex() < 0) {
            txtBanco.setText("");
            txtCuenta.setText("");
            txtCheque.setText("");
            txtSaldoCuenta.setText("0,00");
            return;
        }

        final String Mensaje = (String) cmbCuentas.getSelectedItem();
        final String cuenta = Mensaje.substring(0, 20);
        txtCuenta.setText(cuenta);
        txtBanco.setText(Mensaje.substring(21));
        txtBanco.setCaretPosition(0);

        double saldo = 0;
        try {
            saldo = BancosContabilidad.getSaldoF(cuenta, Globales.getEjeFisYear(), 12).doubleValue();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + _ex);
        }
        txtSaldoCuenta.setText(Format.toStr(saldo));

        final double total;
        try {
            total = Format.toDouble(txtSumaTotalCau.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
            txtSumaTotalCau.requestFocusInWindow();
            return;
        }

        final double apagar;
        try {
            apagar = Format.toDouble(txtA_Pagar.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
            txtA_Pagar.requestFocusInWindow();
            return;
        }

        if (Globales.ORD_PAG == OrdPagIdTipo.ABSOLUTO) {
            if ((total > 0) && (apagar <= 0)) {
                JOptionPane.showMessageDialog(this, "Monto a pagar inválido");
                txtA_Pagar.requestFocusInWindow();
                return;
            }

            if (saldo < apagar) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente");
                cargarComboCuentas(cmbCuentas, regCuenta);
                return;
            }
        }

        txtCheque.requestFocusInWindow();

        try {
            regCuenta = CuentaModel.getxCuenta(cuenta);
        } catch (Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar el registro Cuenta" + System.getProperty("line.separator") + _ex);
            regCuenta = null;
        }

    }//GEN-LAST:event_cmbCuentasItemStateChanged

    private void btnEditarUltCheque1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUltCheque1ActionPerformed

        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChequeCambiar(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnEditarUltCheque1ActionPerformed

    private void txtOtrasRetencionesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOtrasRetencionesKeyTyped
        if (!"0123456789,".contains(String.valueOf(evt.getKeyChar())) || txtOtrasRetenciones.getText().length() > 16) {
            evt.consume();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                calcularTotalRetencion();
            }
        });
    }//GEN-LAST:event_txtOtrasRetencionesKeyTyped

    private void txtOtrasRetencionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOtrasRetencionesActionPerformed
        try {
            txtOtrasRetenciones.commitEdit();
        } catch (ParseException _ex) {
            JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + "Valor de otra retención inválido");
        }
    }//GEN-LAST:event_txtOtrasRetencionesActionPerformed

    private void chkByCheckItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkByCheckItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    txtCheque.setText("");
                }
            });
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null, "Orden de pago: " + System.getProperty("line.separator") + "Sin Cheque");
                    txtCheque.setText("N.D. XXX");
                }
            });
        }
    }//GEN-LAST:event_chkByCheckItemStateChanged

    private void btnReImprimirChActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReImprimirChActionPerformed

        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChequeReImprimir(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnReImprimirChActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (JOptionPane.showConfirmDialog(this, "La operación de anulación de Pago es irreversible. ¿ Desea continuar ?",
                "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {

            java.awt.Window me = this;
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new PagoAnular(me).setVisible(true);
                    setVisible(false);
                }
            });
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChequeActionPerformed
        btnGuardar.requestFocusInWindow();
    }//GEN-LAST:event_txtChequeActionPerformed

    private void btnGuardarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnGuardarFocusGained
        getRootPane().setDefaultButton(btnGuardar);
    }//GEN-LAST:event_btnGuardarFocusGained

    private void btnGuardarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnGuardarFocusLost
        getRootPane().setDefaultButton(null);
    }//GEN-LAST:event_btnGuardarFocusLost

    /**
     * Rev 25/10/2016
     *
     * @param _id_orden_pago
     * @param _titulo
     * @return
     * @throws java.lang.Exception
     */
    public static Map<String, Object> genParamRptPag(final long _id_orden_pago, final String _titulo) throws Exception {
        if (_id_orden_pago <= 0) {
            throw new Exception("Número de Orden inválido");
        }

        StringBuilder listCau = null;
        StringBuilder listCompr = null;

        final PagoModel regPag;
        final long numPag;

        final ResultSet rsPag = Conn.executeQuery("SELECT * FROM orden_pago WHERE id_orden_pago= " + _id_orden_pago);
        if (rsPag.next()) {

            regPag = new PagoModel(rsPag);

            if (Globales.ORD_PAG == OrdPagIdTipo.ABSOLUTO) {
                numPag = regPag.getNum_x_Pag();
            } else {
                numPag = regPag.getNum_x_Cuenta();
            }

            // Hallar todos los causados de la orden                        
            final ResultSet rsCauPag = Conn.executeQuery("SELECT * FROM causado_orden_pago "
                    + "WHERE id_orden_pago= " + _id_orden_pago + " AND anulado_sn= 'N'");
            while (rsCauPag.next()) {
                long id_causado = rsCauPag.getLong("id_causado");

                if (listCau == null) {
                    listCau = new StringBuilder(String.valueOf(id_causado));
                } else {
                    listCau.append(", ").append(id_causado);
                }

                final TipoCompr tipoCompr;
                final ResultSet rsCau = Conn.executeQuery("SELECT * FROM causado WHERE id_causado= " + id_causado);
                if (rsCau.next()) {
                    tipoCompr = TipoCompr.valueOf(rsCau.getString("tipo_compr"));
                } else {
                    throw new Exception("Causado no encontrado");
                }

                // Hallar los compromisos asociados al causado
                final ResultSet rsComprs = Conn.executeQuery("SELECT * FROM " + tipoCompr.getTbl() + " WHERE id_causado=" + id_causado);
                while (rsComprs.next()) {
                    if (listCompr == null) {
                        listCompr = new StringBuilder(tipoCompr.name());
                        listCompr.append("-").append(rsComprs.getLong("id_compr"));
                    } else {
                        listCompr.append(", ").append(tipoCompr).append("-").append(rsComprs.getLong("id_compr"));
                    }
                } // while (rsComprs.next()) 

            } // while (rsCau.next()) {
        } else {
            throw new Exception("Número de orden inválida, no encontrada");
        }

        final String pagosAnteriores = Format.toStr(regPag.getTotal_bs().doubleValue() - regPag.getResta_bs().doubleValue() - regPag.getA_pagar_bs().doubleValue() - regPag.getIslr_ret_bs().doubleValue() - regPag.getIva_ret_bs().doubleValue() - regPag.getOtras_ret_bs().doubleValue());

        final Map<String, Object> param = new HashMap<>(101);
        param.put("numPag", numPag);
        param.put("numcaus", listCau == null ? "" : listCau.toString());
        param.put("numcomprs", listCompr == null ? "" : listCompr.toString());
        param.put("razonSocial", regPag.getBenef_razonsocial());
        param.put("rif", regPag.getBenef_rif_ci());
        param.put("montoPagado", Format.toStr(pagosAnteriores));
        param.put("montoPagar", Format.toStr(regPag.getA_pagar_bs()));
        param.put("montoPagarBigDec", regPag.getA_pagar_bs());
        param.put("banco", regPag.getBanco());
        param.put("cuenta", regPag.getCuenta());
        param.put("cheque", regPag.getCheque());
        param.put("endosable", regPag.getEndosable_sn().equals("S") ? "" : "NO ENDOSABLE");
        param.put("ivaRet", regPag.getIva_ret_bs());
        param.put("islr_ret_bs", regPag.getIslr_ret_bs());
        param.put("otras_ret_bs", regPag.getOtras_ret_bs());
        param.put("ivaTotal", Format.toStr(regPag.getIva_bs()));
        param.put("total_bs", regPag.getTotal_bs());
        param.put("montoTotal", Format.toStr(regPag.getTotal_bs()));
        param.put("resta", Format.toStr(regPag.getResta_bs()));
        param.put("fechaHoy", Format.toStr(regPag.getFecha_pago()));
        param.put("fechaPago", regPag.getFecha_pago());

        final String NumLiteral = Num2Let.convert(regPag.getA_pagar_bs());
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

        param.put("concepto", regPag.getObservacion());
        param.put("titulo", _titulo); // "ORDEN DE PAGO"); 
        param.put("iduser", UserPassIn.getIdUser());
        param.put("idsession", UserPassIn.getIdSession());
        param.put("user", UserPassIn.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(UserPassIn.getIdUser()));
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("ciudad", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);
        param.put("ejefis", Globales.getEjeFisYear());
        param.put("anulado", regPag.getAnulado_sn().equals("S") ? "ANULADO" : "");

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

        param.put("logo_iz", FrmPrincipal.getInstance().getClass().getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", FrmPrincipal.getInstance().getClass().getResourceAsStream("/imagenes/logo_der.png"));

        return param;
    }

    /**
     * Rev 25/10/2016
     *
     * @param _id_avance_efectivo
     * @param _titulo
     * @return
     * @throws java.lang.Exception
     */
    public static Map<String, Object> genParamRptPagAvaEfe(final long _id_avance_efectivo, final String _titulo) throws Exception {
        if (_id_avance_efectivo <= 0) {
            throw new Exception("Número de orden inválido");
        }

        StringBuilder listCau = null;
        StringBuilder listCompr = null;

        AvaEfeModel regAvaEfe;
        final ResultSet rsPag = Conn.executeQuery("SELECT * FROM avance_efectivo WHERE id= " + _id_avance_efectivo);
        if (rsPag.next()) {

            regAvaEfe = new AvaEfeModel(rsPag);

            // Hallar todos los causados de la orden                        
            final ResultSet rsCauAva = Conn.executeQuery("SELECT * FROM causado_avance_efectivo_nn WHERE id_avance_efectivo= " + _id_avance_efectivo);
            while (rsCauAva.next()) {
                long id_causado = rsCauAva.getLong("id_causado");

                if (listCau == null) {
                    listCau = new StringBuilder(String.valueOf(id_causado));
                } else {
                    listCau.append(", ").append(id_causado);
                }

                final TipoCompr tipoCompr;
                final ResultSet rsCau = Conn.executeQuery("SELECT * FROM causado WHERE id_causado= " + id_causado);
                if (rsCau.next()) {
                    tipoCompr = TipoCompr.valueOf(rsCau.getString("tipo_compr"));
                } else {
                    throw new Exception("Causado no encontrado");
                }

                // Hallar los compromisos asociados al causado
                final ResultSet rsComprs = Conn.executeQuery("SELECT * FROM " + tipoCompr.getTbl() + " WHERE id_causado=" + id_causado);
                while (rsComprs.next()) {
                    if (listCompr == null) {
                        listCompr = new StringBuilder(tipoCompr.name());
                        listCompr.append("-").append(rsComprs.getLong("id_compr"));
                    } else {
                        listCompr.append(", ").append(tipoCompr).append("-").append(rsComprs.getLong("id_compr"));
                    }
                } // while (rsComprs.next()) 

            } // while (rsCau.next()) {
        } else {
            throw new Exception("Número de orden inválida, no encontrada");
        }

        final Map<String, Object> param = new HashMap<>(101);
        param.put("numPag", regAvaEfe.getId());
        param.put("numcaus", listCau == null ? "" : listCau.toString());
        param.put("numcomprs", listCompr == null ? "" : listCompr.toString());
        param.put("razonSocial", regAvaEfe.getBenef_razonsocial());
        param.put("rif", regAvaEfe.getBenef_rif_ci());
        param.put("montoPagado", "0,00");
        param.put("montoPagar", Format.toStr(regAvaEfe.getA_pagar_bs()));
        param.put("banco", regAvaEfe.getBanco());
        param.put("cuenta", regAvaEfe.getCuenta());
        param.put("cheque", regAvaEfe.getCheque());
        param.put("endosable", "");
        param.put("ivaRet", BigDecimal.ZERO);
        param.put("islr_ret_bs", BigDecimal.ZERO);
        param.put("otras_ret_bs", BigDecimal.ZERO);
        param.put("ivaTotal", BigDecimal.ZERO);
        param.put("total_bs", regAvaEfe.getA_pagar_bs());
        param.put("montoTotal", regAvaEfe.getA_pagar_bs());
        param.put("resta", BigDecimal.ZERO);
        param.put("fechaHoy", Format.toStr(regAvaEfe.getFecha_pago()));

        final String NumLiteral = Num2Let.convert(regAvaEfe.getA_pagar_bs());
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

        param.put("concepto", regAvaEfe.getObservacion());
        param.put("titulo", _titulo); // "ORDEN DE PAGO"); 
        param.put("iduser", UserPassIn.getIdUser());
        param.put("idsession", UserPassIn.getIdSession());
        param.put("user", UserPassIn.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(UserPassIn.getIdUser()));
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("ciudad", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);
        param.put("ejefis", Globales.getEjeFisYear());

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

        return param;
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
                new PagoOrden(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAvanceEfectivo;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEditarUltCheque;
    private javax.swing.JButton btnEditarUltCheque1;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnOrdenDirecta;
    private javax.swing.JButton btnReImprimirCh;
    private javax.swing.JCheckBox chkByCheck;
    private javax.swing.JCheckBox chkEndosable;
    private javax.swing.JComboBox<String> cmbCuentas;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JPanel pnlBanco;
    private javax.swing.JPanel pnlRetencion;
    private javax.swing.JPanel pnlTotal;
    private javax.swing.JTable tblMaster;
    private javax.swing.JTextArea txaComprFact;
    private javax.swing.JTextArea txaConceptoCau;
    private javax.swing.JTextArea txaConceptoOrden;
    private javax.swing.JFormattedTextField txtA_Pagar;
    private javax.swing.JTextField txtBanco;
    private javax.swing.JTextField txtBenef_rif_ci;
    private javax.swing.JTextField txtBeneficiario;
    private javax.swing.JTextField txtCheque;
    private javax.swing.JButton txtConsultar;
    private javax.swing.JTextField txtCuenta;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JFormattedTextField txtIslrRetenido;
    private javax.swing.JFormattedTextField txtIvaRetenido;
    private javax.swing.JFormattedTextField txtIva_bs;
    private javax.swing.JFormattedTextField txtOrdPago;
    private javax.swing.JFormattedTextField txtOtrasRetenciones;
    private javax.swing.JFormattedTextField txtPagado;
    private javax.swing.JFormattedTextField txtResta;
    private javax.swing.JFormattedTextField txtSaldoCuenta;
    private javax.swing.JFormattedTextField txtSumaTotalCau;
    private javax.swing.JFormattedTextField txtSumaTotalCau_menosRet;
    private javax.swing.JFormattedTextField txtTotalRet;
    // End of variables declaration//GEN-END:variables

    private static final Connection cn = Conn.getConnection();

    public static void genReport(final long _id, final String _titulo, final boolean _export, final boolean isByCheck) throws Exception {
        rptOrdPag(_id, _titulo, _export);
        if (isByCheck) {
            rptChPag(_id, _titulo, _export);
        }
    }

    public static void genReportSinImp(final long _id, final String _titulo, final boolean _export, final boolean _byCheck) throws Exception {
        rptOrdPagSinImp(_id, _titulo, _export);
        if (_byCheck) {
            rptChPag(_id, _titulo, _export);
        }
    }

    /**
     * Rev 26-02-2017
     *
     * @param _regCuenta
     */
    @Override
    public void setCuenta(final CuentaModel _regCuenta) {
        if (_regCuenta == null) {
            actSalir();
            return;
        }

        regCuenta = _regCuenta;

        if (Globales.ORD_PAG == OrdPagIdTipo.RELATIVO) {
            pagNextNum = new GenNum_xCuenta();
        }

        txtFecha.setText(Format.toStr(new java.sql.Date(Globales.getServerTimeStamp().getTime())));
        clearComp();
        updateMaster();
    }

    @Override
    public CuentaModel getCuenta() {
        return regCuenta;
    }
}
