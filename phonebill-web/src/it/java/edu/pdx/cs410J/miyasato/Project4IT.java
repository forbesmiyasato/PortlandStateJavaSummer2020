package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void test0RemoveAllMappings() throws IOException {
      PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllPhoneBills();
    }

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing Command Line Arguments"));
    }

    @Test
    public void test2NoHost() {
        MainMethodResult result = invokeMain( Project4.class, "-port", "8080", "-search");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing host"));
    }

    @Test
    public void test3NoPort() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "Customer");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing port"));
    }

    @Test
    public void test4NoArguments() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing Command Line Arguments"));
    }

    @Test
    public void test5OneArgument() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Customer");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Got an HTTP Status Code of 404"));
    }

    @Test
    public void test6TwoArguments() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "Customer", "Invalid");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid behavior"));
    }

    @Test
    public void test7PrintNoArgs() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", "Invalid");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid behavior"));
    }

    @Test
    public void test8PrintWithValidArgs() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print",
                "customer", "808-200-6188", "200-200-2000", "1/1/2020", "1:39", "am", "01/2/2020", "1:03", "pm");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Phone call from"));
    }

    @Test
    public void test9NonIntegerPortNumber() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", "kweq", "-print", "Invalid");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("must be an integer"));
    }

    @Test
    public void test10SearchWithOneArguments() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "one");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments for search"));
    }

    @Test
    public void test11SearchWithEightArguments() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "name",
                "1/1/2020", "1:39", "am", "01/2/2020", "1:03", "pm", "extra");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Extraneous command line arguments for search"));
    }

    @Test
    public void test12SearchWithValidArguments() throws IOException {
        PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));

        client.addPhoneCall("name", "808-200-6188", "808-200-6188", "1/1/2020 1:11 am",
                "1/2/2020 1:11 am");

        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "name",
                "1/1/2020", "1:39", "am", "01/2/2020", "1:03", "pm");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void test13PrintWithSearch() {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", "-print", "name",
                "1/1/2020", "1:39", "am", "01/2/2020", "1:03", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("No phone call to print!"));
    }

    @Test
    public void test14OneArgumentWithCustomer() throws IOException {
        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "name");
        assertThat(result.getExitCode(), equalTo(0));
    }
}