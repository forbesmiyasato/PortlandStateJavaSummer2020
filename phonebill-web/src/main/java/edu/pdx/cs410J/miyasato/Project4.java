package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.ParseException;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        int startOfArguments;
        int argumentLength = args.length;
        boolean print = false;
        boolean search = false;
        final int maxOptionArgs = 7;
        int port = 0;
        final int phoneCallArgsLength = 8;
        PhoneBill phoneBill;
        PhoneCall phoneCall = null;
        String customer = null;
        String callerNumber;
        String calleeNumber;
        String startDate;
        String startTime;
        String endDate;
        String endTime;
        String startAmPm;
        String endAmPm;

        if (argumentLength == 0) {
            printErrorMessageAndExit("Missing Command Line Arguments");
        }
        for (startOfArguments = 0; startOfArguments < (Math.min(maxOptionArgs, argumentLength)); startOfArguments++) {
            if (!args[startOfArguments].startsWith("-")) {
                break;
            }

            if (args[startOfArguments].equals("-README")) {
                printReadMeAndExit();
            } else if (args[startOfArguments].equals("-print")) {
                print = true;
            } else if (args[startOfArguments].equals("-search")) {
                search = true;
            } else if (args[startOfArguments].equals("-port")) {
                if (++startOfArguments >= argumentLength) {
                    printErrorMessageAndExit("Missing Port number");
                }
                portString = args[startOfArguments];
                try {
                    port = Integer.parseInt( portString );
                } catch (NumberFormatException ex) {
                    printErrorMessageAndExit("Port \"" + portString + "\" must be an integer");
                    return;
                }
            } else if (args[startOfArguments].equals("-host")) {
                if (++startOfArguments >= argumentLength) {
                    printErrorMessageAndExit("Missing host name");
                }
                hostName = args[startOfArguments];
            } else {
                printErrorMessageAndExit("INVALID OPTION!");
            }
        }

        if (hostName == null) {
            printErrorMessageAndExit("Missing host");
        } else if ( portString == null) {
            printErrorMessageAndExit("Missing port");
        } else if (argumentLength - startOfArguments == 0) {
            printErrorMessageAndExit("Missing Command Line Arguments");
        }

        customer = args[startOfArguments++];

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        try {
            if (search) {
                final int searchArgs = 7;
                if (argumentLength - startOfArguments < searchArgs) {
                    printErrorMessageAndExit("Missing command line arguments for search");
                } else if (argumentLength - startOfArguments > searchArgs) {
                    printErrorMessageAndExit("Extraneous command line arguments for search");
                }
            } else if (argumentLength == 5) {
                try {
                    phoneBill = client.getPhoneBill(customer);
                    PrettyPrinter prettyPrinter = new PrettyPrinter(new PrintWriter(System.out, true));
                    prettyPrinter.dump(phoneBill);
                } catch (IOException | ParserException e) {
                    printErrorMessageAndExit(e.getMessage());
                }
            } else if (argumentLength - startOfArguments == phoneCallArgsLength) {
                callerNumber = args[startOfArguments++];
                calleeNumber = args[startOfArguments++];
                startDate = args[startOfArguments++];
                startTime = args[startOfArguments++];
                startAmPm = args[startOfArguments++];
                endDate = args[startOfArguments++];
                endTime = args[startOfArguments++];
                endAmPm = args[startOfArguments++];
                String startTimeString = startDate + " " + startTime + " " + startAmPm;
                String endTimeString = endDate + " " + endTime + " " + endAmPm;
                try {
                    client.addPhoneCall(customer, callerNumber, calleeNumber, startTimeString, endTimeString);
                } catch (IOException e) {
                    printErrorMessageAndExit(e.getMessage());
                }
                if (print) {
                    try {
                        phoneCall = new PhoneCall(callerNumber, calleeNumber, startTimeString, endTimeString);
                    } catch (IllegalArgumentException e) {
                        printErrorMessageAndExit(e.getMessage());
                    }
                }
            } else {
                printErrorMessageAndExit("Invalid behavior");
            }
        } catch (PhoneBillRestClient.PhoneBillRestException e) {
            printErrorMessageAndExit(e.getMessage());
        }

        if (print) {
            assert phoneCall != null;
            System.out.println(phoneCall.toString());
        }

        System.exit(0);
    }

    private static void printErrorMessageAndExit(String message) {
        System.err.println(message);
        System.exit(1);
    }

    private static void printReadMeAndExit() {
        System.out.println("README!");
        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }
}