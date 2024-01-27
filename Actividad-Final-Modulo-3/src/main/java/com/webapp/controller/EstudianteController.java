package com.webapp.controller;

import com.webapp.entity.Estudiante;
import com.webapp.repository.EstudianteRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("estudiante")
public class EstudianteController extends HttpServlet {

    @Autowired
    private EstudianteRepository repo;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "create": {
                    String rutC = req.getParameter("rut");
                    String nombreC = req.getParameter("nombre");
                    String emailC = req.getParameter("email");
                    String telefonoC = req.getParameter("telefono");

                    LocalDate lastUpdateC = LocalDate.now();
                    repo.save(new Estudiante(null, rutC, nombreC, emailC, telefonoC, lastUpdateC));

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/estudiante/listado.jsp").forward(req, res);
                }
                break;

                case "read": {
                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/estudiante/listado.jsp").forward(req, res);
                }
                break;

                case "update": {
                    String strU = req.getParameter("id");
                    Integer idU = Integer.parseInt(strU);
                    String rutU = req.getParameter("rut");
                    String nombreU = req.getParameter("nombre");
                    String emailU = req.getParameter("email");
                    String telefonoU = req.getParameter("telefono");
                    LocalDate lastUpdateU = LocalDate.now();
                    repo.save(new Estudiante(idU, rutU, nombreU, emailU, telefonoU, lastUpdateU));

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/estudiante/listado.jsp").forward(req, res);
                }
                break;

                case "delete": {
                    String strD = req.getParameter("id");
                    if (strD == null) {
                        strD = "0";
                    }
                    Integer idD = Integer.parseInt(strD);

                    Optional<Estudiante> optionalD = repo.findById(idD);
                    if (optionalD.isPresent()) {
                        repo.delete(optionalD.get());
                    }

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/estudiante/listado.jsp").forward(req, res);
                }
                break;

                case "edit": {
                    String strE = req.getParameter("id");
                    if (strE == null) {
                        strE = "0";
                    }
                    Integer idE = Integer.parseInt(strE);

                    Optional<Estudiante> optionalE = repo.findById(idE);
                    if (optionalE.isPresent()) {
                        req.setAttribute("item", optionalE.get());
                        req.getRequestDispatcher("/WEB-INF/estudiante/modificar.jsp").forward(req, res);
                    }
                }
                break;

                default:
                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/estudiante/listado.jsp").forward(req, res);
            }
        } else {
            req.setAttribute("listado", repo.findAll());
            req.getRequestDispatcher("/WEB-INF/estudiante/listado.jsp").forward(req, res);
        }
    }

}
