package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhoneBillTest {

    @Test
    public void addPhoneCallAddsPhoneCallToPhoneBill () {
        PhoneBill cPB = new PhoneBill();
        PhoneCall cPC = new PhoneCall("test", "test", "test", "test");

        cPB.addPhoneCall(cPC);

        assertThat("Phone bill has one phone call after adding phone call",
                cPB.getPhoneCalls().size(), equalTo(1));
    }

    @Test
    public void phoneBillInitiallyHasNoPhoneCalls () {
        PhoneBill cPB = new PhoneBill();

        assertThat("Phone bill initially has no phone calls", cPB.getPhoneCalls().size(), equalTo(0));
    }

    @Test
    public void getCustomerNameGetsCorrectCustomerName () {
        PhoneBill cPB = new PhoneBill("John");

        assertThat(cPB.getCustomer(), equalTo("John"));
    }

    @Test
    public void phoneBillToStringHasTheCorrectCustomerName () {
        PhoneBill cPB = new PhoneBill("John");

        assertThat(cPB.toString(), containsString("John"));
    }

    @Test public void phoneBillToStringHasTheCorrectAmountOfPhoneCalls () {
        PhoneBill cPB = new PhoneBill("John");

        PhoneCall cPC = new PhoneCall("test", "test", "test", "test");
        PhoneCall cPC2 = new PhoneCall("test", "test", "test", "test");

        cPB.addPhoneCall(cPC);
        cPB.addPhoneCall(cPC2);

        assertThat(cPB.toString(), containsString("2"));
    }

//    @Test
//    public void printPhoneCallsPrintsCorrectPhoneCalls() {
//        PhoneCall cPC = new PhoneCall("test", "test", "test", "test");
//        PhoneBill cPB = new PhoneBill("John");
//
//        cPB.addPhoneCall(cPC);
//
//        assertThat(cPB.printPhoneCalls(), containsString("1"));
//    }

}
