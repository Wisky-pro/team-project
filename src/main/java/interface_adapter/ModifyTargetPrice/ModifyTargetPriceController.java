package interface_adapter.ModifyTargetPrice;

import use_case.ModifyTargetPrice.ModifyTargetPriceInputBoundary;
import use_case.ModifyTargetPrice.ModifyTargetPriceInputData;

public class ModifyTargetPriceController {
    private final ModifyTargetPriceInputBoundary inputBoundary;
    
    public ModifyTargetPriceController(ModifyTargetPriceInputBoundary boundary) {
        this.inputBoundary = boundary;
    }

    public void execute(String username, String productUrl, int newPrice) {
        ModifyTargetPriceInputData input = new ModifyTargetPriceInputData(username, productUrl , newPrice);
        inputBoundary.execute(input);
    }
}
