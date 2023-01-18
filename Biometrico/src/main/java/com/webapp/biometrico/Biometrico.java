package com.webapp.biometrico;

/**
 * Abstract class Biometrico - write a description of the class here
 *
 * @author: Date:
 */
public abstract class Biometrico {

    // Hace las veces de constante
    public static final String ON = "ON";
    public static final String OFF = "OFF";

    // instance variables 
    private String ledAzul;
    private String ledRojo;
    private String ledLector;
    private String ledErrorLectura;
    private String data;

    // constructor por defecto
    public Biometrico() {
        this(ON, OFF, OFF, OFF, "");
    }

    // constructor con parametros
    public Biometrico(String ledAzul, String ledRojo, String ledLector, String ledErrorLectura, String data) {
        this.ledAzul = ledAzul;
        this.ledRojo = ledRojo;
        this.ledLector = ledLector;
        this.ledErrorLectura = ledErrorLectura;
        this.data = data;
    }

    // Metodos abstractos
    public abstract String onHuella() throws Exception; // Es cuando la persona coloca la huella en el biométrico

    public abstract String onMuestra() throws Exception; // Es cuando el lector toma una muestra de la huella

    public abstract String outHuella() throws Exception; // Es cuando la persona saca la huella del biométrico    

    // Sobre-escritura para presentar los atributos del objeto
    @Override
    public String toString() {
        return "{"
                + "         ledAzul   : " + ledAzul
                + "\n        ledRojo   : " + ledRojo
                + "\n        ledLector : " + ledLector
                + "\n  ledErrorLectura : " + ledErrorLectura
                + "\n             data : " + data
                + "}";
    }

    // *****************
    // Getters y Setters
    // *****************
    //
    public String getLedAzul() {
        return ledAzul;
    }

    public void setLedAzul(String ledAzul) {
        this.ledAzul = ledAzul;
    }

    public String getLedRojo() {
        return ledRojo;
    }

    public void setLedRojo(String ledRojo) {
        this.ledRojo = ledRojo;
    }

    public String getLedLector() {
        return ledLector;
    }

    public void setLedLector(String ledLector) {
        this.ledLector = ledLector;
    }

    public String getLedErrorLectura() {
        return ledErrorLectura;
    }

    public void setLedErrorLectura(String ledErrorLectura) {
        this.ledErrorLectura = ledErrorLectura;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
