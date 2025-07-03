package org.bozntouran.dao;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class ProductDaoImplTest {
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
                .addAnnotatedClasses(Product.class, Manufacturer.class)
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

            session.createQuery("DELETE FROM Product").executeUpdate();
            session.createQuery("DELETE FROM Manufacturer").executeUpdate();

            tx.commit();
        }
    }

    @Test
    void getProductByBarcode() {
        int barcodeInt = new Random(1).nextInt(99999999);
        String barcode = String.valueOf(barcodeInt);
        Product newProduct = Product.builder()
                .barcode(barcode)
                .name("Some Product")
                .description("test description")
                .price(123.23)
                .quantity(23)
                .build();

        boolean save = productDao.save(newProduct);
        assertTrue(save,"Expected true");

        Product product = productDao.getProductByBarcode(barcode);

        assertEquals(newProduct.getName(),product.getName() );
        assertEquals(barcode,product.getBarcode());
    }

    @Test
    void save() {

        String barcode = String.valueOf(new Random(1).nextInt(99999999));
        Product product = Product.builder()
                .barcode(barcode)
                .name("Some Product")
                .description("test description")
                .price(123.23)
                .quantity(23)
                .build();

        boolean save = productDao.save(product);
        assertTrue(save,"Expected true");

    }

    @Test
    void updateProductQuantity_Add_One() {
        int quantity = 100;

        String barcode = String.valueOf(new Random(1).nextInt(99999999));
        Product product = Product.builder()
                .barcode(barcode)
                .name("Some Product")
                .description("test description")
                .price(123.23)
                .quantity(quantity)
                .build();

        boolean save = productDao.save(product);
        assertTrue(save, "Save failed");

        Product returnedProduct = productDao.getProductByBarcode(barcode);
        assertNotNull(returnedProduct,"Must be non null ");

        productDao.updateProductQuantity("+"
                ,returnedProduct.getId(),1);
        Product returnedProduct2 = productDao.getProductByBarcode(barcode);

        assertEquals(quantity+1 ,productDao.getProductByBarcode(barcode).getQuantity());


    }

    @Test
    void updateProductQuantity_Remove_One() {
        int quantity = 100;
        String barcode = String.valueOf(new Random(1).nextInt(99999999));
        Product product = Product.builder()
                .barcode(barcode)
                .name("Some Product")
                .description("test description")
                .price(123.23)
                .quantity(quantity)
                .build();

        boolean save = productDao.save(product);
        assertTrue(save, "Save failed");

        Product returnedProduct = productDao.getProductByBarcode(barcode);
        assertNotNull(returnedProduct,"Must be non null ");

        productDao.updateProductQuantity("-"
                ,returnedProduct.getId(),1);

        assertEquals(quantity-1 ,productDao.getProductByBarcode(barcode).getQuantity());

    }


    @Test
    void getProducts() {
        Random random = new Random();
        Product product1 = Product.builder()
                .barcode(String.valueOf(random.nextInt(90000000) ))
                .name("Gaming Mouse")
                .description("High precision wireless mouse")
                .price(49.99)
                .quantity(25)
                .build();

        Product product2 = Product.builder()
                .barcode(String.valueOf(random.nextInt(90000000) ))
                .name("Mechanical Keyboard")
                .description("RGB backlit mechanical keyboard")
                .price(89.50)
                .quantity(40)
                .build();

        Product product3 = Product.builder()
                .barcode(String.valueOf(random.nextInt(90000000) ))
                .name("USB-C Hub")
                .description("Multiport adapter with HDMI and USB")
                .price(29.99)
                .quantity(15)
                .build();

        Product product4 = Product.builder()
                .barcode(String.valueOf(random.nextInt(90000000) ))
                .name("27-inch Monitor")
                .description("Full HD LED monitor with HDMI support")
                .price(159.95)
                .quantity(10)
                .build();
        boolean save1 = productDao.save(product1);
        boolean save2 = productDao.save(product2);
        boolean save3 = productDao.save(product3);
        boolean save4 = productDao.save(product4);
        assertTrue(save1);
        assertTrue(save2);
        assertTrue(save3);
        assertTrue(save4);
        assertTrue(productDao.getProducts().isPresent());
        assertEquals(4,productDao.getProducts().get().size());

    }




    @Test
    void checkIfProductValid_Valid(){

        Product product = Product.builder()
                .barcode(String.valueOf(1234 ))
                .name("Gaming Mouse")
                .description("High precision wireless mouse")
                .price(49.99)
                .quantity(25)
                .build();

        Set<ConstraintViolation<Product>> constraintValidators =
                validator.validate(product);

        assertEquals(0,constraintValidators.size());
    }

    @Test
    void checkIfProductValid_NullName(){

        Product product = Product.builder()
                .barcode(String.valueOf( "234"))
                .name(null)
                .description("High precision wireless mouse")
                .price(49.99)
                .quantity(25)
                .build();

        Set<ConstraintViolation<Product>> constraintValidators =
                validator.validate(product);

        assertEquals(1,constraintValidators.size());
        assertEquals("must not be blank",
                constraintValidators.iterator().next().getMessage());
    }


    @Test
    void checkIfProductValid_BlankBarcode(){

        Product product = Product.builder()
                .barcode(String.valueOf( ""))
                .name("Gaming Mouse")
                .description("High precision wireless mouse")
                .price(49.99)
                .quantity(25)
                .build();

        Set<ConstraintViolation<Product>> constraintValidators =
                validator.validate(product);

        assertEquals(1,constraintValidators.size());
        assertEquals("must not be blank",
                constraintValidators.iterator().next().getMessage());


    }

    @Test
    void checkIfProductValid_NegativePrice(){

        Product product = Product.builder()
                .barcode(String.valueOf( 123))
                .name("Gaming Mouse")
                .description("High precision wireless mouse")
                .price(-49.99)
                .quantity(25)
                .build();

        Set<ConstraintViolation<Product>> constraintValidators =
                validator.validate(product);

        assertEquals(1,constraintValidators.size());
        assertEquals("must be greater than 0",
                constraintValidators.iterator().next().getMessage());


    }

    @Test
    void checkIfProductValid_NegativeQuantity(){

        Product product = Product.builder()
                .barcode(String.valueOf( 123))
                .name("Gaming Mouse")
                .description("High precision wireless mouse")
                .price(49.99)
                .quantity(-25)
                .build();

        Set<ConstraintViolation<Product>> constraintValidators =
                validator.validate(product);

        assertEquals(1,constraintValidators.size());
        assertEquals("must be greater than or equal to 0",
                constraintValidators.iterator().next().getMessage());


    }





}