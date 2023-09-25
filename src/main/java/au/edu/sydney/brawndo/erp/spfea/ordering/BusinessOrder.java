package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.OrderCategory;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BusinessOrder implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private final int id;
    private LocalDateTime date;
    private int customerID;
    private boolean finalised = false;
    private OrderCategory orderType;

    public BusinessOrder(int id, int customerID, LocalDateTime date, OrderCategory orderType) {
        this.id = id;
        this.customerID = customerID;
        this.date = date;
        this.orderType = orderType;
    }

    @Override
    public LocalDateTime getOrderDate() {
        return date;
    }

    @Override
    public void setProduct(Product product, int qty) {
        if (finalised) throw new IllegalStateException("Order was already finalised.");

        // We can't rely on like products having the same object identity since they get
        // rebuilt over the network, so we had to check for presence and same values

        for (Product contained: products.keySet()) {
            if (contained.equals(product) && contained.hashCode() == product.hashCode()) {
            // if (contained.getCost() == product.getCost() &&
            //     contained.getProductName().equals(product.getProductName()) &&
            //         Arrays.equals(contained.getManufacturingData(), product.getManufacturingData()) &&
            //         Arrays.equals(contained.getRecipeData(), product.getRecipeData()) &&
            //         Arrays.equals(contained.getMarketingData(), product.getMarketingData()) &&
            //         Arrays.equals(contained.getSafetyData(), product.getSafetyData()) &&
            //         Arrays.equals(contained.getLicensingData(), product.getLicensingData())) {
                product = contained;
                break;
            }
        }

        products.put(product, qty);
    }

    @Override
    public Set<Product> getAllProducts() {
        return products.keySet();
    }

    @Override
    public int getProductQty(Product product) {
        // We can't rely on like products having the same object identity since they get
        // rebuilt over the network, so we had to check for presence and same values

        for (Product contained: products.keySet()) {
            if (contained.equals(product) && contained.hashCode() == product.hashCode()) {
            // if (contained.getCost() == product.getCost() &&
            //         contained.getProductName().equals(product.getProductName()) &&
            //         Arrays.equals(contained.getManufacturingData(), product.getManufacturingData()) &&
            //         Arrays.equals(contained.getRecipeData(), product.getRecipeData()) &&
            //         Arrays.equals(contained.getMarketingData(), product.getMarketingData()) &&
            //         Arrays.equals(contained.getSafetyData(), product.getSafetyData()) &&
            //         Arrays.equals(contained.getLicensingData(), product.getLicensingData())) {
                product = contained;
                break;
            }
        }
        Integer result = products.get(product);
        return null == result ? 0 : result;
    }

    @Override
    public int getCustomer() {
        return customerID;
    }

    @Override
    public void finalise() {
        this.finalised = true;
    }

    // modified
    protected double getDiscountRate() {
        return this.orderType.getDiscountType().getRate();
    }

    protected int getDiscountThreshold() {
        return ((BulkDiscount) this.orderType).getDiscountThreshold();
    }

    @Override
    public Order copy() {
        Order copy = new BusinessOrder(id, customerID, date, orderType);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    

    @Override
    public String shortDesc() {
        String output;
        if (orderType.isSubscription()) {
            output = String.format("ID:%s $%,.2f per shipment, $%,.2f total", getOrderID(), getTotalCost() / ((SubscriptionOrder) orderType).numberOfShipmentsOrdered(), getTotalCost() / ((SubscriptionOrder) orderType).numberOfShipmentsOrdered());
        } else {
            output = String.format("ID:%s $%,.2f", id, getTotalCost());
        }
        return output;
    }

    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(products.keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * products.get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    products.get(product),
                    product.getCost(),
                    subtotal));
        }
        String output;
        if (orderType.isSubscription()) {
            discountedCost = discountedCost / ((SubscriptionOrder) orderType).numberOfShipmentsOrdered();
            output = String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of shipments: %d\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                ((SubscriptionOrder) orderType).numberOfShipmentsOrdered(),
                productSB.toString(),
                // discountedCost,
                fullCost - discountedCost,
                discountedCost,
                getTotalCost());

        } else {
            output = String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        // orderType.isSubscription() ? "Number of shipments: %d\n" : "" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        // orderType.isSubscription() ? "Recurring cost: $%,.2f\n" : "" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost,
                getTotalCost());

        }
        return output;
        

        // return String.format(finalised ? "" : "*NOT FINALISED*\n" +
        //                 "Order details (id #%d)\n" +
        //                 "Date: %s\n" +
        //                 // orderType.isSubscription() ? "Number of shipments: %d\n" : "" +
        //                 "Products:\n" +
        //                 "%s" +
        //                 "\tDiscount: -$%,.2f\n" +
        //                 // orderType.isSubscription() ? "Recurring cost: $%,.2f\n" : "" +
        //                 "Total cost: $%,.2f\n",
        //         id,
        //         date.format(DateTimeFormatter.ISO_LOCAL_DATE),
        //         productSB.toString(),
        //         fullCost - discountedCost,
        //         discountedCost,
        //         getTotalCost()
        //         );
    }

    @Override
    public String generateInvoiceData() {
        String output;
        if (orderType.isSubscription()) {
            output = String.format("Your business account will be charged: $%,.2f each week, with a total overall cost of: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", getTotalCost() / ((SubscriptionOrder) orderType).numberOfShipmentsOrdered(), getTotalCost());
        } else {
            output = String.format("Your business account has been charged: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", getTotalCost());
        }
        return output;
        // return String.format("Your business account has been charged: $%,.2f" +
        //         "\nPlease see your Brawndo© merchandising representative for itemised details.", getTotalCost());
    }

    @Override
    public double getTotalCost() {
        double totalCost = orderType.calculateCostAfterDiscount(products);
        
        return totalCost;
    }

    protected Map<Product, Integer> getProducts() {
        return products;
    }

    @Override
    public int getOrderID() {
        return id;
    }

    protected boolean isFinalised() {
        return finalised;
    }
}
