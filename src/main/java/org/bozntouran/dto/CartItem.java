package org.bozntouran.dto;

import lombok.Data;
import org.bozntouran.entities.Product;

@Data
public class CartItem {

    private int id;
    private String barcode;
    private String name;
    private String description;
    private double price;

    //quantity of items to be purchased
    private int quantity;

    public CartItem(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.barcode = product.getBarcode();
        this.price = product.getPrice();
        this.quantity = 1;
    }

    public void incrementQuantity(){
        this.quantity = this.quantity+1;
    }

    public void decreaseQuantity(){
        this.quantity = this.quantity-1;
    }
}
