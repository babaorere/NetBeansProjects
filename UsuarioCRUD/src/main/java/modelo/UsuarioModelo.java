package modelo;

/**
 *
 * @author johii
 */
public class UsuarioModelo {

    private static String FileName = "johana.txt";

    private String nombre;
    private String apellido;
    private String edad;
    private String tipodeSangre;

    public UsuarioModelo() {
        this(null, null, null, null);
    }

    public UsuarioModelo(String nombre, String apellido, String edad, String inTipodeSangre) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.tipodeSangre = inTipodeSangre;
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
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the edad
     */
    public String getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(String edad) {
        this.edad = edad;
    }

    /**
     * @return the FileName
     */
    public static String getFileName() {
        return FileName;
    }

    /**
     * @return the tipodeSangre
     */
    public String getTipodeSangre() {
        return tipodeSangre;
    }

    /**
     * @param tipodeSangre the tipodeSangre to set
     */
    public void setTipodeSangre(String tipodeSangre) {
        this.tipodeSangre = tipodeSangre;
    }

    /**
     * @param FileName the FileName to set
     */
    public static void setFileName(String inFileName) {
        FileName = inFileName;
    }

}
