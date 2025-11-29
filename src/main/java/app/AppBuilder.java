package app;

import data_access.BestBuyProductDataAccess;
import data_access.CommodityDataAccessObject;
import data_access.InMemoryCartDataAccess;
import data_access.FileUserDataAccessObject;

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
import interface_adapter.Recommendation.PurchaseRecommendationController;
import interface_adapter.Recommendation.PurchaseRecommendationPresenter;

import use_case.LogIn.LogInInteractor;
import use_case.Signup.SignupInteractor;
import use_case.AddToCart.AddToCartInteractor;
import use_case.AddToCart.ProductDataAccessInterface;
import use_case.Cart.CartDataAccessInterface;
import use_case.RemoveFromCart.RemoveFromCartInteractor;
import use_case.Recommendation.*;

import view.LoginView;
import view.SignupView;
import view.PriceTrackerView;
import view.DashboardViewForTest;

public class AppBuilder {

    private final UserFactory userFactory = new UserFactory();
    private final FileUserDataAccessObject userDataAccess =
            new FileUserDataAccessObject("users.csv", userFactory);

    private final LogInViewModel loginVM = new LogInViewModel();
    private final SignupViewModel signupVM = new SignupViewModel();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final DashboardViewModel dashboardVM = new DashboardViewModel();

    private LoginView loginView;
    private SignupView signupView;
    private PriceTrackerView priceTrackerView;
    private DashboardViewForTest recommendationView;

    private CartDataAccessInterface cartDataAccess;
    private CartViewModel cartViewModel;
    private AddToCartController addToCartController;
    private RemoveFromCartController removeFromCartController;
    private PurchaseRecommendationController recommendationController;

    public AppBuilder addLoginUseCase() {
        LogInPresenter presenter =
                new LogInPresenter(loginVM, viewManagerModel);

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

        String username = "Kevin"; // temp until login → cart username is wired

        priceTrackerView =
                new PriceTrackerView(addToCartController, removeFromCartController,
                        cartViewModel, cartDataAccess, username);

        return this;
    }

    public AppBuilder addRecommendationUseCase() {
        CommodityDataAccessObject dataAccess = new CommodityDataAccessObject();

        PurchaseRecommendationOutputBoundary presenter =
                new PurchaseRecommendationPresenter(dashboardVM);

        PurchaseRecommendationInputBoundary interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        recommendationController =
                new PurchaseRecommendationController(interactor);

        recommendationView =
                new DashboardViewForTest(dashboardVM, recommendationController);

        return this;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public SignupView getSignupView() {
        return signupView;
    }

    public PriceTrackerView getPriceTrackerView() {
        return priceTrackerView;
    }

    public DashboardViewForTest getRecommendationView() {
        return recommendationView;
    }

    public ViewManagerModel getViewManagerModel() {
        return viewManagerModel;
    }
}
