package com.benchmark;

import java.text.DecimalFormat;
import java.util.Random;

public class CompletedBenchmarkTool implements BenchmarkTool {

    // 1. Implement the method public Integer[] generateTestDataBinary(int size). <br>
    // Half the data is 0s, half 1s. For example, an input of length 8 might look like 
    // [0, 0, 0, 0, 1, 1, 1, 1]. See the interface. [4 points]
    public Integer[] generateTestDataBinary(int size) {

        Integer[] arr = new Integer[size];

        for (int i = 0; i < size; i++) {
            arr[i] = (i < size / 2) ? 0 : 1;
        }

        return arr;
    }

    // 2. Implement the method public Integer[] generateTestDataHalves(int size). 
    // Half the data is 0s, half the remainder is 1s, half the reminder is 2s, 
    // half the reminder is 3s, and so forth. 
    // For example, an input of length 8 might look like 
    // [0, 0, 0, 0, 1, 1, 2, 3]. See the interface. [6 points]
    public Integer[] generateTestDataHalves(int size) {

        Integer[] arr = new Integer[size];

        int count = 0;
        int base = 0;
        int half = size;
        while (base < size) {

            half = half / 2;
            if (half <= 0) {
                half = 1;
            }

            for (int offset = 0; (offset < half) && (base + offset < size); offset++) {
                arr[base + offset] = count;
            }

            base += half;
            count++;
        }

        return arr;

    }

    // 3. Implement the method public Integer[] generateTestDataHalfRandom(int size). 
    // Half the data is 0s, half random int values (use nextInt() from Java's Random package, 
    // and use Integer.MAX_VALUE as its argument to ensure the values are positive). 
    // For example, an input of length 8 might look like 
    // [0, 0, 0, 0, 54119567, 4968, 650736346, 138617093]. See the interface. [4 points]
    public Integer[] generateTestDataHalfRandom(int size) {

        Integer[] arr = new Integer[size];

        Random rand = new Random();

        for (int i = 0; i < size; i++) {

            arr[i] = (i < size / 2) ? 0 : rand.nextInt(Integer.MAX_VALUE);

        }

        return arr;

    }

    // 4. Implement the method public double computeDoublingFormula(double t1, double t2). 
    // See the interface for more information. [2 points]
    public double computeDoublingFormula(double t1, double t2) {

        return Math.log(t2 / t1) / Math.log(2);

    }

    // 5. Implement the method public double benchmarkInsertionSort(Integer[] small, Integer[] large). 
    // See the interface for more information. [2 points]
    public double benchmarkInsertionSort(Integer[] small, Integer[] large) {

        Stopwatch startTime1 = new Stopwatch();
        insertionSort(small);
        double smallArrayTime = startTime1.elapsedTime();

        Stopwatch startTime2 = new Stopwatch();
        insertionSort(large);
        double largeArrayTime = startTime2.elapsedTime();

        return computeDoublingFormula(smallArrayTime, largeArrayTime);

    }

    // 6. Implement the method public double benchmarkShellsort(Integer[] small, Integer[] large). 
    // See the interface for more information. [2 points]
    public double benchmarkShellsort(Integer[] small, Integer[] large) {

        Stopwatch startTime1 = new Stopwatch();
        shellsort(small);
        double smallArrayTime = startTime1.elapsedTime();

        Stopwatch startTime2 = new Stopwatch();
        shellsort(large);
        double largeArrayTime = startTime2.elapsedTime();

        return computeDoublingFormula(smallArrayTime, largeArrayTime);

    }

    // 7. Implement the method public void runBenchmarks(int size). 
    // Whitespace is flexible (any number of tabs or spaces) but you must show 
    // three decimal places. 
    // See the interface for more information. 
    // Hint: should call the two benchmark methods above. The output should look like below. [4 points]
    public void runBenchmarks(int size) {

        Integer[] binSmall = generateTestDataBinary(size);
        System.out.println("\nbinSmall");
//        for (int i = 0; i < binSmall.length; i++) {
//            System.out.print(" " + Integer.valueOf(binSmall[i]));
//        }

        Integer[] binLarge = generateTestDataBinary(size * 2);
        System.out.println("\nbinLarge");
//        for (int i = 0; i < binLarge.length; i++) {
//            System.out.print(" " + Integer.valueOf(binLarge[i]));
//        }

        Integer[] halfSmall = generateTestDataHalves(size);
        System.out.println("\nhalfSmall");
//        for (int i = 0; i < halfSmall.length; i++) {
//            System.out.print(" " + Integer.valueOf(halfSmall[i]));
//        }

        Integer[] halfLarge = generateTestDataHalves(size * 2);
        System.out.println("\nhalfLarge");
//        for (int i = 0; i < halfLarge.length; i++) {
//            System.out.print(" " + Integer.valueOf(halfLarge[i]));
//        }

        Integer[] randSmall = generateTestDataHalfRandom(size);
        System.out.println("\nrandSmall");
//        for (int i = 0; i < randSmall.length; i++) {
//            System.out.print(" " + Integer.valueOf(randSmall[i]));
//        }

        Integer[] randLarge = generateTestDataHalfRandom(size * 2);
        System.out.println("\nrandLarge");
//        for (int i = 0; i < randLarge.length; i++) {
//            System.out.print(" " + Integer.valueOf(randLarge[i]));
//        }

        double binInsertionTime = benchmarkInsertionSort(binSmall, binLarge);

        double halfInsertionTime = benchmarkInsertionSort(halfSmall, halfLarge);

        double RandInsertionTime = benchmarkInsertionSort(randSmall, randLarge);

        double binShellTime = benchmarkShellsort(binSmall, binLarge);

        double halfShellTime = benchmarkShellsort(halfSmall, halfLarge);

        double RandShellTime = benchmarkShellsort(randSmall, randLarge);

        DecimalFormat dc = new DecimalFormat("#.###");

        System.out.println("\n\n Results: ");

        System.out.println("         Insertion       Shellsort");

        System.out.println("Bin           " + dc.format(binInsertionTime) + "       " + dc.format(binShellTime));

        System.out.println("Half          " + dc.format(halfInsertionTime) + "       " + dc.format(halfShellTime));

        System.out.println("RandInt       " + dc.format(RandInsertionTime) + "       " + dc.format(RandShellTime));

    }

    /**
     * *************************************************************************
     *
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK) *
     *
     *************************************************************************
     */
    // Insertion sort.
    public static void insertionSort(Comparable[] a) {

        int N = a.length;

        for (int i = 1; i < N; i++) {

            // Insert a[i] among a[i-1], a[i-2], a[i-3]...
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }

        }

        // for (int i = 0; i < N; i++)
        // {
        //     // Exchange a[i] with each smaller entry on its a[i+1...N]. This for finds the smallest element in the array and puts it in the first position.
        //     int min = i; // index of smallest entry.
        //     for (int j = i+1; j < N; j++)
        //     if (less(a[j], a[min])) min = j;
        //     exch(a, i, min);
        // }
    }

    public static void shellsort(Comparable[] a) {

        int N = a.length;

        int h = 1;

        while (h < N / 3) {
            h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        }

        while (h >= 1) {

            // h-sort the array.
            for (int i = h; i < N; i++) {

                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }

            }

            h = h / 3;

        }

    }

    private static boolean less(Comparable v, Comparable w) {

        return v.compareTo(w) < 0;

    }

    private static void exch(Comparable[] a, int i, int j) {

        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;

    }

    /**
     * *************************************************************************
     *
     * END - SORTING UTILITIES, DO NOT MODIFY *
     *
     *************************************************************************
     */
    // TODO: implement interface methods.
    public static void main(String args[]) {

        BenchmarkTool me = new CompletedBenchmarkTool();

        int size = 4096;
//        int size = 8;

        //NOTE: feel free to change size here. all other code must go in the
        //      methods.
        // insertionSort(a);
        me.runBenchmarks(size);

    }

}
