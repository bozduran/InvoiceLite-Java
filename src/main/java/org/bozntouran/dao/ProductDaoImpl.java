package org.bozntouran.dao;

import lombok.extern.log4j.Log4j2;
import org.bozntouran.entities.Product;
import org.bozntouran.manager.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;


@Log4j2
public class ProductDaoImpl implements ProductDao {

    private static ProductDaoImpl instance;
    private        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();


    private ProductDaoImpl() {
    }

    public static ProductDaoImpl getInstance() {
        if (instance == null) {
            instance = new ProductDaoImpl();
        }

        return instance;
    }


    @Override
    public Product getProductByBarcode(String barcode) {

        Product product = null;
        String jpql = "SELECT p FROM Product p WHERE p.barcode LIKE :barcode";

        try (Session session = sessionFactory.openSession()) {
            product = session.createSelectionQuery(jpql, Product.class)
                    .setParameter("barcode", barcode)
                    .getSingleResult();
        } catch (Exception e) {
            log.error("Error ", e);
        }

        return product;


    }

    @Override
    public boolean save(Product product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Failed to obtain session", e);
            return false;
        }
    }

    @Override
    public boolean updateProductQuantity(String addOrRemove, int id, int quantity) {

        String jpql = "UPDATE Product p SET p.quantity = p.quantity+ :extraQuantity WHERE p.id = :id ";
        if (addOrRemove.equals("+")) {
            jpql = "UPDATE Product p SET p.quantity = p.quantity + :extraQuantity WHERE p.id = :id ";
        } else if (addOrRemove.equals("-")) {
            jpql = "UPDATE Product p SET p.quantity = p.quantity - :extraQuantity WHERE p.id = :id ";
        }

        Transaction transaction;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(jpql)
                    .setParameter("id", id)
                    .setParameter("extraQuantity", quantity)
                    .executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            log.error("Failed to obtain session", e);
            return false;
        }


    }

    @Override
    public Optional<List<Product>> getProducts() {

        try (Session session = sessionFactory.openSession()) {
            List<Product> result =
                    session.createQuery("SELECT p FROM Product p ", Product.class).getResultList();

            return Optional.of(result);

        } catch (Exception e) {
            log.error(e);
            return Optional.empty();
        }

    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
