package use_case.AddToCart;

import entity.ProductData;

public interface ProductDataAccessInterface {

    ProductData getByUrl(String productUrl);
}
