/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package pagos;

import capipsistema.CapipPropiedades;
import capipsistema.UserTrack;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class PagoDirectoSel extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    final java.awt.Window parent;

    /**
     * Creates new form ccompromisos
     *
     * @param _parent
     */
    public PagoDirectoSel(final java.awt.Window _parent) {
        super();
        initComponents();

        parent = _parent;

        setOwnBehavior();
        setCompBehavior();
        setStartConditions();
    }

    /**
     * Para Reubicar la ventana al ser visualizada
     * Rev 25/09/2016
     *
     * @param _b
     */
    @Override
    public void setVisible(boolean _b) {
        // Para mostrar la ventana en el tope de la pantalla
        if (_b) {
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2, 10);
        }

        super.setVisible(_b);
    }

    /**
     * Establece el comportamiento de la presente Ventana
     * Rev 21/09/2016
     *
     */
    public void setOwnBehavior() {
        try {
            UserTrack.trackUser(getClass().getName(), "INIT", getTitle());
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }

        setTitle(CapipPropiedades.CAPIP_SISTEMAS + " - " + getTitle() + " - " + CapipPropiedades.CAPIP_CLIENTE_RAZONSOCIAL);

        // Establecer la acción al cerrar ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                actSalir();
            }

        });

        // Para salir con la tecla ESC
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");

        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actSalir();
            }
        });

    }

    /**
     * Establece el comportamiento de los componentes de la Ventana
     * Rev 21/09/2016
     */
    public void setCompBehavior() {
    }

    /**
     * Rev /10/2016
     */
    private void setStartConditions() {
    }

    private void actSalir() {
        if (parent != null) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    parent.setVisible(true);
                    dispose();
                }
            });
        } else {
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnPagoDirectoNormal = new javax.swing.JButton();
        btnSinImp = new javax.swing.JButton();
        btnSinReg = new javax.swing.JButton();
        btnAvanceEfectivo = new javax.swing.JButton();
        btnImputarPago = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SELECCIÓN DE ORDEN DE PAGO DIRECTA");
        setMinimumSize(new java.awt.Dimension(500, 200));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel1.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SELECCIONE EL TIPO DE ORDEN DE PAGO DIRECTA");
        jLabel1.setAlignmentX(0.5F);
        jPanel2.add(jLabel1);

        jPanel1.setMaximumSize(new java.awt.Dimension(350, 250));
        jPanel1.setMinimumSize(new java.awt.Dimension(350, 250));
        jPanel1.setName(""); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(350, 250));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        btnPagoDirectoNormal.setText("NORMAL");
        btnPagoDirectoNormal.setAlignmentX(0.5F);
        btnPagoDirectoNormal.setMaximumSize(new java.awt.Dimension(180, 30));
        btnPagoDirectoNormal.setMinimumSize(new java.awt.Dimension(180, 30));
        btnPagoDirectoNormal.setPreferredSize(new java.awt.Dimension(180, 30));
        btnPagoDirectoNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagoDirectoNormalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(btnPagoDirectoNormal, gridBagConstraints);

        btnSinImp.setText("SIN IMPUTACION");
        btnSinImp.setAlignmentX(0.5F);
        btnSinImp.setMaximumSize(new java.awt.Dimension(180, 30));
        btnSinImp.setMinimumSize(new java.awt.Dimension(180, 30));
        btnSinImp.setPreferredSize(new java.awt.Dimension(180, 30));
        btnSinImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinImpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(btnSinImp, gridBagConstraints);

        btnSinReg.setText("SIN IMP. Y SIN BCO.");
        btnSinReg.setAlignmentX(0.5F);
        btnSinReg.setMaximumSize(new java.awt.Dimension(180, 30));
        btnSinReg.setMinimumSize(new java.awt.Dimension(180, 30));
        btnSinReg.setPreferredSize(new java.awt.Dimension(180, 30));
        btnSinReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinRegActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(btnSinReg, gridBagConstraints);

        btnAvanceEfectivo.setText("FONDO EN AVANCE");
        btnAvanceEfectivo.setMaximumSize(new java.awt.Dimension(180, 30));
        btnAvanceEfectivo.setMinimumSize(new java.awt.Dimension(180, 30));
        btnAvanceEfectivo.setPreferredSize(new java.awt.Dimension(180, 30));
        btnAvanceEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanceEfectivoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(btnAvanceEfectivo, gridBagConstraints);

        btnImputarPago.setText("IMPUTAR FONDO AVA.");
        btnImputarPago.setMaximumSize(new java.awt.Dimension(180, 30));
        btnImputarPago.setMinimumSize(new java.awt.Dimension(180, 30));
        btnImputarPago.setPreferredSize(new java.awt.Dimension(180, 30));
        btnImputarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImputarPagoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(btnImputarPago, gridBagConstraints);

        jPanel2.add(jPanel1);

        btnSalir.setBackground(new java.awt.Color(153, 153, 0));
        btnSalir.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.setAlignmentX(0.5F);
        btnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnSalir.setMaximumSize(new java.awt.Dimension(100, 40));
        btnSalir.setMinimumSize(new java.awt.Dimension(100, 40));
        btnSalir.setPreferredSize(new java.awt.Dimension(100, 40));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 480, 350));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo azul 22.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 0, 530, 370));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        actSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnPagoDirectoNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagoDirectoNormalActionPerformed
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagoDirectoNormal(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnPagoDirectoNormalActionPerformed

    private void btnSinImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinImpActionPerformed
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagoDirectoSinImputacion(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnSinImpActionPerformed

    private void btnSinRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinRegActionPerformed
        final java.awt.Window me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagoDirectoSinImpSinReg(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnSinRegActionPerformed

    private void btnAvanceEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanceEfectivoActionPerformed
        JFrame me = this;
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FondoAvanceSinImp(me).setVisible(true);
                setVisible(false);
            }
        });

    }//GEN-LAST:event_btnAvanceEfectivoActionPerformed

    private void btnImputarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImputarPagoActionPerformed
        final java.awt.Window me = this;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagoImputacion(me).setVisible(true);
                me.setVisible(false);
            }
        });

    }//GEN-LAST:event_btnImputarPagoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PagoDirectoSel(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvanceEfectivo;
    private javax.swing.JButton btnImputarPago;
    private javax.swing.JButton btnPagoDirectoNormal;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSinImp;
    private javax.swing.JButton btnSinReg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
