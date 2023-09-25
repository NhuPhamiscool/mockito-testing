package au.edu.sydney.brawndo.erp.spfea.products;

public class UnsharedConcreteFlyweight implements Flyweight {
    private String name;
    private double cost;

    public UnsharedConcreteFlyweight(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    public double[] getManufacturingData(){
        return new double[]{};
    }

    public double[] getRecipeData() {
        return new double[]{};

    }

    public double[] getMarketingData() {
        return new double[]{};

    }

    public double[] getSafetyData() {
        return new double[]{};

    }

    public double[] getLicensingData() {
        return new double[]{};

    }
}