package test;

import appmall.Estacionamiento;
import appmall.Normal;
import appmall.Preferente;

/**
 *
 */
public class Main {

    public static void main(String[] args) {

        // Instanciacion de objetos, con sus parametros iniciales
        Estacionamiento e1, e2;
        Normal nm = new Normal("A1", "Sector 1");
        Preferente pr = new Preferente("P1", "Sector 1");

        // Error porque en la clase base "Estacionamiento", no estan definidos la referencias a los metodos
        // no tenemos acceso a estos metodos, dado que son desconocidos para "Estacionamiento"
        nm.registrarEntrada(10, 20);
        nm.registrarSalida(11, 30);
        nm.definirTarifaMinuto(50);

        // Error porque en la clase base "Estacionamiento", no estan definidos la referencias a los metodos
        // no tenemos acceso a estos metodos, dado que son desconocidos para "Estacionamiento"
        pr.definirTipoDescuento(1);
        pr.definirTarifaUnica(3000);

        // Aqui hacemos  la asignacion, para posteriormente utilizar la caracteristica de polimorfismo
        e1 = nm;
        e2 = pr;

        System.out.println(e1.mostrarPago());
        System.out.println(e2.mostrarPago());
    }
}
