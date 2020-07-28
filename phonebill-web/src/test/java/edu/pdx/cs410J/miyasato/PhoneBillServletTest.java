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
    String customerName = "Dave";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customerName);
    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customerName));
  }

  @Test
  public void addPhoneCallToBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "TEST WORD";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
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

    PhoneBill phoneBill = servlet.getPhoneBill(customer);
    assertThat(phoneBill, notNullValue());
    assertThat(phoneBill.getCustomer(), equalTo(customer));

    PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
    assertThat(phoneCall.getCaller(), equalTo(testCaller));
    assertThat(phoneCall.getCallee(), equalTo(testCallee));

  }

  @Test
  public void requestingExistingPhoneBillDumpsItToPrintWriter() throws IOException, ServletException {
    String customer = "TEST CUSTOMER";

    PhoneBill bill = new PhoneBill(customer);
    bill.addPhoneCall(new PhoneCall(testCaller, testCallee, testStartTime, testEndTime));

    PhoneBillServlet servlet = new PhoneBillServlet();
    servlet.addPhoneBill(bill);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    HttpServletResponse response = mock(HttpServletResponse.class);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);
    String textPhoneBill = sw.toString();

    assertThat(textPhoneBill, containsString(customer));
    assertThat(textPhoneBill, containsString(testCaller));
  }
}
