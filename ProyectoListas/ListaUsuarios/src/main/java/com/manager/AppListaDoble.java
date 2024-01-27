package com.manager;

/**
 *
 * @author manager
 */
public class AppListaDoble {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                (new frmAppMenu()).setVisible(true);
            }
        });
    }
}
