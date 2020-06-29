package edu.pdx.cs410J.miyasato;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {

  @Test
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = new PhoneCall("test", "test", "test", "test");
    call.getStartTimeString();
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getCallee(), containsString("not implemented"));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall("test", "test", null, "test");
    assertThat(call.getStartTime(), is(nullValue()));
  }

  @Test
  public void checkIfGetCalleeGetsCorrectCallee () {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getCallee(), "808-200-2000");
  }

  @Test
  public void checkIfGetCallerGetsCorrectCaller () {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getCaller(), "808-200-6188");
  }

  @Test
  public void checkIfGetStartTimeStringGetsCorrectStartTime () {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getStartTimeString(), "1/15/2020 19:39");
  }

  @Test
  public void checkIfGetEndTimeStringGetsCorrectEndTime () {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getEndTimeString(), "01/2/2020 1:03");
  }

  public PhoneCall createPhoneCall() {
    PhoneCall pc = new PhoneCall("808-200-6188", "808-200-2000", "1/15/2020 19:39",
            "01/2/2020 1:03");

    return pc;
  }
}
