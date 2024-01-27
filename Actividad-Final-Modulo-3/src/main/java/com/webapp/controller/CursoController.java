package com.webapp.controller;

import com.webapp.repository.CursoRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("/curso")
public class CursoController extends HttpServlet {

    @Autowired
    private CursoRepository repo;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        String action = req.getParameter("action");
//        if (action != null) {
//            switch (action) {
//
//                case "create": {
//                    String codigoC = req.getParameter("codigo");
//                    String nombreC = req.getParameter("nombre");
//                    String strC = req.getParameter("idFacilitador");
//                    if (strC == null) {
//                        strC = "0";
//                    }
//                    Integer id_facilitadorC = Integer.parseInt(strC);
//                    LocalDate lastUpdate = LocalDate.now();
//                    repo.save(new Curso(null, codigoC, nombreC, id_facilitadorC, lastUpdate));
//
//                    req.setAttribute("listado", repo.findAll());
//                    req.getRequestDispatcher("/WEB-INF/curso/listado.jsp").forward(req, res);
//                }
//                break;
//
//                case "read": {
//                    req.setAttribute("listado", repo.findAll());
//                    req.getRequestDispatcher("/WEB-INF/curso/listado.jsp").forward(req, res);
//                }
//                break;
//
//                case "update": {
//                    String strU = req.getParameter("id");
//                    Integer idU = Integer.parseInt(strU);
//                    String codigoU = req.getParameter("codigo");
//                    String nombreU = req.getParameter("nombre");
//                    strU = req.getParameter("idFacilitador");
//                    if (strU == null) {
//                        strU = "0";
//                    }
//                    Integer idFacilitadorU = Integer.parseInt(strU);
//
//                    LocalDate lastUpdateU = LocalDate.now();
//                    repo.save(new Curso(idU, codigoU, nombreU, idFacilitadorU, lastUpdateU));
//
//                    req.setAttribute("listado", repo.findAll());
//                    req.getRequestDispatcher("/WEB-INF/curso/listado.jsp").forward(req, res);
//                }
//                break;
//
//                case "delete": {
//                    String strD = req.getParameter("id");
//                    if (strD == null) {
//                        strD = "0";
//                    }
//                    Integer idD = Integer.parseInt(strD);
//
//                    Optional<Curso> optionalD = repo.findById(idD);
//                    if (optionalD.isPresent()) {
//                        repo.delete(optionalD.get());
//                    }
//
//                    req.setAttribute("listado", repo.findAll());
//                    req.getRequestDispatcher("/WEB-INF/curso/listado.jsp").forward(req, res);
//                }
//                break;
//
//                case "edit": {
//                    String strE = req.getParameter("id");
//                    if (strE == null) {
//                        strE = "0";
//                    }
//                    Integer idE = Integer.parseInt(strE);
//
//                    Optional<Curso> optionalE = repo.findById(idE);
//                    if (optionalE.isPresent()) {
//                        req.setAttribute("item", optionalE.get());
//                        req.getRequestDispatcher("/WEB-INF/curso/modificar.jsp").forward(req, res);
//                    }
//                }
//                break;
//
//                default:
//                    req.setAttribute("listado", repo.findAll());
//                    req.getRequestDispatcher("/WEB-INF/curso/listado.jsp").forward(req, res);
//
//            }
//        } else {
//            req.setAttribute("listado", repo.findAll());
//            req.getRequestDispatcher("/WEB-INF/curso/listado.jsp").forward(req, res);
//        }
    }

}
