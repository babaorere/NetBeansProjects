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
public class ServicioCRUD implements CRUD<Servicio, Integer> {

    public ServicioCRUD() {

    }

    @Override
    public Optional<Servicio> create(Servicio inEntity) {

        Integer resultId;

        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("INSERT INTO servicios(nombre, precio) VALUES(?,?)",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, inEntity.getNombre());
            ps.setInt(2, inEntity.getPrecio());

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

        inEntity.setIdServicio(resultId);
        return Optional.of(new Servicio(resultId, inEntity.getNombre(), inEntity.getPrecio()));
    }

    @Override
    public Optional<Servicio> read(Integer inId) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM servicios WHERE idservicio=?")) {

            ps.setInt(1, inId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Servicio(rs.getInt("idservicio"), rs.getString("nombre"), rs.getInt("precio")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Servicio> update(Servicio inEntity) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("UPDATE servicios SET nombre=?, precio=? WHERE idservicio=?")) {

            ps.setString(1, inEntity.getNombre());
            ps.setInt(2, inEntity.getPrecio());
            ps.setInt(3, inEntity.getIdServicio());
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
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("DELETE FROM servicios WHERE idservicio=?")) {

            ps.setInt(1, inId);

            // verificar que la operacion fue exitosa
            return ps.executeUpdate() == 1;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return false;
    }

    @Override
    public ArrayList<Servicio> findAll() {
        ArrayList<Servicio> list = new ArrayList<Servicio>();

        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM servicios")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Servicio(rs.getInt("idservicio"), rs.getString("nombre"), rs.getInt("precio")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return list;
    }

    @Override
    public Optional<Servicio> readStr(String inStr) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM servicios WHERE nombre LIKE ?")) {

            ps.setString(1, inStr);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Servicio(rs.getInt("idservicio"), rs.getString("nombre"), rs.getInt("precio")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        return Optional.empty();
    }

}
