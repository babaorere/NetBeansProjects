package com.isiweek.company;

import com.isiweek.AppConfig;
import com.isiweek.status.Status;
import com.isiweek.status.StatusRepository;
import com.isiweek.status.StatusService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

@Disabled
@ComponentScan(basePackages = "com.isiweek.company")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig
@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class CompanyServiceTest {

    private final StatusRepository statusRepository;
    private final StatusService statusService;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    @Autowired
    public CompanyServiceTest(StatusRepository inStatusRepository, StatusService inStatusService,
            CompanyRepository inCompanyRepository, CompanyService inCompanyService) {

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
        companyService.deleteAll();
        statusService.deleteAll();
    }

    /**
     * Test of findAll method, of class CompanyService.
     */
    @Test
    public void testFindAll() {

        Company company = Company.generateRandom();
        Optional<Status> statusOp = statusRepository.findFirst();

        company.setStatus(statusOp.get());

        companyService.create(company);

        List<Company> result = companyService.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() > 0);
    }

    /**
     * Test of get method, of class CompanyService.
     */
    @Test
    public void testGet() {
        Company company = Company.generateRandom();
        Optional<Status> statusOp = statusRepository.findFirst();

        company.setStatus(statusOp.get());

        company = companyService.create(company);

        Company company2 = Company.generateRandom();
        company2.setStatus(statusOp.get());

        Optional<Company> company3Op = companyRepository.findById(company.getId());

        assertNotNull(company3Op.get());

        Assertions.assertEquals(company, company3Op.get());
        Assertions.assertNotEquals(company2, company);
    }

    /**
     * Test of create method, of class CompanyService.
     */
    @Test
    public void testCreate() {
        Company company = Company.generateRandom();
        Optional<Status> statusOp = statusRepository.findFirst();

        company.setStatus(statusOp.get());

        Company result = companyService.create(company);

        company.setId(result.getId());

        assertEquals(company, result);
    }

    /**
     * Test of update method, of class CompanyService.
     */
    @Test
    public void testUpdate() {

        Company company = Company.generateRandom();
        Optional<Status> statusOp = statusRepository.findFirst();

        company.setStatus(statusOp.get());

        company = companyService.create(company);

        String name = "ISIWEEK";
        company.setName(name);

        Company result = companyService.update(company.getId(), company);

        assertEquals(company, result);
    }

    /**
     * Test of delete method, of class CompanyService.
     */
    @Test
    public void testDelete() {

        Company company = Company.generateRandom();
        Optional<Status> statusOp = statusRepository.findFirst();

        company.setStatus(statusOp.get());

        company = companyService.create(company);

        // Obtener el ID de la Company antes de borrar
        Long companyId = company.getId();

        // Borrar la Company
        companyService.delete(companyId);

        // Verificar que la Company se haya eliminado buscándola por su ID
        Optional<Company> deletedCompany = companyService.findById(companyId);
        assertFalse(deletedCompany.isPresent(), "Company should be deleted");

    }

    @Test
    public void testExistsMethods() {

        Company company = Company.generateRandom();
        Optional<Status> statusOp = statusRepository.findFirst();

        company.setStatus(statusOp.get());

        company = companyService.create(company);

        // Datos de prueba
        String email = "test@example.com";
        String name = "Test Company";
        String taxidnumber = "12345678";
        Long id = company.getId();

        // Verificar los métodos de existencia
        assertFalse(companyService.emailExists(email));
        assertFalse(companyService.nameExists(name));
        assertFalse(companyService.taxidnumberExists(taxidnumber));

        // Verificar los métodos de existencia
        assertTrue(companyService.emailExists(company.getEmail()));
        assertTrue(companyService.nameExists(company.getName()));
        assertTrue(companyService.taxidnumberExists(company.getTaxidnumber()));

    }
}
