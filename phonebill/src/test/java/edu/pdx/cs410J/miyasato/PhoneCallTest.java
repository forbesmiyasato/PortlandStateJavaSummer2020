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

  public final String testCaller = "808-200-6188";
  public final String testCallee = "808-200-6188";
  public final String testStartTime = "1/15/2020 19:39";
  public final String testEndTime = "01/2/2020 1:03";
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

    assertEquals(pc.getCallee(), testCallee);
  }

  @Test
  public void checkIfGetCallerGetsCorrectCaller () {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getCaller(), testCaller);
  }

  @Test
  public void checkIfGetStartTimeStringGetsCorrectStartTime () {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getStartTimeString(), testStartTime);
  }

  @Test
  public void checkIfGetEndTimeStringGetsCorrectEndTime () {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getEndTimeString(), testEndTime);
  }

  @Test public void phoneCallToStringHasTheCorrectCaller () {
    PhoneCall pc = createPhoneCall();

    assertThat(pc.toString(), containsString(testCaller));
  }

  public PhoneCall createPhoneCall() {
    PhoneCall pc = new PhoneCall(testCaller, testCallee, testStartTime,
            testEndTime);

    return pc;
  }
}
