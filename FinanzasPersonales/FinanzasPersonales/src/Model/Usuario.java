package Model;

import com.google.gson.Gson;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A partir de la definicion de "usuario" en la BD: <br>
 * CREATE TABLE `usuario` <br>
 * ( `id` int unsigned NOT NULL,  <br>
 * `NombreCompleto` varchar(45) NOT NULL,  <br>
 * `Fecha` datetime DEFAULT NULL,  <br>
 * `Direccion` varchar(45) DEFAULT NULL,  <br>
 * `Correo` varchar(45) NOT NULL,  <br>
 * `Telefono` int NOT NULL, PRIMARY KEY (`id`) ) <br>
 *
 * Procedemos a crear el objeto POJO. Utilizamos la implementacion de la interface "Serializable", <br>
 * en prevision de que la data sea transmitida por Internet <br>
 *
 * @author Alejandra
 */
public class Usuario implements Serializable {

    // Dado que estamos implementado la interface "Serializable" es mejor utilizar tipos Wrapper (Integer, ...), <br>
    // que sus primitivas (int, ...)
    private Integer id;
    private String nombreCompleto;
    private LocalDateTime fecha;
    private String direccion;
    private String correo;
    private String telefono;
    private String nickName;
    private byte password[];

    /**
     * Constructor por defecto, se sugiere que toda clase Serializable, debe tenerlo. <br>
     * Los valores son arbitrarios, y a proposito son valores invalidos, para obligar a la generacion de <br>
     * una excepcion en caso de que no se hallan establecido adecuadamente, con posterioridad.
     */
    public Usuario() {
        this(null, null, null, null, null, null, null, null);
    }

    /**
     * Constructor, con parametros, para establecer los valores iniciales del Objeto
     *
     * @param id
     * @param nombreCompleto
     * @param fecha
     * @param direccion
     * @param correo
     * @param telefono
     * @param inNickName
     * @param inPassword
     */
    public Usuario(Integer id, String nombreCompleto, LocalDateTime fecha, String direccion, String correo, String telefono, String inNickName, byte inPassword[]) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.fecha = fecha;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;

        this.nickName = inNickName;
        this.password = inPassword;
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
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the fecha
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
     * @return the nickName
     */
    public String getNickname() {
        return nickName;
    }

    /**
     * @param nickName the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return the password
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * @param inPassword
     */
    public void setPassword(byte[] inPassword) {
        this.password = inPassword;
    }

}
