package com.isiweek.company.model;

import com.isiweek.company.CompanyDTO;
import com.isiweek.company.CompanyStatusDTO;
import java.util.HashSet;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author manager
 */
public class CompanyDTOTest {

    public CompanyDTOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testNoArgsConstructor() {
        // Crear instancia utilizando el constructor sin argumentos
        CompanyDTO company = new CompanyDTO();

        // Verificar que la instancia no sea nula
        assertNotNull(company);

        // Verificar que las propiedades inicializadas en el constructor estén en sus valores predeterminados
        assertEquals(null, company.getId());
        // Verificar otras propiedades...
    }

    @Test
    void testAllArgsConstructor() {
        // Configurar valores para el constructor con todos los argumentos
        Long id = 1L;
        String address = "CompanyDTO Address";
        String description = "CompanyDTO Description";
        String email = "company@example.com";
        String name = "CompanyDTO Name";
        String phone1 = "123456789";
        String phone2 = "987654321";
        String primaryContact = "Primary Contact";
        String taxidnumber = "ABC123";
        CompanyStatusDTO status = new CompanyStatusDTO(1L, "NONE");

        // Crear instancia utilizando el constructor con todos los argumentos
        CompanyDTO company = new CompanyDTO(id, name, description, taxidnumber, address, email, phone1, phone2,
            primaryContact, null, null, new HashSet<>(), status);

        // Verificar que la instancia no sea nula
        assertNotNull(company);

        // Verificar que las propiedades se hayan inicializado correctamente
        assertEquals(id, company.getId());
        assertEquals(address, company.getAddress());
        assertEquals(description, company.getDescription());
        assertEquals(email, company.getEmail());
        assertEquals(name, company.getName());
        assertEquals(phone1, company.getPhone1());
        assertEquals(phone2, company.getPhone2());
        assertEquals(primaryContact, company.getPrimaryContact());
        assertEquals(taxidnumber, company.getTaxidnumber());
    }

    @Test
    void testCreateCompany() {
        CompanyDTO company = new CompanyDTO();
        assertNotNull(company);
    }

    @Test
    void testBuilder() {
        // Crear instancia utilizando el builder de Lombok
        CompanyDTO company = CompanyDTO.builder()
            .id(1L)
            .address("CompanyDTO Address")
            .description("CompanyDTO Description")
            .email("company@example.com")
            .name("CompanyDTO Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .build();

        // Verificar que la instancia no sea nula
        assertNotNull(company);

        // Verificar que las propiedades se hayan inicializado correctamente
        assertEquals(1L, company.getId());
        assertEquals("CompanyDTO Address", company.getAddress());
        assertEquals("CompanyDTO Description", company.getDescription());
        assertEquals("company@example.com", company.getEmail());
        assertEquals("CompanyDTO Name", company.getName());
        assertEquals("123456789", company.getPhone1());
        assertEquals("987654321", company.getPhone2());
        assertEquals("Primary Contact", company.getPrimaryContact());
        assertEquals("ABC123", company.getTaxidnumber());

    }

    @Test
    void testBuilderValidation() {
        Assertions.assertDoesNotThrow(()
            -> CompanyDTO.builder()
                // No establecer campos requeridos
                .build()
        );
    }

    @Test
    void testGetterSetter() {
        // Crear instancia de CompanyDTO
        CompanyDTO company = new CompanyDTO();

        // Configurar valores
        company.setId(1L);
        company.setAddress("CompanyDTO Address");
        company.setDescription("CompanyDTO Description");
        company.setEmail("company@example.com");
        company.setName("CompanyDTO Name");
        company.setPhone1("123456789");
        company.setPhone2("987654321");
        company.setPrimaryContact("Primary Contact");
        company.setTaxidnumber("ABC123");

        // Verificar los valores a través de los getters
        assertEquals(1L, company.getId());
        assertEquals("CompanyDTO Address", company.getAddress());
        assertEquals("CompanyDTO Description", company.getDescription());
        assertEquals("company@example.com", company.getEmail());
        assertEquals("CompanyDTO Name", company.getName());
        assertEquals("123456789", company.getPhone1());
        assertEquals("987654321", company.getPhone2());
        assertEquals("Primary Contact", company.getPrimaryContact());
        assertEquals("ABC123", company.getTaxidnumber());
    }

    @Test
    void testEqualsAndHashCode() {

        // Crear instancia utilizando el builder de Lombok
        CompanyDTO company1 = CompanyDTO.builder()
            .id(1L)
            .address("CompanyDTO Address")
            .description("CompanyDTO Description")
            .email("company@example.com")
            .name("CompanyDTO Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .build();

        // Crear instancia utilizando el builder de Lombok
        CompanyDTO company2 = CompanyDTO.builder()
            .id(1L)
            .address("CompanyDTO Address")
            .description("CompanyDTO Description")
            .email("company@example.com")
            .name("CompanyDTO Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .build();

        // Crear instancia utilizando el builder de Lombok
        CompanyDTO company3 = CompanyDTO.builder()
            .id(2L)
            .address("CompanyDTO Address")
            .description("CompanyDTO Description")
            .email("company@example.com")
            .name("CompanyDTO Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .build();

        assertEquals(company1, company2);
        assertNotEquals(company1, company3);
        assertEquals(company1.hashCode(), company2.hashCode());
        assertNotEquals(company1.hashCode(), company3.hashCode());
    }

    @Test
    void testAddPersonToCompany() {
        // Crear instancia utilizando el builder de Lombok
        CompanyDTO company = CompanyDTO.builder()
            .id(1L)
            .address("CompanyDTO Address")
            .description("CompanyDTO Description")
            .email("company@example.com")
            .name("CompanyDTO Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .build();
    }

    @Test
    void testUtilityMethods() {

        final String name = "MyCompany";
        // Crear instancia utilizando el builder de Lombok
        CompanyDTO company1 = CompanyDTO.builder()
            .address("CompanyDTO Address")
            .description("CompanyDTO Description")
            .email("company@example.com")
            .name(name)
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .build();

        CompanyDTO company2 = CompanyDTO.builder()
            .address("CompanyDTO Address")
            .description("CompanyDTO Description")
            .email("company@example.com")
            .name(name)
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .build();

        assertEquals("[\"id\": " + null + ", \"name\": " + name + "]", company1.toString());

        assertEquals(Objects.hash(company2.getId(), company2.getName(), company2.getTaxidnumber()), company1.hashCode());
    }

}
