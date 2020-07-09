package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
    String fileName;

    TextDumper(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        BufferedWriter writer;
        try {
            Path outputPath = Paths.get(Project1.class.getResource(fileName).toURI());
            writer = Files.newBufferedWriter(outputPath);
        } catch (URISyntaxException | IOException e) {
            throw new IOException("Failed to output to text file");
        }

        String customerName = phoneBill.getCustomer();

        writer.write(customerName);
        writer.newLine();

        Collection<PhoneCall> phoneCalls = phoneBill.getPhoneCalls();

        for (PhoneCall phoneCall : phoneCalls) {
            writer.write(phoneCall.getCaller());
            writer.newLine();
            writer.write(phoneCall.getCallee());
            writer.newLine();
            writer.write(phoneCall.getStartTimeString());
            writer.newLine();
            writer.write(phoneCall.getEndTimeString());
            writer.newLine();
        }

        writer.close();
    }
}
