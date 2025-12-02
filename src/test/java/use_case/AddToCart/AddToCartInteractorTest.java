package use_case.AddToCart;

import entity.Cart;
import entity.CartItem;
import entity.ProductData;
import org.junit.jupiter.api.Test;
import use_case.Cart.CartDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AddToCartInteractorTest {

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

    private static class FakeProductDataAccess implements ProductDataAccessInterface {
        private ProductData product;

        public void setProduct(ProductData product) {
            this.product = product;
        }

        @Override
        public ProductData getByUrl(String url) {
            return product;
        }
    }

    private static class TestAddPresenter implements AddToCartOutputBoundary {
        boolean success;
        AddToCartOutputData lastSuccessData;
        String lastErrorMessage;

        @Override
        public void prepareSuccessView(AddToCartOutputData outputData) {
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
    public void testAddToCartSuccess() {
        InMemoryCartDataAccess cartDAO = new InMemoryCartDataAccess();
        FakeProductDataAccess productDAO = new FakeProductDataAccess();
        TestAddPresenter presenter = new TestAddPresenter();

        ProductData product = new ProductData("url1", "Test Product", 10.0);
        productDAO.setProduct(product);

        AddToCartInteractor interactor =
                new AddToCartInteractor(cartDAO, productDAO, presenter);

        AddToCartInputData input =
                new AddToCartInputData("user1", "url1", 3);

        interactor.execute(input);

        assertTrue(presenter.success);
        assertNotNull(presenter.lastSuccessData);
        assertEquals("Test Product", presenter.lastSuccessData.getProductName());

        Cart cart = cartDAO.getCart("user1");
        CartItem item = cart.getItemByUrl("url1");
        assertNotNull(item);
        assertEquals(3, item.getQuantity());
        assertEquals(30.0, cart.getTotal(), 0.0001);
    }

    @Test
    public void testAddToCartFailsOnNonPositiveQuantity() {
        InMemoryCartDataAccess cartDAO = new InMemoryCartDataAccess();
        FakeProductDataAccess productDAO = new FakeProductDataAccess();
        TestAddPresenter presenter = new TestAddPresenter();

        ProductData product = new ProductData("url1", "Test Product", 10.0);
        productDAO.setProduct(product);

        AddToCartInteractor interactor =
                new AddToCartInteractor(cartDAO, productDAO, presenter);

        AddToCartInputData input =
                new AddToCartInputData("user1", "url1", 0);

        interactor.execute(input);

        assertFalse(presenter.success);
        Cart cart = cartDAO.getCart("user1");
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testAddToCartFailsWhenProductNotFound() {
        InMemoryCartDataAccess cartDAO = new InMemoryCartDataAccess();
        FakeProductDataAccess productDAO = new FakeProductDataAccess();
        TestAddPresenter presenter = new TestAddPresenter();

        productDAO.setProduct(null);

        AddToCartInteractor interactor =
                new AddToCartInteractor(cartDAO, productDAO, presenter);

        AddToCartInputData input =
                new AddToCartInputData("user1", "missing-url", 2);

        interactor.execute(input);

        assertFalse(presenter.success);
        Cart cart = cartDAO.getCart("user1");
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testAddToCartFailsOnIllegalArgumentException() {
        InMemoryCartDataAccess cartDAO = new InMemoryCartDataAccess();
        FakeProductDataAccess productDAO = new FakeProductDataAccess();
        TestAddPresenter presenter = new TestAddPresenter();

        ProductDataAccessInterface throwingDAO = new ProductDataAccessInterface() {
            @Override
            public ProductData getByUrl(String url) {
                throw new IllegalArgumentException("Invalid URL");
            }
        };

        AddToCartInteractor interactor =
                new AddToCartInteractor(cartDAO, throwingDAO, presenter);

        AddToCartInputData input =
                new AddToCartInputData("user1", "bad-url", 1);

        interactor.execute(input);

        assertFalse(presenter.success);
        assertEquals("Invalid BestBuy product URL.", presenter.lastErrorMessage);

        Cart cart = cartDAO.getCart("user1");
        assertTrue(cart.isEmpty());
    }
}
