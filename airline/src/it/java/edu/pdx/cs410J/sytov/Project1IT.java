package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
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
  public void testPrintsREADME() {
      MainMethodResult result = invokeMain("-README");
      assertThat(result.getExitCode(), equalTo(0));
      assertThat(result.getTextWrittenToStandardOut(), containsString("Gennadii Sytov"));
  }

  @Test
  public void testFlightNumberNotInteger() {
      String str = "Test3 NUMBER PDX 03/03/2020 12:00 ORD 03/03/2020 16:00";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("int"));
  }

  @Test
  public void testDepartureTimeMalformatted() {
      String str = "Test4 123 PDX 03/03/2020 12:XX ORD 03/03/2020 16:00";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("departure"));
  }

  @Test
  public void testArrivalTimeMalformatted() {
      String str = "Test5 123 PDX 03/03/2020 12:00 ORD 01/04/20/1 16:00";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("arrival"));
  }

  @Test
  public void testUnkownCommandLineOption() {
      String str = "-fred Test6 600 PDX 03/03/2020 12:00 ORD 04/04/2020 16:00";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("Unknown option"));
  }

  @Test
  public void testUnkownCommandLineArgument() {
      String str = "Test7 123 PDX 03/03/2020 12:00 ORD 04/04/2020 16:00 fred";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("Unknown command line arguments"));
  }

    @Test
    public void testPrintingOutAFlight() {
        String str = "-print Test8 123 PDX 03/03/2020 12:00 ORD 05/04/2020 16:00";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), equalTo("Flight 123 departs PDX at 03/03/2020 12:00 arrives ORD at 05/04/2020 16:00\n"));
    }

    @Test
    public void testMultiWordAirlineName() {
        String str = "-print \"Test 9\" 123 PDX 03/03/2020 12:00 ORD 09/04/2020 16:00";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), equalTo("Flight 123 departs PDX at 03/03/2020 12:00 arrives ORD at 09/04/2020 16:00\n"));
    }

    @Test
    public void testMissingArrivalTime() {
        String str = "Test10 123 PDX 03/03/2020 12:00 ORD 09/09/2020";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments."));
    }

    @Test
    public void testAirportCodeIsTooShort() {
        String str = "Test11 123 P 03/03/2020 12:00 O 09/09/2020 16:00";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("has to contain the three-letter code"));
    }

    @Test
    public void testAirportCodeHasNumber() {
        String str = "Test12 123 PD7 03/03/2020 12:00 O 09/09/2020 16:00";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("has to contain the three-letter code"));
    }

}