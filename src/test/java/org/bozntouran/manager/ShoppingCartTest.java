package org.bozntouran.manager;

import org.bozntouran.dto.CartItem;
import org.bozntouran.entities.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ShoppingCartTest {

    ShoppingCart shoppingCart = new ShoppingCart();
/*    @BeforeEach
    void setUp(){
        shoppingCart = new ShoppingCart();
    }

    @AfterEach
    void tearDown(){
        shoppingCart = null;
    }*/

    private static Stream<Arguments> calculateTotalPriceAndQuantity() {

        HashMap<Integer, CartItem> map1 = new HashMap<>();
        map1.put(1, CartItem.builder().price(100).quantity(1).build());
        map1.put(2, CartItem.builder().price(200).quantity(1).build());

        HashMap<Integer, CartItem> map2 = new HashMap<>();
        map2.put(1, CartItem.builder().price(50).quantity(1).build());
        map2.put(2, CartItem.builder().price(200).quantity(5).build());

        return Stream.of(
                Arguments.of(map1, 300, 2),
                Arguments.of(map2, 1050, 6)

        );
    }

    @Test
    @DisplayName("Add one product and chek size if equals to 1.")
    void addToCart_One_Product() {

        Product product = new Product();

        shoppingCart.addToCart(product);

        assertEquals(1, shoppingCart.getCartItems().size(), () -> "This should have been one");

    }

    @Test
    void decrementCartItemQuantity() {
        Product product = new Product();
        product.setId(1);
        shoppingCart.addToCart(product);
        shoppingCart.incrementCartItemQuantity(1);
        var cartItems = shoppingCart.getCartItems();

        assertEquals(2, cartItems.get(1).getQuantity(), () -> "This should have been two");

        shoppingCart.decrementCartItemQuantity(1);
        assertEquals(1, cartItems.get(1).getQuantity(), () -> "This should have been one");

    }

    @Test
    void decrementCartItemQuantity_For_Non_Existent_Product_Whene_The_Cart_Is_Empty() {
        Product product = new Product();
        product.setId(1);

        assertEquals(0, shoppingCart.getCartItems().size());
    }

    @Test
    void decrementCartItemQuantity_For_Non_Existent_Product_Whene_The_Cart_Has_Items() {
        Product product = Product.builder().id(1).build();
        Product product2 = Product.builder().id(2).build();
        Product product3 = Product.builder().id(3).build();
        shoppingCart.addToCart(product);
        shoppingCart.addToCart(product2);
        assertEquals(2, shoppingCart.getCartItems().size(), "Cart size should have been 2");
        shoppingCart.decrementCartItemQuantity(3);
        assertEquals(2, shoppingCart.getCartItems().size()
                , "Cart size should have been 2 after trying to decrease cart item");
    }

    @Test
    void incrementCartItemQuantity() {
        Product product = Product.builder().id(1).build();
        shoppingCart.addToCart(product);
        shoppingCart.incrementCartItemQuantity(1);
        assertEquals(2, shoppingCart.getCartItems().get(1).getQuantity(), () -> "This should have been 2");

    }

    @Test
    void incrementCartItemQuantity_To_Non_Existent_Product_When_The_Cart_Is_Empty() {
        shoppingCart.incrementCartItemQuantity(1);
        assertEquals(0, shoppingCart.getCartItems().size());

    }

    @Test
    void incrementCartItemQuantity_For_Non_Existent_Product_Whne_The_Cart_Has_Items() {
        Product product = Product.builder().id(1).build();
        Product product2 = Product.builder().id(2).build();
        Product product3 = Product.builder().id(3).build();
        shoppingCart.addToCart(product);
        shoppingCart.addToCart(product2);
        shoppingCart.addToCart(product3);
        shoppingCart.incrementCartItemQuantity(12);
        assertEquals(3, shoppingCart.getCartItems().size());
        for (var temp : shoppingCart.getCartItems().values()) {
            assertEquals(1, temp.getQuantity(), () -> "This should have been 1");
        }

    }

    @Test
    void getDataVector() {
        shoppingCart.addToCart(Product.builder().id(1).build());
        assertNotEquals(null, shoppingCart.getDataVector());
        assertEquals(1, shoppingCart.getDataVector().length, () -> "This should have been 1");
    }

    @Test
    void getDataVector_Empty_Shopping_Cart() {
        assertNotEquals(null, shoppingCart.getDataVector());
    }

    @Test
    void removeItem() {
        shoppingCart.addToCart(Product.builder().id(1).build());
        shoppingCart.removeItem(1);
        assertEquals(0, shoppingCart.getCartItems().size(), "This should have been 0");
    }

    @Test
    void getCartItems() {
        assertNotEquals(null, shoppingCart.getCartItems());
    }

    @Test
    void getTotalPrice() {
        assertEquals(0, shoppingCart.getTotalPrice(), () -> "This should have been 0");

    }

    @Test
    void getTotalQuantity() {
        assertEquals(0, shoppingCart.getTotalQuantity(), () -> "This should have been 0");
    }

    @Test
    void setCartItems() {
        HashMap<Integer, CartItem> map = new HashMap<>();
        Product product = Product.builder().id(1).build();
        shoppingCart.addToCart(product);
        assertEquals(1, shoppingCart.getCartItems().size());
        shoppingCart.setCartItems(map);
        assertEquals(map, shoppingCart.getCartItems());

    }

    @Test
    void setTotalPrice() {
        double price = 123.0;
        assertEquals(0, shoppingCart.getTotalPrice(), "This should have been 0");
        shoppingCart.setTotalPrice(price);
        assertEquals(price, shoppingCart.getTotalPrice(), () -> "This should have been" + price);
    }

    @Test
    void setTotalQuantity() {
        int quantity = 123;
        assertEquals(0, shoppingCart.getTotalQuantity(), () -> "This should have been 0");
        shoppingCart.setTotalQuantity(quantity);
        assertEquals(quantity, shoppingCart.getTotalQuantity(), () -> "This should have been" + quantity);
    }

    @ParameterizedTest
    @MethodSource
    void calculateTotalPriceAndQuantity(HashMap<Integer, CartItem> map,
                                        float expectedPrice,
                                        int expectedQuantity) {
        shoppingCart.setCartItems(map);
        shoppingCart.calculateTotalPriceAndQuantity();
        assertEquals(expectedPrice, shoppingCart.getTotalPrice(),
                () -> "This should have been" + expectedPrice);
        assertEquals(expectedQuantity, shoppingCart.getTotalQuantity()
                , () -> "This should have been" + expectedQuantity);

    }
}