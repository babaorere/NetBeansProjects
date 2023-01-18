package ventas_online;

import javax.swing.JFrame;
import ventas_online.view.CamisaDialog;
import ventas_online.entities.Prenda;
import ventas_online.entities.Zapato;
import ventas_online.entities.RopaInterior;
import ventas_online.entities.Playera;
import ventas_online.entities.PantalonTela;
import ventas_online.entities.Jean;
import ventas_online.entities.Camisa;

/**
 *
 */
public class VentaRopaCaballeros {

    public static void main(String[] args) {

        Prenda prenda[] = new Prenda[100];
        Camisa camisa[] = new Camisa[100];
        Jean jean[] = new Jean[100];
        PantalonTela pantTela[] = new PantalonTela[100];
        Playera playera[] = new Playera[100];
        RopaInterior ropaInt[] = new RopaInterior[100];
        Zapato zapato[] = new Zapato[100];

        Camisa c1 = new Camisa();
        Camisa c2 = new Camisa(36, "Corta", "Generico", "Pantalon deportivo", 75, 200);
        Camisa c3 = new Camisa(40, "Larga", "Generico", "Pantalon deportivo", 75, 250);
        camisa[0] = c1;
        camisa[1] = c2;
        camisa[2] = c3;

        Jean j1 = new Jean();
        Jean j2 = new Jean("hilo grueso", 36, "Rojo", "Generico", "Pantalon deportivo", 75, 200);
        Jean j3 = new Jean("hilo trliple", 40, "Rojo", "Generico", "Pantalon deportivo", 75, 250);
        jean[0] = j1;
        jean[1] = j2;
        jean[2] = j3;

        PantalonTela pt1 = new PantalonTela();
        PantalonTela pt2 = new PantalonTela(32, "Rojo", "Generico", "Pantalon de vestir", 50, 300);
        PantalonTela pt3 = new PantalonTela(36, "Rojo", "Generico", "Pantalon deportivo", 75, 200);
        pantTela[0] = pt1;
        pantTela[1] = pt2;
        pantTela[2] = pt3;

        Playera py1 = new Playera();
        Playera py2 = new Playera("Abierta", "tela paracaida", 50, 50);
        Playera py3 = new Playera("Perforada", "tela perforada", 75, 50);
        playera[0] = py1;
        playera[1] = py2;
        playera[2] = py3;

        RopaInterior ri1 = new RopaInterior();
        RopaInterior ri2 = new RopaInterior("Masculina", "boxer", "generico", "boxer normal", 100, 25);
        RopaInterior ri3 = new RopaInterior("Masculina", "tradicional", "generico", "corte clasico", 100, 25);
        ropaInt[0] = ri1;
        ropaInt[1] = ri2;
        ropaInt[2] = ri3;

        Zapato z1 = new Zapato();
        Zapato z2 = new Zapato(45, "tela", "clasico", "deportivo", "para caminata", 120, 100);
        Zapato z3 = new Zapato(40, "tela", "clasico", "salir", "negro para fiesta", 120, 200);
        zapato[0] = z1;
        zapato[1] = z2;
        zapato[2] = z3;

        // Formulario Camisa
        CamisaRun();
        Camisa cwin = CamisaDialog.getCamisa();
        camisa[3] = cwin;

        // Leer del dialog la camisa del usuario
        CamisaRun();
        cwin = CamisaDialog.getCamisa();
        camisa[4] = cwin;

    }

    public static void CamisaRun() {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            JFrame win= new AppMain();
            win.setVisible(true);
        });
    }
}
