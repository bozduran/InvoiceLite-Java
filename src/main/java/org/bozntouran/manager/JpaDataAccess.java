package org.bozntouran.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.bozntouran.entities.Customer;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.entities.Product;
import org.bozntouran.entities.Receipt;

import java.util.List;
import java.util.Optional;

public class JpaDataAccess implements DataAccesor {

    private static JpaDataAccess jpaDataAccess;

    private EntityManager entityManager;
    private EntityManagerFactory sessionFactory;

    private JpaDataAccess() {

    }

    public static JpaDataAccess getInstance() {

        if (jpaDataAccess == null) {
            jpaDataAccess = new JpaDataAccess();

        }

        return jpaDataAccess;
    }

    @Override
    public void intialize() {

        if (entityManager == null) {
            sessionFactory =
                    Persistence.createEntityManagerFactory("org.bozntouran.entities");
            entityManager = sessionFactory.createEntityManager();
        }


    }

    @Override
    public Customer getCustomer(int afm) {


        var transaction = entityManager.getTransaction();
        Customer customer = null;
        try {


            transaction.begin();
            String jpql = "SELECT c FROM Customer c WHERE c.afm = :afm";
            customer = entityManager.createQuery(jpql,Customer.class )
                    .setParameter("afm", afm)
                    .getSingleResult();

            if (customer == null) {
                System.err.println("is null");
            }
            transaction.commit();


        } catch (Exception e) {
            System.err.println(e);
        }

        return customer;
    }

    @Override
    public boolean addNewReceipt(Receipt receipt){

        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(receipt);
            transaction.commit();
        }catch (Exception e) {
            System.err.println(e);
        }

        return true;
    }

    @Override
    public Product getProduct(String barcode) {

        var transaction = entityManager.getTransaction();
        Product product = null;
        try {


            transaction.begin();
            String jpql = "SELECT p FROM Product p WHERE p.barcode LIKE :barcode";

            product = entityManager.createQuery(jpql, Product.class)
                    .setParameter("barcode", barcode)
                    .getSingleResult();

            transaction.commit();

        } catch (Exception e) {
            System.err.println(e);
        }


        return product;
    }

    @Override
    public boolean addNewCustomer(Customer customer) {
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();

            return true;
        }catch (Exception e) {
            System.err.println(e);

        }
        return false;
    }

    @Override
    public Optional<List<Receipt>> getReceipts() {
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            String jpql = "SELECT r FROM  Receipt r ";

            List<Receipt> result= entityManager.createQuery(jpql, Receipt.class)
                    .getResultList();

            transaction.commit();
            return Optional.of(result) ;

        }catch (Exception e) {
            System.err.println(e);

        }
        return null;
    }

    @Override
    public void deleteReceipt(int id) {
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            String jpql = "DELETE  FROM Receipt r WHERE r.id = :id ";

            entityManager.createQuery(jpql)
                    .setParameter("id", id)
                    .executeUpdate();

            transaction.commit();
        }catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public Optional<List<Manufacturer>> getManufacturers() {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            String jpql = "SELECT m FROM Manufacturer m ";
            var result = entityManager.createQuery(jpql, Manufacturer.class)
                            .getResultList();


            transaction.commit();
            return Optional.of(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean saveProduct(Product product) {
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.persist(product);


            transaction.commit();
            return true;
        }catch (Exception e) {
            System.err.println(e);
        }

        return false;
    }


}


