package Model;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Creamos una interface, que nos servira para usar cualquier sub-clase GenericDAO, <br>
 * la Utlidad del DAO es abstraer de la clase POJO, las Operaciones sobre la BD <br>
 *
 * @param <T> clase del equivalente Java, de la tabla en la base de datos
 * @param <PK> clase de la clave primaria, por lo general es de tipo Integer o String
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * Crea el registro de la tabla de la base de datos, a partir de un objeto T, <br>
     * por ejemplo: T es una clase Usuario, con una PK Integer llamada "id" en la tabla "Usuarios" de la BD
     *
     * La operacion es atomica, abre y cierra la conexion
     *
     * @param inInstance
     * @return la Pk recien creada, o null en caso de error
     * @throws java.lang.Exception
     */
    Optional<PK> create(T inInstance) throws Exception;

    /**
     * Retorna un objeto T, a partir de una clave primaria, por los general Integer o String <br>
     * por ejemplo: T es una clase Usuario, con una PK Integer llamada "id" en la tabla "Usuarios" de la BD
     *
     * La operacion es atomica, abre y cierra la conexion
     *
     * @param inPk
     * @return el registro en caso de una busqueda exitosa, null en caso de no encontrado
     * @throws java.lang.Exception
     */
    Optional<T> read(PK inPk) throws Exception;

    /**
     * Retorna un objeto T, con una clave PK, guardado previamente en la base de datos <br>
     *
     * La operacion es atomica, utiliza una conexion externa
     *
     * @param inPk
     * @param connection
     * @return el registro si es exitoso, en caso contrario retorna null
     * @throws java.lang.Exception
     */
    public Optional<T> read(Integer inPk, Connection connection) throws Exception;

    /**
     * Guarda los cambios sobre un objeto T, creado anteriormente en la base de datos, <br>
     * por ejemplo: T es una clase Usuario, con una PK Integer llamada "id" en la tabla "Usuarios" de la BD
     *
     * La operacion es atomica, abre y cierra la conexion
     *
     * @param inInstance
     * @return PK if success, null on fail
     * @throws java.lang.Exception
     */
    Optional<PK> update(T inInstance) throws Exception;

    /**
     * Elemina un objeto T, guardado previamente en la base de datos <br>
     * por ejemplo: T es una clase Usuario, con una PK Integer llamada "id" en la tabla "Usuarios" de la BD
     *
     * La operacion es atomica, abre y cierra la conexion
     *
     * @param inPk
     * @return PK if success, null on fail
     * @throws java.lang.Exception
     */
    Optional<PK> delete(PK inPk) throws Exception;

    /**
     * Retorna un objeto T, con una clave PK, guardado previamente en la base de datos <br>
     *
     * La operacion es atomica, abre y cierra la conexion
     *
     * @param inPk
     * @return el registro si es exitoso, en caso contrario retorna null
     * @throws java.lang.Exception
     */
    Optional<T> findId(PK inPk) throws Exception;

    /**
     * Retorna un objeto T, con una clave PK, guardado previamente en la base de datos <br>
     *
     * La operacion es atomica, utiliza una conexion externa
     *
     * @param inPk
     * @param connection
     * @return el registro si es exitoso, en caso contrario retorna null
     * @throws java.lang.Exception
     */
    public Optional<T> findId(Integer inPk, Connection connection) throws Exception;

    /**
     * Retorna una List de todos los Objetos, almacenados en la tabla de la base de datos
     *
     * La operacion es atomica, abre y cierra la conexion
     *
     * @return una lista de los registros, puede estar vacia
     * @throws java.lang.Exception
     */
    List<T> findAll() throws Exception;

}
