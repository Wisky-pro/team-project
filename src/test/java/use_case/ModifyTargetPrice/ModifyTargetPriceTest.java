package use_case.ModifyTargetPrice;

import data_access.InMemoryCartDataAccess;
import entity.Cart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifyTargetPriceTest {
    static class MockModifyTargetPricePresenter implements ModifyTargetPriceOutputBoundary {
        public boolean success;
        public String message;

        @Override
        public void prepareSuccessView(ModifyTargetPriceOutputData outputData) {
            this.message = "successfully modified target price to: " + outputData.getModifiedTargetPrice();
            this.success = true;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.message = "did not successfully update the price: " +  errorMessage;
            this.success = false;
        }
    }

    private ModifyTargetPriceInteractor interactor;
    private MockModifyTargetPricePresenter presenter;
    private InMemoryCartDataAccess cartDataAccess;

    @BeforeEach
    void setUp() {
        this.cartDataAccess = new InMemoryCartDataAccess();
        this.presenter = new MockModifyTargetPricePresenter();
        this.interactor = new ModifyTargetPriceInteractor(this.cartDataAccess, this.presenter);

        cartDataAccess.getCart("Dylan");

        Cart cart = cartDataAccess.getCart("Dylan");
        cart.addItem("url", "test", 10, 10, 10);
        cartDataAccess.saveCart("Dylan", cart);
    }

    // Here, we'll use some random strings to stand in for URLs.

    @Test
    void modifyTargetPriceWithNonExistentProductUrl() {
        String username = "Dylan";
        int price = 100;
        String productUrl = "https://www.google.com";

        ModifyTargetPriceInputData input = new ModifyTargetPriceInputData(username, productUrl, price);
        interactor.execute(input);

        Assertions.assertFalse(presenter.success);
    }

    @Test
    void modifyTargetPriceWithNegativePrice() {
        String username = "Dylan";
        int price = -30;
        String productUrl = "https://www.google.com";

        ModifyTargetPriceInputData input = new ModifyTargetPriceInputData(username, productUrl, price);
        interactor.execute(input);

        Assertions.assertFalse(presenter.success);
    }

    @Test
    void modifyTargetPriceWithZeroPrice() {
        String username = "Dylan";
        int price = 0;
        String productUrl = "url";

        ModifyTargetPriceInputData input = new ModifyTargetPriceInputData(username, productUrl, price);
        interactor.execute(input);

        Assertions.assertFalse(presenter.success);
    }

    @Test
    void modifyTargetPriceWithAllValidInput() {
        String username = "Dylan";
        int price = 30;
        String productUrl = "url"; // match the entry in the cart right now

        ModifyTargetPriceInputData input = new ModifyTargetPriceInputData(username, productUrl, price);
        interactor.execute(input);

        Assertions.assertTrue(presenter.success);
    }

    @Test
    void modifyTargetPriceOutputDataWorks() {
        int price = 100;
        ModifyTargetPriceOutputData output = new ModifyTargetPriceOutputData(price);

        Assertions.assertEquals("Updated target price to: 100", output.toString());
    }
}