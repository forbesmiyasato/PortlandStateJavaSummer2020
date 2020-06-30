package edu.pdx.cs410J.miyasato;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void printReadMe() {
    System.out.println("Read Me");
  }

  public static void main(String[] args) {
    String customerName = null;
    String callerNumber = null;
    String calleeNumber = null;
    String startDate = null;
    String startTime = null;
    String endDate = null;
    String endTime = null;
    String options = null;
    PhoneCall phoneCall = null;
    PhoneBill phoneBill = null;
    int argumentLength = args.length;
    int expectedArgumentLength = 8;

    if (argumentLength < expectedArgumentLength) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }
    else if (argumentLength > expectedArgumentLength) {
      System.err.println("Extraneous command line arguments");
      System.exit(1);
    }

    options = args[0];
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

    if (options.equals("-print")) {
      phoneBill.printPhoneCalls();
    }
    else if (options.equals("-README")) {
      printReadMe();
    }

    System.exit(1);
  }

}