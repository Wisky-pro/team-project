package use_case.ModifyTargetPrice;

public interface ModifyTargetPriceOutputBoundary {
    void prepareSuccessView(ModifyTargetPriceOutputData data);

    void prepareFailView(String message);
}
