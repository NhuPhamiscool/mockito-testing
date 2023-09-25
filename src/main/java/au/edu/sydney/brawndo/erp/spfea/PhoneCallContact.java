package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class PhoneCallContact extends Contact {
    private Contact nextContactMethod;

    public PhoneCallContact () {
        // super(nextContactMethod);
    }

    public Contact setNext(Contact c) {
        this.nextContactMethod = c;
        return this.nextContactMethod;
    }


    @Override
    public boolean handle(AuthToken token, Customer customer, String data) {
        String phone = customer.getPhoneNumber();
        if (null != phone) {
            PhoneCall.sendInvoice(token, customer.getfName(), customer.getlName(), data, phone);
            return true;
        }

        return this.passToNextContactMethod(token, customer, data, nextContactMethod);

    }
}