package Model;

import com.google.gson.Gson;
import java.io.Serializable;

/**
 * A partir de la definicion de "resultado" en la BD: CREATE TABLE `resultado` <br>
 *
 * CREATE TABLE `resultado` ( `id` int unsigned NOT NULL, <br>
 * `Resultadoingresostotales` int NOT NULL, <br>
 * `ResultadoGastosTotales` int DEFAULT NULL, <br>
 * `UtilidadOperdida` varchar(45) NOT NULL, <br>
 * PRIMARY KEY (`id`) )
 *
 * Procedemos a crear el objeto POJO. Utilizamos la implementacion de la interface "Serializable", <br>
 * en prevision de que la data sea transmitida por Internet
 *
 * @author Alejandra
 */
public class Resultado implements Serializable {

    private Integer id;
    private Usuario usuario;
    private Integer resultadoIngresosTotales;
    private Integer resultadoGastosTotales;
    private Integer utilidadOperdida;

    /**
     * Constructor por defecto, se sugiere que toda clase Serializable, debe tenerlo <br>
     * Los valores son arbitrarios, y a proposito son valores invalidos, para obligar a la generacion de <br>
     * una excepcion en caso de que no se hallan establecido adecuadamente, con posterioridad
     */
    public Resultado() {
        this(null, null, null, null, null);
    }

    /**
     * Constructor, con parametros, para establecer los valores iniciales del Objeto
     *
     * @param id
     * @param inUsuario
     * @param resultadoIngresosTotales
     * @param resultadoGastosTotales
     * @param utilidadOperdida
     */
    public Resultado(Integer id, Usuario inUsuario, Integer resultadoIngresosTotales, Integer resultadoGastosTotales, Integer utilidadOperdida) {
        super();
        this.id = id;
        this.usuario = inUsuario;
        this.resultadoIngresosTotales = resultadoIngresosTotales;
        this.resultadoGastosTotales = resultadoGastosTotales;
        this.utilidadOperdida = utilidadOperdida;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the resultadoIngresosTotales
     */
    public Integer getIngresos() {
        return resultadoIngresosTotales;
    }

    /**
     * @param resultadoIngresosTotales the resultadoIngresosTotales to set
     */
    public void setResultadoIngresosTotales(Integer resultadoIngresosTotales) {
        this.resultadoIngresosTotales = resultadoIngresosTotales;
    }

    /**
     * @return the resultadoGastosTotales
     */
    public Integer getGastos() {
        return resultadoGastosTotales;
    }

    /**
     * @param resultadoGastosTotales the resultadoGastosTotales to set
     */
    public void setResultadoGastosTotales(Integer resultadoGastosTotales) {
        this.resultadoGastosTotales = resultadoGastosTotales;
    }

    /**
     * @return the utilidadOperdida
     */
    public Integer getUtilidad() {
        return utilidadOperdida;
    }

    /**
     * @param utilidadOperdida the utilidadOperdida to set
     */
    public void setUtilidadOperdida(Integer utilidadOperdida) {
        this.utilidadOperdida = utilidadOperdida;
    }

    /**
     * Imprimir el objeto, en formato json
     *
     * @return
     */
    @Override
    public String toString() {
        return (new Gson()).toJson(this);
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the resultadoIngresosTotales
     */
    public Integer getResultadoIngresosTotales() {
        return resultadoIngresosTotales;
    }

    /**
     * @return the resultadoGastosTotales
     */
    public Integer getResultadoGastosTotales() {
        return resultadoGastosTotales;
    }

    /**
     * @return the utilidadOperdida
     */
    public Integer getUtilidadOperdida() {
        return utilidadOperdida;
    }

}
