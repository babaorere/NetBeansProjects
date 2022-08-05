package com.manager;

import java.util.Scanner;

/**
 *
 * @author manager
 */
public abstract class Enemigo implements IEnemigo {

    protected Scanner sc = new Scanner(System.in);

    @Override
    public abstract void combate(Jugador player);

}
