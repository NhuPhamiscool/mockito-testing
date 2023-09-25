package au.edu.sydney.brawndo.erp.spfea.products;


import java.util.List;
import java.util.ArrayList;


public interface Flyweight {
    public String getName();
    public double getCost();
    public double[] getManufacturingData();

    public double[] getRecipeData();

    public double[] getMarketingData();

    public double[] getSafetyData();

    public double[] getLicensingData();
}