package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.OrderCategory;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersonalOrder implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private LocalDateTime date;
    private int customerID;
    private int id;
    private boolean finalised = false;
    private OrderCategory orderType;


    public PersonalOrder(int id, int customerID, LocalDateTime date, OrderCategory orderType) {
        this.date = date;
        this.customerID = customerID;
        this.id = id;
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
            // if (contained.getCost() == product.getCost() &&
            //         contained.getProductName().equals(product.getProductName()) &&
            //         Arrays.equals(contained.getManufacturingData(), product.getManufacturingData()) &&
            //         Arrays.equals(contained.getRecipeData(), product.getRecipeData()) &&
            //         Arrays.equals(contained.getMarketingData(), product.getMarketingData()) &&
            //         Arrays.equals(contained.getSafetyData(), product.getSafetyData()) &&
            //         Arrays.equals(contained.getLicensingData(), product.getLicensingData())) {
            if (contained.equals(product) && contained.hashCode() == product.hashCode()) {
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
            // if (contained.getCost() == product.getCost() &&
            //         contained.getProductName().equals(product.getProductName()) &&
            //         Arrays.equals(contained.getManufacturingData(), product.getManufacturingData()) &&
            //         Arrays.equals(contained.getRecipeData(), product.getRecipeData()) &&
            //         Arrays.equals(contained.getMarketingData(), product.getMarketingData()) &&
            //         Arrays.equals(contained.getSafetyData(), product.getSafetyData()) &&
            //         Arrays.equals(contained.getLicensingData(), product.getLicensingData())) {
            if (contained.equals(product) && contained.hashCode() == product.hashCode()) {
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
    public Order copy() {
        Order copy = new PersonalOrder(id, customerID, date, orderType);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    protected double getDiscountRate() {
        return this.orderType.getDiscountType().getRate();
    }

    protected int getDiscountThreshold() {
        return ((BulkDiscount) this.orderType).getDiscountThreshold();
    }


    @Override
    public String generateInvoiceData() {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Brawndo© order!\n");
        sb.append("Your order comes to: $");

        if (orderType.isSubscription()) {
            sb.append(String.format("%,.2f", getTotalCost() / ((SubscriptionOrder) orderType).numberOfShipmentsOrdered()));
            sb.append(" each week, with a total overall cost of: $");
            sb.append(String.format("%,.2f", getTotalCost()));

        } else {
            sb.append(String.format("%,.2f", getTotalCost()));
        }
        // sb.append(String.format("%,.2f", getTotalCost()));
        sb.append("\nPlease see below for details:\n");
        List<Product> keyList = new ArrayList<>(products.keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            sb.append("\tProduct name: ");
            sb.append(product.getProductName());
            sb.append("\tQty: ");
            sb.append(products.get(product));
            sb.append("\tCost per unit: ");
            sb.append(String.format("$%,.2f", product.getCost()));
            sb.append("\tSubtotal: ");
            sb.append(String.format("$%,.2f\n", product.getCost() * products.get(product)));
        }

        return sb.toString();
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

    @Override
    public void finalise() {
        this.finalised = true;
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
        // return String.format("ID:%s $%,.2f", id, getTotalCost());
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
        //                 "Products:\n" +
        //                 "%s" +
        //                 "\tDiscount: -$%,.2f\n" +
        //                 "Total cost: $%,.2f\n",
        //         id,
        //         date.format(DateTimeFormatter.ISO_LOCAL_DATE),
        //         productSB.toString(),
        //         fullCost - discountedCost,
        //         discountedCost
        // );
    }

    protected boolean isFinalised() {
        return finalised;
    }
}
