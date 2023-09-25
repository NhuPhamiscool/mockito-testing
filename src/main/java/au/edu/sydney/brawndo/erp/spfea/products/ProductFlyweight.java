package au.edu.sydney.brawndo.erp.spfea.products;

import java.util.List;
import java.util.ArrayList;

public class ProductFlyweight implements Flyweight {
    private String name;
    private double cost;
    private double[][] sharedData;

    public ProductFlyweight(String name, double cost, double[][] sharedData) {
        this.name = name;
        this.cost = cost;
        this.sharedData = sharedData;

    }

    public String getName() {
        return "";
    }

    public double getCost() {
        return 0.0;
    }

    public double[] getManufacturingData() {
        // double[] arr = new double[sharedData.get(0).size()];
        // arr = sharedData.get(0).toArray(arr);
  
        return this.sharedData[0];
    }

    public double[] getRecipeData() {
        // double[] arr = new double[sharedData.get(1).size()];
        // arr = sharedData[1].toArray(arr);
  
        return this.sharedData[1];
    }

    public double[] getMarketingData() {
        // double[] arr = new double[sharedData.get(2).size()];
        // arr = sharedData[2].toArray(arr);
  
        return this.sharedData[2];
    }

    public double[] getSafetyData() {
        // double[] arr = new double[sharedData.get(3).size()];
        // arr = sharedData[3].toArray(arr);
  
        return this.sharedData[3];
    }

    public double[] getLicensingData() {
        // double[] arr = new double[sharedData.get(4).size()];
        // arr = sharedData[4].toArray(arr);
  
        return this.sharedData[4];
    }
}