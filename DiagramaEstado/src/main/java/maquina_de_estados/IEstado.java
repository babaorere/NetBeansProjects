package maquina_de_estados;

/**
 *
 */
public interface IEstado {

    public IEstado getProximoEstado(String codTrans);

    /**
     * Muestra el estado presente, y retorna la siguiente transicion
     *
     * @return
     */
    public String mostrarEstado();

}
