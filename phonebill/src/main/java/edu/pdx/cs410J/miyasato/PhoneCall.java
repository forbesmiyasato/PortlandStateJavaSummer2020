package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This class represents a PhoneCall, and extends AbstractPhoneCall
 */
public class PhoneCall extends AbstractPhoneCall {

  private String pCaller;
  private String pCallee;
  private String pStartTime;
  private String pEndTime;

  /**
   * Initializes the PhoneCall
   */
  public PhoneCall () {
    pCallee = "not implemented";
    pCaller = null;
    pStartTime = null;
    pEndTime = null;
  }

  /**
   * Initializes the PhoneCall
   * @param caller The caller of this PhoneCall
   * @param callee The calle of this PhoneCall
   * @param startTime The startTime of this PhoneCall
   * @param endTime The endTime of this PhoneCall
   * @throws IllegalArgumentException if there's invalid argument input
   */
  public PhoneCall (String caller, String callee, String startTime, String endTime) {
    String phoneNumberRegex = "^\\d{3}-\\d{3}-\\d{4}$";
    String dateTimeRegex = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$";
    if (caller == null || !caller.matches(phoneNumberRegex)) {
      throw new IllegalArgumentException("Wrong format for Caller's phone number!");
    }
    if (callee == null || !callee.matches(phoneNumberRegex)) {
      throw new IllegalArgumentException("Wrong format for Callee's phone number!");
    }
    if (startTime == null || !startTime.matches(dateTimeRegex)) {
      throw new IllegalArgumentException("Wrong format for start time!");
    }
    if (endTime == null || !endTime.matches(dateTimeRegex)) {
      throw new IllegalArgumentException("Wrong format for end time!");
    }

    pCaller = caller;
    pCallee = callee;
    pStartTime = startTime;
    pEndTime = endTime;
  }


  /**
   * Gets the caller of this PhoneCall
   * @return The caller of this PhoneCall as a String
   */
  @Override
  public String getCaller() {
    return pCaller;
  }

  /**
   * Gets the callee of this PhoneCall
   * @return The callee of this PhoneCall as a String
   */
  @Override
  public String getCallee() {
    return pCallee;
  }


  /**
   * Gets the start time of this PhoneCall
   * @return The start time of this PhoneCall as a String
   */
  @Override
  public String getStartTimeString() {
    return pStartTime;
  }


  /**
   * Gets the end time of this PhoneCall
   * @return The end time of this PhoneCall as a String
   */
  @Override
  public String getEndTimeString() {
    return pEndTime;
  }
}
