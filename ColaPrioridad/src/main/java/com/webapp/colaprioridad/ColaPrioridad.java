package com.webapp.colaprioridad;

/**
 *
 * @author manager
 */
public class ColaPrioridad {

    public static void main(String[] args) {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            DlgColaPrioridad dialog = new DlgColaPrioridad(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
}
