package org.bozntouran.gui;

import org.bozntouran.dao.ManufacturerDao;
import org.bozntouran.dao.ManufacturerDaoImpl;
import org.bozntouran.entities.Manufacturer;

import javax.swing.*;
import java.util.ArrayList;

public class ProductInfoPanel extends JPanel {

    private ManufacturerDao manufacturerDao;

    public ProductInfoPanel() {
        add(new JLabel("'Ονομα:"));
        add(new JLabel("Περιγραφή:"));
        add(new JLabel("Κατασκευαστής:"));

        manufacturerDao = ManufacturerDaoImpl.getInstance();

        String[] manufacturerNames = manufacturerDao.getManufacturers()
                .orElseGet(ArrayList<Manufacturer>::new)
                .stream()
                .map(Manufacturer::getName)
                .toArray(String[]::new);
        JComboBox<String> manufacturerComboBox = new JComboBox<>(manufacturerNames);
        add(manufacturerComboBox);
        add(new JLabel("Barcode:"));
        add(new JLabel("Τιμή:"));
        add(new JLabel("Ποσότητα:"));
    }
}
