package com.example.olli.financeproject;

/**
 * Created by olli on 6.9.2016.
 */
public class StockInformation {

    double price, changePercentage;

    public StockInformation(){}

    public StockInformation(double price, double changePercentage) {
        this.price = price;
        this.changePercentage = changePercentage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }


}
