package ventas_online.entities;

/**
 *
 */
public class Prenda {

    private Integer idprenda;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private Integer precio;

    public Prenda() {
        this.nombre = null;
        this.descripcion = null;
        this.cantidad = null;
        this.precio = null;
    }

    public Prenda(String nombre, String descripcion, Integer cantidad, Integer precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Prenda { " + nombre + ", " + descripcion + ", " + cantidad + ", " + precio + " }";
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * @return the idprenda
     */
    public Integer getIdprenda() {
        return idprenda;
    }

}
