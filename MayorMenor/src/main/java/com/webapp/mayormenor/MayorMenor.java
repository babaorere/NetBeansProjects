package com.webapp.mayormenor;

/**
 *
 */
public class MayorMenor {

    private int mayor;
    private int menor;
    private int nele;
    private int nfil;
    private int ncol;

    private int m[][] = {
        {7, 0, 8, 9},
        {12, 16, 5, 2},
        {3, 5, 13, 17},
        {5, 9, 22, 11}
    };

    // inicializar variables del sistema
    public MayorMenor() {

        // suponemos que el mayor es el primer elemento
        mayor = m[0][0];

        // suponemos que el menor es el primer elemento
        menor = m[0][0];

        nfil = m.length;
        ncol = m[0].length;
        nele = nfil * ncol;
    }

    /**
     *
     * @param inM Matriz sobre la cual se realiza el proceso
     * @param idx La posicion sobre la matriz, a procesar
     */
    public void recorrerMatRecursivo(int inM[][], int idx) {

        // verificar que sea un valor valido, dentro de la matriz
        if (idx < nele) {
            int i = idx / nfil;
            int j = idx % ncol;

            if (m[i][j] > mayor) {
                mayor = m[i][j];
            }

            System.out.println("Subiendo matriz[" + i + "][" + j + "]= " + m[i][j] + " Mayor: " + mayor);

            // Aqui ocurre la recursividad
            recorrerMatRecursivo(inM, idx + 1);

            if (m[i][j] < menor) {
                menor = m[i][j];
            }

            System.out.println("Bajando matriz[" + i + "][" + j + "]= " + m[i][j] + " Menor: " + menor);

        } else {
            System.out.println("Ha llegado al caso base");

            // aqui se acaba la recursion
            return;
        }
    }

    public int[][] getM() {
        return m;
    }

    public static void main(String[] args) {

        MayorMenor mm = new MayorMenor();

        // el 0 indica el primer valor a partir del cual se realizara el proceso
        // otra manera de hacerlo, es colocar un contador interno a la clase,
        // y que se valla incrementando con cada llamada del metodo recursivo
        mm.recorrerMatRecursivo(mm.getM(), 0);

        System.out.println("Fin del programa");
    }
}
