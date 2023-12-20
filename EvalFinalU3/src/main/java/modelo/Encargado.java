package modelo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Existe un encargado por cada sala que se identifica por:<br>
 * - Nombre <br>
 * - RUT<br>
 * - Profesión<br>
 * - Año de ingreso al museo<br>
 *
 * Se va a reutilizar la clase Persona, porque tiene atributos que comparte con un Encargado
 */
public class Encargado {

    // Lista de algunas profesiones permitidas en un museo
    private static final String[] strArr = {
        "CURADOR",
        "ARQUEOLOGO",
        "HISTORIADOR",
        "GUÍA",
        "EDUCADOR",
        "RESTAURADOR",
        "CONSERVADOR",
        "INVESTIGADOR",
        "ADMINISTRADOR",
        "TÉCNICO",
        "MANTENIMIENTO"};

    // Lista de profesiones permitidas en un museo
    private static final List<String> strList = Arrays.asList(strArr);

    // Atrubutos privados de la clase
    private Persona persona;
    private String rut;
    private String profesion;
    private int anioIngreso;

    // Validar
    public static boolean valEncargado(Encargado encargado) {

        return Persona.valPersona(encargado.getPersona())
                && valRut(encargado.getRut())
                && valProfesion(encargado.getProfesion())
                && valAnionIngreso(encargado.getAnioIngreso());
    }

    // Validar, solo se valida el formato, no el digito v. <br>
    // se utiliza una expresion regular
    public static boolean valRut(String rut) {

        return Pattern.compile("\\d{6,8}-\\d").matcher(rut).matches();
    }

    // Validar
    public static boolean valProfesion(String profesion) {

        for (String item : strList) {
            if (item.equalsIgnoreCase(profesion)) {
                return true;
            }
        }

        return false;
    }

    // Validar
    public static boolean valAnionIngreso(int anioIngreso) {

        return (anioIngreso >= 1970) && (anioIngreso <= 2023);
    }

    /**
     * Constructor por defectos, se reutiliza el constructor con parametros para<br>
     * inicializar los atributos con blanco o cero
     */
    public Encargado() {
        this(new Persona(), "", "", 0);
    }

    /**
     * Constructor con parametros, para inicializar la clase
     *
     * @param rut
     * @param profesion
     * @param anioIngreso
     */
    public Encargado(Persona persona, String rut, String profesion, int anioIngreso) {

        // se procede a validar todas las entradas, en caso de error, se deja el campo en blanco o cero
        if ((Persona.valPersona(persona))) {
            this.persona = persona;
        } else {
            this.persona = new Persona(); // persona "vacia"
        }

        if (valRut(rut)) {
            this.rut = rut;
        } else {
            this.rut = "";
        }

        if (valProfesion(profesion)) {
            this.profesion = profesion;
        } else {
            this.profesion = "";
        }

        if (valAnionIngreso(anioIngreso)) {
            this.anioIngreso = anioIngreso;
        } else {
            this.anioIngreso = anioIngreso;
        }
    }

    @Override
    public String toString() {
        return "Encargado [" + getPersona() + ", " + getRut() + ", " + getProfesion() + ", " + getAnioIngreso() + "]";
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

    /**
     * @return the profesion
     */
    public String getProfesion() {
        return profesion;
    }

    /**
     * @param profesion the profesion to set
     */
    public void setProfesion(String profesion) {
        if (valProfesion(profesion)) {
            this.profesion = profesion;
        } else {
            this.profesion = "";
        }
    }

    /**
     * @return the anioIngreso
     */
    public int getAnioIngreso() {
        return anioIngreso;
    }

    /**
     * @param anioIngreso the anioIngreso to set
     */
    public void setAnioIngreso(int anioIngreso) {
        if (valAnionIngreso(anioIngreso)) {
            this.anioIngreso = anioIngreso;
        } else {
            this.anioIngreso = 0;
        }
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Encargado other = (Encargado) obj;

        // Comparación de atributos para determinar la igualdad de contenido
        return Objects.equals(this.getPersona(), other.getPersona())
                && Objects.equals(this.getRut(), other.getRut())
                && Objects.equals(this.getProfesion(), other.getProfesion())
                && Objects.equals(this.getAnioIngreso(), other.getAnioIngreso());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersona(), getRut(), getProfesion(), getAnioIngreso());
    }
}
