package com.manager;

/**
 *
 * @author manager
 */
public class Bosque extends Tierra {

    /**
     * Constructor parra inicializar los atributos
     *
     * @param probabilidad_enemigo
     * @param monstruo
     * @param jefe_final
     * @param npc
     */
    public Bosque(float probabilidad_enemigo, Monstruo monstruo, Jefe_Final jefe_final, NPC npc) {
        super(probabilidad_enemigo, monstruo, jefe_final, npc);
    }

    /**
     * Realiza una accion sobre el jugador, lo hacer interactuar con lo otros personajes de la tierra
     *
     * @param player
     * @return
     */
    @Override
    public boolean accion(Jugador player) {

        if (player.getVida() <= 0) {
            System.out.println("\nJugador sin vida");
            return false;
        }

        if (player.getMana() <= 0) {
            System.out.println("\nJugador sin vida");
            return false;
        }

        if (player.getMana() >= 3) {
            player.setMana(player.getMana() - 3);
        } else {
            int resto = 3 - player.getMana();
            player.setMana(0);
            player.setVida(player.getVida() - resto);
        }

        if (player.getVida() <= 0) {
            return false;
        }

        NPC npc = getNpc();
        if (npc != null) {
            npc.interracion(player);
        }

        if (player.getVida() <= 0) {
            return false;
        }

        if (rand.nextDouble() > getProbabilidad_enemigo()) {
            Monstruo m = getMonstruo();
            if (m != null) {
                System.out.println("Combate con Monstruo");
                m.combate(player);
            }
        } else {
            System.out.println("Sin combate con Monstruo");

            Jefe_Final jf = getJefe_final();
            if (jf != null) {
                System.out.println("Combate con Jefe Final");
                jf.combate(player);
            }
        }

        return player.getVida() > 0;
    }

}
