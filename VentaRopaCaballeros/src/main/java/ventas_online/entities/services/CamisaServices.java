package ventas_online.entities.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import ventas_online.entities.Camisa;
import ventas_online.utils.MyConn;

/**
 *
 * @author manager
 */
public class CamisaServices {
    
    
    public int Create(Camisa inReg) {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("INSERT INTO camisa(idprenda, talla, tipo) VALUES(?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inReg.getIdprenda());
            ps.setInt(2, inReg.getTalla());
            ps.setString(3, inReg.getTipo());

            // verificar que la operacion fue exitosa
            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    resultId = generatedKeys.getInt(1);
                } else {
                    resultId = null;
                }
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CamisaServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 1;
    }
    
    public int Read(int inId) {


        return 1;
    }
    
    public int Update(Camisa inReg) {


        return 1;
    }
    
    public int Delete(int inId) {


        return 1;
    }
    
}
