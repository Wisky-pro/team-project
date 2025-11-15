package app;

import data_access.FileUserDataAccessObject;
import entity.UserFactory;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.LogIn.*;
import interface_adapter.Signup.*;
import interface_adapter.ViewManagerModel;
import use_case.LogIn.*;
import use_case.Signup.*;
import view.DashboardView;
import view.LoginView;
import view.SignupView;

public class AppBuilder {

    private final UserFactory userFactory = new UserFactory();
    private final FileUserDataAccessObject userDataAccess =
            new FileUserDataAccessObject("users.csv", userFactory);

    // Shared view models (ONE instance each)
    private final LogInViewModel loginVM = new LogInViewModel();
    private final SignupViewModel signupVM = new SignupViewModel();
    private final DashboardViewModel dashboardVM = new DashboardViewModel();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    // Views
    private LoginView loginView;
    private SignupView signupView;
    private DashboardView dashboardView;

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

    public AppBuilder addDashboardView() {
        dashboardView = new DashboardView(dashboardVM);
        return this;
    }

    // --- Getters ---

    public LoginView getLoginView() {
        return loginView;
    }

    public SignupView getSignupView() {
        return signupView;
    }

    public DashboardView getDashboardView() {
        return dashboardView;
    }

    public ViewManagerModel getViewManagerModel() {
        return viewManagerModel;
    }
}
