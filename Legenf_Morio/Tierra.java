import java.util.Random;

/**
 *
 * @author manager
 */
public abstract class Tierra implements ITierra {

    protected static Random rand = new Random();

    private float probabilidad_enemigo;
    private Monstruo monstruo;
    private Jefe_Final jefe_final;
    private NPC npc;

    /**
     * Constructor para inicializar los atributos
     *
     * @param probabilidad_enemigo
     * @param monstruo
     * @param jefe_final
     * @param npc
     */
    public Tierra(float probabilidad_enemigo, Monstruo monstruo, Jefe_Final jefe_final, NPC npc) {
        this.probabilidad_enemigo = probabilidad_enemigo;
        this.monstruo = monstruo;
        this.jefe_final = jefe_final;
        this.npc = npc;
    }

    /**
     * Realiza una accion sobre el jugador, lo hacer interactuar con lo otros personajes de la tierra
     *
     * @param player
     * @return
     */
    @Override
    public abstract boolean accion(Jugador player);

    /**
     * @return the probabilidad_enemigo
     */
    public float getProbabilidad_enemigo() {
        return probabilidad_enemigo;
    }

    /**
     * @param probabilidad_enemigo the probabilidad_enemigo to set
     */
    public void setProbabilidad_enemigo(float probabilidad_enemigo) {
        this.probabilidad_enemigo = probabilidad_enemigo;
    }

    /**
     * @return the monstruo
     */
    public Monstruo getMonstruo() {
        return monstruo;
    }

    /**
     * @param monstruo the monstruo to set
     */
    public void setMonstruo(Monstruo monstruo) {
        this.monstruo = monstruo;
    }

    /**
     * @return the jefe_final
     */
    public Jefe_Final getJefe_final() {
        return jefe_final;
    }

    /**
     * @param jefe_final the jefe_final to set
     */
    public void setJefe_final(Jefe_Final jefe_final) {
        this.jefe_final = jefe_final;
    }

    /**
     * @return the npc
     */
    public NPC getNpc() {
        return npc;
    }

    /**
     * @param npc the npc to set
     */
    public void setNpc(NPC npc) {
        this.npc = npc;
    }

}
