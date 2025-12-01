package use_case.Dashboard;

import data_access.InMemoryUserDataAccess;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.LogIn.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private LogInInteractor loginInteractor;
    private TestLoginPresenter presenter;

    @BeforeEach
    void setup() {
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess();
        presenter = new TestLoginPresenter();

        userDataAccess.addUser(new User("alice", "password123"));

        loginInteractor = new LogInInteractor(userDataAccess, presenter);
    }

    static class TestLoginPresenter implements LogInOutputBoundary {
        boolean success = false;
        String message = null;

        @Override
        public void prepareSuccessView(LogInOutputData data) {
            success = true;
            message = data.getUsername();
        }

        @Override
        public void prepareFailView(String errorMessage) {
            success = false;
            message = errorMessage;
        }
    }

    @Test
    void testSuccessfulLogin() {
        loginInteractor.execute(new LogInInputData("alice", "password123"));

        assertTrue(presenter.success);
        assertEquals("alice", presenter.message);
    }

    @Test
    void testInvalidPassword() {
        loginInteractor.execute(new LogInInputData("alice", "wrongpassword"));

        assertFalse(presenter.success);
        assertEquals("Invalid username or password", presenter.message);
    }

    @Test
    void testNonexistentUser() {
        loginInteractor.execute(new LogInInputData("bob", "some pass"));

        assertFalse(presenter.success);
        assertEquals("Invalid username or password", presenter.message);
    }

    @Test
    void testEmptyUsername() {
        loginInteractor.execute(new LogInInputData("", "password123"));

        assertFalse(presenter.success);
        assertEquals("Username cannot be empty", presenter.message);
    }

    @Test
    void testEmptyPassword() {
        loginInteractor.execute(new LogInInputData("alice", ""));

        assertFalse(presenter.success);
        assertEquals("Password cannot be empty", presenter.message);
    }
}
