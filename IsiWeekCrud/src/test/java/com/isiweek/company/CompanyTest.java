package com.isiweek.company;

import com.isiweek.AppConfig;
import com.isiweek.LoanAppApplication;
import com.isiweek.person.Person;
import com.isiweek.status.Status;
import com.isiweek.status.StatusRepository;
import com.isiweek.status.StatusService;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//@Disabled
@ComponentScan(basePackages = "com.isiweek")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig
@SpringBootTest(classes = LoanAppApplication.class)
@ContextConfiguration(classes = AppConfig.class)
@ExtendWith(SpringExtension.class)
class CompanyTest {

    private StatusRepository statusRepository;

    private StatusService statusService;

    private CompanyRepository companyRepository;

    private CompanyService companyService;

    @Autowired
    public CompanyTest(StatusRepository inStatusRepository, StatusService inStatusService, CompanyRepository inCompanyRepository,
            CompanyService inCompanyService) {
        this.statusRepository = inStatusRepository;
        this.statusService = inStatusService;
        this.companyRepository = inCompanyRepository;
        this.companyService = inCompanyService;
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        companyRepository.deleteAll();
        statusService.persistStatusEnumValues();
    }

    @AfterEach
    public void tearDown() {
        companyRepository.deleteAll();
        statusService.deleteAll();
    }

    @Test
    // @Disabled
    void testNoArgsConstructor() {
        // Crear instancia utilizando el constructor sin argumentos
        Company company = new Company();

        // Verificar que la instancia no sea nula
        assertNotNull(company);

        // Verificar que las propiedades inicializadas en el constructor estén en sus valores predeterminados
        assertEquals(null, company.getId());
        assertEquals("", company.getAddress());
        assertEquals("", company.getDescription());
        assertEquals("", company.getEmail());
        assertEquals("", company.getName());
        assertEquals("", company.getPhone1());
        assertEquals("", company.getPhone2());
        assertEquals("", company.getPrimaryContact());
        assertEquals("", company.getTaxidnumber());
        assertEquals(new HashSet(), company.getPersons());
        assertNotNull(company.getDateCreated());
        assertNotNull(company.getLastUpdated());
        assertNotNull(company.getStatus());
    }

    @Test
//    @Disabled
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

        Optional<Status> status = statusRepository.findFirst();

        // Verificar que la instancia no sea nula
        assertNotNull(status);

        // Verificar que la instancia no sea nula
        assertTrue(status.isPresent());

        // Crear instancia utilizando el constructor con todos los argumentos
        Company company = new Company(id, name, description, taxidnumber, address, email, phone1, phone2,
                primaryContact, dateCreated, lastUpdated, persons, status.get());

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
        assertEquals(status.get(), company.getStatus());
    }

    @Test
    @Disabled
    void testCreateCompany() {

        Company company = new Company();
        assertNotNull(company);
    }

    @Test
    @Disabled
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
        Status status = Status.randomGenerator();

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
    @Disabled
    void testBuilderValidation() {
        Assertions.assertDoesNotThrow(() -> Company.builder().build()
        );
    }

    @Test
    @Disabled
    void testGetterSetter() {

        Optional<Status> status = statusRepository.findFirst();

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
        company.setStatus(status.get());

        System.out.println("Company: " + company);

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
        assertEquals(status.get(), company.getStatus());
    }

    @Test
    @Disabled
    void testEqualsAndHashCode() {

        Optional<Status> status = statusRepository.findFirst();

        Status status1 = status.get();
        Status status2 = status.get();
        Status status3 = status.get();

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
    @Disabled
    void testAddPersonToCompany() {

        Optional<Status> status = statusRepository.findFirst();

        System.out.println("Status: " + status);

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
                .status(status.get())
                .build();

        System.out.println("Company: " + company);
        System.out.println("Company.dtatus: " + company.getStatus());

        Company auxCompany = companyService.create(company);

        Optional<Company> companyOp = companyService.get(auxCompany.getId());

        assertTrue(companyOp.isPresent());

        System.out.println("Company get: " + companyOp.get());
    }

    @Test
    @Disabled
    void testUtilityMethods() {
        Optional<Status> status = statusRepository.findFirst();

        Status status1 = status.get();
        Status status2 = status.get();

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

        assertEquals("[\"id\": " + 0 + ", \"name\": " + name + "]", company1.toString());

        assertEquals(Objects.hash(company2.getId(), company2.getName(), company2.getTaxidnumber()), company1.hashCode());
    }
}
