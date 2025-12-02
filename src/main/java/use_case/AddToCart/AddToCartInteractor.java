package use_case.AddToCart;

import entity.Cart;
import entity.ProductData;
import use_case.Cart.CartDataAccessInterface;

public class AddToCartInteractor implements AddToCartInputBoundary {

    private final CartDataAccessInterface cartDataAccess;
    private final ProductDataAccessInterface productDataAccess;
    private final AddToCartOutputBoundary presenter;

    public AddToCartInteractor(CartDataAccessInterface cartDataAccess,
                               ProductDataAccessInterface productDataAccess,
                               AddToCartOutputBoundary presenter) {
        this.cartDataAccess = cartDataAccess;
        this.productDataAccess = productDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddToCartInputData inputData) {
        String username = inputData.getUsername();
        String productUrl = inputData.getProductUrl();
        int quantity = inputData.getQuantity();
        int targetPrice = inputData.getTargetPrice();

        if (quantity <= 0) {
            presenter.prepareFailView("Quantity must be positive.");
            return;
        }

        try {
            ProductData productData = productDataAccess.getByUrl(productUrl);
            if (productData == null) {
                presenter.prepareFailView("Product not found for URL: " + productUrl);
                return;
            }

            Cart cart = cartDataAccess.getCart(username);
            cart.addItem(productData.getProductUrl(), productData.getName(), productData.getPrice(), quantity, targetPrice);
            cartDataAccess.saveCart(username, cart);

            double total = cart.getTotal();
            AddToCartOutputData outputData = new AddToCartOutputData(productData.getName(), total);
            presenter.prepareSuccessView(outputData);

        } catch (IllegalArgumentException e) {
            presenter.prepareFailView("Invalid BestBuy product URL.");
        } catch (Exception e) {
            e.printStackTrace();
            presenter.prepareFailView("Could not add item to cart.");
        }
    }
}
