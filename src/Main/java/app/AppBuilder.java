package app;

import data_access.BestBuyProductDataAccess;
import data_access.InMemoryCartDataAccess;
import interface_adapter.AddToCart.AddToCartController;
import interface_adapter.AddToCart.AddToCartPresenter;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import interface_adapter.RemoveFromCart.RemoveFromCartPresenter;
import use_case.AddToCart.AddToCartInteractor;
import use_case.AddToCart.ProductDataAccessInterface;
import use_case.Cart.CartDataAccessInterface;
import use_case.RemoveFromCart.RemoveFromCartInteractor;
import view.PriceTrackerView;

public class AppBuilder {

    private CartDataAccessInterface cartDataAccess;
    private CartViewModel cartViewModel;
    private AddToCartController addToCartController;
    private RemoveFromCartController removeFromCartController;

    private PriceTrackerView priceTrackerView;

    public AppBuilder addCartUseCase() {

        cartDataAccess = new InMemoryCartDataAccess();
        ProductDataAccessInterface productDataAccess =
                new BestBuyProductDataAccess("data_access/priceHistory.json");

        cartViewModel = new CartViewModel();

        AddToCartPresenter addPresenter = new AddToCartPresenter(cartViewModel);
        AddToCartInteractor addInteractor =
                new AddToCartInteractor(cartDataAccess, productDataAccess, addPresenter);
        addToCartController = new AddToCartController(addInteractor);

        RemoveFromCartPresenter removePresenter = new RemoveFromCartPresenter(cartViewModel);
        RemoveFromCartInteractor removeInteractor =
                new RemoveFromCartInteractor(cartDataAccess, removePresenter);
        removeFromCartController = new RemoveFromCartController(removeInteractor);

        String username = "Kevin";
        priceTrackerView =
                new PriceTrackerView(addToCartController, removeFromCartController,
                        cartViewModel, cartDataAccess, username);

        return this;
    }

    public PriceTrackerView getPriceTrackerView() {
        return priceTrackerView;
    }
}
