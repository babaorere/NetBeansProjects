package com.manager.listacirculardoble;

import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ListaDobleCircular extends javax.swing.JFrame {

    public class Nodo {

        String codigo;
        String nombre;
        String apellidos;
        String sexo;
        float sueldo;
        Nodo enlaceSig;
        Nodo enlaceAnt;

        public Nodo(String cod, String nom, String ape, String sex, float suel) {
            codigo = cod;
            nombre = nom;
            apellidos = ape;
            sexo = sex;
            sueldo = suel;
            enlaceSig = this;
            enlaceAnt = this;
        }
    }

    DefaultTableModel miModelo;
    String[] cabecera = {"N°", "CODIGO", "NOMBRE", "APELLIDO", "SEXO", "SUELDO"};

    String[][] data = {};

    public Nodo ic;
    public Nodo nodoActual;

    public Nodo pFound;
    int num = 0;

    public ListaDobleCircular() {

        initComponents();

        MyInitComponents();

        ic = null;
        nodoActual = null;
        pFound = null;

        miModelo = new DefaultTableModel(data, cabecera);
        tblResultados.setModel(miModelo);
    }

    private void MyInitComponents() {

        cbxSEXO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"FEMENINO", "MASCULINO"}));
        cbxSEXO.setSelectedIndex(-1);

        // Centrar la Ventana
        setLocationRelativeTo(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtSueldo = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnInserFinal = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnReestaurar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSuelMayor = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        cbxSEXO = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setText("REGISTRO DE EMPLEADOS");

        jLabel2.setText("CODIGO");

        jLabel3.setText("NOMBRE");

        jLabel4.setText("APELLIDOS");

        jLabel5.setText("SEXO");

        jLabel6.setText("SUELDO");

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnInserFinal.setText("INSERTAR FINAL");

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnConsultar.setText("CONSULTAR");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnReestaurar.setText("REESTAURAR");
        btnReestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReestaurarActionPerformed(evt);
            }
        });

        btnSalir.setText("SALIR");

        tblResultados.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null}
                },
                new String[]{
                    "N°", "CODIGO", "NOMBRES", "APELLIDOS", "SEXO", "SUELDO"
                }
        ));
        jScrollPane1.setViewportView(tblResultados);

        jLabel7.setText("EMPLEADO CON MAYOR SUELDO");

        jLabel8.setText("MONTO DE SUELDOS ACUMULADOS");

        cbxSEXO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel6))
                                                .addGap(30, 30, 30)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addContainerGap())
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(txtSueldo, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(cbxSEXO, javax.swing.GroupLayout.Alignment.LEADING, 0, 182, Short.MAX_VALUE))
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(55, 55, 55)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(btnEliminar)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                .addComponent(btnReestaurar))
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(btnActualizar)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                .addComponent(btnConsultar))
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(btnGuardar)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                .addComponent(btnInserFinal)))
                                                                                .addGap(37, 37, 37))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(btnSalir)
                                                                                .addGap(106, 106, 106))))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addComponent(txtSuelMayor, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(42, 42, 42)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel8)
                                                                .addGap(0, 70, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(txtMonto)
                                                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnGuardar)
                                        .addComponent(btnInserFinal))
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnActualizar)
                                        .addComponent(btnConsultar))
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(btnEliminar)
                                        .addComponent(btnReestaurar)
                                        .addComponent(cbxSEXO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSalir))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtSuelMayor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String cod = txtCodigo.getText().trim().toUpperCase();
        if (cod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique un Codigo");
            txtCodigo.requestFocusInWindow();
            return;
        }

        String nom = txtNombre.getText().trim().toUpperCase();
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique un Nombre");
            txtNombre.requestFocusInWindow();
            return;
        }

        String ape = txtApellido.getText().trim().toUpperCase();
        if (ape.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique un Apellido");
            txtApellido.requestFocusInWindow();
            return;
        }

        String sex = cbxSEXO.getSelectedItem().toString();;
        if (cbxSEXO.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Indique un Sexo");
            cbxSEXO.requestFocusInWindow();
            return;
        }

        String strSuel = txtSueldo.getText().trim();

        float suel;

        try {
            suel = Float.parseFloat(strSuel);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "Sueldo invalido");
            txtSueldo.requestFocusInWindow();
            return;
        }

        ic = InsertarFinal(ic, cod, nom, ape, sex, suel);
        LimpiarEntradas();
        VerDatos();
        Resumen();
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        pFound.codigo = txtCodigo.getText();
        pFound.nombre = txtNombre.getText().toUpperCase();
        pFound.apellidos = txtApellido.getText().toUpperCase();
        pFound.sexo = cbxSEXO.getSelectedItem().toString();
        pFound.sueldo = Float.parseFloat(txtSueldo.getText());
        LimpiarEntradas();
        deshabilitar();
        VerDatos();
        Resumen();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Eliminar();
        LimpiarEntradas();
        VerDatos();
        if (ic == null) {
            JOptionPane.showMessageDialog(this, "LA LISTA ESTA VACIA");
            deshabilitar();
            Resumen();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnReestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReestaurarActionPerformed
        LimpiarEntradas();
        deshabilitar();
    }//GEN-LAST:event_btnReestaurarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        String cod = txtCodigo.getText();
        if (cod.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo");
        } else {
            pFound = Buscar(ic, cod);
            if (pFound != null) {
                txtNombre.setText(pFound.nombre);
                txtApellido.setText(pFound.apellidos);
                if (pFound.sexo.equalsIgnoreCase("MASCULINO")) {
                    cbxSEXO.setSelectedIndex(2);
                } else {
                    cbxSEXO.setSelectedIndex(1);
                }
                txtSueldo.setText(String.valueOf(pFound.sueldo));
                habilitar();
            } else {
                JOptionPane.showMessageDialog(this, "El codigo:" + cod + "no esta en la lista");
            }
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    /**
     * @param args the command line arguments
     */
    Nodo Buscar(Nodo ic) {
        Nodo actual;
        boolean encontrado = false;
        actual = ic;
        while (actual.enlaceSig != ic && !encontrado) {
            encontrado = actual.enlaceSig.codigo.equalsIgnoreCase(txtCodigo.getText().trim());
            if (!encontrado) {
                actual = actual.enlaceSig;
            }
        }
        return actual.enlaceSig;
    }

    public Nodo InsertarFinal(Nodo ic, String cod, String nom, String ape, String sex, float suel) {
        Nodo nuevo = new Nodo(cod, nom, ape, sex, suel);
        if (ic != null) {

            Nodo nSig = ic.enlaceSig;

            nuevo.enlaceSig = nSig;
            ic.enlaceSig = nuevo;

            nSig.enlaceAnt = nuevo;
            nuevo.enlaceAnt = ic;
        }
        ic = nuevo;
        return ic;
    }

    void Eliminar() {
        Nodo actual;
        boolean encontrado = false;
        actual = ic;
        while ((actual.enlaceSig != ic) && (!encontrado)) {
            encontrado = actual.enlaceSig.codigo.equalsIgnoreCase(txtCodigo.getText().trim());
            if (!encontrado) {
                actual = actual.enlaceSig;
            }
        }

        encontrado = actual.enlaceSig.codigo.equalsIgnoreCase(txtCodigo.getText().trim());
        if (encontrado) {
            Nodo p;
            p = actual.enlaceSig;
            if (ic == ic.enlaceSig) {
                ic = null;
            } else {
                if (p == ic) {
                    ic = actual;
                }
                actual.enlaceSig = p.enlaceSig;
            }
        }
        VerDatos();
    }

    void VerDatos() {
        String cod, nom, ape, se, su;
        float s;

        vaciarTabla();

        Nodo p;
        if (ic != null) {
            num = 0;
            p = ic.enlaceSig;
            do {
                cod = p.codigo;
                nom = p.nombre;
                ape = p.apellidos;
                se = p.sexo;
                DecimalFormat df2 = new DecimalFormat("####,00");
                su = df2.format(p.sueldo);
                num++;
                Object[] fila = {num, cod, nom, ape, se, su};
                miModelo.addRow(fila);
                p = p.enlaceSig;
            } while (p != ic.enlaceSig);
        }
    }

    void Resumen() {
        String nom = "", acum = "";
        float suma = 0, mayor = -9999;
        float s;
        Nodo p = null;
        if (ic != null) {
            p = ic.enlaceSig;
            do {
                s = p.sueldo;
                if (s > mayor) {
                    mayor = s;
                    nom = p.nombre + "" + p.apellidos;
                }
                suma = suma + s;
                p = p.enlaceSig;
            } while (p != ic.enlaceSig);
            txtSuelMayor.setText(nom);
            DecimalFormat df2 = new DecimalFormat("####,00");
            acum = df2.format(suma);
            txtMonto.setText(nom);
        }
    }

    void habilitar() {
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnGuardar.setEnabled(false);
    }

    void deshabilitar() {
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(true);
    }

    void LimpiarEntradas() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtSueldo.setText("");
        txtApellido.setText("");
        cbxSEXO.setSelectedIndex(-1);
        txtCodigo.requestFocusInWindow();
    }

    void vaciarTabla() {
        int filas = tblResultados.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0);
        }
    }

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaDobleCircular.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListaDobleCircular().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnInserFinal;
    private javax.swing.JButton btnReestaurar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbxSEXO;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResultados;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSuelMayor;
    private javax.swing.JTextField txtSueldo;
    // End of variables declaration//GEN-END:variables
}
