package edu.pdx.cs410J.miyasato;

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
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  public void test0RemoveAllPhoneBills() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllPhoneBills();
  }

  @Test
  public void test1NoneExistentPhoneBillThrowsException() throws IOException{
    PhoneBillRestClient client = newPhoneBillRestClient();
    try {
      client.getPhoneBill("Dave");
      fail("Should have thrown a PhoneBillRestException");
    } catch (PhoneBillRestClient.PhoneBillRestException e) {
      assertThat(e.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
    }
  }
}
