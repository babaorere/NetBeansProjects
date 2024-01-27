package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import servicios.Conn;

/**
 *
 */
public class DaoUsuario implements Dao_IF<Usuario> {

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public Usuario get(long inId) throws Exception {

        connection = null;
        ps = null;
        rs = null;

        Usuario item = null;

        try {
            connection = Conn.getConnection();
            ps = connection.prepareStatement("SELECT * FROM usuarios WHERE id= ?");
            ps.setLong(1, inId);
            rs = ps.executeQuery();

            if (rs.next()) {
                long id = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String user = rs.getString("user");
                String password = rs.getString("password");
                int rol = rs.getInt("rol");

                item = new Usuario(id, nombre, user, password, rol);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    Conn.CloseConnection();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }

        return item;
    }

    @Override
    public List<Usuario> getAll() throws Exception {

        connection = null;
        ps = null;
        rs = null;

        List<Usuario> list = new ArrayList<>();

        try {
            connection = Conn.getConnection();
            ps = connection.prepareStatement("SELECT * FROM usuarios");
            rs = ps.executeQuery();

            while (rs.next()) {

                long id = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String user = rs.getString("user");
                String password = rs.getString("password");
                int rol = rs.getInt("rol");

                list.add(new Usuario(id, nombre, user, password, rol));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    Conn.CloseConnection();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }

        return list;
    }

    @Override
    public long save(Usuario inT) throws Exception {
        long newId = 0;

        connection = null;
        ps = null;
        rs = null;

        try {
            connection = Conn.getConnection();
            ps = connection.prepareStatement("INSERT INTO usuarios(nombre, user, password, rol) VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, inT.getNombre());
            ps.setString(2, inT.getUser());
            ps.setString(3, inT.getPassword());
            ps.setInt(4, inT.getRol());
            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    newId = generatedKeys.getLong(1);
                }
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    Conn.CloseConnection();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }

        return newId;
    }

    @Override
    public boolean update(Usuario inT) throws Exception {

        connection = null;
        ps = null;
        rs = null;

        boolean updated = false;

        try {
            connection = Conn.getConnection();
            ps = connection.prepareStatement("UPDATE usuario SET nombre= ?, user= ?, password= ?, rol= ? WHERE id= ?");
            ps.setString(1, inT.getNombre());
            ps.setString(2, inT.getUser());
            ps.setString(3, inT.getPassword());
            ps.setInt(4, inT.getRol());
            ps.setLong(5, inT.getId());
            updated = ps.executeUpdate() == 1;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {

                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    Conn.CloseConnection();
                }

            } catch (Exception ex) {
                throw ex;
            }
        }

        return updated;
    }

    @Override
    public boolean delete(Usuario inT) throws Exception {
        return delete(inT.getId());
    }

    @Override
    public boolean delete(long inId) throws Exception {

        connection = null;
        ps = null;
        rs = null;

        boolean deleted = false;

        try {
            connection = Conn.getConnection();
            ps = connection.prepareStatement("DELETE FROM usuarios WHERE id= ?");
            ps.setLong(1, inId);
            deleted = ps.executeUpdate() == 1;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {

                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    Conn.CloseConnection();
                }

            } catch (Exception ex) {
                throw ex;
            }
        }

        return deleted;
    }
}
