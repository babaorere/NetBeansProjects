package com.webapp.biometrico;

/**
 * Write a description of class DigitalPersona here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DigitalPersona extends Biometrico {

    /**
     * Constructor for objects of class Microsoft
     */
    public DigitalPersona() {
        // El lector DigitalPersona su estado inicial es:
        // ledAzul encendido
        // ledRojo apagado
        // lector apagado
        // errorLectura apagado
        // data en String vacio “”        
        this(ON, OFF, OFF, OFF, "");
    }

    public DigitalPersona(String ledAzul, String ledRojo, String ledLector, String ledErrorLectura, String data) {
        super(ledAzul, ledRojo, ledLector, ledErrorLectura, data);
    }

    // Sobre-escritura de los metodos abstractos heredados de  la clase Biometrico
    public String onHuella() throws Exception { // Es cuando la persona coloca la huella en el biométrico
        // En el método onHuella deberá realizar lo siguiente:
        // Apagar el ledAzul
        // Encender el ledRojo
        setLedAzul(OFF);
        setLedRojo(ON);

        return "DigitalPersona.onHuella(), con éxito";
    }

    public String onMuestra() throws Exception { // Es cuando el lector toma una muestra de la huella
        // En el método onMuestra deberá realizar lo siguiente:
        // Verificar si hubo algún error (Simulando el resultado con Math.random)
        // Realizar lectura del biométrico 

        // genera un error aproximadamente en el 20% de la muestra
        double dData = Math.random();
        Boolean error = (dData <= 0.20d);

        String msj;
        if (error) {
            setData(""); // simulamos la lectura con un dato  vacio
            msj = "DigitalPersona.onMuestra(), con errores";
        } else {
            setData(String.valueOf(dData)); // simulamos la lectura, como el resultado de Math.random()
            msj = "DigitalPersona.onMuestra(), con éxito";
        }

        return msj;

    }

    public String outHuella() throws Exception { // Es cuando la persona saca la huella del biométrico
        // Para el método outHuella lo que actualmente está haciendo es lo siguiente:
        // Si no existe data lanza una excepción, en cualquier caso se enciende el led
        // del sensor y se apaga el lector
        // La acción debe indicar en qué estado ocurrió    

        if (getData().equals("")) { // error
            setLedAzul(OFF);
            setLedRojo(OFF);
            setLedLector(OFF);
            setLedErrorLectura(ON);
            throw new Exception("DigitalPersona.outHuella(), con errores");
        } else {
            setLedAzul(ON);
            setLedRojo(OFF);
            setLedLector(OFF);
            setLedErrorLectura(OFF);
        }

        return "DigitalPersona.outHuella(), con éxito";
    }
}
