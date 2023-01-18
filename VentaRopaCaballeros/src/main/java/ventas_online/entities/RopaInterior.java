package ventas_online.entities;

/**
 *
 */
public class RopaInterior extends Prenda {

    private String genero;
    private String tipoCorte;

    public RopaInterior() {
        super();
    }

    public RopaInterior(String genero, String tipoCorte) {
        super();
        this.genero = genero;
        this.tipoCorte = tipoCorte;
    }

    public RopaInterior(String genero, String tipoCorte, String nombre, String descripcion, Integer cantidad, Integer precio) {
        super(nombre, descripcion, cantidad, precio);
        this.genero = genero;
        this.tipoCorte = tipoCorte;
    }

    @Override
    public String toString() {
        return "RopaInteror { " + genero + ", " + tipoCorte + " } " + super.toString();
    }

}
