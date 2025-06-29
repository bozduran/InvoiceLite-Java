package org.bozntouran.gui;

import org.bozntouran.dao.CustomerDao;
import org.bozntouran.dao.CustomerDaoImpl;
import org.bozntouran.entities.Customer;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

public class NewClientPanel extends JPanel {

    private CustomerDao customerDao;

    public NewClientPanel() {

        customerDao = CustomerDaoImpl.getInstance();

        setLayout(new GridLayout(5, 1, 25, 25));

        add(new JLabel("Όνομα"));
        JTextField nameTextField = new JTextField();
        add(nameTextField);

        add(new JLabel("ΑΦΜ"));
        JTextField afm = new JTextField();
        add(afm);
        add(new JLabel("email"));
        JTextField email = new JTextField();
        add(email);
        add(new JLabel("Τηλέφωνο"));

        JTextField phoneNumber = new JTextField();
        add(phoneNumber);

        JButton clearButton = new JButton("Καθάρισε τα παιδοία");
        add(clearButton);

        JButton submitButton = new JButton("Αποθήκευσε");
        submitButton.addActionListener(e -> {
            saveClient(nameTextField, afm, email, phoneNumber);
        });
        add(submitButton);


    }

    public void saveClient(JTextField nameTextField, JTextField afm, JTextField email, JTextField phoneNumber) {

        if (nameTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Το πεδίο με το όνομα δεν είναι σωστό", "Όνομα", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!checkIfNumeric(afm.getText())) {
            JOptionPane.showMessageDialog(this, "Το πεδίο ΑΦΜ δεν είναι σωστό", "ΑΦΜ", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (email.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Το πεδίο με το email δεν είναι σωστό", "Email", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!checkIfNumeric(phoneNumber.getText())) {
            JOptionPane.showMessageDialog(this, "Το πεδίο με το τηλέφωνο δεν είναι σωστό", "Τηλέφωνο", JOptionPane.WARNING_MESSAGE);
            return;
        }

        customerDao.save(new Customer(Integer.parseInt(afm.getText())
                , nameTextField.getText()
                , email.getText()
                , new BigInteger(phoneNumber.getText())));
    }

    public boolean checkIfNumeric(String value) {

        return !value.isEmpty() &&
                value.chars().allMatch(Character::isDigit);
    }
}
