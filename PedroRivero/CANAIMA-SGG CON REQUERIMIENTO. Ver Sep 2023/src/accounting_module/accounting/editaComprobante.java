package accounting_module.accounting;

import accounting_module.Conexion.ConnAccounting;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public final class editaComprobante extends javax.swing.JFrame {

    DefaultTableModel dtm;//Se Crea un Modelo para la Tabla
    Statement sent;
    private final Connection con = null;
    ConnAccounting bd;
    private final String accion = "nuevo";
    private final int id = 0;

    public editaComprobante() {
        initComponents();
        inicializarTabla();
        Llenar();
        desHabilitar();
        etiLinea.setVisible(false);
        etiCodigo.setVisible(false);
        this.setExtendedState(editaComprobante.MAXIMIZED_BOTH);
    }

    private void inicializarTabla() {
        //array de String's con los títulos de las columnas
        String[] columnNames = {"L",
            "Nro. Comprobante",
            "Cuenta",
            "Auxiliar",
            "Concepto",
            "Debe",
            "Haber"};
        //creamos el Modelo de la tabla con als columnas
        dtm = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        this.tabla.setModel(dtm);
        //le damos tamaño
        this.tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.tabla.setRowHeight(30);
//        Llenar();
    }

    public void Habilitar() {
        txtConcepto.setEditable(true);
        txtDebe.setEnabled(false);
        txtHaber.setEnabled(false);
        txtCuenta.setEnabled(false);
        txtAuxiliar.setEnabled(false);
    }

    public void desHabilitar() {
        txtAuxiliar.setEditable(false);
        txtConcepto.setEditable(false);
        txtCuenta.setEditable(false);
        txtDebe.setEditable(false);
        txtHaber.setEditable(false);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    public void limpiar() {
        txtAuxiliar.setText("");
        txtConcepto.setText("");
        txtCuenta.setText("");
        txtDebe.setText("");
        txtHaber.setText("");
        etiComp.setText("");
    }

    public int NroMes() {
        int mes = 0;
        if (cmbMes.getSelectedItem().equals("Enero")) {
            mes = 1;
        } else if (cmbMes.getSelectedItem().equals("Febrero")) {
            mes = 2;
        } else if (cmbMes.getSelectedItem().equals("Marzo")) {
            mes = 3;
        } else if (cmbMes.getSelectedItem().equals("Abril")) {
            mes = 4;
        } else if (cmbMes.getSelectedItem().equals("Mayo")) {
            mes = 5;
        } else if (cmbMes.getSelectedItem().equals("Junio")) {
            mes = 6;
        } else if (cmbMes.getSelectedItem().equals("Julio")) {
            mes = 7;
        } else if (cmbMes.getSelectedItem().equals("Agosto")) {
            mes = 8;
        } else if (cmbMes.getSelectedItem().equals("Septiembre")) {
            mes = 9;
        } else if (cmbMes.getSelectedItem().equals("Octubre")) {
            mes = 10;
        } else if (cmbMes.getSelectedItem().equals("Noviembre")) {
            mes = 11;
        } else if (cmbMes.getSelectedItem().equals("Diciembre")) {
            mes = 12;
        }
        return mes;
    }

    public final void Llenar() {
        try {
            int month = NroMes();
            String fecha1 = " " + yyear.getText() + "/" + month + "/01 ";
            String fecha2 = " " + yyear.getText() + "/" + month + "/31 ";
            String sql = "SELECT `mh_comprobante`.`comp_linea` , `mh_comprobante`.`comp_id`, `mh_comprobante`.`cta_id`,"
                    + " `mh_comprobante`.`aux_id`, `mh_comprobante`.`comp_concepto`, `mh_comprobante`.`comp_debe`, `mh_comprobante`.`comp_haber` "
                    + "FROM `mh_comprobante` WHERE `mh_comprobante`.`emp_id`='" + etiCodigo.getText() + "' "
                    + "AND `mh_comprobante`.`comp_fecha` BETWEEN '" + fecha1 + "' AND '" + fecha2 + "' ORDER BY `mh_comprobante`.`comp_id`";
            ConnAccounting bb;
            bb = ConnAccounting.getInstance();
            ResultSet rs = bb.buscar(sql);
            inicializarTabla();
            String[] fila = new String[8];
            while (rs.next()) {
                fila[0] = rs.getString(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getString(7);
                dtm.addRow(fila);
            }
            tabla.setModel(dtm);
        } catch (Exception e) {
        }
    }

    public void Eliminar() {
        String a = "¿Desea Eliminar este Comprobante?\n Esta Opcion No es Reversible!";
        String b = "Confirmar";
        String id = etiComp.getText();
        String codigo = etiCodigo.getText();
        int month = NroMes();
        String fecha1 = " " + yyear.getText() + "/" + month + "/01 ";
        String fecha2 = " " + yyear.getText() + "/" + month + "/31 ";
        if (JOptionPane.showOptionDialog(this, a, b, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[]{"Si", "No"}, "NO") == 0) {
            String sql;
            sql = "DELETE FROM mh_comprobante  WHERE `mh_comprobante`.`comp_id` = '" + id + "' "
                    + "AND  `mh_comprobante`.`emp_id`  = '" + codigo + "' AND \n"
                    + " `mh_comprobante`.`comp_fecha` BETWEEN '" + fecha1 + "' AND '" + fecha2 + "'";
            ConnAccounting bd = ConnAccounting.getInstance();
            bd.eliminar(sql);
            inicializarTabla();
            Llenar();
            limpiar();
            JOptionPane.showMessageDialog(this, "Datos Eliminados");
            desHabilitar();
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    public void Modificar() {
        if (JOptionPane.showOptionDialog(this, "Desea Modificar estos Datos?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "NO"}, "No") == 0) {
            String sql;
            sql = "UPDATE mh_comprobante SET cta_id = '" + txtCuenta.getText() + "',";
            sql += "aux_id='" + txtAuxiliar.getText() + "',";
            sql += "comp_concepto='" + txtConcepto.getText() + "',";
            sql += "comp_debe='" + txtDebe.getText() + "',";
            sql += "comp_haber='" + txtHaber.getText() + "'";
            sql += "WHERE comp_linea = '" + etiLinea.getText() + "' ";
            sql += "AND emp_id = '" + etiCodigo.getText() + "'";

            System.out.println(sql); //muestra si la instuccion sql esta bien contruida
            ConnAccounting bd = ConnAccounting.getInstance();
            bd.ejecutar(sql);
            inicializarTabla();
            Llenar();
            limpiar();
            JOptionPane.showMessageDialog(null, "Datos Modificados");
            desHabilitar();
        } else {
            JOptionPane.showMessageDialog(this, "Accion Cancelada");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCuenta = new javax.swing.JTextField();
        txtAuxiliar = new javax.swing.JTextField();
        txtConcepto = new javax.swing.JTextField();
        txtDebe = new javax.swing.JTextField();
        txtHaber = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        etiComp = new javax.swing.JLabel();
        cmbMes = new javax.swing.JComboBox();
        yyear = new javax.swing.JLabel();
        etiLinea = new javax.swing.JLabel();
        etiCodigo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Actualizar Comprobante");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Modificar Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Cuenta");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Auxiliar");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Concepto");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Debe");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Haber");

        txtCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCuentaKeyTyped(evt);
            }
        });

        txtAuxiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAuxiliarActionPerformed(evt);
            }
        });
        txtAuxiliar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAuxiliarKeyTyped(evt);
            }
        });

        txtConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConceptoKeyTyped(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Nro Compr");

        etiComp.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        etiComp.setText("0");

        cmbMes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cmbMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMesActionPerformed(evt);
            }
        });

        yyear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        yyear.setText("jLabel7");

        etiLinea.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        etiLinea.setText("jLabel7");

        etiCodigo.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        etiCodigo.setText("Codigo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(etiLinea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(etiCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(84, 84, 84))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(etiComp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtConcepto, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtAuxiliar)
                                            .addComponent(txtCuenta)
                                            .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(yyear)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtDebe)
                                                .addComponent(txtHaber, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(116, 116, 116)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(etiComp)
                            .addComponent(cmbMes)
                            .addComponent(yyear, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(etiLinea)
                            .addComponent(etiCodigo))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtDebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtHaber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Comprobantes Registrados este Mes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));

        titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Actualizar Comprobantes");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == 1) {
            int fila = tabla.getSelectedRow();
            try {
                int month = NroMes();
                String year = yyear.getText();
                limpiar();
                Habilitar();
                String sql = "SELECT `mh_comprobante`.`comp_linea`, `mh_comprobante`.`comp_id`,`mh_comprobante`.`comp_fecha`,`mh_comprobante`.`cta_id`,"
                        + "`mh_comprobante`.`aux_id`, `mh_comprobante`.`comp_concepto`,`mh_comprobante`.`comp_debe`,`mh_comprobante`.`comp_haber`"
                        + " FROM  `mh_comprobante` WHERE `mh_comprobante`.`comp_linea` = '" + tabla.getValueAt(fila, 0) + "'"
                        + "AND `mh_comprobante`.`comp_fecha` BETWEEN'" + year + "/" + month + "/01' AND '" + year + "/" + month + "/31' "
                        + "ORDER BY `mh_comprobante`.`comp_id`";
                ConnAccounting b = ConnAccounting.getInstance();
                ResultSet result = b.buscar(sql);
                while (result.next()) {
                    etiLinea.setText(result.getString(1));
                    etiComp.setText(result.getString(2));
                    txtCuenta.setText(result.getString(4));                    
                    txtAuxiliar.setText(result.getString(5));
                    txtConcepto.setText(result.getString(6));
                    txtDebe.setText(result.getString(7));
                    txtHaber.setText(result.getString(8));
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_tablaMouseClicked

    private void cmbMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMesActionPerformed
        // TODO add your handling code here:
        limpiar();
        tabla.removeAll();
        Llenar();
        System.out.println("Este es el año: " + this.yyear.getText());
    }//GEN-LAST:event_cmbMesActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        Eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        tabla.removeAll();
        Modificar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtAuxiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAuxiliarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAuxiliarActionPerformed

    private void txtCuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCuentaKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtCuentaKeyTyped

    private void txtAuxiliarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAuxiliarKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtAuxiliarKeyTyped

    private void txtConceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConceptoKeyTyped
        String txtString = String.valueOf(evt.getKeyChar());
        if (!(txtString.matches("[0-9, ,A-Z]"))) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtConceptoKeyTyped

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
            java.util.logging.Logger.getLogger(editaComprobante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editaComprobante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editaComprobante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editaComprobante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editaComprobante().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JComboBox cmbMes;
    public javax.swing.JLabel etiCodigo;
    private javax.swing.JLabel etiComp;
    private javax.swing.JLabel etiLinea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    public javax.swing.JLabel titulo;
    private javax.swing.JTextField txtAuxiliar;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtCuenta;
    private javax.swing.JTextField txtDebe;
    private javax.swing.JTextField txtHaber;
    public javax.swing.JLabel yyear;
    // End of variables declaration//GEN-END:variables
}
