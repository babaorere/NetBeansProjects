package soapapp;

/**
 * Representa los datos del servicio SOAP
 *
 * Es una clase inmutable, no se pueden modificar sus datos
 *
 */
public class RequestMessage {

    private final String text;
    private final byte[] file;

    public RequestMessage(String inText, byte[] inFile) {
        this.text = inText;
        this.file = inFile;
    }

    public String getText() {
        return text;
    }

    public byte[] getFile() {
        return file;
    }

}
