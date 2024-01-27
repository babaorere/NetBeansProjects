package modelos;

import java.text.DecimalFormat;
import java.util.Objects;


/**
 *
 * @author manager
 *
 * Clase especialmente creada para almacenar datos, que seran manejados por el TDA <br>
 * Se trata de una clase inmutable, una vez creado los valores, no pueden ser modificados, <br>
 * seria necesario crear una nueva instancia
 */
public final class NumComplejo implements Cloneable, Comparable {

    private static DecimalFormat df = new DecimalFormat("###.#");

    private final double re;
    private final double im;
    private final double modulo;

    // Constructor con parametros

    public NumComplejo(double _re, double _im) {
        this.re = _re;
        this.im = _im;
        modulo = CalModulo();
    }

    // Copy Constructor

    public NumComplejo(NumComplejo _other) {
        this.re = _other.re;
        this.im = _other.im;
        modulo = CalModulo();
    }


    @Override
    public String toString() {
        return "(" + df.format(this.re) + ", " + df.format(this.im) + ")= " + df.format(modulo);
    }


    public String strXY() {
        return df.format(this.re) + ", " + df.format(this.im);
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //métodos setters y getters

    public double getIm() {
        return im;
    }


    public double getRe() {
        return re;
    }


    /**
     * Sumar dos números complejos.
     *
     * (a, b) + (c, d) = (a + c, b + d)
     *
     * @param other
     * @return
     */
    public NumComplejo Sumar(NumComplejo other) {
        return new NumComplejo(re + other.re, im + other.im);
    }


    /**
     * Restar dos números complejos
     *
     * (a, b) - (c, d) = (a - c, b - d);
     *
     * @param other
     * @return
     */
    public NumComplejo Restar(NumComplejo other) {
        return new NumComplejo(re - other.re, im - other.im);
    }


    /**
     * multiplicar dos números complejos
     *
     * (a, b) * (c, d) = (a*c – b*d, a*d + b*c)
     *
     * @param other
     * @return
     */
    public NumComplejo Multiplicar(NumComplejo other) {
        return new NumComplejo(re * other.re - im * other.im, re * other.im + im * other.re);
    }


    /**
     * multiplicar un double por un número complejo
     *
     * n x (a, b) = (a * n, b * n)
     *
     * @param doubleN
     * @return
     */
    public NumComplejo Multiplicar(double doubleN) {
        return new NumComplejo(re * doubleN, im * doubleN);
    }


    /**
     * Dividir entre un numero double
     *
     * @param doubleN
     * @return
     * @throws ArithmeticException
     */
    public NumComplejo Dividir(double doubleN) throws ArithmeticException {
        if (doubleN == 0.0) {
            throw new ArithmeticException("Division entre cero");
        }

        return new NumComplejo(this.re / doubleN, this.im / doubleN);
    }


    /**
     * dividir dos números complejos
     *
     * (a, b) / (c, d) = ((a*c + b*d) / (c^2 + d^2) , (b*c – a*d) / (c^2 + d^2))
     *
     * @param other
     * @return
     */
    public NumComplejo Dividir(NumComplejo other) throws ArithmeticException {
        if (other.getModulo() == 0.0) {
            throw new ArithmeticException("Division entre cero");
        }

        return new NumComplejo((re * other.re + im * other.im) / (other.re * other.re + other.im * other.im),
            (im * other.re - re * other.im) / (other.re * other.re + other.im * other.im));
    }


    /**
     * Retorna el Conjugado del Numero
     *
     * @return
     */
    public NumComplejo Conjugado() {
        return new NumComplejo(this.re, -this.im);
    }


    /**
     * Retorna el opuesto
     *
     * @return
     */
    public NumComplejo Opuesto() {
        return new NumComplejo(-this.re, -this.im);
    }


    /**
     * Retorna el Modulo o Distancia del numero
     *
     * @return
     */
    public double getModulo() {
        return modulo;
    }


    /**
     * Retorna el Modulo o Distancia del numero
     *
     * @return
     */
    private double CalModulo() {
        return Math.sqrt(re * re + im * im);
    }


    /**
     * Retorna el Modulo o Distancia del numero
     *
     * @return
     */
    private String getStrModulo() {
        return df.format(getModulo());
    }


    /**
     * Devuelve el ángulo en grados entre -PI y +PI
     *
     * @return
     */
    public double getGrados() {
        double angulo = Math.atan2(im, re);
        if (angulo < 0) {
            angulo = 2 * Math.PI + angulo;
        }
        return angulo * 180 / Math.PI;
    }


    /**
     * Devuelve el ángulo en grados entre -PI y +PI
     *
     * @return
     */
    public String getStrGrados() {
        return df.format(getGrados());
    }


    //método equals
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final NumComplejo other = (NumComplejo) obj;

        return (this.re == other.re) && (this.im == other.im);
    }


    @Override
    public int hashCode() {

        return Objects.hash(re, im);
    }


    @Override
    public int compareTo(Object inOther) {
        return Double.compare(modulo, ((NumComplejo) inOther).modulo);
    }
}
