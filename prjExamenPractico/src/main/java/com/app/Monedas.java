package com.app;

import java.util.Scanner;

public class Monedas {

    public static void run() {

        int salario;
        boolean marca = true;
        Scanner in = new Scanner(System.in);

        System.out.println("\n\n\n***   Monedas   ***");

        do {
            do {
                System.out.println("\n\nSalarios > 0");
                System.out.println("\nSalarios = 0, para terminar");
                System.out.println("\nSalario= ? ");

                salario = in.nextInt();

                if (salario >= 0) {
                    marca = false; // salir del "do while"
                } else {
                    System.out.println("\nOpcion invalida, intente de nuevo");
                }

            } while (marca);

            if (salario > 0) {

                int entero;
                int resto;

                entero = salario / 20000;
                resto = salario % 20000;

                if (entero > 0) {
                    System.out.println("\nBilletes 20000: " + entero);
                }

                entero = resto / 10000;
                resto = resto % 10000;

                if (entero > 0) {
                    System.out.println("\nBilletes 10000: " + entero);
                }

                entero = resto / 5000;
                resto = resto % 5000;

                if (entero > 0) {
                    System.out.println("\nBilletes 5000: " + entero);
                }

                entero = resto / 500;
                resto = resto % 500;

                if (entero > 0) {
                    System.out.println("\nMonedas 500: " + entero);
                }

                entero = resto / 100;
                resto = resto % 100;

                if (entero > 0) {
                    System.out.println("\nMonedas 100: " + entero);
                }

                entero = resto / 50;
                resto = resto % 50;

                if (entero > 0) {
                    System.out.println("\nMonedas 50: " + entero);
                }

                entero = resto / 25;
                resto = resto % 25;

                if (entero > 0) {
                    System.out.println("\nMonedas 25: " + entero);
                }

                entero = resto / 10;
                resto = resto % 10;

                if (entero > 0) {
                    System.out.println("\nMonedas 10: " + entero);
                }

                entero = resto / 5;
                resto = resto % 5;

                if (entero > 0) {
                    System.out.println("\nMonedas 5: " + entero);
                }

                System.out.println("\nResto: " + resto);

            }

        } while (salario > 0);

    }

    public static void main(String[] args) {

        Monedas.run();

    }

}
