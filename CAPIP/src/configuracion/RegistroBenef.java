/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package configuracion;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import static capipsistema.Globales.CAPIP_CONTACTE_MSJ;
import capipsistema.UserPassIn;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import static java.lang.Integer.min;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modelos.BenefModel;
import utils.EstadoReg;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class RegistroBenef extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private final java.awt.Window parent;
    private EstadoReg edoReg;
    private final boolean isRegModificable;

    /**
     * Creates new form configuracion
     *
     * @param _parent
     * @param _isModificable
     */
    public RegistroBenef(java.awt.Window _parent, boolean _isModificable) {
        super();
        initComponents();

        parent = _parent;

        isRegModificable = _isModificable || UserPassIn.getUser().equals("ADMIN");

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Rev /10/2016
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
     * Rev /10/2016
     */
    private void setCompBehavior() {

        tblBenef.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (evt.getClickCount() == 2) {
                    UpdatePanelDetails();
                }

            }

        });

        // Para actualizar automaticamente los campos de detalles, al seleccionar una fila de la tabla
        // al estilo master/detail
        tblBenef.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent _lse) {
                if (_lse.getValueIsAdjusting()) {
                    return;
                }

                UpdatePanelDetails();
            }

        });
    }

    private void UpdatePanelDetails() {
        int fila = tblBenef.getSelectedRow();
        if ((fila >= 0) && tblBenef.isEnabled()) {
            txtRazonS.setText(tblBenef.getValueAt(fila, 0).toString());
            txtRifCI.setText(tblBenef.getValueAt(fila, 1).toString());
            txtDomicilio.setText(tblBenef.getValueAt(fila, 2).toString());
            txtTelfs.setText(tblBenef.getValueAt(fila, 3).toString());
        }
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        edoReg = EstadoReg.VISTA;
        btnModificar.setEnabled(isRegModificable);
        MostrarDatos();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     * Rev 25/09/2016
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

    private void MostrarDatos() {
        UpdateTblBenef();
    }

    /**
     *
     */
    private void UpdateTblBenef() {

        // Registrar la fila seleccionada
        int lastRowSel = tblBenef.getSelectedRow();

        // Para evitar multiples llamadas al metodo valueChanged
        tblBenef.clearSelection();

        final DefaultTableModel model = (DefaultTableModel) tblBenef.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[5];
        try {
            final ResultSet rs = Conn.executeQuery("SELECT * FROM beneficiario ORDER BY razonsocial ASC");
            while (rs.next()) {
                BenefModel reg = new BenefModel(rs);
                datos[0] = reg.getRazonsocial();
                datos[1] = reg.getRif_ci();
                datos[2] = reg.getDomicilio();
                datos[3] = reg.getTelefonos();
                datos[4] = reg;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        // Reposicionar el registro seleccionado
        int lastRow = tblBenef.getRowCount() - 1;
        if (lastRow >= 0) {
            if (lastRowSel < 0) {
                lastRowSel = 0;
            } else {
                lastRowSel = Math.min(lastRowSel, lastRow);
            }

            tblBenef.scrollRectToVisible(tblBenef.getCellRect(lastRowSel, 0, true));
            tblBenef.clearSelection();
            tblBenef.setRowSelectionInterval(lastRowSel, lastRowSel);
        }

    }

    void limpiarBenef() {
        txtRazonS.setText("");
        txtRifCI.setText("");
        txtTelfs.setText("");
        txtDomicilio.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtRazonS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtRifCI = new javax.swing.JTextField();
        txtDomicilio = new javax.swing.JTextField();
        txtTelfs = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnNuevoBenef = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGuardarBeneficiario = new javax.swing.JButton();
        btnCancelarBeneficiario = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBenef = new javax.swing.JTable();
        lblSeparador_1 = new javax.swing.JLabel();
        lblSeparador_2 = new javax.swing.JLabel();
        lblSeparador_3 = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("REGISTRO DE BENEFICIARIOS");
        setMinimumSize(new java.awt.Dimension(720, 750));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("REGISTRO DE BENEFICIARIOS ");
        jLabel6.setMaximumSize(new java.awt.Dimension(600, 40));
        jLabel6.setMinimumSize(new java.awt.Dimension(600, 40));
        jLabel6.setPreferredSize(new java.awt.Dimension(600, 40));
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 600, 40));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel1.setMaximumSize(new java.awt.Dimension(600, 200));
        jPanel1.setMinimumSize(new java.awt.Dimension(600, 200));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 200));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        txtRazonS.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        txtRazonS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtRazonS.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtRazonS.setEnabled(false);
        txtRazonS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonSKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.ipadx = 416;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 40);
        jPanel1.add(txtRazonS, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel4.setText("RIF/CI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("TELÉFONO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 0);
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("RAZÓN SOCIAL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtRifCI.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        txtRifCI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtRifCI.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtRifCI.setEnabled(false);
        txtRifCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRifCIKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 196;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 0, 0);
        jPanel1.add(txtRifCI, gridBagConstraints);

        txtDomicilio.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        txtDomicilio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtDomicilio.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtDomicilio.setEnabled(false);
        txtDomicilio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDomicilioKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 466;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 12, 0, 40);
        jPanel1.add(txtDomicilio, gridBagConstraints);

        txtTelfs.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        txtTelfs.setToolTipText("Formato NNNN-NNNNNNN");
        txtTelfs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtTelfs.setDisabledTextColor(new java.awt.Color(102, 0, 0));
        txtTelfs.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 176;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 40);
        jPanel1.add(txtTelfs, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel15.setText("DIRECCIÓN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 10, 0, 0);
        jPanel1.add(jLabel15, gridBagConstraints);

        btnNuevoBenef.setBackground(new java.awt.Color(153, 153, 0));
        btnNuevoBenef.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnNuevoBenef.setText("Nuevo");
        btnNuevoBenef.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnNuevoBenef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoBenefActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 60, 20, 0);
        jPanel1.add(btnNuevoBenef, gridBagConstraints);

        btnModificar.setBackground(new java.awt.Color(153, 153, 0));
        btnModificar.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 0);
        jPanel1.add(btnModificar, gridBagConstraints);

        btnGuardarBeneficiario.setBackground(new java.awt.Color(153, 153, 0));
        btnGuardarBeneficiario.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnGuardarBeneficiario.setText("Guardar");
        btnGuardarBeneficiario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnGuardarBeneficiario.setEnabled(false);
        btnGuardarBeneficiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarBeneficiarioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 0);
        jPanel1.add(btnGuardarBeneficiario, gridBagConstraints);

        btnCancelarBeneficiario.setBackground(new java.awt.Color(153, 153, 0));
        btnCancelarBeneficiario.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        btnCancelarBeneficiario.setText("Cancelar");
        btnCancelarBeneficiario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnCancelarBeneficiario.setEnabled(false);
        btnCancelarBeneficiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBeneficiarioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 20, 0);
        jPanel1.add(btnCancelarBeneficiario, gridBagConstraints);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 600, 200));

        jScrollPane4.setMaximumSize(new java.awt.Dimension(990, 420));
        jScrollPane4.setMinimumSize(new java.awt.Dimension(990, 420));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(990, 420));

        tblBenef.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        tblBenef.setForeground(new java.awt.Color(0, 51, 153));
        tblBenef.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BENEFICIARIO", "R.I.F. / C.I.", "DOMICILIO", "TELÉFONOS", "RegBenef"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBenef.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblBenef.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tblBenef);
        tblBenef.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tblBenef.getColumnModel().getColumnCount() > 0) {
            tblBenef.getColumnModel().getColumn(0).setMinWidth(150);
            tblBenef.getColumnModel().getColumn(0).setPreferredWidth(300);
            tblBenef.getColumnModel().getColumn(0).setMaxWidth(500);
            tblBenef.getColumnModel().getColumn(1).setMinWidth(50);
            tblBenef.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblBenef.getColumnModel().getColumn(2).setMinWidth(150);
            tblBenef.getColumnModel().getColumn(2).setPreferredWidth(350);
            tblBenef.getColumnModel().getColumn(2).setMaxWidth(500);
            tblBenef.getColumnModel().getColumn(3).setMinWidth(75);
            tblBenef.getColumnModel().getColumn(3).setPreferredWidth(250);
            tblBenef.getColumnModel().getColumn(3).setMaxWidth(300);
            tblBenef.getColumnModel().getColumn(4).setMinWidth(0);
            tblBenef.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblBenef.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 990, 420));
        getContentPane().add(lblSeparador_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 600, 20));
        getContentPane().add(lblSeparador_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 600, 20));
        getContentPane().add(lblSeparador_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 720, 600, 20));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 780));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Rev 21/11/2016
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

    private void btnGuardarBeneficiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarBeneficiarioActionPerformed

        String sAux = txtRazonS.getText().trim().toUpperCase();

        if (sAux.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Razón Social inválida");
            java.awt.EventQueue.invokeLater(txtRazonS::requestFocusInWindow);
            return;
        }

        final String razonSocial = sAux.substring(0, min(sAux.length(), 128));

        // verificar si es un nombre especial, comprobando el primer caracter del nombre
        final boolean isSpecial = sAux.substring(0, 1).equals("@");

        // Verificar si es un nombre especial, no se permite su edicion
        if (isSpecial && (edoReg == EstadoReg.EDICION)) {
            JOptionPane.showMessageDialog(this, "Error, no es permitido la edición de registros especiales" + System.getProperty("line.separator") + CAPIP_CONTACTE_MSJ);
            return;
        }

        // tomar el rif/ci tal cual, para su procesamiento, y limpieza
        final String aux_rif_ci = txtRifCI.getText().trim().toUpperCase();

        // el campo rif/ci no puede estar vacio
        if (aux_rif_ci.isEmpty()) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido");
            java.awt.EventQueue.invokeLater(txtRifCI::requestFocusInWindow);
            return;
        }

        // tomar el primer caracter del rif/ci, para verificar que tipo es
        final char priC = aux_rif_ci.charAt(0);

        // verificar que el primer caracter sea válido
        if ((priC != 'J') && (priC != 'G') && (priC != 'V') && (priC != 'E')) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Debe comenzar con J/G/V/E");
            java.awt.EventQueue.invokeLater(txtRifCI::requestFocusInWindow);
            return;
        }

        // verificar la longitud del rif/ci
        if ((aux_rif_ci.length() < 7) || (aux_rif_ci.length() > 10)) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Longitud fuera de rango");
            java.awt.EventQueue.invokeLater(txtRifCI::requestFocusInWindow);
            return;
        }

        // tomar la seccion correspondiente al numero del rif/ci
        final String sNum = aux_rif_ci.substring(1, aux_rif_ci.length());

        if (((priC == 'J') || (priC == 'G')) && sNum.length() != 9) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Cantidad de caracteres debe ser 9");
            java.awt.EventQueue.invokeLater(txtRifCI::requestFocusInWindow);
            return;
        }

        // validad que la parte correspondiente al numero, es un numero
        final long iNum;
        try {
            iNum = Long.parseLong(sNum);
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Error en el formato del Número ");
            java.awt.EventQueue.invokeLater(txtRifCI::requestFocusInWindow);
            return;
        }

        if (iNum < 0L) {
            JOptionPane.showMessageDialog(this, "RIF/CI inválido. Error en el formato del Número ");
            java.awt.EventQueue.invokeLater(txtRifCI::requestFocusInWindow);
            return;
        }

        final String saux = txtTelfs.getText().trim();

        if (saux.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Teléfonos inválido");
            java.awt.EventQueue.invokeLater(txtTelfs::requestFocusInWindow);
            return;
        }

        // Separar y formatear el telefono
        final String telfs = FormatPhone(saux);

        if (telfs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Teléfonos inválido");
            java.awt.EventQueue.invokeLater(txtTelfs::requestFocusInWindow);
            return;
        }

        sAux = txtDomicilio.getText().trim().toUpperCase();

        if (sAux.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Domicilio inválido");
            java.awt.EventQueue.invokeLater(txtDomicilio::requestFocusInWindow);
            return;
        }

        final String domicilio = sAux.substring(0, min(sAux.length(), 256));

        final String rif_ci = priC + String.valueOf(iNum);

        if (edoReg == EstadoReg.NUEVO) {
            // Verificar si existe un beneficiario con la misma razonsocial
            try {
                final PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT * FROM beneficiario where razonsocial= ?");
                pst.setString(1, razonSocial);
                final ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    BenefModel reg = new BenefModel(rs);
                    JOptionPane.showMessageDialog(this, "Error, ya existe un registro con la misma Razon Social"
                        + System.getProperty("line.separator") + "El registro esta " + (reg.getActivo().equals("S") ? "ACTIVO" : "ANULADO"));
                    java.awt.EventQueue.invokeLater(txtRazonS::requestFocusInWindow);
                    return;
                }
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(this, "Error al tratar de verificar la Razon Social" + System.getProperty("line.separator") + _ex);
                return;
            }

            // Verificar si existe un beneficiario con el mismo rif_cii
            try {
                final PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT * FROM beneficiario where rif_ci= ?");
                pst.setString(1, rif_ci);
                final ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    BenefModel reg = new BenefModel(rs);
                    JOptionPane.showMessageDialog(this, "Error, ya existe un registro con el mismo RIF/CI"
                        + System.getProperty("line.separator") + "El registro esta " + (reg.getActivo().equals("S") ? "ACTIVO" : "ANULADO"));
                    java.awt.EventQueue.invokeLater(txtRifCI::requestFocusInWindow);
                    return;
                }
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(this, "Error al tratar de verificar la Razon Social" + System.getProperty("line.separator") + _ex);
                return;
            }

            try {
                final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO beneficiario"
                    + "(id_user, id_session, date_session, razonsocial, rif_ci, " // 1, 2, 3, 4, 5
                    + "domicilio, telefonos, activo) " // 6, 7, 8
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setLong(1, UserPassIn.getIdUser());
                pst.setLong(2, UserPassIn.getIdSession());
                pst.setTimestamp(3, UserPassIn.getDateSession());
                pst.setString(4, razonSocial);
                pst.setString(5, rif_ci);
                pst.setString(6, domicilio);
                pst.setString(7, telfs);
                pst.setString(8, "S");
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error al guardar el registro");
                } else {
                    limpiarBenef();
                }

            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(null, _ex);
            }
        } else if (edoReg == EstadoReg.EDICION) {

            final int selRow = tblBenef.getSelectedRow();
            if (selRow < 0) {
                return;
            }

            BenefModel regBenef = (BenefModel) tblBenef.getValueAt(selRow, 4);

            // Verificar si existe un beneficiario con la misma razonsocial
            try {
                final PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT * FROM beneficiario where razonsocial= ? AND id_beneficiario != ?");
                pst.setString(1, razonSocial);
                pst.setLong(2, regBenef.getId_beneficiario());
                final ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    BenefModel reg = new BenefModel(rs);
                    JOptionPane.showMessageDialog(this, "Error, ya existe un registro con la misma Razon Social"
                        + System.getProperty("line.separator") + "El registro esta " + (reg.getActivo().equals("S") ? "ACTIVO" : "ANULADO"));
                    java.awt.EventQueue.invokeLater(txtRazonS::requestFocusInWindow);
                    return;
                }
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(this, "Error al tratar de verificar la Razon Social" + System.getProperty("line.separator") + _ex);
                return;
            }

            // Verificar si existe un beneficiario con el mismo rif_cii
            try {
                final PreparedStatement pst = Conn.getConnection().prepareStatement("SELECT * FROM beneficiario where rif_ci= ? AND id_beneficiario != ?");
                pst.setString(1, rif_ci);
                pst.setLong(2, regBenef.getId_beneficiario());
                final ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    BenefModel reg = new BenefModel(rs);
                    JOptionPane.showMessageDialog(this, "Error, ya existe un registro con el mismo RIF/CI"
                        + System.getProperty("line.separator") + "El registro esta " + (reg.getActivo().equals("S") ? "ACTIVO" : "ANULADO"));
                    java.awt.EventQueue.invokeLater(txtRifCI::requestFocusInWindow);
                    return;
                }
            } catch (final Exception _ex) {
                JOptionPane.showMessageDialog(this, "Error al tratar de verificar la Razon Social" + System.getProperty("line.separator") + _ex);
                return;
            }

            try {
                Conn.BeginTransaction();

                PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE beneficiario "
                    + "SET id_user= ?, id_session= ?, date_session= ?, " // 1, 2, 3,
                    + "razonsocial= ?, rif_ci= ?, " // 4, 5,
                    + "domicilio= ?, telefonos= ?, activo= ? " // 6, 7, 8
                    + "WHERE id_beneficiario= ?"); // 9
                pst.setLong(1, UserPassIn.getIdUser());
                pst.setLong(2, UserPassIn.getIdSession());
                pst.setTimestamp(3, UserPassIn.getDateSession());
                pst.setString(4, razonSocial);
                pst.setString(5, rif_ci);
                pst.setString(6, domicilio);
                pst.setString(7, telfs);
                pst.setString(8, "S");
                pst.setLong(9, regBenef.getId_beneficiario());
                if (pst.executeUpdate() != 1) {
                    throw new Exception("Error al actualizar el registro");
                }
                pst.close();

                // ava_efe_rpt_summary
                // razonsocial, rif
                pst = Conn.getConnection().prepareStatement("UPDATE ava_efe_rpt_summary "
                    + "SET razonsocial= ?, rif= ? " // 1, 2
                    + "WHERE rif= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // avance_efectivo
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE avance_efectivo "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // cau_rpt_summary
                // razonsocial, rif
                pst = Conn.getConnection().prepareStatement("UPDATE cau_rpt_summary "
                    + "SET razonsocial= ?, rif= ? " // 1, 2
                    + "WHERE rif= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();
                //

                // causado
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE causado "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // compr_compra
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE compr_compra "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // compr_otros
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE compr_otros "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // compr_rpt_summary
                // razonsocial, rif
                pst = Conn.getConnection().prepareStatement("UPDATE compr_rpt_summary "
                    + "SET razonsocial= ?, rif= ? " // 1, 2
                    + "WHERE rif= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // compr_servicio
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE compr_servicio "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();
                //
                // islr_retencion
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE islr_retencion "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // islr_retencion_det
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE islr_retencion_det "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // iva_retencion
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE iva_retencion "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // iva_retencion_det
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE iva_retencion_det "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                // 
                // orden_pago
                // benef_razonsocial, benef_rif_ci
                pst = Conn.getConnection().prepareStatement("UPDATE orden_pago "
                    + "SET benef_razonsocial= ?, benef_rif_ci= ? " // 1, 2
                    + "WHERE benef_rif_ci= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();

                //
                // ordenpago_rpt_summary
                // razonsocial, rif
                pst = Conn.getConnection().prepareStatement("UPDATE ordenpago_rpt_summary "
                    + "SET razonsocial= ?, rif= ? " // 1, 2
                    + "WHERE rif= ?"); // 3
                pst.setString(1, razonSocial);
                pst.setString(2, rif_ci);
                pst.setString(3, rif_ci);
                pst.executeUpdate();
                pst.close();
                //

                Conn.EndTransaction();

                JOptionPane.showMessageDialog(this, "Actualización realizada");

            } catch (final Exception _ex) {
                try {
                    Conn.RollBack();
                } catch (final Exception _ex2) {
                    JOptionPane.showMessageDialog(null, "Error al tratar de cerrar la operación");
                }

                JOptionPane.showMessageDialog(null, _ex);
            }
        }

        UpdateTblBenef();

        java.awt.EventQueue.invokeLater(txtRazonS::requestFocusInWindow);
    }//GEN-LAST:event_btnGuardarBeneficiarioActionPerformed

    private void btnNuevoBenefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoBenefActionPerformed
        edoReg = EstadoReg.NUEVO;

        limpiarBenef();
        txtRazonS.setEnabled(true);
        txtRifCI.setEnabled(true);
        txtTelfs.setEnabled(true);
        txtDomicilio.setEnabled(true);
        btnNuevoBenef.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardarBeneficiario.setEnabled(true);
        btnCancelarBeneficiario.setEnabled(true);

        tblBenef.setEnabled(false);

        java.awt.EventQueue.invokeLater(txtRazonS::requestFocusInWindow);
    }//GEN-LAST:event_btnNuevoBenefActionPerformed

    private void btnCancelarBeneficiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarBeneficiarioActionPerformed

        edoReg = EstadoReg.VISTA;

        limpiarBenef();

        if (tblBenef.getRowCount() > 0) {
            tblBenef.scrollRectToVisible(tblBenef.getCellRect(0, 0, true));
            tblBenef.clearSelection();
            tblBenef.setRowSelectionInterval(0, 0);
        }

        txtRazonS.setEnabled(false);
        txtRifCI.setEnabled(false);
        txtTelfs.setEnabled(false);
        txtDomicilio.setEnabled(false);
        btnNuevoBenef.setEnabled(true);
        btnModificar.setEnabled(isRegModificable);
        btnGuardarBeneficiario.setEnabled(false);
        btnCancelarBeneficiario.setEnabled(false);

        tblBenef.setEnabled(true);
    }//GEN-LAST:event_btnCancelarBeneficiarioActionPerformed

    private void txtRazonSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonSKeyTyped
        if (txtRazonS.getText().length() >= 128) {
            evt.consume();
        }
    }//GEN-LAST:event_txtRazonSKeyTyped

    private void txtDomicilioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDomicilioKeyTyped
        if (txtDomicilio.getText().length() >= 256) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDomicilioKeyTyped

    private void txtRifCIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRifCIKeyTyped
        if (txtRifCI.getText().length() >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtRifCIKeyTyped

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        actModificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void actModificar() {

        final int selRow = tblBenef.getSelectedRow();
        if ((selRow < 0) || !isRegModificable) {
            return;
        }

        // Verificar si se trata de un nombre especial, en cuyo caso no se permite su edicion
        if (txtRazonS.getText().trim().toUpperCase().substring(0, 1).equals("@")) {
            JOptionPane.showMessageDialog(this, "Error, no es permitido la edición de registros especiales" + System.getProperty("line.separator") + CAPIP_CONTACTE_MSJ);
            return;
        }

        edoReg = EstadoReg.EDICION;

        txtRazonS.setEnabled(true);
        txtRifCI.setEnabled(true);
        txtTelfs.setEnabled(true);
        txtDomicilio.setEnabled(true);

        btnNuevoBenef.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardarBeneficiario.setEnabled(true);
        btnCancelarBeneficiario.setEnabled(true);

        tblBenef.setEnabled(false);

        java.awt.EventQueue.invokeLater(txtRazonS::requestFocusInWindow);
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
                new RegistroBenef(null, true).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarBeneficiario;
    private javax.swing.JButton btnGuardarBeneficiario;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevoBenef;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblSeparador_1;
    private javax.swing.JLabel lblSeparador_2;
    private javax.swing.JLabel lblSeparador_3;
    private javax.swing.JTable tblBenef;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtRazonS;
    private javax.swing.JTextField txtRifCI;
    private javax.swing.JTextField txtTelfs;
    // End of variables declaration//GEN-END:variables

    /**
     * Formatea un string en su posible representación telefónica
     *
     */
    private String FormatPhone(String _saux) {

        StringBuilder resp = new StringBuilder();

        final String list[] = _saux.split(" ");

        if (list.length <= 0) {
            return resp.toString();
        }

        for (String phone : list) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < phone.length(); j++) {
                final String temp = String.valueOf(phone.charAt(j));
                if ("0123456789".contains(temp)) {
                    sb.append(temp);
                }
            }

            //         0412 716 63 88
            // Formato NNNN NNN NN NN, 11 digitos
            if (sb.length() != 11) {
                continue;
            }

            resp.append(sb.charAt(0)).append(sb.charAt(1)).append(sb.charAt(2)).append(sb.charAt(3)).append("-")
                .append(sb.charAt(4)).append(sb.charAt(5)).append(sb.charAt(6)).append(".")
                .append(sb.charAt(7)).append(sb.charAt(8)).append(".")
                .append(sb.charAt(9)).append(sb.charAt(10));
        }

        return resp.toString();
    }
}
