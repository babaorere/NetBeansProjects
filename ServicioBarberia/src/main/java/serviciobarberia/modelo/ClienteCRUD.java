package serviciobarberia.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 */
public class ClienteCRUD implements CRUD<Cliente, Integer> {

    public ClienteCRUD() {

    }

    @Override
    public Optional<Cliente> create(Cliente inEntity) {

        Integer resultId;

        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("INSERT INTO clientes(nombre, telefono, direccion, idusuario) VALUES(?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, inEntity.getNombre());
            ps.setString(2, inEntity.getTelefono());
            ps.setString(3, inEntity.getDireccion());
            if (inEntity.getIdUsuario() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, inEntity.getIdUsuario());
            }

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

        inEntity.setIdcliente(resultId);
        return Optional.of(new Cliente(resultId, inEntity.getNombre(), inEntity.getTelefono(), inEntity.getDireccion(), null));

    }

    @Override
    public Optional<Cliente> read(Integer inId) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM clientes WHERE idcliente=?")) {

            ps.setInt(1, inId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Cliente(rs.getInt("idcliente"), rs.getString("nombre"), rs.getString("telefono"), rs.getString("direccion"), rs.getInt("idusuario")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Cliente> update(Cliente inEntity) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("UPDATE clientes SET nombre=?, telefono=?, direccion=?, idusuario=? WHERE idcliente=?")) {

            ps.setString(1, inEntity.getNombre());
            ps.setString(2, inEntity.getTelefono());
            ps.setString(3, inEntity.getDireccion());
            if (inEntity.getIdUsuario() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, inEntity.getIdUsuario());
            }
            ps.setInt(5, inEntity.getIdcliente());
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
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("DELETE FROM clientes WHERE idcliente=?")) {

            ps.setInt(1, inId);

            // verificar que la operacion fue exitosa
            return ps.executeUpdate() == 1;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return false;
    }

    @Override
    public ArrayList<Cliente> findAll() {
        ArrayList<Cliente> list = new ArrayList<Cliente>();

        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM clientes")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Cliente(rs.getInt("idcliente"), rs.getString("nombre"), rs.getString("telefono"), rs.getString("direccion"), rs.getInt("idusuario")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return list;
    }

    @Override
    public Optional<Cliente> readStr(String inStr) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM clientes WHERE nombre LIKE ?")) {

            ps.setString(1, inStr);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Cliente(rs.getInt("idcliente"), rs.getString("nombre"), rs.getString("telefono"), rs.getString("direccion"), rs.getInt("idusuario")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        return Optional.empty();
    }

}
