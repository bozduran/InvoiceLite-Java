package org.bozntouran.gui;

import org.bozntouran.dao.ManufacturerDao;
import org.bozntouran.dao.ManufacturerDaoImpl;
import org.bozntouran.dao.ProductDao;
import org.bozntouran.dao.ProductDaoImpl;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.entities.Product;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NewProduct extends JPanel {

    private ProductDao productDao;
    private ManufacturerDao manufacturerDao;

    private JTextField productName;
    private JTextField productPrice;
    private JTextField productDescription;
    private JTextField productBarcode;
    private JTextField productQuantity;


    private JComboBox<String> manufacturerComboBox;

    private Manufacturer[] manufacturers;

    public NewProduct() {
        this.productDao = ProductDaoImpl.getInstance();
        this.manufacturerDao = ManufacturerDaoImpl.getInstance();
        populateManufacturers();

        setLayout(new GridLayout(7, 2));
        add(new JLabel("Όνομα προϊόντος"));
        add(this.productName = new JTextField());
        add(new JLabel("Τιμή προϊόντος"));
        add(this.productPrice = new JTextField());
        add(new JLabel("Περιγραφή προϊόντος"));
        add(this.productDescription = new JTextField());
        add(new JLabel("Barcode προϊόντος"));
        add(this.productBarcode = new JTextField());
        add(new JLabel("Διαθέσιμη ποσότητα προϊόντος"));
        add(this.productQuantity = new JTextField());
        add(new JLabel("Κατασκευαστής"));
        String[] manufacturerNames = Arrays.stream(manufacturers)
                .map(Manufacturer::getName)
                .toArray(String[]::new);
        manufacturerComboBox = new JComboBox<>(manufacturerNames);

        add(manufacturerComboBox);

        JButton saveButton = new JButton("Αποθήκευση προϊόντος");
        saveButton.addActionListener(e -> {
            saveProduct();
        });
        add(saveButton);

    }

    private void populateManufacturers() {
        Optional<List<Manufacturer>> result = manufacturerDao.getManufacturers();
        result.ifPresent(manufacturerList -> manufacturers = manufacturerList.toArray(new Manufacturer[0]));

    }

    private void saveProduct() {
        Manufacturer productManufacturer = null;
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getName().equals(manufacturerComboBox.getSelectedItem())) {
                productManufacturer = manufacturer;
            }
        }


        if (productName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Το πεδίο με το όνομα δεν είναι σωστό", "Όνομα", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!checkIfNumeric(productPrice.getText())) {
            JOptionPane.showMessageDialog(this, "Το πεδίο με τη τιμή δεν είναι σωστό", "Τιμή", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (productDescription.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Το πεδίο με το περιγραφή δεν είναι σωστό", "Περιγραφή", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (productBarcode.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Το πεδίο με το barcode δεν είναι σωστό", "barcode", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!checkIfNumeric(productQuantity.getText())) {
            JOptionPane.showMessageDialog(this, "Το πεδίο με τη ποσότητα δεν είναι σωστό", "Ποσότητα", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product product = new Product(
                productName.getText()
                , productDescription.getText()
                , productBarcode.getText()
                , Double.parseDouble(productPrice.getText())
                , Integer.parseInt(productQuantity.getText())
                , productManufacturer
        );

        boolean result = productDao.save(product);
        System.out.println(result);
        if (!result) {
            JOptionPane.showMessageDialog(this, "Αποτυχία αποθήκευσης προιόντος", "Αποθήκευση", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean checkIfNumeric(String value) {

        return !value.isEmpty() &&
                value.chars().allMatch(Character::isDigit);
    }
}
