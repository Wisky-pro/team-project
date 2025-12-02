package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemTest {

    @Test
    public void testConstructorAndGetters() {
        CartItem item = new CartItem("url1", "Item 1", 5.0, 2);
        assertEquals("url1", item.getProductUrl());
        assertEquals("Item 1", item.getName());
        assertEquals(5.0, item.getPrice(), 0.0001);
        assertEquals(2, item.getQuantity());
    }

    @Test
    public void testIncreaseQuantity() {
        CartItem item = new CartItem("url1", "Item 1", 5.0, 2);
        item.increaseQuantity(3);
        assertEquals(5, item.getQuantity());
    }

    @Test
    public void testDecreaseQuantity() {
        CartItem item = new CartItem("url1", "Item 1", 5.0, 5);
        item.decreaseQuantity(2);
        assertEquals(3, item.getQuantity());
    }
}
