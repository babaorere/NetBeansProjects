package com.app;

/**
 *
 * @author manager
 */
public class ValoresIniciales {

    char c;
    byte b;
    short s;
    int i;
    long l;
    float f;
    double d;
    boolean booleano;
    Object o;

    public static void main(String[] args) {
        ValoresIniciales valores = new ValoresIniciales();
        valores.mostrarValoresIniciales();
    }

    private void mostrarValoresIniciales() {
        System.out.println(" Valores iniciales de los tipos primitivos");
        System.out.println("*******************************************");
        System.out.println("char\t" + c);
        System.out.println("byte\t" + b);
        System.out.println("short\t" + s);
        System.out.println("int\t" + i);
        System.out.println("long\t" + l);
        System.out.println("float\t" + f);
        System.out.println("double\t" + d);
        System.out.println("boolean\t " + booleano);

        System.out.println("\nValor inicial de un objeto");
        System.out.println("Object\t" + o);
    }

}
