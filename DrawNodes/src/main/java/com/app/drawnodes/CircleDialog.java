package com.app.drawnodes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.SwingUtilities;

public class CircleDialog extends JDialog {

    private int numNodes;
    private String[] nodeNames;
    private JTextField[] textFields;
    private Color[] nodeColors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA, Color.CYAN};

    public CircleDialog(Window parent, String[] nodeNames) {
        super(parent, "");

        this.nodeNames = nodeNames;
        this.numNodes = Math.min(nodeNames.length, 6); // Máximo 6 nodos
        this.textFields = new JTextField[numNodes];
        setTitle("Distance Vector Routing");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // El siguiente codigo es para justar la politica de recorrido de los componentes,
        // al presionar la tecla "TAB"
        setFocusTraversalPolicyProvider(true);
        LayoutFocusTraversalPolicy policy = new LayoutFocusTraversalPolicy();
        policy.setImplicitDownCycleTraversal(true);
        setFocusTraversalPolicy(policy);

        // Crear los JTextField, que seran usados para guardar los valores de los pesos,
        // y posteriormente puedan ser modificados por el usuario
        for (int i = 0; i < numNodes; i++) {
            // Agrega un JTextField debajo del título
            textFields[i] = new JTextField();

            // Establece un ancho de 3 columnas
            textFields[i].setColumns(3);

            textFields[i].setBounds(calX2(i), calY(i) - 20, 60, 20);

            add(textFields[i]);
        }

        repaint();

    }

    private int calX(int inPos) {
        int centerX = getWidth() / 2;
        int radius = centerX - 120;

        double angle = 2 * Math.PI * inPos / numNodes;
        return (int) (centerX + radius * Math.cos(angle));
    }

    private int calX2(int inPos) {
        int centerX = getWidth() / 2;
        int radius = centerX - 120;

        double angle = 2 * Math.PI * inPos / numNodes;
        int x = (int) (centerX + radius * Math.cos(angle));

        if (x < centerX) {
            x -= 65;
        } else {
            x += 45;
        }

        return x;
    }

    private int calY(int inPos) {
        int centerY = getHeight() / 2;
        int radius = centerY - 120;

        double angle = 2 * Math.PI * inPos / numNodes;
        return (int) (centerY + radius * Math.sin(angle));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawCircles(g);
    }

    private void drawCircles(Graphics g) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = centerX - 120;

        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < numNodes; i++) {
            int y = calY(i);

            g.setColor(nodeColors[i % nodeColors.length]);
            g.fillOval(calX(i), y, 40, 40);

            g.setColor(Color.BLACK);
            g.drawString(nodeNames[i], calX2(i), y - 10);

        }
    }

    public static void main(String[] args) {
        String[] nodeNames = {"Node 1", "Node 2", "Node 3", "Node 4", "Node 5", "Node 6"};

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CircleDialog dialog = new CircleDialog(null, nodeNames);
                dialog.setVisible(true);
            }
        });
    }
}
