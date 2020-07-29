package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.ParserException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBillRestClientIT {

    public final String testCaller = "808-200-6188";
    public final String testCallee = "808-200-6188";
    public final String testStartTime = "1/1/2020 9:39 am";
    public final String testEndTime = "01/2/2020 1:03 pm";
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    private PhoneBillRestClient newPhoneBillRestClient() {
        int port = Integer.parseInt(PORT);
        return new PhoneBillRestClient(HOSTNAME, port);
    }

//  @Test
//  public void test0RemoveAllPhoneBills() throws IOException {
//    PhoneBillRestClient client = newPhoneBillRestClient();
//    client.removeAllPhoneBills();
//  }

    @Test
    public void test1NoneExistentPhoneBillThrowsException() throws IOException, ParserException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        try {
            client.getPhoneBill("Dave");
            fail("Should have thrown a PhoneBillRestException");
        } catch (PhoneBillRestClient.PhoneBillRestException e) {
            assertThat(e.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }

    @Test
    public void test2AddPhoneCall() throws IOException, ParserException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        String customer = "TEST CUSTOMER";

        client.addPhoneCall(customer, testCaller, testCallee, testStartTime, testEndTime);

        PhoneBill phoneBill = client.getPhoneBill(customer);
        assertThat(phoneBill.getCustomer(), equalTo(customer));

        PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
        assertThat(phoneCall.getCaller(), equalTo(testCaller));
    }

    @Test
    public void test3AddPhoneCallToExistingPhoneBill() throws IOException, ParserException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        String customer = "TEST CUSTOMER";
        String newCallerNumber = "123-456-7890";
        client.addPhoneCall(customer, newCallerNumber, testCallee, testStartTime, testEndTime);

        PhoneBill phoneBill = client.getPhoneBill(customer);
        assertThat(phoneBill.getCustomer(), equalTo(customer));

        PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
        assertThat(phoneCall.getCaller(), equalTo(newCallerNumber));
    }
}
