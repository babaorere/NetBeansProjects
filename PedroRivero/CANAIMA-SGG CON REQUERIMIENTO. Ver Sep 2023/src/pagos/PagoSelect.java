/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package pagos;

import capipsistema.CapipPropiedades;
import capipsistema.Globales;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import presupuesto.ConnPpto;
import presupuesto.TblCreditoAdicional;
import presupuesto.WinState;
import presupuesto.XJFrameSelect;

/**
 *
 * @author Capip Sistemas C.A.
 */
@SuppressWarnings("serial")
public final class PagoSelect extends XJFrameSelect {

    /**
     * Utilizada para hacer mas claro la lectura del codigo programado, mantiene relacionado el nombre de la columna con su posicion
     */
    private final int colSoporte = 0;
    private final int colDesc = 1;
    private final int colMonto = 2;
    private final int colFecha = 3;

    /**
     *
     * @param inparent
     * @param inidSelIO
     */
    public PagoSelect(JFrame inparent, int inidSelIO) {
        super( inparent, inidSelIO);
        initComponents();
        setTitle(getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        // jbReturn, jcbFilter, jtCriterio, jbReset, jtable, colObj, jbSelect, jbCancel
        final Map<String, Object> param = new HashMap<>(101);
        param.put(JBRETURN, jbRetornar);
        param.put(JCBFILTER, jcbFilter);
        param.put(JTFILTRO, jtFiltro);
        param.put(JBRESET, jbResetear);
        param.put(JTABLE, jtable);
        param.put(COLOBJ, colSoporte);
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
     * @param inb
     */
    @Override
    public void setVisible(boolean inb) {
        // Para mostrar la ventana en el tope de la pantalla
        if ( inb) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }

        super.setVisible( inb);
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
    protected void ConfigComponents(final Map<String, Object> inparam) {
        super.ConfigComponents( inparam);

        // Establecer el ordenador de las cabeceras de la jtable
        TableRowSorter<TableModel> rowsorter = new TableRowSorter<>(jtable.getModel());
        jtable.setRowSorter(rowsorter);

        // Establecer el metodo de comparacion para laa columna 
        rowsorter.setComparator(colMonto, Globales.getCurComparator());
        rowsorter.setComparator(colFecha, Globales.getDateComparator());

        // Configurar el formato de la fecha
        jftFecha.setFormatterFactory(Globales.getDateFormatterFactory());

        // Configurar el campo de Monto del Credito, para que acepte y maneje BigDecimal        
        jftMontoCred.setFormatterFactory(Globales.getCurFormatterFactory());

        jtable.getColumnModel().getColumn(colMonto).setCellRenderer(Globales.getCurCellRenderer());
        jtable.getColumnModel().getColumn(colFecha).setCellRenderer(Globales.getDateCellRenderer());
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

        int cont = 0;
        final Object[] datos = new Object[4];
        try {
            // Generar el comando SQL
            final ResultSet rs = ConnPpto.executeQuery("SELECT * FROM " + Globales.TBL_CREDITO_ADICIONAL + " ORDER BY fecha ASC");
            while (rs.next()) {
                cont++;
                int id = rs.getInt("id");
                String soporte = rs.getString("soporte");
                String desc = rs.getString("descripcion");
                BigDecimal monto = rs.getBigDecimal("monto");
                java.sql.Date fecha = rs.getDate("fecha");
                java.sql.Date fechaGen = rs.getDate("fchsession");

                TblCreditoAdicional aux = new TblCreditoAdicional(id, soporte, desc, monto, fecha, fechaGen);
                datos[colSoporte] = aux;
                datos[colDesc] = aux.getDescripcion();
                datos[colMonto] = aux.getMonto();
                datos[colFecha] = aux.getFecha();
                model.addRow(datos);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
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

        // Actualizar el campo cantidad de beneficiarios
        jtCantBeneficiarios.setText("" + cont);
    }

    @Override
    protected void UpdatePanelDetails() {
        if (jtable.getRowCount() > 0) {
            int selectedRow = jtable.getSelectedRow();
            if (selectedRow >= 0) {
                jtSoporte.setText(jtable.getValueAt(selectedRow, colSoporte).toString());
                jtDesc.setText(jtable.getValueAt(selectedRow, colDesc).toString());
                try {
                    jftMontoCred.setText(Globales.curFormat.valueToString(jtable.getValueAt(selectedRow, colMonto)));
                } catch (ParseException ex) {
                    jftMontoCred.setText("Error");
                }
                jftFecha.setText(Globales.dateFormat.format(jtable.getValueAt(selectedRow, colFecha)));
            } else {
                ClearDetails();
            }
        } else {
            ClearDetails();
        }
    }

    @Override
    protected void ClearDetails() {
        jtSoporte.setText("");
        jtDesc.setText("");
        jftMontoCred.setText("0,00");
        jftFecha.setText("");
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
        jpEditables = new javax.swing.JPanel();
        jpCmdState = new javax.swing.JPanel();
        jbSeleccionar = new javax.swing.JButton();
        lblmonto1 = new javax.swing.JLabel();
        jtCantBeneficiarios = new javax.swing.JFormattedTextField(Integer.valueOf(0));
        jbCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jtSoporte = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtDesc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jftMontoCred = new javax.swing.JFormattedTextField(new BigDecimal("0.00"));
        jLabel9 = new javax.swing.JLabel();
        jftFecha = new javax.swing.JFormattedTextField();
        jpComandos = new javax.swing.JPanel();
        jbRetornar = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();

        setTitle("SELECCIONAR CREDITO ADICIONAL");
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

        jcbFilter.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jcbFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Soporte", "Descripción", "Monto Bs.", "Fecha" }));
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
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1060, 390));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1060, 390));

        jtable.setAutoCreateRowSorter(true);
        jtable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtable.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtable.setForeground(new java.awt.Color(0, 51, 153));
        jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Soporte", "Descripción", "Monto Bs.", "Fecha"
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
            jtable.getColumnModel().getColumn(0).setMinWidth(150);
            jtable.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtable.getColumnModel().getColumn(0).setMaxWidth(200);
            jtable.getColumnModel().getColumn(2).setMinWidth(200);
            jtable.getColumnModel().getColumn(2).setPreferredWidth(150);
            jtable.getColumnModel().getColumn(2).setMaxWidth(250);
            jtable.getColumnModel().getColumn(3).setMinWidth(100);
            jtable.getColumnModel().getColumn(3).setPreferredWidth(150);
            jtable.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        jpFondo.add(jScrollPane1);

        jpEditables.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle del Credito"));
        jpEditables.setMaximumSize(new java.awt.Dimension(1060, 135));
        jpEditables.setMinimumSize(new java.awt.Dimension(1060, 135));
        jpEditables.setOpaque(false);
        jpEditables.setPreferredSize(new java.awt.Dimension(1060, 135));
        jpEditables.setLayout(new java.awt.GridBagLayout());

        jpCmdState.setOpaque(false);
        jpCmdState.setLayout(new java.awt.GridBagLayout());

        jbSeleccionar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbSeleccionar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbSeleccionar.setText("SELECCIONAR");
        jbSeleccionar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSeleccionarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        jpCmdState.add(jbSeleccionar, gridBagConstraints);

        lblmonto1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblmonto1.setText("Cantidad de Créditos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 311, 0, 0);
        jpCmdState.add(lblmonto1, gridBagConstraints);

        jtCantBeneficiarios.setEditable(false);
        jtCantBeneficiarios.setBackground(new java.awt.Color(175, 204, 125));
        jtCantBeneficiarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtCantBeneficiarios.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jtCantBeneficiarios.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtCantBeneficiarios.setDoubleBuffered(true);
        jtCantBeneficiarios.setFocusable(false);
        jtCantBeneficiarios.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtCantBeneficiarios.setMinimumSize(new java.awt.Dimension(200, 21));
        jtCantBeneficiarios.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        jpCmdState.add(jtCantBeneficiarios, gridBagConstraints);

        jbCancelar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbCancelar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbCancelar.setText("CANCELAR");
        jbCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpCmdState.add(jbCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jpCmdState, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel6.setText("Soporte");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpEditables.add(jLabel6, gridBagConstraints);

        jtSoporte.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtSoporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtSoporte.setEnabled(false);
        jtSoporte.setMinimumSize(new java.awt.Dimension(200, 21));
        jtSoporte.setPreferredSize(new java.awt.Dimension(200, 21));
        jtSoporte.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(jtSoporte, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel7.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel7, gridBagConstraints);

        jtDesc.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jtDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtDesc.setEnabled(false);
        jtDesc.setMaximumSize(new java.awt.Dimension(450, 21));
        jtDesc.setMinimumSize(new java.awt.Dimension(450, 21));
        jtDesc.setPreferredSize(new java.awt.Dimension(450, 21));
        jtDesc.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(jtDesc, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel8.setText("Monto Bs.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel8, gridBagConstraints);

        jftMontoCred.setBackground(new java.awt.Color(175, 204, 125));
        jftMontoCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jftMontoCred.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jftMontoCred.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftMontoCred.setDoubleBuffered(true);
        jftMontoCred.setEnabled(false);
        jftMontoCred.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jftMontoCred.setMinimumSize(new java.awt.Dimension(200, 21));
        jftMontoCred.setPreferredSize(new java.awt.Dimension(200, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(jftMontoCred, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel9.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jpEditables.add(jLabel9, gridBagConstraints);

        jftFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jftFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jftFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jftFecha.setEnabled(false);
        jftFecha.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jftFecha.setMinimumSize(new java.awt.Dimension(100, 21));
        jftFecha.setPreferredSize(new java.awt.Dimension(100, 21));
        jftFecha.setSelectionColor(new java.awt.Color(175, 204, 125));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jpEditables.add(jftFecha, gridBagConstraints);

        jpFondo.add(jpEditables);

        jpComandos.setMaximumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setMinimumSize(new java.awt.Dimension(1060, 40));
        jpComandos.setOpaque(false);
        jpComandos.setPreferredSize(new java.awt.Dimension(1060, 40));
        jpComandos.setLayout(new java.awt.GridBagLayout());

        jbRetornar.setBackground(java.awt.SystemColor.inactiveCaption);
        jbRetornar.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        jbRetornar.setText("RETORNAR");
        jbRetornar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 560, 0, 0);
        jpComandos.add(jbRetornar, gridBagConstraints);

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

    private void jbSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSeleccionarActionPerformed

    }//GEN-LAST:event_jbSeleccionarActionPerformed

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
                new PagoSelect(null, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbResetear;
    private javax.swing.JButton jbRetornar;
    private javax.swing.JButton jbSeleccionar;
    private javax.swing.JComboBox jcbFilter;
    private javax.swing.JFormattedTextField jftFecha;
    private javax.swing.JFormattedTextField jftMontoCred;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JPanel jpCmdState;
    private javax.swing.JPanel jpComandos;
    private javax.swing.JPanel jpEditables;
    private javax.swing.JPanel jpFiltro;
    private javax.swing.JPanel jpFondo;
    private javax.swing.JFormattedTextField jtCantBeneficiarios;
    private javax.swing.JTextField jtDesc;
    private javax.swing.JTextField jtFiltro;
    private javax.swing.JTextField jtSoporte;
    private javax.swing.JTable jtable;
    private javax.swing.JLabel lblmonto1;
    // End of variables declaration//GEN-END:variables
}
