package com.manager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manager
 */
public abstract class Jugador implements IJugador {

    protected int VIDA_MAX;
    protected int FZA_INI;
    private int FZA_INI_MAX;
    protected int INT_INI;
    protected int ENE_MAX;
    protected int MAN_MAX;

    private String nombre;
    private int vida;
    private int xp;
    private int fuerza;
    private int inteligencia;
    private int energia;
    private int mana;
    private List<Mision> list_misiones;

    public Jugador(String nombre, int vida, int fuerza, int inteligencia, int energia, int mana) {
        this.nombre = nombre;
        this.vida = vida;
        this.xp = 0;
        this.fuerza = fuerza;
        this.inteligencia = inteligencia;
        this.energia = energia;
        this.mana = mana;
        this.list_misiones = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Nombre= " + nombre + ", vida= " + vida + ", xp= " + xp + ", fza= " + fuerza + ", intel= " + inteligencia + ", ene= " + energia + ", mana= " + mana + ", Misiones= " + list_misiones.toString();
    }

    @Override
    public abstract int ataque();

    @Override
    public abstract int hechizo();

    @Override
    public abstract void subir_experiencia(int inXp);

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
     * @param inVida the vida to set
     */
    public void setVida(int inVida) {
        if (inVida < 0) {
            inVida = 0;
        } else if (inVida > VIDA_MAX) {
            inVida = VIDA_MAX;
        }

        this.vida = inVida;
    }

    /**
     * @return the xp
     */
    public int getXP() {
        return xp;
    }

    /**
     * @param inXP the xp to set
     */
    public void setXP(int inXP) {
        if (inXP < 0) {
            inXP = 0;
        } else if (inXP > 900) {
            inXP = 900;
        }

        this.xp = inXP;
    }

    public int getNivel() {

        if (xp < 10) {
            return 1;
        } else if (xp < 25) {
            return 2;
        } else if (xp < 50) {
            return 3;
        } else if (xp < 100) {
            return 4;
        } else if (xp < 200) {
            return 5;
        } else if (xp < 350) {
            return 6;
        } else if (xp < 600) {
            return 7;
        }

        return 8;
    }

    /**
     * @return the fuerza
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * @param inFuerza the fuerza to set
     */
    public void setFuerza(int inFuerza) {
        if (inFuerza < 0) {
            inFuerza = 0;
        }

        this.fuerza = inFuerza;
    }

    /**
     * @return the inteligencia
     */
    public int getInteligencia() {
        return inteligencia;
    }

    /**
     * @param inteligencia the inteligencia to set
     */
    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    /**
     * @return the energia
     */
    public int getEnergia() {
        return energia;
    }

    /**
     * @param inEnergia the energia to set
     */
    public void setEnergia(int inEnergia) {
        if (inEnergia < 0) {
            inEnergia = 0;
        } else {
            if (inEnergia > ENE_MAX) {
                inEnergia = ENE_MAX;
            }
        }

        this.energia = inEnergia;
    }

    /**
     * @return the mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param inMana the mana to set
     */
    public void setMana(int inMana) {
        if (inMana < 0) {
            inMana = 0;
        } else if (inMana > MAN_MAX) {
            inMana = MAN_MAX;
        }

        this.mana = inMana;
    }

    /**
     * @return the list_misiones
     */
    public List<Mision> getList_misiones() {
        return list_misiones;
    }

    /**
     * @param inMision
     */
    public void addMision(Mision inMision) {
        this.list_misiones.add(inMision);
    }

}
