package org.bozntouran.gui;

import org.bozntouran.manager.Language;

import javax.swing.*;
import java.awt.*;


public class HeaderPanel extends JPanel {

    private ContentPanel contentPanel;
    private ReceiptsMenu receiptsMenu;

    public HeaderPanel(ContentPanel contentPanel) {
        setLayout(new BorderLayout());
        //setBackground();
        this.contentPanel = contentPanel;

        JMenuBar menuBar = new JMenuBar();
        JMenu editMenu = new JMenu(Language.getInstance().getMessage("help"));

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


    public JMenuItem exitMenuItem() {
        JMenuItem exitMenuItem = new JMenuItem(Language.getInstance().getMessage("exit"));
        exitMenuItem.addActionListener(e -> System.exit(1));

        return exitMenuItem;
    }

    public JMenuItem backToMainWindowMenuItem() {
        JMenuItem backToMainWindowMenuItem = new JMenuItem(Language.getInstance().getMessage("back.to.main"));

        backToMainWindowMenuItem.addActionListener(e -> {
            contentPanel.showCard("Main");
        });
        return backToMainWindowMenuItem;
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
}
