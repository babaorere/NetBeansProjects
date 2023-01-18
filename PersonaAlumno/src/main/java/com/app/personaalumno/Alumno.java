package com.app.personaalumno;

/**
 *
 */
public class Alumno extends Persona {

    private String Carrera;
    private String Facultad;

    public Alumno() {
        super();
        this.Carrera = null;
        this.Facultad = null;
    }

    public Alumno(String Carrera, String Facultad, String rut, String nombres, String apellidoPaterno, String apellidoMaterno, String fechaDeNacimiento, int peso, double estatura, char sexo, String direcccion) {
        super(rut, nombres, apellidoPaterno, apellidoMaterno, fechaDeNacimiento, peso, estatura, sexo, direcccion);
        this.Carrera = Carrera;
        this.Facultad = Facultad;
    }

    public String StrDatos() {
        return DatosBasico() + ", Carrera= " + Carrera + ", Facultad=" + Facultad;
    }

    /**
     * @return the Carrera
     */
    public String getCarrera() {
        return Carrera;
    }

    /**
     * @param Carrera the Carrera to set
     */
    public void setCarrera(String Carrera) {
        this.Carrera = Carrera;
    }

    /**
     * @return the Facultad
     */
    public String getFacultad() {
        return Facultad;
    }

    /**
     * @param Facultad the Facultad to set
     */
    public void setFacultad(String Facultad) {
        this.Facultad = Facultad;
    }

}
