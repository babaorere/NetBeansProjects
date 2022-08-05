package com.manager.tda_arreglo_5_1;

import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author manager
 */
public class TDA_Arreglo implements Cloneable {

    /**
     * Clase especiamente creada para almacenar datos, que seran manejados por el TDA
     */
    public static class NumComplejo implements Cloneable {

        private static DecimalFormat df = new DecimalFormat("###.##");

        private double re;
        private double im;

        // Constructor con parametros
        public NumComplejo(double _re, double _im) {
            this.re = _re;
            this.im = _im;
        }

        // Copy Constructor
        public NumComplejo(NumComplejo _other) {
            this.re = _other.re;
            this.im = _other.im;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        //métodos setters y getters
        public double getIm() {
            return im;
        }

        public void setIm(double _im) {
            this.im = _im;
        }

        public double getRe() {
            return re;
        }

        public void setRe(double _re) {
            this.re = _re;
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
            return Math.sqrt(re * re + im * im);
        }

        /**
         * Retorna el Modulo o Distancia del numero
         *
         * @return
         */
        public String getStrModulo() {
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

        /**
         * método toString
         */
        @Override
        public String toString() {
            return "(" + df.format(this.re) + ", " + df.format(this.im) + ")";
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
            int hash = 7;
            hash = 89 * hash + (int) (Double.doubleToLongBits(this.re) ^ (Double.doubleToLongBits(this.re) >>> 32));
            hash = 89 * hash + (int) (Double.doubleToLongBits(this.im) ^ (Double.doubleToLongBits(this.im) >>> 32));
            return hash;
        }
    }

    // ************************************************************************
    // ************************************************************************
    // Atributos propios de la clase
    //
    // Aqui se almacenan los datos del vector de datos, array unidimencional
    private NumComplejo[] myVector;

    public TDA_Arreglo() {
        // crea el vector de un tamaño predefinido
        this.myVector = new NumComplejo[128];
    }

    /**
     * Copy constructor
     *
     * @param _myVector
     * @throws java.lang.CloneNotSupportedException
     */
    public TDA_Arreglo(NumComplejo[] _myVector) throws CloneNotSupportedException {
        this.myVector = new NumComplejo[_myVector.length];

        for (int i = 0; i < _myVector.length; i++) {
            this.myVector[i] = (NumComplejo) _myVector[i].clone();
        }
    }

    public TDA_Arreglo(int _size) {
        this.myVector = new NumComplejo[_size];
    }

    /**
     * Realiza una Deep copy del objeto
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new TDA_Arreglo(((TDA_Arreglo) super.clone()).myVector);
    }

    /**
     * Retorna una copia del vector
     *
     * @return the myVector
     * @throws java.lang.CloneNotSupportedException
     */
    public NumComplejo[] getMyVector() throws CloneNotSupportedException {

        if (myVector == null) {
            return null;
        }

        NumComplejo[] otherVector = new NumComplejo[myVector.length];

        for (int i = 0; i < myVector.length; i++) {

            if (myVector[i] != null) {
                otherVector[i] = (NumComplejo) myVector[i].clone();
            } else {
                otherVector[i] = null;
            }
        }
        return otherVector;
    }

    /**
     * Realiza una copia interna del parametro
     *
     * @param _myVector
     * @throws java.lang.CloneNotSupportedException
     */
    public void setMyVector(NumComplejo[] _myVector) throws CloneNotSupportedException {
        this.myVector = new NumComplejo[_myVector.length];

        for (int i = 0; i < myVector.length; i++) {
            this.myVector[i] = (NumComplejo) _myVector[i].clone();
        }
    }

    public NumComplejo getIdx(int idx) throws CloneNotSupportedException {
        return (NumComplejo) (myVector[idx].clone());
    }

    /**
     * Actualizar un numero, en una posicion especifica
     *
     * @param idx
     * @param other
     * @throws java.lang.CloneNotSupportedException
     */
    public void setIdx(int idx, NumComplejo other) throws CloneNotSupportedException {
        if ((idx >= 0) && (idx < myVector.length)) {
            myVector[idx] = (NumComplejo) other.clone();
        }
    }

    public int getTam() {
        return myVector.length;
    }

    /**
     * Llena el vector de numeros complejos aleatorios
     *
     * @param intam
     */
    public void fillRandom(int intam) {

        // maxima cantidad de numeros es de 100
        int cant = intam % 101;

        if (cant <= 0) {
            cant = 100;
        }

        // crea el vector de un tamaño predefinido
        this.myVector = new NumComplejo[cant];

        Random r = new Random();

        for (int i = 0; i < cant; i++) {
            // Añadir el numero complejo de dos digitos enteros y do sdigitos decimales
            this.myVector[i] = new NumComplejo(Math.round(r.nextDouble() * 10000) / 100.00, Math.round(r.nextDouble() * 10000) / 100.00);
        }

    }

}
