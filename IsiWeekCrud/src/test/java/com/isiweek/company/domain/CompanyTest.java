package com.isiweek.company.domain;

import com.isiweek.AppConfig;
import com.isiweek.LoanAppApplication;
import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.company.CompanyStatus;
import com.isiweek.company.CompanyStatusRepository;
import com.isiweek.person.domain.Person;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LoanAppApplication.class)
@ContextConfiguration(classes = AppConfig.class)
class CompanyTest {

    @Autowired
    private CompanyStatusRepository companyStatusRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        CompanyStatus status = CompanyStatus.genRandom();
        companyStatusRepository.save(status);

        String address = "Test Company Address";
        String description = "Test Company Description";
        String email = UUID.randomUUID() + "test_company@example.com";
        String name = UUID.randomUUID() + "Test Company Name";
        String phone1 = "123456789";
        String primaryContact = "Test Primary Contact";
        String taxidnumber = UUID.randomUUID() + "ABC123";
        Set<Person> companyPersons = new HashSet<>();
        OffsetDateTime dateCreated = OffsetDateTime.now();

        // Crear instancia utilizando el builder de Lombok
        Company company = Company.builder()
            .address(address)
            .description(description)
            .email(email)
            .name(name)
            .phone1(phone1)
            .primaryContact(primaryContact)
            .taxidnumber(taxidnumber)
            .persons(companyPersons)
            .dateCreated(dateCreated)
            .status(status)
            .build();

        Company save = companyRepository.save(company);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testNoArgsConstructor() {
        // Crear instancia utilizando el constructor sin argumentos
        Company company = new Company();

        // Verificar que la instancia no sea nula
        assertNotNull(company);

        // Verificar que las propiedades inicializadas en el constructor estén en sus valores predeterminados
        assertEquals(null, company.getId());
        assertEquals(null, company.getAddress());
        assertEquals(null, company.getDescription());
        assertEquals(null, company.getEmail());
        assertEquals(null, company.getName());
        assertEquals(null, company.getPhone1());
        assertEquals(null, company.getPhone2());
        assertEquals(null, company.getPrimaryContact());
        assertEquals(null, company.getTaxidnumber());
        assertEquals(new HashSet(), company.getPersons());
        assertEquals(null, company.getDateCreated());
        assertEquals(null, company.getLastUpdated());
        assertEquals(null, company.getStatus());
    }

    @Test
    void testAllArgsConstructor() {
        // Configurar valores para el constructor con todos los argumentos
        Long id = 1L;
        String address = "Company Address";
        String description = "Company Description";
        String email = "company@example.com";
        String name = "Company Name";
        String phone1 = "123456789";
        String phone2 = "987654321";
        String primaryContact = "Primary Contact";
        String taxidnumber = "ABC123";
        Set<Person> persons = new HashSet<>();
        OffsetDateTime dateCreated = OffsetDateTime.now();
        OffsetDateTime lastUpdated = OffsetDateTime.now();
        CompanyStatus status = new CompanyStatus(1L, "NONE");

        // Crear instancia utilizando el constructor con todos los argumentos
        Company company = new Company(id, name, description, taxidnumber, address, email, phone1, phone2,
            primaryContact, dateCreated, lastUpdated, persons, status);

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
        assertEquals(persons, company.getPersons());
        assertEquals(dateCreated, company.getDateCreated());
        assertEquals(lastUpdated, company.getLastUpdated());
        assertEquals(status, company.getStatus());
    }

    @Test
    void testCreateCompany() {
        Company company = new Company();
        assertNotNull(company);
    }

    @Test
    void testBuilder() {
        Long id = 1L;
        String address = "Company Address";
        String description = "Company Description";
        String email = "company@example.com";
        String name = "Company Name";
        String phone1 = "123456789";
        String phone2 = "987654321";
        String primaryContact = "Primary Contact";
        String taxidnumber = "ABC123";
        Set<Person> persons = new HashSet<>();
        OffsetDateTime dateCreated = OffsetDateTime.now();
        OffsetDateTime lastUpdated = OffsetDateTime.now();
        CompanyStatus status = new CompanyStatus(1L, "NONE");

        // Crear instancia utilizando el builder de Lombok
        Company company = Company.builder()
            .id(id)
            .address(address)
            .description(description)
            .email(email)
            .name(name)
            .phone1(phone1)
            .phone2(phone2)
            .primaryContact(primaryContact)
            .taxidnumber(taxidnumber)
            .persons(persons)
            .dateCreated(dateCreated)
            .lastUpdated(lastUpdated)
            .status(status)
            .build();

        // Verificar que la instancia no sea nula
        assertNotNull(company);

        // Verificar que las propiedades se hayan inicializado correctamente
        assertEquals(1L, company.getId());
        assertEquals(address, company.getAddress());
        assertEquals(description, company.getDescription());
        assertEquals(email, company.getEmail());
        assertEquals(name, company.getName());
        assertEquals(phone1, company.getPhone1());
        assertEquals(phone2, company.getPhone2());
        assertEquals(primaryContact, company.getPrimaryContact());
        assertEquals(taxidnumber, company.getTaxidnumber());
        assertEquals(persons, company.getPersons());
        assertEquals(dateCreated, company.getDateCreated());
        assertEquals(lastUpdated, company.getLastUpdated());
        assertEquals(status, company.getStatus());
    }

    @Test
    void testBuilderValidation() {
//        Assertions.assertDoesNotThrow(() -> Company.builder().build()
//        );

        assertThrows(Exception.class, ()
            -> Company.builder().build()
        );
    }

    @Test
    void testGetterSetter() {
        Company company = new Company();

        // Configurar valores
        company.setId(1L);
        company.setAddress("Company Address");
        company.setDescription("Company Description");
        company.setEmail("company@example.com");
        company.setName("Company Name");
        company.setPhone1("123456789");
        company.setPhone2("987654321");
        company.setPrimaryContact("Primary Contact");
        company.setTaxidnumber("ABC123");
        Set<Person> persons = new HashSet<>();
        company.setPersons(persons);
        OffsetDateTime now = OffsetDateTime.now();
        company.setDateCreated(now);
        company.setLastUpdated(now);
        CompanyStatus status = new CompanyStatus(1L, "NONE");

        // Verificar los valores a través de los getters
        assertEquals(1L, company.getId());
        assertEquals("Company Address", company.getAddress());
        assertEquals("Company Description", company.getDescription());
        assertEquals("company@example.com", company.getEmail());
        assertEquals("Company Name", company.getName());
        assertEquals("123456789", company.getPhone1());
        assertEquals("987654321", company.getPhone2());
        assertEquals("Primary Contact", company.getPrimaryContact());
        assertEquals("ABC123", company.getTaxidnumber());
        assertEquals(persons, company.getPersons());
        assertEquals(now, company.getDateCreated());
        assertEquals(now, company.getLastUpdated());
        assertEquals(status, company.getStatus());
    }

    @Test
    void testEqualsAndHashCode() {

        CompanyStatus status1 = new CompanyStatus(1L, "NONE");
        CompanyStatus status2 = new CompanyStatus(1L, "NONE");
        CompanyStatus status3 = new CompanyStatus(1L, "NONE");

        Set<Person> persons1 = new HashSet<>();
        Set<Person> persons2 = new HashSet<>();
        Set<Person> persons3 = new HashSet<>();

        // Crear instancia utilizando el builder de Lombok
        Company company1 = Company.builder()
            .id(1L)
            .address("Company Address")
            .description("Company Description")
            .email("company@example.com")
            .name("Company Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .persons(persons1)
            .dateCreated(OffsetDateTime.now())
            .lastUpdated(OffsetDateTime.now())
            .status(status1)
            .build();

        // Crear instancia utilizando el builder de Lombok
        Company company2 = Company.builder()
            .id(1L)
            .address("Company Address")
            .description("Company Description")
            .email("company@example.com")
            .name("Company Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .persons(persons2)
            .dateCreated(OffsetDateTime.now())
            .lastUpdated(OffsetDateTime.now())
            .status(status2)
            .build();

        // Crear instancia utilizando el builder de Lombok
        Company company3 = Company.builder()
            .id(2L)
            .address("Company Address")
            .description("Company Description")
            .email("company@example.com")
            .name("Company Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .persons(persons3)
            .dateCreated(OffsetDateTime.now())
            .lastUpdated(OffsetDateTime.now())
            .status(status3)
            .build();

        assertEquals(company1, company2);
        assertNotEquals(company1, company3);
        assertEquals(company1.hashCode(), company2.hashCode());
        assertNotEquals(company1.hashCode(), company3.hashCode());
    }

    @Test
    void testAddPersonToCompany() {
        CompanyStatus status = new CompanyStatus(1L, "NONE");

        // Crear instancia utilizando el builder de Lombok
        Company company = Company.builder()
            .id(1L)
            .address("Company Address")
            .description("Company Description")
            .email("company@example.com")
            .name("Company Name")
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .persons(new HashSet<>())
            .dateCreated(OffsetDateTime.now())
            .lastUpdated(OffsetDateTime.now())
            .status(status)
            .build();

        Person person = Person.builder().build();

        company.addPerson(person);

        assertTrue(company.getPersons().contains(person));
        assertEquals(company, person.getCompanies().stream().findAny().get());
    }

    @Test
    void testUtilityMethods() {
        CompanyStatus status1 = new CompanyStatus(1L, "NONE");
        CompanyStatus status2 = new CompanyStatus(1L, "NONE");

        Set<Person> persons1 = new HashSet<>();
        Set<Person> persons2 = new HashSet<>();

        final String name = "MyCompany";
        // Crear instancia utilizando el builder de Lombok
        Company company1 = Company.builder()
            .address("Company Address")
            .description("Company Description")
            .email("company@example.com")
            .name(name)
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .persons(persons1)
            .dateCreated(OffsetDateTime.now())
            .lastUpdated(OffsetDateTime.now())
            .status(status1)
            .build();

        Company company2 = Company.builder()
            .address("Company Address")
            .description("Company Description")
            .email("company@example.com")
            .name(name)
            .phone1("123456789")
            .phone2("987654321")
            .primaryContact("Primary Contact")
            .taxidnumber("ABC123")
            .persons(persons2)
            .dateCreated(OffsetDateTime.now())
            .lastUpdated(OffsetDateTime.now())
            .status(status2)
            .build();

        assertEquals("[\"id\": " + null + ", \"name\": " + name + "]", company1.toString());

        assertEquals(Objects.hash(company2.getId(), company2.getName(), company2.getTaxidnumber()), company1.hashCode());
    }
}
