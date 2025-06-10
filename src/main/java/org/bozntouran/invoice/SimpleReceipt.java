package org.bozntouran.invoice;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.bozntouran.dto.CartItem;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;


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

        filename = date.replaceAll("[ .:]" ,"-") + ".pdf";

        PDPage first_page = new PDPage();
        System.out.println(first_page.getMediaBox().getHeight());
        System.out.println(first_page.getMediaBox().getWidth());

        PDDocumentInformation information = document.getDocumentInformation();
        addCreatorInformation(information);
        try{
            PDPageContentStream contentStream = new PDPageContentStream(document, first_page);
            addReceiptContent(contentStream);
            contentStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }



        document.addPage(first_page);
        try{
            document.save("receipts_pdf_folder/"+ filename);
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void addReceiptContent(PDPageContentStream contentStream) {


        try {


            float pageWidth = 1000;


            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10); // font and size

            contentStream.newLineAtOffset(50, 700); // start from here
            contentStream.setLeading(14.5f);  // set the size of the newline to something reasonable

            receiptHeader(contentStream); // add info for store etc..

            contentStream.newLineAtOffset(0, -45 );


            receiptProducts(contentStream); //print product on receipt

            contentStream.newLineAtOffset(380 , -20);

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

        // Column positions (adjust as needed)
        float[] colX = {
                0,        // ID
                50,   // Name
                250,  // Quantity
                300,  // Price
                380   // Total
        };

        contentStream.newLineAtOffset(colX[0], startY); // 0
        contentStream.showText("ID");
        contentStream.newLineAtOffset(50, 0); // 0+50-0=50
        contentStream.showText("Product");
        contentStream.newLineAtOffset(200, 0);// 0+250 - =+50 = 200
        contentStream.showText("Qty");
        contentStream.newLineAtOffset(50, 0); // 300 - 250 = 50
        contentStream.showText("Price");
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText("Total");
        contentStream.newLine();
        contentStream.newLineAtOffset(-380, startY); // 0

        for (CartItem item : cartItems.values()){

            contentStream.newLineAtOffset(0, 0);
            contentStream.showText(String.valueOf(item.getId()));
            contentStream.newLineAtOffset(50, 0);  // 50 - 0
            contentStream.showText(item.getName());
            contentStream.newLineAtOffset(200, 0);    //
            contentStream.showText(String.valueOf(item.getQuantity()));
            contentStream.newLineAtOffset(50, 0);
            contentStream.showText(String.valueOf(item.getPrice()));
            contentStream.newLineAtOffset(80, 0);
            contentStream.showText(String.valueOf(item.getPrice() * item.getQuantity() ) );
            contentStream.newLineAtOffset(-380, -lineHeight);

        }

        contentStream.newLine();

    }

    @Override
    public void setCartItems(HashMap<Integer, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public HashMap<Integer, CartItem> getCartItems() {
        return cartItems;
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
        information.setTitle("Invoice:"+this.filename);
        information.setCreator("Some Creator");

    }

    @Override
    public LocalDateTime getReceiptDate() {
        return receiptDate;
    }
}
