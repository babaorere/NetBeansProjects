/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package com.principal.presupuesto;

import com.principal.capipsistema.Globales;
import com.principal.capipsistema.Propiedades;
import com.principal.capipsistema.UserTrack;
import com.principal.compromisos.Compromiso;
import com.principal.connection.ConnCapip;
import com.principal.modelos.PptoModel;
import static com.principal.presupuesto.XJFrame.COLOBJ;
import static com.principal.presupuesto.XJFrame.JTABLE;
import com.principal.utils.Format;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class PptoEli extends XJFrameSelect {

    /**
     * Utilizada para hacer mas claro la lectura del codigo programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colAnulado = 0;

    private final int colCodigo = 1;

    private final int colPartida = 2;

    private final int colMonto = 3;

    private final int colReg = 4;

    private final String tabla;

    /**
     * @param inparent
     * @param intabla
     */
    public PptoEli(java.awt.Window inparent, final String intabla) {
        super(inparent, 0);
        tabla = intabla;
        initComponents();
        setTitle(getTitle() + " - " + Propiedades.CAPIP_CLIENTE_RAZONSOCIAL);
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        // jbReturn, jcbFilter, jtCriterio, jbReset, jtable, colObj, jbSelect, jbCancel
        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, jbRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, jbResetear);
        param.put(JTABLE, jtable);
        param.put(COLOBJ, colReg);
        param.put(JBSELECT, jbSeleccionar);
        param.put(JBCANCEL, jbCancelar);
        ConfigComponents(param);
        InitConditions();
        UpdatePanels();
        UpdateEnabledAndFocus();
        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Rev /10/2016
     */
    private void setCompBehavior() {
        Compromiso.setTblPartidaBehavior(jtable, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                jbSeleccionarActionPerformed(null);
            }
        });
    }

    /**
     * Rev /10/2016
     */
    private void setOwnBehavior() {
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
    }

    /**
     * Para Reubicar la ventana al ser visualizada Rev 25/09/2016
     *
     * @param inb
     */
    @Override
    public void setVisible(boolean inb) {
        // Para mostrar la ventana en el tope de la pantalla
        if (inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }
        super.setVisible(inb);
    }

    /**
     * Valores y/o condiciones iniciales de la ventana
     */
    @Override
    public void InitConditions() {
        super.InitConditions();
        winState = WinState.NORMAL;
        chkAllReg.setSelected(true);
        cmbOrden.setSelectedIndex(0);
    }

    @Override
    protected void ConfigComponents(final Map<String, Object> inparam) {
        super.ConfigComponents(inparam);
        DefaultTableCellRenderer tcr;
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        jtable.getColumnModel().getColumn(colCodigo).setCellRenderer(tcr);
        // Establecer el ordenador de las cabeceras de la jtable
        TableRowSorter<TableModel> rowsorter = new TableRowSorter<>(jtable.getModel());
        jtable.setRowSorter(rowsorter);
        jtable.getColumnModel().getColumn(colMonto).setCellRenderer(Globales.getCurCellRenderer());
    }

    @Override
    protected void UpdatePanels() {
        UpdateJTable();
    }

    /**
     * Actualizar la vista de la ventana. Deberia llamarse cuando se halla modificado la tabla
     */
    @Override
    public void UpdateJTable() {
        final String[] orden = {"id", "codigo", "partida"};
        // Registrar la fila seleccionada
        int lastRowSel = jtable.getSelectedRow();
        // Para evitar multiples llamadas al metodo valueChanged
        jtable.clearSelection();
        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel model = (DefaultTableModel) jtable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        final String anulado_sn = chkAllReg.isSelected() ? "" : " AND anulado_sn= 'N'";
        final Object[] datos = new Object[5];
        try {
            // Generar el comando SQL
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM " + tabla + " WHERE ejefis=" + Globales.getEjeFisYear() + anulado_sn + " ORDER BY " + orden[cmbOrden.getSelectedIndex()] + " ASC");
            while (rs.next()) {
                PptoModel reg = new PptoModel(rs);
                datos[colAnulado] = reg.getAnulado_sn().equals("S");
                datos[colCodigo] = reg.getCodigo();
                datos[colPartida] = reg.getPartida();
                datos[colMonto] = Format.toDouble(reg.getMonto());
                datos[colReg] = reg;
                model.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
        }
        // Reposicionar el registro seleccionado
        int lastRow = jtable.getRowCount() - 1;
        if (lastRow >= 0) {
            if (lastRowSel < 0) {
                lastRowSel = 0;
            } else {
                lastRowSel = Math.min(lastRowSel, lastRow);
            }
            jtable.scrollRectToVisible(jtable.getCellRect(lastRowSel, 0, true));
            jtable.clearSelection();
            jtable.setRowSelectionInterval(lastRowSel, lastRowSel);
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
        boolean isTableFill = jtable.getRowCount() > 0;
        jbResetear.setEnabled(isTableFill);
        jbSeleccionar.setEnabled(isTableFill);
        jbCancelar.setEnabled(isTableFill && (jtable.getSelectedRow() >= 0));
        jbRetornar.setEnabled(true);
        if (isTableFill) {
            jtable.requestFocusInWindow();
            getRootPane().setDefaultButton(jbSeleccionar);
        } else {
            jbRetornar.requestFocusInWindow();
            getRootPane().setDefaultButton(jbRetornar);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        buttonGroup1 = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jpFiltro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbOrden = new javax.swing.JComboBox();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jbResetear = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        chkAllReg = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();
        jpComandos = new javax.swing.JPanel();
        jbSeleccionar = new javax.swing.JButton();
        jbRetornar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        btnApply = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();
        setTitle("ELIMINAR PARTIDA");
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
        // NOI18N
        jLabel3.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel3.setText("Orden");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(jLabel3, gridBagConstraints);
        cmbOrden.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        cmbOrden.setFont(new java.awt.Font("Arial", 2, 16));
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
        // NOI18N
        jtFiltro.setFont(new java.awt.Font("Arial", 3, 16));
        jtFiltro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtFiltro.setDoubleBuffered(true);
        jtFiltro.setMaximumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setMinimumSize(new java.awt.Dimension(200, 21));
        jtFiltro.setPreferredSize(new java.awt.Dimension(200, 21));
        jtFiltro.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Arial", 3, 14));
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jpFiltro.add(jLabel1, gridBagConstraints);
        jbResetear.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jbResetear.setFont(new java.awt.Font("Arial", 2, 16));
        jbResetear.setText("RESET");
        jbResetear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jbResetear, gridBagConstraints);
        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16));
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Anulado", "Código", "Partida" }));
        jcbFilter.setSelectedIndex(2);
        jcbFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbFilter.setMaximumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setMinimumSize(new java.awt.Dimension(150, 21));
        jcbFilter.setPreferredSize(new java.awt.Dimension(150, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jcbFilter, gridBagConstraints);
        // NOI18N
        chkAllReg.setFont(new java.awt.Font("Arial", 3, 14));
        chkAllReg.setSelected(true);
        chkAllReg.setText("Todos");
        chkAllReg.setToolTipText("Marcado muestra todos los Registros, desmarcado muestra solo los registros acivos");
        chkAllReg.setOpaque(false);
        chkAllReg.addItemListener(new java.awt.event.ItemListener() {

            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkAllRegItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpFiltro.add(chkAllReg, gridBagConstraints);
        jpFondo.add(jpFiltro);
        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 530));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 530));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 530));
        jtable.setAutoCreateRowSorter(true);
        jtable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        // NOI18N
        jtable.setFont(new java.awt.Font("Arial", 3, 15));
        jtable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "Anu.", "Código", "Partida", "Monto Bs.", "Reg" }) {

            Class[] types = new Class[] { java.lang.Boolean.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class };

            boolean[] canEdit = new boolean[] { true, false, false, false, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jtable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtable.setDoubleBuffered(true);
        jtable.setFillsViewportHeight(true);
        jtable.setSelectionBackground(new java.awt.Color(175, 204, 125));
        jtable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jtable);
        if (jtable.getColumnModel().getColumnCount() > 0) {
            jtable.getColumnModel().getColumn(0).setMinWidth(25);
            jtable.getColumnModel().getColumn(0).setPreferredWidth(50);
            jtable.getColumnModel().getColumn(0).setMaxWidth(75);
            jtable.getColumnModel().getColumn(1).setMinWidth(200);
            jtable.getColumnModel().getColumn(1).setPreferredWidth(150);
            jtable.getColumnModel().getColumn(1).setMaxWidth(250);
            jtable.getColumnModel().getColumn(3).setMinWidth(200);
            jtable.getColumnModel().getColumn(3).setPreferredWidth(150);
            jtable.getColumnModel().getColumn(3).setMaxWidth(250);
            jtable.getColumnModel().getColumn(4).setMinWidth(0);
            jtable.getColumnModel().getColumn(4).setPreferredWidth(0);
            jtable.getColumnModel().getColumn(4).setMaxWidth(0);
        }
        jpFondo.add(jScrollPane1);
        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());
        jbSeleccionar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jbSeleccionar.setFont(new java.awt.Font("Arial", 2, 16));
        jbSeleccionar.setText("SELECCIONAR");
        jbSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpComandos.add(jbSeleccionar, gridBagConstraints);
        jbRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jbRetornar.setFont(new java.awt.Font("Arial", 2, 16));
        jbRetornar.setText("RETORNAR");
        jbRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpComandos.add(jbRetornar, gridBagConstraints);
        jbCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        jbCancelar.setFont(new java.awt.Font("Arial", 2, 16));
        jbCancelar.setText("CANCELAR");
        jbCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpComandos.add(jbCancelar, gridBagConstraints);
        btnApply.setBackground(java.awt.SystemColor.inactiveCaption);
        // NOI18N
        btnApply.setFont(new java.awt.Font("Arial", 2, 16));
        btnApply.setText("APLICAR");
        btnApply.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnApply.setMaximumSize(new java.awt.Dimension(79, 19));
        btnApply.setMinimumSize(new java.awt.Dimension(79, 19));
        btnApply.setPreferredSize(new java.awt.Dimension(79, 19));
        btnApply.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 600, 0, 0);
        jpComandos.add(btnApply, gridBagConstraints);
        jpFondo.add(jpComandos);
        getContentPane().add(jpFondo);
        jpFondo.setBounds(0, 0, 1100, 650);
        // NOI18N
        jlFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png")));
        jlFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jlFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        getContentPane().add(jlFondo);
        jlFondo.setBounds(0, -10, 1150, 660);
        pack();
    }

    // </editor-fold>//GEN-END:initComponents
    private void chkAllRegItemStateChanged(java.awt.event.ItemEvent evt) {
//GEN-FIRST:event_chkAllRegItemStateChanged
        UpdateJTable();
    }

//GEN-LAST:event_chkAllRegItemStateChanged
    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnApplyActionPerformed
        actApply();
    }

//GEN-LAST:event_btnApplyActionPerformed
    private void cmbOrdenItemStateChanged(java.awt.event.ItemEvent evt) {
//GEN-FIRST:event_cmbOrdenItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            UpdateJTable();
        }
    }

//GEN-LAST:event_cmbOrdenItemStateChanged
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new PptoEli(null, "presupe").setVisible(true);
            }
        });
    }

    @Override
    protected void jbSeleccionarActionPerformed(AWTEvent ine) {
        final int selRow = jtable.getSelectedRow();
        if (selRow < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return;
        }
        jtable.setValueAt(!(Boolean) jtable.getValueAt(selRow, colAnulado), selRow, colAnulado);
    }

    /**
     * Rev 25/11/2016
     *
     * @return
     */
    private boolean actApply() {
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        for (int selRow = 0; selRow < jtable.getRowCount(); selRow++) {
            final String cod = jtable.getValueAt(selRow, colCodigo).toString();
            final String anulado_sn = ((boolean) jtable.getValueAt(selRow, colAnulado)) ? "S" : "N";
            try {
                ConnPpto.conn.prepareStatement("UPDATE " + tabla + " SET anulado_sn= '" + anulado_sn + "' WHERE  codigo='" + cod + "' AND ejefis=" + Globales.getEjeFisYear()).executeUpdate();
            } catch (final Exception inex) {
                JOptionPane.showMessageDialog(null, inex);
                logger.error(inex);
                return false;
            } finally {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        }
        UpdateJTable();
        UpdateEnabledAndFocus();
        return true;
    }

    /**
     * Rev 25/11/2016
     *
     * @return
     */
    private boolean actEliminar() {
        final int selRow = jtable.getSelectedRow();
        if (selRow < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
            return false;
        }
        String cod = jtable.getValueAt(selRow, colCodigo).toString();
        try {
            ConnPpto.conn.prepareStatement("UPDATE " + tabla + " SET anulado_sn= 'S' WHERE  codigo='" + cod + "' AND ejefis=" + Globales.getEjeFisYear()).executeUpdate();
            UpdateJTable();
            UpdateEnabledAndFocus();
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
            logger.error(inex);
            return false;
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;

    private javax.swing.ButtonGroup buttonGroup1;

    private javax.swing.JCheckBox chkAllReg;

    private javax.swing.JComboBox cmbOrden;

    private javax.swing.Box.Filler filler1;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel3;

    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JButton jbCancelar;

    private javax.swing.JButton jbResetear;

    private javax.swing.JButton jbRetornar;

    private javax.swing.JButton jbSeleccionar;

    private javax.swing.JComboBox jcbFilter;

    private javax.swing.JLabel jlFondo;

    private javax.swing.JPanel jpComandos;

    private javax.swing.JPanel jpFiltro;

    private javax.swing.JPanel jpFondo;

    private javax.swing.JTextField jtFiltro;

    private javax.swing.JTable jtable;

    // End of variables declaration//GEN-END:variables
    private static final Logger logger = LogManager.getLogger(PptoEli.class);
}
