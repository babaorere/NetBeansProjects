package com.isiweek.status;

import com.isiweek.company.Company;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Disabled
@ComponentScan(basePackages = "com.isiweek")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ExtendWith(SpringExtension.class)
//@SpringJUnitConfig
@SpringBootTest()
//@ContextConfiguration(classes = DomainConfig.class)
//@ExtendWith(SpringExtension.class)
public class StatusTest {

    private final StatusRepository statusRepository;
    private final StatusService statusService;
    private Optional<Status> testStatus;

    @Autowired
    public StatusTest(StatusRepository inStatusRepository, StatusService inStatusEntityService) {
        this.statusRepository = inStatusRepository;
        this.statusService = inStatusEntityService;
        this.testStatus = null; //Optional.ofNullable(Status.builderPending());
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
//        statusService.deleteAll();
    }

    @Test
    public void testCreateAndRead() {
////        statusService.deleteAll();
//
//        // Create
//        Status createdStatus = statusService.create(Status.builderPending());
//
//        // Read
//        Optional<Status> retrievedStatus = statusService.read(createdStatus.getId());
//
//        Assertions.assertTrue(retrievedStatus.isPresent());
//        Assertions.assertEquals(createdStatus, retrievedStatus.get());
    }

    /**
     * Test of randomGenerator method, of class Status.
     */
    @Test
    public void testRandomGenerator() {
//        statusService.deleteAll();
//        Status result = Status.randomGenerator();
//        Assertions.assertNotNull(result);
    }

    /**
     * Test of builderPending method, of class Status.
     */
    @Test
    public void testEmptyGenerator() {
//        Status result = Status.builderPending();
//        Assertions.assertNotNull(result);
    }

    /**
     * Test of builder method, of class Status.
     */
    @Test
    public void testBuilder() {
        Status.StatusBuilder result = Status.builder();
        Assertions.assertNotNull(result);
    }

    /**
     * Test of getId method, of class Status.
     */
    @Test
    public void testGetId() {
        Status instance = new Status();
        Long expResult = 0L;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatusEnum method, of class Status.
     */
    @Test
    public void testGetStatusEnum() {
//        Status instance = new Status();
//        StatusEnum expResult = StatusEnum.PENDING;
//        StatusEnum result = instance.getStatusEnum();
//        assertEquals(expResult, result);
    }

    /**
     * Test of getDateCreated method, of class Status.
     */
    @Test
    public void testGetDateCreated() {
        Status instance = new Status();
        OffsetDateTime result = instance.getDateCreated();
        Assertions.assertNotNull(result);
    }

    /**
     * Test of getLastUpdated method, of class Status.
     */
    @Test
    public void testGetLastUpdated() {
        Status instance = new Status();
        OffsetDateTime result = instance.getLastUpdated();
        Assertions.assertNotNull(result);
    }

    /**
     * Test of getCompanies method, of class Status.
     */
    @Test
    public void testGetCompanies() {
        Status instance = new Status();
        Set<Company> result = instance.getCompanies();
        Assertions.assertNotNull(result);
    }

    /**
     * Test of setId method, of class Status.
     */
    @Test
    public void testSetId() {
        Long id = 100L;
        Status instance = new Status();
        instance.setId(id);
        assertEquals(id, instance.getId());
    }

    /**
     * Test of setStatusEnum method, of class Status.
     */
    @Test
    public void testSetStatusEnum() {
//        StatusEnum statusEnum = StatusEnum.BLOCKEDUP;
//        Status instance = new Status();
//        instance.setStatusEnum(statusEnum);
//        assertEquals(statusEnum, instance.getStatusEnum());
    }

    /**
     * Test of setDateCreated method, of class Status.
     */
    @Test
    public void testSetDateCreated() {
        Status instance = new Status();
        Assertions.assertNotNull(instance.getDateCreated());
    }

    /**
     * Test of setLastUpdated method, of class Status.
     */
    @Test
    public void testSetLastUpdated() {
        Status instance = new Status();
        Assertions.assertNotNull(instance.getLastUpdated());
    }

    /**
     * Test of setCompanies method, of class Status.
     */
    @Test
    public void testSetCompanies() {
        Set<Company> companies = new HashSet<Company>();
        Status instance = new Status();
        instance.setCompanies(companies);
    }

    @Test
    public void testUpdate() {
//        statusService.deleteAll();
//
//        // Create
//        Status createdStatus = statusService.create(Status.builderPending());
//
//        // Update
//        Status updatedStatus = createdStatus;
//        updatedStatus.setStatusEnum(StatusEnum.BLOCKEDUP);
//
//        updatedStatus = statusService.update(updatedStatus);
//
//        // Read
//        Optional<Status> retrievedStatus = statusService.read(createdStatus.getId());
//        Assertions.assertTrue(retrievedStatus.isPresent());
//
//        System.out.println(updatedStatus);
//        System.out.println(retrievedStatus.get());
//
//        Assertions.assertEquals(updatedStatus, retrievedStatus.get());
    }

    @Test
    public void testDelete() {
        // Create
//        Status createdStatusEntity = statusService.create(testStatus.get());
//
//        // Delete
//        statusService.delete(createdStatusEntity.getId());
//
//        // Read
//        Optional<Status> retrievedStatusEntity = statusService.read(createdStatusEntity.getId());
//
//        Assertions.assertFalse(retrievedStatusEntity.isPresent());
    }

    @Test
    public void testFindAll() {
//        // Create some entities
//        statusService.deleteAll();
//        statusService.persistStatusEnumValues();
//
//        // Find all
//        List<Status> statusEntities = statusService.findAll();
//
//        Assertions.assertFalse(statusEntities.isEmpty());
//        Assertions.assertTrue(statusEntities.size() == StatusEnum.values().length);
    }

    @Test
    public void testEquals() {
//        Status statusEntity1 = Status.randomGenerator();
//        Status statusEntity2 = Status.builder().id(statusEntity1.getId()).statusEnum(statusEntity1.getStatusEnum()).build();
//
//        Assertions.assertEquals(statusEntity1, statusEntity2);
    }

    @Test
    public void testNullCheck() {
//        Assertions.assertThrows(Exception.class, () -> Status.builder().id(null).statusEnum(StatusEnum.PENDING).build());
//        Assertions.assertThrows(Exception.class, () -> Status.builder().id(1L).statusEnum(null).build());
    }

}
