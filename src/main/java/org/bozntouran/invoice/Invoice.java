package org.bozntouran.invoice;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.bozntouran.dto.CartItem;

import java.time.LocalDateTime;
import java.util.HashMap;

public interface Invoice {

    void createInvoice();

    void addCreatorInformation(PDDocumentInformation information);

    void addReceiptContent(PDPageContentStream contentStream);

    HashMap<Integer, CartItem> getCartItems();

    void setCartItems(HashMap<Integer, CartItem> cartItems);

    void setTotalPrice(double totalPrice);

    void setTotalQuantity(int totalQuantity);

    String getFilename();

    LocalDateTime getReceiptDate();
}
