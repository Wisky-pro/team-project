package interface_adapter.ModifyTargetPrice;

import use_case.ModifyTargetPrice.ModifyTargetPriceInputBoundary;
import use_case.ModifyTargetPrice.ModifyTargetPriceInputData;
import entity.CartItem;

public class ModifyTargetPriceController {
    private final ModifyTargetPriceInputBoundary inputBoundary;
    
    public ModifyTargetPriceController(ModifyTargetPriceInputBoundary boundary) {
        this.inputBoundary = boundary;
    }

    public void updatePrice(CartItem item, int newPrice) {
        ModifyTargetPriceInputData input = new ModifyTargetPriceInputData(item, newPrice);

        inputBoundary.execute(input);
    }
}
