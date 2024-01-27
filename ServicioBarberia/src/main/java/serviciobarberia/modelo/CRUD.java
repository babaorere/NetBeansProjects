package serviciobarberia.modelo;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Creamos una iterface comun a todos los CRUD, utilizamos "Genericos" de java
 */
public interface CRUD<T, K> {

    Optional<T> create(T entity);

    Optional<T> read(K inId);

    Optional<T> readStr(String inStr);

    Optional<T> update(T entity);

    boolean delete(K inId);

    ArrayList<T> findAll();

}
