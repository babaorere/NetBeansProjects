package modelo;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Este runner se especializa en ejecutar pruebas relacionadas con la clase Pintura <br>
 */
public class RunnerPintura {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(PinturaTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Clase Pintura. Resultado de las pruebas: " + result.wasSuccessful());
    }

}
