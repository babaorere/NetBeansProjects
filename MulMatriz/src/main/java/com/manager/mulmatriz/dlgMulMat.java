package com.manager.mulmatriz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author manager
 */
public final class dlgMulMat extends javax.swing.JDialog {

    private DefaultTableModel modelA;
    private DefaultTableModel modelB;
    private DefaultTableModel modelC;

    private class CustomRenderer implements TableCellRenderer {

        TableCellRenderer render;

        Border b;

        public CustomRenderer(TableCellRenderer r, Color color) {

            render = r;

            //It looks funky to have a different color on each side - but this is what you asked
            //You can comment out borders if you want too. (example try commenting out top and left borders)
            b = BorderFactory.createCompoundBorder();

            b = BorderFactory.createCompoundBorder(b, BorderFactory.createLineBorder(color, 1));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            JComponent result = (JComponent) render.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            result.setBorder(b);
            return result;

        }

    }

    /**
     * Creates new form dlgMulMat
     *
     * @param parent
     */
    public dlgMulMat(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        MyInitComponents();
    }

    private void MyInitComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Salir();
            }
        });

        modelA = (DefaultTableModel) (tblMatA.getModel());
        modelB = (DefaultTableModel) (tblMatB.getModel());
        modelC = (DefaultTableModel) (tblMatC.getModel());

        CleanTbl(tblMatA);
        CleanTbl(tblMatB);
        CleanTbl(tblMatC);

        tblMatA.setIntercellSpacing(new Dimension(0, 0));
        tblMatA.setDefaultRenderer(Object.class, new CustomRenderer(tblMatA.getDefaultRenderer(Object.class), Color.BLUE));
        tblMatB.setIntercellSpacing(new Dimension(0, 0));
        tblMatB.setDefaultRenderer(Object.class, new CustomRenderer(tblMatB.getDefaultRenderer(Object.class), Color.GREEN));
        tblMatC.setIntercellSpacing(new Dimension(0, 0));
        tblMatC.setDefaultRenderer(Object.class, new CustomRenderer(tblMatC.getDefaultRenderer(Object.class), Color.BLACK));

        DefaultTableCellRenderer centerRendererA = new DefaultTableCellRenderer();
        centerRendererA.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < modelA.getColumnCount(); i++) {
            tblMatA.getColumnModel().getColumn(i).setCellRenderer(centerRendererA);
        }

        DefaultTableCellRenderer centerRendererB = new DefaultTableCellRenderer();
        centerRendererB.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < modelB.getColumnCount(); i++) {
            tblMatB.getColumnModel().getColumn(i).setCellRenderer(centerRendererB);
        }

        DefaultTableCellRenderer centerRendererC = new DefaultTableCellRenderer();
        centerRendererC.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < modelC.getColumnCount(); i++) {
            tblMatC.getColumnModel().getColumn(i).setCellRenderer(centerRendererC);
        }

        tblMatA.setIntercellSpacing(new Dimension(0, 0));
        tblMatA.setDefaultRenderer(Object.class, new CustomRenderer(tblMatA.getDefaultRenderer(Object.class), Color.BLUE));
        tblMatB.setIntercellSpacing(new Dimension(0, 0));
        tblMatB.setDefaultRenderer(Object.class, new CustomRenderer(tblMatB.getDefaultRenderer(Object.class), Color.GREEN));
        tblMatC.setIntercellSpacing(new Dimension(0, 0));
        tblMatC.setDefaultRenderer(Object.class, new CustomRenderer(tblMatC.getDefaultRenderer(Object.class), Color.BLACK));

    }

    /**
     * Limpiar la tabla, llenandola de ceros (0) en su totalidad
     *
     * @param table
     */
    private void CleanTbl(JTable table) {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.setValueAt(0, i, j);
            }
        }
    }

    private void Salir() {

        final javax.swing.JDialog me = this;
        final javax.swing.JFrame parent = (javax.swing.JFrame) me.getParent();

        me.setVisible(false);
        me.dispose();

        if (parent != null) {
            parent.setVisible(false);
            parent.dispose();
        }

        System.exit(0);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlText = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAFil = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtACol = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtBFil = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBCol = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblMatC = new javax.swing.JLabel();
        btnMult = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        pnlTables = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMatA = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMatB = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMatC = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        pnlTitulo.setLayout(new javax.swing.BoxLayout(pnlTitulo, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Multiplicación de Matrices Integer: AxB => C");
        jLabel1.setPreferredSize(new java.awt.Dimension(330, 30));
        pnlTitulo.add(jLabel1);

        getContentPane().add(pnlTitulo);

        pnlText.setMaximumSize(new java.awt.Dimension(2147483647, 75));
        pnlText.setMinimumSize(new java.awt.Dimension(700, 75));
        pnlText.setPreferredSize(new java.awt.Dimension(770, 75));
        pnlText.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Matriz A");
        jLabel6.setAlignmentX(0.5F);
        jLabel6.setMaximumSize(new java.awt.Dimension(80, 25));
        jLabel6.setMinimumSize(new java.awt.Dimension(80, 25));
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlText.add(jLabel6, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nro. Fil.");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setMaximumSize(new java.awt.Dimension(90, 25));
        jLabel2.setMinimumSize(new java.awt.Dimension(60, 25));
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlText.add(jLabel2, gridBagConstraints);

        txtAFil.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAFil.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAFil.setMinimumSize(new java.awt.Dimension(50, 25));
        txtAFil.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlText.add(txtAFil, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nro. Col.");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setMaximumSize(new java.awt.Dimension(90, 25));
        jLabel3.setMinimumSize(new java.awt.Dimension(80, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(80, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlText.add(jLabel3, gridBagConstraints);

        txtACol.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtACol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtACol.setMinimumSize(new java.awt.Dimension(50, 25));
        txtACol.setPreferredSize(new java.awt.Dimension(50, 25));
        txtACol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAColKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlText.add(txtACol, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 102, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Matriz B");
        jLabel7.setAlignmentX(0.5F);
        jLabel7.setMaximumSize(new java.awt.Dimension(80, 25));
        jLabel7.setMinimumSize(new java.awt.Dimension(80, 25));
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 0, 0);
        pnlText.add(jLabel7, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 102, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nro. Fil.");
        jLabel4.setAlignmentX(0.5F);
        jLabel4.setMaximumSize(new java.awt.Dimension(90, 25));
        jLabel4.setMinimumSize(new java.awt.Dimension(60, 25));
        jLabel4.setPreferredSize(new java.awt.Dimension(60, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlText.add(jLabel4, gridBagConstraints);

        txtBFil.setEditable(false);
        txtBFil.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtBFil.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBFil.setMinimumSize(new java.awt.Dimension(50, 25));
        txtBFil.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        pnlText.add(txtBFil, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 102, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Nro. Col.");
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setMaximumSize(new java.awt.Dimension(90, 25));
        jLabel5.setMinimumSize(new java.awt.Dimension(80, 25));
        jLabel5.setPreferredSize(new java.awt.Dimension(80, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlText.add(jLabel5, gridBagConstraints);

        txtBCol.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtBCol.setMinimumSize(new java.awt.Dimension(50, 25));
        txtBCol.setPreferredSize(new java.awt.Dimension(50, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        pnlText.add(txtBCol, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Matriz C");
        jLabel8.setMaximumSize(new java.awt.Dimension(80, 25));
        jLabel8.setMinimumSize(new java.awt.Dimension(80, 25));
        jLabel8.setPreferredSize(new java.awt.Dimension(80, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 75, 0, 0);
        pnlText.add(jLabel8, gridBagConstraints);

        lblMatC.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblMatC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMatC.setText("XX FIL x XX COL");
        lblMatC.setPreferredSize(new java.awt.Dimension(125, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 75);
        pnlText.add(lblMatC, gridBagConstraints);

        btnMult.setBackground(new java.awt.Color(0, 153, 51));
        btnMult.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnMult.setForeground(new java.awt.Color(0, 0, 0));
        btnMult.setText("Multiplicar");
        btnMult.setMinimumSize(new java.awt.Dimension(100, 25));
        btnMult.setPreferredSize(new java.awt.Dimension(100, 25));
        btnMult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        pnlText.add(btnMult, gridBagConstraints);

        btnSalir.setBackground(new java.awt.Color(255, 153, 153));
        btnSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(0, 0, 0));
        btnSalir.setText("Salir");
        btnSalir.setPreferredSize(new java.awt.Dimension(100, 25));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        pnlText.add(btnSalir, gridBagConstraints);

        getContentPane().add(pnlText);

        pnlTables.setLayout(new javax.swing.BoxLayout(pnlTables, javax.swing.BoxLayout.LINE_AXIS));

        tblMatA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "A.Col.1", "A.Col.2", "A.Col.3", "A.Col.4", "A.Col.5", "A.Col.6"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblMatA.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblMatA);
        if (tblMatA.getColumnModel().getColumnCount() > 0) {
            tblMatA.getColumnModel().getColumn(0).setResizable(false);
            tblMatA.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(1).setResizable(false);
            tblMatA.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(2).setResizable(false);
            tblMatA.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(3).setResizable(false);
            tblMatA.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(4).setResizable(false);
            tblMatA.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblMatA.getColumnModel().getColumn(5).setResizable(false);
            tblMatA.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        pnlTables.add(jScrollPane3);

        tblMatB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "B.Col.1", "B.Col.2", "B.Col.3", "B.Col.4", "B.Col.5", "B.Col.6"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblMatB.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblMatB);
        if (tblMatB.getColumnModel().getColumnCount() > 0) {
            tblMatB.getColumnModel().getColumn(0).setResizable(false);
            tblMatB.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(1).setResizable(false);
            tblMatB.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(2).setResizable(false);
            tblMatB.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(3).setResizable(false);
            tblMatB.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(4).setResizable(false);
            tblMatB.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblMatB.getColumnModel().getColumn(5).setResizable(false);
            tblMatB.getColumnModel().getColumn(5).setPreferredWidth(40);
        }

        pnlTables.add(jScrollPane2);

        tblMatC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "C.Col.1", "C.Col.2", "C.Col.3", "C.Col.4", "C.Col.5", "C.Col.6"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
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
        tblMatC.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblMatC);

        pnlTables.add(jScrollPane1);

        getContentPane().add(pnlTables);

        setSize(new java.awt.Dimension(1010, 330));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultActionPerformed

        lblMatC.setText("A FIL x B COL");

        int aFil;
        String strAFil;
        strAFil = txtAFil.getText().trim();
        try {
            aFil = Integer.parseInt(strAFil);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "A Fil., valor Invalido");
            txtAFil.requestFocus();
            return;
        }

        if ((aFil <= 0) || (aFil > 6)) {
            JOptionPane.showMessageDialog(this, "A Fil., valor Invalido, 0 < A Fil. <= 6");
            txtAFil.requestFocus();
            return;
        }

        int aCol;
        String strACol;
        strACol = txtACol.getText().trim();
        try {
            aCol = Integer.parseInt(strACol);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "A Col., valor Invalido");
            txtACol.requestFocus();
            return;
        }

        if ((aCol <= 0) || (aCol > 6)) {
            JOptionPane.showMessageDialog(this, "A Col., valor Invalido, 0 < A Col. <= 6");
            txtACol.requestFocus();
            return;
        }

        int bFil;
        String strBFil;
        strBFil = txtBFil.getText().trim();
        try {
            bFil = Integer.parseInt(strBFil);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "B Fil., valor Invalido");
            txtBFil.requestFocus();
            return;
        }

        if ((bFil <= 0) || (bFil > 6)) {
            JOptionPane.showMessageDialog(this, "B Fil., valor Invalido, 0 < B Fil. <= 6");
            txtBFil.requestFocus();
            return;
        }

        int bCol;
        String strBCol;
        strBCol = txtBCol.getText().trim();
        try {
            bCol = Integer.parseInt(strBCol);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "B Col., valor Invalido");
            txtBCol.requestFocus();
            return;
        }

        if ((bCol <= 0) || (bCol > 6)) {
            JOptionPane.showMessageDialog(this, "B Col., valor Invalido, 0 < B Col. <= 6");
            txtBCol.requestFocus();
            return;
        }

        Integer[][] matA = new Integer[aFil][aCol];
        for (int i = 0; i < modelA.getRowCount(); i++) {
            for (int j = 0; j < modelA.getColumnCount(); j++) {
                if ((i < aFil) && (j < aCol)) {
                    try {
                        matA[i][j] = (Integer) modelA.getValueAt(i, j);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Mat. A, valor Invalido, cell " + (i + 1) + ", " + (j + 1));
                        return;
                    }
                } else {
                    modelA.setValueAt(0, i, j);
                }
            }
        }

        Integer[][] matB = new Integer[bFil][bCol];
        for (int i = 0; i < modelB.getRowCount(); i++) {
            for (int j = 0; j < modelB.getColumnCount(); j++) {
                if ((i < bFil) && (j < bCol)) {
                    try {
                        matB[i][j] = (Integer) modelB.getValueAt(i, j);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Mat. B, valor Invalido, cell " + (i + 1) + ", " + (j + 1));
                        return;
                    }
                } else {
                    modelB.setValueAt(0, i, j);
                }
            }
        }

        Integer[][] matC = new Integer[aFil][bCol];
        for (int i = 0; i < modelC.getRowCount(); i++) {
            for (int j = 0; j < modelC.getColumnCount(); j++) {
                modelC.setValueAt(0, i, j);
            }
        }

        // Se realiza la Multiplicacion
        for (int a = 0; a < bCol; a++) {
            for (int i = 0; i < aFil; i++) {
                int suma = 0;
                for (int j = 0; j < aCol; j++) {
                    suma += matA[i][j] * matB[j][a];
                }
                matC[i][a] = suma;
            }
        }

        for (int i = 0; i < matC.length; i++) {
            for (int j = 0; j < matC[0].length; j++) {
                modelC.setValueAt(matC[i][j], i, j);
            }
        }

        lblMatC.setText(aFil + " FIL x " + bCol + " COL");

    }//GEN-LAST:event_btnMultActionPerformed

    private void txtAColKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAColKeyReleased
        txtBFil.setText(txtACol.getText());
    }//GEN-LAST:event_txtAColKeyReleased

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        Salir();
    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dlgMulMat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dlgMulMat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dlgMulMat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dlgMulMat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                javax.swing.JFrame frmApp = new javax.swing.JFrame();
                dlgMulMat dialog = new dlgMulMat(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMult;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMatC;
    private javax.swing.JPanel pnlTables;
    private javax.swing.JPanel pnlText;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTable tblMatA;
    private javax.swing.JTable tblMatB;
    private javax.swing.JTable tblMatC;
    private javax.swing.JTextField txtACol;
    private javax.swing.JTextField txtAFil;
    private javax.swing.JTextField txtBCol;
    private javax.swing.JTextField txtBFil;
    // End of variables declaration//GEN-END:variables
}
