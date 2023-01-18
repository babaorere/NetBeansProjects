package serviciobarberia;

import serviciobarberia.modelo.Servicio;
import serviciobarberia.modelo.ServicioCRUD;

/**
 *
 */
public class TestServicio {

    public static void main(String[] args) {

        ServicioCRUD crud = new ServicioCRUD();

        Servicio c = new Servicio(null, "Corte Caballero", 555);

        if (!crud.create(c).isPresent()) {
            System.out.println("Error al tratar de crear el registro");
            return;
        }

        if (!crud.read(c.getIdServicio()).isPresent()) {
            System.out.println("Error al tratar de leer el registro");
            return;
        }

        c.setNombre("Corte Caballero corto");

        if (!crud.update(c).isPresent()) {
            System.out.println("Error al tratar de actualizar el registro");
            return;
        }

        if (!crud.delete(c.getIdServicio())) {
            System.out.println("Error al tratar de eliminar el registro");
            return;
        }
    }

}
