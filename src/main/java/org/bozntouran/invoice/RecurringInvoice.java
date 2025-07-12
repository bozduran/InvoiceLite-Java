package org.bozntouran.invoice;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.bozntouran.dto.CartItem;

import java.time.LocalDateTime;
import java.util.HashMap;

public class RecurringInvoice implements Invoice {

    private String                     filename;
    private HashMap<Integer, CartItem> cartItems;
    private double                     totalPrice;
    private int                        totalQuantity;


    @Override
    public void createInvoice() {

    }

    @Override
    public void addCreatorInformation(PDDocumentInformation information) {

    }

    @Override
    public void addReceiptContent(PDPageContentStream contentStream) {

    }

    @Override
    public HashMap<Integer, CartItem> getCartItems() {
        return null;
    }

    @Override
    public void setCartItems(HashMap<Integer, CartItem> cartItems) {

    }

    @Override
    public void setTotalPrice(double totalPrice) {

    }

    @Override
    public void setTotalQuantity(int totalQuantity) {

    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public LocalDateTime getReceiptDate() {
        return null;
    }
}
