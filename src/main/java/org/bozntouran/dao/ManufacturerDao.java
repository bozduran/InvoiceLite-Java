package org.bozntouran.dao;

import org.bozntouran.entities.Manufacturer;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {
    Optional<List<Manufacturer>> getManufacturers();

    void setSessionFactory(SessionFactory sessionFactory);

    boolean save(Manufacturer manufacturer);
}
