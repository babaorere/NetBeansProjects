/**
 *
 *
 *
 *
 */
/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
 * MATRICULA: 20200967
 * CARRERA: TECNOLOGÍAS DE LA INFORMACIÓN
 * CUATRIMESTRE: 4TO
 * GRUPO: C
 */
package com.manager.fractals;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Clase modelo, a partir de la cual se deben heredar todos los Fractal
 *
 * @author manager
 */
public abstract class CanvasFractalModel extends Canvas implements IFractal {

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Centinela para saber si se esta en proceso de limpieza del canvas
     */
    private boolean clear;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Tiempo maximo en pausa, para controlar la velocidad de ejecucion
     *
     */
    private final static int MAX_SLEEP = 100;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Maxima profundidad del Arbol
     */
    private final static int MAX_DEEP = 10;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Mantiene el panel donde esta anclado este componente
     */
    private JPanel panel;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Representa el ambiente grafico del Panel
     */
    private static Graphics2D g;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Establece la velocidad de ejecucion
     */
    private static int tpause;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Establece la velocidad de ejecucion
     */
    private static int initAngle;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Establece la cantidad de niveles del fractal
     */
    private static int initDeep;

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Utilizado para mantener constante el escalamiento del fractal
     */
    private static int offset;

    private static final java.util.Random RANDOM = new java.util.Random(); // Generador de numeros aleatorios

    // NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
    // Vectores para generar los colores segun el nivel
    private static int[] RED = {50, 75, 100, 125, 150, 175, 180, 205, 230, 255};
    private static int[] GREEN = {255, 245, 235, 225, 215, 205, 195, 185, 175, 165};
    private static int[] BLUE = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Constructor base padre, para establecer valores iniciales
     */
    public CanvasFractalModel() {
        clear = false;
        tpause = 0;
        initAngle = 0;
        initDeep = 5;
        offset = 0;
        panel = null;
        g = null;
    }

    @Override
    public void Setup(final JPanel panel) {
        this.setPanel(panel);
    }

    @Override
    public void Clear() {
        setBackground(Color.BLACK);
        getG().setColor(Color.BLACK);
        getG().fillRect(0, 0, getPanel().getWidth(), getPanel().getHeight());
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the MAX_SLEEP
     */
    public static int getMAX_SLEEP() {
        return MAX_SLEEP;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the MAX_DEEP
     */
    public static int getMAX_DEEP() {
        return MAX_DEEP;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @param panel the panel to set
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the g
     */
    public static Graphics2D getG() {
        return g;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @param aG the g to set
     */
    public static void setG(Graphics2D aG) {
        g = aG;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the tpause
     */
    public static int getTpause() {
        return tpause;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Validar y establece el tiempo de pausa entre la reproduccion de cada parte del fractal
     *
     * @param aTpause the tpause to set
     */
    public static void setTpause(int aTpause) {
        if (aTpause < 0) {
            tpause = 0;
        } else if (aTpause > MAX_SLEEP) {
            tpause = MAX_SLEEP;
        } else {
            tpause = aTpause;
        }
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the initLevel
     */
    public static int getInitDeep() {
        return initDeep;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Validar y establecer el initLevel
     *
     * @param aInitLevel the initLevel to set
     */
    public static void setInitDeep(int aInitLevel) {
        if (aInitLevel <= 0) {
            initDeep = 1;
        } else if (aInitLevel > MAX_DEEP) {
            initDeep = MAX_DEEP;
        } else {
            initDeep = aInitLevel;
        }
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the offset
     */
    public static int getOffset() {
        return offset;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Validar y establecer el offset
     *
     * @param aOffset the offset to set
     */
    public static void setOffset(int aOffset) {
        if (aOffset < 0) {
            offset = 0;
        } else if (aOffset > (MAX_DEEP - 1)) {
            offset = MAX_DEEP - 1;
        } else {
            offset = aOffset;
        }
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the RANDOM
     */
    public static java.util.Random getRANDOM() {
        return RANDOM;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the RED
     */
    public static int[] getRED() {
        return RED;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the GREEN
     */
    public static int[] getGREEN() {
        return GREEN;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the BLUE
     */
    public static int[] getBLUE() {
        return BLUE;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @return the clear
     */
    public boolean isClear() {
        return clear;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * @param clear the clear to set
     */
    public void setClear(boolean clear) {
        this.clear = clear;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Disminuye la velocidad de ejecucion
     */
    @Override
    public void SetVelMenos() {
        // A mayor tpause, menor velocidad
        setTpause(getTpause() + 5);
        repaint();
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Aumenta la velocidad de ejecucion
     */
    @Override
    public void SetVelMas() {
        // A menor tpause, mayor velocidad
        setTpause(getTpause() - 5);
        repaint();
    }

    @Override
    public void SetAngMenos() {
        setInitAngle(getInitAngle() - 5);
        repaint();
    }

    @Override
    public void SetAngMas() {
        setInitAngle(getInitAngle() + 5);
        repaint();
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Disminuye la profundidad del Fractal
     */
    @Override
    public void SetDeepMenos() {
        setInitDeep(getInitDeep() - 1);
        repaint();
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Aumenta la profundidad del Fractal
     */
    @Override
    public void SetDeepMas() {
        setInitDeep(getInitDeep() + 1);
        repaint();
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Retornar el angulo inicial del fractal
     *
     * @return the initAngle
     */
    public static int getInitAngle() {
        return initAngle;
    }

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ Validad y establecer el angulo inicial del fractal
     *
     * @param aInitAngle the initAngle to set
     */
    public static void setInitAngle(int aInitAngle) {
        initAngle = aInitAngle % 360;
    }

}
