package interface_adapter.PriceHistory;

import java.util.Date;
import java.util.List;

public class PriceHistoryViewModel {
    private List<Date> dates;
    private List<Double> prices;
    private String productName;
    private String errorMessage;
    public PriceHistoryViewModel(List<Date> dates, List<Double> prices) {}

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

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
