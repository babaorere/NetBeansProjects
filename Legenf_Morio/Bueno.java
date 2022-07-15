/**
 *
 * @author manager
 */
public class Bueno extends NPC {

    private String atributo;
    private int cantidad;

    public Bueno(String atributo, int cantidad, String nombre) {
        super(nombre);
        this.atributo = atributo;
        this.cantidad = cantidad;
    }

    @Override
    public void interracion(Jugador player) {

        System.out.println("< " + player.getNombre() + " >: Creo que necesitas un poco de ayuda, te subire < " + cantidad + " > a tu < " + atributo + " >");

        switch (atributo) {
            case "vida":
                player.setVida(player.getVida() + cantidad);
                break;

            case "xp":
                player.setXP(player.getXP() + cantidad);
                break;

            case "energia":
                player.setEnergia(player.getEnergia() + cantidad);
                break;

            case "mana":
                player.setMana(player.getMana() + cantidad);
                break;
        }

    }

    /**
     * @return the atributo
     */
    public String getAtributo() {
        return atributo;
    }

    /**
     * @param atributo the atributo to set
     */
    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
