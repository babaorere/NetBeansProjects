package modelo;

public class Facilitador {
   private int id;
   private String rut;
   private String nombre;
   private String email;
   private String telefono;
   private String valorhora;
   private String banco;
   private String ctabancaria;
   private String last_update;

    public Facilitador(int id, String rut, String nombre, String email, String telefono, String valorhora, String banco, String ctabancaria, String last_update) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.valorhora = valorhora;
        this.banco = banco;
        this.ctabancaria = ctabancaria;
        this.last_update = last_update;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getValorhora() {
        return valorhora;
    }

    public void setValorhora(String valorhora) {
        this.valorhora = valorhora;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCtabancaria() {
        return ctabancaria;
    }

    public void setCtabancaria(String ctabancaria) {
        this.ctabancaria = ctabancaria;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
   
}

