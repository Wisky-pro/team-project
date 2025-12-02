package use_case.Signup;

import data_access.InMemoryUserDataAccess;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SignupTest {

    private InMemoryUserDataAccess userDataAccess;
    private SignupInputBoundary signupInteractor;
    private TestSignupPresenter presenter;

    @BeforeEach
    void setup() {
        userDataAccess = new InMemoryUserDataAccess();
        presenter = new TestSignupPresenter();
        UserFactory userFactory = new UserFactory();
        signupInteractor = new SignupInteractor(userDataAccess, presenter, userFactory);
    }

    static class TestSignupPresenter implements SignupOutputBoundary {
        boolean success = false;
        List<String> errors = new ArrayList<>();
        String signedUpUsername = null;

        // TEST-facing API
        public void presentSignupSuccess(SignupOutputData outputData) {
            success = true;
            signedUpUsername = outputData.getUsername();
        }

        public void presentSignupFailure(List<String> errors) {
            this.errors = errors;
        }

        @Override
        public void prepareSuccessView(SignupOutputData outputData) {
            presentSignupSuccess(outputData);
        }

        @Override
        public void prepareFailView(String errorMessage) {
            List<String> list = new ArrayList<>();
            list.add(errorMessage);
            presentSignupFailure(list);
        }
    }

    @Test
    void testSuccessfulSignup() {
        signupInteractor.execute(new SignupInputData("alice", "password123"));

        assertTrue(presenter.success);
        assertEquals("alice", presenter.signedUpUsername);
        assertTrue(userDataAccess.usernameTaken("alice"));
    }

    @Test
    void testEmptyUsername() {
        signupInteractor.execute(new SignupInputData("", "password123"));

        assertFalse(presenter.success);
        assertTrue(presenter.errors.contains("Username cannot be empty"));
        assertFalse(userDataAccess.usernameTaken(""));
    }

    @Test
    void testEmptyPassword() {
        signupInteractor.execute(new SignupInputData("bob", ""));

        assertFalse(presenter.success);
        assertTrue(presenter.errors.contains("Password cannot be empty"));
        assertFalse(userDataAccess.usernameTaken("bob"));
    }

    @Test
    void testDuplicateUsername() {
        signupInteractor.execute(new SignupInputData("charlie", "pass1"));

        presenter = new TestSignupPresenter(); // reset presenter
        signupInteractor = new SignupInteractor(userDataAccess, presenter, new UserFactory());
        signupInteractor.execute(new SignupInputData("charlie", "pass2"));

        assertFalse(presenter.success);
        assertTrue(presenter.errors.contains("Username already exists: charlie"));
        assertTrue(userDataAccess.userExists("charlie"));
    }
}
