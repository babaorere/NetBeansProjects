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
 * Implementacion de la clase "GenericDao", para manejar la tabla "tipo de gastos" <br>
 *
 * @author Alejandra
 */
public class TipoDeGastoDao implements GenericDao<TipoDeGasto, Integer> {

    /**
     * Default Constructor <br>
     * Aqui llamamos al Constructor de la super clase (clase Padre), <br>
     *
     * Hay que mencionar que en un sistema grande, pudieran haber multiples bases de datos, <br>
     * usando multiples motores (mysql, postgres, oracle, ...), <br>
     * por lo que cada DAO una tendra asociada un Objeto Connection particular, en este caso a mydb usando mysql
     *
     */
    public TipoDeGastoDao() {
        super();
    }

    @Override
    public Optional<Integer> create(TipoDeGasto inInstance) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("INSERT INTO tipoDeGastos(Usuario_id, TipoDeGastos, Valorgasto, Fechagasto) VALUES(?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inInstance.getUsuario().getId());
            ps.setString(2, inInstance.getTipoDeGastos());
            ps.setInt(3, inInstance.getValorGasto());
            ps.setString(4, inInstance.getFechaGasto().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

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

            Integer totalG = totalGastoByUsuario(inInstance.getUsuario().getId(), connection);
            Integer totalI = (new TipoDeIngresoDao()).totalByUsuario(inInstance.getUsuario().getId(), connection);

            ResultadoDao daoR = new ResultadoDao();

            Optional<Resultado> resOtp = daoR.findId(inInstance.getUsuario().getId());

            Resultado resultado;
            if (resOtp.isPresent()) {
                resultado = resOtp.get();
                resultado.setResultadoGastosTotales(totalG);
                resultado.setResultadoIngresosTotales(totalI);
                resultado.setUtilidadOperdida(totalI - totalG);

                daoR.update(resultado);
            } else {
                daoR.create(new Resultado(0, inInstance.getUsuario(), totalI, totalG, totalI - totalG));
            }

        }

        return Optional.ofNullable(resultId);

    }

    @Override
    public Optional<TipoDeGasto> read(Integer inPk) throws Exception {

        TipoDeGasto item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT * FROM tipoDeGastos WHERE id= ?")) {

            ps.setInt(1, inPk);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recobrar un registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                String tipoDeGastos = rs.getString("TipoDeGastos");
                Integer valorGasto = rs.getInt("ValorGasto");
                LocalDateTime fechaGasto = LocalDateTime.parse(rs.getString("FechaGasto"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Leer el String y pasarlo como LocalDateTime

                if (usuario != null) {
                    item = new TipoDeGasto(id, usuario, tipoDeGastos, valorGasto, fechaGasto);
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
    public Optional<Integer> update(TipoDeGasto inRegistro) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("UPDATE tipoDeGastos SET Usuario_id= ?, TipoDeGastos= ?, ValorGasto= ?, FechaGasto= ? WHERE id= ?")) {

            ps.setInt(1, inRegistro.getUsuario().getId());
            ps.setString(2, inRegistro.getTipoDeGastos());
            ps.setInt(3, inRegistro.getValorGasto());
            ps.setString(4, inRegistro.getFechaGasto().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setInt(5, inRegistro.getId());

            // para resguardarnos en caso de error
            MyDB_Connection.BeginTransaction();

            int cantReg = ps.executeUpdate();

            Integer totalG = totalGastoByUsuario(inRegistro.getUsuario().getId(), connection);
            Integer totalI = (new TipoDeIngresoDao()).totalByUsuario(inRegistro.getUsuario().getId(), connection);

            ResultadoDao daoR = new ResultadoDao();

            Optional<Resultado> resOtp = daoR.findId(inRegistro.getUsuario().getId());

            Resultado resultado;
            if (resOtp.isPresent()) {
                resultado = resOtp.get();
                resultado.setResultadoGastosTotales(totalG);
                resultado.setResultadoIngresosTotales(totalI);
                resultado.setUtilidadOperdida(totalI - totalG);

                daoR.update(resultado);
            } else {
                daoR.create(new Resultado(0, inRegistro.getUsuario(), totalI, totalG, totalI - totalG));
            }

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

    @Override
    public Optional<Integer> delete(Integer inPk) throws Exception {

        Integer resultId;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("DELETE FROM tipoDeGastos WHERE id= ?")) {
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
    public Optional<TipoDeGasto> findId(Integer inPk) throws Exception {
        return read(inPk);
    }

    @Override
    public List<TipoDeGasto> findAll() throws Exception {
        List<TipoDeGasto> list = new ArrayList<>();

        try ( Connection connection = MyDB_Connection.getConnection();  PreparedStatement ps = connection.prepareStatement("SELECT * FROM tipoDeGastos")) {
            ResultSet rs = ps.executeQuery();

            // leer todos los registros recuperados
            while (rs.next()) {

                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                String tipoDeGastos = rs.getString("TipoDeGastos");
                Integer valorGasto = rs.getInt("ValorGasto");
                LocalDateTime fechaGasto = LocalDateTime.parse(rs.getString("FechaGasto"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Leer el String y pasarlo como LocalDateTime

                if (usuario != null) {
                    list.add(new TipoDeGasto(id, usuario, tipoDeGastos, valorGasto, fechaGasto));
                }
            }
        }

        return list;
    }

    @Override
    public Optional<TipoDeGasto> read(Integer inPk, Connection connection) throws Exception {
        TipoDeGasto item;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( PreparedStatement ps = connection.prepareStatement("SELECT * FROM tipoDeGastos WHERE id= ?")) {

            ps.setInt(1, inPk);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recobrar un registro
            if (rs.next()) {
                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                String tipoDeGastos = rs.getString("TipoDeGastos");
                Integer valorGasto = rs.getInt("ValorGasto");
                LocalDateTime fechaGasto = LocalDateTime.parse(rs.getString("FechaGasto"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Leer el String y pasarlo como LocalDateTime

                if (usuario != null) {
                    item = new TipoDeGasto(id, usuario, tipoDeGastos, valorGasto, fechaGasto);
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
    public Optional<TipoDeGasto> findId(Integer inPk, Connection inConnection) throws Exception {
        return read(inPk, inConnection);
    }

    public List<TipoDeGasto> readByUsuario(Integer inUsuarioId, Connection connection) throws Exception {
        TipoDeGasto item;

        ArrayList<TipoDeGasto> list = new ArrayList<>();

        // try con recursos, a la salida cierra los recursos abiertos
        try ( PreparedStatement ps = connection.prepareStatement("SELECT * FROM tipoDeGastos WHERE Usuario_id= ?")) {

            ps.setInt(1, inUsuarioId);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recobrar un registro
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Usuario usuario = (new UsuarioDao()).findId(rs.getInt("Usuario_id"), connection).get();
                String tipoDeGastos = rs.getString("TipoDeGastos");
                Integer valorGasto = rs.getInt("ValorGasto");
                LocalDateTime fechaGasto = LocalDateTime.parse(rs.getString("FechaGasto"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Leer el String y pasarlo como LocalDateTime

                if (usuario != null) {
                    list.add(new TipoDeGasto(id, usuario, tipoDeGastos, valorGasto, fechaGasto));
                }

            }

            return list;

        }

    }

    public Integer totalGastoByUsuario(Integer inUsuarioId, Connection connection) throws Exception {

        Integer total = 0;

        // try con recursos, a la salida cierra los recursos abiertos
        try ( PreparedStatement ps = connection.prepareStatement("SELECT SUM(ValorGasto) FROM tipoDeGastos WHERE Usuario_id= ?")) {

            ps.setInt(1, inUsuarioId);
            ResultSet rs = ps.executeQuery();

            // Verificar si fue posible recobrar un registro
            if (rs.next()) {
                total = rs.getInt(1);
            } else {
                total = 0;
            }

        }

        return total;

    }
}
