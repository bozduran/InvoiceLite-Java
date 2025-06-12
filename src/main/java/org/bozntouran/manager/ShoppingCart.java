package org.bozntouran.manager;

import lombok.Getter;
import lombok.Setter;
import org.bozntouran.dto.CartItem;
import org.bozntouran.entities.Product;

import javax.swing.*;
import java.util.*;

@Setter
@Getter
public class ShoppingCart {

    private HashMap<Integer, CartItem> cartItems;
    private double totalPrice;
    private int totalQuantity;


    public ShoppingCart(){
        cartItems = new HashMap<>(10);
    }

    public void addToCart(Product product){

        if(cartItems.containsKey(product.getId()) &&
                !(cartItems.get(product.getId()).getQuantity() + 1 >= product.getQuantity()) ){
            JOptionPane.showMessageDialog(new JOptionPane(), "Δέν υπάρχουν διαθέσημα αποθέματα",product.getName(),JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(product != null ){
            if (cartItems.containsKey(product.getId())){
                incrementCartItemQuantity(product.getId());
            }else{
                cartItems.put(product.getId(),new CartItem(product) );
            }

            calculateTotalPriceAndQuantity();
        } else {
            System.out.println("Error product is null");
        }

    }

    private void calculateTotalPriceAndQuantity() {
        totalPrice = 0;
        totalQuantity = 0;

        for (CartItem tempCartItem : cartItems.values()) {
            totalQuantity = totalQuantity + tempCartItem.getQuantity();
            totalPrice = totalPrice + (tempCartItem.getQuantity() * tempCartItem.getPrice());
        }

    }

    public void decrementCartItemQuantity(int productId){
        if(cartItems.get(productId).getQuantity() == 1){
            cartItems.remove(productId);
        }else{
            cartItems.get(productId).decreaseQuantity();
        }

    }

    public void incrementCartItemQuantity(int productId){
        cartItems.get(productId).incrementQuantity();
    }

    public Object[][] getDataVector() {
        List<Object[]> dataList = new ArrayList<>();

        for (CartItem tempCartItem : cartItems.values()) {
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
        cartItems.remove(productId);
    }
}
