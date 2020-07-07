package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;

public class TextParser implements PhoneBillParser {
    BufferedReader textReader;

    TextParser(BufferedReader reader) {
        textReader = reader;
    }

    @Override
    public AbstractPhoneBill parse() throws ParserException {
        String line;
        int lineCount = 0;
        PhoneBill phoneBill = null;
        String customerName;
        String caller = null;
        String callee = null;
        String start = null;
        String end = null;

        try {
            customerName = textReader.readLine();
            phoneBill = new PhoneBill(customerName);
            while ((line = textReader.readLine()) != null) {
                switch (lineCount % 4) {
                    case 0:
                        caller = line;
                        break;
                    case 1:
                        callee = line;
                        break;
                    case 2:
                        start = line;
                        break;
                    case 3:
                        end = line;
                        break;
                }

                if (lineCount % 4 == 0 && lineCount > 0) {
                    PhoneCall phoneCall = new PhoneCall(caller, callee, start, end);
                    phoneBill.addPhoneCall(phoneCall);
                }

                lineCount++;
            }
        } catch (IOException e) {
            throw new ParserException(e.getMessage());
        }

        return phoneBill;
    }
}
