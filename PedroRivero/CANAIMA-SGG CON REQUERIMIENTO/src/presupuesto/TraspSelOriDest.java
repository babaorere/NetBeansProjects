/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import modelos.PptoModel;
import static presupuesto.WinState.ADD;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;

/**
 *
 * @author
 */
@SuppressWarnings("serial")
public final class TraspSelOriDest extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del codigo programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colCod = 0;
    private final int colPartida = 1;
    private final int colMontoIni = 2;
    private final int colMontoAct = 3;
    private final int colMontoTrasp = 4;
    private final int colRegPtto = 5;
    private final int colRegTrasp = 6;

    private final String strTbl;
    private final String strTipoOriDest;

    /**
     *
     * @param _parent
     * @param _strTbl
     * @param _strTipoOriDest
     */
    public TraspSelOriDest(final java.awt.Window _parent, final String _strTbl, final String _strTipoOriDest) {
        super(_parent);

        strTbl = _strTbl;
        strTipoOriDest = _strTipoOriDest;

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
        param.put(JBRETURN, btnRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, jbResetear);
        param.put(JTABLE, tblMaster);
        ConfigComponents(param);

        if (strTbl.equals(Globales.TBL_PPTO_EGRESO)) {
            txtCodigo.setValue(null);
            txtCodigo.setFormatterFactory(PptoGlobales.getCodEgresoFormatterFactory());
        } else {
            txtCodigo.setValue(null);
            txtCodigo.setFormatterFactory(PptoGlobales.getCodIngresoFormatterFactory());
        }

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
     *
     * Rev 05-02-2017
     */
    @Override
    public void InitConditions() {
        super.InitConditions();

        lblTitulo.setText("Presupuesto de " + (strTbl.equals(Globales.TBL_PPTO_EGRESO) ? "Egreso" : "Ingreso") + " como " + (strTipoOriDest.equals("O") ? "Origen" : "Destino"));

        winState = WinState.MODIFY;
        tblMaster.requestFocusInWindow();
    }

    /**
     * Eliminar los registros emporales en la tabla detalle
     *
     * Rev 05-02-2017
     *
     */
    private void ReCrear() throws Exception {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        try {
            Conn.BeginTransaction();

            final String ppto_egr_ing = strTbl.equals(Globales.TBL_PPTO_EGRESO) ? "E" : "I";

            final int cont = ConnPpto.conn.prepareStatement("DELETE FROM trasppartidas_det WHERE id_trasp= 0 AND id_user= " + UserPassIn.getIdUser() + " AND ppto_egr_ing= '" + ppto_egr_ing + "' AND tipo_ori_dest= '" + strTipoOriDest + "'").executeUpdate();

            JOptionPane.showMessageDialog(this, "Operación realizada" + System.getProperty("line.separator") + "Se han eliminado " + cont + " registros");

            Conn.EndTransaction();
        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex1) {
                JOptionPane.showMessageDialog(null, _ex1);
            }

            JOptionPane.showMessageDialog(null, _ex);
        } finally {
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

        // Configurar el campo de Monto, para que acepte y maneje BigDecimal
        txtMontoPartida.setFormatterFactory(PptoGlobales.getCurFormatterFactory());

        // Configurar la JTable de los Beneficiarios, para manejar el ENTER
        final InputMap imBenef = tblMaster.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap amBenef = tblMaster.getActionMap();

        final KeyStroke enterKeyBenef = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        imBenef.put(enterKeyBenef, "Action.enter");
        amBenef.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                btnGuardar.doClick();
            }
        });

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
        final Object[] datos = new Object[7];
        try {

            // Generar el comando SQL
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + strTbl + " WHERE ejefis= " + Globales.getEjeFisYear() + " AND anulado_sn= 'N' ORDER BY codigo ASC");
            while (rs.next()) {
                // Obtener el registro del presupuesto
                PptoModel regPpto = new PptoModel(rs);

                // Verificar si en la tabla de detalle existe datos cargados, 
                // para la respectiva partida de presupuesto
                final BigDecimal montoTrasp;
                TblTraspDet regTraspDet;
                final ResultSet rsTraspDet = ConnPpto.executeQuery("SELECT * FROM trasppartidas_det WHERE id_trasp= 0 AND id_user= " + UserPassIn.getIdUser() + " AND tipo_ori_dest= '" + strTipoOriDest + "' AND codpresu= '" + regPpto.getCodigo() + "'");
                if (rsTraspDet.next()) {
                    regTraspDet = new TblTraspDet(rsTraspDet);
                    montoTrasp = regTraspDet.getMonto();
                } else {
                    regTraspDet = null;
                    montoTrasp = ZERO;
                }

                datos[colCod] = regPpto.getCodigo();
                datos[colPartida] = regPpto.getPartida();
                datos[colMontoIni] = regPpto.getMontoIni().doubleValue();
                datos[colMontoAct] = regPpto.getMonto().doubleValue();
                datos[colMontoTrasp] = montoTrasp.doubleValue();
                datos[colRegPtto] = regPpto;
                datos[colRegTrasp] = regTraspDet;

                model.addRow(datos);

                // Calcular el total
                total = total.add(montoTrasp);
            }
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

    /**
     * Rev 05-02-2017
     *
     */
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

        txtCodigo.setValue((String) tblMaster.getValueAt(selectedRow, colCod));
        txtPartida.setText((String) tblMaster.getValueAt(selectedRow, colPartida));
        txtPartida.setCaretPosition(0);
        txtMontoPartida.setValue(tblMaster.getValueAt(selectedRow, colMontoTrasp));
    }

    @Override
    protected void ClearDetails() {
        txtCodigo.setText(PptoGlobales.strCodEgresoZero);
        txtPartida.setText("");
        txtMontoPartida.setText("0,00");
    }

    /**
     * Rev 25/11/2016
     *
     */
    protected void UpdateEnabledAndFocus() {

        switch (winState) {

            case NORMAL:
                lblMensaje.setText("Editar para realizar cambios");
                btnGuardar.setText("EDITAR");

                txtMontoPartida.setEnabled(false);
                btnGuardar.setEnabled(tblMaster.getRowCount() > 0);
                jbCancelar.setEnabled(false);

                btnRetornar.setEnabled(true);
                btnReIniciar.setEnabled(true);

                tblMaster.requestFocusInWindow();
                getRootPane().setDefaultButton(null);
                break;

            case MODIFY:
                lblMensaje.setText("Guardar cambios, o Cancelar para salir de Edición");
                btnGuardar.setText("GUARDAR");

                txtMontoPartida.setEnabled(true);
                btnGuardar.setEnabled(true);
                jbCancelar.setEnabled(true);

                btnRetornar.setEnabled(true);
                btnReIniciar.setEnabled(false);

                txtMontoPartida.requestFocusInWindow();
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
     * Rev 05-02-2017
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

        PptoModel regPpto = (PptoModel) tblMaster.getValueAt(selRow, colRegPtto);

        BigDecimal montoTrasp;
        try {
            montoTrasp = Format.toBigDec(txtMontoPartida.getText());
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(null, "Monto invalido");
            txtMontoPartida.requestFocusInWindow();
            return false;
        }

        if (montoTrasp.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Monto invalido");
            txtMontoPartida.requestFocusInWindow();
            return false;
        }

        // Verificar que existe disponibilidad presupuestaria
        if (strTipoOriDest.equals("O")) {
            BigDecimal disp = Format.toBigDec((double) tblMaster.getValueAt(selRow, colMontoAct));
            if (montoTrasp.compareTo(disp) > 0) {
                JOptionPane.showMessageDialog(null, "Monto invalido, supera la disponibilidad");
                txtMontoPartida.requestFocusInWindow();
                return false;
            }
        }

        TblTraspDet regTraspDet = (TblTraspDet) tblMaster.getValueAt(selRow, colRegTrasp);
        try {
            Conn.BeginTransaction();
            if (regTraspDet == null) {
                if (montoTrasp.compareTo(ZERO) > 0) {
                    final PreparedStatement ps = ConnPpto.conn.prepareStatement("INSERT INTO trasppartidas_det "
                        + "(id_user, id_session, date_session, id_trasp, " // 1, 2, 3, 4,
                        + "ppto_egr_ing, tipo_ori_dest, codpresu, partida, monto) " // 5, 6, 7, 8, 9
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setLong(1, UserPassIn.getIdUser());
                    ps.setLong(2, UserPassIn.getIdSession());
                    ps.setTimestamp(3, UserPassIn.getDateSession());
                    ps.setLong(4, 0); // id_trasp
                    ps.setString(5, strTbl.equals(Globales.TBL_PPTO_EGRESO) ? "E" : "I"); // ppto_egr_ing
                    ps.setString(6, strTipoOriDest); // tipo_ori_dest
                    ps.setString(7, regPpto.getCodigo()); // codpresu
                    ps.setString(8, regPpto.getPartida()); // partida                
                    ps.setBigDecimal(9, montoTrasp); // monyo
                    ps.executeUpdate();
                }
            } else {
                if (montoTrasp.compareTo(ZERO) > 0) {
                    final int cont = ConnPpto.executeUpdate("UPDATE trasppartidas_det SET monto=' " + montoTrasp + "' WHERE id_trasp_det= " + regTraspDet.getId_trasp_det());
                    if (cont != 1) {
                        throw new Exception("Error al tratar de actualizar el Registro" + System.getProperty("line.separator") + "Se han actualizado " + cont + " registros");
                    }
                } else {
                    // Si el monto es cero, se procede a eliminar el registro
                    final int cont = ConnPpto.executeUpdate("DELETE FROM trasppartidas_det WHERE id_trasp_det= " + regTraspDet.getId_trasp_det());
                    if (cont != 1) {
                        throw new Exception("Error al tratar de eliminar el Registro" + System.getProperty("line.separator") + "Se han eliminado " + cont + " registros");
                    }
                }
            }

            Conn.EndTransaction();
        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex1) {
                JOptionPane.showMessageDialog(null, _ex1);
            }

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
        lblTitulo = new javax.swing.JLabel();
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
        lblMensaje = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCodEgresoMaskFormatter());
        txtMontoPartida = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCurFormatter());
        jpComandos = new javax.swing.JPanel();
        btnRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("TRASPASO DE PARTIDAS");
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        jpFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setOpaque(false);
        jpFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        jpFondo.setLayout(new javax.swing.BoxLayout(jpFondo, javax.swing.BoxLayout.PAGE_AXIS));

        jpDatosCredito.setBorder(javax.swing.BorderFactory.createTitledBorder(" Datos de la Operación"));
        jpDatosCredito.setMaximumSize(new java.awt.Dimension(1060, 70));
        jpDatosCredito.setMinimumSize(new java.awt.Dimension(1060, 60));
        jpDatosCredito.setOpaque(false);
        jpDatosCredito.setPreferredSize(new java.awt.Dimension(1060, 60));
        jpDatosCredito.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setPreferredSize(new java.awt.Dimension(300, 25));
        jpDatosCredito.add(lblTitulo, java.awt.BorderLayout.CENTER);

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
                "Código", "Partida", "Monto Ini. Bs.", "Monto Disp. Bs.", "Monto Trasp. Bs.", "RegPpto", "RegTraspDet"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
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
            tblMaster.getColumnModel().getColumn(3).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(0);
            tblMaster.getColumnModel().getColumn(6).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(6).setMaxWidth(0);
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
        jbCancelar.setText("GENERAR");
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
        lblmonto1.setText("Total Acumulado Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
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

        lblMensaje.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMensaje.setText("Guardar para iniciar Modo de Edición");
        lblMensaje.setMaximumSize(new java.awt.Dimension(300, 19));
        lblMensaje.setMinimumSize(new java.awt.Dimension(300, 19));
        lblMensaje.setPreferredSize(new java.awt.Dimension(300, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpCmdState.add(lblMensaje, gridBagConstraints);

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
        txtCodigo.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
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

        btnRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnRetornar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        btnRetornar.setText(" RETORNAR ");
        btnRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnRetornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetornarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 750, 0, 0);
        jpComandos.add(btnRetornar, gridBagConstraints);

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
        switch (winState) {
            case NORMAL:
                winState = WinState.MODIFY;
                UpdateJTable();
                UpdateEnabledAndFocus();
                break;

            case MODIFY:
                if (ModificarPartida()) {
                    UpdateJTable();
                    UpdateEnabledAndFocus();
                }
                break;
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtMontoPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoPartidaActionPerformed
        txtMontoPartida.transferFocus();
    }//GEN-LAST:event_txtMontoPartidaActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        btnRetornar.doClick();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void btnGuardarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnGuardarFocusGained
        getRootPane().setDefaultButton(btnGuardar);
    }//GEN-LAST:event_btnGuardarFocusGained

    private void btnGuardarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnGuardarFocusLost
        getRootPane().setDefaultButton(null);
    }//GEN-LAST:event_btnGuardarFocusLost

    private void btnRetornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetornarActionPerformed
        // Codigo programado
    }//GEN-LAST:event_btnRetornarActionPerformed

    private void btnReIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReIniciarActionPerformed
        try {
            ReCrear();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        UpdatePanels();
    }//GEN-LAST:event_btnReIniciarActionPerformed

    @Override
    protected void Salir() {
        if (btnRetornar.isEnabled()) {

            if (parent instanceof TraspPartidas) {
                if (strTipoOriDest.equals("O")) {
                    // Establece el centinela para actualizar la tabla origen
                    ((TraspPartidas) parent).setOri();
                } else {
                    // Establece el centinela para actualizar la tabla dest
                    ((TraspPartidas) parent).setDest();
                }
            }

            super.Salir();
        }
    }

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TraspSelOriDest(null, Globales.TBL_PPTO_EGRESO, "O").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnReIniciar;
    private javax.swing.JButton btnRetornar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbResetear;
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
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblmonto1;
    private javax.swing.JTable tblMaster;
    private javax.swing.JFormattedTextField txtCodigo;
    private javax.swing.JFormattedTextField txtMontoPartida;
    private javax.swing.JTextField txtPartida;
    private javax.swing.JFormattedTextField txtTotalAcumPartidas;
    // End of variables declaration//GEN-END:variables

}
