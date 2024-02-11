package com.isiweek.company;

import com.isiweek.status.Status;
import com.isiweek.AppConfig;
import java.util.Optional;
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

    private final CompanyStatusService statusService;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    @Autowired(required = true)
    public CompanyRepositoryTest(CompanyStatusService inStatusService,
            CompanyRepository inCompanyRepository,
            CompanyService inCompanyService) {
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
        Company company = Company.generateRandom();

        statusService.create(company.getStatus());
        companyService.create(company);
    }

    @AfterEach
    void tearDown() {
        companyService.deleteAll();
        statusService.deleteAll();
    }

//    @Disabled
    @Test
    void testSaveAndFindById() {

        Company company = Company.generateRandom();
        Status status = company.getStatus();

        status = statusService.create(status);
        company.setStatus(status);
        company = companyService.create(company);

        assertNotNull(status.getId());
        assertNotNull(company.getId());

        Status foundStatus = statusService.findById(status.getId()).orElse(null);
        Company foundCompany = companyService.findById(company.getId()).orElse(null);

        assertNotNull(foundStatus);
        assertEquals(status.getId(), foundStatus.getId());
        assertEquals(status.getName(), foundStatus.getName());

        assertNotNull(foundCompany);
        assertEquals(company.getName(), foundCompany.getName());
        assertEquals(company.getEmail(), foundCompany.getEmail());
        assertEquals(company.getTaxidnumber(), foundCompany.getTaxidnumber());
    }

//    @Disabled
    @Test
    void testExistsByEmailIgnoreCase() {
        Company company = Company.generateRandom();
        Status status = company.getStatus();

        status = statusService.create(status);
        company.setStatus(status);
        company = companyService.create(company);

        assertNotNull(status.getId());
        assertNotNull(company.getId());

        Status foundStatus = statusService.findById(status.getId()).orElse(null);
        Company foundCompany = companyService.findById(company.getId()).orElse(null);

        assertTrue(companyRepository.existsByEmailIgnoreCase(foundCompany.getEmail()));
        assertTrue(companyRepository.existsByEmailIgnoreCase(company.getEmail()));
        assertFalse(companyRepository.existsByEmailIgnoreCase("nonexistent@example.com"));
    }

//
//    @Disabled
    @Test
    void testExistsByNameIgnoreCase() {
        Company company = Company.generateRandom();
        Status status = company.getStatus();

        status = statusService.create(status);
        company.setStatus(status);
        company = companyService.create(company);

        assertNotNull(status.getId());
        assertNotNull(company.getId());

        Optional<Status> foundStatus = statusService.findById(status.getId());
        Optional<Company> foundCompany = companyService.findById(company.getId());

        assertTrue(companyRepository.existsByNameIgnoreCase(company.getName()));
        assertTrue(companyRepository.existsByNameIgnoreCase(foundCompany.get().getName()));
        assertFalse(companyRepository.existsByNameIgnoreCase("Nonexistent Company"));
    }

//
//    @Disabled
    @Test
    void testExistsByTaxidnumberIgnoreCase() {

        Company company = Company.generateRandom();
        Status status = company.getStatus();

        status = statusService.create(status);
        company = companyService.create(company);

        assertNotNull(status.getId());
        assertNotNull(company.getId());

        assertThrows(Exception.class, () -> Company.builder().build());

        // Crear instancia utilizando el builder de Lombok
        final Company company2 = Company.builder()
                .address(company.getAddress())
                .description(company.getDescription())
                .email(company.getEmail())
                .name(company.getName())
                .phone1(company.getPhone1())
                .primaryContact(company.getPrimaryContact())
                .taxidnumber(company.getTaxidnumber())
                .persons(company.getPersons())
                .dateCreated(company.getDateCreated())
                .lastUpdated(company.getLastUpdated())
                .status(company.getStatus())
                .build();

        // Intentar guardar una segunda compañía con el mismo correo electrónico debe lanzar DataIntegrityViolationException
        assertThrows(DataIntegrityViolationException.class, () -> {
            Company create = companyService.create(company2);
        });

        Company foundCompany = companyService.findById(company.getId()).get();

        assertTrue(companyRepository.existsByTaxidnumberIgnoreCase(company.getTaxidnumber()));
        assertTrue(companyRepository.existsByTaxidnumberIgnoreCase(foundCompany.getTaxidnumber()));
        assertFalse(companyRepository.existsByTaxidnumberIgnoreCase("nonexistent"));
    }

    @Test
    void testUniqueConstraints() {

        Company company1 = Company.generateRandom();
        Company company2 = Company.generateRandom();

        Status status = company1.getStatus();
        status = statusService.create(status);

        company1.setStatus(status);
        company1 = companyService.create(company1);

        assertNotNull(status.getId());
        assertNotNull(company1.getId());

        company2.setStatus(status);
        company2.setEmail(company1.getEmail());

        System.out.println("C1 " + company1);
        System.out.println("C2" + company2);

        // Intentar guardar una segunda compañía con el mismo correo electrónico debe lanzar DataIntegrityViolationException
        assertThrows(DataIntegrityViolationException.class, () -> {
            companyService.create(company2);
        });
    }
}
