package serviciobarberia.modelo;

/**
 *
 */
public class Usuario {

    private Integer idUsuario; // inmutable
    private String nickname;

    // Para guardar un campo encriptado con MD5, se requiere 32 caracteres
    private String password;

    public Usuario() {
        this(null, null, null);
    }

    public Usuario(Integer inIdUsuario, String nick, String password) {
        this.idUsuario = inIdUsuario;
        this.nickname = nick;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{ idUsuario: " + idUsuario + ", nickname: " + nickname + ", password: " + password + " }";
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the idUsuario
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

}
