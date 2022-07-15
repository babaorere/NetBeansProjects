package con.manager.datos;

import static java.lang.Integer.min;
import java.util.Objects;

/**
 *
 * @author manager
 */
public class TProducto implements Comparable<TProducto> {

    private static TProducto.TNodo head;
    private static TProducto.TNodo nodoActual;
    private static int num;

    public static class TNodo {

        private final TProducto producto;
        private TNodo enlaceSig;

        public TNodo(TProducto producto) {
            this.producto = producto;
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
        public TProducto getProducto() {
            return producto;
        }

        /**
         * @param enlaceSig the enlaceSig to set
         */
        public void setEnlaceSig(TNodo enlaceSig) {
            this.enlaceSig = enlaceSig;
        }

    }

    private int codigo;
    private String descripción;
    private int stock;
    private int precio;
    private String unidadMedida;

    public TProducto(int codigo, String descripción, int stock, int precio, String unidadMedida) {
        this.codigo = codigo;
        this.descripción = descripción;
        this.stock = stock;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
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

        TProducto aux = (TProducto) other;
        return getCodigo() == aux.getCodigo()
                && getDescripción().equals(aux.getDescripción());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getDescripción());
    }

    @Override
    public String toString() {
        return String.format("%5d %32s %3d", getCodigo(), getDescripción().substring(0, min(32, getDescripción().length())), getStock());
    }

    /**
     * Para comparar, y poder ordenar objetos
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(TProducto other) {
        if (this.getCodigo() == other.getCodigo()) {
            return getDescripción().compareTo(other.getDescripción());
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
     * @return the descripción
     */
    public String getDescripción() {
        return descripción;
    }

    /**
     * @param descripción the descripción to set
     */
    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the precio
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * @return the unidadMedida
     */
    public String getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * @param unidadMedida the unidadMedida to set
     */
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     *
     * @param nodo
     * @return
     */
    public static TProducto.TNodo InsertarLista(TProducto.TNodo nodo) {

        nodo.enlaceSig = getHead();
        head = nodo;
        nodoActual = nodo;

        return nodo;
    }

    /**
     * @return the head
     */
    public static TProducto.TNodo getHead() {
        return head;
    }

    /**
     * @return the nodoActual
     */
    public static TProducto.TNodo getNodoActual() {
        return nodoActual;
    }

    /**
     * @return the num
     */
    public static int getNum() {
        return num;
    }

    public static void Actualizar(int codigo, String strDesc, int stock, int precio, String uniMedida) {
        getNodoActual().producto.setCodigo(codigo);
        getNodoActual().producto.setDescripción(strDesc);
        getNodoActual().producto.setStock(stock);
        getNodoActual().producto.setPrecio(precio);
        getNodoActual().producto.setUnidadMedida(uniMedida);
    }

    /**
     * @param codigo
     * @return
     */
    public static TProducto.TNodo Buscar(int codigo) {

        TProducto.TNodo p = head;

        while (p != null) {
            if (p.producto.getCodigo() == codigo) {
                nodoActual = p;
                return p;
            }

            p = p.enlaceSig;
        }

        // no encontrado
        return null;
    }

    public static TProducto.TNodo Eliminar(int codigo) {

        if (head == null) {
            return null;
        }

        TProducto.TNodo nodoOut;

        // Probar con el primero de la lista
        if (head.getProducto().getCodigo() == codigo) {
            nodoOut = head;
            head = head.enlaceSig;
            nodoActual = head;
        } else {
            TProducto.TNodo p = head;
            while ((p.enlaceSig != null) && (p.enlaceSig.getProducto().getCodigo() != codigo)) {
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
