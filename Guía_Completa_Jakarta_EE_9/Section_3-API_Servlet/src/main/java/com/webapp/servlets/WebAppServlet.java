package com.webapp.servlets;

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
@WebServlet("/hola")
public class WebAppServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        // Definir el tipo de contenido de la Respuesta/Response
        resp.setContentType("text/html");

        // un "try" con recurso, para cerrar automaticamente el "PrintWriter"
        try ( PrintWriter out = resp.getWriter()) {

            // Realizar los print utilizando HTML, en este caso crear una pagina WEB sencilla. 
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("    <head>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />");
            out.println("        <meta name=\"description\" content=\"Section_3-API_Servlet\" />");
            out.println("        <meta name=\"author\" content=\"Roger Gallegos\" />");

            out.println("        <title>Section_3-API_Servlet</title>");

            out.println("        <!-- Favicon-->");
            out.println("        <!-- Referencia al favicon, es decir aquel que aparece en la pestaña de titulo -->");
            out.println("        <link rel=\"icon\" type=\"image/x-icon\" href=\"views/images/jakarta_favicon.png\" />");

            out.println("        <!-- Referencia a las distintas hojas de estilos CSS -->");
            out.println("        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");

            out.println("        <!-- DANGER, NO, NO, NO, establecer <<media=”screen”>> para la hoja de estilos, puede traer problemas -->");
            out.println("        <link href=\"views/css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");

            out.println("        <!-- Referencia al JS para los iconos, por sugerencia de fontawesome.com, debe estar en esta seccion -->");
            out.println("        <script src=\"https://kit.fontawesome.com/848895ce17.js\" crossorigin=\"anonymous\"></script>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("       <br>");
            out.println("       <br>");
            out.println("       <h1>Hola Mundo Servlet</h1>");
            out.println("       <br>");
            out.println("       <br>");

            out.println("   </body>");
            out.println("</html>");
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
//        this.doGet(req, resp);
//    }
}
