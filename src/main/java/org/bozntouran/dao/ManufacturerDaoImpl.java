package org.bozntouran.dao;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.manager.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@Log4j2
@Setter
public class ManufacturerDaoImpl implements ManufacturerDao {

    private static ManufacturerDaoImpl instance;
    private        SessionFactory      sessionFactory = HibernateUtility.getSessionFactory();


    private ManufacturerDaoImpl() {

    }

    public static ManufacturerDaoImpl getInstance() {
        if (instance == null) {
            instance = new ManufacturerDaoImpl();
        }
        return instance;
    }

    @Override
    public Optional<List<Manufacturer>> getManufacturers() {
        try (Session session = sessionFactory.openSession()) {
            String jpql = "SELECT m FROM Manufacturer m ";


            return Optional.of(
                    session.createQuery(jpql, Manufacturer.class).getResultList());

        } catch (Exception e) {
            log.error("Failed to obtain session", e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean save(Manufacturer manufacturer) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(manufacturer);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Failed to obtain session", e);
            return false;
        }
    }
}
