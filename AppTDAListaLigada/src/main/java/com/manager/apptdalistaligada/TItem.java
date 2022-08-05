package com.manager.apptdalistaligada;

import java.util.Objects;

/**
 * Clase inmutable para ser mantener informacion de Usuario y Clave <br>
 * Es inmutable dado que no posee setters, y sus atributos son "final",<br>
 * en caso de ser necesaria alguna modificacion, abria que instanciar otro objeto, con los datos actualizados<br>
 *
 * Es una clase que se puede clonar(Deep Copy) y comparar/ordenar
 *
 * @author manager
 */
public final class TItem implements Cloneable, Comparable<TItem> {

    private final String user;
    private final String passw;

    /**
     * Constructor por parametros
     *
     * @param user
     * @param passw
     */
    public TItem(String user, String passw) {
        this.user = user;
        this.passw = passw;
    }

    /**
     * Copy Constructor
     *
     * @param other
     */
    public TItem(TItem other) {
        this.user = other.user;
        this.passw = other.passw;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return user;
    }

    /**
     *
     * @return
     */
    @Override
    protected Object clone() {
        Object aux;

        try {
            aux = super.clone();
        } catch (CloneNotSupportedException ex) {
            aux = new TItem(user, passw);
        }

        return aux;
    }

    /**
     * Solo se considera el nombre para el Hash
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.user);
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final TItem other = (TItem) obj;

        return Objects.equals(this.user, other.user);
    }

    /**
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(TItem other) {

        if (this == other) {
            return 0;
        }

        if (other == null) {
            return 1;
        }

        int aux = this.user.compareToIgnoreCase(other.user);

        if (aux != 0) {
            return aux;
        }

        return this.passw.compareToIgnoreCase(other.passw);
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the passw
     */
    public String getPassw() {
        return passw;
    }

}
