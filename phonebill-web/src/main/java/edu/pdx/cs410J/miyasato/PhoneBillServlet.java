package edu.pdx.cs410J.miyasato;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import static edu.pdx.cs410J.miyasato.PhoneBillURLParameters.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.
 */
public class PhoneBillServlet extends HttpServlet {
    private final Map<String, PhoneBill> phoneBills = new HashMap<>();


    /**
     * Handles the HTTP get request from clients. Returns PhoneBill for customers if customer name
     * is present and exists in PhoneBills, and returns PhoneBill with filtered PhoneCalls if
     * start and end parameters are present.
     *
     * @param request  The servlet request
     * @param response The servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        String customer = getParameterThatHandlesNull(CUSTOMER_PARAMETER, request, response);

        String startTimeString = getParameter(START_TIME_PARAMETER, request);

        String endTimeString = getParameter(END_TIME_PARAMETER, request);

        PhoneBill phoneBill = getPhoneBill(customer);

        if (phoneBill == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customer));
            return;
        }

        if ((startTimeString == null && endTimeString != null) || (startTimeString != null && endTimeString == null)) {
            String message = (startTimeString == null ? Messages.missingRequiredParameter(START_TIME_PARAMETER)
                    : Messages.missingRequiredParameter(END_TIME_PARAMETER));
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
            return;
        } else if (startTimeString != null && endTimeString != null) {
            String pattern = "MM/dd/yyyy hh:mm aa";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setLenient(false);
            Date startTime;
            Date endTime;
            try {
                checkDateTimeFormat(startTimeString, Messages.wrongFormatForStartTime());
                checkDateTimeFormat(endTimeString, Messages.wrongFormatForEndTime());
                startTime = simpleDateFormat.parse(startTimeString);
                endTime = simpleDateFormat.parse(endTimeString);
                if (startTime.after(endTime)) {
                    throw new IllegalArgumentException(Messages.startTimeAfterEndTimeError());
                }
            } catch (ParseException | IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
                return;
            }
            phoneBill = filterPhoneBill(phoneBill, startTime, endTime);
        }

        TextDumper textDumper = new TextDumper(response.getWriter());
        textDumper.dump(phoneBill);

        response.setStatus(HttpServletResponse.SC_OK);
    }


    /**
     * Handles an HTTP POST request by storing the PhoneCall into a customer's PhoneBill.
     * Creates a new PhoneBill if the customer does not have a PhoneBill yet.
     *
     * @param request  The servlet request
     * @param response The servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        String customer = getParameterThatHandlesNull(CUSTOMER_PARAMETER, request, response);

        String caller = getParameterThatHandlesNull(CALLER_NUMBER_PARAMETER, request, response);

        String callee = getParameterThatHandlesNull(CALLEE_NUMBER_PARAMETER, request, response);

        String startTime = getParameterThatHandlesNull(START_TIME_PARAMETER, request, response);

        String endTime = getParameterThatHandlesNull(END_TIME_PARAMETER, request, response);

        PhoneBill phoneBill = phoneBills.get(customer);
        if (phoneBill == null) {
            phoneBill = new PhoneBill(customer);
        }

        try {
            PhoneCall phoneCall = new PhoneCall(caller, callee, startTime, endTime);
            phoneBill.addPhoneCall(phoneCall);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
        }

        this.phoneBills.put(customer, phoneBill);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * <p>
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter(HttpServletResponse response, String parameterName)
            throws IOException {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     * <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    /**
     * Extends the functionality of getParameter by only returning the parameter value.
     * If parameter value is null or empty string, it will throw an error.
     *
     * @param name     The parameter name
     * @param request  The servlet request
     * @param response The servlet response
     * @return The parameter value
     * @throws IOException
     */
    private String getParameterThatHandlesNull(String name, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String value = getParameter(name, request);
        if (value == null) {
            missingRequiredParameter(response, name);
        }
        return value;
    }

    /**
     * Searches for the PhoneBill's PhoneCalls that are between two times
     *
     * @param phoneBill The PhoneBill to filter it's PhoneCalls
     * @param startTime The lower boundary for the PhoneCalls start time
     * @param endTime   The upper boundary for the PhoneCalls end time
     * @return A filteredPhoneBill that only contains PhoneCalls between the two times
     */
    private PhoneBill filterPhoneBill(PhoneBill phoneBill, Date startTime, Date endTime) {
        PhoneBill filteredPhoneBill = new PhoneBill(phoneBill.getCustomer());

        for (PhoneCall phoneCall : phoneBill.getPhoneCalls()) {
            if (phoneCall.getStartTime().after(startTime) && phoneCall.getStartTime().before(endTime)) {
                filteredPhoneBill.addPhoneCall(phoneCall);
            }
        }

        return filteredPhoneBill;
    }

    /**
     * Gets the PhoneBill for a specific customer
     *
     * @param customer Customer name
     * @return The customer's PhoneBill
     */
    @VisibleForTesting
    PhoneBill getPhoneBill(String customer) {
        return this.phoneBills.get(customer);
    }

    /**
     * Adds a PhoneBill for a specific customer
     *
     * @param bill The PhoneBill to be add for the customer
     */
    @VisibleForTesting
    void addPhoneBill(PhoneBill bill) {
        this.phoneBills.put(bill.getCustomer(), bill);
    }

    /**
     * Checks if the date time format for start time and end time is valid
     *
     * @param dateTime     The date time as a String
     * @param errorMessage The error message to print if invalid format
     * @throws IllegalArgumentException if the format is invalid or date time is null
     */
    private void checkDateTimeFormat(String dateTime, String errorMessage) {
        String dateTimeRegex = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} (AM|PM|am|pm|aM|Am|pM|Pm)$";
        if (dateTime == null || !dateTime.matches(dateTimeRegex)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
