package com.manager.intercambio_y_burbuja;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Kevin
 */
public class AppMain {

    final int MAXIDX = 20_000;

    // Llenar un vector con aleatorios
    public void llenarVecRam(double[] vec, int n) {

        // Validar
        if ((n <= 0) || (n > vec.length)) {
            n = vec.length;
        }

        Random r = new Random();

        for (int i = 0; i < n; i++) {
            vec[i] = r.nextDouble();
        }
    }

    // Ordena de menor a mayor
    public void AlgIntercambio(double[] vec, int n) {
        if ((n <= 0) || (n > vec.length)) {
            n = vec.length;
        }

        int i;
        int j;

        for (i = 0; i <= n - 2; i++) /* coloca mínimo de a[i+1]...a[n-1] en a[i] */ {
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
     * Realiza una busqueda secuencial sobre el vector, y encuantra la posicion del primer elemento que coincida con la clave de la busqueda.
     *
     * No necesariamente el vector debe estar ordenado
     *
     * @param vec
     * @param n
     * @return laposicion del elemento, o .1 si no fue encontrado
     */
    private int BusSecuencial(double[] vec, int n, double key) {
        if ((n <= 0) || (n > vec.length)) {
            n = vec.length;
        }

        int pos = -1;
        for (int i = 0; i < n; i++) {
            if (vec[i] == key) {
                pos = i;
                break; // sale del ciclo
            }
        }

        return pos;
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
//            if (abs(vec[idx] - key) < 0.01) {
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

        long[] vecEstInter = new long[NCORRIDAS];
        long[] vecEstBur = new long[NCORRIDAS];

        AppMain app = new AppMain();

        System.out.printf("\nComparacion de tiempos metodos de Ordenacion");
        System.out.printf("\nAlgoritmo de Intercambio vs Burbuja");
        System.out.printf("\nEjecuando, por favor espere ...\n");

        for (int i = 0; i < NCORRIDAS; i++) {
            System.out.print("\n\nNumero de corrida= " + (i + 1));

            double[] vecInter = new double[NMAX];
            double[] vecBur;

            app.llenarVecRam(vecInter, NMAX);

            // Aleatoriamente se busca una posicion, y se inserta el valor de 5.00 para su busqueda
            double key = 0.50;
            Random r = new Random();
            int idx = r.nextInt(NMAX);
            vecInter[idx] = key;
            System.out.print("\nPosicion inicial del " + key + " es " + idx);

            // copiar el vector de randoms, para realizar la ordenacion por burbuja
            vecBur = vecInter.clone();

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

            System.out.print("\nBurbuja. Antes ordenar primeros 10: ");
            for (int j = 0; j < 10; j++) {
                System.out.printf(" %.4f", vecBur[j]);
            }
            long iBur = System.currentTimeMillis();
            app.AlgBurbuja(vecBur, NMAX);
            vecEstBur[i] = System.currentTimeMillis() - iBur;
            System.out.print("\nBurbuja. Despues ordenar primeros 10: ");
            for (int j = 0; j < 10; j++) {
                System.out.printf(" %.4f", vecBur[j]);
            }

            // Busqueda secuencial y Binaria
            System.out.printf("\nRecuerde que comparar dobles en Java y otros sistemas, no es algo exacto");
            System.out.printf("\nBusqueda Secuencial, key= " + key + ", pos= %5d", app.BusSecuencial(vecBur, NMAX, key));
            System.out.printf("\nBusqueda Binaria   , key= " + key + ", pos= %5d", app.BusBinaria(vecBur, NMAX, 0, NMAX - 1, key));
            int pos = Arrays.binarySearch(vecBur, key);
            if (pos > 0) {
                System.out.print("\nArrays.binarySearch. El número " + key + " está en el Array, pos= " + pos);
            } else {
                System.out.print("\nArrays.binarySearch. El número " + key + " NO está en el Array, pos= " + pos);
            }
            System.out.printf("\nBusqueda Secuencial, key= 0.10, pos= %5d", app.BusSecuencial(vecBur, NMAX, 0.10));
            System.out.printf("\nBusqueda Binaria   , key= 0.10, pos= %5d", app.BusBinaria(vecBur, NMAX, 0, NMAX - 1, 0.10));
            pos = Arrays.binarySearch(vecBur, 0.10);
            if (Arrays.binarySearch(vecBur, 0.10) > 0) {
                System.out.print("\nArrays.binarySearch. El número " + 0.10 + " está en el Array, pos= " + pos);
            } else {
                System.out.print("\nArrays.binarySearch. El número " + 0.10 + " NO está en el Array, pos= " + pos);
            }
        }

        System.out.printf("\n\nLos primeros tiempos de la corrida, son en comparacion lentos, porque el computador ajusta el cache");
        System.out.printf("\n\nTiempo mSg Intercambio Burbuja");
        for (int i = 0; i < NCORRIDAS; i++) {
            System.out.printf("\n             %5d", vecEstInter[i]);
            System.out.printf("       %5d", vecEstBur[i]);
        }

        // Busqueda secuencial y Binaria
    }

}
