package com.app.personaalumno;

/**
 *
 */
public class AppMain {

    public static void main(String[] args) {
        Persona p = new Persona("1234567890", "Hector Manuel", "Estuardo", "Sepulveda", "01/01/1940", 70, 165, 'M', "Santiago");

        System.out.println(p.DatosBasico());

        System.out.println(p.aumentarPeso(5));

        System.out.println(p.disminuyaEstatura(10.00));

        System.out.println(p.PesoGramos());

        System.out.println(p.StrSexo());

        Alumno a = new Alumno("Sistemas", "Ingenieria", "1234567890", "Hector Manuel", "Estuardo", "Sepulveda", "01/01/1940", 70, 165, 'M', "Santiago");

        System.out.println(a.DatosBasico());

        System.out.println(a.aumentarPeso(5));

        System.out.println(a.disminuyaEstatura(10.00));

        System.out.println(a.PesoGramos());

        System.out.println(p.StrSexo());
        System.out.println("Datos Basicos del Alumno: " + a.DatosBasico());
    }
}
