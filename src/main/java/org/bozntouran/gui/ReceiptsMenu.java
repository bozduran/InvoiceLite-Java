package org.bozntouran.gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ReceiptsMenu extends JMenu {

    private ContentPanel contentPanel;
    private JMenuItem newReceipt;

    public ReceiptsMenu(ContentPanel contentPanel){
        // get the content panel
        this.contentPanel = contentPanel;

        setText("Receipts");
        // add the rest of the menu
        newReceipt = new JMenuItem("New receipt");

        newReceipt.addActionListener(e -> createNewReceiptTab());

        JMenuItem deleteReceipt = new JMenuItem("Delete receipt");


        add(deleteReceipt);
        add(newReceipt);

    }

    public void createNewReceiptTab() {
        contentPanel.clearTabs();
        contentPanel.addNewCard(new ReceiptsPanel(),"Receipt");
        contentPanel.showCard("Receipt");
    }
}
