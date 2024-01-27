package com.webapp.aprobadosreprobados;

import java.util.Scanner;

/**
 * Programa que lee una cantidad determinada de evaluaciones para alumnos (nalumnos), <br>
 * y al final saca un reporte indicando lacantidad de alumnos aprobados o no, se hacen validaciones de los datos
 */
public class AprobadosReprobados {

    // variables de instancia, que son privadas
    private int nalumnos;
    private int aprobados;
    private int reprobados;

    /**
     * Este es un constructor por defecto, es decir no tiene parametros, <br>
     * se recomienda inicializar las variables en el constructor
     */
    public AprobadosReprobados() {
        nalumnos = 0;
        aprobados = 0;
        reprobados = 0;
    }

    /**
     * Este es un constructor con un unico parametro, en este caso se utiliza para inicializar el <br>
     * numero de alumnos, <br>
     * se recomienda inicializar las variables en el constructor
     */
    public AprobadosReprobados(int inNAlumnos) {
        nalumnos = inNAlumnos;
        aprobados = 0;
        reprobados = 0;
    }

    public void LeerNAlumnos() {

        // Definicion de variables locales, por organizacion se colocan al principio del metodo
        Scanner sc = new Scanner(System.in);
        boolean invalido;

        System.out.println("\n\n\n\n");
        System.out.println("INSTITUTO TEC");
        System.out.println("CALIFICACIONES FINALES DE LOS ESTUDIANTES");
        System.out.println();

        // Verificamos el valor inicializado en el constructor, o posterior 
        if (nalumnos >= 0) {

            invalido = true;
            while (invalido) {
                System.out.print("Introduce un número entero");
                System.out.println("\n¿Cuantos alumnos deseas evaluar? ");

                // leemos la entrada como un string, para luego verificar si es una entrada valida
                String input = sc.nextLine();
                try {
                    this.nalumnos = Integer.parseInt(input);

                    // procedemos a validar si el numero de alumnos esta dentro de un rango                    
                    if (validarNAlumno(nalumnos) == true) {
                        invalido = false; // salimos del ciclo de validacion
                    } else {
                        System.out.println("Error: El valor introducido fuera de rango.");
                    }
                } catch (NumberFormatException e) {
                    this.nalumnos = 0;
                    System.out.println("Error: El valor introducido es inválido.");
                }
            }
        }

    }

    public void LeerCalificacion() {

        aprobados = 0;
        reprobados = 0;

        // Definicion de variables locales, por organizacion se colocan al principio del metodo
        Scanner sc = new Scanner(System.in);
        boolean invalido;
        float calificacion = 0;

        // En este punto hemos validado el dato de entrada
        for (int i = 0; i < nalumnos; i++) {
            System.out.print("Calificacion del alumno No." + (i + 1) + " (1-10): ");

            invalido = true;
            while (invalido) {
                System.out.print("Introduce un número decimal, 1 <= evaluación <= 10");
                System.out.println("\n¿Evaluación ? ");

                // leemos la entrada como un string, para luego verificar si es una entrada valida
                String input = sc.nextLine();
                try {
                    calificacion = Float.parseFloat(input);

                    // procedemos a validar si la calificacion esta dentro de un rango                    
                    if (validarCalificacion(calificacion) == true) {
                        invalido = false; // salimos del ciclo de validacion
                    } else {
                        calificacion = 0;
                        System.out.println("Error: El valor introducido fuera de rango.");
                    }
                } catch (NumberFormatException e) {
                    calificacion = 0;
                    System.out.println("Error: El valor introducido es inválido.");
                }
            }

            // ya hemos validado los datos, asi que procedemos a analizar los datos 
            if ((calificacion >= 0) && (calificacion < 6.50)) { // Entre 1 y 6.5 se pierde
                this.reprobados++;
            } else if ((calificacion >= 6.50) && (calificacion <= 10)) { //A partir de 6.50 se gana
                aprobados++;
            }
        }

    }

    private boolean validarNAlumno(int nalumno) {

        // retornamos false si el numero es negativo, cero, o mayor a 1000, que seria el limite superior
        if ((nalumno <= 0) || (nalumno > 1000)) {
            return false;
        }

        // El numero de alumnos es valido
        return true;
    }

    public boolean validarCalificacion(float calificacion) {
        // retornamos false si el numero es menor a 1, o mayor a 10
        if ((calificacion < 1) || (calificacion > 10)) {
            return false;
        }

        // la calificacion es valida
        return true;
    }

    public void presentarReporte() {
        System.out.println("\n");
        System.out.println("Alumnos reprobados: " + reprobados);
        System.out.println("Alumnos aprobados: " + aprobados);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // creamos la instacia del objeto, se utiliza el contructor sin parametros
        AprobadosReprobados app = new AprobadosReprobados();

        String resp = "1";
        while (resp.equals("1")) {

            app.LeerNAlumnos();
            app.LeerCalificacion();
            app.presentarReporte();

            System.out.println("¿Desea evaluar mas?   1-Si    2-No: ");
            resp = sc.nextLine();

        }

    }

}
