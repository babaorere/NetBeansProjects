package modelo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author manager
 */
public class Pintura {

    // Arraya de algunas tecnicas permitidas
    private static final String[] tecArr = {"ACUARELA", "OLEO", "FRESCO", "TEMPLE", "PUNTILLISMO"};

    // Lista de nacionalidades permitidas
    private static final List<String> tecList = Arrays.asList(tecArr);

    // Array de algunos generos permitidas
    private static final String[] genArr = {"retrato", "desnudo", "naturaleza muerta", "paisajista", "historica"};

    // Lista de algunos generos permitidoss
    private static final List<String> genList = Arrays.asList(genArr);

    /// para el Autor, utilizamos la clase Autor, asi es mas conveniente 
    private Autor autor;

    private String nombre;
    private String numId;
    private String tecnica;
    private String genero;
    private int anioCreacion;
    private Tamanio tam; // tamaño de la pintura
    private Sala ubicacion;

    // Validar
    public static boolean valPintura(Pintura pintura) {

        return Autor.valAutor(pintura.getAutor())
                && valNombre(pintura.getNombre())
                && valNumId(pintura.getNumId())
                && valTecnica(pintura.getTecnica())
                && valGenero(pintura.getGenero())
                && valAnionCreacion(pintura.getAnioCreacion())
                && Tamanio.valTamanio(pintura.getTam())
                && Sala.valSala(pintura.getUbicacion());
    }

    // Validar
    public static boolean valNombre(String nombre) {

        return (nombre != null) && nombre.length() > 0;
    }

    // Validar
    public static boolean valNumId(String valor) {

        return (valor != null) && valor.length() >= 5;
    }

    // Validar las tecnicas permitidas, para la Pintura
    public static boolean valTecnica(String valor) {

        for (String item : tecList) {
            if (item.equalsIgnoreCase(valor)) {
                return true;
            }
        }

        return false;
    }

    // Validar los generos permitidos, para la Pintura
    public static boolean valGenero(String valor) {

        for (String item : genList) {
            if (item.equalsIgnoreCase(valor)) {
                return true;
            }
        }

        return false;
    }

    // Validar
    public static boolean valAnionCreacion(int valor) {

        return (valor >= 1000) && (valor <= 2023);
    }

    /**
     * Constructor por defecto, se reutiliza el constructor con parametros para<br>
     * inicializar el blanco los atributos
     */
    public Pintura() {
        this(new Autor(), "", "", "", "", 0, new Tamanio(), new Sala());
    }

    /**
     * Contructor con parametros
     *
     * @param autor
     * @param nombre
     * @param numId
     * @param tecnica
     * @param genero
     * @param anioCreacion
     * @param tam tamaño de la pintura
     * @param ubicacion
     */
    public Pintura(Autor autor, String nombre, String numId, String tecnica, String genero, int anioCreacion, Tamanio tam, Sala ubicacion) {

        if (Autor.valAutor(autor)) {
            this.autor = autor;
        } else {
            this.autor = new Autor();
        }

        if (valNombre(nombre)) {
            this.nombre = nombre;
        } else {
            this.nombre = "";
        }

        if (valNumId(numId)) {
            this.numId = numId;
        } else {
            this.numId = "";
        }

        if (valTecnica(tecnica)) {
            this.tecnica = tecnica;
        } else {
            this.tecnica = "";
        }

        if (valGenero(genero)) {
            this.genero = genero;
        } else {
            this.genero = "";
        }

        if (valAnionCreacion(anioCreacion)) {
            this.anioCreacion = anioCreacion;
        } else {
            this.anioCreacion = 0;
        }

        // tamaño de la pintura
        if (Tamanio.valTamanio(tam)) {
            this.tam = tam;
        } else {
            this.tam = new Tamanio();
        }

        if (Sala.valSala(ubicacion)) {
            this.ubicacion = ubicacion;
        } else {
            this.ubicacion = new Sala();
        }
    }

    /**
     * @return the autor
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
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
     * @return the numId
     */
    public String getNumId() {
        return numId;
    }

    /**
     * @param numId the numId to set
     */
    public void setNumId(String numId) {
        this.numId = numId;
    }

    /**
     * @return the tecnica
     */
    public String getTecnica() {
        return tecnica;
    }

    /**
     * @param tecnica the tecnica to set
     */
    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the anioCreacion
     */
    public int getAnioCreacion() {
        return anioCreacion;
    }

    /**
     * @param anioCreacion the anioCreacion to set
     */
    public void setAnioCreacion(int anioCreacion) {
        this.anioCreacion = anioCreacion;
    }

    /**
     * @return the tam
     */
    public Tamanio getTam() {
        return tam;
    }

    /**
     * @param tam the tam to set
     */
    public void setTam(Tamanio tam) {
        if (Tamanio.valTamanio(tam)) {
            this.tam = tam;
        } else {
            this.tam = new Tamanio();
        }
    }

    /**
     * @return the ubicacion
     */
    public Sala getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(Sala ubicacion) {
        if (Sala.valSala(ubicacion)) {
            this.ubicacion = ubicacion;
        } else {
            this.ubicacion = new Sala();
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

        Pintura other = (Pintura) obj;

        // Comparación de atributos para determinar la igualdad de contenido
        return Objects.equals(this.autor, other.autor)
                && Objects.equals(this.nombre, other.nombre)
                && Objects.equals(this.numId, other.numId)
                && Objects.equals(this.tecnica, other.tecnica)
                && Objects.equals(this.genero, other.genero)
                && this.anioCreacion == other.anioCreacion
                && Objects.equals(this.tam, other.tam)
                && Objects.equals(this.ubicacion, other.ubicacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(autor, nombre, numId, tecnica, genero,
                anioCreacion, tam, ubicacion);
    }
}
