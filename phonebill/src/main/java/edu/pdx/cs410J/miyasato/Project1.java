package edu.pdx.cs410J.miyasato;

import java.io.*;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void printReadMeAndExit() {
      InputStream readme = Project1.class.getResourceAsStream("README.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line;

      try {
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
      } catch (IOException e){
        System.err.println(e.getMessage());
        System.exit(1);
    }
      System.exit(0);
  }

  private static void printErrorMessageAndExit(String errorMessage) {
    System.err.println(errorMessage);
    System.exit(1);
  }

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

  public static void main(String[] args) {
    String customerName;
    PhoneCall phoneCall;
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
      }
      else if (args[startOfArguments].equals("-README")) {
        printReadMeAndExit();
      }
      else if (args[startOfArguments].equals("-print")) {
        print = true;
      }
    }

    if (argumentLength - startOfArguments < 7) {
      printErrorMessageAndExit("Missing command line arguments");
    }
    else if (argumentLength - startOfArguments > 7) {
      printErrorMessageAndExit("Extraneous command line arguments");
    }

    customerName = args[startOfArguments];
    startOfArguments++;

    phoneBill = new PhoneBill(customerName);

    phoneCall = createPhoneCallWithArguments(Arrays.copyOfRange(args, startOfArguments, argumentLength));

    phoneBill.addPhoneCall(phoneCall);

    if (print) {
      phoneBill.printPhoneCalls();
    }

    System.exit(0);
  }
}