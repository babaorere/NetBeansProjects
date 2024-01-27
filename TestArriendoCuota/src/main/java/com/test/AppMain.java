package com.test;

import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author manager
 */
public class AppMain {

    private static final ArrayList<ArriendoCuota> listArrCuo = new ArrayList<ArriendoCuota>();


    public static void main(String[] args) {

        int precioDia = 200_000;

        ArriendoCuota arrCuo = new ArriendoCuota("01/01/2022", 10, 6);

        if (addArriendoCuota(arrCuo)) {

            JOptionPane.showMessageDialog(null, "Arriendo agregado con exito.");

        }

    }


    private static boolean addArriendoCuota(ArriendoCuota inArrCuo) {
        listArrCuo.add(inArrCuo);
        return ArriendoCuota.WriteToFile(listArrCuo);
    }

}
