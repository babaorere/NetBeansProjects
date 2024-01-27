package serviciobarberia;

import serviciobarberia.modelo.Usuario;
import serviciobarberia.modelo.UsuarioCRUD;

/**
 *
 */
public class TestUsuario {

    public static void main(String[] args) {

        UsuarioCRUD crud = new UsuarioCRUD();

        Usuario c = new Usuario(null, "Roger", "12345678901234567890123456789012");

        if (!crud.create(c).isPresent()) {
            System.out.println("Error al tratar de crear el registro");
            return;
        }

        if (!crud.read(c.getIdUsuario()).isPresent()) {
            System.out.println("Error al tratar de leer el registro");
            return;
        }

        System.out.println("Password es: " + c.getPassword());

        c.setNickname("Magnifico");

        if (!crud.update(c).isPresent()) {
            System.out.println("Error al tratar de actualizar el registro");
            return;
        }

        if (!crud.delete(c.getIdUsuario())) {
            System.out.println("Error al tratar de eliminar el registro");
            return;
        }
    }

}
