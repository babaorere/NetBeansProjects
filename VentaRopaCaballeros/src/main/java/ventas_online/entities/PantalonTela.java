package ventas_online.entities;

/**
 *
 */
public class PantalonTela extends Prenda {

    private Integer talla;
    private String color;

    public PantalonTela() {
        super(null, null, null, null);
        this.talla = null;
        this.color = null;

    }

    public PantalonTela(Integer talla, String color, String nombre, String descripcion, Integer cantidad, Integer precio) {
        super(nombre, descripcion, cantidad, precio);
        this.talla = talla;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Pantalon Tela { " + talla + ", " + color + " } " + super.toString();
    }

    /**
     * @return the talla
     */
    public Integer getTalla() {
        return talla;
    }

    /**
     * @param talla the talla to set
     */
    public void setTalla(Integer talla) {
        this.talla = talla;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

}
