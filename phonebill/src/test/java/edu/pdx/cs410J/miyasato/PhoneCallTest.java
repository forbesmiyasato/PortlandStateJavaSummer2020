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
    PhoneCall call = createPhoneCall();
    call.getStartTimeString();
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getCallee(), containsString("not implemented"));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getStartTime(), is(nullValue()));
  }

  @Test
  public void checkIfGetCalleeGetsCorrectCallee() {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getCallee(), testCallee);
  }

  @Test
  public void checkIfGetCallerGetsCorrectCaller() {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getCaller(), testCaller);
  }

  @Test
  public void checkIfGetStartTimeStringGetsCorrectStartTime() {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getStartTimeString(), testStartTime);
  }

  @Test
  public void checkIfGetEndTimeStringGetsCorrectEndTime() {
    PhoneCall pc = createPhoneCall();

    assertEquals(pc.getEndTimeString(), testEndTime);
  }

  @Test
  public void phoneCallToStringHasTheCorrectCaller() {
    PhoneCall pc = createPhoneCall();

    assertThat(pc.toString(), containsString(testCaller));
  }

  @Test
  public void phoneCallToStringHasTheCorrectCallee() {
    PhoneCall pc = createPhoneCall();

    assertThat(pc.toString(), containsString(testCallee));
  }

  @Test
  public void phoneCallToStringHasCorrectStartTime() {
    PhoneCall pc = createPhoneCall();

    assertThat(pc.toString(), containsString(testStartTime));
  }

  @Test
  public void phoneCallToStringHasCorrectEndTime() {
    PhoneCall pc = createPhoneCall();

    assertThat(pc.toString(), containsString(testEndTime));
  }

  @Test(expected = IllegalArgumentException.class)
  public void EmptyCallerThrowsIllegalArgumentException() {
    new PhoneCall("", testCallee, testStartTime,
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void InvalidCallerFormatThrowsIllegalArgumentException() {
    new PhoneCall("1800-200-6188", testCallee, testStartTime,
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void InvalidCallerFormatThrowsIllegalArgumentException2() {
    new PhoneCall("800+200-6188", testCallee, testStartTime,
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void EmptyCalleeThrowsIllegalArgumentException() {
    new PhoneCall(testCaller, "", testStartTime,
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidCalleeFormatThrowsIllegalArgumentException() {
    new PhoneCall(testCaller, "1800-200-6188", testStartTime,
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidCalleeFormatThrowsIllegalArgumentException2() {
    new PhoneCall(testCaller, "808-200+6188", testStartTime,
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void EmptyStartTimeThrowsIllegalArgumentException() {
    new PhoneCall(testCaller, testCallee, null,
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidStartTimeFormatThrowsIllegalArgumentException() {
    new PhoneCall(testCaller, testCallee, "1/15/2020 19:390",
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidStartTimeFormatThrowsIllegalArgumentException2() {
    new PhoneCall(testCaller, testCallee, "19:30 1/15/2020",
            testEndTime);
  }

  @Test(expected = IllegalArgumentException.class)
  public void EmptyEndTimeThrowsIllegalArgumentException() {
    new PhoneCall(testCaller, testCallee, testStartTime,
            "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidEndTimeFormatThrowsIllegalArgumentException() {
    new PhoneCall(testCaller, testCallee, testStartTime,
            "01/20/2020 101:10");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidEndTimeFormatThrowsIllegalArgumentException2() {
    new PhoneCall(testCaller, testCallee, testStartTime,
            "01/20//2020 01:10");
  }

//  @Test(expected = IllegalArgumentException.class)
//  public void checkPhoneNumberFormatThrowsExceptionWithInvalidFormat() {
//    checkPhoneNumberFormat("808+200+6188");
//  }

  public PhoneCall createPhoneCall() {
    PhoneCall pc = new PhoneCall(testCaller, testCallee, testStartTime,
            testEndTime);

    return pc;
  }
}
