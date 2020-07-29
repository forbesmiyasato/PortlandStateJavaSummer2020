package edu.pdx.cs410J.miyasato;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages {
    /**
     * @param parameterName The required url parameter that is missing
     * @return The String indicating the required parameter is missing
     */
    public static String missingRequiredParameter(String parameterName) {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * @param customerName The customer name for the query
     * @return the String indicating the customer doesn't have a phone bill
     */
    public static String noPhoneBillForCustomer(String customerName) {
        return String.format("No phone bill for customer %s", customerName);
    }

    /**
     * @return The String indicating that the caller's phone number format is wrong
     */
    public static String wrongFormatForCallerPhoneNumber() {
        return String.format("Wrong format for Caller's phone number!");
    }

    /**
     * @return The String indicating that the callee's phone number format is wrong
     */
    public static String wrongFormatForCalleePhoneNumber() {
        return "Wrong format for Callee's phone number!";
    }

    /**
     * @return The String indicating that the start time format is wrong
     */
    public static String wrongFormatForStartTime() {
        return "Wrong format for start time!";
    }

    /**
     * @return The String indicating that the end time format is wrong
     */
    public static String wrongFormatForEndTime() {
        return "Wrong format for end time!";
    }

    /**
     * @return The String indicating that there's an invalidation caused by the start time being after the end time
     */
    public static String startTimeAfterEndTimeError() {
        return "Invalid times! Start Time is after End Time!";
    }

    /**
     * @param dataTime The date time that is unparseable
     * @return The String indicating that the date time is unparseable
     */
    public static String unParsableDate(String dataTime) {
        return String.format("Unparseable date: \"%s\"", dataTime);
    }
}
