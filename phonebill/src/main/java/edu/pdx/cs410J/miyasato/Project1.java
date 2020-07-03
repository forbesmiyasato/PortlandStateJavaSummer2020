package edu.pdx.cs410J.miyasato;

import java.io.*;
import java.util.Scanner;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void printReadMe() throws IOException {
      InputStream readme = Project1.class.getResourceAsStream("README.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line;

      while((line = reader.readLine()) != null) {
        System.out.println(line);
      }
  }

  private static void printErrorMessageAndExit(String errorMessage) {
    System.err.println(errorMessage);
    System.exit(1);
  }

  public static void main(String[] args) {
    String customerName;
    String callerNumber;
    String calleeNumber;
    String startDate;
    String startTime;
    String endDate;
    String endTime;
    String options;
    PhoneCall phoneCall;
    PhoneBill phoneBill;
    int argumentLength = args.length;
    int expectedArgumentLength = 8;

    if (argumentLength == 0) {
      printErrorMessageAndExit("Missing command line arguments");
    }

    options = args[0];

    if (options.equals("-README")) {
      try {
        printReadMe();
      } catch (IOException e) {
        printErrorMessageAndExit(e.getMessage());
      }
    }
    else if (options.equals("-print")) {
      if (argumentLength > expectedArgumentLength) {
        printErrorMessageAndExit("Extraneous command line arguments");
      }
      else if (argumentLength < expectedArgumentLength) {
        printErrorMessageAndExit("Not enough command line arguments to print");
      }

      customerName = args[1];
      callerNumber = args[2];
      calleeNumber = args[3];
      startDate = args[4];
      startTime = args[5];
      endDate = args[6];
      endTime = args[7];

      String startTimeString = startDate + " " + startTime;
      String endTimeString = endDate + " " + endTime;
      phoneCall = new PhoneCall(callerNumber, calleeNumber, startTimeString, endTimeString);
      phoneBill = new PhoneBill(customerName);

      phoneBill.addPhoneCall(phoneCall);

      phoneBill.printPhoneCalls();
    }
    else {
      printErrorMessageAndExit("INVALID OPTION!");
    }

    System.exit(0);
  }
}