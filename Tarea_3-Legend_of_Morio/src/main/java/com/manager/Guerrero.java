package com.manager;

/**
 *
 * @author manager
 */
public class Guerrero extends Jugador {

    private int FZA_INI_MAX = 9;

    public Guerrero(String nombre) {
        super(nombre, 20, 9, 1, 10, 2);

        VIDA_MAX = 20;
        FZA_INI_MAX = 9;
        INT_INI = 1;
        ENE_MAX = 10;
        MAN_MAX = 2;
    }

    @Override
    public int ataque() {
        int aux = getFuerza() * 2 + getEnergia();

        setEnergia(getEnergia() - 5);

        return aux;
    }

    @Override
    public int hechizo() {
        int aux = getInteligencia() * getFuerza() / 4 + getMana();

        setMana(getMana() - 3);

        return aux;
    }

    @Override
    public void subir_experiencia(int inXp) {

        int oldNivel = getNivel();
        setXP(inXp);
        int nivel = getNivel();

        if (nivel > oldNivel) {
            VIDA_MAX += 3 * nivel;
            setFuerza(getFuerza() + 5);
            setInteligencia(getInteligencia() + 1);
            ENE_MAX += 2 * nivel;
            MAN_MAX += 1;
        }
    }

}
