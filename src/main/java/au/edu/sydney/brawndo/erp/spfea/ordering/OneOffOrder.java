package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.OrderCategory;
import au.edu.sydney.brawndo.erp.ordering.OrderDiscount;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;


public class OneOffOrder implements OrderCategory {
    private OrderDiscount orderDiscount;

    public OneOffOrder (OrderDiscount orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public boolean isSubscription() {
        return false;
    }

    public double calculateCostAfterDiscount(Map<Product, Integer> products) {
        // double cost = 0.0;
        // for (Product product: products.keySet()) {
        //     int count = products.get(product);
        //     cost += count * product.getCost();
        // }
        double costAfterDis = orderDiscount.calculateCostAfterDiscount(products);
        return costAfterDis;
    }

    public OrderDiscount getDiscountType() {
        // if (orderDiscount instanceof BulkDiscount) {
        //     return (BulkDiscount) this.orderDiscount;
        // }
        return this.orderDiscount;
    }

    public String getOrderType() {
        return "SUBSCRIPTION ORDER";
    }
}