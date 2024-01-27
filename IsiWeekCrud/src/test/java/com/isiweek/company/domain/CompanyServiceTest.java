package com.isiweek.company.domain;

import com.isiweek.AppConfig;
import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.company.CompanyService;
import com.isiweek.company.CompanyStatusRepository;
import java.util.List;
import java.util.Optional;
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

    private final CompanyStatusRepository companyStatusRepository;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    @Autowired
    public CompanyServiceTest(CompanyService inCcompanyService,
            CompanyRepository inCompanyRepository,
            CompanyStatusRepository inCcompanyStatusRepository) {
        this.companyService = inCcompanyService;
        this.companyRepository = inCompanyRepository;
        this.companyStatusRepository = inCcompanyStatusRepository;
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
        company = companyRepository.save(company);
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
        List<Company> result = companyService.findAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Test of get method, of class CompanyService.
     */
    @Test
    public void testGet() {

        Company company = companyRepository.save(Company.generateRandom());

        Optional<Company> anyCompany = companyRepository.findAll().stream().findFirst();

        assertNotNull(anyCompany.get());
    }

    /**
     * Test of create method, of class CompanyService.
     */
    @Test
    public void testCreate() {
        Company expResult = Company.generateRandom();
        Company result = companyService.create(expResult);
        expResult.setId(result.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class CompanyService.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Long id = null;
        Company entity = null;
        CompanyService instance = null;
        instance.update(id, entity);
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

}
