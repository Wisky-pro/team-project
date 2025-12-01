package use_case.Dashboard;

import data_access.InMemoryUserDataAccess;
import org.junit.jupiter.api.Test;
import use_case.LogOut.LogOutInteractor;
import use_case.LogOut.LogOutOutputBoundary;

import static org.junit.jupiter.api.Assertions.*;

class LogoutTest {

    private static class TestPresenter implements LogOutOutputBoundary {
        boolean successCalled = false;

        @Override
        public void prepareSuccessView() {
            successCalled = true;
        }
    }

    @Test
    void testLogoutClearsCurrentUserAndCallsPresenter() {

        InMemoryUserDataAccess userDAO = new InMemoryUserDataAccess();
        userDAO.setCurrentUsername("Kevin");

        TestPresenter presenter = new TestPresenter();

        LogOutInteractor interactor = new LogOutInteractor(presenter, userDAO);

        interactor.execute();

        assertNull(userDAO.getCurrentUsername(),
                "Logout should clear current user");

        assertTrue(presenter.successCalled,
                "Presenter.prepareSuccessView() should be called");
    }
}
