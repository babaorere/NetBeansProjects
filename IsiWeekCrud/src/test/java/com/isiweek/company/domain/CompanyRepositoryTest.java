package com.isiweek.company.domain;

import com.isiweek.AppConfig;
import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.company.CompanyStatus;
import com.isiweek.company.CompanyStatusRepository;
import com.isiweek.person.Person;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 *
 * @author manager
 */
//@Disabled
@ComponentScan(basePackages = "com.isiweek.company")
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig
@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class CompanyRepositoryTest {

    @Autowired(required = true)
    private CompanyStatusRepository companyStatusRepository;

    @Autowired(required = true)
    private CompanyRepository companyRepository;

    public CompanyRepositoryTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        CompanyStatus status = companyStatusRepository.save(CompanyStatus.generateRandom());
    }

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
        companyStatusRepository.deleteAll();
    }

//    @Disabled
    @Test
    void testSaveAndFindById() {

        Company company = Company.generateRandom();

        companyStatusRepository.save(company.getStatus());
        company = companyRepository.save(company);

        assertNotNull(company.getId());

        Company foundCompany = companyRepository.findById(company.getId()).orElse(null);
        assertNotNull(foundCompany);
        assertEquals(company.getName(), foundCompany.getName());
        assertEquals(company.getEmail(), foundCompany.getEmail());
        assertEquals(company.getTaxidnumber(), foundCompany.getTaxidnumber());
    }

//    @Disabled
//
    @Test
    void testExistsByEmailIgnoreCase() {
        company = Company.generateRandom();
        company.setStatus(status);

        Company foundCompany = companyRepository.save(company);

        assertTrue(companyRepository.existsByEmailIgnoreCase(foundCompany.getEmail()));
        assertTrue(companyRepository.existsByEmailIgnoreCase(company.getEmail()));
        assertFalse(companyRepository.existsByEmailIgnoreCase("nonexistent@example.com"));
    }

//
//    @Disabled
    @Test
    void testExistsByNameIgnoreCase() {
        company = Company.generateRandom();
        company.setStatus(status);

        Company foundCompany = companyRepository.save(company);

        assertTrue(companyRepository.existsByNameIgnoreCase(company.getName()));
        assertTrue(companyRepository.existsByNameIgnoreCase(foundCompany.getName()));
        assertFalse(companyRepository.existsByNameIgnoreCase("Nonexistent Company"));
    }

//
//    @Disabled
    @Test
    void testExistsByTaxidnumberIgnoreCase() {
        // Configurar valores
        String address = "Company Address";
        String description = "Company Description";
        String email = UUID.randomUUID() + "company@example.com";
        String name = UUID.randomUUID() + "Company Name";
        String phone1 = "123456789";
        String primaryContact = "Primary Contact";
        String taxidnumber = UUID.randomUUID() + "ABC123";
        Set<Person> companyPersons = new HashSet<>();
        OffsetDateTime dateCreated = OffsetDateTime.now();
        OffsetDateTime lastUpdated = OffsetDateTime.now();
        status = new CompanyStatus(1L, "NONE");

        assertThrows(Exception.class, ()
                -> Company.builder()
                        .address(address)
                        .description(description)
                        .email(email)
                        .name("Test Company")
                        .build());

        // Crear instancia utilizando el builder de Lombok
        company = Company.builder()
                .address(address)
                .description(description)
                .email(email)
                .name(name)
                .phone1(phone1)
                .primaryContact(primaryContact)
                .taxidnumber(taxidnumber)
                .persons(new HashSet<>())
                .dateCreated(dateCreated)
                .lastUpdated(lastUpdated)
                .status(status)
                .build();

        Company foundCompany = companyRepository.save(company);

        assertTrue(companyRepository.existsByTaxidnumberIgnoreCase(company.getTaxidnumber()));
        assertTrue(companyRepository.existsByTaxidnumberIgnoreCase(foundCompany.getTaxidnumber()));
        assertFalse(companyRepository.existsByTaxidnumberIgnoreCase("nonexistent"));
    }

    @Test
    void testUniqueConstraints() {
        // Configurar valores
        String address1 = "Company Address";
        String description1 = "Company Description";
        String email = UUID.randomUUID() + "company@example.com";
        String name1 = UUID.randomUUID() + "Company Name";
        String phone11 = "123456789";
        String primaryContact1 = "Primary Contact";
        String taxidnumber1 = UUID.randomUUID() + "ABC123";
        Set<Person> companyPersons1 = new HashSet<>();
        OffsetDateTime dateCreated1 = OffsetDateTime.now();
        OffsetDateTime lastUpdated1 = OffsetDateTime.now();
        CompanyStatus status1 = new CompanyStatus(1L, "NONE");

        // Configurar valores
        String address2 = "Company Address 2";
        String description2 = "Company Description 2";
        String name2 = UUID.randomUUID() + "Company Name 2";
        String phone12 = "123456789 2";
        String primaryContact2 = "Primary Contact 2";
        String taxidnumber2 = UUID.randomUUID() + "ABC123";
        Set<Person> companyPersons2 = new HashSet<>();
        OffsetDateTime dateCreated2 = OffsetDateTime.now();
        OffsetDateTime lastUpdated2 = OffsetDateTime.now();
        CompanyStatus status2 = new CompanyStatus(1L, "NONE");

        // Crear instancia utilizando el builder de Lombok
        Company company1 = Company.builder()
                .address(address1)
                .description(description1)
                .email(email)
                .name(name1)
                .phone1(phone11)
                .primaryContact(primaryContact1)
                .taxidnumber(taxidnumber1)
                .persons(new HashSet<>())
                .dateCreated(dateCreated1)
                .lastUpdated(lastUpdated1)
                .status(status1)
                .build();

        companyRepository.save(company1);

        // Intentar guardar una segunda compañía con el mismo correo electrónico debe lanzar DataIntegrityViolationException
        assertThrows(DataIntegrityViolationException.class, () -> {
            Company company2 = Company.builder()
                    .address(address2)
                    .description(description2)
                    .email(email)
                    .name(name2)
                    .phone1(phone12)
                    .primaryContact(primaryContact2)
                    .taxidnumber(taxidnumber2)
                    .persons(new HashSet<>())
                    .dateCreated(dateCreated2)
                    .lastUpdated(lastUpdated2)
                    .status(status2)
                    .build();

            companyRepository.saveAndFlush(company2);
        });
    }
}
