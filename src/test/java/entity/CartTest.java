package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @Test
    public void testAddItemNewAndExisting() {
        Cart cart = new Cart();
        cart.addItem("url1", "Item 1", 5.0, 2);
        cart.addItem("url1", "Item 1", 5.0, 3);

        CartItem item = cart.getItemByUrl("url1");
        assertNotNull(item);
        assertEquals(5, item.getQuantity());
        assertEquals(25.0, cart.getTotal(), 0.0001);
    }

    @Test
    public void testRemoveItemDecreasesQuantityAndRemovesWhenZero() {
        Cart cart = new Cart();
        cart.addItem("url1", "Item 1", 5.0, 3);

        cart.removeItem("url1", 1);
        CartItem item = cart.getItemByUrl("url1");
        assertNotNull(item);
        assertEquals(2, item.getQuantity());

        cart.removeItem("url1", 2);
        assertNull(cart.getItemByUrl("url1"));
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testGetTotal() {
        Cart cart = new Cart();
        cart.addItem("url1", "Item 1", 5.0, 2);
        cart.addItem("url2", "Item 2", 3.0, 4);

        assertEquals(5.0 * 2 + 3.0 * 4, cart.getTotal(), 0.0001);
    }

    @Test
    public void testIsEmpty() {
        Cart cart = new Cart();
        assertTrue(cart.isEmpty());
        cart.addItem("url1", "Item 1", 5.0, 1);
        assertFalse(cart.isEmpty());
    }
}
