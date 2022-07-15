package com.tayrona;

import java.time.LocalDate;

/**
 *
 */
public class EmpVenta extends Empleado {

    private Integer metaVentas;
    private Integer porcNonificacion;

    public EmpVenta() {
    }

    public EmpVenta(String identificacion, String nombre, LocalDate fechaNac, SexoEnum sexo,
        EdoCivilEnum edoCivil, Integer numHijos, GradoEscolaridadEnum gradoEsc, EpsEnum EPSeNUM, CajaCompEnum cajaComp,
        Integer salario, Integer ahorro, TipoContratoEnum inTipoContrato, CategoriaEnum inCategoria, Integer metaVentas, Integer porcNonificacion) {
        super(identificacion, nombre, fechaNac, sexo, edoCivil, numHijos, gradoEsc, EPSeNUM, cajaComp, salario, ahorro, inTipoContrato, inCategoria);
        this.metaVentas = metaVentas;
        this.porcNonificacion = porcNonificacion;
    }

    /**
     * @return the metaVentas
     */
    @Override
    public Integer getMetaVentas() {
        return metaVentas;
    }

    /**
     * @param metaVentas the metaVentas to set
     */
    @Override
    public void setMetaVentas(Integer metaVentas) {
        this.metaVentas = metaVentas;
    }

    /**
     * @return the porcNonificacion
     */
    @Override
    public Integer getPorcNonificacion() {
        return porcNonificacion;
    }

    /**
     * @param porcNonificacion the porcNonificacion to set
     */
    @Override
    public void setPorcNonificacion(Integer porcNonificacion) {
        this.porcNonificacion = porcNonificacion;
    }

}
