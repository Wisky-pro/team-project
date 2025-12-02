package interface_adapter.AddToCart;

import interface_adapter.Cart.CartViewModel;
import use_case.AddToCart.AddToCartOutputBoundary;
import use_case.AddToCart.AddToCartOutputData;

public class AddToCartPresenter implements AddToCartOutputBoundary {

    private final CartViewModel cartViewModel;

    public AddToCartPresenter(CartViewModel cartViewModel) {
        this.cartViewModel = cartViewModel;
    }

    @Override
    public void prepareSuccessView(AddToCartOutputData outputData) {
        String message = "Added to cart: " + outputData.getProductName();
        cartViewModel.setMessage(message);
        cartViewModel.setTotal(outputData.getCartTotal());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        cartViewModel.setMessage(errorMessage);
    }
}
