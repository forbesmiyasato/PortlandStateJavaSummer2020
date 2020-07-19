package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextDumperTest {

    public final String testCaller = "808-200-6188";
    public final String testCallee = "808-200-6188";
    public final String testStartTime = "1/1/2020 1:39 am";
    public final String testEndTime = "01/2/2020 1:03 PM";

    @Test
    public void testIfDumperWorks() throws IOException {
        StringWriter writer = new StringWriter();
        TextDumper textDumper = new TextDumper(writer);
        PhoneBill phoneBill = new PhoneBill("Test");
        PhoneCall phoneCall = createPhoneCall();

        phoneBill.addPhoneCall(phoneCall);
        phoneBill.addPhoneCall(phoneCall);

        textDumper.dump(phoneBill);

        assertThat(writer.toString().trim().replace("\r",""), containsString("Test\n" +
                "808-200-6188 808-200-6188 01/01/2020 01:39 AM 01/02/2020 01:03 PM\n" +
                "808-200-6188 808-200-6188 01/01/2020 01:39 AM 01/02/2020 01:03 PM"));
    }

    public PhoneCall createPhoneCall() {
        PhoneCall pc = new PhoneCall(testCaller, testCallee, testStartTime,
                testEndTime);

        return pc;
    }
}
