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
import static capipsistema.Globales.TBL_PPTO_ADICIONAL;
import static capipsistema.Globales.TBL_PPTO_EGRESO;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import modelos.PptoModel;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import static presupuesto.WinState.ADD;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;

/**
 *
 * @author
 */
@SuppressWarnings("serial")
public final class CreditoAdicional extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del codigo programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colCodigo = 0;
    private final int colPartida = 1;
    private final int colMonto = 2;
    private final int colReg = 3;

    /**
     * Almacena el id del ultimo credito generado
     */
    long idCreAdi;

    /**
     *
     */
    int contActPart;

    /**
     *
     * @param _parent
     */
    public CreditoAdicional(final JFrame _parent) {
        super(_parent);
        initComponents();

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
        param.put(JBRETURN, jbRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, jbResetear);
        param.put(JTABLE, tblMaster);
        ConfigComponents(param);
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        InitConditions();
        UpdatePanels();
        UpdateEnabledAndFocus();
    }

    /**
     * Valores y/o condiciones iniciales de la ventana
     */
    @Override
    public void InitConditions() {
        super.InitConditions();
        winState = WinState.MODIFY;
        txtSoporte.requestFocusInWindow();

        try {
            ConnPpto.execute("LOCK TABLES " + TBL_PPTO_ADICIONAL + " WRITE");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error, otra aplicación ha bloqueado la Tabla de Presupuesto" + System.getProperty("line.separator") + _ex);
        }

        Boolean centReCrear;
        try {
            final ResultSet rs = ConnPpto.executeQuery("SELECT SUM(monto) FROM " + TBL_PPTO_ADICIONAL);
            if (rs.next()) {
                if (rs.getInt("SUM(monto)") > 0) {
                    centReCrear = JOptionPane.showConfirmDialog(this, "Hay datos guardados. ¿ Desea cargarlos ?",
                        "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION;
                } else {
                    centReCrear = true;
                }

            } else {
                centReCrear = true;
            }

        } catch (final Exception _ex) {
            centReCrear = true;
        }

        if (centReCrear) {
            try {
                ReCrear();
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(this, _ex);
            }
        }

        contActPart = 0;
        txtSoporte.requestFocusInWindow();
    }

    /**
     * Crear tabla temporal para el credito adicional Rev 25/11/2016
     *
     */
    private void ReCrear() throws Exception {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            ConnPpto.execute("DROP TABLE IF EXISTS " + TBL_PPTO_ADICIONAL);
            ConnPpto.execute("CREATE TABLE " + TBL_PPTO_ADICIONAL + " LIKE " + TBL_PPTO_EGRESO);
            ConnPpto.executeUpdate("INSERT INTO " + TBL_PPTO_ADICIONAL + " SELECT * FROM " + TBL_PPTO_EGRESO + " WHERE ejefis= " + Globales.getEjeFisYear() + " AND anulado_sn= 'N'");
            ConnPpto.executeUpdate("UPDATE " + TBL_PPTO_ADICIONAL + " SET monto_ini= '0.00', monto= '0.00'");
        } catch (final Exception _ex) {
            throw _ex;
        } finally {
            // independientemente del error o no, se restaura el cursor
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Rev 25/11/2016
     *
     * @param _param
     */
    @Override
    protected void ConfigComponents(final Map<String, Object> _param) {
        super.ConfigComponents(_param);

        // Configurar el formato de la fecha
        txtFechaCred.setFormatterFactory(PptoGlobales.getDateFormatterFactory());

        // Configurar el campo de Monto del Credito, para que acepte y maneje BigDecimal        
        txtMontoCred.setFormatterFactory(PptoGlobales.getCurFormatterFactory());

        // Configurar el campo de Monto, para que acepte y maneje BigDecimal
        txtMontoPartida.setFormatterFactory(PptoGlobales.getCurFormatterFactory());

        // Configurar la JTable de los Beneficiarios, para manejar el ENTER
//        final InputMap imBenef = tblMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//        final ActionMap amBenef = tblMaster.getActionMap();
//
//        final KeyStroke enterKeyBenef = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
//
//        imBenef.put(enterKeyBenef, "Action.enter");
//        amBenef.put("Action.enter", new AbstractAction() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                btnGuardar.doClick();
//            }
//        });
        final InputMap imMonto = txtMontoPartida.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap amMonto = txtMontoPartida.getActionMap();

        final KeyStroke enterKeyMonto = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        imMonto.put(enterKeyMonto, "Action.enter");
        amMonto.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                btnGuardar.doClick();
            }
        });

        // Formatear la vista de las columnas
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());

    }

    /**
     * Rev 25/11/2016
     *
     */
    @Override
    protected void UpdatePanels() {
        UpdateJTable();
        UpdatePanelDetails();
//        UpdatePanelDatosCredito();
    }

    /**
     * Actualizar la vista de la ventana. Deberia llamarse cuando se halla modificado la tabla Rev 04/02/2016
     *
     */
    @Override
    protected void UpdateJTable() {

        // Registrar la fila seleccionada
        int lastRowSel = tblMaster.getSelectedRow();

        // Para evitar multiples llamadas al metodo valueChanged
        tblMaster.clearSelection();

        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        BigDecimal total = BigDecimal.ZERO;
        final Object[] datos = new Object[4];
        try {

            // Generar el comando SQL
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + TBL_PPTO_ADICIONAL + " WHERE anulado_sn= 'N' ORDER BY codigo ASC");
            while (rs.next()) {
                PptoModel aux = new PptoModel(rs);
                datos[colCodigo] = aux.getCodigo();
                datos[colPartida] = aux.getPartida();
                datos[colMonto] = aux.getMonto();
                datos[colReg] = aux;
                model.addRow(datos);

                // Calcular el total
                total = total.add(aux.getMonto());
            }

            // Por alguna razon, si no se coloca la siguiente linea, la tabla queda bloqueada, 
            // y posteriormente se generan mensajes de error, al acceder a la base de datos
            Conn.getConnection().createStatement().execute("UNLOCK TABLES");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        // Reposicionar el registro seleccionado
        // Depende del estado
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

        // Actualizar el campo total
        try {
            txtTotalAcumPartidas.setText(PptoGlobales.curFormat.valueToString(total));
        } catch (final Exception _ex) {
            txtTotalAcumPartidas.setText("Error");
        }
    }

    @Override
    protected void UpdatePanelDetails() {

        if (tblMaster.getRowCount() < 0) {
            ClearDetails();
            return;
        }

        int selectedRow = tblMaster.getSelectedRow();
        if ((selectedRow < 0) || (winState == ADD)) {
            ClearDetails();
            return;
        }

        txtCodigo.setValue(tblMaster.getValueAt(selectedRow, colCodigo));
        txtPartida.setText(tblMaster.getValueAt(selectedRow, colPartida).toString());
        txtPartida.setCaretPosition(0);
        txtMontoPartida.setValue(tblMaster.getValueAt(selectedRow, colMonto));

        txtMontoPartida.setValue(tblMaster.getValueAt(selectedRow, colMonto));

        contActPart++;
        if (contActPart > 2) {
            txtMontoPartida.requestFocusInWindow();
        }

    }

    @Override
    protected void ClearDetails() {
        txtCodigo.setText(PptoGlobales.strCodEgresoZero);
        txtPartida.setText("");
        txtMontoPartida.setText("0,00");
    }

    protected void ClearDatosCredito() {
        txtSoporte.setText("");
        jtDesc.setText("");
        txtMontoCred.setText("0,00");
        txtMontoCred.setValue(ZERO);
        txtFechaCred.setText("");
    }

    /**
     * Rev 25/11/2016
     *
     */
    protected void UpdateEnabledAndFocus() {

        switch (winState) {

            case MODIFY:
                lblMensaje.setText("Guardar cambios, o Cancelar para salir de Edición");
                btnGuardar.setText("GUARDAR");

                txtSoporte.setEnabled(true);
                jtDesc.setEnabled(true);
                txtMontoCred.setEnabled(true);
                txtFechaCred.setEnabled(true);

                txtMontoPartida.setEnabled(true);
                btnGuardar.setEnabled(true);
                jbCancelar.setEnabled(true);

                jbGenerar.setEnabled(true);
                jbConsultarC.setEnabled(true);
                jbRetornar.setEnabled(true);
                btnReIniciar.setEnabled(true);

//                txtMontoPartida.requestFocusInWindow();
                getRootPane().setDefaultButton(null);
                break;
        }
    }

    /**
     * Rev 25/11/2016
     *
     * @param _e
     */
    // Para manejar el doble clic sobre la jtable
    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent _e) {
        btnGuardar.doClick();
    }

    /**
     * Rev 25/11/2016
     *
     * @return
     */
    private boolean Generar() {

        String soporte = txtSoporte.getText().trim().toUpperCase();
        if (soporte.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Soporte inválido");
            txtSoporte.requestFocusInWindow();
            return false;
        }

        String desc = jtDesc.getText().trim().toUpperCase();
        if (desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descripción inválida");
            jtDesc.requestFocusInWindow();
            return false;
        }

        BigDecimal montoC;
        try {
            montoC = (BigDecimal) PptoGlobales.curFormat.stringToValue(txtMontoCred.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto del Crédito inválido");
            txtMontoCred.requestFocusInWindow();
            return false;
        }

        if ((montoC.compareTo(BigDecimal.ZERO) <= 0)) {
            JOptionPane.showMessageDialog(null, "Monto del Crédito inválido");
            txtMontoCred.requestFocusInWindow();
            return false;
        }

        final LocalDate ldFechaCred;
        final java.util.Date fechaCred;

        try {
            ldFechaCred = Format.toLocalDate(txtFechaCred.getText());
            fechaCred = Format.toUtilDate(ldFechaCred);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Fecha inválida");
            txtFechaCred.requestFocusInWindow();
            return false;
        }

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(fechaCred.getTime());
        if (now.get(Calendar.YEAR) != capipsistema.Globales.getEjeFisYear()) {
            JOptionPane.showMessageDialog(null, "Fecha inválida, no corresponde al presente Ejercicio Fiscal");
            txtFechaCred.requestFocusInWindow();
            return false;
        }

        BigDecimal montoAcum = Cur2BigDec.parse(txtTotalAcumPartidas.getText());

        if (montoAcum == null) {
            JOptionPane.showMessageDialog(null, "Monto Total inválido");
            return false;
        }

        // Verificar que no se halla utilizado el total del credito
        if (montoAcum.compareTo(montoC) != 0) {
            JOptionPane.showMessageDialog(null, "El Monto del Crédito debe coincidir con el Monto Acumulado");
            return false;
        }

        final Calendar fechaOp = Calendar.getInstance();
        final long ejeFis = ldFechaCred.getYear();
        final long ejeFisMes = ejeFis * 100 + ldFechaCred.getMonthValue();
        final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());

        // Actualiza la tabla de creditos adicionales, para llevar el historico de los creditos
        try {
            ConnPpto.BeginTransaction();

            // Actualiza las partidas de la tabla de egresos, con los montos del credito adicional
            try {
                StringBuilder sql = new StringBuilder("UPDATE " + TBL_PPTO_ADICIONAL + ", " + TBL_PPTO_EGRESO);
                sql.append(" SET ").append(TBL_PPTO_EGRESO).append(".monto= ").append(TBL_PPTO_EGRESO).append(".monto").append(" + ").append(TBL_PPTO_ADICIONAL).append(".monto");
                sql.append(" WHERE ").append(TBL_PPTO_EGRESO).append(".id = ").append(TBL_PPTO_ADICIONAL).append(".id");
                ConnPpto.executeUpdate(sql.toString());
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, _ex);
                txtMontoPartida.requestFocusInWindow();
                try {
                    ConnPpto.RollBack();
                } catch (final Exception _ex1) {
                    JOptionPane.showMessageDialog(null, _ex1);
                }
                return false;
            }

            final PreparedStatement ps = ConnPpto.conn.prepareStatement("INSERT INTO " + PptoGlobales.TBL_CREDITO_ADICIONAL + "(soporte, descripcion, monto, fecha,ejefis,ejefismes,fchsession,idsession,iduser) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, soporte);
            ps.setString(2, desc);
            ps.setBigDecimal(3, montoC);
            ps.setDate(4, new java.sql.Date(fechaCred.getTime()));
            ps.setLong(5, ejeFis);
            ps.setLong(6, ejeFisMes); // ejefismes
            ps.setDate(7, fchSession);
            ps.setLong(8, UserPassIn.getIdSession());
            ps.setLong(9, UserPassIn.getIdUser());

            ps.executeUpdate();

            final ResultSet rsId = ConnPpto.conn.prepareStatement("SELECT last_insert_id() AS last_id FROM " + PptoGlobales.TBL_CREDITO_ADICIONAL).executeQuery();
            if (rsId.next()) {
                idCreAdi = rsId.getInt("last_id");
            } else {
                idCreAdi = 0;
            }

            // Actualiza la tabla de creditos adicionales detalle, para llevar el historico de los creditos
            for (int i = 0; i < tblMaster.getRowCount(); i++) {
                PptoModel reg = (PptoModel) tblMaster.getValueAt(i, colReg);
                if (reg.getMonto().compareTo(BigDecimal.ZERO) > 0) {
                    final PreparedStatement psDet = ConnPpto.conn.prepareStatement("INSERT INTO " + PptoGlobales.TBL_CREDITO_ADICIONAL_DET + "(codigo, partida, monto, ejefis,ejefismes,fchsession,idsession,iduser,id_cre_adi) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)");
                    psDet.setString(1, reg.getCodigo());
                    psDet.setString(2, reg.getPartida());
                    psDet.setBigDecimal(3, reg.getMonto().setScale(2, RoundingMode.HALF_UP));
                    psDet.setLong(4, ejeFis);
                    psDet.setLong(5, ejeFisMes); // ejefismes
                    psDet.setDate(6, fchSession);
                    psDet.setLong(7, UserPassIn.getIdSession());
                    psDet.setLong(8, UserPassIn.getIdUser());
                    psDet.setLong(9, idCreAdi);

                    psDet.executeUpdate();
                }
            }

            genRpt();

            ConnPpto.execute("UPDATE " + TBL_PPTO_ADICIONAL + " SET monto='0.00'");

            ConnPpto.EndTransaction();

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            try {
                ConnPpto.RollBack();
            } catch (final Exception _exc) {
                JOptionPane.showMessageDialog(null, _ex);
            }
            return false;
        }

        JOptionPane.showMessageDialog(null, "Se ha actualizado la Base de Datos");

        return true;
    }

    /**
     * Rev 25/11/2016
     *
     * @throws Exception
     */
    private void genRpt() throws Exception {
        final String soporte = txtSoporte.getText().trim().toUpperCase();
        final String desc = jtDesc.getText().trim().toUpperCase();
        final BigDecimal monto = (BigDecimal) PptoGlobales.curFormat.stringToValue(txtMontoCred.getText());
        final Date fecha = PptoGlobales.dateFormat.parse(txtFechaCred.getText());
        final Calendar fechaOp = Calendar.getInstance();
        final long ejeFis = fechaOp.get(Calendar.YEAR);
        final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;
        final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());

        final Map<String, Object> param = new HashMap<>(101);
        param.put("idcreadi", idCreAdi);
        param.put("soporte", soporte);
        param.put("desc", desc);
        param.put("monto", Format.toDouble(monto));
        param.put("fecha", fecha);
        param.put("ejefis", ejeFis);
        param.put("ejefismes", ejeFisMes);
        param.put("idsession", UserPassIn.getIdSession());
        param.put("fchsession", fchSession);
        param.put("iduser", UserPassIn.getIdUser());
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

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/credito_adicional_rpt.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), Conn.getConnection()), false);
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de credito adicional no encontrado");
        }
    }

    /**
     * Rev 25/11/2016
     *
     * @return
     */
    private boolean ModificarPartida() {
        if (winState != WinState.MODIFY) {
            return false;
        }

        int selRow = tblMaster.getSelectedRow();
        if (selRow < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return false;
        }

        PptoModel reg = (PptoModel) tblMaster.getValueAt(selRow, colReg);
        int id = reg.getId();
        BigDecimal montoAnt = reg.getMonto();

        BigDecimal montoNuevo;
        try {
            montoNuevo = Format.toBigDec(txtMontoPartida.getText());
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(null, "Monto invalido");
            txtMontoPartida.requestFocusInWindow();
            return false;
        }

        if (montoNuevo.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Monto invalido");
            txtMontoPartida.requestFocusInWindow();
            return false;
        }

        // Verificar que no se halla sobrepasado e mmonto del credito
        BigDecimal montoCred;
        try {
            montoCred = (BigDecimal) PptoGlobales.curFormat.stringToValue(txtMontoCred.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto del Crédito inválido");
            txtMontoPartida.requestFocusInWindow();
            return false;
        }

        BigDecimal montoAcum;
        try {
            montoAcum = (BigDecimal) PptoGlobales.curFormat.stringToValue(txtTotalAcumPartidas.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto Total Acumulado inválido");
            txtMontoPartida.requestFocusInWindow();
            return false;
        }

        if (montoAcum.subtract(montoAnt).add(montoNuevo).compareTo(montoCred) > 0) {
            JOptionPane.showMessageDialog(null, "Verifique. El monto acumulado en las Partidas, no puede ser mayor al Monto del Crédito.");
            txtMontoPartida.requestFocusInWindow();
            return false;
        }

        try {
            ConnPpto.executeUpdate("UPDATE " + TBL_PPTO_ADICIONAL + " SET monto=' " + montoNuevo.doubleValue() + "' WHERE id='" + id + "' AND ejefis= " + Globales.getEjeFisYear());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de actualizar el registro" + System.getProperty("line.separator") + _ex);
            txtMontoPartida.requestFocusInWindow();
            return false;
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        jpDatosCredito = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtSoporte = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtDesc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMontoCred = new javax.swing.JFormattedTextField(new BigDecimal("0.00"));
        jLabel9 = new javax.swing.JLabel();
        txtFechaCred = new javax.swing.JFormattedTextField();
        jpFiltro = new javax.swing.JPanel();
        btnReIniciar = new javax.swing.JButton();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jbResetear = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jlCodigo = new javax.swing.JLabel();
        jlPartida = new javax.swing.JLabel();
        txtPartida = new javax.swing.JTextField();
        jlMonto = new javax.swing.JLabel();
        jpCmdState = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        txtTotalAcumPartidas = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCurFormatter());
        txtCodigo = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCodEgresoMaskFormatter());
        txtMontoPartida = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCurFormatter());
        jpComandos = new javax.swing.JPanel();
        jbGenerar = new javax.swing.JButton();
        jbConsultarC = new javax.swing.JButton();
        jbRetornar = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        jlFondo = new javax.swing.JLabel();

        setTitle("CRÉDITO ADICIONAL");
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        jpFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setOpaque(false);
        jpFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        jpFondo.setLayout(new javax.swing.BoxLayout(jpFondo, javax.swing.BoxLayout.PAGE_AXIS));

        jpDatosCredito.setBorder(javax.swing.BorderFactory.createTitledBorder(" Datos del Crédito "));
        jpDatosCredito.setMaximumSize(new java.awt.Dimension(1060, 70));
        jpDatosCredito.setMinimumSize(new java.awt.Dimension(1060, 60));
        jpDatosCredito.setOpaque(false);
        jpDatosCredito.setPreferredSize(new java.awt.Dimension(1060, 60));
        jpDatosCredito.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel6.setText("SOPORTE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpDatosCredito.add(jLabel6, gridBagConstraints);

        txtSoporte.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtSoporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoporte.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSoporte.setMinimumSize(new java.awt.Dimension(200, 21));
        txtSoporte.setPreferredSize(new java.awt.Dimension(200, 21));
        txtSoporte.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtSoporte.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSoporteFocusGained(evt);
            }
        });
        txtSoporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoporteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        jpDatosCredito.add(txtSoporte, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel7.setText("DESCRIPCIÓN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpDatosCredito.add(jLabel7, gridBagConstraints);

        jtDesc.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        jtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtDesc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jtDesc.setMaximumSize(new java.awt.Dimension(450, 21));
        jtDesc.setMinimumSize(new java.awt.Dimension(450, 21));
        jtDesc.setPreferredSize(new java.awt.Dimension(450, 21));
        jtDesc.setSelectionColor(new java.awt.Color(175, 204, 125));
        jtDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtDescFocusGained(evt);
            }
        });
        jtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtDescActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        jpDatosCredito.add(jtDesc, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel8.setText("MONTO BS.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpDatosCredito.add(jLabel8, gridBagConstraints);
        jLabel8.getAccessibleContext().setAccessibleName("MONTO");

        txtMontoCred.setBackground(new java.awt.Color(175, 204, 125));
        txtMontoCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMontoCred.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtMontoCred.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoCred.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMontoCred.setDoubleBuffered(true);
        txtMontoCred.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtMontoCred.setMinimumSize(new java.awt.Dimension(200, 21));
        txtMontoCred.setPreferredSize(new java.awt.Dimension(200, 21));
        txtMontoCred.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtMontoCred.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMontoCredFocusGained(evt);
            }
        });
        txtMontoCred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoCredActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        jpDatosCredito.add(txtMontoCred, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel9.setText("FECHA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpDatosCredito.add(jLabel9, gridBagConstraints);

        txtFechaCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFechaCred.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        txtFechaCred.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaCred.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFechaCred.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtFechaCred.setMinimumSize(new java.awt.Dimension(100, 21));
        txtFechaCred.setPreferredSize(new java.awt.Dimension(100, 21));
        txtFechaCred.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtFechaCred.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFechaCredFocusGained(evt);
            }
        });
        txtFechaCred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaCredActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        jpDatosCredito.add(txtFechaCred, gridBagConstraints);

        jpFondo.add(jpDatosCredito);

        jpFiltro.setMaximumSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setMinimumSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setOpaque(false);
        jpFiltro.setPreferredSize(new java.awt.Dimension(1060, 50));
        jpFiltro.setLayout(new java.awt.GridBagLayout());

        btnReIniciar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnReIniciar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnReIniciar.setText(" Re-Iniciar Tabla");
        btnReIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReIniciarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(btnReIniciar, gridBagConstraints);

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
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(jLabel1, gridBagConstraints);

        jbResetear.setBackground(java.awt.SystemColor.inactiveCaption);
        jbResetear.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbResetear.setText("RESET");
        jbResetear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jbResetear, gridBagConstraints);

        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "Partida", "Monto" }));
        jcbFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbFilter.setMaximumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setMinimumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 200, 0, 0);
        jpFiltro.add(jcbFilter, gridBagConstraints);

        jpFondo.add(jpFiltro);

        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 360));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 360));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 360));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblMaster.setFont(new java.awt.Font("Arial", 3, 15)); // NOI18N
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Partida", "Monto Bs.", "Reg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblMaster.setDoubleBuffered(true);
        tblMaster.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblMaster.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(500);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jpFondo.add(jScrollPane1);

        jpEditables.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Partida"));
        jpEditables.setMaximumSize(new java.awt.Dimension(1060, 100));
        jpEditables.setMinimumSize(new java.awt.Dimension(1060, 100));
        jpEditables.setOpaque(false);
        jpEditables.setPreferredSize(new java.awt.Dimension(1060, 100));
        jpEditables.setLayout(new java.awt.GridBagLayout());

        jlCodigo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlCodigo.setText("Código ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpEditables.add(jlCodigo, gridBagConstraints);

        jlPartida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlPartida.setLabelFor(txtPartida);
        jlPartida.setText("Partida ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpEditables.add(jlPartida, gridBagConstraints);

        txtPartida.setBackground(new java.awt.Color(175, 204, 125));
        txtPartida.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtPartida.setForeground(new java.awt.Color(0, 51, 102));
        txtPartida.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPartida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPartida.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPartida.setEnabled(false);
        txtPartida.setMinimumSize(new java.awt.Dimension(250, 21));
        txtPartida.setPreferredSize(new java.awt.Dimension(300, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        jpEditables.add(txtPartida, gridBagConstraints);

        jlMonto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlMonto.setText("Monto Bs. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        jpEditables.add(jlMonto, gridBagConstraints);

        jpCmdState.setOpaque(false);
        jpCmdState.setLayout(new java.awt.GridBagLayout());

        btnGuardar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnGuardar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnGuardar.setText(" GUARDAR ");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.setMaximumSize(new java.awt.Dimension(90, 19));
        btnGuardar.setMinimumSize(new java.awt.Dimension(90, 19));
        btnGuardar.setPreferredSize(new java.awt.Dimension(90, 19));
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(btnGuardar, gridBagConstraints);

        jbCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbCancelar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbCancelar.setText(" CANCELAR ");
        jbCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbCancelar.setMaximumSize(new java.awt.Dimension(90, 19));
        jbCancelar.setMinimumSize(new java.awt.Dimension(90, 19));
        jbCancelar.setPreferredSize(new java.awt.Dimension(90, 19));
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(jbCancelar, gridBagConstraints);

        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblmonto1.setText("Total Acumulado en las Partidas Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 250, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);

        txtTotalAcumPartidas.setBackground(new java.awt.Color(175, 204, 125));
        txtTotalAcumPartidas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalAcumPartidas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalAcumPartidas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalAcumPartidas.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalAcumPartidas.setDoubleBuffered(true);
        txtTotalAcumPartidas.setEnabled(false);
        txtTotalAcumPartidas.setFocusable(false);
        txtTotalAcumPartidas.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtTotalAcumPartidas.setMinimumSize(new java.awt.Dimension(200, 21));
        txtTotalAcumPartidas.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(txtTotalAcumPartidas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jpCmdState, gridBagConstraints);

        txtCodigo.setBackground(new java.awt.Color(175, 204, 125));
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.setEnabled(false);
        txtCodigo.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        txtCodigo.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCodigo.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jpEditables.add(txtCodigo, gridBagConstraints);

        txtMontoPartida.setBackground(new java.awt.Color(175, 204, 125));
        txtMontoPartida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMontoPartida.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoPartida.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMontoPartida.setEnabled(false);
        txtMontoPartida.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtMontoPartida.setMinimumSize(new java.awt.Dimension(200, 21));
        txtMontoPartida.setPreferredSize(new java.awt.Dimension(200, 21));
        txtMontoPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoPartidaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jpEditables.add(txtMontoPartida, gridBagConstraints);

        jpFondo.add(jpEditables);

        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());

        jbGenerar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbGenerar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbGenerar.setText(" GENERAR ");
        jbGenerar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        jpComandos.add(jbGenerar, gridBagConstraints);

        jbConsultarC.setBackground(java.awt.SystemColor.inactiveCaption);
        jbConsultarC.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbConsultarC.setText(" CONSULTAR CRÉDITO ");
        jbConsultarC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbConsultarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConsultarCActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpComandos.add(jbConsultarC, gridBagConstraints);

        jbRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbRetornar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbRetornar.setText(" RETORNAR ");
        jbRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbRetornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRetornarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpComandos.add(jbRetornar, gridBagConstraints);

        lblMensaje.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje.setText("Guardar para iniciar Modo de Edición");
        lblMensaje.setMaximumSize(new java.awt.Dimension(600, 19));
        lblMensaje.setMinimumSize(new java.awt.Dimension(600, 19));
        lblMensaje.setPreferredSize(new java.awt.Dimension(600, 19));
        lblMensaje.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpComandos.add(lblMensaje, gridBagConstraints);

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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        BigDecimal montoC;
        try {
            montoC = (BigDecimal) PptoGlobales.curFormat.stringToValue(txtMontoCred.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto del Crédito inválido");
            jbCancelar.doClick();
            txtMontoCred.requestFocusInWindow();
            return;
        }

        if ((montoC.compareTo(BigDecimal.ZERO) <= 0)) {
            JOptionPane.showMessageDialog(null, "Monto del Crédito inválido");
            jbCancelar.doClick();
            txtMontoCred.requestFocusInWindow();
            return;
        }

        if (ModificarPartida()) {
            UpdateJTable();
            UpdateEnabledAndFocus();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jbGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGenerarActionPerformed
        if (Generar()) {
            ClearDatosCredito();
            UpdateJTable();
            UpdatePanelDetails();
            ClearDatosCredito();
            UpdateEnabledAndFocus();
        }
    }//GEN-LAST:event_jbGenerarActionPerformed

    private void jbConsultarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConsultarCActionPerformed
        final javax.swing.JFrame me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new CreditoAdicionalSelect(me, 0).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_jbConsultarCActionPerformed

    private void txtMontoPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoPartidaActionPerformed
        txtMontoPartida.transferFocus();
    }//GEN-LAST:event_txtMontoPartidaActionPerformed

    private void txtSoporteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoporteFocusGained
        txtSoporte.selectAll();
    }//GEN-LAST:event_txtSoporteFocusGained

    private void txtSoporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoporteActionPerformed
        txtSoporte.transferFocus();
    }//GEN-LAST:event_txtSoporteActionPerformed

    private void jtDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtDescFocusGained
        jtDesc.selectAll();
    }//GEN-LAST:event_jtDescFocusGained

    private void jtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtDescActionPerformed
        jtDesc.transferFocus();
    }//GEN-LAST:event_jtDescActionPerformed

    private void txtMontoCredFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMontoCredFocusGained
        // Para que funcione el selectAll()
        txtMontoCred.setText(txtMontoCred.getValue().toString());
        txtMontoCred.selectAll();
    }//GEN-LAST:event_txtMontoCredFocusGained

    private void txtMontoCredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoCredActionPerformed
        txtMontoCred.transferFocus();
    }//GEN-LAST:event_txtMontoCredActionPerformed

    private void txtFechaCredFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaCredFocusGained
        txtFechaCred.selectAll();
    }//GEN-LAST:event_txtFechaCredFocusGained

    private void txtFechaCredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaCredActionPerformed
        try {
            txtFechaCred.commitEdit();
            btnGuardar.doClick();
        } catch (ParseException _ex) {
            JOptionPane.showMessageDialog(null, "Fecha inválida");
            txtFechaCred.requestFocusInWindow();
        }
    }//GEN-LAST:event_txtFechaCredActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed

        UpdateJTable();
        UpdateEnabledAndFocus();

    }//GEN-LAST:event_jbCancelarActionPerformed

    private void btnGuardarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnGuardarFocusGained
        getRootPane().setDefaultButton(btnGuardar);
    }//GEN-LAST:event_btnGuardarFocusGained

    private void btnGuardarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnGuardarFocusLost
        getRootPane().setDefaultButton(null);
    }//GEN-LAST:event_btnGuardarFocusLost

    private void jbRetornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRetornarActionPerformed
        // Codigo programado
    }//GEN-LAST:event_jbRetornarActionPerformed

    private void btnReIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReIniciarActionPerformed
        try {
            ReCrear();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        UpdatePanels();
    }//GEN-LAST:event_btnReIniciarActionPerformed

    /**
     *
     */
    @Override
    protected void Salir() {

        final String sMontoC = txtMontoCred.getText();
        final String sMontoA = txtTotalAcumPartidas.getText();

        // Sale tranquilamente
        if (sMontoC.isEmpty() && sMontoA.isEmpty() || sMontoC.equals("0,00") && sMontoA.equals("0,00")) {
            super.Salir();
            return;
        }

        BigDecimal montoC;
        try {
            montoC = (BigDecimal) PptoGlobales.curFormat.stringToValue(sMontoC);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto del Crédito inválido");
            txtMontoCred.requestFocusInWindow();
            return;
        }

        BigDecimal montoA;
        try {
            montoA = (BigDecimal) PptoGlobales.curFormat.stringToValue(sMontoA);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto Total Acumulado inválido" + System.getProperty("line.separator") + _ex);
            tblMaster.requestFocusInWindow();
            return;
        }

        // Sale tranquilamente
        if (montoC.compareTo(ZERO) <= 0 && montoA.compareTo(ZERO) <= 0) {
            super.Salir();
            return;
        }

        if (montoA.compareTo(ZERO) > 0) {
            if (JOptionPane.showConfirmDialog(this, "Hay datos cargados ¿ Confirmar salida ?",
                "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                return;
            }

            if (JOptionPane.showConfirmDialog(this, "¿ Desea conservar los datos acttuales ?",
                "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                try {
                    // Eliminar la tabla antes de Salir totalmente.
                    ConnPpto.execute("DROP TABLE IF EXISTS " + TBL_PPTO_ADICIONAL);
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, _ex);
                }
            }

        }

        try {
            ConnPpto.execute("UNLOCK TABLES");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + _ex);
        }

        super.Salir();
    }

    /**
     * Rev 25/11/2016
     *
     */
    @Override
    protected void UpdateTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (int row = 0; row < tblMaster.getRowCount(); row++) {
            total = total.add((BigDecimal) tblMaster.getValueAt(row, 2));

        }

        // Actualizar el campo total
        try {
            txtTotalAcumPartidas.setText(PptoGlobales.curFormat.valueToString(total));
        } catch (final Exception _ex) {
            txtTotalAcumPartidas.setText("Error");
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
                new CreditoAdicional(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnReIniciar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbConsultarC;
    private javax.swing.JButton jbGenerar;
    private javax.swing.JButton jbResetear;
    private javax.swing.JButton jbRetornar;
    private javax.swing.JComboBox jcbFilter;
    private javax.swing.JLabel jlCodigo;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JLabel jlMonto;
    private javax.swing.JLabel jlPartida;
    private javax.swing.JPanel jpCmdState;
    private javax.swing.JPanel jpComandos;
    private javax.swing.JPanel jpDatosCredito;
    private javax.swing.JPanel jpEditables;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JPanel jpFondo;
    private javax.swing.JTextField jtDesc;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblmonto1;
    private javax.swing.JTable tblMaster;
    private javax.swing.JFormattedTextField txtCodigo;
    private javax.swing.JFormattedTextField txtFechaCred;
    private javax.swing.JFormattedTextField txtMontoCred;
    private javax.swing.JFormattedTextField txtMontoPartida;
    private javax.swing.JTextField txtPartida;
    private javax.swing.JTextField txtSoporte;
    private javax.swing.JFormattedTextField txtTotalAcumPartidas;
    // End of variables declaration//GEN-END:variables

}
