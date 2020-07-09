package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextParser implements PhoneBillParser<PhoneBill> {
    String fileName;
    String customerName;

    TextParser(String fileName, String customerName) {
        this.fileName = fileName;
        this.customerName = customerName;
    }

    @Override
    public PhoneBill parse() throws ParserException {
//        Path currentRelativePath = Paths.get("");
//        String currentDirectory = currentRelativePath.toAbsolutePath().toString();
//        File file = new File(currentDirectory + "/" + fileName);
//        try {
//            if (file.createNewFile()) {
//                return new PhoneBill(customerName);
//            }
//        } catch (IOException e) {
//            throw new ParserException(e.getMessage());
//        }

        InputStream inputStream = Project1.class.getResourceAsStream(fileName);
        BufferedReader textReader = new BufferedReader(new InputStreamReader(inputStream));

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
