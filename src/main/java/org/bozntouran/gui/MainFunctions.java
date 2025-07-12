package org.bozntouran.gui;

import lombok.Getter;
import lombok.Setter;
import org.bozntouran.manager.Language;

import javax.swing.*;
import java.awt.*;

@Setter
@Getter
public class MainFunctions extends JPanel {

    private HeaderPanel  headerPanel;
    private ContentPanel contentPanel;

    public MainFunctions(HeaderPanel headerPanel, ContentPanel contentPanel) {
        this.contentPanel = contentPanel;

        setLayout(new GridLayout(5, 1));
        JButton newReceipt = new JButton(Language.getInstance().getMessage("new.receipt"));//"Καινούρια απόδιξει"
        newReceipt.addActionListener(e -> newReceipt());
        this.add(newReceipt);

        JButton newCustomer = new JButton(Language.getInstance().getMessage("new.client"));
        newCustomer.addActionListener(e -> newClient());
        this.add(newCustomer);

        JButton receipts = new JButton(Language.getInstance().getMessage("receipts"));
        receipts.addActionListener(e -> showAllReceipts());
        this.add(receipts);

        JButton addProducts = new JButton(Language.getInstance().getMessage("add.new.product"));
        addProducts.addActionListener(e -> addNewPoduct());
        this.add(addProducts);
        JButton manageProducts = new JButton(Language.getInstance().getMessage("manage.products"));
        manageProducts.addActionListener(e -> manageProducts());
        this.add(manageProducts);


    }

    private void manageProducts() {
        this.contentPanel.addNewCard(new ManageProductsPanel(), "ManageProducts");
        this.contentPanel.showCard("ManageProducts");
    }

    public void showAllReceipts() {
        this.contentPanel.addNewCard(new ShowReceiptsPanel(), "ShowReceipts");
        this.contentPanel.showCard("ShowReceipts");
    }

    public void newReceipt() {
        this.contentPanel.addNewCard(new ReceiptsPanel(), "Receipt");
        this.contentPanel.showCard("Receipt");
    }


    public void newClient() {
        this.contentPanel.addNewCard(new NewCustomerPanel(), "NewClient");
        this.contentPanel.showCard("NewClient");
    }

    private void addNewPoduct() {
        this.contentPanel.addNewCard(new NewProduct(), "NewProduct");
        this.contentPanel.showCard("NewProduct");
    }

    private void deleteReceipt() {
    }


}
