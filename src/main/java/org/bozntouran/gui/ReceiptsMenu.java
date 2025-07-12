package org.bozntouran.gui;

import org.bozntouran.manager.Language;

import javax.swing.*;

public class ReceiptsMenu extends JMenu {

    private ContentPanel contentPanel;

    public ReceiptsMenu(ContentPanel contentPanel) {
        // get the content panel
        this.contentPanel = contentPanel;

        setText(Language.getInstance().getMessage("receipts"));
        // add the rest of the menu
        JMenuItem newReceipt = new JMenuItem(Language.getInstance().getMessage("new.receipt"));

        newReceipt.addActionListener(e -> createNewReceiptTab());

        JMenuItem deleteReceipt = new JMenuItem(Language.getInstance().getMessage("delete.receipt"));


        add(deleteReceipt);
        add(newReceipt);

    }

    public void createNewReceiptTab() {
        this.contentPanel.addNewCard(new ReceiptsPanel(), "Receipt");
        this.contentPanel.showCard("Receipt");
    }
}
