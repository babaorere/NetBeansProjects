package ventas_online.entities;

/**
 *
 */
public class Playera extends Prenda {

    public Playera() {
        super();
    }

    public Playera(String nombre, String descripcion, Integer cantidad, Integer precio) {
        super(nombre, descripcion, cantidad, precio);
    }

    @Override
    public String toString() {
        return "Playera { } " + super.toString();
    }

}
