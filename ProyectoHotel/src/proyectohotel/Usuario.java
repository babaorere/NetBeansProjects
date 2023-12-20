
package proyectohotel;

/**
 *
 * @author Esteban Quesada
 */
public class Usuario {
    
    String usuario;
    String contraseña;
    String nombre;

    public Usuario(String usuario, String contraseña, String nombre) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
    }
    
    public Usuario() {
        this.usuario = "";
        this.contraseña = "";
        this.nombre = "";
    }
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    
}
