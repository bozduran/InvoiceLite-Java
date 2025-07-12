package org.bozntouran.gui;

import org.bozntouran.dao.ReceiptDao;
import org.bozntouran.dao.ReceiptDaoImpl;
import org.bozntouran.entities.Receipt;
import org.bozntouran.manager.Language;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ReceiptsInfoPanel extends JPanel {

    private JTextField fileNameTextField;
    private JTextField amountTextField;
    private JTextField dateTextField;
    private int id; // will make it later for update
    private ReceiptDao receiptDao;

    public ReceiptsInfoPanel() {
        setLayout(new GridLayout(4, 2));
        this.receiptDao = ReceiptDaoImpl.getInstance();

        add(new JLabel(Language.getInstance().getMessage("receipt.name")));
        fileNameTextField = new JTextField();
        add(fileNameTextField);
        add(new JLabel(Language.getInstance().getMessage("total.price")));
        amountTextField = new JTextField();
        add(amountTextField);
        add(new JLabel(Language.getInstance().getMessage("receipt.date")));
        dateTextField = new JTextField();
        add(dateTextField);


    }

    public void setReceipt(Receipt receipt) {
        id = receipt.getId();
        dateTextField.setText(receipt.getDate().toString());
        fileNameTextField.setText(receipt.getFilename());
        amountTextField.setText(Double.toString(receipt.getPrice()));
    }

    public void deleteReceipt() {
        receiptDao.delete(id);
        try {
            File file = new File("receipts_pdf_folder/" + fileNameTextField.getText());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
