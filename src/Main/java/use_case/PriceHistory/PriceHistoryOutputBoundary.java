package use_case.PriceHistory;

/**
 Output boundary for Use Case 7.
 */
public interface PriceHistoryOutputBoundary {
    void prepareSuccessView(PriceHistoryOutputData OutputData);
    void prepareFailureView(String ErrorMessage);
}
