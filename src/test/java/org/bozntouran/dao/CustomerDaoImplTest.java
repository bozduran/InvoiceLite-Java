package org.bozntouran.dao;

import org.bozntouran.entities.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class CustomerDaoImplTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private CustomerDao customerDao;

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
                .addAnnotatedClass(Customer.class)
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
        customerDao = CustomerDaoImpl.getInstance();
        customerDao.setSessionFactory(sessionFactory);
    }

    @Test
    void saveCustomer(){

        Customer customer = new Customer(12312312,"Jhon Doe","test@gmail.com", new BigInteger(String.valueOf(695321123)));

        boolean save = customerDao.save(customer);

        assertTrue(save,"Failed to save customer");

    }

    @Test
    void getCustomerByAfm(){
        int afm = 12312312;
        Customer newCustomer = new Customer(afm,"Jhon Doe","test@gmail.com", new BigInteger(String.valueOf(695321123)));
        boolean save = customerDao.save(newCustomer);

        assertTrue(save,"Failed to save customer");

        Customer customer = customerDao.getCustomerByAfm(afm);

        assertEquals(afm,customer.getAfm(),"Failed to retrieve customer");

    }

}