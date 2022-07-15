package com.app;

import java.util.Scanner;

public class Polindromo {

    public static void run() {

        String palabra;
        boolean marca = true;
        Scanner in = new Scanner(System.in);

        System.out.println("\n\n\n***   Polindromo   ***");

        do {
            System.out.println("\nPalabra logitud > 0");
            System.out.println("\nPalabra= ? ");

            palabra = in.nextLine().trim().toUpperCase();

            if (palabra.length() > 0) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpcion invalida, intente de nuevo");
            }

        } while (marca);

        int len = palabra.length();
        marca = true;
        for (int i = 0; i < len / 2; i++) {
            if (palabra.charAt(i) != palabra.charAt(len - 1 - i)) {
                marca = false;
                break;
            }
        }

        if (marca) {
            System.out.println("\nSi es Polindroma");
        } else {
            System.out.println("\nNO es Polindroma");
        }

    }

    public static void main(String[] args) {

        Polindromo.run();

    }

}
