package org.bozntouran.gui;

import org.bozntouran.dao.ReceiptDao;
import org.bozntouran.dao.ReceiptDaoImpl;
import org.bozntouran.entities.Receipt;
import org.bozntouran.manager.Language;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowReceiptsPanel extends JPanel {

    private final ReceiptDao              receiptDao;
    private       ReceiptsInfoPanel       receiptsInfoPanel;
    private       Optional<List<Receipt>> receipts;

    private JList<Receipt> receiptJList;
    private JScrollPane    listScroller;

    public ShowReceiptsPanel() {
        this.receiptDao = ReceiptDaoImpl.getInstance();
        setLayout(new GridLayout(3, 1));


        this.receipts = receiptDao.getReceipts();

        if (this.receipts.isPresent()) {
            this.receiptJList = new JList<>(this.receipts.get().toArray(new Receipt[0]));
            this.receiptJList.addListSelectionListener(e -> {
                showReceipt(e.getFirstIndex());
            });
            this.receiptJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

            this.receiptJList.setVisibleRowCount(-1);

            this.listScroller = new JScrollPane(this.receiptJList);
            this.listScroller.setPreferredSize(new Dimension(250, 80));
            add(this.listScroller);
        }

        this.receiptsInfoPanel = new ReceiptsInfoPanel();

        add(this.receiptsInfoPanel);

        JButton deleteButton = new JButton(Language.getInstance().getMessage("delete.receipt"));
        deleteButton.addActionListener(e -> {
            deleteReceipt();
        });
        add(deleteButton);


    }

    public void deleteReceipt() {
        this.receiptsInfoPanel.deleteReceipt();
        // if it has data return data or return an empty arraylist
        this.receiptJList.setListData(this.receipts.orElseGet(ArrayList::new).toArray(new Receipt[0]));
        this.listScroller.repaint();
    }

    private void showReceipt(int firstIndex) {
        if (this.receipts.isPresent()) {
            if (this.receipts.get().size() <= firstIndex) {
                firstIndex = 0;
            }
        }
        this.receiptsInfoPanel.setReceipt(this.receipts.orElseGet(ArrayList::new).get(firstIndex));
    }

}
