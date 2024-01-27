package com.tayrona;

import java.time.LocalDate;

/**
 *
 */
public interface InterfazEmpleado {

    public String getIdentificacion();

    public void setIdentificacion(String identificacion);

    public String getNombre();

    public void setNombre(String nombre);

    public LocalDate getFechaNac();

    public void setFechaNac(LocalDate fechaNac);

    public SexoEnum getSexo();

    public void setSexo(SexoEnum sexo);

    public EdoCivilEnum getEdoCivil();

    public void setEdoCivil(EdoCivilEnum edoCivil);

    public Integer getNumHijos();

    public void setNumHijos(Integer numHijos);

    public GradoEscolaridadEnum getGradoEsc();

    public void setGradoEsc(GradoEscolaridadEnum gradoEsc);

    public EpsEnum getEpsEnum();

    public void setEpsEnum(EpsEnum EPSeNUM);

    public CajaCompEnum getCajaComp();

    public void setCajaComp(CajaCompEnum cajaComp);

    public Integer getSalario();

    public void setSalario(Integer salario);

    public Integer getAhorro();

    public void setAhorro(Integer ahorro);

    public TipoContratoEnum getTipoCont();

    public void setTipoCont(TipoContratoEnum tipoCont);

    public CategoriaEnum getCategoria();

    public void setCategoria(CategoriaEnum categoria);

    public Integer getMetaVentas();

    public void setMetaVentas(Integer metaVentas);

    public Integer getPorcNonificacion();

    public void setPorcNonificacion(Integer porcNonificacion);
}
