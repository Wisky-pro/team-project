package use_case.RemoveFromCart;

import entity.Cart;
import entity.CartItem;
import use_case.Cart.CartDataAccessInterface;

public class RemoveFromCartInteractor implements RemoveFromCartInputBoundary {

    private final CartDataAccessInterface cartDataAccess;
    private final RemoveFromCartOutputBoundary presenter;

    public RemoveFromCartInteractor(CartDataAccessInterface cartDataAccess,
                                    RemoveFromCartOutputBoundary presenter) {
        this.cartDataAccess = cartDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(RemoveFromCartInputData inputData) {
        String username = inputData.getUsername();
        String productUrl = inputData.getProductUrl();
        int quantity = inputData.getQuantity();

        if (quantity <= 0) {
            presenter.prepareFailView("Quantity must be positive.");
            return;
        }

        Cart cart = cartDataAccess.getCart(username);
        if (cart == null || cart.isEmpty()) {
            presenter.prepareFailView("Your cart is empty.");
            return;
        }

        CartItem item = cart.getItemByUrl(productUrl);
        if (item == null) {
            presenter.prepareFailView("Item not found in cart.");
            return;
        }

        int currentQty = item.getQuantity();
        if (quantity > currentQty) {
            presenter.prepareFailView("You only have " + currentQty + " of this item in your cart.");
            return;
        }

        cart.removeItem(productUrl, quantity);
        cartDataAccess.saveCart(username, cart);

        double total = cart.getTotal();
        RemoveFromCartOutputData outputData =
                new RemoveFromCartOutputData(item.getName(), total);
        presenter.prepareSuccessView(outputData);
    }
}
