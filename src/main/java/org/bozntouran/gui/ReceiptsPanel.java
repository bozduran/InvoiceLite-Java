package org.bozntouran.gui;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.log4j.Log4j2;
import org.bozntouran.dao.ProductDao;
import org.bozntouran.dao.ProductDaoImpl;
import org.bozntouran.dao.ReceiptDao;
import org.bozntouran.dao.ReceiptDaoImpl;
import org.bozntouran.dto.CartItem;
import org.bozntouran.entities.Product;
import org.bozntouran.entities.Receipt;
import org.bozntouran.invoice.Invoice;
import org.bozntouran.invoice.SimpleReceipt;
import org.bozntouran.manager.Language;
import org.bozntouran.manager.ShoppingCart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Set;

@Log4j2
public class ReceiptsPanel extends JPanel implements KeyListener {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final Object[] columnNames;
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
        this.columnNames = new Object[]{Language.getInstance().getMessage("a.a"),
                Language.getInstance().getMessage("product.name"),
                Language.getInstance().getMessage("product.description"),
                Language.getInstance().getMessage("product.price"),
                Language.getInstance().getMessage("products.quantity.in.cart")
        };

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
        orderManagementButtons.add(new JButton(new AbstractAction(Language.getInstance().getMessage("remove.product.from.cart")) {
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

        totalPriceJLable = new JLabel(Language.getInstance().getMessage("total.price.with.space") + 0);
        totalQuantityLable = new JLabel(Language.getInstance().getMessage("total.quantity.with.space") + 0);

        JButton printInvoiceButton = new JButton(Language.getInstance().getMessage("print.receipt"));

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
        totalPriceJLable.setText(Language.getInstance().getMessage("total.price.with.space") + String.valueOf(df.format(shoppingCart.getTotalPrice())));
        totalQuantityLable.setText(Language.getInstance().getMessage("total.quantity.with.space") + String.valueOf(shoppingCart.getTotalQuantity()));
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

        Receipt receipt = new Receipt(shoppingCart.getTotalPrice(),
                invoice.getReceiptDate(),
                invoice.getFilename());


        if(!validateReceipt(receipt)){
            return;
        }

        boolean result = receiptDao.save(receipt);
        if (result){
            log.info("receipt saved successfully");
        }

    }

    public boolean validateReceipt(Receipt receipt){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Receipt>> constraintViolations = validator.validate(receipt);

        if (constraintViolations.isEmpty()){
            return true;
        }else{
            StringBuilder stringBuilder = new StringBuilder(constraintViolations.stream().map(ConstraintViolation::getMessage).toString());
            JOptionPane.showMessageDialog(this, stringBuilder, "Constraints violation", JOptionPane.WARNING_MESSAGE);
        }

        return false;
    }

    @Override
    protected boolean requestFocusInWindow(boolean temporary) {
        return super.requestFocusInWindow(temporary);
    }


}
