package con.manager.datos;

import java.util.Objects;

/**
 *
 * @author manager
 */
public class TDetalleFac implements Comparable<TDetalleFac> {

    private int codigo;
    private int codFactura;
    private int codProducto;
    private int precioProducto;
    private int cantidadVendida;

    public TDetalleFac(int codigo, int codFactura, int codProducto, int precioProducto, int cantidadVendida) {
        this.codigo = codigo;
        this.codFactura = codFactura;
        this.codProducto = codProducto;
        this.precioProducto = precioProducto;
        this.cantidadVendida = cantidadVendida;
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

        TDetalleFac aux = (TDetalleFac) other;
        return this.getCodigo() == aux.getCodigo()
                && this.getCodFactura() == aux.getCodFactura();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getCodFactura(), getCodProducto());
    }

    @Override
    public String toString() {
        return String.format("%5d CodF= %5d, CodP= %5d", getCodigo(), getCodFactura(), getCodProducto());
    }

    @Override
    public int compareTo(TDetalleFac other) {
        if (this.getCodigo() == other.getCodigo()) {
            return Integer.compare(getCodFactura(), other.getCodFactura());
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
     * @return the codFactura
     */
    public int getCodFactura() {
        return codFactura;
    }

    /**
     * @param codFactura the codFactura to set
     */
    public void setCodFactura(int codFactura) {
        this.codFactura = codFactura;
    }

    /**
     * @return the codProducto
     */
    public int getCodProducto() {
        return codProducto;
    }

    /**
     * @param codProducto the codProducto to set
     */
    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    /**
     * @return the precioProducto
     */
    public int getPrecioProducto() {
        return precioProducto;
    }

    /**
     * @param precioProducto the precioProducto to set
     */
    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    /**
     * @return the cantidadVendida
     */
    public int getCantidadVendida() {
        return cantidadVendida;
    }

    /**
     * @param cantidadVendida the cantidadVendida to set
     */
    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

}
