package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 *
 */
public class Cliente implements Cliente_IF, Serializable {

    private static final String FNAME = "clientes.dat";

    public static final int MIN_LOG_CEDULA = 10;
    public static final int MAX_LOG_CEDULA = 10;

    static long nextId = 1;

    private final long id;
    private String cedula;
    private String nombre;
    private boolean vigente;


    public Cliente(String inCedula, String inNombre, boolean inVigente) {
        this.id = nextId++;
        this.cedula = inCedula;
        this.nombre = inNombre;
        this.vigente = inVigente;
    }


    public Cliente(Cliente inOther) {
        this.id = inOther.id;
        this.cedula = inOther.cedula;
        this.nombre = inOther.nombre;
        this.vigente = inOther.vigente;
    }


    public static boolean ReadFromFile(ArrayList<Cliente> listClte) {
        return Cliente_IF.ReadFromFile(listClte);
    }


    public static boolean WriteToFile(ArrayList<Cliente> listClte) {

        return Cliente_IF.WriteToFile(listClte);
    }


    @Override
    public String toString() {
        return String.valueOf(id) + " : " + cedula + " : " + nombre + " : " + String.valueOf(vigente);
    }


    /**
     * @param listClte
     * @return the list
     */
    public static ArrayList<Cliente> getCopyList(ArrayList<Cliente> listClte) {

        ArrayList<Cliente> aux = new ArrayList<>();

        listClte.forEach((c) -> {
            aux.add(new Cliente(c));
        });

        return aux;
    }


    /**
     * @return the id
     */
    public long getId() {
        return id;
    }


    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }


    private static boolean validaRut(String rut) {
        return Pattern.compile("^[0-9]+-[0-9kK]{1}$").matcher(rut).matches();
    }


    public static boolean ValCedula(String inCedula) {
        return (inCedula != null) && (inCedula.length() >= MIN_LOG_CEDULA) && (inCedula.length() <= MAX_LOG_CEDULA) && validaRut(inCedula);
    }


    /**
     * @param inCedula the cedula to set
     */
    public void setCedula(String inCedula) {
        if (ValCedula(inCedula)) {
            this.cedula = inCedula;
        }
    }


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }


    public static boolean ValNombre(String inNombre) {
        return (inNombre != null) && (inNombre.length() > 0);
    }


    /**
     * @param inNombre the nombre to set
     */
    public void setNombre(String inNombre) {
        if (ValNombre(inNombre)) {
            this.nombre = inNombre;
        }
    }


    /**
     * @return the vigente
     */
    public boolean isVigente() {
        return vigente;
    }


    /**
     * @param inVigente the vigente to set
     */
    public void setVigente(boolean inVigente) {
        this.vigente = inVigente;
    }

}
