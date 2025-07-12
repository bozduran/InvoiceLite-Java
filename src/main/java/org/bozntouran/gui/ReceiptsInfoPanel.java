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
    private int        id; // will make it later for update
    private ReceiptDao receiptDao;

    public ReceiptsInfoPanel() {
        setLayout(new GridLayout(4, 2));
        this.receiptDao = ReceiptDaoImpl.getInstance();

        add(new JLabel(Language.getInstance().getMessage("receipt.name")));
        this.fileNameTextField = new JTextField();
        add(this.fileNameTextField);
        add(new JLabel(Language.getInstance().getMessage("total.price")));
        this.amountTextField = new JTextField();
        add(this.amountTextField);
        add(new JLabel(Language.getInstance().getMessage("receipt.date")));
        this.dateTextField = new JTextField();
        add(this.dateTextField);


    }

    public void setReceipt(Receipt receipt) {
        this.id = receipt.getId();
        this.dateTextField.setText(receipt.getDate().toString());
        this.fileNameTextField.setText(receipt.getFilename());
        this.amountTextField.setText(Double.toString(receipt.getPrice()));
    }

    public void deleteReceipt() {
        this.receiptDao.delete(id);
        try {
            File file = new File("receipts_pdf_folder/" + this.fileNameTextField.getText());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
