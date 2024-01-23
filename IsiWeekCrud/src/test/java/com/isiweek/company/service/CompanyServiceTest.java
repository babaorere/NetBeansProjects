package com.isiweek.company.service;

import com.isiweek.AppConfig;
import com.isiweek.company.Company;
import com.isiweek.company.CompanyDTO;
import com.isiweek.company.CompanyRepository;
import com.isiweek.company.CompanyService;
import com.isiweek.company.CompanyStatus;
import com.isiweek.company.CompanyStatusRepository;
import com.isiweek.person.domain.Person;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ComponentScan(basePackages = "com.isiweek.company")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig
@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class CompanyServiceTest {

    @Autowired
    private CompanyStatusRepository companyStatusRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    Company company;
    CompanyStatus status;

    public CompanyServiceTest(CompanyRepository inCompanyRepository) {
        this.companyRepository = inCompanyRepository;
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        status = companyStatusRepository.save(CompanyStatus.genRandom());

        company.setStatus(status);
        company = companyRepository.save(Company.genRandom());
    }

    @AfterEach
    public void tearDown() {

        companyStatusRepository.deleteAll();
        companyRepository.deleteAll();
    }

    /**
     * Test of findAll method, of class CompanyService.
     */
    @Test
    public void testFindAll() {
        List<CompanyDTO> result = companyService.findAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Test of get method, of class CompanyService.
     */
    @Test
    public void testGet() {

        company = companyRepository.save(Company.genRandom());

        Optional<Company> anyCompany = companyRepository.findAll().stream().findFirst();

        assertNotNull(anyCompany.get());
    }

    /**
     * Test of create method, of class CompanyService.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        CompanyDTO companyDTO = null;
        CompanyService instance = null;
        Long expResult = null;
        Long result = instance.create(companyDTO);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class CompanyService.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Long id = null;
        CompanyDTO companyDTO = null;
        CompanyService instance = null;
        instance.update(id, companyDTO);
    }

    /**
     * Test of delete method, of class CompanyService.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Long id = null;
        CompanyService instance = null;
        instance.delete(id);
    }

    /**
     * Test of emailExists method, of class CompanyService.
     */
    @Test
    public void testEmailExists() {
        System.out.println("emailExists");
        String email = "";
        CompanyService instance = null;
        boolean expResult = false;
        boolean result = instance.emailExists(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of nameExists method, of class CompanyService.
     */
    @Test
    public void testNameExists() {
        System.out.println("nameExists");
        String name = "";
        CompanyService instance = null;
        boolean expResult = false;
        boolean result = instance.nameExists(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of taxidnumberExists method, of class CompanyService.
     */
    @Test
    public void testTaxidnumberExists() {
        System.out.println("taxidnumberExists");
        String taxidnumber = "";
        CompanyService instance = null;
        boolean expResult = false;
        boolean result = instance.taxidnumberExists(taxidnumber);
        assertEquals(expResult, result);
    }

    /**
     * Test of getReferencedWarning method, of class CompanyService.
     */
    @Test
    public void testGetReferencedWarning() {
        System.out.println("getReferencedWarning");
        Long id = null;
        CompanyService instance = null;
        String expResult = "";
        String result = instance.getReferencedWarning(id);
        assertEquals(expResult, result);
    }

    private static Company genCompany() {
        // Configurar valores
        String address = UUID.randomUUID() + " Company Address";
        String description = UUID.randomUUID() + " Company Description";
        String email = UUID.randomUUID() + "test_company@example.com";
        String name = UUID.randomUUID() + "Test Company Name";
        String phone1 = UUID.randomUUID() + "123456789";
        String primaryContact = UUID.randomUUID() + " Primary Contact";
        String taxidnumber = UUID.randomUUID() + " ABC123";
        Set<Person> companyPersons = new HashSet<>();
        OffsetDateTime dateCreated = OffsetDateTime.now();
        CompanyStatus status = new CompanyStatus(1L, "NONE");

        return Company.builder()
            .address(address)
            .description(description)
            .email(email)
            .name(name)
            .phone1(phone1)
            .primaryContact(primaryContact)
            .taxidnumber(taxidnumber)
            .persons(new HashSet<>())
            .dateCreated(dateCreated)
            .status(status)
            .build();
    }

}
