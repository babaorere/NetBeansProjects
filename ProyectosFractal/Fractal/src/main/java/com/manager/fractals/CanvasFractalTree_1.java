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

import static com.manager.fractals.CanvasFractalModel.getG;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
 *
 * @author manager
 */
public class CanvasFractalTree_1 extends CanvasFractalModel {

    public static int startX; // Coordenada X inicial
    public static int startY; // Coordenada Y inicial
    public static double treeSize; // Tamaño inicial del Arbol 
    public static final int RAND_FACT = 30; // Factor aleatorio para los angulos de las ramas
    public static final int[] CONST_FACT = {-60, 05, -50, 45}; // Factor constante para los angulos sobre las ramas    

    public CanvasFractalTree_1() {
        super();

        startX = 0;
        startY = 0;
        treeSize = 1.78;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     */
    @Override
    public void Draw() {

        Clear();

        if (isClear()) {
            setClear(false);
        } else {
            drawFractal(startX, startY, getInitDeep(), 0, 0);
        }
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @param aG
     */
    @Override
    public void paint(Graphics aG) {

        setG((Graphics2D) aG);

        setOffset(getMAX_DEEP() - getInitDeep());

        startX = getPanel().getWidth() / 2;
        startY = getPanel().getHeight();

        getG().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Filtro antialiasing           

        Draw();
    }

    // NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
    // Metodo recursivo, retorna cuando se halla cumplido el nivel de profundidad
    @Override
    public void drawFractal(int x, int y, int deep, int angle, int tamano) {

        if (deep <= 0) {
            return;
        }

        final int len = (int) Math.round(Math.pow(treeSize, deep - 1 + getOffset())); // Calcular la longitud de la rama

        final int xn1 = (int) Math.round(x - (2 * len * Math.sin(Math.toRadians(angle)))); // longitud y angulo
        final int yn1 = (int) Math.round(y - (2 * len * Math.cos(Math.toRadians(angle)))); // de las ramas
        final int mid1x = (x + xn1) / 2; // Pocision de las ramas
        final int mid1y = (y + yn1) / 2;
        final int mid2x = (mid1x + xn1) / 2;
        final int mid2y = (mid1y + yn1) / 2;
        final int mid3x = (x + mid1x) / 2;
        final int mid3y = (y + mid1y) / 2;
        final int mid4x = (mid3x + mid1x) / 2;
        final int mid4y = (mid3y + mid1y) / 2;

        Color c = new Color(getRED()[(deep + getOffset()) % 10], getGREEN()[(deep + getOffset()) % 10], getBLUE()[(deep + getOffset()) % 10]); // Crea un color a partir del nivel
        getG().setColor(c); // Establece el color
        getG().draw(new Line2D.Double(x, y, xn1, yn1)); // Dibuja una linea
        if (getTpause() > 0) {
            try {
                Thread.sleep(getTpause());
            } catch (InterruptedException ex) {
                // No hacer algo
            }
        }

        // NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
        // Aqui se genera la recursion
        drawFractal(mid1x, mid1y, deep - 1, (angle + getRANDOM().nextInt(RAND_FACT) + CONST_FACT[0]) % 360, 0);

        // NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
        // Aqui se genera la recursion
        drawFractal(mid2x, mid2y, deep - 1, (angle + getRANDOM().nextInt(RAND_FACT) + CONST_FACT[1]) % 360, 0);

        // NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
        // Aqui se genera la recursion
        drawFractal(mid3x, mid3y, deep - 1, (angle + getRANDOM().nextInt(RAND_FACT) + CONST_FACT[2]) % 360, 0);

        // NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
        // Aqui se genera la recursion
        drawFractal(mid4x, mid4y, deep - 1, (angle + getRANDOM().nextInt(RAND_FACT) + CONST_FACT[3]) % 360, 0);
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @param panel
     */
    @Override
    public void Setup(final JPanel panel) {
        super.Setup(panel);

        setTpause(0);
        setInitAngle(0);
        setInitDeep(5);
    }

}
