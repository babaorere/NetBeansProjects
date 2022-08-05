package con.manager.datos;

import static java.lang.Integer.min;
import java.util.Objects;

/**
 *
 * @author manager
 */
public final class TCliente implements Comparable<TCliente> {

    private static TNodo head;
    private static TNodo nodoActual;
    private static int num;

    // Inicializar variables estaticas
    static {
        head = null;
    }

    public static class TNodo {

        private final TCliente cliente;
        private TNodo enlaceSig;

        public TNodo(TCliente cliente) {
            this.cliente = cliente;
            this.enlaceSig = null;
        }

        /**
         * @return the enlaceSig
         */
        public TNodo getEnlaceSig() {
            return enlaceSig;
        }

        /**
         * @return the cliente
         */
        public TCliente getCliente() {
            return cliente;
        }

        /**
         * @param enlaceSig the enlaceSig to set
         */
        public void setEnlaceSig(TNodo enlaceSig) {
            this.enlaceSig = enlaceSig;
        }

    }

    private int codigo;
    private String nombre;
    private int edad;

    /**
     * Constructor
     *
     * @param codigo
     * @param nombre
     * @param edad
     */
    public TCliente(int codigo, String nombre, int edad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edad = edad;
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

        TCliente aux = (TCliente) other;
        return this.getCodigo() == aux.getCodigo()
                && this.getNombre().equals(aux.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getNombre());
    }

    @Override
    public String toString() {
        return String.format("%5d %32s %2d", getCodigo(), getNombre().substring(0, min(32, getNombre().length())), getEdad());
    }

    /**
     * Para comparar, y poder ordenar objetos
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(TCliente other) {
        if (this.getCodigo() == other.getCodigo()) {
            return this.getNombre().compareTo(other.getNombre());
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
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     *
     * @param nodo
     * @return
     */
    public static TNodo InsertarLista(TNodo nodo) {

        nodo.enlaceSig = head;
        head = nodo;
        nodoActual = nodo;

        return nodo;
    }

    /**
     * @return the head
     */
    public static TNodo getHead() {
        return head;
    }

    public static void Actualizar(int codigo, String strNombre, int edad) {
        getNodoActual().cliente.setCodigo(codigo);
        getNodoActual().cliente.setNombre(strNombre);
        getNodoActual().cliente.setEdad(edad);
    }

    /**
     * @return the nodoActual
     */
    public static TNodo getNodoActual() {
        return nodoActual;
    }

    /**
     * @param codigo
     * @return
     */
    public static TNodo Buscar(int codigo) {

        TNodo p = head;

        while (p != null) {
            if (p.cliente.getCodigo() == codigo) {
                nodoActual = p;
                return p;
            }

            p = p.enlaceSig;
        }

        // no encontrado
        return null;
    }

    public static TNodo Eliminar(int codigo) {

        if (head == null) {
            return null;
        }

        TCliente.TNodo nodoOut;

        // Probar con el primero de la lista
        if (head.getCliente().getCodigo() == codigo) {
            nodoOut = head;
            head = head.enlaceSig;
            nodoActual = head;
        } else {
            TCliente.TNodo p = head;
            while ((p.enlaceSig != null) && (p.enlaceSig.getCliente().getCodigo() != codigo)) {
                p = p.enlaceSig;
            }

            if (p.enlaceSig == null) {
                nodoOut = null;
            } else {
                nodoOut = p.enlaceSig;
                p.enlaceSig = p.enlaceSig.enlaceSig; // Aqui se borra el elemento
                nodoActual = p;
            }

        }

        return nodoOut;
    }

}
