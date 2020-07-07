package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for the PhoneBill class
 */
public class PhoneBillTest {

    public final String testCaller = "808-200-6188";
    public final String testCallee = "808-200-6188";
    public final String testStartTime = "1/15/2020 19:39";
    public final String testEndTime = "01/2/2020 1:03";

    @Test
    public void addPhoneCallAddsPhoneCallToPhoneBill () {
        PhoneBill cPB = new PhoneBill("test");
        PhoneCall cPC = createPhoneCall();

        cPB.addPhoneCall(cPC);

        assertThat("Phone bill has one phone call after adding phone call",
                cPB.getPhoneCalls().size(), equalTo(1));
    }

    @Test
    public void phoneBillInitiallyHasNoPhoneCalls () {
        PhoneBill cPB = new PhoneBill("test");

        assertThat("Phone bill initially has no phone calls", cPB.getPhoneCalls().size(), equalTo(0));
    }

    @Test
    public void getCustomerNameGetsCorrectCustomerName () {
        PhoneBill cPB = new PhoneBill("John");

        assertThat(cPB.getCustomer(), equalTo("John"));
    }

    @Test
    public void getCustomerNameGetsCorrectCustomerFirstNameAndLastName () {
        PhoneBill cPB = new PhoneBill("Brian Griffin");

        assertThat(cPB.getCustomer(), equalTo("Brian Griffin"));
    }

    @Test
    public void phoneBillToStringHasTheCorrectCustomerName () {
        PhoneBill cPB = new PhoneBill("John");

        assertThat(cPB.toString(), containsString("John"));
    }

    @Test public void phoneBillToStringHasTheCorrectAmountOfPhoneCalls () {
        PhoneBill cPB = new PhoneBill("John");

        PhoneCall cPC = createPhoneCall();
        PhoneCall cPC2 = createPhoneCall();

        cPB.addPhoneCall(cPC);
        cPB.addPhoneCall(cPC2);

        assertThat(cPB.toString(), containsString("2"));
    }

    public PhoneCall createPhoneCall() {
        PhoneCall pc = new PhoneCall(testCaller, testCallee, testStartTime,
                testEndTime);

        return pc;
    }

}
