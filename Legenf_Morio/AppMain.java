import java.util.Scanner;

/**
 *
 * @author manager
 */
public class AppMain {

    private static Scanner sc = new Scanner(System.in);

    private static int tam = 0;

    private static int posJug = 0;

    public static int getTam() {
        return tam;
    }

    public static int getPos() {
        return posJug;
    }

    private static Tierra[] mundo;

    public static void main(String[] args) {

        System.out.println("\n\n************************************************************************************************");
        System.out.println("**********************************   Legend of Morio   *****************************************");

        System.out.println("\nTamaño del mundo= ? ");
        tam = sc.nextInt();

        mundo = new Tierra[tam];

        System.out.println("\nProceso de llenar el Mundo");

        for (int i = 0; i < tam; i++) {

            int tipoTierra;
            do {
                System.out.println("\nTierra[ " + i + " ]");
                System.out.println("Tipo de Tierra. 1.- Montaña, 2.- Planicie 3.- Bosque");
                System.out.println("Tipo [1/2/3]= ? ");
                tipoTierra = sc.nextInt();
            } while (tipoTierra <= 0 || tipoTierra > 3);

            float prob;
            do {
                System.out.println("\nTierra[ " + i + " ]");
                System.out.println("Probabilidad 0% a 100%");
                System.out.println("Probabilidad= ? ");
                prob = sc.nextInt();

            } while (prob < 0 || prob > 100);
            prob /= 100; // pasar a escala entre 0 y 1.0

            int tipoEnemigo;
            do {
                System.out.println("\nTierra[ " + i + " ]");
                System.out.println("Tipo de Enemigo. 1.- Monstruo, 2.- Jefe_Final");
                System.out.println("Tipo [1/2]= ? ");
                tipoEnemigo = sc.nextInt();
            } while (tipoEnemigo <= 0 || tipoEnemigo > 2);

            Enemigo enemigo;
            if (tipoEnemigo == 1) {
                System.out.println("Monstruo");

                System.out.println("Vida= ? ");
                int vida = sc.nextInt();

                System.out.println("Daño= ? ");
                int dano = sc.nextInt();

                enemigo = new Monstruo(vida, dano);
            } else {
                System.out.println("Jefe_Final");

                System.out.println("Nombre= ? ");
                String nombre = sc.next().trim().toUpperCase();

                System.out.println("Vida= ? ");
                int vida = sc.nextInt();

                System.out.println("Daño_base= ? ");
                int dano_base = sc.nextInt();

                enemigo = new Jefe_Final(nombre, vida, dano_base);
            }

            int tipoNPC;
            do {
                System.out.println("\nTierra[ " + i + " ]");
                System.out.println("Tipo de NPC. 0.- Ninguno 1.- Bueno, 2.- Malo 3.- Neutro");
                System.out.println("Tipo [0/1/2/3]= ? ");
                tipoNPC = sc.nextInt();
            } while (tipoNPC < 0 || tipoNPC > 3);

            NPC npc;
            switch (tipoNPC) {

                case 0:
                    npc = null;
                    break;

                case 1:
                    System.out.println("Bueno");

                    System.out.println("Nombre= ? ");
                    String nombre = sc.next().trim().toUpperCase();

                    int tipoAtributo;
                    do {
                        System.out.println("\nTierra[ " + i + " ]");
                        System.out.println("Tipo de Atributo. 1.- Vida, 2.- XP, 3.- Energia, 4.- Mana");
                        System.out.println("Tipo [1/2/3/4]= ? ");
                        tipoAtributo = sc.nextInt();
                    } while (tipoAtributo <= 0 || tipoAtributo > 4);

                    String atrib;
                    switch (tipoAtributo) {
                        case 1:
                            atrib = "vida";
                            break;
                        case 2:
                            atrib = "xp";
                            break;
                        case 3:
                            atrib = "energia";
                            break;
                        case 4:
                            atrib = "mana";
                            break;
                        default:
                            atrib = "";
                    }

                    System.out.println("Vida= ? ");
                    int cantidad = sc.nextInt();

                    npc = new Bueno(atrib, cantidad, nombre);
                    break;

                case 2:
                    System.out.println("Malo");

                    System.out.println("Nombre= ? ");
                    String nomMalo = sc.next().trim().toUpperCase();

                    System.out.println("Cantidad Energia= ? ");
                    int cantEne = sc.nextInt();

                    System.out.println("Cantidad Mana= ? ");
                    int cantMana = sc.nextInt();

                    npc = new Malo(cantEne, cantMana, nomMalo);
                    break;

                case 3:
                    System.out.println("Neutral");

                    System.out.println("Nombre= ? ");
                    String nomNeu = sc.next().trim().toUpperCase();

                    String req;
                    do {
                        System.out.println("Requisito[V/M]= ? ");
                        req = sc.next().trim().toUpperCase();
                    } while (!req.equals("V") && !req.equals("M"));

                    System.out.println("Valor= ? ");
                    int valorNeu = sc.nextInt();

                    System.out.println("Recompensa= ? ");
                    int recNeu = sc.nextInt();

                    npc = new Neutro(req.charAt(0), valorNeu, recNeu, nomNeu);

                    break;

                default:
                    npc = null;
            }

            Monstruo m;
            Jefe_Final jf;
            if (tipoEnemigo == 1) {
                if (enemigo instanceof Monstruo) {
                    m = (Monstruo) enemigo;
                } else {
                    m = null;
                    System.out.println("\nERROR en la asignacion del Monstruo");
                }

                jf = null;
            } else {
                m = null;

                if (enemigo instanceof Jefe_Final) {
                    jf = (Jefe_Final) enemigo;
                } else {
                    jf = null;
                    System.out.println("\nERROR en la asignacion del Jefe Final");
                }
            }

            switch (tipoTierra) {
                case 1:
                    mundo[i] = new Montana(prob, m, jf, npc);
                    break;

                case 2:
                    mundo[i] = new Planicie(prob, m, jf, npc);
                    break;

                case 3:
                    mundo[i] = new Bosque(prob, m, jf, npc);

                    break;

            }

        } // for (int i = 0; i < tam; i++) {

        // Ya se ha llenado el mundo
        System.out.println("\n\nJugador");

        int pos;
        do {
            System.out.println("Posicion inicial 0 es la primera posicion");
            System.out.println("Posicion inicial del Jugador 0 <= Posicion < " + tam);
            System.out.println("Posicion= ? ");
            pos = sc.nextInt();
        } while (pos < 0 || pos >= tam);

        posJug = pos;

        System.out.println("Nombre= ? ");
        String nomJug = sc.next().trim().toUpperCase();

        int tipoJug;
        do {
            System.out.println("Tipo de Jugador. 1.- Mago, 2.- Guerrero , 3.- Druida");
            System.out.println("Tipo [1/2/3]= ? ");
            tipoJug = sc.nextInt();
        } while (tipoJug <= 0 || tipoJug > 3);

        Jugador player;
        switch (tipoJug) {
            case 1:
                player = new Mago(nomJug);
                break;

            case 2:
                player = new Guerrero(nomJug);
                break;

            case 3:
                player = new Druida(nomJug);
                break;

            default:
                player = null;

        }

        // ha manera de chequeo
        if (player == null) {
            System.out.println("\n\nERROR el jugador no puede ser NULO");
            return;
        }

        boolean gameOver = false;
        boolean r;
        while (!gameOver) {

            System.out.println("\n\n********************************************************************************************");
            System.out.println("Inicia el Combate");
            System.out.println("Jugador         => " + player.toString());
            System.out.println("Posicion actual => " + posJug);

            r = mundo[posJug].accion(player);

            if (r) {

                // El Jugador gana cuando "mata" al jefe final
                if ((player.getVida() > 0) && (mundo[posJug].getJefe_final() != null) && (mundo[posJug].getJefe_final().getVida() <= 0)) {
                    System.out.println("\n\nGAME OVER Jugador GANO la partida");
                    System.out.println("Jugador         => " + player.toString());
                    System.out.println("Gracias por Jugar con nosotros");

                    // fin
                    return;
                }

                String dir;
                do {
                    System.out.println("Direccion A.- Izquierda, D.- Derecha");
                    System.out.println("Direccion [A/D]= ? ");
                    dir = sc.next().trim().toUpperCase();
                } while (!dir.equals("A") && !dir.equals("D"));

                // se hace la vuelta circular, ciclica
                if (dir.equals("A")) {
                    posJug--;
                    if (posJug < 0) {
                        posJug = tam - 1;
                    }
                    System.out.println("<= Moviendo a la Izquierda <=");

                } else {
                    posJug++;
                    if (posJug >= tam) {
                        posJug = 0;
                    }
                    System.out.println("=> Moviendo a la Derecha =>");

                }
            } else {

                if (player.getVida() <= 0) {
                    System.out.println("\n\nGAME OVER Jugador PERDIO la partida");
                    System.out.println("Jugador         => " + player.toString());
                    System.out.println("Gracias por Jugar con nosotros");

                    // fin
                    return;
                }

            }

        }

    }

}
