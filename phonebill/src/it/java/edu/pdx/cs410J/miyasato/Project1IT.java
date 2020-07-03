package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.core.StringContains;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project1.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void invokingMainWithNoParameterForPrintPrintsMissingArgumentsToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print");
        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                "Not enough command line arguments to print"));
        assertThat(result.getExitCode(), equalTo(1));

    }

    @Test
    public void invokingMainWithOneParameterForPrintPrintsMissingArgumentsToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer");
        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                "Not enough command line arguments to print"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithSixParametersForPrintPrintsMissingArgumentsToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer", "808", "200",
                "1/15/2020", "19:39", "01/2/2020");
        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                "Not enough command line arguments to print"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithSevenParametersForPrintPrintsNoErrorToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer", "808", "200",
                "1/15/2020", "19:39", "01/2/2020", "1:03");
        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                ""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithNineParametersForPrintPrintsExtraneousArgumentsToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer", "808", "200",
                "1/15/2020", "19:39", "01/2/2020", "1:03", "1");

        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                "Extraneous command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithInvalidOptionPrintsInvalidOptionError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-false");

        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString("INVALID OPTION!"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithSevenOptionsPrintsCorrectOutputToStandardOutput() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer", "808", "200",
                "1/15/2020", "19:39", "01/2/2020", "1:03");

        assertThat(result.getTextWrittenToStandardOut(), StringContains.containsString(
                "Phone call from 808 to 200 from 1/15/2020 19:39 to 01/2/2020 1:03"));
    }
}