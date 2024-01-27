package com.manager.fractaarboltest;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class FractalTree1 extends Canvas {

// fields for drawing
    private final JFrame frame;
    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 720;

    public FractalTree1() {
        frame = new JFrame("Fractal Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        Component add = frame.add(this);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        FractalTree1 ft = new FractalTree1();
        ft.setVisible(true);
        ft.setBackground(Color.black);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.green);
        drawFractalTree((Graphics2D) g, WINDOW_WIDTH / 2, WINDOW_HEIGHT - 75, -90, 10, 100, 1.0);
    }

    public void drawFractalTree(Graphics2D g, int x1, int y1, double angle, int depth, double size, double factor) {
        if (depth > 0) {
            int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * size * factor);
            int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * size * factor);

            g.setStroke(new BasicStroke(0.5f * depth));
            g.drawLine(x1, y1, x2, y2);

            drawFractalTree(g, x2, y2, angle + 10, depth - 1, size, 1.0);
            drawFractalTree(g, x2, y2, angle - 35, depth - 1, size, 0.3);
            drawFractalTree(g, x2, y2, angle - 70, depth - 1, size, 1.0);
        }
    }
}
