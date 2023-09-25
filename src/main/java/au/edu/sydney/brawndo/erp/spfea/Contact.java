package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;



public abstract class Contact {
    // private Contact nextContactMethod;

    public Contact() {
        // this.nextContactMethod = nextContactMethod;
    }

    protected boolean passToNextContactMethod(AuthToken token, Customer customer, String data, Contact nextContactMethod) {
        if (nextContactMethod != null) {
            return nextContactMethod.handle(token, customer, data);
        }
        return false;
    }

    public abstract boolean handle(AuthToken token, Customer customer, String data);
    public abstract Contact setNext(Contact c);
}