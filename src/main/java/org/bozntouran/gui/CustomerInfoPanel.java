package org.bozntouran.gui;

import org.bozntouran.dao.CustomerDao;
import org.bozntouran.dao.CustomerDaoImpl;
import org.bozntouran.entities.Customer;
import org.bozntouran.manager.Language;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CustomerInfoPanel extends JPanel {


    private JTextField nameTextField;
    private JTextField cellPhoneText;
    private JTextField afm;

    private CustomerDao customerDao;
    public CustomerInfoPanel() {
        setLayout(new GridLayout(4, 2));
        String[] options = {Language.getInstance().getMessage("receipt"),
                Language.getInstance().getMessage("invoice")};

        customerDao = CustomerDaoImpl.getInstance();


        JComboBox<String> comboBox = new JComboBox<>(options);
        nameTextField = new JTextField();
        cellPhoneText = new JTextField();
        afm = new JTextField();

        afm.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (afm.getText().length() >= 6) {
                    retrieveCustomer(Integer.valueOf(afm.getText()));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        add(new JLabel(Language.getInstance().getMessage("receipt.kind")));
        add(comboBox);
        add(new JLabel(Language.getInstance().getMessage("name")));
        add(nameTextField);
        add(new JLabel(Language.getInstance().getMessage("phone.number")));
        add(cellPhoneText);
        add(new JLabel(Language.getInstance().getMessage("tax.identification.number")));


        add(afm);
    }

    private void retrieveCustomer(Integer afmToRetrieve) {
        Customer customer = customerDao.getCustomerByAfm(afmToRetrieve);

        if (customer != null) {
            nameTextField.setText(customer.getName());
            cellPhoneText.setText(String.valueOf(customer.getPhoneNumber()));
        } else {
            nameTextField.setText(Language.getInstance().getMessage("customer.dont.exist "));
        }

    }
}
