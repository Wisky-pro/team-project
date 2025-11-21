package entity;

/**
  A simple Commodity entity used in use cases 9 and 10.
 */
public class Commodity {

    private final String name;
    private final double currentPrice;

    public Commodity(String name, double currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }
}