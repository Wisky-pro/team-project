package interface_adapter.PriceHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ViewModel for displaying price history graph data.
 */
public class PriceHistoryViewModel {
    private List<Date> dates;
    private List<Double> prices;
    private String productName;
    private String productUrl;
    private String message;

    public PriceHistoryViewModel() {
        this.dates = new ArrayList<>();
        this.prices = new ArrayList<>();
        this.productName = "";
        this.message = "";
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() { return productUrl; }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage() { return this.message; }
}
