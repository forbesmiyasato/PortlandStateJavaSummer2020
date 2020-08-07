package edu.pdx.cs410j.miyasato.phonebill;

import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

class PhoneBillModel extends ViewModel
{
  private final Map<String, PhoneBill> phoneBills = new HashMap<>();

  PhoneBill getPhoneBill(String customerName) throws NoSuchElementException
  {
    if (!phoneBills.containsKey(customerName))
    {
      throw new NoSuchElementException(customerName + " has no phone bills!");
    }

    return phoneBills.get(customerName);
  }

  void addPhoneCallToPhoneBill(String customerName, PhoneCall phoneCall)
  {
    PhoneBill phoneBill = phoneBills.get(customerName);

    if (phoneBill == null)
    {
      phoneBill = new PhoneBill((customerName));
    }

    phoneBill.addPhoneCall(phoneCall);
    phoneBills.put(customerName, phoneBill);
  }

  PhoneBill searchPhoneBill(String customerName, Date startTime, Date endTime) throws NoSuchElementException
  {
    if (!phoneBills.containsKey(customerName))
    {
      throw new NoSuchElementException(customerName + " has no phone bills!");
    }

    PhoneBill phoneBill = phoneBills.get(customerName);
    PhoneBill filteredPhoneBill = new PhoneBill(customerName);

    for (PhoneCall phoneCall : Objects.requireNonNull(phoneBill).getPhoneCalls())
    {
      if (phoneCall.getStartTime().after(startTime) && phoneCall.getStartTime().before(endTime))
      {
        filteredPhoneBill.addPhoneCall(phoneCall);
      }
    }

    return filteredPhoneBill;
  }
}
