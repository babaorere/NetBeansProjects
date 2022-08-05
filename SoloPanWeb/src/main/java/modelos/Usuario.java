package modelos;

/**
 *
 */
public class Usuario {

    private final long id;
    private String nombre;
    private String user;
    private String password;
    private int rol;

    public Usuario(long id, String nombre, String user, String password, int rol) {
        this.id = id;
        this.nombre = nombre;
        this.user = user;
        this.password = password;
        this.rol = rol;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
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
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the rol
     */
    public int getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(int rol) {
        this.rol = rol;
    }

}
