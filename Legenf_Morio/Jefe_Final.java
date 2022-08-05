/**
 *
 * @author manager
 */
public class Jefe_Final extends Enemigo {

    private String nombre;
    private int vida;
    private final int vida_ini;
    private int dano_base;

    public Jefe_Final(String nombre, int vida, int dano_base) {
        this.nombre = nombre;
        this.vida = vida;
        this.vida_ini = vida;
        this.dano_base = dano_base;
    }

    @Override
    public void combate(Jugador player) {

        if (player.getVida() <= 0) {
            System.out.println("\n\nJugador: ya ha perdido");
            return;
        }

        String r;
        do {
            System.out.println("\nJugador: Ataque o Hechizo [A/H]= ? ");
            r = sc.nextLine().trim().toUpperCase();
        } while (!r.equals("A") && !r.equals("H"));

        int respJug;
        if (r.equals("A")) {
            respJug = player.ataque();
            System.out.println("\nAtaque de " + respJug);
        } else {
            respJug = player.hechizo();
            System.out.println("\nHechizo de " + respJug);
        }

        // Ajustar la vida del enemigo
        setVida(getVida() - respJug);

        // Verificar que siga vivo el jefe final
        // Turno de atacar
        if (getVida() > 0) {
            int respJF;
            if (getVida() <= (vida_ini / 2)) { // fase 1
                respJF = dano_base;
            } else { // fase 2
                respJF = dano_base + 2 * 2;
            }

            player.setVida(player.getVida() - respJF);

            System.out.println("\nAtaque Jefe Final de " + respJF);
            if (player.getVida() > 0) {
                System.out.println("Jugardor sobrevive al ataque. Vida= " + player.getVida());
            } else {
                System.out.println("Jugardor muerto por el ataque");
            }

        } else {
            System.out.println("\nJefe Final muerto");
        }

    }

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

    /**
     * @return the vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * @param vida the vida to set
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * @return the dano_base
     */
    public int getDano_base() {
        return dano_base;
    }

    /**
     * @param dano_base the dano_base to set
     */
    public void setDano_base(int dano_base) {
        this.dano_base = dano_base;
    }

}
