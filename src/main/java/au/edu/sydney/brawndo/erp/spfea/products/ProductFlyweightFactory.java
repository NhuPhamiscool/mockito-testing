package au.edu.sydney.brawndo.erp.spfea.products;


import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class ProductFlyweightFactory {
    // Keep a "cache" of flyweights
    private Map<String, ProductFlyweight> flyweights;

    public ProductFlyweightFactory() {
        this.flyweights = new HashMap<>();
    }

    // If the object exists we grab it, if not we make it, save it,
    // then return it
    public ProductFlyweight getProductFlyWeight(String name, double cost, double[][] sharedData) {
        String key = name + String.valueOf(cost);

        if (!flyweights.containsKey(key)) {
            // Create and save
            flyweights.put(key, new ProductFlyweight(name, cost, sharedData));
        }

        return flyweights.get(key);
    }
}