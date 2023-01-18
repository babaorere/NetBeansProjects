package serviciobarberia.modelo;

/**
 *
 */
public class Servicio {

    private Integer idServicio; // inmutable
    private String nombre;
    private Integer precio;

    public Servicio() {
        this(null, null, null);
    }

    public Servicio(Integer inIdServicio, String inNombre, Integer inPrecio) {
        this.idServicio = inIdServicio;
        this.nombre = inNombre;
        this.precio = inPrecio;
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the precio
     */
    public Integer getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * @return the idServicio
     */
    public Integer getIdServicio() {
        return idServicio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * @param idServicio the idServicio to set
     */
    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

}
