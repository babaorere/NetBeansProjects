package polimorfismo;

/**
 *
 * Utilizando polimorfismo paramétrico en los métodos, crear un método llamado operaciones que tenga la
 * suma de 2 números. Otro método llamada operaciones que reste 3 números, otro método llamado operaciones
 * que multiplique por 4 la suma de 3 números.
 */
public class PolimorfismoParametricoVersion1 {

    // Default constructor
    public PolimorfismoParametricoVersion1() {
        // Sin codigo
    }

    /**
     *
     * Utilizando polimorfismo paramétrico en los métodos, crear un método llamado operaciones que tenga:
     * la suma de 2 números
     */
    public int operaciones(int a, int b) {
        return a + b;
    }

    /**
     *
     * Utilizando polimorfismo paramétrico en los métodos, crear un método llamado operaciones que tenga:
     * la resta de 3 números
     */
    public int operaciones(int a, int b, int c) {
        return a - b - c;
    }

    /**
     *
     * Utilizando polimorfismo paramétrico en los métodos, crear un método llamado operaciones que tenga:
     * la multiplicacion de 4 por la suma de 3 números.
     */
    public int operaciones(int a, int b, int c, int d) {
        return (a + b + c) * d;
    }

    /**
     *
     * Utilizando polimorfismo paramétrico en los métodos, crear un método llamado operaciones que tenga:
     * la suma de 2 números
     */
    public String operaciones(String a, String b) {
        return a + b;
    }

    public String operaciones(String a, int b) {
        return a + b;
    }

    public static void main(String[] args) {

        PolimorfismoParametricoVersion1 pp = new PolimorfismoParametricoVersion1();

        int a = 1;
        int b = 2;
        int c = 3;
        int d = 4;

        System.out.println("Suma de dos numeros -> " + a + " + " + b + " = " + pp.operaciones(a, b));
        System.out.println("Concatenacion de dos String -> " + a + " + " + b + " = " + pp.operaciones("Hola ", "Mundo"));
        System.out.println("Concatenacion de dos String -> " + a + " + " + b + " = " + pp.operaciones("Hola ", 123));
        System.out.println("Resta de tres numeros -> " + a + " - " + b + " - " + c + " = " + pp.operaciones(a, b, c));
        System.out.println("multiplicacion de 4 por la suma de 3 números -> (" + a + " + " + b + " + " + c + ") * " + d + " = " + pp.operaciones(a, b, c, d));
    }

}

class Prueva extends PolimorfismoParametricoVersion1 {

    @Override
    public String operaciones(String a, int b) {
        return a.toUpperCase() + b;
    }

}
