package app.webapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 */
@WebServlet("/hola-mundo")
public class HolaMundoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try ( PrintWriter pw = resp.getWriter()) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("    <head>");
            pw.println("        <title>Hola Mundo Page</title>");
            pw.println("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"");
            pw.println("    </head>");
            pw.println("    <body>");
            pw.println("        <h1>Proyecto WebApp para Servlets</h1>");
            pw.println("        <h2>Hola Mundo</h2>");
            pw.println("        <a href=\"/WebApp/param-url?saludo=Roger%20Gallegos\">Enviado parámetro Saludo</a>");
            pw.println("    </body>");
            pw.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
