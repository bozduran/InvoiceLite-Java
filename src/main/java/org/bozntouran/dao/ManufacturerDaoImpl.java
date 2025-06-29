package org.bozntouran.dao;

import lombok.extern.log4j.Log4j2;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.manager.HibernateUtility;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

@Log4j2
public class ManufacturerDaoImpl implements ManufacturerDao {

    private static ManufacturerDaoImpl instance;


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
        try (Session session = HibernateUtility.getSession()) {
            String jpql = "SELECT m FROM Manufacturer m ";


            return Optional.of(
                    session.createQuery(jpql, Manufacturer.class).getResultList());

        } catch (Exception e) {
            log.error("Failed to obtain session", e);

            throw new RuntimeException(e);
        }
    }
}
