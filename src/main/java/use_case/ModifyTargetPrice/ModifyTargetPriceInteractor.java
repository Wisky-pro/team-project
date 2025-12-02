package use_case.ModifyTargetPrice;

import entity.Cart;

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
        String username = input.getUsername();
        String productUrl = input.getProductUrl();
        int price = input.getUpdatedPrice();

        if (price <= 0) {
            outputBoundary.prepareFailView("You have inputted a invalid price. Please try again.");
            return;
        }

        Cart cart = dataAccess.getCart(username);


        if (cart.getTargetPriceByUrl(productUrl) == -1) {
            outputBoundary.prepareFailView("Product not found. Please try again.");
            return;
        }

        cart.updateItem(productUrl, price);
        dataAccess.saveCart(username, cart);

        ModifyTargetPriceOutputData output = new ModifyTargetPriceOutputData(price);
        outputBoundary.prepareSuccessView(output);
    }
}
