/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import capipsistema.CapipPropiedades;
import static capipsistema.Globales.TBL_PPTO_INGRESO;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelos.PptoModel;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class PartidaIngresoSelect extends XJFrameSelect {

    /**
     * Utilizada para hacer mas claro la lectura del codigo programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colCodigo = 0;
    private final int colPartida = 1;
    private final int colMonto = 2;

    /**
     *
     * @param _parent
     * @param _idSelIO
     */
    public PartidaIngresoSelect(JFrame _parent, int _idSelIO) {

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
        param.put(COLOBJ, colCodigo);
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

        // Establecer el ordenador de las cabeceras de la jtable
        TableRowSorter<TableModel> rowsorter = new TableRowSorter<>(jtable.getModel());
        jtable.setRowSorter(rowsorter);

        // Establecer el metodo de ordenacion para las columnas
        rowsorter.setComparator(colMonto, PptoGlobales.getCurComparator());

        // Configurar el campo de Monto, para que acepte y maneje BigDecimal        
        jftMonto.setFormatterFactory(PptoGlobales.getCurFormatterFactory());

        // Estable el rendere para las columnas
        jtable.getColumnModel().getColumn(colMonto).setCellRenderer(PptoGlobales.getCurCellRenderer());
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

        // Para evitar multiples llamadas al metodo valueChanged
        jtable.clearSelection();

        // Eliminar los elementos existentes, manteniendo el mismo modelo
        final DefaultTableModel model = (DefaultTableModel) jtable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final Object[] datos = new Object[3];
        try {
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + TBL_PPTO_INGRESO + " WHERE ejefis=" + capipsistema.Globales.getEjeFisYear());
            while (rs.next()) {
                PptoModel aux = new PptoModel(rs);
                datos[colCodigo] = aux;
                datos[colPartida] = aux.getPartida();
                datos[colMonto] = aux.getMonto();
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

        // Calcular el total, realizando la sumatoria de la columna monto de la jtable
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < jtable.getRowCount(); i++) {
            total = total.add((BigDecimal) jtable.getValueAt(i, colMonto));
        }

        // Actualizar el campo total
        String stotal;
        try {
            stotal = PptoGlobales.curFormat.valueToString(total);
        } catch (final Exception _ex) {
            stotal = "error";
        }
        jtTotalPpto.setText(stotal);
    }

    @Override
    protected void UpdatePanelDetails() {
        if (jtable.getRowCount() > 0) {
            int selectedRow = jtable.getSelectedRow();
            if (selectedRow >= 0) {
                jftCodigo.setText(jtable.getValueAt(selectedRow, colCodigo).toString());
                jtPartida.setText(jtable.getValueAt(selectedRow, colPartida).toString());
                try {
                    jftMonto.setText(capipsistema.Globales.curFormat.valueToString(((Number) jtable.getValueAt(selectedRow, colMonto)).doubleValue()));
                } catch (final Exception _ex) {
                    JOptionPane.showMessageDialog(null, "Error en Monto: " + System.getProperty("line.separator") + _ex);
                    jftMonto.setText("0,00");
                }
            } else {
                ClearDetails();
            }
        } else {
            ClearDetails();
        }
    }

    @Override
    protected void ClearDetails() {
        jftCodigo.setText(PptoGlobales.strCodEgresoZero);
        jtPartida.setText("");
        jftMonto.setText("0,00");
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
        jpFiltro = new javax.swing.JPanel();
        jtFiltro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jbResetear = new javax.swing.JButton();
        jcbFilter = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();
        jpEditables = new javax.swing.JPanel();
        jlCodigo = new javax.swing.JLabel();
        jlPartida = new javax.swing.JLabel();
        jtPartida = new javax.swing.JTextField();
        jlMonto = new javax.swing.JLabel();
        jpCmdState = new javax.swing.JPanel();
        jbSeleccionar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        jtTotalPpto = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCurFormatter());
        jftCodigo = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCodIngresoMaskFormatter());
        jftMonto = new javax.swing.JFormattedTextField(presupuesto.PptoGlobales.getCurFormatter());
        jpComandos = new javax.swing.JPanel();
        jbRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();

        setTitle("SELECCIONAR PARTIDA DE INGRESO");
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        jpFondo.setMaximumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setMinimumSize(new java.awt.Dimension(1100, 650));
        jpFondo.setOpaque(false);
        jpFondo.setPreferredSize(new java.awt.Dimension(1100, 650));
        jpFondo.setLayout(new javax.swing.BoxLayout(jpFondo, javax.swing.BoxLayout.PAGE_AXIS));

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jtFiltro, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setText("FILTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpFiltro.add(jLabel1, gridBagConstraints);

        jbResetear.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jbResetear.setText("RESET");
        jbResetear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpFiltro.add(jbResetear, gridBagConstraints);

        jcbFilter.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
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

        jScrollPane1.setFocusable(false);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 430));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 430));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 430));

        jtable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtable.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtable.setForeground(new java.awt.Color(0, 51, 153));
        jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Partida", "Monto Bs."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class
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
        jtable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtable.setDoubleBuffered(true);
        jtable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jtable);
        if (jtable.getColumnModel().getColumnCount() > 0) {
            jtable.getColumnModel().getColumn(0).setPreferredWidth(200);
            jtable.getColumnModel().getColumn(0).setMaxWidth(200);
            jtable.getColumnModel().getColumn(1).setPreferredWidth(500);
            jtable.getColumnModel().getColumn(2).setPreferredWidth(150);
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
        jlPartida.setLabelFor(jtPartida);
        jlPartida.setText("Partida ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpEditables.add(jlPartida, gridBagConstraints);

        jtPartida.setEditable(false);
        jtPartida.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtPartida.setForeground(new java.awt.Color(0, 51, 102));
        jtPartida.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtPartida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtPartida.setMinimumSize(new java.awt.Dimension(250, 21));
        jtPartida.setPreferredSize(new java.awt.Dimension(300, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        jpEditables.add(jtPartida, gridBagConstraints);

        jlMonto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlMonto.setText("Monto Bs. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        jpEditables.add(jlMonto, gridBagConstraints);

        jpCmdState.setOpaque(false);
        jpCmdState.setLayout(new java.awt.GridBagLayout());

        jbSeleccionar.setBackground(new java.awt.Color(153, 153, 0));
        jbSeleccionar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jbSeleccionar.setText("SELECCIONAR");
        jbSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(jbSeleccionar, gridBagConstraints);

        jbCancelar.setBackground(new java.awt.Color(153, 153, 0));
        jbCancelar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jbCancelar.setText("CANCELAR");
        jbCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(jbCancelar, gridBagConstraints);

        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblmonto1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblmonto1.setText("Total Presupuesto Bs. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 230, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);

        jtTotalPpto.setEditable(false);
        jtTotalPpto.setBackground(new java.awt.Color(204, 102, 0));
        jtTotalPpto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtTotalPpto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jtTotalPpto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtTotalPpto.setDoubleBuffered(true);
        jtTotalPpto.setFocusable(false);
        jtTotalPpto.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jtTotalPpto.setMinimumSize(new java.awt.Dimension(200, 21));
        jtTotalPpto.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(jtTotalPpto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jpCmdState, gridBagConstraints);

        jftCodigo.setEditable(false);
        jftCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jftCodigo.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jftCodigo.setMinimumSize(new java.awt.Dimension(200, 21));
        jftCodigo.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jpEditables.add(jftCodigo, gridBagConstraints);

        jftMonto.setEditable(false);
        jftMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jftMonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftMonto.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jftMonto.setMinimumSize(new java.awt.Dimension(200, 21));
        jftMonto.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        jpEditables.add(jftMonto, gridBagConstraints);

        jpFondo.add(jpEditables);

        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());

        jbRetornar.setBackground(new java.awt.Color(153, 153, 0));
        jbRetornar.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jbRetornar.setText("RETORNAR");
        jbRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 700, 0, 0);
        jpComandos.add(jbRetornar, gridBagConstraints);

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
                new PartidaIngresoSelect(null, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbResetear;
    private javax.swing.JButton jbRetornar;
    private javax.swing.JButton jbSeleccionar;
    private javax.swing.JComboBox jcbFilter;
    private javax.swing.JFormattedTextField jftCodigo;
    private javax.swing.JFormattedTextField jftMonto;
    private javax.swing.JLabel jlCodigo;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JLabel jlMonto;
    private javax.swing.JLabel jlPartida;
    private javax.swing.JPanel jpCmdState;
    private javax.swing.JPanel jpComandos;
    private javax.swing.JPanel jpEditables;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JPanel jpFondo;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JTextField jtPartida;
    private javax.swing.JFormattedTextField jtTotalPpto;
    private javax.swing.JTable jtable;
    private javax.swing.JLabel lblmonto1;
    // End of variables declaration//GEN-END:variables
}
