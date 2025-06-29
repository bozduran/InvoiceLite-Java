package org.bozntouran.dao;

import org.bozntouran.entities.Customer;

public interface CustomerDao {

    Customer getCustomerByAfm(int afm);

    boolean save(Customer customer);

}
