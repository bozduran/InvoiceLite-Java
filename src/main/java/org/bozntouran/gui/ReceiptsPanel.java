package org.bozntouran.gui;

import org.bozntouran.dao.ProductDao;
import org.bozntouran.dao.ProductDaoImpl;
import org.bozntouran.dao.ReceiptDao;
import org.bozntouran.dao.ReceiptDaoImpl;
import org.bozntouran.dto.CartItem;
import org.bozntouran.entities.Product;
import org.bozntouran.entities.Receipt;
import org.bozntouran.invoice.Invoice;
import org.bozntouran.invoice.SimpleReceipt;
import org.bozntouran.manager.ShoppingCart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class ReceiptsPanel extends JPanel implements KeyListener {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final Object[] columnNames = {"A/A", "Ονομα Προιοντος", "Περιγραφή", "Ποσό", "Αριθμός προιόντων"};
    private StringBuilder barcodeString;
    private DefaultTableModel shoppingCartTable;
    private JPanel customerInfoPanel;
    private ProductDao productDao;
    private ReceiptDao receiptDao;
    private ShoppingCart shoppingCart;
    private Invoice invoice;
    private JLabel totalPriceJLable;
    private JLabel totalQuantityLable;

    public ReceiptsPanel() {
        initialize();

        setLayout(new GridLayout(3, 1));

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        // this is the customer info panel
        this.customerInfoPanel = new CustomerInfoPanel();
        add(customerInfoPanel);


        // jtable and option for cart management
        JPanel orderItemsPanel = createOrderItemPanel();

        add(orderItemsPanel);

        JPanel printInvoicePanel = summaryPanel();

        focusByMouseClick();

        add(printInvoicePanel);


    }

    public void initialize() {
        this.barcodeString = new StringBuilder();
        this.shoppingCart = new ShoppingCart();
        receiptDao = ReceiptDaoImpl.getInstance();
        productDao = ProductDaoImpl.getInstance();
    }

    private JPanel createOrderItemPanel() {

        // show scanned products
        shoppingCartTable = new DefaultTableModel(null, columnNames);

        JTable orderItemsJTable = new JTable(shoppingCartTable);
        orderItemsJTable.addKeyListener(this); //regain focus here so scanner can add new products

        // jtable and option for cart management
        JPanel orderItemsPanel = new JPanel(new GridLayout(2, 1));
        orderItemsPanel.add(new JScrollPane(orderItemsJTable));

        JPanel orderManagementButtons = createOrderManagementPanel(orderItemsJTable);
        orderItemsPanel.add(orderManagementButtons);

        return orderItemsPanel;
    }

    private JPanel createOrderManagementPanel(JTable orderItemsJTable) {
        JPanel orderManagementButtons = new JPanel(new GridLayout(1, 3));
        orderManagementButtons.add(new JButton(new AbstractAction("-") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedObject = shoppingCart.getDataVector()[orderItemsJTable.getSelectedRow()][0];
                shoppingCart.decrementCartItemQuantity(Integer.parseInt(selectedObject.toString()));
                refreshShoppingCart();

            }
        }));
        orderManagementButtons.add(new JButton(new AbstractAction("+") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedObject = shoppingCart.getDataVector()[orderItemsJTable.getSelectedRow()][0];
                shoppingCart.incrementCartItemQuantity(Integer.parseInt(selectedObject.toString()));
                refreshShoppingCart();


            }
        }));
        orderManagementButtons.add(new JButton(new AbstractAction("Αφαίρεση προιόντος") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedObject = shoppingCart.getDataVector()[orderItemsJTable.getSelectedRow()][0];
                shoppingCart.removeItem(Integer.parseInt(selectedObject.toString()));
                refreshShoppingCart();


            }
        }));

        return orderManagementButtons;
    }

    public JPanel summaryPanel() {

        /// This is used to see the price and quantity add to the bottom

        totalPriceJLable = new JLabel("Τελική τιμή: " + 0);
        totalQuantityLable = new JLabel("Συνολικα προιοντα: " + 0);

        JButton printInvoiceButton = new JButton("Print Invoice");

        // print invoice
        printInvoiceButton.addActionListener(e -> {
            if (!shoppingCart.getCartItems().isEmpty()) {
                printInvoice();
            }
        });

        JPanel printInvoicePanel = new JPanel(new GridLayout(1, 3));
        printInvoicePanel.add(totalPriceJLable);
        printInvoicePanel.add(totalQuantityLable);
        printInvoicePanel.add(printInvoiceButton);
        return printInvoicePanel;
    }

/*    private void addTestData() {
        shoppingCart.addToCart(dataAccesor.getProduct("087229758196"));
        shoppingCart.addToCart(dataAccesor.getProduct("454426743190"));
        shoppingCart.addToCart(dataAccesor.getProduct("814177540036"));
        shoppingCart.addToCart(dataAccesor.getProduct("445637750351"));
        shoppingCart.addToCart(dataAccesor.getProduct("805688256537"));
        shoppingCart.addToCart(dataAccesor.getProduct("624338181415"));
        shoppingCart.addToCart(dataAccesor.getProduct("094698177204"));
        shoppingCart.addToCart(dataAccesor.getProduct("677357207482"));
        shoppingCart.addToCart(dataAccesor.getProduct("961002799090"));
        shoppingCart.addToCart(dataAccesor.getProduct("229884239035"));
        shoppingCart.addToCart(dataAccesor.getProduct("769187181570"));
        shoppingCart.addToCart(dataAccesor.getProduct("087229758196"));

        shoppingCart.addToCart(dataAccesor.getProduct("752349453457"));
        shoppingCart.addToCart(dataAccesor.getProduct("950289558177"));
        shoppingCart.addToCart(dataAccesor.getProduct("815570758405"));
        shoppingCart.addToCart(dataAccesor.getProduct("223013462770"));
        shoppingCart.addToCart(dataAccesor.getProduct("201408554191"));
        shoppingCart.addToCart(dataAccesor.getProduct("250331593724"));
        shoppingCart.addToCart(dataAccesor.getProduct("986502051845"));
        shoppingCart.addToCart(dataAccesor.getProduct("091376362653"));
        shoppingCart.addToCart(dataAccesor.getProduct("279323822968"));
        shoppingCart.addToCart(dataAccesor.getProduct("444751927349"));
        shoppingCart.addToCart(dataAccesor.getProduct("156845626263"));
        shoppingCart.addToCart(dataAccesor.getProduct("009025109439"));
        refreshShoppingCart();

    }*/

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

    public void refreshShoppingCart() {
        requestFocusInWindow(true);
        this.shoppingCartTable.setDataVector(shoppingCart.getDataVector(), columnNames);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        var typedChar = e.getKeyChar();
        this.barcodeString.append(typedChar);
        System.out.println(typedChar);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {

            Product p = productDao.getProductByBarcode(this.barcodeString.toString().trim());
            if (p != null) {

                shoppingCart.addToCart(p);
                updatePriceAndQuantity();
                refreshShoppingCart();
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

    public void updatePriceAndQuantity() {
        totalPriceJLable.setText("Total price: " + String.valueOf(df.format(shoppingCart.getTotalPrice())));
        totalQuantityLable.setText("Total quantity: " + String.valueOf(shoppingCart.getTotalQuantity()));
    }

    public void updateQuantityInDataBase() {
        var cartItems = shoppingCart.getCartItems();

        for (CartItem tempCartItem : cartItems.values()) {
            System.out.println(tempCartItem.toString());
            productDao.updateProductQuantity("-", tempCartItem.getId(), tempCartItem.getQuantity());
        }

    }

    public void printInvoice() {
        updateQuantityInDataBase();
        invoice = new SimpleReceipt();
        invoice.setCartItems(shoppingCart.getCartItems());
        invoice.setTotalPrice(shoppingCart.getTotalPrice());
        invoice.setTotalQuantity(shoppingCart.getTotalQuantity());
        invoice.createInvoice(); // print the pdf to a pdf file

        receiptDao.save(new Receipt(shoppingCart.getTotalPrice(),
                invoice.getReceiptDate(),
                invoice.getFilename()));

    }

    @Override
    protected boolean requestFocusInWindow(boolean temporary) {
        return super.requestFocusInWindow(temporary);
    }


}
