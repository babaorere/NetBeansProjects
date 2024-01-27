package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A partir de la definicion de "usuario" en la BD: <br>
 * CREATE TABLE `tipodeingreso` ( <br>
 * `id` int unsigned NOT NULL AUTO_INCREMENT, <br>
 * `Usuario_id` int unsigned NOT NULL, <br>
 * `Resultado_id` int unsigned NOT NULL, <br>
 * `TipodeIngreso` varchar(45) NOT NULL, <br>
 * `ValorIngreso` int NOT NULL, <br>
 * `FechaIngreso` varchar(45) NOT NULL, <br>
 * PRIMARY KEY (`id`), <br>
 * KEY `fk_TipodeIngreso_Usuario_idx` (`Usuario_id`), <br>
 * KEY `fk_TipodeIngreso_Resultado1_idx` (`Resultado_id`), <br>
 * CONSTRAINT `fk_TipodeIngreso_Resultado1` FOREIGN KEY (`Resultado_id`) REFERENCES `resultado` (`id`), <br>
 * CONSTRAINT `fk_TipodeIngreso_Usuario` FOREIGN KEY (`Usuario_id`) REFERENCES `usuario` (`id`)) <br>
 *
 * Procedemos a crear el objeto POJO. Utilizamos la implementacion de la interface "Serializable", <br>
 * en prevision de que la data sea transmitida por Internet <br>
 *
 * @author Alejandra
 */
public class TipoDeIngreso implements Serializable {

    private Integer id;
    private Usuario usuario;
    private String tipoDeIngreso;
    private Integer valorIngreso;
    private LocalDateTime fechaIngreso;

    /**
     * Constructor por defecto, se sugiere que toda clase Serializable, debe tenerlo. <br>
     * Los valores son arbitrarios, y a proposito son valores invalidos, para obligar a la generacion de <br>
     * una excepcion en caso de que no se hallan establecido adecuadamente, con posterioridad.
     */
    public TipoDeIngreso() {
        this(null, null, null, null, null);
    }

    public TipoDeIngreso(Integer id, Usuario inUsuario, String tipoDeIngreso, Integer valorIngreso, LocalDateTime fechaIngreso) {
        this.id = id;
        this.usuario = inUsuario;
        this.tipoDeIngreso = tipoDeIngreso;
        this.valorIngreso = valorIngreso;
        this.fechaIngreso = fechaIngreso;
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
     * @return the usuarioId
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param inUsuario the usuarioId to set
     */
    public void setUsuario(Usuario inUsuario) {
        this.usuario = inUsuario;
    }

    /**
     * @return the tipoDeIngreso
     */
    public String getTipoDeIngreso() {
        return tipoDeIngreso;
    }

    /**
     * @param tipoDeIngreso the tipoDeIngreso to set
     */
    public void setTipoDeIngreso(String tipoDeIngreso) {
        this.tipoDeIngreso = tipoDeIngreso;
    }

    /**
     * @return the valorIngreso
     */
    public Integer getValorIngreso() {
        return valorIngreso;
    }

    /**
     * @param valorIngreso the valorIngreso to set
     */
    public void setValorIngreso(Integer valorIngreso) {
        this.valorIngreso = valorIngreso;
    }

    /**
     * @return the fechaIngreso
     */
    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

}
