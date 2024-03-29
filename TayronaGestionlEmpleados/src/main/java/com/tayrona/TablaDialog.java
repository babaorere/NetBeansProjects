package com.tayrona;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manager
 */
public final class TablaDialog extends javax.swing.JDialog {

    /**
     * Creates new form ConsultaDialog
     *
     * @param inWin
     */
    public TablaDialog(final java.awt.Window inWin) {
        super(inWin, DEFAULT_MODALITY_TYPE);

        // Aqui se procede a crear los componentes graficos, varios de la GUI
        initComponents();

        // Aqui se procede a crear los componentes propios de la clase, los funcionales de la aplicación
        myInitComponents();
    }

    public void myInitComponents() {

        // Esto se utiliza mas adelante, para pasar esta Win como parametro en el "windowClosing"
        final java.awt.Window me = this;

        // Creación del listener, quien sera ejecutado una vez que se quiera cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    Controller.actionSalir(me);
                });
            }
        });

        llenarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel1 = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        txtIdentificacion = new javax.swing.JTextField();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel3 = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnConsultar = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        btnCancelar = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Tayrona");
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(800, 50));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 50));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1);

        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sel", "ID", "Identificación", "Nombre", "Contrato"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTabla);
        if (tblTabla.getColumnModel().getColumnCount() > 0) {
            tblTabla.getColumnModel().getColumn(0).setMinWidth(40);
            tblTabla.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblTabla.getColumnModel().getColumn(0).setMaxWidth(40);
            tblTabla.getColumnModel().getColumn(1).setMinWidth(50);
            tblTabla.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblTabla.getColumnModel().getColumn(1).setMaxWidth(50);
            tblTabla.getColumnModel().getColumn(2).setMinWidth(100);
            tblTabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblTabla.getColumnModel().getColumn(2).setMaxWidth(100);
            tblTabla.getColumnModel().getColumn(3).setResizable(false);
            tblTabla.getColumnModel().getColumn(4).setMinWidth(100);
            tblTabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblTabla.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        getContentPane().add(jScrollPane1);

        jPanel2.setMaximumSize(new java.awt.Dimension(800, 30));
        jPanel2.setMinimumSize(new java.awt.Dimension(800, 30));
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 30));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));
        jPanel2.add(filler5);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Identificación");
        jLabel1.setMaximumSize(new java.awt.Dimension(160, 25));
        jLabel1.setMinimumSize(new java.awt.Dimension(160, 25));
        jLabel1.setPreferredSize(new java.awt.Dimension(160, 25));
        jPanel2.add(jLabel1);
        jPanel2.add(filler4);

        txtIdentificacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIdentificacion.setMaximumSize(new java.awt.Dimension(160, 25));
        txtIdentificacion.setMinimumSize(new java.awt.Dimension(160, 25));
        txtIdentificacion.setPreferredSize(new java.awt.Dimension(160, 25));
        jPanel2.add(txtIdentificacion);
        jPanel2.add(filler6);

        getContentPane().add(jPanel2);

        jPanel3.setMaximumSize(new java.awt.Dimension(800, 30));
        jPanel3.setMinimumSize(new java.awt.Dimension(800, 30));
        jPanel3.setPreferredSize(new java.awt.Dimension(800, 30));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));
        jPanel3.add(filler3);

        btnConsultar.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnConsultar.setText("Consultar");
        btnConsultar.setMaximumSize(new java.awt.Dimension(100, 25));
        btnConsultar.setMinimumSize(new java.awt.Dimension(100, 25));
        btnConsultar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        jPanel3.add(btnConsultar);
        jPanel3.add(filler2);

        btnCancelar.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setMaximumSize(new java.awt.Dimension(100, 25));
        btnCancelar.setMinimumSize(new java.awt.Dimension(100, 25));
        btnCancelar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel3.add(btnCancelar);
        jPanel3.add(filler1);

        getContentPane().add(jPanel3);

        setSize(new java.awt.Dimension(810, 630));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed

        // Tomamos el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tblTabla.getModel();

        // recorremos toda la tabla hasta encontrar un registro seleccionado
        Integer idx = -1;
        for (int i = 0; i < tblTabla.getRowCount(); i++) {

            // tiene la casilla marcada
            if ((Boolean) model.getValueAt(i, 0)) {
                idx = i;
                break;
            }
        }

        // No hay registro seleccionado
        if (idx < 0) {
            String strIdentificacion;
            strIdentificacion = txtIdentificacion.getText().trim().toUpperCase();

            // Buscamos en la lista
            List<Empleado> reg = AppFrame.getEmpList();
            for (int i = 0; i < reg.size(); i++) {

                // tiene la misma identificacion
                if (strIdentificacion.equals(reg.get(i).getIdentificacion())) {
                    idx = i;
                    break;
                }
            }
        }

        if (idx >= 0) {
            // Llamamos al controlador con registro seleccionado
            Controller.actionConsultarForm(this, idx);
        } else {
            JOptionPane.showMessageDialog(this, "Debe indicar un Registro");
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Controller.actionSalir(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(TablaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TablaDialog dialog = new TablaDialog(new javax.swing.JFrame());
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

    private void llenarTabla() {

        List<Empleado> list = AppFrame.getEmpList();

        // Eliminar los elementos anteriores
        while (tblTabla.getRowCount() > 0) {
            ((DefaultTableModel) tblTabla.getModel()).removeRow(0);
        }

        Object[] row = new Object[5];
        int iRow = 0;
        for (Empleado registro : list) {
            row[0] = false;
            row[1] = (iRow + 1);
            row[2] = registro.getIdentificacion();
            row[3] = registro.getNombre();
            row[4] = registro.getTipoCont();

            ((DefaultTableModel) tblTabla.getModel()).insertRow(iRow, row);
            iRow++;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTabla;
    private javax.swing.JTextField txtIdentificacion;
    // End of variables declaration//GEN-END:variables

}
