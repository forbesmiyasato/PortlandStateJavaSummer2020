package edu.pdx.cs410J.miyasato;

import java.io.*;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

    /**
     * Prints the read me to standard output and terminates the application successfully
     */
    public static void printReadMeAndExit() {
        InputStream readme = Project1.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
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

        callerNumber = args[0];
        calleeNumber = args[1];
        startDate = args[2];
        startTime = args[3];
        endDate = args[4];
        endTime = args[5];

        pc = new PhoneCall(callerNumber, calleeNumber, startDate + " " + startTime, endDate + " " + endTime);

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
        PhoneBill phoneBill;
        Boolean print = false;
        int argumentLength = args.length;
        int startOfArguments = 0;

        if (argumentLength == 0) {
            printErrorMessageAndExit("Missing command line arguments");
        }

        //only the first two arguments could be valid options
        for (startOfArguments = 0; startOfArguments < (2 < argumentLength ? 2 : argumentLength); startOfArguments++) {
            if (!args[startOfArguments].startsWith("-")) {
                break;
            }

            if (args[startOfArguments].startsWith("-") && (!args[startOfArguments].equals("-README") && !args[startOfArguments].equals("-print"))) {
                printErrorMessageAndExit("INVALID OPTION!");
            } else if (args[startOfArguments].equals("-README")) {
                printReadMeAndExit();
            } else if (args[startOfArguments].equals("-print")) {
                print = true;
            }
        }

        if (argumentLength - startOfArguments < 7) {
            printErrorMessageAndExit("Missing command line arguments");
        } else if (argumentLength - startOfArguments > 7) {
            printErrorMessageAndExit("Extraneous command line arguments");
        }

        customerName = args[startOfArguments];
        startOfArguments++;

        phoneBill = new PhoneBill(customerName);

        try {
            phoneCall = createPhoneCallWithArguments(Arrays.copyOfRange(args, startOfArguments, argumentLength));
        } catch (IllegalArgumentException e) {
            printErrorMessageAndExit(e.getMessage());
        }

        phoneBill.addPhoneCall(phoneCall);

        if (print) {
            phoneBill.printPhoneCalls();
        }

        System.exit(0);
    }
}