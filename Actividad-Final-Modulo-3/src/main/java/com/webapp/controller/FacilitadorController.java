package com.webapp.controller;

import com.webapp.entity.Facilitador;
import com.webapp.repository.FacilitadorRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("/facilitador")
public class FacilitadorController extends HttpServlet {

    @Autowired
    private FacilitadorRepository repo;

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

                    String strC = req.getParameter("valorHora");
                    Float valorHora = Float.parseFloat(strC);

                    String bancoC = req.getParameter("banco");
                    String cuentaBancariaC = req.getParameter("cuentaBancaria");

                    LocalDate lastUpdateC = LocalDate.now();
                    repo.save(new Facilitador(null, rutC, nombreC, emailC, telefonoC, valorHora, bancoC, cuentaBancariaC, lastUpdateC));

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/facilitador/listado.jsp").forward(req, res);
                }
                break;

                case "read": {
                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/facilitador/listado.jsp").forward(req, res);
                }
                break;

                case "update": {
                    String strU = req.getParameter("id");
                    Integer idU = Integer.parseInt(strU);

                    String rutU = req.getParameter("rut");
                    String nombreU = req.getParameter("nombre");
                    String emailU = req.getParameter("email");
                    String telefonoU = req.getParameter("telefono");

                    strU = req.getParameter("valorHora");
                    Float valorHoraU = Float.parseFloat(strU);

                    String bancoU = req.getParameter("banco");
                    String cuentaBancariaU = req.getParameter("cuentaBancaria");

                    LocalDate lastUpdateU = LocalDate.now();
                    repo.save(new Facilitador(idU, rutU, nombreU, emailU, telefonoU, valorHoraU, bancoU, cuentaBancariaU, lastUpdateU));

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/facilitador/listado.jsp").forward(req, res);
                }
                break;

                case "delete":
                    String strD = req.getParameter("id");
                    if (strD == null) {
                        strD = "0";
                    }
                    Integer idD = Integer.parseInt(strD);

                    Optional<Facilitador> optionalD = repo.findById(idD);
                    if (optionalD.isPresent()) {
                        repo.delete(optionalD.get());
                    }

                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/facilitador/listado.jsp").forward(req, res);
                    break;

                case "edit":
                    String strE = req.getParameter("id");
                    if (strE == null) {
                        strE = "0";
                    }
                    Integer idE = Integer.parseInt(strE);

                    Optional<Facilitador> optionalE = repo.findById(idE);
                    if (optionalE.isPresent()) {
                        req.setAttribute("item", optionalE.get());
                        req.getRequestDispatcher("/WEB-INF/facilitador/modificar.jsp").forward(req, res);
                    }
                    break;

                default:
                    req.setAttribute("listado", repo.findAll());
                    req.getRequestDispatcher("/WEB-INF/facilitador/listado.jsp").forward(req, res);
            }
        } else {
            req.setAttribute("listado", repo.findAll());
            req.getRequestDispatcher("/WEB-INF/facilitador/listado.jsp").forward(req, res);
        }
    }
}
