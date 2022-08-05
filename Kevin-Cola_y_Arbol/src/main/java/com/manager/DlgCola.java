package com.manager;

import java.awt.Container;
import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import modelos.Cola;
import modelos.NumComplejo;


/**
 *
 * @author manager
 */
public class DlgCola extends javax.swing.JDialog {

    private Cola<NumComplejo> miCola;


    /**
     * Creates new form DlgCola
     *
     * @param parent
     */
    public DlgCola(java.awt.Window parent) {
        super(parent, DEFAULT_MODALITY_TYPE);
        initComponents();
        MyInitComponents();
    }


    /**
     * Inicializar componentes propios de la clase
     *
     */
    private void MyInitComponents() {

        final javax.swing.JDialog me = this;
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Salir();
            }
        });

        this.miCola = new Cola();

        Limpiar();
        MostrarDatos();

        txtReX.requestFocus();
    }


    private void MostrarDatos() {

        if ((miCola == null) || miCola.vacia()) {
            Limpiar();
            return;
        }

        DefaultListModel model = new DefaultListModel();
        lstList.setModel(model);

        final int tam = miCola.getTam();
        for (int i = 0; i < tam; i++) {

            NumComplejo elem = miCola.eliminar();

            model.addElement(elem.toString());

            miCola.insertar(elem);
        }

        txtTam.setText(String.valueOf(miCola.getTam()));
        txtIni.setText(miCola.getInicio().toString());
        txtFin.setText(miCola.getFinal().toString());

        txtReX.requestFocus();
    }


    private void Limpiar() {
        txtTam.setText("");
        txtIni.setText("");
        txtFin.setText("");
        txtReX.setText("");
        txtImY.setText("");

        txtReX.requestFocus();
    }


    private void Salir() {
        setVisible(false);
        dispose();

        Container parent = getParent();
        if (parent != null) {
            parent.setVisible(true);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTam = new javax.swing.JTextField();
        lblIni = new javax.swing.JLabel();
        txtIni = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtReX = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtImY = new javax.swing.JTextField();
        txtFin = new javax.swing.JTextField();
        lblFin = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnInsertar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TDA Cola - Números Complejos");
        setMinimumSize(new java.awt.Dimension(700, 400));
        setPreferredSize(new java.awt.Dimension(700, 400));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMinimumSize(new java.awt.Dimension(200, 200));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 350));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Tamaño");
        jLabel1.setMinimumSize(new java.awt.Dimension(80, 30));
        jLabel1.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        txtTam.setEditable(false);
        txtTam.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtTam.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTam.setMinimumSize(new java.awt.Dimension(150, 30));
        txtTam.setPreferredSize(new java.awt.Dimension(150, 30));
        txtTam.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtTam, gridBagConstraints);

        lblIni.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblIni.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIni.setText("Inicio");
        lblIni.setMinimumSize(new java.awt.Dimension(80, 30));
        lblIni.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(lblIni, gridBagConstraints);

        txtIni.setEditable(false);
        txtIni.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtIni.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIni.setMinimumSize(new java.awt.Dimension(200, 30));
        txtIni.setPreferredSize(new java.awt.Dimension(250, 30));
        txtIni.setRequestFocusEnabled(false);
        txtIni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtIniMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtIni, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Re X");
        jLabel3.setMinimumSize(new java.awt.Dimension(80, 30));
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        txtReX.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtReX.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtReX.setMinimumSize(new java.awt.Dimension(200, 30));
        txtReX.setPreferredSize(new java.awt.Dimension(250, 30));
        txtReX.setRequestFocusEnabled(false);
        txtReX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtReXMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtReX, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Im Y");
        jLabel4.setMinimumSize(new java.awt.Dimension(80, 30));
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtImY.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtImY.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtImY.setMinimumSize(new java.awt.Dimension(200, 30));
        txtImY.setPreferredSize(new java.awt.Dimension(250, 30));
        txtImY.setRequestFocusEnabled(false);
        txtImY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtImYMouseClicked(evt);
            }
        });
        txtImY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImYActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtImY, gridBagConstraints);

        txtFin.setEditable(false);
        txtFin.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtFin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFin.setMinimumSize(new java.awt.Dimension(200, 30));
        txtFin.setPreferredSize(new java.awt.Dimension(250, 30));
        txtFin.setRequestFocusEnabled(false);
        txtFin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFinMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(txtFin, gridBagConstraints);

        lblFin.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblFin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFin.setText("Fin");
        lblFin.setMinimumSize(new java.awt.Dimension(80, 30));
        lblFin.setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel1.add(lblFin, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setText("Número Complejo (reX, ImY)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jLabel2, gridBagConstraints);

        getContentPane().add(jPanel1);

        jPanel2.setMaximumSize(new java.awt.Dimension(200, 2147483647));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        btnEliminar.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setToolTipText("Eliminar el Inicio de la Cola");
        btnEliminar.setMinimumSize(new java.awt.Dimension(160, 35));
        btnEliminar.setPreferredSize(new java.awt.Dimension(160, 35));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        jPanel2.add(btnEliminar, gridBagConstraints);

        btnInsertar.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnInsertar.setText("Insertar");
        btnInsertar.setToolTipText("Insertar a la Cola");
        btnInsertar.setMinimumSize(new java.awt.Dimension(160, 35));
        btnInsertar.setPreferredSize(new java.awt.Dimension(160, 35));
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        jPanel2.add(btnInsertar, gridBagConstraints);

        btnSalir.setBackground(new java.awt.Color(255, 102, 102));
        btnSalir.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setMinimumSize(new java.awt.Dimension(160, 35));
        btnSalir.setPreferredSize(new java.awt.Dimension(160, 35));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        jPanel2.add(btnSalir, gridBagConstraints);

        getContentPane().add(jPanel2);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(250, 150));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(250, 150));

        lstList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstList.setEnabled(false);
        jScrollPane2.setViewportView(lstList);

        getContentPane().add(jScrollPane2);

        setSize(new java.awt.Dimension(810, 530));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtIniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIniMouseClicked
        txtReX.requestFocus();
    }//GEN-LAST:event_txtIniMouseClicked

    private void txtReXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtReXMouseClicked
        txtReX.requestFocus();
    }//GEN-LAST:event_txtReXMouseClicked

    private void txtImYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtImYMouseClicked
        txtImY.requestFocus();
    }//GEN-LAST:event_txtImYMouseClicked

    private void txtFinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFinMouseClicked
        txtReX.requestFocus();
    }//GEN-LAST:event_txtFinMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        // Lista no ha sido creada
        if ((miCola == null) || miCola.vacia()) {
            JOptionPane.showMessageDialog(null, "La cola esta Vacia");
            Limpiar();
            txtReX.requestFocus();
            return;
        }

        NumComplejo aux = miCola.eliminar();

        MostrarDatos();

        txtReX.setText(String.valueOf(aux.getRe()));
        txtImY.setText(String.valueOf(aux.getIm()));

        txtReX.requestFocus();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed

        // Lista no ha sido creada
        if (miCola == null) {
            JOptionPane.showMessageDialog(null, "La cola no ha sido Creada");
            Limpiar();
            txtReX.requestFocus();
            return;
        }

        String strReX = txtReX.getText().trim().toUpperCase();

        if (strReX.isEmpty()) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO ***");
            txtReX.requestFocus();
            return;
        }

        double reX;
        try {
            reX = Double.parseDouble(strReX);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO ***");
            txtReX.requestFocus();
            return;
        }

        String strImY = txtImY.getText().trim().toUpperCase();

        if (strImY.isEmpty()) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO ***");
            txtImY.requestFocus();
            return;
        }

        double imY;
        try {
            imY = Double.parseDouble(strImY);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "*** VALOR INVALIDO ***");
            txtImY.requestFocus();
            return;
        }

        miCola.insertar(new NumComplejo(reX, imY));

        Limpiar();
        MostrarDatos();
        txtReX.requestFocus();
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        Salir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtImYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImYActionPerformed
        btnInsertar.doClick();
    }//GEN-LAST:event_txtImYActionPerformed


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
            java.util.logging.Logger.getLogger(DlgCola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgCola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgCola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgCola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DlgCola dialog = new DlgCola(new javax.swing.JFrame());
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
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFin;
    private javax.swing.JLabel lblIni;
    private javax.swing.JList<String> lstList;
    private javax.swing.JTextField txtFin;
    private javax.swing.JTextField txtImY;
    private javax.swing.JTextField txtIni;
    private javax.swing.JTextField txtReX;
    private javax.swing.JTextField txtTam;
    // End of variables declaration//GEN-END:variables
}
