package interface_adapter.AddToCart;

import use_case.AddToCart.AddToCartInputBoundary;
import use_case.AddToCart.AddToCartInputData;

public class AddToCartController {

    private final AddToCartInputBoundary interactor;

    public AddToCartController(AddToCartInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String productUrl, int quantity) {
        AddToCartInputData inputData = new AddToCartInputData(username, productUrl, quantity);
        interactor.execute(inputData);
    }

    public void execute(String username, String productUrl, int quantity, int targetPrice) {
        AddToCartInputData input = new AddToCartInputData(username, productUrl, quantity, targetPrice);
        interactor.execute(input);
    }
}
