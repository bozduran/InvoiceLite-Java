package org.bozntouran.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.bozntouran.entities.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.math.BigInteger;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)
class CustomerDaoImplTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private CustomerDao customerDao;

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

        Customer customer = new Customer(2312312,"Jhon Doe","test@gmail.com", new BigInteger(String.valueOf(695321123)));

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


    /*
        Hibernate validations
     */

    @Test
    void checkIfCustomerValid_BlankName(){

        Customer customer = new Customer(1231232,"","test@gamil.com",BigInteger.valueOf(123) );

        Set<ConstraintViolation<Customer>> constraintValidators =
                validator.validate(customer);

        assertEquals(1,constraintValidators.size());
        assertEquals( "must not be blank", constraintValidators.iterator().next().
                getMessage() );
    }

    @Test
    void checkIfCustomerValid_NullName(){

        Customer customer = new Customer(1231232,null,"test@gmail.com",BigInteger.valueOf(123) );

        Set<ConstraintViolation<Customer>> constraintValidators =
                validator.validate(customer);

        assertEquals(1,constraintValidators.size());
        assertEquals( "must not be blank", constraintValidators.iterator().next().
                getMessage() );
    }

    @Test
    void checkIfCustomerValid_BadAEmail(){

        Customer customer = new Customer(1231232,"Some Name","asdasdasd",BigInteger.valueOf(123) );

        Set<ConstraintViolation<Customer>> constraintValidators =
                validator.validate(customer);

        assertEquals(1,constraintValidators.size());
        assertEquals( "must be a well-formed email address", constraintValidators.iterator().next().
                getMessage() );
    }

    @Test
    void checkIfCustomerValid_NegativeAfm(){

        Customer customer = new Customer(-11231232,"Some Name","asdasdasd@gmail.com",BigInteger.valueOf(123) );

        Set<ConstraintViolation<Customer>> constraintValidators =
                validator.validate(customer);

        assertEquals(1,constraintValidators.size());
        assertEquals( "must be greater than 0", constraintValidators.iterator().next().
                getMessage() );
    }

}