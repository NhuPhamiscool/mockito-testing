package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;


public class SMSContact extends Contact {
    private Contact nextContactMethod;

    public SMSContact () {
        // super(nextContactMethod);
    }

    public Contact setNext(Contact c) {
        this.nextContactMethod = c;
        return this.nextContactMethod;
    }

    @Override
    public boolean handle(AuthToken token, Customer customer, String data) {
        String smsPhone = customer.getPhoneNumber();
        if (null != smsPhone) {
            SMS.sendInvoice(token, customer.getfName(), customer.getlName(), data, smsPhone);
            return true;
        }

        return this.passToNextContactMethod(token, customer, data, nextContactMethod);

    }
}