package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class PrettyPrinterTest {

    public final String testCaller = "808-200-6188";
    public final String testCallee = "808-200-6188";
    public final String testStartTime = "11/1/2020 1:39 am";
    public final String testEndTime = "12/2/2020 1:03 PM";

    @Test
    public void testIfPrettyPrinterWorks() throws IOException {
        StringWriter writer = new StringWriter();
        PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
        PhoneBill phoneBill = new PhoneBill("Test");
        PhoneCall phoneCall = createPhoneCall();

        phoneBill.addPhoneCall(phoneCall);
        prettyPrinter.dump(phoneBill);

        assertThat(writer.toString().trim().replace("\r", ""), containsString(
                "Customer Name: Test\n" + "Phone Calls (1):\n" +
                        "                Caller          Callee            Start Time" +
                        "                  End Time                    Duration\n" +
                        "Phone Call from 808-200-6188 to 808-200-6188 from Nov 1, 2020, 1:39:00 AM  to Dec 2, 2020, " +
                        "1:03:00 PM for 45324 minutes"));
    }

    public PhoneCall createPhoneCall() {
        PhoneCall pc = new PhoneCall(testCaller, testCallee, testStartTime,
                testEndTime);

        return pc;
    }
}
