/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package configuracion;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.UserTrack;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.UserModel;
import presupuesto.ConnPpto;
import presupuesto.WinState;
import static presupuesto.XJFrame.COLOBJ;
import static presupuesto.XJFrame.JBCANCEL;
import static presupuesto.XJFrame.JBSELECT;
import static presupuesto.XJFrame.JTABLE;
import presupuesto.XJFrameJTable;

/**
 *
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class ConfigUser extends XJFrameJTable {

    /**
     * Utilizada para hacer mas claro la lectura del código programado, mantiene relacionado el nombre de la columna con su posición
     */
    private final int colNum = 0;
    private final int colUser = 1;
    private final int colPpto = 2;
    private final int colCompr = 3;
    private final int colCau = 4;
    private final int colImp = 5;
    private final int colPag = 6;
    private final int colBanco = 7;
    private final int colCfg = 8;
    private final int colRpt = 9;
    private final int colAct = 10;

    /**
     *
     * @param _parent
     */
    public ConfigUser(final java.awt.Window _parent) {
        super(_parent);
        initComponents();

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

    /**
     * Rev /10/2016
     */
    private void setCompBehavior() {
        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, btnRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, txtReset);
        param.put(JTABLE, tblMaster);
        param.put(COLOBJ, colNum);
        param.put(JBSELECT, btnGuardar);
        param.put(JBCANCEL, btnCancelar);

        ConfigComponents(param);
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
        InitConditions();
        UpdatePanels();
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

    /**
     * Valores y/o condiciones iniciales de la ventana
     */
    @Override
    public void InitConditions() {
        super.InitConditions();
        winState = WinState.NORMAL;
    }

    @Override
    protected void ConfigComponents(final Map<String, Object> _param) {
        super.ConfigComponents(_param);

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

        DefaultTableCellRenderer tcr;

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colNum).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colPpto).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colCompr).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colCau).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colImp).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colPag).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colBanco).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colCfg).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colRpt).setCellRenderer(tcr);

        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tblMaster.getColumnModel().getColumn(colAct).setCellRenderer(tcr);
    }

    @Override
    protected void UpdatePanels() {
        UpdateJTable();
        UpdateEnabledAndFocus();
    }

    /**
     * Actualizar la vista de la ventana. debería llamarse cuando se halla modificado la tabla
     *
     */
    @Override
    public void UpdateJTable() {

        // Registrar la fila seleccionada
        int lastRowSel = tblMaster.getSelectedRow();

        // Para evitar multiples llamadas al metodo valueChanged
        tblMaster.clearSelection();

        final DefaultTableModel model = (DefaultTableModel) tblMaster.getModel();

        // Eliminar los elementos existentes, manteniendo el mismo modelo
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[11];
        try {
            // Generar el comando SQL
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM usuario WHERE active= 'true' ORDER BY user ASC");
            while (rs.next()) {
                UserModel aux = new UserModel(rs);
                datos[colNum] = aux;
                datos[colUser] = aux.getUser();
                datos[colPpto] = (aux.getPpto() != null && aux.getPpto() != 0);
                datos[colCompr] = (aux.getCompr() != null && aux.getCompr() != 0);
                datos[colCau] = (aux.getCau() != null && aux.getCau() != 0);
                datos[colImp] = (aux.getImp() != null && aux.getImp() != 0);
                datos[colPag] = (aux.getPago() != null && aux.getPago() != 0);
                datos[colBanco] = (aux.getBanco() != null && aux.getBanco() != 0);
                datos[colCfg] = (aux.getCfg() != null && aux.getCfg() != 0);
                datos[colRpt] = (aux.getRpt() != null && aux.getRpt() != 0);
                datos[colAct] = (aux.getActive() != null && !aux.getActive().equals("false"));

                model.addRow(datos);
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
            tblMaster.clearSelection();
            tblMaster.setRowSelectionInterval(lastRowSel, lastRowSel);
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
        final boolean isTableFill = tblMaster.getRowCount() > 0;
        txtReset.setEnabled(isTableFill);
        btnGuardar.setEnabled(isTableFill);
        btnCancelar.setEnabled(isTableFill && (tblMaster.getSelectedRow() >= 0));
        btnRetornar.setEnabled(true);

        if (isTableFill) {
            tblMaster.requestFocusInWindow();
            getRootPane().setDefaultButton(btnGuardar);
        } else {
            btnRetornar.requestFocusInWindow();
            getRootPane().setDefaultButton(btnRetornar);
        }
    }

    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent _e) {
    }

    protected void jbCancelarActionPerformed() {
        UpdatePanels();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupTipo = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtReset = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jpCmdState = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jpComandos = new javax.swing.JPanel();
        btnRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();

        setTitle("CONFIGURACIÓN DE USUARIOS");
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

        jtFiltro.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtFiltro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtFiltro.setDoubleBuffered(true);
        jtFiltro.setMaximumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setMinimumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setPreferredSize(new java.awt.Dimension(200, 21));
        jtFiltro.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(jLabel1, gridBagConstraints);

        txtReset.setBackground(java.awt.SystemColor.inactiveCaption);
        txtReset.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        txtReset.setText("RESET");
        txtReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(txtReset, gridBagConstraints);

        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID", "Usuario" }));
        jcbFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbFilter.setMaximumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setMinimumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jcbFilter, gridBagConstraints);

        jpFondo.add(jpFiltro);

        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 440));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 440));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 440));

        tblMaster.setAutoCreateRowSorter(true);
        tblMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblMaster.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N
        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Usuario", "Presupuesto", "Compromiso", "Causado", "Impuesto", "Pago", "Banco", "Configuración", "Reporte", "Activo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true, true, true, true
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
        tblMaster.setFillsViewportHeight(true);
        tblMaster.setSelectionBackground(new java.awt.Color(175, 204, 125));
        tblMaster.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblMaster);
        if (tblMaster.getColumnModel().getColumnCount() > 0) {
            tblMaster.getColumnModel().getColumn(0).setMinWidth(25);
            tblMaster.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblMaster.getColumnModel().getColumn(0).setMaxWidth(75);
            tblMaster.getColumnModel().getColumn(1).setMinWidth(100);
            tblMaster.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblMaster.getColumnModel().getColumn(1).setMaxWidth(300);
        }

        jpFondo.add(jScrollPane1);

        jpEditables.setBorder(javax.swing.BorderFactory.createTitledBorder("Comandos"));
        jpEditables.setMaximumSize(new java.awt.Dimension(1060, 80));
        jpEditables.setMinimumSize(new java.awt.Dimension(1060, 80));
        jpEditables.setOpaque(false);
        jpEditables.setPreferredSize(new java.awt.Dimension(1060, 80));
        jpEditables.setLayout(new java.awt.GridBagLayout());

        jpCmdState.setOpaque(false);
        jpCmdState.setLayout(new java.awt.GridBagLayout());

        btnGuardar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnGuardar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(btnGuardar, gridBagConstraints);

        btnCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnCancelar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jpEditables.add(jpCmdState, gridBagConstraints);

        jpFondo.add(jpEditables);

        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());

        btnRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        btnRetornar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        btnRetornar.setText("RETORNAR");
        btnRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 560, 0, 0);
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (tblMaster.getRowCount() <= 0) {
            return;
        }

        for (int i = 0; i < tblMaster.getRowCount(); i++) {

            final UserModel reg = (UserModel) tblMaster.getValueAt(i, colNum);
            final Long iduser = reg.getIdUser();
            final String user = reg.getUser();

            if (user.equals("ADMIN")) {
                try {
                    final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE usuario set ppto= ?,compr= ?,cau= ?,imp= ?,pago= ?,banco= ?,cfg= ?,rpt= ?,active= ? WHERE id_user= ?");
                    pst.setInt(1, 1);
                    pst.setInt(2, 1);
                    pst.setInt(3, 1);
                    pst.setInt(4, 1);
                    pst.setInt(5, 1);
                    pst.setInt(6, 1);
                    pst.setInt(7, 1);
                    pst.setInt(8, 1);
                    pst.setString(9, "true");
                    pst.setLong(10, iduser);

                    if (pst.executeUpdate() != 1) {
                        JOptionPane.showMessageDialog(null, "Problemas al actualizar el registro");
                    }
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, _ex);
                    return;
                }

            } else {
                try {
                    final Boolean act = (Boolean) tblMaster.getValueAt(i, colAct);

                    if (!act && JOptionPane.showConfirmDialog(this, "¿Seguro de Inactivar usuario: " + user + " ?",
                        "Confirmar acción", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
                        continue;
                    }

                    final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE usuario set ppto= ?,compr= ?,cau= ?,imp= ?,pago= ?,banco= ?,cfg= ?,rpt= ?,active= ? WHERE id_user= ?");
                    pst.setBoolean(1, (Boolean) tblMaster.getValueAt(i, colPpto));
                    pst.setBoolean(2, (Boolean) tblMaster.getValueAt(i, colCompr));
                    pst.setBoolean(3, (Boolean) tblMaster.getValueAt(i, colCau));
                    pst.setBoolean(4, (Boolean) tblMaster.getValueAt(i, colImp));
                    pst.setBoolean(5, (Boolean) tblMaster.getValueAt(i, colPag));
                    pst.setBoolean(6, (Boolean) tblMaster.getValueAt(i, colBanco));
                    pst.setBoolean(7, (Boolean) tblMaster.getValueAt(i, colCfg));
                    pst.setBoolean(8, (Boolean) tblMaster.getValueAt(i, colRpt));
                    pst.setString(9, act ? "true" : "false");
                    pst.setLong(10, iduser);

                    if (pst.executeUpdate() != 1) {
                        JOptionPane.showMessageDialog(null, "Problemas al actualizar el registro");
                    }
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, _ex);
                    return;
                }
            }
        }

        UpdatePanels();
        JOptionPane.showMessageDialog(this, "Operación realizada" + System.getProperty("line.separator"));

    }//GEN-LAST:event_btnGuardarActionPerformed

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
                new ConfigUser(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRetornar;
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbFilter;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JPanel jpCmdState;
    private javax.swing.JPanel jpComandos;
    private javax.swing.JPanel jpEditables;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JPanel jpFondo;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JTable tblMaster;
    private javax.swing.JButton txtReset;
    // End of variables declaration//GEN-END:variables

    private final Connection cn = Conn.getConnection();

}
