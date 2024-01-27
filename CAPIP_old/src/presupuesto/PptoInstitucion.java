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
import static capipsistema.Globales.TBL_PPTO_INGRESO;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.PptoModel;
import static presupuesto.WinState.ADD;
import static presupuesto.WinState.MODIFY;
import static presupuesto.XJFrame.JBRESET;
import static presupuesto.XJFrame.JBRETURN;
import static presupuesto.XJFrame.JCBFILTER;
import static presupuesto.XJFrame.JTABLE;
import static presupuesto.XJFrame.JTFILTRO;
import utils.DecimalCellRenderer;
import utils.Format;
import utils.IntegerCellRenderer;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class PptoInstitucion extends XJFrameJTable {

    private final int colCodigo = 0;
    private final int colPartida = 1;
    private final int colMontoIni = 2;
    private final int colMonto = 3;
    private final int colContable = 4;
    private final int colReg = 5;

    private boolean update;

    private String tabla;

    private int ejeFis;
    private int ejeFisMes;

    /**
     *
     * @param _parent
     */
    public PptoInstitucion(final java.awt.Window _parent) {
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
            UpdateJTable();
            UpdateEnabledAndFocus();
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
        // jbReturn, jcbFilter, jtCriterio, jbReset, jtable, colObj, jbSelect, jbCancel
        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, jbRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, jbResetear);
        param.put(JTABLE, tblMaster);
        param.put(COLOBJ, colReg);
//        param.put(JBSELECT, btnSeleccionar);
//        param.put(JBCANCEL, btnCancelar);
        ConfigComponents(param);
    }

    /**
     * Rev 27/11/2016
     */
    private void setStartConditions() {
        InitConditions();
        UpdatePanels();
        UpdateEnabledAndFocus();
    }

    /**
     * Valores y/o condiciones iniciales de la ventana Rev 25/11/2016
     *
     */
    @Override
    public void InitConditions() {
        super.InitConditions();
        update = false;
        winState = WinState.NORMAL;
        tabla = capipsistema.Globales.TBL_PPTO_EGRESO;

        final Calendar fechaOp = Calendar.getInstance();
        fechaOp.setTimeInMillis(Globales.getServerTimeStamp().getTime());
        ejeFis = fechaOp.get(Calendar.YEAR);
        ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;

        rbEjeFisAct.setSelected(true);
        rbEjeFisAct.setText(String.valueOf(ejeFis));
        rbEjeFisNext.setText(String.valueOf(ejeFis + 1));

        cmbOrden.setSelectedIndex(0);
    }

    /**
     * Rev 08/02/2017
     *
     * @param _param
     */
    @Override
    protected void ConfigComponents(final Map<String, Object> _param) {
        super.ConfigComponents(_param);

        // Establecer funcionalidad para el manejo de montos decimales
        txtCodigo.setFormatterFactory(PptoGlobales.getCodEgresoFormatterFactory());
        txtMontoIni.setFormatterFactory(PptoGlobales.getCurFormatterFactory());

        DefaultTableCellRenderer tcr;

        // Establecer el alineamiento de las columnas
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colCodigo).setCellRenderer(tcr);

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colContable).setCellRenderer(tcr);

        // Formatear la vista de las columnas
        tblMaster.setDefaultRenderer(Integer.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Long.class, new IntegerCellRenderer());
        tblMaster.setDefaultRenderer(Double.class, new DecimalCellRenderer());

    }

    @Override
    protected void UpdatePanels() {
        UpdateJTable();
    }

    /**
     * Actualizar la vista de la ventana. Deberia llamarse cuando se halla modificado la tabla Rev 25/11/2016
     *
     */
    @Override
    public void UpdateJTable() {

        final String orden[] = {"id", "codigo", "partida"};

        // Registrar la fila seleccionada
        int lastRowSel = tblMaster.getSelectedRow();

        // Para evitar multiples llamadas al metodo valueChanged
        tblMaster.clearSelection();

        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        BigDecimal total = BigDecimal.ZERO;
        final Object[] datos = new Object[6];
        try {
            // Generar el comando SQL
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + tabla + " WHERE ejefis= " + ejeFis + " AND anulado_sn= 'N' ORDER BY " + orden[cmbOrden.getSelectedIndex()] + " ASC");
            while (rs.next()) {
                PptoModel aux = new PptoModel(rs);
                datos[colCodigo] = aux.getCodigo();
                datos[colPartida] = aux.getPartida();
                datos[colMontoIni] = aux.getMontoIni().doubleValue();
                datos[colMonto] = aux.getMonto().doubleValue();
                datos[colContable] = aux.getCod_contable();
                datos[colReg] = aux;
                model.addRow(datos);

                total = total.add(aux.getMontoIni());
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
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
            tblMaster.setRowSelectionInterval(lastRowSel, lastRowSel);
        }

        // Actualizar el campo total
        try {
            txtTotalPpto.setText(PptoGlobales.curFormat.valueToString(total));
        } catch (final Exception _ex) {
            txtTotalPpto.setText("Error");
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

        txtCodigo.setText(tblMaster.getValueAt(selectedRow, colCodigo).toString());
        txtCodContable.setText(tblMaster.getValueAt(selectedRow, colContable).toString());
        txtPartida.setText(tblMaster.getValueAt(selectedRow, colPartida).toString());
        txtPartida.setCaretPosition(0);
        txtMontoIni.setText(Format.toStr((double) tblMaster.getValueAt(selectedRow, colMontoIni)));
    }

    @Override
    protected void ClearDetails() {
        txtCodigo.setText(btnPptoGasto.isSelected() ? PptoGlobales.strCodEgresoZero : PptoGlobales.strCodIngresoZero);
        txtCodContable.setText("");
        txtPartida.setText("");
        txtMontoIni.setText("0,00");
    }

    private void UpdateEnabledAndFocus() {
        switch (winState) {

            case NORMAL:
                lblMsj.setText("Agregar para introducir nuevas partidas");
                btnAgregar.setText("AGREGAR");

                btnPptoGasto.setEnabled(true);
                btnPptoIngreso.setEnabled(true);

                jcbFilter.setEnabled(true);
                jtFiltro.setEnabled(true);
                jbResetear.setEnabled(true);

                tblMaster.setEnabled(true);

                txtCodigo.setEnabled(false);
                txtCodContable.setEnabled(false);
                txtPartida.setEnabled(false);
                txtMontoIni.setEnabled(false);

                btnAgregar.setEnabled(true);
                btnModificar.setEnabled(tblMaster.getRowCount() > 0);
                jbEliminar.setEnabled(tblMaster.getRowCount() > 0);
                btnCancelar.setEnabled(false);

                jbCreditoA.setEnabled(true);
                jbTransfP.setEnabled(true);
                jbRetornar.setEnabled(true);

                tblMaster.requestFocusInWindow();
                getRootPane().setDefaultButton(null);
                break;

            case ADD:
                lblMsj.setText("Guardar para agregar el registro, Cancelar para salir de Edición");
                btnAgregar.setText("GUARDAR");

                btnPptoGasto.setEnabled(false);
                btnPptoIngreso.setEnabled(false);

                jcbFilter.setEnabled(false);
                jtFiltro.setEnabled(false);
                jbResetear.setEnabled(false);

//                jtable.setEnabled(true);
                tblMaster.setEnabled(false);

                txtCodigo.setEnabled(true);
                txtCodContable.setEnabled(true);
                txtPartida.setEnabled(true);
                txtMontoIni.setEnabled(true);

                btnAgregar.setEnabled(true);
                btnModificar.setEnabled(false);
                jbEliminar.setEnabled(false);
                btnCancelar.setEnabled(true);

                jbCreditoA.setEnabled(false);
                jbTransfP.setEnabled(false);
                jbRetornar.setEnabled(false);

                txtCodigo.requestFocusInWindow();
                getRootPane().setDefaultButton(null);
                break;

            case MODIFY:
                lblMsj.setText("Modificar guarda los cambios, Cancelar para salir de Edición");
                btnAgregar.setText("GUARDAR");

                btnPptoGasto.setEnabled(false);
                btnPptoIngreso.setEnabled(false);

                jcbFilter.setEnabled(false);
                jtFiltro.setEnabled(false);
                jbResetear.setEnabled(false);

                tblMaster.setEnabled(true);

                txtCodigo.setEnabled(true);
                txtCodContable.setEnabled(true);
                txtPartida.setEnabled(true);
                txtMontoIni.setEnabled(true);

                btnAgregar.setEnabled(false);
                btnModificar.setEnabled(true);
                jbEliminar.setEnabled(false);
                btnCancelar.setEnabled(true);

                jbCreditoA.setEnabled(false);
                jbTransfP.setEnabled(false);
                jbRetornar.setEnabled(false);

                txtMontoIni.requestFocusInWindow();
                getRootPane().setDefaultButton(null);
                break;

            case DELETE:
                lblMsj.setText("Eliminar ejecuta la acción, Cancelar retorna operación");
                btnAgregar.setText("AGREGAR");

                btnPptoGasto.setEnabled(false);
                btnPptoIngreso.setEnabled(false);

                jcbFilter.setEnabled(true);
                jtFiltro.setEnabled(true);
                jbResetear.setEnabled(true);

                tblMaster.setEnabled(false);

                txtCodigo.setEnabled(false);
                txtCodContable.setEnabled(false);
                txtPartida.setEnabled(false);
                txtMontoIni.setEnabled(false);

                btnAgregar.setEnabled(false);
                btnModificar.setEnabled(false);
                jbEliminar.setEnabled(true);
                btnCancelar.setEnabled(true);

                jbCreditoA.setEnabled(false);
                jbTransfP.setEnabled(false);
                jbRetornar.setEnabled(false);

                tblMaster.requestFocusInWindow();
                getRootPane().setDefaultButton(null);
                break;
        }
    }

    /**
     * Rev 25/11/2016
     *
     * @param _e
     */
    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent _e) {

        if (winState == WinState.DELETE) {
            Eliminar();
            UpdateJTable();
            UpdateEnabledAndFocus();
        }
//        else if (winState == WinState.MODIFY) {
//            jtable.requestFocusInWindow();
//            //jtable.dispatchEvent(_e);
//            btnCancelar.doClick();
//            btnModificar.doClick();
//        }
    }

    @Override
    protected void UpdateTotal() {
        BigDecimal total = ZERO;

        for (int row = 0; row < tblMaster.getRowCount(); row++) {
            total = total.add(BigDecimal.valueOf((double) tblMaster.getValueAt(row, colMonto)));
        }

        txtTotalPpto.setText(Format.toStr(total));
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bgPpto = new javax.swing.ButtonGroup();
        bgEjeFis = new javax.swing.ButtonGroup();
        btgOrden = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        jpTipoPpto = new javax.swing.JPanel();
        btnPptoGasto = new javax.swing.JToggleButton();
        btnPptoIngreso = new javax.swing.JToggleButton();
        jpFiltro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbOrden = new javax.swing.JComboBox();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jbResetear = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        rbEjeFisAct = new javax.swing.JRadioButton();
        rbEjeFisNext = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jlCodigo = new javax.swing.JLabel();
        jlPartida = new javax.swing.JLabel();
        txtPartida = new javax.swing.JTextField();
        jlMonto = new javax.swing.JLabel();
        jpCmdState = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        txtTotalPpto = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCurFormatter());
        btnCancelar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JFormattedTextField();
        txtMontoIni = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCurFormatter());
        jlPartida1 = new javax.swing.JLabel();
        txtCodContable = new javax.swing.JFormattedTextField();
        jpComandos = new javax.swing.JPanel();
        jbCreditoA = new javax.swing.JButton();
        jbTransfP = new javax.swing.JButton();
        jbRetornar = new javax.swing.JButton();
        lblMsj = new javax.swing.JLabel();
        jlFondo = new javax.swing.JLabel();

        setTitle("PRESUPUESTO DE LA INSTITUCIÓN");
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        jpFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setOpaque(false);
        jpFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        jpFondo.setLayout(new javax.swing.BoxLayout(jpFondo, javax.swing.BoxLayout.PAGE_AXIS));

        jpTipoPpto.setMaximumSize(new java.awt.Dimension(1060, 30));
        jpTipoPpto.setMinimumSize(new java.awt.Dimension(1060, 30));
        jpTipoPpto.setOpaque(false);
        jpTipoPpto.setPreferredSize(new java.awt.Dimension(1060, 30));
        jpTipoPpto.setLayout(new java.awt.GridBagLayout());

        bgPpto.add(btnPptoGasto);
        btnPptoGasto.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnPptoGasto.setSelected(true);
        btnPptoGasto.setText("PRESUPUESTO DE GASTO");
        btnPptoGasto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPptoGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPptoGastoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpTipoPpto.add(btnPptoGasto, gridBagConstraints);

        bgPpto.add(btnPptoIngreso);
        btnPptoIngreso.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnPptoIngreso.setText("PRESUPUESTO DE INGRESO");
        btnPptoIngreso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPptoIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPptoIngresoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 0);
        jpTipoPpto.add(btnPptoIngreso, gridBagConstraints);

        jpFondo.add(jpTipoPpto);

        jpFiltro.setMaximumSize(new java.awt.Dimension(1060, 35));
        jpFiltro.setMinimumSize(new java.awt.Dimension(1060, 35));
        jpFiltro.setOpaque(false);
        jpFiltro.setPreferredSize(new java.awt.Dimension(1060, 35));
        jpFiltro.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel3.setText("Orden");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(jLabel3, gridBagConstraints);

        cmbOrden.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        cmbOrden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cronológico", "Código", "Partida" }));
        cmbOrden.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbOrdenItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(cmbOrden, gridBagConstraints);

        jtFiltro.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtFiltro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtFiltro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtFiltro.setDoubleBuffered(true);
        jtFiltro.setMaximumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setMinimumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 0);
        jpFiltro.add(jLabel1, gridBagConstraints);

        jbResetear.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jbResetear.setText("RESET");
        jbResetear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jbResetear, gridBagConstraints);

        jcbFilter.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "Partida", "Monto Ini.", "Monto Act." }));
        jcbFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbFilter.setMaximumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setMinimumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jcbFilter, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel2.setText("EJERCICIO FISCAL: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel2, gridBagConstraints);

        bgEjeFis.add(rbEjeFisAct);
        rbEjeFisAct.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rbEjeFisAct.setSelected(true);
        rbEjeFisAct.setText("2018");
        rbEjeFisAct.setOpaque(false);
        rbEjeFisAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEjeFisActActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(rbEjeFisAct, gridBagConstraints);

        bgEjeFis.add(rbEjeFisNext);
        rbEjeFisNext.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rbEjeFisNext.setText("2019");
        rbEjeFisNext.setOpaque(false);
        rbEjeFisNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEjeFisNextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(rbEjeFisNext, gridBagConstraints);

        jpFondo.add(jpFiltro);

        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 380));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 380));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 380));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblMaster.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        tblMaster.setForeground(new java.awt.Color(0, 51, 153));
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Partida", "Monto Ini. Bs.", "Monto Disp. Bs.", "Cod. Contable", "Reg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
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
        tblMaster.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblMaster.setDoubleBuffered(true);
        tblMaster.setMaximumSize(new java.awt.Dimension(3000, 3000));
        tblMaster.setMinimumSize(new java.awt.Dimension(1500, 0));
        tblMaster.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMaster.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblMasterKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(150);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(300);
            tblMaster.getColumnModel().getColumn(2).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(2).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(3).setMinWidth(75);
            tblMaster.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblMaster.getColumnModel().getColumn(3).setMaxWidth(200);
            tblMaster.getColumnModel().getColumn(4).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(4).setPreferredWidth(200);
            tblMaster.getColumnModel().getColumn(4).setMaxWidth(300);
            tblMaster.getColumnModel().getColumn(5).setMinWidth(0);
            tblMaster.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblMaster.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jpFondo.add(jScrollPane1);

        jpEditables.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Partida"));
        jpEditables.setMaximumSize(new java.awt.Dimension(1060, 140));
        jpEditables.setMinimumSize(new java.awt.Dimension(1060, 140));
        jpEditables.setOpaque(false);
        jpEditables.setPreferredSize(new java.awt.Dimension(1060, 140));
        jpEditables.setLayout(new java.awt.GridBagLayout());

        jlCodigo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlCodigo.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpEditables.add(jlCodigo, gridBagConstraints);

        jlPartida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlPartida.setLabelFor(txtPartida);
        jlPartida.setText("Partida");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpEditables.add(jlPartida, gridBagConstraints);

        txtPartida.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        txtPartida.setForeground(new java.awt.Color(0, 51, 102));
        txtPartida.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPartida.setToolTipText("Longitud máxima 256 caracteres");
        txtPartida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPartida.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtPartida.setEnabled(false);
        txtPartida.setMinimumSize(new java.awt.Dimension(250, 21));
        txtPartida.setPreferredSize(new java.awt.Dimension(300, 21));
        txtPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPartidaActionPerformed(evt);
            }
        });
        txtPartida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPartidaKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jpEditables.add(txtPartida, gridBagConstraints);

        jlMonto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlMonto.setText("Monto Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        jpEditables.add(jlMonto, gridBagConstraints);

        jpCmdState.setOpaque(false);
        jpCmdState.setLayout(new java.awt.GridBagLayout());

        btnAgregar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnAgregar.setText("AGREGAR");
        btnAgregar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAgregar.setMaximumSize(new java.awt.Dimension(79, 19));
        btnAgregar.setMinimumSize(new java.awt.Dimension(79, 19));
        btnAgregar.setPreferredSize(new java.awt.Dimension(79, 19));
        btnAgregar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAgregarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnAgregarFocusLost(evt);
            }
        });
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(btnAgregar, gridBagConstraints);

        btnModificar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnModificar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnModificarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnModificarFocusLost(evt);
            }
        });
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(btnModificar, gridBagConstraints);

        jbEliminar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jbEliminar.setText("ELIMINAR");
        jbEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbEliminar.setMaximumSize(new java.awt.Dimension(79, 19));
        jbEliminar.setMinimumSize(new java.awt.Dimension(79, 19));
        jbEliminar.setPreferredSize(new java.awt.Dimension(79, 19));
        jbEliminar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jbEliminarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jbEliminarFocusLost(evt);
            }
        });
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(jbEliminar, gridBagConstraints);

        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblmonto1.setText("Total Presupuesto Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 180, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);

        txtTotalPpto.setEditable(false);
        txtTotalPpto.setBackground(new java.awt.Color(204, 102, 0));
        txtTotalPpto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalPpto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalPpto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalPpto.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtTotalPpto.setDoubleBuffered(true);
        txtTotalPpto.setFocusable(false);
        txtTotalPpto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotalPpto.setMinimumSize(new java.awt.Dimension(200, 21));
        txtTotalPpto.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(txtTotalPpto, gridBagConstraints);

        btnCancelar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jpCmdState, gridBagConstraints);

        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCodigo.setEnabled(false);
        txtCodigo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCodigo.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCodigo.setPreferredSize(new java.awt.Dimension(200, 21));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jpEditables.add(txtCodigo, gridBagConstraints);

        txtMontoIni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMontoIni.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoIni.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtMontoIni.setEnabled(false);
        txtMontoIni.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtMontoIni.setMinimumSize(new java.awt.Dimension(200, 21));
        txtMontoIni.setPreferredSize(new java.awt.Dimension(200, 21));
        txtMontoIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoIniActionPerformed(evt);
            }
        });
        txtMontoIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoIniKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpEditables.add(txtMontoIni, gridBagConstraints);

        jlPartida1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlPartida1.setLabelFor(txtPartida);
        jlPartida1.setText("Cod. Contable");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpEditables.add(jlPartida1, gridBagConstraints);

        txtCodContable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCodContable.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodContable.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCodContable.setEnabled(false);
        txtCodContable.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCodContable.setMinimumSize(new java.awt.Dimension(200, 21));
        txtCodContable.setPreferredSize(new java.awt.Dimension(200, 21));
        txtCodContable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodContableActionPerformed(evt);
            }
        });
        txtCodContable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodContableKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jpEditables.add(txtCodContable, gridBagConstraints);

        jpFondo.add(jpEditables);

        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());

        jbCreditoA.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jbCreditoA.setText("CREDITO ADICIONAL");
        jbCreditoA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbCreditoA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreditoAActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpComandos.add(jbCreditoA, gridBagConstraints);

        jbTransfP.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jbTransfP.setText("TRANSFERENCIA ENTRE PARTIDA");
        jbTransfP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbTransfP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTransfPActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpComandos.add(jbTransfP, gridBagConstraints);

        jbRetornar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jbRetornar.setText("RETORNAR");
        jbRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbRetornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRetornarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpComandos.add(jbRetornar, gridBagConstraints);

        lblMsj.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMsj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMsj.setMaximumSize(new java.awt.Dimension(500, 19));
        lblMsj.setMinimumSize(new java.awt.Dimension(500, 19));
        lblMsj.setPreferredSize(new java.awt.Dimension(500, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpComandos.add(lblMsj, gridBagConstraints);

        jpFondo.add(jpComandos);

        getContentPane().add(jpFondo);
        jpFondo.setBounds(0, 0, 1100, 650);

        jlFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        jlFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        getContentPane().add(jlFondo);
        jlFondo.setBounds(0, 0, 1100, 650);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        switch (winState) {
            case NORMAL:
                winState = WinState.ADD;
                UpdateJTable();
                UpdateEnabledAndFocus();
                break;

            case ADD:
                Agregar();
                UpdateJTable();
                UpdateEnabledAndFocus();

                break;
        }
}//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        switch (winState) {
            case NORMAL:

                java.awt.EventQueue.invokeLater(() -> {
                    new PptoMod(this, tabla).setVisible(true);
                    setVisible(false);
                });
                break;

            case MODIFY:
                Modificar();
                UpdateJTable();
                UpdateEnabledAndFocus();
                break;
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed

        if (JOptionPane.showConfirmDialog(this, "La operación de Eliminar es irreversible. ¿ Desea continuar ?",
            "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }

        java.awt.EventQueue.invokeLater(() -> {
            new PptoEli(this, tabla).setVisible(true);
            setVisible(false);
        });

    }//GEN-LAST:event_jbEliminarActionPerformed

    private void btnPptoGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPptoGastoActionPerformed
        tabla = capipsistema.Globales.TBL_PPTO_EGRESO;

        final JTextField jtCriterio = (JTextField) xjparam.get(JTFILTRO);
        if (jtCriterio != null) {
            jtCriterio.setText("");
            jtCriterio.requestFocusInWindow();
        }

        // null para eliminar el objeto anterior
        txtCodigo.setValue(null);
        txtCodigo.setFormatterFactory(PptoGlobales.getCodEgresoFormatterFactory());
        UpdateJTable();
        UpdateEnabledAndFocus();
    }//GEN-LAST:event_btnPptoGastoActionPerformed

    private void btnPptoIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPptoIngresoActionPerformed
        tabla = TBL_PPTO_INGRESO;

        final JTextField jtCriterio = (JTextField) xjparam.get(JTFILTRO);
        if (jtCriterio != null) {
            jtCriterio.setText("");
            jtCriterio.requestFocusInWindow();
        }

        // null para eliminar el objeto anterior
        txtCodigo.setValue(null);
        txtCodigo.setFormatterFactory(PptoGlobales.getCodIngresoFormatterFactory());
        UpdateJTable();
        UpdateEnabledAndFocus();
    }//GEN-LAST:event_btnPptoIngresoActionPerformed

    /**
     * Rev 25/11/2016
     *
     * @param evt
     */
    private void jbCreditoAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreditoAActionPerformed

        // Para que cuando retorne, actualice la vista
        update = true;
        final javax.swing.JFrame me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new CreditoAdicional(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_jbCreditoAActionPerformed

    /**
     * Rev 25/11/2016
     *
     * @param evt
     */
    private void jbTransfPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTransfPActionPerformed

        // Para que cuando retorne, actualice la vista
        update = true;

        final javax.swing.JFrame me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TraspPartidas(me).setVisible(true);
                me.setVisible(false);
            }
        });
    }//GEN-LAST:event_jbTransfPActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        txtCodigo.transferFocus();
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtMontoIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoIniActionPerformed
        switch (winState) {
            case ADD:
                btnAgregar.requestFocusInWindow();
                break;

            case MODIFY:
                btnModificar.requestFocusInWindow();
                break;

            case DELETE:
                jbEliminar.requestFocusInWindow();
                break;
        }
    }//GEN-LAST:event_txtMontoIniActionPerformed

    private void txtPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPartidaActionPerformed
        txtPartida.transferFocus();
    }//GEN-LAST:event_txtPartidaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        switch (winState) {
            case ADD:
            case MODIFY:
            case DELETE:
                winState = WinState.NORMAL;
                UpdateJTable();
                UpdateEnabledAndFocus();
                break;
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAgregarFocusGained
        getRootPane().setDefaultButton(btnAgregar);
    }//GEN-LAST:event_btnAgregarFocusGained

    private void btnModificarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnModificarFocusGained
        getRootPane().setDefaultButton(btnModificar);
    }//GEN-LAST:event_btnModificarFocusGained

    private void jbEliminarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jbEliminarFocusGained
        getRootPane().setDefaultButton(jbEliminar);
    }//GEN-LAST:event_jbEliminarFocusGained

    private void btnAgregarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAgregarFocusLost
        getRootPane().setDefaultButton(null);
    }//GEN-LAST:event_btnAgregarFocusLost

    private void btnModificarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnModificarFocusLost
        getRootPane().setDefaultButton(null);
    }//GEN-LAST:event_btnModificarFocusLost

    private void jbEliminarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jbEliminarFocusLost
        getRootPane().setDefaultButton(null);
    }//GEN-LAST:event_jbEliminarFocusLost

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        if (txtCodigo.getText().length() >= 32) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtPartidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPartidaKeyTyped
        if (txtPartida.getText().length() >= 256) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPartidaKeyTyped

    private void txtMontoIniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoIniKeyTyped
        if (txtMontoIni.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMontoIniKeyTyped

    private void rbEjeFisActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEjeFisActActionPerformed
        final Calendar fechaOp = Calendar.getInstance();
        ejeFis = fechaOp.get(Calendar.YEAR);
        ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;

        UpdatePanels();
    }//GEN-LAST:event_rbEjeFisActActionPerformed

    /**
     * Para Seleccionar el Siguiente año Fiscal Rev 25/11/2016
     *
     * @param evt
     */
    private void rbEjeFisNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEjeFisNextActionPerformed
        final Calendar fechaOp = Calendar.getInstance();
        ejeFis = fechaOp.get(Calendar.YEAR) + 1;
        ejeFisMes = ejeFis * 100 + (fechaOp.get(Calendar.MONTH) + 1);

        try {
            if (rbEjeFisNext.isSelected()) {
                final ResultSet rsNext = ConnPpto.executeQuery("SELECT COUNT(*) FROM " + tabla + " WHERE ejefis=" + ejeFis);
                if (rsNext.next() && (rsNext.getInt("COUNT(*)") > 0)) {
                    if (JOptionPane.showConfirmDialog(this, "Existen registros para el proximo año Fiscal" + System.getProperty("line.separator")
                        + " ¿ Desea eliminar estos registros ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                        ConnPpto.execute("DELETE FROM " + tabla + " WHERE ejefis= " + ejeFis);

                        // Verificar si existe un presupuesto actual
                        final ResultSet rsAct = ConnPpto.executeQuery("SELECT COUNT(*) FROM " + tabla + " WHERE ejefis=" + (ejeFis - 1));
                        if (rsAct.next() && (rsAct.getInt("COUNT(*)") > 0)) {
                            if (JOptionPane.showConfirmDialog(this, "¿ Desea copiar los registros actuales, al nuevo Ejercicio Fiscal ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                                final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());
                                final ResultSet rsCopy = ConnPpto.executeQuery("SELECT * FROM " + tabla + " WHERE ejefis=" + (ejeFis - 1));
                                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                while (rsCopy.next()) {
                                    PreparedStatement pstCopy = ConnPpto.conn.prepareStatement("INSERT INTO " + tabla + " "
                                        + "(codigo, partida, monto_ini, monto, " // 1, 2, 3, 4
                                        + "ejefis, ejefismes, fchsession, " // 5, 6, 7
                                        + "idsession, iduser, anulado_sn) " // 8, 9, 10
                                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                    pstCopy.setString(1, rsCopy.getString("codigo"));
                                    pstCopy.setString(2, rsCopy.getString("partida"));
                                    pstCopy.setString(3, rsCopy.getString("monto_ini"));
                                    pstCopy.setBigDecimal(4, ZERO); // monto
                                    pstCopy.setInt(5, ejeFis);
                                    pstCopy.setInt(6, ejeFisMes); // ejefismes
                                    pstCopy.setDate(7, fchSession);
                                    pstCopy.setLong(8, UserPassIn.getIdSession());
                                    pstCopy.setLong(9, UserPassIn.getIdUser());
                                    pstCopy.setString(10, "N");
                                    pstCopy.executeUpdate();
                                }
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                                JOptionPane.showMessageDialog(null, "Se han copiado " + rsNext.getInt("COUNT(*)") + " registros");
                            }
                        }
                    }
                } else { // No hay registros para el proximo Ejercicio Fiscal if (rsNext.next() && (rsNext.getInt("COUNT(*)") > 0)) {
                    // Verificar si existe un presupuesto actual
                    final ResultSet rsAct = ConnPpto.executeQuery("SELECT COUNT(*) FROM " + tabla + " WHERE ejefis=" + (ejeFis - 1));
                    if (rsAct.next() && (rsAct.getInt("COUNT(*)") > 0)) {
                        if (JOptionPane.showConfirmDialog(this, "No existe presupuesto para el nuevo Ejercicio Fiscal" + System.getProperty("line.separator")
                            + "¿ Desea copiar los registros actuales, al nuevo Ejercicio Fiscal ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                            final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());
                            final ResultSet rsCopy = ConnPpto.executeQuery("SELECT * FROM " + tabla + " WHERE ejefis=" + (ejeFis - 1));

                            setCursor(new Cursor(Cursor.WAIT_CURSOR));
                            while (rsCopy.next()) {
                                PreparedStatement pstCopy = ConnPpto.conn.prepareStatement("INSERT INTO " + tabla + ""
                                    + "(codigo, partida, monto_ini, monto, "
                                    + "ejefis, ejefismes, fchsession, "
                                    + "idsession, iduser, anulado_sn) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                pstCopy.setString(1, rsCopy.getString("codigo"));
                                pstCopy.setString(2, rsCopy.getString("partida"));
                                pstCopy.setString(3, rsCopy.getString("monto_ini"));
                                pstCopy.setBigDecimal(4, ZERO); // monto
                                pstCopy.setInt(5, ejeFis);
                                pstCopy.setInt(6, ejeFisMes); // ejefismes
                                pstCopy.setDate(7, fchSession);
                                pstCopy.setLong(8, UserPassIn.getIdSession());
                                pstCopy.setLong(9, UserPassIn.getIdUser());
                                pstCopy.setString(10, "N");
                                pstCopy.executeUpdate();
                            }
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                            final ResultSet rs2 = ConnPpto.executeQuery("SELECT COUNT(*) FROM " + tabla + " WHERE ejefis=" + ejeFis);
                            if (rs2.next()) {
                                if (rs2.getInt("COUNT(*)") > 0) {
                                    JOptionPane.showMessageDialog(null, "Se han copiado " + rs2.getInt("COUNT(*)") + " registros");
                                }
                            }
                        }
                    }
                } // else del if (rsNext.next() && (rsNext.getInt("COUNT(*)") > 0)) {

            } // if (rbEjeFisNext.isSelected()) {

        } catch (final Exception _ex) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, _ex);
        }

        UpdatePanels();
    }//GEN-LAST:event_rbEjeFisNextActionPerformed

    private void tblMasterKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMasterKeyTyped

    }//GEN-LAST:event_tblMasterKeyTyped

    private void jbRetornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRetornarActionPerformed

    }//GEN-LAST:event_jbRetornarActionPerformed

    private void txtCodContableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodContableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodContableActionPerformed

    private void txtCodContableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodContableKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodContableKeyTyped

    private void cmbOrdenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbOrdenItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            UpdateJTable();
        }
    }//GEN-LAST:event_cmbOrdenItemStateChanged

    /**
     * Rev 25/11/2016
     *
     * @param evt
     */
    @Override
    protected void formWindowActivated(java.awt.event.WindowEvent evt) {
        super.formWindowActivated(evt);

        if (update) {
            update = false;
            UpdatePanels();
            UpdateEnabledAndFocus();
        }
    }

    /**
     * Agregar el registro a la Base de datos
     */
    private void Agregar() {
        if (winState != WinState.ADD) {
            return;
        }

        final String codigo = txtCodigo.getText();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Código inválido");
            txtCodigo.requestFocusInWindow();
            return;
        }

        final String cod_contable = txtCodContable.getText().trim().toUpperCase();

        final String partida = txtPartida.getText().trim().toUpperCase();
        if (partida.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Partida inválida");
            txtPartida.requestFocusInWindow();
            return;
        }

        final String strMonto = txtMontoIni.getText().trim();
        if (strMonto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            txtMontoIni.requestFocusInWindow();
            return;
        }

        final BigDecimal monto;
        try {
            monto = Format.toBigDec(strMonto);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            return;
        }

        if ((monto == null) || (monto.compareTo(BigDecimal.ZERO) < 0)) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            txtMontoIni.requestFocusInWindow();
            return;
        }

        try {
            // Verificar que no existe el Codigo en la DB
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + tabla + " WHERE codigo='" + codigo + "' AND ejefis=" + ejeFis);
            if (rs.next()) {
                PptoModel reg = new PptoModel(rs);
                if (reg.getAnulado_sn().equals("N")) {
                    JOptionPane.showMessageDialog(null, "Ya existe un registro con el mismo Código. Intente de nuevo.");
                    txtCodigo.requestFocusInWindow();
                } else {
                    if (JOptionPane.showConfirmDialog(this, "Ya existe un registro ANULADO (Eliminado) con el mismo Código." + System.getProperty("line.separator") + "¿ Desea recuperar dicho Registro ?",
                        "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                        ConnPpto.conn.prepareStatement("UPDATE " + tabla + " SET anulado_sn= 'N' WHERE  codigo='" + reg.getCodigo() + "' AND ejefis=" + ejeFis).executeUpdate();
                    }
                }
                return;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            txtCodigo.requestFocusInWindow();
            return;
        }

        final Calendar fechaOp = Calendar.getInstance();
        final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());

        try {
            final PreparedStatement pst = ConnPpto.conn.prepareStatement("INSERT INTO " + tabla + "(codigo, partida, monto_ini, monto, ejefis, ejefismes, fchsession, idsession, iduser, cod_contable) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, codigo);
            pst.setString(2, partida);
            pst.setBigDecimal(3, monto); // monto inicial
            pst.setBigDecimal(4, monto);
            pst.setInt(5, ejeFis);
            pst.setInt(6, ejeFisMes);
            pst.setDate(7, fchSession);
            pst.setLong(8, UserPassIn.getIdSession());
            pst.setLong(9, UserPassIn.getIdUser());
            pst.setString(10, cod_contable);
            pst.executeUpdate();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        txtCodigo.requestFocusInWindow();
    }

    /**
     * Rev 04/02/2017
     *
     * @return
     */
    private void Modificar() {

        if (winState != WinState.MODIFY) {
            return;
        }

        int selRow = tblMaster.getSelectedRow();
        if (selRow < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return;
        }

        PptoModel regSel = (PptoModel) tblMaster.getValueAt(selRow, colReg);

        long id = ((ID) regSel).getId();

        String codigo = txtCodigo.getText();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Código inválido");
            txtCodigo.requestFocusInWindow();
            return;
        }

        final String cod_contable = txtCodContable.getText().trim().toUpperCase();

        String partida = txtPartida.getText().trim().toUpperCase();

        if (partida.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Partida inválida");
            txtPartida.requestFocusInWindow();
            return;
        }

        String strMonto = txtMontoIni.getText().trim();
        if (strMonto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            txtMontoIni.requestFocusInWindow();
            return;
        }

        BigDecimal monto_ini_nuevo;
        try {
            monto_ini_nuevo = Format.toBigDec(strMonto);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            return;
        }

        if (monto_ini_nuevo.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Monto inválido");
            txtMontoIni.requestFocusInWindow();
            return;
        }

        try {
            // Se quiere cambiar el codigo de la partida
            if (!regSel.getCodigo().equals(codigo)) {

                // Verificar que no se han realizado operaciones sobre la partida
                if (regSel.getMontoIni().compareTo(regSel.getMonto()) == 0) {

                    final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + tabla + " WHERE codigo= '" + codigo + "' AND ejefis= " + ejeFis);
                    if (rs.next()) {
                        PptoModel reg = new PptoModel(rs);
                        if (reg.getAnulado_sn().equals("N")) {
                            JOptionPane.showMessageDialog(null, "Ya existe un registro con el mismo Código. Intente de nuevo.");
                            txtCodigo.requestFocusInWindow();
                        } else {
                            if (JOptionPane.showConfirmDialog(this, "Ya existe un registro ANULADO (Eliminado) con el mismo Código." + System.getProperty("line.separator") + "¿ Desea recuperar dicho Registro ?",
                                "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                                if (ConnPpto.conn.prepareStatement("UPDATE " + tabla + " SET anulado_sn= 'N' WHERE  codigo= '" + reg.getCodigo() + "' AND ejefis= " + ejeFis).executeUpdate() != 1) {
                                    throw new Exception("Cantidad de registros actualizados inválido");
                                }
                            }
                        }
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error. No es posible redefinir el código de la Partida" + System.getProperty("line.separator") + "Ya se han realizado operaciones sobre este Registro");
                    return;
                }
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            txtCodigo.requestFocusInWindow();
            return;
        }

        try {
            Conn.BeginTransaction();

            if (regSel.getMontoIni().compareTo(regSel.getMonto()) != 0) {
                if (JOptionPane.showConfirmDialog(this, "Este registro ya cuenta con operaciones en la Base de Datos." + System.getProperty("line.separator") + "¿ Desea actualizar el monto inicial de todos modos ?",
                    "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                    return; // Abortar operacion
                }
            }

            final BigDecimal monto_nuevo = regSel.getMonto().subtract(regSel.getMontoIni()).add(monto_ini_nuevo).setScale(2, RoundingMode.HALF_UP);
            if (monto_nuevo.compareTo(ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "Error. No es posible actualizar" + System.getProperty("line.separator") + "El monto neto actual de las operaciones es inválido (negativo)");
                return;
            }

            // Actualizar o reversar el monto inicial, y el monto actual
            PreparedStatement pst = ConnPpto.conn.prepareStatement("UPDATE " + tabla + " SET codigo= ?, partida= ?, monto_ini= ?, monto= ?, cod_contable= ? "
                + "WHERE id= ? AND ejefis= ?");
            pst.setString(1, codigo);
            pst.setString(2, partida);
            pst.setBigDecimal(3, monto_ini_nuevo);
            pst.setBigDecimal(4, monto_nuevo);
            pst.setLong(5, id);
            pst.setLong(6, ejeFis);
            pst.setString(7, cod_contable);
            int regAct = pst.executeUpdate();
            if (regAct != 1) {
                throw new Exception("Error" + System.getProperty("line.separator") + "Fueron actualizados " + regAct + " registros");
            }

            Conn.EndTransaction();

        } catch (final Exception _ex) {

            try {
                Conn.RollBack();
            } catch (final Exception _ex2) {
                JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
            }

            JOptionPane.showMessageDialog(null, "Error" + System.getProperty("line.separator") + _ex);
            txtCodigo.requestFocusInWindow();
            return;
        }

        // Reposicionar el registro seleccionado
        // Depende del estado
        int lastRow = tblMaster.getRowCount() - 1;
        if (lastRow >= 0) {
            if (selRow < 0) {
                selRow = 0;
            } else if (selRow < lastRow) {
                selRow++;
            } else {
                selRow = lastRow;
            }

            tblMaster.scrollRectToVisible(tblMaster.getCellRect(selRow, 0, true));
            tblMaster.clearSelection();
            tblMaster.setRowSelectionInterval(selRow, selRow);
        }
    }

    /**
     * Rev 25/11/2016
     *
     * @return
     */
    private boolean Eliminar() {

        int selRow = tblMaster.getSelectedRow();

        if (selRow < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return false;
        }

        String cod = tblMaster.getValueAt(selRow, colCodigo).toString();
        try {
            ConnPpto.conn.prepareStatement("UPDATE " + tabla + " SET anulado_sn= 'S' WHERE  codigo='" + cod + "' AND ejefis=" + ejeFis).executeUpdate();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        return true;
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
                new PptoInstitucion(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgEjeFis;
    private javax.swing.ButtonGroup bgPpto;
    private javax.swing.ButtonGroup btgOrden;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JToggleButton btnPptoGasto;
    private javax.swing.JToggleButton btnPptoIngreso;
    private javax.swing.JComboBox cmbOrden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCreditoA;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbResetear;
    private javax.swing.JButton jbRetornar;
    private javax.swing.JButton jbTransfP;
    private javax.swing.JComboBox<String> jcbFilter;
    private javax.swing.JLabel jlCodigo;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JLabel jlMonto;
    private javax.swing.JLabel jlPartida;
    private javax.swing.JLabel jlPartida1;
    private javax.swing.JPanel jpCmdState;
    private javax.swing.JPanel jpComandos;
    private javax.swing.JPanel jpEditables;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JPanel jpFondo;
    private javax.swing.JPanel jpTipoPpto;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JLabel lblmonto1;
    private javax.swing.JRadioButton rbEjeFisAct;
    private javax.swing.JRadioButton rbEjeFisNext;
    private javax.swing.JTable tblMaster;
    private javax.swing.JFormattedTextField txtCodContable;
    private javax.swing.JFormattedTextField txtCodigo;
    private javax.swing.JFormattedTextField txtMontoIni;
    private javax.swing.JTextField txtPartida;
    private javax.swing.JFormattedTextField txtTotalPpto;
    // End of variables declaration//GEN-END:variables
}
