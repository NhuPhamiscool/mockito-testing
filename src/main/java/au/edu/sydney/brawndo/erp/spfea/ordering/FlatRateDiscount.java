package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.OrderDiscount;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public class FlatRateDiscount implements OrderDiscount {
    private double discountRate;

    public FlatRateDiscount(double discountRate) {
        this.discountRate = discountRate;
    }
    
    @Override
    public double calculateCostAfterDiscount(Map<Product, Integer> products) {
        // return total - total * this.discountRate;
        double cost = 0.0;
        for (Product product: products.keySet()) {
            cost +=  products.get(product) * product.getCost() * this.discountRate;
        }
        return cost;
    }

    @Override
    public double getRate() {
        return this.discountRate;
    }


    public String getOrderType() {
        return "SUBSCRIPTION ORDER";
    }
}