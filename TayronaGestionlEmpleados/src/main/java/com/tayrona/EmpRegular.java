package com.tayrona;

import java.time.LocalDate;

/**
 *
 * @author manager
 */
public class EmpRegular extends Empleado {

    // Default constructor
    public EmpRegular() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public EmpRegular(String identificacion, String nombre, LocalDate fechaNac, SexoEnum sexo, EdoCivilEnum edoCivil, Integer numHijos,
        GradoEscolaridadEnum gradoEsc, EpsEnum EPSeNUM, CajaCompEnum cajaComp, Integer salario, Integer ahorro, TipoContratoEnum inTipoContrato, CategoriaEnum inCategoria) {
        super(identificacion, nombre, fechaNac, sexo, edoCivil, numHijos, gradoEsc, EPSeNUM, cajaComp, salario, ahorro, inTipoContrato, inCategoria);
    }

    @Override
    public Integer getMetaVentas() {
        return 0;
    }

    @Override
    public void setMetaVentas(Integer metaVentas) {
    }

    @Override
    public Integer getPorcNonificacion() {
        return 0;
    }

    @Override
    public void setPorcNonificacion(Integer porcNonificacion) {

    }

}
