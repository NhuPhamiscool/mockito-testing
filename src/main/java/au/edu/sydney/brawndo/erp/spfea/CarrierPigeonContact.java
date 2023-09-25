package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;


public class CarrierPigeonContact extends Contact {
    private Contact nextContactMethod;

    public CarrierPigeonContact () {
        // super(nextContactMethod);
    }

    public Contact setNext(Contact c) {
        this.nextContactMethod = c;
        return this.nextContactMethod;
    }

    @Override
    public boolean handle(AuthToken token, Customer customer, String data) {
        String pigeonCoopID = customer.getPigeonCoopID();
        if (null != pigeonCoopID) {
            CarrierPigeon.sendInvoice(token, customer.getfName(), customer.getlName(), data, pigeonCoopID);
            return true;
        }
        return this.passToNextContactMethod(token, customer, data, this.nextContactMethod);
    }
}