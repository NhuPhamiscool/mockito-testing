package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
// import org.apache.commons.lang3.ArrayUtils;

public class ProductImpl implements Product {

    private final String name;
    // private final double[] manufacturingData;
    private final double cost;
    // private double[] recipeData;
    // private double[] marketingData;
    // private double[] safetyData;
    // private double[] licensingData;

    private double[][] sharedData;
    private ProductFlyweightFactory factory = new ProductFlyweightFactory();
    // private ProductFlyweight productFlyweight;
    private Flyweight productFlyweight;
    private Flyweight nonSharedFlyweight;



    public ProductImpl(String name,
                       double cost,
                       double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        this.name = name;
        this.cost = cost;
        sharedData = new double[5][];

        sharedData[0] = manufacturingData;
        sharedData[1] = recipeData;
        sharedData[2] = marketingData;
        sharedData[3] = safetyData;
        sharedData[4] = licensingData; 

        this.productFlyweight = factory.getProductFlyWeight(name, cost, sharedData);
        this.nonSharedFlyweight = new UnsharedConcreteFlyweight(name, cost);
        // this.manufacturingData = manufacturingData;
        // this.recipeData = recipeData;
        // this.marketingData = marketingData;
        // this.safetyData = safetyData;
        // this.licensingData = licensingData;
    }

    @Override
    public String getProductName() {
        // return name;
        return this.nonSharedFlyweight.getName();
    }

    @Override
    public double getCost() {
        // return cost;
        return this.nonSharedFlyweight.getCost();
    }

    @Override
    public double[] getManufacturingData() {
        // return manufacturingData;
        return this.productFlyweight.getManufacturingData();
    }

    @Override
    public double[] getRecipeData() {
        // return recipeData;
        return this.productFlyweight.getRecipeData();
    }

    @Override
    public double[] getMarketingData() {
        // return marketingData;
        return this.productFlyweight.getMarketingData();
    }

    @Override
    public double[] getSafetyData() {
        // return safetyData;
        return this.productFlyweight.getSafetyData();
    }

    @Override
    public double[] getLicensingData() {
        // return licensingData;
        return this.productFlyweight.getLicensingData();
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }

    @Override
    public boolean equals(Object o) {
        // assum manufaturing act as product version
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return name.equals(product.getProductName())
                && sharedData[0].equals(product.getManufacturingData());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + sharedData[0].hashCode();
        return result;
    }
}
