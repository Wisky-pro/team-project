package use_case.ModifyTargetPrice;

public interface ModifyTargetPriceDataAccessInterface {
    boolean isValidPrice(int price);

    int getCurrentTargetPrice();
    void setCurrentTargetPrice(int price);
}
