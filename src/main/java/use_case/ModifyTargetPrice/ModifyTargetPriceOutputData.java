package use_case.ModifyTargetPrice;

public class ModifyTargetPriceOutputData {
    private final int updatedTargetPrice;

    public ModifyTargetPriceOutputData(int updatedTargetPrice) {
        this.updatedTargetPrice = updatedTargetPrice;
    }

    public int getModifiedTargetPrice() {
        return this.updatedTargetPrice;
    }

    @Override
    public String toString() {
        return "Updated target price: " + this.updatedTargetPrice;
    }
}
