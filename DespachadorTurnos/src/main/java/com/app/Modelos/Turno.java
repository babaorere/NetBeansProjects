package com.app.Modelos;

import java.util.Objects;

/**
 * No se pueden Instanciar objectos de esta clase
 *
 * @author manager
 */
public abstract class Turno implements Comparable<Turno> {

    private final String tipoTurno;
    private final int id;
    private int caja;

    public Turno(String inTipoTurno, int inId) {
        this.tipoTurno = inTipoTurno;
        this.id = inId;
        this.caja = 0;
    }

    @Override
    public String toString() {
        return tipoTurno + String.valueOf(id);
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the tipoTurno
     */
    public String getTipoTurno() {
        return tipoTurno;
    }

    /**
     * @return the caja
     */
    public int getCaja() {
        return caja;
    }

    /**
     * @param caja the caja to set
     */
    public void setCaja(int caja) {
        if ((caja >= 1) && (caja <= 3)) {
            this.caja = caja;
        }
    }

    /**
     * @return the atendido
     */
    public boolean fueAtendido() {
        return caja >= 1;
    }

    @Override
    public boolean equals(Object inOther) {
        if (this == inOther) {
            return true;
        }

        if (inOther == null || getClass() != inOther.getClass()) {
            return false;
        }

        return (tipoTurno.compareTo(((Turno) inOther).tipoTurno) == 0) && (Integer.compare(id, ((Turno) inOther).id) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoTurno, id);
    }

    @Override
    public int compareTo(Turno inOther) {

        // Ordenar descendente la "Z" es menor que la "A", Z, Y, X ... C, B, A
        int compare = inOther.tipoTurno.compareTo(tipoTurno);

        if (compare != 0) {
            return compare;
        }

        return Integer.compare(id, inOther.id);
    }
}
