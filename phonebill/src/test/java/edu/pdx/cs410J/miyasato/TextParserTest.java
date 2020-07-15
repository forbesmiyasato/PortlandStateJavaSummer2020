package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TextParserTest {
    @Test
    public void checkIfTextParserWorks () throws ParserException {
        InputStream testFile = getClass().getResourceAsStream("ParserTestPhoneBill.txt");
        TextParser textParser = new TextParser(new InputStreamReader(testFile), "Test");

        PhoneBill phoneBill = textParser.parse();

        assertThat(phoneBill.getCustomer(), equalTo("Test"));
        assertThat(phoneBill.getPhoneCalls().toArray()[0].toString(), equalTo("Phone call from 808-200-6188 to " +
                "808-200-6188 from 1/15/2020 19:39 to 01/2/2020 1:03"));
    }

    @Test (expected = ParserException.class)
    public void exceptionThrownIfCustomerNamesDoNotMatch() throws ParserException {
        InputStream testFile = getClass().getResourceAsStream("ParserTestPhoneBill.txt");
        TextParser textParser = new TextParser(new InputStreamReader(testFile), "false");

        PhoneBill phoneBill = textParser.parse();
        assertThat(phoneBill.getCustomer(), equalTo("Test"));
    }

    @Test (expected = ParserException.class)
    public void exceptionThrownForMalformattedTextFile() throws ParserException {
        InputStream testFile = getClass().getResourceAsStream("MalformattedPhoneBill.txt");
        TextParser textParser = new TextParser(new InputStreamReader(testFile), "Test");

        PhoneBill phoneBill = textParser.parse();
        assertThat(phoneBill.getCustomer(), equalTo("Test"));
    }
}
