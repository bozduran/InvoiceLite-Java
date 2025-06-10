package org.bozntouran.gui;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Setter
@Getter
public class MainFunctions extends JPanel {

    private HeaderPanel headerPanel;
    private ContentPanel contentPanel;

    public MainFunctions(HeaderPanel headerPanel , ContentPanel contentPanel) {
        this.contentPanel = contentPanel;

        setLayout(new GridLayout(3, 3));
        JButton newReceipt = new JButton("New Receipt");
        newReceipt.addActionListener(e-> headerPanel.newReceipt() );
        this.add( newReceipt);

        JButton newCustomer = new JButton("Καινούριος πελάτης");
        newCustomer.addActionListener(e -> {headerPanel.newClient();});
        this.add(newCustomer);

        JButton receipts = new JButton("Αποδίξεις");
        receipts.addActionListener(e -> {headerPanel.showAllReceipts();});
        this.add(receipts);

        JButton addProducts = new JButton("Πρόθεσε νέο προιόν");
        addProducts.addActionListener(e -> {addNewPoduct();});
        this.add(addProducts);
        this.add(new JButton("5"));
        this.add(new JButton("6"));
        this.add(new JButton("7"));
        this.add(new JButton("8"));
        this.add(new JButton("9"));

    }

    private void addNewPoduct() {
        this.contentPanel.addNewCard(new NewProduct() ,"NewProduct");
        this.contentPanel.showCard("NewProduct");
    }

    private void deleteReceipt() {
    }


}
