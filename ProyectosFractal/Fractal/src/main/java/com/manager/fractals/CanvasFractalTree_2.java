/*
 */
/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
 * MATRICULA: 20200967
 * CARRERA: TECNOLOGÍAS DE LA INFORMACIÓN
 * CUATRIMESTRE: 4TO
 * GRUPO: C
 */
package com.manager.fractals;

import static com.manager.fractals.CanvasFractalModel.getMAX_DEEP;
import static com.manager.fractals.CanvasFractalModel.setG;
import static com.manager.fractals.CanvasFractalModel.setInitAngle;
import static com.manager.fractals.CanvasFractalModel.setOffset;
import static com.manager.fractals.CanvasFractalModel.setTpause;
import static com.manager.fractals.CanvasFractalTree_1.startX;
import static com.manager.fractals.CanvasFractalTree_1.startY;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import static com.manager.fractals.CanvasFractalModel.getInitDeep;
import static com.manager.fractals.CanvasFractalModel.setInitDeep;

/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
 *
 * @author manager
 */
public final class CanvasFractalTree_2 extends CanvasFractalModel {

    private int xi;
    private int yi;
    private int tamanoi;
    private int anguloi;
    private int profundidadi;

    private double sx = 1.0;
    private double sy = 1.0;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Constructor
     *
     */
    public CanvasFractalTree_2() {
        super();
    }

    @Override
    public void Draw() {

        Clear();

        if (isClear()) {
            setClear(false);
        } else {
            drawFractal(xi, yi, profundidadi, getInitAngle(), tamanoi);
        }

    }

    @Override
    public void paint(Graphics aG) {

        setG((Graphics2D) aG);

        setOffset(getMAX_DEEP() - getInitDeep());

        startX = getPanel().getWidth() / 2;
        startY = getPanel().getHeight();

        xi = getPanel().getWidth() / 2;
        yi = getPanel().getHeight();

        tamanoi = 50;
        anguloi = -90;
        profundidadi = 12;

        getG().setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON); //Filtro antialiasing              

        getG().setColor(Color.black);
        getG().fillRect(0, 0, xi * 2, yi);

        Clear();

        Draw();
    }

    @Override
    public void Setup(final JPanel panel) {
        super.Setup(panel);

        setTpause(0);
        setInitAngle(-90);
        setInitDeep(5);
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Constructor
     *
     * Metodo recursivo
     *
     * @param x
     * @param y
     * @param deep
     * @param angle
     * @param tamano
     */
    @Override
    public void drawFractal(int x, int y, int deep, int angle, int tamano) {
        if (deep <= 0) {
            return;
        }

        getG().setColor(Color.GREEN);

        int x2 = x + (int) (Math.cos(Math.toRadians(angle)) * tamano * sx);
        int y2 = y + (int) (Math.sin(Math.toRadians(angle)) * tamano * sy);
        getG().drawLine(x, y, x2, y2);
        if (getTpause() > 0) {
            try {
                Thread.sleep(getTpause());
            } catch (InterruptedException ex) {
                // No hacer algo
            }
        }

        // NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
        // Aqui se genera la recursion
        drawFractal(x2, y2, deep - 1, angle - 20, (int) (tamano * .9));
        drawFractal(x2, y2, deep - 1, angle + 20, (int) (tamano * .9));
    }

}
