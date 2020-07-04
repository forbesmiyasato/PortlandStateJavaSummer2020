package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

  private String pCaller;
  private String pCallee;
  private String pStartTime;
  private String pEndTime;

  /**
   *
   */
  public PhoneCall () {
    pCallee = "not implemented";
    pCaller = null;
    pStartTime = null;
    pEndTime = null;
  }

  /**
   * @param caller
   * @param callee
   * @param startTime
   * @param endTime
   */
  public PhoneCall (String caller, String callee, String startTime, String endTime) {
    String phoneNumberRegex = "^\\d{3}-\\d{3}-\\d{4}$";
    String dateTimeRegex = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$";
    if (!caller.matches(phoneNumberRegex)) {
      throw new IllegalArgumentException("Wrong format for Caller's phone number!");
    }
    if (!callee.matches(phoneNumberRegex)) {
      throw new IllegalArgumentException("Wrong format for Callee's phone number!");
    }
    if (!startTime.matches(dateTimeRegex)) {
      throw new IllegalArgumentException("Wrong format for start time!");
    }
    if (!endTime.matches(dateTimeRegex)) {
      throw new IllegalArgumentException("Wrong format for end time!");
    }

    pCaller = caller;
    pCallee = callee;
    pStartTime = startTime;
    pEndTime = endTime;
  }

  @Override
  public String getCaller() {
    return pCaller;
  }

  @Override
  public String getCallee() {
    return pCallee;
  }

  @Override
  public String getStartTimeString() {
    return pStartTime;
  }

  @Override
  public String getEndTimeString() {
    return pEndTime;
  }
}
