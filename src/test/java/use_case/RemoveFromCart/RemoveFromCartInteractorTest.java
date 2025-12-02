package use_case.RemoveFromCart;

import entity.Cart;
import entity.CartItem;
import org.junit.jupiter.api.Test;
import use_case.Cart.CartDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveFromCartInteractorTest {

    private static class InMemoryCartDataAccess implements CartDataAccessInterface {
        private final Map<String, Cart> carts = new HashMap<>();

        @Override
        public Cart getCart(String username) {
            return carts.computeIfAbsent(username, u -> new Cart());
        }

        @Override
        public void saveCart(String username, Cart cart) {
            carts.put(username, cart);
        }
    }

    private static class TestRemovePresenter implements RemoveFromCartOutputBoundary {
        boolean success;
        RemoveFromCartOutputData lastSuccessData;
        String lastErrorMessage;

        @Override
        public void prepareSuccessView(RemoveFromCartOutputData outputData) {
            success = true;
            lastSuccessData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            success = false;
            lastErrorMessage = errorMessage;
        }
    }

    @Test
    public void testRemoveSomeQuantityKeepsItem() {
        InMemoryCartDataAccess cartDAO = new InMemoryCartDataAccess();
        TestRemovePresenter presenter = new TestRemovePresenter();

        Cart cart = cartDAO.getCart("user1");
        cart.addItem("url1", "Test Product", 10.0, 5);
        cartDAO.saveCart("user1", cart);

        RemoveFromCartInteractor interactor =
                new RemoveFromCartInteractor(cartDAO, presenter);

        RemoveFromCartInputData input =
                new RemoveFromCartInputData("user1", "url1", 2);

        interactor.execute(input);

        assertTrue(presenter.success);
        Cart updatedCart = cartDAO.getCart("user1");
        CartItem item = updatedCart.getItemByUrl("url1");
        assertNotNull(item);
        assertEquals(3, item.getQuantity());
    }

    @Test
    public void testRemoveExactlyAllRemovesItem() {
        InMemoryCartDataAccess cartDAO = new InMemoryCartDataAccess();
        TestRemovePresenter presenter = new TestRemovePresenter();

        Cart cart = cartDAO.getCart("user1");
        cart.addItem("url1", "Test Product", 10.0, 3);
        cartDAO.saveCart("user1", cart);

        RemoveFromCartInteractor interactor =
                new RemoveFromCartInteractor(cartDAO, presenter);

        RemoveFromCartInputData input =
                new RemoveFromCartInputData("user1", "url1", 3);

        interactor.execute(input);

        assertTrue(presenter.success);
        Cart updatedCart = cartDAO.getCart("user1");
        CartItem item = updatedCart.getItemByUrl("url1");
        assertNull(item);
        assertTrue(updatedCart.isEmpty());
    }

    @Test
    public void testRemoveMoreThanExistsFailsAndDoesNotChangeCart() {
        InMemoryCartDataAccess cartDAO = new InMemoryCartDataAccess();
        TestRemovePresenter presenter = new TestRemovePresenter();

        Cart cart = cartDAO.getCart("user1");
        cart.addItem("url1", "Test Product", 10.0, 2);
        cartDAO.saveCart("user1", cart);

        RemoveFromCartInteractor interactor =
                new RemoveFromCartInteractor(cartDAO, presenter);

        RemoveFromCartInputData input =
                new RemoveFromCartInputData("user1", "url1", 5);

        interactor.execute(input);

        assertFalse(presenter.success);
        Cart updatedCart = cartDAO.getCart("user1");
        CartItem item = updatedCart.getItemByUrl("url1");
        assertNotNull(item);
        assertEquals(2, item.getQuantity());
    }

    @Test
    public void testRemoveWithNonPositiveQuantityFails() {
        InMemoryCartDataAccess cartDAO = new InMemoryCartDataAccess();
        TestRemovePresenter presenter = new TestRemovePresenter();

        Cart cart = cartDAO.getCart("user1");
        cart.addItem("url1", "Test Product", 10.0, 2);
        cartDAO.saveCart("user1", cart);

        RemoveFromCartInteractor interactor =
                new RemoveFromCartInteractor(cartDAO, presenter);

        RemoveFromCartInputData input =
                new RemoveFromCartInputData("user1", "url1", 0);

        interactor.execute(input);

        assertFalse(presenter.success);
        Cart updatedCart = cartDAO.getCart("user1");
        CartItem item = updatedCart.getItemByUrl("url1");
        assertNotNull(item);
        assertEquals(2, item.getQuantity());
    }
}
