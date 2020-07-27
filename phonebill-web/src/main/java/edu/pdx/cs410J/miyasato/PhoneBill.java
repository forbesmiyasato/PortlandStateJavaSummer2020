package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * PhoneBill class extends AbstractPhoneBill, and serves as a container for PhoneCalls
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

    private SortedSet<PhoneCall> pPhoneCalls;
    private String pCustomerName;

    /**
     * Constructor for the PhoneBill object, initializes the instance variables.
     * @param customerName
     */
    public PhoneBill(String customerName) {
        super();
        pCustomerName = customerName;
        pPhoneCalls = new TreeSet<>();
    }

    /**
     * @return the PhoneBill's customer name as a String
     */
    @Override
    public String getCustomer() {
        return pCustomerName;
    }

    /**
     * Adds a PhoneCall to the PhoneBill
     * @param phoneCall the PhoneCall added to the PhoneBill
     */
    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        pPhoneCalls.add(phoneCall);
    }

    /**
     * Gets the PhoneCalls from the PhoneBill
     * @return The Collection of PhoneCalls this PhoneBill contains
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return pPhoneCalls;
    }

    /**
     * Prints the PhoneCall descriptions for all the PhoneCalls within this PhoneBill to the screen
     */
    public void printPhoneCalls() {
        for (PhoneCall phoneCall : pPhoneCalls) {
            System.out.println(phoneCall.toString());
        }
    }
}
