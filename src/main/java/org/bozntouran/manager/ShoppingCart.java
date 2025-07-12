package org.bozntouran.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.bozntouran.dto.CartItem;
import org.bozntouran.entities.Product;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@Log4j2
public class ShoppingCart {

    private HashMap<Integer, CartItem> cartItems;
    private double                     totalPrice;
    private int                        totalQuantity;


    public ShoppingCart() {
        this.totalPrice = 0;
        this.totalQuantity = 0;
        this.cartItems = new HashMap<>();
    }

    public void addToCart(Product product) {

        if (this.cartItems.containsKey(product.getId()) &&
                !(this.cartItems.get(product.getId()).getQuantity() + 1 >= product.getQuantity())) {
            JOptionPane.showMessageDialog(new JOptionPane(),
                    Language.getInstance().getMessage("no.available.products"),
                    product.getName(), JOptionPane.WARNING_MESSAGE);
            return;
        }


        if (this.cartItems.containsKey(product.getId())) {
            incrementCartItemQuantity(product.getId());
        } else {
            this.cartItems.put(product.getId(), new CartItem(product));
        }

        calculateTotalPriceAndQuantity();


    }

    public void calculateTotalPriceAndQuantity() {


        this.totalQuantity = cartItems.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        this.totalPrice = cartItems.values().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();

    }

    public void decrementCartItemQuantity(int productId) {

        if (!this.cartItems.containsKey(productId)) {
            return;
        }

        if (this.cartItems.get(productId).getQuantity() == 1) {
            this.cartItems.remove(productId);
        } else {
            this.cartItems.get(productId).decreaseQuantity();
        }

    }

    public void incrementCartItemQuantity(int productId) {
        if (!this.cartItems.containsKey(productId)) {
            return;
        }
        this.cartItems.get(productId).incrementQuantity();
    }

    public Object[][] getDataVector() {
        List<Object[]> dataList = new ArrayList<>();

        for (CartItem tempCartItem : this.cartItems.values()) {
            Object[] row = {
                    tempCartItem.getId(),
                    tempCartItem.getName(),
                    tempCartItem.getDescription(),
                    tempCartItem.getPrice(),
                    tempCartItem.getQuantity()
            };
            dataList.add(row);
        }

        // Convert List<Object[]> to Object[][]
        return dataList.toArray(new Object[0][]);
    }

    public void removeItem(int productId) {
        if (!this.cartItems.containsKey(productId)) {
            return;
        }
        this.cartItems.remove(productId);
    }
}
