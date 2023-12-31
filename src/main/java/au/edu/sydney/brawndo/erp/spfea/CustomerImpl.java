package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class CustomerImpl implements Customer {

    private final int id;
    private String fName;
    private String lName;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String suburb;
    private String state;
    private String postCode;
    private String merchandiser;
    private String businessName;
    private String pigeonCoopID;
    private AuthToken token;

    public CustomerImpl(AuthToken token, int id) {

        this.id = id;
        this.token = token;
        // this.fName = TestDatabase.getInstance().getCustomerField(token, id, "fName");
        // this.lName = TestDatabase.getInstance().getCustomerField(token, id, "lName");
        // this.phoneNumber = TestDatabase.getInstance().getCustomerField(token, id, "phoneNumber");
        // this.emailAddress = TestDatabase.getInstance().getCustomerField(token, id, "emailAddress");
        // this.address = TestDatabase.getInstance().getCustomerField(token, id, "address");
        // this.suburb = TestDatabase.getInstance().getCustomerField(token, id, "suburb");
        // this.state = TestDatabase.getInstance().getCustomerField(token, id, "state");
        // this.postCode = TestDatabase.getInstance().getCustomerField(token, id, "postCode");
        // this.merchandiser = TestDatabase.getInstance().getCustomerField(token, id, "merchandiser");
        // this.businessName = TestDatabase.getInstance().getCustomerField(token, id, "businessName");
        // this.pigeonCoopID = TestDatabase.getInstance().getCustomerField(token, id, "pigeonCoopID");
    }

    public int getId() {
        return id;
    }

    @Override
    public String getfName() {
        if (fName == null) {
            this.fName = TestDatabase.getInstance().getCustomerField(this.token, this.id, "fName");
        }
        return fName;
    }

    @Override
    public String getlName() {
        if (this.lName == null) {
            this.lName = TestDatabase.getInstance().getCustomerField(this.token, this.id, "lName");
        }
        return lName;
    }

    @Override
    public String getPhoneNumber() {
        if (this.phoneNumber == null) {
            this.phoneNumber = TestDatabase.getInstance().getCustomerField(this.token, this.id, "phoneNumber");
        }
        return phoneNumber;
    }

    @Override
    public String getEmailAddress() {
        if (this.emailAddress == null) {
            this.emailAddress = TestDatabase.getInstance().getCustomerField(this.token, this.id, "emailAddress");
        }
        return emailAddress;
    }

    @Override
    public String getAddress() {
        if (this.address == null) {
            this.address = TestDatabase.getInstance().getCustomerField(this.token, this.id, "address");
        }
        return address;
    }

    @Override
    public String getSuburb() {
        if (this.suburb == null) {
            this.suburb = TestDatabase.getInstance().getCustomerField(this.token, this.id, "suburb");
        }
        return suburb;
    }

    @Override
    public String getState() {
        if (this.state == null) {
            this.state = TestDatabase.getInstance().getCustomerField(this.token, this.id, "state");
        }
        return state;
    }

    @Override
    public String getPostCode() {
        if (this.postCode == null) {
            this.postCode = TestDatabase.getInstance().getCustomerField(this.token, this.id, "postCode");
        }
        return postCode;
    }

    @Override
    public String getMerchandiser() {
        if (this.merchandiser == null) {
            this.merchandiser = TestDatabase.getInstance().getCustomerField(this.token, this.id, "merchandiser");
        }
        return this.merchandiser;
    }

    @Override
    public String getBusinessName() {
        if (this.businessName == null) {
            this.businessName = TestDatabase.getInstance().getCustomerField(this.token, this.id, "businessName");
        }
        return this.businessName;
    }

    @Override
    public String getPigeonCoopID() {
        if (this.pigeonCoopID == null) {
            this.pigeonCoopID = TestDatabase.getInstance().getCustomerField(this.token, this.id, "pigeonCoopID");
        }
        return this.pigeonCoopID;
    }
}

