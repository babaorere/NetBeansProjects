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
@WebServlet("/param-url")
public class ParamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try ( PrintWriter pw = resp.getWriter()) {

            String saludo = req.getParameter("saludo");
            String nombre = req.getParameter("nombre");

            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("    <head>");
            pw.println("        <title>Servlet con parámetros</title>");
            pw.println("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"");
            pw.println("    </head>");
            pw.println("    <body>");
            pw.println("        <h1>Servlet con parámetros</h1>");

            if (saludo != null) {
                pw.println("        <h2>Saludo: " + saludo + "</h2>");
            } else {
                pw.println("        <h2>Saludo: null</h2>");
            }

            if (nombre != null) {
                pw.println("        <h2>Nombre: " + nombre + "</h2>");
            } else {
                pw.println("        <h2>Nombre: null</h2>");
            }

            pw.println("    </body>");
            pw.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
