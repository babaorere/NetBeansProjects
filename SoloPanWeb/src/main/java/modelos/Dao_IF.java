package modelos;

import java.util.List;

/**
 *
 * @param <T>
 */
public interface Dao_IF<T> {

    T get(long inId) throws Exception;

    List<T> getAll() throws Exception;

    /**
     * En caso de exito retorna el ID del registrorecien guardado, <br>
     * en caso de error retornar un valor menor o igual a 0
     *
     * @param inT
     * @return
     * @throws Exception
     */
    long save(T inT) throws Exception;

    boolean update(T inT) throws Exception;

    boolean delete(T inT) throws Exception;

    boolean delete(long inId) throws Exception;
}
