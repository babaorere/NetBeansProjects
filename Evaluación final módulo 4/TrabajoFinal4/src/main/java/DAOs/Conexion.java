package DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    
    private static Connection con;
    
    private Conexion(){
        con = null;
        try{
        	
        	
            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/docented_sustantiva", "root", "");                
            } catch (SQLException e) {
                System.out.println("HAY PROBLEMAS CON LA CONEXIÓN A MYSQL. REVISE SI MYSQL ESTÁ INICIADO Y ESCUCHANDO EN EL PUERTO 3306");
                e.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            System.out.println("REVISE EL DRIVER");
            e.printStackTrace();
        }
    }
    
    public static Connection getCon(){
        if(con == null){
            new Conexion();
        }
        return con;
    }
}
