package ventas_online.entities;

/**
 *
 */
public class Zapato extends Prenda {

    private Integer talla;
    private String material;
    private String tipoCorte;

    public Zapato() {
        super();
    }

    public Zapato(Integer talla, String material, String tipoCorte) {
        super();
        this.talla = talla;
        this.material = material;
        this.tipoCorte = tipoCorte;
    }

    public Zapato(Integer talla, String material, String tipoCorte, String nombre, String descripcion, Integer cantidad, Integer precio) {
        super(nombre, descripcion, cantidad, precio);
        this.talla = talla;
        this.material = material;
        this.tipoCorte = tipoCorte;
    }

    @Override
    public String toString() {
        return "Zapatos { " + talla + ", " + material + ", " + tipoCorte + " } " + super.toString();
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
     * @return the material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * @return the tipoCorte
     */
    public String getTipoCorte() {
        return tipoCorte;
    }

    /**
     * @param tipoCorte the tipoCorte to set
     */
    public void setTipoCorte(String tipoCorte) {
        this.tipoCorte = tipoCorte;
    }

}
