package DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import modelo.Estudiante;
import modelo.Facilitador;
import DAOs.Conexion;

public class FacilitadorDAO {

	
	public List<Facilitador> Facilitadores;
	public void cargarFacilitadores(){
	this.Facilitadores = new ArrayList<Facilitador>();
	
		String sql = "SELECT * FROM FACILITADOR";
      
        try {
            Statement declaracion = Conexion.getCon().createStatement();
            ResultSet Resultados = declaracion.executeQuery(sql);
            while(Resultados.next())
            {
            	
            this.Facilitadores.add(new Facilitador(Resultados.getInt("id"),Resultados.getString("rut"),Resultados.getString("nombre"),
                Resultados.getString("email"), Resultados.getString("telefono"),Resultados.getString("valorhora"),Resultados.getString("banco"),Resultados.getString("ctabancaria"), Resultados.getString("last_update")));
            } 
           } catch (SQLException e)
        {
            System.out.println("REVISA BIEN LA CONSULTA");    
            e.printStackTrace();
        } 
	}
	public Facilitador obtenerFacilitador(int id) {
	    
		
		   String sql= "SELECT * FROM FACILITADOR WHERE id =" + id;
		   Facilitador facilitador = null;
		   
	        try {
	            Statement declaracion = Conexion.getCon().createStatement();
	            ResultSet Resultados = declaracion.executeQuery(sql);
	            while(Resultados.next()){
	                facilitador = new Facilitador(Resultados.getInt("id"),Resultados.getString("rut"),Resultados.getString("nombre"),
	                        Resultados.getString("email"), Resultados.getString("telefono"),Resultados.getString("valorhora"),Resultados.getString("banco"),Resultados.getString("ctabancaria"), Resultados.getString("last_update"));
	            } 
	        } catch (SQLException e) {
	            System.out.println("REVISA BIEN LA CONSULTA");    
	            e.printStackTrace();
	        }
		return facilitador;
	}
	public void modificarFacilitador(Facilitador facilitador){
        String sql = "UPDATE Facilitador SET rut= '" + facilitador.getRut() + "', nombre= '" + facilitador.getNombre() + "',"
                + " email= '" + facilitador.getEmail() + "', telefono= '" + facilitador.getTelefono() + "', valorhora = '"+ facilitador.getValorhora()+"', banco = '"+ facilitador.getBanco()+"', ctabancaria = '"+facilitador.getCtabancaria() + "', last_update= '" + facilitador.getLast_update() + "' "
                + "WHERE id =" + facilitador.getId();
        try {
            Statement declaracion = Conexion.getCon().createStatement();
            declaracion.executeUpdate(sql);
           
        } catch (SQLException e) {
            System.out.println("REVISA BIEN LA CONSULTA");    
            e.printStackTrace();
        }
       
    }
	public void eliminarFacilitador(int id){
        String sql = "Delete from facilitador WHERE id =" + id;
        try {
            Statement declaracion = Conexion.getCon().createStatement();
            declaracion.executeUpdate(sql);
           
        } catch (SQLException e) {
            System.out.println("REVISA BIEN LA CONSULTA");    
            e.printStackTrace();
        }
       
    }
}
