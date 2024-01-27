/**
 *
 * @author manager
 */
public abstract class NPC implements INPC {

    private String nombre;

    public NPC(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public abstract void interracion(Jugador player);

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
