package polimorfismo;

/**
 *
 * Utilizando polimorfismo paramétrico en los métodos, crear un método llamado operaciones que tenga la
 * suma de 2 números. Otro método llamada operaciones que reste 3 números, otro método llamado operaciones
 * que multiplique por 4 la suma de 3 números.
 */
public class PolimorfismoParametricoVersion2 {

    // Constructor por defecto
    public PolimorfismoParametricoVersion2() {
        // sin codigo
    }

    // Suma de 2 números
    public static <T extends Number> double operaciones(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }

    // Resta de 3 números
    public static <T extends Number> double operaciones(T a, T b, T c) {
        return a.doubleValue() - b.doubleValue() - c.doubleValue();
    }

    // Multiplicar por 4 la suma de 3 números
    public static <T extends Number> double operaciones(T a, T b, T c, T d) {
        return (a.doubleValue() + b.doubleValue() + c.doubleValue()) * d.doubleValue();
    }

    public static void main(String[] args) {

        int a = 1;
        int b = 2;
        int c = 3;
        int d = 4;

        System.out.println("Suma de dos numeros -> " + a + " + " + b + " = " + operaciones(a, b));
        System.out.println("Resta de tres numeros -> " + a + " - " + b + " - " + c + " = " + operaciones(a, b, c));
        System.out.println("multiplicacion de 4 por la suma de 3 números -> (" + a + " + " + b + " + " + c + ") * " + d + " = " + operaciones(a, b, c, d));
    }

}
