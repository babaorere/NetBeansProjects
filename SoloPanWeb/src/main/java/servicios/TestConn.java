package servicios;

import java.sql.Connection;
import java.util.List;
import modelos.DaoUsuario;
import modelos.Usuario;

/**
 *
 */
public class TestConn {

    public static void main(String[] args) {

        Connection conexion;
        try {
            conexion = Conn.getConnection();
        } catch (Exception ex) {
            conexion = null;
        }

        if (conexion != null) {
            System.out.println("Conexión establecida");
        } else {
            System.out.println("Error en la Conexión");
        }

        DaoUsuario dao = new DaoUsuario();

        long newId = 0;
        Usuario item = new Usuario(0L, "Roger8", "magnifico8", "1234", 1);
        try {
            newId = dao.save(item);
            if (newId > 0) {
                System.out.println("Usuario guardado con exito");
            } else {
                System.out.println("Error al tratar de guardar un Usuario");
            }
        } catch (Exception ex) {
            System.out.println("Error al tratar de guardar un Usuario");
        }

        try {
            item = dao.get(newId);
            if (item != null) {
                System.out.println("Usuario recuperado con exito. Nombre= " + item.getNombre());
            } else {
                System.out.println("Error al tratar de recuperar un Usuario");
            }
        } catch (Exception ex) {
            System.out.println("Error al tratar de recuperar un Usuario");
        }

        try {
            if (dao.delete(item)) {
                System.out.println("Usuario eliminado con exito");
            } else {
                System.out.println("Error al tratar de eliminar un Usuario");
            }
        } catch (Exception ex) {
            System.out.println("Error al tratar de eliminar un Usuario");
        }

        try {
            List<Usuario> list = dao.getAll();

            if (list != null && list.size() > 0) {
                System.out.println("Usuarios rescatados con exito: " + list.size());
            } else {
                System.out.println("Error al tratar de recuperar los Usuarios");
            }
        } catch (Exception ex) {
            System.out.println("Error al tratar de recuperar los Usuarios");
        }

    }
}
