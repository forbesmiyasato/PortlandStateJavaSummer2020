package edu.pdx.cs410j.miyasato.phonebill;

import edu.pdx.cs410J.PhoneBillDumper;
import edu.pdx.cs410j.miyasato.phonebill.PhoneBill;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * This class represents a TextDumper, and implements PhoneBillDumper
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {

  private Writer writer;

  /**
   * Initializes the TextDumper
   *
   * @param writer The writer that specifies where to write the data
   */
  TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * Dumps the content of the phone bill
   *
   * @param phoneBill The PhoneBill that we're dumping to the output
   * @throws IOException
   */
  @Override
  public void dump(PhoneBill phoneBill) throws IOException {
    BufferedWriter writer;

    writer = new BufferedWriter(this.writer);

    String customerName = phoneBill.getCustomer();

    writer.write(customerName);
    writer.newLine();

    Collection<PhoneCall> phoneCalls = phoneBill.getPhoneCalls();

    String startTime;
    String endTime;
    String pattern = "MM/dd/yyyy hh:mm aa";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    for (PhoneCall phoneCall : phoneCalls) {
      writer.write(phoneCall.getCaller());
      writer.write(" ");
      writer.write(phoneCall.getCallee());
      writer.write(" ");
      startTime = simpleDateFormat.format(phoneCall.getStartTime());
      writer.write(startTime);
      writer.write(" ");
      endTime = simpleDateFormat.format(phoneCall.getEndTime());
      writer.write(endTime);
      writer.newLine();
    }

    writer.close();
  }
}
