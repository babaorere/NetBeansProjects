package com.manager;

/**
 *
 * @author manager
 */
public class Malo extends NPC {

    private int cantidad_energia;
    private int cantidad_mana;

    public Malo(int cantidad_energia, int cantidad_mana, String nombre) {
        super(nombre);
        this.cantidad_energia = cantidad_energia;
        this.cantidad_mana = cantidad_mana;
    }

    @Override
    public void interracion(Jugador player) {

        System.out.println("< " + player.getNombre() + " >: SOY MALO TE VOY A DISMINUR TU ENERGIA Y MANA EN < " + cantidad_energia + " > y < " + cantidad_mana + " >");

        if ((1 + Math.random() * 10) < 6) {
            player.setEnergia(player.getEnergia() - cantidad_energia);
        }

        if ((1 + Math.random() * 10) < 6) {
            player.setMana(player.getMana() - cantidad_mana);
        }
    }

    /**
     * @return the cantidad_energia
     */
    public int getCantidad_energia() {
        return cantidad_energia;
    }

    /**
     * @param cantidad_energia the cantidad_energia to set
     */
    public void setCantidad_energia(int cantidad_energia) {
        this.cantidad_energia = cantidad_energia;
    }

    /**
     * @return the cantidad_mana
     */
    public int getCantidad_mana() {
        return cantidad_mana;
    }

    /**
     * @param cantidad_mana the cantidad_mana to set
     */
    public void setCantidad_mana(int cantidad_mana) {
        this.cantidad_mana = cantidad_mana;
    }

}
