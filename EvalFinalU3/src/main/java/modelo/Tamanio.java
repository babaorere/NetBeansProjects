package modelo;

import java.util.Objects;

/**
 *
 */
public class Tamanio {

    private int ancho;
    private int alto;

    // Validar
    public static boolean valTamanio(Tamanio tamanio) {
        return valAnchoCM(tamanio.getAncho()) && valAltoCM(tamanio.getAlto());
    }

    // Validar
    public static boolean valAnchoCM(int valor) {
        return (valor > 0) && (valor < 500);
    }

    // Validar
    public static boolean valAltoCM(int valor) {
        return (valor > 0) && (valor < 500);
    }

    /**
     * Constructor por defecto, se reutiliza el constructor con parametros para<br>
     * inicializar el blanco o cero los atributos
     */
    public Tamanio() {
        this(0, 0);
    }

    /**
     * Constructor con parametros, para inicializar la clase
     *
     * @param ancho
     * @param alto
     */
    public Tamanio(int ancho, int alto) {
        if (valAnchoCM(alto)) {
            this.ancho = ancho;
        } else {
            this.ancho = 0;
        }

        if (valAltoCM(alto)) {
            this.alto = alto;
        } else {
            this.alto = 0;
        }
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @param ancho the ancho to set
     */
    public void setAncho(int ancho) {
        if (valAnchoCM(alto)) {
            this.ancho = ancho;
        } else {
            this.ancho = 0;
        }
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @param alto the alto to set
     */
    public void setAlto(int alto) {
        if (valAltoCM(alto)) {
            this.alto = alto;
        } else {
            this.alto = 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Tamanio other = (Tamanio) obj;

        // Comparación de atributos para determinar la igualdad de contenido
        return Objects.equals(this.getAncho(), other.getAncho())
                && Objects.equals(this.getAlto(), other.getAlto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ancho, alto);
    }
}
