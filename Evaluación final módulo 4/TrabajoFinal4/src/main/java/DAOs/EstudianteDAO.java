package DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import modelo.Estudiante;
import DAOs.Conexion;

public class EstudianteDAO {

	
	public List<Estudiante> Estudiantes;
	public void cargarEstudiantes(){
		this.Estudiantes = new ArrayList<Estudiante>();
		
		String sql = "SELECT * FROM ESTUDIANTE";
      
        try {
            Statement declaracion = Conexion.getCon().createStatement();
            ResultSet Resultados = declaracion.executeQuery(sql);
            while(Resultados.next())
            {
            	
            this.Estudiantes.add(new Estudiante(Resultados.getInt("id"),Resultados.getString("rut"),Resultados.getString("nombre"),
                Resultados.getString("email"), Resultados.getString("telefono"), Resultados.getString("last_update")));
            } 
           } catch (SQLException e)
        {
            System.out.println("REVISA BIEN LA CONSULTA");    
            e.printStackTrace();
        } 
	}
	public Estudiante obtenerEstudiante(int id) {
		    
		
		   String sql= "SELECT * FROM ESTUDIANTE WHERE id =" + id;
		   Estudiante estudiante = null;
		   
	        try {
	            Statement declaracion = Conexion.getCon().createStatement();
	            ResultSet Resultados = declaracion.executeQuery(sql);
	            while(Resultados.next()){
	                estudiante = new Estudiante(Resultados.getInt("id"),Resultados.getString("rut"),Resultados.getString("nombre"),
	                        Resultados.getString("email"), Resultados.getString("telefono"), Resultados.getString("last_update"));
	            } 
	        } catch (SQLException e) {
	            System.out.println("REVISA BIEN LA CONSULTA");    
	            e.printStackTrace();
	        }
		return estudiante;
	}
	public void modificarEstudiante(Estudiante estudiante){
        String sql = "UPDATE ESTUDIANTE SET rut= '" + estudiante.getRut() + "', nombre= '" + estudiante.getNombre() + "',"
                + " email= '" + estudiante.getEmail() + "', telefono= '" + estudiante.getTelefono() + "', last_update= '" + estudiante.getLast_update() + "' "
                        + "WHERE id =" + estudiante.getId();
        try {
            Statement declaracion = Conexion.getCon().createStatement();
            declaracion.executeUpdate(sql);
           
        } catch (SQLException e) {
            System.out.println("REVISA BIEN LA CONSULTA");    
            e.printStackTrace();
        }
       
    }
	public void eliminarEstudiante(int id){
        String sql = "Delete from estudiante WHERE id =" + id;
        try {
            Statement declaracion = Conexion.getCon().createStatement();
            declaracion.executeUpdate(sql);
           
        } catch (SQLException e) {
            System.out.println("REVISA BIEN LA CONSULTA");    
            e.printStackTrace();
        }
       
    }
}
