package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;


public class MailContact extends Contact {
    private Contact nextContactMethod;

    public MailContact () {
        // super(nextContactMethod);
    }

    public Contact setNext(Contact c) {
        this.nextContactMethod = c;
        return this.nextContactMethod;
    }


    @Override
    public boolean handle(AuthToken token, Customer customer, String data) {
        String address = customer.getAddress();
        String suburb = customer.getSuburb();
        String state = customer.getState();
        String postcode = customer.getPostCode();

        if (null != address && null != suburb &&
            null != state && null != postcode) {
            Mail.sendInvoice(token, customer.getfName(), customer.getlName(), data, address, suburb, state, postcode);
            return true;
        }
        return this.passToNextContactMethod(token, customer, data, nextContactMethod);

    }
}