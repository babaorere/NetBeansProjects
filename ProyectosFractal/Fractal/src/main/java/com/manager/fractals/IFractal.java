/*
 * Se trata de mantener un unico contrato para la manipulacion de los Fractales,
 * precisamente con una forma procedimental comun, lo que beneficia en mantener invariable el programa principal
 * a pesar de hacer cambios al Fractal en cuestion, añadir o eliminar modelos de fractales
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

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
 *
 * @author manager
 */
public interface IFractal {

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * Establece las condiciones iniciales del fractal
     *
     * @param panel
     */
    public void Setup(final JPanel panel);

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * Limpia la zona grafica
     */
    public void Clear();

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * Aqui se activa la recursividad, en las clases que lo implementen
     */
    public void Draw();

    // NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
    // Metodo recursivo, retorna cuando se halla cumplido el nivel de profundidad
    public void drawFractal(int x, int y, int deep, int angle, int tamano);

    public void paint(Graphics g);

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * Disminuye la velocidad de ejecucion
     */
    public void SetVelMenos();

    public void SetVelMas();

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * Disminuye el angulo inicial del fractal
     */
    public void SetAngMenos();

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * Aumenta el angulo inicial del Fractal
     */
    public void SetAngMas();

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * Disminuye la profundidad del Fractal
     */
    public void SetDeepMenos();

    /**
     * NOMBRE: KEVIN FRANCISCO MINA MARTÍNEZ
     *
     * Aumenta la profundidad del Fractal
     */
    public void SetDeepMas();
}
