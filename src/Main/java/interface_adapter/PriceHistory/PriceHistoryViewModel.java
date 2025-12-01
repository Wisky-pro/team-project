package interface_adapter.PriceHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceHistoryViewModel {
    private List<Date> dates;
    private List<Double> prices;
    private String productName;
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

    public void setMessage(String errorMessage){
        this.message = errorMessage;
    }

    public String getMessage() {
        return this.message;
    }
}
