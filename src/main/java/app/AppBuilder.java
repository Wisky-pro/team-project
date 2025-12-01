package app;

import data_access.BestBuyProductDataAccess;
import data_access.InMemoryCartDataAccess;
import data_access.InMemoryUserDataAccess;

import data_access.PriceHistoryDataAccessObject;
import entity.UserFactory;

import interface_adapter.AddToCart.AddToCartController;
import interface_adapter.AddToCart.AddToCartPresenter;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.Dashboard.DashBoardController;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.PriceHistory.PriceHistoryController;
import interface_adapter.PriceHistory.PriceHistoryPresenter;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import interface_adapter.RemoveFromCart.RemoveFromCartPresenter;
import interface_adapter.PriceHistory.PriceHistoryViewModel;

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
import use_case.PriceHistory.PriceHistoryInputBoundary;
import use_case.RemoveFromCart.RemoveFromCartInteractor;

import use_case.Signup.SignupInputBoundary;
import use_case.Signup.SignupInteractor;
import use_case.Signup.SignupOutputBoundary;

import use_case.LogIn.LogInInteractor;

import use_case.PriceHistory.PriceHistoryInteractor;
import use_case.PriceHistory.PriceHistoryDataAccessInterface;

import view.DashboardView;
import view.LoginView;
import view.SignupView;
import view.AccountInfoView;
import view.PriceTrackerView;

import javax.swing.*;

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
        SignupOutputBoundary presenter = new SignupPresenter(signupVM, viewManagerModel);
        UserFactory userFactory = new UserFactory();
        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, presenter, userFactory);
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

        // ------------------- Account Info -------------------
        AccountInfoView accountInfoView = new AccountInfoView();
        viewManager.addView(accountInfoView, "accountInfo");

        // ------------------- Dashboard Buttons Panel -------------------
        DashboardView dashboardButtons = new DashboardView(dashboardVM);
        dashboardVM.setDashboardView(dashboardButtons);

        // Controller handles the Account Info button
        new DashboardController(dashboardVM, viewManagerModel, accountInfoView);

        // ------------------- Price History -------------------
        PriceHistoryViewModel priceHistoryViewModel = new PriceHistoryViewModel();
        PriceHistoryPresenter priceHistoryPresenter = new PriceHistoryPresenter(priceHistoryViewModel);

        PriceHistoryDataAccessInterface priceHistoryDataAccessInterface = new PriceHistoryDataAccessObject();
        PriceHistoryInputBoundary priceHistoryInputBoundary =
                new PriceHistoryInteractor(priceHistoryDataAccessInterface, priceHistoryPresenter);

        PriceHistoryController priceHistoryController = new PriceHistoryController(priceHistoryInputBoundary);

        // ------------------- Price Tracker Panel -------------------
        priceTrackerView =
                new PriceTrackerView(addToCartController, removeFromCartController, cartViewModel, cartDataAccess,
                        "Kevin", priceHistoryViewModel, priceHistoryController);

        // ------------------- Combine Dashboard Buttons + Tracker -------------------
        JPanel combinedDashboard = new JPanel();
        combinedDashboard.setLayout(new BoxLayout(combinedDashboard, BoxLayout.Y_AXIS));
        combinedDashboard.add(dashboardButtons); // top buttons
        combinedDashboard.add(priceTrackerView); // tracker below




        // Add combined panel as "dashboard"
        viewManager.addView(combinedDashboard, "dashboard");

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

    private class DashboardController {
        public DashboardController(DashboardViewModel dashboardVM, ViewManagerModel viewManagerModel, AccountInfoView accountInfoView) {
        }
    }
}
