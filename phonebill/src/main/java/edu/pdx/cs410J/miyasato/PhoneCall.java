package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

  private String pCaller;
  private String pCallee;
  private String pStartTime;
  private String pEndTime;

  public PhoneCall () {
    pCallee = "not implemented";
    pCaller = null;
    pStartTime = null;
    pEndTime = null;
  }

  public PhoneCall (String caller, String callee, String startTime, String endTime) {
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
