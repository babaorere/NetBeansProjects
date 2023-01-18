package examen2.automovil;

/**
 * Una interface general de un Automovil
 *
 * @author Felipe Belloy
 */
public interface Automovil {

    @Override
    public String toString();

    @Override
    public boolean equals(Object obj);

    public int getId();

    public String getPpu();

    public void setPpu(String ppu);

    public String getMarca();

    public void setMarca(String marca);

    public int getAnio();

    public void setAnio(int anio);

    public Catalitico getCatalitico();

    public void setCatalitico(Catalitico esCatalitico);

}
