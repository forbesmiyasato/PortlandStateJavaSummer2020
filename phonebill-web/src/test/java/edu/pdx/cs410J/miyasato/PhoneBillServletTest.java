package edu.pdx.cs410J.miyasato;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static edu.pdx.cs410J.miyasato.PhoneBillURLParameters.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

    public final String testCaller = "808-200-6188";
    public final String testCallee = "808-200-6188";
    public final String testStartTime = "1/1/2020 9:39 am";
    public final String testEndTime = "01/2/2020 1:03 pm";
    public final String testCustomer = "TEST CUSTOMER";

    @Test
    public void requestWithNoCustomerReturnMissingParameter() throws ServletException, IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
                Messages.missingRequiredParameter(CUSTOMER_PARAMETER));
    }

    @Test
    public void requestCustomerWithNoPhoneBillReturnsNotFound() throws ServletException, IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(testCustomer));
    }

    @Test
    public void addPhoneCallToBill() throws ServletException, IOException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(testCaller);
        when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(testCallee);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn(testStartTime);
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn(testEndTime);

        HttpServletResponse response = mock(HttpServletResponse.class);

        PrintWriter pw = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(pw, times(0)).println(Mockito.any(String.class));
        verify(response).setStatus(HttpServletResponse.SC_OK);

        PhoneBill phoneBill = servlet.getPhoneBill(testCustomer);
        assertThat(phoneBill, notNullValue());
        assertThat(phoneBill.getCustomer(), equalTo(testCustomer));

        PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
        assertThat(phoneCall.getCaller(), equalTo(testCaller));
        assertThat(phoneCall.getCallee(), equalTo(testCallee));
    }

    @Test
    public void requestingExistingPhoneBillDumpsItToPrintWriter() throws IOException, ServletException {

        PhoneBill phoneBill = new PhoneBill(testCustomer);
        phoneBill.addPhoneCall(new PhoneCall(testCaller, testCallee, testStartTime, testEndTime));

        PhoneBillServlet servlet = new PhoneBillServlet();
        servlet.addPhoneBill(phoneBill);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        String textPhoneBill = sw.toString();

        assertThat(textPhoneBill, containsString(testCustomer));
        assertThat(textPhoneBill, containsString(testCaller));
    }

    @Test
    public void searchExistingStartTimeReturnsPhoneBill() throws IOException, ServletException {

        PhoneBill phoneBill = new PhoneBill(testCustomer);
        phoneBill.addPhoneCall(new PhoneCall(testCaller, testCallee, testStartTime, testEndTime));

        PhoneBillServlet servlet = new PhoneBillServlet();
        servlet.addPhoneBill(phoneBill);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn("1/1/2020 8:39 am");
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/1/2020 10:39 am");
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        String textPhoneBill = sw.toString();

        assertThat(textPhoneBill, containsString(testCustomer));
        assertThat(textPhoneBill, containsString(testCaller));
    }

    @Test
    public void searchNonExistingStartTimeDoesNotReturnPhoneBill() throws IOException, ServletException {

        PhoneBill phoneBill = new PhoneBill(testCustomer);
        phoneBill.addPhoneCall(new PhoneCall(testCaller, testCallee, testStartTime, testEndTime));

        PhoneBillServlet servlet = new PhoneBillServlet();
        servlet.addPhoneBill(phoneBill);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn("1/1/2020 1:39 pm");
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/1/2020 2:39 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        String textPhoneBill = sw.toString();

        System.out.println(textPhoneBill);
        assertThat(textPhoneBill, containsString(testCustomer));
        assertThat(textPhoneBill, not(containsString(testCaller)));
    }

    @Test
    public void searchWithMissingEndParameterThrowsError() throws ServletException, IOException {
        PhoneBill phoneBill = new PhoneBill(testCustomer);
        phoneBill.addPhoneCall(new PhoneCall(testCaller, testCallee, testStartTime, testEndTime));

        PhoneBillServlet servlet = new PhoneBillServlet();
        servlet.addPhoneBill(phoneBill);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn(testCustomer);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
                Messages.missingRequiredParameter(END_TIME_PARAMETER));
    }

    @Test
    public void addPhoneBillToServletWithStartTimeAfterEndTimeThrowsAppropriateError() throws IOException, ServletException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(testCallee);
        when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(testCaller);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn("1/2/2020 1:39 pm");
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/1/2020 2:39 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.startTimeAfterEndTimeError());
    }

    @Test
    public void addPhoneBillToServletWithInvalidCallerThrowsAppropriateError() throws IOException, ServletException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn("808+200-61a7");
        when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(testCallee);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn("1/1/2020 1:39 pm");
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/2/2020 2:39 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.wrongFormatForCallerPhoneNumber());
    }

    @Test
    public void addPhoneBillToServletWithInvalidCalleeThrowsAppropriateError() throws IOException, ServletException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(testCaller);
        when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn("808+200-61a7");
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn("1/1/2020 1:39 pm");
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/2/2020 2:39 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.wrongFormatForCalleePhoneNumber());
    }

    @Test
    public void addPhoneBillToServletWithInvalidStartTimeThrowsAppropriateError() throws IOException, ServletException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(testCallee);
        when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(testCaller);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn("1/1/2020/1 1:39 pm");
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/2/2020 2:39 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.wrongFormatForStartTime());
    }

    @Test
    public void addPhoneBillToServletWithInvalidEndTimeThrowsAppropriateError() throws IOException, ServletException {
        PhoneBillServlet servlet = new PhoneBillServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(CALLEE_NUMBER_PARAMETER)).thenReturn(testCallee);
        when(request.getParameter(CALLER_NUMBER_PARAMETER)).thenReturn(testCaller);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn("1/1/2020 1:39 pm");
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/ZZ/2020 2:39 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.wrongFormatForEndTime());
    }

    @Test
    public void searchWithInvalidTimesThrowsError() throws IOException, ServletException {
        String invalidDate = "1/XX/2020 1:39 pm";
        PhoneBill phoneBill = new PhoneBill(testCustomer);
        phoneBill.addPhoneCall(new PhoneCall(testCaller, testCallee, testStartTime, testEndTime));

        PhoneBillServlet servlet = new PhoneBillServlet();
        servlet.addPhoneBill(phoneBill);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(START_TIME_PARAMETER)).thenReturn(invalidDate);
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/1/2020 2:39 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
                Messages.unParsableDate(invalidDate));
    }

    @Test
    public void searchWithMissingStartParameterThrowsError() throws IOException, ServletException {
        PhoneBill phoneBill = new PhoneBill(testCustomer);
        phoneBill.addPhoneCall(new PhoneCall(testCaller, testCallee, testStartTime, testEndTime));

        PhoneBillServlet servlet = new PhoneBillServlet();
        servlet.addPhoneBill(phoneBill);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(testCustomer);
        when(request.getParameter(END_TIME_PARAMETER)).thenReturn("1/1/2020 2:39 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
                Messages.missingRequiredParameter(START_TIME_PARAMETER));
    }
}
