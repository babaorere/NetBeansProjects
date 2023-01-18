package com.app.personaalumno;

/**
 *
 */
public class Persona {

    private String rut;
    private String nombres;
    private String ApellidoPaterno;
    private String apellidoMaterno;
    private String fechaDeNacimiento;
    private int peso;
    private double estatura;
    private char sexo;
    private String direcccion;

    public Persona() {
        this.rut = null;
        this.nombres = null;
        this.ApellidoPaterno = null;
        this.apellidoMaterno = null;
        this.fechaDeNacimiento = null;
        this.peso = 0;
        this.estatura = 0.00;
        this.sexo = 0;
        this.direcccion = null;
    }

    public Persona(String rut,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String fechaDeNacimiento,
            int peso,
            double estatura,
            char sexo,
            String direcccion) {
        this.rut = rut;
        this.nombres = nombres;
        this.ApellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.peso = peso;
        this.estatura = estatura;
        this.sexo = sexo;
        this.direcccion = direcccion;
    }

    public String DatosBasico() {
        return nombres + ", " + ApellidoPaterno + ", " + apellidoMaterno + ", peso Kg.= " + peso + ", estatura cm." + estatura;
    }

    public int aumentarPeso(int x) {
        if (x > 0) {
            peso += x;
        }

        return peso;
    }

    public double disminuyaEstatura(double y) {
        if (y > 0) {
            estatura += y;
        }

        return peso;
    }

    public int PesoGramos() {
        try {
            if (peso <= 0) {
                throw new Exception("Peso no inicializado");
            }

        } catch (Exception e) {
            System.out.println("Error peso invalido: " + e.toString());
            peso = 0;
        }

        return peso * 1000;
    }

    public String StrSexo() {
        try {
            if ((sexo != 'F') && (sexo != 'M')) {
                throw new Exception("Sexo no inicializado");
            }

        } catch (Exception e) {
            System.out.println("Error sexo invalido: " + e.toString());
            sexo = 0;
        }

        return sexo == 'F' ? "Femenino" : "Masculino";
    }

    /**
     *
     */
    public String getRut() {

        return rut;
    }

    /**
     *
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     *
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     *
     */
    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    /**
     *
     */
    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    /**
     *
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     *
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     *
     */
    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    /**
     *
     */
    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    /**
     *
     */
    public int getPeso() {
        return peso;
    }

    /**
     *
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     *
     */
    public double getEstatura() {
        return estatura;
    }

    /**
     *
     */
    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    /**
     *
     */
    public char getSexo() {
        return sexo;
    }

    /**
     *
     */
    public void setSexo(char sexo) {
        if ((sexo == 'F') || (sexo == 'M')) {
            this.sexo = sexo;
        }
    }

    /**
     *
     */
    public String getDirecccion() {
        return direcccion;
    }

    /**
     *
     */
    public void setDirecccion(String direcccion) {
        this.direcccion = direcccion;
    }

}
