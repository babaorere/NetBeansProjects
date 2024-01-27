package com.webapp.iii.a.operaciones;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * a) Realice un programa para cada operación que se muestra a continuación, usted podrá elegir usar<br>
 * variables y/o constantes (pueden ser ambas es su elección), puede usar clases distintas es su opción y<br>
 * puede hacer solicitud de datos por teclado es su opción, resuelva esas operaciones, copie y pegue su<br>
 * código completo después de la R/más el resultado que da la operación, además deberá adjuntar los<br>
 * archivos de java que correspondan a lo que programo, se calificará estructura del desarrollo para un<br>
 * salvamento de 3pts por cada una queda a la discreción y valoración del profesor, si el programa no<br>
 * compila o no se ejecuta por errores lógicos o de cualquier otro tipo perderá 4.5 puntos por cada una:<br>
 * valor total 30pts, valor individual 7.5 c/u. NOTA : a la respuesta 1 saque su raíz cuadrada y a las<br>
 * respuestas 2, 3 y 4 saque el redondeo, use la librería matemática vista en clase.<br>
 * 1) 200+360(15*10)^2 + (12/6) = R/<br>
 * 2) 300^8 + 460(18/10) -(120/10 -1*12%) = R/<br>
 * 3) 400+600/20^8 (12*300) + (10*30%/2) = R/<br>
 * 4) 500+100/10^2 (12*20) + (120/2*15%)20^2 R/<br>
 *
 *
 *
 */
public class IIIAOperaciones {

    private interface Operacion {

        public double run() throws Exception;
    }

    private static class OpA implements Operacion {

        // Constructor
        public OpA() {
        }

        @Override
        public double run() throws Exception {

            final double termConst = 12.00d / 6.00d; // una constante, con valores doubles
            boolean valorInvalido;
            String strAux;
            double valor = 0;
            double resultado = 0;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\n\nEcuación:  A + 360x(B*10)^C + (12/6) ");
            System.out.println("Favor indique los valores de A, B y C");

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor -1000 <= A <= 1000");
                System.out.println("A = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > -1000.00) && (valor <= 1000.00)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double A = valor;

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor de 0 < B <= 100");
                System.out.println("B = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > 0) && (valor <= 100)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double B = valor;

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor de 0 < C <= 100");
                System.out.println("C = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > 0) && (valor <= 100)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double C = valor;

            return A + 360.00 * Math.pow((B * 10.00), C) + termConst;
        }
    }

    private static class OpB implements Operacion {

        // Constructor
        public OpB() {
        }

        @Override
        public double run() throws Exception {
            final double termConst = 120.00d / 10.00d; // una constante, con valores doubles
            boolean valorInvalido;
            String strAux;
            double valor = 0;
            double resultado = 0;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\n\nEcuación:  A^8 + 460(B/10) -(120/10 -C*12%) ");
            System.out.println("Favor indique los valores de A, B y C");

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor -1000 <= A <= 1000");
                System.out.println("A = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > -1000.00) && (valor <= 1000.00)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double A = valor;

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor de 0 < B <= 100");
                System.out.println("B = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > 0) && (valor <= 100)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double B = valor;

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor de 0 < C <= 100");
                System.out.println("C = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > 0) && (valor <= 100)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double C = valor;

            return Math.pow(A, 8) + 460.00 * (B / 10.00) - (termConst - C * 0.12);
        }
    }

    private static class OpC implements Operacion {

        // Constructor
        public OpC() {
        }

        @Override
        public double run() throws Exception {
            final double termConst = 10.00 * 0.30 / 2.00; // una constante, con valores doubles
            boolean valorInvalido;
            String strAux;
            double valor = 0;
            double resultado = 0;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\n\nEcuación:  A + 600/B^8 * (C*300) + (10*30%/2) ");
            System.out.println("Favor indique los valores de A, B, C");

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor -1000 <= A <= 1000");
                System.out.println("A = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > -1000.00) && (valor <= 1000.00)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double A = valor;

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor de 0 < B <= 100");
                System.out.println("B = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > 0) && (valor <= 100)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double B = valor;

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor de 0 < C <= 100");
                System.out.println("C = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > 0) && (valor <= 100)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double C = valor;

            return A + 600.00 / Math.pow(B, 8) * (C * 300) + termConst;
        }
    }

    private static class OpD implements Operacion {

        // Constructor
        public OpD() {
        }

        @Override
        public double run() throws Exception {
            final double termConst1 = 12.00 * 20.00; // una constante, con valores doubles
            final double termConst2 = 120.00 / 2.00 * 0.15; // una constante, con valores doubles
            boolean valorInvalido;
            String strAux;
            double valor = 0;
            double resultado = 0;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\n\nEcuación:  A + 100/B^2 (12*20) + (120/2*15%)C^2 ");
            System.out.println("Favor indique los valores de A, B, C");

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor -1000 <= A <= 1000");
                System.out.println("A = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > -1000.00) && (valor <= 1000.00)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double A = valor;

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor de 0 < B <= 100");
                System.out.println("B = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > 0) && (valor <= 100)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double B = valor;

            valor = 0;
            valorInvalido = true;
            while (valorInvalido) {
                System.out.println("Valor de 0 < C <= 100");
                System.out.println("C = ? ");
                strAux = br.readLine();

                try {
                    valor = Double.parseDouble(strAux);
                    if ((valor > 0) && (valor <= 100)) { // validar el rango
                        valorInvalido = false;
                    } else {
                        System.out.println("Valor fuera de rango");
                    }
                } catch (Exception e) {
                    System.out.println("Valor invalido, intente de nuevo");
                    valor = 0;
                    valorInvalido = true;
                }
            }

            double C = valor;
            return A + 100.00 / Math.pow(B, 2) * termConst1 + termConst2 * Math.pow(C, 2);
        }
    }

    public static void main(String[] args) {

        Operacion op = new OpA();

        double resultado;
        try {
            resultado = op.run();

            System.out.println("El resultado del OpA es: " + resultado);
            if (resultado >= 0) {
                System.out.println("La raiz cuadrada del resultado es: " + Math.sqrt(resultado));
            } else {
                System.out.println("Número negativo, no tiene raiz cuadrada");
            }

        } catch (Exception ex) {
            System.out.println("Error al tratr de ejecutar la operación");
        }

        op = new OpB();
        try {
            resultado = op.run();

            System.out.println("El resultado del OpB es: " + resultado);
            System.out.println("Utilizando el Math.round: " + Math.round(resultado));
        } catch (Exception ex) {
            System.out.println("Error al tratr de ejecutar la operación");
        }

        op = new OpC();
        try {
            resultado = op.run();

            System.out.println("El resultado del OpC es: " + resultado);
            System.out.println("Utilizando el Math.round: " + Math.round(resultado));
        } catch (Exception ex) {
            System.out.println("Error al tratr de ejecutar la operación");
        }

        op = new OpD();
        try {
            resultado = op.run();

            System.out.println("El resultado del OpD es: " + resultado);
            System.out.println("Utilizando el Math.round: " + Math.round(resultado));
        } catch (Exception ex) {
            System.out.println("Error al tratar de ejecutar la operación");
        }

        System.out.println("\nFin del programa");
    }
}
