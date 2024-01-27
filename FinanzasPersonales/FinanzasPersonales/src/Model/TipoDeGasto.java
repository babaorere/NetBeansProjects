package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A partir de la definicion de "resultado" en la BD: CREATE TABLE `resultado` <br>
 *
 * CREATE TABLE `tipo de gastos` ( <br>
 * `id` int NOT NULL AUTO_INCREMENT, <br>
 * `Usuario_id` int unsigned NOT NULL, <br>
 * `Resultado_id` int unsigned NOT NULL, <br>
 * `Tipo de Gastos` varchar(50) NOT NULL, <br>
 * `Valorgasto` int NOT NULL, <br>
 * `Fechagasto` datetime DEFAULT NULL, <br>
 * PRIMARY KEY (`id`), <br>
 * KEY `fk_Tipo de Gastos_Usuario1_idx` (`Usuario_id`), <br>
 * KEY `fk_Tipo de Gastos_Resultado1_idx` (`Resultado_id`), <br>
 * CONSTRAINT `fk_Tipo de Gastos_Resultado1` FOREIGN KEY (`Resultado_id`) REFERENCES `resultado` (`id`), <br>
 * CONSTRAINT `fk_Tipo de Gastos_Usuario1` FOREIGN KEY (`Usuario_id`) REFERENCES `usuario` (`id`)) <br>
 *
 * Procedemos a crear el objeto POJO. Utilizamos la implementacion de la interface "Serializable", <br>
 * en prevision de que la data sea transmitida por Internet <br>
 *
 * @author Alejandra
 */
public class TipoDeGasto implements Serializable {

    private Integer id;
    private Usuario usuario;
    private String tipoDeGastos;
    private Integer valorGasto;
    private LocalDateTime fechaGasto;

    public TipoDeGasto() {
        this(null, null, null, null, null);
    }

    public TipoDeGasto(Integer id, Usuario inUsuario, String tipoDeGastos, Integer valorGasto, LocalDateTime fechaGasto) {
        this.id = id;
        this.usuario = inUsuario;
        this.tipoDeGastos = tipoDeGastos;
        this.valorGasto = valorGasto;
        this.fechaGasto = fechaGasto;
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
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param inUsuario the usuario to set
     */
    public void setUsuario(Usuario inUsuario) {
        this.usuario = inUsuario;
    }

    /**
     * @return the tipoDeGastos
     */
    public String getTipoDeGastos() {
        return tipoDeGastos;
    }

    /**
     * @param tipoDeGastos the tipoDeGastos to set
     */
    public void setTipoDeGastos(String tipoDeGastos) {
        this.tipoDeGastos = tipoDeGastos;
    }

    /**
     * @return the valorGasto
     */
    public Integer getValorGasto() {
        return valorGasto;
    }

    /**
     * @param valorGasto the valorGasto to set
     */
    public void setValorGasto(Integer valorGasto) {
        this.valorGasto = valorGasto;
    }

    /**
     * @return the fechaGasto
     */
    public LocalDateTime getFechaGasto() {
        return fechaGasto;
    }

    /**
     * @param fechaGasto the fechaGasto to set
     */
    public void setFechaGasto(LocalDateTime fechaGasto) {
        this.fechaGasto = fechaGasto;
    }

}
