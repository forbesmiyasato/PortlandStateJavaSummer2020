package edu.pdx.cs410J.miyasato;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static edu.pdx.cs410J.miyasato.PhoneBillURLParameters.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet {
    private final Map<String, PhoneBill> phoneBills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        String customer = getParameter(CUSTOMER_PARAMETER, request);
        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        PhoneBill phoneBill = getPhoneBill(customer);
        if (phoneBill == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customer));
        }
        else {
            TextDumper textDumper = new TextDumper(response.getWriter());
            textDumper.dump(phoneBill);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
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
        } catch (IllegalArgumentException e){
            throw new ServletException(e.getMessage());
        }

        this.phoneBills.put(customer, phoneBill);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.phoneBills.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

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

    private String getParameterThatHandlesNull(String name, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String value = getParameter(name, request);
        if (value == null) {
            missingRequiredParameter(response, name);
        }
        return value;
    }
    @VisibleForTesting
    PhoneBill getPhoneBill(String customer) {
        return this.phoneBills.get(customer);
    }

    @VisibleForTesting
    void addPhoneBill(PhoneBill bill) {
        this.phoneBills.put(bill.getCustomer(), bill);
    }
}
