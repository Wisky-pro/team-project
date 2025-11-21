package use_case.AddToCart;

public interface AddToCartOutputBoundary {

    void prepareSuccessView(AddToCartOutputData outputData);

    void prepareFailView(String errorMessage);
}
