
package proyectohotel;

/**
 *
 * @author Esteban Quesada
 */
public class Cliente {
    
    String codigo;
    String nombre;
    String telefono;
    String direccion;

    public Cliente(String codigo, String nombre, String telefono, String direccion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    public Cliente(){
         this.codigo="";
         this.nombre="";
         this.telefono="";
         this.direccion="";
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    
}
