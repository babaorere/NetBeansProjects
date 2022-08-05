package doblelink;

/**
 *
 * @author manager
 */
public class Fraccion {

    private int num;
    private int den;

    @Override
    public String toString() {
        return "(" + this.num + "/" + this.den + ")";
    }

    private int mod(int a, int b) {
        if (b == 0) {
            return a;
        }

        return mod(b, a % b);
    }

    private void simplificar() {
        int m = this.mod(this.num, this.den);
        this.num = this.num / m;
        this.den = this.den / m;
    }

    public Fraccion() {
        this.num = 0;
        this.den = 1;
    }

    public Fraccion(int innum, int inden) {
        this.num = innum;

        if (inden != 0) {
            this.den = inden;
        } else {
            this.den = 1;
        }

        simplificar();
    }

    public Fraccion multiplicacion(Fraccion b) {
        int n = this.num * b.num;
        int d = this.den * b.den;

        return new Fraccion(n, d);
    }

    @Override
    public boolean equals(Object obj) {
        Fraccion other = (Fraccion) obj;

        return ((this.num == other.num) && (this.den == other.den));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) Integer.hashCode(num);
        hash = 31 * hash + (int) Integer.hashCode(den);
        return hash;
    }

}
