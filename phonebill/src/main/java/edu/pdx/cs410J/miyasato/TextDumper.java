package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.util.Collection;

/**
 * This class represents a TextDumper, and implements PhoneBillDumper
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {

    private Writer writer;

    /**
     * Initializes the TextDumper
     * @param writer The writer that specifies where to write the data
     */
    TextDumper(Writer writer) {
        this.writer = writer;
    }

    /**
     * Dumps the content of the phone bill
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

        for (PhoneCall phoneCall : phoneCalls) {
            writer.write(phoneCall.getCaller());
            writer.write(" ");
            writer.write(phoneCall.getCallee());
            writer.write(" ");
            writer.write(phoneCall.getStartTimeString());
            writer.write(" ");
            writer.write(phoneCall.getEndTimeString());
            writer.newLine();
        }

        writer.close();
    }
}
