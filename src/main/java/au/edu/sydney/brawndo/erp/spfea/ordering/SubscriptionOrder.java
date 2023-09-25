package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.OrderCategory;
import au.edu.sydney.brawndo.erp.ordering.OrderDiscount;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public class SubscriptionOrder implements OrderCategory {
    private OrderDiscount orderDiscount;
    private int numShipments;

    public SubscriptionOrder (OrderDiscount orderDiscount, int numShipments) {
        this.orderDiscount = orderDiscount;
        this.numShipments = numShipments;
    }

    public double calculateCostAfterDiscount(Map<Product, Integer> products) {
        // double cost = 0.0;
        // for (Product product: products.keySet()) {
        //     int count = products.get(product);
        //     cost += count * product.getCost();
        // }
        double costAfterDis = this.orderDiscount.calculateCostAfterDiscount(products);
        return costAfterDis * this.numShipments;
    }

    
    public int numberOfShipmentsOrdered() {
        return this.numShipments;
    }

    // protected int getDiscountThreshold() {
    //     return this.orderType.getThreshold();
    // }

    public boolean isSubscription() {
        return true;
    }


    public OrderDiscount getDiscountType() {
        return this.orderDiscount;
    }

    public String getOrderType() {
        return "SUBSCRIPTION ORDER";
    }
}