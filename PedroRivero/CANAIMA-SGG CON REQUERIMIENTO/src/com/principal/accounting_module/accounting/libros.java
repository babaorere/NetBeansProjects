package com.principal.accounting_module.accounting;

import com.principal.accounting_module.Conexion.reportes;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

public class libros extends javax.swing.JFrame {

    public libros() {
        initComponents();
        this.empresa.setVisible(false);
        this.mes.setVisible(false);
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

    public void parametroLibroD() {
        int month = NroMes();
        String a = yyear.getText();
        String fecha1 = " " + a + "/" + month + "/01 ";
        String fecha2 = " " + a + "/" + month + "/31 ";
        String codigo = empresa.getText();
        reportes rd = new reportes();
        try {
            rd.libro(codigo, fecha1, fecha2);
            System.out.println(fecha1 + " " + fecha2);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(libros.class.getName()).log(Level.SEVERE, null, ex);
            logger.error(ex);
        }
    }

    public void parametroMayor() {
        int month = NroMes();
        String dia = "";
        if (month == 2) {
            dia = "28";
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            dia = "30";
        } else {
            dia = "31";
        }
        int year = Integer.parseInt(this.yyear.getText());
        int mes = Integer.parseInt(this.mes.getText());
        int meses = 0;
        int in = 0;
        int day = 0;
        if (mes == month) {
            meses = mes - 1;
            in = month + 1;
            day = 1;
        } else {
            meses = mes;
            in = month;
            day = 31;
        }
        String emp = this.empresa.getText();
        String inicio = " " + year + "/" + meses + "/01 ";
        String mesAnt = " " + year + "/" + (in - 1) + "/" + day + "";
        String mesAct = " " + year + "/" + month + "/" + dia + "";
        String fecha = "" + dia + "/" + month + "/" + year + "";
        reportes rd = new reportes();
        try {
            rd.mayor(emp, inicio, mesAnt, mesAct, fecha);
            System.out.println("empresa: " + emp);
            System.out.println("inicioFiscal: " + inicio);
            System.out.println("mesAnterior: " + mesAnt);
            System.out.println("mesActual: " + mesAct);
            System.out.println("fecha: " + fecha);
        } catch (Exception e) {
            Logger.getLogger(libros.class.getName()).log(Level.SEVERE, null, e);
            logger.error(e);
        }
    }

    public void parameroBalanceComp() {
        try {
            int month = NroMes();
            String dia = "";
            if (month == 2) {
                dia = "28";
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                dia = "30";
            } else {
                dia = "31";
            }
            String emp = this.empresa.getText();
            String inicio = " " + this.yyear.getText() + "/" + this.mes.getText() + "/01 ";
            String mesAct = " " + this.yyear.getText() + "/" + month + "/" + dia + "";
            String fecha = "" + dia + "/" + month + "/" + this.yyear.getText() + "";
            reportes rbc = new reportes();
            rbc.BalanceComp(inicio, mesAct, fecha, emp);
            System.out.println("fecha1 " + inicio + "\n fecha2  " + mesAct + "\n empresa " + emp + "\n fecha " + fecha);
        } catch (Exception ex) {
            Logger.getLogger(libros.class.getName()).log(Level.SEVERE, null, ex);
            logger.error(ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    void initComponents() {
        reportGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        radioDiario = new javax.swing.JRadioButton();
        radioMayor = new javax.swing.JRadioButton();
        radioComprb = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        yyear = new javax.swing.JLabel();
        yyear1 = new javax.swing.JLabel();
        yyear2 = new javax.swing.JLabel();
        cmbMes = new javax.swing.JComboBox();
        btnImprimir = new javax.swing.JButton();
        empresa = new javax.swing.JLabel();
        mes = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(0, 0, 0), null, null));
        // NOI18N
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Libros Contables");
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE));
        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reporte Mensual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        radioDiario.setBackground(new java.awt.Color(255, 255, 255));
        reportGroup.add(radioDiario);
        // NOI18N
        radioDiario.setFont(new java.awt.Font("Tahoma", 1, 12));
        radioDiario.setText("Libro Diario");
        radioMayor.setBackground(new java.awt.Color(255, 255, 255));
        reportGroup.add(radioMayor);
        // NOI18N
        radioMayor.setFont(new java.awt.Font("Tahoma", 1, 12));
        radioMayor.setText("Libro Mayor");
        radioComprb.setBackground(new java.awt.Color(255, 255, 255));
        reportGroup.add(radioComprb);
        // NOI18N
        radioComprb.setFont(new java.awt.Font("Tahoma", 1, 12));
        radioComprb.setText("Balance de Comprobacion");
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(radioDiario).addGap(18, 18, 18).addComponent(radioMayor).addGap(18, 18, 18).addComponent(radioComprb).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(radioDiario).addComponent(radioMayor).addComponent(radioComprb)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        // NOI18N
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Periodo Fiscal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        // NOI18N
        yyear.setFont(new java.awt.Font("Tahoma", 1, 12));
        yyear.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        yyear.setText("2017");
        // NOI18N
        yyear1.setFont(new java.awt.Font("Tahoma", 1, 12));
        yyear1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        yyear1.setText("Año: ");
        // NOI18N
        yyear2.setFont(new java.awt.Font("Tahoma", 1, 12));
        yyear2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        yyear2.setText("Mes: ");
        // NOI18N
        cmbMes.setFont(new java.awt.Font("Tahoma", 1, 12));
        cmbMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cmbMes.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMesActionPerformed(evt);
            }
        });
        // NOI18N
        btnImprimir.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnImprimir.setText("Generar Reporte");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        // NOI18N
        empresa.setFont(new java.awt.Font("Tahoma", 0, 8));
        empresa.setText("Codigo");
        // NOI18N
        mes.setFont(new java.awt.Font("Tahoma", 0, 8));
        mes.setText("Codigo");
        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addComponent(mes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(109, 109, 109)))).addGroup(jPanel4Layout.createSequentialGroup().addComponent(yyear2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE).addComponent(yyear1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(yyear, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(42, 42, 42)))));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(yyear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(yyear1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(yyear2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(cmbMes))).addGap(32, 32, 32).addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(empresa).addComponent(mes))));
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pack();
    }

    // </editor-fold>//GEN-END:initComponents
    private void cmbMesActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_cmbMesActionPerformed
        // TODO add your handling code here:
    }

//GEN-LAST:event_cmbMesActionPerformed
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnImprimirActionPerformed
        if (this.radioDiario.isSelected()) {
            parametroLibroD();
        } else if (this.radioMayor.isSelected()) {
            parametroMayor();
        } else if (this.radioComprb.isSelected()) {
            parameroBalanceComp();
        }
    }

//GEN-LAST:event_btnImprimirActionPerformed
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(libros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(libros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(libros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(libros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            logger.error(ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new libros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;

    private javax.swing.JComboBox cmbMes;

    public javax.swing.JLabel empresa;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JPanel jPanel3;

    private javax.swing.JPanel jPanel4;

    public javax.swing.JLabel mes;

    private javax.swing.JRadioButton radioComprb;

    private javax.swing.JRadioButton radioDiario;

    private javax.swing.JRadioButton radioMayor;

    private javax.swing.ButtonGroup reportGroup;

    public javax.swing.JLabel yyear;

    public javax.swing.JLabel yyear1;

    public javax.swing.JLabel yyear2;

    // End of variables declaration//GEN-END:variables
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(libros.class);
}
