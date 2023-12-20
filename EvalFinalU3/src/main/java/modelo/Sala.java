package modelo;

import java.util.Objects;

/**
 *
 * @author manager
 */
public class Sala {

    private Encargado encargado;

    private String nombre;
    private int cantLamparas;
    private int tempCelsius;
    private boolean tieneCC; // Cierre Centralizado
    private boolean tieneACI; // alarma contra incendio

    public static boolean valSala(Sala sala) {

        return valEncargado(sala.getEncargado())
                && valNombre(sala.getNombre())
                && valCantLamparas(sala.getCantLamparas())
                && valTempCelcius(sala.getTempCelsius())
                && valTieneCC(sala.getTieneCC())
                && valTieneACI(sala.getTieneACI());
    }

    // Validar
    public static boolean valEncargado(Encargado encargado) {

        return (encargado != null);
    }

    // Validar
    // Podriamos colocar, una lista de nombre o lugares disponibles para validar
    public static boolean valNombre(String nombre) {

        return (nombre != null) && nombre.length() > 0;
    }

    public static boolean valCantLamparas(int valor) {

        return (valor >= 0) && (valor <= 100);
    }

    // Validar
    public static boolean valTempCelcius(int valor) {

        return (valor >= -10) && (valor <= 40);
    }

    // Validar
    public static boolean valAnionIngreso(int anioIngreso) {

        return (anioIngreso >= 1970) && (anioIngreso <= 2023);
    }

    // Validar
    public static boolean valTieneCC(boolean valor) {

        return true;
    }

    // Validar
    public static boolean valTieneACI(boolean valor) {

        return true;
    }

    /**
     * Constructor por defectos, se reutiliza el constructor con parametros para<br>
     * inicializar el blanco o cero los atributos
     */
    public Sala() {
        this(new Encargado(), "", 0, 0, false, false);
    }

    /**
     * Constructor con parametros, para inicializar la clase
     *
     * @param encargado
     * @param nombre
     * @param cantLamparas
     * @param tempCelsius
     * @param tieneCC
     * @param tieneACI
     */
    public Sala(Encargado encargado, String nombre, int cantLamparas, int tempCelsius, boolean tieneCC, boolean tieneACI) {
        if (valEncargado(encargado)) {
            this.encargado = encargado;
        } else {
            this.encargado = new Encargado(); // Dejar un encargado en "blanco"
        }

        if (valNombre(nombre)) {
            this.nombre = nombre;
        } else {
            this.nombre = "";
        }

        if (valCantLamparas(cantLamparas)) {
            this.cantLamparas = cantLamparas;
        } else {
            this.cantLamparas = 0;
        }

        if (valTempCelcius(cantLamparas)) {
            this.tempCelsius = tempCelsius;
        } else {
            this.tempCelsius = 0;
        }

        if (valTieneCC(tieneCC)) {
            this.tieneCC = tieneCC;
        } else {
            this.tieneCC = false;
        }

        if (valTieneACI(tieneCC)) {
            this.tieneACI = tieneACI;
        } else {
            this.tieneACI = false;
        }
    }

    /**
     * @return the encargado
     */
    public Encargado getEncargado() {
        return encargado;
    }

    /**
     * @param encargado the encargado to set
     */
    public void setEncargado(Encargado encargado) {
        if (valEncargado(encargado)) {
            this.encargado = encargado;
        } else {
            this.encargado = new Encargado(); // Dejar un encargado en "blanco"
        }
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
     * @return the cantLamparas
     */
    public int getCantLamparas() {
        return cantLamparas;
    }

    /**
     * @param cantLamparas the cantLamparas to set
     */
    public void setCantLamparas(int cantLamparas) {
        if (valCantLamparas(cantLamparas)) {
            this.cantLamparas = cantLamparas;
        } else {
            this.cantLamparas = 0;
        }
    }

    /**
     * @return the tempCelsius
     */
    public int getTempCelsius() {
        return tempCelsius;
    }

    /**
     * @param tempCelsius the tempCelsius to set
     */
    public void setTempCelsius(int tempCelsius) {
        if (valTempCelcius(tempCelsius)) {
            this.tempCelsius = tempCelsius;
        } else {
            this.tempCelsius = 0;
        }
    }

    /**
     * @return the tieneCC
     */
    public boolean getTieneCC() {
        return tieneCC;
    }

    /**
     * @param tieneCC the tieneCC to set
     */
    public void setTieneCC(boolean tieneCC) {
        if (valTieneCC(tieneCC)) {
            this.tieneCC = tieneCC;
        } else {
            this.tieneCC = false;
        }
    }

    /**
     * @return the tieneACI
     */
    public boolean getTieneACI() {
        return tieneACI;
    }

    /**
     * @param tieneACI the tieneACI to set
     */
    public void setTieneACI(boolean tieneACI) {
        if (valTieneACI(tieneCC)) {
            this.tieneACI = tieneACI;
        } else {
            this.tieneACI = false;
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

        Sala other = (Sala) obj;

        // Comparación de atributos para determinar la igualdad de contenido
        return Objects.equals(this.getEncargado(), other.getEncargado())
                && Objects.equals(this.getNombre(), other.getNombre())
                && Objects.equals(this.getCantLamparas(), other.getCantLamparas())
                && Objects.equals(this.getTempCelsius(), other.getTempCelsius())
                && Objects.equals(this.getTieneCC(), other.getTieneCC())
                && Objects.equals(this.getTieneACI(), other.getTieneACI());
    }

    @Override
    public int hashCode() {
        return Objects.hash(encargado, nombre, cantLamparas, tempCelsius,
                tieneCC, tieneACI);
    }

}
