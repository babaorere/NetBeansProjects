package View.Resultado;

import Controller.Resultado.ResultadoCrudController;
import Controller.UsuarioCrudController;
import Model.Resultado;
import Model.ResultadoDao;
import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
public final class ResultadoCrudDialog extends javax.swing.JDialog {

    /**
     * Creates new form CRUD_Usuarios
     *
     * @param inWin
     */
    public ResultadoCrudDialog(final java.awt.Window inWin) {
        super(inWin, DEFAULT_MODALITY_TYPE);

        // Aqui se procede a crear los componentes graficos, varios de la GUI
        initComponents();

        // Aqui se procede a crear los componentes propios de la clase, los funcionales de la aplicación
        myInitComponents();
    }

    public void myInitComponents() {

        // Esto se utiliza mas adelante, para pasar esta Win como parametro en el "windowClosing"
        final java.awt.Window me = this;

        // Creación del listener, qie sera ejecutado una vez que se quiera cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    UsuarioCrudController.actionSalir(me);
                });
            }
        });

    }

    /**
     * Sustituimos el metodo set visible para llenar la tabla de Usuarios
     *
     * @param bln
     */
    @Override
    public void setVisible(boolean bln) {
        if (bln) {
            llenarTabla();
        }

        super.setVisible(bln);
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
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnCrear = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        btnConsultar = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        btnModificar = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        btnEliminar = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        btnCancelar = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("CRUD Resultado");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setLayout(new java.awt.BorderLayout());

        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Marca", "ID", "Usuario", "Ingreso", "Gasto", "Utilidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblTabla);
        if (tblTabla.getColumnModel().getColumnCount() > 0) {
            tblTabla.getColumnModel().getColumn(0).setMinWidth(50);
            tblTabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblTabla.getColumnModel().getColumn(0).setMaxWidth(50);
            tblTabla.getColumnModel().getColumn(1).setResizable(false);
            tblTabla.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblTabla.getColumnModel().getColumn(2).setResizable(false);
            tblTabla.getColumnModel().getColumn(3).setResizable(false);
            tblTabla.getColumnModel().getColumn(4).setResizable(false);
            tblTabla.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1);

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 30));
        jPanel2.setMinimumSize(new java.awt.Dimension(0, 30));
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 30));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));
        jPanel2.add(filler2);

        btnCrear.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnCrear.setText("Crear");
        btnCrear.setEnabled(false);
        btnCrear.setMaximumSize(new java.awt.Dimension(100, 25));
        btnCrear.setMinimumSize(new java.awt.Dimension(100, 25));
        btnCrear.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        jPanel2.add(btnCrear);
        jPanel2.add(filler3);

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
        jPanel2.add(btnConsultar);
        jPanel2.add(filler6);

        btnModificar.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.setMaximumSize(new java.awt.Dimension(100, 25));
        btnModificar.setMinimumSize(new java.awt.Dimension(100, 25));
        btnModificar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel2.add(btnModificar);
        jPanel2.add(filler4);

        btnEliminar.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setMaximumSize(new java.awt.Dimension(100, 25));
        btnEliminar.setMinimumSize(new java.awt.Dimension(100, 25));
        btnEliminar.setPreferredSize(new java.awt.Dimension(100, 25));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar);
        jPanel2.add(filler5);

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
        jPanel2.add(btnCancelar);
        jPanel2.add(filler1);

        getContentPane().add(jPanel2);

        setSize(new java.awt.Dimension(810, 430));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        ResultadoCrudController.Crear(this);
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        ResultadoCrudController.Modificar(this);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        ResultadoCrudController.Eliminar(this);
        llenarTabla();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        ResultadoCrudController.actionSalir(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        ResultadoCrudController.ConsultarUsuario(this);
    }//GEN-LAST:event_btnConsultarActionPerformed

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
            java.util.logging.Logger.getLogger(ResultadoCrudDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResultadoCrudDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResultadoCrudDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResultadoCrudDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ResultadoCrudDialog dialog = new ResultadoCrudDialog(new javax.swing.JFrame());
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTabla;
    // End of variables declaration//GEN-END:variables

    private void llenarTabla() {

        ResultadoDao dao = new ResultadoDao();

        List<Resultado> list;
        try {
            list = dao.findAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al tratar de recuperar la lista de Registros\n" + ex);
            return;
        }

        // Eliminar los elementos anteriores
        while (tblTabla.getRowCount() > 0) {
            ((DefaultTableModel) tblTabla.getModel()).removeRow(0);
        }

        Object[] row = new Object[6];
        int iRow = 0;
        for (Resultado item : list) {
            row[0] = false;
            row[1] = item.getId();
            row[2] = item.getUsuario().getNickname();
            row[3] = item.getIngresos();
            row[4] = item.getGastos();
            row[5] = item.getUtilidad();

            ((DefaultTableModel) tblTabla.getModel()).insertRow(iRow, row);
            iRow++;
        }

    }

    /**
     * Retornamos la pk ID, del registro seleccionado en la Tabla, si no hay retornamos 0
     *
     * @return
     */
    public Integer getPK() {

        // Tomamos el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tblTabla.getModel();

        // recorremos toda la tabla hasta encontrar un registro seleccionado
        Integer pk = 0;
        for (int i = 0; i < tblTabla.getRowCount(); i++) {

            // tiene la casilla marcada
            if ((Boolean) model.getValueAt(i, 0)) {
                pk = (Integer) model.getValueAt(i, 1);
                break;
            }

        }

        // Si no hay casilla seleccionada, retorna un 0
        return pk;

    }
}
