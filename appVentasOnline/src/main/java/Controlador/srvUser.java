
package Controlador;

import Modelo.SrvUsuario_Service;
import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;



public class srvUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            String usuario,password,respuesta;
            String nombre="";
            RequestDispatcher rd=null;
            
            usuario = request.getParameter("txtUsuario").toString();
            password = request.getParameter("txtPassword").toString();  
                        
            //instancia del servicio web ''usuario
            Usuario u = new Usuario();
            SrvUsuario_Service user = new SrvUsuario_Service();
            
            try
            {
            u=user.getSrvUsuarioPort().autenticar(usuario, password);
           
            if (u.getUsuario()!=null)
               {
                  respuesta="True" ;
                  nombre = u.getNombre();
               } 
            else
                {
                    respuesta ="False";
                }
             }
            catch (Exception ex)
              {
                  respuesta= "False";
              }
                        
            request.setAttribute("respuesta", respuesta);
            request.setAttribute("nombre", nombre);
            request.setAttribute("usuario", usuario);
            
            rd=request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
            
            
            /*out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login -Server</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Usuario: " + "Respuesta de base de datos: " + respuesta+ "</h1>");
            out.println("</body>");
            out.println("</html>");*/
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    
}
