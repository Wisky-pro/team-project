package interface_adapter.RemoveFromCart;

import interface_adapter.Cart.CartViewModel;
import use_case.RemoveFromCart.RemoveFromCartOutputBoundary;
import use_case.RemoveFromCart.RemoveFromCartOutputData;

public class RemoveFromCartPresenter implements RemoveFromCartOutputBoundary {

    private final CartViewModel viewModel;

    public RemoveFromCartPresenter(CartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(RemoveFromCartOutputData outputData) {
        viewModel.setMessage("Removed " + outputData.getProductName() + " from cart.");
        viewModel.setTotal(outputData.getTotal());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage(errorMessage);
    }
}
