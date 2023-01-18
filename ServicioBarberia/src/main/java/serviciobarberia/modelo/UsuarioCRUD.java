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
public class UsuarioCRUD implements CRUD<Usuario, Integer> {

    public UsuarioCRUD() {

    }

    @Override
    public Optional<Usuario> create(Usuario inEntity) {

        Integer resultId;

        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("INSERT INTO usuarios(nickname, password) VALUES(?,?)",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, inEntity.getNickname());
            ps.setString(2, String.valueOf(inEntity.getPassword()));

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

        inEntity.setIdUsuario(resultId);
        return Optional.of(new Usuario(resultId, inEntity.getNickname(), inEntity.getPassword()));
    }

    @Override
    public Optional<Usuario> read(Integer inId) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM usuarios WHERE idusuario=?")) {

            ps.setInt(1, inId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Usuario(rs.getInt("idusuario"), rs.getString("nickname"), rs.getString("password")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        return Optional.empty();
    }

    public Optional<Usuario> readByNickname(String inNickname) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM usuarios WHERE nickname=?")) {

            ps.setString(1, inNickname);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Usuario(rs.getInt("idusuario"), rs.getString("nickname"), rs.getString("password")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Usuario> update(Usuario inEntity) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("UPDATE usuarios SET nickname=?, password=? WHERE idusuario=?")) {

            ps.setString(1, inEntity.getNickname());
            ps.setString(2, inEntity.getPassword());
            ps.setInt(3, inEntity.getIdUsuario());
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
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("DELETE FROM usuarios WHERE idusuario=?")) {

            ps.setInt(1, inId);

            // verificar que la operacion fue exitosa
            return ps.executeUpdate() == 1;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }

        return false;
    }

    @Override
    public ArrayList<Usuario> findAll() {

        return new ArrayList<Usuario>();
    }

    @Override
    public Optional<Usuario> readStr(String inStr) {
        try ( Connection connection = MyConn.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT *  FROM usuarios WHERE nickname LIKE ?")) {

            ps.setString(1, inStr);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Usuario(rs.getInt("idusuario"), rs.getString("nickname"), rs.getString("password")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
            return Optional.empty();
        }

        return Optional.empty();

    }

}
