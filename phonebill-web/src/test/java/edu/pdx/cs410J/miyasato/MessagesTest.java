package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static edu.pdx.cs410J.miyasato.PhoneBillURLParameters.*;
import static org.hamcrest.Matchers.*;

public class MessagesTest {

    @Test
    public void testMissingRequiredParameter() {
        assertThat(Messages.missingRequiredParameter(CUSTOMER_PARAMETER),
                containsString("The required parameter \"customer\" is missing"));
    }

    @Test
    public void testNoPhoneBillForCustomer() {
        assertThat(Messages.noPhoneBillForCustomer("dave"),
                containsString("No phone bill for customer dave"));
    }

    @Test
    public void testWrongCallerFormat() {
        assertThat(Messages.wrongFormatForCallerPhoneNumber(),
                containsString("Wrong format for Caller's phone number!"));
    }

    @Test
    public void testWrongCalleeFormat() {
        assertThat(Messages.wrongFormatForCalleePhoneNumber(),
                containsString("Wrong format for Callee's phone number!"));
    }

    @Test
    public void testWrongStartTimeFormat() {
        assertThat(Messages.wrongFormatForStartTime(),
                containsString("Wrong format for start time!"));
    }

    @Test
    public void testWrongEndTimeFormat() {
        assertThat(Messages.wrongFormatForEndTime(),
                containsString("Wrong format for end time!"));
    }

    @Test
    public void testStartTimeAfterEndTimeError() {
        assertThat(Messages.startTimeAfterEndTimeError(),
                containsString("Invalid times! Start Time is after End Time!"));
    }

    @Test
    public void testUnParsableDate() {
        assertThat(Messages.unParsableDate("1/XX/20 1:10 am"),
                containsString("Unparseable date: \"1/XX/20 1:10 am\""));
    }
}
