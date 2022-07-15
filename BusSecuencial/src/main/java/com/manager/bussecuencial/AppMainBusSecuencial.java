package com.manager.bussecuencial;

import java.util.Random;

/**
 *
 * @author Kevin
 */
public class AppMainBusSecuencial {

    final int MAXIDX = 20_000;
    private final Random r = new Random();

    public Random GetRandom() {
        return r;
    }

    public double GetDouble() {
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

    // Punto de entrada de la aplicacion
    public static void main(String[] args) {

        final int NMAX = 20_000;
        final int NCORRIDAS = 25;

        // Instaciar clase para su uso
        AppMainBusSecuencial app = new AppMainBusSecuencial();

        System.out.printf("\nTiempos busqueda secuencial");
        System.out.printf("\nEjecuando, por favor espere ...\n");

        for (int i = 0; i < NCORRIDAS; i++) {
            System.out.print("\n\nNumero de corrida= " + (i + 1));

            double[] vecBus = new double[NMAX];

            app.llenarVecRam(vecBus, NMAX);

            // Aleatoriamente se busca una posicion, y se toma el elemento para su comparacion
            int idx = app.GetRandom().nextInt(NMAX);
            double key = vecBus[idx];
            System.out.print("\n\nElemento a buscar, posicion inicial de: " + key + " es " + idx);

            // Busqueda secuencial y Binaria
            System.out.printf("\nRecuerde que comparar dobles en Java y otros sistemas, no es exacto");
            long iInter = System.nanoTime();
            int pos = app.BusSecuencial(vecBus, NMAX, key);
            long t = System.nanoTime() - iInter;

            System.out.printf("\nBusqueda Secuencial, key= " + key + ", pos= %5d, tiempo nSg=  %5d", pos, t);
            if (pos > 0) {
                System.out.print("\nBusqueda Secuencial, key= " + key + " está en el Array, pos= " + pos);
            } else {
                System.out.print("\nBusqueda Secuencial, key= " + key + " NO está en el Array, pos= " + pos);
            }

            key = app.GetDouble();
            iInter = System.nanoTime();
            pos = app.BusSecuencial(vecBus, NMAX, key);
            t = System.nanoTime() - iInter;
            System.out.printf("\nBusqueda Secuencial, key= " + key + ", pos= %5d, tiempo nSg=  %5d", pos, t);
            if (pos > 0) {
                System.out.print("\nArrays.binarySearch. El número " + key + " está en el Array, pos= " + pos);
            } else {
                System.out.print("\nArrays.binarySearch. El número " + key + " NO está en el Array, pos= " + pos);
            }
        }

    }

}
