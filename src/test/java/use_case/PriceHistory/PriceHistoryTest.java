package use_case.PriceHistory;

import entity.PriceHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PriceHistoryTest {

    static class MockPresenter implements PriceHistoryOutputBoundary {
        boolean success;
        String message;
        PriceHistoryOutputData capturedOutput;

        @Override
        public void prepareSuccessView(PriceHistoryOutputData outputData) {
            this.success = true;
            this.capturedOutput = outputData;
            this.message = "success";
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.success = false;
            this.message = errorMessage;
        }
    }

    static class MockDataAccess implements PriceHistoryDataAccessInterface {
        PriceHistory historyToReturn;

        public void setHistoryToReturn(PriceHistory history) {
            this.historyToReturn = history;
        }

        @Override
        public PriceHistory getPriceHistory(String productUrl) {
            return historyToReturn;
        }
    }

    private MockPresenter presenter;
    private MockDataAccess dataAccess;
    private PriceHistoryInteractor interactor;

    @BeforeEach
    void setUp() {
        presenter = new MockPresenter();
        dataAccess = new MockDataAccess();
        interactor = new PriceHistoryInteractor(dataAccess, presenter);
    }

    @Test
    void test_NoPriceHistoryEntity() {
        dataAccess.setHistoryToReturn(null);

        interactor.execute(new PriceHistoryInputData("url"));

        assertFalse(presenter.success);
        assertEquals("No Price History Found for this product.", presenter.message);
    }

    @Test
    void test_PriceHistoryMapNull() {
        PriceHistory history = new PriceHistory("url", "Product", null);
        dataAccess.setHistoryToReturn(history);

        interactor.execute(new PriceHistoryInputData("url"));

        assertFalse(presenter.success);
        assertEquals("No Price Data Available for this product.", presenter.message);
    }

    @Test
    void test_PriceHistoryMapEmpty() {
        PriceHistory history = new PriceHistory("url", "Product", Collections.emptyMap());
        dataAccess.setHistoryToReturn(history);

        interactor.execute(new PriceHistoryInputData("url"));

        assertFalse(presenter.success);
        assertEquals("No Price Data Available for this product.", presenter.message);
    }

    @Test
    void test_SuccessContinuous7Days() {
        LocalDate today = LocalDate.now();
        Map<LocalDate, Double> map = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            map.put(today.minusDays(i), 10.0 + i);
        }

        PriceHistory history = new PriceHistory("url", "Product", map);
        dataAccess.setHistoryToReturn(history);

        interactor.execute(new PriceHistoryInputData("url"));

        assertTrue(presenter.success);
        assertNotNull(presenter.capturedOutput);

        PriceHistoryOutputData out = presenter.capturedOutput;

        assertEquals(7, out.getDates().size());
        assertEquals(7, out.getPrices().size());
        assertEquals("Product", out.getProductName());
    }

    @Test
    void test_SuccessWithGaps() {
        LocalDate today = LocalDate.now();

        Map<LocalDate, Double> map = new HashMap<>();
        map.put(today.minusDays(6), 10.0);
        map.put(today.minusDays(3), 15.0);
        map.put(today, 20.0);

        PriceHistory history = new PriceHistory("url", "Product", map);
        dataAccess.setHistoryToReturn(history);

        interactor.execute(new PriceHistoryInputData("url"));

        assertTrue(presenter.success);

        PriceHistoryOutputData out = presenter.capturedOutput;
        assertEquals(7, out.getDates().size());
        assertEquals(7, out.getPrices().size());

        out.getPrices().forEach(Objects::requireNonNull);

        assertEquals(10.0, out.getPrices().get(0));
    }
}
