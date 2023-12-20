package modelo;

import java.util.Objects;

/**
 * La ficha de ingreso a sala debe contener la sala y la pintura
 */
public class FichaIngreso {

    private Pintura pintura;
    private Sala ubicacion;

    /**
     * Constructor por defecto, se reutiliza el constructor con parametros para<br>
     * inicializar los atributos con blanco o cero
     */
    public FichaIngreso() {
        this(new Pintura(), new Sala());
    }

    /**
     * Contructor con parametros
     *
     * @param pintura
     * @param ubicacion
     */
    public FichaIngreso(Pintura pintura, Sala ubicacion) {

        if (Pintura.valPintura(pintura)) {
            this.pintura = pintura;
        } else {
            this.pintura = new Pintura();
        }

        if (Sala.valSala(ubicacion)) {
            this.ubicacion = ubicacion;
        } else {
            this.ubicacion = new Sala();
        }
    }

    /**
     * @return the Pintura
     */
    public Pintura getPintura() {
        return pintura;
    }

    /**
     * @param pintura
     */
    public void setPintura(Pintura pintura) {
        if (Pintura.valPintura(pintura)) {
            this.pintura = pintura;
        } else {
            this.pintura = new Pintura();
        }
    }

    /**
     * @return the ubicacion
     */
    public Sala getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(Sala ubicacion) {
        if (Sala.valSala(ubicacion)) {
            this.ubicacion = ubicacion;
        } else {
            this.ubicacion = new Sala();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(pintura, ubicacion);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FichaIngreso other = (FichaIngreso) obj;
        if (!Objects.equals(this.pintura, other.pintura)) {
            return false;
        }
        return Objects.equals(this.ubicacion, other.ubicacion);
    }
}
