package use_case.RemoveFromCart;

public interface RemoveFromCartOutputBoundary {
    void prepareSuccessView(RemoveFromCartOutputData outputData);
    void prepareFailView(String errorMessage);
}