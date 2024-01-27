package com.webapp.clinicaveterinaria;

/**
 *
 */
public class Mascota {

    // Atributos de clase
    private String nombreMascota;
    private String nombrePropietario;
    private int codigo;
    private int edad;
    private char razaSN;
    private boolean vacunado;

    // Llamamos al constructor con parametros, con valores en blanco o invalidos,
    // para indicar que el objeto esta inicializado, pero se deben actualizar los valores de los atributos
    public Mascota() {
        this("", "", -1, -1, '\0', false);
    }

    public Mascota(String inNombreMascota, String inNombrePropietario, int inCodigo, int inEdad, char inRazaSN, boolean inVacunado) {
        this.nombreMascota = inNombreMascota;
        this.nombrePropietario = inNombrePropietario;
        this.codigo = inCodigo;
        this.edad = inEdad;
        this.razaSN = inRazaSN;
        this.vacunado = inVacunado;
    }

    // Este metodo es llamado por defecto en un print
    @Override
    public String toString() {
        return "{ " + nombreMascota + ", " + nombrePropietario + ", " + codigo + ", " + edad + ", " + razaSN + ", " + vacunado + " }";
    }

    // Este metodo es llamado por defecto en un print
    public String getPropietarioMascota() {
        return "{ propietario: " + nombrePropietario + " mascota => " + nombrePropietario + " }";
    }

    // Este metodo es llamado por defecto en un print
    public float getTotal(float precio, float cantidad) throws Exception {

        if (precio <= 0) {
            throw new Exception("El precio no puede ser negativo");
        }

        if (cantidad <= 0) {
            throw new Exception("La cantidad nopuede ser negativa");
        }

        float total = precio * cantidad;
        return total;
    }

    /**
     * @return the nombreMascota
     */
    public String getNombreMascota() {
        return nombreMascota;
    }

    /**
     * @param nombreMascota the nombreMascota to set
     */
    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    /**
     * @return the nombrePropietario
     */
    public String getNombrePropietario() {
        return nombrePropietario;
    }

    /**
     * @param nombrePropietario the nombrePropietario to set
     */
    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
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
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @return the razaSN
     */
    public char getRazaSN() {
        return razaSN;
    }

    /**
     * @param razaSN the razaSN to set
     */
    public void setRazaSN(char razaSN) {
        this.razaSN = razaSN;
    }

    /**
     * @return the vacunado
     */
    public boolean isVacunado() {
        return vacunado;
    }

    /**
     * @param vacunado the vacunado to set
     */
    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    public static void main(String[] args) {

        Mascota mascotaA = new Mascota("Michi", "Juan Andrade", 1234, 1, 'N', false);
        Mascota mascotaB = new Mascota("Mocho", "Manuel Beltran", 1235, 1, 'S', false);
        Mascota mascotaC = new Mascota("Nacho", "Antonio Her", 1236, 1, 'N', true);

        System.out.println("La mascotaA es: " + mascotaA);
        System.out.println("La mascotaB es: " + mascotaB);
        System.out.println("La mascotaC es: " + mascotaC);

        System.out.println("Total servicio mascotaA es => ");
        try {
            System.out.println(mascotaA.getTotal(100, 333));
        } catch (Exception ex) {
            System.out.println("Error en: " + ex);
        }

        System.out.println("Total servicio mascotaB es => ");
        try {
            System.out.println(mascotaB.getTotal(-100, 444));
        } catch (Exception ex) {
            System.out.println("Error en: " + ex);
        }

        System.out.println("Total servicio mascotaC es => ");
        try {
            System.out.println(mascotaC.getTotal(10, 555));
        } catch (Exception ex) {
            System.out.println("Error en: " + ex);
        }

        System.out.println("Fin del programa");
    }

}
