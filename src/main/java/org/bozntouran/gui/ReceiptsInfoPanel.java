package org.bozntouran.gui;

import org.bozntouran.entities.Receipt;
import org.bozntouran.manager.DataAccesor;
import org.bozntouran.manager.JpaDataAccess;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ReceiptsInfoPanel extends JPanel {

    private JTextField fileNameTextField;
    private JTextField amountTextField;
    private JTextField dateTextField;
    private int id; // will make it later for update
    private DataAccesor dataAccesor;

    public ReceiptsInfoPanel() {
        dataAccesor = JpaDataAccess.getInstance();
        setLayout(new GridLayout(4,2));
        add(new JLabel("'Ονομα απόδιξεις"));
        fileNameTextField = new JTextField();
        add(fileNameTextField);
        add(new JLabel("Συνολικό ποσό"));
        amountTextField = new JTextField();
        add(amountTextField);
        add(new JLabel("Ημερομηνία έκδοσής"));
        dateTextField = new JTextField();
        add(dateTextField);



    }

    public void setReceipt(Receipt receipt){
        id=receipt.getId();
        dateTextField.setText(receipt.getDate().toString());
        fileNameTextField.setText(receipt.getFilename());
        amountTextField.setText(Double.toString(receipt.getPrice()) );
    }

    public void deleteReceipt() {
        dataAccesor.deleteReceipt(id);
        try {
            File file = new File("receipts_pdf_folder/"+fileNameTextField.getText());
            if(file.exists()){
                file.delete();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
