package com.manager.ordintercambio;

import java.util.Random;

/**
 *
 * @author Kevin
 */
public class AppMainOrdIntercambio {

    private final int MAXIDX = 20_000;
    private final Random r = new Random();

    private double GetDouble() {
        return (double) r.nextInt(200_000);
    }

    // Llenar un vector con aleatorios
    public void llenarVecRam(double[] vec, int n) {

        // Validar
        if ((n <= 0) || (n > vec.length)) {
            n = vec.length;
        }

        for (int i = 0; i < n; i++) {
            vec[i] = GetDouble();
        }
    }

    // Ordena de menor a mayor
    public void AlgIntercambio(double[] vec, int n) {
        if ((n <= 0) || (n > vec.length)) {
            n = vec.length;
        }

        int i;
        int j;

        for (i = 0; i <= n - 2; i++) {
            for (j = i + 1; j <= n - 1; j++) {
                if (vec[i] > vec[j]) {
                    double aux;
                    aux = vec[i];
                    vec[i] = vec[j];
                    vec[j] = aux;
                }
            }
        }
    }

    // Punto de entrada de la aplicacion
    public static void main(String[] args) {

        final int NMAX = 20_000;
        final int NCORRIDAS = 25;

        long[] vecEstInter = new long[NCORRIDAS];

        // Instanciar la clase, para utilizarla
        AppMainOrdIntercambio app = new AppMainOrdIntercambio();

        System.out.printf("\nComparacion de tiempos metodos de Ordenacion");
        System.out.printf("\nAlgoritmo de Intercambio");
        System.out.printf("\nEjecuando, por favor espere ...\n");

        for (int i = 0; i < NCORRIDAS; i++) {
            System.out.print("\n\nNumero de corrida= " + (i + 1));

            double[] vecInter = new double[NMAX];

            // Llenar el vector con numeros aleatorios
            app.llenarVecRam(vecInter, NMAX);

            System.out.print("\nIntercambio. Antes ordenar primeros 10: ");
            for (int j = 0; j < 10; j++) {
                System.out.printf(" %.4f", vecInter[j]);
            }
            long iInter = System.currentTimeMillis();

            app.AlgIntercambio(vecInter, NMAX);

            vecEstInter[i] = System.currentTimeMillis() - iInter;
            System.out.print("\nIntercambio. Despues ordenar primeros 10: ");
            for (int j = 0; j < 10; j++) {
                System.out.printf(" %.4f", vecInter[j]);
            }
        }

        System.out.printf("\n\nLos primeros tiempos de la corrida, son en comparacion lentos, porque el computador va ajustando el cache");
        System.out.printf("\n\nTiempo mSg Intercambio");
        for (int i = 0; i < NCORRIDAS; i++) {
            System.out.printf("\n             %5d", vecEstInter[i]);
        }

        // Busqueda secuencial y Binaria
    }

}
