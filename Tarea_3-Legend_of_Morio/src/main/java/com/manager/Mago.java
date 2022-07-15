package com.manager;

/**
 *
 * @author manager
 */
public class Mago extends Jugador {

    public Mago(String nombre) {
        super(nombre, 10, 3, 10, 6, 15);

        VIDA_MAX = 10;
        FZA_INI = 3;
        INT_INI = 10;
        ENE_MAX = 6;
        MAN_MAX = 15;
    }

    @Override
    public int ataque() {
        int aux = getFuerza() * getInteligencia() / 4 + getEnergia();

        setEnergia(getEnergia() - 3);

        return aux;
    }

    @Override
    public int hechizo() {
        int aux = getInteligencia() * getFuerza() / 4 + getMana();

        setMana(getMana() - 5);

        return aux;
    }

    @Override
    public void subir_experiencia(int inXp) {

        int oldNivel = getNivel();
        setXP(inXp);
        int nivel = getNivel();

        if (nivel > oldNivel) {
            VIDA_MAX += 2 * nivel;
            setFuerza(getFuerza() + 1);
            setInteligencia(getInteligencia() + 3 * nivel);
            ENE_MAX += 1;
            MAN_MAX += 3 * nivel;
        }
    }

}
