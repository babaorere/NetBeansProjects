package ventas_online.entities;

/**
 *
 */
public class Camisa extends Prenda {

    private Integer idcamisa;
    private Integer talla;
    private String tipo;

    public Camisa() {
        super();
        this.talla = null;
        this.tipo = null;
    }

    public Camisa(Integer talla, String tipoManga) {
        super();
        this.talla = talla;
        this.tipo = tipoManga;
    }

    public Camisa(Integer talla, String tipo, String nombre, String descripcion, Integer cantidad, Integer precio) {
        super(nombre, descripcion, cantidad, precio);
        this.talla = talla;
        this.tipo = this.tipo;
    }

    @Override
    public String toString() {
        return "Camisa { " + getTalla() + ", " + getTipo() + " } " + super.toString();
    }

    /**
     * @return the tipoManga
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipoManga to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
     * @return the idcamisa
     */
    public Integer getIdcamisa() {
        return idcamisa;
    }

}
