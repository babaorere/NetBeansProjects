package com.manager.regnumeros;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author manager
 */
public class AppMain {

    private static int PresentarMenu() {

        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            System.out.println("\n\n*****************************************************");
            System.out.println("*****REGISTRO DE NUMEROS - ESTRUCTURA DE DATOS *****");

            System.out.println("\t\t1. Entregar Ficha");
            System.out.println("\t\t2. Digitar números");
            System.out.println("\t\t3. Ver cantidad de números convertidos");
            System.out.println("\t\t4. Mover números al vector");
            System.out.println("\t\t5. Ver números registrados");
            System.out.println("\t\t6. Identificar número binario repetido");
            System.out.println("\t\t7. Limpiar sistema (eliminar números)");
            System.out.println("\t\t8. Salir");

            System.out.println("\nDigite la opción que desee ejecutar: ");
            String opcion;
            try {
                opcion = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                opcion = "";
            }

            System.out.println("OPCION= " + opcion);

            if ((opcion.length() == 1) && ("12345678".contains(opcion))) {
                return Integer.parseInt(opcion);
            } else {
                System.out.println("\n*** OPCION INVALIDA ***");
            }
        }

    }

    private static void EntregarFicha(TCola colaProf) {
        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n*****************************************************");
        System.out.println("*****REGISTRO DE NUMEROS - ESTRUCTURA DE DATOS *****");
        System.out.println("*****  Entregar Ficha  *****");

        String strNomProf;
        while (true) {
            System.out.println("\nNombre Profesor= ? ");
            try {
                strNomProf = reader.readLine().trim().toUpperCase();
            } catch (IOException ex) {
                strNomProf = "";
            }

            if (strNomProf.length() > 0) {
                break;
            } else {
                System.out.println("*** VALOR INVALIDO ***");
            }
        }

        colaProf.encola(new TNodoCola(strNomProf, colaProf.getNextFicha()));
    }

    private static TCola DigitarNumeros(TCola colaProf) {

        if (colaProf.getTam() <= 0) {
            System.out.println("***  LISTA VACIA   ***");
        }

        //Ingrese datos usando BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n\n**************************************");
        System.out.println("*****REGISTRO DE NUMEROS - ESTRUCTURA DE DATOS *****");
        System.out.println("   *****  Digitar Números  *****");

        TCola auxCola = new TCola();

        TNodoCola prof = colaProf.atiende();

        while (prof != null) {

            String strOpcionSN = "S";
            while (strOpcionSN.equals("S")) {
                long numero;
                while (true) {
                    System.out.println("Ficha= " + prof.getFicha());
                    System.out.println("Profesor= " + prof.getNombreProf());
                    System.out.println("Numero= ? ");
                    String strNumero;
                    try {
                        strNumero = reader.readLine().trim().toUpperCase();
                    } catch (IOException ex) {
                        strNumero = "";
                    }

                    try {
                        numero = Long.parseLong(strNumero);
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("*** VALOR INVALIDO ***");
                        numero = -1;
                    }

                    if (numero > 0) {
                        break;
                    }
                }

                // Añadir los datos a la pila del profesor
                prof.addDecBin(new TNodoPila(numero, DecBinRecursivo(numero), prof.getNombreProf()));

                System.out.println("Desea convertir otro numero S/N= ? ");
                try {
                    strOpcionSN = reader.readLine().trim().toUpperCase();
                } catch (IOException ex) {
                    strOpcionSN = "N";
                }
            }

            // Guarda el Profesor atendido
            auxCola.encola(prof);

            // Siguiente profesor
            prof = colaProf.atiende();
        }

        return auxCola;
    }

    public static void main(String[] args) {

        TCola colaProf = new TCola();
        TNodoPila[] vector = null;
        int cant = 0;

        boolean seguir = true;
        while (seguir) {
            switch (PresentarMenu()) {
                case 1:
                    EntregarFicha(colaProf);
                    break;
                case 2:
                    colaProf = DigitarNumeros(colaProf);
                    break;
                case 3:
                    colaProf = CantidadNumConvertidos(colaProf);
                    break;

                case 4:
                    Object[] aux = MoverNumerosVector(colaProf);
                    colaProf = (TCola) aux[0];
                    vector = (TNodoPila[]) aux[1];
                    cant = (int) aux[2];
                    break;

                case 5:
                    MostrarVector(vector, cant);
                    break;

                case 8:
                    seguir = false;
                    break;

                default:
                    System.out.println("*** MENU OPCION INVALIDA ***");
            }
        }

        System.out.println("\n\nPrograma ejecutado con exito");

    }

    /**
     * Funcion recursiva
     *
     * @param n
     * @param sb
     */
    private static void decBin(long n, StringBuilder sb) {
        if (n < 2) {
            sb.append(n);
        } else {
            decBin(n / 2, sb);
            sb.append(n % 2);
        }
    }

    public static String DecBinRecursivo(long numero) {
        StringBuilder sb = new StringBuilder();

        decBin(numero, sb);

        return sb.toString();
    }

    private static TCola CantidadNumConvertidos(TCola colaProf) {

        if (colaProf.getTam() <= 0) {
            System.out.println("***  LISTA VACIA   ***");
        }

        System.out.println("\n\n**************************************");
        System.out.println("*****REGISTRO DE NUMEROS - ESTRUCTURA DE DATOS *****");
        System.out.println("   *****  Cantidad NUmeros Convertidos  *****");

        TCola auxCola = new TCola();

        TNodoCola prof = colaProf.atiende();

        int suma = 0;

        while (prof != null) {

            suma *= prof.getPilaTam();
            auxCola.encola(prof);
            prof = colaProf.atiende();
        }

        System.out.println("\nCantidad Numeros Convertidos = " + suma);

        return auxCola;
    }

    private static Object[] MoverNumerosVector(TCola colaProf) {

        if (colaProf == null) {
            System.out.println("***  ERROR EN PARAMETROS   ***");
        }

        if (colaProf.getTam() <= 0) {
            System.out.println("***  LISTA VACIA   ***");
        }

        System.out.println("\n\n**************************************");
        System.out.println("*****REGISTRO DE NUMEROS - ESTRUCTURA DE DATOS *****");
        System.out.println("   *****  Mover Numeros a Vector  *****");

        TNodoPila[] vec = new TNodoPila[1000];
        int cont = 0;

        TCola auxCola = new TCola();

        // Recorrer todo
        TNodoCola prof = colaProf.atiende();
        while (prof != null) {

            TNodoPila pop = prof.getPop();
            while (pop != null) {
                if (pop.getNumero() % 2 != 0) { // impar
                    vec[cont] = pop;
                    cont++;
                }
                pop = prof.getPop();
            }

            auxCola.encola(prof);
            prof = colaProf.atiende();
        }

        Object[] aux = {auxCola, vec, cont};

        return aux;
    }

    private static void MostrarVector(TNodoPila[] vector, int cant) {
        if (vector == null || cant <= 0) {
            System.out.println("***  ERROR EN PARAMETROS   ***");
        }

        System.out.println("\n\n**************************************");
        System.out.println("*****REGISTRO DE NUMEROS - ESTRUCTURA DE DATOS *****");
        System.out.println("   *****  Ver Numeros Registrados  *****");

        for (int i = 0; i < cant; i++) {
            System.out.println(vector[i].getNumero());
        }

    }

}
