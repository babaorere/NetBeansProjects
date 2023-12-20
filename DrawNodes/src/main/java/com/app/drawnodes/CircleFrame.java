package com.app.drawnodes;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class CircleFrame extends JFrame {

    static String[] nodeNames = {"Node 1", "Node 2", "Node 3", "Node 4", "Node 5", "Node 6"};

    private final JFrame jframe = this;

    public CircleFrame() {
        // Configura tu JFrame y crea un menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        JMenuItem menuItem = new JMenuItem("Abrir Ventana de Diálogo");

        menuItem.addActionListener((ActionEvent e) -> {
            // Crea y muestra la ventana de diálogo cuando se hace clic en el menú
            CircleDialog dialog = new CircleDialog(jframe, nodeNames);
            dialog.setVisible(true);
        });

        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        CircleFrame frame = new CircleFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
