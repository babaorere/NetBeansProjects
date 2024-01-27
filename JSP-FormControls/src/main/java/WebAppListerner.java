
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * ServletContextListener: Listener que se encarga de gestionar los eventos generales de la aplicación como son arranque y parada. <br>
 * HttpSessionListener :Listener que se encarga de gestionar los propios eventos de la sessión como creación, invalidación y destrucción de sessiones. <br>
 * ServletRequestListener : Listener que se encarga de los eventos de creación y destruccion de peticiones. <br>
 * Ref: https://www.arquitecturajava.com/servletcontextlistener/
 */
@WebListener
public class WebAppListerner implements ServletContextListener, // Eventos generales de la aplicación
        ServletRequestListener, // Eventos de la sessión
        HttpSessionListener { // Eventos de creación y destruccion de peticiones

    /**
     * Es utilizada en donde no es posible acceder via parametro, en el caso de HttpSessionEvent, <br>
     * no tiene disponible el metodo getServletContext
     */
    private ServletContext servletContext;

    /**
     * Podemos usar este evento para cargar datos en memoria cuando arranca la aplicación, <br>
     * enviarnos un correo informándonos de inicio o parada, o para otras muchas necesidades.
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("Inicializando la webapp");
        servletContext = sce.getServletContext();

        servletContext.setAttribute("mensaje", "valor global de la webapp"); // al estilo [clave, valor]

        String realWebAppPath = servletContext.getRealPath("");
        String view = realWebAppPath.concat("hit.txt");

        servletContext.setAttribute("realPath", view); // al estilo [clave, valor]

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext.log("Destruyendo la aplicacion");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        servletContext.log("Inicializando el request");

        /**
         * Este atributo solo es valido, o esta disponible para cada nuevo request
         */
        sre.getServletRequest().setAttribute("mensaje", "guardando algun valor para el request");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        servletContext.log("Destruyendo el request");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        servletContext.log("Inicializando la sesion http");
        HttpSession session = se.getSession();
        session.setAttribute("mensaje", "valor sesión de la webapp");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        servletContext.log("Destruyendo la sesion http");
    }
}
