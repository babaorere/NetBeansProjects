package com.principal.account_module.main;

import com.principal.accounting_module.Conexion.ConnAccounting;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class elegir extends javax.swing.JDialog {

    /**
     * Creates new form elegir
     *
     * @param parent
     * @param modal
     */
    DefaultTableModel model;

    public elegir(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.visibleFalse();
        this.btnAceptar.setEnabled(false);
        this.year.setVisible(false);
        this.mes.setVisible(false);
        this.fin.setVisible(false);
        this.Llenar();
    }

    public void visibleFalse() {
        this.codigo.setVisible(false);
        this.nombre.setVisible(false);
        this.rif.setVisible(false);
        this.inicio.setVisible(false);
        this.cierre.setVisible(false);
    }

    public void visibleTrue() {
        this.codigo.setVisible(true);
        this.nombre.setVisible(true);
        this.rif.setVisible(true);
        this.inicio.setVisible(true);
        this.cierre.setVisible(true);
    }

    public void Llenar() {
        try {
            //Encabezado del JTablet
            String[] titulos = { "Codigo", "Empresa", "RIF" };
            String sql = "SELECT  mh_empresas.`emp_id` AS Codigo,  mh_empresas.`emp_nombre` AS Empresa," + "mh_empresas.`emp_rif` AS RIF, DATE_FORMAT(mh_empresas.`emp_inicioFiscal`,'%m/%Y') AS InicioFiscal," + "DATE_FORMAT(`emp_finFiscal`,'%m/%Y') AS CierreFiscal " + "FROM  `mh_empresas` mh_empresas";
            //Se llama al modelo del JTable
            model = new DefaultTableModel(null, titulos);
            ConnAccounting bd = ConnAccounting.getInstance();
            // Se llama al metodo de busqueda de la base de datos
            ResultSet rs = bd.buscar(sql);
            String[] fila = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                // Va apareciendo una fila por cada elemento encontrado en la base de datos
                model.addRow(fila);
            }
            //Se le asigna el modelo al JTable
            tabla.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logger.error(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        codigo = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        rif = new javax.swing.JLabel();
        inicio = new javax.swing.JLabel();
        cierre = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        year = new javax.swing.JLabel();
        mes = new javax.swing.JLabel();
        fin = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Seleccionar Empresa");
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 255), null, null));
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccionar Empresa");
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE));
        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empresa Seleccionada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        // NOI18N
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Codigo: ");
        // NOI18N
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Empresa: ");
        // NOI18N
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("R.I.F.: ");
        // NOI18N
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Inicio Fiscal: ");
        // NOI18N
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Cierre Fiscal: ");
        // NOI18N
        codigo.setFont(new java.awt.Font("Tahoma", 1, 12));
        codigo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        codigo.setText("codigo");
        // NOI18N
        nombre.setFont(new java.awt.Font("Tahoma", 1, 12));
        nombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nombre.setText("nombre");
        // NOI18N
        rif.setFont(new java.awt.Font("Tahoma", 1, 12));
        rif.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rif.setText("rif");
        // NOI18N
        inicio.setFont(new java.awt.Font("Tahoma", 1, 12));
        inicio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inicio.setText("inicio");
        // NOI18N
        cierre.setFont(new java.awt.Font("Tahoma", 1, 12));
        cierre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cierre.setText("cierre");
        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 51), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        // NOI18N
        btnAceptar.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnAceptar.setText("Aceptar");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(rif, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(codigo)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(nombre)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(rif)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel5).addComponent(inicio)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel6).addComponent(cierre)).addGap(0, 0, Short.MAX_VALUE)).addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empresas Registradas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        tabla.setFont(new java.awt.Font("Tahoma", 1, 12));
        tabla.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null }, { null, null, null, null } }, new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);
        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE));
        year.setText("jLabel7");
        mes.setText("jLabel7");
        fin.setText("jLabel7");
        // NOI18N
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton1.setText("<< Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(year).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(mes).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(fin).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton1).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(year).addComponent(mes).addComponent(fin).addComponent(jButton1)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pack();
    }

    // </editor-fold>//GEN-END:initComponents
    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        //GEN-FIRST:event_tablaMouseClicked
        if (evt.getButton() == 1) {
            int fila = tabla.getSelectedRow();
            try {
                this.visibleTrue();
                String sql = "SELECT  emp_id,emp_nombre,emp_rif,DATE_FORMAT(mh_empresas.`emp_inicioFiscal`,'%d/%m/%Y')," + " DATE_FORMAT(`emp_finFiscal`,'%d/%m/%Y') AS CierreFiscal," + "DATE_FORMAT(mh_empresas.`emp_inicioFiscal`,'%Y') AS year," + "DATE_FORMAT(mh_empresas.`emp_inicioFiscal`,'%m') AS mes " + "FROM  mh_empresas Where emp_id=" + tabla.getValueAt(fila, 0);
                ConnAccounting bd = ConnAccounting.getInstance();
                ResultSet rs = bd.buscar(sql);
                rs.next();
                codigo.setText(rs.getString(1));
                nombre.setText(rs.getString(2));
                rif.setText(rs.getString(3));
                inicio.setText(rs.getString(4));
                cierre.setText(rs.getString(5));
                year.setText(rs.getString(6));
                mes.setText(rs.getString(7));
                fin.setText(rs.getString(5));
                this.btnAceptar.setEnabled(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                logger.error(e);
            }
        }
    }

    //GEN-LAST:event_tablaMouseClicked
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_btnAceptarActionPerformed
        AccountingMain p = new AccountingMain(null);
        p.setVisible(rootPaneCheckingEnabled);
        p.enableFalse();
        p.visibleTrue();
        p.etiqCodigo.setText(codigo.getText());
        p.etiqNombre.setText(nombre.getText());
        p.etiqRif.setText(rif.getText());
        p.etiInicio.setText(inicio.getText());
        p.etiFin.setText(cierre.getText());
        p.mes.setText(mes.getText());
        p.etiyear.setText(year.getText());
        System.out.println("CODIGO EMPRESA: " + p.etiqCodigo.getText());
        this.dispose();
    }

    //GEN-LAST:event_btnAceptarActionPerformed
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        //GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        AccountingMain p = new AccountingMain(null);
        p.setVisible(true);
    }

    //GEN-LAST:event_jButton1ActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            java.util.logging.Logger.getLogger(elegir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            elegir dialog = new elegir(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;

    private javax.swing.JLabel cierre;

    private javax.swing.JLabel codigo;

    private javax.swing.JLabel fin;

    private javax.swing.JLabel inicio;

    private javax.swing.JButton jButton1;

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

    private javax.swing.JPanel jPanel5;

    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JLabel mes;

    private javax.swing.JLabel nombre;

    private javax.swing.JLabel rif;

    private javax.swing.JTable tabla;

    private javax.swing.JLabel year;

    // End of variables declaration//GEN-END:variables
    private static final Logger logger = LogManager.getLogger(elegir.class);
}