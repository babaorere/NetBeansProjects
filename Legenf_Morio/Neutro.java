import java.util.Scanner;

/**
 *
 * @author manager
 */
public class Neutro extends NPC {

    private char requisito;
    private int valor;
    private int recompensa;
    private boolean ya_dio_mision;

    public Neutro(char requisito, int valor, int recompensa, String nombre) {
        super(nombre);
        this.requisito = requisito;
        this.valor = valor;
        this.recompensa = recompensa;
        this.ya_dio_mision = false;
    }

    @Override
    public void interracion(Jugador player) {

        if (ya_dio_mision) {
            System.out.println("\n\n" + player.getNombre() + ": ya te di mision, saludos.");
        } else {
            System.out.println("\n\n" + player.getNombre() + ": hola, ¿ Quieres cumplir esta mision? Deberas < " + (requisito == 'v' ? "llegar a" : "matar") + " > "
                    + "< " + valor + " > < " + (requisito == 'v' ? "del mundo" : "de Monstruos") + " > y recibirás < " + recompensa + " > de xp");

            Scanner sc = new Scanner(System.in);

            System.out.println("Acepta [S/N] = ? ");
            String r = sc.next().trim().toUpperCase();

            if (r.equals("S")) {

                int cantidad = (requisito == 'v') ? AppMain.getPos() : 0;
                player.addMision(new Mision(requisito, valor, cantidad, recompensa));
                ya_dio_mision = true;
            }
        }

    }

    /**
     * @return the requisito
     */
    public char getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(char requisito) {
        this.requisito = requisito;
    }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * @return the recompensa
     */
    public int getRecompensa() {
        return recompensa;
    }

    /**
     * @param recompensa the recompensa to set
     */
    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }

    /**
     * @return the ya_dio_mision
     */
    public boolean ya_dio_mision() {
        return ya_dio_mision;
    }

    /**
     * @param ya_dio_mision the ya_dio_mision to set
     */
    public void setYa_dio_mision(boolean ya_dio_mision) {
        this.ya_dio_mision = ya_dio_mision;
    }

}
