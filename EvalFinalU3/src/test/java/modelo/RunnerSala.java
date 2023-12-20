package modelo;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Este runner se especializa en ejecutar pruebas relacionadas con la clase Sala <br>
 */
public class RunnerSala {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(SalaTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Clase Sala. Resultado de las pruebas: " + result.wasSuccessful());
    }

}
