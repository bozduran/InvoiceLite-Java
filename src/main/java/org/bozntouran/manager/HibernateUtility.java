package org.bozntouran.manager;

import lombok.extern.log4j.Log4j2;
import org.bozntouran.entities.Customer;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.entities.Product;
import org.bozntouran.entities.Receipt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Log4j2
public class HibernateUtility {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        log.info("Create SessionFactory");
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .build();

        try {

            Metadata metadata = new MetadataSources(registry).addAnnotatedClasses(
                            Customer.class, Product.class, Manufacturer.class, Receipt.class
                    )
                    .getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();

        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            log.error("SessionFactory creation failed", e);
            throw new ExceptionInInitializerError();
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
