package edu.pdx.cs410j.miyasato.phonebill;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a PhoneCall, and extends AbstractPhoneCall
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {

  private final String pCaller;
  private final String pCallee;
  private final Date pStartTime;
  private final Date pEndTime;

  /**
   * Initializes the PhoneCall
   */
  public PhoneCall() {
    pCallee = "not implemented";
    pCaller = null;
    pStartTime = null;
    pEndTime = null;
  }

  /**
   * Initializes the PhoneCall
   *
   * @param caller    The caller of this PhoneCall
   * @param callee    The calle of this PhoneCall
   * @param startTime The startTime of this PhoneCall
   * @param endTime   The endTime of this PhoneCall
   * @throws IllegalArgumentException if there's invalid argument input
   */
  public PhoneCall(String caller, String callee, String startTime, String endTime) throws IllegalArgumentException{
    checkPhoneNumberFormat(caller, "Wrong format for Caller's phone number!");
    checkPhoneNumberFormat(callee, "Wrong format for Callee's phone number!");
    checkDateTimeFormat(startTime, "Wrong format for start time!");
    checkDateTimeFormat(endTime, "Wrong format for end time!");

    String pattern = "MM/dd/yyyy hh:mm aa";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    pCaller = caller;
    pCallee = callee;
    simpleDateFormat.setLenient(false);
    try {
      pStartTime = simpleDateFormat.parse(startTime);
      pEndTime = simpleDateFormat.parse(endTime);
    } catch (ParseException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    if (pStartTime.after(pEndTime)) {
      throw new IllegalArgumentException("Invalid times! Start Time is after End Time!");
    }
  }

  /**
   * Checks if the date time format for start time and end time is valid
   *
   * @param dateTime     The date time as a String
   * @param errorMessage The error message to print if invalid format
   * @throws IllegalArgumentException if the format is invalid or date time is null
   */
  private void checkDateTimeFormat(String dateTime, String errorMessage) {
    String dateTimeRegex = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} (AM|PM|am|pm|aM|Am|pM|Pm)$";
    if (dateTime == null || !dateTime.matches(dateTimeRegex)) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  /**
   * Checks if the phone number format for caller and callee is valid
   *
   * @param phoneNumberString The phone number as a String
   * @param errorMessage      The error message to print if invalid format
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
   *
   * @return The caller of this PhoneCall as a String
   */
  @Override
  public String getCaller() {
    return pCaller;
  }

  /**
   * Gets the callee of this PhoneCall
   *
   * @return The callee of this PhoneCall as a String
   */
  @Override
  public String getCallee() {
    return pCallee;
  }


  /**
   * Gets the start time of this PhoneCall
   *
   * @return The start time of this PhoneCall as a String
   */
  @Override
  public String getStartTimeString() {
    int format = DateFormat.SHORT;
    DateFormat dateFormat = DateFormat.getDateTimeInstance(format, format);
    return dateFormat.format(pStartTime);
  }


  /**
   * Gets the end time of this PhoneCall
   *
   * @return The end time of this PhoneCall as a String
   */
  @Override
  public String getEndTimeString() {
    int format = DateFormat.SHORT;
    DateFormat dateFormat = DateFormat.getDateTimeInstance(format, format);
    return dateFormat.format(pEndTime);
  }

  @Override
  public Date getStartTime() {
    return pStartTime;
  }

  @Override
  public Date getEndTime() {
    return pEndTime;
  }

  @Override
  public int compareTo(PhoneCall otherPhoneCall) {
    if (this.getStartTime().after(otherPhoneCall.getStartTime())) {
      return 1;
    } else if (this.getStartTime().before(otherPhoneCall.getStartTime())) {
      return -1;
    } else {
      return this.getCaller().compareTo(otherPhoneCall.getCaller());
    }
  }
}
