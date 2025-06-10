package org.bozntouran.manager;

import org.bozntouran.entities.Customer;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.entities.Product;
import org.bozntouran.entities.Receipt;

import java.util.List;
import java.util.Optional;

public interface DataAccesor {


    public static DataAccesor getInstance() {
        return null;
    }

    void intialize();

    Customer getCustomer(int id);

    Product getProduct(String barcode);

    boolean addNewReceipt(Receipt receipt);

    boolean addNewCustomer(Customer customer);

    Optional<List<Receipt>> getReceipts();

    void deleteReceipt(int id);

    Optional<List<Manufacturer>> getManufacturers();

    boolean saveProduct(Product product);
}