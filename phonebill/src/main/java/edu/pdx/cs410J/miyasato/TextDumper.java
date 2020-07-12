package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
    File file;

    TextDumper(File file) {
        this.file = file;
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        BufferedWriter writer;
        FileWriter fileWriter = new FileWriter(file);

        writer = new BufferedWriter(fileWriter);

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
