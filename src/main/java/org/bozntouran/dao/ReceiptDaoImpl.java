package org.bozntouran.dao;

import lombok.extern.log4j.Log4j2;
import org.bozntouran.entities.Receipt;
import org.bozntouran.manager.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;


@Log4j2
public class ReceiptDaoImpl implements ReceiptDao {

    private static ReceiptDao     instance;
    private        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();

    private ReceiptDaoImpl() {

    }

    public static ReceiptDao getInstance() {
        if (instance == null) {
            instance = new ReceiptDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean save(Receipt receipt) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(receipt);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error(e);
            return false;
        }

    }

    @Override
    public boolean update(Receipt receipt) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.find(Receipt.class, id));
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error(e);
            return false;
        }

    }

    @Override
    public Optional<List<Receipt>> getReceipts() {

        try (Session session = sessionFactory.openSession()) {
            List<Receipt> result =
                    session.createQuery("SELECT r FROM  Receipt r ", Receipt.class).getResultList();

            return Optional.of(result);

        } catch (Exception e) {
            log.error("Failed to obtain session", e);

            return Optional.empty();
        }

    }

    @Override
    public Optional<Receipt> getReceipt(int id) {
        return Optional.empty();
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
