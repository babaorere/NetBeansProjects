package com.webapp.controller;

import com.webapp.entity.Matricula;
import com.webapp.repository.MatriculaRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("/matricula")
public class MatriculaController extends HttpServlet {

    @Autowired
    private MatriculaRepository repo;

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

                    String strC = req.getParameter("idEstudiante");
                    Integer idEstudianteC = Integer.parseInt(strC);

                    strC = req.getParameter("idCurso");
                    Integer idCursoC = Integer.parseInt(strC);

                    strC = req.getParameter("fecha");
                    LocalDate fechaC = LocalDate.parse(strC, DateTimeFormatter.ofPattern("d/MM/yyyy"));

                    LocalDate lastUpdateC = LocalDate.now();
                    repo.save(new Matricula(null, idEstudianteC, idCursoC, fechaC, lastUpdateC));

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/matricula/listado.jsp").forward(req, res);
                }
                break;

                case "read": {
                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/matricula/listado.jsp").forward(req, res);
                }
                break;

                case "update": {
                    String strU = req.getParameter("id");
                    Integer idU = Integer.parseInt(strU);

                    strU = req.getParameter("idEstudiante");
                    Integer idEstudianteU = Integer.parseInt(strU);

                    strU = req.getParameter("idCurso");
                    Integer idCursoU = Integer.parseInt(strU);

                    strU = req.getParameter("fecha");
                    LocalDate fechaU = LocalDate.parse(strU, DateTimeFormatter.ofPattern("d/MM/yyyy"));

                    LocalDate lastUpdateU = LocalDate.now();
                    repo.save(new Matricula(idU, idEstudianteU, idCursoU, fechaU, lastUpdateU));

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/matricula/listado.jsp").forward(req, res);
                }
                break;

                case "delete":
                    String strD = req.getParameter("id");
                    if (strD == null) {
                        strD = "0";
                    }
                    Integer idD = Integer.parseInt(strD);

                    Optional<Matricula> optionalD = repo.findById(idD);
                    if (optionalD.isPresent()) {
                        repo.delete(optionalD.get());
                    }

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/matricula/listado.jsp").forward(req, res);
                    break;

                case "edit":
                    String strE = req.getParameter("id");
                    if (strE == null) {
                        strE = "0";
                    }
                    Integer idE = Integer.parseInt(strE);

                    Optional<Matricula> optionalE = repo.findById(idE);
                    if (optionalE.isPresent()) {
                        req.setAttribute("item", optionalE.get());
                        req.getRequestDispatcher("/WEB-INF/matricula/modificar.jsp").forward(req, res);
                    }
                    break;

                default:
                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/matricula/listado.jsp").forward(req, res);
            }
        } else {
            req.setAttribute("listado", repo.findAll());
            req.getRequestDispatcher("/WEB-INF/matricula/listado.jsp").forward(req, res);
        }
    }
}
