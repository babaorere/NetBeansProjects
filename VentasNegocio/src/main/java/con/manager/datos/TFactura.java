package con.manager.datos;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author manager
 */
public class TFactura implements Comparable<TFactura> {

    private int codigo;
    private int codVendedor;
    private int codCliente;
    private LocalDate fecha;
    private int total;

    public TFactura(int codigo, int codVendedor, int codCliente, LocalDate fecha, int total) {
        this.codigo = codigo;
        this.codVendedor = codVendedor;
        this.codCliente = codCliente;
        this.fecha = fecha;
        this.total = total;
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

        TFactura aux = (TFactura) other;
        return this.getCodigo() == aux.getCodigo()
                && this.getTotal() == aux.getTotal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getCodVendedor(), getCodCliente());
    }

    @Override
    public String toString() {
        return String.format("%5d CodV= %5d, CodC= %5d", getCodigo(), getCodVendedor(), getCodCliente());
    }

    @Override
    public int compareTo(TFactura other) {
        if (this.getCodigo() == other.getCodigo()) {
            return 0;
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
     * @return the codVendedor
     */
    public int getCodVendedor() {
        return codVendedor;
    }

    /**
     * @param codVendedor the codVendedor to set
     */
    public void setCodVendedor(int codVendedor) {
        this.codVendedor = codVendedor;
    }

    /**
     * @return the codCliente
     */
    public int getCodCliente() {
        return codCliente;
    }

    /**
     * @param codCliente the codCliente to set
     */
    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

}
