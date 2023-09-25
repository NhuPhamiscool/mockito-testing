package au.edu.sydney.brawndo.erp.ordering;
import java.util.Map;


public interface OrderCategory {
    double calculateCostAfterDiscount(Map<Product, Integer> p); 
    OrderDiscount getDiscountType();
    boolean isSubscription();
    // double getRate();
    // String getOrderType();
}