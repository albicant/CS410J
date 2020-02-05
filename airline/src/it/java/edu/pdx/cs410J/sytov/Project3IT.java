package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project3} main class.
 */
public class Project3IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
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
      String str = "Test3 NUMBER PDX 03/03/2020 12:00 am ORD 03/03/2020 4:00 pm";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("int"));
  }

  @Test
  public void testDepartureTimeMalformatted() {
      String str = "Test4 123 PDX 03/03/2020 12:XX am ORD 03/03/2020 4:00 pm";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("departure"));
  }

  @Test
  public void testArrivalTimeMalformatted() {
      String str = "Test5 123 PDX 03/03/2020 12:00 am ORD 01/04/20/1 4:00 pm";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("arrival"));
  }

  @Test
  public void testUnkownCommandLineOption() {
      String str = "-fred Test6 600 PDX 03/03/2020 12:00 am ORD 04/04/2020 4:00 pm";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("Unknown option"));
  }

  @Test
  public void testUnkownCommandLineArgument() {
      String str = "Test7 123 PDX 03/03/2020 12:00 am ORD 04/04/2020 4:00 pm fred";
      String[] args = str.split(" ");
      MainMethodResult result = invokeMain(args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("Unknown command line arguments"));
  }

    @Test
    public void testPrintingOutAFlight() {
        String str = "-print Test8 123 PDX 03/03/2020 12:00 am ORD 05/04/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs PDX at 3/3/20, 12:00 AM arrives ORD at 5/4/20, 4:00 PM"));
    }

    @Test
    public void testMultiWordAirlineName() {
        String str = "-print \"Test 9\" 123 PDX 03/03/2020 12:00 am ORD 09/04/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs PDX at 3/3/20, 12:00 AM arrives ORD at 9/4/20, 4:00 PM"));
    }

    @Test
    public void testMissingArrivalTime() {
        String str = "Test10 123 PDX 03/03/2020 12:00 am ORD 09/09/2020";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments."));
    }

    @Test
    public void testAirportCodeIsTooShort() {
        String str = "Test11 123 P 03/03/2020 12:00 am O 09/09/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("has to contain the three-letter code"));
    }

    @Test
    public void testAirportCodeHasNumber() {
        String str = "Test12 123 PD7 03/03/2020 12:00 am O 09/09/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("has to contain the three-letter code"));
    }

    @Test
    public void testTextfileNameIsMissing() {
        String str = "-print -textFile Test13 123 PDX 03/03/2020 12:00 am ORD 09/09/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testAirlineWritesToAFile() {
        String str = "-textFile test14.txt -print Test14 123 PDX 03/03/2020 12:00 am ORD 09/09/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs PDX at 3/3/20, 12:00 AM arrives ORD at 9/9/20, 4:00 PM"));
    }

    @Test
    public void testTextfileHasExtraArguments() {
        String str = "-textFile test15.txt -print Test15 123 PDX 03/03/2020 12:00 am ORD 09/09/2020 4:00 pm extra";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Unknown command line arguments"));
    }

    @Test
    public void testTextfileIsMalformatted() throws IOException {
        String file_name = "test16.txt";
        File file = new File(file_name);
        if(!file.exists()) {
            Writer writer = new FileWriter(file_name);
            writer.write("Test16\n123PDX03/03/2020 12:00 am O/RD 09/09/2020 4:00 pm");
            writer.flush();
            writer.close();
        }

        String str = "-textFile " + file_name + " -print Test16 123 PDX 03/03/2020 12:00 am ORD 09/09/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot create the airline from this file"));
    }

    @Test
    public void testAirlineNameIsDifferentThanTheOneFoundInTheTextFile() throws IOException {
        String file_name = "test17.txt";
        File file = new File(file_name);
        if(!file.exists()) {
            Writer writer = new FileWriter(file_name);
            writer.write("Test17\n123 PDX 03/03/2020 12:00 am ORD 09/09/2020 4:00 pm\n");
            writer.flush();
            writer.close();
        }

        String str = "-textFile " + file_name + " -print Test16 123 PDX 03/03/2020 12:00 am ORD 09/09/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("airline name from the file does not match the airline name from the console!"));
    }

    @Test
    public void testCreateAirlineFromMalformatedFileName() {
        String str = "-textFile . -print Test18 123 PDX 03/03/2020 12:00 am ORD 09/09/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot create airline from the file"));
    }

    @Test
    public void testTextFileAndPrettyFileAreTheSame() {
        String str = "-textFile test.airline -pretty test.airline -print Test19 123 PDX 03/03/2020 12:00 am ORD 09/09/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Error: text file name and pretty file name cannot be the same!"));
    }

    @Test
    public void testAirlineCanBeSavedToPrettyFile() {
        String str = "-textFile test20.airline -pretty test20.pretty -print Test20 123 AAA 09/03/2020 12:00 am BBB 09/03/2020 4:00 pm";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain(args);
        assertThat(result.getExitCode(), equalTo(0));
    }


}