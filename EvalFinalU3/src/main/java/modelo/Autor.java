package modelo;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * El Autor, al que se le debe considerar su nombre y apellido, RUT o DNI y nacionalidad. <br>
 * Se usa la clase Persona para aprovechar la reutilizacion de codigo, ya que Persona tiene <br>
 * los atributos "nombre", "apellido" y "nacionalidad"
 */
public class Autor extends Persona {

    // Atributos privados de la clase, adicionales a la clase Persona
    private String rut;

    // Validar
    public static boolean valAutor(Autor autor) {

        return Persona.valNombre(autor.getNombre())
                && Persona.valApellido(autor.getApellido())
                && Persona.valNacionalidad(autor.getNacionalidad())
                && valRut(autor.getRut());
    }

    // Validar, solo se valida el formato, no el digito verivicador <br>
    // se utiliza una expresion regular para la validacion
    public static boolean valRut(String rut) {

        return Pattern.compile("\\d{6,8}-\\d").matcher(rut).matches();
    }

    /**
     * Constructor por defecto, se reutiliza el constructor con parametros para<br>
     * inicializar los atributos con blanco o cero
     */
    public Autor() {
        this("", "", "", "");
    }

    /**
     * Contructor con parametros
     *
     * @param nombre
     * @param apellido
     * @param Nacionalidad
     * @param rut
     * @pa * @param rut
     */
    public Autor(String nombre, String apellido, String Nacionalidad, String rut) {

        // Se llama al constructor de la clase de la cual hereda
        super(nombre, apellido, Nacionalidad);

        // Validar
        if (valRut(rut)) {
            this.rut = rut;
        } else {
            this.rut = "";
        }
    }

    /**
     * Retorna los datos completos del Autor, en un formato conveniente
     *
     * @return
     */
    @Override
    public String toString() {
        return "Autor [" + getNombre() + ", " + getApellido() + ", " + getRut() + getNacionalidad() + "]";
    }

    /**
     * @return the rut
     */
    public String getRut() {
        return rut;
    }

    /**
     * @param rut the rut to set
     */
    public void setRut(String rut) {
        if (valRut(rut)) {
            this.rut = rut;
        } else {
            this.rut = "";
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

        Autor other = (Autor) obj;

        // Comparación de atributos para determinar la igualdad de contenido
        return Objects.equals(this.getNombre(), other.getNombre())
                && Objects.equals(this.getApellido(), other.getApellido())
                && Objects.equals(this.getNacionalidad(), other.getNacionalidad())
                && Objects.equals(this.getRut(), other.getRut());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getApellido(), getNacionalidad(), getRut());
    }

}
