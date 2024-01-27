package serviciobarberia.modelo;

/**
 *
 */
public class Bitacora {

    private Integer idbitacora;
    private Integer idcliente;
    private Integer idservicio;
    private String texto;

    public Bitacora(Integer idbitacora, Integer idcliente, Integer idservicio, String texto) {
        this.idbitacora = idbitacora;
        this.idcliente = idcliente;
        this.idservicio = idservicio;
        this.texto = texto;
    }

    public Bitacora() {
        this(null, null, null, null);
    }

    /**
     * @return the idbitacora
     */
    public Integer getIdbitacora() {
        return idbitacora;
    }

    /**
     * @param idbitacora the idbitacora to set
     */
    public void setIdbitacora(Integer idbitacora) {
        this.idbitacora = idbitacora;
    }

    /**
     * @return the idcliente
     */
    public Integer getIdcliente() {
        return idcliente;
    }

    /**
     * @param idcliente the idcliente to set
     */
    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    /**
     * @return the idservicio
     */
    public Integer getIdservicio() {
        return idservicio;
    }

    /**
     * @param idservicio the idservicio to set
     */
    public void setIdservicio(Integer idservicio) {
        this.idservicio = idservicio;
    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

}
