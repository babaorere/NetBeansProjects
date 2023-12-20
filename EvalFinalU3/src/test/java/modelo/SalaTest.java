package modelo;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author manager
 */
public class SalaTest {

    public SalaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("@BeforeClass");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("@AfterClass");
    }

    @Before
    public void setUp() {
        System.out.println("@Before");
    }

    @After
    public void tearDown() {
        System.out.println("@After");
    }

    /**
     * Test of getEncargado method, of class Sala.
     */
    @Test
    public void testSetGetEncargado() {
        System.out.println("getEncargado");
        Sala instance = new Sala();
        Encargado expResult = new Encargado();
        Encargado result = instance.getEncargado();
        assertEquals(expResult, result);

//
//        Encargado expResult_2 = new Encargado(new Persona("Maria", "Santos", "CHILE"), "10123123-5", "EDUCADOR", 1990);
//        Encargado result_2 = instance.getEncargado();
//        assertEquals(expResult_2, result_2);
    }

    /**
     * Test of getNombre method, of class Sala.
     */
    @Test
    public void testSetGetNombre() {
        System.out.println("getNombre");
        Sala instance = new Sala();
        String expResult = "";
        instance.setNombre(null);
        String result = instance.getNombre();
        assertEquals(expResult, result);

        String expResult_2 = "Pedro";
        instance.setNombre("Pedro");
        String result_2 = instance.getNombre();
        assertEquals(expResult_2, result_2);
    }

    /**
     * Test of getCantLamparas method, of class Sala.
     */
    @Test
    public void testSetGetCantLamparas() {
        System.out.println("getCantLamparas");
        Sala instance = new Sala();
        int expResult = 0;
        instance.setCantLamparas(101);
        int result = instance.getCantLamparas();
        assertEquals(expResult, result);

        int expResult_2 = 10;
        instance.setCantLamparas(10);
        int result_2 = instance.getCantLamparas();
        assertEquals(expResult_2, result_2);
    }

    /**
     * Test of getTempCelsius method, of class Sala.
     */
    @Test
    public void testSetGetTempCelsius() {
        System.out.println("getTempCelsius");
        Sala instance = new Sala();
        int expResult_1 = 0;
        int result_1 = instance.getTempCelsius();
        assertEquals(expResult_1, result_1);

        int expResult_2 = -10;
        instance.setTempCelsius(expResult_2);
        int result_2 = instance.getTempCelsius();
        assertEquals(expResult_2, result_2);

        int expResult_3 = 0;
        instance.setTempCelsius(-20);
        int result_3 = instance.getTempCelsius();
        assertEquals(expResult_3, result_3);

        int expResult_4 = 0;
        instance.setTempCelsius(50);
        int result_4 = instance.getTempCelsius();
        assertEquals(expResult_4, result_4);

        int expResult_5 = 20;
        instance.setTempCelsius(20);
        int result_5 = instance.getTempCelsius();
        assertEquals(expResult_5, result_5);
    }

}
