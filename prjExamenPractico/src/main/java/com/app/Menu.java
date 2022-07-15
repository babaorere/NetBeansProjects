package com.app;

import java.util.Scanner;

public class Menu {

    public Menu() {
    }

    public static String menuPrincipal() {

        String opcion;
        boolean marca = true;
        Scanner in = new Scanner(System.in);

        System.out.println("\n\n\n***   Menu de la Aplicacion   ***");

        do {
            System.out.println("\n1.- Monedas");
            System.out.println("\n2.- Polindromo");
            System.out.println("\n3.- Fibonacci");
            System.out.println("\n4.- Salir");
            System.out.println("\nOpcion= ? ");

            opcion = in.nextLine().trim();

            if ((opcion.length() == 1) && "1234".contains(opcion)) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpcion invalida, intente de nuevo");
            }

        } while (marca);

        return opcion;
    }

}
