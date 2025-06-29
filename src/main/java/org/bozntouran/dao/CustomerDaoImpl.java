package org.bozntouran.dao;

import lombok.extern.log4j.Log4j2;
import org.bozntouran.entities.Customer;
import org.bozntouran.manager.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Log4j2
public class CustomerDaoImpl implements CustomerDao {


    private static CustomerDaoImpl instance;

    private CustomerDaoImpl() {

    }

    public static CustomerDaoImpl getInstance() {

        if (instance == null) {
            instance = new CustomerDaoImpl();
        }

        return instance;
    }


    @Override
    public Customer getCustomerByAfm(int afm) {

        Customer customer = null;
        try (Session session = HibernateUtility.getSession()) {
            String jpql = "SELECT c FROM Customer c WHERE c.afm = :afm";
            customer = session.createSelectionQuery(jpql, Customer.class)
                    .setParameter("afm", afm)
                    .getSingleResult();

        } catch (Exception e) {
            log.error("Failed to obtain session");
        }

        return customer;
    }

    @Override
    public boolean save(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtility.getSession()) {
            transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Failed to obtain session", e);
            return false;
        }
    }
}
