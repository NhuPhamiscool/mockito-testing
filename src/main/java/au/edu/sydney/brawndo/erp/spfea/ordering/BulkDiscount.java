package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.OrderDiscount;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public class BulkDiscount implements OrderDiscount {
    private double discountRate;
    private int discountThreshold;

    public BulkDiscount(double discountRate, int discountThreshold) {
        this.discountRate = discountRate;
        this.discountThreshold = discountThreshold;
    }

    @Override
    public double calculateCostAfterDiscount(Map<Product, Integer> products) {
        // if (total >= this.discountThreshold) {
        //     return total - total * this.discountRate;
        // }
        
        // return total;
        double cost = 0.0;
        for (Product product: products.keySet()) {
            int count = products.get(product);
            if (count >= this.discountThreshold) {
                cost +=  count * product.getCost() * this.discountRate;
            } else {
                cost +=  count * product.getCost();
            }
        }
        
        return cost;
    }

    // @Override
    public int getDiscountThreshold() {
        return this.discountThreshold;
    }
    
    @Override
    public double getRate() {
        return this.discountRate;
    }


    // public String getOrderType() {
    //     return "SUBSCRIPTION ORDER";
    // }
}