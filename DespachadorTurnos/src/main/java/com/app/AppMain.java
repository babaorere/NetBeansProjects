package com.app;

/**
 *
 */
public class AppMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "Error al tratar de establecer el Look&Feel\n" + ex);
//        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrmAppMenu().setVisible(true);
        });
    }

}
