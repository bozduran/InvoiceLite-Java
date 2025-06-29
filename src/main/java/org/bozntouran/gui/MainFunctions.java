package org.bozntouran.gui;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Setter
@Getter
public class MainFunctions extends JPanel {

    private HeaderPanel headerPanel;
    private ContentPanel contentPanel;

    public MainFunctions(HeaderPanel headerPanel, ContentPanel contentPanel) {

        this.contentPanel = contentPanel;

        setLayout(new GridLayout(5, 1));
        JButton newReceipt = new JButton("Καινούρια απόδιξει");
        newReceipt.addActionListener(e -> newReceipt());
        this.add(newReceipt);

        JButton newCustomer = new JButton("Καινούριος πελάτης");
        newCustomer.addActionListener(e -> newClient());
        this.add(newCustomer);

        JButton receipts = new JButton("Αποδίξεις");
        receipts.addActionListener(e -> showAllReceipts());
        this.add(receipts);

        JButton addProducts = new JButton("Πρόθεσε νέο προιόν");
        addProducts.addActionListener(e -> addNewPoduct());
        this.add(addProducts);
        JButton manageProducts = new JButton("Διαχήρηση προιοντον");
        manageProducts.addActionListener(e -> manageProducts());
        this.add(manageProducts);


    }

    private void manageProducts() {
        contentPanel.addNewCard(new ManageProductsPanel(), "ManageProducts");
        contentPanel.showCard("ManageProducts");
    }

    public void showAllReceipts() {
        contentPanel.addNewCard(new ShowReceiptsPanel(), "ShowReceipts");
        contentPanel.showCard("ShowReceipts");
    }

    public void newReceipt() {
        contentPanel.addNewCard(new ReceiptsPanel(), "Receipt");
        contentPanel.showCard("Receipt");
    }


    public void newClient() {
        contentPanel.addNewCard(new NewCustomerPanel(), "NewClient");
        contentPanel.showCard("NewClient");
    }

    private void addNewPoduct() {
        this.contentPanel.addNewCard(new NewProduct(), "NewProduct");
        this.contentPanel.showCard("NewProduct");
    }

    private void deleteReceipt() {
    }


}
