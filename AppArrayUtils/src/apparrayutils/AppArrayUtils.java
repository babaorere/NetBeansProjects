//package apparrayutils;

/**
 *
 * @author manager
 */
public class AppArrayUtils {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int[] arr = {1, 2, 3, 1, 2, 3, 4, 5, 6};

        final int X = 40;
        final int Y = 1 * 2 * 3 * 4 * 5 * 6;

        // Parte a
        int[] arrAux = ArrayUtils.cloneArray(arr);

        System.out.println("\n\nArrayUtils.cloneArray:");

        for (int i = 0; i < arr.length; i++) {
            System.out.println("original array[" + i + "]= " + arr[i]);
        }

        for (int i = 0; i < arrAux.length; i++) {
            System.out.println("clone array[" + i + "]= " + arrAux[i]);
        }

        // parte b
        int[][] arrHist = ArrayUtils.makeHist(arr);

        System.out.println("\n\nArrayUtils.makeHist:");

        for (int[] item : arrHist) {
            System.out.println("Histograma valor= " + item[0] + ", contador= " + item[1]);
        }

        // parte c
        System.out.println("\n\nArrayUtils.AreFactos:");
        System.out.println("Si n= " + X + ", areFactors= " + ArrayUtils.areFactors(X, arr));
        System.out.println("Si n= " + Y + ", areFactors= " + ArrayUtils.areFactors(Y, arr));

        // parte d
        System.out.println("\n\nArrayUtils.indexOf:");
        System.out.println("Si n= " + X + ", indexOf= " + ArrayUtils.indexOf(arr, X));
        System.out.println("Si n= " + Y + ", indexOf= " + ArrayUtils.indexOf(arr, Y));
        System.out.println("Si n= " + 3 + ", indexOf= " + ArrayUtils.indexOf(arr, 3));
        System.out.println("Si n= " + 4 + ", indexOf= " + ArrayUtils.indexOf(arr, 4));
    }

}
