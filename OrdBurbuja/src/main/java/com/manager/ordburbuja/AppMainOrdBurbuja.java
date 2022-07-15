package com.manager.ordburbuja;

import java.util.Random;

/**
 *
 * @author Kevin
 */
public class AppMainOrdBurbuja {

    final int MAXIDX = 20_000;
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

    /**
     * Algoritmo de ordenacion de la Burbuja
     *
     * @param vec
     * @param n
     */
    private void AlgBurbuja(double[] vec, int n) {

        if ((n <= 0) || (n > vec.length)) {
            n = vec.length;
        }

        boolean desorden = true;
        double aux;

        while (desorden) {
            desorden = false;
            for (int i = 0; i < n - 1; i++) {
                if (vec[i] > vec[i + 1]) {
                    aux = vec[i];
                    vec[i] = vec[i + 1];
                    vec[i + 1] = aux;
                    desorden = true;
                }
            }
        }
    }

    // Punto de entrada de la aplicacion
    public static void main(String[] args) {

        final int NMAX = 20_000;
        final int NCORRIDAS = 25;

        long[] vecEstBur = new long[NCORRIDAS];

        // Instanciar el objeto para su uso
        AppMainOrdBurbuja app = new AppMainOrdBurbuja();

        System.out.printf("\nComparacion de tiempos metodos de Ordenacion");
        System.out.printf("\nAlgoritmo de ordenacion la Burbuja");
        System.out.printf("\nEjecuando, por favor espere ...\n");

        for (int i = 0; i < NCORRIDAS; i++) {
            System.out.print("\n\nNumero de corrida= " + (i + 1));

            double[] vecBur = new double[NMAX];

            app.llenarVecRam(vecBur, NMAX);

            System.out.print("\nBurbuja. Antes ordenar, primeros 10: ");
            for (int j = 0; j < 10; j++) {
                System.out.printf(" %.2f", vecBur[j]);
            }
            long iBur = System.currentTimeMillis();
            app.AlgBurbuja(vecBur, NMAX);
            vecEstBur[i] = System.currentTimeMillis() - iBur;
            System.out.print("\nBurbuja. Despues ordenar, primeros 10: ");
            for (int j = 0; j < 10; j++) {
                System.out.printf(" %.2f", vecBur[j]);
            }

        }

        System.out.printf("\n\nLos primeros tiempos de la corrida, son en comparacion lentos, porque el computador ajusta el cache");
        System.out.printf("\n\nTiempo mSg: Burbuja");
        for (int i = 0; i < NCORRIDAS; i++) {
            System.out.printf("\n              %5d", vecEstBur[i]);
        }

    }

}
