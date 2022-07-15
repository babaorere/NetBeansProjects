package con.manager.datos;

import static java.lang.Integer.min;
import java.util.Objects;

/**
 *
 * @author manager
 */
public class TVendedor implements Comparable<TVendedor> {

    private int codigo;
    private String nombre;
    private String correo;
    private String telefono;

    public TVendedor(int codigo, String nombre, String correo, String telefono) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        TVendedor aux = (TVendedor) other;
        return this.getCodigo() == aux.getCodigo()
                && this.getNombre().equals(aux.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getNombre());
    }

    @Override
    public String toString() {
        return String.format("%5d %32s %32s", getCodigo(), getNombre().substring(0, min(32, getNombre().length())), getCorreo());
    }

    @Override
    public int compareTo(TVendedor other) {
        if (this.getCodigo() == other.getCodigo()) {
            return getNombre().compareTo(other.getNombre());
        } else {
            return this.getCodigo() > other.getCodigo() ? 1 : -1;
        }
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
