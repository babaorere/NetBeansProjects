package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOs.EstudianteDAO;
import modelo.Estudiante;

/**
 * Servlet implementation class ControladorEstudiante
 */
@WebServlet("/ControladorEstudiante")
public class ControladorEstudiante extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControladorEstudiante() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	    EstudianteDAO eDAO = new EstudianteDAO();	   
        String op = (String)request.getParameter("op"); 	    
	       
	    if(op==null) {
	    	
	    	eDAO.Estudiantes = new ArrayList<Estudiante>();
	        eDAO.cargarEstudiantes();
	        
	    	request.setAttribute("Estudiantes", eDAO.Estudiantes);
	    	request.getRequestDispatcher("views/estudiantes.jsp").forward(request, response);  
	        	
	    }else {
	    	 if(op.equalsIgnoreCase("actualizar")) {
	 	    	
	  	       int id = Integer.parseInt(request.getParameter("id"));	
	  	       request.setAttribute("estudiante", eDAO.obtenerEstudiante(id));
	  	       request.getRequestDispatcher("views/modificarestudiante.jsp").forward(request, response);  	       	
	  	    } 
	    	 
	         if(op.equalsIgnoreCase("update")) {
	        	   Estudiante estudiante = new Estudiante(Integer.parseInt(request.getParameter("id")),(String)request.getParameter("rut"),
	                       (String)request.getParameter("nombre"), (String)request.getParameter("email"),
	                       (String)request.getParameter("telefono"), (String)request.getParameter("lastUpdate"));
		 	    	
		  	     
		  	       eDAO.modificarEstudiante(estudiante);
		  	       eDAO.cargarEstudiantes();
		  	       request.setAttribute("Estudiantes", eDAO.Estudiantes);
			       request.getRequestDispatcher("views/estudiantes.jsp").forward(request, response);  	       	
		  	    } 
	         
	         if(op.equalsIgnoreCase("eliminar")) {  	    	    	
		  	     
		  	       eDAO.eliminarEstudiante(Integer.parseInt(request.getParameter("id")));
		  	       eDAO.cargarEstudiantes();
		  	       request.setAttribute("Estudiantes", eDAO.Estudiantes);
			       request.getRequestDispatcher("views/estudiantes.jsp").forward(request, response);  	       	
		  	    }  	  
	    	
	    }
	        	
	}      
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
