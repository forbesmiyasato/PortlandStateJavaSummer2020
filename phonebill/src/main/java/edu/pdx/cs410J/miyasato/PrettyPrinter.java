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

        writer.write("Customer Name: " + customerName);
        writer.println();

        Collection<PhoneCall> phoneCalls = phoneBill.getPhoneCalls();
        int format = DateFormat.MEDIUM;
        DateFormat dateFormat = DateFormat.getDateTimeInstance(format, format);

        if (phoneCalls.size() > 0) {
            writer.printf("%-15s %-15s %-30s %-30s %-15s%n", "Caller", "Callee", "Start Time", "End Time",
                    "Duration (minutes)");
        }
        for (PhoneCall phoneCall : phoneCalls) {
            Date endTime = phoneCall.getEndTime();
            Date startTime = phoneCall.getStartTime();
            int duration = (int) TimeUnit.MILLISECONDS.toMinutes(endTime.getTime()
                    - startTime.getTime());
            writer.printf("%-15s %-15s %-30s %-30s %-15s%n", phoneCall.getCaller(), phoneCall.getCallee(),
                    startTime, endTime, duration);
        }

        writer.close();
    }
}
