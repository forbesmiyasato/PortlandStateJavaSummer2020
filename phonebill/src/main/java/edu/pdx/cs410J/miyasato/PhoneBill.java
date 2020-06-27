package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall>{

    private ArrayList<PhoneCall> pPhoneCalls;
    private String customerName;

    public String getCustomer() {
        return customerName;
    }

    public void addPhoneCall(PhoneCall var1) {
        pPhoneCalls.add(var1);
    }

    public Collection<PhoneCall> getPhoneCalls() {
        return pPhoneCalls;
    }
}
