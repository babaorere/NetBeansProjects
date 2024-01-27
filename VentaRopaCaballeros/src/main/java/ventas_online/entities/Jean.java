package ventas_online.entities;

/**
 *
 */
public class Jean extends PantalonTela {

    private String tipoHilo;

    public Jean() {
        super();
        tipoHilo = null;
    }

    public Jean(String tipoHilo, Integer talla, String color, String nombre, String descripcion, Integer cantidad, Integer precio) {
        super(talla, color, nombre, descripcion, cantidad, precio);
        this.tipoHilo = tipoHilo;
    }

    @Override
    public String toString() {
        return "Jean { " + tipoHilo + " } " + super.toString();
    }

    /**
     * @return the tipoHilo
     */
    public String getTipoHilo() {
        return tipoHilo;
    }

    /**
     * @param tipoHilo the tipoHilo to set
     */
    public void setTipoHilo(String tipoHilo) {
        this.tipoHilo = tipoHilo;
    }

}
