package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.auth.AuthModule;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;



public class UnitOfWork {
    private TestDatabase database = TestDatabase.getInstance();
    // private Map<AuthToken, List<Order>> finalisedOrders;
    private List<Order> finalisedOrders;
    private int nextOrderID = 1;

    public UnitOfWork() {
        this.database = database;
        this.finalisedOrders = new ArrayList<>();
    }

    public int getNextOrderID() {
        return nextOrderID++;
    }

    public Order getOrder (AuthToken token, int orderID) {
        // for (Map.Entry<AuthToken, List<Order>> entry : finalisedOrders.entrySet()) {
        //     if (entry.getKey() == token) {
        //         for (Order o : entry.getValue()) {
        //             if (o.getOrderID() == orderID) {
        //                 return o;
        //             }
        //         }
        //     }
        // }
        // return null;
        if (!AuthModule.authenticate(token)) {
            throw new SecurityException("Invalid authorisation");
        }

        for (Order o : finalisedOrders) {
            if (o.getOrderID() == orderID) {
                return o.copy();
            }
        }
        return null;
        // finalisedOrders.add(order);
    }

    public boolean removeOrder (AuthToken token, int orderID) {
        // for (Map.Entry<AuthToken, List<Order>> entry : finalisedOrders.entrySet()) {
        //     if (entry.getKey() == token) {
        //         for (Order o : entry.getValue()) {
        //             if (o.getOrderID() == orderID) {
        //                 finalisedOrders.get(token).remove(o);
        //                 return true;
        //             }
        //         }
        //     }
        // }
        // return false;
        if (!AuthModule.authenticate(token)) {
            throw new SecurityException("Invalid authorisation");
        }

        for (Order iter : finalisedOrders) {
            if (iter.getOrderID() == orderID) {
                finalisedOrders.remove(iter);
                return true;
            }
        }
        return false;
        // finalisedOrders.add(order);
    }

    public void addOrder(AuthToken token, Order order) {
        // if (!AuthModule.authenticate(token)) {
        //     throw new SecurityException("Invalid authorisation");
        // }

        for (Order iter : finalisedOrders) {
            if (iter.getOrderID() == order.getOrderID()) {
                finalisedOrders.remove(iter);
                break;
            }
        }
        finalisedOrders.add(order.copy());
        
        // if (!finalisedOrders.containsKey(token)) {
        //     List<Order> o = new ArrayList<>();
        //     o.add(order);
        //     finalisedOrders.put(token, o);
            
        // } else {
        //     if (!finalisedOrders.get(token).contains(order)) {
        //         finalisedOrders.get(token).add(order);
        //     }
        // }
    }

    // public List<Order> getOrders(AuthToken token) {
    //     // if (!AuthModule.authenticate(token)) {
    //     //     throw new SecurityException("Invalid authorisation");
    //     // }

    //     List<Order> result = new ArrayList<>();
    //     for (Order order: finalisedOrders) {
    //         result.add(order.copy());
    //     }
    //     return result;
    // }

    public void commit(AuthToken token) {
        // for (Map.Entry<AuthToken, List<Order>> entry : finalisedOrders.entrySet()) {
        //     for (Order o : entry.getValue()) {
        //         database.saveOrder(entry.getKey(), o);
        //     }
        // }

        for (Order o : finalisedOrders) {
            database.saveOrder(token, o);
        }

        finalisedOrders.clear();
    }
}