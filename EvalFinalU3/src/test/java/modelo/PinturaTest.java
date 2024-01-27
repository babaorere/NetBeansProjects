package modelo;

import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author manager
 */
@RunWith(Parameterized.class)
public class PinturaTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAutor method, of class Pintura.
     */
    @Test
    public void testSetGetAutor() {
        System.out.println("getAutor");
        Pintura instance = new Pintura();
        Autor expResult = new Autor();
        Autor result = instance.getAutor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNombre method, of class Pintura.
     */
    @Test
    public void testSetGetNombre() {
        System.out.println("getNombre");
        Pintura instance = new Pintura();
        String expResult = "";
        String result = instance.getNombre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumId method, of class Pintura.
     */
    @Test
    public void testSetGetNumId() {
        System.out.println("getNumId");
        Pintura instance = new Pintura();
        String expResult = "";
        String result = instance.getNumId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTecnica method, of class Pintura.
     */
    @Test
    public void testSetGetTecnica() {
        System.out.println("getTecnica");
        Pintura instance = new Pintura();
        String expResult = "";
        String result = instance.getTecnica();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGenero method, of class Pintura.
     */
    @Test
    public void testSetGetGenero() {
        System.out.println("getGenero");
        Pintura instance = new Pintura();
        String expResult = "";
        String result = instance.getGenero();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAnioCreacion method, of class Pintura.
     */
    @Test
    public void testSetGetAnioCreacion() {
        System.out.println("getAnioCreacion");
        Pintura instance = new Pintura();
        int expResult = 0;
        int result = instance.getAnioCreacion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTam method, of class Pintura.
     */
    @Test
    public void testSetGetTam() {
        System.out.println("getTam");
        Pintura instance = new Pintura();
        Tamanio expResult = new Tamanio();
        Tamanio result = instance.getTam();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUbicacion method, of class Pintura.
     */
    @Test
    public void testSetGetUbicacion() {
        System.out.println("getUbicacion");
        Pintura instance = new Pintura();
        Sala expResult = new Sala();
        Sala result = instance.getUbicacion();
        assertEquals(expResult, result);
    }

    private Pintura pintura;

    public PinturaTest(Pintura pintura) {
        this.pintura = pintura;
    }

    // Prueba 1: Verificar si la pintura tiene un nombre válido
    @Test
    public void testValidNombre() {
        assertTrue(Pintura.valNombre(pintura.getNombre()));
    }

    // Prueba 2: Verificar si la pintura tiene un número de identificación válido
    @Test
    public void testValidNumId() {
        assertTrue(Pintura.valNumId(pintura.getNumId()));
    }

    // Prueba 3: Verificar si la técnica de la pintura es válida
    @Test
    public void testValidTecnica() {
        assertTrue(Pintura.valTecnica(pintura.getTecnica()));
    }

    // Prueba 4: Verificar si el género de la pintura es válido
    @Test
    public void testValidGenero() {
        assertTrue(Pintura.valGenero(pintura.getGenero()));
    }

    // Prueba 5: Verificar si el año de creación de la pintura es válido
    @Test
    public void testValidAnioCreacion() {
        assertTrue(Pintura.valAnionCreacion(pintura.getAnioCreacion()));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Sala sala_1 = new Sala(new Encargado(new Persona("Manuel", "Rios", "CHILE"), "1234567-1", "TECNICO", 2013), "PRINCIPAL", 10, 22, true, true);
        Sala sala_2 = new Sala(new Encargado(new Persona("Pamela", "Sepulveda", "CHILE"), "1234567-1", "CURADOR", 2010), "DORADA", 25, 20, true, true);
        Sala sala_3 = new Sala();

        return Arrays.asList(new Object[][]{
            {new Pintura(new Autor(), "La persistencia de la memoria", "12345", "OLEO", "retrato", 1931, new Tamanio(100, 100), sala_1)},
            {new Pintura(new Autor(), "No. 5, 1948", "67890", "ACUARELA", "naturaleza muerta", 1948, new Tamanio(200, 200), sala_2)},
            {new Pintura(new Autor(), "Campbell's Soup Cans", "ABCDE", "TEMPLE", "paisajista", 1962, new Tamanio(300, 300), sala_2)},
            {new Pintura(new Autor(), "Starry Night", "FGHIJ", "PUNTILLISMO", "historica", 1889, new Tamanio(), new Sala())},
            {new Pintura(new Autor(), "Les Demoiselles d'Avignon", "KLMNO", "FRESCO", "desnudo", 1907, new Tamanio(101, 205), sala_3)}
        });
    }
}
