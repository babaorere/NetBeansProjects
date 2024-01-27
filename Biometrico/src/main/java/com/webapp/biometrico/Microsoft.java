package com.webapp.biometrico;

/**
 * Write a description of class Microsoft here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Microsoft extends Biometrico {

    /**
     * Constructor for objects of class Microsoft
     */
    public Microsoft() {
        this(ON, OFF, OFF, OFF, "");
    }

    public Microsoft(String ledAzul, String ledRojo, String ledLector, String ledErrorLectura, String data) {
        super(ledAzul, ledRojo, ledLector, ledErrorLectura, data);
    }

    // Sobre-escritura de los metodos abstractos heredados de  la clase Biometrico
    public String onHuella() throws Exception { // Es cuando la persona coloca la huella en el biométrico
        // En el lector Microsoft el código ya realizado en el método onHuella realiza lo
        // siguiente:
        // Cuando la huella es colocada en el sensor, se apaga la luz
        // Se enciende el lector y aleatoriamente indica si hubo error en la lectura
        // La acción debe indicar en qué estado ocurrió

        setLedAzul(OFF);
        setLedLector(ON);

        // genera un error aproximadamente en el 20% de la muestra
        double dData = Math.random();
        Boolean error = (dData <= 0.20d);

        String msj;
        if (error) {
            setData("ERROR"); // simulamos el error con una lectura temporal
            msj = "Microsoft.onHuella(), con errores";
        } else {
            setData(""); // simulamos un proceso exitoso, con un valor vacio
            msj = "Microsoft.onHuella(), con éxito";
        }

        return msj;
    }

    public String onMuestra() throws Exception { // Es cuando el lector toma una muestra de la huella
        // Para el método onMuestra lo que actualmente está haciendo es lo siguiente:
        // Si no hay error de lectura se extrae el dato de la huella en un String
        // La acción debe indicar en qué estado ocurrió

        String msj;
        if (getData().equals("ERROR")) {
            setData(""); // simulamos la lectura con un dato  vacio
            msj = "Microsoft.onMuestra(), con errores";
        } else {
            setData(String.valueOf(Math.random())); // simulamos la lectura, como el resultado de Math.random()
            msj = "Microsoft.onMuestra(), con éxito";
        }

        return msj;
    }

    @Override
    public String outHuella() throws Exception { // Es cuando la persona saca la huella del biométrico
        // Para el método outHuella lo que actualmente está haciendo es lo siguiente:
        // Si no existe data lanza una excepción, en cualquier caso se enciende el led
        // del sensor y se apaga el lector
        // La acción debe indicar en qué estado ocurrió    

        String aux = getData(); // tomamos la data
        if (aux.equals("") || aux.equals("ERROR")) { // error
            setLedAzul(OFF);
            setLedRojo(OFF);
            setLedLector(OFF);
            setLedErrorLectura(ON);
            throw new Exception("Microsoft.outHuella(), con errores");
        } else {
            setLedAzul(ON);
            setLedRojo(OFF);
            setLedLector(OFF);
            setLedErrorLectura(OFF);
        }

        return "Microsoft.outHuella(), con éxito";

    }
}
