package app;

import data_access.BestBuyProductDataAccess;
import data_access.InMemoryCartDataAccess;
import data_access.InMemoryUserDataAccess;

import entity.UserFactory;

import interface_adapter.AddToCart.AddToCartController;
import interface_adapter.AddToCart.AddToCartPresenter;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import interface_adapter.RemoveFromCart.RemoveFromCartPresenter;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManager;

import interface_adapter.Signup.SignupController;
import interface_adapter.Signup.SignupPresenter;
import interface_adapter.Signup.SignupViewModel;

import interface_adapter.LogIn.LogInController;
import interface_adapter.LogIn.LogInPresenter;
import interface_adapter.LogIn.LogInViewModel;

import use_case.AddToCart.AddToCartInteractor;
import use_case.AddToCart.ProductDataAccessInterface;
import use_case.Cart.CartDataAccessInterface;
import use_case.RemoveFromCart.RemoveFromCartInteractor;

import use_case.Signup.SignupInputBoundary;
import use_case.Signup.SignupInteractor;
import use_case.Signup.SignupOutputBoundary;

import use_case.LogIn.LogInInteractor;

import view.LoginView;
import view.PriceTrackerView;
import view.SignupView;

public class AppBuilder {

    private RemoveFromCartController removeFromCartController;
    private PriceTrackerView priceTrackerView;

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(viewManagerModel);

    private SignupViewModel signupVM = new SignupViewModel();
    private LogInViewModel loginVM = new LogInViewModel();
    private DashboardViewModel dashboardVM = new DashboardViewModel();

    private final InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess();

    // ------------------- Signup -------------------
    public AppBuilder addSignupUseCase() {
        SignupOutputBoundary presenter =
                new SignupPresenter(signupVM, viewManagerModel);
        UserFactory userFactory = new UserFactory();
        SignupInputBoundary interactor =
                new SignupInteractor(userDataAccess, presenter, userFactory);
        SignupController controller = new SignupController(interactor);

        SignupView signupView = new SignupView(controller, signupVM);
        viewManager.addView(signupView, "signup");

        // Callback: back to login view
        signupView.setSwitchToLoginCallback(() -> viewManagerModel.setActiveView("login"));

        return this;
    }

    // ------------------- Login -------------------
    public AppBuilder addLoginUseCase() {
        LogInPresenter presenter = new LogInPresenter(loginVM, dashboardVM, viewManagerModel);
        LogInInteractor interactor = new LogInInteractor(userDataAccess, presenter);
        LogInController controller = new LogInController(interactor);

        LoginView loginView = new LoginView(controller, loginVM);
        viewManager.addView(loginView, "login");

        // Callback: switch to signup view
        loginView.setSwitchToSignupCallback(() -> viewManagerModel.setActiveView("signup"));

        return this;
    }

    // ------------------- Cart / Dashboard -------------------
    public AppBuilder addCartUseCase() {
        CartDataAccessInterface cartDataAccess = new InMemoryCartDataAccess();
        ProductDataAccessInterface productDataAccess =
                new BestBuyProductDataAccess("data_access/priceHistory.json");

        CartViewModel cartViewModel = new CartViewModel();

        AddToCartPresenter addPresenter = new AddToCartPresenter(cartViewModel);
        AddToCartInteractor addInteractor =
                new AddToCartInteractor(cartDataAccess, productDataAccess, addPresenter);
        AddToCartController addToCartController = new AddToCartController(addInteractor);

        RemoveFromCartPresenter removePresenter = new RemoveFromCartPresenter(cartViewModel);
        RemoveFromCartInteractor removeInteractor =
                new RemoveFromCartInteractor(cartDataAccess, removePresenter);
        removeFromCartController = new RemoveFromCartController(removeInteractor);

        priceTrackerView =
                new PriceTrackerView(addToCartController, removeFromCartController,
                        cartViewModel, cartDataAccess, "Kevin");

        viewManager.addView(priceTrackerView, "dashboard");

        return this;
    }

    // ------------------- Getters -------------------
    public ViewManager getViewManager() {
        return viewManager;
    }

    public ViewManagerModel getViewManagerModel() {
        return viewManagerModel;
    }

    public PriceTrackerView getPriceTrackerView() {
        return priceTrackerView;
    }
}
