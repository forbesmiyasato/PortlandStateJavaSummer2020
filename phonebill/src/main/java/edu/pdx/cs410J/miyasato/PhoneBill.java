package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall>{

    private ArrayList<PhoneCall> pPhoneCalls;
    private String pCustomerName;

    public PhoneBill () {
        pPhoneCalls = new ArrayList<PhoneCall>();
    }

    public PhoneBill (String customerName) {
        pCustomerName = customerName;
        pPhoneCalls = new ArrayList<PhoneCall>();
    }
    public String getCustomer() {
        return pCustomerName;
    }

    public void addPhoneCall(PhoneCall var1) {
        if (var1 != null) {
            pPhoneCalls.add(var1);
        }
    }

    public Collection<PhoneCall> getPhoneCalls() {
        return pPhoneCalls;
    }
}
