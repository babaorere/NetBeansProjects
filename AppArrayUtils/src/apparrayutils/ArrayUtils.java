//package apparrayutils;

/**
 * Propuesta 44
 *
 * Diseñe la clase ArrayUtils
 *
 * @author manager
 */
public final class ArrayUtils implements Cloneable {

    /**
     * Retorna una instacia clon del presente objeto
     *
     * @return
     */
    @Override
    protected Object clone() {

        ArrayUtils aux;
        try {
            aux = (ArrayUtils) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            aux = null;
        }

        return aux;
    }

    /**
     * Escriba un método llamado cloncArray que reciba un array de números enteros como parámetro, cree un nuevo array del mismo tamaño, <br>
     * copie los elementos del primer array hacia el nuevo y luego devuelva una referencia al nuevo array. <br>
     *
     * @param array
     * @return
     */
    public static int[] cloneArray(int[] array) {

        // validacion
        if ((array == null) || (array.length <= 0)) {
            return null;
        }

        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Implemente el método makeHist el cual recibe como parámetro un Array y debe retornar el histograma de los valores del Array. <br>
     *
     * @param array
     * @return
     */
    public static int[][] makeHist(int[] array) {

        // validacion
        if ((array == null) || (array.length <= 0)) {
            return null;
        }

        // Array de boolean para saber cuando un numero fue contabilizado
        boolean centinela[] = new boolean[array.length];
        for (int i = 0; i < centinela.length; i++) {
            centinela[i] = false;
        }

        // Array donde se contabilizara el histograma
        int[][] arrAux = new int[array.length][2];
        int idxAux = 0;

        for (int i = 0; i < array.length; i++) {

            if (centinela[i]) { // ya fue contabilizado
                continue;
            }

            // Añadimos la primera aparicion del elemento
            centinela[i] = true;
            arrAux[idxAux][0] = array[i];
            arrAux[idxAux][1] = 1;

            // Buscamos en el resto del array
            for (int j = i + 1; j < array.length; j++) {
                if ((arrAux[idxAux][0] == array[j])) {
                    arrAux[idxAux][1]++;
                    centinela[j] = true;
                }
            }

            // pasamos a la siguiente posicion del array auxiliar
            idxAux++;
        }

        // Procedemos a crear un nuevo histograma, justo del tamaño requerido
        int[][] arrHist = new int[idxAux][2];

        for (int i = 0; i < idxAux; i++) {
            arrHist[i][0] = arrAux[i][0];
            arrHist[i][1] = arrAux[i][1];
        }

        return arrHist;
    }

    /**
     * Implemente el método areFactors el cual recibe un entero n y un Array de enteros, <br>
     * el método debe retornar "true" si el entero n es divisible por todos los enteros contenidos en el Array. <br>
     *
     * @param n
     * @param array
     * @return
     */
    public static boolean areFactors(int n, int[] array) {

        // validacion
        if ((array == null) || (array.length <= 0)) {
            return false;
        }

        // Apenas se encuentre el primer valor NO divisible, se retorna false
        for (int i = 0; i < array.length; i++) {
            if (n % array[i] != 0) {
                return false;
            }
        }

        // Fueron recorridos todos lo elementos del array, y todos hicieron divisible a "n"
        return true;
    }

    /**
     * Implemente un método que reciba un Array de enteros y un numero entero. <br>
     * Debe retornar la posición en la que el entero n aparece por primera ves en el arreglo. Si no aparece, debe retornar -1.
     *
     * @param n
     * @param array
     * @return
     */
    public static int indexOf(int[] array, int n) {

        // validacion
        if ((array == null) || (array.length <= 0)) {
            return -1;
        }

        // Apenas se encuentre el primer se retorna el indice "i"
        for (int i = 0; i < array.length; i++) {
            if (n == array[i]) {
                return i;
            }
        }

        // Fueron recorridos todos lo elementos del array, y ninguno coincidio con "n"
        return -1;
    }
}
