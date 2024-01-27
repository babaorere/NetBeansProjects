package acceso_datos;

import java.util.List;


/**
 *
 * @author @param <T>
 */
public interface IDAO<T> {

    T get(long id);


    List<T> getAll();


    boolean save(T inT);


    boolean update(T inT);


    boolean delete(T inT);


    boolean delete(long inEn_id);
}
