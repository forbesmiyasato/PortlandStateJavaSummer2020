package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhoneBillTest {

    @Test
    public void addPhoneCallAddsPhoneCallToPhoneBill () {
        PhoneBill cPB = new PhoneBill();
        PhoneCall cPC = new PhoneCall();

        cPB.addPhoneCall(cPC);

        assertEquals("Phone bill now has one phone call after adding one phone call", cPB.getPhoneCalls().size(), 1);
    }
}
