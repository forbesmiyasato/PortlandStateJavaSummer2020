package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TextDumperTest {

    public final String testCaller = "808-200-6188";
    public final String testCallee = "808-200-6188";
    public final String testStartTime = "1/15/2020 19:39";
    public final String testEndTime = "01/2/2020 1:03";

    @Test
    public void testIfDumperWorks() throws IOException {
        File file = new File("test");
        TextDumper textDumper = new TextDumper(file);
        PhoneBill phoneBill = new PhoneBill("Test");
        PhoneCall phoneCall = createPhoneCall();

        phoneBill.addPhoneCall(phoneCall);
        phoneBill.addPhoneCall(phoneCall);

        textDumper.dump(phoneBill);
    }

    public PhoneCall createPhoneCall() {
        PhoneCall pc = new PhoneCall(testCaller, testCallee, testStartTime,
                testEndTime);

        return pc;
    }
}
