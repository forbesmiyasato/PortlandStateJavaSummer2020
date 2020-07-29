package edu.pdx.cs410J.miyasato;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.miyasato.PhoneBillURLParameters.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client. Extends HttpRequestHelper,
 * and is specialized to handle post and gets to the PhoneBillServlet
 */
public class PhoneBillRestClient extends HttpRequestHelper {
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     *
     * @param hostName The name of the host
     * @param port     The port
     */
    public PhoneBillRestClient(String hostName, int port) {
        this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
    }

    /**
     * Gets the PhoneBill for a specific customer
     *
     * @param customer The customer we're getting the PhoneBill for
     * @return The PhoneBill for the customer
     * @throws IOException
     * @throws ParserException
     */
    public PhoneBill getPhoneBill(String customer) throws IOException, ParserException {
        Response response = get(this.url, Map.of(CUSTOMER_PARAMETER, customer));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();
        TextParser parser = new TextParser(new StringReader(content), customer);
        return parser.parse();
    }

    /**
     * Gets the PhoneBill for a specific customer that has its PhoneCalls between two times
     *
     * @param customer  The customer we're getting the PhoneBill for
     * @param startTime The lower boundary of the times
     * @param endTime   The upper boundary of the times
     * @return The PhoneBill with filtered PhoneCalls
     * @throws IOException
     * @throws ParserException
     */
    public PhoneBill getFilteredPhoneBill(String customer, String startTime, String endTime) throws IOException, ParserException {
        Response response = get(this.url, Map.of(CUSTOMER_PARAMETER, customer, START_TIME_PARAMETER, startTime, END_TIME_PARAMETER, endTime));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();
        TextParser parser = new TextParser(new StringReader(content), customer);
        return parser.parse();
    }

    /**
     * Adds a PhoneCall to a specific customers PhoneBill
     *
     * @param customer  Customer's name
     * @param caller    The PhoneCall's caller number
     * @param callee    The PhoneCall's callee number
     * @param startTime The PhoneCall's start time
     * @param endTime   The PhoneCall's end time
     * @throws IOException
     */
    public void addPhoneCall(String customer, String caller, String callee, String startTime, String endTime) throws IOException {
        Response response = postToMyURL(Map.of(CUSTOMER_PARAMETER, customer, CALLER_NUMBER_PARAMETER, caller,
                CALLEE_NUMBER_PARAMETER, callee, START_TIME_PARAMETER, startTime, END_TIME_PARAMETER, endTime));
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Posts the url parameters to the servlet
     *
     * @param phoneCallEntries A map containing all the parameter and value pairs
     * @return The response from the post request
     * @throws IOException
     */
    @VisibleForTesting
    Response postToMyURL(Map<String, String> phoneCallEntries) throws IOException {
        return post(this.url, phoneCallEntries);
    }

//    public void removeAllPhoneBills() throws IOException {
//      Response response = delete(this.url, Map.of());
//      throwExceptionIfNotOkayHttpStatus(response);
//    }

    /**
     * Throws a PhoneBillRestException if the status code of response is not 200
     *
     * @param response The response that is being checked
     * @return The response that has status code 200
     */
    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code != HTTP_OK) {
            throw new PhoneBillRestException(code);
        }
        return response;
    }

    /**
     * The PhoneBillRestException class that extends RuntimeException,
     * and helps clients get the HTTP status code
     */
    @VisibleForTesting
    class PhoneBillRestException extends RuntimeException {
        private final int httpStatusCode;

        PhoneBillRestException(int httpStatusCode) {
            super("Got an HTTP Status Code of " + httpStatusCode);
            this.httpStatusCode = httpStatusCode;
        }

        public int getHttpStatusCode() {
            return this.httpStatusCode;
        }
    }

}
