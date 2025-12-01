package app;

import data_access.BestBuyProductDataAccess;
import data_access.JsonUserDataAccess;
import entity.UserFactory;

import interface_adapter.AddToCart.AddToCartController;
import interface_adapter.AddToCart.AddToCartPresenter;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.Dashboard.DashBoardController;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import interface_adapter.RemoveFromCart.RemoveFromCartPresenter;
import interface_adapter.ViewManager;
import interface_adapter.ViewManagerModel;
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

import view.AccountInfoView;
import view.DashboardView;
import view.PriceTrackerView;
import view.LoginView;
import view.SignupView;

import javax.swing.*;

public class AppBuilder {

    private RemoveFromCartController removeFromCartController;
    private PriceTrackerView priceTrackerView;

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(viewManagerModel);

    private final SignupViewModel signupVM = new SignupViewModel();
    private final LogInViewModel loginVM = new LogInViewModel();
    private final DashboardViewModel dashboardVM = new DashboardViewModel();

    private final JsonUserDataAccess userDataAccess = new JsonUserDataAccess("users.json");

    // Signup
    public AppBuilder addSignupUseCase() {
        SignupOutputBoundary presenter = new SignupPresenter(signupVM, viewManagerModel);
        UserFactory userFactory = new UserFactory();
        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, presenter, userFactory);
        SignupController controller = new SignupController(interactor);

        SignupView signupView = new SignupView(controller, signupVM);
        signupView.setSwitchToLoginCallback(() -> viewManagerModel.setActiveView("login"));

        viewManager.addView(signupView, "signup");
        return this;
    }

    // Login
    public AppBuilder addLoginUseCase() {
        LogInPresenter presenter = new LogInPresenter(loginVM, dashboardVM, viewManagerModel);
        LogInInteractor interactor = new LogInInteractor(userDataAccess, presenter);
        LogInController controller = new LogInController(interactor);

        LoginView loginView = new LoginView(controller, loginVM);
        loginView.setSwitchToSignupCallback(() -> viewManagerModel.setActiveView("signup"));

        viewManager.addView(loginView, "login");
        return this;
    }

    // Cart / Dashboard / PriceTracker
    public AppBuilder addCartUseCase() {

        CartDataAccessInterface cartDataAccess = userDataAccess;
        ProductDataAccessInterface productDataAccess =
                new BestBuyProductDataAccess("priceHistory.json");

        CartViewModel cartViewModel = new CartViewModel();

        AddToCartPresenter addPresenter = new AddToCartPresenter(cartViewModel);
        AddToCartInteractor addInteractor =
                new AddToCartInteractor(cartDataAccess, productDataAccess, addPresenter);
        AddToCartController addToCartController = new AddToCartController(addInteractor);

        RemoveFromCartPresenter removePresenter = new RemoveFromCartPresenter(cartViewModel);
        RemoveFromCartInteractor removeInteractor =
                new RemoveFromCartInteractor(cartDataAccess, removePresenter);
        removeFromCartController = new RemoveFromCartController(removeInteractor);

        AccountInfoView accountInfoView = new AccountInfoView();
        viewManager.addView(accountInfoView, "accountInfo");

        DashboardView dashboardButtons = new DashboardView(dashboardVM);
        dashboardVM.setDashboardView(dashboardButtons);

        new DashBoardController(dashboardVM, viewManagerModel, accountInfoView);

        // NOTE: pass loginVM (view-model) rather than static username
        priceTrackerView = new PriceTrackerView(
                addToCartController,
                removeFromCartController,
                cartViewModel,
                cartDataAccess,
                loginVM
        );

        JPanel combinedDashboard = new JPanel();
        combinedDashboard.setLayout(new BoxLayout(combinedDashboard, BoxLayout.Y_AXIS));
        combinedDashboard.add(dashboardButtons);
        combinedDashboard.add(priceTrackerView);

        viewManager.addView(combinedDashboard, "dashboard");

        return this;
    }

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
