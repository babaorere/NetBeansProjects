package com.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class NodePanel extends JPanel {

    private final int numNodes;
    private final ConcurrentHashMap<Integer, Node> mapNodes;
    private final LinkRepo listLink;
    private final Main main;
    private JLabel[] fields;
    private final Color[] nodeColors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA, Color.CYAN};

    int centerX;
    int centerY;
    int radius;

    public NodePanel(Window parent, ConcurrentHashMap<Integer, Node> inMapNodes, LinkRepo inLinkRepo, Main inMain) {
        super(false);
        this.mapNodes = inMapNodes;
        this.listLink = inLinkRepo;
        this.main = inMain;

        this.numNodes = this.mapNodes.size();
        this.fields = new JLabel[numNodes];

        // Crear los fields
        for (ConcurrentHashMap.Entry<Integer, Node> entry : mapNodes.entrySet()) {
            final Integer key = entry.getKey();
            final Node value = entry.getValue();

            fields[key] = new JLabel(String.valueOf(main.getIdNode(value.getId())));
            fields[key].setForeground(Color.WHITE);
            this.add(fields[key]);
        }

    }

    public void setCenter(int inParentW, int inParentH) {

        // Se divide entre 4, porque el ancho del panel, es la mitad del JDialog contenedor
        if (inParentW > 0) {
            centerX = inParentW / 4;
        }

        if (inParentH > 0) {
            centerY = inParentH / 2;
        }

        radius = Math.min(centerX, centerY) - 120;

        for (ConcurrentHashMap.Entry<Integer, Node> entry : mapNodes.entrySet()) {
            final Integer key = entry.getKey();
            final Node value = entry.getValue();

            int x = calX(key);
            int x2;
            if (x < centerX) {
                x2 = x - 65;
            } else {
                x2 = x + 45;
            }

            int y = calY(key);

            fields[key].setBounds(x2, y - 20, 60, 20);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (ConcurrentHashMap.Entry<Integer, Node> entry : mapNodes.entrySet()) {
            final Integer key = entry.getKey();
            final Node value = entry.getValue();

            int x = calX(key);
            int x2;
            if (x < centerX) {
                x2 = x - 65;
            } else {
                x2 = x + 45;
            }

            int y = calY(key);

            g.setColor(nodeColors[key % nodeColors.length]);
            g.fillOval(x, y, 40, 40);

            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(main.getIdNode(key)), x2, y - 10);

        }
    }

    private int calX(int inPos) {
        double angle = 2 * Math.PI * inPos / numNodes;
        return (int) (centerX + radius * Math.cos(angle));
    }

    private int calY(int inPos) {

        double angle = 2 * Math.PI * inPos / numNodes;
        return (int) (centerY + radius * Math.sin(angle));
    }
}
