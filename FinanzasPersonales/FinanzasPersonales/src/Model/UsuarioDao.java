package Model;

import Config.MyDB_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion de la clase "GenericDao", para manejar la tabla "usuario" <br>
 *
 * @author Alejandra
 */
public class UsuarioDao implements GenericDao<Usuario, Integer> {

    /**
     * Default Constructor <br>
     * Aqui llamamos al Constructor de la super clase (clase Padre), <br>
     *
     * Hay que mencionar que en un sistema grande, pudieran haber multiples bases de datos, <br>
     * usando multiples motores (mysql, postgres, oracle, ...), <br>
     * por lo que cada DAO una tendra asociada un Objeto Connection particular, en este caso a mydb usando mysql
     *
     */
    public UsuarioDao() {
        super();
    }

    @Override
    public Optional<Integer> create(Usuario inInstance) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("INSERT INTO usuario(NombreCompleto, Fecha, Direccion, Correo, Telefono, nickName, passWord) VALUES(?,?,?,?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, inInstance.getNombreCompleto());
            ps.setString(2, inInstance.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setString(3, inInstance.getDireccion());
            ps.setString(4, inInstance.getCorreo());
            ps.setString(5, inInstance.getTelefono());
            ps.setString(6, inInstance.getNickname());
            ps.setBytes(7, inInstance.getPassword());

            // verificar que la operacion fue exitosa
            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    resultId = generatedKeys.getInt(1);
                } else {
                    resultId = null;
                }
            } else {
                resultId = null;
            }
        }

        return Optional.ofNullable(resultId);
    }

    @Override
    public Optional<Usuario> read(Integer inPk) throws Exception {

        Usuario item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE id=?")) {

            ps.setInt(1, inPk);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recobrar un registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                String nombreCompleto = rs.getString("NombreCompleto");
                LocalDateTime fecha = LocalDateTime.parse(rs.getString("Fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Leer el String y pasarlo como LocalDateTime
                String direccion = rs.getString("Direccion");
                String correo = rs.getString("Correo");
                String telefono = rs.getString("Telefono");
                String nickName = rs.getString("nickName");
                byte password[] = rs.getBytes("passWord");

                item = new Usuario(id, nombreCompleto, fecha, direccion, correo, telefono, nickName, password);
            } else {
                // indicativo, de que no fue encontrado el registro
                item = null;
            }

        }

        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Usuario> read(Integer inPk, Connection connection) throws Exception {

        Usuario item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE id=?")) {

            ps.setInt(1, inPk);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recobrar un registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                String nombreCompleto = rs.getString("NombreCompleto");
                LocalDateTime fecha = LocalDateTime.parse(rs.getString("Fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Leer el String y pasarlo como LocalDateTime
                String direccion = rs.getString("Direccion");
                String correo = rs.getString("Correo");
                String telefono = rs.getString("Telefono");
                String nickName = rs.getString("nickName");
                byte password[] = rs.getBytes("passWord");

                item = new Usuario(id, nombreCompleto, fecha, direccion, correo, telefono, nickName, password);
            } else {
                // indicativo, de que no fue encontrado el registro
                item = null;
            }

        }

        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Integer> update(Usuario inInstance) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("UPDATE usuario SET NombreCompleto=?, Fecha=?, Direccion=?, Correo=?, Telefono=?, nickName=? WHERE id=?")) {

            ps.setString(1, inInstance.getNombreCompleto());
            ps.setString(2, inInstance.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setString(3, inInstance.getDireccion());
            ps.setString(4, inInstance.getCorreo());
            ps.setString(5, inInstance.getTelefono());
            ps.setString(6, inInstance.getNickname());
            ps.setInt(7, inInstance.getId());

            // para resguardarnos en caso de error
            MyDB_Connection.BeginTransaction();

            int cantReg = ps.executeUpdate();

            if (cantReg <= 1) {
                MyDB_Connection.EndTransaction(); // cerramos la operacion
                if (cantReg == 1) {
                    resultId = inInstance.getId(); // retornamos la misma PK
                } else {
                    resultId = null; // no se pudo actualizar
                }
            } else {
                MyDB_Connection.RollBack(); // rechazamos la operacion
                throw new Exception("Error al tratar de actualizar el registro");
            }
        }

        return Optional.ofNullable(resultId);
    }

    @Override
    public Optional<Integer> delete(Integer inPk) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("DELETE FROM usuario WHERE id=?")) {
            ps.setLong(1, inPk);

            MyDB_Connection.BeginTransaction();

            int cantReg = ps.executeUpdate();

            if (cantReg <= 1) {
                MyDB_Connection.EndTransaction(); // cerramos la operacion
                if (cantReg == 1) {
                    resultId = inPk; // retornamos la misma PK
                } else {
                    resultId = null; // no se pudo actualizar
                }
            } else {
                MyDB_Connection.RollBack(); // rechazamos la operacion
                throw new Exception("Error al tratar de actualizar el registro");
            }
        }

        return Optional.ofNullable(resultId);
    }

    @Override
    public Optional<Usuario> findId(Integer inPk) throws Exception {
        return read(inPk);
    }

    @Override
    public Optional<Usuario> findId(Integer inPk, Connection InConnection) throws Exception {
        return read(inPk, InConnection);
    }

    public Optional<Usuario> findNickName(String inNickname) throws Exception {
        Usuario item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE nickName=?")) {

            ps.setString(1, inNickname);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recobrar un registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                String nombreCompleto = rs.getString("NombreCompleto");
                LocalDateTime fecha = LocalDateTime.parse(rs.getString("Fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Leer el String y pasarlo como LocalDateTime
                String direccion = rs.getString("Direccion");
                String correo = rs.getString("Correo");
                String telefono = rs.getString("Telefono");
                String nickName = rs.getString("nickName");
                byte password[] = rs.getBytes("passWord");

                item = new Usuario(id, nombreCompleto, fecha, direccion, correo, telefono, nickName, password);
            } else {
                // indicativo, de que no fue encontrado el registro
                item = null;
            }

        }

        return Optional.ofNullable(item);

    }

    @Override
    public List<Usuario> findAll() throws Exception {
        List<Usuario> list = new ArrayList<>();

        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario")) {
            ResultSet rs = ps.executeQuery();

            // leer todos los registros recuperados
            while (rs.next()) {

                Integer id = rs.getInt("id");
                String nombreCompleto = rs.getString("NombreCompleto");
                LocalDateTime fecha = LocalDateTime.parse(rs.getString("Fecha"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Leer el String y pasarlo como LocalDateTime
                String direccion = rs.getString("Direccion");
                String correo = rs.getString("Correo");
                String telefono = rs.getString("Telefono");
                String nickName = rs.getString("nickName");
                byte password[] = rs.getBytes("passWord");

                list.add(new Usuario(id, nombreCompleto, fecha, direccion, correo, telefono, nickName, password));
            }
        }

        return list;
    }

    /**
     * Con este codigo, realizamos el test sobre la clase
     *
     * @param args
     */
    public static void main(String[] args) {

        Optional<Integer> a = Optional.empty();
        Optional<Integer> b = Optional.ofNullable(null);

        if (a.isPresent()) {
            System.out.println("a.isPresent()");
        } else {
            System.out.println("NOT a.isPresent()");
        }

        if (b.isPresent()) {
            System.out.println("b.isPresent()");
        } else {
            System.out.println("NOT b.isPresent()");
        }

        UsuarioDao dao;
        Optional<Usuario> optUser;
        Optional<Integer> optKey;

        try {
            dao = new UsuarioDao();

            byte passWord[] = new byte[32];
            optUser = Optional.of(new Usuario(0, "Roger5", LocalDateTime.now(), "direccion5", "correo5", "telefono5", "baba6", passWord));

            optKey = dao.create(optUser.get());
            if (optKey.isPresent()) {
                System.out.println("Usuario creado, PK: " + optKey.toString());
            } else {
                System.out.println("Error al crear el registro");
            }

            optUser = dao.read(optKey.get());

            if (optUser.isPresent()) {
                System.out.println("Usuario recuperado: " + optUser.toString());
            } else {
                System.out.println("Usuario no encontrado");
            }

            optUser.get().setNombreCompleto("Jesus Antonio 3");
            optKey = dao.update(optUser.get());
            if (optKey.isPresent()) {
                System.out.println("Usuario actualizado: " + optUser.toString());
            } else {
                System.out.println("Error al actualizar el registro");
            }

            optKey = dao.delete(optKey.get());
            if (optKey.isPresent()) {
                System.out.println("Usuario eliminado: " + optUser.toString());
            } else {
                System.out.println("Error al eliminar el registro");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
        }
    }

}
