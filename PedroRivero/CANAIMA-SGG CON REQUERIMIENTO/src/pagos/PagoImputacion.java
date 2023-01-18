/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package pagos;

import capipsistema.CapipPropiedades;
import static capipsistema.CapipPropiedades.CAPIP_IVA_APLICADO;
import static capipsistema.CapipPropiedades.CAPIP_IVA_PARTIDA;
import capipsistema.Conn;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import causados.Causado;
import compromisos.Compromiso;
import compromisos.CompromisosConsultar;
import gen_next_num.GenNextNum;
import impuestos.ImpuestoRetencion;
import impuestos.OrdPagCuentaSel;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import static java.lang.Math.min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.AvanceEfectivoModel;
import modelos.CauModel;
import modelos.CuentaModel;
import modelos.IvaAplicModel;
import modelos.PptoModel;
import utils.CapipState;
import utils.CuentaUpdatable;
import utils.Format;
import utils.TipoCompr;

public final class PagoImputacion extends javax.swing.JFrame implements CuentaUpdatable {

    private static final BigDecimal[] porcRetIslr = {BigDecimal.valueOf(0.00d),
        BigDecimal.valueOf(0.01d),
        BigDecimal.valueOf(0.02d),
        BigDecimal.valueOf(0.03d),
        BigDecimal.valueOf(0.04d),
        BigDecimal.valueOf(0.05d),
        BigDecimal.valueOf(0.06d)};

    private static final BigDecimal[] porcRetIva = {BigDecimal.valueOf(0.00d),
        BigDecimal.valueOf(0.75d),
        BigDecimal.valueOf(1.00d)};

    private final java.awt.Window parent;
    private boolean conIva;

    private static final long serialVersionUID = 1L;

    private final int mcolCantidad = 0;
    private final int mcolDescripcion = 1;
    private final int mcolPUnitario = 2;
    private final int mcolSubTotal = 3;
    private final int mcolCodPartida = 4;
    private final int mcolDescPartida = 5;

    private BigDecimal iva_grav_bs;
    private BigDecimal IVA_DEC_VALUE;

    private CapipState estado;

    /**
     * Mantiene la cuenta sobre la cual se generara las operaciones
     */
    private CuentaModel regCuenta;

    /**
     * Mantiene el generador de numeros o ID para los compromisos
     */
    private final GenNextNum comprNextNum;

    /**
     * Mantiene el generador de numeros o ID para los causados
     */
    private final GenNextNum cauNextNum;

    /**
     * Rev
     *
     * @param _parent
     */
    public PagoImputacion(final java.awt.Window _parent) {
        super();
        initComponents();

        this.parent = _parent;
        comprNextNum = new GenNextNum("CO");
        cauNextNum = new GenNextNum("CAU");
        regCuenta = null;

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
     * Rev 06/11/2016
     */
    private void setCompBehavior() {
        Compromiso.setTblBenefBehavior(tblBenef, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                actSelectBenef();
            }
        });

        Compromiso.setTblItemBehavior(tblItem);

        Compromiso.setTblPartidaBehavior(tblPartida, new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                actComprInsertItem(false);
            }
        });

        Compromiso.setTxtFechaFactBehavior(txtFechaFact);
        Compromiso.setTxtCantBehavior((JFormattedTextField) txtCant, (JFormattedTextField) txtPUnitario, txtSubTotal);
        Compromiso.setTxtPUnitarioBehavior((JFormattedTextField) txtCant, (JFormattedTextField) txtPUnitario, txtSubTotal);
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {

        regCuenta = null;
        switch (Globales.ORD_PAG) {
            case ABSOLUTO:
                break;

            case RELATIVO:
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
        }

        clearComp();
    }

    private void clearComp() throws HeadlessException {
        Compromiso.cargarComboIva(cmbIva);

        iva_grav_bs = BigDecimal.ZERO;
        IVA_DEC_VALUE = BigDecimal.ZERO;
        conIva = false;

        txtRazonSocial.setText("");
        txtRIF_CI.setText("");
        txtNroFact.setText("");
        final String sfecha = Format.toStr(new java.sql.Date(Globales.getServerTimeStamp().getTime()));
        txtFechaFact.setText(sfecha);
        txtFecha.setText(sfecha);

        ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);
        txtDesc.setText("");
        ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);

        txtSubTotal.setText("0,00");

        cmbIvaPorcRet.setEnabled(false);
        cmbIvaPorcRet.setSelectedIndex(-1);
        txtIvaRet.setText("0,00");

        cmbIslrPorc.setEnabled(false);
        cmbIslrPorc.setSelectedIndex(-1);
        txtIslrRet.setText("0,00");

        txtOtrasRetenciones.setText("0,00");
        txtTotalRet.setText("0,00");

        txaConcepto.setText("");

        txtSumaTotalCau.setText("0,00");
        txtIva_bs.setText("0,00");
        txtA_Pagar.setText("0,00");
        txtSumaTotalCau_menosRet.setText("0,00");

        txtTotalBs.setText("0,00");
        txtBancoCuenta.setText("");
        txtPendImputar.setText("0,00");

        btnInsertar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnIVA.setEnabled(true);
        btnAtras.setEnabled(false);
        btnGuardar.setEnabled(false);

        // Limpiar tabla
        final DefaultTableModel model = (DefaultTableModel) tblItem.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        ImpuestoRetencion.checkIvaNextId();
        ImpuestoRetencion.checkIslrNextId();

        try {
            txtID_Compr.setText(String.valueOf(comprNextNum.checkNum()));
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al generar Num. Compromiso" + System.getProperty("line.separator") + _ex);
            txtID_Compr.setText("0");
        }

        try {
            txtNumCausado.setText(String.valueOf(cauNextNum.checkNum()));
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + _ex);
            txtNumCausado.setText("1");
        }

        // Dado que este campo sera de entrada, con este valor se valida y enlaza el pago a la imputación,
        // hay que verificar la imputación a la orden de avance de efectivo
        txtOrdPago.setText("");

        tblItem.clearSelection();
        final DefaultTableModel modelItem = (DefaultTableModel) tblItem.getModel();
        modelItem.getDataVector().removeAllElements();
        modelItem.fireTableDataChanged();

        Compromiso.UpdateTblPartida(tblPartida);
        Compromiso.UpdateTblBenef(tblBenef);

        tblBenef.requestFocusInWindow();

        if (tblBenef.getRowCount() > 0) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    tblBenef.setRowSelectionInterval(0, 0);
                }
            });
        }

        UpdateEdo(CapipState.SEL_ORDEN);
    }

    /**
     * Rev 07/11/2016
     *
     */
    void calcularTotal() {
        BigDecimal total_bs = BigDecimal.ZERO;

        for (int i = 0; i < tblItem.getRowCount(); i++) {
            total_bs = total_bs.add(Format.toBigDec((double) tblItem.getValueAt(i, mcolSubTotal)));
        }

        txtTotalBs.setText(Format.toStr(total_bs));

        BigDecimal ivaRet;
        BigDecimal islrRet;
        try {
            ivaRet = Format.toBigDec(txtIvaRet.getText());
            islrRet = Format.toBigDec(txtIslrRet.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al formateat los totales" + System.getProperty("line.separator") + _ex);
            ivaRet = BigDecimal.ZERO;
            islrRet = BigDecimal.ZERO;
        }

        BigDecimal montoAPagar = total_bs.subtract(ivaRet).subtract(islrRet).setScale(2, RoundingMode.HALF_UP);

        try {

            txtIva_bs.setText(Format.toStr(iva_grav_bs.multiply(IVA_DEC_VALUE).setScale(2, RoundingMode.HALF_UP)));
            txtSumaTotalCau.setText(txtTotalBs.getText());
            txtSumaTotalCau_menosRet.setText(Format.toStr(montoAPagar));
            txtA_Pagar.setText(Format.toStr(montoAPagar));
            txtResta.setText("0,00");
            txtMontoPagado.setText("0,00");
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al formatear los totales" + System.getProperty("line.separator") + _ex);
            txtIvaRet.setText("0,00");
            txtTotalRet.setText("0,00");
            txtIva_bs.setText("0,00");
            txtSumaTotalCau.setText("0,00");
            txtSumaTotalCau_menosRet.setText("0,00");
            txtA_Pagar.setText("0,00");
            txtResta.setText("0,00");
            txtMontoPagado.setText("0,00");
        }
    }

    /**
     * Rev 07/11/2016
     *
     * @param isIVA
     */
    private void actComprInsertItem(boolean _isIVA) {

        if (!tblPartida.isEnabled()) {
            return;
        }

        // Validar cantidad
        final BigDecimal cant;
        try {
            final JFormattedTextField txtAux = (JFormattedTextField) txtCant;
            txtAux.commitEdit();
            cant = (BigDecimal) txtAux.getValue();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida");
            tblPartida.clearSelection();
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return;
        }

        if (cant.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida");
            tblPartida.clearSelection();
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return;
        }

        // Validar descripción
        final String sAux = txtDesc.getText().trim();

        if (sAux.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descripción inválida");
            tblPartida.clearSelection();
            java.awt.EventQueue.invokeLater(txtDesc::requestFocusInWindow);
            return;
        }
        final String sDesc = sAux.toUpperCase().substring(0, min(sAux.length(), 128));

        // Validar precio unitario
        final BigDecimal punitario;
        try {
            final JFormattedTextField txtAux = (JFormattedTextField) txtPUnitario;
            txtAux.commitEdit();
            punitario = (BigDecimal) txtAux.getValue();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Precio Unitario inválido");
            tblPartida.clearSelection();
            java.awt.EventQueue.invokeLater(txtPUnitario::requestFocusInWindow);
            return;
        }

        if (punitario.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Precio Unitario inválido");
            tblPartida.clearSelection();
            java.awt.EventQueue.invokeLater(txtPUnitario::requestFocusInWindow);
            return;
        }

        final BigDecimal subtotal = cant.multiply(punitario);
        txtSubTotal.setText(Format.toStr(subtotal));

        final String sCodPartida;
        final String sDescPartida;

        // Verificar si se trata de la inclusión del IVA
        if (_isIVA) {
            if (cmbIva.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un IVA");
                java.awt.EventQueue.invokeLater(tblItem::requestFocusInWindow);
                return;
            }

            final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
            final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
            PptoModel regPpto = null;
            try {
                regPpto = PptoModel.getReg_x_Id("presupe", regIva.getId_part_ppto());
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, "Error al recuperar el IVA" + System.getProperty("line.separator") + _ex);
                java.awt.EventQueue.invokeLater(tblItem::requestFocusInWindow);
                return;
            }

            sCodPartida = regPpto.getCodigo();
            sDescPartida = regPpto.getPartida();
        } else {
            final int row = tblPartida.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                java.awt.EventQueue.invokeLater(tblPartida::requestFocusInWindow);
                return;
            }

            sCodPartida = (String) tblPartida.getValueAt(row, 0);
            for (PptoModel partIva : CAPIP_IVA_PARTIDA.values()) {
                if (sCodPartida.equals(partIva.getCodigo())) {
                    JOptionPane.showMessageDialog(null, "Esta partida no puede insertarse directamente");
                    tblPartida.clearSelection();
                    java.awt.EventQueue.invokeLater(tblPartida::requestFocusInWindow);
                    return;
                }
            }

            sDescPartida = (String) tblPartida.getValueAt(row, 1);
        }

        final Object[] datos = new Object[6];

        final DefaultTableModel model = (DefaultTableModel) tblItem.getModel();
        datos[mcolCantidad] = Format.toDouble(cant);
        datos[mcolDescripcion] = sDesc;
        datos[mcolPUnitario] = Format.toDouble(punitario);
        datos[mcolSubTotal] = Format.toDouble(subtotal);
        datos[mcolCodPartida] = sCodPartida;
        datos[mcolDescPartida] = sDescPartida;
        model.addRow(datos);

        // Limpiar campos
        ((JFormattedTextField) txtCant).setValue(BigDecimal.ZERO);
        txtDesc.setText("");
        ((JFormattedTextField) txtPUnitario).setValue(BigDecimal.ZERO);
        txtSubTotal.setText("0,00");

        if (tblPartida.getRowCount() > 0) {
            tblPartida.setRowSelectionInterval(0, 0);
            tblPartida.scrollRectToVisible(tblPartida.getCellRect(0, 0, true));
        }

        // Inicializar los impuestos
        if (conIva) {
            cmbIvaPorcRet.setSelectedIndex(-1);
            cmbIvaPorcRet.setEnabled(true);
        } else {
            cmbIvaPorcRet.setSelectedItem(0);
            cmbIvaPorcRet.setEnabled(false);
        }

        cmbIslrPorc.setSelectedIndex(-1);

        calcularTotal();

        java.awt.EventQueue.invokeLater(txtCant::requestFocus);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btngPorcIva = new javax.swing.ButtonGroup();
        cmbIva = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        txtNroFact = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaConcepto = new javax.swing.JTextArea();
        txtSubTotal = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblItem = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPartida = new javax.swing.JTable();
        txtCant = textfield_decimal.DecimalTextField.getTextField();
        txtPUnitario = textfield_decimal.DecimalTextField.getTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNumControl = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnInsertar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnIVA = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtCheque = new javax.swing.JTextField();
        txtPendImputar = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtBancoCuenta = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtSumaTotalCau_menosRet = new javax.swing.JFormattedTextField();
        txtSumaTotalCau = new javax.swing.JFormattedTextField();
        txtIva_bs = new javax.swing.JFormattedTextField();
        txtA_Pagar = textfield_decimal.DecimalTextField.getTextField();
        txtResta = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        txtMontoPagado = new javax.swing.JFormattedTextField();
        jLabel35 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtIvaRet = new javax.swing.JFormattedTextField();
        txtIslrRet = new javax.swing.JFormattedTextField();
        txtOtrasRetenciones = new javax.swing.JFormattedTextField();
        txtTotalRet = new javax.swing.JFormattedTextField();
        jPanel6 = new javax.swing.JPanel();
        cmbIvaPorcRet = new javax.swing.JComboBox();
        cmbIslrPorc = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtID_Compr = new javax.swing.JFormattedTextField();
        txtNumCausado = new javax.swing.JFormattedTextField();
        txtOrdPago = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JFormattedTextField();
        jLabel30 = new javax.swing.JLabel();
        txtTotalBs = new javax.swing.JFormattedTextField();
        txtFechaFact = new javax.swing.JFormattedTextField();
        jLabel34 = new javax.swing.JLabel();
        txtRIF_CI = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBenef = new javax.swing.JTable();
        btnAtras = new javax.swing.JButton();
        btnEditarCheque = new javax.swing.JButton();
        chkISLR = new javax.swing.JCheckBox();
        jLabel118 = new javax.swing.JLabel();

        cmbIva.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        cmbIva.setMinimumSize(new java.awt.Dimension(64, 27));
        cmbIva.setPreferredSize(new java.awt.Dimension(64, 27));
        cmbIva.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbIvaItemStateChanged(evt);
            }
        });
        cmbIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIvaActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("IMPUTACIÓN AVANCE DE EFECTIVO");
        setMinimumSize(new java.awt.Dimension(1150, 710));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("Razón Social:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 103, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("RIF / CI:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 60, -1));

        txtRazonSocial.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtRazonSocial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtRazonSocial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRazonSocial.setEnabled(false);
        getContentPane().add(txtRazonSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 290, 30));

        txtNroFact.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtNroFact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNroFact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNroFact.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNroFact.setEnabled(false);
        txtNroFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroFactActionPerformed(evt);
            }
        });
        txtNroFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNroFactKeyTyped(evt);
            }
        });
        getContentPane().add(txtNroFact, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 142, 30));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(815, 540));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel9.setText("Cant:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 39, 30));

        jLabel10.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel10.setText("P/Und:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 50, 30));

        jLabel11.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Descripción:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 90, 30));

        jLabel12.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel12.setText("Sub-Total");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 40, 70, 30));

        txtDesc.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDesc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDesc.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescActionPerformed(evt);
            }
        });
        jPanel1.add(txtDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 350, 30));

        jLabel7.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Concepto de la Orden");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 170, -1));

        txaConcepto.setColumns(20);
        txaConcepto.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txaConcepto.setLineWrap(true);
        txaConcepto.setRows(5);
        txaConcepto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txaConcepto.setEnabled(false);
        txaConcepto.setSelectionColor(new java.awt.Color(175, 204, 125));
        txaConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txaConceptoKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txaConcepto);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 750, 60));

        txtSubTotal.setBackground(new java.awt.Color(175, 204, 125));
        txtSubTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSubTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubTotal.setEnabled(false);
        txtSubTotal.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jPanel1.add(txtSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 190, 30));

        tblItem.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        tblItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cant.", "Descripción", "P. Unitario Bs.", "Sub. Total Bs.", "Cod. Partida", "Partida"
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
        tblItem.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblItem.setEnabled(false);
        tblItem.setSelectionBackground(new java.awt.Color(175, 204, 125));
        jScrollPane2.setViewportView(tblItem);
        if (tblItem.getColumnModel().getColumnCount() > 0) {
            tblItem.getColumnModel().getColumn(0).setMinWidth(25);
            tblItem.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblItem.getColumnModel().getColumn(0).setMaxWidth(75);
            tblItem.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblItem.getColumnModel().getColumn(2).setPreferredWidth(125);
            tblItem.getColumnModel().getColumn(3).setPreferredWidth(125);
            tblItem.getColumnModel().getColumn(4).setMinWidth(100);
            tblItem.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblItem.getColumnModel().getColumn(4).setMaxWidth(180);
            tblItem.getColumnModel().getColumn(5).setPreferredWidth(350);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 80, 750, 100));

        tblPartida.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        tblPartida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "PARTIDA", "MONTO BS."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPartida.setEnabled(false);
        tblPartida.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblPartida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPartidaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPartida);
        if (tblPartida.getColumnModel().getColumnCount() > 0) {
            tblPartida.getColumnModel().getColumn(0).setMinWidth(100);
            tblPartida.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblPartida.getColumnModel().getColumn(0).setMaxWidth(200);
            tblPartida.getColumnModel().getColumn(2).setMinWidth(150);
            tblPartida.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblPartida.getColumnModel().getColumn(2).setMaxWidth(250);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 190, 750, 100));

        txtCant.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtCant.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCant.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCant.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtCant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantActionPerformed(evt);
            }
        });
        txtCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantKeyTyped(evt);
            }
        });
        jPanel1.add(txtCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 60, 35));

        txtPUnitario.setBackground(new java.awt.Color(175, 204, 125));
        txtPUnitario.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtPUnitario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPUnitario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPUnitario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPUnitarioActionPerformed(evt);
            }
        });
        txtPUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPUnitarioKeyTyped(evt);
            }
        });
        jPanel1.add(txtPUnitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 140, 31));

        jLabel18.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel18.setText("Núm. Control");
        jLabel18.setToolTipText("");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 100, 30));

        txtNumControl.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtNumControl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumControl.setToolTipText("Número asociado a la Factura");
        txtNumControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNumControl.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNumControl.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtNumControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumControlActionPerformed(evt);
            }
        });
        txtNumControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumControlKeyTyped(evt);
            }
        });
        jPanel1.add(txtNumControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 150, 31));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 770, 390));

        jLabel14.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BENEFICIARIOS");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 170, 120, 20));

        btnGuardar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnGuardar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(685, 630, 110, 40));

        btnInsertar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnInsertar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnInsertar.setText("INSERTAR");
        btnInsertar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnInsertar.setEnabled(false);
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 110, 40));

        btnEliminar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnEliminar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnEliminar.setText("ELIM. ULT.");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 630, 110, 40));

        btnIVA.setBackground(java.awt.SystemColor.inactiveCaption);
        btnIVA.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnIVA.setText("IVA");
        btnIVA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnIVA.setEnabled(false);
        btnIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIVAActionPerformed(evt);
            }
        });
        getContentPane().add(btnIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 630, 60, 40));

        btnConsultar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnConsultar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        getContentPane().add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 630, 140, 40));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCheque.setBackground(new java.awt.Color(175, 204, 125));
        txtCheque.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtCheque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCheque.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCheque.setEnabled(false);
        txtCheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtChequeKeyTyped(evt);
            }
        });
        jPanel3.add(txtCheque, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 140, 30));

        txtPendImputar.setEditable(false);
        txtPendImputar.setBackground(new java.awt.Color(175, 204, 125));
        txtPendImputar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtPendImputar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPendImputar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPendImputar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPendImputar.setEnabled(false);
        jPanel3.add(txtPendImputar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 230, 30));

        jLabel33.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel33.setText("CHEQUE Nº");
        jPanel3.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 70, 30));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("MONTO PENDIENTE DE IMPUTAR BS.");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 230, 30));

        jScrollPane6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        txtBancoCuenta.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtBancoCuenta.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtBancoCuenta.setEnabled(false);
        txtBancoCuenta.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        txtBancoCuenta.setMinimumSize(new java.awt.Dimension(6, 50));
        txtBancoCuenta.setPreferredSize(new java.awt.Dimension(6, 50));
        jScrollPane6.setViewportView(txtBancoCuenta);

        jPanel3.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 10, 360, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 380, 180));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("MONTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 0);
        jPanel4.add(jLabel17, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("A PAGAR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 0);
        jPanel4.add(jLabel19, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("TOTAL MENOS RET.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 10);
        jPanel4.add(jLabel20, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel21.setText("RESTA POR PAGAR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel4.add(jLabel21, gridBagConstraints);

        txtSumaTotalCau_menosRet.setBackground(new java.awt.Color(175, 204, 125));
        txtSumaTotalCau_menosRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSumaTotalCau_menosRet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSumaTotalCau_menosRet.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSumaTotalCau_menosRet.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSumaTotalCau_menosRet.setEnabled(false);
        txtSumaTotalCau_menosRet.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 146;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 10);
        jPanel4.add(txtSumaTotalCau_menosRet, gridBagConstraints);

        txtSumaTotalCau.setBackground(new java.awt.Color(175, 204, 125));
        txtSumaTotalCau.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSumaTotalCau.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSumaTotalCau.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSumaTotalCau.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSumaTotalCau.setEnabled(false);
        txtSumaTotalCau.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 126;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel4.add(txtSumaTotalCau, gridBagConstraints);

        txtIva_bs.setBackground(new java.awt.Color(175, 204, 125));
        txtIva_bs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIva_bs.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIva_bs.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIva_bs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIva_bs.setEnabled(false);
        txtIva_bs.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 126;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel4.add(txtIva_bs, gridBagConstraints);

        txtA_Pagar.setBackground(new java.awt.Color(175, 204, 125));
        txtA_Pagar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtA_Pagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtA_Pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtA_Pagar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtA_Pagar.setEnabled(false);
        txtA_Pagar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 126;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel4.add(txtA_Pagar, gridBagConstraints);

        txtResta.setBackground(new java.awt.Color(175, 204, 125));
        txtResta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtResta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtResta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtResta.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtResta.setEnabled(false);
        txtResta.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 126;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 0);
        jPanel4.add(txtResta, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setText("I.V.A  APLICADO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 0);
        jPanel4.add(jLabel32, gridBagConstraints);

        txtMontoPagado.setBackground(new java.awt.Color(175, 204, 125));
        txtMontoPagado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMontoPagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtMontoPagado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMontoPagado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMontoPagado.setEnabled(false);
        txtMontoPagado.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 146;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel4.add(txtMontoPagado, gridBagConstraints);

        jLabel35.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("PAGADO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 30, 0, 0);
        jPanel4.add(jLabel35, gridBagConstraints);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 440, 440, 180));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);

        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("I.V.A.");
        jPanel5.add(jLabel22);
        jLabel22.setBounds(90, 20, 40, 15);

        jLabel23.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel23.setText("I.S.R.L.");
        jPanel5.add(jLabel23);
        jLabel23.setBounds(100, 70, 38, 15);

        jLabel24.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel24.setText("OTRAS RETENCIONES");
        jPanel5.add(jLabel24);
        jLabel24.setBounds(10, 110, 130, 15);

        jLabel25.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel25.setText("TOTAL RETENCION");
        jPanel5.add(jLabel25);
        jLabel25.setBounds(10, 150, 110, 15);

        txtIvaRet.setBackground(new java.awt.Color(175, 204, 125));
        txtIvaRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIvaRet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIvaRet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIvaRet.setText("0,00");
        txtIvaRet.setToolTipText("");
        txtIvaRet.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIvaRet.setEnabled(false);
        txtIvaRet.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jPanel5.add(txtIvaRet);
        txtIvaRet.setBounds(140, 10, 120, 30);

        txtIslrRet.setBackground(new java.awt.Color(175, 204, 125));
        txtIslrRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtIslrRet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIslrRet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIslrRet.setText("0,00");
        txtIslrRet.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIslrRet.setEnabled(false);
        txtIslrRet.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jPanel5.add(txtIslrRet);
        txtIslrRet.setBounds(140, 60, 120, 30);

        txtOtrasRetenciones.setBackground(new java.awt.Color(175, 204, 125));
        txtOtrasRetenciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtOtrasRetenciones.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtOtrasRetenciones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOtrasRetenciones.setText("0,00");
        txtOtrasRetenciones.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtOtrasRetenciones.setEnabled(false);
        txtOtrasRetenciones.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jPanel5.add(txtOtrasRetenciones);
        txtOtrasRetenciones.setBounds(140, 100, 120, 30);

        txtTotalRet.setBackground(new java.awt.Color(175, 204, 125));
        txtTotalRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalRet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalRet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalRet.setText("0,00");
        txtTotalRet.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalRet.setEnabled(false);
        txtTotalRet.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jPanel5.add(txtTotalRet);
        txtTotalRet.setBounds(140, 140, 120, 30);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridBagLayout());

        cmbIvaPorcRet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "  0%", " 75%", "100%" }));
        cmbIvaPorcRet.setSelectedIndex(-1);
        cmbIvaPorcRet.setEnabled(false);
        cmbIvaPorcRet.setPreferredSize(new java.awt.Dimension(75, 25));
        cmbIvaPorcRet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbIvaPorcRetItemStateChanged(evt);
            }
        });
        jPanel6.add(cmbIvaPorcRet, new java.awt.GridBagConstraints());

        cmbIslrPorc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "  0%", "  1%", "  2%", "  3%", "  4%", "  5%" }));
        cmbIslrPorc.setSelectedIndex(-1);
        cmbIslrPorc.setPreferredSize(new java.awt.Dimension(75, 25));
        cmbIslrPorc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbIslrPorcItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel6.add(cmbIslrPorc, gridBagConstraints);

        jPanel5.add(jPanel6);
        jPanel6.setBounds(0, 0, 90, 100);

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 440, 270, 180));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel26.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel26.setText("COMPROMISO Nº");
        jPanel2.add(jLabel26);
        jLabel26.setBounds(10, 10, 90, 20);

        jLabel27.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel27.setText("GASTO CAUSADO Nº");
        jPanel2.add(jLabel27);
        jLabel27.setBounds(100, 10, 110, 20);

        jLabel28.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel28.setText("ORDEN DE PAGO Nº");
        jPanel2.add(jLabel28);
        jLabel28.setBounds(210, 10, 100, 20);

        txtID_Compr.setEditable(false);
        txtID_Compr.setBackground(new java.awt.Color(175, 204, 125));
        txtID_Compr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtID_Compr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtID_Compr.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtID_Compr.setEnabled(false);
        txtID_Compr.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jPanel2.add(txtID_Compr);
        txtID_Compr.setBounds(10, 30, 80, 30);

        txtNumCausado.setEditable(false);
        txtNumCausado.setBackground(new java.awt.Color(175, 204, 125));
        txtNumCausado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNumCausado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumCausado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNumCausado.setEnabled(false);
        txtNumCausado.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jPanel2.add(txtNumCausado);
        txtNumCausado.setBounds(100, 30, 90, 30);

        txtOrdPago.setBackground(new java.awt.Color(175, 204, 125));
        txtOrdPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtOrdPago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOrdPago.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtOrdPago.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtOrdPago.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOrdPagoFocusLost(evt);
            }
        });
        txtOrdPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrdPagoActionPerformed(evt);
            }
        });
        jPanel2.add(txtOrdPago);
        txtOrdPago.setBounds(210, 30, 90, 30);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, 320, 80));

        jLabel29.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel29.setText("Nº Factura");
        getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtFecha.setSelectionColor(new java.awt.Color(175, 204, 125));
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, 120, 30));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("Fecha");
        getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(887, 50, 90, -1));

        txtTotalBs.setEditable(false);
        txtTotalBs.setBackground(new java.awt.Color(175, 204, 125));
        txtTotalBs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalBs.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtTotalBs.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalBs.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalBs.setEnabled(false);
        txtTotalBs.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        getContentPane().add(txtTotalBs, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 630, 180, 40));

        txtFechaFact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFechaFact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFechaFact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaFact.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFechaFact.setEnabled(false);
        txtFechaFact.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtFechaFact.setSelectionColor(new java.awt.Color(175, 204, 125));
        txtFechaFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFactActionPerformed(evt);
            }
        });
        getContentPane().add(txtFechaFact, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 6, 120, 30));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("Fecha Factura");
        getContentPane().add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(872, 17, 110, 20));

        txtRIF_CI.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtRIF_CI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRIF_CI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtRIF_CI.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRIF_CI.setEnabled(false);
        getContentPane().add(txtRIF_CI, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 142, 30));

        tblBenef.setAutoCreateRowSorter(true);
        tblBenef.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        tblBenef.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BENEFICIARIOS", "RIF / CI", "DOMICILIO", "TELÉFONOS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBenef.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblBenef.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblBenef.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblBenef.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBenefMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblBenef);
        tblBenef.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tblBenef.getColumnModel().getColumnCount() > 0) {
            tblBenef.getColumnModel().getColumn(0).setMinWidth(100);
            tblBenef.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblBenef.getColumnModel().getColumn(0).setMaxWidth(300);
            tblBenef.getColumnModel().getColumn(1).setMinWidth(100);
            tblBenef.getColumnModel().getColumn(1).setPreferredWidth(125);
            tblBenef.getColumnModel().getColumn(1).setMaxWidth(150);
            tblBenef.getColumnModel().getColumn(2).setPreferredWidth(250);
            tblBenef.getColumnModel().getColumn(3).setPreferredWidth(150);
        }

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 190, 320, 240));

        btnAtras.setBackground(java.awt.SystemColor.inactiveCaption);
        btnAtras.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnAtras.setText("ATRAS");
        btnAtras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });
        getContentPane().add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 630, 80, 40));

        btnEditarCheque.setBackground(java.awt.SystemColor.inactiveCaption);
        btnEditarCheque.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnEditarCheque.setText(" EDT. ULT. CH. ");
        btnEditarCheque.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnEditarCheque.setEnabled(false);
        btnEditarCheque.setMaximumSize(new java.awt.Dimension(170, 27));
        btnEditarCheque.setMinimumSize(new java.awt.Dimension(170, 27));
        btnEditarCheque.setPreferredSize(new java.awt.Dimension(170, 27));
        btnEditarCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarChequeActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditarCheque, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 630, 150, 40));

        chkISLR.setBackground(java.awt.SystemColor.inactiveCaption);
        chkISLR.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        chkISLR.setText("I.S.L.R.");
        chkISLR.setAlignmentX(0.5F);
        chkISLR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        chkISLR.setEnabled(false);
        chkISLR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(chkISLR, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 630, 120, 40));

        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 1150, 750));
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Rev
     *
     * @param evt
     */
    private void btnIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIVAActionPerformed
        if (tblItem.getRowCount() <= 0) {
            return;
        }

        txtCant.setEnabled(false);
        ((JFormattedTextField) txtCant).setValue(BigDecimal.ONE);

        txtDesc.setEnabled(false);
        txtDesc.setText("");

        txtDesc.setText("Impuesto al Valor Agregado");
        try {
            iva_grav_bs = Format.toBigDec(txtTotalBs.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al formatear el Total Gravable" + System.getProperty("line.separator") + _ex);
            iva_grav_bs = BigDecimal.ZERO;
        }

        if (cmbIva.getSelectedIndex() <= 0) {
            IVA_DEC_VALUE = BigDecimal.ZERO;
        } else {
            final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
            final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
            IVA_DEC_VALUE = regIva.getValor_porc().movePointLeft(2); // 
        }

        txtPUnitario.setEnabled(false);
        BigDecimal iva_bs = iva_grav_bs.multiply(IVA_DEC_VALUE).setScale(2, RoundingMode.HALF_UP);
        ((JFormattedTextField) txtPUnitario).setValue(iva_bs);
        txtSubTotal.setText(Format.toStr(iva_bs));

        conIva = true;
        actComprInsertItem(true);

        btnIVA.setEnabled(false);
        txtCant.setEnabled(true);
        txtDesc.setEnabled(true);
        txtPUnitario.setEnabled(true);
    }//GEN-LAST:event_btnIVAActionPerformed

    /**
     * Rev
     *
     * @param evt
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (tblItem.getRowCount() <= 0) {
            return;
        }

        final DefaultTableModel model = (DefaultTableModel) tblItem.getModel();

        int fila = tblItem.getRowCount() - 1;
        final String codPar = (String) tblItem.getValueAt(fila, 4);

        // Verificar que es la partida del IVA
        for (PptoModel partIva : CAPIP_IVA_PARTIDA.values()) {
            if (codPar.equals(partIva.getCodigo())) {
                btnIVA.setEnabled(true);
                cmbIvaPorcRet.setEnabled(false);
                cmbIvaPorcRet.setSelectedIndex(-1);
                txtIvaRet.setText("0,00");
            }
        }

        model.removeRow(fila);

        calcularTotal();
    }//GEN-LAST:event_btnEliminarActionPerformed

    /**
     * Rev
     *
     * @param evt
     */
    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        actComprInsertItem(false);
    }//GEN-LAST:event_btnInsertarActionPerformed

    /**
     * Rev
     *
     * @param evt
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        actGuardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Rev 14/11/2016
     *
     */
    private void actGuardar() {

        if (tblItem.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "Debe incluir al menus un Item");
            txtCant.requestFocusInWindow();
            return;
        }

        final Map<String, Object> param = new HashMap<>(101);
        if (!valIdAvanceEfectivo(param)) {
            return;
        }

        if (!valComprComp(param)) {
            return;
        }

        if (!valCausadoComp(param)) {
            return;
        }

        if (!valIvaComp(param)) {
            return;
        }

        if (!valIslrComp(param)) {
            return;
        }

        if (!valPagoComp(param)) {
            return;
        }

        try {
            Conn.BeginTransaction();

            comprGuardar(param);
            causadoGuardar(param);
            retencionGuardar(param);
            pagoGuardar(param);
            Conn.EndTransaction();

        } catch (final Exception _ex) {
            try {
                Conn.RollBack();
            } catch (final Exception _ex1) {
                JOptionPane.showMessageDialog(null, _ex1);
            }

            JOptionPane.showMessageDialog(null, "Error durante la operación: " + System.getProperty("line.separator") + _ex);
            return;
        }

        JOptionPane.showMessageDialog(this, "Operación realizada");

        try {
            Compromiso.genReport((long) param.get("id_compr"), new HashMap<>(param), true);

            Causado.genReport((long) param.get("id_causado"), param);

            if (((BigDecimal) param.get("iva_bs")).compareTo(BigDecimal.ZERO) > 0) {
                ImpuestoRetencion.genIvaReport((long) param.get("id_iva_retencion"), param);
            }

            if (((BigDecimal) param.get("islr_retenido_bs")).compareTo(BigDecimal.ZERO) > 0) {
                ImpuestoRetencion.genIslrReport((long) param.get("id_islr_retencion"), param);
            }

        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de generar el Reporte" + System.getProperty("line.separator") + _ex);
        }

        clearComp();
    }

    /**
     * Rev
     *
     * @param evt
     */
    private void txtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescActionPerformed
        txtDesc.transferFocus();
    }//GEN-LAST:event_txtDescActionPerformed

    /**
     * Rev 08/11/2016
     *
     * @param evt
     */
    private void tblPartidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPartidaMouseClicked
        actComprInsertItem(false);
    }//GEN-LAST:event_tblPartidaMouseClicked

    private void tblBenefMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBenefMouseClicked
        actSelectBenef();
    }//GEN-LAST:event_tblBenefMouseClicked

    /**
     * Rev 07/11/2016
     *
     */
    private void actSelectBenef() {

        if (!tblBenef.isEnabled()) {
            return;
        }

        int fila = tblBenef.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return;
        }

        txtRazonSocial.setText(tblBenef.getValueAt(fila, 0).toString());
        txtRazonSocial.setCaretPosition(0);
        txtRIF_CI.setText(tblBenef.getValueAt(fila, 1).toString());

        UpdateEdo(CapipState.SEL_ITEM);
    }

    /**
     * Rev
     *
     * @param evt
     */
    private void txtFechaFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFactActionPerformed
        txtCant.requestFocusInWindow();
    }//GEN-LAST:event_txtFechaFactActionPerformed

    /**
     * Rev 16/11/2016
     *
     * @param evt
     */
    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        switch (estado) {
            case SEL_ORDEN:
                break;

            case SEL_BENEF:
                UpdateEdo(CapipState.SEL_ORDEN);
                break;

            case SEL_ITEM:
                UpdateEdo(CapipState.SEL_BENEF);
                break;

            case SEL_CUENTA:
                UpdateEdo(CapipState.SEL_ITEM);
                break;

            default:
                throw new AssertionError();
        }
    }//GEN-LAST:event_btnAtrasActionPerformed

    /**
     * Rev
     *
     * @return
     * @throws Exception
     */
    private void comprGuardar(final Map<String, Object> _param) throws Exception {
        _param.put("id_compr", comprNextNum.nextNum());
        Compromiso.genMaster(_param, tblItem, IVA_DEC_VALUE);
        Compromiso.genDetail(_param, tblItem);
        comprNextNum.update();
    }

    /**
     * Rev 12/10/2016
     *
     * @param _param
     * @return
     */
    private boolean valComprComp(final Map<String, Object> _param) {

        // Verificar si hay items seleccionados
        // 1.-
        if (tblItem.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "Debe incluir al menos un Ítem");
            java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
            return false;
        }

        // Validación interna, por si acaso falto alguna actualización
        // 2.-
        try {
            if (Format.toLong(txtID_Compr.getText()) <= 0) {
                JOptionPane.showMessageDialog(this, "Número de compromiso inválido");
                return false;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Número de compromiso inválido");
            java.awt.EventQueue.invokeLater(txtID_Compr::requestFocusInWindow);
            return false;
        }

        // Verificar el tipo de compromiso
        // 3.-
        final TipoCompr tipo_compr = TipoCompr.CO;
        _param.put("tipo_compr", tipo_compr);

        // Verficar la fecha de Compromiso
        // 4.-
        final java.sql.Date fecha_compr;
        try {
            fecha_compr = Format.toDateSql(txtFecha.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Fecha de Compromiso inválida: " + System.getProperty("line.separator") + _ex);
            java.awt.EventQueue.invokeLater(txtFecha::requestFocusInWindow);
            return false;
        }
        _param.put("fecha_compr", fecha_compr);

        // Verificar el Beneficiario
        // 5.-
        final String benef_razonsocial = txtRazonSocial.getText();
        if (benef_razonsocial.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Beneficiario inválido");
            java.awt.EventQueue.invokeLater(tblBenef::requestFocusInWindow);
            return false;
        }
        _param.put("benef_razonsocial", benef_razonsocial);

        // Verificar el beneficiario, rif_ci
        // 6.-
        final String benef_rif_ci = txtRIF_CI.getText();
        if (benef_rif_ci.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Beneficiario inválido");
            java.awt.EventQueue.invokeLater(tblBenef::requestFocusInWindow);
            return false;
        }
        _param.put("benef_rif_ci", benef_rif_ci);

        // Verificar las observaciones
        // 7.-
        final String auxObs = txaConcepto.getText().trim().toUpperCase();
        if (auxObs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Concepto de la Orden inválido");
            java.awt.EventQueue.invokeLater(txaConcepto::requestFocusInWindow);
            return false;
        }
        _param.put("observacion", auxObs.substring(0, min(auxObs.length(), 512)));

        // Verificar el número de factura
        // 8.-    
        final String num_fact = txtNroFact.getText().trim().toUpperCase();
        _param.put("num_fact", num_fact.substring(0, min(num_fact.length(), 32)));

        // Verificar el número de factura
        // 8B.-    
        final String sAuxNumControl = txtNumControl.getText().trim().toUpperCase();
        final String num_control = sAuxNumControl.substring(0, min(sAuxNumControl.length(), 32));
        _param.put("num_control", num_control);

        // Verificar la fecha de la factura
        // 9.-
        final java.sql.Date fecha_fact;
        try {
            fecha_fact = Format.toDateSql(txtFechaFact.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Fecha de factura inválida");
            java.awt.EventQueue.invokeLater(txtFechaFact::requestFocusInWindow);
            return false;
        }

        // Validar la Fecha, no puede ser mayor a mañana
        // 10.-
        Calendar fechaTomorrow = Calendar.getInstance();
        fechaTomorrow.add(Calendar.DAY_OF_MONTH, 1);

        if (fecha_fact.compareTo(Format.toDateSql(fechaTomorrow.getTime())) > 0) {
            JOptionPane.showMessageDialog(null, "Fecha de factura inválida");
            java.awt.EventQueue.invokeLater(txtFechaFact::requestFocusInWindow);
            return false;
        }

        // Validar la Fecha, no puede tener mas de un año
        // 11.-
        Calendar fechaAntAno = Calendar.getInstance();
        fechaAntAno.add(Calendar.YEAR, -1);

        if (fecha_fact.compareTo(Format.toDateSql(fechaAntAno.getTime())) <= 0) {
            JOptionPane.showMessageDialog(null, "Fecha de factura inválida");
            java.awt.EventQueue.invokeLater(txtFechaFact::requestFocusInWindow);
            return false;
        }
        _param.put("fecha_fact", fecha_fact);

        // Validar el Monto
        // 12.- 
        BigDecimal total_bs;
        try {
            total_bs = Format.toBigDec(txtTotalBs.getText().trim());
        } catch (final Exception ex) {
            total_bs = BigDecimal.ZERO;
        }

        if (total_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Monto total inválido");
            return false;
        }

        // Verificar el monto del IVA
        // 12.-
        if (cmbIva.getSelectedIndex() <= 0) {
            IVA_DEC_VALUE = BigDecimal.ZERO;
        } else {
            final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
            final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
            IVA_DEC_VALUE = regIva.getValor_porc().movePointLeft(2); // 
        }

        BigDecimal iva_bs = iva_grav_bs.multiply(IVA_DEC_VALUE).setScale(2, RoundingMode.HALF_UP);
        if (iva_bs.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Monto del IVA inválido");
            return false;
        }

        // Verificar la base imponible
        // 13.-
        final BigDecimal base_imponible_bs = total_bs.subtract(iva_bs).setScale(2, RoundingMode.HALF_UP);
        if (base_imponible_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Base imponible inválida");
            return false;
        }

        _param.put("total_bs", total_bs);
        _param.put("iva_porc_aplic", IVA_DEC_VALUE.movePointRight(2));
        _param.put("iva_grav_bs", iva_grav_bs);
        _param.put("iva_bs", iva_bs);
        _param.put("base_imponible_bs", base_imponible_bs);
        _param.put("islr_grav_bs", total_bs.subtract(iva_bs).setScale(2, RoundingMode.HALF_UP));

        // Verificar que para el beneficiario dado, NO se halla procesado la misma factura              
        if (!num_fact.isEmpty()) {
            final String[] arrTbl = {"compr_compra", "compr_servicio", "compr_otros"};

            for (String tbl : arrTbl) {
                try {
                    final ResultSet rs = Conn.executeQuery("SELECT * FROM " + tbl + " WHERE benef_rif_ci='" + benef_rif_ci + "' AND num_fact= '" + num_fact + "'");
                    if (rs.next()) {
                        final long lAuxIdCompr = rs.getLong("id_compr");
                        final String sAuxFechaCompr = Format.toStr(rs.getDate("fecha_compr"));
                        JOptionPane.showMessageDialog(this, "Este núm. de Factura ya fue procesada (" + tbl + ")" + System.getProperty("line.separator") + "Núm. de Orden: " + lAuxIdCompr + ", de fecha: " + sAuxFechaCompr);
                        java.awt.EventQueue.invokeLater(txtNroFact::requestFocusInWindow);
                        return false;
                    }
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(this, "No fue posible verificar la validez de la factura" + System.getProperty("line.separator") + _ex);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Rev 14/11/2016
     *
     * @param _param
     * @return
     */
    private boolean valCausadoComp(Map<String, Object> _param) {

        // Retornar si no hay ordenes
        if (tblItem.getRowCount() <= 0) {
            return false;
        }

        final long id_causado;

        try {
            id_causado = Long.parseLong(txtNumCausado.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Número de causado inválido");
            return false;
        }

        if (id_causado <= 0) {
            JOptionPane.showMessageDialog(null, "Número de causado inválido");
            return false;
        }

        final java.sql.Date fecha_causado;
        try {
            fecha_causado = Format.toDateSql(txtFecha.getText().trim());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida" + System.getProperty("line.separator") + _ex);
            return false;
        }
        _param.put("fecha_causado", fecha_causado);

        return true;
    }

    /**
     * Rev 13/10/2016
     *
     * @param _param
     * @return
     */
    private boolean valIvaComp(final Map<String, Object> _param) {

        // Retornar si no se ha calculado el monto del IVA
        final int selIdx = cmbIvaPorcRet.getSelectedIndex();
        if (selIdx < 0) {
            JOptionPane.showMessageDialog(this, "No ha calculado el monto a retener");
            return false;
        }

        final BigDecimal iva_retenido_bs;
        try {
            iva_retenido_bs = Format.toBigDec(txtIvaRet.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        if (iva_retenido_bs.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Monto del IVA es menor a cero (0), no se puede generar la operación");
            return false;
        }

        _param.put("ivaBenef_razonsocial", _param.get("benef_razonsocial"));
        _param.put("ivaBenef_rif_ci", _param.get("benef_rif_ci"));
        _param.put("iva_porc_ret", porcRetIva[selIdx].movePointRight(2).setScale(2, RoundingMode.HALF_UP));
        _param.put("iva_retenido_bs", iva_retenido_bs);
        _param.put("ejefis", Globales.getEjeFisYear());

        return true;
    }

    /**
     * Rev 23/10/2016
     *
     * @param _param
     * @return
     */
    private boolean valIslrComp(final Map<String, Object> _param) {

        // Retornar si no se ha calculado el porcentaje del Islr
        final int selIdx = cmbIslrPorc.getSelectedIndex();
        if (selIdx < 0) {
            JOptionPane.showMessageDialog(this, "No ha calculado el porcentaje del ISLR a retener");
            return false;
        }

        final BigDecimal islr_retenido_bs;
        try {
            islr_retenido_bs = Format.toBigDec(txtIslrRet.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
            return false;
        }

        if (islr_retenido_bs.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(null, "Monto del monto retenido es menor que cero (0), no se puede generar la operación");
            return false;
        }

        _param.put("islrBenef_razonsocial", _param.get("benef_razonsocial"));
        _param.put("islrBenef_rif_ci", _param.get("benef_rif_ci"));
        _param.put("islr_porc_ret", porcRetIslr[selIdx].movePointRight(2).setScale(2, RoundingMode.HALF_UP));
        _param.put("islr_retenido_bs", islr_retenido_bs);

        if (cmbIva.getSelectedIndex() <= 0) {
            IVA_DEC_VALUE = BigDecimal.ZERO;
        } else {
            final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
            final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
            IVA_DEC_VALUE = regIva.getValor_porc().movePointLeft(2); // 
        }

        _param.put("islr_gravable_bs", ((BigDecimal) _param.get("total_bs")).subtract(iva_grav_bs.multiply(IVA_DEC_VALUE)).setScale(2, RoundingMode.HALF_UP));

        _param.put("otras_ret_bs", BigDecimal.ZERO);

        return true;
    }

    /**
     * Rev 26/10/2016
     *
     * @param param
     * @return
     */
    private boolean valPagoComp(Map<String, Object> _param) {

        if (tblItem.getRowCount() <= 0) {
            return false;
        }

        final BigDecimal total_bs = (BigDecimal) _param.get("total_bs");

        final BigDecimal apagar_bs;
        try {
            apagar_bs = Format.toBigDec(txtA_Pagar.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto A Pagar inválido");
            return false;
        }

        if ((apagar_bs.compareTo(BigDecimal.ZERO) <= 0) || (apagar_bs.compareTo(total_bs) > 0)) {
            JOptionPane.showMessageDialog(null, "Monto A Pagar inválido");
            return false;
        }

        final BigDecimal pendImp_bs;
        try {
            pendImp_bs = Format.toBigDec(txtPendImputar.getText());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Monto Pendiente de Imputar inválido");
            return false;
        }

        if (pendImp_bs.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "Monto Pendiente de Imputar inválido");
            return false;
        }

        if (apagar_bs.compareTo(pendImp_bs) > 0) {
            JOptionPane.showMessageDialog(null, "Monto A Pagar supera al pendiente por Imputar");
            return false;
        }

        final BigDecimal resta_bs;
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

        final String cheque = txtCheque.getText().trim().toUpperCase();
        if (cheque.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe indicar un número de cheque");
            txtCheque.requestFocusInWindow();
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

//        _param.put("banco", banco);
//        _param.put("cuenta", cuenta);
//        _param.put("cheque", cheque);
        _param.put("a_pagar_bs", apagar_bs);
        _param.put("resta_bs", resta_bs);
        _param.put("fecha_hoy", fecha_hoy);
        _param.put("fecha_cheque", fecha_cheque);

        return true;
    }

    /**
     * Guarda/Genera el Causado
     *
     * @throws Exception
     */
    private void causadoGuardar(final Map<String, Object> _param) throws Exception {
        _param.put("id_causado", cauNextNum.nextNum());
        Causado.genCausadoMaster(_param, null);
        Causado.genCausadoDetail(_param, null);
        cauNextNum.update();
    }

    /**
     * Rev 16//11/2016
     *
     * @return
     * @throws Exception
     *
     */
    private void retencionGuardar(final Map<String, Object> param) throws Exception {
        BigDecimal aux;

        try {
            aux = Format.toBigDec(txtIvaRet.getText());
        } catch (final Exception _ex) {
            aux = BigDecimal.ZERO;
        }
        if (aux.compareTo(BigDecimal.ZERO) > 0) {
            ImpuestoRetencion.genIvaMaster(param);
            genIvaDetail(param);
        }

        try {
            aux = Format.toBigDec(txtIslrRet.getText());
        } catch (final Exception _ex) {
            aux = BigDecimal.ZERO;
        }
        if (aux.compareTo(BigDecimal.ZERO) > 0) {
            ImpuestoRetencion.genIslrMaster(param);
            genIslrDetail(param);
        }

    }

    /**
     * Rev 16/11/2016
     *
     * @param _param
     * @throws Exception
     */
    private void genIvaDetail(final Map<String, Object> _param) throws Exception {

        final long id_iva_retencion = (long) _param.get("id_iva_retencion");

        final long id_causado = (long) _param.get("id_causado");
        final java.sql.Date fecha_fact = (java.sql.Date) _param.get("fecha_fact");
        final String num_fact = (String) _param.get("num_fact");
        final TipoCompr tipo_compr = (TipoCompr) _param.get("tipo_compr");
        final long id_compr = (long) _param.get("id_compr");
        final String ndebito = ""; // num. nota de debito
        final String ncredito = ""; // num nota de credito
        final String transaccion = "C";
        final String factura_aft = ""; // num factura que afecta
        final BigDecimal total_fact = (BigDecimal) _param.get("total_bs");
        final BigDecimal base_imponible_bs = (BigDecimal) _param.get("base_imponible_bs");
        final BigDecimal exento = base_imponible_bs.subtract(iva_grav_bs).setScale(2, RoundingMode.HALF_UP);
        final BigDecimal iva_retenido_bs = (BigDecimal) _param.get("iva_retenido_bs");
        final String obs = (String) _param.get("observacion");
        final BigDecimal iva_bs = (BigDecimal) _param.get("iva_bs");
        final BigDecimal iva_porc_aplic = (BigDecimal) _param.get("iva_porc_aplic");
        final BigDecimal iva_porc_ret = (BigDecimal) _param.get("iva_porc_ret");

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
            pst.setBigDecimal(12, base_imponible_bs); // bimponible
            pst.setBigDecimal(13, iva_porc_aplic); // elicuota
            pst.setBigDecimal(14, iva_bs); // impuesto
            pst.setBigDecimal(15, iva_retenido_bs); // iretenido
            pst.setString(16, obs); // conceptoret
            pst.setString(17, tipo_compr.name()); // tipo_compr
            pst.setDate(18, new java.sql.Date(Globales.getServerTimeStamp().getTime())); // ejefis
            pst.setString(19, (String) _param.get("ivaBenef_razonsocial"));
            pst.setString(20, (String) _param.get("ivaBenef_rif_ci"));
            if (pst.executeUpdate() != 1) {
                throw new Exception("Error sl insertar el registro de Detalle");
            }
        }

        // Actualizar los datos del Compromiso asociado
        PreparedStatement pstCompr = Conn.getConnection().prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET iva_grav_bs= ?, iva_porc_ret= ? WHERE id_compr= ?");
        pstCompr.setBigDecimal(1, iva_bs);
        pstCompr.setBigDecimal(2, iva_porc_ret);
        pstCompr.setLong(3, id_compr);
        if (pstCompr.executeUpdate() != 1) {
            throw new Exception("Error al actualizar el Causado");
        }

        // Verificar si ya se ha actualizado el campo retenido_sn del causado
        if (Conn.getConnection().prepareStatement("UPDATE causado SET iva_ret_sn='S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
            throw new Exception("Error al actualizar el Causado");
        }

        final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs - ? WHERE id_causado= ?");
        pstCau.setBigDecimal(1, iva_retenido_bs);
        pstCau.setLong(2, id_causado);
        if (pstCau.executeUpdate() != 1) {
            throw new Exception("Error sl actualizar el impuesto Retenido");
        }
    }

    /**
     * Rev 16/11/2016
     *
     * @param _param
     * @throws Exception
     */
    private void genIslrDetail(final Map<String, Object> _param) throws Exception {

        final long id_islr_retencion = (long) _param.get("id_islr_retencion");

        final long id_causado = (long) _param.get("id_causado");
        final java.sql.Date fecha_fact = (java.sql.Date) _param.get("fecha_fact");
        final String num_fact = (String) _param.get("num_fact");
        final TipoCompr tipo_compr = (TipoCompr) _param.get("tipo_compr");
        final long id_compr = (long) _param.get("id_compr");
        final BigDecimal total_fact = (BigDecimal) _param.get("total_bs");
        final BigDecimal base_imponible_bs = (BigDecimal) _param.get("base_imponible_bs");
        final BigDecimal isrl_grav_bs = (BigDecimal) _param.get("islr_gravable_bs");
        final BigDecimal islr_porc_ret = (BigDecimal) _param.get("islr_porc_ret");
        final BigDecimal islr_retenido_bs = (BigDecimal) _param.get("islr_retenido_bs");
        final String obs = (String) _param.get("observacion");

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
        pst.setBigDecimal(8, base_imponible_bs);
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
        PreparedStatement pstCompr = Conn.getConnection().prepareStatement("UPDATE " + tipo_compr.getTbl() + " SET islr_grav_bs= ?, islr_porc_ret= ? WHERE id_compr= ?");
        pstCompr.setBigDecimal(1, isrl_grav_bs);
        pstCompr.setBigDecimal(2, islr_porc_ret);
        pstCompr.setLong(3, id_compr);
        if (pstCompr.executeUpdate() != 1) {
            throw new Exception("Error al actualizar el Causado");
        }

        // Verificar si ya se ha actualizado el campo retenido_sn del causado
        if (Conn.getConnection().prepareStatement("UPDATE causado SET islr_ret_sn= 'S' WHERE id_causado= " + id_causado).executeUpdate() != 1) {
            throw new Exception("Error al actualizar el Causado");
        }

        final PreparedStatement pstCau = Conn.getConnection().prepareStatement("UPDATE causado SET resta_bs= resta_bs - ? WHERE id_causado= ?");
        pstCau.setBigDecimal(1, islr_retenido_bs);
        pstCau.setLong(2, id_causado);
        if (pstCau.executeUpdate() != 1) {
            throw new Exception("Error sl actualizar el Resta, en el Causado");
        }
    }

    /**
     * Rev 16/11/2016
     *
     * @param _id_cau
     * @return
     * @throws SQLException
     */
    private void pagoGuardar(final Map<String, Object> _param) throws Exception {
        genPagoDetail(_param);
    }

    /**
     * Rev 26/10/2016
     *
     * @param _param
     */
    private void genPagoDetail(Map<String, Object> _param) throws Exception {

        final long id_avance_efectivo = (long) _param.get("id_avance_efectivo");
        final BigDecimal a_pagar_bs = (BigDecimal) _param.get("a_pagar_bs");

        // Tomar el causado seleccionado
        final CauModel regCau = CauModel.getxID((long) _param.get("id_causado"));

        // Esta linea esta un poco de mas, pero se deja a manera de debug, ya que el resta nunca sera <= 0
        if (regCau.getResta_bs().compareTo(BigDecimal.ZERO) > 0.00d) {

            PagoOrden.setAPagarCau(regCau.getId_causado(), a_pagar_bs);

            final PreparedStatement pstCauPagHist = Conn.getConnection().prepareStatement("INSERT INTO causado_avance_efectivo_nn"
                + "(id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4
                + "id_causado, id_avance_efectivo, apagar_bs) " // 5, 6, 7
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstCauPagHist.setLong(1, UserPassIn.getIdUser());
            pstCauPagHist.setLong(2, UserPassIn.getIdSession());
            pstCauPagHist.setTimestamp(3, UserPassIn.getDateSession()); // date_session
            pstCauPagHist.setDate(4, Globales.getEjefis());
            pstCauPagHist.setLong(5, regCau.getId_causado());
            pstCauPagHist.setLong(6, id_avance_efectivo);
            pstCauPagHist.setBigDecimal(7, a_pagar_bs);
            if (pstCauPagHist.executeUpdate() != 1) {
                throw new Exception("Error al insertar el registro");
            }

        } else {
            throw new Exception("Error en el registro Causado, resta_bs= " + Format.toStr(regCau.getResta_bs()));
        }

    }

    /**
     * Rev
     *
     */
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        final boolean salir;
        if (tblItem.getRowCount() > 0) {
            salir = JOptionPane.showConfirmDialog(this, "¿Hay registros pendientes . ¿ Seguro desea Salir ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
        } else {
            salir = true;
        }

        if (salir) {
            new CompromisosConsultar(this).setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void txtNroFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroFactActionPerformed
        txtNroFact.transferFocus();
    }//GEN-LAST:event_txtNroFactActionPerformed

    private void txtChequeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChequeKeyTyped
        if (txtCheque.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtChequeKeyTyped

    private void txaConceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaConceptoKeyTyped
        if (txaConcepto.getText().length() >= 512) {
            evt.consume();
        }
    }//GEN-LAST:event_txaConceptoKeyTyped

    private void btnEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarChequeActionPerformed

    }//GEN-LAST:event_btnEditarChequeActionPerformed

    private void txtCantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantActionPerformed
        txtCant.transferFocus();
    }//GEN-LAST:event_txtCantActionPerformed

    private void txtCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyTyped
        if (!"0123456789.".contains(String.valueOf(evt.getKeyChar())) || txtCant.getText().length() > 32) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantKeyTyped

    private void txtPUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPUnitarioActionPerformed
        Compromiso.newSubTotal((JFormattedTextField) txtCant, (JFormattedTextField) txtPUnitario, txtSubTotal);

        try {
            if ((Format.toDouble(txtSubTotal.getText()) > 0) && (tblPartida.getRowCount() >= 0)) {
                tblPartida.setRowSelectionInterval(0, 0);
                tblPartida.scrollRectToVisible(tblPartida.getCellRect(0, 0, true));
                java.awt.EventQueue.invokeLater(tblPartida::requestFocusInWindow);
            }
        } catch (final Exception _ex) {
            txtSubTotal.setText("0,00");
        }
    }//GEN-LAST:event_txtPUnitarioActionPerformed

    private void txtPUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPUnitarioKeyTyped
        if (!"0123456789.".contains(String.valueOf(evt.getKeyChar())) || txtPUnitario.getText().length() > 32) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPUnitarioKeyTyped

    /**
     * Rev 10/11/2016
     *
     * @param evt
     */
    private void cmbIslrPorcItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIslrPorcItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {

            if (txaConcepto.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Antes de continuar debe indicar el concepto" + System.getProperty("line.separator") + "");
                cmbIslrPorc.setSelectedIndex(-1);
                txaConcepto.requestFocusInWindow();
                return;
            }

            final int selIdx = cmbIslrPorc.getSelectedIndex();
            if (selIdx >= 0) {
                try {

                    final BigDecimal IVA_DEC_VALUE;
                    if (cmbIva.getSelectedIndex() <= 0) {
                        IVA_DEC_VALUE = BigDecimal.ZERO;
                    } else {
                        final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
                        final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
                        IVA_DEC_VALUE = regIva.getValor_porc().movePointLeft(2); // 
                    }

                    txtIslrRet.setText(Format.toStr(Format.toBigDec(txtTotalBs.getText()).subtract(iva_grav_bs.multiply(IVA_DEC_VALUE)).multiply(porcRetIslr[selIdx]).setScale(2, RoundingMode.HALF_UP)));
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + _ex);
                    txtIslrRet.setText("0.00");
                }

                if (conIva) {
                    if (cmbIvaPorcRet.getSelectedIndex() >= 0) {
                        UpdateEdo(CapipState.SEL_CUENTA);
                    }
                } else {
                    cmbIvaPorcRet.setSelectedIndex(0);
                    UpdateEdo(CapipState.SEL_CUENTA);
                }

            } else {
                txtIslrRet.setText("0,00");
            }
        } else {
            txtIslrRet.setText("0,00");
        }

        try {
            final BigDecimal auxRet = Format.toBigDec(txtIvaRet.getText()).add(Format.toBigDec(txtIslrRet.getText()));
            txtTotalRet.setText(Format.toStr(auxRet));

            final String saux = Format.toStr(Format.toBigDec(txtTotalBs.getText()).subtract(auxRet).setScale(2, RoundingMode.HALF_UP));
            txtSumaTotalCau_menosRet.setText(saux);
            txtA_Pagar.setText(saux);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al formatear el Total Retenciones" + System.getProperty("line.separator") + _ex);
            txtTotalRet.setText("0,00");
        }

    }//GEN-LAST:event_cmbIslrPorcItemStateChanged

    /**
     * Rev 10/11/2016
     *
     * @param evt
     */
    private void cmbIvaPorcRetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIvaPorcRetItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (txaConcepto.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Antes de continuar debe indicar el concepto" + System.getProperty("line.separator") + "");
                cmbIvaPorcRet.setSelectedIndex(-1);
                txaConcepto.requestFocusInWindow();
                return;
            }

            final int selIdx = cmbIvaPorcRet.getSelectedIndex();
            if (selIdx >= 0) {
                try {

                    final BigDecimal IVA_DEC_VALUE;
                    if (cmbIva.getSelectedIndex() <= 0) {
                        IVA_DEC_VALUE = BigDecimal.ZERO;
                    } else {
                        final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
                        final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
                        IVA_DEC_VALUE = regIva.getValor_porc().movePointLeft(2); // 
                    }
                    txtIvaRet.setText(Format.toStr(iva_grav_bs.multiply(IVA_DEC_VALUE).multiply(porcRetIva[selIdx])));
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + _ex);
                    txtIslrRet.setText("0.00");
                }

                if (cmbIslrPorc.getSelectedIndex() >= 0) {
                    UpdateEdo(CapipState.SEL_CUENTA);
                }

            } else {
                txtIvaRet.setText("0,00");
            }
        } else {
            txtIvaRet.setText("0,00");
        }

        try {
            final BigDecimal auxRet = Format.toBigDec(txtIvaRet.getText()).add(Format.toBigDec(txtIslrRet.getText()));
            txtTotalRet.setText(Format.toStr(auxRet));

            final String saux = Format.toStr(Format.toBigDec(txtTotalBs.getText()).subtract(auxRet).setScale(2, RoundingMode.HALF_UP));
            txtSumaTotalCau_menosRet.setText(saux);
            txtA_Pagar.setText(saux);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al formatear el Total Retenciones" + System.getProperty("line.separator") + _ex);
            txtTotalRet.setText("0,00");
        }
    }//GEN-LAST:event_cmbIvaPorcRetItemStateChanged

    private void txtNroFactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroFactKeyTyped
        if (txtNroFact.getText().length() >= 32) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNroFactKeyTyped

    private void txtOrdPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdPagoActionPerformed
        checkIdAvanceEfectivo();
    }//GEN-LAST:event_txtOrdPagoActionPerformed

    private void txtOrdPagoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOrdPagoFocusLost
    }//GEN-LAST:event_txtOrdPagoFocusLost

    private void cmbIvaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIvaItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            final int selIdx = cmbIva.getSelectedIndex();
            if (selIdx >= 0) {
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cmbIvaItemStateChanged

    private void cmbIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIvaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbIvaActionPerformed

    private void txtNumControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumControlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumControlActionPerformed

    private void txtNumControlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumControlKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumControlKeyTyped

    private void checkIdAvanceEfectivo() {
        final Map<String, Object> param = new HashMap<>(101);
        if (valIdAvanceEfectivo(param)) {
            UpdateEdo(CapipState.SEL_ITEM);
        }
    }

    /**
     *
     * @param _param
     * @return
     */
    private boolean valIdAvanceEfectivo(Map<String, Object> _param) {

        final String saux = txtOrdPago.getText().trim();

        if (saux.isEmpty()) {
            return false;
        }

        long id_avance_efectivo;

        try {
            id_avance_efectivo = Long.parseLong(saux);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error. Número de orden inválido" + System.getProperty("line.separator") + _ex);
            return false;
        }

        // Buscar el numero de orden
        AvanceEfectivoModel reg;
        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM avance_efectivo WHERE id= " + id_avance_efectivo);
            if (rs.next()) {
                reg = new AvanceEfectivoModel(rs);
            } else {
                JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + "Registro no encontrado");
                java.awt.EventQueue.invokeLater(txtOrdPago::requestFocusInWindow);
                return false;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar el registro" + System.getProperty("line.separator") + _ex);
            return false;
        }

        _param.put("id_avance_efectivo", id_avance_efectivo);
        _param.put("banco", reg.getBanco());
        _param.put("cuenta", reg.getCuenta());
        _param.put("cheque", reg.getCheque());

        txtRazonSocial.setText(reg.getBenef_razonsocial());
        txtRIF_CI.setText(reg.getBenef_rif_ci());

        txtBancoCuenta.setText(reg.getBanco() + " " + reg.getCuenta());
        txtCheque.setText(reg.getCheque());

        if (regCuenta != null) {
            final CuentaModel cuentaAux;
            try {
                cuentaAux = CuentaModel.getxCuenta(reg.getCuenta());
                if (!cuentaAux.getCuenta().equals(regCuenta.getCuenta())) {
                    JOptionPane.showMessageDialog(this, "El número de Orden de Pago, " + System.getProperty("line.separator") + "no coincide con la cuenta seleccionada inicialmente");
                    return false;
                }
            } catch (Exception _ex) {
                JOptionPane.showMessageDialog(this, "Error al tratar de recuperar la Cuenta" + System.getProperty("line.separator") + _ex);
                regCuenta = null;
            }
        }

        BigDecimal imputado_bs;

        try {
            final ResultSet rs = Conn.executeQuery("SELECT SUM(apagar_bs) FROM causado_avance_efectivo_nn WHERE id_avance_efectivo=" + id_avance_efectivo);
            if (rs.next()) {
                imputado_bs = rs.getBigDecimal(1);
                if (imputado_bs == null) {
                    imputado_bs = BigDecimal.ZERO;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + "Registro NN no encontrado");
                txtPendImputar.setText("0,00");
                java.awt.EventQueue.invokeLater(txtOrdPago::requestFocusInWindow);
                return false;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar el registro NN" + System.getProperty("line.separator") + _ex);
            return false;
        }

        // Se muestra el pendiente del avance de efectivo, que esta pendiente de justificacion
        txtPendImputar.setText(Format.toStr(reg.getA_pagar_bs().subtract(imputado_bs)));

        return true;
    }

    /**
     * Rev
     *
     */
    private void UpdateEdo(CapipState _edo) {
        estado = _edo;

        switch (estado) {

            case SEL_ORDEN:
                txtOrdPago.setEnabled(true);
                tblBenef.setEnabled(false);

                txtNroFact.setEnabled(false);
                txtFechaFact.setEnabled(false);

                txtCant.setEnabled(false);
                txtDesc.setEnabled(false);
                txtPUnitario.setEnabled(false);

                tblItem.setEnabled(false);
                tblPartida.setEnabled(false);
                txaConcepto.setEnabled(false);

                cmbIvaPorcRet.setSelectedIndex(-1);
                cmbIvaPorcRet.setEnabled(false);
                cmbIslrPorc.setSelectedIndex(-1);
                cmbIslrPorc.setEnabled(false);

                txtCheque.setEnabled(false);

                btnInsertar.setEnabled(false);
                btnEliminar.setEnabled(false);
                btnIVA.setEnabled(false);
                btnGuardar.setEnabled(false);
                btnAtras.setEnabled(false);

                java.awt.EventQueue.invokeLater(txtOrdPago::requestFocusInWindow);
                break;

            case SEL_BENEF:

                txtOrdPago.setEnabled(false);
                tblBenef.setEnabled(true);

                txtNroFact.setEnabled(false);
                txtFechaFact.setEnabled(false);

                txtCant.setEnabled(false);
                txtDesc.setEnabled(false);
                txtPUnitario.setEnabled(false);

                tblItem.setEnabled(false);
                tblPartida.setEnabled(false);
                txaConcepto.setEnabled(false);

                cmbIvaPorcRet.setSelectedIndex(-1);
                cmbIvaPorcRet.setEnabled(false);
                cmbIslrPorc.setSelectedIndex(-1);
                cmbIslrPorc.setEnabled(false);

                txtCheque.setEnabled(false);

                btnInsertar.setEnabled(false);
                btnEliminar.setEnabled(false);
                btnIVA.setEnabled(false);
                btnGuardar.setEnabled(false);
                btnAtras.setEnabled(false);

                java.awt.EventQueue.invokeLater(tblBenef::requestFocusInWindow);

                if (tblBenef.getRowCount() > 0) {
                    tblPartida.setRowSelectionInterval(0, 0);
                    tblBenef.scrollRectToVisible(tblBenef.getCellRect(0, 0, true));
                }
                break;

            case SEL_ITEM:
                txtOrdPago.setEnabled(false);
                tblBenef.setEnabled(false);

                txtNroFact.setEnabled(true);
                txtFechaFact.setEnabled(true);

                txtCant.setEnabled(true);
                txtDesc.setEnabled(true);
                txtPUnitario.setEnabled(true);

                tblItem.setEnabled(true);
                tblPartida.setEnabled(true);
                txaConcepto.setEnabled(true);

                cmbIvaPorcRet.setSelectedIndex(-1);
                cmbIvaPorcRet.setEnabled(conIva);
                cmbIslrPorc.setSelectedIndex(-1);
                cmbIslrPorc.setEnabled(true);

                txtCheque.setEnabled(false);

                btnInsertar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnIVA.setEnabled(!conIva);
                btnGuardar.setEnabled(true);
                btnAtras.setEnabled(true);

                java.awt.EventQueue.invokeLater(txtCant::requestFocusInWindow);
                break;
        }

    }

    /**
     * Rev 06/11/2016
     *
     */
    private void actSalir() {
        if (tblItem.getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(this, "Hay registros pendientes . ¿ Seguro desea Salir ?", "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
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

    @Override
    public void setCuenta(CuentaModel _regCuenta) {
        if (_regCuenta == null) {
            actSalir();
            return;
        }

        regCuenta = _regCuenta;

        clearComp();
    }

    @Override
    public CuentaModel getCuenta() {
        return regCuenta;
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
                new PagoImputacion(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEditarCheque;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnIVA;
    private javax.swing.JButton btnInsertar;
    private javax.swing.ButtonGroup btngPorcIva;
    private javax.swing.JCheckBox chkISLR;
    private javax.swing.JComboBox cmbIslrPorc;
    private javax.swing.JComboBox<String> cmbIva;
    private javax.swing.JComboBox cmbIvaPorcRet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tblBenef;
    private javax.swing.JTable tblItem;
    private javax.swing.JTable tblPartida;
    private javax.swing.JTextArea txaConcepto;
    private javax.swing.JFormattedTextField txtA_Pagar;
    private javax.swing.JTextPane txtBancoCuenta;
    private javax.swing.JTextField txtCant;
    private javax.swing.JTextField txtCheque;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JFormattedTextField txtFechaFact;
    private javax.swing.JFormattedTextField txtID_Compr;
    private javax.swing.JFormattedTextField txtIslrRet;
    private javax.swing.JFormattedTextField txtIvaRet;
    private javax.swing.JFormattedTextField txtIva_bs;
    private javax.swing.JFormattedTextField txtMontoPagado;
    private javax.swing.JTextField txtNroFact;
    private javax.swing.JFormattedTextField txtNumCausado;
    private javax.swing.JTextField txtNumControl;
    private javax.swing.JFormattedTextField txtOrdPago;
    private javax.swing.JFormattedTextField txtOtrasRetenciones;
    private javax.swing.JTextField txtPUnitario;
    private javax.swing.JTextField txtPendImputar;
    private javax.swing.JTextField txtRIF_CI;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JFormattedTextField txtResta;
    private javax.swing.JFormattedTextField txtSubTotal;
    private javax.swing.JFormattedTextField txtSumaTotalCau;
    private javax.swing.JFormattedTextField txtSumaTotalCau_menosRet;
    private javax.swing.JFormattedTextField txtTotalBs;
    private javax.swing.JFormattedTextField txtTotalRet;
    // End of variables declaration//GEN-END:variables

    Connection cn = Conn.getConnection();

}
