package org.bozntouran.gui;

import org.bozntouran.dao.ProductDao;
import org.bozntouran.dao.ProductDaoImpl;
import org.bozntouran.entities.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ManageProductsPanel extends JPanel {

    private ProductDao productDao;


    public ManageProductsPanel() {
        setLayout(new GridLayout(2, 1));
        // get products data
        this.productDao = ProductDaoImpl.getInstance();
        JList<Product> productList = new JList<>(getProducts().toArray(new Product[0]));
        // add jllist
        // add jscroll pane
        JScrollPane scrollPane = new JScrollPane(productList);
        add(scrollPane);

        // add new pane for product info
        add(new ProductInfoPanel());

    }

    private List<Product> getProducts() {

        return this.productDao.getProducts().orElseGet(ArrayList<Product>::new);

    }
}
