package serviciobarberia;

import serviciobarberia.modelo.Cliente;
import serviciobarberia.modelo.ClienteCRUD;

/**
 *
 */
public class TestCliente {

    public static void main(String[] args) {

        ClienteCRUD crud = new ClienteCRUD();

        Cliente c = new Cliente(null, "Roger", "555", "Puente Alto", null);

        if (!crud.create(c).isPresent()) {
            System.out.println("Error al tratar de crear el registro");
            return;
        }

        if (!crud.read(c.getIdcliente()).isPresent()) {
            System.out.println("Error al tratar de leer el registro");
            return;
        }

        c.setNombre("Roger Antonio");

        if (!crud.update(c).isPresent()) {
            System.out.println("Error al tratar de actualizar el registro");
            return;
        }

        if (!crud.delete(c.getIdcliente())) {
            System.out.println("Error al tratar de eliminar el registro");
            return;
        }
    }

}
