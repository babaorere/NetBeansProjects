package serviciobarberia.modelo;

/**
 *
 */
public class Cliente {

    private Integer idcliente; // inmutable
    private String nombre;
    private String telefono;
    private String direccion;
    private Integer idUsuario;

    public Cliente() {
        this(null, "", "", "", null);
    }

    public Cliente(Integer inIdCliente, String inNombre, String InTelefono, String InDireccion, Integer inIdUsuario) {
        this.idcliente = inIdCliente;
        this.nombre = inNombre;
        this.telefono = InTelefono;
        this.direccion = InDireccion;
        this.idUsuario = inIdUsuario;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param idcliente the idcliente to set
     */
    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    /**
     * @return the idUsuario
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

}
