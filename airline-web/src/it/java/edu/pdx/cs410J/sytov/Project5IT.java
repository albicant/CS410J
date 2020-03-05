package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.sytov.AirlineRestClient.AirlineRestException;
import org.hamcrest.CoreMatchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void testPrintsREADME() {
        MainMethodResult result = invokeMain(Project5.class,"-README");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Gennadii Sytov"));
    }

    @Test
    public void test0RemoveAllMappings() throws IOException {
      AirlineRestClient client = new AirlineRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllDictionaryEntries();
    }

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project5.MISSING_ARGS));
    }

    @Test
    public void test2MissingCommandLineArguments() {
        String str = "-host " + HOSTNAME + " -port " + PORT;
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain( Project5.class, args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments."));
    }

    @Test
    public void test3ConnectionToTheServesIsInvalid() {
        String str = "-host " + HOSTNAME + " -port " + 0 + " TestAir";
        String[] args = str.split(" ");
        MainMethodResult result = invokeMain( Project5.class, args);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Cannot connect to the server."));
    }

//    @Test(expected = AirlineRestException.class)
//    public void test3NoDefinitionsThrowsAppointmentBookRestException() throws Throwable {
//        String str = "-host " + HOSTNAME + " -port " + 8080;
//        String[] args = str.split(" ");
//        try {
//            MainMethodResult result = invokeMain( Project5.class, args);
//
//        } catch (IllegalArgumentException ex) {
//            throw ex.getCause().getCause();
//        }
//    }

//    @Test
//    public void test4AddNewAirline() {
//        String host_port = "-host " + HOSTNAME + " -port " + PORT;
//        String str = host_port + " -print Test4 123 PDX 03/03/2020 12:00 am ORD 03/03/2020 4:00 pm";
//        String[] args = str.split(" ");
//        MainMethodResult result = invokeMain( Project5.class, args);
//        assertThat(result.getExitCode(), equalTo(0));
//        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs PDX at 3/3/20, 12:00 AM arrives ORD at 3/3/20, 4:00 PM"));
//    }

//    @Test
//    public void test5FlightNumberIsMalformatted() {
//        String host_port = "-host " + HOSTNAME + " -port " + PORT;
//        String str = host_port + " Test5 NUMBER PDX 03/03/2020 12:00 am ORD 03/03/2020 4:00 pm";
//        String[] args = str.split(" ");
//        MainMethodResult result = invokeMain( Project5.class, args);
//        assertThat(result.getExitCode(), equalTo(1));
//        assertThat(result.getTextWrittenToStandardError(), containsString("Error: Cannot convert 'NUMBER' to type int!"));
//    }

//    @Test
//    public void test6FlightTimeIsMalformatted() {
//        String host_port = "-host " + HOSTNAME + " -port " + PORT;
//        String str = host_port + " Test5 123 PDX 03/03/20 12:00 am ORD 03/03/2020 4:00 pm";
//        String[] args = str.split(" ");
//        MainMethodResult result = invokeMain( Project5.class, args);
//        assertThat(result.getExitCode(), equalTo(1));
//        assertThat(result.getTextWrittenToStandardError(), containsString("Error: cannot create the flight."));
//    }


//    @Test
//    public void test4AddDefinition() {
//        String word = "WORD";
//        String definition = "DEFINITION";
//
//        MainMethodResult result = invokeMain( Project5.class, HOSTNAME, PORT, word, definition );
//        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
//        String out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.definedWordAs(word, definition)));
//
//        result = invokeMain( Project5.class, HOSTNAME, PORT, word );
//        out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.formatDictionaryEntry(word, definition)));
//
//        result = invokeMain( Project5.class, HOSTNAME, PORT );
//        out = result.getTextWrittenToStandardOut();
//        assertThat(out, out, containsString(Messages.formatDictionaryEntry(word, definition)));
//    }
}