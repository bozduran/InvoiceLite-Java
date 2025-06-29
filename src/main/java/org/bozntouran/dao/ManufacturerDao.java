package org.bozntouran.dao;

import org.bozntouran.entities.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {
    Optional<List<Manufacturer>> getManufacturers();
}
