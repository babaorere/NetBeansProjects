package com.app;

import controlador.Controlador;

/**
 *
 */
public final class FrmAppMenu extends javax.swing.JFrame {

    /**
     */
    public FrmAppMenu() {
        super("Despachador de Turnos");
        initComponents();
        MyInitComponents();
    }

    private void MyInitComponents() {

        final java.awt.Window me = this;

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    Controlador.actionSalir(me);
                });
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuDespachador = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jMenu1.setText("Archivo");
        jMenu1.setToolTipText("");
        jMenu1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jMenu1.add(jSeparator1);

        mnuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        mnuSalir.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        mnuSalir.setText("Salir");
        mnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalirActionPerformed(evt);
            }
        });
        jMenu1.add(mnuSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Operaciones");
        jMenu2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jMenu2.add(jSeparator2);

        mnuDespachador.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        mnuDespachador.setText("Despachador");
        mnuDespachador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDespachadorActionPerformed(evt);
            }
        });
        jMenu2.add(mnuDespachador);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 571, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(810, 630));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnuDespachadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDespachadorActionPerformed

        Controlador.actionDespachador(this);

    }//GEN-LAST:event_mnuDespachadorActionPerformed

    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        Controlador.actionSalir(this);
    }//GEN-LAST:event_mnuSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

////        try {
////            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
////                if ("Nimbus".equals(info.getName())) {
////                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
////                    break;
////                }
////            }
////        } catch (Exception ex) {
////            JOptionPane.showMessageDialog(null, "Error al tratar de establecer el Look&Feel\n" + ex);
////        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrmAppMenu().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem mnuDespachador;
    private javax.swing.JMenuItem mnuSalir;
    // End of variables declaration//GEN-END:variables
}
