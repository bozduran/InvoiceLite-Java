package org.bozntouran.dao;

import org.bozntouran.entities.Customer;
import org.hibernate.SessionFactory;

public interface CustomerDao {

    Customer getCustomerByAfm(int afm);

    boolean save(Customer customer);

    void setSessionFactory(SessionFactory sessionFactory);

}
