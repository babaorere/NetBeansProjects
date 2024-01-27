package com.app;

public class PrjExamenPractico {

    public static void main(String[] args) {

        String opcion;
        do {
            opcion = Menu.menuPrincipal();

            switch (opcion) {
                case "1":
                    Monedas.run();
                    break;

                case "2":
                    Polindromo.run();
                    break;

                case "3":
                    Fibonacci.run();
                    break;

            }

        } while (!opcion.equals("4"));

    }
}
