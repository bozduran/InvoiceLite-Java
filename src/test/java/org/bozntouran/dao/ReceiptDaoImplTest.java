package org.bozntouran.dao;

import org.bozntouran.entities.Manufacturer;
import org.bozntouran.entities.Product;
import org.bozntouran.entities.Receipt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class ReceiptDaoImplTest {

    static StandardServiceRegistry registry;
    static SessionFactory sessionFactory;
    ProductDao productDao;
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
}