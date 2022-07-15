package acceso_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import negocio.Encomienda;
import servicios.Conn;

/**
 *
 *
 */
public class DAO_Encomienda implements IDAO<Encomienda> {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    public Encomienda get(long inId) {

        connection = null;
        ps = null;
        rs = null;

        Encomienda item = null;

        try {
            String sql = "SELECT * FROM encomienda WHERE en_id= ?";
            connection = Conn.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, inId);
            rs = ps.executeQuery();

            if (rs.next()) {
                long en_id = rs.getLong("en_id");
                String en_destinatario = rs.getString("en_destinatario");
                String en_direccion = rs.getString("en_direccion");
                String en_tipo = rs.getString("en_tipo");
                int en_entregadomicilio = rs.getInt("en_entregadomicilio");
                String en_tamano = rs.getString("en_tamano");
                String en_remitente = rs.getString("en_remitente");

                item = new Encomienda(en_id, en_destinatario, en_direccion, en_tipo, en_entregadomicilio, en_tamano, en_remitente);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en <public Object get(long inId)> \n" + e.toString());
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
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en <public Object get(long inId)> \n" + e.toString());
            }
        }

        return item;
    }

    @Override
    public List getAll() {

        connection = null;
        ps = null;
        rs = null;

        List<Encomienda> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM encomienda";
            connection = Conn.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                long en_id = rs.getLong("en_id");
                String en_destinatario = rs.getString("en_destinatario");
                String en_direccion = rs.getString("en_direccion");
                String en_tipo = rs.getString("en_tipo");
                int en_entregadomicilio = rs.getInt("en_entregadomicilio");
                String en_tamano = rs.getString("en_tamano");
                String en_remitente = rs.getString("en_remitente");

                list.add(new Encomienda(en_id, en_destinatario, en_direccion, en_tipo, en_entregadomicilio, en_tamano, en_remitente));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en <public List getAll()> \n" + e.toString());
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
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en <public List getAll()> \n" + e.toString());
            }
        }

        return list;
    }

    @Override
    public boolean save(Encomienda inT) {

        boolean saved = false;

        connection = null;
        ps = null;
        rs = null;

        try {
            String sql = "INSERT INTO encomienda(en_destinatario, en_direccion, en_tipo, en_entregadomicilio, en_tamano, en_remitente) VALUES(?,?,?,?,?,?)";
            connection = Conn.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, inT.getEn_destinatario());
            ps.setString(2, inT.getEn_direccion());
            ps.setString(3, inT.getEn_tipo());
            ps.setInt(4, inT.getEn_entregadomicilio());
            ps.setString(5, inT.getEn_tamano());
            ps.setString(6, inT.getEn_remitente());
            saved = ps.executeUpdate() == 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en <public void save(Encomienda inT)> \n" + e.toString());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    Conn.CloseConnection();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en <public void save(Encomienda inT)> \n" + e.toString());
            }
        }

        return saved;
    }

    @Override
    public boolean update(Encomienda inT) {

        connection = null;
        ps = null;
        rs = null;

        boolean updated = false;

        try {
            String queryString = "UPDATE encomienda SET en_destinatario= ?, en_direccion= ?, en_tipo= ?, en_entregadomicilio= ?, en_tamano= ?, en_remitente= ? WHERE en_id= ?";
            connection = Conn.getConnection();
            ps = connection.prepareStatement(queryString);
            ps.setString(1, inT.getEn_destinatario());
            ps.setString(2, inT.getEn_direccion());
            ps.setString(3, inT.getEn_tipo());
            ps.setInt(4, inT.getEn_entregadomicilio());
            ps.setString(5, inT.getEn_tamano());
            ps.setString(6, inT.getEn_remitente());
            ps.setLong(7, inT.getEn_id());
            updated = ps.executeUpdate() == 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en <public void update(Encomienda inT)> \n" + e.toString());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    Conn.CloseConnection();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en <public void update(Encomienda inT)> \n" + e.toString());
            }
        }

        return updated;
    }

    @Override
    public boolean delete(Encomienda inT) {
        return delete(inT.getEn_id());
    }

    @Override
    public boolean delete(long inId) {

        connection = null;
        ps = null;
        rs = null;

        boolean deleted = false;

        try {
            connection = Conn.getConnection();
            ps = connection.prepareStatement("DELETE FROM usuario WHERE id= ?");
            ps.setLong(1, inId);
            deleted = ps.executeUpdate() == 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en <public void delete(Encomienda inT)> \n" + e.toString());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    Conn.CloseConnection();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en <public void delete(Encomienda inT)> \n" + e.toString());
            }
        }

        return deleted;
    }
}
