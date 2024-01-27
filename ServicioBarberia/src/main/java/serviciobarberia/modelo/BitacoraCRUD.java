package serviciobarberia.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 */
public class BitacoraCRUD implements CRUD<Bitacora, Integer> {

    public BitacoraCRUD() {

    }

    @Override
    public Optional<Bitacora> create(Bitacora inEntity) {

        Integer resultId;

        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("INSERT INTO bitacora(idcliente, idservicio, texto) VALUES(?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inEntity.getIdcliente());
            ps.setInt(2, inEntity.getIdservicio());
            ps.setString(3, inEntity.getTexto());

            // verificar que la operacion fue exitosa
            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    resultId = generatedKeys.getInt(1);
                } else {
                    resultId = null;
                }
            } else {
                return Optional.empty();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        inEntity.setIdbitacora(resultId);
        return Optional.of(new Bitacora(resultId, inEntity.getIdbitacora(), inEntity.getIdservicio(), inEntity.getTexto()));

    }

    @Override
    public Optional<Bitacora> read(Integer inId) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM bitacora WHERE idbitacora=?")) {

            ps.setInt(1, inId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Bitacora(rs.getInt("idbitacora"), rs.getInt("idcliente"), rs.getInt("idServicio"), rs.getString("texto")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Bitacora> update(Bitacora inEntity) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("UPDATE bitacora SET idcliente=?, idservicio=?, texto=? WHERE idbitacora=?")) {

            ps.setInt(1, inEntity.getIdcliente());
            ps.setInt(2, inEntity.getIdservicio());
            ps.setString(3, inEntity.getTexto());
            ps.setInt(4, inEntity.getIdbitacora());

            int rows = ps.executeUpdate();

            if (rows == 1) {
                return Optional.of(inEntity);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Integer inId) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("DELETE FROM bitacora WHERE idbitacora=?")) {

            ps.setInt(1, inId);

            // verificar que la operacion fue exitosa
            return ps.executeUpdate() == 1;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return false;
    }

    @Override
    public ArrayList<Bitacora> findAll() {
        ArrayList<Bitacora> list = new ArrayList<>();

        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM bitacora")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Bitacora(rs.getInt("idbitacora"), rs.getInt("idcliente"), rs.getInt("idServicio"), rs.getString("texto")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return list;
    }

    @Override
    public Optional<Bitacora> readStr(String inStr) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
