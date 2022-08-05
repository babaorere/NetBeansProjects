package com.manager;

import static java.lang.Math.max;

/**
 *
 * @author manager
 */
public class Druida extends Jugador {

    public Druida(String nombre) {
        super(nombre, 15, 5, 5, 5, 5);

        VIDA_MAX = 15;
        FZA_INI = 5;
        INT_INI = 5;
        ENE_MAX = 5;
        MAN_MAX = 5;
    }

    @Override
    public int ataque() {
        int aux = (getFuerza() + getInteligencia()) / 3 * max(getEnergia() / 3, getMana() / 2);

        setEnergia(getEnergia() - 3);

        return aux;
    }

    @Override
    public int hechizo() {
        int aux = (getFuerza() + getInteligencia()) / 3 * max(getEnergia() / 2, getMana() / 3);

        setMana(getMana() - 3);

        return aux;
    }

    @Override
    public void subir_experiencia(int inXp) {
        int oldNivel = getNivel();
        setXP(inXp);
        int nivel = getNivel();

        if (nivel > oldNivel) {
            VIDA_MAX += nivel;
            setFuerza(getFuerza() + nivel);
            setInteligencia(getInteligencia() + nivel);
            ENE_MAX += nivel;
            MAN_MAX += nivel;
        }
    }

}
