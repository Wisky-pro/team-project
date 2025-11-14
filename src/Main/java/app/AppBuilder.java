package app;

import data_access.FileUserDataAccessObject;
import entity.UserFactory;
import interface_adapter.LogIn.*;
import interface_adapter.Signup.*;
import use_case.LogIn.*;
import use_case.Signup.*;
import view.LoginView;
import view.SignupView;

public class AppBuilder {

    private final UserFactory userFactory = new UserFactory();
    private final LogInUserDataAccessInterface userDataAccess = new FileUserDataAccessObject("users.csv", userFactory);

    // Views (kept as fields for getters)
    private LoginView loginView;
    private SignupView signupView;

    // --- Add Login use case ---
    public AppBuilder addLoginUseCase() {
        LogInViewModel loginViewModel = new LogInViewModel();
        LogInPresenter loginPresenter = new LogInPresenter(loginViewModel);
        LogInInteractor loginInteractor = new LogInInteractor(userDataAccess, loginPresenter);
        LogInController loginController = new LogInController(loginInteractor);

        loginView = new LoginView(loginController, loginViewModel);

        return this;
    }

    // --- Add Signup use case ---
    public AppBuilder addSignupUseCase() {
        SignupViewModel signupViewModel = new SignupViewModel();
        SignupPresenter signupPresenter = new SignupPresenter(signupViewModel);
        SignupInteractor signupInteractor = new SignupInteractor(
                (SignupUserDataAccessInterface) userDataAccess,
                signupPresenter,
                userFactory
        );
        SignupController signupController = new SignupController(signupInteractor);

        signupView = new SignupView(signupController, signupViewModel);

        return this;
    }

    // --- Getters for views ---
    public LoginView getLoginView() {
        return loginView;
    }

    public SignupView getSignupView() {
        return signupView;
    }
}
