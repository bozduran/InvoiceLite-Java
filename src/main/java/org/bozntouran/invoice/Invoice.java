package org.bozntouran.invoice;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.bozntouran.dto.CartItem;

import java.time.LocalDateTime;
import java.util.HashMap;

public interface Invoice {

    public void createInvoice();
    public void addCreatorInformation(PDDocumentInformation information);
    public void addReceiptContent(PDPageContentStream contentStream);



    public void setCartItems(HashMap<Integer, CartItem> cartItems);
    public HashMap<Integer, CartItem>  getCartItems();

    void setTotalPrice(double totalPrice);

    void setTotalQuantity(int totalQuantity);

    String getFilename();
    public LocalDateTime getReceiptDate();
}
