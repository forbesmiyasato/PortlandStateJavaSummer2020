package edu.pdx.cs410J.miyasato;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void printReadMe() {
    File readMe;
    final String readMePath = "src/main/resources/edu/pdx/cs410J/miyasato/README.txt";
    Scanner scanner;

    try {
      readMe = new File(readMePath);
      scanner = new Scanner(readMe);
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
      return;
    }

    while(scanner.hasNextLine()) {
      System.out.println(scanner.nextLine());
    }
  }

  private static void errorHandle(String errorMessage) {
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
      errorHandle("Missing command line arguments");
    }

    options = args[0];

    if (options.equals("-README")) {
      printReadMe();
    }
    else if (options.equals("-print")) {
      if (argumentLength > expectedArgumentLength) {
        errorHandle("Extraneous command line arguments");
      }
      else if (argumentLength < expectedArgumentLength) {
        errorHandle("Not enough command line arguments to print");
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
      errorHandle("INVALID OPTION!");
    }

    System.exit(0);
  }
}