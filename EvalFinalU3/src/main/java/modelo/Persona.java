package modelo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static modelo.Persona.valApellido;
import static modelo.Persona.valNacionalidad;

/**
 *
 */
public class Persona {

    // Lista de algunas nacionalidades permitidas
    private static final String[] strArr = {"ARGENTINA", "PERU", "CHILE", "URUGUAY"};

    // Lista de nacionalidades permitidas
    private static final List<String> strList = Arrays.asList(strArr);

    // Atributos privados
    private String nombre;
    private String apellido;
    private String nacionalidad;

    // Validar
    public static boolean valNombre(String nombre) {

        return (nombre != null) && nombre.length() > 0;
    }

    // Validar
    public static boolean valApellido(String apellido) {

        return (apellido != null) && apellido.length() > 0;
    }

    // Validar
    // Verificamos que la nacionalidad este dentro de la lista
    public static boolean valNacionalidad(String nacionalidad) {

        for (String item : strList) {
            if (item.equalsIgnoreCase(nacionalidad)) {
                return true;
            }
        }

        return false;
    }

    // Validar
    // Validamos todos los atributos de un objeto Persona
    public static boolean valPersona(Persona persona) {

        return valNombre(persona.getNombre())
                && valApellido(persona.getApellido())
                && valNacionalidad(persona.getNacionalidad());
    }

    /**
     * Constructor por defecto, se reutiliza el constructor con parametros para<br>
     * inicializar los atributos con blanco o cero
     */
    public Persona() {
        this("", "", "");
    }

    /**
     * Contructor con parametros
     *
     * @param nombre
     * @param apellido
     * @param nacionalidad
     */
    public Persona(String nombre, String apellido, String nacionalidad) {

        // Validar el valor, con el metodo
        if (valNombre(nombre)) {
            this.nombre = nombre;
        } else {
            this.nombre = "";
        }

        // Validar el valor, con el metodo
        if (valApellido(apellido)) {
            this.apellido = apellido;
        } else {
            this.apellido = "";
        }

        // Validar el valor, con el metodo
        if (valNacionalidad(nacionalidad)) {
            this.nacionalidad = nacionalidad;
        } else {
            this.nacionalidad = "";
        }

    }

    /**
     * Retorna los datos completos de la Persona, en un formato conveniente
     *
     * @return
     */
    @Override
    public String toString() {
        return "Persona [" + nombre + ", " + apellido + ", " + nacionalidad + "]";
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
        if (valNombre(nombre)) {
            this.nombre = nombre;
        } else {
            this.nombre = "";
        }
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        if (valApellido(apellido)) {
            this.apellido = apellido;
        } else {
            this.apellido = "";
        }
    }

    /**
     * @return the nacionalidad
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * @param nacionalidad the nacionalidad to set
     */
    public void setNacionalidad(String nacionalidad) {
        if (valNacionalidad(nacionalidad)) {
            this.nacionalidad = nacionalidad;
        } else {
            this.nacionalidad = "";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Persona other = (Persona) obj;

        // Comparación de atributos para determinar la igualdad de contenido
        return Objects.equals(this.getNombre(), other.getNombre())
                && Objects.equals(this.getApellido(), other.getApellido())
                && Objects.equals(this.getNacionalidad(), other.getNacionalidad());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getApellido(), getNacionalidad());
    }
}
