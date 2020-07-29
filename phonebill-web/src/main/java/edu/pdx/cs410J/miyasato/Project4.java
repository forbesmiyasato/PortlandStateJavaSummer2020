package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
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
                    port = Integer.parseInt(portString);
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
        } else if (portString == null) {
            printErrorMessageAndExit("Missing port");
        } else if (argumentLength - startOfArguments == 0) {
            printErrorMessageAndExit("Missing Command Line Arguments");
        }

        customer = args[startOfArguments++];

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        try {
            if (search) {
                final int dateArgsLength = 6;
                if (argumentLength - startOfArguments < dateArgsLength) {
                    printErrorMessageAndExit("Missing command line arguments for search");
                } else if (argumentLength - startOfArguments > dateArgsLength) {
                    printErrorMessageAndExit("Extraneous command line arguments for search");
                }
                String startTimeString = getDateTimeString(args[startOfArguments++], args[startOfArguments++], args[startOfArguments++]);
                String endTimeString = getDateTimeString(args[startOfArguments++], args[startOfArguments++], args[startOfArguments++]);
                try {
                    phoneBill = client.getFilteredPhoneBill(customer, startTimeString, endTimeString);
                    PrettyPrinter prettyPrinter = new PrettyPrinter(new PrintWriter(System.out, true));
                    prettyPrinter.dump(phoneBill);
                } catch (IOException | ParserException e) {
                    printErrorMessageAndExit(e.getMessage());
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
                String startTimeString = getDateTimeString(args[startOfArguments++], args[startOfArguments++], args[startOfArguments++]);
                String endTimeString = getDateTimeString(args[startOfArguments++], args[startOfArguments++], args[startOfArguments++]);
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
            if (phoneCall == null) {
                printErrorMessageAndExit("No phone call to print!");
            }
            assert phoneCall != null;
            System.out.println(phoneCall.toString());
        }

        System.exit(0);
    }

    /**
     * Prints an error message and then exists
     *
     * @param message The error message to print
     */
    private static void printErrorMessageAndExit(String message) {
        System.err.println(message);
        System.exit(1);
    }

    /**
     * Prints the README then exists
     */
    private static void printReadMeAndExit() {
        InputStream readme = Project4.class.getResourceAsStream("README.txt");
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
     * Combine the given date, time and meridiem into a new String
     *
     * @param date     The date
     * @param time     The time
     * @param meridiem The meridiem
     * @return A String that combines the date, time and meridiem
     */
    private static String getDateTimeString(String date, String time, String meridiem) {
        return date + " " + time + " " + meridiem;
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     *
     * @param code     The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode(int code, HttpRequestHelper.Response response) {
        if (response.getCode() != code) {
            printErrorMessageAndExit(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getCode(), response.getContent()));
        }
    }
}