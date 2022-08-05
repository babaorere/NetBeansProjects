/**
 *
 * @author manager
 */
public class Monstruo extends Enemigo {

    private int vida;
    private int dano;

    public Monstruo(int vida, int dano) {
        super();
        this.vida = vida;
        this.dano = dano;
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
            System.out.println("\nJugador Ataque de " + respJug);
        } else {
            respJug = player.hechizo();
            System.out.println("\nJugador Hechizo de " + respJug);
        }

        // Ajustar la vida del enemigo
        setVida(getVida() - respJug);

        // Turno de atacar el Mostruo
        if (getVida() > 0) {
            System.out.println("Monstruo Ataque de " + dano);
            player.setVida(player.getVida() - dano);
        } else {
            System.out.println("Monstruo muerto");
        }

    }

    /**
     * @return the vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * @param inVida the vida to set
     */
    public void setVida(int inVida) {

        if (inVida < 0) {
            inVida = 0;
        }

        this.vida = inVida;
    }

    /**
     * @return the dano
     */
    public int getDano() {
        return dano;
    }

    /**
     * @param inDano the dano to set
     */
    public void setDano(int inDano) {
        if (inDano < 0) {
            inDano = 0;
        }

        this.dano = inDano;
    }

}
