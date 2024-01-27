package evaluacion2;

/**
 *
 */
public class ChatApp {

    public static void main(String[] args) {

        System.out.println("Comienzo de la sesion del Chat");

        // Instanciar el objeto que será Observado, en este caso el Chat 
        ChatObservado chat = new ChatObservado();

        // Instanciamos a los usuarios del Chat
        Usuario maria = new Usuario("Maria");
        Usuario jose = new Usuario("Jose");
        Usuario manuel = new Usuario("Manuel");

        // Agregamos los Usuarios al Chat
        chat.agregarObservador(maria);
        chat.agregarObservador(jose);
        chat.agregarObservador(manuel);

        // Comenzamos a enviar mensajes al Chat
        chat.setUltMensaje(maria.dijo("Hola a todos"));
        chat.setUltMensaje(maria.dijo("Espero que esten bien"));
        chat.setUltMensaje(jose.dijo("Saludos Maria"));

        System.out.println("Fin de la sesion del Chat");

    }
}
