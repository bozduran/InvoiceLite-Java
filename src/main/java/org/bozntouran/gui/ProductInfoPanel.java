package org.bozntouran.gui;

import org.bozntouran.dao.ManufacturerDao;
import org.bozntouran.dao.ManufacturerDaoImpl;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.manager.Language;

import javax.swing.*;
import java.util.ArrayList;

public class ProductInfoPanel extends JPanel {

    private ManufacturerDao manufacturerDao;

    public ProductInfoPanel() {

        add(new JLabel(Language.getInstance().getMessage("product.name")));
        add(new JLabel(Language.getInstance().getMessage("product.description")));
        add(new JLabel(Language.getInstance().getMessage("product.manufacturer")));

        manufacturerDao = ManufacturerDaoImpl.getInstance();

        String[] manufacturerNames = manufacturerDao.getManufacturers()
                .orElseGet(ArrayList<Manufacturer>::new)
                .stream()
                .map(Manufacturer::getName)
                .toArray(String[]::new);
        JComboBox<String> manufacturerComboBox = new JComboBox<>(manufacturerNames);
        add(manufacturerComboBox);
        add(new JLabel(Language.getInstance().getMessage("product.barcode")));
        add(new JLabel(Language.getInstance().getMessage("product.price")));
        add(new JLabel(Language.getInstance().getMessage("product.quantity")));
    }
}
