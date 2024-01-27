package com.tayrona;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 */
public abstract class Empleado implements InterfazEmpleado, Serializable {

    private String identificacion;
    private String nombre;
    private LocalDate fechaNac;
    private SexoEnum sexo;
    private EdoCivilEnum edoCivil;
    private Integer numHijos;
    private GradoEscolaridadEnum gradoEsc;
    private EpsEnum epsEnum;
    private CajaCompEnum cajaComp;
    private Integer salario;
    private Integer ahorro;
    private TipoContratoEnum tipoCont;
    private CategoriaEnum categoria;

    public Empleado() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Empleado(String identificacion, String nombre, LocalDate fechaNac, SexoEnum sexo, EdoCivilEnum edoCivil, Integer numHijos,
        GradoEscolaridadEnum gradoEsc, EpsEnum inEpsEnum, CajaCompEnum cajaComp, Integer salario, Integer ahorro,
        TipoContratoEnum inTipoCont, CategoriaEnum inCategoria) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.edoCivil = edoCivil;
        this.numHijos = numHijos;
        this.gradoEsc = gradoEsc;
        this.epsEnum = inEpsEnum;
        this.cajaComp = cajaComp;
        this.salario = salario;
        this.ahorro = ahorro;
        this.tipoCont = inTipoCont;
        this.categoria = inCategoria;
    }

    /**
     * @return the identificacion
     */
    @Override
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    @Override
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the nombre
     */
    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fechaNac
     */
    @Override
    public LocalDate getFechaNac() {
        return fechaNac;
    }

    /**
     * @param fechaNac the fechaNac to set
     */
    @Override
    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    /**
     * @return the sexo
     */
    @Override
    public SexoEnum getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    @Override
    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the edoCivil
     */
    @Override
    public EdoCivilEnum getEdoCivil() {
        return edoCivil;
    }

    /**
     * @param edoCivil the edoCivil to set
     */
    @Override
    public void setEdoCivil(EdoCivilEnum edoCivil) {
        this.edoCivil = edoCivil;
    }

    /**
     * @return the numHijos
     */
    @Override
    public Integer getNumHijos() {
        return numHijos;
    }

    /**
     * @param numHijos the numHijos to set
     */
    @Override
    public void setNumHijos(Integer numHijos) {
        this.numHijos = numHijos;
    }

    /**
     * @return the gradoEsc
     */
    @Override
    public GradoEscolaridadEnum getGradoEsc() {
        return gradoEsc;
    }

    /**
     * @param gradoEsc the gradoEsc to set
     */
    @Override
    public void setGradoEsc(GradoEscolaridadEnum gradoEsc) {
        this.gradoEsc = gradoEsc;
    }

    /**
     * @return the epsEnum
     */
    @Override
    public EpsEnum getEpsEnum() {
        return epsEnum;
    }

    /**
     * @param EPSeNUM the epsEnum to set
     */
    @Override
    public void setEpsEnum(EpsEnum EPSeNUM) {
        this.epsEnum = EPSeNUM;
    }

    /**
     * @return the cajaComp
     */
    @Override
    public CajaCompEnum getCajaComp() {
        return cajaComp;
    }

    /**
     * @param cajaComp the cajaComp to set
     */
    @Override
    public void setCajaComp(CajaCompEnum cajaComp) {
        this.cajaComp = cajaComp;
    }

    /**
     * @return the salario
     */
    @Override
    public Integer getSalario() {
        return salario;
    }

    /**
     * @param inSalario
     */
    @Override
    public void setSalario(Integer inSalario) {
        if (inSalario >= 0) {
            this.salario = inSalario;
        }
    }

    /**
     * @return the ahorro
     */
    @Override
    public Integer getAhorro() {
        return ahorro;
    }

    /**
     * @param inAhorro
     */
    @Override
    public void setAhorro(Integer inAhorro) {
        if (inAhorro >= 0) {
            this.ahorro = inAhorro;
        }
    }

    /**
     * @return the tipoCont
     */
    public TipoContratoEnum getTipoCont() {
        return tipoCont;
    }

    /**
     * @param tipoCont the tipoCont to set
     */
    public void setTipoCont(TipoContratoEnum tipoCont) {
        this.tipoCont = tipoCont;
    }

    /**
     * @return the categoria
     */
    public CategoriaEnum getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaEnum categoria) {
        this.categoria = categoria;
    }

}
