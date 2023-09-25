package au.edu.sydney.brawndo.erp.ordering;

import java.util.Map;


public interface OrderDiscount {
    double calculateCostAfterDiscount(Map<Product, Integer> p);
    double getRate();
    // String getOrderType();
}