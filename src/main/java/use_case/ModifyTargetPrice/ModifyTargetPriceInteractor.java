package use_case.ModifyTargetPrice;

import entity.CartItem;

public class ModifyTargetPriceInteractor implements ModifyTargetPriceInputBoundary {
    private final ModifyTargetPriceDataAccessInterface dataAccess;
    private final ModifyTargetPriceOutputBoundary outputBoundary;

    public ModifyTargetPriceInteractor(ModifyTargetPriceDataAccessInterface dataAccess, 
                                        ModifyTargetPriceOutputBoundary outputBoundary) {
            this.dataAccess = dataAccess;
            this.outputBoundary = outputBoundary;
    }

    @Override 
    public void execute(ModifyTargetPriceInputData input) {
        int price = input.getPrice();

        if (!dataAccess.isValidPrice(price)) {
            outputBoundary.prepareFailView("You have inputted a invalid price. Please try again.");
            return;
        }

        CartItem item = input.getItem();
        dataAccess.setCurrentTargetPrice(item, price);

        ModifyTargetPriceOutputData output = new ModifyTargetPriceOutputData(price);

        outputBoundary.prepareSuccessView(output);
    }
}
