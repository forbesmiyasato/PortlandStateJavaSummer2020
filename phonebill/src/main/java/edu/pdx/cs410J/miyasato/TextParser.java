package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.text.ParseException;
import java.util.StringTokenizer;

public class TextParser implements PhoneBillParser<PhoneBill> {
    File file;
    String customerName;

    TextParser(File file, String customerName) {
        this.file = file;
        this.customerName = customerName;
    }

    @Override
    public PhoneBill parse() throws ParserException {
        PhoneBill phoneBill = new PhoneBill(customerName);
        FileReader fileReader;
        BufferedReader reader;
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            throw new ParserException("File not found!");
        }


        String line;
        StringTokenizer st;
        String[] args;
        int counter = 0;

        try {
            if ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line, " ");
                if (st.hasMoreTokens()) {
                    String nameInFile = st.nextToken();
                    if (!phoneBill.getCustomer().equals(nameInFile)) {
                        throw new ParserException("Customer names do not match!");
                    }
                }
            }
            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line, " ");
                args = new String[6];
                while (st.hasMoreTokens()) {
                    args[counter] = st.nextToken();
                    counter++;
                }
                counter = 0;
                PhoneCall phoneCall = new PhoneCall(args[0], args[1], args[2] + " " + args[3],
                        args[4] + " " + args[5]);
                phoneBill.addPhoneCall(phoneCall);
            }
            reader.close();
        } catch (IOException e) {
            throw new ParserException(e.getMessage());
        }

        return phoneBill;
    }
}
