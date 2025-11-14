package app;

import data_access.FileUserDataAccess;
import entity.UserFactory;
import interface_adapter.LogIn.LogInController;
import interface_adapter.LogIn.LogInPresenter;
import interface_adapter.LogIn.LogInViewModel;
import use_case.LogIn.LogInInteractor;
import use_case.LogIn.LogInUserDataAccessInterface;
import view.LoginView;

public class AppBuilder {

    final UserFactory userFactory = new UserFactory();
    final LogInUserDataAccessInterface userDataAccess = new FileUserDataAccess("users.csv", userFactory);

    private LogInViewModel loginViewModel;
    private LogInPresenter loginPresenter;
    private LogInInteractor loginInteractor;
    private LogInController loginController;

    private LoginView loginView;

    public AppBuilder addLoginUseCase() {

        loginViewModel = new LogInViewModel();

        loginPresenter = new LogInPresenter(loginViewModel);

        loginInteractor = new LogInInteractor(userDataAccess, loginPresenter);

        loginController = new LogInController(loginInteractor);

        loginView = new LoginView(loginController, loginViewModel);

        return this;
    }

    public LoginView getLoginView() {
        return loginView;
    }
}
