package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class IIIBEmpleados {

    public static void main(String[] args) {

        final int SALARIO_MINIMO = 325000;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n\nInicio de programa");

        String strAux;
        int intAux;
        boolean valorInvalido;
        boolean hayMasEmpleados = true;

        while (hayMasEmpleados) {

            System.out.println("\nProceda a introducir los datos del Empleado");

            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nNombre= ? ");
                    strAux = br.readLine().trim(); // sin blancos a los lados
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                if (strAux.length() > 0) {
                    valorInvalido = false; // salir del ciclo
                } else {
                    System.out.println("Valor invalido, intente de nuevo");
                }
            }
            String nombre = strAux;

            intAux = 0;
            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nEdad= ? ");
                    strAux = br.readLine().trim(); // sin blancos a los lados
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                try {
                    intAux = Integer.parseInt(strAux);

                    if (intAux > 18) {
                        valorInvalido = false; // salir del ciclo
                    } else {
                        System.out.println("Valor invalido, intente de nuevo");
                    }

                } catch (Exception e) {
                    intAux = 0;
                    System.out.println("Error al tratar de leer de la consola");
                }

            }
            int edad = intAux;

            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nPuesto= ? ");
                    strAux = br.readLine().trim(); // sin blancos a los lados
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                if (strAux.length() > 0) {
                    valorInvalido = false; // salir del ciclo
                } else {
                    System.out.println("Valor invalido, intente de nuevo");
                }
            }
            String puesto = strAux;

            intAux = 0;
            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nSalario= ? ");
                    strAux = br.readLine().trim(); // sin blancos a los lados
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                try {
                    intAux = Integer.parseInt(strAux);

                    if (intAux >= SALARIO_MINIMO) {
                        valorInvalido = false; // salir del ciclo
                    } else {
                        System.out.println("Valor invalido, intente de nuevo");
                    }

                } catch (Exception e) {
                    intAux = 0;
                    System.out.println("Error al tratar de leer de la consola");
                }
            }
            int salario = intAux;

            intAux = 0;
            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nAños laborando= ? ");
                    strAux = br.readLine().trim(); // sin blancos a los lados
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                try {
                    intAux = Integer.parseInt(strAux);

                    if (intAux >= 0 && intAux <= 65) {
                        valorInvalido = false; // salir del ciclo
                    } else {
                        System.out.println("Valor invalido, intente de nuevo");
                    }

                } catch (Exception e) {
                    intAux = 0;
                    System.out.println("Error al tratar de leer de la consola");
                }
            }
            int aniosLabor = intAux;

            intAux = 0;
            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nAños en el gremio= ? ");
                    strAux = br.readLine().trim(); // sin blancos a los lados
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                try {
                    intAux = Integer.parseInt(strAux);

                    if (intAux > 0 && intAux <= 65) {
                        valorInvalido = false; // salir del ciclo
                    } else {
                        System.out.println("Valor invalido, intente de nuevo");
                    }

                } catch (Exception e) {
                    intAux = 0;
                    System.out.println("Error al tratar de leer de la consola");
                }
            }
            int aniosGremio = intAux;

            intAux = 0;
            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nIVM= ? ");
                    strAux = br.readLine().trim(); // sin blancos a los lados
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                try {
                    intAux = Integer.parseInt(strAux);

                    if (intAux >= 0 && intAux <= 1000) { // validar
                        valorInvalido = false; // salir del ciclo
                    } else {
                        System.out.println("Valor invalido, intente de nuevo");
                    }

                } catch (Exception e) {
                    intAux = 0;
                    System.out.println("Error al tratar de leer de la consola");
                }
            }
            int ivm = intAux;

            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nSexo [M/H]= ? ");
                    strAux = br.readLine().trim().toUpperCase(); // sin blancos a los lados, y en mayuscula
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                if (strAux.length() == 1) {
                    if (strAux.equals("M") || strAux.equals("H")) {
                        valorInvalido = false; // salir del ciclo                        
                    } else {
                        System.out.println("Valor invalido, intente de nuevo");
                    }
                } else {
                    System.out.println("Valor invalido, intente de nuevo");
                }
            }
            String sexo = strAux;

            // *********************
            // Calculo de Vacaciones
            int diasVac;
            if (aniosLabor <= 0) {
                System.out.println("\nNo tiene derecho a vacaciones, años laborando: " + aniosLabor);
            } else {

                if (aniosLabor >= 1 && aniosLabor < 5) {
                    diasVac = 12;
                } else if (aniosLabor >= 5 && aniosLabor < 10) {
                    diasVac = 24;

                } else if (aniosLabor >= 10 && aniosLabor < 20) {
                    diasVac = 30;
                } else {
                    diasVac = 30;
                }

                System.out.println("\nTiene derecho a " + diasVac + " dias de vacaciones");

            }

            // *********************
            // Calculo de Prestamos
            if (aniosGremio <= 0) {
                System.out.println("No tiene derecho a prestamos del Gremio");
            } else {
                final int cantMin = 1;
                final int montoMin = cantMin * SALARIO_MINIMO;
                int montoMax;
                int cantMax;

                if (aniosGremio >= 1 && aniosGremio <= 3) {
                    cantMax = 3;
                } else if (aniosGremio >= 4 && aniosGremio <= 10) {
                    cantMax = 8;
                } else if (aniosGremio >= 11 && aniosGremio <= 25) {
                    cantMax = 20;
                } else {
                    cantMax = 20;
                }

                montoMax = cantMax * SALARIO_MINIMO;

                int n3 = 12 * 3;
                int n5 = 12 * 5;

                double tasaMes_3a = Math.pow((1.00d + 0.20d), (1.00d / 12.00d)) - 1.00d;
                double tasaMes_5a = Math.pow((1.00d + 0.25d), (1.00d / 12.00d)) - 1.00d;

                double minCuotaMes_3 = (montoMin * tasaMes_3a * Math.pow(1 + tasaMes_3a, n3)) / (Math.pow(1 + tasaMes_3a, n3) - 1);
                double maxCuotaMes_3 = (montoMax * tasaMes_3a * Math.pow(1 + tasaMes_3a, n3)) / (Math.pow(1 + tasaMes_3a, n3) - 1);

                double minCuotaMes_5 = (montoMin * tasaMes_5a * Math.pow(1 + tasaMes_5a, n5)) / (Math.pow(1 + tasaMes_5a, n5) - 1);
                double maxCuotaMes_5 = (montoMax * tasaMes_5a * Math.pow(1 + tasaMes_5a, n5)) / (Math.pow(1 + tasaMes_5a, n5) - 1);

                System.out.println("\nPrestamo de 1 salario minimo a 3 años: " + minCuotaMes_3);
                System.out.println("Prestamo de " + cantMax + " salario minimo a 3 años: " + minCuotaMes_3);

                System.out.println("Prestamo de 1 salario minimo a 5 años: " + minCuotaMes_5);
                System.out.println("Prestamo de " + cantMax + " salario minimo a 5 años: " + minCuotaMes_5);

                if (salario < minCuotaMes_3) {
                    System.out.println("\nEl empleado no tiene capacidad de cubrir la cuata minima mensual");
                    System.out.println("Prestamo de 1 salario minimo a 3 años");
                } else {
                    int iAux = 0;
                    double cuotaAux = 0.00;
                    for (int i = 1; i <= cantMax; i++) {
                        double cuotaMes = (i * SALARIO_MINIMO * tasaMes_3a * Math.pow(1 + tasaMes_3a, n3)) / (Math.pow(1 + tasaMes_3a, n3) - 1);
                        if (salario <= cuotaMes) {
                            iAux = i;
                            cuotaAux = cuotaMes;
                        } else {
                            break;
                        }
                    }

                    System.out.println("El monto maximo prestamo a 3 años");
                    System.out.println("Salarios minimo: " + iAux);
                    System.out.println("Monto prestamo: " + (iAux * SALARIO_MINIMO));
                    System.out.println("Monto cuota mensual: " + cuotaAux);

                }

                if (salario < minCuotaMes_5) {
                    System.out.println("\nEl empleado no tiene capacidad de cubrir la cuata minima mensual");
                    System.out.println("Prestamo de 1 salario minimo a 5 años");
                } else {
                    int iAux = 0;
                    double cuotaAux = 0.00;
                    for (int i = 1; i <= cantMax; i++) {
                        double cuotaMes = (i * SALARIO_MINIMO * tasaMes_5a * Math.pow(1 + tasaMes_5a, n5)) / (Math.pow(1 + tasaMes_5a, n5) - 1);
                        if (salario <= cuotaMes) {
                            iAux = i;
                            cuotaAux = cuotaMes;
                        } else {
                            break;
                        }
                    }

                    System.out.println("El monto maximo prestamo a 5 años");
                    System.out.println("Salarios minimo: " + iAux);
                    System.out.println("Monto prestamo: " + (iAux * SALARIO_MINIMO));
                    System.out.println("Monto cuota mensual: " + cuotaAux);

                }

                System.out.println("\nMontos de las cuotas segun monto del credito");
                System.out.println("Plazo de 3 años");
                for (int i = 1; i <= cantMax; i++) {
                    double monto = SALARIO_MINIMO * i;
                    double cuotaMes = Math.round((monto * tasaMes_3a * Math.pow(1 + tasaMes_3a, n3)) / (Math.pow(1 + tasaMes_3a, n3) - 1) * 100) / 100; // redondear a dos digitos
                    double montoFinal = Math.round(cuotaMes * n3 * 100) / 100; // redondear a dos digitos
                    System.out.println("\nPrestamo de " + i + " salario minimo, equivalente a: " + monto);
                    System.out.println("Cuota mensual= " + cuotaMes + ", plazo: " + n3 + " meses, monto final" + montoFinal);
                }

                System.out.println("\nMontos de las cuotas segun monto del credito");
                System.out.println("Plazo de 5 años");
                for (int i = 1; i <= cantMax; i++) {
                    double monto = SALARIO_MINIMO * i;
                    double cuotaMes = Math.round((monto * tasaMes_5a * Math.pow(1 + tasaMes_5a, n5)) / (Math.pow(1 + tasaMes_5a, n5) - 1) * 100.00) / 100 - 00;
                    double montoFinal = Math.round(cuotaMes * n5 * 100) / 100; // redondear a dos digitos
                    System.out.println("\nPrestamo de " + i + " salario minimo, equivalente a: " + monto);
                    System.out.println("Cuota mensual= " + cuotaMes + ", plazo: " + n5 + " meses, monto final" + montoFinal);
                }

            }

            // *********************
            // Calculo de Pension
            if (sexo.equals("M")) { // Mujeres

                if (edad >= 65) {
                    if (ivm >= 450) {
                        System.out.println("Puede optar a la pensión");
                    } else {
                        System.out.println("Le falta " + (450 - ivm) + " IVMs para optar a la pensión");
                    }
                }

            } else { // Hombres

                if (edad >= 65) {
                    if (ivm >= 420) {
                        System.out.println("Puede optar a la pensión");
                    } else {
                        System.out.println("Le falta " + (462 - ivm) + " IVMs para optar a la pensión");
                    }
                } else if (edad >= 62) {
                    if (ivm >= 462) {
                        System.out.println("Puede optar a la pensión anticipada");
                    } else {
                        System.out.println("Le falta " + (462 - ivm) + " IVMs para optar a la pensión anticipada");
                    }
                } else {
                    System.out.println("No tiene edad suficiente para optar a la pensión");
                }

            }

            strAux = "";
            valorInvalido = true;
            while (valorInvalido) {

                try {
                    System.out.println("\nDesea continuar con otro Empleado [S/N]= ? ");
                    strAux = br.readLine().trim().toUpperCase(); // sin blancos a los lados, en mayuscula
                } catch (IOException ex) {
                    strAux = "";
                    System.out.println("Error al tratar de leer de la consola");
                }

                if (strAux.length() == 1) {
                    if (strAux.equals("S") || strAux.equals("N")) {
                        valorInvalido = false; // salir del ciclo
                    } else {
                        System.out.println("Valor invalido, intente de nuevo");
                    }
                } else {
                    System.out.println("Valor invalido, intente de nuevo");
                }
            }
            String opcion = strAux;

            hayMasEmpleados = opcion.equals("S");

        }
    }

}
