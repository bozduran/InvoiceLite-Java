package org.bozntouran.gui;

import org.bozntouran.entities.Product;
import org.bozntouran.entities.Receipt;
import org.bozntouran.invoice.Invoice;
import org.bozntouran.invoice.SimpleReceipt;
import org.bozntouran.manager.DataAccesor;
import org.bozntouran.manager.JpaDataAccess;
import org.bozntouran.manager.ShoppingCart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class ReceiptsPanel extends JPanel implements KeyListener {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private StringBuilder barcodeString;
    private DefaultTableModel shoppingCartTable;
    private JPanel customerInfoPanel;
    private DataAccesor dataAccesor;
    private ShoppingCart shoppingCart;
    private Invoice invoice;

    private JLabel totalPriceJLable;
    private JLabel totalQuantityLable;



    public ReceiptsPanel() {

        setLayout(new GridLayout(3, 1));
        this.barcodeString = new StringBuilder();
        this.shoppingCart = new ShoppingCart();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        //
        dataAccesor = JpaDataAccess.getInstance();
        //dataAccesor.intialize();

        this.customerInfoPanel = new CustomerInfoPanel();
        add(customerInfoPanel);

        // show scanned products
        String[] columnNames = {"A/A", "Ονομα Προιοντος","Περιγραφή" , "Κθαρο ποσο"};
        shoppingCartTable = new DefaultTableModel(null , columnNames);

        JTable orderItemsJTable = new JTable(shoppingCartTable);
        orderItemsJTable.addKeyListener(this); //regain focus here so scanner can add new products
        add(orderItemsJTable);

        /// This is used to see the price and quantity add to the bottom

        totalPriceJLable = new JLabel("Τελική τιμή: " + String.valueOf(0));
        totalQuantityLable = new JLabel("Συνολικα προιοντα: " + String.valueOf( 0));

        JButton printInvoiceButton = new JButton("Print Invoice");

        // print invoice
        printInvoiceButton.addActionListener(e->printInvoice());

        JPanel printInvoicePanel = new JPanel(new GridLayout(1,3));
        printInvoicePanel.add(totalPriceJLable);
        printInvoicePanel.add(totalQuantityLable);
        printInvoicePanel.add(printInvoiceButton);

        focusByMouseClick();

        add(printInvoicePanel);

    }

    private void focusByMouseClick() {
        this.addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                  if (SwingUtilities.isLeftMouseButton(e)) {
                      requestFocusInWindow();
                  } else if (SwingUtilities.isRightMouseButton(e)) {
                      requestFocusInWindow();
                  }
              }
          });
    }

    public void addToShoppingCart(Product product){
        String[] columnNames = {"A/A", "Ονομα Προιοντος","Περιγραφή","Ποσό","Αριθμός προιόντων"};
        this.shoppingCartTable.setDataVector(shoppingCart.getDataVector(),columnNames);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        var typedChar = e.getKeyChar();
        this.barcodeString.append(typedChar);
        System.out.println(typedChar);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER){

            Product p = dataAccesor.getProduct(this.barcodeString.toString().trim());
            if (p != null){

                shoppingCart.addToCart(p);
                updatePriceAndQuantity();
                addToShoppingCart(p);
            }

            this.barcodeString = new StringBuilder();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            requestFocusInWindow(); // Request focus when the panel is shown
        }
    }

    public void updatePriceAndQuantity(){
        totalPriceJLable.setText("Total price: " + String.valueOf(df.format(shoppingCart.getTotalPrice() )));
        totalQuantityLable.setText("Total quantity: " + String.valueOf(shoppingCart.getTotalQuantity()));
    }

    public void printInvoice(){

        invoice = new SimpleReceipt();
        invoice.setCartItems(shoppingCart.getCartItems());
        invoice.setTotalPrice( shoppingCart.getTotalPrice());
        invoice.setTotalQuantity(shoppingCart.getTotalQuantity());
        invoice.createInvoice(); // print the pdf to a pdf file

        dataAccesor.addNewReceipt(new Receipt(shoppingCart.getTotalPrice(),
                invoice.getReceiptDate(),
                invoice.getFilename()));

    }

    @Override
    protected boolean requestFocusInWindow(boolean temporary) {
        return super.requestFocusInWindow(temporary);
    }


}
