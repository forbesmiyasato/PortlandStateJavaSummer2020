package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class PrettyPrinterTest {

    public final String testCaller = "808-200-6188";
    public final String testCallee = "808-200-6188";
    public final String testStartTime = "1/1/2020 1:39 am";
    public final String testEndTime = "01/2/2020 1:03 PM";

    @Test
    public void testIfPrettyPrinterWorks() throws IOException {
        StringWriter writer = new StringWriter();
        PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
        PhoneBill phoneBill = new PhoneBill("Test");
        PhoneCall phoneCall = createPhoneCall();

        phoneBill.addPhoneCall(phoneCall);
        prettyPrinter.dump(phoneBill);

        assertThat(writer.toString().trim().replace("\r",""), containsString(
                "Customer Name: Test\n" + "Caller          Callee          Start Time" +
                        "                     End Time                       Duration (minutes)\n" +
                "808-200-6188    808-200-6188    Wed Jan 01 01:39:00 PST 2020   Thu Jan 02 13:03:00 PST 2020   2124"));
    }

    public PhoneCall createPhoneCall() {
        PhoneCall pc = new PhoneCall(testCaller, testCallee, testStartTime,
                testEndTime);

        return pc;
    }
}
