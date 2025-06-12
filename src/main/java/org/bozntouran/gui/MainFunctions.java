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

    public MainFunctions(HeaderPanel headerPanel , ContentPanel contentPanel) {

        this.contentPanel = contentPanel;

        setLayout(new GridLayout(3, 3));
        JButton newReceipt = new JButton("Καινούρια απόδιξει");
        newReceipt.addActionListener(e-> newReceipt() );
        this.add( newReceipt);

        JButton newCustomer = new JButton("Καινούριος πελάτης");
        newCustomer.addActionListener(e -> newClient());
        this.add(newCustomer);

        JButton receipts = new JButton("Αποδίξεις");
        receipts.addActionListener(e -> showAllReceipts());
        this.add(receipts);

        JButton addProducts = new JButton("Πρόθεσε νέο προιόν");
        addProducts.addActionListener(e -> addNewPoduct());
        this.add(addProducts);
        this.add(new JButton("5"));
        this.add(new JButton("6"));
        this.add(new JButton("7"));
        this.add(new JButton("8"));
        this.add(new JButton("9"));

    }

    public void showAllReceipts() {
        contentPanel.addNewCard(new ShowReceiptsPanel() ,"ShowReceipts");
        contentPanel.showCard("ShowReceipts");
    }

    public void newReceipt(){
        contentPanel.addNewCard(new ReceiptsPanel(),"Receipt");
        contentPanel.showCard("Receipt");
    }


    public void newClient() {
        contentPanel.addNewCard(new NewClientPanel() ,"NewClient");
        contentPanel.showCard("NewClient");
    }

    private void addNewPoduct() {
        this.contentPanel.addNewCard(new NewProduct() ,"NewProduct");
        this.contentPanel.showCard("NewProduct");
    }

    private void deleteReceipt() {
    }


}
