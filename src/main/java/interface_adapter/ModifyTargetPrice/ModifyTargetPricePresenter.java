package interface_adapter.ModifyTargetPrice;

import use_case.ModifyTargetPrice.ModifyTargetPriceOutputData;
import use_case.ModifyTargetPrice.ModifyTargetPriceOutputBoundary;

public class ModifyTargetPricePresenter implements ModifyTargetPriceOutputBoundary {
    private final ModifyTargetPriceViewModel viewModel;

    public ModifyTargetPricePresenter(ModifyTargetPriceViewModel model) {
        this.viewModel = model;
    }

    @Override
    public void prepareSuccessView(ModifyTargetPriceOutputData output) {
        String message = "Successfully updated price to: " + output.getModifiedTargetPrice();
        viewModel.setMessage(message);
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.setMessage(error);
    }
}
