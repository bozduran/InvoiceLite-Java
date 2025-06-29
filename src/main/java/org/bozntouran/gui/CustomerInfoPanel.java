package org.bozntouran.gui;

import org.bozntouran.dao.CustomerDao;
import org.bozntouran.dao.CustomerDaoImpl;
import org.bozntouran.entities.Customer;

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

        String[] options = {"Απόδιξει", "Τιμολόγιο"};

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

        add(new JLabel("Είδος απόδιξεις"));
        add(comboBox);
        add(new JLabel("Όνομα"));
        add(nameTextField);
        add(new JLabel("Τηλέφωνο"));
        add(cellPhoneText);
        add(new JLabel("ΑΦΜ"));


        add(afm);
    }

    private void retrieveCustomer(Integer afmToRetrieve) {
        Customer customer = customerDao.getCustomerByAfm(afmToRetrieve);

        if (customer != null) {
            nameTextField.setText(customer.getName());
            cellPhoneText.setText(String.valueOf(customer.getPhoneNumber()));
        } else {
            nameTextField.setText("Customer doesnt exist in database");
        }

    }
}
