/**
 *
 * @author manager
 */
public class Mision implements IMision {

    private final int MAXMATAR = 11;
    private final int MAXREC = 11;

    private char requisito;
    private int valor;
    private int cantidad;
    private int recompensa;

    /**
     *
     * @param inRequisito ’v’ llegar a una posicion, o ’m’ matar Monstruos
     * @param inValor posicion a la que debe llegar, o cantidad de Monstruos ha matar
     * @param inCantidad posición actual o cantidad de Monstruos matados
     * @param inRecompensa cantidad de xp que gana el Jugador
     */
    public Mision(char inRequisito, int inValor, int inCantidad, int inRecompensa) {

        // Validar el requisito
        inRequisito = java.lang.Character.toLowerCase(inRequisito);
        if ((inRequisito != 'v') || (inRequisito != 'm')) {
            System.out.println("\nERROR en parametro <requisito> en Mision");
            inRequisito = 'v';
        }

        this.requisito = inRequisito;

        if (this.requisito == 'v') {
            inValor %= AppMain.getTam();
            inCantidad %= AppMain.getTam();
        } else {
            inValor %= MAXMATAR;
            inCantidad %= MAXMATAR;
        }

        this.valor = Math.abs(inValor);

        this.cantidad = Math.abs(inCantidad);

        this.recompensa = Math.abs(inRecompensa % MAXREC);
    }

    /**
     * Debe retornar verdadero o falso dependiendo si la misión ya cumplió su valor o no
     *
     * @return
     */
    @Override
    public boolean verificar_requisito() {

        boolean aux;
        if (requisito == 'v') {
            aux = cantidad == valor;
        } else {
            aux = cantidad >= valor;
        }

        return aux;
    }

    @Override
    public String toString() {
        return "Req.: " + requisito + ", valor: " + valor + ", cant.: " + cantidad + "rec.: " + recompensa;
    }

    /**
     * @return the requisito
     */
    public char getRequisito() {
        return requisito;
    }

    /**
     * @param inRequisito the requisito to set
     */
    public void setRequisito(char inRequisito) {

        inRequisito = java.lang.Character.toLowerCase(inRequisito);

        if ((inRequisito != 'v') || (inRequisito != 'm')) {
            System.out.println("\nERROR en parametro <requisito> en Mision");
            inRequisito = 'v';
        }

        this.requisito = inRequisito;

        // rechequear valores
        setValor(this.valor);
        setCantidad(this.cantidad);
    }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param inValor
     */
    public void setValor(int inValor) {
        if (this.requisito == 'v') {
            inValor %= AppMain.getTam();
        } else {
            inValor %= MAXMATAR;
        }

        this.valor = Math.abs(inValor);
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param inCantidad the cantidad to set
     */
    public void setCantidad(int inCantidad) {
        if (this.requisito == 'v') {
            inCantidad %= AppMain.getTam();
        } else {
            inCantidad %= MAXMATAR;
        }

        this.cantidad = Math.abs(inCantidad);
    }

    /**
     * @return the recompensa
     */
    public int getRecompensa() {
        return recompensa;
    }

    /**
     * @param inRecompensa the recompensa to set
     */
    public void setRecompensa(int inRecompensa) {
        this.recompensa = Math.abs(inRecompensa % MAXREC);
    }

}
