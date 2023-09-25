package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;


public class MerchandiserContact extends Contact {
    private Contact nextContactMethod;

    public MerchandiserContact () {
        // super(nextContactMethod);
    }

    public Contact setNext(Contact c) {
        this.nextContactMethod = c;
        return this.nextContactMethod;
    }

    @Override
    public boolean handle(AuthToken token, Customer customer, String data) {
        String merchandiser = customer.getMerchandiser();
        String businessName = customer.getBusinessName();

        if (null != merchandiser && null != businessName) {
            Merchandiser.sendInvoice(token, customer.getfName(), customer.getlName(), data, merchandiser,businessName);
            return true;
        }

        return this.passToNextContactMethod(token, customer, data, nextContactMethod);

    }
}