package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private Writer writer;

    PrettyPrinter(Writer writer) {
        this.writer = writer;
    }
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        BufferedWriter bufferedWriter;

        bufferedWriter = new BufferedWriter(this.writer);

        PrintWriter writer = new PrintWriter(bufferedWriter);
        String customerName = phoneBill.getCustomer();

        Collection<PhoneCall> phoneCalls = phoneBill.getPhoneCalls();

        writer.write("Customer Name: " + customerName);
        writer.println();
        writer.write("Phone Calls (" + phoneCalls.size() + "):");
        writer.println();

        int format = DateFormat.MEDIUM;
        DateFormat dateFormat = DateFormat.getDateTimeInstance(format, format);

        if (phoneCalls.size() > 0) {
            writer.printf("                %-15s %-17s %-27s %-27s %s%n", "Caller", "Callee", "Start Time", "End Time",
                    "Duration");
        }
        for (PhoneCall phoneCall : phoneCalls) {
            Date endTime = phoneCall.getEndTime();
            Date startTime = phoneCall.getStartTime();
            int duration = (int) TimeUnit.MILLISECONDS.toMinutes(endTime.getTime()
                    - startTime.getTime());
            writer.printf("Phone Call from %-12s to %-11s from %-24s to %-23s for %s minutes%n", phoneCall.getCaller(), phoneCall.getCallee(),
                    dateFormat.format(startTime), dateFormat.format(endTime), duration);
        }

        writer.close();
    }
}
