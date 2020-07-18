package edu.pdx.cs410J.miyasato;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.core.StringContains;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project3.class, args);
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
                "Missing command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));

    }

    @Test
    public void invokingMainWithOneParameterForPrintPrintsMissingArgumentsToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer");
        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                "Missing command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithSixParametersForPrintPrintsMissingArgumentsToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer", "808", "200",
                "1/15/2020", "19:39", "01/2/2020");
        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                "Missing command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithSevenParametersForPrintPrintsNoErrorToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer", "808-200-6188", "200-200-2000",
                "1/15/2020", "19:39", "01/2/2020", "1:03");
        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                ""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithEightParametersForPrintPrintsExtraneousArgumentsToStandardError() {
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
    public void invokingMainWithSevenParametersForPrintPrintsCorrectOutputToStandardOutput() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "customer", "808-200-6188", "200-200-2000",
                "1/15/2020", "19:39", "01/2/2020", "1:03");

        assertThat(result.getTextWrittenToStandardOut(), StringContains.containsString(
                "Phone call from 808-200-6188 to 200-200-2000 from 1/15/2020 19:39 to 01/2/2020 1:03"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithReadMeOptionPrintsReadMeToStandardOutput() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-README");

        assertThat(result.getTextWrittenToStandardOut(), StringContains.containsString(
                "The Phone Bill Application reads in a series of options and arguments with information"));

        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithReadMeThenPrintOptionWithNineCLArgumentsPrintsNoErrorToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-README", "-print", "customer", "808", "200",
                "1/15/2020", "19:39", "01/2/2020", "1:03");

        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                ""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithPrintThenReadMeOptionWithNineCLArgumentsPrintsNoErrorToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "-README", "customer", "808", "200",
                "1/15/2020", "19:39", "01/2/2020", "1:03");

        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                ""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithReadMeThenPrintOptionWithTwoCLArgumentsPrintsNoErrorToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-README", "-print");

        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                ""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithPrintThenReadMeOptionPrintsNoErrorToStandardErrorAndPrintsREADME() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "-README");

        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                ""));
        assertThat(result.getTextWrittenToStandardOut(), StringContains.containsString(
                "The Phone Bill Application reads in a series of options and arguments with information"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithReadMeThenPrintOptionWithTenCLArgumentsPrintsNoErrorToStandardError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-README", "-print", "customer", "808", "200",
                "1/15/2020", "19:39", "01/2/2020", "1:03", "1");

        assertThat(result.getTextWrittenToStandardError(), StringContains.containsString(
                ""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithSevenArgumentsAndNoOptionsPrintNothingToScreenAndExitsSuccessfully() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("customer", "808-200-6188", "200-200-2000",
                "1/15/2020", "19:39", "01/2/2020", "1:03");

        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithTextFileOptionAndNoTextFileCausesAnError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-textFile");

        assertThat(result.getTextWrittenToStandardError(), containsString("Missing file name and command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithTextFileOptionAndJustTextFileNameCausesAnError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-textFile", "test");

        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithTextFileOptionAndTextFileNameAndValidArgumentsDoesNotCauseAnError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-textFile", "test", "Test", "808-200-6188",
                "200-200-2000", "1/15/2020", "19:39", "01/2/2020", "1:03");

        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithTextFileOptionAndTextFileNameAndInvalidArgumentsWithReadMeDoesNotCauseAnError() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-textFile", "test", "-README", "Test", "808-200-6188",
                "200-200-2000", "1/15/2020", "19:39", "01+2/2020");

        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void invokingMainWithPrintAndTextFileOptionAndTextFileNameAndValidArgumentsWithReadMeDoesNotPrint() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-textFile", "test", "-README", "Test", "808-200-6188",
                "200-200-2000", "1/15/2020", "19:39", "01/2/2020", "12:15");

        assertThat(result.getTextWrittenToStandardOut(), StringContains.containsString(
                "The Phone Bill Application reads in a series of options and arguments with information"));
        assertThat(result.getTextWrittenToStandardOut(), not(containsString("Phone call from 808-200-6188 to " +
                "200-200-2000 from 1/15/2020 19:39 to 01/2/2020 12:15")));
        assertThat(result.getExitCode(), equalTo(0));
    }
}