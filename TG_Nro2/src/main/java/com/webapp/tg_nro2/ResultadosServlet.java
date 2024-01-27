package com.webapp.tg_nro2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/resultado")
public class ResultadosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // recuperar el id del postulante
        String strId = req.getParameter("id");

        // retornar si no hay parametro
        if (strId == null) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(strId);
        } catch (Exception e) {
            return;
        }

        Postulante reg = Postulante.obtener(id);

        if (reg == null) {
            // el registro no es encontrado
            try ( PrintWriter out = resp.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"es\">");
                out.println("<head>");
                out.println("    <meta charset=\"UTF-8\">");
                out.println("    <title>Error 404, no encontrado</title>");
                out.println("\n<style>\nbody {\nheight: auto;\nbackground-color: #EEE;"
                        + "\n}\n.mycenter {\ndisplay:flex;\njustify-content: center;\nalign-items: center;"
                        + "\n}\n.mytable {\nbackground-color: #000033;\npadding: 5px;\nborder-spacing: 5px;"
                        + "\n}\n</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("    <h1 class=\"mycenter\">Error 404</h1>");
                out.println("<p class=\"mycenter\" style=\"color:red;\">Cliente no encontrado</p>");
                out.println("<br><ul><li><a href = \"index.jsp\"> Home </a></li></ul><br>");

            }
        } else {

            try ( PrintWriter out = resp.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"es\">");
                out.println("<head>");
                out.println("    <meta charset=\"UTF-8\">");
                out.println("    <title>Resultados</title>");
                out.println("\n<style>\nbody {\nheight: auto;\nbackground-color: #EEE ;max-width: 800px;margin:auto;"
                        + "\n}\n.mycenter {\ndisplay:flex;\njustify-content: center;\nalign-items: center;"
                        + "\n}\n.mytable {\nbackground-color: #000033;\npadding: 5px;\nborder-spacing: 5px;"
                        + "\n}\n.myderecha { float:right}\n</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("    <h1 class=\"mycenter\">Resultados</h1>");
                out.println("<br><ul><li><a href = \"index.jsp\"> Home </a></li></ul><br>");

                out.println("<div>");

                String strFecha = DateTimeFormatter.ofPattern("dd/MM/YYYY").format(LocalDate.now());
                out.println("<div class=\"myderecha\">Fecha: " + strFecha + "</div>");
                out.println("<div>Empresa: ABC</div>");
                out.println("</div>");

                out.println("<div class=\"mycenter\"><table class=\"mytable\">");
                out.println("<tr>");
                out.println("<th> Cedula </th>");
                out.println("<th> Nombre </th>");
                out.println("<th>Preguntas realizadas</th>");
                out.println("<th>Preguntas correctas</th>");
                out.println("</tr>");
                out.println("<td>" + reg.getCedula() + "</td>");
                out.println("<td>" + reg.getNombre() + "</td>");
                out.println("<td>" + reg.getCantPregRe() + "</td>");
                out.println("<td>" + reg.getCantPregOk() + "</td>");
                out.println("</tr></table></div>");
                out.println("<br>");

                String strNivel = String.valueOf((new PorcentajeImpl()).obtenerNivel(reg.getPoncentaje()));
                out.println("<div class=\"mycenter\"><table class=\"mytable\">");
                out.println("<td>Porcentaje obtenido</td>");
                out.println("<td>" + reg.getPoncentaje() + "</td>");
                out.println("<td>Nivel</td>");
                out.println("<td>" + strNivel + "</td>");
                out.println("</tr></table></div>");

                out.println("<br><ul><li><a href = \"index.jsp\"> Home </a></li></ul><br>");
                out.println("</body>");
                out.println("</html>");
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
