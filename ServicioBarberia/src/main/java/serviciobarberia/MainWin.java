package serviciobarberia;

import java.awt.Window;
import serviciobarberia.vista.BitacoraCreate;
import serviciobarberia.vista.BitacoraVerTabla;
import serviciobarberia.vista.ClienteCreate;
import serviciobarberia.vista.ClienteEliminar;
import serviciobarberia.vista.ClienteModificar;
import serviciobarberia.vista.ServicioCreate;
import serviciobarberia.vista.ServicioEliminar;
import serviciobarberia.vista.ServicioModificar;
import serviciobarberia.vista.UsuarioCreate;
import serviciobarberia.vista.UsuarioEliminar;
import serviciobarberia.vista.UsuarioModificar;

/**
 *
 * @author manager
 */
public class MainWin extends javax.swing.JFrame {

    /**
     * Creates new form MainWin
     */
    public MainWin() {
        initComponents();

        myInitComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuExit = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        mnuConsumirServicio = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mnuClientes = new javax.swing.JMenu();
        mnuClienteCrear = new javax.swing.JMenuItem();
        mnuClienteLeer = new javax.swing.JMenuItem();
        mnuClienteModificar = new javax.swing.JMenuItem();
        mnuClienteEliminar = new javax.swing.JMenuItem();
        mnuServicios = new javax.swing.JMenu();
        mnuServicioCrear = new javax.swing.JMenuItem();
        mnuServicioLeer = new javax.swing.JMenuItem();
        mnuServicioModificar = new javax.swing.JMenuItem();
        mnuServicioEliminar = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        mnuUsuariosCrear = new javax.swing.JMenuItem();
        mnuUsuariosLeer = new javax.swing.JMenuItem();
        mnuUsuariosModificar = new javax.swing.JMenuItem();
        mnuUsuariosEliminar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Servicio de Barberia");

        jMenuBar1.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N

        jMenu2.setText("Archivo");
        jMenu2.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jMenu2.add(jSeparator1);

        mnuExit.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuExit.setText("Salir");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        jMenu2.add(mnuExit);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Servicios");
        jMenu1.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N

        mnuConsumirServicio.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuConsumirServicio.setText("Consumir Servicio");
        mnuConsumirServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsumirServicioActionPerformed(evt);
            }
        });
        jMenu1.add(mnuConsumirServicio);

        jMenuItem1.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jMenuItem1.setText("Bitacoras");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Mantenimiento");
        jMenu3.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N

        mnuClientes.setText("Clientes");
        mnuClientes.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N

        mnuClienteCrear.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuClienteCrear.setText("Crear");
        mnuClienteCrear.setToolTipText("");
        mnuClienteCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClienteCrearActionPerformed(evt);
            }
        });
        mnuClientes.add(mnuClienteCrear);

        mnuClienteLeer.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuClienteLeer.setText("Leer");
        mnuClienteLeer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClienteLeerActionPerformed(evt);
            }
        });
        mnuClientes.add(mnuClienteLeer);

        mnuClienteModificar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuClienteModificar.setText("Modificar");
        mnuClienteModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClienteModificarActionPerformed(evt);
            }
        });
        mnuClientes.add(mnuClienteModificar);

        mnuClienteEliminar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuClienteEliminar.setText("Eliminar");
        mnuClienteEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClienteEliminarActionPerformed(evt);
            }
        });
        mnuClientes.add(mnuClienteEliminar);

        jMenu3.add(mnuClientes);

        mnuServicios.setText("Servicios");
        mnuServicios.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N

        mnuServicioCrear.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuServicioCrear.setText("Crear");
        mnuServicioCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuServicioCrearActionPerformed(evt);
            }
        });
        mnuServicios.add(mnuServicioCrear);

        mnuServicioLeer.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuServicioLeer.setText("Leer");
        mnuServicioLeer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuServicioLeerActionPerformed(evt);
            }
        });
        mnuServicios.add(mnuServicioLeer);

        mnuServicioModificar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuServicioModificar.setText("Modificar");
        mnuServicioModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuServicioModificarActionPerformed(evt);
            }
        });
        mnuServicios.add(mnuServicioModificar);

        mnuServicioEliminar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuServicioEliminar.setText("Eliminar");
        mnuServicioEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuServicioEliminarActionPerformed(evt);
            }
        });
        mnuServicios.add(mnuServicioEliminar);

        jMenu3.add(mnuServicios);

        jMenu6.setText("Usuarios");
        jMenu6.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N

        mnuUsuariosCrear.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuUsuariosCrear.setText("Crear");
        mnuUsuariosCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUsuariosCrearActionPerformed(evt);
            }
        });
        jMenu6.add(mnuUsuariosCrear);

        mnuUsuariosLeer.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuUsuariosLeer.setText("Leer");
        mnuUsuariosLeer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUsuariosLeerActionPerformed(evt);
            }
        });
        jMenu6.add(mnuUsuariosLeer);

        mnuUsuariosModificar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuUsuariosModificar.setText("Modificar");
        mnuUsuariosModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUsuariosModificarActionPerformed(evt);
            }
        });
        jMenu6.add(mnuUsuariosModificar);

        mnuUsuariosEliminar.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        mnuUsuariosEliminar.setText("Eliminar");
        mnuUsuariosEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUsuariosEliminarActionPerformed(evt);
            }
        });
        jMenu6.add(mnuUsuariosEliminar);

        jMenu3.add(jMenu6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 367, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(610, 430));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        ActionExit(this);
    }//GEN-LAST:event_mnuExitActionPerformed

    private void mnuClienteCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClienteCrearActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new ClienteCreate(this)).setVisible(true);
        });
    }//GEN-LAST:event_mnuClienteCrearActionPerformed

    private void mnuClienteLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClienteLeerActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new ClienteModificar(this, false)).setVisible(true);
        });
    }//GEN-LAST:event_mnuClienteLeerActionPerformed

    private void mnuClienteModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClienteModificarActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new ClienteModificar(this, true)).setVisible(true);
        });
    }//GEN-LAST:event_mnuClienteModificarActionPerformed

    private void mnuClienteEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClienteEliminarActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new ClienteEliminar(this)).setVisible(true);
        });
    }//GEN-LAST:event_mnuClienteEliminarActionPerformed

    private void mnuServicioCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuServicioCrearActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new ServicioCreate(this)).setVisible(true);
        });
    }//GEN-LAST:event_mnuServicioCrearActionPerformed

    private void mnuServicioLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuServicioLeerActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new ServicioModificar(this, false)).setVisible(true);
        });
    }//GEN-LAST:event_mnuServicioLeerActionPerformed

    private void mnuServicioModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuServicioModificarActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new ServicioModificar(this, true)).setVisible(true);
        });
    }//GEN-LAST:event_mnuServicioModificarActionPerformed

    private void mnuServicioEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuServicioEliminarActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new ServicioEliminar(this)).setVisible(true);
        });
    }//GEN-LAST:event_mnuServicioEliminarActionPerformed

    private void mnuUsuariosCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUsuariosCrearActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new UsuarioCreate(this)).setVisible(true);
        });
    }//GEN-LAST:event_mnuUsuariosCrearActionPerformed

    private void mnuUsuariosLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUsuariosLeerActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new UsuarioModificar(this, false)).setVisible(true);
        });
    }//GEN-LAST:event_mnuUsuariosLeerActionPerformed

    private void mnuUsuariosModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUsuariosModificarActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new UsuarioModificar(this, true)).setVisible(true);
        });
    }//GEN-LAST:event_mnuUsuariosModificarActionPerformed

    private void mnuUsuariosEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUsuariosEliminarActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new UsuarioEliminar(this)).setVisible(true);
        });
    }//GEN-LAST:event_mnuUsuariosEliminarActionPerformed

    private void mnuConsumirServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsumirServicioActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new BitacoraCreate(this)).setVisible(true);
        });
    }//GEN-LAST:event_mnuConsumirServicioActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            (new BitacoraVerTabla(this)).setVisible(true);
        });
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(MainWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWin().setVisible(true);
            }
        });
    }

    public void myInitComponents() {

        // Esto se utiliza mas adelante, para pasar esta Win como parametro en el "windowClosing"
        final java.awt.Window me = this;

        // Creación del listener, qie sera ejecutado una vez que se quiera cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                java.awt.EventQueue.invokeLater(() -> {
                    ActionExit(me);
                });
            }
        });

    }

    private void ActionExit(final java.awt.Window inWin) {
        inWin.setVisible(false);
        inWin.dispose();

        final Window parent = inWin.getOwner();
        if (parent != null) {
            parent.setVisible(true);
        } else {
            System.exit(0);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem mnuClienteCrear;
    private javax.swing.JMenuItem mnuClienteEliminar;
    private javax.swing.JMenuItem mnuClienteLeer;
    private javax.swing.JMenuItem mnuClienteModificar;
    private javax.swing.JMenu mnuClientes;
    private javax.swing.JMenuItem mnuConsumirServicio;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuServicioCrear;
    private javax.swing.JMenuItem mnuServicioEliminar;
    private javax.swing.JMenuItem mnuServicioLeer;
    private javax.swing.JMenuItem mnuServicioModificar;
    private javax.swing.JMenu mnuServicios;
    private javax.swing.JMenuItem mnuUsuariosCrear;
    private javax.swing.JMenuItem mnuUsuariosEliminar;
    private javax.swing.JMenuItem mnuUsuariosLeer;
    private javax.swing.JMenuItem mnuUsuariosModificar;
    // End of variables declaration//GEN-END:variables
}
