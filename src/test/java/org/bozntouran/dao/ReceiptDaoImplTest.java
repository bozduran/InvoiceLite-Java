package org.bozntouran.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.bozntouran.entities.Receipt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.MethodName.class)
class ReceiptDaoImplTest {

    static StandardServiceRegistry registry;
    static SessionFactory sessionFactory;
    ProductDao productDao;


    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeAll
    static void setup() {
        // Use H2 in-memory DB for testing
        registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.h2.Driver")
                .applySetting("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .applySetting("hibernate.hbm2ddl.auto", "create-drop")
                .build();

        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClasses(Receipt.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) sessionFactory.close();
        if (registry != null) StandardServiceRegistryBuilder.destroy(registry);
    }

    @BeforeEach
    void init() {
        productDao = ProductDaoImpl.getInstance();
        productDao.setSessionFactory(sessionFactory);

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.createQuery("DELETE FROM Receipt ").executeUpdate();

            tx.commit();
        }
    }


    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getReceipts() {
    }

    @Test
    void getReceipt() {
    }


    @Test
    void chekIfReceiptValid_Valid() {
        Receipt receipt = Receipt.builder()
                .price(123.2)
                .filename("filename.txt")
                .date(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Receipt>> constraintViolations =
                validator.validate(receipt);

        assertEquals(0, constraintViolations.size());

    }

    @Test
    void chekIfReceiptValid_NegativePrice() {
        Receipt receipt = Receipt.builder()
                .price(-123.2)
                .filename("filename.txt")
                .date(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Receipt>> constraintViolations =
                validator.validate(receipt);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than or equal to 0",constraintViolations.iterator().next().getMessage());
    }

    @Test
    void chekIfReceiptValid_BlankFilename() {
        Receipt receipt = Receipt.builder()
                .price(123.2)
                .filename("")
                .date(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Receipt>> constraintViolations =
                validator.validate(receipt);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be blank",constraintViolations.iterator().next().getMessage());
    }

    @Test
    void chekIfReceiptValid_NullFilename() {
        Receipt receipt = Receipt.builder()
                .price(123.2)
                .filename(null)
                .date(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Receipt>> constraintViolations =
                validator.validate(receipt);

        assertEquals(1, constraintViolations.size());
        assertEquals("must not be blank",constraintViolations.iterator().next().getMessage());
    }

    @Test
    void chekIfReceiptValid_FutureDate() {
        Receipt receipt = Receipt.builder()
                .price(123.2)
                .filename("filename.txt")
                .date(LocalDateTime.of(3123,1,1,1,1))
                .build();

        Set<ConstraintViolation<Receipt>> constraintViolations =
                validator.validate(receipt);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be a date in the past or in the present",constraintViolations.iterator().next().getMessage());
    }
}