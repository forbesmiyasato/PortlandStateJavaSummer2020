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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

  @Test
  public void requestWithNoCustomerReturnMissingParameter() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
            Messages.missingRequiredParameter("customer"));
  }

  @Test
  public void requestCustomerWithNoPhoneBillReturnsNotFound() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();
    String customerName = "Dave";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(customerName);
    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customerName));
  }

  @Test
  public void addPhoneCallToBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "TEST WORD";
    String callerPhoneNumber = "808-200-6188";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("customer")).thenReturn(customer);
    when(request.getParameter("callerNumber")).thenReturn(callerPhoneNumber);

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
    assertThat(phoneCall.getCaller(), equalTo(callerPhoneNumber));
  }

}
