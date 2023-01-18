package examen2.automovil;

import java.util.Objects;

/**
 *
 * @author Felipe Belloy
 */
public class AutomovilImpl implements Automovil {

    // este codigo ayuda a generar automaticamente el id del automovil
    // es static, por lo que se comparte entre todas las instancias
    private static int nextId = 1;

    // es final, ya que el identificador es inmutable
    private final int id;

    private String ppu;
    private String marca;
    private int anio;
    private Catalitico esCatalitico;

    public AutomovilImpl() {
        // utilizar el contructor con parametros, de la misma clase
        this("", "", 0, Catalitico.NO);
    }

    public AutomovilImpl(String ppu, String marca, int anio, Catalitico esCatalitico) {
        // se autogenera el id del automovil
        this.id = nextId++;

        this.ppu = ppu.toUpperCase();
        this.marca = marca.toUpperCase();
        this.anio = anio;
        this.esCatalitico = esCatalitico;
    }

    @Override
    public String toString() {
        return "[id: " + id + " patente: " + ppu + " marca: " + marca + " año: " + anio + " catalitico: " + esCatalitico + "]";
    }

    @Override
    public boolean equals(Object obj) {
        Automovil auto = (Automovil) obj;
        return Objects.equals(id, auto.getId()) && Objects.equals(ppu, auto.getPpu()) && Objects.equals(marca, auto.getMarca());
    }

    /**
     * @return the id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return the ppu
     */
    @Override
    public String getPpu() {
        return ppu;
    }

    /**
     * @param ppu the ppu to set
     */
    @Override
    public void setPpu(String ppu) {
        this.ppu = ppu;
    }

    /**
     * @return the marca
     */
    @Override
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the anio
     */
    @Override
    public int getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    @Override
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * @return the esCatalitico
     */
    @Override
    public Catalitico getCatalitico() {
        return esCatalitico;
    }

    /**
     * @param esCatalitico the esCatalitico to set
     */
    @Override
    public void setCatalitico(Catalitico esCatalitico) {
        this.esCatalitico = esCatalitico;
    }

}
