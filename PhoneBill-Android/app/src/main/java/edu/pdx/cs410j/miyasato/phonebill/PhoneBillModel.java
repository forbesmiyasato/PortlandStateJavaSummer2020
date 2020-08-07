package edu.pdx.cs410j.miyasato.phonebill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PhoneBillModel
{
  private final Map<String, PhoneBill> phoneBills = new HashMap<>();

  public PhoneBill getPhoneBill(String customerName) throws NullPointerException{
    PhoneBill phoneBill = phoneBills.get(customerName);
    if (phoneBill == null) {
      throw new NullPointerException(customerName + " has no phone bills!");
    }

    return phoneBill;
  }

  public void addPhoneCallToPhoneBill(String customerName, PhoneCall phoneCall) {
    PhoneBill phoneBill = phoneBills.get(customerName);

    if(phoneBill == null) {
      phoneBill = new PhoneBill((customerName));
    }

    phoneBill.addPhoneCall(phoneCall);
  }

  public PhoneBill searchPhoneBill(PhoneBill phoneBill, Date startTime, Date endTime)
  {
    PhoneBill filteredPhoneBill = new PhoneBill(phoneBill.getCustomer());

    for (PhoneCall phoneCall : phoneBill.getPhoneCalls()) {
      if (phoneCall.getStartTime().after(startTime) && phoneCall.getStartTime().before(endTime)) {
        filteredPhoneBill.addPhoneCall(phoneCall);
      }
    }

    return filteredPhoneBill;
  }
}
