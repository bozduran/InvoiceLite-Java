package org.bozntouran.invoice;

import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.bozntouran.dto.CartItem;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@Log4j2
public class SimpleReceipt implements Invoice {

    private String filename;
    private HashMap<Integer, CartItem> cartItems;
    private double totalPrice;
    private int totalQuantity;
    private LocalDateTime receiptDate;


    @Override
    public void createInvoice() {
        PDDocument document = new PDDocument();

        receiptDate = LocalDateTime.now();

        String date = String.valueOf(receiptDate);

        filename = date.replaceAll("[ .:]", "-") + ".pdf";
        log.info("Generating receipt file with name: {}", filename);
        PDPage first_page = new PDPage();

        PDDocumentInformation information = document.getDocumentInformation();
        addCreatorInformation(information);

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, first_page);
            PDType0Font font = PDType0Font.load(document, new File("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"));
            contentStream.setFont(font, 10);
            addReceiptContent(contentStream);
            contentStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        document.addPage(first_page);
        try {
            ;
            document.save("receipts_pdf_folder/" + filename);

            document.close();
            log.info("Receipt successfully generated: {}", filename);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public void addReceiptContent(PDPageContentStream contentStream) {


        try {

            float pageWidth = 1000;

            contentStream.beginText();

            contentStream.newLineAtOffset(50, 700); // start from here
            contentStream.setLeading(14.5f);  // set the size of the newline to something reasonable

            receiptHeader(contentStream); // add info for store etc..

            contentStream.newLineAtOffset(0, -45);


            receiptProducts(contentStream); //print product on receipt

            contentStream.newLineAtOffset(380, -20);

            showPriceAndQuantity(contentStream); // total price and quantity


            //no more writing to the pdf after this line
            contentStream.endText();
            contentStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    private void showPriceAndQuantity(PDPageContentStream contentStream) throws IOException {
        contentStream.newLine();
        contentStream.showText("Total price:" + totalPrice);
        contentStream.newLine();
        contentStream.showText("Total quantity:" + totalQuantity);
    }

    private void receiptProducts(PDPageContentStream contentStream) throws IOException {

        float startY = 0;  // Starting Y position (from top)
        float lineHeight = 15; // Space between rows


        contentStream.newLineAtOffset(0, startY); // 0
        contentStream.showText("ID");
        contentStream.newLineAtOffset(50, 0); // 0+50-0=50
        contentStream.showText("Προϊον");
        contentStream.newLineAtOffset(200, 0);// 0+250 - =+50 = 200
        contentStream.showText("Ποσότητα");
        contentStream.newLineAtOffset(80, 0); // 300 - 250 = 50
        contentStream.showText("Τιμή");
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText("Συνολική ποσότητα");
        contentStream.newLine();
        contentStream.newLineAtOffset(-410, startY); // 0

        for (CartItem item : cartItems.values()) {

            contentStream.newLineAtOffset(0, 0);
            contentStream.showText(String.valueOf(item.getId()));
            contentStream.newLineAtOffset(50, 0);  // 50 - 0
            contentStream.showText(item.getName());
            contentStream.newLineAtOffset(200, 0);    //
            contentStream.showText(String.valueOf(item.getQuantity()));
            contentStream.newLineAtOffset(80, 0);
            contentStream.showText(String.valueOf(item.getPrice()));
            contentStream.newLineAtOffset(80, 0);
            contentStream.showText(String.valueOf(item.getPrice() * item.getQuantity()));
            contentStream.newLineAtOffset(-410, -lineHeight);

        }

        contentStream.newLine();

    }

    @Override
    public HashMap<Integer, CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public void setCartItems(HashMap<Integer, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    private void receiptHeader(PDPageContentStream contentStream) throws IOException {
        contentStream.showText("Sauron A.E");
        contentStream.newLine();
        contentStream.showText("25410 12345");
        contentStream.newLine();
        contentStream.showText("Mordor Street 9");
        contentStream.newLine();
        contentStream.showText(String.valueOf(this.receiptDate));
    }

    @Override
    public void addCreatorInformation(PDDocumentInformation information) {
        information.setAuthor("Some Author");
        information.setTitle("Invoice:" + this.filename);
        information.setCreator("Some Creator");

    }

    @Override
    public LocalDateTime getReceiptDate() {
        return receiptDate;
    }
}
