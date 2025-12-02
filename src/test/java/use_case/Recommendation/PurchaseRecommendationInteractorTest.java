package use_case.Recommendation;

import org.junit.jupiter.api.Test;

import java.util.List;

import java.util.ArrayList;
import java.util.Objects;


import static org.junit.jupiter.api.Assertions.*;

class PurchaseRecommendationInteractorTest {

    private static class TestDataAccess implements PurchaseRecommendationDataAccessInterface {

        @Override
        public boolean commodityExists(String commodityName) {
            // Only laptop exist
            return Objects.equals(commodityName, "Laptop")
                    || Objects.equals(commodityName, "GTX 1080")
                    || Objects.equals(commodityName, "i7 4790")
                    || Objects.equals(commodityName, "AMD Raden RX 580")
                    || Objects.equals(commodityName, "EmptyHistory");
        }

        @Override
        public List<Double> getPriceHistory(String commodityName) {
            if ("Laptop".equals(commodityName)) {
                List<Double> history = new ArrayList<>();
                // history price：100, 200, 300  -> mean = 200
                history.add(100.0);
                history.add(200.0);
                history.add(300.0);
                return history;
            }else if ("i7 4790".equals(commodityName)) {
                List<Double> history = new ArrayList<>();
                history.add(150.0);
                history.add(200.0);
                history.add(250.0);
                return history;
            } else if ("AMD Raden RX 580".equals(commodityName)) {
                List<Double> history = new ArrayList<>();
                history.add(300.0);
                history.add(350.0);
                history.add(400.0);
                return history;
            } else if ("EmptyHistory".equals(commodityName)) {
                return new ArrayList<>();
            }

            return null;
        }

        @Override
        public double getCurrentPrice(String commodityName) {
            if ("Laptop".equals(commodityName)) {
                return 150.0;
            } else if ("GTX 1080".equals(commodityName)){
                return 200.0;
            } else if ("i7 4790".equals(commodityName)) {
                return 300.0;
            } else if ("AMD Raden RX 580".equals(commodityName)) {
                return 350.0;
            } else{
            return 0.0;
            }
        }
    }

    private static class TestPresenter implements PurchaseRecommendationOutputBoundary {

        PurchaseRecommendationOutputData outputData;
        String errorMessage;

        @Override
        public void prepareSuccessView(PurchaseRecommendationOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    /**
     * laptop exist and lower than mean; recommend
     */
    @Test
    void testRecommendWhenPriceBelowMean() {
        // arrange
        TestDataAccess dataAccess = new TestDataAccess();
        TestPresenter presenter = new TestPresenter();
        PurchaseRecommendationInteractor interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        PurchaseRecommendationInputData input =
                new PurchaseRecommendationInputData("Laptop");

        // act
        interactor.execute(input);

        // assert
        assertNull(presenter.errorMessage);
        assertNotNull(presenter.outputData);
        assertEquals("Laptop", presenter.outputData.getCommodityName());
        assertEquals(150.0, presenter.outputData.getCurrentPrice());
        assertEquals(200.0, presenter.outputData.getMeanPrice());
        assertEquals("Price is lower than mean, recommend to buy.",
                presenter.outputData.getSuggestionMessage());
    }
    @Test
    void testRecommendWhenPriceAboveMean() {
        // arrange
        TestDataAccess dataAccess = new TestDataAccess();
        TestPresenter presenter = new TestPresenter();
        PurchaseRecommendationInteractor interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        PurchaseRecommendationInputData input =
                new PurchaseRecommendationInputData("i7 4790");

        // act
        interactor.execute(input);

        // assert
        assertNull(presenter.errorMessage);
        assertNotNull(presenter.outputData);
        assertEquals("i7 4790", presenter.outputData.getCommodityName());
        assertEquals(300.0, presenter.outputData.getCurrentPrice());
        assertEquals(200.0, presenter.outputData.getMeanPrice());
        assertEquals("Price is equal or higher than mean, NOT recommend to buy.",
                presenter.outputData.getSuggestionMessage());
    }

    @Test
    void testRecommendWhenPriceEqualsMean() {
        // arrange
        TestDataAccess dataAccess = new TestDataAccess();
        TestPresenter presenter = new TestPresenter();
        PurchaseRecommendationInteractor interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        PurchaseRecommendationInputData input =
                new PurchaseRecommendationInputData("AMD Raden RX 580");

        // act
        interactor.execute(input);

        // assert
        assertNull(presenter.errorMessage);
        assertNotNull(presenter.outputData);
        assertEquals("AMD Raden RX 580", presenter.outputData.getCommodityName());
        assertEquals(350.0, presenter.outputData.getCurrentPrice());
        assertEquals(350.0, presenter.outputData.getMeanPrice());
        assertEquals("Price is equal or higher than mean, NOT recommend to buy.",
                presenter.outputData.getSuggestionMessage());
    }

    @Test
    void testNonExistingHistoryData() {
        // arrange
        TestDataAccess dataAccess = new TestDataAccess();
        TestPresenter presenter = new TestPresenter();
        PurchaseRecommendationInteractor interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        PurchaseRecommendationInputData input =
                new PurchaseRecommendationInputData("GTX 1080");

        // act
        interactor.execute(input);

        // assert
        assertNotNull(presenter.errorMessage);
        assertEquals("No price history for GTX 1080.", presenter.errorMessage);
        assertNull(presenter.outputData);
    }

    /**
     * give a string does not exist: should fail
     */
    @Test
    void testNonExistingCommodityGivesError() {
        // arrange
        TestDataAccess access = new TestDataAccess();
        TestPresenter presenter = new TestPresenter();
        PurchaseRecommendationInteractor interactor =
                new PurchaseRecommendationInteractor(access, presenter);

        PurchaseRecommendationInputData input =
                new PurchaseRecommendationInputData("RTX 5090"); // 不存在的名字

        // act
        interactor.execute(input);

        // assert
        assertNull(presenter.outputData);
        assertEquals("Commodity RTX 5090 not found.", presenter.errorMessage);// no output
        assertNotNull(presenter.errorMessage);       // has error message
    }

    @Test
    void testExistingCommodityWithEmptyHistory() {
        // arrange
        TestDataAccess dataAccess = new TestDataAccess();
        TestPresenter presenter = new TestPresenter();
        PurchaseRecommendationInteractor interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        PurchaseRecommendationInputData input =
                new PurchaseRecommendationInputData("EmptyHistory");

        // act
        interactor.execute(input);

        // assert
        assertNull(presenter.outputData);
        assertNotNull(presenter.errorMessage);
        assertEquals("No price history for EmptyHistory.", presenter.errorMessage);
    }
}