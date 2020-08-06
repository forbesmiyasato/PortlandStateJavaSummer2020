package edu.pdx.cs410j.miyasato.phonebill;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import edu.pdx.cs410j.miyasato.phonebill.PhoneBill;

import java.io.*;
import java.text.ParseException;
import java.util.StringTokenizer;

/**
 * This class represents a TextParser, and implements PhoneBillParser
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
  private Reader reader;
  private String customerName;

  /**
   * Initializes the TextParser
   *
   * @param reader       The reader that specifies how to read data
   * @param customerName Person whose phone bill we're modeling
   */
  TextParser(Reader reader, String customerName) {
    this.reader = reader;
    this.customerName = customerName;
  }

  /**
   * Parses the content of a phone bill into a new PhoneBill object
   *
   * @return The parsed Phone Bill based on the data stored
   * @throws ParserException
   */
  @Override
  public PhoneBill parse() throws ParserException {
    PhoneBill phoneBill = new PhoneBill(customerName);
    BufferedReader reader;

    reader = new BufferedReader(this.reader);

    String line;
    StringTokenizer st;
    String[] args;
    int counter = 0;

    try {
      if ((line = reader.readLine()) != null) {
        String nameInFile = line;
        if (!phoneBill.getCustomer().equals(nameInFile)) {
          throw new ParserException("Customer names do not match!");
        }
      }
      while ((line = reader.readLine()) != null) {
        st = new StringTokenizer(line, " ");
        args = new String[8];
        while (st.hasMoreTokens()) {
          if (counter >= 8) {
            throw new ParserException("Malformatted File! Too much Arguments!");
          }
          args[counter] = st.nextToken();
          counter++;
        }
        counter = 0;
        args[2] = args[2].trim().replace(",", "");
        args[5] = args[5].trim().replace(",", "");
        try {
          PhoneCall phoneCall = new PhoneCall(args[0], args[1], args[2] + " " + args[3] + " " + args[4],
                  args[5] + " " + args[6] + " " + args[7]);
          phoneBill.addPhoneCall(phoneCall);
        } catch (IllegalArgumentException e) {
          throw new ParserException("Malformatted File! " + e.getMessage());
        }
      }
      reader.close();
    } catch (
            IOException e) {
      throw new ParserException(e.getMessage());
    }

    return phoneBill;
  }
}
