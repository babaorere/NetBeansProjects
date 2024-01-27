package com.app.controlanimal;

import java.awt.Window;
import java.util.List;


/**
 *
 */
public class DlgVenderAnimal extends javax.swing.JDialog {

    private List<Animal> listAlta;
    private List<Animal> listBaja;


    /**
     * Creates new form DlgVenderAnimal
     *
     * @param parent
     */
    public DlgVenderAnimal(java.awt.Window parent) {
        super(parent, ModalityType.APPLICATION_MODAL);
        initComponents();
        myInit();
    }


    private void myInit() {

        final java.awt.Window me = this;

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    actionSalir(me);
                });
            }
        });

        listAlta = FrmAppMain.getListAlta();
        listBaja = FrmAppMain.getListBaja();

        btnVender.setEnabled(true);

        btnAlta_1.setEnabled(false);
        txtUbicacion_1.setEnabled(false);
        txtPrecioCompra_1.setEnabled(false);
        txtPrecioVenta_1.setEnabled(false);

        btnAlta_2.setEnabled(false);
        txtUbicacion_2.setEnabled(false);
        txtPrecioCompra_2.setEnabled(false);
        txtPrecioVenta_2.setEnabled(false);

        txtAnimalesDisponibles.setText(String.valueOf(listAlta.size()));
    }


    public static boolean actionSalir(Window inWin) {

        Window parent = inWin.getOwner();

        if (parent != null) {
            inWin.setVisible(false);
            inWin.dispose();
            parent.setVisible(true);
        } else {
            System.exit(0);
        }

        return true;
    }


    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        txtAnimalesDisponibles = new javax.swing.JTextField();
        btnVender = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUbicacion_1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPrecioCompra_1 = new javax.swing.JTextField();
        txtPrecioVenta_1 = new javax.swing.JTextField();
        txtPrecioCompra_2 = new javax.swing.JTextField();
        txtPrecioVenta_2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtUbicacion_2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnAlta_1 = new javax.swing.JButton();
        btnAlta_2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Animales Disponibles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        txtAnimalesDisponibles.setEditable(false);
        txtAnimalesDisponibles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAnimalesDisponibles.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAnimalesDisponibles.setMaximumSize(new java.awt.Dimension(100, 30));
        txtAnimalesDisponibles.setMinimumSize(new java.awt.Dimension(100, 30));
        txtAnimalesDisponibles.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        getContentPane().add(txtAnimalesDisponibles, gridBagConstraints);

        btnVender.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnVender.setText("Vender 1");
        btnVender.setMaximumSize(new java.awt.Dimension(100, 30));
        btnVender.setMinimumSize(new java.awt.Dimension(100, 30));
        btnVender.setPreferredSize(new java.awt.Dimension(100, 30));
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 30);
        getContentPane().add(btnVender, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Animal Agregar 1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 30, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Ubicación");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        txtUbicacion_1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtUbicacion_1.setMaximumSize(new java.awt.Dimension(100, 30));
        txtUbicacion_1.setMinimumSize(new java.awt.Dimension(100, 30));
        txtUbicacion_1.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        getContentPane().add(txtUbicacion_1, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Precio de Compra");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 0, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("Precio de Venta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 0, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        txtPrecioCompra_1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPrecioCompra_1.setMaximumSize(new java.awt.Dimension(100, 30));
        txtPrecioCompra_1.setMinimumSize(new java.awt.Dimension(100, 30));
        txtPrecioCompra_1.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        getContentPane().add(txtPrecioCompra_1, gridBagConstraints);

        txtPrecioVenta_1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPrecioVenta_1.setMaximumSize(new java.awt.Dimension(100, 30));
        txtPrecioVenta_1.setMinimumSize(new java.awt.Dimension(100, 30));
        txtPrecioVenta_1.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        getContentPane().add(txtPrecioVenta_1, gridBagConstraints);

        txtPrecioCompra_2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPrecioCompra_2.setMaximumSize(new java.awt.Dimension(100, 30));
        txtPrecioCompra_2.setMinimumSize(new java.awt.Dimension(100, 30));
        txtPrecioCompra_2.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        getContentPane().add(txtPrecioCompra_2, gridBagConstraints);

        txtPrecioVenta_2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPrecioVenta_2.setMaximumSize(new java.awt.Dimension(100, 30));
        txtPrecioVenta_2.setMinimumSize(new java.awt.Dimension(100, 30));
        txtPrecioVenta_2.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        getContentPane().add(txtPrecioVenta_2, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("Animal Agregar 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 30, 0, 0);
        getContentPane().add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setText("Ubicación");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 0, 0);
        getContentPane().add(jLabel7, gridBagConstraints);

        txtUbicacion_2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtUbicacion_2.setMaximumSize(new java.awt.Dimension(100, 30));
        txtUbicacion_2.setMinimumSize(new java.awt.Dimension(100, 30));
        txtUbicacion_2.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        getContentPane().add(txtUbicacion_2, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setText("Precio de Compra");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 0, 0);
        getContentPane().add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("Precio de Venta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 0, 0);
        getContentPane().add(jLabel9, gridBagConstraints);

        btnAlta_1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAlta_1.setText("Alta 1");
        btnAlta_1.setMaximumSize(new java.awt.Dimension(100, 30));
        btnAlta_1.setMinimumSize(new java.awt.Dimension(100, 30));
        btnAlta_1.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAlta_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlta_1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 30, 0, 30);
        getContentPane().add(btnAlta_1, gridBagConstraints);

        btnAlta_2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAlta_2.setText("Alta 2");
        btnAlta_2.setMaximumSize(new java.awt.Dimension(100, 30));
        btnAlta_2.setMinimumSize(new java.awt.Dimension(100, 30));
        btnAlta_2.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAlta_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlta_2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 30, 0, 30);
        getContentPane().add(btnAlta_2, gridBagConstraints);

        setSize(new java.awt.Dimension(510, 430));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenderActionPerformed
        actionVender();
    }//GEN-LAST:event_btnVenderActionPerformed

    private void btnAlta_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlta_1ActionPerformed
        actionAlta_1();
    }//GEN-LAST:event_btnAlta_1ActionPerformed

    private void btnAlta_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlta_2ActionPerformed
        actionAlta_2();
    }//GEN-LAST:event_btnAlta_2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlta_1;
    private javax.swing.JButton btnAlta_2;
    private javax.swing.JButton btnVender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtAnimalesDisponibles;
    private javax.swing.JTextField txtPrecioCompra_1;
    private javax.swing.JTextField txtPrecioCompra_2;
    private javax.swing.JTextField txtPrecioVenta_1;
    private javax.swing.JTextField txtPrecioVenta_2;
    private javax.swing.JTextField txtUbicacion_1;
    private javax.swing.JTextField txtUbicacion_2;
    // End of variables declaration//GEN-END:variables


    private void actionVender() {
        if (listAlta.size() <= 0) { // verificar si hay animales
            return;
        }
        Animal animal = listAlta.remove(0); // eliminar el primer animal de la lista
        listBaja.add(animal); // agregar el animal vendido, a la lista de bajas/vendidos

        btnVender.setEnabled(false);

        btnAlta_1.setEnabled(true);
        txtUbicacion_1.setEnabled(true);
        txtPrecioCompra_1.setEnabled(true);
        txtPrecioVenta_1.setEnabled(true);

        btnAlta_2.setEnabled(false);
        txtUbicacion_2.setEnabled(false);
        txtPrecioCompra_2.setEnabled(false);
        txtPrecioVenta_2.setEnabled(false);

    }


    private void actionAlta_1() {

        String ubicacion = txtUbicacion_1.getText().trim();
        String strPrecioCompra = txtPrecioCompra_1.getText().trim();
        float precioCompra;
        try {
            precioCompra = Float.parseFloat(strPrecioCompra);
        } catch (NumberFormatException inNumberFormatException) {
            txtPrecioCompra_1.requestFocus();
            return;
        }

        String strPrecioVenta = txtPrecioVenta_1.getText().trim();
        float precioVenta;
        try {
            precioVenta = Float.parseFloat(strPrecioVenta);
        } catch (NumberFormatException inNumberFormatException) {
            txtPrecioVenta_1.requestFocus();
            return;
        }

        listAlta.add(new Animal("raza", ubicacion, precioCompra, precioVenta));

        btnAlta_1.setEnabled(false);
        txtUbicacion_1.setEnabled(false);
        txtPrecioCompra_1.setEnabled(false);
        txtPrecioVenta_1.setEnabled(false);

        btnAlta_2.setEnabled(true);
        txtUbicacion_2.setEnabled(true);
        txtPrecioCompra_2.setEnabled(true);
        txtPrecioVenta_2.setEnabled(true);

        txtAnimalesDisponibles.setText(String.valueOf(listAlta.size()));
    }


    private void actionAlta_2() {

        String ubicacion = txtUbicacion_2.getText().trim();
        String strPrecioCompra = txtPrecioCompra_2.getText().trim();
        float precioCompra;
        try {
            precioCompra = Float.parseFloat(strPrecioCompra);
        } catch (NumberFormatException inNumberFormatException) {
            txtPrecioCompra_2.requestFocus();
            return;
        }

        String strPrecioVenta = txtPrecioVenta_2.getText().trim();
        float precioVenta;
        try {
            precioVenta = Float.parseFloat(strPrecioVenta);
        } catch (NumberFormatException inNumberFormatException) {
            txtPrecioVenta_2.requestFocus();
            return;
        }

        listAlta.add(new Animal("raza 2", ubicacion, precioCompra, precioVenta));

        btnVender.setEnabled(true);

        btnAlta_1.setEnabled(false);
        txtUbicacion_1.setEnabled(false);
        txtPrecioCompra_1.setEnabled(false);
        txtPrecioVenta_1.setEnabled(false);

        btnAlta_2.setEnabled(false);
        txtUbicacion_2.setEnabled(false);
        txtPrecioCompra_2.setEnabled(false);
        txtPrecioVenta_2.setEnabled(false);

        txtAnimalesDisponibles.setText(String.valueOf(listAlta.size()));
    }

}
