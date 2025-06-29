package org.bozntouran.dao;

import org.bozntouran.entities.Product;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product getProductByBarcode(String barcode);

    boolean save(Product product);

    boolean updateProductQuantity(String addOrRemove, int id, int quantity);

    Optional<List<Product>> getProducts();

    void setSessionFactory(SessionFactory sessionFactory);
}
