package app;

import data_access.BestBuyProductDataAccess;
import data_access.CommodityDataAccessObject;
import data_access.InMemoryCartDataAccess;
import data_access.JsonUserDataAccess;

import entity.UserFactory;

import interface_adapter.AddToCart.AddToCartController;
import interface_adapter.AddToCart.AddToCartPresenter;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.Dashboard.DashBoardController;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Recommendation.PurchaseRecommendationController;
import interface_adapter.Recommendation.PurchaseRecommendationPresenter;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import interface_adapter.RemoveFromCart.RemoveFromCartPresenter;
import interface_adapter.Logout.LogoutPresenter;
import interface_adapter.Logout.LogoutController;
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
import use_case.Recommendation.PurchaseRecommendationDataAccessInterface;
import use_case.Recommendation.PurchaseRecommendationInputBoundary;
import use_case.Recommendation.PurchaseRecommendationInteractor;
import use_case.Recommendation.PurchaseRecommendationOutputBoundary;
import use_case.RemoveFromCart.RemoveFromCartInteractor;

import use_case.Signup.SignupInputBoundary;
import use_case.Signup.SignupInteractor;
import use_case.Signup.SignupOutputBoundary;

import use_case.LogIn.LogInInteractor;
import use_case.LogOut.LogOutInteractor;
import view.*;

import javax.swing.*;

public class AppBuilder {

    private RemoveFromCartController removeFromCartController;
    private PriceTrackerView priceTrackerView;
    private RecommendationView recommendationView;

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(viewManagerModel);

    private final SignupViewModel signupVM = new SignupViewModel();
    private final LogInViewModel loginVM = new LogInViewModel();
    private final DashboardViewModel dashboardVM = new DashboardViewModel();

    private final JsonUserDataAccess userDataAccess =
            new JsonUserDataAccess("users.json");

    // ------------------- Signup -------------------
    public AppBuilder addSignupUseCase() {

        SignupOutputBoundary presenter = new SignupPresenter(signupVM, viewManagerModel);
        UserFactory userFactory = new UserFactory();
        SignupInputBoundary interactor = new SignupInteractor(userDataAccess, presenter, userFactory);

        SignupController controller = new SignupController(interactor);
        SignupView signupView = new SignupView(controller, signupVM);

        viewManager.addView(signupView, "signup");

        signupView.setSwitchToLoginCallback(() ->
                viewManagerModel.setActiveView("login"));

        return this;
    }

    // ------------------- Login -------------------
    public AppBuilder addLoginUseCase() {

        LogInPresenter presenter = new LogInPresenter(loginVM, dashboardVM, viewManagerModel);
        LogInInteractor interactor = new LogInInteractor(userDataAccess, presenter);

        LogInController controller = new LogInController(interactor);
        LoginView loginView = new LoginView(controller, loginVM);

        viewManager.addView(loginView, "login");

        loginView.setSwitchToSignupCallback(() ->
                viewManagerModel.setActiveView("signup"));

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

        DashboardView dashboardButtons = new DashboardView(dashboardVM);
        dashboardVM.setDashboardView(dashboardButtons);

        new DashBoardController(dashboardVM, viewManagerModel, dashboardButtons);

        LogoutPresenter logoutPresenter =
                new LogoutPresenter(viewManagerModel, loginVM);

        LogOutInteractor logoutInteractor =
                new LogOutInteractor(logoutPresenter, userDataAccess);

        LogoutController logoutController =
                new LogoutController(logoutInteractor);

        dashboardButtons.setLogoutController(logoutController);

        priceTrackerView = new PriceTrackerView(
                addToCartController,
                removeFromCartController,
                cartViewModel,
                cartDataAccess,
                "Kevin"
        );

        JPanel combinedDashboard = new JPanel();
        combinedDashboard.setLayout(new BoxLayout(combinedDashboard, BoxLayout.Y_AXIS));

        combinedDashboard.add(dashboardButtons);
        combinedDashboard.add(priceTrackerView);

        viewManager.addView(combinedDashboard, "dashboard");
        priceTrackerView.setSwitchToRecommendationCallback(() -> viewManagerModel.setActiveView("recommendation"));
        return this;
    }

    public AppBuilder addRecommendationUseCase() {
        PurchaseRecommendationDataAccessInterface dataAccess =
                new CommodityDataAccessObject();
        PurchaseRecommendationOutputBoundary presenter =
                new PurchaseRecommendationPresenter(dashboardVM);
        PurchaseRecommendationInputBoundary interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);
        PurchaseRecommendationController controller = new PurchaseRecommendationController(interactor);
        recommendationView = new RecommendationView(dashboardVM, controller);
        viewManager.addView(recommendationView, "recommendation");
        recommendationView.setSwitchToPriceTrackerViewCallback(() -> viewManagerModel.setActiveView("dashboard"));
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
