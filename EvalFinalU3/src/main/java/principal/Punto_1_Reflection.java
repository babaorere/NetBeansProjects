package principal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import modelo.Pintura;

/**
 * Crear una clase que permita, a partir de reflection, obtener los componentes de una clase, <br>
 * cualquiera esta sea del grupo definido en la imagen, sus modificadores de acceso y las clases que <br>
 * tiene herencia.
 */
public class Punto_1_Reflection {

    public static void Desglozar(String tab, Class<?> clase) {

        System.out.println("Asi esta construida la clase -> " + clase.getSimpleName());
        System.out.println("\nAtributos [MODIFICADOR]    [TIPO]        [NOMBRE] ");

        // Con este codigo podemos conocer cuales son los atributos de la Clase
        Field[] arrAtr = clase.getDeclaredFields();
        for (Field atr : arrAtr) {

            String aux = tab + "[" + Modifier.toString(atr.getModifiers()) + "] "
                    + "[" + atr.getType().getSimpleName() + "] "
                    + "[" + atr.getName() + "]";

            System.out.println(aux);
        }

        // Ahora vamos a obteener los constructorees de la clase
        System.out.println("\nConstructor(es) [MODIFICADOR]    [NOMBRE]        [PARAMETROS]");
        Constructor<?>[] arrCtor = clase.getConstructors();
        for (Constructor<?> ctor : arrCtor) {

            String aux = tab + "        [" + Modifier.toString(ctor.getModifiers()) + "] "
                    + "[" + ctor.getName() + "] ( ";

            // Buscamos los tipos de los parametros
            Class<?>[] arrParam = ctor.getParameterTypes();
            for (Class<?> param : arrParam) {
                aux += "\t" + param.getSimpleName();
            }

            aux += ");";
            System.out.println(aux);
        }

        // Con este codigo podemos conocer cuales son los metodos de la Clase
        System.out.println("\nMetodo(s) [MODIFICADOR]    [RETORNO]        [NOMBRE]        [PARAMETROS]");
        Method[] arrMet = clase.getDeclaredMethods();
        for (Method met : arrMet) {

            String aux = tab + "  [" + Modifier.toString(met.getModifiers()) + "] "
                    + "[" + met.getReturnType() + "] "
                    + "[" + met.getName() + "] ( ";

            // Buscamos los tipos de los parametros
            Class<?>[] arrParam = met.getParameterTypes();
            for (Class<?> param : arrParam) {
                aux += " " + param.getSimpleName();
            }

            aux += ");";
            System.out.println(aux);

        }
    }

    public static void Exec() {

        // Aqui hacemos referencia a la Clase Pintura, que en realidad pudiera ser cualquiera Clase,
        // pero para el ejemplo en especifico utlizaremos Pintura
        Class<?> objeto = Pintura.class;

        Desglozar("\t", objeto);

        // Aqui conocemos de quien hereda la Clase
        System.out.println("\n***********************");
        System.out.println("Ahora vemos la Herencia, la Clase a partir de la cual esta heredando");
        Desglozar("\t", objeto.getSuperclass());

    }
}
