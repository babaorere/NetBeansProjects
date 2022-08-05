package com.app;

import java.util.Scanner;

public class Fibonacci {

    public static void run() {

        int numero;
        boolean marca = true;
        Scanner in = new Scanner(System.in);

        System.out.println("\n\n\n***   Aplicar Fibonacci   ***");

        do {
            System.out.println("\n 5 <= Numero <= 30");
            System.out.println("\nNumero= ? ");

            numero = in.nextInt();

            if ((numero >= 5) && (numero <= 30)) {
                marca = false; // salir del "do while"
            } else {
                System.out.println("\nOpcion invalida, intente de nuevo");
            }

        } while (marca);

        int x1 = 0;
        int x2 = 1;

        System.out.println("\nFibonacci(1)= 0");
        System.out.println("\nFibonacci(2)= 1");

        for (int i = 3; i <= numero; i++) {
            int x3 = x1 + x2;

            System.out.println("\nFibonacci(" + String.valueOf(i) + ")= " + String.valueOf(x3));
            x1 = x2;
            x2 = x3;
        }

    }

    public static void main(String[] args) {

        Fibonacci.run();

    }

}
