package app;

import data_access.BestBuyProductDataAccess;
import data_access.FileUserDataAccessObject;
import data_access.InMemoryCartDataAccess;

import entity.UserFactory;

import interface_adapter.AddToCart.AddToCartController;
import interface_adapter.AddToCart.AddToCartPresenter;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.LogIn.LogInController;
import interface_adapter.LogIn.LogInPresenter;
import interface_adapter.LogIn.LogInViewModel;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import interface_adapter.RemoveFromCart.RemoveFromCartPresenter;
import interface_adapter.Signup.SignupController;
import interface_adapter.Signup.SignupPresenter;
import interface_adapter.Signup.SignupViewModel;
import interface_adapter.ViewManagerModel;

import use_case.AddToCart.AddToCartInteractor;
import use_case.AddToCart.ProductDataAccessInterface;
import use_case.Cart.CartDataAccessInterface;
import use_case.LogIn.LogInInteractor;
import use_case.RemoveFromCart.RemoveFromCartInteractor;
import use_case.Signup.SignupInteractor;

import view.DashboardView;
import view.LoginView;
import view.PriceTrackerView;
import view.SignupView;

public class AppBuilder {

    private final UserFactory userFactory = new UserFactory();
    private final FileUserDataAccessObject userDataAccess =
            new FileUserDataAccessObject("users.csv", userFactory);

    private final LogInViewModel loginVM = new LogInViewModel();
    private final SignupViewModel signupVM = new SignupViewModel();
    private final DashboardViewModel dashboardVM = new DashboardViewModel();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private LoginView loginView;
    private SignupView signupView;
    private DashboardView dashboardView;

    private CartDataAccessInterface cartDataAccess;
    private CartViewModel cartViewModel;
    private AddToCartController addToCartController;
    private RemoveFromCartController removeFromCartController;
    private PriceTrackerView priceTrackerView;
    private String username; // shared between views

    public AppBuilder addLoginUseCase() {
        LogInPresenter presenter =
                new LogInPresenter(loginVM, dashboardVM, viewManagerModel);

        LogInInteractor interactor =
                new LogInInteractor(userDataAccess, presenter);

        LogInController controller = new LogInController(interactor);

        loginView = new LoginView(controller, loginVM);

        return this;
    }

    public AppBuilder addSignupUseCase() {
        SignupPresenter presenter =
                new SignupPresenter(signupVM, viewManagerModel);

        SignupInteractor interactor =
                new SignupInteractor(userDataAccess, presenter, userFactory);

        SignupController controller = new SignupController(interactor);

        signupView = new SignupView(controller, signupVM);

        return this;
    }

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

        username = "Kevin"; // temp until wired to real login

        priceTrackerView =
                new PriceTrackerView(addToCartController, removeFromCartController,
                        cartViewModel, cartDataAccess, username);

        return this;
    }

    public AppBuilder addDashboardView() {
        if (cartViewModel == null || cartDataAccess == null
                || addToCartController == null || removeFromCartController == null
                || username == null) {
            addCartUseCase();
        }

        dashboardView = new DashboardView(
                dashboardVM,
                addToCartController,
                removeFromCartController,
                cartDataAccess,
                cartViewModel,
                username
        );

        return this;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public SignupView getSignupView() {
        return signupView;
    }

    public DashboardView getDashboardView() {
        return dashboardView;
    }

    public PriceTrackerView getPriceTrackerView() {
        return priceTrackerView;
    }

    public ViewManagerModel getViewManagerModel() {
        return viewManagerModel;
    }
}
