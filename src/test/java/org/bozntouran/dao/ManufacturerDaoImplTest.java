package org.bozntouran.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.entities.Product;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)
class ManufacturerDaoImplTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private ManufacturerDao manufacturerDao;

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeAll
    public static void setup() {
        // Use H2 in-memory DB for testing
        registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.h2.Driver")
                .applySetting("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .applySetting("hibernate.hbm2ddl.auto", "create-drop")
                .build();

        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClasses(Manufacturer.class, Product.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) sessionFactory.close();
        if (registry != null) StandardServiceRegistryBuilder.destroy(registry);
    }

    @BeforeEach
    public void init() {
        manufacturerDao = ManufacturerDaoImpl.getInstance();
        manufacturerDao.setSessionFactory(sessionFactory);
    }

    @Test
    void getManufacturers() {
        Manufacturer manufacturer1 = Manufacturer.builder()
                .email("test1@gmail.com")
                .name("TestManu1")
                .phoneNumber(BigInteger.valueOf(123123121))
                .products(null)
                .build();

        Manufacturer manufacturer2 = Manufacturer.builder()
                .email("test2@gmail.com")
                .name("TestManu2")
                .phoneNumber(BigInteger.valueOf(123123122))
                .products(null)
                .build();

        Manufacturer manufacturer3 = Manufacturer.builder()
                .email("test3@gmail.com")
                .name("TestManu3")
                .phoneNumber(BigInteger.valueOf(123123123))
                .products(null)
                .build();
        boolean save1 = manufacturerDao.save(manufacturer1);
        boolean save2 = manufacturerDao.save(manufacturer2);
        boolean save3 = manufacturerDao.save(manufacturer3);
        assertTrue(save1);
        assertTrue(save2);
        assertTrue(save3);

        Optional<List<Manufacturer>> manufacturerList = manufacturerDao.getManufacturers();
        assertTrue(manufacturerList.isPresent(), "Expected true cause dao has items");
        assertEquals(3, manufacturerList.get().size(), "Expected 3");
    }

    @Test
    void save() {
        Manufacturer manufacturer = Manufacturer.builder()
                .email("test@gmail.com")
                .name("TesManu")
                .phoneNumber(BigInteger.valueOf(123123123))
                .products(null)
                .build();

        boolean save = manufacturerDao.save(manufacturer);

        assertTrue(save, "Failed to save manufacturer");
    }

    /*
        Hibernate validations
     */
    @Test
    void checkIfManufacturerValid_Valid() {

        Manufacturer manufacturer = Manufacturer.builder()
                .name("Manufacturer")
                .email("test@gmail.com")
                .phoneNumber(BigInteger.valueOf(123123))
                .products(new ArrayList<Product>())
                .build();


        Set<ConstraintViolation<Manufacturer>> constraintViolations =
                validator.validate(manufacturer);

        assertEquals(0, constraintViolations.size());


    }

    @Test
    void checkIfManufacturerValid_BlankName() {

        Manufacturer manufacturer = Manufacturer.builder()
                .name("")
                .email("test@gmail.com")
                .phoneNumber(BigInteger.valueOf(123123))
                .products(new ArrayList<Product>())
                .build();


        Set<ConstraintViolation<Manufacturer>> constraintViolations =
                validator.validate(manufacturer);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be blank", constraintViolations
                .iterator().next().getMessage());


    }

    @Test
    void checkIfManufacturerValid_NullName() {

        Manufacturer manufacturer = Manufacturer.builder()
                .name(null)
                .email("test@gmail.com")
                .phoneNumber(BigInteger.valueOf(123123))
                .products(new ArrayList<Product>())
                .build();

        Set<ConstraintViolation<Manufacturer>> constraintViolations =
                validator.validate(manufacturer);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be blank", constraintViolations
                .iterator().next().getMessage());

    }

    @Test
    void checkIfManufacturerValid_BadEmail() {

        Manufacturer manufacturer = Manufacturer.builder()
                .name("John doe")
                .email("testgmail")
                .phoneNumber(BigInteger.valueOf(123123))
                .products(new ArrayList<Product>())
                .build();

        Set<ConstraintViolation<Manufacturer>> constraintViolations =
                validator.validate(manufacturer);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a well-formed email address", constraintViolations
                .iterator().next().getMessage());


    }
}