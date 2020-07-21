package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {

    /**
     * Prints the read me to standard output and terminates the application successfully
     */
    public static void printReadMeAndExit() {
        InputStream readme = Project3.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }

    /**
     * Prints an error message to standard error and terminates the application unsuccessfully
     *
     * @param errorMessage The error message print to standard error
     */
    private static void printErrorMessageAndExit(String errorMessage) {
        System.err.println(errorMessage);
        System.exit(1);
    }

    /**
     * Creates a new PhoneCall with the arguments passed in
     *
     * @param args The PhoneCall arguments
     * @return The instantiated PhoneCall
     */
    private static PhoneCall createPhoneCallWithArguments(String[] args) {
        PhoneCall pc;
        String callerNumber;
        String calleeNumber;
        String startDate;
        String startTime;
        String endDate;
        String endTime;
        String startAmPm;
        String endAmPm;

        callerNumber = args[0];
        calleeNumber = args[1];
        startDate = args[2];
        startTime = args[3];
        startAmPm = args[4];
        endDate = args[5];
        endTime = args[6];
        endAmPm = args[7];

        pc = new PhoneCall(callerNumber, calleeNumber,
                startDate + " " + startTime + " " + startAmPm,
                endDate + " " + endTime + " " + endAmPm);

        return pc;
    }

    /**
     * Main program that parses the command line, handles invalid input,
     * creates a PhoneCall and PhoneBill, and prints a description
     * of the PhoneCalls within the PhoneBill to standard out by
     * invoking PhoneCall's <code>toString</code> method.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        String customerName;
        PhoneCall phoneCall = null;
        PhoneBill phoneBill = null;
        TextParser textParser;
        TextDumper textDumper;
        PrettyPrinter prettyPrinter;
        File textFile = null;
        File prettyFile = null;
        boolean print = false;
        boolean textOption = false;
        boolean prettyPrint = false;
        String textFilename = null;
        String prettyFilename = null;
        int argumentLength = args.length;
        int startOfArguments = 0;
        int validArguments = 9;

        if (argumentLength == 0) {
            printErrorMessageAndExit("Missing command line arguments");
        }

        for (startOfArguments = 0; startOfArguments < (Math.min(6, argumentLength)); startOfArguments++) {
            if (!args[startOfArguments].startsWith("-")) {
                break;
            }

            if (args[startOfArguments].equals("-README")) {
                printReadMeAndExit();
            } else if (args[startOfArguments].equals("-print")) {
                print = true;
            } else if (args[startOfArguments].equals("-textFile")) {
                textOption = true;
                if (++startOfArguments >= argumentLength) {
                    printErrorMessageAndExit("Missing file name and command line arguments");
                }
                textFilename = args[startOfArguments];
            } else if (args[startOfArguments].equals("-pretty")) {
                prettyPrint = true;
                if (++startOfArguments >= argumentLength) {
                    printErrorMessageAndExit("Missing file name and command line arguments");
                }
                prettyFilename = args[startOfArguments];
            } else {
                printErrorMessageAndExit("INVALID OPTION!");
            }
        }

        if (argumentLength - startOfArguments < validArguments) {
            printErrorMessageAndExit("Missing command line arguments");
        } else if (argumentLength - startOfArguments > validArguments) {
            printErrorMessageAndExit("Extraneous command line arguments");
        }

        customerName = args[startOfArguments];
        startOfArguments++;

        if (textOption) {
            textFile = new File(textFilename);
            if (textFile.exists()) {
                try {
                    textParser = new TextParser(new FileReader(textFile), customerName);
                    phoneBill = textParser.parse();
                } catch (FileNotFoundException | ParserException e) {
                    printErrorMessageAndExit(e.getMessage());
                }
            }
        }

        try {
            phoneCall = createPhoneCallWithArguments(Arrays.copyOfRange(args, startOfArguments, argumentLength));
        } catch (IllegalArgumentException e) {
            printErrorMessageAndExit(e.getMessage());
        }

        if (phoneBill == null) {
            phoneBill = new PhoneBill(customerName);
        }

        phoneBill.addPhoneCall(phoneCall);

        if (prettyPrint) {
            prettyFile = new File(prettyFilename);
            try {
                if (prettyFilename.equals("-")) {
                    prettyPrinter = new PrettyPrinter(new PrintWriter(System.out, true));
                } else {
                    prettyPrinter = new PrettyPrinter(new FileWriter(prettyFile));
                }
                prettyPrinter.dump(phoneBill);
            } catch (IOException e) {
                printErrorMessageAndExit(e.getMessage());
            }
        }

        if (textOption) {
            try {
                textDumper = new TextDumper(new FileWriter(textFile));
                textDumper.dump(phoneBill);
            } catch (IOException e) {
                printErrorMessageAndExit(e.getMessage());
            }
        }

        if (print) {
            assert phoneCall != null;
            System.out.println(phoneCall.toString());
        }

        System.exit(0);
    }
}