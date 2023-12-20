package evaluacion2;

import java.util.ArrayList;

/**
 * En este caso el objeto observado va a ser el Chat
 */
public class ChatObservado extends Observado {

    private ArrayList<String> mensajes = new ArrayList<String>();

    private String ultMensaje = "";

    // default constructor
    public ChatObservado() {
        ultMensaje = "";
    }

    // constructor con parametros
    public ChatObservado(String objMensaje) {
        this.ultMensaje = objMensaje;
    }

    public String getUltMensaje() {

        return this.ultMensaje;
    }

    public void setUltMensaje(String objUltMensaje) {

        // actualizamos el nuevo ultMensaje, para un futuro uso
        this.ultMensaje = objUltMensaje;

        // agregamos el nuevo mensaje, a la lista de mensajes del Chat
        mensajes.add(this.ultMensaje);
        System.out.println("Chat mensaje: [" + this.ultMensaje + "]");

        // Enviarles la notificación a cada usuario/observador a través de su propio método 
        this.notificarObservadores(this.ultMensaje);

    }
}
