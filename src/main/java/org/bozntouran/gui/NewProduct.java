package org.bozntouran.gui;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.log4j.Log4j2;
import org.bozntouran.dao.ManufacturerDao;
import org.bozntouran.dao.ManufacturerDaoImpl;
import org.bozntouran.dao.ProductDao;
import org.bozntouran.dao.ProductDaoImpl;
import org.bozntouran.entities.Manufacturer;
import org.bozntouran.entities.Product;
import org.bozntouran.manager.Language;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class NewProduct extends JPanel {

    private ProductDao      productDao;
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
        add(new JLabel(Language.getInstance().getMessage("product.name")));
        add(this.productName = new JTextField());
        add(new JLabel(Language.getInstance().getMessage("product.price")));
        add(this.productPrice = new JTextField());
        add(new JLabel(Language.getInstance().getMessage("product.description")));
        add(this.productDescription = new JTextField());
        add(new JLabel(Language.getInstance().getMessage("product.barcode")));
        add(this.productBarcode = new JTextField());
        add(new JLabel(Language.getInstance().getMessage("product.quantity")));
        add(this.productQuantity = new JTextField());
        add(new JLabel(Language.getInstance().getMessage("product.manufacturer")));
        String[] manufacturerNames = Arrays.stream(this.manufacturers)
                .map(Manufacturer::getName)
                .toArray(String[]::new);
        this.manufacturerComboBox = new JComboBox<>(manufacturerNames);

        add(this.manufacturerComboBox);

        JButton saveButton = new JButton(Language.getInstance().getMessage("product.save"));
        saveButton.addActionListener(e -> {
            saveProduct();
        });
        add(saveButton);

    }

    private void populateManufacturers() {
        Optional<List<Manufacturer>> result = this.manufacturerDao.getManufacturers();
        result.ifPresent(manufacturerList -> this.manufacturers = manufacturerList.toArray(new Manufacturer[0]));

    }

    private void saveProduct() {
        Manufacturer productManufacturer = null;
        for (Manufacturer manufacturer : this.manufacturers) {
            if (manufacturer.getName().equals(this.manufacturerComboBox.getSelectedItem())) {
                productManufacturer = manufacturer;
            }
        }

        if (!checkIfNumeric(this.productQuantity.getText())) {
            JOptionPane.showMessageDialog(this, Language.getInstance().getMessage("error.product.quantity"),
                    Language.getInstance().getMessage("error.product.quantity.title"), JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!checkIfNumeric(this.productPrice.getText())) {
            JOptionPane.showMessageDialog(this, Language.getInstance().getMessage("error.product.price")
                    , Language.getInstance().getMessage("error.product.price.title"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product product = new Product(
                this.productName.getText()
                , this.productDescription.getText()
                , this.productBarcode.getText()
                , Double.parseDouble(this.productPrice.getText())
                , Integer.parseInt(this.productQuantity.getText())
                , productManufacturer
        );

        if (!validateProduct(product)) {
            return;
        }

        boolean result = this.productDao.save(product);

        if (!result) {
            JOptionPane.showMessageDialog(this, Language.getInstance().getMessage("error.product.save"), Language.getInstance().getMessage("save"), JOptionPane.WARNING_MESSAGE);
        } else {
            log.info("product added successfully");
        }
    }

    public boolean validateProduct(Product product) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);


        if (constraintViolations.isEmpty()) {
            return true;
        } else {
            StringBuilder stringBuilder = new StringBuilder(
                    constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining()));
            JOptionPane.showMessageDialog(this, stringBuilder,
                    Language.getInstance().getMessage("constraints,violation.title"), JOptionPane.WARNING_MESSAGE);
        }

        return false;
    }

    public boolean checkIfNumeric(String value) {

        return !value.isEmpty() &&
                value.chars().allMatch(Character::isDigit);
    }
}
