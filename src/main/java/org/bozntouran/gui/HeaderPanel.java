package org.bozntouran.gui;

import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class HeaderPanel extends JPanel {

    private ContentPanel contentPanel;
    private ReceiptsMenu receiptsMenu;

    public HeaderPanel(ContentPanel contentPanel){
        setLayout(new BorderLayout());
        //setBackground();
        this.contentPanel = contentPanel;

        JMenuBar menuBar = new JMenuBar();
        JMenu editMenu = new JMenu("Βοηθεια");

        JMenuItem exitMenuItem = exitMenuItem();

        // go back to main window
        JMenuItem backToMainWindowMenuItem = backToMainWindowMenuItem();


        editMenu.add(exitMenuItem);

        editMenu.add(backToMainWindowMenuItem);

        receiptsMenu = new ReceiptsMenu(contentPanel);
        menuBar.add(receiptsMenu);
        menuBar.add(editMenu);
        add(menuBar);




    }

    public void newReceipt(){
        receiptsMenu.createNewReceiptTab();
    }


    public void newClient() {
        contentPanel.addNewCard(new NewClientPanel() ,"NewClient");
        contentPanel.showCard("NewClient");
    }

    public JMenuItem exitMenuItem() {
        JMenuItem exitMenuItem = new JMenuItem("Έξοδος");
        exitMenuItem.addActionListener(e -> System.exit(1));

        return exitMenuItem;
    }

    public JMenuItem backToMainWindowMenuItem() {
        JMenuItem backToMainWindowMenuItem = new JMenuItem("Πίσω στην αρχική");

        backToMainWindowMenuItem.addActionListener(e -> {
            contentPanel.clearTabs();
            contentPanel.showCard("Main");
        });
        return backToMainWindowMenuItem;
    }

    public void showAllReceipts() {
        contentPanel.addNewCard(new ShowReceiptsPanel() ,"ShowReceipts");
        contentPanel.showCard("ShowReceipts");
    }
}
