package Model;

import Config.MyDB_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion de la clase "GenericDao", para manejar la tabla "resultado" <br>
 *
 * @author Alejandra
 */
public class ResultadoDao implements GenericDao<Resultado, Integer> {

    /**
     * Default Constructor <br>
     * Aqui llamamos al Constructor de la super clase (clase Padre), <br>
     *
     * Hay que mencionar que en un sistema grande, pudieran haber multiples bases de datos, <br>
     * usando multiples motores (mysql, postgres, oracle, ...), <br>
     * por lo que cada DAO una tendra asociada un Objeto Connection particular, en este caso a mydb usando mysql
     *
     * @author Alejandra
     */
    public ResultadoDao() {
        super();
    }

    // La operacion es atomica, abre y cierra la conexion
    @Override
    public Optional<Integer> create(Resultado inInstance) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("INSERT INTO resultado(Usuario_id, ResultadoIngresosTotales, ResultadoGastosTotales, UtilidadOperdida) VALUES(?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inInstance.getUsuario().getId());
            ps.setInt(2, inInstance.getIngresos());
            ps.setInt(3, inInstance.getGastos());
            ps.setInt(4, inInstance.getUtilidad());

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

    // La operacion es atomica, abre y cierra la conexion
    @Override
    public Optional<Resultado> read(Integer inPk) throws Exception {
        Resultado item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT * FROM resultado WHERE id= ?")) {

            ps.setInt(1, inPk);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recuperar el registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                Integer resultadoIngresosTotales = rs.getInt("ResultadoIngresosTotales");
                Integer resultadoGastosTotales = rs.getInt("ResultadoGastosTotales");
                Integer utilidadOperdida = rs.getInt("UtilidadOperdida");

                if (usuario != null) {
                    item = new Resultado(id, usuario, resultadoIngresosTotales, resultadoGastosTotales, utilidadOperdida);
                } else {
                    item = null;
                }
            } else {
                // indicativo, de que no fue encontrado el registro
                item = null;
            }

        }

        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Resultado> read(Integer inPk, Connection connection) throws Exception {
        Resultado item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( PreparedStatement ps = connection.prepareStatement("SELECT * FROM resultado WHERE id= ?")) {

            ps.setInt(1, inPk);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recuperar el registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                Integer resultadoIngresosTotales = rs.getInt("ResultadoIngresosTotales");
                Integer resultadoGastosTotales = rs.getInt("ResultadoGastosTotales");
                Integer utilidadOperdida = rs.getInt("UtilidadOperdida");

                if (usuario != null) {
                    item = new Resultado(id, usuario, resultadoIngresosTotales, resultadoGastosTotales, utilidadOperdida);
                } else {
                    item = null;
                }
            } else {
                // indicativo, de que no fue encontrado el registro
                item = null;
            }

        }

        return Optional.ofNullable(item);

    }

    public Optional<Resultado> readByUsuarioId(Integer inUsuarioId) throws Exception {
        Resultado item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT * FROM resultado WHERE Usuario_id= ?")) {

            ps.setInt(1, inUsuarioId);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recuperar el registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                Integer resultadoIngresosTotales = rs.getInt("ResultadoIngresosTotales");
                Integer resultadoGastosTotales = rs.getInt("ResultadoGastosTotales");
                Integer utilidadOperdida = rs.getInt("UtilidadOperdida");

                if (usuario != null) {
                    item = new Resultado(id, usuario, resultadoIngresosTotales, resultadoGastosTotales, utilidadOperdida);
                } else {
                    item = null;
                }
            } else {
                // indicativo, de que no fue encontrado el registro
                item = null;
            }

        }

        return Optional.ofNullable(item);

    }

    public Optional<Resultado> readByUsuario(Integer inUsuarioId, Connection connection) throws Exception {
        Resultado item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( PreparedStatement ps = connection.prepareStatement("SELECT * FROM resultado WHERE Usuario_id= ?")) {

            ps.setInt(1, inUsuarioId);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recuperar el registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                Integer resultadoIngresosTotales = rs.getInt("ResultadoIngresosTotales");
                Integer resultadoGastosTotales = rs.getInt("ResultadoGastosTotales");
                Integer utilidadOperdida = rs.getInt("UtilidadOperdida");

                if (usuario != null) {
                    item = new Resultado(id, usuario, resultadoIngresosTotales, resultadoGastosTotales, utilidadOperdida);
                } else {
                    item = null;
                }
            } else {
                // indicativo, de que no fue encontrado el registro
                item = null;
            }

        }

        return Optional.ofNullable(item);

    }

    @Override
    public Optional<Integer> update(Resultado inRegistro) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("UPDATE resultado SET Usuario_id=?, ResultadoIngresosTotales= ?, ResultadoGastosTotales= ?, UtilidadOperdida= ? WHERE id= ?")) {

            ps.setInt(1, inRegistro.getUsuario().getId());
            ps.setInt(2, inRegistro.getIngresos());
            ps.setInt(3, inRegistro.getGastos());
            ps.setInt(4, inRegistro.getUtilidad());
            ps.setInt(5, inRegistro.getId());

            // para resguardarnos en caso de error
            MyDB_Connection.BeginTransaction();

            int cantReg = ps.executeUpdate();

            if (cantReg <= 1) {
                MyDB_Connection.EndTransaction(); // cerramos la operacion
                if (cantReg == 1) {
                    resultId = inRegistro.getId(); // retornamos la misma PK
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

    // La operacion es atomica, abre y cierra la conexion
    @Override
    public Optional<Integer> delete(Integer inPk) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("DELETE FROM resultado WHERE id= ?")) {
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

    // La operacion es atomica, abre y cierra la conexion
    @Override
    public Optional<Resultado> findId(Integer inPk) throws Exception {
        return read(inPk);
    }

    @Override
    public Optional<Resultado> findId(Integer inPk, Connection inConnection) throws Exception {
        return read(inPk, inConnection);
    }

    // La operacion es atomica, abre y cierra la conexion
    @Override
    public List<Resultado> findAll() throws Exception {
        List<Resultado> list = new ArrayList<>();

        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT * FROM resultado")) {
            ResultSet rs = ps.executeQuery();

            // leer todos los registros recuperados
            while (rs.next()) {

                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                Integer resultadoIngresosTotales = rs.getInt("ResultadoIngresosTotales");
                Integer resultadoGastosTotales = rs.getInt("ResultadoGastosTotales");
                Integer utilidadOperdida = rs.getInt("UtilidadOperdida");

                if (usuario != null) {
                    list.add(new Resultado(id, usuario, resultadoIngresosTotales, resultadoGastosTotales, utilidadOperdida));
                }
            }
        }

        return list;
    }

    public static void main(String[] args) {

        try {
            ResultadoDao dao = new ResultadoDao();
            Optional<Resultado> optReg = Optional.of(new Resultado(0, null, 1000, 500, 500));
            Optional<Integer> optKey = dao.create(optReg.get());
            if (optKey.isPresent()) {
                System.out.println("Registro creado: " + optReg.toString());
            } else {
                System.out.println("Error al crear el registro");
            }

            optReg = dao.read(optKey.get());
            if (optReg.isPresent()) {
                System.out.println("Registro recuperado: " + optReg.get().toString());
            } else {
                System.out.println("Error al recuperar el registro");
            }

            optReg.get().setResultadoGastosTotales(2000);
            optKey = dao.update(optReg.get());
            if (optKey.isPresent()) {
                System.out.println("Registro actualizado: " + optReg.toString());
            } else {
                System.out.println("Error al actualizar el registro");
            }

            optKey = dao.delete(optKey.get());
            if (optKey.isPresent()) {
                System.out.println("Registro eliminado: " + optReg.toString());
            } else {
                System.out.println("Error al eliminar el registro");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
        }
    }

}
