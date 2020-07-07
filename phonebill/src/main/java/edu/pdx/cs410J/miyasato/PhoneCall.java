package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This class represents a PhoneCall, and extends AbstractPhoneCall
 */
public class PhoneCall extends AbstractPhoneCall {

  private final String pCaller;
  private final String pCallee;
  private final String pStartTime;
  private final String pEndTime;

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
    String dateTimeRegex = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$";

    checkPhoneNumberFormat(caller, "Wrong format for Caller's phone number!");
    checkPhoneNumberFormat(callee, "Wrong format for Callee's phone number!");
    checkDateTimeFormat(startTime, "Wrong format for start time!");
    checkDateTimeFormat(endTime, "Wrong format for end time!");

    pCaller = caller;
    pCallee = callee;
    pStartTime = startTime;
    pEndTime = endTime;
  }

  /**
   * Checks if the date time format for start time and end time is valid
   * @param dateTime The date time as a String
   * @param errorMessage The error message to print if invalid format
   * @throws IllegalArgumentException if the format is invalid or date time is null
   */
  private void checkDateTimeFormat(String dateTime, String errorMessage) {
    String dateTimeRegex = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$";
    if (dateTime == null || !dateTime.matches(dateTimeRegex)) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  /**
   * Checks if the phone number format for caller and callee is valid
   * @param phoneNumberString The phone number as a String
   * @param errorMessage The error message to print if invalid format
   * @throws IllegalArgumentException if the format is invalid or phone number is null
   */
  private void checkPhoneNumberFormat(String phoneNumberString, String errorMessage) {
    String phoneNumberRegex = "^\\d{3}-\\d{3}-\\d{4}$";
    if (phoneNumberString == null || !phoneNumberString.matches(phoneNumberRegex)) {
      throw new IllegalArgumentException(errorMessage);
    }
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
