/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package compromisos;

import capipsistema.CapipPropiedades;
import capipsistema.Conn;
import capipsistema.Globales;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
import modelos.PptoModel;
import presupuesto.WinState;
import presupuesto.XJFrameSelect;
import utils.Format;

/**
 *
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class PresupeSel extends XJFrameSelect {

    /**
     * Utilizada para hacer mas claro la lectura del codigo programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colCodigo = 0;
    private final int colPartida = 1;
    private final int colMonto = 2;
    private final int colReg = 3;

    /**
     *
     * @param _parent
     * @param _idSelIO
     */
    public PresupeSel(java.awt.Window _parent, int _idSelIO) {
        super(_parent, _idSelIO);
        initComponents();
        setTitle(getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
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
     *
     */
    @Override
    public void UpdateJTable() {

        // Registrar la fila seleccionada
        int lastRowSel = jtable.getSelectedRow();

        // Para evitar multiples llamadas al metodo valueChanged
        jtable.clearSelection();

        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel model = (DefaultTableModel) jtable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[4];
        try {
            // Generar el comando SQL
            final ResultSet rs = Conn.executeQuery("SELECT * FROM presupe WHERE ejefis=" + Globales.getEjeFisYear() + " AND anulado_sn= 'N' ORDER BY codigo ASC");
            while (rs.next()) {
                PptoModel reg = new PptoModel(rs);

                datos[colCodigo] = reg.getCodigo();
                datos[colPartida] = reg.getPartida();
                datos[colMonto] = Format.toDouble(reg.getMonto());
                datos[colReg] = reg;
                model.addRow(datos);
            }
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jpFondo = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jbResetear = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();
        jpComandos = new javax.swing.JPanel();
        jbSeleccionar = new javax.swing.JButton();
        jbRetornar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();

        setTitle("SELECCIONAR LA PARTIDA");
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jbResetear, gridBagConstraints);

        jcbFilter.setBackground(java.awt.SystemColor.inactiveCaption);
        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "Partida" }));
        jcbFilter.setSelectedIndex(1);
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

        jpFondo.add(jpFiltro);

        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 530));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 530));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 530));

        jtable.setAutoCreateRowSorter(true);
        jtable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtable.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Partida", "Monto Bs.", "Reg"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jtable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtable.setDoubleBuffered(true);
        jtable.setFillsViewportHeight(true);
        jtable.setSelectionBackground(new java.awt.Color(175, 204, 125));
        jtable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jtable);
        if (jtable.getColumnModel().getColumnCount() > 0) {
            jtable.getColumnModel().getColumn(0).setMinWidth(200);
            jtable.getColumnModel().getColumn(0).setPreferredWidth(150);
            jtable.getColumnModel().getColumn(0).setMaxWidth(250);
            jtable.getColumnModel().getColumn(2).setMinWidth(200);
            jtable.getColumnModel().getColumn(2).setPreferredWidth(150);
            jtable.getColumnModel().getColumn(2).setMaxWidth(250);
            jtable.getColumnModel().getColumn(3).setMinWidth(0);
            jtable.getColumnModel().getColumn(3).setPreferredWidth(0);
            jtable.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jpFondo.add(jScrollPane1);

        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());

        jbSeleccionar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbSeleccionar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jbSeleccionar.setText("SELECCIONAR");
        jbSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 720, 0, 0);
        jpComandos.add(jbSeleccionar, gridBagConstraints);

        jbRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbRetornar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jbRetornar.setText("RETORNAR");
        jbRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jpComandos.add(jbRetornar, gridBagConstraints);

        jbCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbCancelar.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jbCancelar.setText("CANCELAR");
        jbCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpComandos.add(jbCancelar, gridBagConstraints);

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
                new PresupeSel(null, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
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
}
