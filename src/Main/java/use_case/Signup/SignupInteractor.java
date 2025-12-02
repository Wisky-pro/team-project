package use_case.Signup;

import entity.User;
import entity.UserFactory;

public class SignupInteractor implements SignupInputBoundary {

    private final SignupUserDataAccessInterface userDataAccess;
    private final SignupOutputBoundary presenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface userDataAccess,
                            SignupOutputBoundary presenter,
                            UserFactory userFactory) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData inputData) {

        String username = inputData.getUsername();
        String password = inputData.getPassword();

        // 1️⃣ 手动校验避免 User constructor 抛 IllegalArgumentException
        if (username == null || username.isBlank()) {
            presenter.prepareFailView("Username cannot be empty");
            return;
        }
        if (password == null || password.isBlank()) {
            presenter.prepareFailView("Password cannot be empty");
            return;
        }

        // 2️⃣ 重复用户名
        if (userDataAccess.usernameTaken(username)) {
            presenter.prepareFailView("Username already exists: " + username);
            return;
        }

        // 3️⃣ 创建用户
        User newUser = userFactory.createUser(username, password);
        userDataAccess.addUser(newUser);

        // 4️⃣ 调用成功逻辑
        presenter.prepareSuccessView(new SignupOutputData(newUser.getUsername()));
    }
}
