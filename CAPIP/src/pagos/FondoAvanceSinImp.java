/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package pagos;

import bancos.BancosChequeAvanceCambiar;
import bancos.BancosContabilidad;
import capipsistema.CapipPropiedades;
import static capipsistema.CapipPropiedades.CAPIP_IVA_APLICADO;
import static capipsistema.CapipPropiedades.CAPIP_IVA_PARTIDA;
import capipsistema.Conn;
import capipsistema.FrmPrincipal;
import capipsistema.Globales;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import compromisos.Compromiso;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import static java.lang.Math.min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelos.AvanceEfectivoModel;
import modelos.ChequeModel;
import modelos.IvaAplicModel;
import modelos.ModelMapNextOp;
import modelos.PagoRptModel;
import modelos.ParamRptPagAva;
import modelos.PptoModel;
import modelos.UserModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.Format;
import utils.Num2Let;

/**
 * Maneja lo relacionado con la ventana de Avance de Efectivo sin imputacion presupuestaria
 *
 * @author Capip Sistemas C.A.
 */
public final class FondoAvanceSinImp extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private final java.awt.Window parent;

    private long ultNumOrden;

    /**
     *
     * @param _parent
     */
    public FondoAvanceSinImp(final java.awt.Window _parent) {
        super();
        initComponents();

        this.parent = _parent;
        ultNumOrden = 0;

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

        // Configurar la JTable de los Beneficiarios, para manejar el ENTER
        final InputMap imBenef = tblBenef.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final ActionMap amBenef = tblBenef.getActionMap();

        final KeyStroke enterKeyBenef = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        imBenef.put(enterKeyBenef, "Action.enter");
        amBenef.put("Action.enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                tblBenefMouseClicked(null);
            }
        });

        // Para que cada vez que se modifique el campo se actualice el subtotal
        txtMontoTotal.getDocument().addDocumentListener(
            new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    setTotales();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    setTotales();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    setTotales();
                }
            });

        // Actualiza el radio button con el Iva actual
        if (rbtnIvaExcento.isSelected() || (cmbIva.getSelectedIndex() <= 0)) {
            rbtnIvaAct.setText("Iva actual 0,00 %");
        } else {
            final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
            final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
            rbtnIvaAct.setText("Iva actual " + Format.toStr(regIva.getValor_porc()) + " %");
        }

        try {
        } catch (final Exception _ex) {
            rbtnIvaAct.setText("Iva actual");
        }

        tblBenef.requestFocusInWindow();
        if (tblBenef.getRowCount() > 0) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    tblBenef.setRowSelectionInterval(0, 0);
                    tblBenef.scrollRectToVisible(tblBenef.getCellRect(0, 0, true));
                }
            });
        }

    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        Compromiso.UpdateTblBenef(tblBenef);

        try {
            setCompInitVal();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup = new javax.swing.ButtonGroup();
        buttonGroupIvaPorc = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtIvaRetenido = new javax.swing.JFormattedTextField();
        txtISLR = new javax.swing.JFormattedTextField();
        txtOtrasRetenciones = new javax.swing.JFormattedTextField();
        txtMontoRet = new javax.swing.JFormattedTextField();
        cmbIva = new javax.swing.JComboBox<String>();
        jPanel6 = new javax.swing.JPanel();
        tbtnCero = new javax.swing.JToggleButton();
        tbtn75 = new javax.swing.JToggleButton();
        tbtn100 = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        cmbCuentas = new javax.swing.JComboBox<String>();
        txtBanco = new javax.swing.JTextField();
        chkEndosable = new javax.swing.JCheckBox();
        txtCuenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNumCheque = new javax.swing.JTextField();
        txtSaldoCuenta = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtMontoTotal = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        txtIVA_Aplicado = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        txtRetenciones = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        txtA_Pagar = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaConcepto = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        rbtnIvaExcento = new javax.swing.JRadioButton();
        rbtnIvaAct = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        txtPagoNum = new javax.swing.JFormattedTextField();
        txtFecha = new javax.swing.JFormattedTextField();
        jLabel30 = new javax.swing.JLabel();
        txtRif = new javax.swing.JTextField();
        pnlCmd = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEditarCheque = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBenef = new javax.swing.JTable();
        jLabel118 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("FONDO EN AVANCE, SIN IMPUTACIÓN");
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
        txtRazonSocial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtRazonSocial.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtRazonSocial.setEnabled(false);
        getContentPane().add(txtRazonSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 290, 30));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(815, 540));
        jPanel1.setLayout(null);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);

        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("I.V.A. BS. ");
        jPanel5.add(jLabel22);
        jLabel22.setBounds(90, 50, 70, 15);

        jLabel23.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("I.S.R.L. BS. ");
        jPanel5.add(jLabel23);
        jLabel23.setBounds(40, 130, 120, 15);

        jLabel24.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel24.setText("OTRAS RETENCIONES Bs. ");
        jPanel5.add(jLabel24);
        jLabel24.setBounds(10, 170, 150, 15);

        jLabel25.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("TOTAL RETENCIONES BS. ");
        jPanel5.add(jLabel25);
        jLabel25.setBounds(10, 210, 150, 15);

        txtIvaRetenido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtIvaRetenido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIvaRetenido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIvaRetenido.setText("0,00");
        txtIvaRetenido.setToolTipText("");
        txtIvaRetenido.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtIvaRetenido.setEnabled(false);
        jPanel5.add(txtIvaRetenido);
        txtIvaRetenido.setBounds(160, 40, 120, 30);

        txtISLR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtISLR.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtISLR.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtISLR.setText("0,00");
        txtISLR.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtISLR.setEnabled(false);
        jPanel5.add(txtISLR);
        txtISLR.setBounds(160, 120, 120, 30);

        txtOtrasRetenciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtOtrasRetenciones.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtOtrasRetenciones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOtrasRetenciones.setText("0,00");
        txtOtrasRetenciones.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtOtrasRetenciones.setEnabled(false);
        jPanel5.add(txtOtrasRetenciones);
        txtOtrasRetenciones.setBounds(160, 160, 120, 30);

        txtMontoRet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtMontoRet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtMontoRet.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoRet.setText("0,00");
        txtMontoRet.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtMontoRet.setEnabled(false);
        jPanel5.add(txtMontoRet);
        txtMontoRet.setBounds(160, 200, 120, 30);

        cmbIva.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        cmbIva.setMinimumSize(new java.awt.Dimension(64, 27));
        cmbIva.setPreferredSize(new java.awt.Dimension(64, 27));
        cmbIva.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbIvaItemStateChanged(evt);
            }
        });
        jPanel5.add(cmbIva);
        cmbIva.setBounds(100, 8, 180, 27);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridBagLayout());

        buttonGroup.add(tbtnCero);
        tbtnCero.setSelected(true);
        tbtnCero.setText("0%");
        tbtnCero.setEnabled(false);
        tbtnCero.setMaximumSize(new java.awt.Dimension(70, 25));
        tbtnCero.setMinimumSize(new java.awt.Dimension(70, 25));
        tbtnCero.setPreferredSize(new java.awt.Dimension(70, 25));
        tbtnCero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnCeroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel6.add(tbtnCero, gridBagConstraints);

        buttonGroup.add(tbtn75);
        tbtn75.setText("75%");
        tbtn75.setEnabled(false);
        tbtn75.setMaximumSize(new java.awt.Dimension(70, 25));
        tbtn75.setMinimumSize(new java.awt.Dimension(70, 25));
        tbtn75.setPreferredSize(new java.awt.Dimension(70, 25));
        tbtn75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtn75ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel6.add(tbtn75, gridBagConstraints);

        buttonGroup.add(tbtn100);
        tbtn100.setText("100%");
        tbtn100.setEnabled(false);
        tbtn100.setMaximumSize(new java.awt.Dimension(70, 25));
        tbtn100.setMinimumSize(new java.awt.Dimension(70, 25));
        tbtn100.setPreferredSize(new java.awt.Dimension(70, 25));
        tbtn100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtn100ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel6.add(tbtn100, gridBagConstraints);

        jPanel5.add(jPanel6);
        jPanel6.setBounds(0, 0, 90, 120);

        jPanel1.add(jPanel5);
        jPanel5.setBounds(490, 10, 300, 250);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel3.setEnabled(false);
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbCuentas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmbCuentas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCuentasItemStateChanged(evt);
            }
        });
        jPanel3.add(cmbCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 350, 30));

        txtBanco.setEditable(false);
        txtBanco.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtBanco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtBanco.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtBanco.setEnabled(false);
        txtBanco.setFocusable(false);
        jPanel3.add(txtBanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 220, 30));

        chkEndosable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        chkEndosable.setSelected(true);
        chkEndosable.setText("Endosable");
        jPanel3.add(chkEndosable, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, -1, -1));

        txtCuenta.setEditable(false);
        txtCuenta.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtCuenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtCuenta.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtCuenta.setEnabled(false);
        txtCuenta.setFocusable(false);
        jPanel3.add(txtCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 220, 30));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("CHEQUE Nº");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 70, 30));

        txtNumCheque.setBackground(new java.awt.Color(204, 153, 0));
        txtNumCheque.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtNumCheque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumCheque.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtNumCheque.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtNumCheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumChequeKeyTyped(evt);
            }
        });
        jPanel3.add(txtNumCheque, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 130, 30));

        txtSaldoCuenta.setBackground(new java.awt.Color(204, 102, 0));
        txtSaldoCuenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSaldoCuenta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtSaldoCuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSaldoCuenta.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtSaldoCuenta.setEnabled(false);
        txtSaldoCuenta.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jPanel3.add(txtSaldoCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 140, 40));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("SALDO  EN  CUENTA");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, -1, 30));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(0, 360, 410, 180);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("MONTO TOTAL BS.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel4.add(jLabel17, gridBagConstraints);

        txtMontoTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtMontoTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtMontoTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMontoTotal.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtMontoTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoTotalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 176;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        jPanel4.add(txtMontoTotal, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setText("I.V.A  APLICADO BS.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel4.add(jLabel32, gridBagConstraints);

        txtIVA_Aplicado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtIVA_Aplicado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtIVA_Aplicado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIVA_Aplicado.setText("0,00");
        txtIVA_Aplicado.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtIVA_Aplicado.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 176;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        jPanel4.add(txtIVA_Aplicado, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel21.setText("RETENCIONES BS.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel4.add(jLabel21, gridBagConstraints);

        txtRetenciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtRetenciones.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtRetenciones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRetenciones.setText("0,00");
        txtRetenciones.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtRetenciones.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 176;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        jPanel4.add(txtRetenciones, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("A PAGAR BS.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 57;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel4.add(jLabel19, gridBagConstraints);

        txtA_Pagar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtA_Pagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtA_Pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtA_Pagar.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtA_Pagar.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 176;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        jPanel4.add(txtA_Pagar, gridBagConstraints);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(410, 360, 380, 180);

        txaConcepto.setColumns(20);
        txaConcepto.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txaConcepto.setLineWrap(true);
        txaConcepto.setRows(5);
        txaConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txaConceptoKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txaConcepto);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(0, 290, 790, 60);

        jLabel7.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel7.setText("Concepto de la Orden");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(280, 270, 134, 15);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel7.setOpaque(false);

        jLabel35.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel35.setText("I.V.A  APLICADO %");

        buttonGroupIvaPorc.add(rbtnIvaExcento);
        rbtnIvaExcento.setSelected(true);
        rbtnIvaExcento.setText("Excento    0.00 %");
        rbtnIvaExcento.setEnabled(false);
        rbtnIvaExcento.setOpaque(false);
        rbtnIvaExcento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnIvaExcentoActionPerformed(evt);
            }
        });

        buttonGroupIvaPorc.add(rbtnIvaAct);
        rbtnIvaAct.setText("Iva actual");
        rbtnIvaAct.setEnabled(false);
        rbtnIvaAct.setOpaque(false);
        rbtnIvaAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnIvaActActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel35)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnIvaExcento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbtnIvaAct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(rbtnIvaExcento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnIvaAct, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
        );

        jPanel1.add(jPanel7);
        jPanel7.setBounds(240, 10, 230, 250);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 790, 550));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel28.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("ORDEN DE PAGO Nº");
        jLabel28.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel28.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel28.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel2.add(jLabel28, java.awt.BorderLayout.NORTH);

        txtPagoNum.setEditable(false);
        txtPagoNum.setBackground(new java.awt.Color(204, 153, 0));
        txtPagoNum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtPagoNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPagoNum.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtPagoNum.setEnabled(false);
        txtPagoNum.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jPanel2.add(txtPagoNum, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, 320, 80));

        txtFecha.setBackground(new java.awt.Color(204, 153, 0));
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtFecha.setEnabled(false);
        txtFecha.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, 120, 30));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setText("FECHA");
        getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 50, -1, -1));

        txtRif.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtRif.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtRif.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtRif.setEnabled(false);
        getContentPane().add(txtRif, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 142, 30));

        pnlCmd.setOpaque(false);
        pnlCmd.setLayout(new java.awt.GridBagLayout());

        btnGuardar.setBackground(new java.awt.Color(153, 153, 0));
        btnGuardar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnGuardar.setText(" GUARDAR ");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 380, 0, 0);
        pnlCmd.add(btnGuardar, gridBagConstraints);

        btnSalir.setBackground(new java.awt.Color(153, 153, 0));
        btnSalir.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnSalir.setText(" RETORNAR ");
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        pnlCmd.add(btnSalir, gridBagConstraints);

        btnEditarCheque.setBackground(new java.awt.Color(153, 153, 0));
        btnEditarCheque.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnEditarCheque.setText(" EDITAR ULT. CH. ");
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        pnlCmd.add(btnEditarCheque, gridBagConstraints);

        btnGuardar1.setBackground(new java.awt.Color(153, 153, 0));
        btnGuardar1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnGuardar1.setText("IMPUTAR");
        btnGuardar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnGuardar1.setMaximumSize(new java.awt.Dimension(120, 27));
        btnGuardar1.setMinimumSize(new java.awt.Dimension(120, 27));
        btnGuardar1.setPreferredSize(new java.awt.Dimension(120, 27));
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        pnlCmd.add(btnGuardar1, gridBagConstraints);

        getContentPane().add(pnlCmd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 1120, 40));

        tblBenef.setAutoCreateRowSorter(true);
        tblBenef.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        tblBenef.setForeground(new java.awt.Color(0, 51, 153));
        tblBenef.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BENEFICIARIOS", "RIF / CI", "DIRECCIÓN", "TELÉFONOS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tblBenef.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
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
            tblBenef.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblBenef.getColumnModel().getColumn(1).setMaxWidth(150);
            tblBenef.getColumnModel().getColumn(2).setMinWidth(100);
            tblBenef.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblBenef.getColumnModel().getColumn(2).setMaxWidth(250);
        }

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, 320, 420));

        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        jLabel118.setMinimumSize(new java.awt.Dimension(1280, 642));
        jLabel118.setPreferredSize(new java.awt.Dimension(1280, 642));
        getContentPane().add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 1150, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     *
     * @param evt
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        // Validar Todo
        //
        ////////////////////////////////////////////////////////////////////
        // Seccion de Compromiso
        //
        // Verifica que se halla seleccionado un beneficiario
        String razonSocial = txtRazonSocial.getText().trim().toUpperCase();
        if (razonSocial.isEmpty()) {
            tblBenef.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Beneficiario");
            return;
        }

        //
        String RifCI = txtRif.getText().trim().toUpperCase();
        if (RifCI.isEmpty()) {
            txtRif.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Error en el RIF / C.I. del Beneficiario");
            return;
        }

        // Verificar que la fecha tenga un formato valido
        final String sFecha;
        final java.util.Date dateFecha;
        try {
            sFecha = txtFecha.getText().trim();
            dateFecha = Globales.dateFormat.parse(sFecha);
        } catch (final Exception _ex) {
            txtFecha.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Error en la Fecha");
            return;
        }

        // Verificar el concepto
        String auxConcepto = txaConcepto.getText().trim().toUpperCase();
        if (auxConcepto.isEmpty()) {
            txaConcepto.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Concepto inválido");
            return;
        }

        final String concepto = auxConcepto.substring(0, min(500, auxConcepto.length()));

        // Chequeo adicional, para saber si se ha indicado el monto a pagar, antes de la retencion
        final String sMontoTotal = txtMontoTotal.getText().trim();
        if (sMontoTotal.isEmpty()) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto inválido");
            return;
        }

        // Verificar el formato del monto
        final Double dMontoTotal;
        try {
            dMontoTotal = (Double) Globales.curFormat.stringToValue(sMontoTotal);
        } catch (final Exception _ex) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto inválido" + System.getProperty("line.separator") + _ex);
            return;
        }

        if (dMontoTotal <= 0.00d) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto total inválido");
            return;
        }

        // Chequeo adicional, para saber si se ha indicado el monto a pagar, despues de la retencion
        final String sMontoAPagar = txtA_Pagar.getText().trim();
        if (sMontoAPagar.isEmpty()) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto a pagar inválido");
            return;
        }

        final Double dMontoPagar;
        try {
            dMontoPagar = (Double) Globales.curFormat.stringToValue(sMontoAPagar);
        } catch (final Exception _ex) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Error al recuperar el monto Total: " + System.getProperty("line.separator") + _ex);
            return;
        }

        if (dMontoPagar <= 0.00d) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto a pagar inválido");
            return;
        }

        final String sIVA = txtIVA_Aplicado.getText().trim();
        if (sIVA.isEmpty()) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "IVA inválido");
            return;
        }

        final Double dMontoIVA;
        try {
            dMontoIVA = (Double) Globales.curFormat.stringToValue(sIVA);
        } catch (final Exception _ex) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Error en el IVA" + System.getProperty("line.separator") + _ex);
            return;
        }

        if ((dMontoIVA < 0.00d) || (dMontoIVA > dMontoTotal)) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Error en el IVA");
            return;
        }

        // Monto grabable 
        final String sTotGra;
        if (rbtnIvaExcento.isSelected()) {
            sTotGra = "0,00";
        } else {
            sTotGra = txtMontoTotal.getText().trim();
        }
        if (sTotGra.isEmpty()) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto grabable inválido");
            return;
        }

        final Double dTotGra;
        try {
            dTotGra = (Double) Globales.curFormat.stringToValue(sTotGra);
        } catch (final Exception _ex) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto grabable inválido" + System.getProperty("line.separator") + _ex);
            return;
        }

        if ((dTotGra < 0.00d) || (dTotGra > dMontoTotal)) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto grabable inválido");
            return;
        }

        final Double dTotExe;
        if (rbtnIvaExcento.isSelected()) {
            dTotExe = dMontoTotal - dTotGra - dMontoIVA;
        } else {
            dTotExe = 0.00d;
        }

        if ((dTotExe < 0.00d) || (dTotExe > dMontoTotal)) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto excento inválido");
            return;
        }

        final String sTotExe;
        try {
            sTotExe = Globales.curFormat.valueToString(dTotExe);
        } catch (final Exception _ex) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto excento inválido" + System.getProperty("line.separator") + _ex);
            return;
        }

        if (sTotExe.isEmpty()) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto excento inválido");
            return;
        }

        final Double dBaseI = dMontoTotal - dMontoIVA;
        if ((dBaseI < 0.00d) || (dBaseI > dMontoTotal)) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Base imponible inválido");
            return;
        }

        final String sBaseI;
        try {
            sBaseI = Globales.curFormat.valueToString(dBaseI);
        } catch (final Exception _ex) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Base imponible inválido" + System.getProperty("line.separator") + _ex);
            return;
        }

        // Esto es para mostrar el monto formateado
        try {
            txtMontoTotal.setText(Globales.curFormat.valueToString(dMontoTotal));
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al formatear el monto total: " + System.getProperty("line.separator") + _ex);
            return;
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Validacion de la Retencion
        //
        final String sMontoRet = txtMontoRet.getText().trim();
        if (sMontoRet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Monto retenido inválido");
            return;
        }

        final Double dMontoRet;
        try {
            dMontoRet = (Double) Globales.curFormat.stringToValue(sMontoRet);
        } catch (final Exception _ex) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto retenido inválido" + System.getProperty("line.separator") + _ex);
            return;
        }

        if ((dMontoRet < 0.00d) || (dMontoRet > dMontoTotal)) {
            txtMontoTotal.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Monto retenido inválido");
            return;
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Validacion de Pago
        //
        if (cmbCuentas.getSelectedIndex() < 0) {
            cmbCuentas.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Debe seleccionar un número de cuenta");
            return;
        }

        final String cuenta = ((String) cmbCuentas.getSelectedItem()).substring(0, 20);

        final double saldo;
        try {
            saldo = BancosContabilidad.getSaldoF(cuenta, Globales.getEjeFisYear(), 12).doubleValue();
        } catch (final Exception _ex) {
            txtSaldoCuenta.setText("");
            txtNumCheque.setText("");
            JOptionPane.showMessageDialog(null, "Saldo inválido" + System.getProperty("line.separator") + _ex);
            return;
        }

        try {
            txtSaldoCuenta.setText(Globales.curFormat.valueToString(saldo));
        } catch (final Exception _ex) {
            txtSaldoCuenta.setText("");
            txtNumCheque.setText("");
            JOptionPane.showMessageDialog(null, "Saldo inválido" + System.getProperty("line.separator") + _ex);
            return;
        }

        if (saldo < dMontoPagar) {
            txtNumCheque.setText("");
            cmbCuentas.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            return;
        }

        String nroCheque = txtNumCheque.getText().trim().toUpperCase();
        if (nroCheque.isEmpty()) {
            txtNumCheque.requestFocusInWindow();
            JOptionPane.showMessageDialog(null, "Debe indicar el número de cheque");
            return;
        }

        // Verificar si el soporte/cheque para la cuenta es valido
        try {
            final PreparedStatement pstCheck = Conn.getConnection().prepareStatement("SELECT * FROM bancos_operaciones "
                + "WHERE cuenta= ? AND soporte_o_cheque= ?");
            pstCheck.setString(1, cuenta);
            pstCheck.setString(2, nroCheque);

            final ResultSet rs = pstCheck.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Soporte/Cheque inválido, posiblemente ya se halla utilizado");
                return;
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de verificar el número de cheque" + System.getProperty("line.separator") + _ex);
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "¿Seguro desea Guardar?",
            "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {

            try {
                Conn.BeginTransaction();

                final ParamRptPagAva avaRptParam = AvaGuardar();
                rptOrdAva(avaRptParam, true);
                rptChAva(avaRptParam.getId_orden_pago(), avaRptParam);

                Conn.EndTransaction();

                setCompInitVal();
                ultNumOrden = avaRptParam.getId_orden_pago();
                btnEditarCheque.setEnabled(true);

            } catch (final Exception _ex) {
                try {
                    Conn.RollBack();
                } catch (final Exception _ex1) {
                    JOptionPane.showMessageDialog(null, _ex1);
                }
                JOptionPane.showMessageDialog(null, "Error al finalizar la operación: " + System.getProperty("line.separator") + _ex);
            }
        }

        tblBenef.requestFocusInWindow();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        final boolean salir;
        final String montoAPagar = txtA_Pagar.getText();
        if (montoAPagar.isEmpty() || montoAPagar.equals("0,00")) {
            salir = true;
        } else {
            salir = JOptionPane.showConfirmDialog(this, "¿Hay datos cargados . ¿ Seguro desea Salir ?",
                "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
        }

        if (salir) {
            if (parent != null) {
                parent.setVisible(true);
            }
            setVisible(false);
            dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblBenefMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBenefMouseClicked
        int fila = tblBenef.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return;
        }

        txtRazonSocial.setText(tblBenef.getValueAt(fila, 0).toString());
        txtRazonSocial.setCaretPosition(0);
        txtRif.setText(tblBenef.getValueAt(fila, 1).toString());

        txaConcepto.requestFocusInWindow();
    }//GEN-LAST:event_tblBenefMouseClicked

    private void tbtnCeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnCeroActionPerformed
        setTotales();
    }//GEN-LAST:event_tbtnCeroActionPerformed

    private void tbtn75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtn75ActionPerformed
        setTotales();
    }//GEN-LAST:event_tbtn75ActionPerformed

    private void tbtn100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtn100ActionPerformed
        setTotales();
    }//GEN-LAST:event_tbtn100ActionPerformed

    private void rbtnIvaActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnIvaActActionPerformed
        tbtnCero.setEnabled(true);
        tbtn75.setEnabled(true);
        tbtn100.setEnabled(true);
        setTotales();
    }//GEN-LAST:event_rbtnIvaActActionPerformed

    private void rbtnIvaExcentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnIvaExcentoActionPerformed
        tbtnCero.setEnabled(false);
        tbtn75.setEnabled(false);
        tbtn100.setEnabled(false);
        setTotales();
    }//GEN-LAST:event_rbtnIvaExcentoActionPerformed

    private void txaConceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaConceptoKeyTyped
        if (txaConcepto.getText().length() >= 500) {
            evt.consume();
        }
    }//GEN-LAST:event_txaConceptoKeyTyped

    private void btnEditarChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarChequeActionPerformed
        if (ultNumOrden <= 0) {
            return;
        }

        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BancosChequeAvanceCambiar(me, ultNumOrden).setVisible(true);
                me.setVisible(false);
            }
        });

    }//GEN-LAST:event_btnEditarChequeActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagoImputacion(me).setVisible(true);
                me.setVisible(false);
            }
        });

    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void cmbCuentasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCuentasItemStateChanged

        if (cmbCuentas.getSelectedIndex() < 0) {
            txtBanco.setText("");
            txtCuenta.setText("");
            txtNumCheque.setText("");
            txtSaldoCuenta.setText("0,00");
            return;
        }

        final String Mensaje = cmbCuentas.getSelectedItem().toString();
        final String cuenta = Mensaje.substring(0, 20);
        txtCuenta.setText(cuenta);
        txtBanco.setText(Mensaje.substring(21));
        double saldo = 0;
        try {
            saldo = BancosContabilidad.getSaldoF(cuenta, Globales.getEjeFisYear(), 12).doubleValue();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "Error" + System.getProperty("line.separator") + _ex);
        }
        txtSaldoCuenta.setText(Format.toStr(saldo));

        final double apagar;
        try {
            final String aux = txtA_Pagar.getText().trim();
            if (aux.isEmpty()) {
                apagar = 0.00d;
            } else {
                apagar = Format.toDouble(aux);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, _ex);
            txtA_Pagar.requestFocusInWindow();
            return;
        }

        if (apagar <= 0) {
            JOptionPane.showMessageDialog(this, "Monto a pagar inválido");
            txtA_Pagar.requestFocusInWindow();
            return;
        }

        if (saldo < apagar) {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            PagoOrden.cargarComboCuentas(cmbCuentas, -1);
            return;
        }

        txtNumCheque.requestFocusInWindow();
        btnGuardar.setEnabled(true);
    }//GEN-LAST:event_cmbCuentasItemStateChanged

    private void txtNumChequeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumChequeKeyTyped
        if (txtNumCheque.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumChequeKeyTyped

    private void txtMontoTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoTotalActionPerformed
        cmbCuentas.requestFocusInWindow();
    }//GEN-LAST:event_txtMontoTotalActionPerformed

    private void cmbIvaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIvaItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            final int selIdx = cmbIva.getSelectedIndex();
            if (selIdx >= 0) {
                if (rbtnIvaExcento.isSelected() || (cmbIva.getSelectedIndex() <= 0)) {
                    rbtnIvaAct.setText("Iva actual 0,00 %");
                } else {
                    final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
                    final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
                    rbtnIvaAct.setText("Iva actual " + Format.toStr(regIva.getValor_porc()) + " %");
                }
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cmbIvaItemStateChanged

    private void checkIdAvaEfe() throws Exception {
        final ResultSet rs = Conn.executeQuery("SELECT MAX(id+1) FROM avance_efectivo");
        if (rs.next()) {
            final String aux = rs.getString(1);
            if (aux == null) {
                txtPagoNum.setText("1");
            } else {
                txtPagoNum.setText(aux);
            }
        } else {
            txtPagoNum.setText("1");
        }
    }

    private ParamRptPagAva AvaGuardar() throws Exception {

        final String sMontoAPagar = txtA_Pagar.getText().trim();
        if (sMontoAPagar.isEmpty()) {
            txtMontoTotal.requestFocusInWindow();
            throw new Exception("Monto a pagar");
        }

        final double dMontoPagar;
        try {
            dMontoPagar = (double) Globales.curFormat.stringToValue(sMontoAPagar);
        } catch (final Exception _ex) {
            throw new Exception("Monto a pagar", _ex);
        }

        if (dMontoPagar <= 0.00d) {
            throw new Exception("Monto a pagar");
        }

        if (cmbCuentas.getSelectedIndex() < 0) {
            cmbCuentas.requestFocusInWindow();
            throw new Exception("Cuenta seleccionada");
        }

        final String cuenta = ((String) cmbCuentas.getSelectedItem()).substring(0, 20);

        final double saldo;
        try {
            saldo = BancosContabilidad.getSaldoF(cuenta, Globales.getEjeFisYear(), 12).doubleValue();
        } catch (final Exception _ex) {
            txtSaldoCuenta.setText("");
            txtNumCheque.setText("");
            throw _ex;
        }

        try {
            txtSaldoCuenta.setText(Globales.curFormat.valueToString(saldo));
        } catch (final Exception _ex) {
            txtSaldoCuenta.setText("");
            txtNumCheque.setText("");
            throw new Exception("Saldo");
        }

        if (saldo < dMontoPagar) {
            txtNumCheque.setText("");
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            throw new Exception("Saldo insuficiente");
        }

        String nroCheque = txtNumCheque.getText().trim().toUpperCase();
        if (nroCheque.isEmpty()) {
            txtNumCheque.requestFocusInWindow();
            throw new Exception("Número de cheque");
        }

        // Verificar si el soporte/cheque para la cuenta, en el presente ejercicio fiscal es valido
        try {
            final PreparedStatement pstCheck = Conn.getConnection().prepareStatement("SELECT * FROM bancos_operaciones "
                + "WHERE cuenta= ? AND soporte_o_cheque= ?");
            pstCheck.setString(1, cuenta);
            pstCheck.setString(2, nroCheque);

            final ResultSet rs = pstCheck.executeQuery();
            if (rs.next()) {
                throw new Exception("Soporte/Cheque inválido, posiblemente ya se halla utilizado");
            }
        } catch (final Exception _ex) {
            throw new Exception(_ex);
        }

        final ParamRptPagAva avaRptParam = pagoMasterGenerar();
        avaDetailGenerar(String.valueOf(avaRptParam.getId_orden_pago()));

        return avaRptParam;
    }

    /**
     * Rev 24/11/2016
     *
     * @return
     * @throws Exception
     */
    private ParamRptPagAva pagoMasterGenerar() throws Exception {

        final Integer numAdeEfe;
        final String rif;
        final String razonSocial;
        final BigDecimal a_pagar_bs;
        final java.sql.Date fechaHoy;
        final String obs;
        final String banco;
        final String sCuenta;
        final String cheque;
        final String endosable_sn;
        final java.sql.Date fechaCheque;

        rif = txtRif.getText();
        razonSocial = txtRazonSocial.getText();
        a_pagar_bs = Format.toBigDec(txtA_Pagar.getText().trim());
        fechaHoy = new java.sql.Date(Globales.getServerTimeStamp().getTime());
        fechaCheque = fechaHoy;

        String auxConcepto = txaConcepto.getText().trim().toUpperCase();
        if (auxConcepto.isEmpty()) {
            txaConcepto.requestFocusInWindow();
            throw new Exception("Concepto");
        }

        obs = auxConcepto.substring(0, min(500, auxConcepto.length()));

        banco = txtBanco.getText();
        sCuenta = txtCuenta.getText();
        cheque = txtNumCheque.getText().trim().toUpperCase();
        endosable_sn = chkEndosable.isSelected() ? "S" : "N";

        final Calendar fechaOp = Calendar.getInstance();
        final long ejeFis = fechaOp.get(Calendar.YEAR);
        final long ejeFisMes = ejeFis * 100 + fechaOp.get(Calendar.MONTH) + 1;

        final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO avance_efectivo"
            + "(id_user, id_session, date_session, ejefis, " // 1, 2, 3, 4
            + "benef_razonsocial, benef_rif_ci, fecha_pago, observacion, " // 5, 6, 7, 8, 
            + "banco, cuenta, cheque, fecha_cheque, endosable_sn, a_pagar_bs) " // 9, 10, 11, 12, 13, 14
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst.setLong(1, UserPassIn.getIdUser());
        pst.setLong(2, UserPassIn.getIdSession());
        pst.setTimestamp(3, UserPassIn.getDateSession()); // date_session
        pst.setDate(4, Globales.getEjefis());
        pst.setString(5, razonSocial);
        pst.setString(6, rif); // rifcli
        pst.setDate(7, fechaHoy); // fecha
        pst.setString(8, obs); // observacion
        pst.setString(9, banco); // banco
        pst.setString(10, sCuenta); // cuenta
        pst.setString(11, cheque); // cheque
        pst.setDate(12, fechaCheque); // fecha cheque
        pst.setString(13, endosable_sn);
        pst.setBigDecimal(14, a_pagar_bs); // monto_pagar
        if (pst.executeUpdate() != 1) {
            throw new Exception("Error al insertar el registro");
        }

        // Recobrar el numero de pago
        final ResultSet rsId = Conn.getConnection().prepareStatement("SELECT last_insert_id() AS last_id FROM avance_efectivo").executeQuery();
        if (rsId.next()) {
            numAdeEfe = rsId.getInt("last_id");
        } else {
            numAdeEfe = 0;
        }

        final String sFecha = txtFecha.getText();
        final long iCuenta;
        final double capcan;
        try {
            iCuenta = ModelMapNextOp.getNext(Globales.getEjeFisYear(), sCuenta);
            capcan = (Double) Globales.curFormat.stringToValue(txtA_Pagar.getText().trim().toUpperCase());
        } catch (final Exception _ex) {
            throw new Exception("Monto a Pagar");
        }

        // Verificar si el soporte/cheque para la cuenta, en el presente ejercicio fiscal es valido
        try {
            final PreparedStatement pstCheck = Conn.getConnection().prepareStatement("SELECT * FROM bancos_operaciones "
                + "WHERE ejefis= ? AND cuenta= ? AND tipo_operacion= 'FA' AND soporte_o_cheque= ?");
            pstCheck.setLong(1, ejeFis);
            pstCheck.setString(2, sCuenta);
            pstCheck.setString(3, cheque);

            final ResultSet rs = pstCheck.executeQuery();
            if (rs.next()) {
                throw new Exception("Cheque inválido, posiblemente ya se halla utilizado");
            }
        } catch (final Exception _ex) {
            throw new Exception(_ex);
        }

        final PreparedStatement pst2 = Conn.getConnection().prepareStatement("INSERT INTO bancos_operaciones("
            + "cuenta, banco, descripcion, fecha, tipo_operacion, "
            + "soporte_o_cheque, conciliado, debe, haber, cuenta_numop, cuenta_numop_tipo, "
            + "num_pag_ava, ejefis, ejefismes, iduser) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pst2.setString(1, sCuenta); // cuenta
        pst2.setString(2, banco); // banco
        pst2.setString(3, "FA" + "-" + numAdeEfe + " - " + razonSocial); // desc
        pst2.setString(4, sFecha); // fecha
        pst2.setString(5, "FA"); // tipo
        pst2.setString(6, cheque); // soporte
        pst2.setString(7, "EN TRANSITO"); // conciliado
        pst2.setDouble(8, 0.00d); // debe
        pst2.setDouble(9, a_pagar_bs.doubleValue()); // haber
        pst2.setLong(10, iCuenta); // cuenta_numop
        pst2.setLong(11, ModelMapNextOp.getNext(Globales.getEjeFisYear(), sCuenta + "-" + "FA")); // cuenta_numop
        pst2.setInt(12, numAdeEfe); // pago_ava
        pst2.setLong(13, ejeFis);
        pst2.setLong(14, ejeFisMes);
        pst2.setLong(15, UserPassIn.getIdUser());
        pst2.executeUpdate();

        return genParamRptAva(numAdeEfe);
    }

    /**
     * Rev 22/11/2016
     *
     * @param _sNumpago
     * @throws SQLException
     * @throws FormatException
     * @throws RangeException
     * @throws ValueException
     */
    private void avaDetailGenerar(final String _sNumpago) throws Exception {

        String sMontoPagar = txtA_Pagar.getText().trim();
        if (sMontoPagar.isEmpty()) {
            txtMontoTotal.requestFocusInWindow();
            throw new Exception("Monto a pagar");
        }

        final Double dMontoPagar;
        try {
            dMontoPagar = (Double) Globales.curFormat.stringToValue(sMontoPagar);
        } catch (final Exception _ex) {
            txtMontoTotal.requestFocusInWindow();
            throw new Exception("Monto a pagar");
        }

        if (dMontoPagar <= 0.00d) {
            txtMontoTotal.requestFocusInWindow();
            throw new Exception("Monto a pagar");
        }

        try {
            sMontoPagar = Globales.curFormat.valueToString(dMontoPagar);
            txtA_Pagar.setText(sMontoPagar);
        } catch (final Exception exception) {
            throw new Exception("Monto a pagar");
        }

        final Calendar fechaOp = Calendar.getInstance();
        final java.sql.Date fchSession = new java.sql.Date(fechaOp.getTimeInMillis());

        fechaOp.add(Calendar.DAY_OF_MONTH, -2);
        final java.sql.Date fchDelete = new java.sql.Date(fechaOp.getTimeInMillis());

        // Borrar la tabla auxiliar para el reporte
        Conn.getConnection().prepareStatement("DELETE FROM ava_efe_aux_report WHERE fchsession <= '" + fchDelete + "' OR iduser=" + UserPassIn.getIdUser() + " AND idsession=" + UserPassIn.getIdSession()).executeUpdate();

        final PreparedStatement pst2 = Conn.getConnection().prepareStatement("INSERT INTO ava_efe_aux_report(codpresu,partida,total,fchsession,idsession,iduser) VALUES (?,?,?,?,?,?)");
        pst2.setString(1, "SIN IMP"); // cod presup
        pst2.setString(2, "SIN IMP"); // partida
        pst2.setDouble(3, dMontoPagar);
        pst2.setDate(4, fchSession);
        pst2.setLong(5, UserPassIn.getIdSession());
        pst2.setLong(6, UserPassIn.getIdUser());
        pst2.executeUpdate();
    }

    /**
     * Rev 22/11/2016
     *
     * @param _id_ava
     * @return
     * @throws Exception
     */
    public static ParamRptPagAva genParamRptAva(long _id_ava) throws Exception {
        if (_id_ava < 0) {
            throw new Exception("Número de orden inválido");
        }

        AvanceEfectivoModel regAva;
        final ResultSet rsAva = Conn.executeQuery("SELECT * FROM avance_efectivo WHERE id= " + _id_ava);
        if (rsAva.next()) {
            regAva = new AvanceEfectivoModel(rsAva);
        } else {
            throw new Exception("Número de orden inválida, no encontrada");
        }

        final String NumLiteral = Num2Let.convert(regAva.getA_pagar_bs());
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

        return new ParamRptPagAva(regAva.getId(), "", "",
            regAva.getBenef_razonsocial(), regAva.getBenef_rif_ci(), "", Format.toStr(regAva.getA_pagar_bs()), regAva.getBanco(),
            regAva.getCuenta(), regAva.getCheque(), "0,00", "0,00", Format.toStr(regAva.getA_pagar_bs()), "0,00", // Monto totat == monto pagar
            Format.toStr(regAva.getFecha_pago()), NumLiteral_1, NumLiteral_2, regAva.getObservacion());
    }

    /**
     * Rev 18/01/2017
     *
     * @param _param
     * @param _export
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void rptOrdAva(ParamRptPagAva _param, boolean _export) throws Exception {
        if (_param == null) {
            return;
        }

        final Map<String, Object> param = new HashMap<>(101);
        param.put("numPag", _param.getId_orden_pago());

        param.put("NumLiteral_1", _param.getEnLetras_1());
        param.put("NumLiteral_2", _param.getEnLetras_2());

        param.put("montoPagar", _param.getMontoPagar());
        param.put("montoTotal", _param.getMontoTotal());
        param.put("resta", _param.getResta());
        param.put("razonSocial", _param.getBenef_RazonSocial());
        param.put("rif", _param.getBenef_rif_ci());
        param.put("banco", _param.getBanco());
        param.put("cuenta", _param.getCuenta());
        param.put("cheque", _param.getCheque());
        param.put("ivaRet", _param.getIvaRet());
        param.put("ivaTotal", _param.getIvaTot());
        param.put("concepto", _param.getConcepto());
        param.put("fechaHoy", _param.getFechaHoy());
        param.put("numcaus", _param.getNumCaus());
        param.put("numcomprs", _param.getNumComprs());
        param.put("titulo", "FONDO DE AVANCE");
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

        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/avanceEfectivo.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
//                        JasperViewer.viewReport(JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), cn), false);

                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), cn);

                        JasperViewer.viewReport(jasperPrint, false);

                        if (_export) {
                            JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/" + _param.getId_orden_pago() + "_avance.pdf");
                        }

                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Reporte de adelanto de efectivo no encontrado");
        }
    }

    /**
     * Rev 22/11/2016
     *
     * @param _id_orden_pago
     * @param _paramAva
     * @throws Exception
     */
    @SuppressWarnings({"unchecked"})
    public static void rptChAva(long _id_orden_pago, ParamRptPagAva _paramAva) throws Exception {

        final Map<String, Object> param = new HashMap<>(101);
        param.put("Numorden", String.valueOf(_id_orden_pago));
        param.put("Numliteral_1", _paramAva.getEnLetras_1());
        param.put("Numliteral_2", _paramAva.getEnLetras_2());
        param.put("iduser", UserPassIn.getIdUser());
        param.put("idsession", UserPassIn.getIdSession());
        param.put("user", UserPassIn.getIdUser() <= 0 ? "DEBUG" : UserModel.getUser(UserPassIn.getIdUser()));
        param.put("rpt_fecha_hora", Globales.getServerTimeStamp());
        param.put("ciudad", CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL);

        final AvanceEfectivoModel reg;
        final ResultSet rs = Conn.executeQuery("SELECT * FROM avance_efectivo WHERE id= " + _id_orden_pago);
        if (rs.next()) {
            reg = new AvanceEfectivoModel(rs);
        } else {
            JOptionPane.showMessageDialog(null, "Error" + System.getProperty("line.separator") + "Registro no encontrado");
            return;
        }

        final ArrayList<ChequeModel> list = new ArrayList<>();
        final ChequeModel cheque = new ChequeModel(Format.toStr(reg.getA_pagar_bs()), reg.getBenef_razonsocial(), reg.getBenef_rif_ci(), _paramAva.getEnLetras_1(), _paramAva.getEnLetras_2(), CapipPropiedades.CAPIP_CLIENTE_DOMICILIO_FISCAL, new java.util.Date(reg.getFecha_pago().getTime()), reg.getEndosable_sn().equals("S") ? "" : "NO ENDOSABLE");
        list.add(cheque);

        final Class aClass = FrmPrincipal.getInstance().getClass();

        final InputStream pathRpt = aClass.getResourceAsStream("/reportes/cheque_global.jasper");
        if (pathRpt != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(200);

                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRpt), new HashMap<>(param), new JRBeanCollectionDataSource(list));

                        JasperViewer.viewReport(jasperPrint, false);

                        JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/" + _id_orden_pago + "-CH-AvaEfe.pdf");
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Cheque no encontrado");
            return;
        }

        // Los campos se combierten en parametros
        param.put("razonSocial", cheque.getBenef_razonsocial());
        param.put("rif_ci", cheque.getBenef_rif_ci());
        param.put("ciudad", cheque.getCiudad());
        param.put("endosable", cheque.getEndosable());
        param.put("fecha", cheque.getFecha());
        param.put("montoPagar", cheque.getMontoPagar());
        param.put("NumLiteral_1", cheque.getNumLiteral_1());
        param.put("NumLiteral_2", cheque.getNumLiteral_2());

        // Se colocan los parametros como si fuera una orden de pago
        param.put("numPag", _paramAva.getId_orden_pago());
        param.put("montoPagar", _paramAva.getMontoPagar());
        param.put("montoTotal", _paramAva.getMontoTotal());
        param.put("resta", _paramAva.getResta());
        param.put("razonSocial", _paramAva.getBenef_RazonSocial());
        param.put("rif", _paramAva.getBenef_rif_ci());
        param.put("banco", _paramAva.getBanco());
        param.put("cuenta", _paramAva.getCuenta());
        param.put("cheque", _paramAva.getCheque());
        param.put("ivaRet", _paramAva.getIvaRet());
        param.put("ivaTotal", _paramAva.getIvaTot());
        param.put("concepto", _paramAva.getConcepto());
        param.put("fechaHoy", _paramAva.getFechaHoy());
        param.put("numcaus", _paramAva.getNumCaus());
        param.put("numcomprs", _paramAva.getNumComprs());
        param.put("titulo", "FONDO DE AVANCE");
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

        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        String x = (String) param.get("endosable");

        // Generar y agrupar los registros
        final ArrayList<PagoRptModel> list2 = new ArrayList<>();
        list2.add(new PagoRptModel("SIN IMP", "SIN IMPUTACIÓN", reg.getA_pagar_bs()));

        final InputStream pathRptAsiento = aClass.getResourceAsStream("/reportes/asiento_de_cheque.jasper");
        if (pathRptAsiento != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(100);

                        final JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(pathRptAsiento), new HashMap<>(param), new JRBeanCollectionDataSource(list2));

                        JasperViewer.viewReport(jasperPrint, false);

                        JasperExportManager.exportReportToPdfFile(jasperPrint, CapipPropiedades.CAPIP_DIR_LOCAL_ORDENPAGO + "/" + _id_orden_pago + "_asiento_ch.pdf");
                    } catch (final Exception _ex) {
                        JOptionPane.showMessageDialog(null, _ex);
                    }
                }
            }.start();
        } else {
            JOptionPane.showMessageDialog(null, "Asiento de Cheque no encontrado");
        }

    }

    /**
     * Establece los valores iniciales de los componentes
     *
     */
    private void setCompInitVal() throws Exception {

        btnEditarCheque.setEnabled(ultNumOrden > 0);

        txtRazonSocial.setText("");
        txtRif.setText("");
        txtFecha.setText(Globales.dateFormat.format(Globales.getServerTimeStamp()));

        txtIvaRetenido.setText("0,00");
        txtISLR.setText("0,00");
        txtOtrasRetenciones.setText("0,00");
        txtMontoRet.setText("0,00");

        checkIdAvaEfe();

        txaConcepto.setText("");

        Compromiso.cargarComboIva(cmbIva);
        txtMontoTotal.setText("");
        txtIVA_Aplicado.setText("0,00");
        txtRetenciones.setText("0,00");
        txtA_Pagar.setText("");

        cmbCuentas.setSelectedIndex(-1);
        txtBanco.setText("");
        txtCuenta.setText("");
        txtNumCheque.setText("");
        txtSaldoCuenta.setText("");

        rbtnIvaExcento.setSelected(true);
        tbtnCero.setSelected(true);

        PagoOrden.cargarComboCuentas(cmbCuentas, -1);
    }

    /**
     * Rev 22/11/2016
     *
     */
    private void setTotales() {

        String sMontoTotal = txtMontoTotal.getText();

        if (sMontoTotal.isEmpty()) {
            txtIvaRetenido.setText("");
            txtMontoRet.setText("");
            txtIVA_Aplicado.setText("");
            txtA_Pagar.setText("");
            return;
        }

        final Double montoTotal;
        try {
            montoTotal = (Double) Globales.curFormat.stringToValue(sMontoTotal);
        } catch (final Exception _ex) {
            txtIvaRetenido.setText("");
            txtMontoRet.setText("");
            txtIVA_Aplicado.setText("");
            txtA_Pagar.setText("");
            JOptionPane.showMessageDialog(null, "Monto Total inválido: " + System.getProperty("line.separator") + _ex);
            return;
        }

        final double porcIvaRetenido;

        if (tbtnCero.isSelected()) {
            porcIvaRetenido = 0.00d;
        } else if (tbtn75.isSelected()) {
            porcIvaRetenido = 0.75d;
        } else {
            porcIvaRetenido = 1.00d;
        }

        final BigDecimal ivaAplicado;
        if (rbtnIvaExcento.isSelected() || (cmbIva.getSelectedIndex() <= 0)) {
            ivaAplicado = new BigDecimal(0.00d).setScale(2, RoundingMode.HALF_UP);
        } else {
            final long id_iva_aplicado = Long.valueOf(((String) cmbIva.getSelectedItem()).split("\t")[2].trim());
            final IvaAplicModel regIva = CAPIP_IVA_APLICADO.get(id_iva_aplicado);
            final PptoModel regPpto = CAPIP_IVA_PARTIDA.get(id_iva_aplicado);

            ivaAplicado = BigDecimal.valueOf(montoTotal * regIva.getValor_porc().doubleValue() / 100.00d).setScale(2, RoundingMode.HALF_UP);
        }

        final BigDecimal ivaRet = ivaAplicado.multiply(new BigDecimal(porcIvaRetenido)).setScale(2, RoundingMode.HALF_UP);
        final BigDecimal montoAPagar = (new BigDecimal(montoTotal)).subtract(ivaRet).setScale(2, RoundingMode.HALF_UP);

        try {
            txtIvaRetenido.setText(Globales.curFormat.valueToString(ivaRet.doubleValue()));
            txtMontoRet.setText(Globales.curFormat.valueToString(ivaRet.doubleValue()));
            txtRetenciones.setText(Globales.curFormat.valueToString(ivaRet.doubleValue()));
            txtIVA_Aplicado.setText(Globales.curFormat.valueToString(ivaAplicado.doubleValue()));
            txtA_Pagar.setText(Globales.curFormat.valueToString(montoAPagar.doubleValue()));
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, "Error al formatear: " + System.getProperty("line.separator") + _ex);
            txtIvaRetenido.setText("");
            txtMontoRet.setText("");
            txtIVA_Aplicado.setText("");
            txtA_Pagar.setText("");
        }
    }

    void cargarComboCuentas() {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM bancos ORDER BY banco, cuenta ASC");
            while (rs.next()) {
                modeloCombo.addElement(rs.getObject("cuenta") + " " + rs.getObject("banco"));
            }
            cmbCuentas.setModel(modeloCombo);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        cmbCuentas.setSelectedIndex(-1);
    }

    /**
     * Rev 06/11/2016
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
                new FondoAvanceSinImp(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditarCheque;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.ButtonGroup buttonGroupIvaPorc;
    private javax.swing.JCheckBox chkEndosable;
    private javax.swing.JComboBox<String> cmbCuentas;
    private javax.swing.JComboBox<String> cmbIva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel pnlCmd;
    private javax.swing.JRadioButton rbtnIvaAct;
    private javax.swing.JRadioButton rbtnIvaExcento;
    private javax.swing.JTable tblBenef;
    private javax.swing.JToggleButton tbtn100;
    private javax.swing.JToggleButton tbtn75;
    private javax.swing.JToggleButton tbtnCero;
    private javax.swing.JTextArea txaConcepto;
    private javax.swing.JFormattedTextField txtA_Pagar;
    private javax.swing.JTextField txtBanco;
    private javax.swing.JTextField txtCuenta;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JFormattedTextField txtISLR;
    private javax.swing.JFormattedTextField txtIVA_Aplicado;
    private javax.swing.JFormattedTextField txtIvaRetenido;
    private javax.swing.JFormattedTextField txtMontoRet;
    private javax.swing.JFormattedTextField txtMontoTotal;
    private javax.swing.JTextField txtNumCheque;
    private javax.swing.JFormattedTextField txtOtrasRetenciones;
    private javax.swing.JFormattedTextField txtPagoNum;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JFormattedTextField txtRetenciones;
    private javax.swing.JTextField txtRif;
    private javax.swing.JFormattedTextField txtSaldoCuenta;
    // End of variables declaration//GEN-END:variables

    private static final Connection cn = Conn.getConnection();
}
