package com.mvdir.flowchart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class FlowchartSymbols {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new FlowchartPanel2());
        frame.setVisible(true);
    }
}

class FlowchartPanel2 extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar el símbolo "Inicio"
        g2d.drawOval(50, 50, 50, 50);
        g2d.drawString("Inicio", 75, 115);

        // Dibujar el símbolo "Fin"
        g2d.drawOval(250, 50, 50, 50);
        g2d.drawString("Fin", 275, 115);

        // Dibujar el símbolo "Proceso"
        g2d.drawRect(50, 150, 100, 50);
        g2d.drawString("Proceso", 90, 185);

        // Dibujar el símbolo "Entrada"
        g2d.draw(new Rectangle2D.Double(250, 150, 100, 50));
        g2d.drawString("Entrada", 275, 185);

        // Dibujar el símbolo "Salida"
        g2d.draw(new Rectangle2D.Double(50, 250, 100, 50));
        g2d.drawString("Salida", 75, 285);

        // Dibujar el símbolo "Comentario"
        g2d.drawRect(250, 250, 100, 50);
        g2d.drawString("// Comentario", 255, 280);

        // Dibujar el símbolo "If"
        Polygon polygonIf = new Polygon();
        polygonIf.addPoint(200, 350);
        polygonIf.addPoint(250, 350);
        polygonIf.addPoint(225, 400);
        g2d.drawPolygon(polygonIf);
        g2d.drawString("If", 225, 415);

        // Dibujar el símbolo "While"
        Polygon polygonWhile = new Polygon();
        polygonWhile.addPoint(50, 350);
        polygonWhile.addPoint(100, 350);
        polygonWhile.addPoint(75, 400);
        g2d.drawPolygon(polygonWhile);
        g2d.drawString("While", 70, 415);

        // Dibujar el símbolo "Do While"
        Polygon polygonDoWhile = new Polygon();
        polygonDoWhile.addPoint(350, 350);
        polygonDoWhile.addPoint(400, 350);
        polygonDoWhile.addPoint(375, 400);
        g2d.drawPolygon(polygonDoWhile);
        g2d.drawString("Do While", 355, 415);
    }
}
