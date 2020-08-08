package edu.pdx.cs410j.miyasato.phonebill;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

class PhoneBillModel extends AppCompatActivity
{
  private Map<String, PhoneBill> phoneBills;
  private File file;
  private Context context;

  PhoneBillModel(Context context) throws IOException, ClassNotFoundException
  {
    this.context = context;
    File dir = context.getFilesDir();
    file = new File(dir, "phonebill.txt");
    if (!file.exists())
    {
      if (!file.createNewFile())
      {
        throw new IOException("Unable to create PhoneBill file");
      }
    }
    loadPhoneBills();
  }

  PhoneBill getPhoneBill(String customerName) throws NoSuchElementException
  {
    if (!phoneBills.containsKey(customerName))
    {
      throw new NoSuchElementException(customerName + " has no phone bills!");
    }

    return phoneBills.get(customerName);
  }

  void addPhoneCallToPhoneBill(String customerName, PhoneCall phoneCall) throws IOException
  {
    PhoneBill phoneBill = phoneBills.get(customerName);

    if (phoneBill == null)
    {
      phoneBill = new PhoneBill((customerName));
    }

    phoneBill.addPhoneCall(phoneCall);
    phoneBills.put(customerName, phoneBill);
    savePhoneBills();
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

  private void savePhoneBills() throws IOException
  {
    FileOutputStream fileOutputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(this.phoneBills);
    objectOutputStream.close();
  }

  @SuppressWarnings(value = "unchecked")
  private void loadPhoneBills() throws IOException, ClassNotFoundException
  {
    FileInputStream fileInputStream = context.openFileInput(file.getName());
    try
    {
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      this.phoneBills = (Map<String, PhoneBill>) objectInputStream.readObject();
      objectInputStream.close();
      fileInputStream.close();
    } catch (EOFException e)
    {
      phoneBills = new HashMap<>();
    }
  }
}
