package com.manager.busbinaria;

import java.util.Random;

/**
 *
 * @author Kevin
 */
public class AppMainBusBinaria {

    final int MAXIDX = 20_000;
    private final Random r = new Random();

    private Random GetRandom() {
        return r;
    }

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

    /**
     * Busqueda binario usando recursion, necesariamente el vector debe estar ordenado. Dado que es un metodo recursivo, se declara static, para facilitar su ejecucion
     *
     * @param vec
     * @param n
     * @param key
     * @return la posicion del elemento buscado, -1 si no fue encontrado
     */
    private static int BusBinaria(double[] vec, int n, int minIdx, int maxIdx, double key) {

        if (maxIdx >= minIdx && minIdx < n - 1) {
            int idx = minIdx + (maxIdx - minIdx) / 2;
            if (vec[idx] == key) {
                return idx;
            }
            if (vec[idx] > key) {
                return BusBinaria(vec, n, minIdx, idx - 1, key);
            }
            return BusBinaria(vec, n, idx + 1, maxIdx, key);
        }
        return -1;
    }

    // Punto de entrada de la aplicacion
    public static void main(String[] args) {

        final int NMAX = 20_000;
        final int NCORRIDAS = 25;

        AppMainBusBinaria app = new AppMainBusBinaria();

        System.out.printf("\nComparacion de tiempos metodos de Ordenacion");
        System.out.printf("\nAlgoritmo de la Burbuja");
        System.out.printf("\nEjecuando, por favor espere ...\n");

        for (int i = 0; i < NCORRIDAS; i++) {
            System.out.print("\n\nNumero de corrida= " + (i + 1));

            double[] vecBus = new double[NMAX];

            app.llenarVecRam(vecBus, NMAX);

            // Aleatoriamente se busca una posicion, y se inserta el valor de 5.00 para su busqueda
            final int idxIni = app.GetRandom().nextInt(NMAX);
            double key = vecBus[idxIni];
            System.out.print("\nPosicion inicial del " + key + " es " + idxIni);

            System.out.print("\nBurbuja. Antes ordenar, primeros 10: ");
            for (int j = 0; j < 10; j++) {
                System.out.printf(" %.2f", vecBus[j]);
            }
            app.AlgBurbuja(vecBus, NMAX);
            System.out.print("\nBurbuja. Despues ordenar, primeros 10: ");
            for (int j = 0; j < 10; j++) {
                System.out.printf(" %.2f", vecBus[j]);
            }

            // Busqueda Binaria
            System.out.print("\nElemento a buscar, posicion inicial de: " + key + " es " + idxIni);
            System.out.printf("\nRecuerde que comparar dobles en Java y otros sistemas, no es algo exacto");
            long iInter = System.nanoTime();
            int pos = app.BusBinaria(vecBus, NMAX, 0, NMAX - 1, key);
            long t = System.nanoTime() - iInter;

            System.out.printf("\nBusqueda Binaria, key= " + key + ", pos= %5d, tiempo nSg=  %5d", idxIni, t);
            if (pos > 0) {
                System.out.print("\nBusqueda Binaria, key= " + key + " está en el Array, pos= " + pos);
            } else {
                System.out.print("\nBusqueda Binaria, key= " + key + " NO está en el Array, pos= " + pos);
            }

            key = app.GetDouble();
            System.out.printf("\nRecuerde que comparar dobles en Java y otros sistemas, no es algo exacto");
            pos = app.BusBinaria(vecBus, NMAX, 0, NMAX - 1, key);
            System.out.printf("\nBusqueda Binaria, key aleatorio= " + key + ", tiempo nSg=  %5d", idxIni, t);
            if (pos > 0) {
                System.out.print("\nBusqueda Binaria, key= " + key + " está en el Array, pos= " + pos);
            } else {
                System.out.print("\nBusqueda Binaria, key= " + key + " NO está en el Array, pos= " + pos);
            }

        }
    }

}
