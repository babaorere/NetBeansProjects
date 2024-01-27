package evaluacion2;

/**
 * El usuario del Chat, hace las veces de Observador
 */
public class Usuario implements IObservador {

    private String nombre = "";
    private String ultMensaje = "";

    // default constructor
    public Usuario() {
        nombre = "";
        ultMensaje = "";
    }

    // Inicializamos el Usuario, con su nombre
    public Usuario(String objNombre) {
        this.nombre = objNombre;
        this.ultMensaje = "";
    }

    // Metodo auxiliar para trazar el ultimo mensaje del usuario al chat
    String dijo(String objMensaje) {

        this.ultMensaje = objMensaje;
        return nombre + " dijo " + this.ultMensaje;
    }

    @Override
    /**
     * Implementamos el metodo de la interface
     */
    public void observadoActualizado(Observado objObservado, Object valor) {

        // Utilizamos el metodo toString propio, para aumentar la versatilidad de la aplicacion
        System.out.println("El usuario[" + nombre + "] ha recibido el mensaje[" + valor.toString() + "]");

    }

}
