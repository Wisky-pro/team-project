package use_case.PriceHistory;

/**
 Output boundary for Use Case 7.
 */
public interface PriceHistoryOutputBoundary {

    void prepareSuccessView(PriceHistoryOutputData outputData);

    void prepareFailView(String errorMessage);
}
