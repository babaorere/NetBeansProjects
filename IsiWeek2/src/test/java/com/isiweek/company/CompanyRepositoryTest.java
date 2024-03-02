package com.isiweek.company;

import com.isiweek.status.StatusRepository;
import com.isiweek.status.StatusService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author manager
 */
@Disabled
@ComponentScan(basePackages = "com.isiweek.company")
//@ExtendWith(SpringExtension.class)
//@SpringJUnitConfig
@SpringBootTest
//@ContextConfiguration(classes = DomainConfig.class)
public class CompanyRepositoryTest {

    private final StatusRepository statusRepository;
    private final StatusService statusService;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    @Autowired(required = true)
    public CompanyRepositoryTest(StatusRepository inStatusRepository, StatusService inStatusService,
            CompanyRepository inCompanyRepository,
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
//        companyRepository.deleteAll();
//        statusService.persistStatusEnumValues();
    }

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
        statusRepository.deleteAll();
    }

    @Test
//    @Disabled
    void testSaveAndFindById() {

//        Company company = Company.generateRandom();
//        Optional<Status> statusOp = statusRepository.findFirst();
//
//        company.setStatus(statusOp.get());
//
//        company = companyService.create(company);
//
//        assertTrue(statusOp.isPresent());
//        assertNotNull(statusOp.get().getId());
//
//        company.setStatus(statusOp.get());
//        company = companyService.create(company);
//
//        assertNotNull(company.getId());
//
//        Status foundStatus = statusService.findById(statusOp.get().getId()).orElse(null);
//        Company foundCompany = companyService.findById(company.getId()).orElse(null);
//
//        assertNotNull(foundStatus);
//        assertEquals(statusOp.get().getId(), foundStatus.getId());
//        assertEquals(statusOp.get().getStatusEnum(), foundStatus.getStatusEnum());
//
//        assertNotNull(foundCompany);
//        assertEquals(company.getName(), foundCompany.getName());
//        assertEquals(company.getEmail(), foundCompany.getEmail());
//        assertEquals(company.getTaxidnumber(), foundCompany.getTaxidnumber());
    }

//    @Disabled
    @Test
    void testExistsByEmailIgnoreCase() {
//        Company company = Company.generateRandom();
//        Optional<Status> statusOp = statusRepository.findFirst();
//
//        company.setStatus(statusOp.get());
//
//        company = companyService.create(company);
//
//        assertNotNull(statusOp.get().getId());
//        assertNotNull(company.getId());
//
//        Status foundStatus = statusService.findById(statusOp.get().getId()).orElse(null);
//        Company foundCompany = companyService.findById(company.getId()).orElse(null);
//
//        assertTrue(companyRepository.existsByEmailIgnoreCase(foundCompany.getEmail()));
//        assertTrue(companyRepository.existsByEmailIgnoreCase(company.getEmail()));
//        assertFalse(companyRepository.existsByEmailIgnoreCase("nonexistent@example.com"));
    }

//
//    @Disabled
    @Test
    void testExistsByNameIgnoreCase() {
//        Company company = Company.generateRandom();
//        Optional<Status> statusOp = statusRepository.findFirst();
//
//        company.setStatus(statusOp.get());
//
//        company = companyService.create(company);
//
//        assertNotNull(statusOp.get().getId());
//        assertNotNull(company.getId());
//
//        Optional<Status> foundStatus = statusService.findById(statusOp.get().getId());
//        Optional<Company> foundCompany = companyService.findById(company.getId());
//
//        assertTrue(companyRepository.existsByNameIgnoreCase(company.getName()));
//        assertTrue(companyRepository.existsByNameIgnoreCase(foundCompany.get().getName()));
//        assertFalse(companyRepository.existsByNameIgnoreCase("Nonexistent Company"));
    }

//
//    @Disabled
    @Test
    void testExistsByTaxidnumberIgnoreCase() {
//
//        Company company = Company.generateRandom();
//        Optional<Status> statusOp = statusRepository.findFirst();
//
//        company.setStatus(statusOp.get());
//
//        company = companyService.create(company);
//
//        assertNotNull(statusOp.get().getId());
//        assertNotNull(company.getId());
//
//        assertDoesNotThrow(() -> Company.builder().build());
//
//        // Crear instancia utilizando el builder de Lombok
//        final Company company2 = Company.builder()
//                .address(company.getAddress())
//                .description(company.getDescription())
//                .email(company.getEmail())
//                .name(company.getName())
//                .phone1(company.getPhone1())
//                .primaryContact(company.getPrimaryContact())
//                .taxidnumber(company.getTaxidnumber())
//                .persons(company.getPersons())
//                .dateCreated(company.getDateCreated())
//                .lastUpdated(company.getLastUpdated())
//                .status(company.getStatus())
//                .build();
//
//        // Intentar guardar una segunda compañía con el mismo correo electrónico debe lanzar DataIntegrityViolationException
//        assertThrows(Exception.class, () -> {
//            Company create = companyService.create(company2);
//        });
//
//        Company foundCompany = companyService.findById(company.getId()).get();
//
//        assertTrue(companyRepository.existsByTaxidnumberIgnoreCase(company.getTaxidnumber()));
//        assertTrue(companyRepository.existsByTaxidnumberIgnoreCase(foundCompany.getTaxidnumber()));
//        assertFalse(companyRepository.existsByTaxidnumberIgnoreCase("nonexistent"));
    }

//    @Disabled
    @Test
    void testUniqueConstraints() {

//        Company company1 = Company.generateRandom();
//        Company company2 = Company.generateRandom();
//
//        Optional<Status> statusOp = statusRepository.findFirst();
//
//        company1.setStatus(statusOp.get());
//
//        company2.setStatus(statusOp.get());
//        company2.setEmail(company1.getEmail());
//
//        company1 = companyService.create(company1);
//
//        assertNotNull(statusOp.get().getId());
//        assertNotNull(company1.getId());
//
//        // Intentar guardar una segunda compañía con el mismo correo electrónico debe lanzar DataIntegrityViolationException
//        assertThrows(Exception.class, () -> {
//            companyService.create(company2);
//        });
    }
}
