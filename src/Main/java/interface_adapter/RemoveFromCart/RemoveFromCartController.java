package interface_adapter.RemoveFromCart;

import use_case.RemoveFromCart.RemoveFromCartInputBoundary;
import use_case.RemoveFromCart.RemoveFromCartInputData;

public class RemoveFromCartController {

    private final RemoveFromCartInputBoundary interactor;

    public RemoveFromCartController(RemoveFromCartInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String productUrl, int quantity) {
        RemoveFromCartInputData inputData =
                new RemoveFromCartInputData(username, productUrl, quantity);
        interactor.execute(inputData);
    }
}
