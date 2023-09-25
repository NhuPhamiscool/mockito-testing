package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;


public class EmailContact extends Contact {
    private Contact nextContactMethod;

    public EmailContact () {
        // super(nextContactMethod);
    }

    public Contact setNext(Contact c) {
        this.nextContactMethod = c;
        return this.nextContactMethod;
    }


    @Override
    public boolean handle(AuthToken token, Customer customer, String data) {
        String email = customer.getEmailAddress();
        if (null != email) {
            Email.sendInvoice(token, customer.getfName(), customer.getlName(), data, email);
            return true;
        }
        return this.passToNextContactMethod(token, customer, data, nextContactMethod);

    }
}