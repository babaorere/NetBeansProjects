/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 *
 * @author Capip Sistemas C.A.
 */
public final class TblCreditoAdicional implements ID<TblCreditoAdicional> {

    public static Comparator<TblCreditoAdicional> getComparator() {
        return new Comparator<TblCreditoAdicional>() {
            @Override
            public int compare(TblCreditoAdicional o1, TblCreditoAdicional o2) {
                return o1.toString().compareTo(o2.toString());
            }
        };
    }

    private final int id;
    private final String soporte;
    private final String descripcion;
    private final BigDecimal monto;
    private final java.sql.Date fecha;
    private final java.sql.Date fechaGen;

    public TblCreditoAdicional(int _id, String _soporte, String _descripcion, BigDecimal _monto,
        java.sql.Date _fecha, java.sql.Date _fechaGen) {
        this.id = _id;
        this.soporte = _soporte;
        this.descripcion = _descripcion;
        this.monto = _monto;
        this.fecha = _fecha;
        this.fechaGen = _fechaGen;
    }

    @Override
    public int compareTo(TblCreditoAdicional _otro) {
        return toString().compareTo(_otro.toString());
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the fecha
     */
    public java.sql.Date getFecha() {
        return fecha;
    }

    /**
     * @return the id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @return the soporte
     */
    public String getSoporte() {
        return soporte;
    }

    @Override
    public String toString() {
        return soporte;
    }

    /**
     * @return the fechaGen
     */
    public java.sql.Date getFechaGen() {
        return fechaGen;
    }

}
